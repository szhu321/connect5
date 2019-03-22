package server;

import java.io.IOException;
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
	
	/**
	 * Creates stream connections and starts the thread.
	 */
	private void createConnections()
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
	
	/**
	 * creates a thread that listens for input
	 */
	private void createThread()
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
	
	/**
	 * starts the thread.
	 */
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
		case SEND_ROLE: readRole();
		}
		waiting = false;
	}

	private void readRole()
	{
		try {
			int playerNum = objIn.readInt();
			master.receiveRole(this, playerNum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/**
	 * Tells the client if they are player1 or player2.
	 * @param playerNumber GameConstant.PLAYER1 or GameConstant.PLAYER2
	 */
	public void sendRole(int playerNumber)
	{
		if(connected)
		{
			try {
				objOut.writeInt(SEND_ROLE);//Sends the type of command.
				objOut.flush();
				objOut.writeInt(playerNumber);
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
