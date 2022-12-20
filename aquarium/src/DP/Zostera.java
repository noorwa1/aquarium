package DP;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import hw2.AquaPanel;

public class Zostera extends Immobile {

	private int size=0,x=0,y=0;
	private Color col = Color.green;
	private AquaPanel myAquaPanel;
	protected int id;
	public Zostera(int size,AquaPanel aquaPanel)
	{
		this.name = "Zostera";
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
		Graphics2D g2 =(Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setColor(col);
		g.drawLine(x, y, x, y-size);
		g.drawLine(x-2,y,x-10,y-size*9/10);
		g.drawLine(x-2,y,x+10,y-size*9/10);
		g.drawLine(x-4,y,x-20,y-size*4/5);
		g.drawLine(x-4,y,x+20,y-size*4/5);
		g.drawLine(x-6,y,x-30,y-size*7/10);
		g.drawLine(x-6,y,x+30,y-size*7/10);
		g.drawLine(x-8,y,x-40,y-size*4/7);
		g.drawLine(x-8,y,x+40,y-size*4/7);
		g2.setStroke(new BasicStroke(1));
		
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public String getColor() {
		return "Green";
	}

	@Override
	public Immobile Clone(boolean SameId) {
		Zostera temp = new Zostera(size,myAquaPanel);
		temp.setx(this.x);
		temp.sety(this.y);
		if(SameId)
		{
			temp.id = this.id;			
		}
		
		return temp;
	}

	@Override
	public void setx(int x) {
		this.x = x;
	}



	@Override
	public void sety(int y) {
		this.y = y;		
	}

	@Override
	public void setsize(int size) {
		this.size = size;
		
	}

	@Override
	public void setid(int id) {
		this.id = id;
		
	}

	@Override
	public int getmyid() {
		return this.id;
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
