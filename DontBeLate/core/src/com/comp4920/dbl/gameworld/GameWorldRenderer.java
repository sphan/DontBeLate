package com.comp4920.dbl.gameworld;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Drop;
import com.comp4920.dbl.gameobjects.BusStop;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.gameobjects.Scenery;
import com.comp4920.dbl.gameobjects.Score;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.helpers.DropsHandler.DropType;

public class GameWorldRenderer {
	private GameWorld myWorld;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	
	private int gameWidth;
	
	private Bus bus;
	private Animation busAnimation;

	private List<Lane> lanes;
	private List<Drop> drops;
	private List<Scenery> sceneries;
	
	private BusStop busStop;
	public Road road;
	public TextureRegion roadTex;
	private Image addingTimeBg;

	private long timeHit;
	BitmapFont bitmapFont;
	
	public GameWorldRenderer(GameWorld world, Stage stage, OrthographicCamera camera, int gameWidth, int midPointX) {
		myWorld = world;
		this.gameWidth = gameWidth;

		batch = (SpriteBatch) stage.getBatch();
		batch.setProjectionMatrix(camera.combined);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		addingTimeBg = new Image(AssetLoader.addingTimeBackground);

		timeHit = 0;
		bitmapFont = new BitmapFont(false);
		
		initGameObjects();
		initAssets();
	}
	
	public void render(float runTime) {
		// we ask the game world to remove out of bound cars and generate new ones
		myWorld.updateCars(runTime); 
		// also update checkpoints and create a new one if required
		myWorld.updateCheckpoints(runTime);
		// update drops like cars
		myWorld.updateDrops(runTime);
		// update scenery like cars (remove + spawn new scenery)
		myWorld.updateScenery(runTime);
		
		myWorld.collisionUpdate();
		
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
				
		// begin SpriteBatch
		batch.begin();
		batch.enableBlending();

		//draw checkpoints
		renderCheckpoints(runTime);
		
		//draw drops
		renderDrops(runTime);
		
		//draw cars
		renderObstacless(runTime);
		
		//draw scenery
		renderScenery(runTime);
		
		// Not sure if this should go here...
		renderTimeAdditionAnimation();
		
		//draw bus
		batch.draw(busAnimation.getKeyFrame(runTime),
				bus.getX(), bus.getY(), bus.getWidth() / 2.0f, bus.getHeight() / 2.0f,
				bus.getWidth(), bus.getHeight(), 1, 1, bus.getRotation());
		
		// draw the points added upon a collision
		renderPointsAdded();
		
		batch.end();
						
		// UNCOMMENT TO VIEW HITBOXES
		/*
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(bus.getHitBox().x, 
				bus.getHitBox().y, bus.getHitBox().width, bus.getHitBox().height);
		//shapeRenderer.setColor(Color.BLUE);
		Gdx.graphics.getGL30().glEnable(GL20.GL_BLEND); // Or GL20
        shapeRenderer.setColor(new Color(0, 0, 100, 0.3f));
		for (Lane lane : lanes){
			List<Obstacle> obstacles = lane.getObstacles();
			for (Obstacle obstacle : obstacles) {
				shapeRenderer.rect(obstacle.getHitBox().x, obstacle.getHitBox().y, 
					obstacle.getHitBox().width, obstacle.getHitBox().height);
			}
		}
		shapeRenderer.end();
		*/
	}
	
	private void renderScenery(float runTime) {
		//we draw every scenery object
		for (Scenery scenery : sceneries){		
			Animation testing = scenery.getAnimation();
			if (testing == null){
				//System.out.println("We got a problem doc");
			}
			batch.draw(scenery.getAnimation().getKeyFrame(runTime), scenery.getX(), scenery.getY(), 
					scenery.getWidth() / 2.0f, scenery.getHeight() / 2.0f, scenery.getWidth(), 
					scenery.getHeight(), 1, 1, 0);
		}
		
	}

	private void renderPointsAdded() {
		
		if (myWorld.getLastDropCollision() != null) {
			timeHit = myWorld.getLastDropCollision().getTimeHit();
		}

		if ((System.currentTimeMillis() - timeHit < 350)) {
			DropType type = myWorld.getLastCollisionDropType();
			int points;
			if (type == DropType.POINTS) {
				points = Score.OPAL_SCORE;
			} else {
				points = Score.COIN_SCORE;
			}
			String pointsLabel = "+" + points;
			bitmapFont.draw(batch, pointsLabel, bus.getX()+2, bus.getY()+bus.getHeight()+17);
		}

	}
	
	private void renderTimeAdditionAnimation() {
		
		if (busStop.isStopped()) {
			
			addingTimeBg.setScale(0.5f);
			addingTimeBg.setPosition(0, -6);
			addingTimeBg.draw(batch, 1);
			
			int timeToAdd = busStop.getTimeToAdd();
			String addingTime = "";
			String nextDistance = "" + busStop.getDistance();
			int length = nextDistance.length();
			bitmapFont.draw(batch, addingTime.concat(String.valueOf(timeToAdd)), 142, 270);
			bitmapFont.draw(batch, nextDistance, 150-length*4, 225);			
			
		}
	}
	
	
	private void renderDrops(float runTime) {
		//for each lane we must render all their obstacles

		for (Drop drop : drops){				
			batch.draw(drop.getAnimation().getKeyFrame(runTime), drop.getX(), drop.getY(), 
					drop.getWidth() / 2.0f, drop.getHeight() / 2.0f, drop.getWidth(), 
					drop.getHeight(), 1, 1, 0);
		}
		
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
		
	// Since a bus stop 'exists' above the screen, we only render it once it
	// is onscreen.
	private void renderCheckpoints(float runTime) {
		//render bus stop
		if (busStop.onScreen()) {
			batch.draw(busStop.getAnimation().getKeyFrame(runTime), busStop.getX(), busStop.getY(), 
					busStop.getWidth() / 2.0f, busStop.getHeight() / 2.0f, busStop.getWidth(), 
					busStop.getHeight(), 1, 1, 0);
		}
		
		// bus stop leader
		batch.draw(busStop.getBusStopLeadAnimation().getKeyFrame(runTime), 
				busStop.getLeadX(), busStop.getLeadY(),
				busStop.getLeadOriginX(), busStop.getLeadOriginY(), 
				busStop.getLeadWidth(), busStop.getLeadHeight(), 
				busStop.getLeadScaleX(), busStop.getLeadScaleY(), 0);

		
		//render bus stop warning
		if (!busStop.onScreen()){ 
			batch.draw(busStop.getWarningAnimation().getKeyFrame(runTime), busStop.getWarningX(), busStop.getWarningY(), 
					busStop.getWarningSideLen() / 2.0f, busStop.getWarningSideLen() / 2.0f, busStop.getWarningSideLen(), 
					busStop.getWarningSideLen(), 1, 1, 0);
		}
		
	}
	
	private void initGameObjects() {
		bus = myWorld.getBus();
		lanes = myWorld.getLaneList();
		road = myWorld.getRoad();
		drops = myWorld.getDropsList();
		busStop = myWorld.getBusStop();
		sceneries = myWorld.getSceneryList();
	}
	
	private void initAssets() {
		busAnimation = AssetLoader.busAnimation;
		roadTex = AssetLoader.road;		
	}
		
	public void dispose(){
		bitmapFont.dispose();
		shapeRenderer.dispose();	
	}
	
}
