package at.aau.game;

/**
 * @author Kevin-Laptop
 */
public class GameConstants {
	public static final String FAIRY_SPRITE_PATH = "gameplay/fairy.png";
	public static final String BIG_FAIRY_SPRITE_PATH_LEFT = "gameplay/bigpixie-left-anim.png";
	public static final String BIG_FAIRY_SPRITE_PATH_RIGHT = "gameplay/bigpixie-right-anim.png";
	public static final String MAD_BIG_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN = "gameplay/pixie-spec-mad-left-upsidedown.png";
	public static final String MAD_BIG_FAIRY_SPRITE_PATH_LEFT = "gameplay/pixie-spec-mad-left-anim.png";
	public static final String MAD_BIG_FAIRY_SPRITE_PATH_RIGHT = "gameplay/pixie-spec-mad-right-anim.png";

	public static final String EVIL_FAIRY_SPRITE_PATH_LEFT = "gameplay/pixie-evil-left-anim.png";
	public static final String EVIL_FAIRY_SPRITE_PATH_RIGHT = "gameplay/pixie-evil-right-anim.png";

	public static final String MAD_FAIRY_SPRITE_PATH_LEFT = "gameplay/obj-pixie_mad_left.png";
	public static final String MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN = "gameplay/obj-pixie_mad_left_upsidedown.png";
	public static final String EVIL_MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN = "gameplay/evil_fairy_mad_left_upsidedown.png";
	public static final String PLAYER_SPRITE_PATH = "gameplay/korb-anim.png";

	public static final String DAMAGED_BIG_FAIRY_SPRITE_PATH_LEFT = "gameplay/pixie-spec-mad-left-anim.png";
	public static final String DAMAGED_BIG_FAIRY_SPRITE_PATH_RIGHT = "gameplay/pixie-spec-mad-right-anim.png";

	public static final float BIG_FAIRY_SPAWN_THRESHOLD = 9f;
	public static final float BAD_FAIRY_SPAWN_THRESHOLD = 3f;
	public static final float FAIRY_MIN_X = PixieSmackGame.MENU_GAME_WIDTH * 0.2f;
	public static final float FAIRY_MIN_Y = PixieSmackGame.MENU_GAME_HEIGHT * 0.4f;
	public static final float FAIRY_MAX_X = PixieSmackGame.MENU_GAME_WIDTH * 0.8f;
	public static final float FAIRY_MAX_Y = PixieSmackGame.MENU_GAME_HEIGHT * 0.8f;

	public static final float FAIRY_MAX_X_OFFSET = 80f;
	public static final float FAIRY_MAX_Y_OFFSET = 100f;
	public static final float FAIRY_SPAWN_THRESHOLD = 1f;

	public static final float BAD_FAIRY_SMACK_CHANGE = -3;
	public static final float BIG_FAIRY_SMACK_CHANGE = 3;

	public static final float BIG_FAIRY_TIME_PLUS = 3f;
	public static final float BAD_FAIRY_TIME_MINUS = 2f;

	public static final float SMACKER_REACH = 70;

	public static final float SMACK_REGENERATION_TIME = 0.7f;
	public static final int SMACK_LIMIT = 13;

	public static final float TIMEOUT = 30;

	public static final int MAX_FAIRIES = 10;
	public static final int MAX_BAD_FAIRIES = 5;
	public static final int MAX_BIG_FAIRIES = 3;
	public static final int BIG_FAIRIES_HEALTH = 5;

	public static final String NEW_GAME = "New Game";
	public static final String RESUME_GAME = "Resume Game";

	public static final String SOUND_DEAD_BIG_FAIRY = "sfx/bfrx_wav/Cast.wav";
	public static final String SOUND_BAD_COLLECT = "sfx/hit.wav";
	public static final String MUSIC_INTRO = "music/introMusic.mp3";
	public static final String MUSIC_INGAME = "music/gameMusic.mp3";
	public static final String GAME_MUSIC = "gameMusic";
	public static final String INTRO_MUSIC = "introMusic";

	public static final float MAX_FEEDBACK_TIME = Float.MAX_VALUE;

	public static final int SmackSoundsCount = 5;
	public static final int CollectSoundsCount = 2;
}
