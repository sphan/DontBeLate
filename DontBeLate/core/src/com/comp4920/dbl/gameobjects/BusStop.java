package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
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
		
	private static final int WIDTH = 75;
	private static final int HEIGHT = 150;
	private final int EDGE_OF_ROAD = Gdx.graphics.getWidth()/2-WIDTH;
	
	// the distance between bus stops.
	public static int distance = 300;	// this needs a better name!
	public static final int firstX = distance*2;
	
	// the time available 
	private int AVAILABLE_TIME = 300;
	private int timeRemaining;
	private Clock clock;
	
	// whether the bus can be stopped inside this stop or not
	private boolean canContain;
	private boolean stopped;
	private long stoppedTime;
	private Rectangle boundingRectangle;

	//TODO
	//private List<Passenger> passengers;
	

	private Animation busstopAnimation;
	
	public BusStop(int y) {
		position = new Vector2(EDGE_OF_ROAD, y);
		busstopAnimation = AssetLoader.roadworkAnimation;	//TODO: get image of busstop!
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = Road.getRoadSpeed();        
        clock = new Clock();
        canContain = true;
        stopped = false;
        stoppedTime = 0;
        boundingRectangle = new Rectangle();
	}
	
	public void update(float delta) {
		if (!stopped) {
			position.y += delta*(velocity.y + (Road.getRoadSpeed()-Road.DEFAULT_SPEED));
			boundingRectangle.set(position.x, position.y, WIDTH, HEIGHT);
			timeRemaining = (int) (AVAILABLE_TIME - clock.getElapsedTime());
			System.out.println(timeRemaining + "seconds left!");
		}
	}

	public void replace() {
		this.position.set(EDGE_OF_ROAD, -distance);
		canContain = true;
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


	@Override
	public int getDistance(int y) {
		return 0;
	}

	@Override
	public Animation getAnimation() {
		return busstopAnimation;
	}

	@Override
	public boolean offScreen() {
		int screenHeight = Gdx.graphics.getHeight();
		return (this.getY() > screenHeight);
	}

	@Override
	public boolean onScreen() {
		return (this.getY()+HEIGHT/2 > 0);
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
		clock = new Clock();
	}
	
	public void stop() {
		this.stopped = true;
		this.stoppedTime = System.currentTimeMillis();
		this.velocity.set(0,0);
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
}
