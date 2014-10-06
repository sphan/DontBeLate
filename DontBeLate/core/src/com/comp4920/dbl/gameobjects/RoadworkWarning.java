package com.comp4920.dbl.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.comp4920.dbl.helpers.AssetLoader;

public class RoadworkWarning implements Obstacle {

	public static final float HEIGHT = 150;;
	public static final float WIDTH = 20;
	
	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	
	private Animation roadworkWarningAnimation;
	
	public RoadworkWarning(float x_position, float y_position) {
		int x = (int) x_position;
		int y = (int) y_position;
		this.position = new Vector2(x, y);
		velocity = new Vector2(0, 20);
        acceleration = new Vector2(0, 100);
        velocity.y = Road.DEFAULT_SPEED;
		roadworkWarningAnimation = AssetLoader.roadworkWarningAnimation;
	}
	
	public float getHeight() {
		return HEIGHT;
	}
	
	public float getWidth() {
		return WIDTH;
	}

	@Override
	public int getStartX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int genStartSpeed() {
		// TODO Auto-generated method stub
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
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
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
	public float getVerticalSpeed() {
		return velocity.y;
	}

	@Override
	public Rectangle getHitBox() {
		return new Rectangle(0,0,0,0);
	}

	@Override
	public Animation getAnimation() {
		return roadworkWarningAnimation;
	}
}
