package at.aau.game.mechanic.entities.actors;

import at.aau.game.mechanic.World;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Herkt Kevin
 *
 */
public abstract class AbstractControlledObject extends AbstractMoveableObject {
	public AbstractControlledObject(Vector2 position, World world, Vector2 size) {
		super(position, world, size);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		handleInput();
		handleMovement(delta);
	}

	/*
	 * @Override public void render(float delta, SpriteBatch spriteBatch) { switch (heading) { // heading must be set accordingly: 1 - UP, 2 - Right, 3 - Down,
	 * 
	 * 4 - Left case UP: frame = movingUpAnimation.getKeyFrame(animTime, true); spriteBatch.draw(frame, position.x, position.y); break; case RIGHT: frame =
	 * movingRightAnimation.getKeyFrame(animTime, true); spriteBatch.draw(frame, position.x, position.y); break; case DOWN: frame =
	 * movingDownAnimation.getKeyFrame(animTime, true); spriteBatch.draw(frame, position.x, position.y); break; case LEFT: frame =
	 * movingLeftAnimation.getKeyFrame(animTime, true); spriteBatch.draw(frame, position.x, position.y); break; default: frame =
	 * idleAnimation.getKeyFrame(animTime, true); spriteBatch.draw(frame, position.x, position.y); } }
	 */
	protected abstract void handleInput();

	@Override
	protected abstract void handleMovement(float delta);
}