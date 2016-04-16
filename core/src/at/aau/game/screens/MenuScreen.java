package at.aau.game.screens;

import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends ScreenAdapter {
	private final SpriteBatch batch;
	private final OrthographicCamera cam;
	private PixieSmack parentGame;

	Texture backgroundImage;
	BitmapFont menuFont;

	String[] menuStrings = { "Play", "Highscore", "Credits", "Exit" };
	int currentMenuItem = 0;

	float offsetLeft = PixieSmack.MENU_GAME_WIDTH / 8, offsetTop = PixieSmack.MENU_GAME_WIDTH / 8, offsetY = PixieSmack.MENU_GAME_HEIGHT / 8;

	public MenuScreen(PixieSmack game) {
		this.parentGame = game;

		backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
		menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
		menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmack.MENU_GAME_WIDTH, PixieSmack.MENU_GAME_HEIGHT);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		handleInput();
		// camera:
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// draw bgImage ...
		batch.draw(backgroundImage, 0, 0, PixieSmack.MENU_GAME_WIDTH, PixieSmack.MENU_GAME_HEIGHT);
		// draw Strings ...
		for (int i = 0; i < menuStrings.length; i++) {
			if (i == currentMenuItem)
				menuFont.setColor(0.2f, 1f, 0.2f, 1f);
			else
				menuFont.setColor(0.2f, 0.2f, 1f, 1f);
			menuFont.draw(batch, menuStrings[i], offsetLeft, PixieSmack.MENU_GAME_HEIGHT - offsetTop - i * offsetY);
		}
		batch.end();
	}

	private void handleInput() {
		// keys ...
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			currentMenuItem = (currentMenuItem + 1) % menuStrings.length;
			parentGame.getSoundManager().playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			currentMenuItem = (currentMenuItem - 1) % menuStrings.length;
			parentGame.getSoundManager().playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (menuStrings[currentMenuItem].equals("Exit")) {
				Gdx.app.exit();
				parentGame.getSoundManager().playEvent("explode");
			} else if (menuStrings[currentMenuItem].equals("Credits")) {
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
			} else if (menuStrings[currentMenuItem].equals("Play")){
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Game);
			} else if (menuStrings[currentMenuItem].equals("Highscore")){
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Highscore);
			}
		}
		// touch
		if (Gdx.input.justTouched()) {
			Vector3 touchWorldCoords = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
			// find the menu item ..
			for (int i = 0; i < menuStrings.length; i++) {
				if (touchWorldCoords.x > offsetLeft) {
					float pos = PixieSmack.MENU_GAME_HEIGHT - offsetTop - i * offsetY;
					if (touchWorldCoords.y < pos && touchWorldCoords.y > pos - menuFont.getLineHeight()) {
						// it's there
						if (menuStrings[i].equals("Exit")) {
							Gdx.app.exit();
						} else if (menuStrings[i].equals("Play")) {
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Game);
						} else if (menuStrings[i].equals("Credits")) {
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
						} else if (menuStrings[i].equals("Highscore")) {
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Highscore);
						}
					}
				}

			}
		}
	}

}
