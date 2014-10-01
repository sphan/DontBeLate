package com.comp4920.dbl.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameworld.GameRenderer;

public class InputHandler implements InputProcessor {
	private Bus myBus;
	private GameRenderer renderer;
	public static boolean leftKeyPressed;
	public static boolean rightKeyPressed;
	
	public InputHandler(Bus bus) {
		myBus = bus;
	}
	
	public InputHandler(Bus bus, GameRenderer renderer) {
		myBus = bus;
		this.renderer = renderer;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			leftKeyPressed = true;
		} else if (keycode == Keys.RIGHT) {
			rightKeyPressed = true;
		} else if (keycode == Keys.P || keycode == Keys.ESCAPE) {
			//TODO: pause the game, need the renderer.
			return true;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) {
			leftKeyPressed = false;
		} else if (keycode == Keys.RIGHT) {
			rightKeyPressed = false;
		} else if (keycode == Keys.P || keycode == Keys.ESCAPE) {
			renderer.stopGame();
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		myBus.onClick();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
