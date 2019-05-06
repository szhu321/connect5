package scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PromptWindow
{
	private static boolean answer;
	
	/**
	 * Opens a pop-up window that will block all events to 
	 * another window until this window is closed.
	 * @param message - The message to be displayed on the popup window.
	 * @return true if the user selects yes, false otherwise.
	 */
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
		
		//setting button minsize
		yes.setStyle("-fx-min-width: 200px");
		no.setStyle("-fx-min-width: 200px");
		
		VBox container = new VBox(10);
		container.setAlignment(Pos.CENTER);
		HBox btnContainer = new HBox(30);
		btnContainer.getChildren().addAll(yes, no);
		container.getChildren().addAll(text, btnContainer);
		
		Scene scene = new Scene(container);
		Style.addTextStyle(scene);
		Style.addButtonStyle(scene);
		
		window.setScene(scene);
		window.setOnCloseRequest(e -> answer = false);
		window.centerOnScreen();
		window.initModality(Modality.APPLICATION_MODAL);
		window.showAndWait();
		
		return answer;
	}
}
