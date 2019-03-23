package server;

import game.Token;

public interface SocketMaster
{
	public void receiveMessage(SocketManager source, String message);
	public void receiveMove(SocketManager source, Token tk);
	
	
	public void manageDisconnect(SocketManager source);
}
