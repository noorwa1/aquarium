package hw2;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import DP.MarineAnimalDecorator;
import hw1.Fish;
import hw1.Jellyfish;
import hw1.Swimmable;

public class JPanelDecorato extends JDialog implements ActionListener 
{
	protected static final long serialVersionUID = 1L;
	protected Integer[] Speeds={1,2,3,4,5,6,7,8,9,10};//speed allowed to the animals
	protected String[] ColorNames = {"Red","Blue","Green","Orange","Cyan","Pink","Black"};
	protected Color[] ColorId={Color.red,Color.blue,Color.green,Color.orange,Color.cyan,Color.pink,Color.black};
	protected String[] FishsNames;// = {"Fish","JellyFish"};
	protected JComboBox<String> AnimalCmb;
	protected JSlider sizeSld;
	protected JComboBox<Integer> HorizontalCmb,VerticalCmb; 
	protected String[] LableNames = {"Select Animal","Color","Size","Speed: Horizontal", "                Vertical"};
	protected JLabel KindLbl,ColorLbl,SizeLbl,HorizontalSLbl, VerticalSLbl;
	protected JButton SaveBtn,CancelBtn,ColorBtn;
	protected Fish MyFish;
	protected Jellyfish MyJellyFish;
	protected AquaPanel RefMyAquaPanel;
	protected Swimmable swim;
	protected HashSet<Swimmable> RefAllAnimalHashSet;
	protected Swimmable[] swimmeArr;
	protected JColorChooser colorchangeset;
	protected int pastcolor;
	protected Color newcol;
	protected String tempStr;
	

	 public   JPanelDecorato(AquaPanel MyAquaPanel,String MyTitle,HashSet<Swimmable> RefAllAnimalHashSet)
	{
		 	this.RefAllAnimalHashSet = RefAllAnimalHashSet;
		 	this.setLayout(new GridLayout(12,1));
			this.setSize(400,460);
			this.setLocation(MyAquaPanel.getWidth()/2-200,MyAquaPanel.getHeight()/2-150);//put on the center

			RefMyAquaPanel=MyAquaPanel;//get reference to MyAquaPanel
			KindLbl=new JLabel(LableNames[0]);//set Labels to all the fields
			//ColorLbl=new JLabel(LableNames[1]);
			SizeLbl=new JLabel(LableNames[2]);
			HorizontalSLbl=new JLabel(LableNames[3]);
			VerticalSLbl=new JLabel(LableNames[4]);
			
			AnimalCmb = new JComboBox<String>();//make a comboBox with all the names
			int i = 0;
			swimmeArr = new Swimmable[RefAllAnimalHashSet.size()];
			
			java.util.Iterator<Swimmable> itr = RefAllAnimalHashSet.iterator();
			
			while(itr.hasNext()) {
				swimmeArr[i] = itr.next();
				AnimalCmb.addItem(swimmeArr[i].getAnimalName()+"["+ (i+1)+"]");
		         i++;
		      }


			this.add(KindLbl);
			add(AnimalCmb);
			AnimalCmb.addActionListener(this);
			
			//the past color
			int index = 0;
			int saveindex = 0;
			for(int j =0; j<this.ColorNames.length;j++){
				if(this.ColorNames[j] == swimmeArr[index].getColor()){
					saveindex = j;
					tempStr = this.ColorNames[j];
					break;
				}
			}
			this.pastcolor = saveindex;
			
			ColorLbl=new JLabel(LableNames[1] +" : "+ swimmeArr[0].getColor());
			
			this.add(ColorLbl);
			ColorBtn=new JButton("Change Color");
			ColorBtn.addActionListener(this);
			add(ColorBtn);

			this.add(SizeLbl);
			sizeSld= new JSlider(JSlider.HORIZONTAL,20,320,120);
			sizeSld.setMajorTickSpacing(20);
			sizeSld.setMinorTickSpacing(10);
			sizeSld.setPaintLabels(true);
			sizeSld.setPaintTicks(true);
			sizeSld.setEnabled(false);
			this.add(sizeSld);
			
			HorizontalCmb=new JComboBox<Integer>(Speeds);
			HorizontalCmb.setSelectedIndex(4);
			HorizontalCmb.setEnabled(false);
			this.add(HorizontalSLbl);
			this.add(HorizontalCmb);
			
			VerticalCmb=new JComboBox<Integer>(Speeds);
			VerticalCmb.setSelectedIndex(4);
			VerticalCmb.setEnabled(false);
			this.add(VerticalSLbl);
			this.add(VerticalCmb);

			SaveBtn=new JButton("Save");
			SaveBtn.addActionListener(this);
			add(SaveBtn);
		
			CancelBtn=new JButton("Cancel");
			CancelBtn.addActionListener(this);
			add(CancelBtn);
			this.setVisible(true);	
			
			this.sizeSld.setValue(this.swimmeArr[index].getSize());
			this.HorizontalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
			this.VerticalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource() == AnimalCmb)
		{
			int index = (int)AnimalCmb.getSelectedIndex();
			this.sizeSld.setValue(this.swimmeArr[index].getSize());
			this.HorizontalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
			this.VerticalCmb.setSelectedIndex(swimmeArr[index].getHorSpeed()-1);
			int saveindex = 0;
			for(int i =0; i<this.ColorNames.length;i++){
				if(this.ColorNames[i] == swimmeArr[index].getColor()){
					saveindex = i;
					break;
				}
			}
			this.pastcolor = saveindex;
			//this.colorCmb.setSelectedIndex(saveindex);
			ColorLbl.setText("Color : " + swimmeArr[index].getColor());
			
			
		}
		else if(e.getSource() == CancelBtn)
		{
			this.setVisible(false);//hide			
		}
		else if(e.getSource() == ColorBtn)
		{
			newcol = JColorChooser.showDialog(this, "select new color",swimmeArr[(int)AnimalCmb.getSelectedIndex()].getColorID());
			if(newcol != null)
				ColorLbl.setText("Color : " + getColorName(newcol));
		}
		
		
		else if(e.getSource() == SaveBtn)//save the new animal if there is place in the AllAnimalHashSet
		{
			int i = 0;
			int index = (int)AnimalCmb.getSelectedIndex();
			for (Swimmable s : RefAllAnimalHashSet)
			{
				if(i == index){
					MarineAnimalDecorator fish = new MarineAnimalDecorator(s);
					fish.PaintFish(newcol);
					
				}
				i++;
			}
			this.setVisible(false);
			
			
			}//if (RefMyAquaPanel.AllAnimalHashSet.size()<MAX_ANIMALS)
			else
			{
				JOptionPane.showMessageDialog(this,"The maximum Animals is "+AquaPanel.MAX_ANIMALS);
//				System.out.println("The maximum Animals is "+RefMyAquaPanel.MAX_ANIMALS);
			}
		}

	
	public String getColorName(Color c) {
		   return "("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
		}

	
	 
	 
	

}
