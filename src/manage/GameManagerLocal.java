package manage;

import game.*;
import javafx.stage.Stage;

public class GameManagerLocal extends Manager
{

	public GameManagerLocal(Stage window)
	{
		super(window, new GameLocal());
	}

	@Override
	public void gameOver()
	{
		
	}
}
