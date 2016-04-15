package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import at.aau.game.Mechanics.States.Direction;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Veit on 07.02.2016.
 */
public abstract class MoveableObject extends GameObject {
	protected Vector2 direction;
	Float speed;
	Movement movement;
	Float movingTime;
	protected Direction directionEnum = Direction.STOP;

	public MoveableObject(Vector2 position, World world) {
		super(position, world);
		movement = Movement.IDLE;
		movingTime = 0f;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		movingTime += delta;
	}

	protected void handleMovement(Float delta) {
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
			movingTime = 0f;
		}
	}

	public enum Movement {
		IDLE, MOVING
	}
}