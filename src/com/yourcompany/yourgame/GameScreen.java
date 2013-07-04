package com.yourcompany.yourgame;

import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.graphics.*;
import android.media.MediaPlayer;
import android.view.View;

import com.kilobolt.framework.Audio;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Sound;

public class GameScreen extends Screen {
	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;
	int x;
	int y;
	int x1;
	int x2;
	// left paddle is user paddle
	int userPaddleY;
	int paddleY;
	int xInc;
	int yInc;
	int paddleSpeed;

	int livesLeft = 1;
	Paint paint;
	Paint paint1;

	public GameScreen(Game game) {
		super(game);

		// Initialize game objects here

		// Defining a paint object
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);

		// for pause button
		// for game over
		paint1 = new Paint();
		paint1.setTextSize(40);
		paint1.setTextAlign(Paint.Align.CENTER);
		paint1.setAntiAlias(true);
		paint1.setColor(Color.WHITE);

		// x and y are rectangle numbers, and
		x = 100;
		y = 300;

		// x1 and x2 are for paddles
		x1 = 100;
		x2 = 1180;
		userPaddleY = 50;
		paddleY = 50;

		// xInc and yInc are speed settings
		xInc = 13;
		yInc = 8;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// We have four separate update methods in this example.
		// Depending on the state of the game, we call different update methods.
		// Refer to Unit 3's code. We did a similar thing without separating the
		// update methods.

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	// the arrayList touchEvents updates hella fast, so the size isn't
	// cumulative
	private void updateReady(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 0) {
			// changes state from Ready to Running
			state = GameState.Running;
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		// sound object is created
		Audio aud = game.getAudio();
		Assets.addu = aud.createSound("addu.mp3");

		if (touchEvents.size() > 0)
			userPaddleY = (touchEvents.get(0).y - 75);
		// move left and right
		x += xInc;
		y += yInc;

		// moving paddle
		paddleY += paddleSpeed;
		if (paddleY <= 0 || paddleY >= 650)
			paddleSpeed = -paddleSpeed;

		// hit the top or bottom wall
		if (y <= 0 || y >= 662) {
			yInc = -yInc;
		}

		// hit the left or right wall

		// collision with left paddle
		if (x == x1 && (y >= userPaddleY && y <= (150 + userPaddleY))) {
			xInc = -xInc;
			
			//THIS IS A HUGE PROBLEM
			//THIS PRODUCES LAG AND DOESN'T PLAY THE SOUND
			Assets.addu.play(1f);

		}
		// constant collision with right, as in AI never loses
		paddleY = y - 75;
		// only off right wall now, it's impossible for the AI to lose track of
		// ball
		if (x >= 1045) {
			xInc = -xInc;
			
			//SAME HERE
			Assets.addu.play(1f);
		}

		/*
		 * //game pauses if user touches the menu key
		 * if(Input.GetKey(KeyCode.Menu)) state = GameState.Paused;
		 */

		// game over
		if (x < 100) {
			state = GameState.GameOver;
		}

	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 1)
			state = GameState.Running;
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 0) {
			reset();
			state = GameState.Ready;
		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.clearScreen(Color.BLACK);
		if (state == GameState.Ready) {
			// this is here for the second tap, doesn't really do anything
			// worthwhile
			// g.drawRect(x, y, 20, 20, Color.WHITE);
		}
		if (state == GameState.Running) {
			// actual box
			// g.drawRect(x, y, 20, 20, Color.WHITE);
			g.drawImage(Assets.menu, x, y);

			// paddles below

			// left
			g.drawRect(x1, userPaddleY, 10, 150, Color.WHITE);

			// right
			g.drawRect(x2, paddleY, 10, 150, Color.WHITE);
		}
		if (state == GameState.Paused) {
			g.clearScreen(Color.BLACK);
			g.drawString("Game Paused, touch screen twice to resume", 640, 360,
					paint1);
		}
		if (state == GameState.GameOver) {
			g.clearScreen(Color.BLACK);
			g.drawString("GAME OVER", 640, 360, paint1);
			g.drawString("Touch the screen to play again", 640, 460, paint1);

		}

	}

	private void nullify() {

		// Set all variables to null. You will be recreating them in the
		// constructor.
		paint = null;

		// Call garbage collector to clean up memory.
		System.gc();
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {
		pause();
	}

	public void reset() {
		paint = new Paint();
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);

		// for pause button
		// for game over
		paint1 = new Paint();
		paint1.setTextSize(40);
		paint1.setTextAlign(Paint.Align.CENTER);
		paint1.setAntiAlias(true);
		paint1.setColor(Color.WHITE);

		// x and y are rectangle numbers, and
		x = 100;
		y = 300;
		x1 = 100;
		x2 = 1180;
		userPaddleY = 50;
		paddleY = 50;

		// xInc and yInc are speed settings
		xInc = 15;
		yInc = 9;
	}
}
