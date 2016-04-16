package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.States.DirectionX;
import at.aau.game.Mechanics.States.DirectionY;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Kevin Herkt
 */
public class FairyObject extends MoveableObject {
	private Animation idleAnimation;
	private TextureRegion tempFrame;
	private int health = 1;
	private final Vector2 startPosition;

	static Vector2 SIZE = new Vector2(64, 64);

	public FairyObject(Vector2 position, World world) {
		super(position, world, SIZE);
		this.startPosition = position.cpy();
		this.speed = 2f;
		this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.FAIRY_SPRITE_PATH, 0.0f, (int) this.size.x,
				(int) this.size.y);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		if (this.isDead) {
			return;
		}
		switch (this.directionX) {
		case LEFT:
			tempFrame = idleAnimation.getKeyFrame(movingTime, true);
			break;
		case RIGHT:
			tempFrame = idleAnimation.getKeyFrame(movingTime, true);
			break;
		case STOP:
			tempFrame = idleAnimation.getKeyFrame(movingTime, true);
			break;
		default:
			break;
		}

		spriteBatch.draw(tempFrame, position.x, position.y);
		// spriteBatch.draw(tempFrame, position.x, position.y, tempFrame.getRegionWidth() / GameConstants.PIXEL_TO_METER, tempFrame.getRegionHeight()
		// / GameConstants.PIXEL_TO_METER);
	}

    public void onCollision() {
        this.health--;
        if (this.health <= 0) {
            this.isDead = true;
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

	private void checkXcoord() {
		if (this.position.x >= startPosition.x + GameConstants.FAIRY_MAX_X_OFFSET || this.position.x >= world.pixelSize.x) {
			this.directionX = DirectionX.LEFT;
		} else if (this.position.x <= startPosition.x - GameConstants.FAIRY_MAX_X_OFFSET || this.position.x <= 0) {
			this.directionX = DirectionX.RIGHT;
		}

		if (this.directionX.equals(DirectionX.RIGHT)) {
			this.position.x += 1;
		} else if (this.directionX.equals(DirectionX.LEFT)) {
			this.position.x -= 1;
		}
	}

	private void checkYcoord() {
		if (this.directionY.equals(DirectionY.UP)
				&& (this.position.y >= startPosition.y + GameConstants.FAIRY_MAX_Y_OFFSET || this.position.y >= world.pixelSize.y)) {
			this.directionY = DirectionY.DOWN;
		} else if (this.directionY.equals(DirectionY.DOWN) && (this.position.y <= startPosition.y - GameConstants.FAIRY_MAX_Y_OFFSET || this.position.y <= 0)) {
			this.directionY = DirectionY.UP;
		}

		if (this.directionY.equals(DirectionY.UP)) {
			this.position.y += 1;
		} else if (this.directionY.equals(DirectionY.DOWN)) {
			this.position.y -= 1;
		}
	}
}