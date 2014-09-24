package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class Bus {
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	
	private float rotation;  // For handling bus rotation
	private int width;
	private int height;
	
	public Bus(float x, float y, int width, int height) {
		this.width = width;
		this.height = height;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 160);
	}
	
	public void update(float delta) {
		velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y < 200) {
            velocity.y = 0;
        }

        position.add(velocity.cpy().scl(delta));
        
        if (velocity.x < 0) {
        	rotation -= 600 * delta;
        	
        	if (rotation < -20) {
        		rotation = -20;
        	}
        }
        
        if (velocity.x > 0) {
        	rotation += 480 * delta;
        	if (rotation > 20) {
        		rotation = 20;
        	}
        }
	}
	
	public void onClick() {
        velocity.y = -140;
    }
	
	public void onKeyDown(int keycode) {
		Gdx.app.log("BusOnKeyDown", keycode + " is pressed");
		if (keycode == Keys.LEFT) {
			velocity.x -= 200 * Gdx.graphics.getDeltaTime();
		}
		if (keycode == Keys.RIGHT) {
			velocity.x += 200 * Gdx.graphics.getDeltaTime();
		}
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

    public float getRotation() {
        return rotation;
    }
}
