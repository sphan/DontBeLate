package com.comp4920.dbl.helpers;

import java.util.List;
import java.util.Random;

import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Roadwork;

public class ObstacleHandler {

	private static final int MAX_POINTS = 5;
	
	private static final int CAR_POINTS = 1;
	private static final int ROADWORK_POINTS = 3;
	
	private static int totalPoints;
	private static int maxPoints;
	
	/*
	 * Obstacle addition logic:
	 * points = #lanes * points per lane
	 * car = 1 point
	 * roadwork = 2 points
	 * etc
	 */
	
	public ObstacleHandler() {
		totalPoints = 0;
		maxPoints = MAX_POINTS;
	}
	
	
	public static Obstacle newObstacle(int positionX,int maxSpeed, int laneID) {
		if (randInt(0,10) < 3) {
			return new Roadwork(positionX, maxSpeed);
		}
		return new Car(positionX, maxSpeed, laneID);
	}

	
	public boolean canAdd(Lane lane) {
		if (totalPoints < maxPoints) {
			return true;
		}
		return false;
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

		
		
}
