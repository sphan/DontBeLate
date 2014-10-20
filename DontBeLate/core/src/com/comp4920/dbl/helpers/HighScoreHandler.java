package com.comp4920.dbl.helpers;

import com.comp4920.dbl.gameobjects.Score;

public class HighScoreHandler {

	private com.badlogic.gdx.Preferences prefs;
	
	public HighScoreHandler() {
		prefs = AssetLoader.prefs;
	}
	
	/*
	 * This method inspects the submitted score.
	 * If it is higher than the current high score it
	 * replaces it and returns true, otherwise it returns false.
	 */
	public boolean submitScore(Score score) {
		if (score.getScore() > prefs.getInteger("highScore")) {
			prefs.putInteger("highScore",  score.getScore());
			prefs.flush();
			return true;
		}
		return false;
	}
	
	public int getHighScore() {
		return prefs.getInteger("highScore");
	}
}
