package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.Token;
import javafx.application.Platform;

public class SocketManager implements ServerConstants
{
	private Socket mainSocket;
	private boolean connected;
	
	private boolean waiting;
	
	private DataInputStream dataIn;
	private DataOutputStream dataOut;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	private Thread thread;
	
	//The object that uses this class.
	private SocketMaster master;
	
	public SocketManager(SocketMaster master, Socket socket)
	{
		mainSocket = socket;
		this.master = master;
		connected = true;
		waiting = true;
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
	
	//creates a thread the listens for input
	public void listenForInput()
	{
		thread = new Thread(() -> 
		{
			try {
				while(connected)
				{
					int command = dataIn.readInt();
					waiting = true;
					Platform.runLater(() -> manageCommand(command));
					
					//waits for the command to continue reading.
					while(waiting)
					{
						Thread.sleep(5);
					}
				}
			} catch(IOException e)
			{
				e.printStackTrace();
				//connection error with this socket.
			} catch(InterruptedException inE)
			{
				inE.printStackTrace();
			}
		});
		thread.start();
	}
	
	//only one command at a time.
	public synchronized void manageCommand(int command)
	{
		switch(command)
		{
		case MESSAGE: readMessage(); break;
		case CONTINUE_STATUS: readStatus(); break;
		case SEND_TOKEN: readToken(); break;
		}
		waiting = false;
	}

	/**
	 * Read Player. Then read Color.
	 */
	private void readToken()
	{
		int player, number;
		try {
			player = dataIn.readInt();
			number = dataIn.readInt();
			Token tk = Token.createNumberToken(player, number);
			master.giveToken(tk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readStatus()
	{
		try {
			int status = dataIn.readInt();
			master.giveStatus(status);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readMessage()
	{
		try {
			String message = (String)objIn.readObject();
			System.out.println(message);
			master.giveMessage(message);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
