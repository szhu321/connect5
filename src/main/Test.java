package main;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.GameBoard;
import game.NumberToken;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import game.GameConstants;

public class Test
{
	public static void main(String[] args)
	{	
		FileInputStream fileInput;
		try
		{
			fileInput = new FileInputStream("data.dat");
			DataInputStream input = new DataInputStream(fileInput);
			
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//finally will only not execute for uncaught exception and system.exit.
}