package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class OptionsScreen extends ScrotsScreen {
	// Actors
	protected SelectBox<String> mode;
	protected SelectBox<String> bg_color;
	protected CheckBox sound_effs;
	protected CheckBox auto_gplay_signin;
	protected CheckBox color_blind;
	
	protected Table game_options;

	private float label_pad_left = (float) 35; // Lower # = more left

	public OptionsScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		showTableScreen();

		LabelStyle style = new LabelStyle();
		style.font = Assets.font_32;
		mode = new SelectBox<String>(Assets.skin);
		mode.getStyle().font = Assets.font_32;
		mode.getList().getStyle().font = Assets.font_32;
		mode.setItems("Normal", "Challenge");
		if (!Assets.prefs.getString("mode", "").equals(""))
			mode.setSelected(Assets.prefs.getString("mode"));
		// Set the font size of the current shown item.
		mode.getStyle().font = Assets.font_32;
		// Set the font size of all the items in the list.
		mode.getList().getStyle().font = Assets.font_32;

		sound_effs = new CheckBox("", Assets.skin);
		sound_effs.setChecked(true);
		sound_effs
				.getCells()
				.get(0)
				.size(Assets.font_32.getLineHeight() / 2,
						Assets.font_32.getLineHeight() / 2);
		if (Assets.prefs.getBoolean("sound_effs", true) == false)
			sound_effs.setChecked(false);

		auto_gplay_signin = new CheckBox("", Assets.skin);
		auto_gplay_signin.setChecked(true);
		if (Assets.prefs.getBoolean("auto_gplay_signin", true) == false)
			auto_gplay_signin.setChecked(false);

		color_blind = new CheckBox("", Assets.skin);
		color_blind.setChecked(false);
		color_blind
				.getCells()
				.get(0)
				.size(Assets.font_32.getLineHeight() / 2,
						Assets.font_32.getLineHeight() / 2);
		if (Assets.prefs.getBoolean("color_blind", false) == true)
			color_blind.setChecked(true);

		bg_color = new SelectBox<String>(Assets.skin);
		bg_color.setItems("White", "Black");
		bg_color.getStyle().fontColor = Assets.ORANGE;
		bg_color.getStyle().listStyle.fontColorSelected = Assets.ORANGE;
		bg_color.getStyle().listStyle.fontColorUnselected = Assets.ORANGE;
		if (Assets.prefs.getString("bg_color", "White").equals("Black")) {
			Gdx.gl.glClearColor(0, 0, 0, 0);
			bg_color.setSelected("Black");
		} else {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			bg_color.setSelected("White");
		}
		
		Table non_game_options = new Table(Assets.skin);

		// When adding options, keep non-game options at the bottom.
		non_game_options.add(new Label("Background: ", Assets.style_font_32_orange))
				.left().padLeft((float) (table.getWidth() / label_pad_left));
		non_game_options.add(bg_color).center().padLeft(Gdx.graphics.getWidth() / 6);
		non_game_options.row();
		non_game_options.add("").height(Gdx.graphics.getHeight() / 50);
		non_game_options.row();
		non_game_options.add(new Label("Sound Effects: ", Assets.style_font_32_orange))
				.left().padLeft((float) (table.getWidth() / label_pad_left));
		non_game_options.add(sound_effs).center().padLeft(Gdx.graphics.getWidth() / 6);
		
		game_options = new Table(Assets.skin);
		game_options.add(new Label("Auto Google Signin", Assets.style_font_32_orange)).left();
		game_options.add(auto_gplay_signin);
		game_options.add("").height(Gdx.graphics.getHeight() / 50);
		game_options.row();
		game_options.add(new Label("Color Blind Mode: ", Assets.style_font_32_orange))
		.left().padLeft((float) (table.getWidth() / label_pad_left));
		game_options.add(color_blind).center().padLeft(table.getWidth() / 6);
		
		table.add(non_game_options).center().padLeft(Assets.width / 6);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(game_options).center().padLeft(Assets.width / 6);
	}
	
	public void enableNonGameOptions(boolean enable) {
		game_options.setVisible(enable);
	}

	@Override
	public void render(float delta) {
		if (bg_color.getSelected().equals("White")) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 0);
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();

		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
	}

	public void hide() {
		super.hide();
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		if (Assets.prefs.getBoolean("color_blind", false) != color_blind
				.isChecked()) {
			Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
			Assets.loadDotTextures();
		} else
			Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
		Assets.prefs.putBoolean("auto_gplay_signin", auto_gplay_signin.isChecked());
		Assets.prefs.flush();
		if (backScreen.getClass() == MainMenuScreen.class)
			Assets.playMenuBGM();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.prefs.putString("mode", mode.getSelected());
		Assets.prefs.putString("bg_color", bg_color.getSelected());
		Assets.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		Assets.prefs.putBoolean("color_blind", color_blind.isChecked());
		Assets.prefs.putBoolean("auto_gplay_signin", auto_gplay_signin.isChecked());
	}

	public void setActorsColor(Color color) {
		for (Actor actor : stage.getActors()) {
			if (actor.toString().equals("Table")) {
				for (Actor table_actor : ((Table) actor).getChildren()) {
					if (table_actor.toString().equals("Label"))
						table_actor.setColor(color);
				}
			}
		}
	}
}
