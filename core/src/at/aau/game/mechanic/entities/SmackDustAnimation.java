package at.aau.game.mechanic.entities;

import at.aau.game.mechanic.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SmackDustAnimation extends AbstractGameObject {
	private static Vector2 SIZE = new Vector2(80, 80);
	private final Animation anim;

	public SmackDustAnimation(Vector2 position, World world) {
		super(position.cpy(), world, SmackDustAnimation.SIZE);
		this.anim = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/fairysmack.png", 0.07f, (int) SmackDustAnimation.SIZE.x,
				(int) SmackDustAnimation.SIZE.y);
		this.anim.setPlayMode(Animation.PlayMode.NORMAL);
		animTime = 0;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (anim.isAnimationFinished(animTime)) {
			this.toRemove = true;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		final TextureRegion frame = this.anim.getKeyFrame(animTime, false);
		spriteBatch.draw(frame, position.x, position.y);
	}
}