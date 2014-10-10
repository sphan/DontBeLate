package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.InputHandler;

public class Bus {
	
	public final static int BUS_WIDTH = 50;
	public final static int BUS_HEIGHT = 110;
	public final static int BUS_START_X = 50;
	public final static int BUS_START_Y = 288;
	public final static int HEADLIGHT_LEN = 12;
	
	public final static int MAX_SPEED = 1000;
	public final static int MIN_SPEED = 650;
	
	private static int BUS_TURN_ACCEL = 1800;
	private static int BUS_TURN_ACCEL2 = 1000;
	private static int ACC_CHAN_SPEED = 170; //the speed at which we swap to a different acceleration
	private static int MAX_TURN_SPEED = 630; //maximum speed
	private static int BOUNDARY_LEFT = 2;
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
        acceleration = new Vector2(BUS_TURN_ACCEL, 600);
        boundingRectangle = new Rectangle();
	}
	
	public void update(float delta, InputHandler busInputHandler) {
		
		if(!stopped){
			//velocity.add(acceleration.cpy().scl(delta)); //no effect?
			boundingRectangle.set(position.x, position.y+HEADLIGHT_LEN, width, height-HEADLIGHT_LEN);	//TODO: check these numbers
	        if (velocity.y < 200) {
	            velocity.y = 0;
	        }
	        
	        if (InputHandler.isLeftKeyPressed()) {
	        	moveLeft();
	        	//rotateLeft(delta);
	        }
	
	        if (InputHandler.isRightKeyPressed()) {
	        	moveRight();
	        	//rotateRight(delta);
	        }
	        
	        if (InputHandler.isUpKeyPressed()) {
	        	speedUp();
	        }
	        
	        if (InputHandler.isDownKeyPressed()) {
	        	slowDown();
	        }
	       
	        if (InputHandler.isLeftKeyPressed() == false &&
	        	InputHandler.isRightKeyPressed() == false) {
	        	
	        	if(velocity.x > 0){
	        		moveLeft();
	        	} else if(velocity.x < 0){
	        		moveRight();
	        	} 
	        }
	        
	        
	        position.add(velocity.cpy().scl(delta));
	        
	        // make sure the bucket stays within the screen bounds
		    if(position.x < BOUNDARY_LEFT) position.x = BOUNDARY_LEFT;
		    if(position.x > BOUNDARY_RIGHT) position.x = BOUNDARY_RIGHT;
        
       	}
	
	}
	
	private void moveLeft() {
		if (velocity.x > 0) {
    		velocity.x -= acceleration.x * Gdx.graphics.getDeltaTime() * 3.5;
    		if(velocity.x < 0){
    			velocity.x = 0;
    		}
    	}  else if(velocity.x > -ACC_CHAN_SPEED) { //initial accel
			velocity.x -= acceleration.x * Gdx.graphics.getDeltaTime();
    	}  else if(velocity.x > -MAX_TURN_SPEED) {
			velocity.x -= BUS_TURN_ACCEL2 * Gdx.graphics.getDeltaTime();
    	}
	}
	
	private void moveRight() {
		if (velocity.x < 0) {
			velocity.x += acceleration.x * Gdx.graphics.getDeltaTime() * 3.5;
			if(velocity.x > 0){
    			velocity.x = 0;
    		}
    	} else if(velocity.x < ACC_CHAN_SPEED) { //initial accel
    		velocity.x += acceleration.x * Gdx.graphics.getDeltaTime();
    	} else if(velocity.x < MAX_TURN_SPEED) {
    		velocity.x += BUS_TURN_ACCEL2 * Gdx.graphics.getDeltaTime();
    	}
	}
	
	/*
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
	*/
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
	
	public void start() {
		stopped = false;
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
    	return forwardVelocity;
    }
}
