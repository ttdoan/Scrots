package com.ayrten.scrots.dots.regular;

import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class RegDot2 extends MovingDot {
	public static final float TIME_ADD = 2.6f; // seconds

	public RegDot2(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel(InputEvent event) {
		super.touchedByAnAngel(event);
		
		// Lose time
		gm.addTime(TIME_ADD);
		Assets.stats_manager.getPlayerStats().reg_dot_2.popped++;
	}
}