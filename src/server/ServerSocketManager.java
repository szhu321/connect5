package server;

import java.io.IOException;
import java.net.Socket;

import game.Token;

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
		Server.write("Command: " + command);
		switch(command)
		{
		case MESSAGE: readMessage(); break;
		case ASK_FOR_TOKEN: sendToken(); break;
		case SEND_MOVE: readMove();
		}
		setWaiting(false);
	}

	private void sendToken()
	{
		Server.write("Client asked for token.");
		Token tk = master.transferToken(this);
		try {
			getObjOut().writeInt(SEND_TOKEN);
			getObjOut().writeInt(tk.getPlayer());
			getObjOut().writeInt(tk.getPoints());
			getObjOut().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
