package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class Koerbchen extends SkeletonControlledObject {

    static Vector2 SIZE = new Vector2(100, 70);
    static float PICKUP_DISTANCE = 80;

    public Koerbchen(Vector2 position, World world) {
        super(position, world, SIZE);
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_idle.png", 0.3f, 100, 70);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_up.png", 0.3f, 100, 70);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_down.png", 0.3f, 100, 70);
        this.movingLeftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_left.png", 0.3f, 100, 70);
        this.movingRightAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_right.png", 0.3f, 100, 70);
    }

    /**
     * Your typical render function
     *
     * @param delta
     * @param spriteBatch heading must be set accordingly: 1 - UP, 2 - Right, 3 - Down, 4 - Left
     */
    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        super.render(delta, spriteBatch);

        //Check for collisions with pixie dust
        collectPixieDust();
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
        return dust.position.dst(this.position) <= PICKUP_DISTANCE;
    }
}
