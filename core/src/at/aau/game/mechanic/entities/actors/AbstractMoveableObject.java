package at.aau.game.mechanic.entities.actors;

import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;
import at.aau.game.mechanic.states.DirectionX;
import at.aau.game.mechanic.states.DirectionY;

import com.badlogic.gdx.math.Vector2;

public abstract class AbstractMoveableObject extends AbstractGameObject {
	protected Vector2 direction;
	protected float speed;
	protected Movement movement;
	protected DirectionX directionX = DirectionX.RIGHT;
	protected DirectionY directionY = DirectionY.UP;

	public AbstractMoveableObject(Vector2 position, World world, Vector2 size) {
		super(position, world, size);
		this.direction = new Vector2();
		movement = Movement.IDLE;
		animTime = 0f;
	}

	protected void handleMovement(float delta) {
		if (this.direction == null) {
			throw new RuntimeException("direction of " + this.getClass().getSimpleName() + " is null");
		}
		this.position.add(direction.nor().scl(speed * delta));
		if (!(direction.nor().scl(speed * delta).isZero())) {
			changeMovementTo(Movement.MOVING);
		} else {
			changeMovementTo(Movement.IDLE);
		}
	}

	public void changeMovementTo(Movement movement) {
		if (this.movement != movement) {
			this.movement = movement;
			animTime = 0f;
		}
	}

	public enum Movement {
		IDLE, MOVING
	}
}
