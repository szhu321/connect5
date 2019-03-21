package manage;

import game.GameConstants;
import game.GameLocal;
import game.GameSingle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.Connect5;

public class MainMenu
{
	private Scene scene;
	private VBox root;
	
	private Button localBtn;
	private Button singleBtn;
	private Button onlineBtn;
	
	public MainMenu()
	{
		setUpButtons();
		setUpScene();
	}
	
	public void setUpButtons()
	{
		localBtn = new Button("Local 2 Players");
		singleBtn = new Button("Singleplayer");
		onlineBtn = new Button("Online Multiplayer");
		
		localBtn.setOnAction(e -> 
		{
			Connect5.createNewManager(new GameLocal());
			Connect5.toManagerScene();
		});
		
		singleBtn.setOnAction(e ->
		{
			Connect5.createNewManager(new GameSingle());
			Connect5.toManagerScene();
		});
		
		onlineBtn.setOnAction(e -> 
		{
			Connect5.toServerScene();
		});
	}
	
	public void setUpScene()
	{
		root = new VBox(50);
		scene = new Scene(root, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
		root.getChildren().addAll(singleBtn, localBtn, onlineBtn);
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
