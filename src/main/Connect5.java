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
		manager = new GameManagerLocal(window);
		window.show();
		window.setResizable(false);
		window.setOnCloseRequest(e -> System.exit(0));
		
		//Testing
//		GameBoard gb = manager.getGame().getGameBoard();
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER1, 3), 3);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER1, 3), 4);
//		manager.getGame().calcPoints();
//		manager.updateGUI();
//		
//		System.out.println(manager.getGame().getPlayerPile());
//		System.out.println(manager);
		
		//System.exit(0);
	}
	
	
	public static double getScale(){return scale;}
	public static void setScale(double scale){Connect5.scale = scale;}
}
