package server;

public interface ServerConstants
{
	//server to client commands
	public static final int MESSAGE = 8;
	public static final int SEND_TOKEN = 10;
	public static final int SEND_MOVE = 12;
	public static final int ASK_FOR_TOKEN = 19;
	
	
	public static final int CONTINUE_STATUS = 3;
	public static final int PLAYER1_WIN_STATUS = 1;
	public static final int PLAYER2_WIN_STATUS = 2;
	public static final int TIED = 0;
}
