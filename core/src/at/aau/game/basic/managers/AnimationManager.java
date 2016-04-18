package at.aau.game.basic.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationManager extends AbstractManager {
	/*
	 * private final PixieSmackGame game;
	 *
	 * public AnimationManager(PixieSmackGame game) { this.game = game; }
	 */

	public AnimationManager(AssetManager gdxAssetManager) {
		super(gdxAssetManager);
	}

	/**
	 * Loads an Animation from single files. Files must be named like path1.png
	 *
	 * @param path
	 * @param frames
	 * @param frameDuration
	 * @return
	 */
	public Animation loadAnimation(String path, int frames, float frameDuration) {
		final TextureRegion[] regions = new TextureRegion[frames];

		for (int i = 0; i < frames; i++) {
			final Texture tex = gdxAssetManager.get(path + (i + 1) + ".png");
			tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			regions[i] = new TextureRegion(tex);
		}
		return new Animation(frameDuration, regions);
	}

	/**
	 * Loads an Animation from a Spritesheet
	 *
	 * @param filename
	 * @param frameDuration
	 * @param width
	 * @param height
	 * @return
	 */
	public Animation loadAnimation(String filename, float frameDuration, int width, int height) {
		final Texture tex = gdxAssetManager.get(filename);
		final int h = tex.getHeight() / height;
		final int w = tex.getWidth() / width;

		final TextureRegion[] regions = new TextureRegion[w * h];

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
				regions[i + (j * w)] = new TextureRegion(tex, i * width, j * height, width, height);
			}
		}
		return new Animation(frameDuration, regions);
	}

	/*
	 * public Animation loadAnimation(String filename, float frameDuration, int width, int height, PlayMode playmode) { Texture tex =
	 * game.getAssetManager().get(filename); int h = tex.getHeight() / height; int w = tex.getWidth() / width;
	 *
	 * TextureRegion[] regions = new TextureRegion[w * h];
	 *
	 * for (int i = 0; i < w; i++) { for (int j = 0; j < h; j++) { tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); regions[i + (j *
	 * w)] = new TextureRegion(tex, i * width, j * height, width, height); } } return new Animation(frameDuration, regions, playmode); }
	 */
}
