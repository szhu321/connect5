package main;

import java.net.Socket;

import animation.ImageLoader;
import game.Game;
import javafx.application.Application;
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
	private static double scale = .8;
	public static final double SCREEN_WIDTH = 1000;
	public static final double SCREEN_HEIGHT = 800;
	
	
	private static Stage window;
	private static Manager manager;
	private static MainMenu mainMenu;
	private static ServerPage serverPage;
	private static ServerLounge serverLounge;

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
		window.setScene(serverPage.getScene());
	}
	
	public static void toMainMenuScene()
	{
		mainMenu = new MainMenu();
		window.setScene(mainMenu.getScene());
	}
	
	public static void toServerLounge(Socket server)
	{
		serverLounge = new ServerLounge(server);
		window.setScene(serverLounge.getScene());
	}
	
	public static double getScale(){return scale;}
	public static void setScale(double scale){Connect5.scale = scale;}
}
