package hw1;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import DP.Hungry;
import DP.MarineAnimal;
import hw2.AddAnimalDialog;
import hw2.AquaPanel;

import java.util.Random;

public class Fish extends Swimmable implements MarineAnimal
{
	private Color col;
	private int size;
	private int y_front, x_front, x_dir;
	private AquaPanel aquaPanel;
	protected int numOfEats = 0;
	private String ColorName;
	private Boolean isSuspended = false;
	double  v,k,v_hor_new,v_ver_new;
	private CyclicBarrier Barrier;
	int upDown=1;
	protected AddAnimalDialog animaldialog;
	protected int EatFeq;
	protected int ShiftCount;
	protected int id;


	public Fish(AquaPanel aquaPanel, Color col, Integer size, int horSpeed, int verSpeed,String ColorName,AddAnimalDialog animaldialog,int eatprq) {
		super(horSpeed, verSpeed,animaldialog,aquaPanel);
		this.animaldialog = animaldialog;
		this.id = Swimmable.id;
		Swimmable.id++;
		this.aquaPanel = aquaPanel;
		this.col = col;
		this.size = size;
		this.ShiftCount = 0;
		this.EatFeq = eatprq;
		Random rand = new Random();
		this.x_front = rand.nextInt(aquaPanel.getWidth()-100);
		this.y_front = rand.nextInt(aquaPanel.getHeight()-100);
		this.x_dir = rand.nextInt(2);
		//Design patterns - State
		Satiated t = new Satiated();
		t.setState(this);

		if (this.x_dir==0)
		{
			this.x_dir=-1;
		}
		this.ColorName=ColorName;
	}

	@Override
	public String getAnimalName() 
	{
		return this.getClass().getSimpleName();
	}

	@Override
	public synchronized void setSuspend() 
	{
		this.isSuspended = true;
	}

	@Override
	public synchronized void setResume() {
		this.isSuspended = false;
		this.notify();			
	}

	@Override
	public void setBarrier(CyclicBarrier b)
	{
		this.Barrier=b;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public void eatInc() {
		this.numOfEats++;
	}

	public int getmyid(){
		return this.id;
	}

	@Override
	public int getEatCount()
	{
		return this.numOfEats;
	}

	@Override
	public String getColor() {

		return ColorName;
	}

	@Override
	public void drawAnimal(Graphics g) {
		g.setColor(col);
		if (x_dir == 1) // fish swims to right side
		{
			// Body of fish
			g.fillOval(x_front - size, y_front - size / 4, size, size / 2);
			// Tail of fish
			int[] x_t = { x_front - size - size / 4, x_front - size - size / 4, x_front - size };
			int[] y_t = { y_front - size / 4, y_front + size / 4, y_front };
			Polygon t = new Polygon(x_t, y_t, 3);
			g.fillPolygon(t);
			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
			g2.fillOval(x_front - size / 5, y_front - size / 10, size / 10, size / 10);
			// Mouth of fish
			if (size > 70)
				g2.setStroke(new BasicStroke(3));
			else if (size > 30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
			g2.drawLine(x_front, y_front, x_front - size / 10, y_front + size / 10);
			g2.setStroke(new BasicStroke(1));
		} 
		else // fish swims to left side
		{
			// Body of fish
			g.fillOval(x_front, y_front - size / 4, size, size / 2);
			// Tail of fish
			int[] x_t = { x_front + size + size / 4, x_front + size + size / 4, x_front + size };
			int[] y_t = { y_front - size / 4, y_front + size / 4, y_front };
			Polygon t = new Polygon(x_t, y_t, 3);
			g.fillPolygon(t);
			// Eye of fish
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue()));
			g2.fillOval(x_front + size / 10, y_front - size / 10, size / 10, size / 10);
			// Mouth of fish
			if (size > 70)
				g2.setStroke(new BasicStroke(3));
			else if (size > 30)
				g2.setStroke(new BasicStroke(2));
			else
				g2.setStroke(new BasicStroke(1));
			g2.drawLine(x_front, y_front, x_front + size / 10, y_front + size / 10);
			g2.setStroke(new BasicStroke(1));
		}
	}

