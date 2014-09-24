package com.comp4920.dbl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comp4920.dbl.DBL;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Don't Be Late!";
		config.width = 600;
		config.height = 800;
		new LwjglApplication(new DBL(), config);
	}
}
