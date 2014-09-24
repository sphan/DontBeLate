package com.comp4920.dbl.gameworld;

import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.helpers.InputHandler;

public class GameWorld {
	private Bus bus;
	
	public GameWorld(int midPointX) {
		bus = new Bus(midPointX, 330, 50, 60);
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		bus.update(delta, busInputHandler);
	}
	
	public Bus getBus() {
		return bus;
	}
}
