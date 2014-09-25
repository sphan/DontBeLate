package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.InputHandler;

public class Car {
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	private int width;
	private int height;
	
	public Car(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
	}
	
	public void update(float delta) {
		position.y += delta*100;       
    }
	
		
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


}
