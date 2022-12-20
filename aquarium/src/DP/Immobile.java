package DP;

public abstract class Immobile implements SeaCreature {
	String name;
	public static int id = 0;
	public String getname(){
		return this.name;
	}
	public abstract int getSize();
	public abstract String getColor();
	/**
	 * its make a copy of the plants
	 * 
	 * the parameter is boolean - true/false to take the same id 
	 * @return Immobile
	 */
	public abstract Object Clone(boolean SameId);
	/**
	 * Set the X position of the plant
	 * @return {@link int}
	 */
	public abstract void setx(int x);
	/**
	 * set the Y_front of the plant
	 * @param y
	 */
	public abstract void sety(int y);
	/**
	 * set the size of the plant
	 * @param size
	 */
	public abstract void setsize(int size);
	/**
	 * set the ID of the plant
	 * @param y
	 */
	public abstract void setid(int id);
	/**
	 * Get the ID of the plant
	 * @param y
	 */
	public abstract int getmyid();
	/**
	 * Get the X position of the plant
	 * @return {@link int}
	 */
	public abstract int getx();
	/**
	 * Get the Y position of the animal
	 * @return {@link int}
	 */
	public abstract int gety();

}

