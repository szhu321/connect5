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
	
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	private Thread thread;
	
	//The object that uses this class.
	private SocketMaster master;
	
	public SocketManager(SocketMaster master, Socket socket)
	{
		mainSocket = socket;
		this.master = master;	
		waiting = true;
		createThread();
		createConnections();
	}
	
	public void createConnections()
	{
		System.out.println("Creating connections");
		new Thread(() -> 
		{
			try {
				objOut = new ObjectOutputStream(mainSocket.getOutputStream());
				objOut.flush();
				objIn = new ObjectInputStream(mainSocket.getInputStream());
				connected = true;
				listensForInput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();	
	}
	
	//creates a thread that listens for input
	public void createThread()
	{
		thread = new Thread(() -> 
		{
			try {
				while(connected)
				{
					System.out.println("listening for input");
					int command = objIn.readInt();
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
	}
	
	public void listensForInput()
	{
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
			player = objIn.readInt();
			number = objIn.readInt();
			Token tk = Token.createNumberToken(player, number);
			master.receiveToken(this, tk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readStatus()
	{
		try {
			int status = objIn.readInt();
			master.receiveStatus(this, status);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readMessage()
	{
		try {
			String message = (String)objIn.readObject();
			//System.out.println(message);
			master.receiveMessage(this, message);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends the connected socket a message.
	 * @param message The given message.
	 */
	public void sendMessage(String message)
	{
		if(connected)
		{
			try {
				objOut.writeInt(MESSAGE);
				objOut.flush();
				objOut.writeObject(message);
				objOut.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String toString()
	{
		return mainSocket.getInetAddress().toString();
	}
}
