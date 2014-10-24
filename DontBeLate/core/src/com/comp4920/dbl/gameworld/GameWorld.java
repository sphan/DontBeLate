package com.comp4920.dbl.gameworld;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.comp4920.dbl.gameobjects.Bus;
import com.comp4920.dbl.gameobjects.Drop;
import com.comp4920.dbl.gameobjects.BusStop;
import com.comp4920.dbl.gameobjects.Lane;
import com.comp4920.dbl.gameobjects.Obstacle;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.gameobjects.Scenery;
import com.comp4920.dbl.gameobjects.Score;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.helpers.CollisionHandler;
import com.comp4920.dbl.helpers.DropsHandler;
import com.comp4920.dbl.helpers.DropsHandler.DropType;
import com.comp4920.dbl.helpers.HighScoreHandler;
import com.comp4920.dbl.helpers.InputHandler;
import com.comp4920.dbl.helpers.LaneHandler;
import com.comp4920.dbl.helpers.SceneryHandler;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.DBL.MusicState;
import com.comp4920.dbl.DBL.SoundState;

public class GameWorld {
	private Road road; //reference used to edit road/bus speed only
	private Bus bus;	
	private LaneHandler lanes;
	private DropsHandler drops;
	private CollisionHandler collisions;
	private BusStop busStop;
	private Drop lastDrop;
	private SceneryHandler sceneryHandler;
	
	private int numCars; //number of cars currently on the road
	private int numDrops; //number of cars currently on the road
	private int numTimeDropsCollected; //number of cars currently on the road
	private int health = 1;
	private int points = 0;
	//private int score = 0;
	private Score score;
	private HighScoreHandler highScoreHandler;
	public static final int POINT_MULTIPLIER = 5;
	private static final int MAX_CARS = 5;
	private static int maxNumCars = 1;	// max number of cars onscreen at any time
	private static int maxNumDrops = 2;	// max number of cars onscreen at any time
	private static double carDelay = 0.8; 	// delay between a car going offscreen and a new car spawning
	private static final double noCarWarmupDelay = 4;
	private static float lastCarTime;
	private boolean stopped;
	private int collisionCheckCounter = 0;
	private int currentCheckPoint = 0;
	private boolean gameOverCollision;
	private Sound coinCollectSound;
	private Sound carCrashSound;
	private Sound gameOverSound;
	private boolean endSoundPlayedAlready;
	public enum GameState {
		READY, RUNNING, PAUSED, GAMEOVER, END_GAME_CONFIRM;
	}
	
	public enum Intention {
		RESTART, BACK_TO_MENU;
	}
	
	private GameState currentState;
	private GameState prevState;
	private Intention intention;
	private SoundState soundState;
	private MusicState musicState ;
	 
	public GameWorld(int midPointX, SoundState soundState, MusicState musicState) {
		carDelay = 0.8; 
		endSoundPlayedAlready = false;
		gameOverCollision = false;
		score = new Score();
		highScoreHandler = new HighScoreHandler();		
		maxNumCars = 1;
		currentCheckPoint = 0;
		stopped = false;
		lastCarTime = 0;
		bus = new Bus(midPointX-Bus.BUS_WIDTH/2, Bus.BUS_START_Y, Bus.BUS_WIDTH, Bus.BUS_HEIGHT);
		busStop = new BusStop((int) (bus.getY() + BusStop.firstX));
		lanes = new LaneHandler(busStop);
		sceneryHandler = new SceneryHandler();
		road = new Road();
		drops = new DropsHandler();
		collisions = new CollisionHandler();		
		prevState = currentState = GameState.READY;
		intention = Intention.RESTART;
		
		this.soundState = soundState;
		this.musicState = musicState;
		
		coinCollectSound = AssetLoader.coinCollectSound;
		carCrashSound = AssetLoader.carCrashSound;
		gameOverSound = AssetLoader.gameOverSound;
	}
	
	public void update(float delta, InputHandler busInputHandler) {

		switch (currentState) {
		case READY:
			updateReady(delta);
			break;
		case RUNNING:
			updateRunning(delta, busInputHandler);
			break;
		case PAUSED:
			break;
		}
	}
	
	public void updateReady(float delta) {
		start();
	}
	
	public void updateRunning(float delta, InputHandler busInputHandler) {
		if(stopped){
			return;
		}
	
		road.setRoadSpeed(bus.getForwardVelocity());
		road.update(delta);
		bus.update(delta, busInputHandler);
		lanes.update(delta);
		drops.update(delta);
		busStop.update(delta);
		sceneryHandler.update(delta);
	}
	
	
	//we need to check if a car has gone off the edge
	//of the screen and remove it, and spawn a new car if needed.
	public void updateCars (float runTime){
		if(stopped) {
			return;
		}

		numCars = lanes.updateObstacles();
		if (newCarTime(runTime)) {
			if ((runTime%10) < 6) { //Change proportion every 10 seconds it is random
				lanes.addObstacleRandomLane(runTime);
				lastCarTime = runTime;
			} else {
				lanes.addObstacle(runTime);
				lastCarTime = runTime;
			}
		}
	}
	
