package DP;

public class Originator 
{
	private SeaCreature state;
	
	public void setState(SeaCreature state){
	      this.state = state;
	   }
	 public SeaCreature getState(){
	      return state;
	   }
	 public SeaCreatureMemento SaveToSeaCreatureMemento(){
	      return new SeaCreatureMemento(state);
	   }
	 public void getStateFromMemento(SeaCreatureMemento Memento){
	      state = Memento.getSeaCreature();
	   }
	 
}
