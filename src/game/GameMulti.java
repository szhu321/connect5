package game;

import java.net.Socket;

import server.ClientSocketManager;
import server.SocketManager;
import server.SocketMaster;

public class GameMulti extends Game implements SocketMaster
{
	private SocketManager socketManager;
	
	public GameMulti(Socket socket, int playerNum)
	{
		super(playerNum);
		socketManager = new ClientSocketManager(this, socket);
		//System.out.println("creating game Multi");
		System.out.println("Created SocketManager");
	}
	
	@Override
	public Token placeToken(int handIdx, int col)
	{
		
		return null;
	}

	@Override
	public TokenPile getOnScreenTokenPile()
	{
		return getPlayerPile();
	}

	@Override
	public int getGameType()
	{
		return GameConstants.ONLINE_GAME;
	}
	
	public void sendMessage(String message)
	{
		socketManager.sendMessage(message);
	}

	@Override
	public void receiveMessage(SocketManager source, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveToken(SocketManager source, Token tk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveStatus(SocketManager source, int status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveRole(SocketManager source, int playerNum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageDisconnect(SocketManager source) {
		// TODO Auto-generated method stub
		
	}

}
