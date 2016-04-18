package at.aau.game.mechanic;

import java.util.ArrayList;
import java.util.Iterator;

import at.aau.game.BasicConstants;
import at.aau.game.GameConstants;
import at.aau.game.ImageConstants;
import at.aau.game.PixieSmackGame;
import at.aau.game.basic.managers.ScreenEnum;
import at.aau.game.basic.screens.PlayScreen;
import at.aau.game.mechanic.entities.AbstractGameObject;
import at.aau.game.mechanic.entities.CollectiblePixieDust;
import at.aau.game.mechanic.entities.SmackDustAnimation;
import at.aau.game.mechanic.entities.actors.BadFairyObject;
import at.aau.game.mechanic.entities.actors.BigFairyObject;
import at.aau.game.mechanic.entities.actors.FairyObject;
import at.aau.game.mechanic.entities.actors.PlayerObject;
import at.aau.game.mechanic.entities.actors.Smacker;
import at.aau.game.mechanic.entities.gui.SmackCntPic;
import at.aau.game.mechanic.entities.gui.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Herkt Kevin
 *
 */
public class World {
	private final SpriteBatch batch;
	private final Array<AbstractGameObject> abstractGameObjects;

	// --- strange public stuff
	public final PlayScreen playScreen; // TODO public
	public final Array<CollectiblePixieDust> collectiblePixieDusts; // TODO public
	private final Array<FairyObject> fairies;
	private final Array<FairyObject> bigFairies;
	private final Array<FairyObject> badFairies;
	private final ArrayList<AbstractGameObject> toRemoveList = new ArrayList<AbstractGameObject>();
	private int highscore;
	private String highscoreName;
	private BitmapFont highscoreBitmapFont;
	private BitmapFont redBitmapFont;
	private BitmapFont greenBitmapFont;
	private final SmackCntPic smackCounterPic;
	private static Vector2 SIZE = new Vector2(237, 46);
	private final PlayerObject playerObject;
	private final Smacker smacker;
	private float feedbackTime = 0.0f;
	private float fairySpawnTimer = 0.0f;
	private float badFairySpawnTimer = 0.0f;
	private float bigFairySpawnTimer = 0.0f;
	private int fairySpawnStage = 0;
	private float fairySpawnSpeed = 1.0f;
	private GlyphLayout highScoreLayout;
	private final GlyphLayout gameOverLayout;
	private final GlyphLayout retryLayout;
	private final GlyphLayout menuLayout;
	private final GlyphLayout addedTimeLayout;
	private final GlyphLayout addedPointsLayout;
	private String addedTime = "", addedPoints = "";
	private final Timer timer;
	private long timeElapsed = (long) GameConstants.TIMEOUT * 1000;
	private boolean gameEnded = false;
	private int currentMenuItem = 0;

