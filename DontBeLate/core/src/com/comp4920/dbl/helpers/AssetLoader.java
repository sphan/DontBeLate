package com.comp4920.dbl.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static Texture texture;
	public static TextureRegion redBus;
	public static Animation busAnimation;
	public static Texture startGameButton;
	public static Texture quitButton;
	
	public static Texture textureCar;
	public static TextureRegion redCar;
	public static Animation carAnimation;
	
	public static Texture textureRoad;
	public static TextureRegion road;
	
	public static Texture textureRoadwork;
	public static TextureRegion roadwork;
	public static Animation roadworkAnimation;

	public static Texture textureRoadworkWarning;
	public static TextureRegion roadworkWarning;
	public static Animation roadworkWarningAnimation;

	
	public static Texture pauseButton;
	public static Texture resumeButton;

	public static void load() {
		// Bus
		texture = new Texture(Gdx.files.internal("red-bus_crop.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		redBus = new TextureRegion(texture);
		redBus.flip(false, true);
		TextureRegion[] buses = { redBus, redBus, redBus };
		busAnimation = new Animation(0.06f, buses);
		busAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car
		textureCar = new Texture(Gdx.files.internal("red-car_crop.jpg"));
		textureCar.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		redCar = new TextureRegion(textureCar);
		redCar.flip(false, true);
		TextureRegion[] cars = { redCar, redCar, redCar };
		carAnimation = new Animation(0.06f, cars);
		carAnimation.setPlayMode(Animation.PlayMode.LOOP);

		//Roadwork
		textureRoadwork = new Texture(Gdx.files.internal("roadwork.jpg"));
		textureRoadwork.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		roadwork = new TextureRegion(textureRoadwork);
		roadwork.flip(false, true);
		TextureRegion[] roadworks = {roadwork, roadwork, roadwork};
		roadworkAnimation = new Animation(0.06f, roadworks);
		roadworkAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// Roadwork Warning
		textureRoadworkWarning = new Texture(Gdx.files.internal("roadworkWarning.png"));
		textureRoadworkWarning.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		roadworkWarning = new TextureRegion(textureRoadworkWarning);
		roadworkWarning.flip(false, true);
		TextureRegion[] roadworkWarnings = {roadworkWarning, roadworkWarning, roadworkWarning};
		roadworkWarningAnimation = new Animation(0.06f, roadworkWarnings);
		roadworkWarningAnimation.setPlayMode(Animation.PlayMode.LOOP);

		
		startGameButton = new Texture(Gdx.files.internal("start-game-button.png"));
		quitButton = new Texture(Gdx.files.internal("quit-button.png"));
		
		//road texture
		//textureRoad = new Texture(Gdx.files.internal("street.jpg"));
		//road = new TextureRegion(textureRoad, 0, 0, 770, 1037);
		
		textureRoad = new Texture(Gdx.files.internal("highway_road.png"));
		road = new TextureRegion(textureRoad, 0, 0, 320, 512);
		
		// Pause button
		pauseButton = new Texture(Gdx.files.internal("pause-button.png"));
		
		// Resume button
		resumeButton = new Texture(Gdx.files.internal("resume-button.png"));
	}
	
	public static void dispose() {
		texture.dispose();
		textureCar.dispose();
		startGameButton.dispose();
		quitButton.dispose();
		pauseButton.dispose();
		resumeButton.dispose();
		
		//
		textureRoad.dispose();
	}
}
