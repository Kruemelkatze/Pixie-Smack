package at.aau.game.desktop;

import at.aau.game.PixieSmackGame;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	@SuppressWarnings("unused")
	public static void main(String[] arg) {
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 768;
		config.addIcon("general/ic_launcher_32.png", Files.FileType.Internal);
		config.fullscreen = false;
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new PixieSmackGame(), config);
	}
}