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
import hw2.AddAnimalDialog;
import hw1.Swimmable;
public class SaveObjectState extends JDialog implements ActionListener 
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
	public   SaveObjectState(AquaPanel MyAquaPanel,String MyTitle,HashSet<Swimmable>  AllAnimalHashSet,HashSet<Immobile> AllPlantsHashSet)
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
		//temp.setRowCount(0);//remove old info
		String[] Mystring=new String[infoLableNames.length];//{ "Animal", "Color", "size", "Hor.Speed", "Ver.Speed", "Eat Counter"};

		this.add(pane);//,BorderLayout.CENTER);//add the info table to the main frame
		
		KindLbl=new JLabel("Chose Sea Creature:");//set Labels to all the fields
		KindLbl.setPreferredSize(new Dimension(100, 100) );
		this.add(KindLbl);
		AnimalCmb = new JComboBox<String>();//make a comboBox with all the names
		int i=0;
		swimarr=new Swimmable[RefAllAnimalHashSet.size()];
		java.util.Iterator<Swimmable> itr = RefAllAnimalHashSet.iterator();
		while(itr.hasNext()) 
		{
			swimarr[i] = (Swimmable)itr.next().Clone(true);

			AnimalCmb.addItem(swimarr[i].getAnimalName()+"("+(i+1)+")");
			Mystring[0]=swimarr[i].getAnimalName() + (" ["+(i+1)+"]");
			Mystring[1]=swimarr[i].getColor();
			Mystring[2]=Integer.toString(swimarr[i].getSize());
			Mystring[3]=Integer.toString(swimarr[i].getHorSpeed());
			Mystring[4]=Integer.toString(swimarr[i].getVerSpeed());
			Mystring[5]=Integer.toString(swimarr[i].getEatCount());
			temp.addRow(Mystring);
		
			i++;
		}
		Immobarr=new Immobile[RefAllPlantsHashSet.size()];
		int j=0;
		for(Immobile t:RefAllPlantsHashSet)
		{
			Immobarr[j] = (Immobile) t.Clone(true);
			AnimalCmb.addItem(t.getname()+"("+(j+1)+")");
			
			Mystring[0]=Immobarr[j].getname() + (" ["+(j+1)+"]");
			Mystring[1]=Immobarr[j].getColor();
			Mystring[2]=Integer.toString(Immobarr[j].getSize());
			Mystring[3]=null;//Integer.toString(s.getHorSpeed());
			Mystring[4]=null;//Integer.toString(s.getVerSpeed());
			Mystring[5]=null;//Integer.toString(s.getEatCount());
			//TotalEat+=//s.getEatCount();
			temp.addRow(Mystring);
			
			j++;
		}
		
		AnimalCmb.addActionListener(this);
		add(AnimalCmb);


		SaveBtn=new JButton("Save To Memento");
		SaveBtn.addActionListener(this);
		add(SaveBtn);
		
		CancelBtn=new JButton("Cancel");
		CancelBtn.addActionListener(this);
		add(CancelBtn);
		
		this.setVisible(true);	
	}
	/**
	 * Receive Color and return String "(R,G,B)" of the color
	 * @param c Color
	 * @return String
	 */
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
			int index=AnimalCmb.getSelectedIndex();
			
			
		
			
			
			if(index+1>swimarr.length){
				index-= swimarr.length;
			Originator temp = new Originator();
			
			
			temp.setState(Immobarr[index]);
			MyAquaPanel.addtomemento(temp.SaveToSeaCreatureMemento());

			}
			else{
		
				for(int i = 0;i<this.MyAquaPanel.mementoList.size();i++){
					if(swimarr[index].getmyid() == this.MyAquaPanel.get(i).getSeaCreature().getmyid()){
						this.MyAquaPanel.deletefrommemnto(i);
						break;
					}
				}
				Originator temp = new Originator();
				temp.setState(swimarr[index]);
				MyAquaPanel.addtomemento(temp.SaveToSeaCreatureMemento());
			}
			this.setVisible(false);
		}
	}
}