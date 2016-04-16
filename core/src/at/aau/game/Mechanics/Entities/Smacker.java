package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Smacker extends MoveableObject {

    public static final Vector2 SIZE = new Vector2(200, 70);


    public Smacker(Vector2 position, World world) {
        super(position, world, SIZE);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {

    }
}
