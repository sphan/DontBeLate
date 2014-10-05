package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.AssetLoader;

public class Car implements Obstacle{

	public static final int MAX_CAR_SPEED = 250;
	public static final int MIN_CAR_SPEED = 150;

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;

	private Animation carAnimation;
	
	protected Rectangle boundingRectangle;
	
	public static final int WIDTH = 40;
	public static final int HEIGHT = 80;
	
	protected static final int CAR_WIDTH = 40;
	protected static final int CAR_HEIGHT = 80;	

	protected static boolean randomStartSpeed = true;
	private boolean stopped;
	private int maxSpeed;
	private int minSpeed;

	int defaultSpeed = 100;	//used if randomStartSpeed = false
	
	// does not take an x or y coord
	// x is generated by getRandomX
	// y is the same every time
	public Car() {
		stopped = false;
        maxSpeed = MAX_CAR_SPEED;
        minSpeed = MIN_CAR_SPEED;
		int x = 10;
		int y = -CAR_HEIGHT;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = genStartSpeed();
        boundingRectangle = new Rectangle();
        carAnimation = AssetLoader.carAnimation;
	}
	
	//generates a car with a maximum speed
	public Car(int x_position, int maxSpeed) {
        minSpeed = MIN_CAR_SPEED;
        this.maxSpeed = maxSpeed;
		int x = x_position;
		int y = -CAR_HEIGHT;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = genStartSpeed();
        boundingRectangle = new Rectangle();
        carAnimation = AssetLoader.carAnimation;
	}
	
	
	
	public void update(float delta) {
		if(!stopped){
			position.y += delta*(velocity.y + (Road.getRoadSpeed()-Road.DEFAULT_SPEED));
			boundingRectangle.set(position.x, position.y, CAR_WIDTH, CAR_HEIGHT);	//TODO: check these numbers
		}
	}
	
	
	// returns a random starting x-coord
	//TODO: 'assign' columns to cars so they never overlap - maybe 'lanes'?
	// Cars do not generate own starting position anymore. Determined by which lane it belongs to.
	public int getStartX() {
		//Random rand = new Random();
		//int randomX = rand.nextInt((max - min) + 1) + min;
		//TODO: How to use this unused method?
		return 10;
	}
	
	
	public int genStartSpeed() {
		int speed = defaultSpeed;
		if (randomStartSpeed) {
			Random rand = new Random();
			speed = rand.nextInt((maxSpeed - minSpeed) + 1) + minSpeed;
		}
		return speed;
	}
	
	// Returns true if the coords of the car are offscreen.
	public boolean offScreen() {
		int screenHeight = Gdx.graphics.getHeight();
		return (this.getY()-CAR_HEIGHT/2 > screenHeight/2);
	}
	
	
	public void stop() {
		stopped = true;
	}
	
	public void start() {
		stopped = false;
	}

		
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return CAR_WIDTH;
    }

    public float getHeight() {
        return CAR_HEIGHT;
    }

    public Rectangle getHitBox() {
    	return boundingRectangle;
    }
    
    public float getVerticalSpeed(){
    	return velocity.y;
    }

    public Animation getAnimation() {
    	return carAnimation;
    }
}
