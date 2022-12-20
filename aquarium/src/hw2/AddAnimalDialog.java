package hw2;
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

import DP.AnimalFactory;
import hw1.Fish;
import hw1.Jellyfish;
import hw1.Swimmable;

public class AddAnimalDialog extends JDialog implements ActionListener 
{
	protected static final long serialVersionUID = 1L;
	protected Integer[] Speeds={1,2,3,4,5,6,7,8,9,10};//speed allowed to the animals
	public String[] ColorNames = {"Red","Blue","Green","Orange","Cyan","Pink","Black"};
	public Color[] ColorId={Color.red,Color.blue,Color.green,Color.orange,Color.cyan,Color.pink,Color.black};
	protected String[] FishsNames = {"Fish","JellyFish"};
	protected JComboBox<String> kindCmb;
	public JComboBox<String> colorCmb;
	public JSlider sizeSld;
	public JSlider FeqSLD;
	public JComboBox<Integer> HorizontalCmb;
	public JComboBox<Integer> VerticalCmb; 
	protected String[] LableNames = {"Kind","Color","Size","Speed: Horizontal", "                Vertical","The frequency of eating (by number of shifts)"};
	protected JLabel KindLbl,ColorLbl,SizeLbl,HorizontalSLbl, VerticalSLbl,frqLbl;
	protected JButton SaveBtn,CancelBtn;
	protected Fish MyFish;
	protected Jellyfish MyJellyFish;
	protected AquaPanel RefMyAquaPanel;
	protected Swimmable swim;

	public   AddAnimalDialog(AquaPanel MyAquaPanel,String MyTitle)
	{
		this.setLayout(new GridLayout(14,1));
		this.setSize(400,560);
		this.setLocation(MyAquaPanel.getWidth()/2-200,MyAquaPanel.getHeight()/2-150);//put on the center



		RefMyAquaPanel=MyAquaPanel;//get reference to MyAquaPanel
		KindLbl=new JLabel(LableNames[0]);//set Labels to all the fields
		ColorLbl=new JLabel(LableNames[1]);
		SizeLbl=new JLabel(LableNames[2]);
		HorizontalSLbl=new JLabel(LableNames[3]);
		VerticalSLbl=new JLabel(LableNames[4]);
		frqLbl=new JLabel(LableNames[5]);


		int count = 0;
		for(Swimmable s : RefMyAquaPanel.AllAnimalHashSet){

			for(Color t: ColorId){
				if(s.getColorID() == t)
					count++;
			}

			if(count == 0)//to add not default color (after decorator)
			{
				addnewcolor(s.getColorID());
			}
			count = 0;

		}


		kindCmb = new JComboBox<String>(FishsNames);//make a comboBox with all the names

		this.add(KindLbl);
		add(kindCmb);

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


		this.add(frqLbl);
		FeqSLD= new JSlider(JSlider.HORIZONTAL,1,20,5);
		FeqSLD.setMajorTickSpacing(2);
		FeqSLD.setMinorTickSpacing(1);
		FeqSLD.setPaintLabels(true);
		FeqSLD.setPaintTicks(true);
		this.add(FeqSLD);



		SaveBtn=new JButton("Save");
		SaveBtn.addActionListener(this);
		add(SaveBtn);

		CancelBtn=new JButton("Cancel");
		CancelBtn.addActionListener(this);
		add(CancelBtn);
		this.setVisible(true);	

	}
	/**
	 * Add new color to the combobox 
	 * @return void
	 */
	public void addnewcolor(Color newcol)
	{
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
		else if(e.getSource() == SaveBtn)//save the new animal if there is place in the AllAnimalHashSet
		{
			if(RefMyAquaPanel.AllAnimalHashSet.size()<AquaPanel.MAX_ANIMALS)//checking if there is place for more animals
			{	

				AnimalFactory rc = new AnimalFactory(RefMyAquaPanel,this);
				swim = (Swimmable)rc.produceSeaCreature(kindCmb.getSelectedItem().toString());
				if(swim!=null)//if was created
				{
					this.setVisible(false);	//close save form		
					RefMyAquaPanel.AllAnimalHashSet.add(swim);
					swim.start();//start the new tread
					RefMyAquaPanel.repaint();//paint it
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
	 * get Color Name in RGB Format  "(R,G,B)"
	 * @return String
	 */
	public String getColorName(Color col) {
		return "("+col.getRed()+","+col.getGreen()+","+col.getBlue()+")";
	}





}
