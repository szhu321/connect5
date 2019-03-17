package game;


public abstract class Game implements GameConstants
{
	protected boolean myTurn;
	private TokenPile playerPile;
	private GameBoard gameBoard;
	private int player1Points;
	private int player2Points;
	
	public Game()
	{
		gameBoard = new GameBoard();
		playerPile = new TokenPile(PLAYER1);
	}
	
	/**
	 * Gets the token pile that should be currently be displayed.
	 * @return Current TokenPile.
	 */
	public abstract TokenPile getOnScreenTokenPile();
	
	/**
	 * Adds a token to the game board.
	 * @param tk - The token to be added.
	 * @param col - The column to add it in.
	 * @return if successfully placed return the placed token and set myTurn to false. 
	 * else return null.
	 */
	public abstract Token placeToken(int handIdx, int col);
	
	/**
	 * Swaps the turn
	 */
	public void swapTurn()
	{
		myTurn = !myTurn;
	}
	
	/**
	 * Checks to see if there is either a connect 5 or a full board.
	 */
	public boolean isGameOver()
	{
//		System.out.println(gameBoard.isFull());
//		System.out.println(isConnect5());
		return gameBoard.isFull() || isConnect5();
	}
	
	/**
	 * Calculates the number of token each player is going to get from the start.
	 * @return Number of tokens
	 */
	protected int getTokenCount()
	{
		return (gameBoard.totalSize() % 2 == 0) ? gameBoard.totalSize() / 2 : (gameBoard.totalSize() / 2) + 1;
	}
	
	/**
	 * calculates both player's points and update the points instance variable
	 * for both players.
	 */
	public void calcPoints()
	{
		//calculate and set player1's points
		int tempPoints = 0;
		for(int row = 0; row < gameBoard.rowSize(); row++)
			for(int col = 0; col < gameBoard.colSize(); col++)
				if(gameBoard.getToken(row, col) != null && gameBoard.getToken(row, col).getPlayer() == PLAYER1)
					tempPoints += gameBoard.getToken(row, col).getPoints();
		tempPoints += getConnectPoints(PLAYER1);
		player1Points = tempPoints;
		
		//calculate and set player2's points
		tempPoints = 0;
		for(int row = 0; row < gameBoard.rowSize(); row++)
			for(int col = 0; col < gameBoard.colSize(); col++)
				if(gameBoard.getToken(row, col) != null && gameBoard.getToken(row, col).getPlayer() == PLAYER2)
					tempPoints += gameBoard.getToken(row, col).getPoints();
		tempPoints += getConnectPoints(PLAYER2);
		player2Points = tempPoints;
	}
	
