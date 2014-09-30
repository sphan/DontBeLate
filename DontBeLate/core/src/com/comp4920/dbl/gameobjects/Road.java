package com.comp4920.dbl.gameobjects;

public class Road {
	private final static int DEFAULT_SPEED = 10;
	private static int roadSpeed = DEFAULT_SPEED;
	private static boolean stopped = false;
	
	private static int roadTexStart1 = -400;
	private static int roadTexStart2 = 0;
	
	public Road (){}
	
	public Road (int busSpeed){
		roadSpeed = busSpeed;
	}
	
	public void update (){
		if(!stopped){
			//increment based on bus forward speed
			roadTexStart1 += Road.getRoadSpeed(); //speed is distance/60 seconds
			roadTexStart2 += Road.getRoadSpeed();
			//wrap around
			roadTexStart1 = ((roadTexStart1+400)%800) - 400;
			roadTexStart2 = ((roadTexStart2+400)%800) - 400;
		}
	}
	
	public void setRoadSpeed (int speed){
		roadSpeed = speed;
	}
	
	public static int getRoadSpeed (){
		return roadSpeed;
	}
	
	public void stop (){
		stopped = true;
	}
	
	public int getRoadStart1(){
		return roadTexStart1;
	}
	
	public int getRoadStart2(){
		return roadTexStart2;
	}
}
