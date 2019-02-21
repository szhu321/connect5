package game;

public class NumberToken extends Token
{
	private static final long serialVersionUID = 6585671174319245550L;
	
	private int number;

	public NumberToken(double x, double y, double width, double height, int player)
	{
		super("Number Token", null, x, y, width, height, player);
	}
	
	public int getNumber() {return number;}
	
	public String toString()
	{
		return number + "";
	}
}
