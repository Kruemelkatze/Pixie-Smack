package at.aau.game.basic.managers;

import com.badlogic.gdx.assets.AssetManager;

/**
 *
 * @author Herkt Kevin
 *
 */
public abstract class AbstractManager {
	protected final AssetManager gdxAssetManager;

	protected AbstractManager(AssetManager gdxAssetManager) {
		this.gdxAssetManager = gdxAssetManager;
	}
}
