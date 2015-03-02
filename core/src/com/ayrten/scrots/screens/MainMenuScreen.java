package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.MainMenuBackgroundGameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends ScrotsScreen 
{
	protected GameScreen 		game_screen;
	protected OthersScreen 		others_screen;
	protected ProfileScreen 	profile_screen;
	protected ScoresScreen 		high_score_screen;
	protected ShopScreen		shop_screen;
	protected OptionsScreen 	options_screen;
	protected ContactScreen		contact_screen;

	protected Label gplay_log;

	public NormalScoreboard nsb;

	// public ChallengeScoreboard csb;

	public MainMenuScreen() {
		super(null, false);
		
		// Initialize variables
		nsb = new NormalScoreboard();
		// csb = new ChallengeScoreboard();
		
		contact_screen 		= new ContactScreen(this);
		high_score_screen 	= new ScoresScreen(this);
		options_screen  	= new OptionsScreen(this);
		others_screen 		= new OthersScreen(this);
		profile_screen		= new ProfileScreen(this);
		shop_screen 		= new ShopScreen(this);
		
		
		LabelStyle title_style = new LabelStyle();
		title_style.font = Assets.font_120;
		title_style.fontColor = Color.valueOf("9f38ff");
		Label scrots = new Label("Scrots", title_style);
		scrots.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 3 * 2);
		
		LabelStyle style = new LabelStyle();
		style.font = Assets.font_64;
		style.fontColor = Assets.ORANGE;
		
		Label shop = new Label("Shop", style);
		shop.setBounds(shop.getX(), shop.getY(), shop.getWidth(), shop.getHeight());
		shop.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(shop_screen);
//				Gdx.net.openURI("market://details?id=com.ayrten.scrots.android");
			}
		});
		
		Label play = new Label("Play", style);
		play.setBounds(play.getX(), play.getY(), play.getWidth(),
				play.getHeight());
		play.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				if (Assets.prefs.getBoolean("first_time", true))
					loadTutorialScreen();
				else {
					game_screen = new GameScreen();
					Assets.playGameBGM();
					Assets.game.setScreen(game_screen);
				}
			}
		});

		Label scores = new Label("Scores", style);
		scores.setBounds(scores.getX(), scores.getY(),
				scores.getWidth(), scores.getHeight());
		scores.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(high_score_screen);
			}
		});
		
		Label options = new Label("Options", style);
		options.setBounds(options.getX(), options.getY(), options.getWidth(), options.getHeight());
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setActorsTouchable(Touchable.disabled);
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				options_screen.setBackScreen(Assets.game.getScreen());
				Assets.game.setScreen(options_screen);
			}
		});
		
		Label contacts = new Label("Contacts", style);
		contacts.setBounds(contacts.getX(), contacts.getY(), contacts.getWidth(), contacts.getHeight());
		contacts.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setActorsTouchable(Touchable.disabled);
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(contact_screen);
			}
		});

		Label others = new Label("Others", style);
		others.setBounds(others.getX(), others.getY(), others.getWidth(),
				others.getHeight());
		others.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(others_screen);
			}
		});
		
		gplay_log = new Label("Sign in", style);
		gplay_log.setWidth(gplay_log.getStyle().font.getBounds("Logout").width);
		gplay_log.setBounds(gplay_log.getX(), gplay_log.getY(), gplay_log.getWidth(), gplay_log.getHeight());
		gplay_log.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if(Assets.game.apk_intf.is_gplay_signedin())
					Assets.game.apk_intf.gplay_logout();
				else
					Assets.game.apk_intf.gplay_signin();
			}
		});
		gplay_log.setPosition(0, Assets.height - gplay_log.getStyle().font.getLineHeight());

		Manager gm = new Manager(0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), stage);
		gm.setMode(GameMode.MAIN_MENU_BACKGROUND_MODE);
		gm.setScoreboard(new Scoreboard());
		new MainMenuBackgroundGameMode(stage, gm, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()).gen_curr_level();
		gm.changeDotSize();

		setupStage();
		
		Table upperTable = new Table(Assets.skin);
		upperTable.add(scrots);
		
		ArrayList<Actor> lowerTableActors = new ArrayList<Actor>();
		lowerTableActors.add(shop);
		lowerTableActors.add(play);
		lowerTableActors.add(scores);
		Table lowerTable = new Table(Assets.skin);
		for(int i = 0; i < lowerTableActors.size(); i++)
		{
			// Reason for adding another layer of table is alignment correct and
			// bounds correction of touch listener.
			Table temp = new Table(Assets.skin);
			temp.add(lowerTableActors.get(i));
			lowerTable.add(temp).width(Assets.width/3).height(Assets.height/5);
		}
		
		lowerTable.row();
		lowerTableActors.clear();
		lowerTableActors.add(options);
		lowerTableActors.add(contacts);
		lowerTableActors.add(others);
		for(int i = 0; i < lowerTableActors.size(); i++)
		{
			Table temp = new Table(Assets.skin);
			temp.add(lowerTableActors.get(i));
			lowerTable.add(temp).width(Assets.width/3).height(Assets.height/5);
		}
		
		Table main_table = new Table(Assets.skin);
		main_table.setFillParent(true);
		main_table.add(upperTable).height(Assets.height/2);
		main_table.row();
		main_table.add(lowerTable).height(Assets.height/2).width(Assets.width);
		
		stage.addActor(gplay_log);
		stage.addActor(main_table);
	}
	
	private void loadTutorialScreen()
	{
		int pages = 3;
	    Table top_tutorial_table = new Table(Assets.skin);
	    top_tutorial_table.setWidth(Assets.width * pages);
	    top_tutorial_table.add("first").width(Assets.width);
	    top_tutorial_table.add("middle").width(Assets.width);
	    top_tutorial_table.add("end").width(Assets.width);
		
	    // Use the slideshow type MessageScreen.
		MessageScreen tutorial_screen = new MessageScreen(top_tutorial_table, pages){
			@Override
			public void transition() {
				Assets.prefs.putBoolean("first_time", false);
				Assets.prefs.flush();
				game_screen = new GameScreen();
				Assets.playGameBGM();
				Assets.game.setScreen(game_screen);
			}
		};
		
		Assets.game.setScreen(tutorial_screen);
	}
	
//	public void update_gplay_status(boolean isConnected) {
//		if(isConnected)
//			gplay_log.setText("Logout");
//		else
//			gplay_log.setText("Sign in");
//	}

	@Override
	public void show() {
		super.show();
//		Assets.game.apk_intf.shouldShowAd(true);
		Assets.game.apk_intf.setGPlayButton(gplay_log);
		gplay_log.setText((Assets.game.apk_intf.is_gplay_signedin()) ? "Logout" : "Sign in");
	}

	@Override
	public void hide() {
		super.hide();
//		Assets.game.apk_intf.shouldShowAd(false);
	}
}