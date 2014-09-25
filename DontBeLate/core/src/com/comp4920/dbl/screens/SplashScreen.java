package com.comp4920.dbl.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.helpers.AssetLoader;

public class SplashScreen implements Screen {
	private Image startButton;
	private Image quitButton;
	private Game myGame;
	private Stage stage;
	
	public SplashScreen(Game g) {
		Gdx.app.log("SplashScreen", "created");
		myGame = g;
		stage = new Stage();
		startButton = new Image(AssetLoader.startGameButton);
		quitButton = new Image(AssetLoader.quitButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		stage.addActor(startButton);
		stage.addActor(quitButton);
		startButton.setPosition(300, 400);
		quitButton.setPosition(300, 300);
		Gdx.input.setInputProcessor(stage);
		
//		addButtonListener(startButton, new GameScreen());
		
		startButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        myGame.setScreen(new GameScreen());
		    }
		});
		
		quitButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        Gdx.app.exit();
		    }
		});
	}
	
//	private void addButtonListener(Image button, final Object screen) {
//		button.addListener(new InputListener() {
//			@Override
//		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
//		    {
//		        return true;
//		    }
//			
//		    @Override
//		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
//		    {
//		        myGame.setScreen((GameScreen) screen);
//		    }
//		});
//	}

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

}
