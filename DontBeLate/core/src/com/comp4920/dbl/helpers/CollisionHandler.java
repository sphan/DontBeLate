package com.comp4920.dbl.helpers;

import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;

public class CollisionHandler {

	private Bus bus;
	private List<Car> cars;
	
	public void Collisions() {
	
	}
	
	// TODO: properly abstract this
	public boolean collision(Bus bus, Car car) {
		return Intersector.overlaps(bus.getHitBox(), car.getHitBox());
	}
	
}
