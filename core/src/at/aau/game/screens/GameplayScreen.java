package at.aau.game.screens;

import at.aau.game.GameConstants;
import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameplayScreen extends ScreenAdapter {
   
	private final SpriteBatch batch;
	public final OrthographicCamera cam;
	public PixieSmack parentGame;
	private boolean pause = false;

	Texture backgroundImage;
	BitmapFont menuFont;
	World world;
	String[] menuStrings = { GameConstants.NEW_GAME, GameConstants.RESUME_GAME, "Credits", "Exit" };
	int currentMenuItem = 0;

	// float offsetLeft = PixieSmack.GAME_WIDTH / 8, offsetTop = PixieSmack.GAME_WIDTH / 8, offsetY = PixieSmack.GAME_HEIGHT / 8;

	public GameplayScreen(PixieSmack game) {
		this.parentGame = game;
		this.world = new World(this);

		backgroundImage = parentGame.getAssetManager().get("gameplay/bg-forest.png");
		menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
		menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();
		Gdx.input.setCursorCatched(true);
		
	}

	@Override
	public void render(float delta) {
		this.parentGame.alreadyIngame = true;
		world.gameplayScreen.parentGame.getSoundManager().playEvent("gameMusic");
		if (pause) {
			return;
		}
		handleInput();
		// camera:
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		world.update(delta);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// draw bgImage ...
		batch.draw(backgroundImage, 0, 0, PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);
		batch.end();
		world.render(delta);

		int x, y;
		x = Gdx.input.getX();
		y = Gdx.input.getY();

		if (x < 0) {
			Gdx.input.setCursorPosition(0, y);
		} else if (x > PixieSmack.MENU_GAME_WIDTH) {
			Gdx.input.setCursorPosition((int) PixieSmack.MENU_GAME_WIDTH, y);
		}

		if (y < 0) {
			Gdx.input.setCursorPosition(x, 0);
		} else if (y > PixieSmack.MENU_GAME_HEIGHT) {
			Gdx.input.setCursorPosition(x, (int) PixieSmack.MENU_GAME_HEIGHT);
		}
	}

	public void show() {
		this.pause = false;
		parentGame.getSoundManager().resumeEvent("gameMusic");
		Gdx.input.setCursorCatched(true);
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // JUST
			this.pause = true;
			parentGame.getSoundManager().pauseEvent("gameMusic");
			Gdx.input.setCursorCatched(false);
			this.world.gameplayScreen.parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
			parentGame.getSoundManager().playEvent("blip");
		}

		if (Gdx.input.justTouched()) {
			Vector3 unprojected = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
			Vector2 touchPixelCoords = PixieSmack.worldToPixel(new Vector2(unprojected.x, unprojected.y));
			world.touch(touchPixelCoords);
		}
	}
}
