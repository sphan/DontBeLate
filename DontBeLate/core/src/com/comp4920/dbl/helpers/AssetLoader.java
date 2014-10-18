package com.comp4920.dbl.helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
	
	public static Texture textureCar1;
	public static TextureRegion realCar1;
	public static Animation carAnimation1;
	
	public static Texture textureCar2;
	public static TextureRegion realCar2;
	public static Animation carAnimation2;
	
	public static Texture textureCar3;
	public static TextureRegion realCar3;
	public static Animation carAnimation3;
	
	public static Texture textureCar4;
	public static TextureRegion realCar4;
	public static Animation carAnimation4;
	
	public static Texture textureCar5;
	public static TextureRegion realCar5;
	public static Animation carAnimation5;
	
	public static Texture textureCar6;
	public static TextureRegion realCar6;
	public static Animation carAnimation6;
	
	public static Texture textureCar7;
	public static TextureRegion realCar7;
	public static Animation carAnimation7;
	
	public static Texture textureGoldCoin;
	public static TextureRegion goldCoin;
	public static Animation goldCoinAnimation;
	
	public static Texture textureOpalCard;
	public static TextureRegion opalCard;
	public static Animation opalAnimation;
	
	public static Texture textureTree;
	public static TextureRegion tree;
	public static Animation treeAnimation;
	
	public static Texture textureRoad;
	public static TextureRegion road;
	
	public static Texture textureRoadwork;
	public static TextureRegion roadwork;
	public static Animation roadworkAnimation;

	public static Texture textureRoadworkWarning;
	public static TextureRegion roadworkWarning;
	public static Animation roadworkWarningAnimation;

	public static Texture texturebusStop;
	public static TextureRegion busStopRight;
	public static Animation busStopAnimationRight;
	public static TextureRegion busStopLeft;
	public static Animation busStopAnimationLeft;

	
	public static Texture texturebusStopWarning;
	public static TextureRegion busStopWarning;
	public static Animation busStopWarningAnimation;
	
	// menu background textures
	public static Texture startMenuBackground;
	public static TextureRegion startMenuBgRegion;
	public static Texture pauseMenuBackground;
	public static TextureRegion pauseMenuBgRegion;
	public static Texture gameOverBackground;
	public static TextureRegion gameOverBgRegion;
	
	// button textures
	public static Texture pauseButton;
	public static Texture resumeButton;
	public static Texture restartButton;
	public static Texture endGameButton;
	public static Texture yesButton;
	public static Texture noButton;
	
	public static Sound carCrashSound;
	public static Sound coinCollectSound;

	// display font
	private static Font ledFont;
	
	public static void load() {
		// Bus
		texture = new Texture(Gdx.files.internal("yellow-bus.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		redBus = new TextureRegion(texture);
		redBus.flip(false, false);
		TextureRegion[] buses = { redBus, redBus, redBus };
		busAnimation = new Animation(0.06f, buses);
		busAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Red
		textureCar = new Texture(Gdx.files.internal("real_car0.png"));
		textureCar.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		redCar = new TextureRegion(textureCar);
		redCar.flip(false, false);
		TextureRegion[] cars = { redCar, redCar, redCar };
		carAnimation = new Animation(0.06f, cars);
		carAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Real 1
		textureCar1 = new Texture(Gdx.files.internal("real_car1.png"));
		textureCar1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar1 = new TextureRegion(textureCar1);
		realCar1.flip(false, false);
		TextureRegion[] cars1 = { realCar1, realCar1, realCar1 };
		carAnimation1 = new Animation(0.06f, cars1);
		carAnimation1.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Real 2
		textureCar2 = new Texture(Gdx.files.internal("real_car2.png"));
		textureCar2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar2 = new TextureRegion(textureCar2);
		realCar2.flip(false, false);
		TextureRegion[] cars2 = { realCar2, realCar2, realCar2 };
		carAnimation2 = new Animation(0.06f, cars2);
		carAnimation2.setPlayMode(Animation.PlayMode.LOOP);

		// Car Real 3
		textureCar3 = new Texture(Gdx.files.internal("real_car3.png"));
		textureCar3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar3 = new TextureRegion(textureCar3);
		realCar3.flip(false, false);
		TextureRegion[] cars3 = { realCar3, realCar3, realCar3 };
		carAnimation3 = new Animation(0.06f, cars3);
		carAnimation3.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Real 4
		textureCar4 = new Texture(Gdx.files.internal("real_car4.png"));
		textureCar4.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar4 = new TextureRegion(textureCar4);
		realCar4.flip(false, false);
		TextureRegion[] cars4 = { realCar4, realCar4, realCar4 };
		carAnimation4 = new Animation(0.06f, cars4);
		carAnimation4.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Real 5
		textureCar5 = new Texture(Gdx.files.internal("real_car5.png"));
		textureCar5.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar5 = new TextureRegion(textureCar5);
		realCar5.flip(false, false);
		TextureRegion[] cars5 = { realCar5, realCar5, realCar5 };
		carAnimation5 = new Animation(0.06f, cars5);
		carAnimation5.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Real 6
		textureCar6 = new Texture(Gdx.files.internal("real_car6.png"));
		textureCar6.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar6 = new TextureRegion(textureCar6);
		realCar6.flip(false, false);
		TextureRegion[] cars6 = { realCar6, realCar6, realCar6 };
		carAnimation6 = new Animation(0.06f, cars6);
		carAnimation6.setPlayMode(Animation.PlayMode.LOOP);
		
		// Car Real 7
		textureCar7 = new Texture(Gdx.files.internal("real_car7.png"));
		textureCar7.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		realCar7 = new TextureRegion(textureCar7);
		realCar7.flip(false, false);
		TextureRegion[] cars7 = { realCar7, realCar7, realCar7 };
		carAnimation7 = new Animation(0.06f, cars7);
		carAnimation7.setPlayMode(Animation.PlayMode.LOOP);
		
		textureGoldCoin = new Texture(Gdx.files.internal("gold_coin.png"));
		textureGoldCoin.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		goldCoin = new TextureRegion(textureGoldCoin);
		goldCoin.flip(false, false);
		TextureRegion[] coin = { goldCoin, goldCoin, goldCoin };
		goldCoinAnimation = new Animation(0.06f, coin);
		goldCoinAnimation.setPlayMode(Animation.PlayMode.LOOP);
	
		textureOpalCard = new Texture(Gdx.files.internal("opal.png"));
		textureOpalCard.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		opalCard = new TextureRegion(textureOpalCard);
		opalCard.flip(false, false);
		TextureRegion[] opal = { opalCard, opalCard, opalCard };
		opalAnimation = new Animation(0.06f, opal);
		opalAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		
		//Roadwork
		textureRoadwork = new Texture(Gdx.files.internal("roadwork.jpg"));
		textureRoadwork.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		roadwork = new TextureRegion(textureRoadwork);
		roadwork.flip(false, false);
		TextureRegion[] roadworks = {roadwork, roadwork, roadwork};
		roadworkAnimation = new Animation(0.06f, roadworks);
		roadworkAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// Roadwork Warning
		textureRoadworkWarning = new Texture(Gdx.files.internal("roadworkWarning.png"));
		textureRoadworkWarning.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		roadworkWarning = new TextureRegion(textureRoadworkWarning);
		roadworkWarning.flip(false, false);
		TextureRegion[] roadworkWarnings = {roadworkWarning, roadworkWarning, roadworkWarning};
		roadworkWarningAnimation = new Animation(0.06f, roadworkWarnings);
		roadworkWarningAnimation.setPlayMode(Animation.PlayMode.LOOP);

		// Bus stop 
		texturebusStop = new Texture(Gdx.files.internal("busstop-with-shelter.png"));
		texturebusStop.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		// Right side
		busStopRight = new TextureRegion(texturebusStop);
		busStopRight.flip(false, false);
		TextureRegion[] busStopRights = {busStopRight, busStopRight, busStopRight};
		busStopAnimationRight = new Animation(0.06f, busStopRights);
		busStopAnimationRight.setPlayMode(Animation.PlayMode.LOOP);
		// Left side
		busStopLeft = new TextureRegion(texturebusStop);
		busStopLeft.flip(true, false);
		TextureRegion[] busStopLefts = {busStopLeft, busStopLeft, busStopLeft};
		busStopAnimationLeft = new Animation(0.06f, busStopLefts);
		busStopAnimationLeft.setPlayMode(Animation.PlayMode.LOOP);
		
		// Bus stop warning
		texturebusStopWarning = new Texture(Gdx.files.internal("busstop_warning.png"));
		texturebusStopWarning.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		busStopWarning = new TextureRegion(texturebusStopWarning);
		busStopWarning.flip(false, false);
		TextureRegion[] busStopWarn = {busStopWarning, busStopWarning, busStopWarning};
		busStopWarningAnimation = new Animation(0.06f, busStopWarn);
		busStopWarningAnimation.setPlayMode(Animation.PlayMode.LOOP);

		
		
		startGameButton = new Texture(Gdx.files.internal("start-game-button.png"));
		quitButton = new Texture(Gdx.files.internal("quit-button.png"));
		
		//road texture
		//textureRoad = new Texture(Gdx.files.internal("street.jpg"));
		//road = new TextureRegion(textureRoad, 0, 0, 770, 1037);
		
		textureRoad = new Texture(Gdx.files.internal("highway_road4lane2.png"));
		road = new TextureRegion(textureRoad, 0, 0, 320, 512);
		
		// Pause button
		pauseButton = new Texture(Gdx.files.internal("pause-button.png"));
		
		// Resume button
		resumeButton = new Texture(Gdx.files.internal("resume-button.png"));
		
		// Restart button
		restartButton = new Texture(Gdx.files.internal("restart-button.png"));
		
		// End Game Button
		endGameButton = new Texture(Gdx.files.internal("end-game-button.png"));
		
		// Yes and No Button
		yesButton = new Texture(Gdx.files.internal("yes-button.png"));
		noButton = new Texture(Gdx.files.internal("no-button.png"));
		
		startMenuBackground = new Texture(Gdx.files.internal("start-menu-background.png"));
		startMenuBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		startMenuBgRegion = new TextureRegion(startMenuBackground, 0, 0,
				startMenuBackground.getWidth(), startMenuBackground.getHeight());
		
		pauseMenuBackground = new Texture(Gdx.files.internal("pause-menu-background.png"));
		pauseMenuBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pauseMenuBgRegion = new TextureRegion(pauseMenuBackground, 0, 0,
				pauseMenuBackground.getWidth(), pauseMenuBackground.getHeight());
		
		gameOverBackground = new Texture(Gdx.files.internal("gameover-background.png"));
		gameOverBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		gameOverBgRegion = new TextureRegion(gameOverBackground, 0, 0,
				gameOverBackground.getWidth(), gameOverBackground.getHeight());
		
		coinCollectSound = Gdx.audio.newSound(Gdx.files.internal("sound-effects/coin-get.ogg"));
		
		// Font
		// TODO: need to extract font extension for this to work
		try {
			ledFont = Font.createFont(Font.TRUETYPE_FONT, Gdx.files.internal("scoreboard.ttf").read());
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void dispose() {
		texture.dispose();
		textureCar.dispose();
		textureCar1.dispose();
		textureCar2.dispose();
		textureCar3.dispose();
		textureCar4.dispose();
		textureCar5.dispose();
		textureCar6.dispose();
		textureCar7.dispose();
		startGameButton.dispose();
		quitButton.dispose();
		pauseButton.dispose();
		resumeButton.dispose();
		restartButton.dispose();
		endGameButton.dispose();
		yesButton.dispose();
		noButton.dispose();
		startMenuBackground.dispose();
		pauseMenuBackground.dispose();
		gameOverBackground.dispose();
		coinCollectSound.dispose();
		
		//
		textureRoad.dispose();
		textureRoadwork.dispose();
		textureRoadworkWarning.dispose();
		
		textureGoldCoin.dispose();
	}




}
