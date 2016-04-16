package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import at.aau.game.Mechanics.States.DirectionX;
import at.aau.game.Mechanics.States.DirectionY;

import com.badlogic.gdx.math.Vector2;


public abstract class MoveableObject extends GameObject {
	protected Vector2 direction;
	Float speed;
	Movement movement;
	Float animTime;
	protected DirectionX directionX = DirectionX.RIGHT;
	protected DirectionY directionY = DirectionY.UP;
	//protected DirectionX directionY = DirectionX.STOP;

    public MoveableObject(Vector2 position, World world, Vector2 size) {
        super(position, world, size);
        movement = Movement.IDLE;
        animTime = 0f;
    }

	@Override
	public void update(float delta) {
		super.update(delta);
		animTime += delta;
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
			animTime = 0f;
		}
	}

	public enum Movement {
		IDLE, MOVING
	}
}
