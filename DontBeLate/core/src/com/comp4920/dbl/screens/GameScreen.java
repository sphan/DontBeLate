package com.comp4920.dbl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.comp4920.dbl.gameworld.GameRenderer;
import com.comp4920.dbl.gameworld.GameWorld;
import com.comp4920.dbl.helpers.InputHandler;

public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private float runTime = 0;
	
	public GameScreen() {
		Gdx.app.log("GameScreen", "created");
		float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameHeight = 400;
        float gameWidth = screenWidth / (screenHeight / gameHeight);

        int midPointX = (int) (gameWidth / 2);
		
		world = new GameWorld(midPointX);
		renderer = new GameRenderer(world, (int) gameWidth, midPointX);
		
		Gdx.input.setInputProcessor(new InputHandler(world.getBus()));
	}

	@Override
	public void render(float delta) {
//		// Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
//        Gdx.gl.glClearColor(204/255.0f, 204/255.0f, 204/255.0f, 1f);
//        // Fills the screen with the selected color
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        // Covert Frame rate to String, print it
//        Gdx.app.log("GameScreen FPS", (1/delta) + "");
		runTime += delta;
		world.update(delta);
		renderer.render(runTime);
	}

	@Override
	public void resize(int width, int height) {
//		Gdx.app.log("GameScreen", "resizing");
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show called");
	}

	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "pause called");  
	}

	@Override
	public void resume() {
		Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	public void dispose() {
		
	}

}
