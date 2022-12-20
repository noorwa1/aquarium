package DP;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import hw2.AquaPanel;

public class Food {
	private static Food instance = null;
	/* A private Constructor prevents any other
	 * class from instantiating.*/
	
	private Food(){ }
	

	public static Food getInstance(){
		if(instance == null){
			instance = new Food();
		}
		return instance;
	}
	
	public void set(AquaPanel temp,Graphics g)
	{
		  Graphics2D g2 = (Graphics2D) g;
	      g2.setStroke(new BasicStroke(3));
	      g2.setColor(Color.red);
	      g2.drawArc(temp.getWidth()/2, temp.getHeight()/2-5, 10, 10, 30, 210);	   
	      g2.drawArc(temp.getWidth()/2, temp.getHeight()/2+5, 10, 10, 180, 270);	g2.setStroke(new BasicStroke(1));
	}
	
}
