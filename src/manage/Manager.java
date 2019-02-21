package manage;

import game.GameBoard;
import game.GameConstants;
import javafx.stage.Stage;

public abstract class Manager implements GameConstants
{
	protected Stage window;
	protected GameBoard gameBoard;
	
	
	public Manager(Stage window)
	{
		this.window = window;
		gameBoard = new GameBoard();
	}
	
	/**calculates the given players points and returns it.
	 * 
	 * @param player the player constant that the user wants calculated.
	 * @return the points of the given player.
	 */
	public int calcPoints(int player)
	{
		return 0;
	}
	
	/**checks to see if the game is over and returns the winning player.
	 * 
	 * @return the winning player.
	 */
	public abstract int checkGameOver();
	
	public Stage getWindow()
	{
		return window;
	}
}
