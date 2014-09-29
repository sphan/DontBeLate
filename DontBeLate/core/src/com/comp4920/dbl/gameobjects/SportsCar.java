package com.comp4920.dbl.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class SportsCar extends Car {

	private static final int maxSpeed = 800;
	private static final int minSpeed = 500;

	public SportsCar() {
		super();
		int x = getStartX();
		int y = 330;
		this.position = new Vector2(x, y);
	}
	
	public void update(float delta) {
		System.out.println(position.y);
		position.y -= delta*velocity.y;
		boundingRectangle.set(position.x, position.y, CAR_WIDTH, CAR_HEIGHT);	//TODO: check these numbers
    }
	
	@Override
	public int genStartSpeed() {
		int speed = defaultSpeed;
		if (randomStartSpeed) {
			Random rand = new Random();
			speed = rand.nextInt((maxSpeed - minSpeed) + 1) + minSpeed;
		}
		return speed;
	}


	
}
