package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.AssetLoader;

public class Bike implements Obstacle{
	public static final int MAX_BIKE_SPEED = 335;
	public static final int MIN_BIKE_SPEED = 275;

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;

	private Animation bikeAnimation;
	
	protected Rectangle boundingRectangle;
	
	public static final int WIDTH = 40;
	public static final int HEIGHT = 80;
	
	protected static final int BIKE_WIDTH = 15;
	protected static final int BIKE_HEIGHT = 40;	

	protected static boolean randomStartSpeed = true;
	private boolean stopped;
	private int maxSpeed;
	private int minSpeed;

	private boolean isHit = false;
	protected int defaultSpeed = 100;	//used if randomStartSpeed = false
	
	// does not take an x or y coord
	// x is generated by getRandomX
	// y is the same every time
	public Bike() {
		stopped = false;
        maxSpeed = MAX_BIKE_SPEED;
        minSpeed = MIN_BIKE_SPEED;
		int x = 10;
		int y = 400;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 250);
        acceleration = new Vector2(0, 100);
        velocity.y = genStartSpeed();
        boundingRectangle = new Rectangle();
        bikeAnimation = AssetLoader.bikeAnimation;
	}
	
	
	//generates a car with a minimum speed with default colour
	public Bike(int x_position, int minSpeed) {
		maxSpeed = MAX_BIKE_SPEED;
        this.minSpeed = minSpeed;
		int x = x_position;
		int y = 400;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = genStartSpeed();
        boundingRectangle = new Rectangle();
        bikeAnimation = AssetLoader.bikeAnimation;
	}
	
	public void update(float delta) {
		if(!stopped){
			position.y -= delta*(-velocity.y + (Road.getRoadSpeed()));
			boundingRectangle.set(position.x, position.y, BIKE_WIDTH, BIKE_HEIGHT);	//TODO: check these numbers
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
		return (this.getY() < -HEIGHT || this.getY() > 1000); 
		//to prevent car popping out although cars now stay offscreen for a while
	}
	
	public void stop() {
		stopped = true;
	}
	
	public void start() {
		stopped = false;
	}
		
    public float getX() {
        return position.x + 8;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return BIKE_WIDTH;
    }

    public float getHeight() {
        return BIKE_HEIGHT;
    }

    public Rectangle getHitBox() {
    	return boundingRectangle;
    }
    
    public float getVerticalSpeed(){
    	return velocity.y;
    }

    public Animation getAnimation() {
    	return bikeAnimation;
    }
    
    public void hit(){
    	//System.out.println("hit!");
    	isHit = true;
    }
    
    public boolean isHit(){
    	return isHit;
    }
    
    public void setSpeed(float speed) {
    	this.velocity.y = speed;
    }

}
