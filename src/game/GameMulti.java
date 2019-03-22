package game;

import java.net.Socket;

import server.SocketManager;
import server.SocketMaster;

public class GameMulti extends Game implements SocketMaster
{
	private TokenPile player1Pile; //alias for the token pile in the super class;
	private SocketManager socketManager;
	
	
	public GameMulti(Socket socket)
	{
		player1Pile = getPlayerPile();
		//System.out.println("creating game Multi");
		socketManager = new SocketManager(this, socket);
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
		return player1Pile;
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

}
