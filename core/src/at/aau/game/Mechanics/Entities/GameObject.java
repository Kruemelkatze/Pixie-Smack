package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.States.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.Rectangle;

import at.aau.game.Mechanics.World;


public abstract class GameObject {
    Vector2 position;
    Rectangle bounds;
    World world;
    Texture texture;
    Array<State> states;
    Vector2 size;


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

    ;

    public abstract void render(float delta, SpriteBatch spriteBatch);


}
