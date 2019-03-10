package game;

public class GameLocal extends Game
{
	public TokenPile player2Pile;
	
	public GameLocal()
	{
		player2Pile = new TokenPile(PLAYER2);
	}
	
	public TokenPile getOnScreenTokenPile()
	{
		if(myTurn)
			return getPlayerPile();
		return player2Pile;
	}

	@Override
	public boolean placeToken(int handIdx, int col)
	{
		Token tk = myTurn ? getPlayerPile().getToken(handIdx) : player2Pile.getToken(handIdx);	
		if(getGameBoard().placeToken(tk, col))
		{
			myTurn = !myTurn;
			getPlayerPile().populateHand(); //add a new token to the hand.
			player2Pile.populateHand();
			return true;
		}
		return false;
	}
}
