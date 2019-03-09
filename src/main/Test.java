package main;

import game.GameBoard;
import game.NumberToken;
import game.GameConstants;

public class Test
{
	public static void main(String[] args)
	{	
		GameBoard gb = new GameBoard();
		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER2, 3), 0);
		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER1, 3), 3);
		gb.placeToken(NumberToken.createNumberToken(GameConstants.PLAYER1, 3), 4);
		//gb.clearBoard();
		gb.removeToken(5, 5);
		System.out.println(gb);
	}
	
	
}
