package com.comp4920.dbl.gameworld;

import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.helpers.InputHandler;

public class GameWorld {
	private Bus bus;
	private Car car; //TODO: List of cars
	
	public GameWorld(int midPointX) {
		bus = new Bus(midPointX, 330, 50, 60);
		car = new Car(10, 10, 40, 40);
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		bus.update(delta, busInputHandler);
		car.update(delta);
	}
	
	public Bus getBus() {
		return bus;
	}

	public Car getCar() {
		return car;
	}
}
