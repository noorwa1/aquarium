package hw1;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.CyclicBarrier;

import DP.AnimalFactory;
import DP.HungerState;
import DP.MarineAnimal;
import DP.SeaCreature;
import hw2.AddAnimalDialog;
import hw2.AquaPanel;


public abstract class Swimmable extends AnimalFactory implements SeaCreature,Cloneable,MarineAnimal{

	protected int horSpeed;
	protected int verSpeed;
	protected AddAnimalDialog myd;
	protected AquaPanel mypanle;
	public static int id = 0;
	protected HungerState state;


	public Swimmable(AddAnimalDialog myd,AquaPanel mypanle) {
		super(mypanle,myd);
		horSpeed = 0;
		verSpeed = 0;
		this.state = null;
	}
	public Swimmable(int hor, int ver,AddAnimalDialog myd,AquaPanel mypanle) {
		super(mypanle,myd);
		horSpeed = hor;
		verSpeed = ver;
	}

	/**
	 * get the Hor speed of the animal
	 * @return int
	 */
	public int getHorSpeed() { return horSpeed; }

	/**
	 * get the vertical speed of the animal
	 * @return int
	 */
	public int getVerSpeed() { return verSpeed; }

	/**
	 * set the Hor speed of the animal
	 * @param hor
	 */
	public void setHorSpeed(int hor) { horSpeed  = hor; }

	/**
	 * set the speed of the animal in one
	 * @param horSpeed
	 * @param verSpeed
	 */
	public void setSpeed(int horSpeed,int verSpeed){
		this.horSpeed = horSpeed;
		this.verSpeed = verSpeed;
	}

	/**
	 * Set the Eat Count of the Animal
	 * @param count
	 */
	public abstract void setEatCount(int count);

	/**
	 * Get the X position of the animal
	 * @return {@link int}
	 */
	public abstract int getX();
	
	/**
	 * Get the Y position of the animal
	 * @return {@link int}
	 */
	public abstract int getY();
	
	/**
	 * Set the Hunger State of the Animal - in the gard work 4 design patterns State
	 * @param state
	 */
	public void setHungerState(HungerState state){
		this.state = state;		

	}
	
	/**
	 * Get the Hunger State of the Animal - in the gard work 4 design patterns State
	 * @return HungerState
	 */
	public HungerState getHungerState(){
		return this.state;

	}
	

	/**
	 * its make a copy of the animal
	 * aquaPanel, col, size, horSpeed, verSpeed,ColorName,EatFeq,X_front,Y_front,id
	 * the parameter is boolean - true/false to take the same id 
	 * @return Swimmable
	 */
	public abstract Object Clone(boolean SameId);

	/**
	 * return the id of the animal
	 * @return int
	 */
	public abstract int getmyid();

	/**
	 * set the vertical speed of the animal
	 * @param ver
	 */
	public void setVerSpeed(int ver) { verSpeed  = ver; }

	/** 
	 * Get The Animal Name
	 * 
	 * @return {@link String}
	 */
	abstract public String getAnimalName();

	/** 
	 * Draw the Animal Name with the Graphics 
	 * @param Graphics
	 * @return {@link Void}
	 */
	abstract public void drawAnimal(Graphics g);

	/** 
	 * Suspend the Swimmable  Thread  
	 * @param 
	 * @return {@link Void}
	 */
	abstract public void setSuspend();

	/** 
	 * Resume the Swimmable  Thread  
	 * @param 
	 * @return {@link Void}id
	 */
	abstract public void setResume();

	/** 
	 * set a CyclicBarrier to a Swimmable  type Thread  
	 * @param 
	 * @return {@link Void}
	 */
	abstract public void setBarrier(CyclicBarrier b);

	/** 
	 * get Size of the animal
	 * @return {@link Integer}
	 */
	abstract public int getSize();

	/** 
	 * Increase the animal Eat Counter
	 * @return {@link Void}
	 */
	abstract public void eatInc();

	/** 
	 * Get the animal Eat Counter
	 * @return {@link Integer}
	 */
	abstract public int getEatCount();

	/** 
	 * Get The Animal Color
	 * 
	 * @return {@link String}
	 */
	abstract public String getColor();

	/**
	 * get the color id of the animal
	 * @return Color
	 */
	abstract public Color getColorID();

	/**
	 * Set the color id of the animal
	 * @param col
	 */
	abstract public void setcolorCol(Color col);

	/** 
	 * Paint the color of the fish or jellifish
	 * 
	 * @return {@not return is void}
	 */
	abstract public void PaintFish(Color newcol);

	/**
	 * Receive Color and return String "(R,G,B)" of the color
	 * @param c Color
	 * @return String
	 */
	public String getColorName(Color c) {
		return "("+c.getRed()+","+c.getGreen()+","+c.getBlue()+")";
	}

	/**
	 * set the size of the animal
	 * @param size
	 */
	abstract public void setSize(int size);
	
	/**
	 * set the X_front of the animal
	 * @param x
	 */
	public abstract void setX(int x);
	
	/**
	 * set the Y_front of the animal
	 * @param y
	 */
	public abstract void setY(int y);
}

