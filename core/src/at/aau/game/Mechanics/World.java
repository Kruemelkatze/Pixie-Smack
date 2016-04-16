package at.aau.game.Mechanics;

import java.util.Iterator;
import java.util.Random;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.Entities.*;
import at.aau.game.PixieSmack;
import at.aau.game.screens.GameplayScreen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    public Array<FairyObject> fairies;
    public GameplayScreen gameplayScreen;
    public int highscore;
    public String highscoreName;
    public BitmapFont highscoreBitmapFont;

    Koerbchen koerbchen;
    Smacker smacker;

    private float fairySpawnTimer = 0.0f;
    private Random random = new Random();
    public Vector2 size;
    public Vector2 pixelSize;

    com.badlogic.gdx.physics.box2d.World box2DWorld;


    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        pixieDusts = new Array<PixieDust>();
        fairies = new Array<FairyObject>();
        this.gameplayScreen = gameplayScreen;

        size = new Vector2(PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);
        pixelSize = PixieSmack.worldToPixel(size);

        koerbchen = new Koerbchen(new Vector2(50, 200), this);
        gameObjects.add(koerbchen);

        smacker = new Smacker(Vector2.Zero, this);

        box2DWorld = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, false);
        
        highscore = 0;
        highscoreName = "Score: 0";
        highscoreBitmapFont = new BitmapFont();
       

    }

    public void update(float delta) {
        for (GameObject go : gameObjects) {
            go.update(delta);
        }

        for (FairyObject fairy : fairies) {
            fairy.update(delta);
        }

        for (PixieDust pi : pixieDusts) {
            pi.update(delta);
        }
        this.spawnRandomFairies(delta);
        this.checkClickOnFairies();
    }

    public void render(float delta) {
        spriteBatch.begin();
        for (GameObject go : gameObjects) {
            go.render(delta, spriteBatch);
        }
        for (FairyObject fairy : fairies) {
            fairy.render(delta, spriteBatch);
        }
        for (PixieDust pi : pixieDusts) {
            pi.render(delta, spriteBatch);
        }
        highscoreBitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        highscoreBitmapFont.draw(spriteBatch, highscoreName, 910, 700);
        smacker.render(delta, spriteBatch);
        spriteBatch.end();
    }

    private void checkClickOnFairies() {
        // TODO Auto-generated method stub

    }

    private void spawnRandomFairies(float delta) {
        this.fairySpawnTimer += delta;
        if (fairySpawnTimer >= GameConstants.FAIRY_SPAWN_THRESHOLD) {
            this.fairySpawnTimer = 0.0f;
            float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
            float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
            FairyObject fairy = new FairyObject(new Vector2(xSpawn, ySpawn), this);
            fairies.add(fairy);
        }
    }

    public void touch(Vector2 touchCoords) {
        smacker.smack();

        Iterator<FairyObject> iterator = fairies.iterator();
        while (iterator.hasNext()) {
            FairyObject fairy = iterator.next();
            if (isWithinSmackBounds(touchCoords, fairy)) {
                fairy.onCollision();
            }
        }
    }

    public com.badlogic.gdx.physics.box2d.World getBox2DWorld() {
        return box2DWorld;
    }

    public static float randInRange(float min, float max) {
        return (float) (min + (Math.random() * ((1 + max) - min)));
    }

    private void spawnDust(Vector2 position) {
        PixieDust pixieDust = new PixieDust(position, this);
        pixieDusts.add(pixieDust);
    }


    public void pixieDustCollected(PixieDust pixieDust, float distance) {
        //Give points and stuff
        highscore += 10;
        highscoreName = "Score: "+highscore;
        
        System.out.println(highscore);
        pixieDusts.removeValue(pixieDust, true);
    }

    public void pixieDustMissed(PixieDust pixieDust) {
        pixieDusts.removeValue(pixieDust, true);
    }

    public void pixieSmacked(FairyObject fairy) {
        spawnDust(fairy.position);
        fairies.removeValue(fairy, true);
    }

    private boolean isWithinSmackBounds(Vector2 touchPosition, GameObject dust) {
        return dust.position.dst(touchPosition) <= GameConstants.SMACKER_REACH;
    }
}
