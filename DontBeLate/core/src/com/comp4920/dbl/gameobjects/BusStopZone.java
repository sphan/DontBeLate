package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.math.Vector2;

// Bus Stop Zone is the shaded area on the road where the bus stops.

public class BusStopZone {

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	
	private int y;
	private int x;
	private static final int WIDTH = 75;
	private static final int HEIGHT = 125;

	
	public BusStopZone(float x, float y) {
		position = new Vector2(x,y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = Road.DEFAULT_SPEED;
	}
	
	public float  getX() {
		return position.x;
	}

	
	public float  getY() {
		return position.y;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
	}
}

