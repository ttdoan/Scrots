package com.ayrten.scrots.level;

import java.util.ArrayList;

import com.ayrten.scrots.dots.BabyBlueDot;
import com.ayrten.scrots.dots.BlueDot;
import com.ayrten.scrots.dots.DotGenerator;
import com.ayrten.scrots.dots.GreenDot;
import com.ayrten.scrots.dots.RedDot;
import com.ayrten.scrots.screens.Manager;

public class Level
{
	private static final int GREEN_DOT_START = 0;
	private static final int RED_DOT_START = 1;
	private static final int BLUE_DOT_START = 2;
	private static final int BABY_BLUE_DOT_START = 5;
	
	// Modifiers for generating dots
	protected static final float GREEN_DOT_MOD = 1.5f;
	protected static final float RED_DOT_MOD = 1.3f;
	protected static final float BLUE_DOT_MOD = 1.2f;
	protected static final float BABY_BLUE_DOT_MOD = 1.25f;

	// Stores all the dots for that level.
	private ArrayList<GreenDot> greenDots;
	private ArrayList<RedDot> redDots;
	private ArrayList<BlueDot> blueDots;
	private ArrayList<BabyBlueDot> babyBlueDots;

	private int level;
	private DotGenerator generator;
	
	private int number_of_green_dots;

	public Level(int level, int width, int height, Manager gm)
	{
		assert level >= 0;
		this.level = level;
		// Maybe should move this up to the GameMode level? - Tony
		generator = new DotGenerator(width, height, gm);

//		generator = new DotGenerator(width, height, gm);
				
		greenDots = new ArrayList<GreenDot>();
		redDots = new ArrayList<RedDot>();
		blueDots = new ArrayList<BlueDot>();
		babyBlueDots = new ArrayList<BabyBlueDot>();

		gen_grn_dots();
		gen_red_dots();
		gen_blue_dots();
		gen_baby_blue_dots();
		
		number_of_green_dots = greenDots.size();
	}

	public boolean level_clear()
	{
		return number_of_green_dots <= 0 ? true : false;
	}
	
	public void minusGreenDot()
	{
		number_of_green_dots--;
	}

	public ArrayList<GreenDot> get_grn_dots()
	{
		return greenDots;
	}

	public ArrayList<RedDot> get_red_dots()
	{
		return redDots;
	}

	public ArrayList<BlueDot> get_blue_dots()
	{
		return blueDots;
	}

	public ArrayList<BabyBlueDot> get_baby_blue_dots()
	{
		return babyBlueDots;
	}

	private void gen_grn_dots()
	{
		greenDots.clear();
		int num = (int) Math.floor((level - GREEN_DOT_START) * GREEN_DOT_MOD);
		for (int i = 0; i < num; i++)
		{
			GreenDot dot = generator.genGreenDot();
			greenDots.add(dot);
		}
	}

	private void gen_red_dots()
	{
		redDots.clear();
		int num = (int) Math.floor((level - RED_DOT_START) * RED_DOT_MOD);
		for (int i = 0; i < num; i++)
		{
			RedDot dot = generator.genRedDot();
			redDots.add(dot);
		}
	}

	private void gen_blue_dots()
	{
		blueDots.clear();
		int num = (int) Math.floor((level - BLUE_DOT_START) * BLUE_DOT_MOD);
		for (int i = 0; i < num; i++)
		{
			BlueDot dot = generator.genBlueDot();
			blueDots.add(dot);
		}
	}

	private void gen_baby_blue_dots()
	{
		babyBlueDots.clear();
		int num = (int) Math.floor((level - BABY_BLUE_DOT_START) * BABY_BLUE_DOT_MOD);
		for (int i = 0; i < num; i++)
		{
			BabyBlueDot dot = generator.genBabyBlueDot();
			babyBlueDots.add(dot);
		}
	}

}
