package com.yourcompany.yourgame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.kilobolt.framework.Audio;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Graphics.ImageFormat;

public class LoadingScreen extends Screen {

	Paint p;

	public LoadingScreen(Game game) {
		super(game);
		p = new Paint();
		p.setTextSize(20);
		p.setTextAlign(Align.CENTER);
		p.setAntiAlias(true);
		p.setColor(Color.CYAN);
		
	
		
		//picture
		Graphics g1 = game.getGraphics();
		Assets.menu = g1.newImage("menu.png", ImageFormat.ARGB4444);

	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		// Assets.menu = g.newImage("menu.jpg", ImageFormat.RGB565);
		// Assets.click = game.getAudio().createSound("explode.ogg");

		game.setScreen(new MainMenuScreen(game));

	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawString("Loading...Daniel and Alex's baby", 600, 400, p);

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}
