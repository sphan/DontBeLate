package com.comp4920.dbl.gameobjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.comp4920.dbl.helpers.ObstacleHandler;

public class Lane implements Comparable<Lane>{
	private int id;
	public static final int LANE_MAX_NUM_OBSTACLES = 2;
	private int positionX; //for determining x position of car
	private int minSpeed; //max speed so far
	private int maxNumObstacles;
	private boolean stopped;

	private List<Obstacle> obstacles;
	
	public Lane (int positionX, int id){
		this.id = id;
		this.positionX = positionX;
		obstacles = new ArrayList<Obstacle>();
		this.minSpeed = Car.MIN_CAR_SPEED;// FIRST CAR'S MAX SPEED;
		this.maxNumObstacles = LANE_MAX_NUM_OBSTACLES;
		this.stopped = false;
	}
	
	public boolean canAddObstacle (){
		return (obstacles.size() <= maxNumObstacles && !roadworkOnScreen() && !stopped);
	}
	
	// Returns true if there is roadwork on the screen
	private boolean roadworkOnScreen() {
		for (Obstacle obstacle :obstacles) {
			if (obstacle instanceof Roadwork) {
				return true;
			}
		}
		return false;
	}
	
	public void addObstacle (){
		//check max speed and set car speed to that
		Obstacle newObstacle = ObstacleHandler.newObstacle(positionX,minSpeed);
		if(newObstacle.getVerticalSpeed() > minSpeed){
			minSpeed = (int) newObstacle.getVerticalSpeed(); //TODO: Issue with speed being float or int
		}
		obstacles.add(newObstacle);
		if (newObstacle instanceof Roadwork) {
			obstacles.add(((Roadwork) newObstacle).getWarning());
		}
	}
	
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void checkObstacleBounds (){
		for (Iterator<Obstacle> iter = obstacles.iterator(); iter.hasNext(); ){
			Obstacle obstacle = iter.next();
			if (obstacle.offScreen()) {
				iter.remove();
			}
		}
		//we need to check if there are no cars on the lane, if so set maxSpeed to normal max
		if(getNumObstacles() == 0){
			minSpeed = Car.MIN_CAR_SPEED;
		}
				
	}
	/**
	 * Update all the car positions
	 * @param delta
	 */
	
	public void update(float delta) {
		
		//update each obstacle
		for (Obstacle obstacle : obstacles){
			obstacle.update(delta);
		}
	}
	
	public List<Obstacle> getObstacles() {
		return obstacles;
	}
	
	public int getNumObstacles(){
		return obstacles.size();
	}
	
	public int getMaxSpeed(){
		return minSpeed;
	}
	
	public int getXPosition(){
		return positionX;
	}

	public int getMaxObstacles() {
		return maxNumObstacles;
	}
	
	public List<Car> getCars() {
		List<Car> cars = new ArrayList<Car>();;
		for (Obstacle obstacle : obstacles) {
			if (obstacle instanceof Car) {
				cars.add((Car) obstacle);
			}
		}
		return cars;
	}
	
	public void stop() {
		stopped = true;
	}
	
	public void resume() {
		stopped = false;
	}
	
	@Override
	public int compareTo(Lane otherLane) {
		return (obstacles.size() - otherLane.getNumObstacles());
	}
	
	public int getId(){
		return id;
	}
}
