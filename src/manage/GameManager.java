package manage;

import game.GameSingle;
import javafx.stage.Stage;

public class GameManager extends Manager
{
	
	public GameManager(Stage window)
	{
		super(window, new GameSingle());
	}

	@Override
	public int checkGameOver()
	{
		return 0;
		
	}
	
}
