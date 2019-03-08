package main;

import game.GameBoard;
import game.GameConstants;
import game.NumberToken;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manage.GameManager;
import manage.Manager;

public class Connect5 extends Application
{
	private static double scale = 1;
	private Stage window;

	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage st)
	{
		window = st;
		window.setTitle("Connect 5");
		Manager mg = new GameManager(window);
		//window.show();
		
		
		//Testing
//		GameBoard gb = mg.getGame().getGameBoard();
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER1, 3), 3);
//		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER1, 3), 4);
//		mg.getGame().calcPoints();
//		mg.updateScoreBox();
//		
//		System.out.println(mg);
//		gb.clearBoard();
		System.out.println(mg.getGame().getPlayerPile());
		
		System.exit(0);
	}
	
	public static double getScale()
	{
		return scale;
	}
	
	
	public static void setScale(double scale)
	{
		Connect5.scale = scale;
	}
}
