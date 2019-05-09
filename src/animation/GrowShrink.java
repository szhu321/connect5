package animation;

import game.Sprite;
import util.Timer;

public class GrowShrink extends SpriteWrapper
{
	private int phase;
	private double deltaPercent;
	private double increaseDeltaPercent;
	private double originalWidth;
	private double originalHeight;
	private double originalX;
	private double originalY;
	
	private long totalTime;
	private double percentConstant;
	
	private long phase1Time;
	private long phase2Time;
	private long phase3Time;
	
	private Timer timer;
	
	private boolean waiting;
	
	public GrowShrink(Sprite sprite)
	{
		super(sprite);
		phase = 1;
		totalTime = 1000;
		percentConstant = .1;
		deltaPercent = 0.0001;
		increaseDeltaPercent = 0.0015;
		phase2Time = (long)(totalTime * percentConstant);
		phase1Time = (totalTime - phase2Time) / 2;
		phase3Time = phase1Time;
		originalWidth = sprite.getOriginalWidth();
		originalHeight = sprite.getOriginalHeight();
		originalX = sprite.getOriginalX();
		originalY = sprite.getOriginalY();
		waiting = true;
		timer = new Timer(phase1Time);
	}

	@Override
	public void tick()
	{
		if(!waiting)
		{
			switch(phase)
			{
			case 1: runPhase1();break;
			case 2: runPhase2(); break;
			case 3: runPhase3();
			}
		}
		else
		{
			if(originalWidth == getMainSprite().getWidth())
				waiting = false;
		}
	}

	//increase size phase
	private void runPhase1()
	{
		if(!timer.isDone())
		{
			phase1Helper();
		}
		else
		{
			phase++;
			timer = new Timer(phase2Time);
		}
	}

	//constant phase
	private void runPhase2()
	{
		if(timer.isDone())
		{
			phase++;
		}
	}
	
	//decrease size phase
	private void runPhase3()
	{
		phase3Helper();
		if(originalWidth > getMainSprite().getWidth())
		{
			phase++;
			setDone(true);
			getMainSprite().setWidth(originalWidth);
			getMainSprite().setHeight(originalHeight);
			getMainSprite().setX(originalX);
			getMainSprite().setY(originalY);
		}
	}
	
	private void phase1Helper()
	{
		//find the new width and height of the sprite.
		double currentWidth = getMainSprite().getWidth();
		double currentHeight = getMainSprite().getHeight();
		double newWidth = currentWidth * (1 + deltaPercent);
		double newHeight = currentHeight * (1 + deltaPercent);
		//deltaWidth += newWidth - currentWidth;
		//deltaHeight += newHeight - currentHeight;
		
		//update the sprite's information
		getMainSprite().setWidth(newWidth);
		getMainSprite().setHeight(newHeight);
		getMainSprite().setX(getMainSprite().getX() - ((newWidth - currentWidth) / 2));
		getMainSprite().setY(getMainSprite().getY() - ((newHeight - currentHeight) / 2));
		
		//update deltaPercent
		deltaPercent += increaseDeltaPercent;
	}
	
	private void phase3Helper()
	{
		//find the new width and height of the sprite.
			double currentWidth = getMainSprite().getWidth();
			double currentHeight = getMainSprite().getHeight();
			double newWidth = currentWidth * (1 - deltaPercent);
			double newHeight = currentHeight * (1 - deltaPercent);
			//deltaWidth += newWidth - currentWidth;
			//deltaHeight += newHeight - currentHeight;
			
			//update the sprite's information
			getMainSprite().setWidth(newWidth);
			getMainSprite().setHeight(newHeight);
			getMainSprite().setX(getMainSprite().getX() + ((currentWidth - newWidth) / 2));
			getMainSprite().setY(getMainSprite().getY() + ((currentHeight - newHeight) / 2));
			
			//update deltaPercent
			deltaPercent -= increaseDeltaPercent;
	}
}
