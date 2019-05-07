package animation;

import java.util.ArrayList;
import java.util.List;

import game.GameConstants;
import game.Sprite;
import game.Token;
import javafx.application.Platform;
import util.Util;

/**
 * This class is used to animate Sprites.
 *	You would need to first wrap the sprite in a 
 *	spriteWapper before adding that spriteWrapper into this class.
 *	This class will run the spriteWrapper class's tick method 
 *	repeatedly which will transform the Sprite based on the 
 *	specifications in the spriteWapper. The spriteWrapper 
 *	also has a boolean field called done, which this class
 *	will check to see if the spriteWapper is done animating and 
 *	thus can be removed.
 * @author Sheng Wei
 *	
 */
public class SpriteAnimator
{
	private static SpriteAnimator currentAnimator;
	private List<SpriteWrapper> spriteWrapper;
	private boolean running;
	
	//added animation for token spinning.
	//private ArrayList<Token[]> tokenSpinQueue = new ArrayList<Token[]>();
	
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
				long timebefore = System.currentTimeMillis();
				manageSpriteWrappers();
				long timePassedMilli = System.currentTimeMillis() - timebefore;
				long sleepTime = (GameConstants.SLEEP_MILITIME - timePassedMilli) > 0 ? (GameConstants.SLEEP_MILITIME - timePassedMilli) : 0;
				try {
					Thread.sleep(sleepTime);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void manageSpriteWrappers()
	{
		for(int i = spriteWrapper.size() - 1; i >= 0; i--)
		{
			//System.out.println("Tick " + i);
			SpriteWrapper temp = spriteWrapper.get(i);
			temp.tick(); //ticks the animation.
			if(temp.getDone())
			{
				spriteWrapper.remove(temp);
				temp.getMainSprite().setAnimating(false);
			}
		}
	}
	
	public void animateSpinningGroup(Token[] tokens)
	{
		new Thread(() -> 
		{
			for(Token tk: tokens)
			{
				//SpriteWrapper sw = new Spin(tk, 1200);
				//addSpriteWrapper(sw);
				SpriteWrapper sw2 = new GrowShrink(tk);
				addSpriteWrapper(sw2);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void addToSpinQueue(Token[] tokens)
	{
		new Thread(() -> 
		{
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(isAnimating(tokens))
			{
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			animateSpinningGroup(tokens);
		}).start();
	}
	
	public boolean isAnimating(Sprite[] sprite)
	{
		for(Sprite sp: sprite)
			if(sp.isAnimating())
				return true;
		return false;
	}
	
	/**
	 * Stops the timer. There may be a problem if a new timer is created to soon after invoking this method.
	 */
	public void stopTimer()
	{
		running = false;
	}
	
	/**
	 * Checks to see if there is anything animating.
	 * @return true if there is something animating, false otherwise.
	 */
	public boolean isRunning()
	{
		return spriteWrapper.size() != 0;
	}
	
	public static SpriteAnimator getCurrentAnimator()
	{
		return currentAnimator;
	}
}
