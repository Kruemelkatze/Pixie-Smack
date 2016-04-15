package at.aau.game.Mechanics;

import at.aau.game.Mechanics.Entities.GameObject;
import at.aau.game.Mechanics.Entities.PixieDust;
import at.aau.game.Mechanics.Entities.SkeletonControlledObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import at.aau.game.screens.GameplayScreen;

/**
 * Created by Veit on 06.02.2016.
 */
public class World {
    private SpriteBatch spriteBatch;
    public Array<GameObject> gameObjects;
    public Array<PixieDust> pixieDusts;
    public GameplayScreen gameplayScreen;
    SkeletonControlledObject skeletonControlledObject;
    PixieDust pixieDust;

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        pixieDusts = new Array<PixieDust>();
        this.gameplayScreen = gameplayScreen;

        //Add SkeletonControlledObject
        skeletonControlledObject = new SkeletonControlledObject(new Vector2(0f,0f),this);
        pixieDust = new PixieDust(new Vector2(300f,300f), this);
        gameObjects.add(skeletonControlledObject);
        pixieDusts.add(pixieDust);

    }

    public void update(float delta) {
        for (GameObject go: gameObjects) {
            go.update(delta);
        }
        for (PixieDust pi: pixieDusts) {
            pi.update(delta);
        }
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

    public void touch(Vector3 touchCoords) {
        skeletonControlledObject.touch(touchCoords);
    }
}
