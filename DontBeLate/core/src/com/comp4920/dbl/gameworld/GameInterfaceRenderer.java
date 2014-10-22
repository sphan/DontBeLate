package com.comp4920.dbl.gameworld;

import java.sql.Time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.gameworld.GameWorld.Intention;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.GameScreen;


public class GameInterfaceRenderer {
	private GameScreen currentScreen;
	private Clock clock;
	private Stage stage;
	private SpriteBatch batch;
	private Sprite sprite;

	private BitmapFont yourBitmapFontName;
	private ShapeRenderer shapeRenderer;
	
	// opal score
	private float posOpalLabX = 10;
	private float posOpalLabY = 787 / 2 - 20;

	// timer bonus
	private float posCoinLabX = 160;
	private float posCoinLabY = 787 / 2 - 20;
	
	// time remaining
	private float posRemainingTimeLabX = 295/2 - 5;
	private float posRemainingTimeLabY = 388;

	// score
	private float scoreLabelX = 180;
	private float scoreLabelY = 395;
	
	// high score
	private float highScoreLabelX = scoreLabelX;
	private float highScoreLabelY = scoreLabelY - 15;
	
	// Game screen UI
	private Image uiBackground;
	private float uiBackgroundX = 0;
	private float uiBackgroundY = 365;
	private float uiBackgroundScale = (float) 0.6;
	
	private Image uiBusStop;
	private float uiBusStopX = 132;
	private float uiBusStopY = 365;
	private float uiBusStopScale = (float) 0.45;
	
	
	
	// resume button
	private int resumeButtonX;
	private int resumeButtonY;
	
	//end game button
	private int endGameButtonX;
	private int endGameButtonY;

	// pause, resume and restart buttons
	private Image pauseButton;
	private Image resumeButton;
	private Image restartButton;
	private Image endGameButton;
	private Image yesButton;
	private Image noButton;
	private Image soundEffectButton;
	private Image pauseMenuBg;
	private Image restartMenuBg;
	private Image mainMenuBg;
	private Image crashedMenuBg;
	private Image timeoutMenuBg;
	private Image objectiveBg;
	
	private Image offBar;
	

	// yes and no buttons
	private int yesButtonX = 150;
	private int yesButtonY = 180;
	private int noButtonX = 300;
	private int noButtonY = 180;

	// resume
	private int restartButtonX;
	private int restartButtonY;

	// data
	private int midPointX;
	private int gameWidth;
	public Road road;
	private GameWorld myWorld;
	private int screenHeight;

	// helper attribute, for rendering exit confirmation page
	String endGameConfirmationfromPage = "";

	public GameInterfaceRenderer(GameScreen screen, GameWorld myWorld,
	        OrthographicCamera camera, int gameWidth, int midPointX) {
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
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
		clock = myWorld.getBusStop().getClock();// new Clock();
		pauseButton = new Image(AssetLoader.pauseButton);
		resumeButton = new Image(AssetLoader.resumeButton);
		restartButton = new Image(AssetLoader.restartButton);// TODO: create
		                                                     // restart button
		endGameButton = new Image(AssetLoader.endGameButton);
		yesButton = new Image(AssetLoader.yesButton);
		noButton = new Image(AssetLoader.noButton);
		soundEffectButton = new Image(AssetLoader.soundEffectButton);
		offBar = new Image(AssetLoader.offBar);
		
		pauseMenuBg = new Image(AssetLoader.pauseMenuBackground);
		restartMenuBg = new Image(AssetLoader.restartMenuBackground);
		mainMenuBg = new Image(AssetLoader.mainMenuBackground);
		crashedMenuBg = new Image(AssetLoader.crashedGameOverBackground);
		timeoutMenuBg = new Image(AssetLoader.timeoutGameOverBackground);
		objectiveBg = new Image(AssetLoader.objectiveBackground);
		
		
		stage = new Stage(new FitViewport(300, 400, camera));
		batch = (SpriteBatch) stage.getBatch();
		batch.setProjectionMatrix(camera.combined);
		uiBackground = new Image(AssetLoader.uiBackground);
		uiBusStop = new Image(AssetLoader.uiBusStop);

		yourBitmapFontName = new BitmapFont(false);	
	}