	/**
	 * Where ever there is a connect4 add those token's points.
	 * @param player The player selected.
	 * @return Number of points the selected player has.
	 */
	private int getConnectPoints(int player)
	{
		int[][] tempBoard = gameBoard.getPlayerGrid();
		int result = 0;
		//connect 4 Vertical
		for(int row = 0; row < gameBoard.rowSize() - 3; row++)
			for(int col = 0; col < gameBoard.colSize(); col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row + 1][col] == player
						&& tempBoard[row + 2][col] == player && tempBoard[row + 3][col] == player)
				{
					result += gameBoard.getToken(row, col).getPoints() + 
							gameBoard.getToken(row + 1, col).getPoints() +
							gameBoard.getToken(row + 2, col).getPoints() +
							gameBoard.getToken(row + 3, col).getPoints();
				}
			}
		
		//connect 4 Horizontal
		for(int row = 0; row < gameBoard.rowSize(); row++)
			for(int col = 0; col < gameBoard.colSize() - 3; col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row][col + 1] == player
						&& tempBoard[row][col + 2] == player && tempBoard[row][col + 3] == player)
				{
					result += gameBoard.getToken(row, col).getPoints() + 
							gameBoard.getToken(row, col + 1).getPoints() +
							gameBoard.getToken(row, col + 2).getPoints() +
							gameBoard.getToken(row, col + 3).getPoints();
				}
			}
		
		//connect 4 Up Right
		for(int row = 0; row < gameBoard.rowSize() - 3; row++)
			for(int col = 0; col < gameBoard.colSize() - 3; col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row + 1][col + 1] == player
						&& tempBoard[row + 2][col + 2] == player && tempBoard[row + 3][col + 3] == player)
				{
					result += gameBoard.getToken(row, col).getPoints() + 
							gameBoard.getToken(row + 1, col + 1).getPoints() +
							gameBoard.getToken(row + 2, col + 2).getPoints() +
							gameBoard.getToken(row + 3, col + 3).getPoints();
				}
			}
		
		//connect 4 Up Left
		for(int row = 0; row < gameBoard.rowSize() - 3; row++)
			for(int col = 3; col < gameBoard.colSize(); col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row + 1][col - 1] == player
						&& tempBoard[row + 2][col - 2] == player && tempBoard[row + 3][col - 3] == player)
				{
					result += gameBoard.getToken(row, col).getPoints() + 
							gameBoard.getToken(row + 1, col - 1).getPoints() +
							gameBoard.getToken(row + 2, col - 2).getPoints() +
							gameBoard.getToken(row + 3, col - 3).getPoints();
				}
			}
		
		return result;
	}
	
	public boolean isConnect5()
	{
		int[][] tempBoard = gameBoard.getPlayerGrid();
		//connect 5 Vertical
		for(int row = 0; row < gameBoard.rowSize() - 4; row++)
			for(int col = 0; col < gameBoard.colSize(); col++)
			{
				int player = tempBoard[row][col];
				if(player != VOID_PLAYER && tempBoard[row + 1][col] == player
						&& tempBoard[row + 2][col] == player && tempBoard[row + 3][col] == player
						&& tempBoard[row + 4][col] == player )
					return true;
			}
		
		//connect 5 Horizontal
		for(int row = 0; row < gameBoard.rowSize(); row++)
			for(int col = 0; col < gameBoard.colSize() - 4; col++)
			{
				int player = tempBoard[row][col];
				if(player != VOID_PLAYER && tempBoard[row][col + 1] == player
						&& tempBoard[row][col + 2] == player && tempBoard[row][col + 3] == player
						&& tempBoard[row][col + 4] == player)
					return true;
			}
		
		//connect 5 Up Right
		for(int row = 0; row < gameBoard.rowSize() - 4; row++)
			for(int col = 0; col < gameBoard.colSize() - 4; col++)
			{
				int player = tempBoard[row][col];
				if(player != VOID_PLAYER && tempBoard[row + 1][col + 1] == player
						&& tempBoard[row + 2][col + 2] == player && tempBoard[row + 3][col + 3] == player
						&& tempBoard[row + 4][col + 4] == player)
					return true;
			}
		
		//connect 5 Up Left
		for(int row = 0; row < gameBoard.rowSize() - 4; row++)
			for(int col = 4; col < gameBoard.colSize(); col++)
			{
				int player = tempBoard[row][col];
				if(player != VOID_PLAYER && tempBoard[row + 1][col - 1] == player
						&& tempBoard[row + 2][col - 2] == player && tempBoard[row + 3][col - 3] == player
						&& tempBoard[row + 4][col - 4] == player )
					return true;
			}
		
		return false;
	}
	
	
	//Accessors
	public boolean isMyTurn() {return myTurn;}
	public GameBoard getGameBoard() {return gameBoard;}
	public int getPlayer1Points() {return player1Points;}
	public int getPlayer2Points() {return player2Points;}
	public TokenPile getPlayerPile() {return playerPile;}
	
	public String toString()
	{
		String result = "";
		result += gameBoard.toString() + "\n";
		result += "Player1 Score: " + player1Points + "\n";
		result += "Player2 Score: " + player2Points + "\n";
		return result;
	}
}
