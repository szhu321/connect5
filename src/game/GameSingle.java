package game;

public class GameSingle extends Game
{
	private TokenPile cpuPile;
	private Thread brain;
	
	public GameSingle()
	{
		cpuPile = new TokenPile(PLAYER2);
		brain = new Thread(() -> 
		{
			//make move
			makeMove();
			
			//wait for next turn
		});
	}

	@Override
	public Token placeToken(int handIdx, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TokenPile getOnScreenTokenPile() {
		return getPlayerPile();
	}
	
	private void makeMove()
	{
		Token[] ATokens = cpuPile.getCurrentHandCopy();
		int[][] gameBoard = getGameBoard().getPlayerGrid();
		
		//random Ai
		
	}

	@Override
	public int getGameType()
	{
		return GameConstants.SINGLE_GAME;
	}
	
	public void close()
	{
		System.out.println("Single Game Closed");
	}
}
