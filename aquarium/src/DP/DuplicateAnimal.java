package DP;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import hw1.Fish;
import hw1.Jellyfish;
import hw1.Swimmable;
import hw2.AquaPanel;

public class DuplicateAnimal extends JDialog implements ActionListener 
{
	protected static final long serialVersionUID = 1L;
	protected Integer[] Speeds={1,2,3,4,5,6,7,8,9,10};//speed allowed to the animals
	protected String[] ColorNames = {"Red","Blue","Green","Orange","Cyan","Pink","Black"};
	protected Color[] ColorId={Color.red,Color.blue,Color.green,Color.orange,Color.cyan,Color.pink,Color.black};
	protected String[] FishsNames;// = {"Fish","JellyFish"};
	protected JComboBox<String> AnimalCmb,colorCmb;
	protected JSlider sizeSld;
	protected JComboBox<Integer> HorizontalCmb,VerticalCmb; 
	protected String[] LableNames = {"Select Animal","Color","Size","Speed: Horizontal", "                Vertical"};
	protected JLabel KindLbl,ColorLbl,SizeLbl,HorizontalSLbl, VerticalSLbl;
	protected JButton SaveBtn,CancelBtn;
	protected Fish MyFish;
	protected Jellyfish MyJellyFish;
	protected AquaPanel RefMyAquaPanel;
	protected Swimmable swim;
	protected HashSet<Swimmable> RefAllAnimalHashSet;
	protected Swimmable[] swimmeArr;


	public   DuplicateAnimal(AquaPanel MyAquaPanel,String MyTitle,HashSet<Swimmable> RefAllAnimalHashSet)
	{
		this.RefAllAnimalHashSet = RefAllAnimalHashSet;
		this.setLayout(new GridLayout(12,1));
		this.setSize(400,460);
		this.setLocation(MyAquaPanel.getWidth()/2-200,MyAquaPanel.getHeight()/2-150);//put on the center

		RefMyAquaPanel=MyAquaPanel;//get reference to MyAquaPanel
		KindLbl=new JLabel(LableNames[0]);//set Labels to all the fields
		ColorLbl=new JLabel(LableNames[1]);
		SizeLbl=new JLabel(LableNames[2]);
		HorizontalSLbl=new JLabel(LableNames[3]);
		VerticalSLbl=new JLabel(LableNames[4]);

		int count= 0;
		for(Swimmable s : RefMyAquaPanel.AllAnimalHashSet){

			for(Color t: ColorId){
				if(s.getColorID() == t)
					count++;
			}

			if(count == 0)//to add new colors if there is new colors not from the defaults
			{
				addnewcolor(s.getColorID());
			}
			count = 0;

		}

		AnimalCmb = new JComboBox<String>();//make a comboBox with all the names
		int i = 0;
		swimmeArr = new Swimmable[RefAllAnimalHashSet.size()];

		java.util.Iterator<Swimmable> itr = RefAllAnimalHashSet.iterator();

		while(itr.hasNext())//fill animal cmb
		{
			swimmeArr[i] = itr.next();
			AnimalCmb.addItem(swimmeArr[i].getAnimalName()+"["+ (i+1)+"]");
			i++;
		}

		this.add(KindLbl);
		add(AnimalCmb);
		AnimalCmb.addActionListener(this);


		colorCmb = new JComboBox<String>(ColorNames);//make a comboBox with all the names

		this.add(ColorLbl);
		this.add(colorCmb);

		this.add(SizeLbl);
		sizeSld= new JSlider(JSlider.HORIZONTAL,20,320,120);
		sizeSld.setMajorTickSpacing(20);
		sizeSld.setMinorTickSpacing(10);
		sizeSld.setPaintLabels(true);
		sizeSld.setPaintTicks(true);
		this.add(sizeSld);


		HorizontalCmb=new JComboBox<Integer>(Speeds);
		HorizontalCmb.setSelectedIndex(4);
		this.add(HorizontalSLbl);
		this.add(HorizontalCmb);

		VerticalCmb=new JComboBox<Integer>(Speeds);
		VerticalCmb.setSelectedIndex(4);
		this.add(VerticalSLbl);
		this.add(VerticalCmb);

		SaveBtn=new JButton("Save");
		SaveBtn.addActionListener(this);
		add(SaveBtn);

		CancelBtn=new JButton("Cancel");
		CancelBtn.addActionListener(this);
		add(CancelBtn);
		this.setVisible(true);	


		//make the first info correct
		int index = 0;
		this.sizeSld.setValue(this.swimmeArr[index].getSize());
		this.HorizontalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
		this.VerticalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
		int saveindex = 0;
		for(int j =0; j<this.ColorNames.length;j++){
			if(this.ColorNames[j].equals(swimmeArr[index].getColor())){
				saveindex = j;
				break;
			}
		}
		this.colorCmb.setSelectedIndex(saveindex);

	}

