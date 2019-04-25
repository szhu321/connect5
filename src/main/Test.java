package main;

import game.GameBoard;
import game.NumberToken;
import game.GameConstants;

public class Test
{
	public static void main(String[] args)
	{	
		try
		{
			throw new NullPointerException();
		}catch(ArithmeticException e)
		{
			System.out.println(1);
		}finally
		{
			System.out.println(2);
		}
	}
	
	public static void a()
	{
		try
		{
			throw new RuntimeException();
			
		}catch(NullPointerException e)
		{
			System.out.println(5);
			
		}finally
		{
			System.out.println(7);
		}
	}
	//finally will only not execute for uncaught exception and system.exit.
}
