package at.aau.game.mechanic.entities;

import at.aau.game.GameConstants;
import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.actors.AbstractMoveableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CollectiblePixieDust extends AbstractMoveableObject {
	private TextureRegion frame;
	private Animation movingDownAnimation;
	public boolean isBadDust = false;
	public boolean isSpecialDust = false;
	private final ParticleEffect pe;
	private static Vector2 SIZE = new Vector2(64, 64);

	// strange
	// private final SpriteBatch batch; // you should not use this, but is needed for particle
	private float tempDelta;

	public CollectiblePixieDust(Vector2 position, World world) {
		super(position, world, CollectiblePixieDust.SIZE);
		// this.batch = batch;
		this.movement = Movement.IDLE;
		this.animTime = 0f;
		this.speed = 5f;
		this.movingDownAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/obj_staub_sprit.png", 0.3f, 64, 64);
		this.direction = new Vector2(0, -1);

		pe = new ParticleEffect();
		pe.setPosition(position.x, position.y);
		pe.load(Gdx.files.internal("gameplay/randomparticle"), Gdx.files.internal("gameplay"));
		pe.getEmitters().first().setPosition(position.x, position.y);
		pe.start();
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		frame = movingDownAnimation.getKeyFrame(animTime, true);
		spriteBatch.draw(frame, position.x, position.y);
		this.pe.getEmitters().first().setPosition(this.position.x + 16, this.position.y);
		this.pe.draw(spriteBatch, this.tempDelta);
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		// strange
		this.tempDelta = delta;
		// batch.begin();
		// pe.draw(batch, delta); // strange to do this here with an own batch
		// batch.end();
		// strange end

		animTime += delta;
		moveDown();

		if (position.y <= -CollectiblePixieDust.SIZE.y) {
			world.pixieDustMissed(this);
		}
	}

	private void moveDown() {
		this.position.add(direction.nor().scl(speed));
	}

	public void collect() {
		final int randomNum = AbstractGameObject.RANDOM.nextInt(GameConstants.CollectSoundsCount) + 1;
		if (!this.isBadDust) {
			world.playScreen.getSoundManager().playEvent("collect" + randomNum);
		} else {
			world.playScreen.getSoundManager().playEvent("bad_collect");
		}
		world.pixieDustCollected(this);
	}

	public void setIsBadDust(boolean value) {
		if (value == true) {
			this.movingDownAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/obj_staub_sprit_bad.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle-bad"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		} else {
			this.movingDownAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/obj_staub_sprit.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		}
		this.isBadDust = value;
	}

	public void setIsSpecialDust(boolean value) {
		if (value == true) {
			this.movingDownAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/obj_staub_sprit_spec.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle-good"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		} else {
			this.movingDownAnimation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/obj_staub_sprit.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		}
		this.isSpecialDust = value;
	}
}