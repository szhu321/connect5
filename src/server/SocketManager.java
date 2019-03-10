package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.application.Platform;

public class SocketManager implements ServerConstants
{
	private Socket mainSocket;
	private boolean connected;
	
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	public SocketManager(Socket socket)
	{
		mainSocket = socket;
		connected = true;
		createConnections();
		listenForInput();
	}
	
	public void createConnections()
	{
		try {
			dataIn = new DataInputStream(mainSocket.getInputStream());
			dataOut = new DataOutputStream(mainSocket.getOutputStream());
			objIn = new ObjectInputStream(mainSocket.getInputStream());
			objOut = new ObjectOutputStream(mainSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listenForInput()
	{
		new Thread(() -> 
		{
			try {
				while(connected)
				{
					int command = dataIn.readInt();
					Platform.runLater(() -> manageCommand(command));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	
	//only one command at a time.
	public synchronized void manageCommand(int command)
	{
		switch(command)
		{
		case PING_PONG: break;
		case MESSAGE: break;
		case CONTINUE_STATUS: break;
		case SERVER_TOKEN: break;
		}
	}
}
