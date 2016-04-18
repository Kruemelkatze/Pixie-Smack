package at.aau.game.basic.screens;

import at.aau.game.ImageConstants;
import at.aau.game.PixieSmackGame;
import at.aau.game.basic.managers.ScreenEnum;
import at.aau.game.basic.managers.ScreenManager;
import at.aau.game.basic.managers.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LoadingScreen extends AbstractScreenAdapter {
	private final Texture loadingSheet;
	private final TextureRegion[] loadingFrames;

	private int animationFrame = 0;
	private final float animationFrameShownFor = 0.05f; // how long is each frame shown ..
	private float animationFrameShownAlready = 0f;

	Texture backgroundImage;

	public LoadingScreen(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		super(gdxAssetManager, soundManager, screenManager);
		// this are the only asset not loaded by the AssetManager !!
		loadingSheet = new Texture(Gdx.files.internal("loadingScreen/preloader_180x40.png"));
		loadingFrames = TextureRegion.split(loadingSheet, 180, 40)[0];
		backgroundImage = new Texture(Gdx.files.internal(ImageConstants.MENU_BACKGROUND));

		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
	}

	@Override
	public void render(float delta) {
		if (gdxAssetManager.update()) {
			screenManager.setCurrentState(ScreenEnum.Menu);
		}
		// camera:
		cam.update();
		AbstractScreenAdapter.batch.setProjectionMatrix(cam.combined);

		// determine the current frame:
		animationFrameShownAlready += delta;
		if (animationFrameShownAlready > animationFrameShownFor) {
			animationFrame = (animationFrame + 1) % loadingFrames.length;
			animationFrameShownAlready = 0f;
		}

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AbstractScreenAdapter.batch.begin();
		AbstractScreenAdapter.batch.draw(backgroundImage, 0, 0, PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		AbstractScreenAdapter.batch.draw(loadingFrames[animationFrame],
				PixieSmackGame.MENU_GAME_WIDTH / 2 - loadingFrames[animationFrame].getRegionWidth() / 2, PixieSmackGame.MENU_GAME_HEIGHT / 2
				- loadingFrames[animationFrame].getRegionHeight() / 2);
		AbstractScreenAdapter.batch.end();
	}

}
