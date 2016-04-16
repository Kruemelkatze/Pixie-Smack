package at.aau.game.screens;

import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HighscoreScreen extends ScreenAdapter{
	private final SpriteBatch batch;
	private final OrthographicCamera cam;
	private PixieSmack parentGame;
	//Scores auf größe 5 begrenzen!
	public String[] scores = {"nie gespielt"};
	
	Texture backgroundImage;
	BitmapFont highscoreFont;
	BitmapFont highscoreFontEntries;
	String highscore = "Highscores";
	
	float offsetLeft = PixieSmack.MENU_GAME_WIDTH / 6,
			offsetTop = PixieSmack.MENU_GAME_WIDTH / 20,
			offsetY = PixieSmack.MENU_GAME_HEIGHT / 8;

	
	public HighscoreScreen(PixieSmack game) {
		this.parentGame = game;
		
		backgroundImage = parentGame.getAssetManager().get("menu/menu_background.jpg");
		highscoreFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
		highscoreFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmack.MENU_GAME_WIDTH, PixieSmack.MENU_GAME_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
		highscoreFontEntries = parentGame.getAssetManager().get("menu/Ravie_42.fnt");
		highscoreFontEntries.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		
		
		//scores gets highscores from file;
		Preferences prefs = Gdx.app.getPreferences("Highscores");
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
		// draw heading
		highscoreFont.setColor(0.2f, 0.2f, 1f, 1f);
		highscoreFont.draw(batch, highscore, offsetLeft, PixieSmack.MENU_GAME_HEIGHT - offsetTop);
		
		
		// draw String- scores ...
		for (int i = 0; i < scores.length; i++) {
			highscoreFontEntries.setColor(0.2f, 0.2f, 1f, 1f);
			highscoreFontEntries.draw(batch, scores[i], offsetLeft, PixieSmack.MENU_GAME_HEIGHT - offsetTop - i * offsetY -120);
		}
		batch.end();
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
			parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
		}
	}

	
	

}
