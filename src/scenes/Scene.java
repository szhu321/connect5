package scenes;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class Scene extends javafx.scene.Scene
{
	private static Stage window;
	
	public Scene(Parent arg0)
	{
		super(arg0);
		if(window == null)
			window = new Stage();
	}
	
	public Scene(Parent parent, double width, double height)
	{
		super(parent, width, height);
	}
	
	public void showScene()
	{
		window.setScene(this);
		window.show();
	}
	
	/**
	 * Adding styles to the scene.
	 * @param args - file locations for the stylesheets.
	 */
	public void addStylesheets(String... args)
	{
		for(String arg: args)
			super.getStylesheets().add(arg);
	}
	
	public static void setWindow(Stage window)
	{
		Scene.window = window;
	}
	
	public static Stage getStage()
	{
		return window;
	}
}
