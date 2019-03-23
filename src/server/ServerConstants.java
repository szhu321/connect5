package server;

public interface ServerConstants
{
	//server to client commands
	public static final int MESSAGE = 8;
	public static final int SEND_ROLE = 9;
	public static final int SEND_TOKEN = 10;
	public static final int SEND_MOVE = 12;
	public static final int ASK_FOR_TOKEN = 19;
	public static final int CONTINUE_STATUS = 5;
	
	public static final int CONTINUE = 3;
	public static final int WIN = 1;
	public static final int LOSE = 2;
	public static final int TIED = 0;
}
