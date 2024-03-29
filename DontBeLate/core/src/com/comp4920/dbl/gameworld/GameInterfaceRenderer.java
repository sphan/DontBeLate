package com.comp4920.dbl.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.gameobjects.Clock;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.gameworld.GameWorld.Intention;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.GameScreen;


public class GameInterfaceRenderer {
	//volume
	private static final float SOUND_OPTIONS_VOLUME = 0.2f; 
	private static final float MENU_BUTTONS_VOLUME = 0.15f; 
	
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
	private float posRemainingTimeLabX = 295/2 - 6;
	private float posRemainingTimeLabY = 388;

	// high score
	private float highScoreLabelX = 177;
	private float highScoreLabelY =  396;
	
	// score
	private float scoreLabelX = highScoreLabelX + 17;
	private float scoreLabelY = highScoreLabelY - 15;
	
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
	private Image musicEffectButton;
	
	// header and menu background
	private Image header;
	private Image pauseMenuBg;
	private Image restartMenuBg;
	private Image mainMenuBg;
	private Image crashedMenuBg;
	private Image timeoutMenuBg;
	private Image objectiveBg;
	
	private Image offBar;
	private Image offBar2;

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

	//counter for timer sound
	private int counter; 
	
	// helper attribute, for rendering exit confirmation page
	String endGameConfirmationfromPage = "";

	public GameInterfaceRenderer(GameScreen screen, GameWorld myWorld,
	        OrthographicCamera camera, int gameWidth, int midPointX) {
		
		counter = 9;
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
		musicEffectButton = new Image(AssetLoader.musicButton);
		offBar = new Image(AssetLoader.offBar);
		offBar2 = new Image(AssetLoader.offBar);
		
		header = new Image(AssetLoader.header);
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
		batch.begin();
		
		if (runTime < 3.5){
			objectiveBg.setScale(0.5f);
			objectiveBg.setPosition(0, -6);
			objectiveBg.draw(batch, 1);
		}

		// draw header
		header.setPosition(0,0);
		header.setScale(0.5f);
		header.draw(batch, 1);		
	
		//display current checkpoint
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		getBitMapFont().scale((float) 0.05);
		String checkPointLabel = "Stage: " + myWorld.getCurrentCheckPoint();
		getBitMapFont().draw(batch, checkPointLabel, 22.5f, 390);
		getBitMapFont().scale((float) -0.05);
		// score
		String pointLabel = "Score: " + myWorld.generateScore(); //
		getBitMapFont().draw(batch, pointLabel, scoreLabelX, scoreLabelY);
		getBitMapFont().scale((float) -0.07);
		// High score so far
		String highScoreLabel = "High Score: " + myWorld.getHighScore();
		getBitMapFont().draw(batch, highScoreLabel, highScoreLabelX, highScoreLabelY);
		getBitMapFont().scale((float) 0.07);
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
			timeLeft < 10 && 
			DBL.isSoundOn()) { 
			
			if (timeLeft == counter){ 
				counter--; 
				AssetLoader.countDownSound.play(1.0f); 
			}
			
			Gdx.app.log("runTime", String.valueOf(runTime));
			//AssetLoader.countDownSound.play(1.0f);
		}
		
		if (counter <= 0 || timeLeft > 9){ 
			counter = 9; 
		}
		
		if ((timeLeft < 10 && timeLeft > counter+1) ){ 
			counter = timeLeft; 
		}
		
		batch.end();

		// draw pause menu
		Stage stage = getStage();
		stage.act();
		stage.draw();
		
		drawPauseButton(stage);
		drawSoundEffectButton(stage);
		drawMusicButton(stage);
		
		if (!DBL.isSoundOn()) {
			drawSoundOffBar(stage, 270, 0);
		}
		
		if (!DBL.isMusicOn()) {
			drawSoundOffBar2(stage, 270, 30);
		}

