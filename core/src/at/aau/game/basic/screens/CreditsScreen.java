package at.aau.game.basic.screens;

import at.aau.game.GameConstants;
import at.aau.game.ImageConstants;
import at.aau.game.PixieSmackGame;
import at.aau.game.basic.managers.ScreenEnum;
import at.aau.game.basic.managers.ScreenManager;
import at.aau.game.basic.managers.SoundManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class CreditsScreen extends AbstractScreenAdapter {
	private final Texture backgroundImage;
	private final BitmapFont creditsFont;

	private final String[] credits = ("Credits\n Pr0grammers: \nFabian Sch0ber \nKevin Herkt \nBernhard Nitsch \nChristian Hribernik \nFrederik Platter \n\n Art:\nNatascha Rauscher\nAlice Eberhard\n\n Musik:\nPeter Hafele\nMathias Lux\n\n F00d Supply\n Management:\nSara W0gatai\n\nGdxGameSkelet0n\nby Mathias Lux\n"
			+ "All assets are\n public d0main\n").split("\\n");
	private float moveY;

	public CreditsScreen(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		super(gdxAssetManager, soundManager, screenManager);
		backgroundImage = gdxAssetManager.get(ImageConstants.MENU_BACKGROUND_EMPTY);
		creditsFont = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "Ravie_42.fnt");

		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		soundManager.playEvent(GameConstants.INTRO_MUSIC);
	}

	@Override
	public void render(float delta) {
		moveY += delta * 100;
		handleInput();
		// camera:
		cam.update();
		AbstractScreenAdapter.batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AbstractScreenAdapter.batch.begin();
		// draw bgImage
		AbstractScreenAdapter.batch.draw(backgroundImage, 0, 0, PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);

		// draw moving text:
		for (int i = 0; i < credits.length; i++) {
			creditsFont.draw(AbstractScreenAdapter.batch, credits[i], PixieSmackGame.MENU_GAME_WIDTH / 6, moveY - i * creditsFont.getLineHeight() * 1.5f);
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