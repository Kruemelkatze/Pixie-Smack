package at.aau.game.screens;

import at.aau.game.GameConstants;
import at.aau.game.PixieSmack;
import at.aau.game.ScreenManager;
import at.aau.game.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreditsScreen extends ScreenAdapter {
	private final SpriteBatch batch;
	private final OrthographicCamera cam;
	private PixieSmack parentGame;
	//Music menuMusic;
	Texture backgroundImage, gradientTop, gradientBottom;
	BitmapFont creditsFont;

	String[] credits = ("Credits\n Pr0grammers: \nFabian Sch0ber \nKevin Herkt \nBernhard Nitsch \nChristian Hribernik \nFrederik Platter \n\n Art:\nNatascha Rauscher\nAlice Eberhard\n\n Musik:\nPeter Hafele\nMathias Lux\n\n F00d Supply\n Management:\nSara W0gatai\n\nGdxGameSkelet0n\nby Mathias Lux\n" + "All assets are\n public d0main\n")
			.split("\\n");
	private float moveY;

	public CreditsScreen(PixieSmack game) {
		this.parentGame = game;

		backgroundImage = parentGame.getAssetManager().get("menu/menu_background_empty.png");
		gradientTop = parentGame.getAssetManager().get("credits/gradient_top.png");
		gradientBottom = parentGame.getAssetManager().get("credits/gradient_bottom.png");

		creditsFont = parentGame.getAssetManager().get("menu/Ravie_42.fnt");

		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmack.MENU_GAME_WIDTH, PixieSmack.MENU_GAME_HEIGHT);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();
		
		this.parentGame.getSoundManager().playEvent(GameConstants.INTRO_MUSIC);
		//menuMusic = Gdx.audio.newMusic(Gdx.files.internal(GameConstants.MUSIC_INTRO));
		//menuMusic.setLooping(true);
		//menuMusic.play();
	}

	@Override
	public void render(float delta) {
		moveY += delta * 100;
		handleInput();
		// camera:
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// draw bgImage
		batch.draw(backgroundImage, 0, 0, PixieSmack.MENU_GAME_WIDTH, PixieSmack.MENU_GAME_HEIGHT);

		// draw moving text:
		for (int i = 0; i < credits.length; i++) {
			creditsFont.draw(batch, credits[i], PixieSmack.MENU_GAME_WIDTH / 6, moveY - i * creditsFont.getLineHeight() * 1.5f);
		}

		// draw gradient
		//batch.draw(gradientBottom, 0, 0, PixieSmack.MENU_GAME_HEIGHT, gradientBottom.getHeight());
		//batch.draw(gradientTop, 0, PixieSmack.MENU_GAME_HEIGHT - gradientTop.getHeight(), PixieSmack.MENU_GAME_HEIGHT, gradientTop.getHeight());

		batch.end();
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
			//SoundManager.stopMusic();
			parentGame.getScreenManager().setCurrentState(ScreenManager.ScreenState.Menu);
		}
	}

}
