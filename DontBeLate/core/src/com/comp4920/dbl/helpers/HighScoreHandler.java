package com.comp4920.dbl.helpers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.comp4920.dbl.gameobjects.Score;

public class HighScoreHandler {

	private ArrayList<Score> highScores;
	
	// File with highscores
	// We use a binary file to prevent users from easily modifying the highscores. 
	private static final String HIGHSCORE_FILE = "scores.dat";
	
    // InputStream and outputStream for the file
    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighScoreHandler() {
    	highScores = new ArrayList<Score>();
    }
    
    public ArrayList<Score> getHighScores() {
    	loadHighScoreFile();
    	sort();
    	return highScores;
    }
    
    // TODO: add support for names attached to scores.
    public void addHighScore(int score) {
    	loadHighScoreFile();
    	addHighScore(score);
    }
    
    private void sort() {
    	// sort the highscore file here
    }
    
    private void loadHighScoreFile() {
    	// read high scores from the file here
    }
}
