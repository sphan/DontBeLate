package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public interface Dodgeable {

	// use to set the x-coord for spawn location
	public int getStartX();
	
	// use to generate a random speed
	// can be disabled by modifying the boolean randomStartSpeed
	public int genStartSpeed();
	
	// Returns true if the coords of the car are offscreen.
	public boolean offScreen();
	
	public void update(float delta);
	
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();

	
}
