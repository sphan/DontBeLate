package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/*
 * Interface for obstacles (eg cars)
 * Provides methods for interacting with the obstacle.s
 * Also handles collisions.
 */


public interface Obstacle {

	// use to set the x-coord for spawn location
	public int getStartX();
	
	// use to generate a random speed
	// can be disabled by modifying the boolean randomStartSpeed
	public int genStartSpeed();
	
	// Returns true if the coords of the car are offscreen.
	public boolean offScreen();
	
	// Updates the object's position
	public void update(float delta);
	
	// Stops the object
	public void stop();
	
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
    public Rectangle getHitBox();
	
}
