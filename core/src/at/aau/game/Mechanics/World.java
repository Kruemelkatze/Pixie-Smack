package at.aau.game.Mechanics;

import java.util.Iterator;
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
	public Array<FairyObject> bigFairies;
	public Array<FairyObject> badFairies;
	public GameplayScreen gameplayScreen;
	public int highscore;
	public String highscoreName;
	public BitmapFont highscoreBitmapFont;
	public SmackCntPic smackCounterPic;
	static Vector2 SIZE = new Vector2(237, 46);
	Koerbchen koerbchen;
	Smacker smacker;
	public Array<SmackAnim> smackAnims;

	private float fairySpawnTimer = 0.0f;
	private float badFairySpawnTimer = 0.0f;
	private float bigFairySpawnTimer = 0.0f;
	private int fairySpawnStage = 0;
	private float fairySpawnSpeed = 1.0f;
	// public Vector2 size;
	public Vector2 pixelSize;

	private GlyphLayout highScoreLayout;
	private GlyphLayout gameOverLayout;

	private long timeElapsed;
	private String mmss;
	private boolean gameEnded = false;

	// com.badlogic.gdx.physics.box2d.World box2DWorld;

	public World(GameplayScreen gameplayScreen) {
		spriteBatch = new SpriteBatch();
		gameObjects = new Array<GameObject>();
		pixieDusts = new Array<PixieDust>();
		fairies = new Array<FairyObject>();
		bigFairies = new Array<FairyObject>();
		badFairies = new Array<FairyObject>();
		smackAnims = new Array<SmackAnim>();
		this.gameplayScreen = gameplayScreen;

		smackCounterPic = new SmackCntPic(new Vector2(PixieSmack.MENU_GAME_WIDTH-SIZE.x-10, 40), this, SIZE);

		// size = new Vector2(PixieSmack.MENU_GAME_WIDTH,
		// PixieSmack.MENU_GAME_HEIGHT);
		// pixelSize = PixieSmack.worldToPixel(size);
		// pixelSize = size.cpy();
		pixelSize = new Vector2(PixieSmack.MENU_GAME_WIDTH, PixieSmack.MENU_GAME_HEIGHT);

		koerbchen = new Koerbchen(new Vector2(PixieSmack.MENU_GAME_WIDTH/2, 50), this);
		gameObjects.add(koerbchen);

		smacker = new Smacker(Vector2.Zero, this);
		gameObjects.add(smacker);

		// box2DWorld = new com.badlogic.gdx.physics.box2d.World(Vector2.Zero,
		// false);

		highscore = 0;
		highscoreName = "0";
		highscoreBitmapFont = new BitmapFont();
		highscoreBitmapFont = gameplayScreen.parentGame.getAssetManager().get("menu/Ravie_42.fnt");
		highscoreBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);
		highscoreBitmapFont.setColor(GameConstants.COLOR_PINK);
		highScoreLayout = new GlyphLayout(highscoreBitmapFont, highscoreName);
		gameOverLayout = new GlyphLayout(highscoreBitmapFont, "Game Over");

		Timer timer = new Timer(new Vector2(PixieSmack.MENU_GAME_WIDTH / 2f, PixieSmack.MENU_GAME_HEIGHT-100), this, new Vector2(80, 75));
		gameObjects.add(timer);
	}

	public void update(float delta) {
		if (gameEnded) {
			return;
		}
		this.timeElapsed += delta * 1000;
		this.mmss = String.format("%02d:%02d",
				Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(this.timeElapsed) % TimeUnit.HOURS.toMinutes(1)),
				Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(this.timeElapsed) % TimeUnit.MINUTES.toSeconds(1)));
		for (GameObject go : gameObjects) {
			go.update(delta);
		}

		for (FairyObject fairy : fairies) {
			fairy.update(delta);
		}

		for (FairyObject fairy : badFairies) {
			fairy.update(delta);
		}
		for (FairyObject fairy : bigFairies) {
			fairy.update(delta);
		}

		for (PixieDust pi : pixieDusts) {
			pi.update(delta);
		}
		this.spawnRandomFairies(delta);
	}

	private void spawnRandomFairies(float delta) {
		this.fairySpawnTimer += delta;
		this.bigFairySpawnTimer += delta;
		this.badFairySpawnTimer += delta;
		if (fairies.size < GameConstants.MAX_FAIRIES
				&& fairySpawnTimer >= GameConstants.FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.fairySpawnTimer = 0.0f;
			float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			FairyObject fairy = new FairyObject(new Vector2(xSpawn, ySpawn), this);
			fairies.add(fairy);
		}

		if (bigFairies.size < GameConstants.MAX_BIG_FAIRIES
				&& bigFairySpawnTimer >= GameConstants.BIG_FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.bigFairySpawnTimer = 0.0f;
			float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			BigFairyObject fairy = new BigFairyObject(new Vector2(xSpawn, ySpawn), this);
			bigFairies.add(fairy);
		}

		if (badFairies.size < GameConstants.MAX_BAD_FAIRIES
				&& badFairySpawnTimer >= GameConstants.BAD_FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.badFairySpawnTimer = 0.0f;
			float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			BadFairyObject fairy = new BadFairyObject(new Vector2(xSpawn, ySpawn), this);
			badFairies.add(fairy);
		}

	}

	public void touch(Vector2 touchCoords) {

		if (!this.gameEnded && smacker.SmackCnt > 0) {
			smacker.smack();

			Iterator<FairyObject> iterator = fairies.iterator();
			while (iterator.hasNext()) {
				FairyObject fairy = iterator.next();
				if (isWithinSmackBounds(touchCoords, fairy)) {
					fairy.onCollision();
				}

			}
			iterator = bigFairies.iterator();
			while (iterator.hasNext()) {
				FairyObject fairy = iterator.next();
				if (isWithinSmackBounds(touchCoords, fairy)) {
					fairy.onCollision();
				}

			}
			iterator = badFairies.iterator();
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

	private void spawnBadDust(Vector2 position) {
		PixieDust pixieDust = new PixieDust(position, this);
		pixieDust.setIsBadDust(true);
		pixieDusts.add(pixieDust);
	}

	private void spawnSpecialDust(Vector2 position) {
		PixieDust pixieDust = new PixieDust(position, this);
		pixieDust.setIsSpecialDust(true);
		pixieDusts.add(pixieDust);
	}

	public void pixieDustMissed(PixieDust pixieDust) {
		pixieDusts.removeValue(pixieDust, true);
	}

	private boolean isWithinSmackBounds(Vector2 touchPosition, GameObject dust) {
		return dust.position.dst(touchPosition) <= GameConstants.SMACKER_REACH;
	}

	public void pixieDustCollected(PixieDust pixieDust, float distance) {
		// Give points and stuff
		if (pixieDust.IsBadDust) {
			highscore -= 20;
		} else if (pixieDust.IsSpecialDust) {
			highscore += 60;
		} else {
			highscore += 10;
		}

		if (highscore == Math.pow(2, this.fairySpawnStage) * 100) {
			this.fairySpawnSpeed *= 0.85f;
			this.fairySpawnStage++;
		}

		highscoreName = "" + highscore;
		highScoreLayout = new GlyphLayout(highscoreBitmapFont, highscoreName);
		pixieDusts.removeValue(pixieDust, true);
	}

	public void pixieSmacked(FairyObject fairy) {
		if (fairy instanceof BadFairyObject) {
			smacker.SmackCnt += GameConstants.BAD_FAIRY_SMACK_CHANGE;
			if (smacker.SmackCnt < 0) {
				smacker.SmackCnt = 0;
			}
			spawnBadDust(fairy.position.cpy());
		} else if (fairy instanceof BigFairyObject) {
			smacker.SmackCnt += GameConstants.BIG_FAIRY_SMACK_CHANGE;

			if (smacker.SmackCnt > GameConstants.SMACK_LIMIT) {
				smacker.SmackCnt = GameConstants.SMACK_LIMIT;
			}

			spawnSpecialDust(fairy.position.cpy());
		} else {
			spawnDust(fairy.position.cpy());
		}
		spawnSmackAnim(fairy.position.cpy());
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
		for (FairyObject fairy : badFairies) {
			fairy.render(delta, spriteBatch);
		}
		for (FairyObject fairy : bigFairies) {
			fairy.render(delta, spriteBatch);
		}
		for (PixieDust pi : pixieDusts) {
			pi.render(delta, spriteBatch);
		}
		for (SmackAnim smackAnim : smackAnims) {
			smackAnim.render(delta, spriteBatch);
		}
		smacker.render(delta, spriteBatch);
		smackCounterPic.render(delta, spriteBatch);
		smackCounterPic.setAnimation(smacker.SmackCnt);

		// highscoreBitmapFont.draw(spriteBatch, highscoreName, (float) (910 -
		// ((int) (Math.log10(highscore) - 1) * 20)), 680);
		highscoreBitmapFont.draw(spriteBatch, highScoreLayout, PixieSmack.MENU_GAME_WIDTH - 10 - highScoreLayout.width,
				PixieSmack.MENU_GAME_HEIGHT-highScoreLayout.height);
		GlyphLayout layout = new GlyphLayout(highscoreBitmapFont, this.mmss);
		highscoreBitmapFont.draw(spriteBatch, layout, 10, PixieSmack.MENU_GAME_HEIGHT-layout.height);

		if ((timeElapsed / 1000f) >= GameConstants.TIMEOUT) {
			if (!gameEnded) {
				Preferences prefs = Gdx.app.getPreferences("Highscores");
				if (!prefs.contains("highScore1")) {
					prefs.putInteger("highScore1", 0);
					prefs.putInteger("highScore2", 0);
					prefs.putInteger("highScore3", 0);
					prefs.putInteger("highScore4", 0);
					prefs.putInteger("highScore5", 0);
					prefs.flush();
				}
				int highscore1 = prefs.getInteger("highScore1");
				int highscore2 = prefs.getInteger("highScore2");
				int highscore3 = prefs.getInteger("highScore3");
				int highscore4 = prefs.getInteger("highScore4");
				int highscore5 = prefs.getInteger("highScore5");
				if (this.highscore > highscore1) {
					prefs.putInteger("highScore5", highscore4);
					prefs.putInteger("highScore4", highscore3);
					prefs.putInteger("highScore3", highscore2);
					prefs.putInteger("highScore2", highscore1);
					prefs.putInteger("highScore1", this.highscore);
				} else if (this.highscore <= highscore1 && this.highscore > highscore2) {
					prefs.putInteger("highScore5", highscore4);
					prefs.putInteger("highScore4", highscore3);
					prefs.putInteger("highScore3", highscore2);
					prefs.putInteger("highScore2", this.highscore);
				} else if (this.highscore <= highscore2 && this.highscore > highscore3) {
					prefs.putInteger("highScore5", highscore4);
					prefs.putInteger("highScore4", highscore3);
					prefs.putInteger("highScore3", this.highscore);
				} else if (this.highscore <= highscore3 && this.highscore > highscore4) {
					prefs.putInteger("highScore5", highscore4);
					prefs.putInteger("highScore4", this.highscore);
				} else if (this.highscore <= highscore4 && this.highscore > highscore5) {
					prefs.putInteger("highScore5", this.highscore);
				}
				prefs.flush();
			}
			gameEnded = true;
			highscoreBitmapFont.draw(spriteBatch, gameOverLayout,
					PixieSmack.MENU_GAME_WIDTH / 2f - gameOverLayout.width / 2f,
					PixieSmack.MENU_GAME_HEIGHT / 2f + gameOverLayout.height / 2f);
		}
		spriteBatch.end();
	}

}