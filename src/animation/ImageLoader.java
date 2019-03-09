package animation;

import javafx.scene.image.Image;
import main.Connect5;

public class ImageLoader
{
	public static Image RED_BLANK;
	public static Image BLACK_BLANK;
	public static Image EMPTY_TILE;
	
	public static Image RED_1;
	public static Image BLACK_1;
	
	public static Image RED_2;
	public static Image BLACK_2;
	
	public static Image RED_3;
	public static Image BLACK_3;
	
	public static void initImages()
	{
		RED_BLANK = new Image("file:resources/images/tile_red.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		BLACK_BLANK = new Image("file:resources/images/tile_black.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		EMPTY_TILE = new Image("file:resources/images/tile_empty.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		
		RED_1 = new Image("file:resources/images/tile_red_1.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		BLACK_1 = new Image("file:resources/images/tile_black_1.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		
		RED_2 = new Image("file:resources/images/tile_red_2.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		BLACK_2 = new Image("file:resources/images/tile_black_2.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		
		RED_3 = new Image("file:resources/images/tile_red_3.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		BLACK_3 = new Image("file:resources/images/tile_black_3.png", 100 * Connect5.getScale(), 100 * Connect5.getScale(), false, true);
		
	}
}
