package game;

public class GameSingle extends Game
{
	private TokenPile cpuPile;
	
	public GameSingle()
	{
		cpuPile = new TokenPile(PLAYER2);
	}

	@Override
	public boolean placeToken(int handIdx, int col) {
		// TODO Auto-generated method stub
		return false;
	}
}
