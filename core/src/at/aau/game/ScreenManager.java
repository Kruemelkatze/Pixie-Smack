package at.aau.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import at.aau.game.screens.CreditsScreen;
import at.aau.game.screens.GameplayScreen;
import at.aau.game.screens.HelpScreen;
import at.aau.game.screens.HighscoreScreen;
import at.aau.game.screens.LoadingScreen;
import at.aau.game.screens.MenuScreen;

public class ScreenManager {
	public enum ScreenState {
		Loading, Menu, Highscore, NewGame, ResumeGame, Credits, Help, GameOver
	}

	private Screen currentScreen;
	private GameplayScreen playScreen;
	private ScreenState currentState;
	private PixieSmack parentGame;

	public ScreenManager(PixieSmack game) {
		this.parentGame = game;
		currentScreen = new LoadingScreen(game);
		currentState = ScreenState.Loading;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	public ScreenState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ScreenState state) {
		if (state != currentState) { // only if state changes.
			currentState = state;
			if (state == ScreenState.Menu) {
				Gdx.input.setCursorCatched(false);
				currentScreen = new MenuScreen(parentGame);
			} else if (state == ScreenState.Credits) {
				currentScreen = new CreditsScreen(parentGame);
			} else if (state == ScreenState.NewGame) {
				playScreen = new GameplayScreen(parentGame);
				currentScreen = playScreen;
			} else if (state == ScreenState.ResumeGame) {
				currentScreen = playScreen;
				playScreen.show();
			} else if (state == ScreenState.Highscore) {
				currentScreen = new HighscoreScreen(parentGame);
			} else if (state == ScreenState.Help) {
				currentScreen = new HelpScreen(parentGame);
			}
		}
	}

	public PixieSmack getParentGame() {
		return parentGame;
	}

	public void setParentGame(PixieSmack parentGame) {
		this.parentGame = parentGame;
	}
}
