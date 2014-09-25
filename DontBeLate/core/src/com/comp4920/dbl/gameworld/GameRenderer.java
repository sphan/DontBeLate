package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.helpers.AssetLoader;

public class GameRenderer {
	private GameWorld myWorld;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	
	private int midPointX;
	private int gameWidth;
	
	private Bus bus;
	private Car car; //TODO: List of cars
	private Animation busAnimation;
	
	public GameRenderer(GameWorld world, int gameWidth, int midPointX) {
		myWorld = world;
		
		this.gameWidth = gameWidth;
		this.midPointX = midPointX;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 300, 400);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		initGameObjects();
		initAssets();
	}
	
	public void render(float runTime) {
		//Gdx.app.log("GameRenderer", "render");
		Gdx.gl.glClearColor(0, 0, 0, 1);			
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// begin SpriteBatch
		batch.begin();
		// Disable transparency
		batch.disableBlending();
		batch.draw(busAnimation.getKeyFrame(runTime),
				bus.getX(), bus.getY(), bus.getWidth() / 2.0f, bus.getHeight() / 2.0f,
				bus.getWidth(), bus.getHeight(), 1, 1, bus.getRotation());
		batch.end();
	}
	
	private void initGameObjects() {
		bus = myWorld.getBus();
		car = myWorld.getCar();
	}
	
	private void initAssets() {
		busAnimation = AssetLoader.busAnimation;
	}
}
