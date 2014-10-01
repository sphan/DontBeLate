package com.comp4920.dbl.helpers;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;

public class CollisionHandler {

	private Bus bus;
	private List<Car> cars;
	private static final boolean collisionsOn = true;
	
	public void Collisions() {
	
	}
	
	public boolean check(Bus bus, List<Car> cars) {
		for (Car car : cars) {
			if (collision(bus, car)) {
				return true;
			}
		}
		return false;
	}
	
	
	//the Intersector call is expensive and we want to limit how often it's used.
	public boolean collision(Bus bus, Car car) {
		if (collisionsOn && canCollide(bus,car)) {
			return Intersector.overlaps(bus.getHitBox(), car.getHitBox());
		}
		return false;
	}
	
	// The Intersector call is expensive so I wrote this to reduce the number of calls we make.
	// Not sure if it's faster though.
	// bus and car can collide if: 
	//	bottomY(car) <= topY(bus) && (rightX(car) >= leftX(bus) || leftX(car) <= rightX(bus))
	private boolean canCollide(Bus bus, Car car) {
		
		float carX = car.getX();
		float busY = bus.getY();
		float busX = bus.getX();
		
		float carWidth = car.getWidth();
		float busHeight = bus.getHeight();
		float busWidth = bus.getWidth();
		
		float bottomYCar = carX + carWidth/2;
		float rightXCar = carX + carWidth/2;
		float leftXCar = carX - carWidth/2;
		
		float topYBus = busY - busHeight/2;
		float leftXBus = busX - busWidth/2;
		float rightXBus = busX + busWidth/2;
		
		
		return ((bottomYCar <= topYBus) && (rightXCar>=leftXBus || leftXCar <= rightXBus));
	}
	
}
