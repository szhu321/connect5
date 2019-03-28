package scenes;

import animation.FloatDrop;
import animation.GravityDrop;
import animation.ImageLoader;
import animation.SpriteAnimator;
import animation.SpriteWrapper;
import game.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Connect5;
import server.ServerConstants;

public class GameScene implements GameConstants
{
	//Gui
	private GridPane scoreBox;
	private Canvas canvas;
	private Canvas tokenQueue;
	private Scene scene;
	private GridPane root;
	private VBox textBox;
	private Button exitBtn;
	
	//Gui Chunks
	protected Label p1PtLbl;
	protected Label p2PtLbl;
	protected Text p1PtTxt;
	protected Text p2PtTxt;
	private TextArea textArea;
	private TextField textField;
	
	//backend
	private Game game;
	private boolean gameLoop;
	private int selected = -1;
	
	//animation
	private SpriteAnimator spriteAnimator;
	
	public GameScene(Game game)
	{
		spriteAnimator = new SpriteAnimator();
		this.game = game;
		setUpGameScene();
		if(game instanceof GameMulti)
			print("System","Red Goes First! Game Start!");
	}
	
	/**
	 * creates the canvas 
	 */
	private void setUpGameScene()
	{
		setUpScoreBox();
		setUpCanvas();
		setUpTokenQueue();
		setUpTextBox();
		setUpExitGame();
		createNewScene();
		gameLoop = true;
		//runs the gameloop
		new Thread() {
			public void run()
			{
				while(gameLoop)
				{
					Platform.runLater(() -> updateGUI());
					try {
						Thread.sleep(SLEEP_MILITIME);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	private void setUpExitGame()
	{
		exitBtn = new Button("Quit");
		
	}

	private void setUpTextBox()
	{
		textBox = new VBox(10);
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textField = new TextField();
		textBox.getChildren().addAll(textArea, textField);
		
		textField.setOnAction(e -> 
		{
			String text = textField.getText();
			//prints to screen
			if(!text.equals(""))
			{
				print("You",text);
				if(game instanceof GameMulti)
					((GameMulti)game).sendMessage(text);
			}
			textField.setText("");
		});
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

		Token placedToken = game.placeToken(selected, col);
		if(placedToken != null)
		{
			SpriteWrapper sw = new FloatDrop(placedToken, -50, getTokenMomentum(placedToken)); //adding animation
			spriteAnimator.addSpriteWrapper(sw);
			game.calcPoints();
			selected = -1;
		}
		
		//checks game over. Multiplayer games have the server checking for wins.
		if(game.isGameOver() && !(game instanceof GameMulti))
			gameOver();
	}
	
	public void serverPlaceToken(Token tk, int col)
	{
		Token placedToken = ((GameMulti)game).placeToken(tk, col);
		if(placedToken != null)
		{
			SpriteWrapper sw = new FloatDrop(placedToken, -50, getTokenMomentum(placedToken)); //adding animation
			spriteAnimator.addSpriteWrapper(sw);
			game.calcPoints();
			selected = -1;
		}
	}
	
	/**
	 * creates a queue that holds available tokens to be used.
	 */
	private void setUpTokenQueue()
	{
		tokenQueue = new Canvas(350, 120);
		tokenQueue.setOnMouseClicked(e ->
		{
			selectTokenQueue(e.getX(),e.getY());
		});
	}
	
	/**
	 * Called when the user chooses a token to use.
	 * @param x X location of the token on the token queue.
	 * @param y Y location of the token on the token queue.
	 */
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
	
	/**
	 * Erase and draw on the playing board.
	 */
	public void updateGameBoard()
	{
		//repaints the board.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.save();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		
		//draws the board.
		for(int x = 0; x < game.getGameBoard().rowSize(); x++)
			for(int y = 0; y < game.getGameBoard().colSize(); y++)
				gc.drawImage(ImageLoader.EMPTY_TILE, y * 100 * Connect5.getScale(), x * 100 * Connect5.getScale());
		
		//draws the tokens.
		
		gc.setFont(new Font("impact", 26));
		for(Token token: game.getGameBoard().getTokenCopy())
			if(token != null)
			{
				gc.drawImage(token.getImage(), token.getX(), token.getY());
				//draws the number on the token
				if(token.getPoints() != 0)
				{
					gc.setFill((token.getPlayer() == PLAYER1) ? Color.BLACK: Color.LIGHTGRAY);
					gc.fillText(token.getPoints() + "", token.getX() + 30, token.getY() + 48);
				}
					
			}
		gc.restore();
	}
	
	/**
	 * Erase and draw on the token queue.
	 */
	public void updateTokenQueue()
	{
		//repaints the tokenQueue.
		GraphicsContext gc = tokenQueue.getGraphicsContext2D();
		//clears the queue.
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.BLACK);
		Token[] tq = game.getOnScreenTokenPile().getCurrentHandCopy();
		
		
		for(int i = 0; i < tq.length; i++)
		{
			if(tq[i] != null) //if there is nothing on this token queue slot skip it.
			{
				if(i == selected)
					gc.drawImage(tq[i].getImage(), 20 + (i * 100), 5,100,100);
				else
					gc.drawImage(tq[i].getImage(), 25 + (i * 100), 10,90,90);
				if(tq[i].getPoints() != 0) //paints the number on 
				{
					gc.setFont(new Font("impact", (i == selected) ? 31 : 30));
					gc.setFill((tq[i].getPlayer() == PLAYER1) ? Color.BLACK: Color.LIGHTGRAY);
					gc.fillText(tq[i].getPoints() + "", 65 + (i * 100), 70);
				}
			}
		}
	}
		
	
	/**
	 * Creates a new scene.
	 */
	public void createNewScene()
	{
		//setting up the new scene.
		root = new GridPane();
		scene = new Scene(root, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
		root.setHgap(20);
		root.setVgap(20);
		root.setPadding(new Insets(20, 20, 20, 20));
		
		VBox rightContainer = new VBox(50);
		rightContainer.getChildren().addAll(scoreBox, textBox);
		VBox leftContainer = new VBox(20);
		leftContainer.setAlignment(Pos.CENTER);
		leftContainer.getChildren().addAll(canvas, tokenQueue);
		
		//scoreBox.add(textBox, 0, 2);
		
		//adding stuff to scene
		root.add(rightContainer, 1, 0);
		root.add(leftContainer, 0, 0);
	}
	
	/**
	 * Prints the given text on the text area.
	 * @param text The text to be printed.
	 */
	public void print(String text)
	{
		textArea.appendText(text + "\n");
	}
	
	/**
	 * Prints the given text on the screen with a author.
	 * @param author The author provided.
	 * @param text The text provided.
	 */
	public void print(String author, String text)
	{
		textArea.appendText(author + ": " + text + "\n");
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
	
	/**
	 * Manages game over
	 */
	public void gameOver()
	{
		stopGame();
		switch(game.getGameType())
		{
		case LOCAL_GAME: gameOverLocal(); break;
		case SINGLE_GAME: gameOverSingle(); break;
		}
	}
	
	private void gameOverLocal()
	{
		if(game.getPlayer1Points() == game.getPlayer2Points())
			print("System", "It was a Tie!");
		else
			print("System", ((game.getPlayer1Points() > game.getPlayer2Points()) ? "Red":"Black") + " wins!");
	}
	
	private void gameOverSingle()
	{
		
	}
	
	public void gameOverMulti(int status)
	{
		stopGame();
		if(status == ServerConstants.WIN)
			print("Server", "You win!");
		else if(status == ServerConstants.LOSE)
			print("Server", "You lose!");
		else if(status == ServerConstants.TIED)
			print("Server", "A Tie!");
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
	
	public Game getGame()
	{
		return game;
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public String toString()
	{
		return game.toString();
	}
}
