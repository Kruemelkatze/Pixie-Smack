package at.aau.game.basic.screens;

import at.aau.game.BasicConstants;
import at.aau.game.ImageConstants;
import at.aau.game.PixieSmackGame;
import at.aau.game.basic.managers.ScreenEnum;
import at.aau.game.basic.managers.ScreenManager;
import at.aau.game.basic.managers.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class HighscoreScreen extends AbstractScreenAdapter {
	// Scores auf größe 5 begrenzen!
	public String[] scores = new String[5];

	// Music menuMusic;

	Texture backgroundImage;
	BitmapFont highscoreFont;
	BitmapFont highscoreFontEntries;
	String highscore = "Hall Of Fame";

	float offsetLeft = PixieSmackGame.MENU_GAME_WIDTH / 6, offsetTop = PixieSmackGame.MENU_GAME_WIDTH / 20, offsetY = PixieSmackGame.MENU_GAME_HEIGHT / 8;

	public HighscoreScreen(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		super(gdxAssetManager, soundManager, screenManager);
		backgroundImage = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "menu_background_empty.png");
		highscoreFont = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "Ravie_72.fnt");
		highscoreFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		highscoreFontEntries = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "Ravie_42.fnt");
		highscoreFontEntries.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		scores[0] = "nie gespielt";
		scores[1] = scores[2] = scores[3] = scores[4] = "";
		// scores gets highscores from file;
		final Preferences prefs = Gdx.app.getPreferences("Highscores");
		if (prefs.contains("highScore1")) {
			scores[0] = String.valueOf(prefs.getInteger("highScore1"));
		}
		if (prefs.contains("highScore2")) {
			scores[1] = String.valueOf(prefs.getInteger("highScore2"));
		}
		if (prefs.contains("highScore3")) {
			scores[2] = String.valueOf(prefs.getInteger("highScore3"));
		}
		if (prefs.contains("highScore4")) {
			scores[3] = String.valueOf(prefs.getInteger("highScore4"));
		}
		if (prefs.contains("highScore5")) {
			scores[4] = String.valueOf(prefs.getInteger("highScore5"));
		}
	}

	@Override
	public void render(@SuppressWarnings("unused") float delta) {
		handleInput();
		// camera:
		cam.update();
		AbstractScreenAdapter.batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AbstractScreenAdapter.batch.begin();
		AbstractScreenAdapter.batch.draw(backgroundImage, 0, 0, PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		highscoreFont.setColor(BasicConstants.COLOR_PINK);
		highscoreFont.draw(AbstractScreenAdapter.batch, highscore, offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop);

		// draw String- scores ...
		for (int i = 0; i < scores.length; i++) {
			highscoreFontEntries.setColor(BasicConstants.COLOR_PINK);
			highscoreFontEntries.draw(AbstractScreenAdapter.batch, scores[i], offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - i * offsetY - 120);
		}
		AbstractScreenAdapter.batch.end();
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
			// SoundManager.stopMusic();
			screenManager.setCurrentState(ScreenEnum.Menu);
		}
	}
}