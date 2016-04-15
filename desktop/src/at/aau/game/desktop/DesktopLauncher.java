package at.aau.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.aau.game.PixieSmack;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 600;
		config.height = 1000;
		// config.width = 1920;
		// config.height = 1080;
		new LwjglApplication(new PixieSmack(), config);
	}
}
