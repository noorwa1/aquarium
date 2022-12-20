package hw2;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.CyclicBarrier;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DP.AddPlantsDialog;
import DP.DuplicateAnimal;
import DP.Food;
import DP.Immobile;
import DP.SeaCreatureMemento;
import hw1.Swimmable;

public class AquaPanel extends JPanel implements ActionListener {

	private Food temp;
	private static final long serialVersionUID = 1L;
	public static int MAX_ANIMALS =5;
	public static int MAX_PLANTS =5;
	private String IMAGE_PATH="c:\\BACKGROUND.jpg";
	private Image img = null;
	private String[] ButtonNames = { "Add Plants","Add Animal", "Sleep", "Wake up", "Reset", "Food", "Info","Duplicate Animal","Decorator", "Exit" };
	protected JButton[] buttons;
	public HashSet<Swimmable> AllAnimalHashSet;
	public HashSet<Immobile> AllPlantsHashSet;
	protected AddAnimalDialog MyAnimalDialog;
	public Boolean isFood = false;
	public Boolean isHugry = false;
	private int clicks=0;
	private AquaFrame ReMyframe;
	private BorderLayout MyBorderLayout;
	private String[] infoLableNames = { "Animal", "Color", "size", "Hor.Speed", "Ver.Speed", "Eat Counter"};
	protected JTable infoJTable;
	private JScrollPane pane;
	private JPanel MyJPanel;
	protected AddAnimalDialog myAddAnimalDialog;
	protected Swimmable[] swimmeArr;
	public List<SeaCreatureMemento> mementoList;


	/**
	 * CTOR
	 * @param Myframe
	 */
	public AquaPanel(AquaFrame Myframe)
	{
		mementoList = new ArrayList<SeaCreatureMemento>();
		ReMyframe=Myframe;
		AllAnimalHashSet = new HashSet<Swimmable>();//init the HashSet
		AllPlantsHashSet = new HashSet<Immobile>();//init the HashSet
		MyBorderLayout=new BorderLayout();
		this.setLayout(MyBorderLayout);//Make aquaPanel MyBorderLayout


		DefaultTableModel model = new DefaultTableModel(infoLableNames, 0);//set coloms JLables and zero rows -added after
		infoJTable = new JTable(model); //insert the model to the JTable
		pane = new JScrollPane(infoJTable);//make a JScrollPane from all the info table
		ReMyframe.add(pane,BorderLayout.CENTER);//add the panel to the main Frame

		MyJPanel=new JPanel();
		MyJPanel.setLayout(new FlowLayout());//for the buttons

		buttons = new JButton[ButtonNames.length];//make all the buttons


		for (int i = 0; i < ButtonNames.length; i++)
		{//add to every button name,size,background and addActionListener,at lest at it to MyPanel
			this.buttons[i] = new JButton(ButtonNames[i]);
			buttons[i].setPreferredSize(new Dimension(90, 20));//size of buttons
			buttons[i].setBackground(new Color(192, 192, 192));//Grey color
			buttons[i].addActionListener(this);
			buttons[i].setFont(new Font("Arial",Font.PLAIN,11));
			this.MyJPanel.add(buttons[i]);
		}

		buttons[6].addMouseListener( new MouseAdapter() {public void mousePressed(MouseEvent e){ clicks++;}});//make info button MouseListener to clicks 
		buttons[7].setPreferredSize(new Dimension(115, 20));


		ReMyframe.add(MyJPanel,BorderLayout.SOUTH);//add the panel to the main Frame
	}

	/**
	 * add to the memento list the new State
	 * @param SeaCreatureMemento state 
	 */
	public void addtomemento(SeaCreatureMemento state){
		mementoList.add(state);
	}

	/**
	 * return from the memnto list the state in index
	 * @param int index
	 * @return SeaCreatureMemento
	 */
	public SeaCreatureMemento get(int index){
		return mementoList.get(index);
	}

