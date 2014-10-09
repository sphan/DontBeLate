package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
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
	private int TIME_AVAILABLE = 30;
	private int timeRemaining;
	private Clock clock;
	
	//TODO
	//private BusStopZone busStopZone;
	//private List<Passenger> passengers;
	

	private Animation busstopAnimation;
	
	public BusStop(int y) {
		position = new Vector2(EDGE_OF_ROAD, y);
		busstopAnimation = AssetLoader.roadworkAnimation;	//TODO: get image of busstop!
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = Road.DEFAULT_SPEED;
        clock = new Clock();
		//busStopZone = new BusStopZone(position.x, position.y);
	}
	
	public void update(float delta) {
		position.y += delta*(velocity.y + (Road.getRoadSpeed()-Road.DEFAULT_SPEED));
		//boundingRectangle.set(position.x, position.y, WIDTH, HEIGHT);	//TODO: check these numbers
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
		return (this.getY()-HEIGHT/2 > screenHeight/2);
	}

	@Override
	public boolean onScreen() {
		return (this.getY()+HEIGHT > 0);
	}
	
	@Override 
	public boolean contains(Bus bus) {
		return false;
	}

	
}
