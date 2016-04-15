package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Koerbchen extends SkeletonControlledObject {

    static Vector2 SIZE = new Vector2(45, 64);

    public Koerbchen(Vector2 position, World world) {
        super(position, world, SIZE);
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_idle.png", 0.3f, 100, 70);
        this.movingUpAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_up.png", 0.3f, 100, 70);
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_down.png", 0.3f, 100, 70);
        this.movingLeftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_left.png", 0.3f, 100, 70);
        this.movingRightAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/koerbchen_right.png", 0.3f, 100, 70);
    }
}
