package com.comp4920.dbl.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.comp4920.dbl.gameobjects.Road;
import com.comp4920.dbl.gameworld.GameInterfaceRenderer;
import com.comp4920.dbl.gameworld.GameWorldRenderer;
import com.comp4920.dbl.gameworld.GameWorld;
import com.comp4920.dbl.helpers.InputHandler;

public class GameScreen implements Screen {
	private Game myGame;
	private GameWorld world;
	private GameWorldRenderer gameRenderer;
	private GameInterfaceRenderer gameInterfaceRenderer;
	private OrthographicCamera camera;
	private Stage stage;
	private float runTime = 0;
	private InputHandler busInputHandler;
	private InputMultiplexer inputMulti;
	private boolean switchToNewScreen;
	private boolean switchToMenu;
	
	private static final int VIRTUAL_WIDTH = 600;
    private static final int VIRTUAL_HEIGHT = 800;
    private static final float ASPECT_RATIO =
        (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    private Rectangle viewport;
    
	public GameScreen(Game g) {
		Stage stage = new Stage();

		switchToNewScreen = false;
		switchToMenu = false;
		Gdx.app.log("GameScreen", "created");
		float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameHeight = 400;
        float gameWidth = screenWidth / (screenHeight / gameHeight);

        int midPointX = (int) (gameWidth / 2);
        myGame = g;
        
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		camera.setToOrtho(false, 300, 400);

		
		world = new GameWorld(midPointX);
		gameInterfaceRenderer = new GameInterfaceRenderer(this, world, camera,(int) gameWidth, midPointX);
		gameRenderer = new GameWorldRenderer(world, gameInterfaceRenderer.getStage(), camera, (int) gameWidth, midPointX);
		busInputHandler = new InputHandler(world);
		
		inputMulti = new InputMultiplexer();
		inputMulti.addProcessor(gameInterfaceRenderer.getStage());
		inputMulti.addProcessor(busInputHandler);
		Gdx.input.setInputProcessor(inputMulti);
		
	}

	@Override
	public void render(float delta) {
//		// Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
//        Gdx.gl.glClearColor(204/255.0f, 204/255.0f, 204/255.0f, 1f);
//        // Fills the screen with the selected color
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        // Covert Frame rate to String, print it
//        Gdx.app.log("GameScreen FPS", (1/delta) + "");
		
		//Check if we should dispose and switch to a new screen
		//had to do it this way as disposing inside button listener causes an error
		if (switchToNewScreen){
			switchNewScreen();
		}
		
//		if (switchToMenu) {
//			switchToMenu();
//		}
		
		System.out.println(Gdx.graphics.getHeight());
	       // update camera
        camera.update();

        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                          (int) viewport.width, (int) viewport.height);

		runTime += delta;
		world.update(delta, busInputHandler);
		
		gameRenderer.render(runTime); //game world
		gameInterfaceRenderer.render(runTime);//game interface and menus
		
	}

	@Override
	public void resize(int width, int height) {
//		Gdx.app.log("GameScreen", "resizing");
		gameInterfaceRenderer.getStage().getViewport().update(width, height, true);
		 // calculate new viewport
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);
        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }

        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
    
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show called");
	}

	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "pause called");  
	}

	@Override
	public void resume() {
		Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	public void dispose() { 
		gameRenderer.dispose();
		gameInterfaceRenderer.dispose();
	}
	
	public void switchNewScreenSet(){
		switchToNewScreen = true;
	}
	
	public void switchNewScreen(){
		Road.resetDistanceTravelled();
		dispose();
		myGame.setScreen(new GameScreen(myGame));
	}

	public void switchToMenuSet() {
		switchToMenu = true;
	}
	
	public void switchToMenu() {
		Road.resetDistanceTravelled();
//		dispose();
		myGame.setScreen(new SplashScreen(myGame));
	}
}
