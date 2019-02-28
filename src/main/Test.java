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
		
		System.out.println(gb);
	}
}
