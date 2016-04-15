package at.aau.game;

import java.awt.Toolkit;

/**
 * 
 * @author Kevin-Laptop
 *
 */
public class GameConstants {
	public static final float VIEWPORT_HEIGHT_IN_METER = 20;
	public static final float VIEWPORT_WIDTH_IN_METER = 20 / ((float) GameConstants.SCREEN_HEIGHT_IN_PIXEL / (float) GameConstants.SCREEN_WIDTH_IN_PIXEL);
	public static final float PIXEL_TO_METER = 1024;
	public static final int SCREEN_HEIGHT_IN_PIXEL = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int SCREEN_WIDTH_IN_PIXEL = Toolkit.getDefaultToolkit().getScreenSize().width;

	public static final String FAIRY_SPRITE_PATH = "gameplay/fairy.png";
	public static final float FAIRY_SPAWN_THRESHOLD = 2f;
	public static final float FAIRY_MAX_X = 1150f;
	public static final float FAIRY_MAX_Y = 150f;

	public static final float FAIRY_MAX_X_OFFSET = 100f;
	public static final float FAIRY_MAX_Y_OFFSET = 100f;
}
