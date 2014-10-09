package com.comp4920.dbl.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.comp4920.dbl.gameobjects.Drop;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.TimeDrop;

public class DropsHandler {

	private List<Drop> drops;
	private static final int MAX_POINTS = 5;
		
	private static int totalPoints;
	private static int maxPoints;
	
	/*
	 * Obstacle addition logic:
	 * points = #lanes * points per lane
	 * car = 1 point
	 * roadwork = 2 points
	 * etc
	 */
	
	public void update(float delta) {
		//update every drop's position
		for (Drop drop : drops) {
			drop.update(delta);
		}		
	}
	
	public DropsHandler() {
		drops = new ArrayList<Drop>();
		totalPoints = 0;
		maxPoints = MAX_POINTS;
	}
	
	public List<Drop> getDrops(){
		return drops;
	}
	
	public Drop newDrop(float runTime) {
		//int positionX,int maxSpeed
		return newTimeDrop();
	}
	
	public void add(float runTime) {
		// TODO Auto-generated method stub
		
	}
	
    //Replace with type of drop
	public TimeDrop newTimeDrop () {
		return null;
		
	}
	
	
	/**
	 * function checks drops are in the bounds of the game screen, removed them if not. 
	 * @return
	 */
	public int updateDrops() {
		checkDropsBounds();
		
		return drops.size();
	}
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void checkDropsBounds (){
		for (Iterator<Drop> iter = drops.iterator(); iter.hasNext(); ){
			Drop drop = iter.next();
			if (drop.offScreen()) {
				iter.remove();
			}
		}			
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
