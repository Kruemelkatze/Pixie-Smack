package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Koerbchen extends SkeletonControlledObject {

    static Vector2 SIZE = new Vector2(45, 64);

    public Koerbchen(Vector2 position, World world) {
        super(position, world, SIZE);
    }
}
