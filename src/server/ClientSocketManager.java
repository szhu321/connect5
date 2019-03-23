package server;

import java.net.Socket;

public class ClientSocketManager extends SocketManager
{
	public ClientSocketManager(SocketMaster master, Socket socket)
	{
		super(master, socket);
	}
}
