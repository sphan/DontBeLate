package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.helpers.AssetLoader;

public class GameInterface {
	
	private Clock clock;
	private Stage stage;
	private Stage gameOverStage;
	
	//distance
	BitmapFont yourBitmapFontName;
	private float posDistLabX;
	private float posDistLabY;
	
	//pause and resume
	private Image pauseButton;
	private Image resumeButton;
	private Image restartButton;
	
	public GameInterface (){
		clock = new Clock();
		stage = new Stage();
		pauseButton = new Image(AssetLoader.pauseButton);
		resumeButton = new Image(AssetLoader.resumeButton);
		restartButton = new Image(AssetLoader.restartButton);//TODO: create restart button
		
		yourBitmapFontName = new BitmapFont(true);
		posDistLabX = 60;
		posDistLabY = 15;
		gameOverStage = new Stage();
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

}
