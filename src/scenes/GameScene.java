package scenes;

import animation.FloatDrop;
import animation.ImageLoader;
import animation.Spin;
import animation.SpriteAnimator;
import animation.SpriteWrapper;
import game.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import main.Connect5;
import manage.Manager;
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
	private int selected = -1;
	private int mouseHoverCol = -1;
	private boolean displayColMarker = true;
	
	//animation
	private SpriteAnimator spriteAnimator;
	
	public GameScene()
	{
		spriteAnimator = new SpriteAnimator();
		setUpGameScene();
		if(Manager.getGame() instanceof GameMulti)
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
	}
	
	private void setUpExitGame()
	{
		exitBtn = new Button("Quit");
		exitBtn.setStyle("-fx-min-width: 200px");
		exitBtn.setOnAction(e -> Manager.closePrompt());
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
				Game tempGame = Manager.getGame();
				if(tempGame instanceof GameMulti)
					((GameMulti)tempGame).sendMessage(text);
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
		p1PtTxt = new Text("0");
		p2PtTxt = new Text("0");
		
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
		canvas = new Canvas(Manager.getGame().getGameBoard().colSize() * Token.WIDTH * Connect5.getScale(), Manager.getGame().getGameBoard().rowSize() * Token.HEIGHT * Connect5.getScale());
		canvas.setOnMouseClicked(e ->
		{
			placeToken(e.getX());
		});
		canvas.setOnMouseMoved(e ->
		{
			setMouseHoverCol(e.getX());
		});
		canvas.setOnMouseDragged(e ->
		{
			setMouseHoverCol(e.getX());
		});
		canvas.setOnMouseExited(e ->
		{
			mouseHoverCol = -1;
		});
	}
	
	private void setMouseHoverCol(double x)
	{
		mouseHoverCol = (int)(x / (Token.WIDTH * Connect5.getScale()));
	}
	
	private void placeToken(double x)
	{
		if(selected == -1) //no token is selected
			return;
		int col = (int)(x / (Token.WIDTH * Connect5.getScale()));
		Manager.placeToken(selected, col);
		
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
		for(int i = 0; i < Manager.getGame().getOnScreenTokenPile().getCurrentHandCopy().length; i++)
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
		//long timebefore = System.currentTimeMillis();
		
		updateGameBoard();
		updateTokenQueue();
		updateScoreBox();
//		long timePassedMilli = System.currentTimeMillis() - timebefore;
//		long sleepTime = (SLEEP_MILITIME - timePassedMilli) > 0 ? (SLEEP_MILITIME - timePassedMilli) : 0;
//		System.out.println(sleepTime);
		//Connect5.getManager().notify();//tells the gameLoop that the gui is finished updating.
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
		for(int x = 0; x < Manager.getGame().getGameBoard().rowSize(); x++)
			for(int y = 0; y < Manager.getGame().getGameBoard().colSize(); y++)
				gc.drawImage(ImageLoader.EMPTY_TILE, y * Token.WIDTH * Connect5.getScale(), x * Token.HEIGHT * Connect5.getScale());
		
		
		//draws the tokens.
		
		gc.setFont(new Font("impact", 28));
		for(Token token: Manager.getGame().getGameBoard().getTokenCopy())
			if(token != null)
			{
				
				rotateGC(gc, token.getFaceAngle(), token.getX() + token.getWidth() / 2, token.getY() + token.getHeight() / 2);
				gc.drawImage(token.getImage(), token.getX(), token.getY(), token.getWidth(), token.getHeight());
				//draws the number on the token
				
				if(token.getPoints() != 0)
				{
					gc.setFill((token.getPlayer() == PLAYER1) ? Color.BLACK: Color.LIGHTGRAY);
					gc.fillText(token.getPoints() + "", token.getX() + (token.getWidth() / 2) - 4, token.getY() + (token.getHeight() / 2) + 12);
				}
					
			}
		gc.restore();
		//draws the col bars.
		if(displayColMarker)
			gc.setFill(Color.rgb(0, 0, 0, .1));	
		else
			gc.setFill(Color.rgb(0, 0, 0, 0));
		gc.fillRect(mouseHoverCol * (Token.WIDTH * Connect5.getScale()), 0, (Token.WIDTH * Connect5.getScale()), canvas.getHeight());
	}
	
	private static void rotateGC(GraphicsContext gc, double angle, double centerX, double centerY)
	{
		Rotate r = new Rotate(angle, centerX, centerY);
		gc.setTransform(new Affine(r));
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
		Token[] tq = Manager.getGame().getOnScreenTokenPile().getCurrentHandCopy();
		
		
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
					gc.fillText(tq[i].getPoints() + "", 63 + (i * 100), 68);
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
		root.setAlignment(Pos.CENTER);
		scene = new Scene(root, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
		root.setHgap(20);
		root.setVgap(20);
		root.setPadding(new Insets(20, 20, 20, 20));
		
		VBox rightContainer = new VBox(50);
		rightContainer.getChildren().addAll(scoreBox, textBox, exitBtn);
		VBox leftContainer = new VBox(20);
		leftContainer.setAlignment(Pos.CENTER);
		leftContainer.getChildren().addAll(canvas, tokenQueue);
		
		//scoreBox.add(textBox, 0, 2);
		
		//adding stuff to scene
		root.add(rightContainer, 1, 0);
		root.add(leftContainer, 0, 0);
		
		//adding styling
		Style.addTextStyle(scene);
		Style.addButtonStyle(scene);
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
		p1PtTxt.setText(Manager.getGame().getPlayer1Points() + "");
		p2PtTxt.setText(Manager.getGame().getPlayer2Points() + "");
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
	
	public void gameOverMulti(int status)
	{
		System.out.println("SEHHVSIHvhusu");
		System.out.println("SEHHVSIHvhusu");
		System.out.println("SEHHVSIHvhusu");
		System.out.println("SEHHVSIHvhusu");
		System.out.println("SEHHVSIHvhusu");
		System.out.println("SEHHVSIHvhusu");
		stopGame();
		if(status == ServerConstants.WIN)
			print("Server", "You win!");
		else if(status == ServerConstants.LOSE)
			print("Server", "You lose!");
		else if(status == ServerConstants.TIED)
			print("Server", "A Tie!");
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public void setSelected(int selected)
	{
		this.selected = selected;
	}
	
	public String toString()
	{
		return Manager.getGame().toString();
	}
}
