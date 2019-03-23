package server;

import game.Token;

public interface ServerSocketMaster extends SocketMaster
{
	public Token transferToken(SocketManager source);
}
