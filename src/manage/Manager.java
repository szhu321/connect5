package manage;

import animatioon.ImageLoader;
import game.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Connect5;

public abstract class Manager implements GameConstants
{
	//Gui
	private Stage window;
	private GridPane scoreBox;
	private Canvas canvas;
	private Canvas tokenQueue;
	private Scene scene;
	
	//Gui Chunks
	protected Label p1PtLbl;
	protected Label p2PtLbl;
	protected Text p1PtTxt;
	protected Text p2PtTxt;
	
	//backend
	private Game game;
	
	public Manager(Stage window, Game game)
	{
		this.window = window;
		this.game = game;
		setUpGameScene();
	}
	
	//todo: creates the canvas 
	private void setUpGameScene()
	{
		setUpScoreBox();
		setUpCanvas();
		setUpTokenQueue();
		addAndDisplayNewScene();
		updateGUI();
	}
	
	//todo: creates labels to display both players scores.
	private void setUpScoreBox()
	{
		scoreBox = new GridPane();
		scoreBox.setHgap(10);
		scoreBox.setVgap(20);
		
		//labels for the scores
		p1PtLbl = new Label("Player 1:");
		p2PtLbl = new Label("Player 2:");
		p1PtTxt = new Text(game.getPlayer1Points() + "");
		p2PtTxt = new Text(game.getPlayer2Points() + "");
		
		//adding to the scoreBox
		scoreBox.add(p1PtLbl, 0, 0);
		scoreBox.add(p1PtTxt, 1, 0);
		scoreBox.add(p2PtLbl, 0, 1);
		scoreBox.add(p2PtTxt, 1, 1);
	}
	
	//todo: creates the main board to display the players progress.
	private void setUpCanvas()
	{
		canvas = new Canvas(800, 800);
	}
	
	//todo: creates a queue that holds available tokens to be used.
	private void setUpTokenQueue()
	{
		tokenQueue = new Canvas(350, 120);
	}
	
	//todo: redraws the canvas.
	public void updateGUI()
	{
		updateGameBoard();
		updateTokenQueue();
		updateScoreBox();
	}
	
	public void updateGameBoard()
	{
		//repaints the board.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		
		//draws the tokens.
		for(Token token: game.getGameBoard().getTokenCopy())
			if(token != null)
				gc.drawImage(token.getImage(), token.getX(), token.getY());
		
		//draws the board.
		for(int x = 0; x < game.getGameBoard().rowSize(); x++)
			for(int y = 0; y < game.getGameBoard().colSize(); y++)
				gc.drawImage(ImageLoader.EMPTY_TILE, y * 100 * Connect5.getScale(), x * 100 * Connect5.getScale());
		
	}
	
	public void updateTokenQueue()
	{
		//repaints the tokenQueue.
		GraphicsContext gc = tokenQueue.getGraphicsContext2D();
		//clears the queue.
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		
		Token[] tq = game.getOnScreenTokenPile().getCurrentHandCopy();
		for(int i = 0; i < tq.length; i++)
			gc.drawImage(tq[i].getImage(), 25 + (i * 100), 10);
	}
	
	/**
	 * Creates a new scene and displays it on the window.
	 */
	public void addAndDisplayNewScene()
	{
		BorderPane root = new BorderPane();
		scene = new Scene(root, 1000, 800);
		root.setMinHeight(800);
		root.setMinWidth(1000);
		root.setPadding(new Insets(20, 20, 20, 20));
		
		//adding stuff to scene
		root.setRight(scoreBox);
		root.setCenter(canvas);
		root.setBottom(tokenQueue);
		
		
		//set scene to window
		window.setScene(scene);
	}
	
	public void updateScoreBox()
	{
		p1PtTxt.setText(game.getPlayer1Points() + "");
		p2PtTxt.setText(game.getPlayer2Points() + "");
	}
	
	
	
	/**
	 * Manages game over
	 */
	public abstract void gameOver();
	
	public Stage getWindow()
	{
		return window;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public String toString()
	{
		return game.toString();
	}
}
