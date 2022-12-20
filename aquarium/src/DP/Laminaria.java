package DP;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import hw2.AquaPanel;

public class Laminaria extends Immobile {

	private int size=0,x=0,y=0;
	private Color col = Color.green;
	private AquaPanel myAquaPanel;
	protected int id;
	public Laminaria(int size,AquaPanel aquaPanel)
	{
		this.name = "Laminaria";
		this.id = Immobile.id;
		Immobile.id++;
		this.myAquaPanel = aquaPanel;
		Random rand = new Random(); 
		this.size = size;
		this.x = rand.nextInt(aquaPanel.getWidth()-100);
		this.y = rand.nextInt(aquaPanel.getHeight()-100);
	}
	
	@Override
	public void drawCreature(Graphics g) {
		g.setColor(col);
		g.fillArc(x-size/20, y-size, size/10, size*4/5, 0, 360);
		g.fillArc(x-size*3/20, y-size*13/15, size/10, size*2/3, 0, 360);
		g.fillArc(x+size/20,y-size*13/15,size/10,size*2/3,0,360);
		g.drawLine(x, y, x, y-size/5);
		g.drawLine(x, y, x-size/10, y-size/5);
		g.drawLine(x, y, x+size/10, y-size/5);
	}

	@Override
	public String getColor() 
	{
		return "Green";
	}

	@Override
	public int getSize() 
	{
		return this.size;
	}
	
	@Override
	public void setx(int x)
	{
		this.x = x;
	}

	@Override
	public void sety(int y) 
	{
		this.y = y;		
	}

	@Override
	public void setsize(int size)
	{
		this.size = size;	
	}

	@Override
	public void setid(int id) 
	{
		this.id = id;	
	}



	@Override
	public int getmyid() 
	{
		return this.id;
	}



	@Override
	public Object Clone(boolean SameId) {
		Laminaria temp = new Laminaria(size,myAquaPanel);
		temp.setx(this.x);
		temp.sety(this.y);
		if(SameId)
		{
			temp.id = this.id;			
		}
		
		return temp;
	}



	@Override
	public int getx() {
		return this.x;
	}

	@Override
	public int gety() {
		return this.y;
	}
	
	
}
