package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.math.Vector2;


public abstract class MoveableObject extends GameObject {
    Vector2 direction;
    Float speed;
    Movement movement;
    Float movingTime;

    public MoveableObject(Vector2 position, World world, Vector2 size) {
        super(position, world, size);
        movement = Movement.IDLE;
        movingTime = 0f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        movingTime += delta;
    }

    void handleMovement(Float delta) {
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
