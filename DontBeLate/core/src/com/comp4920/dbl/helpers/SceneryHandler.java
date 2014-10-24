package com.comp4920.dbl.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.comp4920.dbl.gameobjects.Drop;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Scenery;
import com.comp4920.dbl.gameobjects.CoinDrop;
import com.comp4920.dbl.gameobjects.TreeScenery;

/**
 * Handles all scenery
 * @author Felix
 *
 */
public class SceneryHandler {
	private List<Scenery> sceneries;

	//
	public int MIN_DISTANCE_BETWEEN = 100;
	public int MAX_SCENERY = 4;
	
	public Scenery lastSceneryLeft;
	public int numSceneryLeft;
	
	public Scenery lastSceneryRight;
	public int numSceneryRight;
	
	public int treeLeftX = -16;
	public int treeRightX = 275;
	
	public boolean veryRandomSpawnTree = true;
	

	public SceneryHandler() {
		sceneries = new ArrayList<Scenery>();
	}
	
	public List<Scenery> getDrops(){
		return sceneries;
	}
	
	public void update(float delta) {
		//update every drop's position
		for (Scenery scenery : sceneries) {
			scenery.update(delta);
		}		
	}
	
	public void newScenery(float runTime) {
		Random rand = new Random();
		//choose to spawn now or not 
		int randomNum = rand.nextInt(60);
		
		if(veryRandomSpawnTree && randomNum > 2) {
			return;
		}
		//randomly choose left or right path
		boolean isLeftSide = false;
		
		randomNum = rand.nextInt(2);
		if(randomNum < 1){
			isLeftSide = true;
		}
		
		//ask whether or not we should have another
		if(canAdd(isLeftSide)) {
			addTree(runTime, isLeftSide);
		}
	}
	
	public void addTree(float runTime, boolean leftSide) {
		int y = Scenery.SCENERY_Y_START;
		int x = treeRightX;
		
		if (leftSide) {
			x = treeLeftX;
			TreeScenery newTree = new TreeScenery(x,y); 
			lastSceneryLeft = newTree;
			numSceneryLeft++;
			sceneries.add(newTree);
		} else {
			x = treeRightX;
			TreeScenery newTree = new TreeScenery(x,y); 
			lastSceneryRight = newTree;
			numSceneryRight++;
			sceneries.add(newTree);
		}		
	}
	
    //Replace with type of drop
	public CoinDrop newTimeDrop () {
		
		return new CoinDrop();
	}
	
	
	/**
	 * function checks drops are in the bounds of the game screen, removed them if not. 
	 * @return
	 */
	public int updateScenery() {
		checkSceneryBounds();
		
		return sceneries.size();
	}
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void checkSceneryBounds (){
		for (Iterator<Scenery> iter = sceneries.iterator(); iter.hasNext(); ){
			Scenery scenery = iter.next();
			if (scenery.offScreen()) {
				iter.remove();
			}
		}			
	}
	
	public boolean canAdd(boolean leftSide) {
		boolean isAdequateDistance = true;
		boolean canAccomodateAnother = true;
		if(leftSide){
			if(lastSceneryLeft != null){
				isAdequateDistance = false;
				canAccomodateAnother = false;
			
				if(lastSceneryLeft.getY() < (Scenery.SCENERY_Y_START - MIN_DISTANCE_BETWEEN)){
					isAdequateDistance = true;
				}
				
				if (sceneries.size() <= MAX_SCENERY){
					canAccomodateAnother = true;
				}
			}
	
		} else {
			if(lastSceneryRight != null){
				isAdequateDistance = false;
				canAccomodateAnother = false;
				if(lastSceneryRight.getY() < (Scenery.SCENERY_Y_START - MIN_DISTANCE_BETWEEN)){
					isAdequateDistance = true;
				}
				
				if (sceneries.size() <= MAX_SCENERY){
					canAccomodateAnother = true;
				}
			}
		}

		return (canAccomodateAnother && isAdequateDistance);
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

	public void removeScenery(Scenery collisionScenery) {
		sceneries.remove(collisionScenery);
	}

	public List<Scenery> getScenery (){
		return sceneries;
	}


}
