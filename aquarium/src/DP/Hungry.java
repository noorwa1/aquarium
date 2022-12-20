package DP;

import hw1.Swimmable;

public class Hungry implements HungerState{

	@Override
	public void setState(Swimmable state) {

		state.setHungerState(this);
	}
	
	@Override
	public String toString(){
		return "Hungry";
	}
	
	
	

}
