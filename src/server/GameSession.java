package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import game.Game;
import game.GameConstants;
import game.GameLocal;
import game.GameMulti;
import game.Token;


public class GameSession implements Runnable, GameConstants, SocketMaster
{
	private static int sessionNum = 0;
	private int session;
	
	private Socket player1;
	private Socket player2;
	
	private SocketManager p1SManager;
	private SocketManager p2SManager;
	
	private Game game;
	
	public GameSession(Socket player1, Socket player2)
	{
		game = new GameLocal();//Decided to use gamelocal.
		session = ++sessionNum;
		this.player1 = player1;
		this.player2 = player2;
		shufflePlayers();
		p1SManager = new SocketManager(this, player1);
		p2SManager = new SocketManager(this, player2);
		sendPlayerRoles();
	}
	
	private void sendPlayerRoles()
	{
		
		
	}

	private void shufflePlayers()
	{
		if(Math.random() > .5)
		{
			Socket temp = player1;
			player1 = player2;
			player2 = temp;
		}
	}

	@Override
	public void run()
	{
		
	}	
	
	public int getSession()
	{
		return session;
	}
	
	private boolean areClientsConnected()
	{
		return !player1.isClosed() && !player2.isClosed(); 
	}

	@Override
	public void receiveMessage(SocketManager source, String message)
	{
		Server.write(source.toString() + message);
		
	}

	@Override
	public void receiveToken(SocketManager source, Token tk)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveStatus(SocketManager source, int status)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveRole(SocketManager source, int playerNum) {
		//Do Nothing.
	}
}
