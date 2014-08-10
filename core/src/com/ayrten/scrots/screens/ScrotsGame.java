package com.ayrten.scrots.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

// Game is an ApplicationListener just like initial setup of Scrots, but with more functionality.
public class ScrotsGame extends Game 
{
	public SpriteBatch batch;
    public BitmapFont font;
    public Preferences prefs;
    public Sound pop;
    public Skin skin;
    public Music bg;
    
    // Going to create the main screens here since we don't want to create them 
    // on the fly, compared to the loading screen.
    // Note: if it takes up too much phone resources, we can always create them on the fly later
    public MainMenuScreen main_menu;
    
    FreeTypeFontGenerator font_generator;
    
	// Different screens.
    LoadingScreen loading_screen;
    
	@Override
	public void create() 
	{
		// Initialize variables
		batch = new SpriteBatch();
		font = new BitmapFont();
		prefs = Gdx.app.getPreferences("My Preferences");
		pop = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		bg = Gdx.audio.newMusic(Gdx.files.internal("sounds/Shinji Orito - Yume no Ato I.mp3"));
		
		main_menu = new MainMenuScreen();		
		loading_screen = new LoadingScreen(this);
		
		bg.setLooping(true);
		bg.play();
		setScreen(loading_screen);
		
		// Catches when the user presses the back button. Has no effects on desktop.
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void dispose() 
	{
		loading_screen.dispose();
		// Make the changes persist. (ie. saves an XML file for Windows in /Users/<user>/.prefs/
		prefs.flush();
		pop.dispose();
		skin.dispose();
	}

}