package manage;

import animation.FloatDrop;
import animation.SpriteAnimator;
import animation.SpriteWrapper;
import game.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import main.Connect5;
import scenes.GameScene;
import scenes.PromptWindow;

public class Manager implements GameConstants
{
	private static Game game; //backend for the game.
	private static GameScene screen; //frontend for the game.
	private static SpriteAnimator spriteAnimator = new SpriteAnimator();
	private boolean gameLoop;
	
	
	public Manager(int gameType)
	{
		switch(gameType)
		{
			case LOCAL_GAME: game = new GameLocal(); break;
			case ONLINE_GAME: game = new GameMulti(); break;
			default: game = new GameSingle();
		}
		startGame();
	}
	
	public Manager(Game game)
	{
		this.game = game;
		screen = new GameScene();
		startGame();
	}
	
	/**
	 * Starts the game by running the game loop.
	 * This game loop will update the GUI at 60Hz.
	 */
	public void startGame()
	{
		if(gameLoop)
			return;
		gameLoop = true;
		new Thread() {
			public void run()
			{
				while(gameLoop)
				{
					//TODO: account for the time spent running the GUI.
					Platform.runLater(() -> screen.updateGUI());
					try {
						Thread.sleep(SLEEP_MILITIME);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void close()
	{
		game.close();
	}
	
	public static void closePrompt()
	{
		String text = "Are you sure you want to leave?";
		if(game.getGameType() == ONLINE_GAME)
			text = "Really Leave?";
		if(PromptWindow.openYesNoWindow(text))
		{
			game.close();
			Connect5.toMainMenuScene();
		}
	}
	
	public static void placeToken(int tokenQueueIdx, int col)
	{
		if(game.getGameBoard().isColFull(col)) //selected column is full
			return;

		//System.out.println("Where");
		Token placedToken = game.placeToken(tokenQueueIdx, col);
		if(placedToken != null)
		{
			addTokenAnimation(placedToken);
			game.calcPoints();
			screen.setSelected(-1);
		}
		
		//checks game over. Multiplayer games have the server checking for wins.
		if(game.isGameOver() && !(game instanceof GameMulti))
			gameOver();
	}
	
	/**
	 * Manages game over
	 */
	public static void gameOver()
	{
		screen.stopGame();
		switch(game.getGameType())
		{
		case LOCAL_GAME: gameOverLocal(); break;
		case SINGLE_GAME: gameOverSingle(); break;
		}
	}
	
	private static void gameOverLocal()
	{
		if(game.getPlayer1Points() == game.getPlayer2Points())
			screen.print("System", "It was a Tie!");
		else
			screen.print("System", ((game.getPlayer1Points() > game.getPlayer2Points()) ? "Red":"Black") + " wins!");
	}
	
	private static void gameOverSingle()
	{
		if(game.getPlayer1Points() == game.getPlayer2Points())
			screen.print("System", "It was a Tie!");
		else
			screen.print("System", ((game.getPlayer1Points() > game.getPlayer2Points()) ? "Red":"Black") + " wins!");
	}
	
	public static void addTokenAnimation(Token token)
	{
		SpriteWrapper sw = new FloatDrop(token, -50, getTokenMomentum(token)); //adding animation
		SpriteAnimator.getCurrentAnimator().addSpriteWrapper(sw);
		//SpriteWrapper sw2 = new Spin(token, 700000);
		//SpriteAnimator.getCurrentAnimator().addSpriteWrapper(sw2);
	}
	
	public static void serverPlaceToken(Token tk, int col)
	{
		Token placedToken = ((GameMulti)game).placeToken(tk, col);
		if(placedToken != null)
		{
			addTokenAnimation(placedToken);
			game.calcPoints();
		}
	}
	
	public static int getTokenMomentum(Token tk)
	{
		int momentum;
		switch(tk.getPoints())
		{
		case 0: momentum = FloatDrop.LOW_MOMENTUM; break;
		case 1: momentum = FloatDrop.MEDIUM_MOMENTUM; break;
		case 2: momentum = FloatDrop.HIGH_MOMENTUM; break;
		case 3: momentum = FloatDrop.EXTREME_MOMENTUM; break;
		default: momentum = FloatDrop.LOW_MOMENTUM;
		}
		return momentum;
	}
	
	public static Game getGame()
	{
		return game;
	}
	
	public static GameScene getGameScene()
	{
		return screen;
	}
}
