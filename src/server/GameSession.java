package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import game.Game;
import game.GameConstants;
import game.GameLocal;
import game.GameMulti;
import game.GameServer;
import game.Token;


public class GameSession implements Runnable, GameConstants, ServerConstants, ServerSocketMaster
{
	private static int sessionNum = 0;
	private int session;
	
	private Socket player1;
	private Socket player2;
	
	private ServerSocketManager p1SManager;
	private ServerSocketManager p2SManager;
	
	private GameServer game;
	
	public GameSession(Socket player1, Socket player2)
	{
		game = new GameServer();//Decided to use gameServer.
		game.getPlayerPile().resetToEmpty();
		game.getPlayer2Pile().resetToEmpty();
		session = ++sessionNum;
		this.player1 = player1;
		this.player2 = player2;
		shufflePlayers();
		sendPlayerRoles(); //sending this will tell the players to start the game.
		p1SManager = new ServerSocketManager(this, this.player1);
		p2SManager = new ServerSocketManager(this, this.player2);
		
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
		if(source == p1SManager)
			p2SManager.sendMessage("Enemy: " + message);
		else
			p1SManager.sendMessage("Enemy: " + message);
	}

	@Override
	public void manageDisconnect(SocketManager source)
	{
		Server.write(source.toString() + " has disconnected.");
		endSession();
	}
	
	@Override
	public void receiveMove(SocketManager source, Token tk, int col)
	{
		game.placeToken(tk, col);
		if(source == p1SManager)
			p2SManager.sendMove(tk, col);
		else
			p1SManager.sendMove(tk, col);
		
		//checks to see if the game is over.
		if(game.isGameOver())
		{
			game.calcPoints();
			int p1 = game.getPlayer1Points();
			int p2 = game.getPlayer2Points();
			if(p1 == p2)
			{
				p2SManager.sendStatus(TIED);
				p1SManager.sendStatus(TIED);
			}
			else if(p1 > p2)
			{
				p2SManager.sendStatus(LOSE);
				p1SManager.sendStatus(WIN);
			}
			else
			{
				p2SManager.sendStatus(WIN);
				p1SManager.sendStatus(LOSE);
			}
		}
		else
		{
			if(source == p1SManager)
				p2SManager.sendStatus(CONTINUE);
			else
				p1SManager.sendStatus(CONTINUE);
		}
	}
	
	@Override
	public Token transferToken(SocketManager source)
	{
		Token tk;
		if(source == p1SManager)
			tk = game.getPlayerPile().popMasterList();
		else
			tk = game.getPlayer2Pile().popMasterList();
		return tk;
	}
	
	private void endSession()
	{
		p1SManager.close();
		p2SManager.close();
	}

	
}
