package game;

import animation.ImageLoader;
import javafx.scene.image.Image;
import main.Connect5;

public abstract class Token extends Sprite implements GameConstants
{
	private static final long serialVersionUID = -9077762852402278129L;
	private int player;
	
	public Token(String name, Image image, double x, double y, double width, double height, int player)
	{
		super(name, image, x, y, width, height, 0);
		this.player = player;
	}
	
	public int getPlayer()
	{
		return player;
	}
	
	public abstract int getPoints();
	
	public static NumberToken createNumberToken(int player, int number)
	{
		return new NumberToken(ImageLoader.getTokenImagePlayer(player, number), 0, 0 , 100 * Connect5.getScale(), 100 * Connect5.getScale(), player, number);
	}
}
