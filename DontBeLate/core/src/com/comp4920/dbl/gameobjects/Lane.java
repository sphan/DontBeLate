package com.comp4920.dbl.gameobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lane implements Comparable<Lane>{
	public static final int LANE_MAX_NUM_CARS = 2;
	private int positionX; //for determining x position of car
	private int maxSpeed; //max speed so far
	private int maxNumCars;
	
	private List<Car> cars;
	
	public Lane (int positionX){
		this.positionX = positionX;
		cars = new ArrayList<Car>();
		this.maxSpeed = Car.MAX_CAR_SPEED;// FIRST CAR'S MAX SPEED;
		this.maxNumCars = LANE_MAX_NUM_CARS;
	}
	
	public boolean canAddCar (){
		return (cars.size() <= maxNumCars);
	}
	
	public void addCar (){
		//check max speed and set car speed to that
		Car newCar = new Car(positionX,maxSpeed);
		if(newCar.getVerticalSpeed() < maxSpeed){
			maxSpeed = (int) newCar.getVerticalSpeed(); //TODO: Issue with speed being float or int
		}
		cars.add(newCar);
	}
	
	public void addCar (Car car){
		if(car.getVerticalSpeed() < maxSpeed){
			maxSpeed = (int) car.getVerticalSpeed(); //TODO: Issue with speed being float or int
		}
		cars.add(car);
	}
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void checkCarBounds (){
		for (Iterator<Car> iter = cars.iterator(); iter.hasNext(); ){
			Car car = iter.next();
			if (car.offScreen()) {
				iter.remove();
			}
		}
		//we need to check if there are no cars on the lane, if so set maxSpeed to normal max
		if(getNumCars() == 0){
			maxSpeed = Car.MAX_CAR_SPEED;
		}
				
	}
	/**
	 * Update all the car positions
	 * @param delta
	 */
	
	public void update(float delta) {
		
		//update each car
		for (Car car : cars){
			car.update(delta);
		}
	}
	
	public List<Car> getCars() {
		return cars;
	}
	
	public int getNumCars(){
		return cars.size();
	}
	
	public int getMaxSpeed(){
		return maxSpeed;
	}
	
	public int getXPosition(){
		return positionX;
	}

	public int getMaxCars() {
		return maxNumCars;
	}
	
	@Override
	public int compareTo(Lane otherLane) {
		return (cars.size() - otherLane.getNumCars());
	}
}
