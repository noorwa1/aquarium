package DP;
import java.awt.Color;

import hw1.Swimmable;

public class MarineAnimalDecorator implements MarineAnimal
{
	protected  Swimmable theSwimm = null;
	
	public MarineAnimalDecorator(Swimmable theSwimm){
		this.theSwimm = theSwimm;
	}
	
	@Override
	public void PaintFish(Color newcol) {
		theSwimm.PaintFish(newcol);
		
	}

}
