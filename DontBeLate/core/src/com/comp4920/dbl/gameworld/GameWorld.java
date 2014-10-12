package com.comp4920.dbl.gameworld;

import java.util.List;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Drop;
import com.comp4920.dbl.gameobjects.BusStop;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.CollisionHandler;
import com.comp4920.dbl.helpers.DropsHandler;
import com.comp4920.dbl.helpers.InputHandler;
import com.comp4920.dbl.helpers.LaneHandler;

public class GameWorld {
	private Road road; //reference used to edit road/bus speed only
	private Bus bus;	
	private LaneHandler lanes;
	private DropsHandler drops;
	private CollisionHandler collisions;
	private BusStop busStop;

	private int numCars; //number of cars currently on the road
	private int numDrops; //number of cars currently on the road
	private int numDropsCollected; //number of cars currently on the road
	
	private static int maxNumCars = 5;	// max number of cars onscreen at any time
	private static int maxNumDrops = 4;	// max number of cars onscreen at any time
	private static final int carDelay = 1; 	// delay between a car going offscreen and a new car spawning
	private static float lastCarTime;
	private boolean stopped;
	
	
	public enum GameState {
		READY, RUNNING, PAUSED, GAMEOVER;
	}
	
	private GameState state;
	private int midPointX;
	public GameWorld(int midPointX) {
		this.midPointX = midPointX;
		stopped = false;
		lastCarTime = 0;
		bus = new Bus(midPointX-Bus.BUS_WIDTH/2, Bus.BUS_START_Y, Bus.BUS_WIDTH, Bus.BUS_HEIGHT);
		lanes = new LaneHandler();
		road = new Road();
		drops = new DropsHandler();
		collisions = new CollisionHandler();
		busStop = new BusStop((int) (-BusStop.firstX));
		state = GameState.READY;
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		switch (state) {
		case READY:
			updateReady(delta);
			break;
		case RUNNING:
			updateRunning(delta, busInputHandler);
			break;
		case PAUSED:
			break;
		}
	}
	
	public void updateReady(float delta) {
		start();
	}
	
	public void updateRunning(float delta, InputHandler busInputHandler) {
		if(stopped){
			return;
		}
	
		road.setRoadSpeed(bus.getForwardVelocity());
		road.update(delta);
		bus.update(delta, busInputHandler);
		lanes.update(delta);
		drops.update(delta);
		busStop.update(delta);
	}
	
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void updateCars (float runTime){
		if(stopped) {
			return;
		}

		numCars += lanes.updateObstacles();
		if (newCarTime(runTime)) {
			if ((runTime%10) < 5) { //Change proportion every 10 seconds it is random
				lanes.addObstacleRandomLane(runTime);
				lastCarTime = runTime;
				numCars++;
			} else {
				lanes.addObstacle(runTime);
				lastCarTime = runTime;
				numCars++;
			}
		}
	}
	
	public void updateCheckpoints(float runTime) {
		// check if the bus is inside a checkpoint
		if (busStop.contains(bus)) {
			// pause the bus (not the cars though) for x seconds
			road.stop();
			bus.stop();
			busStop.stop();
			lanes.roadStopped();
			// add points - just wait a few seconds for now.			
		}
		
		if (busStop.isStopped() && (System.currentTimeMillis() > busStop.getTimeStoppedAt() + busStop.getStopDuration())) {
			// start everything again and create new bus stop
			road.start();
			bus.start();
			lanes.resume();
			busStop.resume();
		}

		// check if the bus stop is off the screen
		if (busStop.offScreen()) {
			// replace the bus stop with a new one
			busStop.replace();
		}
	}
	
	
	public void updateDrops(float runTime) {
		if(stopped) {
			return;
		}
		
		numDrops = drops.updateDrops();
		
		if (newDropTime(runTime)) {
			drops.newDrop(runTime);
			numDrops++;
		}
	}

	
	public boolean checkCarCollisions (){
		for (Lane lane : lanes.getLanes()) {
			List<Obstacle> cars = lane.getObstacles();
			if (collisions.check(bus, cars)) {
				return true;
			}
		}
		return false;
	}
	
	//TODO: need it to change specific details (certain drops have certain effects)
	public boolean checkDropsCollisions (){
		
		//remove the drop from the drop list
		Drop collisionDrop = collisions.checkDrops(bus, drops.getDrops());
		if (collisionDrop != null){
			drops.removeDrop(collisionDrop);
			return true;
		}
		
		return false;
	}
	
	
	// Returns true if we should generate another car, false otherwise.
	// Spawn a new car if there are fewer than numCars on screen AND
	//	at least carDelay seconds have elapsed since the last car was spawned.
	private boolean newCarTime(float runTime) {
		return (numCars < maxNumCars && runTime > lastCarTime + carDelay);
	}
	
	// Returns true if we should generate another drop, false otherwise.
	// Spawn a new drop if there are fewer than numDrops on screen 	
	private boolean newDropTime(float runTime) {
		return (numDrops < maxNumDrops);
	}
	
	public float getDistanceToBusStop() {
		// if the bus stop is above the bus:
		if (busStop.getY() < bus.getY()) { 
			// Case a: The bus is at the bus stop
			if (busStop.contains(bus)) {
				return 0;
			}
			// Case b: The bus stop is above y0
			if (busStop.getY() < 0) {
				return (bus.getY() + Math.abs(busStop.getY()))/Road.CONVERT_METERS;
			}
			// Case c: The bus stop is beneath y0 (and on the screen) 
			return (bus.getY() - busStop.getY())/Road.CONVERT_METERS;
		// else we need to calculate the distance to a stop that doesn't exist yet...
		} else {
			return 0;
		}
	}
	
	
	public Bus getBus() {
		return bus;
	}
	
	
	public Road getRoad(){
		return road;
	}
	
	public List<Lane> getLaneList() {
		return lanes.getLanes();
	}
	
	public List<Drop> getDropsList() {
		return drops.getDrops();
	}
	
	public BusStop getBusStop() {
		return busStop;
	}
	
	public void stop(){
		stopped = true;
		bus.stop();
		road.stop();
		lanes.stop();
	}
	
	public void start() {
		state = GameState.RUNNING;
		stopped = false;
		bus.start();
		road.start();
		lanes.start();
	}
	
	public void pause() {
		state = GameState.PAUSED;
		stop();
	}
	
	public void endGame(){
		state = GameState.GAMEOVER;
		stop();
	}

	public boolean isReady() {
		return state == GameState.READY;
	}
	
	public boolean isPaused() {
		return state == GameState.PAUSED;
	}
	
	public boolean isGameOver() {
		return state == GameState.GAMEOVER;
	}

	public void incrementCoinCounter(){
		numDropsCollected++;
	}
	
	public void decrementCoinCounter(int n){
		numDropsCollected -= n;
		if(numDropsCollected < 0){
			numDropsCollected = 0;
		}
	}
	
	public int getCoinCollected(){
		return numDropsCollected;
	}

}