	public Vector2 worldPixelSize = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);

	public World(PlayScreen playScreen, SpriteBatch batch) {
		this.batch = batch;
		this.abstractGameObjects = new Array<AbstractGameObject>();
		this.collectiblePixieDusts = new Array<CollectiblePixieDust>();
		this.fairies = new Array<FairyObject>();
		this.bigFairies = new Array<FairyObject>();
		this.badFairies = new Array<FairyObject>();
		this.playScreen = playScreen;

		smackCounterPic = new SmackCntPic(new Vector2(PixieSmackGame.MENU_GAME_WIDTH / 2f - 100, PixieSmackGame.MENU_GAME_HEIGHT - 70), this, World.SIZE);
		abstractGameObjects.add(smackCounterPic);

		playerObject = new PlayerObject(new Vector2(PixieSmackGame.MENU_GAME_WIDTH / 2, 50), this);
		abstractGameObjects.add(playerObject);

		smacker = new Smacker(Vector2.Zero, this);
		abstractGameObjects.add(smacker);

		highscore = 0;
		highscoreName = "0";
		highscoreBitmapFont = new BitmapFont();
		highscoreBitmapFont = playScreen.getGdxAssetManager().get(ImageConstants.FOLDER_GENERAL + "Ravie_42.fnt");
		highscoreBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		highscoreBitmapFont.setColor(BasicConstants.COLOR_PINK);
		highScoreLayout = new GlyphLayout(highscoreBitmapFont, highscoreName);
		gameOverLayout = new GlyphLayout(highscoreBitmapFont, "Game Over");
		retryLayout = new GlyphLayout(highscoreBitmapFont, "Retry?");
		menuLayout = new GlyphLayout(highscoreBitmapFont, "Menu");
		addedTimeLayout = new GlyphLayout(highscoreBitmapFont, this.addedTime);
		this.addedPointsLayout = new GlyphLayout(highscoreBitmapFont, this.addedPoints);

		this.redBitmapFont = new BitmapFont();
		this.redBitmapFont = playScreen.getGdxAssetManager().get(ImageConstants.FOLDER_GENERAL + "Ravie_42.fnt");
		this.redBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		this.redBitmapFont.setColor(BasicConstants.COLOR_RED);

		this.greenBitmapFont = new BitmapFont();
		this.greenBitmapFont = playScreen.getGdxAssetManager().get(ImageConstants.FOLDER_GENERAL + "Ravie_42.fnt");
		this.greenBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		this.greenBitmapFont.setColor(BasicConstants.COLOR_GREEN);

		timer = new Timer(new Vector2(50f, PixieSmackGame.MENU_GAME_HEIGHT - 80), this, new Vector2(80, 80));
		abstractGameObjects.add(timer);
	}

	public void update(float delta) {
		if (gameEnded) {
			return;
		}
		this.timeElapsed -= delta * 1000;
		// this.mmss = String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(this.timeElapsed) % TimeUnit.HOURS.toMinutes(1)),
		// Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(this.timeElapsed) % TimeUnit.MINUTES.toSeconds(1)));
		// this.mmssLayout.setText(this.highscoreBitmapFont, this.mmss);
		for (final AbstractGameObject go : abstractGameObjects) {
			go.update(delta); // default implementation only updates animTime
		}

		for (final FairyObject fairy : fairies) {
			fairy.update(delta);
		}

		for (final FairyObject fairy : badFairies) {
			fairy.update(delta);
		}
		for (final FairyObject fairy : bigFairies) {
			fairy.update(delta);
		}

		for (final CollectiblePixieDust pi : collectiblePixieDusts) {
			pi.update(delta);
		}
		this.spawnRandomFairies(delta);

		for (final AbstractGameObject element : this.toRemoveList) {
			if (element instanceof FairyObject) {
				this.fairies.removeValue((FairyObject) element, true);
			} else if (element instanceof AbstractGameObject) {
				this.abstractGameObjects.removeValue(element, true);
			}
		}
		toRemoveList.clear();
	}

	private void spawnRandomFairies(float delta) {
		this.fairySpawnTimer += delta;
		this.bigFairySpawnTimer += delta;
		this.badFairySpawnTimer += delta;
		if (fairies.size < GameConstants.MAX_FAIRIES && fairySpawnTimer >= GameConstants.FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.fairySpawnTimer = 0.0f;
			final float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			final float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			final FairyObject fairy = new FairyObject(new Vector2(xSpawn, ySpawn), this);
			fairies.add(fairy);
		}

		if (bigFairies.size < GameConstants.MAX_BIG_FAIRIES && bigFairySpawnTimer >= GameConstants.BIG_FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.bigFairySpawnTimer = 0.0f;
			final float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			final float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			final BigFairyObject fairy = new BigFairyObject(new Vector2(xSpawn, ySpawn), this);
			bigFairies.add(fairy);
		}

		if (badFairies.size < GameConstants.MAX_BAD_FAIRIES && badFairySpawnTimer >= GameConstants.BAD_FAIRY_SPAWN_THRESHOLD * fairySpawnSpeed) {
			this.badFairySpawnTimer = 0.0f;
			final float xSpawn = randInRange(GameConstants.FAIRY_MIN_X, GameConstants.FAIRY_MAX_X);
			final float ySpawn = randInRange(GameConstants.FAIRY_MIN_Y, GameConstants.FAIRY_MAX_Y);
			final BadFairyObject fairy = new BadFairyObject(new Vector2(xSpawn, ySpawn), this);
			badFairies.add(fairy);
		}

	}

	public void touchWithSmacker(Vector2 touchCoords) {
		if (!this.gameEnded && smacker.SmackCnt > 0) {
			smacker.smack();
			Iterator<FairyObject> iterator = fairies.iterator();
			while (iterator.hasNext()) {
				final FairyObject fairy = iterator.next();
				if (isWithinSmackBounds(touchCoords, fairy)) {
					fairy.onCollision();
				}

			}
			iterator = bigFairies.iterator();
			while (iterator.hasNext()) {
				final FairyObject fairy = iterator.next();
				if (isWithinSmackBounds(touchCoords, fairy)) {
					fairy.onCollision();
				}

			}
			iterator = badFairies.iterator();
			while (iterator.hasNext()) {
				final FairyObject fairy = iterator.next();
				if (isWithinSmackBounds(touchCoords, fairy)) {
					fairy.onCollision();
				}

			}
		} else if (gameEnded) {
			if (Gdx.input.justTouched()) {
				// find the menu item ..
				final float pos = PixieSmackGame.MENU_GAME_HEIGHT - PixieSmackGame.MENU_GAME_HEIGHT / 2f + gameOverLayout.height / 2f
						- PixieSmackGame.MENU_GAME_WIDTH / 16f;
				final float pos2 = PixieSmackGame.MENU_GAME_WIDTH / 2f;
				if (touchCoords.y < pos + highscoreBitmapFont.getLineHeight() && touchCoords.y > pos && touchCoords.x > pos2 - retryLayout.width / 2f
						&& touchCoords.x < pos2 + retryLayout.width / 2f) {
					playScreen.getScreenManager().setCurrentState(ScreenEnum.NewGame, true);
				} else if (touchCoords.y < pos && touchCoords.y > pos - highscoreBitmapFont.getLineHeight() && touchCoords.x > pos2 - menuLayout.width / 2f
						&& touchCoords.x < pos2 + menuLayout.width / 2f) {
					playScreen.getScreenManager().setCurrentState(ScreenEnum.Menu, false);
				}
			}
		}
	}

	public void mouseOver(Vector2 hoverCoords) {
		final float pos = PixieSmackGame.MENU_GAME_HEIGHT - PixieSmackGame.MENU_GAME_HEIGHT / 2f + gameOverLayout.height / 2f - PixieSmackGame.MENU_GAME_WIDTH
				/ 16f;
		final float pos2 = PixieSmackGame.MENU_GAME_WIDTH / 2f;
		if (hoverCoords.y < pos + highscoreBitmapFont.getLineHeight() && hoverCoords.y > pos && hoverCoords.x > pos2 - retryLayout.width / 2f
				&& hoverCoords.x < pos2 + retryLayout.width / 2f) {
			currentMenuItem = 0;
		} else if (hoverCoords.y < pos && hoverCoords.y > pos - highscoreBitmapFont.getLineHeight() && hoverCoords.x > pos2 - menuLayout.width / 2f
				&& hoverCoords.x < pos2 + menuLayout.width / 2f) {
			currentMenuItem = 1;
		}
	}

	public void keyInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // JUST
			this.playScreen.pause = true;
			Gdx.input.setCursorCatched(false);
			this.playScreen.getScreenManager().setCurrentState(ScreenEnum.Menu, false);
			this.playScreen.getSoundManager().playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			currentMenuItem = (currentMenuItem + 1) % 2;
			playScreen.getSoundManager().playEvent("blip");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			currentMenuItem = (currentMenuItem - 1) % 2;
			if (currentMenuItem < 0) {
				currentMenuItem = 0;
			} else {
				playScreen.getSoundManager().playEvent("blip");
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (currentMenuItem == 0) {
				playScreen.getScreenManager().setCurrentState(ScreenEnum.NewGame, true);
			} else if (currentMenuItem == 1) {
				playScreen.getScreenManager().setCurrentState(ScreenEnum.Menu, false);
			}
		}
	}

	public static float randInRange(float min, float max) {
		return (float) (min + (Math.random() * ((1 + max) - min)));
	}

	private void spawnDust(Vector2 position) {
		final CollectiblePixieDust collectiblePixieDust = new CollectiblePixieDust(position, this);
		collectiblePixieDusts.add(collectiblePixieDust);
	}

	private void spawnBadDust(Vector2 position) {
		final CollectiblePixieDust collectiblePixieDust = new CollectiblePixieDust(position, this);
		collectiblePixieDust.setIsBadDust(true);
		collectiblePixieDusts.add(collectiblePixieDust);
	}

	private void spawnSpecialDust(Vector2 position) {
		final CollectiblePixieDust collectiblePixieDust = new CollectiblePixieDust(position, this);
		collectiblePixieDust.setIsSpecialDust(true);
		collectiblePixieDusts.add(collectiblePixieDust);
	}

	public void pixieDustMissed(CollectiblePixieDust collectiblePixieDust) {
		collectiblePixieDusts.removeValue(collectiblePixieDust, true);
	}

	private boolean isWithinSmackBounds(Vector2 touchPosition, AbstractGameObject dust) {
		return dust.position.dst(touchPosition) <= GameConstants.SMACKER_REACH;
	}

	public void pixieDustCollected(CollectiblePixieDust collectiblePixieDust) {
		// Give points and stuff
		int score = 0;
		if (collectiblePixieDust.isBadDust) {
			score = -20;
			highscore = this.highscore + score;
			this.addedPoints = String.valueOf(score).replace(".0", "");
			final float tmp0 = GameConstants.BAD_FAIRY_TIME_MINUS;
			if (this.feedbackTime > GameConstants.MAX_FEEDBACK_TIME) {
				this.feedbackTime = 0.0f;
			}
			this.timeElapsed -= tmp0 * 1000;
			this.addedTime = "-" + String.valueOf(tmp0 + " sec").replace(".0", "");
			if (this.timeElapsed < 0) {
				this.timeElapsed = 0;
			}
			if (this.timeElapsed > GameConstants.TIMEOUT * 1000) {
				this.timeElapsed = (long) GameConstants.TIMEOUT * 1000;
			}
			this.timer.addAnimTime(tmp0);
		} else if (collectiblePixieDust.isSpecialDust) {
			score = 60;
			highscore = this.highscore + score;
			this.addedPoints = "+" + String.valueOf(score).replace(".0", "");
			final float tmp = GameConstants.BIG_FAIRY_TIME_PLUS;
			if (this.feedbackTime > GameConstants.MAX_FEEDBACK_TIME) {
				this.feedbackTime = GameConstants.MAX_FEEDBACK_TIME;
			}
			this.timeElapsed += tmp * 1000;
			this.addedTime = "+" + String.valueOf(tmp + " sec").replace(".0", "");
			if (this.timeElapsed < 0) {
				this.timeElapsed = 0;
			}
			if (this.timeElapsed > GameConstants.TIMEOUT * 1000) {
				this.timeElapsed = (long) GameConstants.TIMEOUT * 1000;
			}
			this.timer.addAnimTime(-tmp);
		} else {
			highscore += 10;
			if (this.feedbackTime > GameConstants.MAX_FEEDBACK_TIME) {
				this.feedbackTime = 0.0f;
			}
			this.addedTime = "";
			this.addedPoints = "+" + String.valueOf(10).replace(".0", "");
		}

		if (highscore == Math.pow(2, this.fairySpawnStage) * 100) {
			this.fairySpawnSpeed *= 0.85f;
			this.fairySpawnStage++;
		}

		highscoreName = "" + highscore;
		highScoreLayout = new GlyphLayout(highscoreBitmapFont, highscoreName);
		collectiblePixieDusts.removeValue(collectiblePixieDust, true);
	}

	public void pixieSmackedSpawnDustAnimation(FairyObject fairyObject) {
		spawnSmackAnim(fairyObject.position.cpy());
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

	}

	private void spawnSmackAnim(Vector2 position) {
		final SmackDustAnimation smackDustAnimation = new SmackDustAnimation(position, this);
		this.abstractGameObjects.add(smackDustAnimation);
	}

	public void render(float delta) {
		batch.begin();
		for (final AbstractGameObject go : abstractGameObjects) {
			go.render(batch);
			if (go.isToRemove()) {
				this.toRemoveList.add(go);
			}
		}
		for (final FairyObject fairy : fairies) {
			fairy.render(batch);
			if (fairy.isToRemove()) {
				this.toRemoveList.add(fairy);
			}
		}
		for (final FairyObject fairy : badFairies) {
			fairy.render(batch);
			if (fairy.isToRemove()) {
				this.toRemoveList.add(fairy);
			}
		}
		for (final FairyObject fairy : bigFairies) {
			fairy.render(batch);
			if (fairy.isToRemove()) {
				this.toRemoveList.add(fairy);
			}
		}
		for (final CollectiblePixieDust pi : collectiblePixieDusts) {
			pi.render(batch);
			if (pi.isToRemove()) {
				this.toRemoveList.add(pi);
			}
		}
		smacker.render(batch);
		smackCounterPic.render(batch);
		smackCounterPic.setAnimation(smacker.SmackCnt);

		highscoreBitmapFont.setColor(BasicConstants.COLOR_PINK);
		redBitmapFont.setColor(BasicConstants.COLOR_PINK);
		greenBitmapFont.setColor(BasicConstants.COLOR_PINK);
		highscoreBitmapFont.draw(batch, highScoreLayout, PixieSmackGame.MENU_GAME_WIDTH - 10 - highScoreLayout.width, PixieSmackGame.MENU_GAME_HEIGHT
				- highScoreLayout.height);
		if (this.addedTime.contains("-") && this.feedbackTime <= GameConstants.MAX_FEEDBACK_TIME) {
			redBitmapFont.setColor(BasicConstants.COLOR_RED);
			this.feedbackTime += delta;
			this.addedTimeLayout.setText(this.redBitmapFont, this.addedTime);
			redBitmapFont.draw(batch, addedTime, 10, PixieSmackGame.MENU_GAME_HEIGHT - (addedTimeLayout.height * 2));
		}
		if (this.addedTime.contains("+") && this.feedbackTime <= GameConstants.MAX_FEEDBACK_TIME) {
			greenBitmapFont.setColor(BasicConstants.COLOR_GREEN);
			this.feedbackTime += delta;
			this.addedTimeLayout.setText(this.greenBitmapFont, this.addedTime);
			greenBitmapFont.draw(batch, addedTime, 10, PixieSmackGame.MENU_GAME_HEIGHT - (addedTimeLayout.height * 2));
		}

		if (this.addedPoints.contains("-") && this.feedbackTime <= GameConstants.MAX_FEEDBACK_TIME) {
			redBitmapFont.setColor(BasicConstants.COLOR_RED);
			this.feedbackTime += delta;
			this.addedTimeLayout.setText(this.redBitmapFont, this.addedTime);
			this.addedPointsLayout.setText(this.highscoreBitmapFont, this.addedPoints);
			redBitmapFont.draw(batch, addedPointsLayout, PixieSmackGame.MENU_GAME_WIDTH - this.addedPointsLayout.width, PixieSmackGame.MENU_GAME_HEIGHT
					- (addedPointsLayout.height * 2));
		}
		if (this.addedPoints.contains("+") && this.feedbackTime <= GameConstants.MAX_FEEDBACK_TIME) {
			greenBitmapFont.setColor(BasicConstants.COLOR_GREEN);
			this.feedbackTime += delta;
			this.addedTimeLayout.setText(this.greenBitmapFont, this.addedTime);
			this.addedPointsLayout.setText(this.highscoreBitmapFont, this.addedPoints);
			greenBitmapFont.draw(batch, addedPointsLayout, PixieSmackGame.MENU_GAME_WIDTH - this.addedPointsLayout.width, PixieSmackGame.MENU_GAME_HEIGHT
					- (addedPointsLayout.height * 2));
		}
		highscoreBitmapFont.setColor(BasicConstants.COLOR_PINK);
		if ((timeElapsed / 1000f) <= 0) {
			if (!gameEnded) {
				final Preferences prefs = Gdx.app.getPreferences("Highscores");
				if (!prefs.contains("highScore1")) {
					prefs.putInteger("highScore1", 0);
					prefs.putInteger("highScore2", 0);
					prefs.putInteger("highScore3", 0);
					prefs.putInteger("highScore4", 0);
					prefs.putInteger("highScore5", 0);
					prefs.flush();
				}
				final int highscore1 = prefs.getInteger("highScore1");
				final int highscore2 = prefs.getInteger("highScore2");
				final int highscore3 = prefs.getInteger("highScore3");
				final int highscore4 = prefs.getInteger("highScore4");
				final int highscore5 = prefs.getInteger("highScore5");
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
			highscoreBitmapFont.draw(batch, gameOverLayout, PixieSmackGame.MENU_GAME_WIDTH / 2f - gameOverLayout.width / 2f, PixieSmackGame.MENU_GAME_HEIGHT
					/ 2f + gameOverLayout.height / 2f + PixieSmackGame.MENU_GAME_WIDTH / 16f);
			if (0 == currentMenuItem) {
				highscoreBitmapFont.setColor(0.8f, 0.0f, 0.7f, 1f);
				retryLayout.setText(highscoreBitmapFont, "Try Again");
			} else {
				highscoreBitmapFont.setColor(BasicConstants.COLOR_PINK);
				retryLayout.setText(highscoreBitmapFont, "Try Again");
			}
			highscoreBitmapFont.draw(batch, retryLayout, PixieSmackGame.MENU_GAME_WIDTH / 2f - retryLayout.width / 2f, PixieSmackGame.MENU_GAME_HEIGHT / 2f
					+ retryLayout.height / 2f);
			if (1 == currentMenuItem) {
				highscoreBitmapFont.setColor(0.8f, 0.0f, 0.7f, 1f);
				menuLayout.setText(highscoreBitmapFont, "Menu");
			} else {
				highscoreBitmapFont.setColor(BasicConstants.COLOR_PINK);
				menuLayout.setText(highscoreBitmapFont, "Menu");
			}
			highscoreBitmapFont.draw(batch, menuLayout, PixieSmackGame.MENU_GAME_WIDTH / 2f - menuLayout.width / 2f, PixieSmackGame.MENU_GAME_HEIGHT / 2f
					+ menuLayout.height / 2f - PixieSmackGame.MENU_GAME_WIDTH / 16f);
			highscoreBitmapFont.setColor(BasicConstants.COLOR_PINK);
		}
		batch.end();
	}
}