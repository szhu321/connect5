	package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import game.GameConstants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Server extends Application implements GameConstants
{
	private Stage window;
	private Scene scene;
	private VBox root;
	private TextField tf;
	private static TextArea ta;
	
	private ServerSocket serverSocket;
	private int numPlayers = 0;
	private int numOfSessions = 1;
	
	public static final int PORT = 8100;
	private Thread serverThread;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception
	{
		window = arg0;
		window.setTitle("Connect 5 Server");
		//creating the container.
		root = new VBox();
		
		tf = new TextField(); //command box
		tf.setEditable(false);
		ta = new TextArea(); //display box
		root.getChildren().addAll(ta, tf); //added to container
		
		scene = new Scene(root, 700, 500);
		window.setScene(scene);
		window.show();
		
		//creates the thread for the serverSocket.
		PlayerConnector pc = new PlayerConnector();
		serverThread = new Thread(pc);
		serverThread.start();
		window.setOnCloseRequest(e ->
		{
			System.exit(0);
		});
		
	}
	
	
	
	private class PlayerConnector extends Thread
	{
		@Override
		public void run()
		{
			//prepares the connection to the players.
			
			try
			{
				//created a server socket to accept connections.
				serverSocket = new ServerSocket(PORT);
				//runLater runs the command on the JavaFx Thread.
				write("Server started at port " + PORT + " on "+ new Date());
				while(true)
				{
					Socket[] sockets = new Socket[2];
					//wait for more connections if the player count is too low.
					while(numPlayers < 2)
					{
						write("Waiting for players to connect to session " + numOfSessions + " (" + numPlayers + "/2)");
						sockets[numPlayers] = serverSocket.accept(); //waiting for connections.
						String playerIP = sockets[numPlayers].getInetAddress().toString(); //gets the player's IP
						
						write(playerIP + " has connected.");
						numPlayers++; //increment the number of players.
					}
					write("All players have connected.\n Starting Session " + numOfSessions++ + "...");
					
					numPlayers = 0;
					new Thread(new GameSession(sockets[1], sockets[0])).start();
				}
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * prints given text on screen
	 * @param str - The string that is going to be printed.
	 */
	public static void write(String str)
	{
		Platform.runLater(() -> ta.appendText(str + "\n"));
	}
}
