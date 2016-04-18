package at.aau.game.mechanic.entities.actors;

import java.util.Iterator;

import at.aau.game.GameConstants;
import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;
import at.aau.game.mechanic.entities.CollectiblePixieDust;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerObject extends AbstractControlledObject {
	private static Vector2 SIZE = new Vector2(100, 70);
	private static float PICKUP_DISTANCE = 70;
	private boolean moveUp, moveDown, moveLeft, moveRight;
	private Heading heading = Heading.IDLE;
	private final Animation idleAnimation;
	private TextureRegion currentFrame;

	private enum Heading {
		LEFT, RIGHT, UP, DOWN, IDLE;
	}

	public PlayerObject(Vector2 position, World world) {
		super(position, world, PlayerObject.SIZE);
		this.speed = 10f;
		this.idleAnimation = AbstractGameObject.getAnimationManager().loadAnimation(GameConstants.PLAYER_SPRITE_PATH, 0.2f, 100, 70);
	}

	private void collectPixieDust() {
		final Iterator<CollectiblePixieDust> iterator = world.collectiblePixieDusts.iterator();
		while (iterator.hasNext()) {
			final CollectiblePixieDust dust = iterator.next();
			if (isWithinPickupBounds(dust)) {
				dust.collect();
			}
		}
	}

	private boolean isWithinPickupBounds(AbstractGameObject dust) {
		final boolean nearEnuff = dust.position.dst(this.position) <= PlayerObject.PICKUP_DISTANCE;
		final boolean isOnTop = dust.position.y + 20 > this.position.y;
		return nearEnuff && isOnTop;
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		switch (heading) {
		case UP:
			currentFrame = idleAnimation.getKeyFrame(animTime, true);
			spriteBatch.draw(currentFrame, position.x, position.y);
			break;
		case RIGHT:
			currentFrame = idleAnimation.getKeyFrame(animTime, true);
			spriteBatch.draw(currentFrame, position.x, position.y);
			break;
		case DOWN:
			currentFrame = idleAnimation.getKeyFrame(animTime, true);
			spriteBatch.draw(currentFrame, position.x, position.y);
			break;
		case LEFT:
			currentFrame = idleAnimation.getKeyFrame(animTime, true);
			spriteBatch.draw(currentFrame, position.x, position.y);
			break;
		default:
			currentFrame = idleAnimation.getKeyFrame(animTime, true);
			spriteBatch.draw(currentFrame, position.x, position.y);
		}

		// Check for collisions with pixie dust
		collectPixieDust();
	}

	@Override
	protected void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
			moveDown = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			moveUp = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
			moveLeft = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
			moveRight = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) { // debug
			System.out.println(this.size);
			System.out.println(world.worldPixelSize);
			System.out.println(this.position);
			System.out.println();
		}
	}

	@Override
	protected void handleMovement(@SuppressWarnings("unused") float delta) {
		calcDirection();
		if (!direction.nor().isZero()) {
			if (direction.x > 0) {
				heading = Heading.RIGHT;
			} else if (direction.x < 0) {
				heading = Heading.LEFT;
			}
			if (direction.y > 0) {
				heading = Heading.UP;
			} else if (direction.y < 0) {
				heading = Heading.DOWN;
			}
			movement = Movement.MOVING;
		} else {
			movement = Movement.IDLE;
			heading = Heading.IDLE;
		}

		this.position.add(direction.nor().scl(speed));

		if (this.position.x < 0) {
			this.position.x = 0;
		} else if (this.position.x > world.worldPixelSize.x - size.x) {
			this.position.x = world.worldPixelSize.x - size.x;
		}

		if (this.position.y < 0) {
			this.position.y = 0;
		} else if (this.position.y > world.worldPixelSize.y - size.y) {
			this.position.y = world.worldPixelSize.y - size.y;
		}
	}

	private void calcDirection() {
		direction = new Vector2(0, 0);
		if (moveUp && !moveDown) {
			direction.y = 1;
		} else if (!moveUp && moveDown) {
			direction.y = -1;
		}

		if (moveLeft && !moveRight) {
			direction.x = -1;
		} else if (!moveLeft && moveRight) {
			direction.x = 1;
		}
		moveDown = moveUp = moveRight = moveLeft = false;
	}
}