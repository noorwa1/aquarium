package DP;

public class SeaCreatureMemento {
	private SeaCreature state;
	
	public SeaCreatureMemento(SeaCreature state){
		this.state = state;
	}
	
	public SeaCreature getSeaCreature(){
		return this.state;
	}
}
