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
	//private int TIME_AVAILABLE = 30;
	//private int timeRemaining;
	//private Clock clock;
	
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
        //clock = new Clock();
        canContain = true;
        stopped = false;
        stoppedTime = 0;
        boundingRectangle = new Rectangle();
        //System.out.println("Bus stop created at " + position.x + ", " + position.y);
	}
	
	public void update(float delta) {
		if (!stopped) {
			position.y += delta*(velocity.y + (Road.getRoadSpeed()-Road.DEFAULT_SPEED));
			boundingRectangle.set(position.x, position.y, WIDTH, HEIGHT);	//TODO: check these numbers
			//System.out.println("Bus stop moved to " + position.x + ", " + position.y);
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
	// the bus stop contains the bus if the following conditions are met:
	//	- left x bus coord is > left bus stop coord
	//	- bottom x bus coord is < bottom x bus stop coord
	// centre of bus is below top of bus stop
	public boolean contains(Bus bus) {
		if (this.canContain) {
			/*
			float leftBus = bus.getX() - bus.getWidth()/2;
			float bottomBus = bus.getY() + bus.getHeight()/2;
			float topBus = bus.getY() - bus.getHeight()/2;
			float leftStop = position.x - WIDTH/2;
			float bottomStop = position.y + HEIGHT/2;
			float topStop = position.y - HEIGHT/2;
			if ((leftBus > leftStop) && (bottomBus < bottomStop) && (topBus > topStop)) {
				this.canContain = false;
				System.out.println("INSIDE STOP!");
				return true;
				
			}*/
			if (this.boundingRectangle.contains(bus.getHitBox())) {
				this.canContain = false;
				System.out.println("INSIDE STOP!");
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
}