	/** 
	 * make a Callback to the other animals that the worm was eated!
	 * @param Swimmable
	 * @return {@link Void}
	 */
	public void callBack(Swimmable animal)
	{
		this.isFood=false;
		animal.eatInc();
		for(Swimmable s:AllAnimalHashSet)//Remove the Cycle Barrier
		{
			s.setBarrier(null);
		}

	}

	/** 
	 *  make a background WHITE/ BLUE/User background
	 * @param integer 
	 * @return {@link Void}
	 */
	void setBackgr(int num)// make a background
	{
		if (num == 0) {
			this.img = null;
			this.setBackground(Color.WHITE);
		} else if (num == 1) {
			this.img = null;
			this.setBackground(Color.BLUE);
		} else if (num == 2) {// BACKGROUND_PATH

			try { 
				img = ImageIO.read(new File(IMAGE_PATH));

				//				  img = img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
				this.repaint();
			} 
			catch (IOException e) { System.out.println("Cannot load image"); }


		}
	}

	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if(img!=null) 
		{ 

			//	    	 g.drawImage(img, 0, 0, ReMyframe.getWidth(), ReMyframe.getHeight(), this.ReMyframe);

			g.drawImage(img, 0, 0, this.getWidth(),  this.getHeight(), this);
			//	    	 this.MyJPanel.setBackground(new Color(192, 192, 192));
		}
		for (Immobile s : AllPlantsHashSet)//draw Animal  SET
		{
			s.drawCreature(g);
		}
		for (Swimmable s : AllAnimalHashSet)//draw Animal  SET
		{
			s.drawAnimal(g);
		}

		if(this.isFood==true)//make a worm
		{
			temp = Food.getInstance();
			temp.set(this, g);
		}


