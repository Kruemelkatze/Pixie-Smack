package at.aau.game.screens;

import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoadingScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private PixieSmack parentGame;
    private Texture loadingSheet;
    private TextureRegion[] loadingFrames;

    private int animationFrame = 0;
    private float animationFrameShownFor = 0.05f; // how long is each frame shown ..
    private float animationFrameShownAlready = 0f;

    public LoadingScreen(PixieSmack game) {
        this.parentGame = game;
        // this is the only asset not loaded by the AssetManager.
        loadingSheet = new Texture(Gdx.files.internal("loading/preloader_180x40.png"));
        loadingFrames = TextureRegion.split(loadingSheet, 180, 40)[0];

        // Create camera taht projects the desktop onto the actual screen size.
        cam = new OrthographicCamera(PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (parentGame.getAssetManager().update()) {
            parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
        }
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        // determine the current frame:
        animationFrameShownAlready += delta;
        if (animationFrameShownAlready > animationFrameShownFor) {
            animationFrame = (animationFrame + 1) % loadingFrames.length;
            animationFrameShownAlready = 0f;
        }

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(loadingFrames[animationFrame], PixieSmack.GAME_WIDTH/2 - loadingFrames[animationFrame].getRegionWidth()/2, PixieSmack.GAME_HEIGHT/2-loadingFrames[animationFrame].getRegionHeight()/2);
        batch.end();
    }


}
