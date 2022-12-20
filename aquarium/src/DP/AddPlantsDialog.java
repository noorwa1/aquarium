

package DP;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import hw2.AquaPanel;

public class AddPlantsDialog extends JDialog implements ActionListener 
{
	protected static final long serialVersionUID = 1L;
	protected Integer[] Speeds={1,2,3,4,5,6,7,8,9,10};//speed allowed to the animals
	protected String ColorNames = "Green";
	protected Color ColorId= Color.green;
	protected String[] PlantNames = {"Zostera","Laminaria"};
	protected JComboBox<String> kindCmb;
	protected JSlider sizeSld;
	protected String[] LableNames = {"Kind","Size"};
	protected JLabel KindLbl,SizeLbl;
	protected JButton SaveBtn,CancelBtn;
	protected Immobile imm;
	protected AquaPanel RefMyAquaPanel;


	public   AddPlantsDialog(AquaPanel MyAquaPanel,String MyTitle)
	{
		this.setLayout(new GridLayout(6,1));
		this.setSize(400,360);
		this.setLocation(MyAquaPanel.getWidth()/2-200,MyAquaPanel.getHeight()/2-150);//put on the center

		RefMyAquaPanel=MyAquaPanel;//get reference to MyAquaPanel
		KindLbl=new JLabel(LableNames[0]);//set Labels to all the fields
		SizeLbl=new JLabel(LableNames[1]);
		kindCmb = new JComboBox<String>(PlantNames);//make a comboBox with all the names

		this.add(KindLbl);
		add(kindCmb);



		this.add(SizeLbl);
		sizeSld= new JSlider(JSlider.HORIZONTAL,20,320,120);
		sizeSld.setMajorTickSpacing(20);
		sizeSld.setMinorTickSpacing(10);
		sizeSld.setPaintLabels(true);
		sizeSld.setPaintTicks(true);
		this.add(sizeSld);


		SaveBtn=new JButton("Save");
		SaveBtn.addActionListener(this);
		add(SaveBtn);

		CancelBtn=new JButton("Cancel");
		CancelBtn.addActionListener(this);
		add(CancelBtn);
		this.setVisible(true);	

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
			if(RefMyAquaPanel.AllPlantsHashSet.size()<AquaPanel.MAX_PLANTS)//checking if there is place for more animals
			{	

				PlantFactory rc = new PlantFactory(RefMyAquaPanel,this);
				imm =  (Immobile)rc.produceSeaCreature(kindCmb.getSelectedItem().toString());
				if(imm!=null)//if was created
				{
					this.setVisible(false);	//close save form					
					RefMyAquaPanel.AllPlantsHashSet.add(imm);
					RefMyAquaPanel.repaint();//paint it
				}

			}//if (RefMyAquaPanel.AllAnimalHashSet.size()<MAX_ANIMALS)
			else
			{
				JOptionPane.showMessageDialog(this,"The maximum Animals is "+AquaPanel.MAX_PLANTS);
				//				System.out.println("The maximum Animals is "+RefMyAquaPanel.MAX_ANIMALS);
			}
		}

	}






}
