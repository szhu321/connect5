package scenes;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import game.GameMulti;
import javafx.application.Platform;
import scenes.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Connect5;

public class ServerLounge
{
	private Scene scene;
	
	private VBox root;
	private Text text;
	
	private Socket server;
	
	public ServerLounge(Socket server)
	{
		this.server = server;
		setUpNodes();
		setUpScene();
		waitForGameStart();
	}
	
	private void waitForGameStart()
	{
		new Thread(() -> 
		{
			try {
				//waiting for game to start.
				int playerNumber = new DataInputStream(server.getInputStream()).readInt();
				Platform.runLater(() -> 
				{
					Connect5.createNewManager(new GameMulti(server, playerNumber));
					System.out.println("Manager Created");
					Connect5.toManagerScene();
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	private void setUpNodes()
	{
		root = new VBox();
		text = new Text("Waiting for another player");
	}

	private void setUpScene()
	{
		root.getChildren().add(text);
		scene = new Scene(root, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
	}

	public Scene getScene()
	{
		return scene;
	}
}
