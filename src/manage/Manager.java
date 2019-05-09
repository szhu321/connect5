package manage;

import java.util.ArrayList;

import animation.FloatDrop;
import animation.Spin;
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
	private static boolean gameLoop;
	
	//Used to keep track of spinning pieces.
	private static int[][] animationGrid;
	
	
	public Manager(int gameType)
	{
		this((gameType == LOCAL_GAME ? new GameLocal(): (gameType == ONLINE_GAME? new GameMulti(): new GameSingle())));
	}
	
	public Manager(Game game)
	{
		this.game = game;
		screen = new GameScene();
		startGame();
		animationGrid = new int[game.getGameBoard().rowSize()][game.getGameBoard().colSize()];
		for(int row = 0; row < animationGrid.length; row++)
			for(int col = 0; col < animationGrid[0].length; col++)
				animationGrid[row][col] = VOID_PLAYER;
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
					long timebefore = System.currentTimeMillis();
					Platform.runLater(() -> screen.updateGUI());
					long timePassedMilli = System.currentTimeMillis() - timebefore;
					long sleepTime = (SLEEP_MILITIME - timePassedMilli) > 0 ? (SLEEP_MILITIME - timePassedMilli) : 0;
					//System.out.println(sleepTime);
					
					try {
						Thread.sleep((sleepTime));
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public static void notifyConnect4(int row, int col, Direction dir)
	{
		int rowInc = 0;
		int colInc = 0;
		switch(dir)
		{
		case VERTICAL: rowInc = 1; break;
		case HORIZONTAL: colInc = 1; break;
		case UP_LEFT: rowInc = 1; colInc = -1; break;
		case UP_RIGHT: rowInc = 1; colInc = 1;
		}
		
		boolean animate = false;
		for(int i = 0; i < 4; i++)
		{
			if(animationGrid[row + (i * rowInc)][col + (i * colInc)] == VOID_PLAYER)
			{
				animate = true;
				animationGrid[row + (i * rowInc)][col + (i * colInc)] = YES_PLAYER;
			}
		}
		
		if(animate)
		{
			Token[] tempTs = new Token[4];
			for(int i = 0; i < 4; i++)
			{
				Token token = game.getGameBoard().getToken(row + (i * rowInc), col + (i * colInc));
				tempTs[i] = token;
			}
			spriteAnimator.addToSpinQueue(tempTs);
		}
	}
	
	public static void close()
	{
		gameLoop = false;
		game.close();
	}
	
	public static void closePrompt()
	{
		String text = "Are you sure you want to leave?";
		if(game.getGameType() == ONLINE_GAME)
			text = "Really Leave?";
		if(PromptWindow.openYesNoWindow(text))
		{
			close();
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
		token.updateOriginalLocations();
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
	
	public static void restart()
	{
		//restart
		PromptWindow.openYesNoWindow("Button does not work yet is that ok?");
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
