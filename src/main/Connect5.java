package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Connect5 extends Application
{
	private static double scale = 1;
	private Stage window;
	private Scene scene;

	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage st)
	{
		window = st;
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
