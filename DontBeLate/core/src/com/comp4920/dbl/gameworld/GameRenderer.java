package com.comp4920.dbl.gameworld;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Car;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.helpers.CollisionHandler;

public class GameRenderer {
	private GameWorld myWorld;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	
	private int midPointX;
	private int gameWidth;
	
	private Bus bus;
	private Animation busAnimation;

	private List<Lane> lanes;
	private Animation carAnimation;

	private CollisionHandler collisions;
	
	public Road road;
	public TextureRegion roadTex;

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
		
		collisions = new CollisionHandler();
		
		initGameObjects();
		initAssets();
	}
	
	public void render(float runTime) {
		// we ask the game world to remove out of bound cars and generate new ones
		myWorld.updateCars(runTime); 
		
		//Gdx.app.log("GameRenderer", "render");
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// begin SpriteBatch
		batch.begin();
		// Disable transparency
//		batch.disableBlending();

		//draw road 1
		batch.draw(roadTex ,0, road.getRoadStart1(), 300, 400);
		//draw road 2
		batch.draw(roadTex ,0, road.getRoadStart2(), 300, 400);
		
		//draw bus
		batch.draw(busAnimation.getKeyFrame(runTime),
				bus.getX(), bus.getY(), bus.getWidth() / 2.0f, bus.getHeight() / 2.0f,
				bus.getWidth(), bus.getHeight(), 1, 1, bus.getRotation());
		//draw cars
		renderCars(runTime);
		
		batch.end();
		
		// UNCOMMENT TO VIEW HITBOXES
		/*
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(bus.getHitBox().x, 
				bus.getHitBox().y, bus.getHitBox().width, bus.getHitBox().height);
		shapeRenderer.setColor(Color.BLUE);
		for (Car car : cars) {
			shapeRenderer.rect(car.getHitBox().x, car.getHitBox().y, 
					car.getHitBox().width, car.getHitBox().height);
		}
		shapeRenderer.end();
		*/

		for (Lane lane : lanes) {
			List<Car> cars = lane.getCars();
			if (collisions.check(bus, cars)) {
				stopGame();
				break;
			}
		}
	}
	
	
	// Each time the cars are rendered
	private void renderCars(float runTime) {
		//for each lane we must render all their cars
		for (Lane lane : lanes){
			List<Car> cars = lane.getCars();
			
			for (Car car : cars){
				batch.draw(carAnimation.getKeyFrame(runTime), car.getX(), car.getY(), 
						car.getWidth() / 2.0f, car.getHeight() / 2.0f, car.getWidth(), car.getHeight(), 1, 1, 0);
			}
		}
	}
	
	
	private void stopGame() {
		bus.stop();
		myWorld.stop();
	}

	
	private void initGameObjects() {
		bus = myWorld.getBus();
		lanes = myWorld.getLanes();
		road = myWorld.getRoad();
	}
	
	private void initAssets() {
		busAnimation = AssetLoader.busAnimation;
		carAnimation = AssetLoader.carAnimation;
		roadTex = AssetLoader.road;
	}
}
