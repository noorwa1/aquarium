package DP;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import hw2.AquaPanel;
import hw1.Swimmable;
import hw2.AddAnimalDialog;
public class RestoreObjectState extends JDialog implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private String[] infoLableNames = { "Animal", "Color", "size", "Hor.Speed", "Ver.Speed", "Eat Counter"};
	protected JTable infoJTable;
	private JScrollPane pane;
	protected JComboBox<String> AnimalCmb;
	private JButton SaveBtn,CancelBtn;
	protected JLabel KindLbl;
	protected Swimmable[] swimarr;
	protected Immobile[] Immobarr;
	protected HashSet<Swimmable>  RefAllAnimalHashSet;
	protected HashSet<Immobile> RefAllPlantsHashSet;
	protected String[] ColorNames = {"Red","Blue","Green","Orange","Cyan","Pink","Black"};
	protected Color[] ColorId={Color.red,Color.blue,Color.green,Color.orange,Color.cyan,Color.pink,Color.black};
	protected AddAnimalDialog RefAddAnimalDialog;
	DefaultTableModel temp;
	protected AquaPanel MyAquaPanel;
	public   RestoreObjectState(AquaPanel MyAquaPanel,String MyTitle,HashSet<Swimmable>  AllAnimalHashSet,HashSet<Immobile> AllPlantsHashSet)
	{
		this.MyAquaPanel = MyAquaPanel;
		this.RefAllAnimalHashSet=AllAnimalHashSet;
		this.RefAllPlantsHashSet=AllPlantsHashSet;
		//this.setLayout(new GridLayout(5,1));
		this.setLayout(new GridLayout(5,1));
		this.setSize(600,700);
		this.setLocation(MyAquaPanel.getWidth()/2-200,MyAquaPanel.getHeight()/2-150);//put on the center
		DefaultTableModel model = new DefaultTableModel(infoLableNames, 0);//set coloms JLables and zero rows -added after
		infoJTable = new JTable(model); //insert the model to the JTable
		pane = new JScrollPane(infoJTable);//make a JScrollPane from all the info table
		temp =  (DefaultTableModel) infoJTable.getModel();
		String[] Mystring=new String[infoLableNames.length];//{ "Animal", "Color", "size", "Hor.Speed", "Ver.Speed", "Eat Counter"};

		this.add(pane);//,BorderLayout.CENTER);//add the info table to the main frame

		KindLbl=new JLabel("Chose Sea Creature:");//set Labels to all the fields
		KindLbl.setPreferredSize(new Dimension(100, 100) );
		this.add(KindLbl);
		AnimalCmb = new JComboBox<String>();//make a comboBox with all the names
		int i=0;
		swimarr=new Swimmable[RefAllAnimalHashSet.size()];
		Swimmable rc1;
		Immobile rc2;
		for(i=0;i<MyAquaPanel.mementoList.size();i++){
			Originator originator = new Originator();
			originator.getStateFromMemento(MyAquaPanel.get(i));

			try{
				rc1 = (Swimmable)originator.getState();
				rc1.getAnimalName();
				rc2 = null;
			}catch (Exception e){
				rc2 = (Immobile)originator.getState();
				rc2.getname();
				rc1 = null;
			}
			if(rc2 == null){
				AnimalCmb.addItem(rc1.getAnimalName()+"("+(i+1)+")");
				Mystring[0]=rc1.getAnimalName() + (" ["+(i+1)+"]");
				Mystring[1]=rc1.getColor();
				Mystring[2]=Integer.toString(rc1.getSize());
				Mystring[3]=Integer.toString(rc1.getHorSpeed());
				Mystring[4]=Integer.toString(rc1.getVerSpeed());
				Mystring[5]=Integer.toString(rc1.getEatCount());
				temp.addRow(Mystring);
				rc1 = null;
			}
			else if(rc1 == null){
				AnimalCmb.addItem(rc2.getname()+"("+(i+1)+")");
				Mystring[0]=rc2.getname() + (" ["+(i+1)+"]");
				Mystring[1]=rc2.getColor();
				Mystring[2]=Integer.toString(rc2.getSize());
				Mystring[3]=null;//Integer.toString(rc1.getHorSpeed());
				Mystring[4]=null;//Integer.toString(rc1.getVerSpeed());
				Mystring[5]=null;//Integer.toString(rc1.getEatCount());
				temp.addRow(Mystring);
				rc2 = null;

			}
		}


		AnimalCmb.addActionListener(this);
		add(AnimalCmb);
		SaveBtn=new JButton("Restor from Memento");
		SaveBtn.addActionListener(this);
		add(SaveBtn);
		CancelBtn=new JButton("Cancel");
		CancelBtn.addActionListener(this);
		add(CancelBtn);
		this.setVisible(true);	
	}
	public String getColorName(Color c) 
	{
		return "("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{


		if(e.getSource() == CancelBtn)
		{
			this.setVisible(false);//hide			
		}
		else if(e.getSource() == SaveBtn)//save the new animal if there is place in the AllAnimalHashSet
		{
			Swimmable rc1;
			Immobile rc2;
			int index=AnimalCmb.getSelectedIndex();
			Originator originator = new Originator();

			originator.getStateFromMemento(MyAquaPanel.get(index));

			int flag = 0;
			try{
				rc1 = (Swimmable)originator.getState();
				rc2 = null;
				flag = 0;
			}catch (Exception f){
				rc2 = (Immobile)originator.getState();
				rc1 = null;
				flag = 1;
			}
			int id;
			if(flag == 0){
				id = rc1.getmyid();
				int ifaquariumnull = 0;
				for (Swimmable s : MyAquaPanel.AllAnimalHashSet)//draw Animal  SET
				{


					if(s.getmyid() == id){
						s.setcolorCol(rc1.getColorID());
						s.setSize(rc1.getSize());
						s.setX(rc1.getX());
						s.setY(rc1.getY());
						s.setSpeed(rc1.getHorSpeed(), rc1.getHorSpeed());
						s.setEatCount(rc1.getEatCount());
						MyAquaPanel.repaint();
						ifaquariumnull = 1;
						break;
					}

				} 
				if(ifaquariumnull == 0){//no have weimmibol null
					MyAquaPanel.AllAnimalHashSet.add((Swimmable)rc1.Clone(true));
					int thisidfish = rc1.getmyid();
					for (Swimmable s : MyAquaPanel.AllAnimalHashSet) {
						if(thisidfish == s.getmyid())
							s.start();
					}
					MyAquaPanel.repaint();
				}
				rc1 = null;	    	 
			}


			if(flag == 1){
				id = rc2.getmyid();
				int ifaquariumnull = 0;
				for (Immobile d : MyAquaPanel.AllPlantsHashSet)
				{

					if(d.getmyid() == id){

						d.setsize(rc2.getSize());
						d.setx(rc2.getx());
						d.sety(rc2.gety());
						MyAquaPanel.repaint();
						ifaquariumnull = 1;
						break;

					}

				}
				if(ifaquariumnull == 0){//no have plant null
					MyAquaPanel.AllPlantsHashSet.add((Immobile)rc2.Clone(true));
					MyAquaPanel.repaint();
				}
				rc2 = null;
			}
			this.setVisible(false);
		}

	}

}
