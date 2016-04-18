package at.aau.game.mechanic.entities.actors;

import at.aau.game.GameConstants;
import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;
import at.aau.game.mechanic.states.DirectionX;
import at.aau.game.mechanic.states.DirectionY;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Kevin Herkt
 */
public class BigFairyObject extends FairyObject {
	public BigFairyObject(Vector2 position, World world) {
		super(position, world);
		this.health = GameConstants.BIG_FAIRIES_HEALTH;
		this.startPosition = this.position.cpy();
		final float rnd = AbstractGameObject.RANDOM.nextFloat();
		if (rnd < 0.33f) {
			this.directionX = DirectionX.LEFT;
			this.directionY = DirectionY.UP;
			this.speed = 2.5f * 1.5f;
		} else if (rnd < 0.66f) {
			this.directionX = DirectionX.LEFT;
			this.directionY = DirectionY.DOWN;
			this.speed = 1.5f * 1.5f;
		} else {
			this.directionX = DirectionX.RIGHT;
			this.directionY = DirectionY.DOWN;
			this.speed = 1.0f * 1.5f;
		}

		this.leftAnimation = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.BIG_FAIRY_SPRITE_PATH_LEFT, 0.3f, (int) this.size.x,
				(int) this.size.y);
		this.rightAnimation = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.BIG_FAIRY_SPRITE_PATH_RIGHT, 0.3f, (int) this.size.x,
				(int) this.size.y);

		this.damagedLeft = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.DAMAGED_BIG_FAIRY_SPRITE_PATH_LEFT, 0.1f, (int) this.size.x,
				(int) this.size.y);
		damagedLeft.setPlayMode(PlayMode.NORMAL);

		this.damagedRight = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.DAMAGED_BIG_FAIRY_SPRITE_PATH_RIGHT, 0.1f, (int) this.size.x,
				(int) this.size.y);
		damagedRight.setPlayMode(PlayMode.NORMAL);

		this.dead2 = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.MAD_BIG_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, 2f, (int) this.size.x,
				(int) this.size.y);
		dead2.setPlayMode(PlayMode.NORMAL);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		if (this.isDead) {
			tempFrame = dead2.getKeyFrame(animTime, true);
			if (dead2.isAnimationFinished(animTime)) {
				this.toRemove = true;
			} else {
				spriteBatch.draw(tempFrame, position.x, position.y);
			}
			return;
		}

		if (this.isDamaged) {
			switch (this.directionX) {
			case LEFT:
				tempFrame = damagedLeft.getKeyFrame(animTime, true);
				spriteBatch.draw(tempFrame, position.x, position.y);
				return;
			case RIGHT:
				tempFrame = damagedRight.getKeyFrame(animTime, true);
				spriteBatch.draw(tempFrame, position.x, position.y);
				return;
			case STOP:
				tempFrame = damagedRight.getKeyFrame(animTime, true);
				break;
			default:
				break;
			}
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

	@Override
	public void onCollision() {
		this.health--;
		playHitSound();
		world.pixieSmackedSpawnDustAnimation(this);
		if (this.health > 0 && this.health < GameConstants.BIG_FAIRIES_HEALTH) {
			this.animTime = 0.0f;
			this.isDamaged = true;
		}
		if (this.health == 0) {
			this.animTime = 0.0f;
			this.isDead = true;
			world.pixieSmacked(this);
		}
	}
}