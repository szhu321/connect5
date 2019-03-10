package manage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
	
	
	public ServerPage()
	{
		setUpNodes();
		setUpScene();
	}
	
	public void setUpNodes()
	{
		//creating the text fields
		ipTf = new TextField();
		portTf = new TextField();
		ipTf.setPromptText("Enter ip");
		portTf.setPromptText("Enter port");
		
		infoTxt = new Text();
		
		connectBtn = new Button("Join");
		connectBtn.setOnAction(e -> connectOnClick());
	}
	
	public void setUpScene()
	{
		root = new GridPane();
		root.setVgap(20);
		root.setHgap(5);
		
		scene = new Scene(root, Connect5.SCREEN_WIDTH, Connect5.SCREEN_HEIGHT);
		
		root.add(new Label("Port Number:"), 0, 0);
		root.add(new Label("IP address:"), 0, 1);
		root.add(portTf, 1, 0);
		root.add(ipTf, 1, 1);
	}
	
	public void connectOnClick()
	{
		infoTxt.setText("Connecting to server...");
		try
		{
			//creates a socket and trys to connect to server.
			connectBtn.setDisable(true); //disable the button when hit.
			Socket socket = new Socket(ipTf.getText(), Integer.parseInt(portTf.getText()));
			
			infoTxt.setText("Successfully connected");
			
			//todo: runs the server game.
		}
		catch (NumberFormatException e1) {
			infoTxt.setText("Incorrect Input");
			e1.printStackTrace();
		} catch (UnknownHostException e1) {
			infoTxt.setText("Unknow host");
			e1.printStackTrace();
		} catch (IOException e1) {
			infoTxt.setText("Connection Failed");
			e1.printStackTrace();
		} finally {
			connectBtn.setDisable(false); //enable the button when it fails to connect.
		}
	}
	
	public Scene getScene()
	{
		return scene;
	}
}
