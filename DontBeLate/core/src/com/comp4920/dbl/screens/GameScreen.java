package com.comp4920.dbl.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameworld.GameInterface;
import com.comp4920.dbl.gameworld.GameRenderer;
import com.comp4920.dbl.gameworld.GameWorld;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.helpers.InputHandler;

public class GameScreen implements Screen {
	private GameWorld world;
	private GameRenderer renderer;
	private GameInterface gameInterface;
	private float runTime = 0;
	private InputHandler busInputHandler;
	private InputMultiplexer inputMulti;
	
	public GameScreen() {
		Gdx.app.log("GameScreen", "created");
		float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameHeight = 400;
        float gameWidth = screenWidth / (screenHeight / gameHeight);

        int midPointX = (int) (gameWidth / 2);
        
        gameInterface = new GameInterface();
		world = new GameWorld(midPointX);
		renderer = new GameRenderer(world, gameInterface, (int) gameWidth, midPointX);
		busInputHandler = new InputHandler(world.getBus(), renderer);
		
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
		renderer.render(runTime);
		
	}

	@Override
	public void resize(int width, int height) {
//		Gdx.app.log("GameScreen", "resizing");
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show called");
		
		gameInterface.getStage().addActor(gameInterface.getPauseButton());
		gameInterface.getPauseButton().setPosition(50, 750);
//		Gdx.input.setInputProcessor(stage);
		
		gameInterface.getPauseButton().addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("GameScreen pausebutton touchDown", "pauseButton is touchDown");
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	Gdx.app.log("GameScreen pausebutton touchUp", "pauseButton is clicked");
		    	renderer.stopGame();
		    }
		});
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
