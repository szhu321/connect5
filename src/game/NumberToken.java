package game;

public class NumberToken extends Token
{
	private static final long serialVersionUID = 6585671174319245550L;
	
	private int number;

	public NumberToken(double x, double y, double width, double height, int player, int number)
	{
		super("Number Token", null, x, y, width, height, player);
		this.number = number;
	}
	
	public int getNumber() {return number;}
	
	public String toString()
	{
		return "Player:" + getPlayer() + " Number:" + number;
	}
	
	public static NumberToken createNumberToken(int player, int number)
	{
		return new NumberToken(0, 0 , 100, 100, player, number);
	}
}
