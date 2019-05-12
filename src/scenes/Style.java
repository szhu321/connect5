package scenes;

import java.io.File;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Style
{
	public static void addTextStyle(Scene scene)
	{
		scene.getStylesheets().add("file:resources/stylesheets/text.css");
	}
	
	public static void addButtonStyle(Scene scene)
	{
		scene.getStylesheets().add("file:resources/stylesheets/button.css");
	}
}
