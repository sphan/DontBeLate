package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.InputHandler;

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
        acceleration = new Vector2(0, 100);
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		velocity.add(acceleration.cpy().scl(delta));

        if (velocity.y < 200) {
            velocity.y = 0;
        }
        
        
        if (busInputHandler.leftKeyPressed) {
        	moveLeft();
        	rotateLeft(delta);
        }

        if (busInputHandler.rightKeyPressed) {
        	moveRight();
        	rotateRight(delta);
        }
<<<<<<< HEAD
       
=======
        if (busInputHandler.leftKeyPressed == false &&
        	busInputHandler.rightKeyPressed == false) {
        	velocity.x = 0;        	
        }
        
>>>>>>> 2a73d58b32769899543f25a3c1ac9a9ce4db332a
        position.add(velocity.cpy().scl(delta));
        
        // make sure the bucket stays within the screen bounds
	    if(position.x < 20) position.x = 20;
	    if(position.x > 230) position.x = 230;
        
       	}
	
	
	private void moveLeft() {
    	if (velocity.x > 0) {
    		velocity.x = 0;
    	} else {
    		velocity.x -= 200 * Gdx.graphics.getDeltaTime();
    	}
	}
	
	private void moveRight() {
		if (velocity.x < 0) {
    		velocity.x = 0;
    	} else {
    		velocity.x += 200 * Gdx.graphics.getDeltaTime();
    	}
	}
	
	
	private void rotateLeft(float delta) {
/*
		if (rotation > 0) {
			rotation = 0;
		} else {
			rotation -= 600 * delta;
        	if (rotation < -20) {
        		rotation = -20;
        	}
		}
*/
	}
	
	private void rotateRight(float delta) {
/*
		if (rotation < 0) {
			rotation = 0;
		} else {
        	rotation += 480 * delta;
        	if (rotation > 20) {
        		rotation = 20;
        	}
<<<<<<< HEAD
		}
*/
=======
        }
        
        if (velocity.x == 0) {
        	rotation = 0;
        }
>>>>>>> 2a73d58b32769899543f25a3c1ac9a9ce4db332a
	}
	
	public void onClick() {
        velocity.y = -140;
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
