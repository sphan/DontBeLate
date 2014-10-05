package com.comp4920.dbl.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;

public class LaneHandler {

	private List<Lane> lanes;
	private static int NO_LANES = 5; //TODO: figure out the number of lanes
	
	//below are values for which cars can spawn
	private int x_min = (int) (Car.WIDTH/2);
	private int x_max = Gdx.graphics.getWidth()/2 - Car.WIDTH/2;
	private int x_shift_right = 3; //for small adjustments

	private static int numMerging;
	private static int mergeCap = 1;
	
	public LaneHandler() {
		initLanes(NO_LANES);
		numMerging = 0;
	}
	
	
	public void update(float delta) {
		for (Lane lane : lanes) {
			lane.update(delta);
		}
	}
	
	
	public int updateObstacles() {
		int numObstacles = 0;
		for (Lane lane : lanes) {
			int prevNumObstacles = lane.getNumObstacles();		
			lane.checkObstacleBounds();
			int afterNumObstacles = lane.getNumObstacles();
			numObstacles-= (prevNumObstacles - afterNumObstacles);
		}
		return numObstacles;
	}
	
	
	// Adds a car to the lane with the fewest cars.
	public void addObstacle(float runTime) {
		Collections.sort(lanes);
		if (lanes.get(0).canAddObstacle()) {
			lanes.get(0).addObstacle();
		}
	}

	
	// Adds a car to a random lane.
	public void addObstacleRandomLane(float runTime) {
		while(true){
			Random rand = new Random();
			int randomNum = rand.nextInt(NO_LANES);
			Lane randomLane = lanes.get(randomNum);
			if(randomLane.canAddObstacle()){
				randomLane.addObstacle();
				//randomLane.addRW();
				break;
			}
		}
	}

	
	public void stop() {
		for (Lane lane : lanes) {
			List<Obstacle> obstacles = lane.getObstacles();
			for (Obstacle obstacle : obstacles) {
				obstacle.stop();
			}
		}
	}
	
	
	private void initLanes(int numLanes) {
		lanes = new ArrayList<Lane>();
		//define lane positions
		int laneSize = (x_max - x_min) / NO_LANES;
		
		for (int n = 0; n < NO_LANES; n++){
			lanes.add(new Lane((laneSize * (n)) + x_min + x_shift_right,n)); //int positionX;
		}

	}
	
	public static boolean canMerge() {
		return numMerging < mergeCap;
	}
	
	public static void startMerge() {
		numMerging++;
	}
	
	public static void stopMerging() {
		numMerging--;
	}
	
	public List<Lane> getLanes() {
		return lanes;
	}

	public int getLaneWidth() {
		return (x_max - x_min) / NO_LANES;
	}
}
