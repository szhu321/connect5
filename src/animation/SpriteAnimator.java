package animation;

import java.util.ArrayList;
import java.util.List;

import game.GameConstants;
import game.Sprite;

public class SpriteAnimator
{
	private static SpriteAnimator currentAnimator;
	private List<SpriteWrapper> spriteWrapper;
	private boolean running;
	
	public SpriteAnimator()
	{
		spriteWrapper = new ArrayList<SpriteWrapper>();
		currentAnimator = this;
		running = true;
		startNewTimer();
	}
	
	public void addSpriteWrapper(SpriteWrapper sw)
	{
		spriteWrapper.add(sw);
	}
	
	public void startNewTimer()
	{
		new Thread(() ->
		{
			running = true;
			while(running)
			{
				for(int i = spriteWrapper.size() - 1; i >= 0; i--)
				{
					//System.out.println("Tick " + i);
					SpriteWrapper temp = spriteWrapper.get(i);
					temp.tick(); //ticks the animation.
					if(temp.getDone())
						spriteWrapper.remove(temp);
				}
				try {
					Thread.sleep(GameConstants.SLEEP_MILITIME);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * Stops the timer. There may be a problem if a new timer is created to soon after invoking this method.
	 */
	public void stopTimer()
	{
		running = false;
	}
	
	public static SpriteAnimator getCurrentAnimator()
	{
		return currentAnimator;
	}
}
