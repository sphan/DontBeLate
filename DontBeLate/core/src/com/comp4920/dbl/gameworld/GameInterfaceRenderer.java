package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.GameScreen;

public class GameInterfaceRenderer {
	private GameScreen currentScreen;
	private Clock clock;
	private Stage stage;
	private SpriteBatch batch;
	private Sprite sprite;
	
	private static final int SCREEN_TOP = Gdx.graphics.getHeight();
	
	//distance travelled / opal cards
	BitmapFont yourBitmapFontName;
	private float posDistLabX= 10;
	private float posDistLabY= SCREEN_TOP/2;
	
	// opal score
	private float posOpalLabX= 10;
	private float posOpalLabY= SCREEN_TOP/2 - 20;

	
	//timer bonus
	private float posCoinLabX = 160;
	private float posCoinLabY = SCREEN_TOP/2 - 20;
	
	// time remaining
	private float posRemainingTimeLabX= 160;
	private float posRemainingTimeLabY= SCREEN_TOP/2;

	
	
	//resume button
	private int resumeButtonX;
	private int resumeButtonY;
	
	//end game button
	private int endGameButtonX;
	private int endGameButtonY;
	
	//pause, resume and restart buttons
	private Image pauseButton;
	private Image resumeButton;
	private Image restartButton;
	private Image endGameButton;
	private Image yesButton;
	private Image noButton;
	
	//yes and no buttons
	private int yesButtonX = 150;
	private int yesButtonY = 180;
	private int noButtonX = 300;
	private int noButtonY = 180;
	
	//resume
	private int restartButtonX;
	private int restartButtonY;
	
	//data
	private int midPointX;
	private int gameWidth;
	public Road road;
	private GameWorld myWorld;
	private int screenHeight;
	
	public GameInterfaceRenderer (GameScreen screen, GameWorld myWorld, OrthographicCamera camera, int gameWidth, int midPointX) {
		this.screenHeight = Gdx.graphics.getHeight();
		this.currentScreen = screen;
		this.myWorld = myWorld;
		this.gameWidth = gameWidth;
		this.midPointX = midPointX;
		endGameButtonX = midPointX;
		resumeButtonX = 150;
		resumeButtonY = 180;
		endGameButtonX = 150;
		endGameButtonY = 180;
		restartButtonX = 150;
		restartButtonY = 180;
		road = myWorld.getRoad();
		clock = myWorld.getBusStop().getClock();//new Clock();
		pauseButton = new Image(AssetLoader.pauseButton);
		resumeButton = new Image(AssetLoader.resumeButton);
		restartButton = new Image(AssetLoader.restartButton);//TODO: create restart button
		endGameButton = new Image(AssetLoader.endGameButton);
		yesButton = new Image(AssetLoader.yesButton);
		noButton = new Image(AssetLoader.noButton);
		stage = new Stage(new FitViewport(300,400,camera));
		batch = (SpriteBatch) stage.getBatch();
		batch.setProjectionMatrix(camera.combined);
		
		yourBitmapFontName = new BitmapFont(false);

	}
		
	public void render(float runTime) {
		//check if we ran out of time
		checkTimer();
		
		batch.begin();
		//draw coins collected
		yourBitmapFontName.setColor(1.0f, 1.4f, 1.4f, 1.0f);
		int timeCollected = myWorld.getTimeDropsCollected();
		//time reduction calculation
		int timeReduction = timeCollected; //1 = 0.2 seconds
		String timeBonusCollectedLabel = "Time Bonus: " + timeReduction;
		getBitMapFont().draw(batch, timeBonusCollectedLabel, getCoinLabX(), getCoinLabY()); 
		
		//draw distance travelled
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String distance = getDistanceStringMtrs(road.getDistanceTravelledMtrs());
		getBitMapFont().draw(batch, distance, getDistLabX(), getDistLabY()); 
		
		//No. of points collected
		String pointLabel = "Opal Cards: " + myWorld.getPoints(); //
		getBitMapFont().draw(batch, pointLabel, posOpalLabX, posOpalLabY); 
		
		// Display the time remaining until the bus stop.
		String timeLabel = clock.getDisplayText() + myWorld.getBusStop().getRemainingTime();
		getBitMapFont().draw(batch, timeLabel, posRemainingTimeLabX, posRemainingTimeLabY);
		
		
		batch.end();

		//draw pause menu
		Stage stage = getStage();
		stage.act();
		stage.draw();
		drawPauseButton(stage);
		
		if (myWorld.isPaused()){
//			batch.begin();
//			drawMenuBackground(AssetLoader.pauseMenuBgRegion);
//			batch.end();
			renderPauseMenu(stage, clock);
		} else if (myWorld.isGameOver()){
			renderGameOverScreen(stage, clock);
		}
				
	}
	

