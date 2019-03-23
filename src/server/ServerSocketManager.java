package server;

import java.net.Socket;

public class ServerSocketManager extends SocketManager
{
	public ServerSocketManager(SocketMaster master, Socket socket)
	{
		super(master, socket);
	}
}
