package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.helpers.AssetLoader;

public class GameInterface {
	
	private Clock clock;
	private Stage stage;
	private Image pauseButton;
	
	public GameInterface (){
		clock = new Clock();
		stage = new Stage();
		pauseButton = new Image(AssetLoader.pauseButton);
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
	
	public Image getPauseButton(){
		return pauseButton;
	}

}