	@Override
	public void run() 
	{
		while (true)
		{
			try //sleep
			{
				Thread.sleep(40);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (isSuspended == true)// if the fish is Suspend
			{
				try {
					synchronized (this)
					{
						wait();
					}
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

			}//end of (isSuspended == true)

			if (isSuspended == false)// if the fish not Suspended
			{
				if(this.aquaPanel.isFood==true && this.getHungerState().toString() == "Hungry")//there is  food and if the fish is hungry
				{
					try {	
						if(Barrier!=null)//for new fish that not in the Barrier set
						{
							Barrier.await();							
						}

						v=Math.sqrt((horSpeed*horSpeed+this.verSpeed*verSpeed));
						k=Math.abs((double)(y_front-aquaPanel.getHeight()/2)/(x_front-aquaPanel.getWidth()/2));
						v_hor_new=v/Math.sqrt(k*k+1);
						v_ver_new=k*v_hor_new;


						if (x_front > aquaPanel.getWidth()/2){
							x_dir = -1;
						}
						else{
							x_dir = 1;
						}
						if (y_front > aquaPanel.getHeight()/2){
							upDown = -1;
						}
						else{
							upDown = 1;
						}

						x_front += v_hor_new * x_dir;
						y_front += v_ver_new * upDown;


						synchronized (this.aquaPanel)
						{
							if ((Math.abs(x_front - aquaPanel.getWidth() / 2) <= 5)&& (Math.abs(y_front - aquaPanel.getHeight() / 2) <= 5)) 
							{
								if(this.aquaPanel.isFood == true)//the callback
								{
									this.aquaPanel.callBack(this);
									ShiftCount = 0;
									//Design patterns - State
									Satiated nnn = new Satiated();
									nnn.setState(this);
								}//end if(this.aquaPanel.isFood == true)
								// fish eats worm ...
							}//end the big if
						}//end synchronized
					}//end of try
					//catch Barrier await
					catch (NullPointerException e)//if animal was created in time that food is out  
					{
						this.aquaPanel.isFood = false;
						e.printStackTrace();

					} catch (InterruptedException e) {
						
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						
						e.printStackTrace();
					}

				}//end of if (this.isBarrier==true)

				//if(this.isBarrier==false)
				else//there is NO food -Move regular
				{
					// aquaPanel.getHeight();
					if (x_front > aquaPanel.getWidth()){
						x_dir = -1;
						ShiftCount++;
					}
					if (x_front <= 0)
					{
						x_dir = 1;
						ShiftCount++;
					}
					if (y_front > aquaPanel.getHeight()){
						upDown = -1;
						ShiftCount++;
					}
					if (y_front <= 0){
						upDown = 1;
						ShiftCount++;
					}

					if(ShiftCount == EatFeq){
						if(this.getHungerState().toString() == "Satiated"){
							this.aquaPanel.isHugry = true;
							
							//Design patterns - State
							Hungry hung =new Hungry();
							hung.setState(this);
						}// end if(this.getHungerState().toString() == "Satiated")
					}//end if(ShiftCount == EatFeq)
					x_front += this.getHorSpeed() * x_dir;
					y_front += this.getVerSpeed() * upDown;
				}
			}//end of (isSuspended == false)
			this.aquaPanel.repaint();
		}//end of while true
	}//end of run function()

	@Override
	public void drawCreature(Graphics g) {
		// TODO Auto-generated method stub
	}

	public void setsize(int size){this.size = size;}

	public void setcolorCol(Color col){
		this.col = col;
	}
	
	public void setcolor(String col){
		this.ColorName = col;
	}

	@Override
	public Fish Clone(boolean SameId) {
		Fish temp = new Fish(aquaPanel, col, size, horSpeed, verSpeed,ColorName,animaldialog,EatFeq);
		temp.setX(this.x_front);
		temp.setY(this.y_front);
		temp.setSpeed(this.horSpeed, this.verSpeed);
		temp.setEatCount(this.numOfEats);// this for the table in "save fish to Memento"
		if (SameId)
		{
			temp.id = this.id;			
		}
		return temp;
	}

	@Override
	public void PaintFish(Color newcol) {
		this.col = newcol;
		this.ColorName = getColorName(newcol);


	}

	@Override
	public Color getColorID() {
		return this.col;
	}

	@Override
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void setX(int x) {
		this.x_front = x;
	}

	@Override
	public void setY(int y) {
		this.y_front = y;
	}

	@Override
	public int getX() {
		return this.x_front;
	}

	@Override
	public int getY() {
		return this.y_front;
	}

	@Override
	public void setEatCount(int count) {
		this.numOfEats = count;

	}
}


