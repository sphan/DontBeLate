package com.comp4920.dbl.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.comp4920.dbl.gameworld.GameInterfaceRenderer;
import com.comp4920.dbl.gameworld.GameRenderer;
import com.comp4920.dbl.gameworld.GameWorld;
import com.comp4920.dbl.helpers.InputHandler;

public class GameScreen implements Screen {
	private Game myGame;
	private GameWorld world;
	private GameRenderer gameRenderer;
	private GameInterfaceRenderer gameInterface;
	private OrthographicCamera camera;
	private float runTime = 0;
	private InputHandler busInputHandler;
	private InputMultiplexer inputMulti;
	
	public GameScreen(Game g) {
		Gdx.app.log("GameScreen", "created");
		float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameHeight = 400;
        float gameWidth = screenWidth / (screenHeight / gameHeight);

        int midPointX = (int) (gameWidth / 2);
        myGame = g;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 300, 400);
		
		world = new GameWorld(midPointX);
		gameInterface = new GameInterfaceRenderer(myGame, world, camera,(int) gameWidth, midPointX);
		gameRenderer = new GameRenderer(myGame, world, camera, (int) gameWidth, midPointX);
		busInputHandler = new InputHandler(world);
		
		
		
		inputMulti = new InputMultiplexer();
		inputMulti.addProcessor(gameInterface.getStage());
		inputMulti.addProcessor(busInputHandler);
		Gdx.input.setInputProcessor(inputMulti);
		
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
		world.update(delta, busInputHandler);
		
		gameRenderer.render(runTime); //game world
		gameInterface.render(runTime);//game interface and menus
		
	}

	@Override
	public void resize(int width, int height) {
//		Gdx.app.log("GameScreen", "resizing");
		gameInterface.getStage().getViewport().update(width, height);
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
