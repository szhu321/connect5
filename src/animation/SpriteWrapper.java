package animation;

import game.Sprite;

public abstract class SpriteWrapper
{
	private Sprite mainSprite;
	
	public SpriteWrapper(Sprite sprite)
	{
		mainSprite = sprite;
	}
	
	public Sprite getMainSprite() {return mainSprite;}
	public abstract void run();
}
