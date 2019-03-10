package main;

import animation.ImageLoader;
import game.GameBoard;
import game.GameConstants;
import game.NumberToken;
import javafx.application.Application;
import javafx.stage.Stage;
import manage.*;
import manage.Manager;

public class Connect5 extends Application
{
	//screen and items sizes
	private static double scale = .7;
	public static final double SCREEN_WIDTH = 850;
	public static final double SCREEN_HEIGHT = 800;
	
	
	private static Stage window;
	private static Manager manager;
	private static MainMenu mainMenu;
	private static ServerPage serverPage;

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
		window.setResizable(false);
		window.setOnCloseRequest(e -> System.exit(0));
		
		//loading images and going to the main menu.
		ImageLoader.initImages();
		toMainMenuScene();
		
		//displaying the screen.
		window.show();
	}
	
	public static void createNewManager(int type)
	{
		manager = new Manager(type);
	}

	public static void toManagerScene()
	{
		window.setScene(manager.getScene());
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
	
	public static double getScale(){return scale;}
	public static void setScale(double scale){Connect5.scale = scale;}
}