		if (getishungry() == true)//show dialog the animal is hungry
		{

			EventQueue.invokeLater(new Runnable(){
				@Override
				public void run() {
					JOptionPane op = new JOptionPane("Some animale are hungry... Give him Food!",JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = op.createDialog("Break");
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);      
					dialog.setVisible(true);
					setishungry(false);
				}
			});

			this.isHugry = false;
		}
	}

	/**
	 * delete state from the memento list
	 * @param int id
	 */
	public void deletefrommemnto(int id){
		mementoList.remove(id);
	}

	/**
	 * set if have an animal thet hungry in the aq
	 * @param bool
	 */
	public void setishungry(boolean bool){this.isHugry = bool;}

	/**
	 * get if have an animal that hungry in the aq
	 * @return boolean
	 */
	public boolean getishungry(){return this.isHugry;}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[0])// "Add Plants"
		{

			if(AllPlantsHashSet.size()<MAX_PLANTS)//if have place to more Plants
			{
				AddPlantsDialog MyPlantsDialog = new AddPlantsDialog(this, "Add Plants to aquarium");
				MyPlantsDialog.setVisible(true);				
			}
			else
			{
				JOptionPane.showMessageDialog(this,"The maximum Plants is "+AquaPanel.MAX_PLANTS);
			}

		}
		else if (e.getSource() == buttons[1])// "Add Animal"
		{

			if(AllAnimalHashSet.size()<MAX_ANIMALS)//if have place to more animals
			{
				AddAnimalDialog MyAnimalDialog = new AddAnimalDialog(this, "Add Fish to aquarium");
				MyAnimalDialog.setVisible(true);				
			}
			else
			{
				JOptionPane.showMessageDialog(this,"The maximum Animals is "+AquaPanel.MAX_ANIMALS);
			}

		}
		else if (e.getSource() == buttons[2])// "Sleep"
		{
			for (Swimmable s : AllAnimalHashSet) {
				s.setSuspend();
			}

		}
		else if (e.getSource() == buttons[3])// "Wake up"
		{

			for (Swimmable s : AllAnimalHashSet)
			{
				s.setResume();
			}
		} 

		else if (e.getSource() == buttons[4])// "Reset"
		{
			this.isFood = false;
			AllPlantsHashSet.clear();
			AllAnimalHashSet.clear();
			this.repaint();//update the screen if the animal are at sleep mode
		} 

		else if (e.getSource() == buttons[5])// "Food"
		{
			boolean flag=false;
			for (Swimmable s : AllAnimalHashSet)//Checking if the animals are NOT SLEEPING
			{
				flag=s.isInterrupted();//if isInterrupted ->return true
			}

			if (flag==false)//to avoid a multi CyclicBarrier before the WORM was Eat and to give a food while some animal is SLEEP
			{
				if (AllAnimalHashSet.size() > 0)
				{
					this.isFood = true;
					int size = 0;
					for (Swimmable s : AllAnimalHashSet) {
						if(s.getHungerState().toString() == "Hungry")//take a sixe on only hungry aminals
							size++;
					}

					if(size != 0)
					{
						CyclicBarrier barrier = new CyclicBarrier(size);
						for (Swimmable s : AllAnimalHashSet)
						{
							if(s.getHungerState().toString() == "Hungry")
								s.setBarrier(barrier);
						}
					}
				}
			}

		}

		else if (e.getSource() == buttons[6])// "Info"***
		{
			int TotalEat=0; 
			if(clicks%2==1)//show table
			{
				DefaultTableModel temp = (DefaultTableModel) infoJTable.getModel();
				temp.setRowCount(0);//remove old info

				String[] Mystring=new String[infoLableNames.length];//{ "Animal", "Color", "size", "Hor.Speed", "Ver.Speed", "Eat Counter"};

				for (Swimmable s : AllAnimalHashSet)//fill all the rows with good values
				{
					Mystring[0]=s.getAnimalName();
					Mystring[1]=s.getColor();
					Mystring[2]=Integer.toString(s.getSize());
					Mystring[3]=Integer.toString(s.getHorSpeed());
					Mystring[4]=Integer.toString(s.getVerSpeed());
					Mystring[5]=Integer.toString(s.getEatCount());
					TotalEat+=s.getEatCount();
					temp.addRow(Mystring);
				}
				String[] TotalRow={"Total","","","","",String.valueOf(TotalEat)};
				temp.addRow(TotalRow);
				//remove this aquapanel(because its in the same place) and show the info table.
				this.setVisible(false);//the aquaPanel
				infoJTable.setVisible(true);//the table
				this.ReMyframe.remove(this);
				this.ReMyframe.add(pane,BorderLayout.CENTER);//add the info table to the main frame

			}

			if(clicks%2==0)//remove table
			{
				//remove the info table(because its in the same place) and show aquapanel agin
				this.ReMyframe.remove(this.pane);//remove the info table(pane) to the main frame
				this.ReMyframe.add(this,BorderLayout.CENTER);//add the AquaPanel again
				this.setVisible(true);//the aquaPanel
			}

		}
		else if(e.getSource() == buttons[7])//duplicate
		{
			// AddAnimalDialog(frame,this,"Add an animal to aquarium");
			if(AllAnimalHashSet.size() == 0){
				JOptionPane.showMessageDialog(this,"you dont have any aminal to duplicate in the aquarium");
			}
			else if(AllAnimalHashSet.size()<MAX_ANIMALS)//if have place to more animals
			{
				DuplicateAnimal duplicate = new DuplicateAnimal(this, "Duplicate Fish From aquarium",AllAnimalHashSet);
				duplicate.setVisible(true);				
			}
			else
			{
				JOptionPane.showMessageDialog(this,"The maximum Animals is "+AquaPanel.MAX_ANIMALS);
			}
		}

		else if (e.getSource() == buttons[8])//Decorator
		{
			if(AllAnimalHashSet.size() == 0)
			{
				JOptionPane.showMessageDialog(this,"you dont have any aminal to modify");
			}
			else
			{
				JPanelDecorato Jpanel = new JPanelDecorato(this, "Change Color to animal",AllAnimalHashSet);
				Jpanel.setVisible(true);				
			}

		}


		else if (e.getSource() == buttons[9])// "Exit"
			System.exit(0);
	}

}
