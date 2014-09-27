package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.InputHandler;

public class Car implements Obstacle{
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	private static final int width = 40;
	private static final int height = 80;	
	
	private int speed;
	private boolean randomStartSpeed = true;
	private int maxSpeed = 200;
	private int minSpeed = 75;
	int defaultSpeed = 100;	//used if randomStartSpeed = false
	
	// does not take an x or y coord
	// x is generated by getRandomX
	// y is the same every time
	public Car() {
		int x = getStartX();
		//int y = height/2; //previous value caused car to spawn on the road
		int y = -height;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        this.speed = genStartSpeed();
	}
	
	public void update(float delta) {
		position.y += delta*speed;       
    }
	
	
	// returns a random starting x-coord
	//TODO: 'assign' columns to cars so they never overlap - maybe 'lanes'?
	public int getStartX() {
		int min = width/2;
		int max = Gdx.graphics.getWidth()/2 - width/2;
		
		Random rand = new Random();
		int randomX = rand.nextInt((max - min) + 1) + min;
		
		return randomX;
	}
	
	public int genStartSpeed() {
		int speed = defaultSpeed;
		if (randomStartSpeed) {
			Random rand = new Random();
			speed = rand.nextInt((maxSpeed - minSpeed) + 1) + minSpeed;
		}
		//System.out.println(speed);
		return speed;

	}
	
	// Returns true if the coords of the car are offscreen.
	public boolean offScreen() {
		int screenHeight = Gdx.graphics.getHeight();
		return (this.getY()-this.height/2 > screenHeight/2);
	}
	
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


}
