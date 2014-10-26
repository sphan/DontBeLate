package com.comp4920.dbl.screens;

import javax.swing.SpringLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.helpers.AssetLoader;

public class SplashScreen implements Screen {
	//volume
	private static final float SOUND_OPTIONS_VOLUME = 0.2f;
	private static final float MENU_BUTTONS_VOLUME = 0.15f;
	
	private Image startButton;
	private Image quitButton;
	private Image instructionButton;
	private Image soundButton;
	private Image musicButton;
	private Image offBar;
	private Image offBar2;
	
	private DBL myGame;
	private Stage stage;
	private final int width = 600;
	private final int height = 800;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private boolean musicSetOff = false;

	public SplashScreen(DBL g) {
		AssetLoader.menuMusic.stop();
		AssetLoader.menuMusic.play();
		AssetLoader.menuMusic.setLooping(true);
		Gdx.app.log("SplashScreen", "created");
		myGame = g;
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false, width, height);
		stage = new Stage(new FitViewport(width, height, camera));
		startButton = new Image(AssetLoader.startGameButton);
		quitButton = new Image(AssetLoader.quitButton);
		instructionButton = new Image(AssetLoader.instructionButton);
		soundButton = new Image(AssetLoader.soundEffectButton);
		musicButton = new Image(AssetLoader.musicButton);
		offBar = new Image(AssetLoader.offBar);
		offBar2 = new Image(AssetLoader.offBar);
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(AssetLoader.startMenuBackground, 0, 0);
		batch.end();
		stage.act();
		stage.draw();

		//Draw the "off" bar is mute is in effect
		if (!DBL.isSoundOn()) {
			drawOffBar(stage, 490, 150);
		}


		if (!DBL.isMusicOn()) {
			if (musicSetOff == false){
				musicSetOff = true;
				AssetLoader.menuMusic.pause();
			}
			drawOffBar2(stage, 440, 90);
			
		}

		if (DBL.isMusicOn() && musicSetOff == true) {
			musicSetOff = false;
			AssetLoader.menuMusic.play();
		}
		
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height);
	}

	@Override
	public void show() {
		if (DBL.isMusicOn() && !AssetLoader.menuMusic.isPlaying()) AssetLoader.menuMusic.play();
		
		stage.addActor(startButton);
		stage.addActor(quitButton);
		stage.addActor(instructionButton);
		stage.addActor(soundButton);
		stage.addActor(musicButton);
		
		startButton.setScale(0.9f);
		startButton.setPosition(70, 240);
		instructionButton.setScale(0.9f);
		instructionButton.setPosition(65, 160);
		quitButton.setScale(0.9f);
		quitButton.setPosition(75, 90);
		soundButton.setPosition(490, 150);
		musicButton.setPosition(440, 90);

		Gdx.input.setInputProcessor(stage);
		
		for (EventListener listener : startButton.getListeners()) {
			startButton.removeListener(listener);
		}
		
		startButton.addListener(new InputListener() {
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
				myGame.setScreen(GameScreen.getInstance(myGame));
			}
		});

		for (EventListener listener : instructionButton.getListeners()) {
			instructionButton.removeListener(listener);
		}
		
		instructionButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				myGame.setScreen(InstructionScreen.getInstance(myGame));
				if(DBL.isSoundOn())
					AssetLoader.clickButton.play(MENU_BUTTONS_VOLUME);
			}
		});

		for (EventListener listener : quitButton.getListeners()) {
			quitButton.removeListener(listener);
		}
		
		quitButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				Gdx.app.exit();
			}
		});

		for (EventListener listener : soundButton.getListeners()) {
			soundButton.removeListener(listener);
		}
		
		soundButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
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
		
		for (EventListener listener : musicButton.getListeners()) {
			musicButton.removeListener(listener);
		}
		
		musicButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
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

	private void drawOffBar(Stage stage, int x, int y) {
		stage.addActor(offBar);
		offBar.setPosition(x, y);

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

	private void drawOffBar2(Stage stage, int x, int y) {
		stage.addActor(offBar2);
		offBar2.setPosition(x, y);

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
				if(DBL.isSoundOn())
					AssetLoader.clickSoundOptions.play(SOUND_OPTIONS_VOLUME);
				offBar2.remove();
			}
		});
	}

	
	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * keep the singleton instance
	 */
	private static SplashScreen _this = null;

	/**
	 * return singleton instance
	 */
	public static SplashScreen getInstance(DBL g) {
		if (_this == null) {
			_this = new SplashScreen(g);
		}
		return _this;
	}
}
