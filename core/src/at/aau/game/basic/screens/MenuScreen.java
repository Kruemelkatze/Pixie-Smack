package at.aau.game.basic.screens;

import at.aau.game.BasicConstants;
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
import com.badlogic.gdx.math.Vector3;

public class MenuScreen extends AbstractScreenAdapter {
	private final Texture backgroundImage, helpButton, helpButtonClicked;
	private final BitmapFont menuFont;
	private boolean helpClicked;
	private final String[] menuStrings = { GameConstants.NEW_GAME, GameConstants.RESUME_GAME, "Hall Of Fame", "Credits", "Exit" };
	private int currentMenuItem = 0;
	private final float offsetLeft = PixieSmackGame.MENU_GAME_WIDTH / 3.8f, offsetTop = PixieSmackGame.MENU_GAME_WIDTH / 4,
			offsetY = PixieSmackGame.MENU_GAME_HEIGHT / 8;

	public MenuScreen(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		super(gdxAssetManager, soundManager, screenManager);
		helpClicked = false;

		backgroundImage = gdxAssetManager.get(ImageConstants.MENU_BACKGROUND);
		helpButton = gdxAssetManager.get("menuScreen/helpButton.png");
		helpButtonClicked = gdxAssetManager.get(ImageConstants.FOLDER_MENU_SCREEN + "helpButton_pressed.png");
		menuFont = gdxAssetManager.get("general/Ravie_42.fnt");
		menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		menuFont.setColor(BasicConstants.COLOR_PINK);
		// Create camera that projects the desktop onto the actual screen size.
		cam = new OrthographicCamera(PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		Gdx.input.setCursorCatched(false);
		soundManager.playEvent(GameConstants.INTRO_MUSIC);
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
		// draw bgImage ...
		AbstractScreenAdapter.batch.draw(backgroundImage, 0, 0, PixieSmackGame.MENU_GAME_WIDTH, PixieSmackGame.MENU_GAME_HEIGHT);
		// draw Strings ...
		int offsetFactor = 0;
		for (int i = 0; i < menuStrings.length; i++) {
			if (i == currentMenuItem) {
				menuFont.setColor(0.8f, 0.0f, 0.7f, 1f);
			} else {
				menuFont.setColor(BasicConstants.COLOR_PINK);
			}
			if (menuStrings[i].equals(GameConstants.RESUME_GAME) && !this.screenManager.isAlreadyIngame()) {
				menuFont.setColor(0.3f, 0.3f, 0.3f, 1f);
				menuFont.draw(AbstractScreenAdapter.batch, menuStrings[i], offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - offsetFactor * offsetY);
				offsetFactor++;
			} else if (menuStrings[i].equals(GameConstants.RESUME_GAME) && this.screenManager.isAlreadyIngame()) {
				menuFont.draw(AbstractScreenAdapter.batch, menuStrings[i], offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - offsetFactor * offsetY);
				offsetFactor++;
			} else if (!menuStrings[i].equals(GameConstants.RESUME_GAME)) {
				menuFont.draw(AbstractScreenAdapter.batch, menuStrings[i], offsetLeft, PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - offsetFactor * offsetY);
				offsetFactor++;
			}
		}
		// draw FAQ Button
		if (!helpClicked) {
			AbstractScreenAdapter.batch.draw(helpButton, PixieSmackGame.MENU_GAME_WIDTH - 90, 20, 90, 90);
		} else {
			AbstractScreenAdapter.batch.draw(helpButtonClicked, PixieSmackGame.MENU_GAME_WIDTH - 90, 20, 90, 90);
		}
		AbstractScreenAdapter.batch.end();
	}

	private void handleInput() {
		// keys ...
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && this.screenManager.isAlreadyIngame()) {
			this.screenManager.setCurrentState(ScreenEnum.ResumeGame);
			// SoundManager.stopMusic();
			soundManager.playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			currentMenuItem = (currentMenuItem + 1) % menuStrings.length;
			if (currentMenuItem == 1 && !this.screenManager.isAlreadyIngame()) {
				currentMenuItem++;
			}
			soundManager.playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			currentMenuItem = (currentMenuItem - 1) % menuStrings.length;
			if (currentMenuItem == 1 && !this.screenManager.isAlreadyIngame()) {
				currentMenuItem--;
			}
			if (currentMenuItem < 0) {
				currentMenuItem = 0;
			} else {
				soundManager.playEvent("blip");
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (menuStrings[currentMenuItem].equals("Exit")) {
				Gdx.app.exit();
				soundManager.playEvent("explode");
			} else if (menuStrings[currentMenuItem].equals("Credits")) {
				// SoundManager.stopMusic();
				screenManager.setCurrentState(ScreenEnum.Credits);
			} else if (menuStrings[currentMenuItem].equals(GameConstants.NEW_GAME)) {
				screenManager.setCurrentState(ScreenEnum.NewGame);
				// SoundManager.stopMusic();
			} else if (menuStrings[currentMenuItem].equals(GameConstants.RESUME_GAME) && this.screenManager.isAlreadyIngame()) {
				screenManager.setCurrentState(ScreenEnum.ResumeGame);
				// SoundManager.stopMusic();
			} else if (menuStrings[currentMenuItem].equals("Hall Of Fame")) {
				screenManager.setCurrentState(ScreenEnum.Highscore);
				// SoundManager.stopMusic();
			}
		}
		// touch
		if (Gdx.input.justTouched()) {
			final Vector3 touchWorldCoords = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
			// find the menu item ..
			for (int i = 0; i < menuStrings.length; i++) {
				if (touchWorldCoords.x > offsetLeft && touchWorldCoords.x < PixieSmackGame.MENU_GAME_WIDTH - offsetLeft) {
					final float pos = PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - i * offsetY;
					if (touchWorldCoords.y < pos && touchWorldCoords.y > pos - menuFont.getLineHeight()) {
						// it's there
						if (menuStrings[i].equals("Exit")) {
							Gdx.app.exit();
						} else if (menuStrings[i].equals(GameConstants.NEW_GAME)) {
							// SoundManager.stopMusic();
							screenManager.setCurrentState(ScreenEnum.NewGame);
						} else if (menuStrings[i].equals(GameConstants.RESUME_GAME) && this.screenManager.isAlreadyIngame()) {
							// SoundManager.stopMusic();
							screenManager.setCurrentState(ScreenEnum.ResumeGame);
						} else if (menuStrings[i].equals("Credits")) {
							// SoundManager.stopMusic();
							screenManager.setCurrentState(ScreenEnum.Credits);
						} else if (menuStrings[i].equals("Hall Of Fame")) {
							// SoundManager.stopMusic();
							screenManager.setCurrentState(ScreenEnum.Highscore);
						}
					}
				}

			}
			if (touchWorldCoords.x > PixieSmackGame.MENU_GAME_WIDTH - 90 && touchWorldCoords.x < PixieSmackGame.MENU_GAME_WIDTH && touchWorldCoords.y < 110
					&& touchWorldCoords.y > 20) {
				helpClicked = true;
				screenManager.setCurrentState(ScreenEnum.Help);
				helpClicked = false;
			}
		}
		final Vector3 worldCoords = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
		if (worldCoords.x > offsetLeft && worldCoords.x < PixieSmackGame.MENU_GAME_WIDTH - offsetLeft
				&& worldCoords.y < PixieSmackGame.MENU_GAME_HEIGHT - offsetTop) {
			for (int i = 0; i < menuStrings.length; i++) {
				final float pos = PixieSmackGame.MENU_GAME_HEIGHT - offsetTop - i * offsetY;
				if (worldCoords.y > pos - menuFont.getLineHeight() && worldCoords.y < pos) {
					currentMenuItem = i;
				}
			}

		}
		if (worldCoords.x > PixieSmackGame.MENU_GAME_WIDTH - 90 && worldCoords.x < PixieSmackGame.MENU_GAME_WIDTH && worldCoords.y < 110 && worldCoords.y > 20) {
			helpClicked = true;
		} else {
			helpClicked = false;
		}
	}

}
