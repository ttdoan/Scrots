package com.ayrten.scrots.game;

import java.util.ArrayList;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.level.MainMenuBackgroundLevel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMode {
	public static final int MAIN_MENU_BACKGROUND_MODE = -1;
	public static final int NORMAL_MODE = 1;
	public static final int CHALLENGE_MODE = 2;

	protected Stage stage;
	protected Manager gm;
	protected ArrayList<Level> all_levels = new ArrayList<Level>();
	protected int w, h;
	
	private int levels_generated = 20;

	public GameMode(Stage stage, Manager gm, int width,
			int height) {
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;

		Gdx.input.setInputProcessor(this.stage);
		generate();
	}

	protected void generate() {
		if(gm.get_game_mode() == GameMode.MAIN_MENU_BACKGROUND_MODE)
		{
			all_levels.add(new MainMenuBackgroundLevel(20, w, h, gm));
			return;
		}
		
		// Generate the first 20 levels.
		for (int i = 1; i <= 20; i++) {
			Level lvl = new Level(i, w, h, gm);
			all_levels.add(lvl);
		}
	}
	
	protected void generate(final int level)
	{
		new Thread(new Runnable()
		{
			   @Override
			   public void run()
			   {
				   Level lvl = new Level(level, w, h, gm);
				   all_levels.add(lvl);
				   GameMode.this.levels_generated++;
			   }
			}).start();
	}
	
	public ArrayList<Level> get_all_levels()
	{
		return all_levels;
	}

	public Level gen_curr_level()
	{
		if(all_levels.size() < 10)
		{
			generate(levels_generated);
		}
		
		Level curr_level = all_levels.remove(0);
		gm.setLevel(curr_level);
		for(int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
		
		for(int i = 0; i < curr_level.get_dwd_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_baby_blue_dots().get(i));
		}
		
		for(int i = 0; i < curr_level.get_dwd_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_blue_dots().get(i));
		}
		
		for(int i = 0; i < curr_level.get_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_blue_dots().get(i));
		}
		
		int red_half_size = curr_level.get_red_dots().size()/2;
		for(int i = 0; i < curr_level.get_red_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		int dwd_red_half_size = curr_level.get_dwd_red_dots().size()/2;
		for(int i = 0; i < curr_level.get_dwd_red_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_dwd_red_dots().get(i));
		}
		
		int grn_half_size = curr_level.get_grn_dots().size()/2;
		for(int i = 0; i < curr_level.get_grn_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		int dwd_grn_half_size = curr_level.get_dwd_grn_dots().size()/2;
		for(int i = 0; i < curr_level.get_dwd_grn_dots().size()/2; i++)
		{
			stage.addActor(curr_level.get_dwd_grn_dots().get(i));
		}
		
		for(int i = dwd_red_half_size; i < curr_level.get_dwd_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_red_dots().get(i));
		}
		
		for(int i = red_half_size; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		for(int i = dwd_grn_half_size; i < curr_level.get_dwd_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_dwd_grn_dots().get(i));
		}
		
		for(int i = grn_half_size; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		return curr_level;
	}	
}
