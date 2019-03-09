package manage;

import game.GameSingle;
import javafx.stage.Stage;

public class GameManagerSingle extends Manager
{
	
	public GameManagerSingle(Stage window)
	{
		super(window, new GameSingle());
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}
	
}
