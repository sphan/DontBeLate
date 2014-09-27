package com.comp4920.dbl.gameworld;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Animation busAnimation;

	private List<Car> cars;
	private Animation carAnimation;
	private static final int numCars = 10;	// max number of cars onscreen at any time
	private static final int carDelay = 1; 	// delay between a car going offscreen and a new car spawning
	private static float lastCarTime;

	public TextureRegion road;
	
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
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// begin SpriteBatch
		batch.begin();
		// Disable transparency
//		batch.disableBlending();

		//draw road
		batch.draw(road ,0, 0, 300, 400);
		
		//draw bus
		batch.draw(busAnimation.getKeyFrame(runTime),
				bus.getX(), bus.getY(), bus.getWidth() / 2.0f, bus.getHeight() / 2.0f,
				bus.getWidth(), bus.getHeight(), 1, 1, bus.getRotation());
		//draw cars
		renderCars(runTime);
		
		batch.end();
		
	}
	
	
	// Each time the cars are rendered, we need to check if a car has gone off the edge
	//	of the screen and spawn a new car if needed.
	private void renderCars(float runTime) {
		for (Car car : cars){
			batch.draw(carAnimation.getKeyFrame(runTime), car.getX(), car.getY(), 
					car.getWidth() / 2.0f, car.getHeight() / 2.0f, car.getWidth(), car.getHeight(), 1, 1, 0);
		}
		
		for (Iterator<Car> iter = cars.iterator(); iter.hasNext(); ){
			Car car = iter.next();
			if (car.offScreen()) {
				iter.remove();
			}
		}
		
		if (newCarTime(runTime)) {
			cars.add(new Car());
			lastCarTime = runTime;
		}
	}
	
	
	// Returns true if we should generate another car, false otherwise.
	// Spawn a new car if there are fewer than numCars on screen AND
	//	at least carDelay seconds have elapsed since the last car was spawned.
	private boolean newCarTime(float runTime) {
		return (cars.size() < numCars && runTime > lastCarTime + carDelay);
	}

	
	
	private void initGameObjects() {
		bus = myWorld.getBus();
		cars = myWorld.getCars();
		lastCarTime = 0;
	}
	
	private void initAssets() {
		busAnimation = AssetLoader.busAnimation;
		carAnimation = AssetLoader.carAnimation;
		road = AssetLoader.road;
	}
}
