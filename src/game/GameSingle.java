package game;

import manage.Manager;

public class GameSingle extends Game
{
	private TokenPile cpuPile;
	private Thread brain;
	
	public GameSingle()
	{
		myTurn = true;
		cpuPile = new TokenPile(PLAYER2);
		brain = new Thread(() -> makeMove());
	}

	@Override
	public Token placeToken(int handIdx, int col)
	{
		//System.out.println("Why");
		if(!myTurn)
			return null;
		Token tk = getPlayerPile().getToken(handIdx);
		if(getGameBoard().placeToken(tk, col))
		{
			myTurn = !myTurn;
			//System.out.println("Hello");
			getPlayerPile().populateHand(); //add a new token to the hand.
			
			new Thread(()->
			{
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				makeMove();
			}).start();;
			return tk;
		}
		return null;
	}

	@Override
	public TokenPile getOnScreenTokenPile() {
		return getPlayerPile();
	}
	
	private void makeMove()
	{
		Token[] ATokens = cpuPile.getCurrentHandCopy();
		int[][] gameBoard = getGameBoard().getPlayerGrid();
		//random Ai
		
		
		
		
		
		Token tk = cpuPile.getToken((int)(Math.random() * 3));
		int col;
		do
		{
			col = (int)(Math.random() * 9);
		}
		while(!(getGameBoard().placeToken(tk, col)));
		
		
		
		
		
		
		
		
		Manager.addTokenAnimation(tk);
		cpuPile.populateHand(); //add a new token to the hand.
		myTurn = true;
	}

	@Override
	public int getGameType()
	{
		return GameConstants.SINGLE_GAME;
	}
	
	public void close()
	{
		System.out.println("Single Game Closed");
	}
}
