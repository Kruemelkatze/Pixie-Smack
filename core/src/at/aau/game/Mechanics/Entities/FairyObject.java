package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
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

    static Vector2 SIZE = new Vector2(64, 64);

    public FairyObject(Vector2 position, World world) {
        super(position, world, SIZE);
        this.speed = 2f;
        this.idleAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.FAIRY_SPRITE_PATH, 0.0f, 64, 64);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        tempFrame = idleAnimation.getKeyFrame(movingTime, true);
        spriteBatch.draw(tempFrame, position.x, position.y);
        // spriteBatch.draw(tempFrame, position.x, position.y, tempFrame.getRegionWidth() / GameConstants.PIXEL_TO_METER, tempFrame.getRegionHeight()
        // / GameConstants.PIXEL_TO_METER);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}