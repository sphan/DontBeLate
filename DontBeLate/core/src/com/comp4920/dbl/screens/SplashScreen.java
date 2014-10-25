package com.comp4920.dbl.screens;

import javax.swing.SpringLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.helpers.AssetLoader;

public class SplashScreen implements Screen {
	private Image startButton;
	private Image quitButton;
	private Image instructionButton;
	private Image soundButton;
	private Image offBar;

	private DBL myGame;
	private Stage stage;
	private final int width = 600;
	private final int height = 800;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private boolean musicSetOff = false;

	public SplashScreen(DBL g) {
		AssetLoader.menuMusic.stop();
		AssetLoader.menuMusic.play();
		Gdx.app.log("SplashScreen", "created");
		myGame = g;
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false, width, height);
		stage = new Stage(new FitViewport(width, height, camera));
		startButton = new Image(AssetLoader.startGameButton);
		quitButton = new Image(AssetLoader.quitButton);
		instructionButton = new Image(AssetLoader.instructionButton);
		soundButton = new Image(AssetLoader.soundEffectButton);
		offBar = new Image(AssetLoader.offBar);
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(AssetLoader.startMenuBackground, 0, 0);
		batch.end();
		stage.act();
		stage.draw();

		if (!DBL.isSoundOn() && musicSetOff == false) {
			musicSetOff = true;
			drawOffBar(stage, 490, 150);
			AssetLoader.menuMusic.pause();
		}

		if (DBL.isSoundOn() && musicSetOff == true) {
			musicSetOff = false;
			AssetLoader.menuMusic.play();
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height);
	}

	@Override
	public void show() {
		AssetLoader.menuMusic.play();
		stage.addActor(startButton);
		stage.addActor(quitButton);
		stage.addActor(instructionButton);
		stage.addActor(soundButton);

		instructionButton.setScale((float) 0.5);
		instructionButton.setPosition(70, 225);
		startButton.setPosition(70, 240);
		quitButton.setPosition(70, 120);
		soundButton.setPosition(490, 150);

		Gdx.input.setInputProcessor(stage);

		startButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				myGame.setScreen(GameScreen.getInstance(myGame));
			}
		});

		instructionButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				myGame.setScreen(InstructionScreen.getInstance(myGame));
			}
		});

		quitButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.exit();
			}
		});

		soundButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				if (DBL.isSoundOn()) {
					DBL.turnOffSound();
				} else {
					DBL.turnOnSound();
				}
			}
		});
	}

	private void drawOffBar(Stage stage, int x, int y) {
		stage.addActor(offBar);
		offBar.setPosition(x, y);

		for (EventListener listener : offBar.getListeners()) {
			offBar.removeListener(listener);
		}

		offBar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen offBar touchDown",
				        "soundEffectButton is touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				DBL.turnOnSound();
				offBar.remove();
			}
		});
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * keep the singleton instance
	 */
	private static SplashScreen _this = null;

	/**
	 * return singleton instance
	 */
	public static SplashScreen getInstance(DBL g) {
		if (_this == null) {
			_this = new SplashScreen(g);
		}
		return _this;
	}
}
