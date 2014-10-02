package com.comp4920.dbl.gameobjects;

public class Road {
	private final static int DEFAULT_SPEED = 700;
	private static float roadSpeed = DEFAULT_SPEED;
	private static boolean stopped = false;
	
	private static float roadTexStart1 = -400;
	private static float roadTexStart2 = 0;
	
	public Road (){}
	
	public Road (int busSpeed){
		roadSpeed = busSpeed;
	}
	
	public void update (float delta){
		if(!stopped){
			//increment based on bus forward speed
			roadTexStart1 += (delta*roadSpeed); //speed is distance/60 seconds
			roadTexStart2 += (delta*roadSpeed);
			
			//wrap around
			roadTexStart1 = ((roadTexStart1+400)%800) - 400;
			roadTexStart2 = ((roadTexStart2+400)%800) - 400;
			
		}
	}
	
	public void setRoadSpeed (float speed){
		roadSpeed = speed;
	}
	
	public static float getRoadSpeed (){
		return roadSpeed;
	}
	
	public void stop (){
		stopped = true;
	}
	
	public float getRoadStart1(){
		return roadTexStart1;
	}
	
	public float getRoadStart2(){
		return roadTexStart2;
	}
}
