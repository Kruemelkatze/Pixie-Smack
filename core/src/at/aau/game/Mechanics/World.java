package at.aau.game.Mechanics;

import at.aau.game.Mechanics.Entities.GameObject;
import at.aau.game.Mechanics.Entities.Koerbchen;
import at.aau.game.Mechanics.Entities.SkeletonControlledObject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

import at.aau.game.screens.GameplayScreen;


public class World {
    private com.badlogic.gdx.physics.box2d.World box2DWorld;

    private SpriteBatch spriteBatch;
    public Array<GameObject> gameObjects;
    public GameplayScreen gameplayScreen;
    Koerbchen skeletonControlledObject;

    //TODO: Remove :)
    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        this.gameplayScreen = gameplayScreen;

        this.box2DWorld = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, false);

        //Add SkeletonControlledObject
        skeletonControlledObject = new Koerbchen(new Vector2(0f, 0f), this);
        gameObjects.add(skeletonControlledObject);

    }

    public void update(float delta) {
        box2DWorld.step(delta, 6, 2);

        for (GameObject go : gameObjects) {
            go.update(delta);
        }
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (GameObject go : gameObjects) {
            go.render(delta, spriteBatch);
        }
        spriteBatch.end();
        debugRenderer.render(box2DWorld, gameplayScreen.cam.combined);

    }

    public com.badlogic.gdx.physics.box2d.World getBox2DWorld() {
        return box2DWorld;
    }

    public void touch(Vector3 touchCoords) {
        skeletonControlledObject.touch(touchCoords);
    }
}
