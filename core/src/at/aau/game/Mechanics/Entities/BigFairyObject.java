package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.States.DirectionX;
import at.aau.game.Mechanics.States.DirectionY;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Kevin Herkt
 */
public class BigFairyObject extends FairyObject {
	private TextureRegion tempFrame;

	public BigFairyObject(Vector2 position, World world) {
		super(position, world);
		this.health = 10;
		this.startPosition = this.position.cpy();
		float rnd = random.nextFloat();
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
		speed = 0.2f;
		this.leftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.BIG_FAIRY_SPRITE_PATH_LEFT, 0.3f, (int) this.size.x,
				(int) this.size.y);
		this.rightAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.BIG_FAIRY_SPRITE_PATH_RIGHT, 0.3f, (int) this.size.x,
				(int) this.size.y);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animTime += delta;
		if (this.isDead) {
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
		if (this.health <= 0) {
			this.isDead = true;
			this.world.gameplayScreen.parentGame.getSoundManager().playEvent("dead");
			world.pixieSmacked(this);
		}
	}

	@Override
	public void update(float delta) {
		checkYcoord();
		checkXcoord();

		this.checkWorldBorders();
		super.update(delta);
	}
}