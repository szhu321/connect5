package animation;

import game.Sprite;

public class FloatDrop extends GravityDrop
{	
	public static final int LOW_MOMENTUM = 1;
	public static final int MEDIUM_MOMENTUM = 2;
	public static final int HIGH_MOMENTUM = 3;
	public static final int EXTREME_MOMENTUM = 4;
	
	private int momentum;
	private double riseRatio;
	private boolean passed;
	
	public FloatDrop(Sprite sprite, double yStart, int momentum)
	{
		super(sprite, yStart);
		this.momentum = momentum;
		passed = false;
		
		switch(momentum)
		{
		case LOW_MOMENTUM: riseRatio = 0.2; break;
		case MEDIUM_MOMENTUM: riseRatio = 0.1; break;
		case HIGH_MOMENTUM: riseRatio = 0.05; break;
		case EXTREME_MOMENTUM: riseRatio = 0.03; break;
		default: riseRatio = 0.2;
		}
	}
	
	public void caculateNewLocation()
	{
		if(!passed)
		{
			yCurrent += speed;
			if(yCurrent > yEnd)
			{
				yCurrent += (speed / 2) + momentum; //dip amount
				passed = true;
			}
		}
		else
		{
			yCurrent -= (yCurrent - yEnd) * riseRatio; //tweening algorithm
			if(yCurrent < (yEnd + 1))
			{
				yCurrent = yEnd;
				setDone(true);
			}
		}
	}
}
