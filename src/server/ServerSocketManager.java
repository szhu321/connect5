package server;

import java.net.Socket;

public class ServerSocketManager extends SocketManager
{
	private ServerSocketMaster master;
	
	public ServerSocketManager(SocketMaster master, Socket socket)
	{
		super(master, socket);
		this.master = (ServerSocketMaster) getMaster();
	}
	
	public synchronized void manageCommand(int command)
	{
		switch(command)
		{
		case MESSAGE: readMessage(); break;
		case SEND_MOVE: readMove();
		}
		setWaiting(false);
	}
}
