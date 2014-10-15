package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;

public interface Scenery {

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