		if (myWorld.isPaused()) {
			renderPauseMenu(stage, clock);
		} else if (myWorld.isGameOver()) {
			renderGameOverScreen(stage, clock);
		}
		
		if (myWorld.isConfirming()) {
			renderEndGameConfirmation();
		}
		
		drawSoundEffectButton(stage);
		drawMusicButton(stage);
	
		if (!DBL.isSoundOn()) {
			drawSoundOffBar(stage, 270, 0);
		}
		
		if (!DBL.isMusicOn()) {
			drawSoundOffBar2(stage, 270, 30);
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
				
				AssetLoader.gameMusic.pause();
				
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME);
				
				if (!myWorld.isGameOver())
					myWorld.pause();
				
			}
		});
	}
	
	private void drawSoundEffectButton(Stage stage) {
		final Image soundButton = soundEffectButton;
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
				
				if(DBL.isSoundOn())
					AssetLoader.clickSoundOptions.play(SOUND_OPTIONS_VOLUME);
			}
		});
	}
	
	private void drawMusicButton(Stage stage) {
		final Image musicButton = musicEffectButton;
		stage.addActor(musicButton);
		musicButton.setPosition(270, 30);
		musicButton.setScale(0.5f);		
		
		for (EventListener listener : musicButton.getListeners()) {
			musicButton.removeListener(listener);
		}

		musicButton.addListener(new InputListener() {
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
				if (DBL.isMusicOn()) {
					DBL.turnOffMusic();
				} else {
					DBL.turnOnMusic();					
				}
				if(DBL.isSoundOn())
					AssetLoader.clickSoundOptions.play(SOUND_OPTIONS_VOLUME); 
			}
		});
	}
	
	
	private void drawSoundOffBar(Stage stage, int x, int y) {
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
				if(DBL.isSoundOn())
					AssetLoader.clickSoundOptions.play(SOUND_OPTIONS_VOLUME); 
				
				offBar.remove();
			}
		});
	}
	
	private void drawSoundOffBar2 (Stage stage, int x, int y) {
		stage.addActor(offBar2);
		offBar2.setPosition(x, y);
		offBar2.setScale(0.5f);
		
		for (EventListener listener : offBar2.getListeners()) {
			offBar2.removeListener(listener);
		}

		offBar2.addListener(new InputListener() {
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
				DBL.turnOnMusic();
				offBar2.remove();
				if(DBL.isSoundOn())
					AssetLoader.clickSoundOptions.play(SOUND_OPTIONS_VOLUME); 
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
		
		// digit of the score
		int length = finalScoreLabel.length()-1;
		getBitMapFont().draw(batch, finalScoreLabel, 148-length*4, 295);

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
				
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
				
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

				if(DBL.isMusicOn())
					AssetLoader.gameMusic.play();
				
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
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
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
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
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
				
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
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
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
				
				myWorld.exitEndGameConfirmation();
			}
		});
	}

	private void renderGameOverScreen(Stage stage, Clock clock) {
		clock.stop();
		AssetLoader.gameMusic.stop();
		
		if (myWorld.isGameOverCollision()){
			crashedMenuBg.setScale(0.5f);
			crashedMenuBg.setPosition(0, 0);
			stage.addActor(crashedMenuBg);
		} else { 
			timeoutMenuBg.setScale(0.5f);
			timeoutMenuBg.setPosition(0, 0);
			stage.addActor(timeoutMenuBg);
		}
				
		batch.begin();
		//display current checkpoint	
		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String finalScoreLabel = "" + myWorld.generateScore();
		int length = finalScoreLabel.length()-1;
		getBitMapFont().draw(batch, finalScoreLabel, 148-length*4, 241);

		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		String highestScore = "" + myWorld.getHighScore();
		int length2 = highestScore.length()-1;
		getBitMapFont().draw(batch, highestScore, 148-length2*4, 200);
		
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
				
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
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
				
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME); 
			}
		});
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