	public void updateCheckpoints(float runTime) {
		// check if the bus is inside a checkpoint
		if (busStop.contains(bus)) {
			// pause the bus (not the cars though) for x seconds
			road.stop();
			bus.stop();
			busStop.stop();
			lanes.roadStopped();
			// add time - just wait a few seconds for now.	
			// turn sound off
			DBL.turnOffSound();
			//turn off collisions
			collisions.turnCollisionsOff();
		}
		
		
		if (busStop.isStopped() && (System.currentTimeMillis() > busStop.getTimeStoppedAt() + busStop.getStopDuration())) {
			// start everything again and create new bus stop
			road.start();
			bus.start();
			lanes.resume();
			busStop.resume();
			DBL.turnOnSound();
			//set back collisions
			collisions.turnCollisionsOn();
		}

		// check if the bus stop is off the screen
		if (busStop.offScreen()) {
			increaseDifficulty();
			// replace the bus stop with a new one
			busStop.replace();
			currentCheckPoint++;
			score.checkpoint(currentCheckPoint);
		}
	}
	
	public void updateScenery (float runTime){
		if(stopped) {
			return;
		}
		
		sceneryHandler.checkSceneryBounds();
		sceneryHandler.newScenery(runTime);
	}
	
	public void updateDrops(float runTime) {
		if(stopped) {
			return;
		}
		
		numDrops = drops.updateDrops();
		
		if (newDropTime(runTime)) {
			drops.newDrop(runTime);
			numDrops++;
		}
	}

	
	public boolean checkCarCollisions (){
		for (Lane lane : lanes.getLanes()) {
			List<Obstacle> cars = lane.getObstacles();
			if (collisions.check(bus, cars)) {
				return true;
			}
		}
		return false;
	}
	
	//TODO: need it to change specific details (certain drops have certain effects)
	public DropType checkDropsCollisions (){
		
		//remove the drop from the drop list
		Drop drop = collisions.checkDrops(bus, drops.getDrops());
		
		if (drop != null){
			//set last drop
			lastDrop = drop;
			drops.removeDrop(drop);
			return drop.getType();
			
		} else {
			return DropType.NONE;
		}
	}
	
	// as above except this returns the actual drop
	public Drop getLastDropCollision() {
		return lastDrop;
	}
	
	
	// Returns true if we should generate another car, false otherwise.
	// Spawn a new car if there are fewer than numCars on screen AND
	//	at least carDelay seconds have elapsed since the last car was spawned.
	private boolean newCarTime(float runTime) {
		return (numCars < maxNumCars && runTime > lastCarTime + carDelay && runTime > noCarWarmupDelay);
	}
	
	// Returns true if we should generate another drop, false otherwise.
	// Spawn a new drop if there are fewer than numDrops on screen 	
	private boolean newDropTime(float runTime) {
		return (numDrops < maxNumDrops);
	}
	
	public float getDistanceToBusStop() {
		// if the bus stop is above the bus:
		if (busStop.getY() < bus.getY()) { 
			// Case a: The bus is at the bus stop
			if (busStop.contains(bus)) {
				return 0;
			}
			// Case b: The bus stop is above y0
			if (busStop.getY() < 0) {
				return (bus.getY() + Math.abs(busStop.getY()+ busStop.getWidth()))/Road.CONVERT_METERS;
			}
			// Case c: The bus stop is beneath y0 (and on the screen) 
			int distance = (int) ((bus.getY() - (busStop.getY() + busStop.getWidth()))/Road.CONVERT_METERS);
			
			if(distance < 0) distance = 0;
			
			return distance;
		// else we need to calculate the distance to a stop that doesn't exist yet...
		} else {
			return 0;
		}
	}
	
	
	public Bus getBus() {
		return bus;
	}
	
	/**
	 * Set difficulty
	 */
	private void increaseDifficulty(){
		if (maxNumCars < MAX_CARS)
		maxNumCars++;
		
		if(currentCheckPoint > 4){
			carDelay -= 0.015;
		}
		
		busStop.setDistance(busStop.getDistance() + 800);
	}
	
	public Road getRoad(){
		return road;
	}
	
	public List<Lane> getLaneList() {
		return lanes.getLanes();
	}
	
	public List<Drop> getDropsList() {
		return drops.getDrops();
	}
	
	public BusStop getBusStop() {
		return busStop;
	}
	
	public void stop(){
		stopped = true;
		bus.stop();
		road.stop();
		lanes.stop();
	}
	
	public void start() {
		prevState = currentState;
		currentState = GameState.RUNNING;
		stopped = false;
		bus.start();
		road.start();
		lanes.start();
	}
	
	public void pause() {
		prevState = currentState;
		currentState = GameState.PAUSED;
		stop();
	}
	
