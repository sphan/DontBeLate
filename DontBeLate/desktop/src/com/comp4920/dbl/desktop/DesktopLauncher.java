package com.comp4920.dbl.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comp4920.dbl.DBL;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenDimenstion = Toolkit.getDefaultToolkit().getScreenSize();
		config.title = "Don't Be Late!";
		config.width = 600;
		config.height = screenDimenstion.height;
		config.resizable = false;
		new LwjglApplication(new DBL(), config);
	}
}