	public void render(float runTime) {
		// check if we ran out of time
		checkTimer();

		//Print objective/ tutorial
		
		if (runTime < 3.5){
			
			batch.begin();
			objectiveBg.setScale(0.5f);
			objectiveBg.setPosition(0, -5);
			objectiveBg.draw(batch, 1);
						
			
//			shapeRenderer.begin(ShapeType.Line);
//			shapeRenderer.setColor(Color.WHITE);
//			shapeRenderer.rect(65, 200, 175, 130);
//			shapeRenderer.end();
//			
//			
//			
//			batch.begin();
//			//display current checkpoint
//			yourBitmapFontName.setColor(1.0f, 1.15f, 1.30f, 1.0f);
//			
//			String tutorialLabel = "Objective";
//			getBitMapFont().draw(batch, tutorialLabel, 77, 319);
//			
//			yourBitmapFontName.setColor(1.0f, 1.0f, 1.2f, 1.0f);
//			String tutorialLabel2 = "Get to the Bus Stop on ";
//			getBitMapFont().draw(batch, tutorialLabel2, 77, 295);
//			
//			String tutorialLabel3 = "time!";
//			getBitMapFont().draw(batch, tutorialLabel3, 77, 276);
			
			batch.end();
		}
		
		
		
		batch.begin();
		
		// Game screen UI
		uiBackground.setPosition(uiBackgroundX, uiBackgroundY);
		uiBackground.setScale(uiBackgroundScale);
		uiBackground.draw(batch, 1);
		
		uiBusStop.setPosition(uiBusStopX, uiBusStopY);
		uiBusStop.setScale(uiBusStopScale);
		uiBusStop.draw(batch, 1);

		//display current checkpoint
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String checkPointLabel = "Stage: " + myWorld.getCurrentCheckPoint();
		getBitMapFont().draw(batch, checkPointLabel, 20, 390);
		
		
		// draw coins collected
		//yourBitmapFontName.setColor(1.0f, 1.4f, 1.4f, 1.0f);
		//int timeCollected = myWorld.getTimeDropsCollected();
		// time reduction calculation
		//int timeReduction = timeCollected; // 1 = 0.2 seconds
		//String timeBonusCollectedLabel = "Time Bonus: " + timeReduction;
		//getBitMapFont().draw(batch, timeBonusCollectedLabel, getCoinLabX(),
		 //       getCoinLabY());
		
		// draw distance travelled
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		//String distance = getDistanceStringMtrs(road.getDistanceTravelledMtrs());
		//getBitMapFont().draw(batch, distance, getDistLabX(), getDistLabY());

		// score
		String pointLabel = "Score: " + myWorld.generateScore(); //
		getBitMapFont().draw(batch, pointLabel, scoreLabelX, scoreLabelY);

		// High score so far
		String highScoreLabel = "High score: " + myWorld.getHighScore();
		getBitMapFont().draw(batch, highScoreLabel, highScoreLabelX, highScoreLabelY);
		
		// Display the time remaining until the bus stop.
		int timeLeft = myWorld.getBusStop().getRemainingTime();
		String timeLabel = Integer.toString(timeLeft);
		if (timeLeft < 10) {
			yourBitmapFontName.setColor(1.0f, 1.4f, 1.4f, 1.0f);
			getBitMapFont().draw(batch, timeLabel, posRemainingTimeLabX+5, posRemainingTimeLabY);
		} else if (timeLeft < 20) {
			getBitMapFont().draw(batch, timeLabel, posRemainingTimeLabX, posRemainingTimeLabY);
		} else {
			getBitMapFont().draw(batch, timeLabel, posRemainingTimeLabX+2, posRemainingTimeLabY);
		}
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if (myWorld.isRunning() &&
			timeLeft <= 3 &&
			DBL.isSoundOn() &&
			(runTime % 12 < 0.02 || runTime % 13 < 0.02 || runTime % 14 < 0.02)) {
			Gdx.app.log("runTime", String.valueOf(runTime));
			AssetLoader.countDownSound.play(1.0f);
		}
		

		batch.end();

		// draw pause menu
		Stage stage = getStage();
		stage.act();
		stage.draw();
		drawPauseButton(stage);
		drawSoundEffectButton(stage);
		
		if (!DBL.isSoundOn()) {
			drawOffBar(stage, 270, 0);
		}

		if (myWorld.isPaused()) {
			// batch.begin();
			// drawMenuBackground(AssetLoader.pauseMenuBgRegion);
			// batch.end();
			renderPauseMenu(stage, clock);
		} else if (myWorld.isGameOver()) {
			renderGameOverScreen(stage, clock);
		}
		
		if (myWorld.isConfirming()) {
			renderEndGameConfirmation();
		}

	}

