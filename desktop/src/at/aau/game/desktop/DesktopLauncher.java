package at.aau.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.aau.game.PixieSmack;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) PixieSmack.MENU_GAME_WIDTH;
        config.height = (int) PixieSmack.MENU_GAME_HEIGHT;
        new LwjglApplication(new PixieSmack(), config);
    }
}