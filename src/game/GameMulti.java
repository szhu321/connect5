package game;

import java.net.Socket;

import main.Connect5;
import server.ClientSocketManager;
import server.ClientSocketMaster;
import server.ServerConstants;
import server.SocketManager;
import server.SocketMaster;

public class GameMulti extends Game implements ClientSocketMaster, ServerConstants
{
	private ClientSocketManager socketManager;
	private boolean gameOn = true;
	
	public GameMulti(Socket socket, int playerNum)
	{
		super(playerNum);
		myTurn = (playerNum == PLAYER1) ? true:false;//sets the turn.
//		if(myTurn)
//			Connect5.getGameScene().print("System", "You go first!");
//		else
//			Connect5.getGameScene().print("System", "Waiting for opponent's move...");
		socketManager = new ClientSocketManager(this, socket);
		getPlayerPile().depopulateHand();//the server will provied the tokens.
		startTokenAsker();
	}
	
	public void startTokenAsker()
	{
		new Thread(() -> {
			while(gameOn)
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
				socketManager.sendMove(tk, col);
				Connect5.getGameScene().print("System", "Waiting for Opponent...");
				return tk;
			}
		}
		return null;
	}
	
	public Token placeToken(Token tk, int col)
	{
		if(getGameBoard().placeToken(tk, col))
		{
			return tk;
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
	public void receiveMessage(SocketManager source, String message)
	{
		Connect5.getGameScene().print(message);
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
		if(status == CONTINUE)
		{
			swapTurn();
			Connect5.getGameScene().print("System","It's your turn.");
		}
		else
			Connect5.getGameScene().gameOverMulti(status);
	}

	@Override
	public void manageDisconnect(SocketManager source)
	{
		gameOn = false;
		Connect5.getGameScene().print("System", "Player Disconnected, Game Ended");
	}

	@Override
	public void receiveMove(SocketManager source, Token tk, int col)
	{
		Connect5.getGameScene().serverPlaceToken(tk, col);
	}

}
