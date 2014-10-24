package com.comp4920.dbl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.gameworld.GameWorld.Intention;
import com.comp4920.dbl.helpers.AssetLoader;

public class InstructionScreen implements Screen {

	private Image mainMenuButton;

	private DBL myGame;
	private Stage stage;
	private final int width = 600;
	private final int height = 800;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	public InstructionScreen(DBL g) {
		Gdx.app.log("InstructionScreen", "created");
		myGame = g;
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false, width, height);
		stage = new Stage(new FitViewport(width, height, camera));
		mainMenuButton = new Image(AssetLoader.endGameButton);
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(AssetLoader.instructionImage, 0, 0);
		batch.end();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		stage.addActor(mainMenuButton);

		mainMenuButton.setPosition(175, 640);

		Gdx.input.setInputProcessor(stage);

		mainMenuButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				myGame.setScreen(SplashScreen.getInstance(myGame));
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
	private static InstructionScreen _this = null;

	/**
	 * return singleton instance
	 */
	public static InstructionScreen getInstance(DBL g) {
		if (_this == null) {
			_this = new InstructionScreen(g);
		}
		return _this;
	}
}
