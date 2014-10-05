package com.comp4920.dbl.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameworld.GameWorld;

public class InputHandler implements InputProcessor {
	private GameWorld myWorld;
	private Bus myBus;
	private static boolean leftKeyPressed;
	private static boolean rightKeyPressed;
	private static boolean upKeyPressed;
	private static boolean downKeyPressed;
	
	public InputHandler(Bus bus) {
		myBus = bus;
	}
	
	public InputHandler(GameWorld world) {
		myWorld = world;
		myBus = myWorld.getBus();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			setLeftKeyPressed(true);
		} else if (keycode == Keys.RIGHT) {
			setRightKeyPressed(true);
		} else if (keycode == Keys.UP) {
			setUpKeyPressed(true);
		} else if (keycode == Keys.DOWN) {
			setDownKeyPressed(true);
		} else if (keycode == Keys.P || keycode == Keys.ESCAPE) {
			//TODO: pause the game, need the renderer.
			return true;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) {
			setLeftKeyPressed(false);
		} else if (keycode == Keys.RIGHT) {
			setRightKeyPressed(false);
		}  else if (keycode == Keys.UP) {
			setUpKeyPressed(false);
		} else if (keycode == Keys.DOWN) {
			setDownKeyPressed(false);
		} else if (keycode == Keys.P || keycode == Keys.ESCAPE) {
			myWorld.pause();
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

	public static boolean isDownKeyPressed() {
		return downKeyPressed;
	}

	public static void setDownKeyPressed(boolean downKeyPressed) {
		InputHandler.downKeyPressed = downKeyPressed;
	}

	public static boolean isLeftKeyPressed() {
		return leftKeyPressed;
	}

	public static void setLeftKeyPressed(boolean leftKeyPressed) {
		InputHandler.leftKeyPressed = leftKeyPressed;
	}

	public static boolean isRightKeyPressed() {
		return rightKeyPressed;
	}

	public static void setRightKeyPressed(boolean rightKeyPressed) {
		InputHandler.rightKeyPressed = rightKeyPressed;
	}

	public static boolean isUpKeyPressed() {
		return upKeyPressed;
	}

	public static void setUpKeyPressed(boolean upKeyPressed) {
		InputHandler.upKeyPressed = upKeyPressed;
	}

}
