package game;

import manage.Manager;

public class GameSingle extends Game
{
	private TokenPile cpuPile;
	private Thread brain;
	private int[] aiChoiceWeight;
	
	public GameSingle()
	{
		myTurn = true;
		cpuPile = new TokenPile(PLAYER2);
		brain = new Thread(() -> makeMove());
		aiChoiceWeight = new int[getGameBoard().colSize()];
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
				if(!isGameOver())
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
		
		
		/*
		 * Some Ai implementation methods.
		 * *check for and 3s and place a token there.
		 * *check for any twos and threes and place them there.
		 * *if there is a available four slot and its enough points then put it there to connect 5.
		 * 
		 */
		
		/*
		 * Implement a danger rating.
		 * If the ai's score is high the danger rating is low.
		 * else the danger rating is high.
		 * 
		 * Depending on the danger rating place numbered tokens
		 * accordingly.
		 */
		
		
		
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
