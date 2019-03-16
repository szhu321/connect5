package animation;

import game.Sprite;

public class FloatDrop extends GravityDrop
{	
	private boolean passed;
	
	public FloatDrop(Sprite sprite, double yStart)
	{
		super(sprite, yStart);
		passed = false;
	}
	
	public void caculateNewLocation()
	{
		if(!passed)
		{
			yCurrent += speed;
			if(yCurrent > yEnd)
			{
				yCurrent += 5;
				passed = true;
			}
		}
		else
		{
			yCurrent -= (yCurrent - yEnd) * .2;
			if(yCurrent < (yEnd + 1))
			{
				yCurrent = yEnd;
				setDone(true);
			}
		}
	}
}
