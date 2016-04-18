package at.aau.game.basic.screens;

import at.aau.game.GameConstants;
import at.aau.game.ImageConstants;
import at.aau.game.PixieSmackGame;
import at.aau.game.basic.managers.ScreenManager;
import at.aau.game.basic.managers.SoundManager;
import at.aau.game.mechanic.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayScreen extends AbstractScreenAdapter {
	public boolean pause = false;
	private final Texture backgroundImage;
	private final BitmapFont menuFont;
	private final World world;

	public PlayScreen(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		super(gdxAssetManager, soundManager, screenManager);
		this.world = new World(this, AbstractScreenAdapter.batch);
		this.pause = false;
		this.screenManager.setAlreadyIngame(true);
		backgroundImage = gdxAssetManager.get("gameplay/bg-forest.png");
		menuFont = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "Ravie_72.fnt");
		menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		// Create camera that projects the desktop onto the actual screen size.
		// cam = new OrthographicCamera(PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		cam = new OrthographicCamera();
		// final float width = PixieSmackGame.MENU_GAME_WIDTH;
		// final float height = PixieSmackGame.MENU_GAME_HEIGHT;
		// Viewport viewport = new FillViewport(width, height, cam);
		// viewport.apply();

		cam.viewportHeight = PixieSmackGame.MENU_GAME_HEIGHT;
		cam.viewportWidth = PixieSmackGame.MENU_GAME_WIDTH;
		cam.position.set(PixieSmackGame.MENU_GAME_WIDTH / 2f, PixieSmackGame.MENU_GAME_HEIGHT / 2f, 0);
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render(float delta) {
		cam.update();
		this.soundManager.playEvent(GameConstants.GAME_MUSIC);
		if (pause) {
			return;
		}
		handleInput();
		cam.update();
		AbstractScreenAdapter.batch.setProjectionMatrix(cam.combined);

		world.update(delta);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AbstractScreenAdapter.batch.begin();
		AbstractScreenAdapter.batch.draw(backgroundImage, 0, 0, PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		AbstractScreenAdapter.batch.end();
		world.render(delta);

		int x, y;
		x = Gdx.input.getX();
		y = Gdx.input.getY();
		// Vector3 unprojected = world.gameplayScreen.cam.unproject(new Vector3(x, y, 1));
		if (x < 0) {
			Gdx.input.setCursorPosition(0, y);
		} else if (x > PixieSmackGame.MENU_GAME_WIDTH) {
			Gdx.input.setCursorPosition((int) PixieSmackGame.MENU_GAME_WIDTH, y);
		}

		if (y < 0) {
			Gdx.input.setCursorPosition(x, 0);
		} else if (y > PixieSmackGame.MENU_GAME_HEIGHT) {
			Gdx.input.setCursorPosition(x, (int) PixieSmackGame.MENU_GAME_HEIGHT);
		}
	}

	@Override
	public void show() {
		this.pause = false;
		// soundManager.resumeEvent(GameConstants.GAME_MUSIC);
		Gdx.input.setCursorCatched(true);
	}

	private void handleInput() {
		world.keyInput();
		final Vector3 unprojected = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
		final Vector2 touchPixelCoords = new Vector2(unprojected.x, unprojected.y);
		if (Gdx.input.justTouched()) {
			world.touchWithSmacker(touchPixelCoords);
		}
		world.mouseOver(touchPixelCoords);
	}
}