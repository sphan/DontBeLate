package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.InputHandler;

public class Bus {
	
	public final static int BUS_WIDTH = 50;
	public final static int BUS_HEIGHT = 110;
	public final static int BUS_START_X = 50;
	public final static int BUS_START_Y = 288;
	
	public final static int MAX_SPEED = 1000;
	public final static int MIN_SPEED = 650;
	
	private static int BUS_TURN_ACCEL = 900;
	private static int MAX_TURN_SPEED = 230;
	private static int BOUNDARY_LEFT = 20;
	private static int BOUNDARY_RIGHT = 250;
	
	private Vector2 position;
	private Vector2 velocity;
	private float forwardVelocity;
	
	private Vector2 acceleration;
	
	private float rotation;  // For handling bus rotation
	private int width;
	private int height;
	private boolean stopped;
	
	private Rectangle boundingRectangle;
	
	public Bus(float x, float y, int width, int height) {
		stopped = false;
		this.width = width;
		this.height = height;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		forwardVelocity = Road.DEFAULT_SPEED;
        acceleration = new Vector2(0, BUS_TURN_ACCEL);
        boundingRectangle = new Rectangle();
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		
		if(!stopped){
			velocity.add(acceleration.cpy().scl(delta));
			boundingRectangle.set(position.x, position.y, width, height);	//TODO: check these numbers
	        if (velocity.y < 200) {
	            velocity.y = 0;
	        }
	        
	        if (busInputHandler.leftKeyPressed) {
	        	moveLeft();
	        	//rotateLeft(delta);
	        }
	
	        if (busInputHandler.rightKeyPressed) {
	        	moveRight();
	        	//rotateRight(delta);
	        }
	        
	        if (busInputHandler.upKeyPressed) {
	        	speedUp();
	        }
	        
	        if (busInputHandler.downKeyPressed) {
	        	slowDown();
	        }
	       
	        if (busInputHandler.leftKeyPressed == false &&
	        	busInputHandler.rightKeyPressed == false) {
	        	velocity.x = 0;        	
	        }
	        
	        
	        position.add(velocity.cpy().scl(delta));
	        
	        // make sure the bucket stays within the screen bounds
		    if(position.x < BOUNDARY_LEFT) position.x = BOUNDARY_LEFT;
		    if(position.x > BOUNDARY_RIGHT) position.x = BOUNDARY_RIGHT;
        
       	}
	
	}
	
	private void moveLeft() {
    	if (velocity.x > 0) {
    		velocity.x = 0;
    	} else if(velocity.x > -MAX_TURN_SPEED) {
			velocity.x -= acceleration.y * Gdx.graphics.getDeltaTime();
    	}
	}
	
	private void moveRight() {
		if (velocity.x < 0) {
    		velocity.x = 0;
    	} else if(velocity.x < MAX_TURN_SPEED) {
    		velocity.x += acceleration.y * Gdx.graphics.getDeltaTime();
    	}
	}
	
	
	private void rotateLeft(float delta) {

		if (rotation > 0) {
			rotation = 0;
		} else {
			rotation -= 600 * delta;
        	if (rotation < -20) {
        		rotation = -20;
        	}
		}
	}
	
	private void rotateRight(float delta) {
		if (rotation < 0) {
			rotation = 0;
		} else {
        	rotation += 480 * delta;
        	if (rotation > 20) {
        		rotation = 20;
        	}
		}
 	}
	
	private void speedUp(){
		if (forwardVelocity < MAX_SPEED) {
			forwardVelocity += acceleration.y * Gdx.graphics.getDeltaTime();
    	} 
	}
	
	private void slowDown(){
		if (forwardVelocity > MIN_SPEED) {
			forwardVelocity -= acceleration.y * Gdx.graphics.getDeltaTime();
    	}
	}
	
	public void stop() {
		stopped = true;
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
    
    public Rectangle getHitBox() {
    	return boundingRectangle;
    }
    
    public float getForwardVelocity(){
    	System.out.println(velocity.y);
    	return forwardVelocity;
    }
}
