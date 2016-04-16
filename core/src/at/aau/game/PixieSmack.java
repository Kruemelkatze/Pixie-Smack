package at.aau.game;

import java.awt.Toolkit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class PixieSmack extends ApplicationAdapter {
	public static int SmackSoundsCount = 5;
	public static int CollectSoundsCount = 2;
	private AssetManager assMan;
	private ScreenManager screenManager;
	private SoundManager soundManager;
	private Animator animator;
	public boolean alreadyIngame = false;

	// gives the original size for all screen working with the scaling
	// orthographic camera
	// set in DesktopLauncher to any resolution and it will be scaled
	// automatically.
	public static final float MENU_GAME_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 1.25f;
	public static final float MENU_GAME_WIDTH =  MENU_GAME_HEIGHT / 0.75f;

	// public static final float GAME_WIDTH = 10; // 1080;
	// public static final float GAME_HEIGHT = 7; // 720;

	@Override
	public void create() {
		screenManager = new ScreenManager(this);
		soundManager = new SoundManager(this);
		animator = new Animator(this);

		// LOAD ASSETS HERE ...
		// Loading screen will last until the last one is loaded.
		assMan = new AssetManager();
		// for the menu
		assMan.load("menu/Ravie_42.fnt", BitmapFont.class);
		assMan.load("menu/Ravie_72.fnt", BitmapFont.class);
		assMan.load("menu/menu_background.png", Texture.class);
		assMan.load("menu/menu_background-old.jpg", Texture.class);
		assMan.load("menu/menu_background_empty.png", Texture.class);
		assMan.load("gameplay/bg-forest.png", Texture.class);
		assMan.load("gameplay/bg-forest-border.png", Texture.class);
		// for the credits
		assMan.load("credits/gradient_top.png", Texture.class);
		assMan.load("credits/gradient_bottom.png", Texture.class);
		// for the sounds
		assMan.load("sfx/blip.wav", Sound.class);
		assMan.load("sfx/explosion.wav", Sound.class);
		assMan.load("sfx/hit.wav", Sound.class);
		assMan.load("sfx/jump.wav", Sound.class);
		assMan.load("sfx/laser.wav", Sound.class);
		assMan.load("sfx/pickup.wav", Sound.class);
		assMan.load("sfx/powerup.wav", Sound.class);
		assMan.load("sfx/powerup.wav", Sound.class);
		assMan.load(GameConstants.SOUND_DEAD_BIG_FAIRY, Sound.class);

		assMan.load(GameConstants.MUSIC_INGAME, Music.class);
		assMan.load(GameConstants.MUSIC_INTRO, Music.class);

		// Smacking sounds
		for (int i = 1; i <= PixieSmack.SmackSoundsCount; i++) {
			assMan.load("sfx/smack" + i + ".wav", Sound.class);
		}
		// Collect sounds
		for (int i = 1; i <= PixieSmack.CollectSoundsCount; i++) {
			assMan.load("sfx/collect" + i + ".wav", Sound.class);
		}

		// Entities
		assMan.load("gameplay/spritesheet.png", Texture.class);
		assMan.load("gameplay/smacker.png", Texture.class);
		assMan.load("gameplay/smacker-anim.png", Texture.class);
		assMan.load("gameplay/smack0.png", Texture.class);
		assMan.load("gameplay/smack1.png", Texture.class);
		assMan.load("gameplay/smack2.png", Texture.class);
		assMan.load("gameplay/smack3.png", Texture.class);
		assMan.load("gameplay/smack4.png", Texture.class);
		assMan.load("gameplay/smack5.png", Texture.class);
		assMan.load("gameplay/smack6.png", Texture.class);
		assMan.load("gameplay/smack7.png", Texture.class);
		assMan.load("gameplay/smack8.png", Texture.class);
		assMan.load("gameplay/smack9.png", Texture.class);
		assMan.load("gameplay/smack10.png", Texture.class);
		assMan.load("gameplay/smack11.png", Texture.class);
		assMan.load("gameplay/smack12.png", Texture.class);
		assMan.load("gameplay/smack13.png", Texture.class);
		assMan.load("gameplay/koerbchen_up.png", Texture.class);
		assMan.load("gameplay/koerbchen_down.png", Texture.class);
		assMan.load("gameplay/koerbchen_left.png", Texture.class);
		assMan.load("gameplay/koerbchen_right.png", Texture.class);
		assMan.load("gameplay/koerbchen_idle.png", Texture.class);
		assMan.load("gameplay/obj_staub_sprit.png", Texture.class);
		assMan.load("gameplay/obj_staub_sprit_bad.png", Texture.class);
		assMan.load("gameplay/obj_staub_sprit_spec.png", Texture.class);
		assMan.load("gameplay/fairysmack.png", Texture.class);
		assMan.load("gameplay/pixie-left-anim.png", Texture.class);
		assMan.load("gameplay/pixie-right-anim.png", Texture.class);
		assMan.load(GameConstants.FAIRY_SPRITE_PATH, Texture.class);
		assMan.load(GameConstants.BIG_FAIRY_SPRITE_PATH_LEFT, Texture.class);
		assMan.load(GameConstants.BIG_FAIRY_SPRITE_PATH_RIGHT, Texture.class);
		assMan.load(GameConstants.MAD_BIG_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, Texture.class);
		assMan.load(GameConstants.MAD_BIG_FAIRY_SPRITE_PATH_LEFT, Texture.class);
		assMan.load(GameConstants.MAD_BIG_FAIRY_SPRITE_PATH_RIGHT, Texture.class);
		
		assMan.load(GameConstants.EVIL_FAIRY_SPRITE_PATH_LEFT, Texture.class);
		assMan.load(GameConstants.EVIL_FAIRY_SPRITE_PATH_RIGHT, Texture.class);
		
		assMan.load(GameConstants.EVIL_MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, Texture.class);
		assMan.load(GameConstants.EVIL_FAIRY_SPRITE_PATH_RIGHT, Texture.class);

		assMan.load(GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT, Texture.class);
		assMan.load(GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, Texture.class);
		
		assMan.load(GameConstants.PLAYER_SPRITE_PATH, Texture.class);
		assMan.load("gameplay/timer-pink.png", Texture.class);

		assMan.load("gameplay/randomparticle", ParticleEffect.class);
		assMan.load("gameplay/randomparticle-bad", ParticleEffect.class);
		assMan.load("gameplay/randomparticle-good", ParticleEffect.class);
	}

	@Override
	public void render() {
		screenManager.getCurrentScreen().render(Gdx.graphics.getDeltaTime());
	}

	public AssetManager getAssetManager() {
		return assMan;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public Animator getAnimator() {
		return animator;
	}

}
