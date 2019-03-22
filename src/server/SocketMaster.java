package server;

import game.Token;

public interface SocketMaster
{
	public void receiveMessage(SocketManager source, String message);
	public void receiveToken(SocketManager source, Token tk);
	public void receiveStatus(SocketManager source, int status);
	public void receiveRole(SocketManager source, int playerNum);
}
