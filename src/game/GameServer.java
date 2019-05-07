package game;

import manage.Manager;

public class GameServer extends GameLocal
{
	public int getConnectPoints(int player)
	{
		int[][] tempBoard = getGameBoard().getPlayerGrid();
		int result = 0;
		//connect 4 Vertical
		for(int row = 0; row < getGameBoard().rowSize() - 3; row++)
			for(int col = 0; col < getGameBoard().colSize(); col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row + 1][col] == player
						&& tempBoard[row + 2][col] == player && tempBoard[row + 3][col] == player)
				{
					result += getGameBoard().getToken(row, col).getPoints() + 
							getGameBoard().getToken(row + 1, col).getPoints() +
							getGameBoard().getToken(row + 2, col).getPoints() +
							getGameBoard().getToken(row + 3, col).getPoints();
				}
			}
		
		//connect 4 Horizontal
		for(int row = 0; row < getGameBoard().rowSize(); row++)
			for(int col = 0; col < getGameBoard().colSize() - 3; col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row][col + 1] == player
						&& tempBoard[row][col + 2] == player && tempBoard[row][col + 3] == player)
				{
					result += getGameBoard().getToken(row, col).getPoints() + 
							getGameBoard().getToken(row, col + 1).getPoints() +
							getGameBoard().getToken(row, col + 2).getPoints() +
							getGameBoard().getToken(row, col + 3).getPoints();
				}
			}
		
		//connect 4 Up Right
		for(int row = 0; row < getGameBoard().rowSize() - 3; row++)
			for(int col = 0; col < getGameBoard().colSize() - 3; col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row + 1][col + 1] == player
						&& tempBoard[row + 2][col + 2] == player && tempBoard[row + 3][col + 3] == player)
				{
					result += getGameBoard().getToken(row, col).getPoints() + 
							getGameBoard().getToken(row + 1, col + 1).getPoints() +
							getGameBoard().getToken(row + 2, col + 2).getPoints() +
							getGameBoard().getToken(row + 3, col + 3).getPoints();
				}
			}
		
		//connect 4 Up Left
		for(int row = 0; row < getGameBoard().rowSize() - 3; row++)
			for(int col = 3; col < getGameBoard().colSize(); col++)
			{
				if(tempBoard[row][col] == player && tempBoard[row + 1][col - 1] == player
						&& tempBoard[row + 2][col - 2] == player && tempBoard[row + 3][col - 3] == player)
				{
					result += getGameBoard().getToken(row, col).getPoints() + 
							getGameBoard().getToken(row + 1, col - 1).getPoints() +
							getGameBoard().getToken(row + 2, col - 2).getPoints() +
							getGameBoard().getToken(row + 3, col - 3).getPoints();
				}
			}
		
		return result;
	}
}
