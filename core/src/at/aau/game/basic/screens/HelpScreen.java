package at.aau.game.basic.screens;

import at.aau.game.BasicConstants;
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

public class HelpScreen extends AbstractScreenAdapter {
	private final Texture backgroundImage, goodPixiePic, badPixiePic, pixiePic;
	private final BitmapFont menuFont;
	private final BitmapFont describe;
	private final String helpString = "Help";
	private final String collect = "Hurry up\n and catch fairy dust!";
	private final String normalPixieText = "+10 Pt";
	private final String badPixieText = "-10 Pt, -2 Sec,\n -3 Smacks";
	private final String goodPixieText = "+60 Pt, +3 Sec,\n +3 Smacks";

	float offsetLeft = PixieSmackGame.MENU_GAME_WIDTH / 6, offsetTop = PixieSmackGame.MENU_GAME_WIDTH / 20, offsetY = PixieSmackGame.MENU_GAME_HEIGHT / 8;

	public HelpScreen(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		super(gdxAssetManager, soundManager, screenManager);
		backgroundImage = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "menu_background_empty.png");
		menuFont = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "Ravie_72.fnt");
		menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		menuFont.setColor(BasicConstants.COLOR_PINK);

		describe = gdxAssetManager.get(ImageConstants.FOLDER_GENERAL + "Ravie_42.fnt");
		describe.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		describe.setColor(BasicConstants.COLOR_PINK);

		cam = new OrthographicCamera(PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		badPixiePic = gdxAssetManager.get("gameplay/bad_pixie.png");
		goodPixiePic = gdxAssetManager.get("gameplay/spec_pixie.png");
		pixiePic = gdxAssetManager.get("gameplay/pixie.png");

	}

	@Override
	public void render(@SuppressWarnings("unused") float delta) {
		handleInput();
		cam.update();
		AbstractScreenAdapter.batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		AbstractScreenAdapter.batch.begin();
		// draw bgImage ...
		AbstractScreenAdapter.batch.draw(backgroundImage, 0, 0, PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		// Help
		menuFont.draw(AbstractScreenAdapter.batch, helpString, offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop);
		// Hurry up and catch fairy dust.....
		describe.draw(AbstractScreenAdapter.batch, collect, offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - 0 * offsetY - 120);

		// Pixie Pics
		AbstractScreenAdapter.batch.draw(pixiePic, 150, 390);
		AbstractScreenAdapter.batch.draw(goodPixiePic, 150, 250);
		AbstractScreenAdapter.batch.draw(badPixiePic, 150, 90);

		// Pixies Text
		describe.draw(AbstractScreenAdapter.batch, normalPixieText, offsetLeft + 150, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - 280);
		describe.draw(AbstractScreenAdapter.batch, goodPixieText, offsetLeft + 150, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - 410);
		describe.draw(AbstractScreenAdapter.batch, badPixieText, offsetLeft + 150, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - 580);

		AbstractScreenAdapter.batch.end();
	}

	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
			screenManager.setCurrentState(ScreenEnum.Menu);
		}
	}

}
