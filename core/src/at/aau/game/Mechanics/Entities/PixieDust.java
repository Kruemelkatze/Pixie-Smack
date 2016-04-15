package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import at.aau.game.Mechanics.Entities.MoveableObject.Movement;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PixieDust extends MoveableObject{
	   private TextureRegion frame;
	    private Animation movingDownAnimation;
	    
	    static Vector2 SIZE = new Vector2(64, 64);
	
	
	public PixieDust(Vector2 position, World world){
		super(position, world, SIZE);
		movement = Movement.IDLE;
        movingTime = 0f;
        this.speed = 5f;
        this.movingDownAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/obj_staub_sprit.png",0.3f,64,64);
        this.direction = new Vector2(0,-1);
	}

	@Override
	public void render(float delta, SpriteBatch spriteBatch) {
		frame = movingDownAnimation.getKeyFrame(movingTime, true);
        spriteBatch.draw(frame, position.x, position.y);
	}
	
	
	@Override
    public void update(float delta) {
        super.update(delta);
        movingTime+=delta;
        move();
    }
	
	public void move(){
		this.position.add(direction.nor().scl(speed));
	}
	
}
