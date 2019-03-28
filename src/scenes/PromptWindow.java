package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PromptWindow
{
	private static boolean answer;
	
	public static boolean openYesNoWindow(String message)
	{
		Stage window = new Stage();
		Text text = new Text(message);
		
		//creating the buttons.
		Button yes = new Button("Yes");
		yes.setOnAction(e ->
		{
			answer = true;
			window.close();
		});
		
		Button no = new Button("No");
		no.setOnAction(e ->
		{
			answer = false;
			window.close();
		});
		
		VBox container = new VBox(10);
		HBox btnContainer = new HBox(30);
		btnContainer.getChildren().addAll(yes, no);
		container.getChildren().addAll(text, btnContainer);
		
		Scene scene = new Scene(container ,200, 100);
		window.setScene(scene);
		window.setOnCloseRequest(e -> answer = false);
		window.showAndWait();
		
		
		return answer;
	}
}
