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
	private static double scale = .7;
	private Stage window;
	private Manager manager;

	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage st)
	{
		window = st;
		window.setTitle("Connect 5");
		ImageLoader.initImages();
		manager = new Manager(window, GameConstants.LOCAL_GAME);
		window.show();
		window.setResizable(false);
		window.setOnCloseRequest(e -> System.exit(0));
	}
	
	
	public static double getScale(){return scale;}
	public static void setScale(double scale){Connect5.scale = scale;}
}
