package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PowerDot extends Dot {
	private float DELAY = (float) 0.05; // seconds
	private static final float ADDER = (float) 0.005; // seconds
	protected float ACTIVE_TIME = 0; // seconds

	private Label num_label;
	private Label time_label;
	private Timer timer;
	public float time;

	protected int num = 0; // The amount of power dots the user has

	protected InputListener powerdot_listener;
	protected float origX, origY;

	protected Image rs;
	protected SpriteBatch batch;
	float angle;
//	protected Image gray_timer_bg;

	public PowerDot(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
		timer = new Timer();

		angle = 0;

		batch = new SpriteBatch();

		powerdot_listener = new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				origX = event.getTarget().getCenterX();
				origY = event.getTarget().getCenterY();
				event.getTarget().setCenterPosition(Gdx.input.getX(pointer), event.getTarget().getCenterY());
				return true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer) {
				if(Gdx.input.getX(pointer) > origX)
					event.getTarget().setCenterPosition(Gdx.input.getX(pointer), event.getTarget().getCenterY());
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				// If x position exceeds a threshold, then activate the effect.
				if (Math.abs(origX - event.getTarget().getCenterX()) > Assets.powerdot_thresh) 
					touchedByAnAngel();
				event.getTarget().setCenterPosition(origX, origY);
			}
		};

		removeListener(listener);
		addListener(powerdot_listener);
	}

	public void setTimeLabel(Label label) {
		time_label = label;
		time_label.setVisible(false);
	}

	public void setNumLabel(Label label) {
		num_label = label;
		updateNumLabel();
	}

	public void setRadialTimer(Image image) {
		rs = image;
	}
	
	public void setGrayBg(Image gray_bg) {
//		gray_timer_bg = gray_bg;
	}

	public void updateNumLabel() {
		num_label.setText("x" + String.valueOf(num));
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

		beforeAction();

		time = ACTIVE_TIME;
		setTime();
		time_label.setVisible(true);
		startTime();
	}

	// Action to do before timer starts.
	public void beforeAction() {
		rs.setVisible(true);
//		gray_timer_bg.setVisible(true);
		setVisible(false);
		
	}

	// Action to do during the timer
	public void duringAction() {
		angle = 360 * (1 - time/ACTIVE_TIME);
		batch.begin();
		((RadialSprite) rs.getDrawable()).draw(batch, rs.getX(), rs.getY(), angle);
		batch.end();
	}

	// Action to do after timer ends
	public void afterAction() {
		angle = 0;
		rs.setVisible(false);
//		gray_timer_bg.setVisible(false);
		setVisible(true);
	}

	public void startTime() {
		timer.scheduleTask(new Task() {
			@Override
			public void run() {
				float subtract = DELAY + ADDER;
				time = time - subtract;
				setTime();
				duringAction();

				if (time <= 0) {
					time_label.setVisible(false);
					afterAction();
					return;
				}

				startTime();
			}
		}, DELAY);
	}

	public void pauseTime() {
		timer.stop();
	}

	public void resumeTime() {
		timer.start();
	}

	private void setTime() {
		if (time < 10) {
			time_label.setText(String.valueOf(time).substring(0, 3));
		} else {
			time_label.setText(String.valueOf(time).substring(0, 2));
		}
	}
}
