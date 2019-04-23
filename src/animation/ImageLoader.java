package animation;

import game.GameConstants;
import javafx.scene.image.Image;
import main.Connect5;

/**
 * This class will load all required images into the game
 * to lessen the load later on. Call the static method initImages()
 * before the start of the game.
 * @author Sheng Wei
 *	
 */
public class ImageLoader implements GameConstants
{
	public static Image RED_BLANK;
	public static Image BLACK_BLANK;
	public static Image EMPTY_TILE;
	
	public static void initImages()
	{
		RED_BLANK = new Image("file:resources/images/tile_red.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		BLACK_BLANK = new Image("file:resources/images/tile_black.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		EMPTY_TILE = new Image("file:resources/images/tile_empty.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
	}
	
	/**
	 * Gets the image with the specified infomation.
	 * @param player Either player1 or player2.
	 * @param number The number of the token. 0, 1, 2, or 3.
	 * @return The specified image.
	 */
	public static Image getTokenImagePlayer(int player, int number)
	{
		Image result;
		if(player == PLAYER1)
		{
				
			result = RED_BLANK;
		}
		else
		{

			result = BLACK_BLANK;
		}
		return result;
	}
}
