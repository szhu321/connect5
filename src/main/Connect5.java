package main;

import java.net.Socket;

import animation.ImageLoader;
import game.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import manage.Manager;
import scenes.*;

public class Connect5 extends Application
{
	/*
	 * TODO:
	 * Disconnect players properly from servers.
	 * Add a restart button.
	 */
	
	//screen and items sizes
	private static double scale = 1;
	public static final double SCREEN_WIDTH = 1100;
	public static final double SCREEN_HEIGHT = 800;
	
	private static Manager manager;
	private static MainMenu mainMenu;
	private static ServerPage serverPage;
	private static ServerLounge serverLounge;
	private static HowToPlay howToPlay;

	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage window)
	{
		//setting up the window.
		window.setTitle("Connect 5");
		//window.setResizable(false);
		window.setOnCloseRequest(e -> System.exit(0));
		HBox bh = new HBox(12);
		bh.setSpacing(20);
		bh.setPadding(new Insets(20,20,20,20));
		//loading images and going to the main menu.
		ImageLoader.initImages();
		
		Scene.setWindow(window);
		
		toMainMenuScene();
	}
	
	public static void createNewManager(Game game)
	{
		manager = new Manager(game);
	}

	public static void toManagerScene()
	{
		Manager.getGameScene().getScene().showScene();
	}
	
	public static void toServerScene()
	{
		serverPage = new ServerPage();
		serverPage.getScene().addStylesheets(Style.BUTTON_STYLE, Style.TEXT_STYLE);
		serverPage.getScene().showScene();
	}
	
	public static void toMainMenuScene()
	{
		mainMenu = new MainMenu();
		mainMenu.getScene().addStylesheets(Style.BUTTON_STYLE, Style.TEXT_STYLE);
		mainMenu.getScene().showScene();
	}
	
	public static void toHowToPlayScene()
	{
		howToPlay = new HowToPlay();
		howToPlay.getScene().addStylesheets(Style.BUTTON_STYLE, Style.TEXT_STYLE);
		howToPlay.getScene();
	}
	
	public static void toServerLounge(Socket server)
	{
		serverLounge = new ServerLounge(server);
		serverLounge.getScene().addStylesheets(Style.BUTTON_STYLE, Style.TEXT_STYLE);
		serverLounge.getScene().showScene();
	}
	
	public static Manager getManager()
	{
		return manager;
	}
	
	public static double getScale(){return scale;}
	public static void setScale(double scale){Connect5.scale = scale;}
}
