package scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Connect5;

public class HowToPlay
{
	private Scene scene;
	private Button back;
	
	public HowToPlay()
	{
		back = new Button("back");
		back.setOnAction(e -> Connect5.toMainMenuScene());
		Text text = new Text("To start select a token from a hand of three"
				+ " and then select a column."
				+ " The rest of your hand would carry on to your next turn."
				+ " That token would then fall,"
				+ " either to the very bottom or top the next available"
				+ " slot. To obtain points place tokens with"
				+ " numbers on them. This will give you the number"
				+ " of points corresponding to that number. If you"
				+ " connect 4 tokens, the points on the four tokens"
				+ " would be counted again(it doubles). A connect 5"
				+ " can be viewed as two connect 4s. The middle three"
				+ " tokens triple in value, while the two tokens at the"
				+ " ends stays doubled. The game ends when someone connect"
				+ " 5 or if the board is full. The player with the most"
				+ " points wins(if the points are the same it’s a tie). ");
		text.setWrappingWidth(900);
		VBox container = new VBox(30);
		container.setAlignment(Pos.CENTER);
		
		Text title = new Text("How To Play");
		title.setStyle("-fx-font-size: 30pt; -fx-font-weight: bold;");
		
		container.getChildren().addAll(back, title ,text);
		scene = new Scene(container, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
