package animation;

import game.Sprite;

public class GravityDrop extends SpriteWrapper
{
	protected static final double GRAVITY = .3;
	double speed;
	
	double yStart;
	double yEnd;
	double yCurrent;
	
	public GravityDrop(Sprite sprite, double yStart)
	{
		super(sprite);
		speed = 0;
		this.yEnd = getMainSprite().getY();
		this.yStart = yStart;
		yCurrent = yStart;
		setSpriteLocation(getMainSprite().getX(), yStart);
	}

	@Override
	public void tick()
	{
		caculateNewLocation();
		changeSpeed();
		setSpriteLocation();
	}
	
	public void changeSpeed()
	{
		speed += GRAVITY;
	}
	
	public void caculateNewLocation()
	{
		yCurrent += speed;
		if(yCurrent > yEnd)
		{
			yCurrent = yEnd;
			setDone(true);
		}
	}
	
	public void setSpriteLocation()
	{
		getMainSprite().setY(yCurrent);
	}
	
	public void setSpriteLocation(double x, double y)
	{
		getMainSprite().setX(x);
		getMainSprite().setY(y);
	}
}
