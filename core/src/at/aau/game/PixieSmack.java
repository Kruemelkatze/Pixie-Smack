package at.aau.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class PixieSmack extends ApplicationAdapter {
    private AssetManager assMan;
    private ScreenManager screenManager;
    private SoundManager soundManager;
    private Animator animator;

    // gives the original size for all screen working with the scaling orthographic camera
    // set in DesktopLauncher to any resolution and it will be scaled automatically.
    public static final float MENU_GAME_WIDTH = 1000; // 1080;
    public static final float MENU_GAME_HEIGHT = 700; // 720;
    public static final float GAME_WIDTH = 10; // 1080;
    public static final float GAME_HEIGHT = 7; // 720;

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
        assMan.load("menu/menu_background.jpg", Texture.class);
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

        // Entities
        assMan.load("gameplay/spritesheet.png", Texture.class);
        assMan.load("gameplay/movingAnimation_Down.png", Texture.class);
        assMan.load("gameplay/obj_staub_mock.png", Texture.class);
        assMan.load(GameConstants.FAIRY_SPRITE_PATH, Texture.class);
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

    public static Vector2 pixelToWorld(Vector2 pixel) {
        return new Vector2(GAME_WIDTH / MENU_GAME_WIDTH * pixel.x, GAME_HEIGHT / MENU_GAME_HEIGHT * pixel.y);

    }


    public static Vector2 worldToPixel(Vector2 world) {
        return new Vector2(MENU_GAME_WIDTH / GAME_WIDTH * world.x, MENU_GAME_HEIGHT / GAME_HEIGHT * world.y);


    }
}