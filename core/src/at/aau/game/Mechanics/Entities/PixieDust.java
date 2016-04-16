package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import at.aau.game.PixieSmack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class PixieDust extends MoveableObject {
	private TextureRegion frame;
	private Animation movingDownAnimation;
	private Vector2 spawnPosition;
	public boolean IsBadDust = false;
	public boolean IsSpecialDust = false;
	ParticleEffect pe;

	private Random random = new Random();

	static Vector2 SIZE = new Vector2(64, 64);

	public PixieDust(Vector2 position, World world) {
		super(position, world, SIZE);
		this.spawnPosition = position.cpy();

		movement = Movement.IDLE;
		animTime = 0f;
		this.speed = 5f;
		this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/obj_staub_sprit.png", 0.3f, 64, 64);
		this.direction = new Vector2(0, -1);

		pe = new ParticleEffect();
		pe.setPosition(position.x, position.y);
		pe.load(Gdx.files.internal("gameplay/randomparticle"), Gdx.files.internal("gameplay"));
		pe.getEmitters().first().setPosition(position.x, position.y);
		pe.start();
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		frame = movingDownAnimation.getKeyFrame(animTime, true);
		spriteBatch.draw(frame, position.x, position.y);
		this.pe.getEmitters().first().setPosition(this.position.x + 16, this.position.y);
		pe.draw(spriteBatch, delta);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		animTime += delta;
		move();

		if (position.y <= -SIZE.y) {
			world.pixieDustMissed(this);
		}
	}

	public void move() {
		this.position.add(direction.nor().scl(speed));
	}

	public void collect() {
		int randomNum = random.nextInt(PixieSmack.CollectSoundsCount) + 1;
		if (!this.IsBadDust) {
			world.gameplayScreen.parentGame.getSoundManager().playEvent("collect" + randomNum);
		} else {
			world.gameplayScreen.parentGame.getSoundManager().playEvent("bad_collect");
		}

		float distance = spawnPosition.dst(position);

		world.pixieDustCollected(this, distance);
	}

	public void setIsBadDust(boolean value) {
		if (value == true) {
			this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/obj_staub_sprit_bad.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle-bad"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		} else {
			this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/obj_staub_sprit.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		}
		this.IsBadDust = value;
	}

	public void setIsSpecialDust(boolean value) {
		if (value == true) {
			this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/obj_staub_sprit_spec.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle-good"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		} else {
			this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/obj_staub_sprit.png", 0.3f, 64, 64);
			pe.load(Gdx.files.internal("gameplay/randomparticle"), Gdx.files.internal("gameplay"));
			pe.getEmitters().first().setPosition(position.x, position.y);
			pe.start();
		}
		this.IsSpecialDust = value;
	}

}
