package com.comp4920.dbl.gameobjects;

import java.util.Objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Clock {

	private long startTime;
	private long elapsedTime;
	private long savedElapsedTime;
	private String displayText;
	private boolean stopped;
	private float posX;
	private float posY;


	public Clock() {

		stopped = false;
		startTime = System.currentTimeMillis();
		elapsedTime = 0;
		savedElapsedTime = 0;
		displayText = "Time remaining: ";
	    posY = 32;
	    posX = 160;
	}
	
	// Returns elapsed time in seconds.
	public long getElapsedTime() {
		if (!stopped) {
			elapsedTime = (System.currentTimeMillis() - startTime)/1000;
		}
		return elapsedTime;
	}
	
	public String getDisplayText() {
		return displayText;
	}
	
	public String getTimeText() {
		return Objects.toString(getElapsedTime(), null);
	}
		
	public float getY() {
		return posY;
	}
	
	public float getX() {
		return posX;
	}
	
	public void stop() {
		stopped = true;
		//need to store current time
		savedElapsedTime = elapsedTime;
	}
	
	public void start() {
		stopped = false;
		//set the start time to when we paused
		startTime = (System.currentTimeMillis() - (savedElapsedTime * 1000));
	}
	
	public void addTime(int seconds) {
		startTime += seconds*1000;
	}
}
