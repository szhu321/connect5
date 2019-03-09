package animation;

import javafx.scene.image.Image;
import main.Connect5;

public class ImageLoader
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
	
	
}
