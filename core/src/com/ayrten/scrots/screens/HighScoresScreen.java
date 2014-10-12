package com.ayrten.scrots.screens;

import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard.Scores;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HighScoresScreen implements Screen {
	private float pad_left = (float) 5.5;
	private float pad_right = (float) 5.5;
	private int height = 75;

	private ScrotsGame game;
	private Stage stage;

	private Table table;
	private SelectBox<String> mode;
	private TextButton back;

	public HighScoresScreen(ScrotsGame game) {
		this.game = game;
		stage = new Stage();

		table = new Table();
		table.setFillParent(true);
		table.setSkin(game.skin);

		LabelStyle style = new LabelStyle();
		style.font = game.font_32;

		back = new TextButton("", game.skin);
		back.add(new Label("Back", style));
		back.setBounds(back.getX(), back.getY(), back.getWidth(),
				back.getHeight());
		back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (((ScrotsGame) Gdx.app.getApplicationListener()).prefs
						.getBoolean("sound_effs"))
					((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener())
						.setScreen(((ScrotsGame) Gdx.app
								.getApplicationListener()).main_menu);
			}
		});

		mode = new SelectBox<String>(game.skin);
		mode.setItems("Normal", "Challenge");
		if (!game.prefs.getString("mode", "").equals(""))
			mode.setSelected(game.prefs.getString("mode"));
		// Set the font size of the current shown item.
		mode.getStyle().font = game.font_32;
		// Set the font size of all the items in the list.
		mode.getList().getStyle().font = game.font_32;
		mode.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchHighScoreTable();
			}
		});

		setHighScoreTable(table);
		stage.addActor(table);
	}

	private void switchHighScoreTable() {
		table.clear();
		setHighScoreTable(table);
	}

	private void setHighScoreTable(Table table) {
		if (mode.getSelected().equals("Normal")) {
			NormalScoreboard scoreboard = new NormalScoreboard();
			fillInHighScore(scoreboard, table);
		} else {
			ChallengeScoreboard scoreboard = new ChallengeScoreboard();
			fillInHighScore(scoreboard, table);
		}
	}

	private void fillInHighScore(Scoreboard scoreboard, Table table) {
		LabelStyle style_big = new LabelStyle();
		style_big.font = game.font_64;

		LabelStyle style_small = new LabelStyle();
		style_small.font = game.font_32;

		Scores scores = scoreboard.getAllScores();
		
		float width = Gdx.graphics.getWidth();
		float font_width = style_big.font.getMultiLineBounds("Highscore").width;
		float center = (width / 2) - (font_width/2);

		table.add(back).top().left();
		table.add(mode).top().right();
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 5);
		table.row();
		table.add(new Label("Highscore", style_big)).left()
				.padLeft(center).fillX();
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.first_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.first), style_small)).right()
				.padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.second_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.second), style_small))
				.right().padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.third_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.third), style_small)).right()
				.padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.fourth_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.fourth), style_small))
				.right().padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.fifth_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.fifth), style_small)).right()
				.padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height).expand();
		table.row();
	}

	@Override
	public void render(float delta) {
		if (game.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
			game.setScreen(game.main_menu);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
