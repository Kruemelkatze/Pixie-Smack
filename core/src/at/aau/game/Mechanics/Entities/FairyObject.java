package at.aau.game.Mechanics.Entities;

import java.util.Random;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.States.DirectionX;
import at.aau.game.Mechanics.States.DirectionY;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Kevin Herkt
 */
public class FairyObject extends MoveableObject {
	protected Animation dead1;
	protected Animation dead2;
	protected Animation leftAnimation;
	protected Animation rightAnimation;
	protected float animTime = 0;
	private float deathSpeed = 1 + (int)(Math.random() * ((2.5f - 1) + 1));

	protected int health = 1;
	protected Vector2 startPosition;
	protected static Random random = new Random();

	protected static Vector2 SIZE = new Vector2(64, 64);
	protected float speed = 3f;
	private TextureRegion tempFrame;

	public FairyObject(Vector2 position, World world) {
		super(position, world, SIZE);
		this.startPosition = this.position.cpy();
		float rnd = random.nextFloat();
		if (rnd < 0.33f) {
			this.directionX = DirectionX.LEFT;
			this.directionY = DirectionY.UP;
			this.speed = 2.5f;
		} else if (rnd < 0.66f) {
			this.directionX = DirectionX.LEFT;
			this.directionY = DirectionY.DOWN;
			this.speed = 1.5f;
		} else {
			this.directionX = DirectionX.RIGHT;
			this.directionY = DirectionY.DOWN;
			this.speed = 1.0f;
		}
		this.leftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/pixie-left-anim.png",
				0.3f, (int) this.size.x, (int) this.size.y);
		this.rightAnimation = world.gameplayScreen.parentGame.getAnimator()
				.loadAnimation("gameplay/pixie-right-anim.png", 0.3f, (int) this.size.x, (int) this.size.y);
		this.dead1 = world.gameplayScreen.parentGame.getAnimator()
				.loadAnimation(GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT, 0.1f, (int) this.size.x, (int) this.size.y);
		dead1.setPlayMode(PlayMode.NORMAL);
		this.dead2 = world.gameplayScreen.parentGame.getAnimator().loadAnimation(
				GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, 2f, (int) this.size.x, (int) this.size.y);
		dead2.setPlayMode(PlayMode.NORMAL);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animTime += delta;
		if (this.isDead) {
			tempFrame = dead1.getKeyFrame(animTime, true);
			if (dead1.isAnimationFinished(animTime)) {
				tempFrame = dead2.getKeyFrame(animTime, true);
				if (dead2.isAnimationFinished(animTime)) {
					this.toRemove = true;
					this.world.fairies.removeValue(this, true);
				} else {
					spriteBatch.draw(tempFrame, position.x, position.y);
				}
			} else {
				spriteBatch.draw(tempFrame, position.x, position.y);
			}
			return;
		}
		switch (this.directionX) {
		case LEFT:
			tempFrame = leftAnimation.getKeyFrame(animTime, true);
			break;
		case RIGHT:
			tempFrame = rightAnimation.getKeyFrame(animTime, true);
			break;
		case STOP:
			tempFrame = rightAnimation.getKeyFrame(animTime, true);
			break;
		default:
			break;
		}

		spriteBatch.draw(tempFrame, position.x, position.y);
		// spriteBatch.draw(tempFrame, position.x, position.y,
		// tempFrame.getRegionWidth() / GameConstants.PIXEL_TO_METER,
		// tempFrame.getRegionHeight()
		// / GameConstants.PIXEL_TO_METER);
	}

	public void onCollision() {
		this.health--;
		if (this.health == 0) {
			this.animTime = 0.0f;
			this.isDead = true;
			world.pixieSmacked(this);
		}
	}

	@Override
	public void update(float delta) {
		if (isDead) {
			if (this.dead1.isAnimationFinished(animTime)) {
				this.position.y -= 1.5f * deathSpeed;
			} else {
				this.position.y -= 0.1f;
			}
		} else {
			checkYcoord();
			checkXcoord();
		}

		this.checkWorldBorders();
		super.update(delta);
	}

	protected void checkXcoord() {
		if (this.position.x >= startPosition.x + GameConstants.FAIRY_MAX_X_OFFSET
				|| this.position.x >= world.pixelSize.x) {
			this.directionX = DirectionX.LEFT;
		} else if (this.position.x <= startPosition.x - GameConstants.FAIRY_MAX_X_OFFSET || this.position.x <= 0) {
			this.directionX = DirectionX.RIGHT;
		}

		if (this.directionX.equals(DirectionX.RIGHT)) {
			this.position.x += this.speed;
		} else if (this.directionX.equals(DirectionX.LEFT)) {
			this.position.x -= this.speed;
		}
	}

	protected void checkYcoord() {
		if (this.directionY.equals(DirectionY.UP)
				&& (this.position.y >= startPosition.y + GameConstants.FAIRY_MAX_Y_OFFSET
						|| this.position.y >= world.pixelSize.y)) {
			this.directionY = DirectionY.DOWN;
		} else if (this.directionY.equals(DirectionY.DOWN)
				&& (this.position.y <= startPosition.y - GameConstants.FAIRY_MAX_Y_OFFSET || this.position.y <= 0)) {
			this.directionY = DirectionY.UP;
		}

		if (this.directionY.equals(DirectionY.UP)) {
			this.position.y += this.speed;
		} else if (this.directionY.equals(DirectionY.DOWN)) {
			this.position.y -= this.speed;
		}
	}
}