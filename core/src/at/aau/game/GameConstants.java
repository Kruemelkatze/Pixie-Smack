package at.aau.game;

import java.awt.Toolkit;

/**
 * @author Kevin-Laptop
 */
public class GameConstants {
    public static final float VIEWPORT_HEIGHT_IN_METER = 20;
    public static final float VIEWPORT_WIDTH_IN_METER = 20 / ((float) GameConstants.SCREEN_HEIGHT_IN_PIXEL / (float) GameConstants.SCREEN_WIDTH_IN_PIXEL);
    public static final float PIXEL_TO_METER = 1024;
    public static final int SCREEN_HEIGHT_IN_PIXEL = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int SCREEN_WIDTH_IN_PIXEL = Toolkit.getDefaultToolkit().getScreenSize().width;

    public static final String FAIRY_SPRITE_PATH = "gameplay/fairy.png";
    public static final float FAIRY_SPAWN_THRESHOLD = 1f;
    public static final float FAIRY_MIN_X = PixieSmack.MENU_GAME_WIDTH * 0.2f;
    public static final float FAIRY_MIN_Y = PixieSmack.MENU_GAME_HEIGHT * 0.2f;
    public static final float FAIRY_MAX_X = PixieSmack.MENU_GAME_WIDTH * 0.8f;
    public static final float FAIRY_MAX_Y = PixieSmack.MENU_GAME_HEIGHT * 0.8f;

    public static final float FAIRY_MAX_X_OFFSET = 80f;
    public static final float FAIRY_MAX_Y_OFFSET = 100f;

    public static final float SMACKER_REACH = 70;

    public static final float TIMEOUT = 10;

    public static final int MAX_FAIRIES = 10;
}
