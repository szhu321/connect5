package game;

import main.Connect5;

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
	public boolean placeToken(Token tk, int col)
	{
		int row = getAvaliableRow(col);
		if(row != -1)
		{
			tk.setX(col * 100 * Connect5.getScale());
			tk.setY((gameBoard.length - 1 - row) * 100 * Connect5.getScale());
			gameBoard[row][col] = tk;
		}
		return false;
	}
	
	//untested: gets the row that is available in the given column.
	public int getAvaliableRow(int col)
	{
		for(int row = 0; row < gameBoard.length; row++)
			if(gameBoard[row][col] == null) // checks to see if the row is empty for a token to be placed.
				return row;
		return -1;
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
	
	//remove all tokens
	public void clearBoard()
	{
		for(int row = 0; row < gameBoard.length; row++)
			for(int col = 0; col < gameBoard[row].length; col++)
				gameBoard[row][col] = null;
	}
	
	public String toString()
	{
		String result = "";
		//goes through the row backwards to create the effect of the pieces dropping to the bottom.
		for(int row = gameBoard.length - 1; row >= 0; row--)
		{
			for(int col = 0; col < gameBoard[row].length; col++)
			{
				if(gameBoard[row][col] == null)
					result += "N";
				else
					result += gameBoard[row][col].toString();
			}
			result += "\n";
		}
		return result;
	}
}
