package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.AssetLoader;

public class Roadwork implements Obstacle {

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	
	private static final int HEIGHT = 140;
	private static final int WIDTH = 40;

	protected Rectangle boundingRectangle;
	private Animation roadworkAnimation;
	
	// We can safely ignore maxspeed as the speed is always whatever the road speed is.
	public Roadwork(int x_position, int maxSpeed) {     
		int x = x_position;
		int y = -HEIGHT;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = Road.DEFAULT_SPEED;
        boundingRectangle = new Rectangle();
        roadworkAnimation = AssetLoader.roadworkAnimation;
	}
	
	
	@Override
	public int getStartX() {
		return 0;
	}

	@Override
	public int genStartSpeed() {
		return 0;
	}

	@Override
	public boolean offScreen() {
		int screenHeight = Gdx.graphics.getHeight();
		return (this.getY()-HEIGHT/2 > screenHeight/2);
	}

	@Override
	public void update(float delta) {
		position.y += delta*(velocity.y + (Road.getRoadSpeed()-Road.DEFAULT_SPEED));
		boundingRectangle.set(position.x, position.y, WIDTH, HEIGHT);	//TODO: check these numbers
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getX() {
		return position.x;
	}

	@Override
	public float getY() {
		return position.y;
	}

	@Override
	public float getWidth() {
		return WIDTH;
	}

	@Override
	public float getHeight() {
		return HEIGHT;
	}

	@Override
	public Rectangle getHitBox() {
		return boundingRectangle;
	}


	@Override
	public float getVerticalSpeed() {
		return velocity.y;
	}


	@Override
	public Animation getAnimation() {
		return roadworkAnimation;
	}

}
