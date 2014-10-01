package com.comp4920.dbl.gameobjects;

import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Clock {

	private long startTime;
	private long elapsedTime;
	private String displayText;
	private boolean stopped;
	private float posX;
	private float posY;
	BitmapFont font;


	public Clock() {
		stopped = false;
		startTime = System.currentTimeMillis();
		elapsedTime = 0;
		displayText = "Time: ";
	    font = new BitmapFont(true);	// true denotes the font should be flipped
	    font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    posY = 15;
	    posX = 240;
	}
	
	// Returns elapsed time in seconds.
	public long getElapsedTime() {
		if (!stopped) {
			elapsedTime = (System.currentTimeMillis() - startTime)/1000;
		}
		return elapsedTime;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public String getDisplayText() {
		return displayText + Objects.toString(getElapsedTime(), null);
	}
	
	public float getY() {
		return posY;
	}
	
	public float getX() {
		return posX;
	}
	
	public void stop() {
		stopped = true;
	}
}