	private void drawPauseButton(Stage stage) {
		stage.addActor(getPauseButton());
		// gameInterface.getStage().addActor(gameInterface.getPauseButton());
		getPauseButton().setPosition(0, 0); // TODO: should not hard code the
		                                    // position
		getPauseButton().setScale((float) 0.5);

		for (EventListener listener : getPauseButton().getListeners()) {
			getPauseButton().removeListener(listener);
		}

		getPauseButton().addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen pausebutton touchDown",
				        "pauseButton is touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen pausebutton touchUp",
				        "pauseButton is clicked");
				if (!myWorld.isGameOver())
					myWorld.pause();
			}
		});
	}
	
	private void drawSoundEffectButton(Stage stage) {
		final Image soundButton = soundEffectButton;
		final Image offBar2 = offBar;
		stage.addActor(soundButton);
		soundButton.setPosition(270, 0);
		soundButton.setScale(0.5f);
		
		for (EventListener listener : soundButton.getListeners()) {
			soundButton.removeListener(listener);
		}

		soundButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen soundEffectButton touchDown",
				        "soundEffectButton is touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				if (DBL.isSoundOn()) {
					DBL.turnOffSound();
				} else {
					DBL.turnOnSound();					
				}
			}
		});
	}
	
	private void drawOffBar(Stage stage, int x, int y) {
		stage.addActor(offBar);
		offBar.setPosition(270, 0);
		offBar.setScale(0.5f);
		
		for (EventListener listener : offBar.getListeners()) {
			offBar.removeListener(listener);
		}

		offBar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen offBar touchDown",
				        "soundEffectButton is touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				DBL.turnOnSound();
				offBar.remove();
			}
		});
	}
	
	/**
	 * We check the time
	 */
	private void checkTimer() {
		if (myWorld.getBusStop().getRemainingTime() < 1) {
			myWorld.endGame();
		}
	}

	private void renderPauseMenu(Stage stage, final Clock clock) {
//		batch.begin();
//		Sprite sprite = new Sprite(AssetLoader.pauseMenuBackground);
//		sprite.setScale(0.5f);
//		sprite.draw(batch);
////		batch.draw(AssetLoader.pauseMenuBackground, 0, 0);
//		batch.end();
		
//		final Image bg = new Image(AssetLoader.pauseMenuBackground);
		
		
		pauseMenuBg.setScale(0.5f);
		pauseMenuBg.setPosition(0, 0);
		stage.addActor(pauseMenuBg);
		
		batch.begin();
		//display current checkpoint
		
        // Need to change the position and size of the score later on
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String finalScoreLabel = ""+ myWorld.generateScore();
		getBitMapFont().draw(batch, finalScoreLabel, 140, 295);

		batch.end();

		final Image resumeButton = getResumeButton();
		final Image endGameButton = getEndGameButton();
		stage.addActor(resumeButton);
		stage.addActor(endGameButton);
		clock.stop();

		resumeButton.setPosition(105, 167);
		resumeButton.setScale((float) 0.5);
		endGameButton.setPosition(83, 79);
		endGameButton.setScale((float) 0.5);

		resumeButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				// can only click on resume if not on end game confirmation
				// state
				pauseMenuBg.remove();
				yesButton.remove();
				noButton.remove();
				resumeButton.remove();
				endGameButton.remove();
				restartButton.remove();
				
				myWorld.start();
				clock.start();
				
			}
		});

		// restart button
		final Image restartButton = getRestartButton();
		stage.addActor(restartButton);
		restartButton.setPosition(86, 121);
		restartButton.setScale((float) 0.5);
		for (EventListener listener : restartButton.getListeners()) {
			restartButton.removeListener(listener);
		}
		restartButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen restartbutton touchDown",
				        "restartButton is touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen restartbutton touchUp",
				        "restartbutton is clicked");
				renderEndGameConfirmation();
				myWorld.confirmEndGame();
				myWorld.setIntention(Intention.RESTART);
			}
		});

		
		for (EventListener listener : endGameButton.getListeners()) {
			endGameButton.removeListener(listener);
		}
		endGameButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				endGameConfirmationfromPage = "pauseMenu";
				renderEndGameConfirmation();
				myWorld.confirmEndGame();
				myWorld.setIntention(Intention.BACK_TO_MENU);
				Gdx.app.log("EndGameButton", "click");
			}
		});
	}

	private void renderEndGameConfirmation() {

		final Image yesButton = getYesButton();
		final Image noButton = getNoButton();

//		batch.begin();
//		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//		String confirmMsg = "Are you sure you want to quit the game?";
//		getBitMapFont().draw(batch, confirmMsg, 150, 150);
//		batch.end();

//		final Image bg = new Image(AssetLoader.restartMenuBackground);
		
		if (myWorld.isRestart()) {
			restartMenuBg.setScale(0.5f);
			restartMenuBg.setPosition(0, 0);
			stage.addActor(restartMenuBg);			
		} else if (myWorld.isBackToMenu()) {
			mainMenuBg.setScale(0.5f);
			mainMenuBg.setPosition(0, 0);
			stage.addActor(mainMenuBg);		
		}		
		
			
		noButton.setPosition(163, 200);
		noButton.setScale((float) 0.5);
		yesButton.setScale((float) 0.5);
		yesButton.setPosition(83, 200);
		
		stage.addActor(yesButton);
		stage.addActor(noButton);

		for (EventListener listener : yesButton.getListeners()) {
			yesButton.removeListener(listener);
		}

		yesButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("YesButton", "click");
				if (myWorld.isBackToMenu()) {
					currentScreen.switchToMenu();
				} else if (myWorld.isRestart()) {
					currentScreen.switchNewScreenSet();
				}
				restartMenuBg.remove();
				AssetLoader.gameOverSound.dispose();
				AssetLoader.gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sound-effects/game-over.wav"));
			}
		});

		for (EventListener listener : noButton.getListeners()) {
			noButton.removeListener(listener);
		}

		noButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("NoButton", "click");
				restartMenuBg.remove();
				mainMenuBg.remove();
				yesButton.remove();
				noButton.remove();
				if (myWorld.isEndGameConfirming()) {
					renderGameOverScreen(stage, clock);
				} else if (myWorld.isPausedConfirming()){
					renderPauseMenu(stage, clock);
				}
				myWorld.exitEndGameConfirmation();
			}
		});
	}

	private void renderGameOverScreen(Stage stage, Clock clock) {
		clock.stop();
		
		if (myWorld.isGameOverCollision()){
			crashedMenuBg.setScale(0.5f);
			crashedMenuBg.setPosition(0, 0);
			stage.addActor(crashedMenuBg);
		} else { 
			timeoutMenuBg.setScale(0.5f);
			timeoutMenuBg.setPosition(0, 0);
			stage.addActor(timeoutMenuBg);
		}
		
		// display score
		//int score = myWorld.generateScore();
		// distance remaining to next bus stop

		//Print objective/ tutorial
//		String gameOverTypeLabel;
//		if (myWorld.isGameOverCollision()){
//			gameOverTypeLabel = "Crashed!";
//		} else {
//			gameOverTypeLabel = "Timeout!";
//		}
//		shapeRenderer.begin(ShapeType.Line);
//		shapeRenderer.setColor(Color.WHITE);
//		shapeRenderer.rect(65, 170, 175, 160);
//		shapeRenderer.end();
		
		batch.begin();
		//display current checkpoint
//		yourBitmapFontName.setColor(1.0f, 1.15f, 1.30f, 1.0f);
//
//		getBitMapFont().draw(batch, gameOverTypeLabel, 77, 319);
		
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String finalScoreLabel = "" + myWorld.generateScore();
		getBitMapFont().draw(batch, finalScoreLabel, 120, 241);

		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String highestScore = "" + myWorld.getHighScore();
		getBitMapFont().draw(batch, highestScore, 120, 200);
		
		batch.end();		

		// restart button
		final Image restartButton = getRestartButton();
		stage.addActor(restartButton);
		restartButton.setPosition(86, 121);
		restartButton.setScale((float) 0.5);
		for (EventListener listener : restartButton.getListeners()) {
			restartButton.removeListener(listener);
		}
		restartButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen restartbutton touchDown",
				        "restartButton is touchDown");
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.log("GameScreen restartbutton touchUp",
				        "restartbutton is clicked");
				AssetLoader.gameOverSound.dispose();
				currentScreen.switchNewScreenSet();
				AssetLoader.gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sound-effects/game-over.wav"));
			}
		});

		// game end button
