package animation;

import game.Sprite;

public abstract class SpriteWrapper
{
	private Sprite mainSprite;
	private boolean done;
	
	public SpriteWrapper(Sprite sprite)
	{
		sprite.setAnimating(true);
		mainSprite = sprite;
		done = false;
	}
	
	public Sprite getMainSprite() {return mainSprite;}
	public abstract void tick();
	public boolean getDone(){return done;}
	public void setDone(boolean done){this.done = done;}
}
