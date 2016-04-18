package at.aau.game.mechanic.entities;

import java.awt.Rectangle;
import java.util.Random;

import at.aau.game.basic.managers.AnimationManager;
import at.aau.game.mechanic.World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	private static AnimationManager animationManager;
	public Vector2 position; // TODO public
	protected Rectangle bounds;
	protected World world;
	// protected Array<State> states;
	protected Vector2 size;
	protected boolean isDead = false;
	protected boolean toRemove = false;
	protected boolean isDamaged = false;
	protected float animTime = 0;
	protected static final Random RANDOM = new Random();

	public AbstractGameObject(Vector2 position, World world, Vector2 size) {
		this.position = position;
		this.world = world;
		// states = new Array<State>();
		this.size = size;
	}

	/*
	 * public void addState(State state) { if (state.stackable) { states.add(state); } else if (!states.contains(state, false)) { states.add(state); } }
	 */

	/*
	 * public void update(float delta) { for (final State state : states) { state.update(delta); } }
	 */

	public void update(float delta) {
		if (this.isToRemove()) {
			return;
		}
		this.animTime += delta;
	}

	public abstract void render(SpriteBatch spriteBatch);

	/*
	 * protected void checkWorldBorders() { if (this.position.x < 0) { this.position.x = 0; } else if (this.position.x > world.pixelSize.x - size.x) {
	 * this.position.x = world.pixelSize.x - size.x; }
	 *
	 * if (this.position.y < 0) { this.position.y = 0; } else if (this.position.y > world.pixelSize.y - size.y) { this.position.y = world.pixelSize.y - size.y;
	 * } }
	 */

	public static void setAnimationManager(AnimationManager animationManager) {
		AbstractGameObject.animationManager = animationManager;
	}

	public static AnimationManager getAnimationManager() {
		if (AbstractGameObject.animationManager == null) {
			throw new RuntimeException("you need to set the AnimationManager first, do this in the Game class (extends ApplicationAdapter)");
		}
		return AbstractGameObject.animationManager;
	}

	public boolean isToRemove() {
		return toRemove;
	}
}