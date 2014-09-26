package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public interface Dodgeable {

	// use to set the x-coord for spawn location
	public int getStartX();
	
	// Returns true if the coords of the car are offscreen.
	public boolean offScreen();
	
	public void update(float delta);
	
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();

	
}
