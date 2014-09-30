package com.comp4920.dbl.helpers;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;

public class CollisionHandler {

	private Bus bus;
	private List<Car> cars;
	private static final boolean collisionsOn = false;
	
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
	
	
	// TODO: properly abstract this
	// When lanes are added, add an initial check for objects in the same lane
	//	as the Intersector call is expensive and we want to limit how often it's used.
	public boolean collision(Bus bus, Car car) {
		if (collisionsOn) {
			return Intersector.overlaps(bus.getHitBox(), car.getHitBox());
		}
		return false;
	}
	
}
