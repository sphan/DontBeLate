package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.helpers.DropsHandler.DropType;

public class CoinDrop implements Drop {
	
	public static DropType type = DropType.EXTRAPOINTS;
	public static final int MAX_DROP_SPEED = 320;
	public static final int MIN_DROP_SPEED = 300;
	
	private int x_min = 53;
	private int x_max = 286;
	
	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;

	private Animation dropAnimation;
	
	protected Rectangle boundingRectangle;
	
	public static final int WIDTH = 20;
	public static final int HEIGHT = 20;
	
	protected static final int DROP_WIDTH = 20;
	protected static final int DROP_HEIGHT = 20;	

	protected static boolean randomStartSpeed = true;
	private boolean stopped;
	private int maxSpeed;
	private int minSpeed;

	int defaultSpeed = 100;	//used if randomStartSpeed = false
	
	
	// does not take an x or y coord
	// x is generated by getRandomX
	// y is the same every time
	public CoinDrop() {
		stopped = false;
        maxSpeed = MAX_DROP_SPEED;
        minSpeed = MIN_DROP_SPEED;
		int x = getStartX();
		int y = Gdx.graphics.getHeight() + DROP_HEIGHT;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = genStartSpeed();
        boundingRectangle = new Rectangle();
        dropAnimation = AssetLoader.goldCoinAnimation; //TODO:CHANGE TO DROP IMAGE
	}
	
	
	
	//generates a car with a maximum speed with default colour
	public CoinDrop(int x_position, int maxSpeed) {
        minSpeed = MIN_DROP_SPEED;
        this.maxSpeed = maxSpeed;
		int x = x_position;
		int y = Gdx.graphics.getHeight() + DROP_HEIGHT;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = genStartSpeed();
        boundingRectangle = new Rectangle();
        dropAnimation = AssetLoader.goldCoinAnimation;
	}
	
	
	public void update(float delta) {
		if(!stopped){
			//position.y -= delta*(velocity.y + (Road.getRoadSpeed()-Road.DEFAULT_SPEED));
			position.y -= delta*Road.getRoadSpeed();
			boundingRectangle.set(position.x, position.y, DROP_WIDTH, DROP_HEIGHT);	//TODO: check these numbers
		}
	}
	
	
	// returns a random starting x-coord
	//TODO: 'assign' columns to cars so they never overlap - maybe 'lanes'?
	// Cars do not generate own starting position anymore. Determined by which lane it belongs to.
	public int getStartX() {
		Random rand = new Random();
		int laneSize = (x_max - x_min)/4;
		int randomX = rand.nextInt(4);
		
		return (randomX*laneSize) + x_min;
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
		return (this.getY() < -HEIGHT || this.getY() > 1200); 
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
        return DROP_WIDTH;
    }

    public float getHeight() {
        return DROP_HEIGHT;
    }

    public Rectangle getHitBox() {
    	return boundingRectangle;
    }
    
    public float getVerticalSpeed(){
    	return velocity.y;
    }

    public Animation getAnimation() {
    	return dropAnimation;
    }
    
    public DropType getType(){
    	return type;
    }

}
