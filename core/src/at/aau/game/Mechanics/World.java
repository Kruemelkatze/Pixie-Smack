package at.aau.game.Mechanics;

import java.util.Random;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.Entities.FairyObject;
import at.aau.game.Mechanics.Entities.GameObject;
import at.aau.game.Mechanics.Entities.SkeletonControlledObject;
import at.aau.game.screens.GameplayScreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {
	private SpriteBatch spriteBatch;
	public Array<GameObject> gameObjects;
	public GameplayScreen gameplayScreen;
	SkeletonControlledObject skeletonControlledObject;
	private float fairySpawnTimer = 0.0f;
	private Random random = new Random();

	public World(GameplayScreen gameplayScreen) {
		spriteBatch = new SpriteBatch();
		gameObjects = new Array<GameObject>();
		this.gameplayScreen = gameplayScreen;

		// Add SkeletonControlledObject
		skeletonControlledObject = new SkeletonControlledObject(new Vector2(0f, 0f), this);
		gameObjects.add(skeletonControlledObject);

	}

	public void update(float delta) {
		// this.spawnRandomFaries(delta);
		for (GameObject go : gameObjects) {
			go.update(delta);
		}
	}

	private void spawnRandomFaries(float delta) {
		this.fairySpawnTimer += delta;
		if (fairySpawnTimer >= GameConstants.FAIRY_SPAWN_THRESHOLD) {
			this.fairySpawnTimer = 0.0f;
			float xSpawn = random.nextFloat() * GameConstants.FAIRY_MAX_X;
			float ySpawn = random.nextFloat() * GameConstants.FAIRY_MAX_Y;
			gameObjects.add(new FairyObject(new Vector2(xSpawn, ySpawn), this));
			gameObjects.add(skeletonControlledObject);
		}

	}

	public void render(float delta) {
		spriteBatch.begin();
		for (GameObject go : gameObjects) {
			go.render(delta, spriteBatch);
		}
		spriteBatch.end();
	}

	public void touch(Vector3 touchCoords) {
		skeletonControlledObject.touch(touchCoords);
	}
}
