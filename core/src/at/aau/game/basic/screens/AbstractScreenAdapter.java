package at.aau.game.basic.screens;

import at.aau.game.basic.managers.ScreenManager;
import at.aau.game.basic.managers.SoundManager;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractScreenAdapter extends ScreenAdapter { // TODO by kehe ScreenAdapter vs screen?
	protected final AssetManager gdxAssetManager;// TODO not public or also no getter?
	protected final SoundManager soundManager; // TODO not public or also no getter?
	protected final ScreenManager screenManager; // TODO not public or also no getter?
	protected static final SpriteBatch batch = new SpriteBatch();
	public OrthographicCamera cam;// TODO not public

	protected AbstractScreenAdapter(AssetManager gdxAssetManager, SoundManager soundManager, ScreenManager screenManager) {
		this.gdxAssetManager = gdxAssetManager;
		this.soundManager = soundManager;
		this.screenManager = screenManager;
	}

	public AssetManager getGdxAssetManager() {
		return gdxAssetManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}
}