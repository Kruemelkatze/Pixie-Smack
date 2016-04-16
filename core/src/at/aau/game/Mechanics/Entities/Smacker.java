package at.aau.game.Mechanics.Entities;

import at.aau.game.Mechanics.World;
import at.aau.game.PixieSmack;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Smacker extends MoveableObject {

    public static final Vector2 SIZE = new Vector2(100, 230);
    public static final Vector2 SMACK_OFFSET = new Vector2(160, 100); //Magic numbers, interpolated in multi-phase testing iterations
    private final Animation smackingAnim;

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
        this.position = touchPixelCoords;

        TextureRegion frame = smackingAnim.getKeyFrame(animTime, false);
        //spriteBatch.draw(frame, position.x, position.y);

        spriteBatch.draw(frame.getTexture(), position.x - SMACK_OFFSET.x, position.y - SMACK_OFFSET.y, 0, 0, SIZE.x, SIZE.y, 1f, 1f, -45, frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight(), false, false);
    }

    public void startAnim() {
        animTime = 0f;
    }


}
