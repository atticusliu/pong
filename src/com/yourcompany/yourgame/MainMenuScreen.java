package com.yourcompany.yourgame;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;

public class MainMenuScreen extends Screen {
	Paint p1;

	public MainMenuScreen(Game game) {
		super(game);
		p1 = new Paint();
		p1.setTextSize(50);
		p1.setTextAlign(Align.CENTER);
		p1.setAntiAlias(true);
		p1.setColor(Color.CYAN);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchevent = game.getInput().getTouchEvents();
		if (touchevent.size() > 0) {
			game.setScreen(new GameScreen(game));

		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawString("Welcome to Pong! Press screen to continue", 640, 360, p1);
		g.drawString("Use your finger to control the leftmost paddle", 640,
				620, p1);
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
		// Display "Exit Game?" Box

	}
}