package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.GameScreen;

public class GameInterfaceRenderer {
	private Game myGame;
	private Clock clock;
	private Stage stage;
	private SpriteBatch batch;
	private Stage gameOverStage; //TODO: is another stage necessary?
	
	//distance
	BitmapFont yourBitmapFontName;
	private float posDistLabX;
	private float posDistLabY;
	
	//pause, resume and restart buttons
	private Image pauseButton;
	private Image resumeButton;
	private Image restartButton;
	private Image endGameButton;
	
	//data
	private int midPointX;
	private int gameWidth;
	public Road road;
	private GameWorld myWorld;
	
	public GameInterfaceRenderer (Game game, GameWorld myWorld, OrthographicCamera camera, int gameWidth, int midPointX) {
		this.myGame = game;
		this.myWorld = myWorld;
		road = myWorld.getRoad();
		this.gameWidth = gameWidth;
		this.midPointX = midPointX;
		clock = new Clock();
		stage = new Stage();
		pauseButton = new Image(AssetLoader.pauseButton);
		resumeButton = new Image(AssetLoader.resumeButton);
		restartButton = new Image(AssetLoader.restartButton);//TODO: create restart button
		endGameButton = new Image(AssetLoader.endGameButton);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		yourBitmapFontName = new BitmapFont(true);
		posDistLabX = 60;
		posDistLabY = 15;
		gameOverStage = new Stage();
	}
		
	public void render(float runTime) {
		
		batch.begin();
		
		//yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String distance = getDistanceStringMtrs(road.getDistanceTravelledMtrs());
		getBitMapFont().draw(batch, distance, getDistLabX(), getDistLabY()); 
		
		
		//draw time
		Clock clock = getClock();
		clock.getFont().draw(batch, clock.getDisplayText(), clock.getX(), clock.getY()); 
		batch.end();
		

		//draw pause menu
		Stage stage = getStage();
		stage.act();
		stage.draw();
		drawPauseButton(stage);
		
		
		if(myWorld.isPaused()){
			renderPauseMenu(stage, clock);
		}
		if(myWorld.isGameOver()){
			renderGameOverScreen(stage, clock);
		}
				
	}
	

	private void drawPauseButton(Stage stage) {
		stage.addActor(getPauseButton());
//		gameInterface.getStage().addActor(gameInterface.getPauseButton());
		getPauseButton().setPosition(posDistLabX, posDistLabY + 690);  //TODO: should not hard code the position
		
		getPauseButton().addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("GameScreen pausebutton touchDown", "pauseButton is touchDown");
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	Gdx.app.log("GameScreen pausebutton touchUp", "pauseButton is clicked");
		    	myWorld.pause();
		    }
		});
	}
	
	private void renderPauseMenu(Stage stage, final Clock clock) {

		final Image resumeButton = getResumeButton();
		stage.addActor(resumeButton);
		stage.addActor(endGameButton);
		clock.stop();
		resumeButton.setPosition(midPointX, 800 / 2 + 200);
		endGameButton.setPosition(midPointX, 800 / 2 + 150);
		
		resumeButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        resumeButton.remove();
		        endGameButton.remove();
		        myWorld.start();
		        clock.start();
		    }
		});
		
		endGameButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		    }
		});
	}
	
	private void renderGameOverScreen(Stage stage, Clock clock) {
		stage.addActor(getRestartButton());
		getRestartButton().setPosition(midPointX+60, 500);
		clock.stop();
		getRestartButton().addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("GameScreen restartbutton touchDown", "restartButton is touchDown");
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	Gdx.app.log("GameScreen restartbutton touchUp", "restartbutton is clicked");
		    	Road.resetDistanceTravelled();
		    	myGame.setScreen(new GameScreen(myGame));
		    }
		});
	}

	
	
	public Clock getClock(){
		return clock;
	}
	
	public void stopClock(){
		clock.stop();
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Stage getGameOverStage(){
		return gameOverStage;
	}
	
	public Image getPauseButton(){
		return pauseButton;
	}
	
	public Image getResumeButton(){
		return resumeButton;
	}
	
	public Image getRestartButton(){
		return restartButton;
	}
	
	public String getDistanceString (float distance){
		return "Distance: " + distance;
	}
	
	public String getDistanceStringMtrs (int distance){
		return "Distance: " + distance + " m";
	}
	
	public BitmapFont getBitMapFont (){
		return yourBitmapFontName;
	}
	
	public float getDistLabY() {
		return posDistLabY;
	}
	
	public float getDistLabX() {
		return posDistLabX;
	}
	
	public void dispose(){
		batch.dispose();
		stage.dispose();
		gameOverStage.dispose();
		yourBitmapFontName.dispose();
	}


}
