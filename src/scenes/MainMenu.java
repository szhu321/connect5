package scenes;

import game.GameConstants;
import game.GameLocal;
import game.GameSingle;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Connect5;
import server.Server;

public class MainMenu
{
	private Scene scene;
	private HBox root;
	
	private Button localBtn;
	private Button singleBtn;
	private Button onlineBtn;
	private Button exitGameBtn;
	
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
		exitGameBtn = new Button("Quit");
		
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
		
		exitGameBtn.setOnAction(e -> 
		{
			System.exit(0);
		});
	}
	
	public void setUpScene()
	{
		root = new HBox();
		root.setAlignment(Pos.CENTER);
		scene = new Scene(root, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
		
		VBox buttonCol = new VBox(50);
		buttonCol.setAlignment(Pos.CENTER);
		buttonCol.setPrefWidth(400);
		buttonCol.setPrefHeight(1000);
		buttonCol.getChildren().addAll(singleBtn, localBtn, onlineBtn, exitGameBtn);
		root.getChildren().add(buttonCol);
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
