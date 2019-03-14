package animation;

import game.Sprite;

public class GravityDrop extends SpriteWrapper
{
	protected static final double GRAVITY = 10;
	private double speed;
	
	private double xStart;
	private double yStart;
	
	private double yEnd;
	private double xEnd;
	
	private double xCurrent;
	private double yCurrent;
	
	public GravityDrop(Sprite sprite, double xStart, double yStart)
	{
		super(sprite);
		speed = 100;
		this.xEnd = getMainSprite().getX();
		this.yEnd = getMainSprite().getY();
		this.xStart = xStart;
		this.yStart = yStart;
		xCurrent = xStart;
		yCurrent = xStart;
	}

	@Override
	public void run()
	{
		
		
		setSpriteLocation();
	}
	
	public void setSpriteLocation()
	{
		getMainSprite().setX(xCurrent);
		getMainSprite().setY(yCurrent);
	}
}
