package at.aau.game.Mechanics.Entities;

import at.aau.game.GameConstants;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Timer extends GameObject {
    private float animTime = 0.0f;
    private Animation timer;

    public Timer(Vector2 position, World world, Vector2 size) {
        super(position, world, size);
        timer = world.gameplayScreen.parentGame.getAnimator()
                .loadAnimation("gameplay/timer-pink.png", GameConstants.TIMEOUT / 15f, (int) size.x, (int) size.y);
    }

    @Override
    public void render(float delta, SpriteBatch spriteBatch) {
        spriteBatch.draw(timer.getKeyFrame(animTime, true), this.position.x, this.position.y);
    }

    @Override
    public void update(float delta) {
        this.animTime += delta;
    }
}