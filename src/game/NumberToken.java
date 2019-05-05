package game;

import animation.ImageLoader;
import javafx.scene.image.Image;

public class NumberToken extends Token
{
	private static final long serialVersionUID = 6585671174319245550L;
	
	private int number;

	public NumberToken(Image image, double x, double y, double width, double height, int player, int number)
	{
		super("Number Token", image, x, y, width, height, player);
		this.number = number;
	}
	
	public int getNumber() {return number;}
	
//	public String toString()
//	{
//		return "Player:" + getPlayer() + " Number:" + number;
//	}

	@Override
	public int getPoints()
	{
		return number;
	}

	@Override
	public Sprite getCopy()
	{
		return new NumberToken(getImage(), getX(), getY(), getWidth(), getHeight(), getPlayer(), number);
	}
	
	
}
