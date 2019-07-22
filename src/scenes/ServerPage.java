package scenes;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.geometry.Pos;
import scenes.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Connect5;

public class ServerPage
{
	private Scene scene;
	private GridPane root;
	
	private TextField ipTf;
	private TextField portTf;
	private Button connectBtn;
	private Text infoTxt;
	private Button backBtn;
	
	public ServerPage()
	{
		setUpNodes();
		setUpScene();
	}
	
	public void setUpNodes()
	{
		//creating the text fields
		ipTf = new TextField();
		ipTf.setText("localhost");
		portTf = new TextField();
		portTf.setText("8100");
		ipTf.setPromptText("Enter ip");
		portTf.setPromptText("Enter port");
		
		infoTxt = new Text();
		
		connectBtn = new Button("Join");
		connectBtn.setOnAction(e -> connectOnClick());
		backBtn = new Button("Back");
		backBtn.setOnAction(e -> Connect5.toMainMenuScene());
	}
	
	public void setUpScene()
	{
		root = new GridPane();
		root.setVgap(35);
		root.setHgap(30);
		
		//added a wrapper to center the gridPane.
		VBox container = new VBox();
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(root);
		
		
		scene = new Scene(container, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
		
		//System.out.println(container.getWidth());
		
		root.add(new Label("Port Number:"), 0, 0);
		root.add(new Label("IP address:"), 0, 1);
		root.add(portTf, 1, 0);
		root.add(ipTf, 1, 1);
		root.add(connectBtn, 1, 2);
		root.add(infoTxt, 1, 3);
		root.add(backBtn, 0, 2);
		root.setMaxWidth(500);//set maxwidth so it can be centered properly.
	}
	
	public void connectOnClick()
	{
		infoTxt.setText("Connecting to server...");
		new Thread(() -> 
		{
			try
			{
				//creates a socket and trys to connect to server.
				connectBtn.setDisable(true); //disable the button when hit.
				Socket socket = new Socket(ipTf.getText(), Integer.parseInt(portTf.getText()));
				
				infoTxt.setText("Successfully connected");
				//moves to server lounge.
				Platform.runLater(() -> Connect5.toServerLounge(socket));
				
			}
			catch (NumberFormatException e1) {
				infoTxt.setText("Incorrect Input");
				//e1.printStackTrace();
			} catch (UnknownHostException e1) {
				infoTxt.setText("Unknow host");
				//e1.printStackTrace();
			} catch (IOException e1) {
				infoTxt.setText("Connection Failed");
				//e1.printStackTrace();
			} finally { 
				connectBtn.setDisable(false); //enable the button when it fails to connect.
			}
		}).start();
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
