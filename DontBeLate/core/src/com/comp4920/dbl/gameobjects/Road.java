package com.comp4920.dbl.gameobjects;

public class Road {
	public final static int DEFAULT_SPEED = 500; //equates with ???km/hour approx (nb 700 = 70km/h)
	private static float roadSpeed = DEFAULT_SPEED;
	private static boolean stopped = false;
	
	private static float roadTexStart1 = 400;
	private static float roadTexStart2 = 0;
	private static float distanceTravelled; //can be used to calculate checkpoints
	public static final int CONVERT_METERS = 37; //divide by approx 37 to get in meters
	                                              //assumes speed of 700 == 70kmh
	public Road (){}
	
	/*
	public Road (int busSpeed){
		roadSpeed = busSpeed;
		distanceTravelled = 0;
	}
	*/
	public void update (float delta){
		if(!stopped){
			//increment based on bus forward speed
			roadTexStart1 -= (delta*roadSpeed); //speed is distance/60 seconds
			roadTexStart2 -= (delta*roadSpeed);
			distanceTravelled += (delta*roadSpeed);
			//wrap around
			roadTexStart1 = ((roadTexStart1-400)%800) + 400;
			roadTexStart2 = ((roadTexStart2-400)%800) + 400;
			
		}
	}
	
	public void setRoadSpeed (float speed){
		roadSpeed = speed;
	}
	
	public static float getRoadSpeed (){
		if(stopped) return 0;
		return roadSpeed;
	}
	
	public void stop (){
		stopped = true;
	}
	
	public void start() {
		stopped = false;
	}
	
	public float getRoadStart1(){
		return roadTexStart1;
	}
	
	public float getRoadStart2(){
		return roadTexStart2;
	}
	
	public static void resetDistanceTravelled(){
		distanceTravelled = 0;
	}
	
	public float getDistanceTravelled(){
		return distanceTravelled;
	}
	
	public int getDistanceTravelledMtrs(){
		return (int) distanceTravelled/CONVERT_METERS;
	}
	
}
