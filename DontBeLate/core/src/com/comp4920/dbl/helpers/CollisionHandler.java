package com.comp4920.dbl.helpers;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Drop;
import com.comp4920.dbl.gameobjects.Obstacle;

public class CollisionHandler {

	private static final boolean collisionsOn = true;
	
	public void Collisions() {
	
	}
	
	public boolean check(Bus bus, List<Obstacle> obstacles) {
		for (Obstacle obstacle : obstacles) {
			if (collision(bus, obstacle)) {
				return true;
			}
		}
		return false;
	}
	
	public Drop checkDrops(Bus bus, List<Drop> drops) {
		for (Drop drop : drops) {
			if (collision(bus, drop)) {
				return drop;
			}
		}
		return null;
	}
	
	
	//the Intersector call is expensive and we want to limit how often it's used.
	public boolean collision(Bus bus, Obstacle obstacle) {
		if (collisionsOn && canCollide(bus,obstacle)) {
			return Intersector.overlaps(bus.getHitBox(), obstacle.getHitBox());
		}
		return false;
	}
	
	//the Intersector call is expensive and we want to limit how often it's used.
	public boolean collision(Bus bus, Drop drop) {
		if (collisionsOn) {
			return Intersector.overlaps(bus.getHitBox(), drop.getHitBox());
		}
		return false;
	}
	
	
	// The Intersector call is expensive so I wrote this to reduce the number of calls we make.
	// Not sure if it's faster though.
	// bus and car can collide if: 
	//	bottomY(car) <= topY(bus) && (rightX(car) >= leftX(bus) || leftX(car) <= rightX(bus))
	private boolean canCollide(Bus bus, Obstacle obstacle) {
		
		float carX = obstacle.getX();
		float carY = obstacle.getY();
		float busY = bus.getY();
		float busX = bus.getX();
		
		
		float carWidth = obstacle.getWidth();
		float carHeight = obstacle.getHeight();
		float busHeight = bus.getHeight();
		float busWidth = bus.getWidth();
		
		float bottomYCar = carY + carHeight/2;
		float rightXCar = carX + carWidth/2;
		float leftXCar = carX - carWidth/2;
		
		float topYBus = busY - busHeight/2;
		float leftXBus = busX - busWidth/2;
		float rightXBus = busX + busWidth/2;
		
		
		return ((bottomYCar >= topYBus) && (rightXCar >= leftXBus || leftXCar <= rightXBus));

	}

	
}
