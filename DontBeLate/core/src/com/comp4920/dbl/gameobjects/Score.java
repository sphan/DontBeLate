package com.comp4920.dbl.gameobjects;

import com.comp4920.dbl.helpers.DropsHandler.DropType;

public class Score {

	private int score;
	private static final int CHECKPOINT_SCORE = 1000;
	private static final int OPAL_SCORE = 500;
	
	public Score() {
		score = 0;
	}
	
	// Only Opal cards add points, not time drops,
	// so we can just add 500 here.
	public void increase() {
		score += OPAL_SCORE;
	}
	
	public void checkpoint(int checkpointNum) {
		score += CHECKPOINT_SCORE * checkpointNum;
	}
	
	public int getScore() {
		return score;
	}
	
}
