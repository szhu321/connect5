package game;

public abstract class Token extends Sprite implements GameConstants
{
	private static final long serialVersionUID = -9077762852402278129L;
	private int player;
	
	public Token(String name, String imgFileName, double x, double y, double width, double height, int player)
	{
		super(name, imgFileName, x, y, width, height, 0);
		this.player = player;
	}
	
	public int getPlayer()
	{
		return player;
	}
}