	private void drawPauseButton(Stage stage) {
		stage.addActor(getPauseButton());
//		gameInterface.getStage().addActor(gameInterface.getPauseButton());
		getPauseButton().setPosition(0, 0 );  //TODO: should not hard code the position
		getPauseButton().setScale((float)0.5);
		
		getPauseButton().addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("GameScreen pausebutton touchDown", "pauseButton is touchDown");
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	Gdx.app.log("GameScreen pausebutton touchUp", "pauseButton is clicked");
		    	if (!myWorld.isGameOver())
		    		myWorld.pause();
		    }
		});
	}
	
	/**
	 * We check the time
	 */
	private void checkTimer (){
		if (myWorld.getBusStop().getRemainingTime() < 1){
			myWorld.endGame();
		}
	}
	
	private void renderPauseMenu(Stage stage, final Clock clock) {

		final Image resumeButton = getResumeButton();
		final Image endGameButton = getEndGameButton();
		stage.addActor(resumeButton);
		stage.addActor(endGameButton);
		clock.stop();
				
		resumeButton.setPosition(gameWidth/3, 10*screenHeight/30);
		resumeButton.setScale((float)0.5);
		endGameButton.setPosition(gameWidth/3, 7*screenHeight/30);
		endGameButton.setScale((float)0.5);
		
		resumeButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        //can only click on resume if not on end game confirmation state
		    	yesButton.remove();
		    	noButton.remove();
		    	resumeButton.remove();
		        endGameButton.remove();
		        myWorld.start();
		        clock.start();

		    }
		});
		
		endGameButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		    	resumeButton.remove();
		    	endGameButton.remove();
		    	renderEndGameConfirmation();
		    	Gdx.app.log("EndGameButton", "click");
		    }
		});
	}
	
	private void renderEndGameConfirmation() {
		
		final Image yesButton = getYesButton();
		final Image noButton = getNoButton();
		
		batch.begin();
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String confirmMsg = "Are you sure you want to quit the game?";
		getBitMapFont().draw(batch, confirmMsg, 150, 150); 
		batch.end();

		yesButton.setPosition(20*gameWidth/35, 10*screenHeight/35);
		yesButton.setScale((float)0.5);
		noButton.setPosition(15*gameWidth/45, 10*screenHeight/35);
		noButton.setScale((float)0.5);
		stage.addActor(yesButton);
		stage.addActor(noButton);
		
		yesButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		    	Gdx.app.log("YesButton", "click");
		    	currentScreen.switchToMenu();
		    }
		});
		
		noButton.addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		    {
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		    {
		    	Gdx.app.log("NoButton", "click");
		    	yesButton.remove();
		    	noButton.remove();
		    	renderPauseMenu(stage, clock);
		    }
		});
	}
	
	private void renderGameOverScreen(Stage stage, Clock clock) {
		clock.stop();
		//display score
		int score = myWorld.generateScore();
		//distance remaining to next bus stop
		
		batch.begin();
		
		String pointsDisplay = "Opal Points (" + myWorld.getPoints() + "x" + myWorld.POINT_MULTIPLIER + "): " + myWorld.getPoints()*myWorld.POINT_MULTIPLIER;
		getBitMapFont().draw(batch, pointsDisplay, 95, 100);
		
		String distanceDisplay = "Distance Points: " + myWorld.getBusDistance();
		getBitMapFont().draw(batch, distanceDisplay, 95, 125);
		
		String scoreDisplay = "Total Score: " + score;
		getBitMapFont().draw(batch, scoreDisplay, 95, 150);
		
		batch.end();
		
		//restart button
		stage.addActor(getRestartButton());
		getRestartButton().setPosition(restartButtonX, restartButtonY);
		getRestartButton().addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("GameScreen restartbutton touchDown", "restartButton is touchDown");
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	Gdx.app.log("GameScreen restartbutton touchUp", "restartbutton is clicked");
		    	currentScreen.switchNewScreenSet();
		    }
		});
	}
	
	private void drawMenuBackground(TextureRegion background) {
		sprite = new Sprite(background);
		sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth() );
	    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	    sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
	}
	
	public Clock getClock(){
		return clock;
	}
	
	public void stopClock(){
		clock.stop();
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Image getPauseButton(){
		return pauseButton;
	}
	
	public Image getResumeButton(){
		return resumeButton;
	}
	
	public Image getEndGameButton() {
		return endGameButton;
	}
	
	public Image getYesButton() {
		return yesButton;
	}
	
	public Image getNoButton() {
		return noButton;
	}
	
	public Image getRestartButton(){
		return restartButton;
	}
	
	public String getDistanceString (float distance){
		return "Distance: " + distance;
	}
	
	public String getDistanceStringMtrs (int distance){
		return "Distance: " + distance + " m";
	}
	
	public BitmapFont getBitMapFont (){
		return yourBitmapFontName;
	}
	
	public float getDistLabY() {
		return posDistLabY;
	}
	
	public float getDistLabX() {
		return posDistLabX;
	}
	
	public float getCoinLabY() {
		return posCoinLabY;
	}
	
	public float getCoinLabX() {
		return posCoinLabX;
	}
	
	
	public void dispose(){
		//batch.dispose();
		
		//TODO: disposal crashes java for some reason... cannot find solution so we aren't disposing for now.
		//However, interesting there seems to be no memory leaks which seem to indicate that 
		//the stage is being disposed automatically... very strange. 
		//stage.dispose(); 
		yourBitmapFontName.dispose();
		
	}
	
	public void reset(){
		clock = new Clock();
		Road.resetDistanceTravelled();
	}


}
