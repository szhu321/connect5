package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import game.Game;
import game.GameConstants;
import game.GameMulti;


public class GameSession implements Runnable, GameConstants
{
	private static int sessionNum = 0;
	private int session;
	
	private Socket player1;
	private Socket player2;
	
	private DataOutputStream toPlayer1;
	private DataOutputStream toPlayer2;
	private DataInputStream fromPlayer1;
	private DataInputStream fromPlayer2;
	
	private Game game;
	
	public GameSession(Socket player1, Socket player2)
	{
		game = new GameMulti();
		session = ++sessionNum;
		this.player1 = player1;
		this.player2 = player2;
	}

	@Override
	public void run()
	{
		try 
		{
			//setting up the connections to the servers.
			toPlayer1 = new DataOutputStream(player1.getOutputStream());
			toPlayer2 = new DataOutputStream(player2.getOutputStream());
			fromPlayer1 = new DataInputStream(player1.getInputStream());
			fromPlayer2 = new DataInputStream(player2.getInputStream());
			
			toPlayer1.writeInt(1); //lets player1 know when to start.
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			Server.write("Session " + session + " Ended.");
		}
	}	
	
	public boolean areClientsConnected()
	{
		return !player1.isClosed() && !player2.isClosed(); 
	}
	
	public void sendMove(DataOutputStream player, int move)
	{
		try
		{
			player.writeInt(move);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
