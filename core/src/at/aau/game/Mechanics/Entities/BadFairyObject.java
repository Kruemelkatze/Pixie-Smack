package at.aau.game.Mechanics.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.World;
import at.aau.game.Mechanics.States.DirectionX;
import at.aau.game.Mechanics.States.DirectionY;

public class BadFairyObject extends FairyObject {

	public BadFairyObject(Vector2 position, World world) {
		super(position, world);
        this.health = 1;
        this.startPosition = this.position.cpy();
        float rnd = random.nextFloat();
        if (rnd < 0.33f) {
            this.directionX = DirectionX.LEFT;
            this.directionY = DirectionY.UP;
            this.speed = 2.5f *2;
        } else if (rnd < 0.66f) {
            this.directionX = DirectionX.LEFT;
            this.directionY = DirectionY.DOWN;
            this.speed = 1.5f*2;
        } else {
            this.directionX = DirectionX.RIGHT;
            this.directionY = DirectionY.DOWN;
            this.speed = 1.0f*2;
        }
		
		this.leftAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.EVIL_FAIRY_SPRITE_PATH_LEFT, 0.3f, (int) this.size.x,
                (int) this.size.y);
		this.rightAnimation = world.gameplayScreen.parentGame.getAnimator().loadAnimation(GameConstants.EVIL_FAIRY_SPRITE_PATH_RIGHT, 0.3f, (int) this.size.x,
                (int) this.size.y);	
		
		this.dead1 = world.gameplayScreen.parentGame.getAnimator()
				.loadAnimation(GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT, 0.1f, (int) this.size.x, (int) this.size.y);
		dead1.setPlayMode(PlayMode.NORMAL);
		this.dead2 = world.gameplayScreen.parentGame.getAnimator().loadAnimation(
				GameConstants.MAD_FAIRY_SPRITE_PATH_LEFT_UPSIDEDOWN, 2f, (int) this.size.x, (int) this.size.y);
		dead2.setPlayMode(PlayMode.NORMAL);
	}

   
}