package at.aau.game.screens;

import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
	public String[] scores = {"Nummer 1", "Nummer 2", "Nummer 3"};
	
	Music menuMusic;
	
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
			menuMusic.stop();
			parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
		}
	}

	
	

}
