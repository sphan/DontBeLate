package com.comp4920.dbl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.comp4920.dbl.DBL;
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
		if (keycode == Keys.LEFT || keycode == Keys.A) {
			setLeftKeyPressed(true);
		} else if (keycode == Keys.RIGHT || keycode == Keys.D) {
			setRightKeyPressed(true);
		} else if (keycode == Keys.UP || keycode == Keys.W) {
			setUpKeyPressed(true);
		} else if (keycode == Keys.DOWN || keycode == Keys.S) {
			setDownKeyPressed(true);
		} else if (keycode == Keys.P || keycode == Keys.ESCAPE) {
			//TODO: pause the game, need the renderer.
			return true;
		} else if (keycode == Keys.M) {
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
			if (!myWorld.isGameOver())
				myWorld.pause();
		}
		
		if (keycode == Keys.A) {
			setLeftKeyPressed(false);
		} else if (keycode == Keys.D) {
			setRightKeyPressed(false);
		}  else if (keycode == Keys.W) {
			setUpKeyPressed(false);
		} else if (keycode == Keys.S) {
			setDownKeyPressed(false);
		} else if (keycode == Keys.M) {
			if (DBL.isSoundOn()) {
				DBL.turnOffSound();
			} else if (!DBL.isSoundOn()) {
				DBL.turnOnSound();
			}
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
		if (screenX < Gdx.graphics.getWidth() / 3 && screenY > Gdx.graphics.getHeight() / 2) {
			setLeftKeyPressed(true);
		} else if  (screenX > Gdx.graphics.getWidth() / 3 * 2 && screenY > Gdx.graphics.getHeight() / 2) {
			setRightKeyPressed(true);
		} else if (screenY < Gdx.graphics.getHeight() / 2) {
			setUpKeyPressed(true);
		} else if (screenY > Gdx.graphics.getHeight() / 2 &&
				   screenX > Gdx.graphics.getWidth() / 3 &&
				   screenX < Gdx.graphics.getWidth() / 3 * 2) {
			setDownKeyPressed(true);
		}
//		myBus.onClick();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if (screenX < Gdx.graphics.getWidth() / 3 && screenY > Gdx.graphics.getHeight() / 2) {
			setLeftKeyPressed(false);
		} else if  (screenX > Gdx.graphics.getWidth() / 3 * 2 && screenY > Gdx.graphics.getHeight() / 2) {
			setRightKeyPressed(false);
		} else if (screenY < Gdx.graphics.getHeight() / 2) {
			setUpKeyPressed(false);
		} else if (screenY > Gdx.graphics.getHeight() / 2 &&
				   screenX > Gdx.graphics.getWidth() / 3 &&
				   screenX < Gdx.graphics.getWidth() / 3 * 2) {
			setDownKeyPressed(false);
		}
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