	/**
	 * Add new color to the combobox 
	 * @return void
	 */
	public void addnewcolor(Color newcol){
		String[] newcolorname;
		Color[] newcolorid;
		newcolorname = new String[this.ColorNames.length+1];

		for(int i =0;i<this.ColorNames.length;i++){
			newcolorname[i] = this.ColorNames[i];
		}
		newcolorname[this.ColorNames.length] = getColorName(newcol);

		newcolorid = new Color[this.ColorId.length+1];
		for(int i =0;i<this.ColorNames.length;i++){
			newcolorid[i] = this.ColorId[i];
		}
		newcolorid[this.ColorNames.length] = newcol;

		this.ColorNames = newcolorname;
		this.ColorId = newcolorid;

	}




	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == CancelBtn)
		{
			this.setVisible(false);//hide			
		}
		else if(e.getSource() == AnimalCmb)//Change all cmb and values to correct by index
		{
			int index = (int)AnimalCmb.getSelectedIndex();
			this.sizeSld.setValue(this.swimmeArr[index].getSize());
			this.HorizontalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
			this.VerticalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
			int saveindex = 0;
			for(int i =0; i<this.ColorNames.length;i++){
				if(this.ColorNames[i].equals(swimmeArr[index].getColor())){
					saveindex = i;
					break;
				}
			}
			this.colorCmb.setSelectedIndex(saveindex);



		}
		else if(e.getSource() == SaveBtn)//save the new animal if there is place in the AllAnimalHashSet
		{
			if(RefMyAquaPanel.AllAnimalHashSet.size()<AquaPanel.MAX_ANIMALS)//checking if there is place for more animals
			{	
				int index = (int)AnimalCmb.getSelectedIndex();

				if(this.swimmeArr[index].getAnimalName() == "Fish")
				{

					Fish newFish = (Fish)this.swimmeArr[index].Clone(false);//no need same id	
					newFish.setVerSpeed(this.VerticalCmb.getSelectedIndex() +1);
					newFish.setHorSpeed(this.HorizontalCmb.getSelectedIndex() +1);
					newFish.setsize(this.sizeSld.getValue());
					newFish.setcolorCol(this.ColorId[this.colorCmb.getSelectedIndex()]);
					newFish.setcolor(this.ColorNames[this.colorCmb.getSelectedIndex()]);
					if(newFish!=null)//if was created
					{
						this.setVisible(false);	//close save form		
						RefMyAquaPanel.AllAnimalHashSet.add(newFish);
						newFish.start();//start the new tread
						RefMyAquaPanel.repaint();//paint it
					}
				}
				else
				{
					Jellyfish newFish = (Jellyfish)this.swimmeArr[index].Clone(false);	
					newFish.setVerSpeed(this.VerticalCmb.getSelectedIndex() +1);
					newFish.setHorSpeed(this.HorizontalCmb.getSelectedIndex() +1);
					newFish.setsize(this.sizeSld.getValue());
					newFish.setcolorCol(this.ColorId[this.colorCmb.getSelectedIndex()]);
					newFish.setcolor(this.ColorNames[this.colorCmb.getSelectedIndex()]);
					if(newFish!=null)//if was created
					{
						this.setVisible(false);	//close save form		
						RefMyAquaPanel.AllAnimalHashSet.add(newFish);
						newFish.start();//start the new tread
						RefMyAquaPanel.repaint();//paint it
					}

				}

			}//if (RefMyAquaPanel.AllAnimalHashSet.size()<MAX_ANIMALS)
			else
			{
				JOptionPane.showMessageDialog(this,"The maximum Animals is "+AquaPanel.MAX_ANIMALS);
				//				System.out.println("The maximum Animals is "+RefMyAquaPanel.MAX_ANIMALS);
			}
		}

	}

	/**
	 * Receive Color and return String "(R,G,B)" of the color
	 * @param c Color
	 * @return String
	 */
	public String getColorName(Color c) {
		return "("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
	}




}
