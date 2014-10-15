package com.comp4920.dbl.screens;

import javax.swing.SpringLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private float width = Gdx.graphics.getWidth();
    private float height = Gdx.graphics.getHeight();
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sprite sprite;
	
	public SplashScreen(Game g) {
		Gdx.app.log("SplashScreen", "created");
		myGame = g;
		stage = new Stage();
		startButton = new Image(AssetLoader.startGameButton);
		quitButton = new Image(AssetLoader.quitButton);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(1, height / width);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
        stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height);
	}

	@Override
	public void show() {
		stage.addActor(startButton);
		stage.addActor(quitButton); 
		startButton.setPosition(2*width/27, 13*height/54);
		quitButton.setPosition(2*width/27, 5*height/54);
		Gdx.input.setInputProcessor(stage);
		
		// http://gamedev.stackexchange.com/questions/71198/how-do-i-make-a-background-fill-the-whole-screen-in-libgdx
		sprite = new Sprite(AssetLoader.startMenuBgRegion);
		sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth() );
	    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	    sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
		
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
		        myGame.setScreen(new GameScreen(myGame));
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
