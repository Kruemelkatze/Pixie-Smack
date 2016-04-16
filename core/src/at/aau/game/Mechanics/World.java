package at.aau.game.Mechanics;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.Entities.*;
import at.aau.game.PixieSmack;
import at.aau.game.screens.GameplayScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    public Array<SmackAnim> smackAnims;

    private float fairySpawnTimer = 0.0f;
    private int fairySpawnStage = 0;
    private float fairySpawnSpeed = 1.0f;
    private Random random = new Random();
    public Vector2 size;
    public Vector2 pixelSize;

    private GlyphLayout highScoreLayout;
    private GlyphLayout gameOverLayout;

    private long timeElapsed;
    private String mmss;
    private boolean gameEnded = false;

    com.badlogic.gdx.physics.box2d.World box2DWorld;

    public World(GameplayScreen gameplayScreen) {
        spriteBatch = new SpriteBatch();
        gameObjects = new Array<GameObject>();
        pixieDusts = new Array<PixieDust>();
        fairies = new Array<FairyObject>();
        smackAnims = new Array<SmackAnim>();
        this.gameplayScreen = gameplayScreen;

        size = new Vector2(PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);
        pixelSize = PixieSmack.worldToPixel(size);

        koerbchen = new Koerbchen(new Vector2(50, 200), this);
        gameObjects.add(koerbchen);

        smacker = new Smacker(Vector2.Zero, this);

        box2DWorld = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero, false);

        highscore = 0;
        highscoreName = "0";
        highscoreBitmapFont = new BitmapFont();
        highscoreBitmapFont = gameplayScreen.parentGame.getAssetManager().get("menu/Ravie_42.fnt");
        highscoreBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        highscoreBitmapFont.setColor(1f, 120f / 255f, 246f / 255f, 1.0f);
        highScoreLayout = new GlyphLayout(highscoreBitmapFont, highscoreName);
        gameOverLayout = new GlyphLayout(highscoreBitmapFont, "Game Over");

        Timer timer = new Timer(new Vector2(PixieSmack.MENU_GAME_WIDTH / 2f, 620f), this, new Vector2(80, 75));
        gameObjects.add(timer);
    }

    public void update(float delta) {
        if (gameEnded) {
            return;
        }
        this.timeElapsed += delta * 1000;
        this.mmss = String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(this.timeElapsed) % TimeUnit.HOURS.toMinutes(1)),
                Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(this.timeElapsed) % TimeUnit.MINUTES.toSeconds(1)));
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
    }

	private void spawnRandomFairies(float delta) {
		this.fairySpawnTimer += delta;
		if (fairies.size <= GameConstants.MAX_FAIRIES && fairySpawnTimer >= GameConstants.FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.fairySpawnTimer = 0.0f;
			float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			FairyObject fairy = new FairyObject(new Vector2(xSpawn, ySpawn), this);
			fairies.add(fairy);
		}
	}

	public void touch(Vector2 touchCoords) {
				
		if (!this.gameEnded) {
			smacker.smack();

			Iterator<FairyObject> iterator = fairies.iterator();
			while (iterator.hasNext()) {
				FairyObject fairy = iterator.next();
				if (isWithinSmackBounds(touchCoords, fairy)) {
					fairy.onCollision();
				}

			}
		}
	}

	public static float randInRange(float min, float max) {
		return (float) (min + (Math.random() * ((1 + max) - min)));
	}

	private void spawnDust(Vector2 position) {
		PixieDust pixieDust = new PixieDust(position, this);
		pixieDusts.add(pixieDust);
	}

	public void pixieDustMissed(PixieDust pixieDust) {
		pixieDusts.removeValue(pixieDust, true);
	}

	private boolean isWithinSmackBounds(Vector2 touchPosition, GameObject dust) {
		return dust.position.dst(touchPosition) <= GameConstants.SMACKER_REACH;
	}

	public com.badlogic.gdx.physics.box2d.World getBox2DWorld() {
		return box2DWorld;
	}

	public void pixieDustCollected(PixieDust pixieDust, float distance) {
		// Give points and stuff
		highscore += 10;

		if (highscore == Math.pow(2, this.fairySpawnStage) * 100) {
			this.fairySpawnSpeed *= 0.85f;
			this.fairySpawnStage++;
		}

		highscoreName = ""+highscore;
		highScoreLayout = new GlyphLayout(highscoreBitmapFont, highscoreName);
		pixieDusts.removeValue(pixieDust, true);
	}


	public void pixieSmacked(FairyObject fairy) {
		spawnDust(fairy.position);
		spawnSmackAnim(fairy.position);
		fairies.removeValue(fairy, true);
	}

	private void spawnSmackAnim(Vector2 position) {
		SmackAnim smackAnim = new SmackAnim(position, this);
		smackAnims.add(smackAnim);
	}

	public Array<SmackAnim> getSmackAnims() {
		return smackAnims;
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
		for (SmackAnim smackAnim : smackAnims) {
			smackAnim.render(delta, spriteBatch);
		}
		smacker.render(delta, spriteBatch);

		// highscoreBitmapFont.draw(spriteBatch, highscoreName, (float) (910 - ((int) (Math.log10(highscore) - 1) * 20)), 680);
		highscoreBitmapFont.draw(spriteBatch, highScoreLayout, PixieSmack.MENU_GAME_WIDTH - 10 - highScoreLayout.width, 680);

		highscoreBitmapFont.draw(spriteBatch, this.mmss, 10, 680);

		if ((timeElapsed / 1000f) >= GameConstants.TIMEOUT) {
			gameEnded = true;
			highscoreBitmapFont.draw(spriteBatch, gameOverLayout, PixieSmack.MENU_GAME_WIDTH / 2f - gameOverLayout.width / 2f, PixieSmack.MENU_GAME_HEIGHT / 2f
					+ gameOverLayout.height / 2f);
			Preferences prefs = Gdx.app.getPreferences("Highscores");
			if (prefs.contains("highScore1") || this.highscore > prefs.getInteger("highScore1")) {
				prefs.putInteger("highScore1", this.highscore);
			} else if (prefs.contains("highScore2") || this.highscore > prefs.getInteger("highScore2")) {
				prefs.putInteger("highScore2", this.highscore);
			} else if (prefs.contains("highScore3") || this.highscore > prefs.getInteger("highScore3")) {
				prefs.putInteger("highScore3", this.highscore);
			} else if (prefs.contains("highScore4") || this.highscore > prefs.getInteger("highScore4")) {
				prefs.putInteger("highScore4", this.highscore);
			} else if (prefs.contains("highScore5") || this.highscore > prefs.getInteger("highScore5")) {
				prefs.putInteger("highScore5", this.highscore);
			}
			prefs.flush();
		}
		spriteBatch.end();
	}

}