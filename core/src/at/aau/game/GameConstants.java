package at.aau.game;

import java.awt.Toolkit;

import com.badlogic.gdx.graphics.Color;

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
    public static final String BIG_FAIRY_SPRITE_PATH_LEFT = "gameplay/bigpixie-left-anim.png";
    public static final String BIG_FAIRY_SPRITE_PATH_RIGHT = "gameplay/bigpixie-right-anim.png";
    
    public static final String EVIL_FAIRY_SPRITE_PATH_LEFT = "gameplay/pixie-evil-left-anim.png";
    public static final String EVIL_FAIRY_SPRITE_PATH_RIGHT = "gameplay/pixie-evil-right-anim.png";
    
    public static final String MAD_FAIRY_SPRITE_PATH_LEFT = "gameplay/obj-pixie_mad_left.png";
    public static final String MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN = "gameplay/obj-pixie_mad_left_upsidedown.png";
    public static final String PLAYER_SPRITE_PATH = "gameplay/korb-anim.png";
    public static final float FAIRY_SPAWN_THRESHOLD = 1f;
    public static final float BIG_FAIRY_SPAWN_THRESHOLD = 9f;
    public static final float BAD_FAIRY_SPAWN_THRESHOLD = 3f;
    public static final float FAIRY_MIN_X = PixieSmack.MENU_GAME_WIDTH * 0.2f;
    public static final float FAIRY_MIN_Y = PixieSmack.MENU_GAME_HEIGHT * 0.2f;
    public static final float FAIRY_MAX_X = PixieSmack.MENU_GAME_WIDTH * 0.8f;
    public static final float FAIRY_MAX_Y = PixieSmack.MENU_GAME_HEIGHT * 0.8f;

    public static final float FAIRY_MAX_X_OFFSET = 80f;
    public static final float FAIRY_MAX_Y_OFFSET = 100f;

    public static final float BAD_FAIRY_SMACK_CHANGE = -3;
    public static final float BIG_FAIRY_SMACK_CHANGE = 3;
    
    public static final float SMACKER_REACH = 70;
    
    public static final float SMACK_REGENERATION_TIME = 0.8f;
    public static final int SMACK_LIMIT = 13;

    public static final float TIMEOUT = 30;

    public static final int MAX_FAIRIES = 10;
    public static final int MAX_BAD_FAIRIES = 5;
    public static final int MAX_BIG_FAIRIES = 3;
    
    public static final String NEW_GAME = "New Game";
    public static final String RESUME_GAME = "Resume Game";
    
    public static final Color COLOR_PINK = new Color(1f, 130f / 255f, 1.0f, 1.0f);
    
    public static final String SOUND_DEAD_BIG_FAIRY = "sfx/bfrx_wav/Cast.wav";
    public static final String MUSIC_INTRO = "sfx/introMusic.wav";
    public static final String MUSIC_INGAME = "sfx/gameMusic.wav";
	public static final String GAME_MUSIC = "gameMusic";
	public static final String INRO_MUSIC = "introMusic";
}
