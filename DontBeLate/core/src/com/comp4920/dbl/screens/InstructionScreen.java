package com.comp4920.dbl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.comp4920.dbl.DBL;
import com.comp4920.dbl.helpers.AssetLoader;

public class InstructionScreen implements Screen {

	private Image mainMenuButton;
	private Image replayButton;
	private Image skipButton;

	private DBL myGame;
	private Stage stage;
	private final int width = 600;
	private final int height = 800;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	
	// animation for instruction
	private Animation           instructionAnimation;      // #3
    private Texture             frame;      // #4
    private TextureRegion[]         instructionFrames;     // #5
    TextureRegion           currentFrame;       // #7
	float stateTime;
	float wasteTime = 0f;
	int index = 0;
	
	

	public InstructionScreen(DBL g) {
		Gdx.app.log("InstructionScreen", "created");
		myGame = g;
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false, width, height);
		stage = new Stage(new FitViewport(width, height, camera));
		mainMenuButton = new Image(AssetLoader.endGameButton);
		replayButton = new Image(AssetLoader.replayButton);
		skipButton = new Image(AssetLoader.skipButton);
		batch = new SpriteBatch();
		createAnimation();
	}


	public void createAnimation(){
		int count = 0;
		String s, fileName;
		instructionFrames = new TextureRegion[370];
		
		while (count < 370) {
			if (count < 10) {
				s = "000" + count;
			} else if (count < 100) {
				s = "00" + count;
			} else {
				s = "0" + count;
			} 
			fileName = "instruction-frames/" + s + ".gif";			
			
			frame = new Texture(Gdx.files.internal(fileName));
			frame.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			currentFrame = new TextureRegion(frame, 0, 0,
					frame.getWidth(), frame.getHeight());
			
			instructionFrames[count] = currentFrame;
			
			count ++;
		}
		instructionAnimation = new Animation(0.083f, instructionFrames);
		stateTime = 0f;				
	}
	
	public void renderAnimation() {
		
		if (index == 0) {
			Gdx.graphics.getDeltaTime();
			index ++;
		} else {
			stateTime += Gdx.graphics.getDeltaTime();
		}
		
		//System.out.println(stateTime);
		currentFrame = instructionAnimation.getKeyFrame(stateTime,true);			
		batch.begin();
		batch.draw(currentFrame,0,0,camera.viewportWidth, camera.viewportHeight);
		batch.end();		
	}
	
	private void drawSkipButton(Stage stage) {
		stage.addActor(skipButton);
		skipButton.setPosition(0, 5);
		//skipButton.setScale(0.5f);
		
		Gdx.input.setInputProcessor(stage);

		skipButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				stateTime = 31;			
			}
		});
	}
	
	private void drawOtherButtons(Stage stage) {
		skipButton.remove();
		stage.addActor(mainMenuButton);
		stage.addActor(replayButton);

		replayButton.setPosition(220,450);
		mainMenuButton.setPosition(170, 350);
		
		Gdx.input.setInputProcessor(stage);

		mainMenuButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
				myGame.setScreen(SplashScreen.getInstance(myGame));
			}
		});
		
		replayButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
			        int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
			        int pointer, int button) {
					stateTime = 0;
					index = 0;
					replayButton.remove();
					mainMenuButton.remove();
			}
		});
		
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		if (stateTime < 30.7) {
			renderAnimation();
			stage.act();
			stage.draw();
			drawSkipButton(stage);
		} else {		
			batch.begin();
			batch.draw(AssetLoader.instructionImage, 0, 0);
			batch.end();
			stage.act();
			stage.draw();
			drawOtherButtons(stage);
		}
	}	
		

	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height);

	}

	@Override
	public void show() {
						
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
	private static InstructionScreen _this = null;

	/**
	 * return singleton instance
	 */
	public static InstructionScreen getInstance(DBL g) {
		if (_this == null) {
			_this = new InstructionScreen(g);
		}
		return _this;
	}
}
