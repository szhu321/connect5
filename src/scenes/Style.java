package scenes;

import java.io.File;

import javafx.scene.Node;
import javafx.scene.Scene;

public class Style
{
	public static void addTextStyle(Scene scene)
	{
		scene.getStylesheets().add("scenes/text.css");
	}
	
	public static void addButtonStyle(Scene scene)
	{
		scene.getStylesheets().add("scenes/button.css");
	}
}
