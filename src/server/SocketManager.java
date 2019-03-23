package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.Token;
import javafx.application.Platform;

public abstract class SocketManager implements ServerConstants
{
	private Socket mainSocket;
	private boolean connected;
	
	private boolean waiting;
	
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	private Thread thread;
	
	//The object that uses this class.
	private SocketMaster master;
	
	/**
	 * Creates a socketManager that will work with a socket.
	 * @param master Link to the the class that uses this class.
	 * @param socket The provided socket.
	 */
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
				master.manageDisconnect(this);
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
	public abstract void manageCommand(int command);

	protected void readMove()
	{
		try {
			int player = objIn.readInt();
			int number = objIn.readInt();
			int col = objIn.readInt();
			Token tk = Token.createNumberToken(player, number);
			master.receiveMove(this, tk, col);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMove(Token tk, int col)
	{
		try {
			objOut.writeInt(SEND_MOVE);//command
			objOut.flush();
			objOut.writeInt(tk.getPlayer());
			objOut.writeInt(tk.getPoints());
			objOut.writeInt(col);
			objOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void readMessage()
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
	
	public void close()
	{
		try {
			mainSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//getters and setters.
	public boolean isConnected() {return connected;}
	public boolean isWaiting() {return waiting;}
	public ObjectInputStream getObjIn() {return objIn;}
	public ObjectOutputStream getObjOut() {return objOut;}
	public SocketMaster getMaster() {return master;}
	public void setWaiting(boolean waiting) {this.waiting = waiting;}
	
	public String toString()
	{
		return mainSocket.getInetAddress().toString();
	}
}
