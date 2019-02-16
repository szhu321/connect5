package game;

public class GameBoard implements GameConstants
{
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
}
