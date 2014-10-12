package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.GameScreen;
import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

public class GameInterfaceRenderer {
	private GameScreen currentScreen;
	private Clock clock;
	private Stage stage;
	private SpriteBatch batch;
	
	//distance
	BitmapFont yourBitmapFontName;
	private float posDistLabX= 25;
	private float posDistLabY= 15;
	
	//coins and timer reduction
	private float posCoinLabX = 145;
	private float posCoinLabY = 15;
	
	//pause, resume and restart buttons
	private Image pauseButton;
	private Image resumeButton;
	private Image restartButton;
	private Image endGameButton;
	private Image yesButton;
	private Image noButton;
	
	//data
	private int midPointX;
	private int gameWidth;
	public Road road;
	private GameWorld myWorld;
	
	public GameInterfaceRenderer (GameScreen screen, GameWorld myWorld, OrthographicCamera camera, int gameWidth, int midPointX) {
		this.currentScreen = screen;
		this.myWorld = myWorld;
		this.gameWidth = gameWidth;
		this.midPointX = midPointX;
		road = myWorld.getRoad();
		clock = myWorld.getBusStop().getClock();//new Clock();
		stage = new Stage();
		pauseButton = new Image(AssetLoader.pauseButton);
		resumeButton = new Image(AssetLoader.resumeButton);
		restartButton = new Image(AssetLoader.restartButton);//TODO: create restart button
		endGameButton = new Image(AssetLoader.endGameButton);
		yesButton = new Image(AssetLoader.yesButton);
		noButton = new Image(AssetLoader.noButton);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		yourBitmapFontName = new BitmapFont(true);

	}
		
	public void render(float runTime) {
		
		batch.begin();
		//draw coins collected
		yourBitmapFontName.setColor(1.0f, 1.4f, 1.4f, 1.0f);
		int coinCollected = myWorld.getCoinCollected();
		//time reduction calculation
		int timeReduction = coinCollected/5; //1 = 0.2 seconds
		String coinCollectedLabel = "Time Bonus: " + timeReduction + " (" + (coinCollected%5) + "/5" + ")";
		getBitMapFont().draw(batch, coinCollectedLabel, getCoinLabX(), getCoinLabY()); 
		
		//draw distance travelled
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String distance = getDistanceStringMtrs(road.getDistanceTravelledMtrs());
		getBitMapFont().draw(batch, distance, getDistLabX(), getDistLabY()); 
		
		//draw time
		/* Replaced with remaining time bellow
		long time = clock.getElapsedTime() - timeReduction;
		String timeLabel = "Time:  " + time + "s (-" + timeReduction + ")";
		
		Clock clock = getClock();
		clock.getFont().draw(batch, timeLabel, clock.getX(), clock.getY());
		*/
		// Display the time remaining until the bus stop.
		String timeLabel = clock.getDisplayText() + myWorld.getBusStop().getRemainingTime();
		clock.getFont().draw(batch, timeLabel, clock.getX(), clock.getY());
		
		String distanceToNextStop = "Distance: " + (int)myWorld.getDistanceToBusStop();
		myWorld.getBusStop().getFont().draw(batch, distanceToNextStop, clock.getX(), clock.getY()+20);
		
		batch.end();

		//draw pause menu
		Stage stage = getStage();
		stage.act();
		stage.draw();
		drawPauseButton(stage);
		
		if (myWorld.isPaused()){
			renderPauseMenu(stage, clock);
		} else if (myWorld.isGameOver()){
			renderGameOverScreen(stage, clock);
		}
				
	}
	

	private void drawPauseButton(Stage stage) {
		stage.addActor(getPauseButton());
//		gameInterface.getStage().addActor(gameInterface.getPauseButton());
		getPauseButton().setPosition(posDistLabX-3, posDistLabY );  //TODO: should not hard code the position
		
		getPauseButton().addListener(new InputListener() {
			@Override
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log("GameScreen pausebutton touchDown", "pauseButton is touchDown");
		        return true;
		    }
			
		    @Override
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		    	Gdx.app.log("GameScreen pausebutton touchUp", "pauseButton is clicked");
		    	myWorld.pause();
		    }
		});
	}
	
	private void renderPauseMenu(Stage stage, final Clock clock) {

		final Image resumeButton = getResumeButton();
		final Image endGameButton = getEndGameButton();
		stage.addActor(resumeButton);
		stage.addActor(endGameButton);
		clock.stop();
		resumeButton.setPosition(midPointX, 800 / 2 + 200);
		endGameButton.setPosition(midPointX, 800 / 2 + 150);
		
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
		
		yesButton.setPosition(150, 180);
		noButton.setPosition(300, 180);
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
		stage.addActor(getRestartButton());
		getRestartButton().setPosition(midPointX+60, 500);
		clock.stop();
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
		batch.dispose();
		stage.dispose();
		yourBitmapFontName.dispose();
		
	}
	
	public void reset(){
		clock = new Clock();
		Road.resetDistanceTravelled();
	}


}
