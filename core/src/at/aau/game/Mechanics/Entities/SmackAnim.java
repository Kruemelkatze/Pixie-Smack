package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SmackAnim extends GameObject {

    public static Vector2 SIZE = new Vector2(80, 80);
    private Animation anim;
    private float animTime;

    public SmackAnim(Vector2 position, World world) {
        super(position.cpy(), world, SIZE);
        this.anim = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/fairysmack.png", 0.03f, (int) SIZE.x, (int) SIZE.y);
        this.anim.setPlayMode(Animation.PlayMode.NORMAL);
        animTime = 0;
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        animTime += delta;

        if (anim.isAnimationFinished(animTime)) {
            world.getSmackAnims().removeValue(this, true);
        }

        TextureRegion frame = this.anim.getKeyFrame(animTime, false);
        spriteBatch.draw(frame, position.x, position.y);
    }
}
