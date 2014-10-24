package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;

/*
 * Interface for obstacles (eg cars)
 * Provides methods for interacting with the obstacle.s
 * Also handles collisions.
 */


public interface Obstacle {
	public static final int OBSTACLE_START_Y = 400;
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
	public void start();
	
    public float getX();
    public float getY();
    public float getWidth();
    public float getHeight();
    public float getVerticalSpeed();
    public Rectangle getHitBox();
	public Animation getAnimation();
	public void hit();
	public boolean isHit();
	
}
