package com.comp4920.dbl.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;

public class LaneHandler {

	private List<Lane> lanes;
	private static int NO_LANES = 5; //TODO: figure out the number of lanes
	
	//below are values for which cars can spawn
	private int x_min = (int) (Car.WIDTH/2);
	private int x_max = Gdx.graphics.getWidth()/2 - Car.WIDTH/2;
	private int x_shift_right = 3; //for small adjustments

	
	public LaneHandler() {
		initLanes(NO_LANES);
	}
	
	
	public void update(float delta) {
		for (Lane lane : lanes) {
			lane.update(delta);
		}
	}
	
	
	public int updateCars() {
		int numCars = 0;
		for (Lane lane : lanes) {
			int prevNumCars = lane.getNumCars();		
			lane.checkCarBounds();
			int afterNumCars = lane.getNumCars();
			numCars-= (prevNumCars - afterNumCars);
		}
		return numCars;
	}
	
	
	// Adds a car to the lane with the fewest cars.
	public void addCar(float runTime) {
		Collections.sort(lanes);
		if (lanes.get(0).canAddCar()) {
			lanes.get(0).addCar();
		}
	}

	
	// Adds a car to a random lane.
	public void addCarRandomLane(float runTime) {
		while(true){
			Random rand = new Random();
			int randomNum = rand.nextInt(NO_LANES);
			Lane randomLane = lanes.get(randomNum);
			if(randomLane.canAddCar()){
				randomLane.addCar();
				break;
			}
		}
	}

	
	public void stop() {
		for (Lane lane : lanes) {
			List<Car> cars = lane.getCars();
			for (Car car : cars) {
				car.stop();
			}
		}
	}
	
	
	private void initLanes(int numLanes) {
		lanes = new ArrayList<Lane>();
		//define lane positions
		int laneSize = (x_max - x_min) / NO_LANES;
		
		for (int n = 0; n < NO_LANES; n++){
			lanes.add(new Lane((laneSize * (n)) + x_min + x_shift_right)); //int positionX;
		}

	}
	
	
	public List<Lane> getLanes() {
		return lanes;
	}

	
}
