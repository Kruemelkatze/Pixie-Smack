package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class CollidableObject extends MoveableObject {
    protected Body body;

    public CollidableObject(Vector2 position, World world, Vector2 size, boolean isTrigger) {
        super(position, world, size);
        createBox2DBody(position, size, world, isTrigger);
    }

    public void createBox2DBody(Vector2 position, Vector2 size, World world, boolean isTrigger) {
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.KinematicBody;
// Set our body's starting position in the world
        bodyDef.position.set(position.x, position.y);

// Create our body in the world using our body definition
        body = world.getBox2DWorld().createBody(bodyDef);

// Create a circle shape and set its radius to 6
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setSensor(isTrigger);

// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();
    }

    public abstract void notifyCollission(GameObject other);
}
