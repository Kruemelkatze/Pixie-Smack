package at.aau.game.mechanic.entities.actors;

import at.aau.game.GameConstants;
import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;
import at.aau.game.mechanic.states.DirectionX;
import at.aau.game.mechanic.states.DirectionY;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BadFairyObject extends FairyObject {
	public BadFairyObject(Vector2 position, World world) {
		super(position, world);
		this.health = 1;
		this.startPosition = this.position.cpy();
		final float rnd = AbstractGameObject.RANDOM.nextFloat();
		if (rnd < 0.33f) {
			this.directionX = DirectionX.LEFT;
			this.directionY = DirectionY.UP;
			this.speed = 2.5f * 2;
		} else if (rnd < 0.66f) {
			this.directionX = DirectionX.LEFT;
			this.directionY = DirectionY.DOWN;
			this.speed = 1.5f * 2;
		} else {
			this.directionX = DirectionX.RIGHT;
			this.directionY = DirectionY.DOWN;
			this.speed = 1.0f * 2;
		}

		this.leftAnimation = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.EVIL_FAIRY_SPRITE_PATH_LEFT, 0.3f, (int) this.size.x,
				(int) this.size.y);
		this.rightAnimation = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.EVIL_FAIRY_SPRITE_PATH_RIGHT, 0.3f, (int) this.size.x,
				(int) this.size.y);

		this.dead2 = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.EVIL_MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, 2f, (int) this.size.x,
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
}