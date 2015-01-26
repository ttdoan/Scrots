package com.ayrten.scrots.dots;

import com.ayrten.scrots.screens.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class DWD_PenDot2 extends DWD {
	private static final float TIME_OFF = (float) -3.0;

	public DWD_PenDot2(Texture dot, Manager gm, Sound pop) {
		super(dot, gm, pop);
	}

	@Override
	public void touchedByAnAngel() {
		super.touchedByAnAngel();

		// Lose time
		gm.addTime(TIME_OFF);
	}
}
