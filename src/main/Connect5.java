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
	
	private static Stage window;
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
	public void start(Stage st)
	{
		//setting up the window.
		window = st;
		window.setTitle("Connect 5");
		//window.setResizable(false);
		window.setOnCloseRequest(e -> System.exit(0));
		HBox bh = new HBox(12);
		bh.setSpacing(20);
		bh.setPadding(new Insets(20,20,20,20));
		//loading images and going to the main menu.
		ImageLoader.initImages();
		toMainMenuScene();
		
		//displaying the screen.
		window.show();
	}
	
	public static void createNewManager(Game game)
	{
		manager = new Manager(game);
	}

	public static void toManagerScene()
	{
		window.setScene(Manager.getGameScene().getScene());
	}
	
	public static void toServerScene()
	{
		serverPage = new ServerPage();
		Style.addTextStyle(serverPage.getScene());
		Style.addButtonStyle(serverPage.getScene());
		window.setScene(serverPage.getScene());
	}
	
	public static void toMainMenuScene()
	{
		mainMenu = new MainMenu();
		Style.addTextStyle(mainMenu.getScene());
		Style.addButtonStyle(mainMenu.getScene());
		window.setScene(mainMenu.getScene());
	}
	
	public static void toHowToPlayScene()
	{
		howToPlay = new HowToPlay();
		Style.addTextStyle(howToPlay.getScene());
		Style.addButtonStyle(howToPlay.getScene());
		window.setScene(howToPlay.getScene());
	}
	
	public static void toServerLounge(Socket server)
	{
		serverLounge = new ServerLounge(server);
		Style.addTextStyle(serverLounge.getScene());
		Style.addButtonStyle(serverLounge.getScene());
		window.setScene(serverLounge.getScene());
	}
	
	public static Manager getManager()
	{
		return manager;
	}
	
	public static double getScale(){return scale;}
	public static void setScale(double scale){Connect5.scale = scale;}
}