//		final Image endGameButton = getEndGameButton();
		stage.addActor(endGameButton);
		endGameButton.setPosition(84, 79);
		endGameButton.setScale((float) 0.5);

		for (EventListener listener : endGameButton.getListeners()) {
			endGameButton.removeListener(listener);
		}
		endGameButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
//				resumeButton.remove();
//				endGameButton.remove();
				endGameConfirmationfromPage = "gameover";
				currentScreen.switchToMenu();
//				renderEndGameConfirmation();
//				myWorld.confirmEndGame();
//				myWorld.setIntention(Intention.BACK_TO_MENU);
				Gdx.app.log("EndGameButton", "click");
			}
		});
	}

	private void drawMenuBackground(TextureRegion background) {
		sprite = new Sprite(background);
		sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
	}

	public Clock getClock() {
		return clock;
	}

	public void stopClock() {
		clock.stop();
	}

	public Stage getStage() {
		return stage;
	}

	public Image getPauseButton() {
		return pauseButton;
	}

	public Image getResumeButton() {
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

	public Image getRestartButton() {
		return restartButton;
	}

	
	
	public String getDistanceString(float distance) {
		return "Distance: " + distance;
	}

	public String getDistanceStringMtrs(int distance) {
		return "Distance: " + distance + " m";
	}

	public BitmapFont getBitMapFont() {
		return yourBitmapFontName;
	}

	/*
	public float getDistLabY() {
		return posDistLabY;
	}

	public float getDistLabX() {
		return posDistLabX;
	}
	*/

	public float getCoinLabY() {
		return posCoinLabY;
	}

	public float getCoinLabX() {
		return posCoinLabX;
	}

	public void dispose() {
		// batch.dispose();

		// TODO: disposal crashes java for some reason... cannot find solution
		// so we aren't disposing for now.
		// However, interesting there seems to be no memory leaks which seem to
		// indicate that
		// the stage is being disposed automatically... very strange.
		// stage.dispose();
		yourBitmapFontName.dispose();
		//shapeRenderer.dispose();
		
	}

	public void reset() {
		clock = new Clock();
		Road.resetDistanceTravelled();
	}

}

