package at.aau.game.basic.managers;

import at.aau.game.basic.screens.CreditsScreen;
import at.aau.game.basic.screens.HelpScreen;
import at.aau.game.basic.screens.HighscoreScreen;
import at.aau.game.basic.screens.LoadingScreen;
import at.aau.game.basic.screens.MenuScreen;
import at.aau.game.basic.screens.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class ScreenManager extends AbstractManager {
	private Screen currentScreen;
	private PlayScreen playScreen;
	private ScreenEnum currentState;
	private final SoundManager soundManager;
	private boolean alreadyIngame = false;

	public ScreenManager(AssetManager gdxAssetManager, SoundManager soundManager) {
		super(gdxAssetManager);
		this.soundManager = soundManager;
		currentScreen = new LoadingScreen(gdxAssetManager, soundManager, this);
		currentState = ScreenEnum.Loading;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public ScreenEnum getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ScreenEnum state) {
		this.setCurrentState(state, false);
	}

	/**
	 *
	 * @param state
	 * @param forceAgain
	 *            will also set the screen if its the same (for example for restart)
	 */
	public void setCurrentState(ScreenEnum state, boolean forceAgain) {
		if (state != currentState || forceAgain) { // only if state changes.
			currentState = state;
			if (state == ScreenEnum.Menu) {
				Gdx.input.setCursorCatched(false);
				currentScreen = new MenuScreen(gdxAssetManager, soundManager, this);
			} else if (state == ScreenEnum.Credits) {
				currentScreen = new CreditsScreen(gdxAssetManager, soundManager, this);
			} else if (state == ScreenEnum.NewGame) {
				playScreen = new PlayScreen(gdxAssetManager, soundManager, this);
				currentScreen = playScreen;
			} else if (state == ScreenEnum.ResumeGame) {
				currentScreen = playScreen;
				if (playScreen == null) {
					throw new RuntimeException("there is no current playScreen");
				}
				playScreen.show();
			} else if (state == ScreenEnum.Highscore) {
				currentScreen = new HighscoreScreen(gdxAssetManager, soundManager, this);
			} else if (state == ScreenEnum.Help) {
				currentScreen = new HelpScreen(gdxAssetManager, soundManager, this);
			}
		}
	}

	public boolean isAlreadyIngame() {
		return alreadyIngame;
	}

	public void setAlreadyIngame(boolean alreadyIngame) {
		this.alreadyIngame = alreadyIngame;
	}
}