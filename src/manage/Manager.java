package manage;

import animation.ImageLoader;
import game.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
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
	private GridPane root;
	
	//Gui Chunks
	protected Label p1PtLbl;
	protected Label p2PtLbl;
	protected Text p1PtTxt;
	protected Text p2PtTxt;
	
	//backend
	private Game game;
	private boolean gameLoop;
	
	private int selected = -1;
	
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
		gameLoop = true;
		//runs the gameloop
		new Thread() {
			public void run()
			{
				while(gameLoop)
				{
					Platform.runLater(() -> updateGUI());
					try {
						Thread.sleep(34);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	/**
	 * creates labels to display both players scores.
	 */
	private void setUpScoreBox()
	{
		scoreBox = new GridPane();
		scoreBox.setHgap(10);
		scoreBox.setVgap(20);
		
		//labels for the scores
		p1PtLbl = new Label("Red  :");
		p2PtLbl = new Label("Black:");
		p1PtTxt = new Text(game.getPlayer1Points() + "");
		p2PtTxt = new Text(game.getPlayer2Points() + "");
		
		//adding to the scoreBox
		scoreBox.add(p1PtLbl, 0, 0);
		scoreBox.add(p1PtTxt, 1, 0);
		scoreBox.add(p2PtLbl, 0, 1);
		scoreBox.add(p2PtTxt, 1, 1);
	}
	
	/**
	 * creates the main board to display the players progress.
	 */
	private void setUpCanvas()
	{
		canvas = new Canvas(game.getGameBoard().colSize() * 100 * Connect5.getScale(), game.getGameBoard().rowSize() * 100 * Connect5.getScale());
		canvas.setOnMouseClicked(e ->
		{
			placeToken(e.getX());
		});
	}
	
	private void placeToken(double x)
	{
		if(selected == -1) //no token is selected
			return;
		int col = (int)(x / (100 * Connect5.getScale()));
		if(game.getGameBoard().isColFull(col)) //selected column is full
			return;

		if(game.placeToken(selected, col))
		{
			game.calcPoints();
			selected = -1;
		}
		//checks game over.
		if(game.isGameOver())
			stopGame();
	}
	
	//todo: creates a queue that holds available tokens to be used.
	private void setUpTokenQueue()
	{
		tokenQueue = new Canvas(350, 120);
		tokenQueue.setOnMouseClicked(e ->
		{
			selectTokenQueue(e.getX(),e.getY());
		});
	}
	
	private void selectTokenQueue(double x, double y)
	{
		x -= 25;
		y -= 10;
		
		int select = -1;
		for(int i = 0; i < game.getOnScreenTokenPile().getCurrentHandCopy().length; i++)
		{
			if(x < 100 + (100 * i) && x > (100 * i) && y > 0 && y < 100)
				select = i;
		}
		if(select == selected)
			selected = -1;
		else
			selected = select;
		//System.out.println(selected);
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
		
		//draws the board.
		for(int x = 0; x < game.getGameBoard().rowSize(); x++)
			for(int y = 0; y < game.getGameBoard().colSize(); y++)
				gc.drawImage(ImageLoader.EMPTY_TILE, y * 100 * Connect5.getScale(), x * 100 * Connect5.getScale());
		
		//draws the tokens.
		for(Token token: game.getGameBoard().getTokenCopy())
			if(token != null)
				gc.drawImage(token.getImage(), token.getX(), token.getY());
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
		{
			if(tq[i] != null)
				gc.drawImage(tq[i].getImage(), 25 + (i * 100), 10, i == selected ? 100: 90,  i == selected ? 100: 90);
		}
	}
		
	
	/**
	 * Creates a new scene and displays it on the window.
	 */
	public void addAndDisplayNewScene()
	{
		root = new GridPane();
		scene = new Scene(root, 850, 800);
		root.setHgap(20);
		root.setVgap(20);
		root.setPadding(new Insets(20, 20, 20, 20));
		
		//adding stuff to scene
		root.add(scoreBox, 1, 0);
		root.add(canvas, 0, 0);
		root.add(tokenQueue, 0, 1);
		
		
		//set scene to window
		window.setScene(scene);
	}
	
	public void updateScoreBox()
	{
		p1PtTxt.setText(game.getPlayer1Points() + "");
		p2PtTxt.setText(game.getPlayer2Points() + "");
	}
	
	public void stopGame()
	{
		canvas.setOnMouseClicked(e ->
		{
			//empty
		});
		tokenQueue.setOnMouseClicked(e ->
		{
			//empty
		});
	}
	
	public void restartGame(Game game)
	{
		this.game = game;
		setUpGameScene();
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
