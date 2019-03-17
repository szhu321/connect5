package server;

import game.Token;

public interface SocketMaster
{
	public void giveMessage(String message);
	public void giveToken(Token tk);
	public void giveStatus(int status);
}
