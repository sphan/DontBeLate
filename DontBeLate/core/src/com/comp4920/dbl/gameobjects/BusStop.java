package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.AssetLoader;

public class BusStop implements Checkpoint {

	// y-coord is absolute, is negative until it appears on-screen.
	// only render stop when it reaches the screen though.
	// x-coord is the edge of the road and is the same for all busstops
	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;
		
	private static int WIDTH = 75;
	private static final int HEIGHT = 150;
	private static final int WARNINGSIDE = 35;
	private final int EDGE_OF_ROAD = (Gdx.graphics.getWidth()/2-WIDTH);
	
	// the distance between bus stops.
	public static int distance = 7500;	// this needs a better name!
	public static final int firstX = distance;
	private static boolean leftSide;
	
	//distance of warning ahead of the bus stop
	public static int warningDistanceAfter = 1000;
	
	// the time available 
	private int AVAILABLE_TIME = 15;
	// the duration the player spends at the bus stop
	private static final int STOP_DURATION = 2000;
	private Clock clock;
	
	// whether the bus can be stopped inside this stop or not
	private boolean canContain;
	private boolean stopped;
	private long stoppedTime;
	private Rectangle boundingRectangle;

	private Animation busStopAnimationLeft;
	private Animation busStopAnimationRight;
	private Animation busStopWarningAnimation;
	
	public BusStop(int y) {
		leftSide = false;
		position = new Vector2(getAlternateSide(), y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = Road.getRoadSpeed();        
        clock = new Clock();
        canContain = true;
        stopped = false;
        stoppedTime = 0;
        boundingRectangle = new Rectangle();
	    busStopAnimationLeft = AssetLoader.busStopAnimationLeft;
	    busStopAnimationRight = AssetLoader.busStopAnimationRight;
	    busStopWarningAnimation = AssetLoader.busStopWarningAnimation;
	}
	
	private int getAlternateSide() {
		if (leftSide == true) {
			leftSide = false;
			return EDGE_OF_ROAD;
		}
		leftSide = true;
		return 0;
	}
	
	public void update(float delta) {
		if (!stopped) {
			position.y -= delta*((Road.getRoadSpeed()));
			// the hit box should extend beyond the left edge so the bus doesnt need to be entirely within it.
			boundingRectangle.set(position.x-WIDTH/2, position.y, (float) (WIDTH*2), HEIGHT);
			//System.out.println(timeRemaining + "seconds left!");
		}
	}

	public void replace() {
		this.position.set(getAlternateSide(), distance);
		canContain = true;
		stoppedTime = 0;
	}
	
	
	@Override
	// Here we just us the contains method to check if the bus rectangle is inside the bus stop rectangle.
	public boolean contains(Bus bus) {
		if (this.canContain) {
			if (this.boundingRectangle.contains(bus.getHitBox())) {
				this.canContain = false;
				return true;
			}
		}
		return false;
	}

	// Starts the bus stop moving at the same speed as the road
	//	and disables the contains() method as the bus stop is not 
	//	being used anymore.
	public void resume() {
		this.stopped = false;
		this.velocity.set(0, Road.getRoadSpeed());
		//clock = new Clock();
		clock.start();
		clock.addTime(15);
	}
	
	public void stop() {
		clock.stop();
		this.stopped = true;
		this.stoppedTime = System.currentTimeMillis();
		this.velocity.set(0,0);
		System.out.println("Player is awarded " + getRemainingTime() + " points!");
	}
	
	public boolean isStopped() {
		return stopped;
	}
	
	public long getTimeStoppedAt() {
		return this.stoppedTime;
	}
	
	public int getAvailableTime() {
		return AVAILABLE_TIME;
	}
	
	// returns time left on the clock.
	public int getRemainingTime() {
		return(int) (AVAILABLE_TIME - clock.getElapsedTime());
	}
	
	public int getStopDuration() {
		return STOP_DURATION;
	}
	
	public Clock getClock() {
		return clock;
	}

	public float  getX() {
		return position.x;
	}
	
	public float  getY() {
		return position.y;
	}
	
	
	public float  getWarningX() {
		if (leftSide == true) {
			return position.x+ WIDTH/8;
		} 
		return EDGE_OF_ROAD + WIDTH/2;
	}
	
	public float  getWarningY() {
		return position.y - warningDistanceAfter;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getWidth() {
		return WIDTH;
	}

	public int getWarningSideLen() {
		return WARNINGSIDE;
	}
	
	public int getWarningDistanceAfter() {
		return warningDistanceAfter;
	}
	
	@Override
	public Animation getAnimation() {
		if (!leftSide) {
			return busStopAnimationRight;
		}
		return busStopAnimationLeft;
	}
	
	public Animation getWarningAnimation() {
		return busStopWarningAnimation;
	}
	
	@Override
	public boolean offScreen() {
		return (this.getY() < -HEIGHT);
	}

	@Override
	public boolean onScreen() {
		return (this.getY() < Gdx.graphics.getHeight());
	}
	
	public boolean isLeftSide(){
		return leftSide;
	}

	
}
