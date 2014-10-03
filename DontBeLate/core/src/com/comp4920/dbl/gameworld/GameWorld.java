package com.comp4920.dbl.gameworld;

import java.util.List;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.CollisionHandler;
import com.comp4920.dbl.helpers.InputHandler;
import com.comp4920.dbl.helpers.LaneHandler;

public class GameWorld {
	private Road road; //reference used to edit road/bus speed only
	private Bus bus;	
	private LaneHandler lanes;
	
	private int numCars; //number of cars currently on the road
	
	private static int maxNumCars = 5;	// max number of cars onscreen at any time
	private static final int carDelay = 1; 	// delay between a car going offscreen and a new car spawning
	private static float lastCarTime;
	private boolean stopped;
	
	private CollisionHandler collisions;
	
	public GameWorld(int midPointX) {
		stopped = false;
		lastCarTime = 0;
		bus = new Bus(midPointX, Bus.BUS_START_Y, Bus.BUS_WIDTH, Bus.BUS_HEIGHT);
		lanes = new LaneHandler();
		road = new Road();
		
		collisions = new CollisionHandler();
		
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		if(stopped){
			return;
		}
	
		road.setRoadSpeed(bus.getForwardVelocity());
		road.update(delta);
		bus.update(delta, busInputHandler);
		lanes.update(delta);	
	}
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void updateCars (float runTime){
		if(stopped) {
			return;
		}

		numCars = lanes.updateCars();
		System.out.println(runTime);
		if (newCarTime(runTime)) {
			if ((runTime%10) < 5) { //Change proportion every 10 seconds it is random
				lanes.addCarRandomLane(runTime);
				lastCarTime = runTime;
				numCars++;
			} else {
				lanes.addCar(runTime);
				lastCarTime = runTime;
				numCars++;
			}
		}
		
	}
	
	public boolean checkCollisions (){
		for (Lane lane : lanes.getLanes()) {
			List<Obstacle> cars = lane.getCars();
			if (collisions.check(bus, cars)) {
				return true;
			}
		}
		return false;
	}
	
	// Returns true if we should generate another car, false otherwise.
	// Spawn a new car if there are fewer than numCars on screen AND
	//	at least carDelay seconds have elapsed since the last car was spawned.
	private boolean newCarTime(float runTime) {
		return (numCars < maxNumCars && runTime > lastCarTime + carDelay);
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
	
	public void stop(){
		stopped = true;
		bus.stop();
		road.stop();
		lanes.stop();
	}

}
