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


public class GameSession implements Runnable, GameConstants, ServerSocketMaster
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
		sendPlayerRoles(); //sending this will tell the players to start the game.
		p1SManager = new ServerSocketManager(this, player1);
		p2SManager = new ServerSocketManager(this, player2);
		
	}
	
	private void sendPlayerRoles()
	{
		try {
			new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);
			new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void receiveMessage(SocketManager source, String message)
	{
		Server.write(source.toString() + message);
		
	}

	@Override
	public void manageDisconnect(SocketManager source)
	{
		Server.write(source.toString() + " has disconnected.");
		endSession();
	}
	
	@Override
	public void receiveMove(SocketManager source, Token tk)
	{
		
		
	}
	
	private void endSession()
	{
		p1SManager.close();
		p2SManager.close();
	}

	
}