	public void endGame(){
		prevState = currentState;
		currentState = GameState.GAMEOVER;
		stop();
		if (DBL.isSoundOn() && !endSoundPlayedAlready) {
			endSoundPlayedAlready = true;
			gameOverSound.play(0.2f);
		}
		//TODO: do something if the user beats their previous highscore
		if (highScoreHandler.submitScore(score)) {
			System.out.println("YOU DID IT! YOU BEAT YOUR HIGH SCORE! GREAT JOB!");
		}
	}
	
	public void confirmEndGame() {
		prevState = currentState;
		currentState = GameState.END_GAME_CONFIRM;
	}
	
	public void exitEndGameConfirmation() {
		GameState temp = prevState;
		prevState = currentState;
		this.currentState = temp;
	}
	
	public void setIntention(Intention intention) {
		this.intention = intention;
	}
	
//	public void turnOnSound() {
//		soundState = SoundState.SOUND_ON;
//	}
//	
//	public void turnOnMusic() {
//		musicState = MusicState.MUSIC_ON;
//	}
//	
//	public void turnOffSound() {
//		soundState = SoundState.SOUND_OFF;
//	}
//	
//	public void turnOffMusic() {
//		musicState = MusicState.MUSIC_OFF;
//	}
	
	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}
	
	public boolean isReady() {
		return currentState == GameState.READY;
	}
	
	public boolean isPaused() {
		return currentState == GameState.PAUSED;
	}
	
	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}
	
	public boolean isConfirming() {
		return currentState == GameState.END_GAME_CONFIRM;
	}
	
	public boolean isPausedConfirming() {
		return currentState == GameState.END_GAME_CONFIRM && prevState == GameState.PAUSED;
	}
	
	public boolean isEndGameConfirming() {
		return currentState == GameState.END_GAME_CONFIRM && prevState == GameState.GAMEOVER;
	}
	
	// for intentions
	public boolean isRestart() {
		return intention == Intention.RESTART;
	}
	
	public boolean isBackToMenu() {
		return intention == Intention.BACK_TO_MENU;
	}
	
//	public boolean isSoundOn() {
//		return soundState == SoundState.SOUND_ON;
//	}
//	
//	public boolean isMusicOn() {
//		return musicState == MusicState.MUSIC_ON;
//	}

	public void incrementDropCounter(DropType type){
		if(type == DropType.TIME){
			numTimeDropsCollected++;
		} else if(type == DropType.POINTS){
			points++;
		}
	}
	
	public void decrementDropCounter(int n, DropType type){
		if(type == DropType.TIME){
			numTimeDropsCollected -= n;
			if(numTimeDropsCollected < 0){
				numTimeDropsCollected = 0;
			}
		} else if (type == DropType.HEALTH){
			health-=n;
			if(health < 0){
				health = 0;
			}
		}
	}
	
	public int getTimeDropsCollected(){
		return numTimeDropsCollected;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getPoints(){
		return points;
	}
	
	public void collisionUpdate(){
		
	//we only wan't to check every 3rd try to reduce computation
		collisionCheckCounter++; 
		//check for collisions
		if(collisionCheckCounter%3 == 0){
			if(checkCarCollisions()){
				setGameOverCollision();
				decrementDropCounter(1, DropType.HEALTH);
				if (DBL.isSoundOn()) {
					carCrashSound.play(0.2f);
				}
				if(getHealth() < 1){
					endGame();
					stop();
					
				}
			}
		}
		//collect coins!
		if(collisionCheckCounter%3 == 1){
			DropType dropType = checkDropsCollisions();
			
			if (dropType == DropType.TIME){
				if (DBL.isSoundOn()) {
					coinCollectSound.play(0.2f);
				}
				incrementDropCounter(dropType);
				score.increase("coin");

			} else if (dropType == DropType.EXTRAPOINTS){
				System.out.println("Caught a time EXTRAPOINTS!");
				
				if (DBL.isSoundOn()) {
					//Gdx.app.log("Collision detection", "sound is on");
					coinCollectSound.play(0.2f);
				}
				
				incrementDropCounter(dropType);
				score.extraIncrease();

			} else if (dropType == DropType.POINTS){
				System.out.println("Caught a time POINTS!");
				if (DBL.isSoundOn()) {
					//Gdx.app.log("Collision detection", "sound is on");
					coinCollectSound.play(0.2f);
				}
				
				incrementDropCounter(dropType);
				score.increase("opal");
			}
		}
	}
	/**
	 * This is the function that handles the score for a game
	 * modify to change how it is determined.
	 * @return
	 */
	public int generateScore(){
		return score.getScore();
	}
	
	public int getHighScore() {
		return highScoreHandler.getHighScore();
	}
	
	public int getBusDistance(){
		return road.getDistanceTravelledMtrs();
	}
	
	public int getCurrentCheckPoint(){
		return currentCheckPoint;
	}
	
	public void setGameOverCollision(){
		gameOverCollision = true;
	}
	
	public boolean isGameOverCollision(){
		return gameOverCollision;
	}

	public List<Scenery> getSceneryList() {
		return sceneryHandler.getScenery();
	}

	public DropType getLastCollisionDropType(){
		return lastDrop.getType();
	}
}
