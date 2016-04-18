package at.aau.game.mechanic.entities.actors;

import at.aau.game.GameConstants;
import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;
import at.aau.game.mechanic.states.DirectionX;
import at.aau.game.mechanic.states.DirectionY;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Kevin Herkt
 */
public class FairyObject extends AbstractMoveableObject {
	protected Animation damagedLeft;
	protected Animation damagedRight;
	protected Animation dead2;
	protected Animation leftAnimation;
	protected Animation rightAnimation;
	private final float deathSpeed = 1 + (int) (Math.random() * ((2.5f - 1) + 1));

	protected int health = 1;
	protected Vector2 startPosition;

	protected static Vector2 SIZE = new Vector2(64, 64);
	protected float speed = 3f;
	protected TextureRegion tempFrame;

	public FairyObject(Vector2 position, World world) {
		super(position, world, FairyObject.SIZE);
		this.startPosition = this.position.cpy();
		final float rnd = AbstractGameObject.RANDOM.nextFloat();
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
		this.leftAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/pixie-left-anim.png", 0.3f, (int) this.size.x, (int) this.size.y);
		this.rightAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/pixie-right-anim.png", 0.3f, (int) this.size.x,
				(int) this.size.y);
		this.dead2 = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, 2f, (int) this.size.x,
				(int) this.size.y);
		dead2.setPlayMode(PlayMode.NORMAL);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		if (this.isToRemove()) {
			return;
		}
		if (this.isDead) {
			tempFrame = dead2.getKeyFrame(animTime, true);
			if (dead2.isAnimationFinished(animTime)) {
				this.toRemove = true;
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
	}

	public void onCollision() {
		this.health--;
		playHitSound();
		if (this.health == 0) {
			this.animTime = 0.0f;
			this.isDead = true;
			world.pixieSmackedSpawnDustAnimation(this);
			world.pixieSmacked(this);
		}
	}

	protected void playHitSound() {
		final int randomNum = AbstractGameObject.RANDOM.nextInt(GameConstants.SmackSoundsCount) + 1;
		world.playScreen.getSoundManager().playEvent("smack" + randomNum);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (isDead) {
			this.position.y -= 1.5f * deathSpeed;
		} else {
			checkYcoord();
			checkXcoord();
		}

		// this.checkWorldBorders(); needs the world pixelSize
	}

	protected void checkXcoord() {
		if (this.position.x >= startPosition.x + GameConstants.FAIRY_MAX_X_OFFSET || this.position.x >= world.worldPixelSize.x) {
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
				&& (this.position.y >= startPosition.y + GameConstants.FAIRY_MAX_Y_OFFSET || this.position.y >= world.worldPixelSize.y)) {
			this.directionY = DirectionY.DOWN;
		} else if (this.directionY.equals(DirectionY.DOWN) && (this.position.y <= startPosition.y - GameConstants.FAIRY_MAX_Y_OFFSET || this.position.y <= 0)) {
			this.directionY = DirectionY.UP;
		}

		if (this.directionY.equals(DirectionY.UP)) {
			this.position.y += this.speed;
		} else if (this.directionY.equals(DirectionY.DOWN)) {
			this.position.y -= this.speed;
		}
	}
}