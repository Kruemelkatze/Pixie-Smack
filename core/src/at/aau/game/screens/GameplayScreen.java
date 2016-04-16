package at.aau.game.screens;

import at.aau.game.PixieSmack;
import at.aau.game.Mechanics.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameplayScreen extends ScreenAdapter {
    private final SpriteBatch batch;
    public final OrthographicCamera cam;
    public PixieSmack parentGame;

    Texture backgroundImage;
    BitmapFont menuFont;
    World world;
    String[] menuStrings = {"Play", "Credits", "Exit"};
    int currentMenuItem = 0;

    // float offsetLeft = PixieSmack.GAME_WIDTH / 8, offsetTop = PixieSmack.GAME_WIDTH / 8, offsetY = PixieSmack.GAME_HEIGHT / 8;

    public GameplayScreen(PixieSmack game) {
        this.parentGame = game;
        this.world = new World(this);

        backgroundImage = parentGame.getAssetManager().get("gameplay/bg-forest-border.png");
        menuFont = parentGame.getAssetManager().get("menu/Ravie_72.fnt");
        menuFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        // Create camera that projects the desktop onto the actual screen size.
        cam = new OrthographicCamera(PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        batch = new SpriteBatch();

        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render(float delta) {
        handleInput();
        // camera:
        cam.update();
        batch.setProjectionMatrix(cam.combined);


        world.update(delta);

        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw bgImage ...
        batch.draw(backgroundImage, 0, 0, PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);
        batch.end();
        world.render(delta);

        // batch.begin();
        // // draw bgImage ...
        // batch.draw(backgroundImage, 0, 0, PixieSmack.GAME_WIDTH, PixieSmack.GAME_HEIGHT);
        // // draw Strings ...
        // for (int i = 0; i < menuStrings.length; i++) {
        // if (i == currentMenuItem) menuFont.setColor(0.2f, 1f, 0.2f, 1f);
        // else menuFont.setColor(0.2f, 0.2f, 1f, 1f);
        // menuFont.draw(batch, menuStrings[i], offsetLeft, PixieSmack.GAME_HEIGHT - offsetTop - i * offsetY);
        // }
        // batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println("ESCAPE PRESSED");
            parentGame.getSoundManager().playEvent("blip");
        }

        if (Gdx.input.justTouched()) {
            Vector3 unprojected = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 1));
            Vector2 touchPixelCoords = PixieSmack.worldToPixel(new Vector2(unprojected.x, unprojected.y));
            world.touch(touchPixelCoords);
        }
    }

}
