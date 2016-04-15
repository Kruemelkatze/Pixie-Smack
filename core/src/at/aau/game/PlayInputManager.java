package at.aau.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayInputManager extends InputAdapter {
	private PlayerController playerController = null;
	private final OrthographicCamera playerCam;
	private boolean enabled = true;
	private final OrthographicCamera b2dCam;
	private final static float ZOOM_FACTOR = 0.3f;

	private boolean escapePressed = false;

	/**
	 * @param PlayerController
	 * @param camera
	 * @param b2dCam
	 */
	public PlayInputManager(final PlayerController playerController, final OrthographicCamera camera, final OrthographicCamera b2dCam) { // ,
		this.playerCam = camera;
		this.b2dCam = b2dCam;
		this.playerController = playerController;
	}

	@Override
	public boolean keyDown(final int keycode) {
		if (!this.enabled) {
			return false;
		}
		switch (keycode) {
		// Camera
		case Input.Keys.P:
			this.playerCam.zoom = this.playerCam.zoom + PlayInputManager.ZOOM_FACTOR;
			this.b2dCam.zoom = this.b2dCam.zoom + PlayInputManager.ZOOM_FACTOR;
			break;
		case Input.Keys.O:
			this.playerCam.zoom = this.playerCam.zoom - PlayInputManager.ZOOM_FACTOR;
			this.b2dCam.zoom = this.b2dCam.zoom - PlayInputManager.ZOOM_FACTOR;
			break;
		case Input.Keys.ESCAPE:
			// this.escapePressed = true;
			// this.PlayerController.setPressingEscape(true);
			break;
		case Input.Keys.L:
			// this.plusPressed = true;
			break;
		case Input.Keys.MINUS:
			// this.minusPressed = true;
			break;
		default:
			break;
		}

		if (this.playerController != null) {
			switch (keycode) {
			// running Player Sleep
			case Input.Keys.A:
				this.playerController.setLeft(true);
				break;
			case Input.Keys.D:
				this.playerController.setRight(true);
				break;
			case Input.Keys.W:
				this.playerController.setUp(true);
				break;
			case Input.Keys.S:
				this.playerController.setDown(true);
				break;
			case Input.Keys.CONTROL_RIGHT:
			case Input.Keys.CONTROL_LEFT:
				this.playerController.setPressingShoot(true);
				break;
			case Input.Keys.SPACE:
				this.playerController.setPressingShoot(true);
				break;

			/*
			 * case Input.Keys.R: this.reset = true; break;
			 */
			default:
				// this.playerWake.stop();
				break;
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(final int keycode) {
		switch (keycode) {
		case Input.Keys.ESCAPE:
			this.escapePressed = false;
			break;
		case Input.Keys.Q:
			Gdx.app.exit(); // this will cause pause() and dispose() on Android
			break;
		default:
			break;
		}

		if (this.playerController != null) {
			switch (keycode) {

			case Input.Keys.A:
				this.playerController.setLeft(false);
				break;
			case Input.Keys.D:
				this.playerController.setRight(false);
				break;
			case Input.Keys.W:
				this.playerController.setUp(false);
				break;
			case Input.Keys.S:
				this.playerController.setDown(false);
				break;
			case Input.Keys.CONTROL_RIGHT:
			case Input.Keys.CONTROL_LEFT:
				this.playerController.setPressingShoot(false);
				break;
			case Input.Keys.SPACE:
				this.playerController.setPressingShoot(false);
				break;
			case Input.Keys.ESCAPE:
				this.escapePressed = false;
				// this.PlayerController.setPressingEscape(false);
				break;
			default:
				break;
			}
		}
		return false;
	}

	@Override
	public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
		if (!this.enabled) {
			return false;
		}
		// Printer.info("x:" + screenX + ", y:" + screenY + ", pointer:" + pointer);
		switch (button) {
		case Input.Buttons.LEFT: // Buttons means mouse
			this.playerController.setPressingShoot(true);
			break;
		case Input.Buttons.RIGHT:
			this.playerController.setPressingJump(true);
			break;
		default:
			break;
		}

		return true;

	}

	@Override
	public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
		if (!this.enabled) {
			return false;
		}
		// Printer.info("x:" + screenX + ", y:" + screenY + ", pointer:" + pointer);
		switch (button) {
		case Input.Buttons.LEFT: // Buttons means mouse
			this.playerController.setPressingShoot(false);
			break;
		case Input.Buttons.RIGHT:
			this.playerController.setPressingJump(false);
			break;
		default:
			break;
		}

		return true;

	}
}