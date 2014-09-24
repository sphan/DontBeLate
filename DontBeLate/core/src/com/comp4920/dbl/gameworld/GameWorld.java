package com.comp4920.dbl.gameworld;

import com.comp4920.dbl.gameobjects.Bus;

public class GameWorld {
	private Bus bus;
	
	public GameWorld(int midPointX) {
		bus = new Bus(midPointX, 330, 50, 60);
	}
	
	public void update(float delta) {
		bus.update(delta);
	}
	
	public Bus getBus() {
		return bus;
	}
}
