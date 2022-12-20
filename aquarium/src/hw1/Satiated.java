package hw1;

import DP.HungerState;

public class Satiated implements HungerState
{
	public void setState(Swimmable state) {
		state.setHungerState(this);	
	}
	
	@Override
	public String toString(){
		return "Satiated";
	}
	
}
