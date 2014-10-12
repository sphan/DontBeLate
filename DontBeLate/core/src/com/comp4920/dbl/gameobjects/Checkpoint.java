package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;

public interface Checkpoint {

	// Returns the x-coord
	public float  getX();
	
	// Returns the y-coord
	public float getY();
	public int getHeight();
	public int getWidth();

	public void update(float delta);
	public boolean offScreen();
	public boolean onScreen();
	public boolean contains(Bus bus);
	public Animation getAnimation();
	
}
