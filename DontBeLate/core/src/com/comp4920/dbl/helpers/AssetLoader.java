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
	


	public static void load() {
		// Bus
		texture = new Texture(Gdx.files.internal("red-bus.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		redBus = new TextureRegion(texture);
		redBus.flip(false, true);
		TextureRegion[] buses = { redBus, redBus, redBus };
		busAnimation = new Animation(0.06f, buses);
		busAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car
		textureCar = new Texture(Gdx.files.internal("red-car.jpg"));
		textureCar.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		redCar = new TextureRegion(textureCar);
		redCar.flip(false, true);

		TextureRegion[] cars = { redCar, redCar, redCar };
		carAnimation = new Animation(0.06f, cars);
		carAnimation.setPlayMode(Animation.PlayMode.LOOP);

		startGameButton = new Texture(Gdx.files.internal("start-game-button.png"));
		quitButton = new Texture(Gdx.files.internal("quit-button.png"));
		
		//road texture
		textureRoad = new Texture(Gdx.files.internal("street.jpg"));
		road = new TextureRegion(textureRoad, 0, 0, 770, 1037);
	}
	
	public static void dispose() {
		texture.dispose();
		textureCar.dispose();
		startGameButton.dispose();
		quitButton.dispose();
		
		//
		textureRoad.dispose();
	}
}
