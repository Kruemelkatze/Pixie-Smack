package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.States.Direction;
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
    private int health = 2;
    private boolean isDead = false;
    private final Vector2 startPosition;

    static Vector2 SIZE = new Vector2(64, 64);

    public FairyObject(Vector2 position, World world) {
        super(position, world, SIZE);
        this.startPosition = position.cpy();
        this.directionEnum = Direction.UP;
        this.speed = 2f;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.FAIRY_SPRITE_PATH, 0.0f, 64, 64);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        if (this.isDead) {
            return;
        }
        switch (this.directionEnum) {
            case DOWN:
                tempFrame = idleAnimation.getKeyFrame(movingTime, true);
                break;
            case LEFT:
                tempFrame = idleAnimation.getKeyFrame(movingTime, true);
                break;
            case RIGHT:
                tempFrame = idleAnimation.getKeyFrame(movingTime, true);
                break;
            case STOP:
                tempFrame = idleAnimation.getKeyFrame(movingTime, true);
                break;
            case UP:
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
        }
    }

    @Override
    public void update(float delta) {
        float yDirection = +1f;
        if (this.directionEnum.equals(Direction.UP) && this.position.y >= startPosition.y + GameConstants.FAIRY_MAX_Y_OFFSET) {
            this.directionEnum = Direction.DOWN;
            System.out.println(this.position.y + " " + startPosition.y);
        } else if (this.directionEnum.equals(Direction.DOWN) && this.position.y < startPosition.y - GameConstants.FAIRY_MAX_Y_OFFSET) {
            this.directionEnum = Direction.UP;
        }

        if (this.directionEnum.equals(Direction.UP)) {
            this.position.y += 1 * yDirection;
        } else if (this.directionEnum.equals(Direction.DOWN)) {
            this.position.y -= 1 * yDirection;
        }

        this.position.x += 1;

        super.update(delta);
    }
}