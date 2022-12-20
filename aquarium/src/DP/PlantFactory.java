package DP;

import hw2.AquaPanel;

public class PlantFactory implements AbstractSeaFactory{

	private AquaPanel RefMyAquaPanel;
	private AddPlantsDialog myd;

	public PlantFactory(AquaPanel mypanle,AddPlantsDialog myd)
	{
		this.RefMyAquaPanel = mypanle;
		this.myd = myd;
	}


	@Override
	public SeaCreature produceSeaCreature(String Type) {


		if(Type.equalsIgnoreCase("Zostera"))
		{
			return new Zostera(myd.sizeSld.getValue(),RefMyAquaPanel);

		}

		if(Type.equalsIgnoreCase("Laminaria"))
		{
			return new Laminaria(myd.sizeSld.getValue(),RefMyAquaPanel);

		}
		else
			return null;

	}

}
