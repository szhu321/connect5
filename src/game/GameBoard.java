package game;

public class GameBoard implements GameConstants
{
	/**
	 * 0,0 would be the bottom left of the board.
	 */
	private Token[][] gameBoard;
	
	public GameBoard()
	{
		gameBoard = new Token[SIZE_ROW][SIZE_COL];
	}
	
	//todo: places a token in a specified column.
	public void placeToken(Token tk, int col)
	{
		
	}
	
	//todo: gets the row that is available in the given column.
	public int getAvaliableRow(int col)
	{
		return 0;
	}
	
	//todo: moves all tokens into the correct position.
	public void dropAll()
	{
		
	}
	
	//todo:moves all tokens in the given column to the correct position.
	public void dropSingle(int col)
	{
		
	}
	
	//todo:checks to see if there is either a connect 5 or a full board.
	public void checkGameOver()
	{
		
	}
	
	//todo:removes a token from the given row and column.
	public void removeToken(int row, int col)
	{
		
	}
	
	//todo:remove all tokens
	public void clearBoard()
	{
		
	}
	
	public String toString()
	{
		String result = "";
		//goes through the row backwards to create the effect of the pieces dropping to the bottom.
		for(int row = gameBoard.length - 1; row >= 0; row--)
		{
			for(int col = 0; col < gameBoard.length; col++)
				result += gameBoard[row][col].toString();
			result += "\n";
		}
		return result;
	}
}
