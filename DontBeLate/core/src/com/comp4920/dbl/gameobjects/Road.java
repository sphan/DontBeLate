package com.comp4920.dbl.gameobjects;

public class Road {
	private final static int DEFAULT_SPEED = 10;
	private static int roadSpeed = DEFAULT_SPEED;
	
	public Road (){}
	
	public Road (int busSpeed){
		roadSpeed = busSpeed;
	}
	
	public void setRoadSpeed (int speed){
		roadSpeed = speed;
	}
	
	public static int getRoadSpeed (){
		return roadSpeed;
	}
}
