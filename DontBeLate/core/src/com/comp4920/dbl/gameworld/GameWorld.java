package com.comp4920.dbl.gameworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Road;
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
	
	
	public GameWorld(int midPointX) {
		stopped = false;
		lastCarTime = 0;
		bus = new Bus(midPointX, 280, Bus.BUS_WIDTH, Bus.BUS_HEIGHT);
		lanes = new LaneHandler();
		road = new Road();
		
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		if(stopped){
			return;
		}
	
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
		
		if (newCarTime(runTime)) {
			if (runTime < 5) {
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
		road.stop();
		lanes.stop();
	}

}
