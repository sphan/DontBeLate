package com.comp4920.dbl.helpers;

import java.util.List;
import java.util.Random;

import com.comp4920.dbl.gameobjects.Bike;
import com.comp4920.dbl.gameobjects.Car.CarColour;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Roadwork;
import com.comp4920.dbl.gameobjects.Truck;

public class ObstacleHandler {

	public ObstacleHandler() {}
	
	
	public static Obstacle newObstacle(int positionX,int minSpeed) {
		
		if (randInt(0,20) < 1 && minSpeed <= Truck.MIN_TRUCK_SPEED) {
			return new Truck(positionX, minSpeed);
		}
		
		if (randInt(0,40) > 1 && minSpeed < Car.MAX_CAR_SPEED) {
			return newCar(positionX, minSpeed);
		}
		
		return new Bike(positionX, minSpeed);
	}

	public static Car newCar (int positionX,int minSpeed) {
		//choose the type of car (colour etc.)
		
		int rand = randInt(0,9);
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
		} else if (rand == 8){
			return new Car(positionX, minSpeed, CarColour.REAL8);
		} else if (rand == 9){
			return new Car(positionX, minSpeed, CarColour.REAL9);
		}
		
		return new Car(positionX, minSpeed, CarColour.RED);
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
