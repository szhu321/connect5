package animation;

import game.Sprite;

public class Spin extends SpriteWrapper
{
	private double angularSpeed;
	private double angularDisplacement;
	
	//time in nanoseconds;
	private long timePassed;
	private long endTime;
	private long previousTick;

	public Spin(Sprite sprite, long timeActiveMilliSeconds)
	{
		super(sprite);
		endTime = timeActiveMilliSeconds * 1000000;
		//System.out.println("Endtime" + endTime);
		timePassed = 0;
		angularSpeed = .3;
		previousTick = System.nanoTime();
	}

	@Override
	public void tick()
	{
		updateTime();
		caculateNewLocation();
		changeSpeed();
		setSpriteLocation();
	}

	private void updateTime()
	{
		long currentTime = System.nanoTime();
		timePassed += currentTime - previousTick;
		previousTick = currentTime;
	}

	private void setSpriteLocation()
	{
		getMainSprite().setFaceAngle(angularDisplacement * (360.0 / (2.0 * Math.PI)));
	}

	private void changeSpeed()
	{
		//
	}

	private void caculateNewLocation()
	{
//		System.out.println("Tick angle");
//		System.out.println(timePassed);
//		System.out.println(endTime);
		angularDisplacement += angularSpeed;
		if(timePassed > endTime)
		{
			setDone(true);
			angularDisplacement = 0;
		}
	}

}
