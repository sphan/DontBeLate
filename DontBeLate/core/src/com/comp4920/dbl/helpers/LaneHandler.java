package com.comp4920.dbl.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.comp4920.dbl.gameobjects.BusStop;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Road;

public class LaneHandler {
	private BusStop busStop;
	private List<Lane> lanes;
	private static int NO_LANES = 4; //TODO: figure out the number of lanes
	
	//below are values for which cars can spawn
	private int x_min = 45;
	private int x_max = 277;
	
	private boolean busStopped;
	
	public LaneHandler(BusStop busStop) {
		this.busStop = busStop;
		initLanes(NO_LANES);
		busStopped = false;
	}
	
	
	public void update(float delta) {
		for (Lane lane : lanes) {
			lane.update(delta);
		}
	}
	
	
	public int updateObstacles() {
		int numObstacles = 0;
		for (Lane lane : lanes) {
			//System.out.println(lane.getId()+": "+lane.getNumObstacles());	
			lane.checkObstacleBounds();
			
			numObstacles+= lane.getNumObstacles();
		}
		return numObstacles;
	}
	
	
	// Adds a car to the lane with the fewest cars.
	public void addObstacle(float runTime) {
		if (!busStopped) {
			Collections.sort(lanes);
			
			//we avoid a lane with the bus stop is close by
			if (lanes.get(0).canAddObstacle() && !isBusStopLane(lanes.get(0))) {
				lanes.get(0).addObstacle();
			} else {
				lanes.get(1).addObstacle();
			}
		}
	}
	
	public boolean isBusStopLane(Lane lane){

		 if(busStop.getWarningY() < 600){
			 if(lane.getId() == 0 && busStop.isLeftSide()){
				 return true;
			 } else if (lane.getId() == 3 && !busStop.isLeftSide()){
				 return true;
			 }
		 }
		 return false;
	}

	
	// Adds a car to a random lane.
	public void addObstacleRandomLane(float runTime) {
		if (!busStopped) {
			while(true){
				Random rand = new Random();
				int randomNum = rand.nextInt(NO_LANES);
				Lane randomLane = lanes.get(randomNum);
				if(randomLane.canAddObstacle() && !isBusStopLane(randomLane)){
					randomLane.addObstacle();
					//randomLane.addRW();
					break;
				}
			}
		}
	}


	// This is used when the bus 'stops'.
	// The speed of the cars must reverse.
	public void roadStopped() {
		if (!busStopped) {	//sanity check
			for (Lane lane : lanes) {
				List<Car> cars = lane.getCars();
				for (Car car : cars) {
				//	car.setSpeed(-car.getVerticalSpeed());
				}
			}
			busStopped = true;
		}
		getRightMostLane().stop();
	}
	
	//TODO: some of these parameters need to be adjusted
	// Use when the road starts moving again.
	public void resume() {
		if (busStopped) {	// sanity check
			for (Lane lane : lanes) {
				List<Car> cars = lane.getCars();
				for (Car car : cars) {
				//	car.setSpeed(-car.getVerticalSpeed());
				}
			}
			busStopped = false;
		}
		getRightMostLane().resume();
	}
	
	public void stop() {
		for (Lane lane : lanes) {
			List<Obstacle> obstacles = lane.getObstacles();
			for (Obstacle obstacle : obstacles) {
				obstacle.stop();
			}
		}
	}
	
	public void start() {
		for (Lane lane : lanes) {
			List<Obstacle> obstacles = lane.getObstacles();
			for (Obstacle obstacle : obstacles) {
				obstacle.start();
			}
		}
	}
	
	
	private void initLanes(int numLanes) {
		lanes = new ArrayList<Lane>();
		//define lane positions
		int laneSize = (x_max - x_min) / NO_LANES;
		
		for (int n = 0; n < NO_LANES; n++){
			lanes.add(new Lane((laneSize * (n)) + x_min, n)); //int positionX;
		}

	}
	
	// returns a list with the lanes ordered from left to right on the screen
	private Lane getRightMostLane() {
		Lane rightLane = lanes.get(0);
		for (Lane lane : lanes) {
			if (lane.getXPosition() > rightLane.getXPosition()) {
				rightLane = lane;
			}
		}
		return rightLane;
	}
	
	public List<Lane> getLanes() {
		return lanes;
	}


}
