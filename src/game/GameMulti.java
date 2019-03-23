package game;

import java.net.Socket;

import server.ClientSocketManager;
import server.ClientSocketMaster;
import server.SocketManager;
import server.SocketMaster;

public class GameMulti extends Game implements ClientSocketMaster
{
	private ClientSocketManager socketManager;
	
	public GameMulti(Socket socket, int playerNum)
	{
		super(playerNum);
		myTurn = (playerNum == PLAYER1) ? true:false;//sets the turn.
		socketManager = new ClientSocketManager(this, socket);
		getPlayerPile().depopulateHand();//the server will provied the tokens.
		startTokenAsker();
	}
	
	public void startTokenAsker()
	{
		new Thread(() -> {
			while(true)
			{
				System.out.println("TICK");
				if(!getPlayerPile().isHandFull())
				{
					socketManager.askForToken();
					System.out.println("Client Asking for token");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@Override
	public Token placeToken(int handIdx, int col)
	{
		if(myTurn)
		{
			Token tk = getPlayerPile().getToken(handIdx);
			if(getGameBoard().placeToken(tk, col))
			{
				swapTurn();
				return tk;
			}
		}
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
	public void receiveToken(SocketManager source, Token tk)
	{
		System.out.println("Added new token");
		getPlayerPile().addTokenToHand(tk);
	}

	@Override
	public void receiveStatus(SocketManager source, int status)
	{
		
		
	}

	@Override
	public void manageDisconnect(SocketManager source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveMove(SocketManager source, Token tk) {
		// TODO Auto-generated method stub
		
	}

}
