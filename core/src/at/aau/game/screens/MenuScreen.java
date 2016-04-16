package at.aau.game.screens;

import at.aau.game.GameConstants;
import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;
import at.aau.game.SoundManager;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
	Music menuMusic;

	String[] menuStrings = { GameConstants.NEW_GAME, GameConstants.RESUME_GAME, "Highscore", "Credits", "Exit" };
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
		
		menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sfx/introMusic.wav"));
		menuMusic.setLooping(true);
		menuMusic.play();
		
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
		int offsetFactor = 0;
		for (int i = 0; i < menuStrings.length; i++) {
			if (i == currentMenuItem)
				menuFont.setColor(0.2f, 1f, 0.2f, 1f);
			else
				menuFont.setColor(0.2f, 0.2f, 1f, 1f);
			if (menuStrings[i].equals(GameConstants.RESUME_GAME) && !this.parentGame.alreadyIngame) {
				menuFont.setColor(0.3f, 0.3f, 0.3f, 1f);
				menuFont.draw(batch, menuStrings[i], offsetLeft, PixieSmack.MENU_GAME_HEIGHT - offsetTop - offsetFactor * offsetY);
				offsetFactor++;
			}
			else if (menuStrings[i].equals(GameConstants.RESUME_GAME) && this.parentGame.alreadyIngame) {
				menuFont.draw(batch, menuStrings[i], offsetLeft, PixieSmack.MENU_GAME_HEIGHT - offsetTop - offsetFactor * offsetY);
				offsetFactor++;
			}
			else if (!menuStrings[i].equals(GameConstants.RESUME_GAME)) {
				menuFont.draw(batch, menuStrings[i], offsetLeft, PixieSmack.MENU_GAME_HEIGHT - offsetTop - offsetFactor * offsetY);
				offsetFactor++;
			}
		}
		batch.end();
	}

	private void handleInput() {
		// keys ...
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && this.parentGame.alreadyIngame) { // JUST
			this.parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.ResumeGame);
			menuMusic.stop();
			parentGame.getSoundManager().playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			currentMenuItem = (currentMenuItem + 1) % menuStrings.length;
			if (currentMenuItem == 1 && !this.parentGame.alreadyIngame) {
				currentMenuItem++;
			}
			parentGame.getSoundManager().playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			currentMenuItem = (currentMenuItem - 1) % menuStrings.length;
			if (currentMenuItem == 1 && !this.parentGame.alreadyIngame) {
				currentMenuItem--;
			}
			if (currentMenuItem < 0) {
				currentMenuItem = 0;
			} else {
				parentGame.getSoundManager().playEvent("blip");
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (menuStrings[currentMenuItem].equals("Exit")) {
				Gdx.app.exit();
				parentGame.getSoundManager().playEvent("explode");
			} else if (menuStrings[currentMenuItem].equals("Credits")) {
				menuMusic.stop();
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
			} else if (menuStrings[currentMenuItem].equals(GameConstants.NEW_GAME)) {
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.NewGame);
				menuMusic.stop();
			} else if (menuStrings[currentMenuItem].equals(GameConstants.RESUME_GAME) && this.parentGame.alreadyIngame) {
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.ResumeGame);
				menuMusic.stop();
			} else if (menuStrings[currentMenuItem].equals("Highscore")) {
				parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Highscore);
				menuMusic.stop();
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
						} else if (menuStrings[i].equals(GameConstants.NEW_GAME)) {
							menuMusic.stop();
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.NewGame);
						} else if (menuStrings[i].equals(GameConstants.RESUME_GAME) && this.parentGame.alreadyIngame) {
							menuMusic.stop();
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.ResumeGame);
						} else if (menuStrings[i].equals("Credits")) {
							menuMusic.stop();
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Credits);
						} else if (menuStrings[i].equals("Highscore")) {
							menuMusic.stop();
							parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Highscore);
						}
					}
				}

			}
		}
	}

}
