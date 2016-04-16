package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class Koerbchen extends SkeletonControlledObject {

    static Vector2 SIZE = new Vector2(100, 70);
    static float PICKUP_DISTANCE = 70;

    public Koerbchen(Vector2 position, World world) {
        super(position, world, SIZE);
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.PLAYER_SPRITE_PATH, 0.3f, 100, 70);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_up.png", 0.3f, 100, 70);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_down.png", 0.3f, 100, 70);
        this.movingLeftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_left.png", 0.3f, 100, 70);
        this.movingRightAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_right.png", 0.3f, 100, 70);
    }

    private void collectPixieDust() {
        Iterator<PixieDust> iterator = world.pixieDusts.iterator();
        while (iterator.hasNext()) {
            PixieDust dust = iterator.next();
            if (isWithinPickupBounds(dust)) {
                dust.collect();
            }
        }
    }

    private boolean isWithinPickupBounds(GameObject dust) {
        boolean nearEnuff = dust.position.dst(this.position) <= PICKUP_DISTANCE;
        boolean isOnTop = dust.position.y + 20 > this.position.y;
        return nearEnuff && isOnTop;
    }
    
    public void render(float delta, SpriteBatch spriteBatch) {
        switch (heading) {
            case UP:
                frame = idleAnimation.getKeyFrame(animTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case RIGHT:
                frame = idleAnimation.getKeyFrame(animTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case DOWN:
                frame = idleAnimation.getKeyFrame(animTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            case LEFT:
                frame = idleAnimation.getKeyFrame(animTime, true);
                spriteBatch.draw(frame, position.x, position.y);
                break;
            default:
                frame = idleAnimation.getKeyFrame(animTime, true);
                spriteBatch.draw(frame, position.x, position.y);
        }
        
        //Check for collisions with pixie dust
        collectPixieDust();
    }
}