package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.States.DirectionX;
import at.aau.game.Mechanics.States.DirectionY;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Kevin Herkt
 */
public class BigFairyObject extends FairyObject {

	public BigFairyObject(Vector2 position, World world) {
		super(position, world);
		this.health = GameConstants.BIG_FAIRIES_HEALTH;
		this.startPosition = this.position.cpy();
		float rnd = random.nextFloat();
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

		this.leftAnimation = world.gameplayScreen.parentGame.getAnimator()
				.loadAnimation(GameConstants.BIG_FAIRY_SPRITE_PATH_LEFT, 0.3f, (int) this.size.x, (int) this.size.y);
		this.rightAnimation = world.gameplayScreen.parentGame.getAnimator()
				.loadAnimation(GameConstants.BIG_FAIRY_SPRITE_PATH_RIGHT, 0.3f, (int) this.size.x, (int) this.size.y);

		this.damagedLeft = world.gameplayScreen.parentGame.getAnimator().loadAnimation(
				GameConstants.DAMAGED_BIG_FAIRY_SPRITE_PATH_LEFT, 0.1f, (int) this.size.x, (int) this.size.y);
		damagedLeft.setPlayMode(PlayMode.NORMAL);
		
		this.damagedRight = world.gameplayScreen.parentGame.getAnimator().loadAnimation(
				GameConstants.DAMAGED_BIG_FAIRY_SPRITE_PATH_RIGHT, 0.1f, (int) this.size.x, (int) this.size.y);
		damagedRight.setPlayMode(PlayMode.NORMAL);
		
		this.dead2 = world.gameplayScreen.parentGame.getAnimator().loadAnimation(
				GameConstants.MAD_BIG_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, 2f, (int) this.size.x, (int) this.size.y);
		dead2.setPlayMode(PlayMode.NORMAL);
	}
	
	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		animTime += delta;
		if (this.isDead) {
			tempFrame = dead2.getKeyFrame(animTime, true);
			if (dead2.isAnimationFinished(animTime)) {
				this.toRemove = true;
				this.world.bigFairies.removeValue(this, true);
			} else {
				spriteBatch.draw(tempFrame, position.x, position.y);
			}
			return;
		}
		
		if (this.isDamaged) {
			switch (this.directionX){
			case LEFT:
				tempFrame = damagedLeft.getKeyFrame(animTime, true);
				spriteBatch.draw(tempFrame, position.x, position.y);
				return;
			case RIGHT:
				tempFrame = damagedRight.getKeyFrame(animTime, true);
				spriteBatch.draw(tempFrame, position.x, position.y);
				return;
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
		// spriteBatch.draw(tempFrame, position.x, position.y,
		// tempFrame.getRegionWidth() / GameConstants.PIXEL_TO_METER,
		// tempFrame.getRegionHeight()
		// / GameConstants.PIXEL_TO_METER);
	}
	
	@Override
	public void onCollision() {
		this.health--;
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