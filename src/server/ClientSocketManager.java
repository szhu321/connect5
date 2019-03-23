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
		}
		setWaiting(false);
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
	
	public void askForToken()
	{
		if(getObjOut() == null)
			return;
		try {
			getObjOut().writeInt(ASK_FOR_TOKEN);
			getObjOut().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
