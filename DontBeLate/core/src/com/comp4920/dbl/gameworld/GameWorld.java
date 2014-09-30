package com.comp4920.dbl.gameworld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.InputHandler;

public class GameWorld {
	private Road road; //reference used to edit road/bus speed only
	private Bus bus;	
	
	private List<Lane> lanes;
	
	private int numCars; 
	
	private static int NO_LANES = 5; //TODO: figure out the number of lanes
	private static int maxNumCars = 7;	// max number of cars onscreen at any time
	private static final int carDelay = 1; 	// delay between a car going offscreen and a new car spawning
	private static float lastCarTime;
	
	//below are values for which cars can spawn
	private int x_min = (int) (Car.WIDTH/2);
	private int x_max = Gdx.graphics.getWidth()/2 - Car.WIDTH/2;
	private int x_shift_right = 3; //for small adjustments
	
	public GameWorld(int midPointX) {
		lastCarTime = 0;
		bus = new Bus(midPointX, 330, 28, 70);
		lanes = new ArrayList<Lane>();
		road = new Road();
		
		//define lane positions
		int laneSize = (x_max - x_min) / NO_LANES;
		
		for (int n = 0; n < NO_LANES; n++){
			lanes.add(new Lane((laneSize * (n)) + x_min + x_shift_right)); //int positionX;
		}
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		road.update();
		bus.update(delta, busInputHandler);
		for (Lane lane : lanes) {
			lane.update(delta);
		}
	
	}
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void updateCars (float runTime){
		for (Lane lane : lanes) {
			int prevNumCars = lane.getNumCars();		
			lane.checkCarBounds();
			int afterNumCars = lane.getNumCars();
			numCars-= (prevNumCars - afterNumCars);
			
			//can we add another car?
			if (newCarTime(runTime)) {
				while(true){
					Random rand = new Random();
					int randomNum = rand.nextInt(NO_LANES);
					Lane randomLane = lanes.get(randomNum);
					if(randomLane.canAddCar()){
						numCars++;
	
						randomLane.addCar();
						
						lastCarTime = runTime;
						break;
					}
				}
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
	
	public List<Lane> getLanes() {
		return lanes;
	}
	
	public Road getRoad(){
		return road;
	}
	
	public void stop(){
		road.stop();
		for (Lane lane : lanes) {
			List<Car> cars = lane.getCars();
			for (Car car : cars) {
				car.stop();
			}
		}
	}

}
