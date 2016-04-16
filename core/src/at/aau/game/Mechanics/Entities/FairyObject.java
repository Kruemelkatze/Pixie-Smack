package at.aau.game.Mechanics.Entities;

import java.util.Random;

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

    private Animation leftAnimation;
    private Animation rightAnimation;
    private float animTime = 0;

    private int health = 1;
    private final Vector2 startPosition;
    private Random random = new Random();
    private boolean isBlackFairy = false;

    static Vector2 SIZE = new Vector2(64, 64);
    private float speed = 3f;
    private TextureRegion tempFrame;

    public FairyObject(Vector2 position, World world) {
        super(position, world, SIZE);
        this.startPosition = this.position.cpy();
        float rnd = random.nextFloat();
        if (rnd < 0.33f) {
            this.directionX = DirectionX.LEFT;
            this.directionY = DirectionY.UP;
            this.speed = 2.5f;
        } else if (rnd < 0.66f) {
            this.directionX = DirectionX.LEFT;
            this.directionY = DirectionY.DOWN;
            this.speed = 1.5f;
        } else {
            this.directionX = DirectionX.RIGHT;
            this.directionY = DirectionY.DOWN;
            this.speed = 1.0f;
        }
        this.leftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/pixie-left-anim.png", 0.3f, (int) this.size.x,
                (int) this.size.y);
        this.rightAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/pixie-right-anim.png", 0.3f, (int) this.size.x,
                (int) this.size.y);


    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        animTime += delta;
        if (this.isDead) {
            return;
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
            this.position.x += this.speed;
        } else if (this.directionX.equals(DirectionX.LEFT)) {
            this.position.x -= this.speed;
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
            this.position.y += this.speed;
        } else if (this.directionY.equals(DirectionY.DOWN)) {
            this.position.y -= this.speed;
        }
    }
}