package at.aau.game.mechanic.entities.actors;

import at.aau.game.GameConstants;
import at.aau.game.PixieSmackGame;
import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Smacker extends AbstractMoveableObject {
	public static final Vector2 SIZE = new Vector2(100, 230);
	public static final Vector2 SMACK_OFFSET = new Vector2(120, 60); // (160, 100);
	private final Animation smackingAnim;

	public int SmackCnt = GameConstants.SMACK_LIMIT;
	private float SmackerRegenerationTime;

	public Smacker(Vector2 position, World world) {
		super(position, world, Smacker.SIZE);
		this.smackingAnim = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smacker-anim.png", 0.02f, (int) Smacker.SIZE.x,
				(int) Smacker.SIZE.y);
		this.smackingAnim.setPlayMode(Animation.PlayMode.NORMAL);
		// texture = world.playScreen.getGdxAssetManager().get("gameplay/smacker.png", Texture.class);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		final Vector3 unprojected = world.playScreen.cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
		final Vector2 touchPixelCoords = new Vector2(unprojected.x, unprojected.y);
		touchPixelCoords.x = Math.max(touchPixelCoords.x, 0);
		touchPixelCoords.x = Math.min(touchPixelCoords.x, PixieSmackGame.MENU_GAME_WIDTH);
		touchPixelCoords.y = Math.max(touchPixelCoords.y, 0);
		touchPixelCoords.y = Math.min(touchPixelCoords.y, PixieSmackGame.MENU_GAME_HEIGHT);
		this.position = touchPixelCoords;

		final TextureRegion frame = smackingAnim.getKeyFrame(animTime, false);
		spriteBatch.draw(frame.getTexture(), position.x - Smacker.SMACK_OFFSET.x, position.y - Smacker.SMACK_OFFSET.y, 0, 0, Smacker.SIZE.x, Smacker.SIZE.y,
				1f, 1f, -45, frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight(), false, false);
	}

	@Override
	public void update(float delta) {
		super.update(delta); // updates animTime
		SmackerRegenerationTime += delta;
		if (SmackCnt < GameConstants.SMACK_LIMIT && SmackerRegenerationTime > GameConstants.SMACK_REGENERATION_TIME) {
			SmackCnt += 1;
			SmackerRegenerationTime = 0;
		}
	}

	public void smack() {
		animTime = 0f;
		// final int randomNum = random.nextInt(GameConstants.SmackSoundsCount) + 1;
		// this sound would be played also if you hit nothing
		// world.playScreen.getSoundManager().playEvent("smack" + randomNum);
		SmackCnt -= 1;
	}
}