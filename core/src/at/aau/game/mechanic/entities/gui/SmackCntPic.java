package at.aau.game.mechanic.entities.gui;

import at.aau.game.mechanic.World;
import at.aau.game.mechanic.entities.AbstractGameObject;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SmackCntPic extends AbstractGameObject {
	private TextureRegion frame;
	private Animation animation;

	public SmackCntPic(Vector2 position, World world, Vector2 size) {
		super(position, world, size);
		animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack13.png", 0.0f, 237, 46);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		frame = animation.getKeyFrame(animTime, true);
		spriteBatch.draw(frame, position.x, position.y);
	}

	public void setAnimation(int x) {
		switch (x) {
		case 0:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack0.png", 0.0f, 237, 46);
			break;
		case 1:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack1.png", 0.0f, 237, 46);
			break;
		case 2:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack2.png", 0.0f, 237, 46);
			break;
		case 3:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack3.png", 0.0f, 237, 46);
			break;
		case 4:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack4.png", 0.0f, 237, 46);
			break;
		case 5:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack5.png", 0.0f, 237, 46);
			break;
		case 6:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack6.png", 0.0f, 237, 46);
			break;
		case 7:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack7.png", 0.0f, 237, 46);
			break;
		case 8:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack8.png", 0.0f, 237, 46);
			break;
		case 9:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack9.png", 0.0f, 237, 46);
			break;
		case 10:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack10.png", 0.0f, 237, 46);
			break;
		case 11:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack11.png", 0.0f, 237, 46);
			break;
		case 12:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack12.png", 0.0f, 237, 46);
			break;
		case 13:
			animation = AbstractGameObject.getAnimationManager().loadAnimation("gameplay/smack13.png", 0.0f, 237, 46);
			break;
		default: // System.out.println("Switch Error in Methode setAnimation() in Klasse SmackCntPic");
		}
	}
}