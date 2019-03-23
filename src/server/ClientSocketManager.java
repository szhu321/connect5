package server;

import java.io.IOException;
import java.net.Socket;

import game.Token;

public class ClientSocketManager extends SocketManager
{
	private ClientSocketMaster master;
	
	public ClientSocketManager(SocketMaster master, Socket socket)
	{
		super(master, socket);
		this.master = (ClientSocketMaster) getMaster();
	}
	
	public synchronized void manageCommand(int command)
	{
		switch(command)
		{
		case MESSAGE: readMessage(); break;
		case CONTINUE_STATUS: readStatus(); break;
		case SEND_TOKEN: readToken(); break;
		case SEND_MOVE: readMove(); break;
		case SEND_ROLE: readRole();
		}
		setWaiting(false);
	}
	
	private void readRole()
	{
		try {
			int playerNum = getObjIn().readInt();
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
			player = getObjIn().readInt();
			number = getObjIn().readInt();
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
			int status = getObjIn().readInt();
			master.receiveStatus(this, status);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
