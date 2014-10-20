package com.comp4920.dbl.gameobjects;

import java.io.Serializable;

import com.comp4920.dbl.helpers.DropsHandler.DropType;

public class Score {

	private int score;
	private static final int CHECKPOINT_SCORE = 1000;
	private static final int OPAL_SCORE = 200;
	private static final int COIN_SCORE = 500;
	
	public Score() {
		score = 0;
	}
	
	// Method useful for testing
	public Score(int score) {
		this.score = score;
	}
	
	// Only Opal cards add points, not time drops,
	// so we can just add 500 here.
	public void increase() {
		score += OPAL_SCORE;
	}
	
	public void extraIncrease(){
		score += COIN_SCORE;
	}
	
	public void checkpoint(int checkpointNum) {
		score += CHECKPOINT_SCORE * checkpointNum;
	}
	
	public int getScore() {
		return score;
	}
	
}
