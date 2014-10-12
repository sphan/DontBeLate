package com.comp4920.dbl.helpers;

import java.util.List;
import java.util.Random;

import com.comp4920.dbl.gameobjects.Car.CarColour;
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
	
	
	public static Obstacle newObstacle(int positionX,int minSpeed) {
		/*
		if (randInt(0,20) < 1) {
			return new Roadwork(positionX, maxSpeed);
		}
		*/
		return newCar(positionX, minSpeed);
	}

	public static Car newCar (int positionX,int minSpeed) {
		//choose the type of car (colour etc.)
		
		int rand = randInt(0,7);
		if (rand == 1){
			return new Car(positionX, minSpeed, CarColour.REAL1);
		} else if (rand == 2){
			return new Car(positionX, minSpeed, CarColour.REAL2);
		} else if (rand == 3){
			return new Car(positionX, minSpeed, CarColour.REAL3);
		} else if (rand == 4){
			return new Car(positionX, minSpeed, CarColour.REAL4);
		} else if (rand == 5){
			return new Car(positionX, minSpeed, CarColour.REAL5);
		} else if (rand == 6){
			return new Car(positionX, minSpeed, CarColour.REAL6);
		} else if (rand == 7){
			return new Car(positionX, minSpeed, CarColour.REAL7);
		}
		
		return new Car(positionX, minSpeed, CarColour.RED);
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
