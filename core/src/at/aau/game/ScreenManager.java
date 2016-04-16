package at.aau.game;

import com.badlogic.gdx.Screen;

import at.aau.game.screens.CreditsScreen;
import at.aau.game.screens.GameplayScreen;
import at.aau.game.screens.HighscoreScreen;
import at.aau.game.screens.LoadingScreen;
import at.aau.game.screens.MenuScreen;

public class ScreenManager {
    public enum ScreenState {Loading, Menu, Highscore, Game, Credits, Help, GameOver};
    private Screen currentScreen;
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
                currentScreen = new MenuScreen(parentGame);
            } else if (state == ScreenState.Credits) {
                currentScreen = new CreditsScreen(parentGame);
            } else if (state == ScreenState.Game) {
                currentScreen = new GameplayScreen(parentGame);
            } else if (state == ScreenState.Highscore){
            	currentScreen = new HighscoreScreen(parentGame);
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
