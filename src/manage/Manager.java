package manage;

import game.GameBoard;
import game.GameConstants;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class Manager implements GameConstants
{
	protected Stage window;
	GameBoard gameBoard;
	VBox scoreBox;
	Canvas canvas;
	HBox tokenQueue;
	
	Scene scene;
	
	boolean myTurn;
	
	
	public Manager(Stage window)
	{
		this.window = window;
		gameBoard = new GameBoard();
		setUpGameScene();
	}
	
	//todo: creates the canvas 
	private void setUpGameScene()
	{
		setUpScoreBox();
		setUpCanvas();
		setUpTokenQueue();
	}
	
	//todo: creates labels to display both players scores.
	private void setUpScoreBox()
	{
		
	}
	
	//todo: creates the main board to display the players progress.
	private void setUpCanvas()
	{
		
	}
	
	//todo: creates a queue that holds available tokens to be used.
	private void setUpTokenQueue()
	{
		
	}
	
	//todo: redraws the canvas.
	public void repaint()
	{
		
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
