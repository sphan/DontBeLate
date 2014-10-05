package com.comp4920.dbl.gameworld;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.gameobjects.Roadwork;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.GameScreen;

public class GameRenderer {
	private GameWorld myWorld;
	private GameInterface gameInterface;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	
	@SuppressWarnings("unused")
	private int midPointX;
	@SuppressWarnings("unused")
	private int gameWidth;
	
	private Bus bus;
	private Animation busAnimation;

	private List<Lane> lanes;
	
	
	public Road road;
	public TextureRegion roadTex;
	
	public Image resumeButton;
	

	public GameRenderer(GameWorld world, GameInterface gameInterface, int gameWidth, int midPointX) {
		myWorld = world;
		this.gameInterface = gameInterface;
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
		// we ask the game world to remove out of bound cars and generate new ones
		myWorld.updateCars(runTime); 
		
		//Gdx.app.log("GameRenderer", "render");
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//draw road first
		batch.begin();
		//draw road 1
		batch.draw(roadTex ,0, road.getRoadStart1(), 300, 400);
		//draw road 2
		batch.draw(roadTex ,0, road.getRoadStart2(), 300, 400);	
		batch.end();
		
		drawRoadworkWarning();	
		
		// begin SpriteBatch
		batch.begin();
		// Disable transparency
//		batch.disableBlending();
		
		//draw cars
		renderObstacless(runTime);
			
		//draw bus
		batch.draw(busAnimation.getKeyFrame(runTime),
				bus.getX(), bus.getY(), bus.getWidth() / 2.0f, bus.getHeight() / 2.0f,
				bus.getWidth(), bus.getHeight(), 1, 1, bus.getRotation());
		
		
		//yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String distance = gameInterface.getDistanceStringMtrs(road.getDistanceTravelledMtrs());
		gameInterface.getBitMapFont().draw(batch, distance, gameInterface.getDistLabX(), gameInterface.getDistLabY()); 
		
		
		//draw time
		Clock clock = gameInterface.getClock();
		clock.getFont().draw(batch, clock.getDisplayText(), clock.getX(), clock.getY()); 
		batch.end();
		

		//draw pause menu
		Stage stage = gameInterface.getStage();
		stage.act();
		stage.draw();
		renderPauseMenu(stage);
				
		//check for collisions
		if(myWorld.checkCollisions()){
			stopGame();
		}
		
				
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
	}
	
	
	// Each time the cars are rendered
	private void renderObstacless(float runTime) {
		//for each lane we must render all their obstacles
		for (Lane lane : lanes){
			List<Obstacle> obstacles = lane.getObstacles();
			for (Obstacle obstacle : obstacles){				
				batch.draw(obstacle.getAnimation().getKeyFrame(runTime), obstacle.getX(), obstacle.getY(), 
						obstacle.getWidth() / 2.0f, obstacle.getHeight() / 2.0f, obstacle.getWidth(), 
						obstacle.getHeight(), 1, 1, 0);
			}
		}
	}
		
	
	private void drawRoadworkWarning() {
		for (Lane lane : lanes){
			List<Obstacle> obstacles = lane.getObstacles();
			for (Obstacle obstacle : obstacles){				
				if (obstacle instanceof Roadwork) {
					shapeRenderer.begin(ShapeType.Filled);
					shapeRenderer.setColor(Color.YELLOW);
					Vector2 warning = ((Roadwork) obstacle).getWarningOrigin();
					shapeRenderer.rect(warning.x, warning.y, 
							((Roadwork) obstacle).warningWidth(), ((Roadwork) obstacle).warningHeight());
					shapeRenderer.end();		
				}
			}
		}
	}
	
	private void renderPauseMenu(Stage stage) {
		if (!myWorld.isPaused())
			return;
		
		stage.addActor(resumeButton);
		resumeButton.setPosition(midPointX, 800 / 2 + 200);
		
		resumeButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        resumeButton.remove();
		        myWorld.start();
		    }
		});
	}
	
	private void initGameObjects() {
		bus = myWorld.getBus();
		lanes = myWorld.getLaneList();
		road = myWorld.getRoad();
	}
	
	private void initAssets() {
		busAnimation = AssetLoader.busAnimation;
		roadTex = AssetLoader.road;
//		pauseButton = new Image(AssetLoader.pauseButton);
		resumeButton = new Image(AssetLoader.resumeButton);
	}
	
	public void stopGame(){
		//stop the relevant elements
		//clock
		gameInterface.stopClock();
		//the gameworld
		myWorld.stop();
	}
	
}
