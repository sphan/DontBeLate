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
	
	public static void load() {
		texture = new Texture(Gdx.files.internal("red-bus.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		redBus = new TextureRegion(texture);
		redBus.flip(false, true);
		
		TextureRegion[] buses = { redBus, redBus, redBus };
		busAnimation = new Animation(0.06f, buses);
		busAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		startGameButton = new Texture(Gdx.files.internal("start-game-button.png"));
		quitButton = new Texture(Gdx.files.internal("quit-button.png"));
	}
	
	public static void dispose() {
		texture.dispose();
		startGameButton.dispose();
		quitButton.dispose();
	}
}
