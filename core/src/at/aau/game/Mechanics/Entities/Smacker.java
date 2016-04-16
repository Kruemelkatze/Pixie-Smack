package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import at.aau.game.GameConstants;
import at.aau.game.PixieSmack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Smacker extends MoveableObject {

    public static final Vector2 SIZE = new Vector2(100, 230);
    public static final Vector2 SMACK_OFFSET = new Vector2(160, 100); //Magic numbers, interpolated in multi-phase testing iterations
    private final Animation smackingAnim;
    private Random random = new Random();
    
    public int SmackCnt = GameConstants.SMACK_LIMIT;
    private float SmackerRegenerationTime;
    
    public Smacker(Vector2 position, World world) {
        super(position, world, SIZE);
        this.smackingAnim = world.gameplayScreen.parentGame.getAnimator().loadAnimation("gameplay/smacker-anim.png", 0.02f, (int) SIZE.x, (int) SIZE.y);
        this.smackingAnim.setPlayMode(Animation.PlayMode.NORMAL);
        texture = world.gameplayScreen.parentGame.getAssetManager().get("gameplay/smacker.png", Texture.class);

    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        animTime += delta;

        Vector3 unprojected = world.gameplayScreen.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
        Vector2 touchPixelCoords = PixieSmack.worldToPixel(new Vector2(unprojected.x, unprojected.y));
        touchPixelCoords.x = Math.max(touchPixelCoords.x, 0);
        touchPixelCoords.x = Math.min(touchPixelCoords.x, PixieSmack.MENU_GAME_WIDTH);
        touchPixelCoords.y = Math.max(touchPixelCoords.y, 0);
        touchPixelCoords.y = Math.min(touchPixelCoords.y, PixieSmack.MENU_GAME_HEIGHT);
        this.position = touchPixelCoords;

        TextureRegion frame = smackingAnim.getKeyFrame(animTime, false);
        //spriteBatch.draw(frame, position.x, position.y);

        spriteBatch.draw(frame.getTexture(), position.x - SMACK_OFFSET.x, position.y - SMACK_OFFSET.y, 0, 0, SIZE.x, SIZE.y, 1f, 1f, -45, frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight(), false, false);
    }
    
    @Override
	public void update(float delta) {
    	super.update(delta);
    	SmackerRegenerationTime += delta;
    	if (SmackCnt <= GameConstants.SMACK_LIMIT && SmackerRegenerationTime > GameConstants.SMACK_REGENERATION_TIME){
    		SmackCnt += 1;
    		SmackerRegenerationTime = 0;
    	}
	}
    
    public void smack() {	
 		animTime = 0f;
        int randomNum = random.nextInt(PixieSmack.SmackSoundsCount) + 1;
        world.gameplayScreen.parentGame.getSoundManager().playEvent("smack" + randomNum);
        SmackCnt -=1;
    }


}
