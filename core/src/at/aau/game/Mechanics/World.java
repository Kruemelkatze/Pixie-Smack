package at.aau.game.Mechanics;

import java.util.Random;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.Entities.FairyObject;
import at.aau.game.Mechanics.Entities.GameObject;
import at.aau.game.Mechanics.Entities.PixieDust;
import at.aau.game.Mechanics.Entities.Koerbchen;
import at.aau.game.PixieSmack;
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
    public Array<PixieDust> pixieDusts;
    public GameplayScreen gameplayScreen;

    PixieDust pixieDust;

    Koerbchen koerbchen;
    private float fairySpawnTimer = 0.0f;
    private Random random = new Random();
    public Vector2 size;
    public Vector2 pixelSize;

    com.badlogic.gdx.physics.box2d.World box2DWorld;


    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        pixieDusts = new Array<PixieDust>();
        this.gameplayScreen = gameplayScreen;

        //Add SkeletonControlledObject
        pixieDust = new PixieDust(new Vector2(300f,300f), this);
        pixieDusts.add(pixieDust);

        size = new Vector2(PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);
        pixelSize = PixieSmack.worldToPixel(size);

        koerbchen = new Koerbchen(new Vector2(50, 200), this);
        gameObjects.add(koerbchen);
        
        box2DWorld = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, false);

    }

    public void update(float delta) {
        for (GameObject go: gameObjects) {
            go.update(delta);
        }
        for (PixieDust pi: pixieDusts) {
            pi.update(delta);
        }
		this.spawnRandomFairies(delta);
		this.checkClickOnFairies();
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (GameObject go: gameObjects) {
            go.render(delta, spriteBatch);
        }
        for (PixieDust pi: pixieDusts) {
            pi.render(delta, spriteBatch);
        }
        spriteBatch.end();
    }

	private void checkClickOnFairies() {
		// TODO Auto-generated method stub

	}

	private void spawnRandomFairies(float delta) {
		this.fairySpawnTimer += delta;
		if (fairySpawnTimer >= GameConstants.FAIRY_SPAWN_THRESHOLD) {
			this.fairySpawnTimer = 0.0f;
			float xSpawn = random.nextFloat() * GameConstants.FAIRY_MAX_X;
			float ySpawn = random.nextFloat() * GameConstants.FAIRY_MAX_Y;
			gameObjects.add(new FairyObject(new Vector2(xSpawn, ySpawn), this));
		}
	}

    public void touch(Vector3 touchCoords) {
        koerbchen.touch(touchCoords);
    }

    public com.badlogic.gdx.physics.box2d.World getBox2DWorld() {
        return box2DWorld;
    }
}
