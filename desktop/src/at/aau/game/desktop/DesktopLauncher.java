package at.aau.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import at.aau.game.PixieSmack;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;//(int) PixieSmack.MENU_GAME_WIDTH;
		config.height = 768;//(int) PixieSmack.MENU_GAME_HEIGHT;
		config.addIcon("menu/ic_launcher_32.png", Files.FileType.Internal);
		config.fullscreen = true;
		config.resizable = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new PixieSmack(), config);
	}
}