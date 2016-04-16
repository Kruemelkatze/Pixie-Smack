package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.States.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.Rectangle;

import at.aau.game.Mechanics.World;


public abstract class GameObject {
    public Vector2 position;
    Rectangle bounds;
    World world;
    Texture texture;
    Array<State> states;
    protected Vector2 size;
    protected boolean isDead = false;
    protected boolean toRemove = false;


    public GameObject(Vector2 position, World world, Vector2 size) {
        this.position = position;
        this.world = world;
        states = new Array<State>();
        this.size = size;
    }

    public void removeState(State state) {
        this.states.removeValue(state, false);
    }

    public void addState(State state) {
        if (state.stackable) {
            states.add(state);
        } else if (!states.contains(state, false)) {
            states.add(state);
        }
    }

    public void update(float delta) {
        for (State state : states) {
            state.update(delta);
        }
    }

    public abstract void render(float delta, SpriteBatch spriteBatch);

    public void checkWorldBorders() {
        if (this.position.x < 0) {
            this.position.x = 0;
        } else if (this.position.x > world.pixelSize.x - size.x) {
            this.position.x = world.pixelSize.x - size.x;
        }

        if (this.position.y < 0) {
            this.position.y = 0;
        } else if (this.position.y > world.pixelSize.y - size.y) {
            this.position.y = world.pixelSize.y - size.y;
        }
    }
}
