package at.aau.game.Mechanics.Entities;

import at.aau.game.PixieSmack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import at.aau.game.Mechanics.World;

/**
 * Created by Veit on 06.02.2016.
 */
public abstract class SkeletonControlledObject extends MoveableObject {

    private boolean moveUp, moveDown, moveLeft, moveRight;
    private Heading heading = Heading.IDLE;
    protected Animation idleAnimation;
    protected Animation movingUpAnimation;
    protected Animation movingDownAnimation;
    protected Animation movingLeftAnimation;
    protected Animation movingRightAnimation;
    private TextureRegion frame;

    public SkeletonControlledObject(Vector2 position, World world, Vector2 size) {
        super(position, world, size);
        this.speed = 10f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        handleInput();
        handleMovement(delta);

    }

    @Override
    protected void handleMovement(Float delta) {
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
        } else if (this.position.x > world.pixelSize.x - size.x) {
            this.position.x = world.pixelSize.x - size.x;
        }

        if (this.position.y < 0) {
            this.position.y = 0;
        } else if (this.position.y > world.pixelSize.y - size.y) {
            this.position.y = world.pixelSize.y - size.y;
        }
    }


    /**
     * Calculates the direction Vector
     */
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

    private void handleInput() {
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

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println(this.size);
            System.out.println(world.pixelSize);
            System.out.println(this.position);
            System.out.println();
        }
    }

    /**
     * Your typical render function
     *
     * @param delta
     * @param spriteBatch heading must be set accordingly: 1 - UP, 2 - Right, 3 - Down, 4 - Left
     */
    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        switch (heading) {
            case UP:
                frame = movingUpAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case RIGHT:
                frame = movingRightAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case DOWN:
                frame = movingDownAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case LEFT:
                frame = movingLeftAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            default:
                frame = idleAnimation.getKeyFrame(movingTime, true);
                spriteBatch.draw(frame, position.x, position.y);
        }
    }

    public enum Heading {
        LEFT, RIGHT, UP, DOWN, IDLE;
    }
}
