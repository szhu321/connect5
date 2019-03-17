package game;

public class GameMulti extends Game
{
	public TokenPile player2Pile;
	public TokenPile player1Pile; //alias for the token pile in the super class;
	
	public GameMulti()
	{
		player2Pile = new TokenPile(PLAYER2);
		player1Pile = getPlayerPile();
	}
	
	@Override
	public Token placeToken(int handIdx, int col)
	{
		
		return null;
	}

}
