package DP;

import hw1.Swimmable;
import hw2.AddAnimalDialog;
import hw2.AquaPanel;
import hw1.Fish;
import hw1.Jellyfish;
public class AnimalFactory extends Thread implements AbstractSeaFactory {

	private AquaPanel RefMyAquaPanel;
	private AddAnimalDialog myd;
	public AnimalFactory(AquaPanel mypanle,AddAnimalDialog myd)
	{
		this.RefMyAquaPanel = mypanle;
		this.myd = myd;
	}


	@Override
	public Swimmable produceSeaCreature(String Type) {


		if(Type.equalsIgnoreCase("Fish"))
		{
			return new Fish(RefMyAquaPanel,myd.ColorId[myd.colorCmb.getSelectedIndex()],myd.sizeSld.getValue(),Integer.parseInt(myd.HorizontalCmb.getSelectedItem().toString()),Integer.parseInt(myd.VerticalCmb.getSelectedItem().toString()),myd.ColorNames[myd.colorCmb.getSelectedIndex()],myd,myd.FeqSLD.getValue());
		}


		if(Type.equalsIgnoreCase("Jellyfish"))
		{
			return new Jellyfish(RefMyAquaPanel,myd.ColorId[myd.colorCmb.getSelectedIndex()],myd.sizeSld.getValue(),Integer.parseInt(myd.HorizontalCmb.getSelectedItem().toString()),Integer.parseInt(myd.VerticalCmb.getSelectedItem().toString()),myd.ColorNames[myd.colorCmb.getSelectedIndex()],myd,myd.FeqSLD.getValue()) ;

		}
		else
			return null;

	}



}
