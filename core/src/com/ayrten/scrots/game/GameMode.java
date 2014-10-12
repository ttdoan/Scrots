package com.ayrten.scrots.game;

import java.util.ArrayList;

import javax.print.attribute.TextSyntax;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class GameMode
{
	protected SpriteBatch batch = new SpriteBatch();
	protected Stage stage;
	protected Manager gm;

	protected ArrayList<Level> all_levels = new ArrayList<Level>();
	protected Level curr_level;
	protected CharSequence str = " points";
	protected CharSequence time = "Time left: ";

	protected BitmapFont font_points;
	protected BitmapFont font_time;
	protected ScrotsGame game;
	
	protected TextField user_name;
	protected Label game_over;
	protected int w, h;
	protected boolean should_clear_stage;

	public GameMode(ScrotsGame game, Stage stage, Manager gm, int width, int height)
	{
		this.game = game;
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;
		this.batch = (SpriteBatch) stage.getBatch();
		should_clear_stage = true;
		
		font_points = game.font_16;
		font_time = game.font_16;
		
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = game.font_64;
		
		TextFieldStyle textStyle = new TextFieldStyle();
		textStyle.font = game.font_32;
		
		if(game.prefs.getString("bg_color", "").equals("") 
				|| game.prefs.getString("bg_color", "").equals("White"))
		{
			labelStyle.fontColor = Color.BLACK;
			textStyle.fontColor = Color.BLACK;
			font_points.setColor(Color.BLACK);
			font_time.setColor(Color.BLACK);
		}
		else
		{
			labelStyle.fontColor = Color.WHITE;
			textStyle.fontColor = Color.WHITE;
			font_points.setColor(Color.WHITE);
			font_time.setColor(Color.WHITE);
		}

		game_over = new Label("Game Over!", labelStyle);
		game_over.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3 * 2);
		
		user_name = new TextField("", textStyle);
		user_name.setCenterPosition(Gdx.graphics.getWidth()/2,  Gdx.graphics.getHeight()/3 * 2 - labelStyle.font.getLineHeight());
		user_name.setMessageText("Enter your name");
		user_name.setTextFieldListener(new TextFieldListener() 
		{
			public void keyTyped(TextField textField, char key) 
			{
				if(key == '\n'|| Gdx.input.isKeyPressed(Keys.ENTER))
				{
					((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen.getManager().addHighScore(textField.getText());
					((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu);
					
				}
			}
		});
		
		Gdx.input.setInputProcessor(this.stage);
		generate();
		gm.startGame();
	}

	public void dispose()
	{
		batch.dispose();
	}
	
	public void resize(int width, int height)
	{
//		stage.setViewport(new StretchViewport(width, height));
//		stage.getCamera().position.set(width, height, 0);
	}

	protected void generate()
	{
		// Generate the first 20 levels.
		for (int i = 1; i < 20; i++)
		{
			Level lvl = new Level(i, w, h, gm);
			all_levels.add(lvl);
		}

		setStage();
	}

	public void render()
	{
		if (gm.isGameOver())
		{
			gameOver();
		}
		else
		{
			point();
			time();

			stage.draw();
			if (curr_level.level_clear())
			{
				levelClear();
			}
		}
	}

	public void point()
	{
		batch.begin();
		font_points.draw(batch, String.valueOf(gm.get_player_score()) + str, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20);
		batch.end();

	}

	public void time()
	{
		batch.begin();
		font_time.draw(batch, time + gm.getTime(), Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/20 + font_points.getLineHeight());
		batch.end();
	}

	public void gameOver()
	{
		if(should_clear_stage)
		{
			stage.clear();
			should_clear_stage = false;
			stage.addActor(game_over);
			if(gm.get_player_score() > gm.getScoreBoard().getLowestHighScore())
			{
				stage.addActor(user_name);
			}
			else
			{
				// Replay
				// Main menu
			}
		}
		
		stage.draw();
	}

	public void levelClear()
	{
		// One point for clearing a level
		stage.clear();
		gm.plusOnePoint();

		// Level newLevel = new Level()
		setStage();
	}

	public void setStage()
	{
		curr_level = all_levels.remove(0);
		gm.setLevel(curr_level);
		for (int i = 0; i < curr_level.get_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_blue_dots().get(i));
		}

		for (int i = 0; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		for (int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
	}
}
