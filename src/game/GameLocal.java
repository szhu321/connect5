package game;

public class GameLocal extends Game
{
	public TokenPile player2Pile;
	
	public GameLocal()
	{
		player2Pile = new TokenPile(PLAYER2, getTokenCount());
	}
	
	public TokenPile getOnScreenTokenPile()
	{
		if(isMyTurn())
			return getPlayerPile();
		return player2Pile;
	}
}
