package com.comp4920.dbl;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.comp4920.dbl.helpers.AssetLoader;
import com.comp4920.dbl.screens.SplashScreen;

public class DBL extends Game /*extends ApplicationAdapter */{
	
	public enum SoundState {
		SOUND_ON, SOUND_OFF;
	}
	
	public enum MusicState {
		MUSIC_ON, MUSIC_OFF
	}
	
	private static SoundState soundState;
	private static MusicState musicState;
	
	@Override
	public void create() {
		Gdx.app.log("DBLGame", "created");
		AssetLoader.load();
		soundState = SoundState.SOUND_ON;
		musicState = MusicState.MUSIC_ON;
		setScreen(SplashScreen.getInstance(this));
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
	
	public SoundState getSoundState() {
		return soundState;
	}
	
	public MusicState getMusicState() {
		return musicState;
	}
	
	public static void turnOnSound() {
		soundState = SoundState.SOUND_ON;
	}
	
	public static void turnOnMusic() {
		musicState = MusicState.MUSIC_ON;
	}
	
	public static void turnOffSound() {
		soundState = SoundState.SOUND_OFF;
	}
	
	public static void turnOffMusic() {
		musicState = MusicState.MUSIC_OFF;
	}
	
	public static boolean isSoundOn() {
		return soundState == SoundState.SOUND_ON;
	}
	
	public static boolean isMusicOn() {
		return musicState == MusicState.MUSIC_ON;
	}
	
//	SpriteBatch batch;
//	Texture img;
	
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}
//
//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}
}
