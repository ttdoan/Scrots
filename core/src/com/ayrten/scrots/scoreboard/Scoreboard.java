package com.ayrten.scrots.scoreboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class Scoreboard {
	protected String file = "highscore.txt";

	public static class Scores {
		public int first = 0;
		public int second = 0;
		public int third = 0;
		public int fourth = 0;
		public int fifth = 0;
		
		public String first_name = "";
		public String second_name = "";
		public String third_name = "";
		public String fourth_name = "";
		public String fifth_name = "";
	}

	public Scoreboard() {
	}

	public String getHighScore() {
		String file = readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Scores scores = json.fromJson(Scores.class, file);

			return String.valueOf(scores.first);
		}

		return "0";
	}

	public Scores getAllScores() {
		String file = readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Scores scores = json.fromJson(Scores.class, file);

			return scores;
		}

		return new Scores();
	}

	public void setHighScore(int highscore, String name) {
		Scores scores = getAllScores();
		Json json = new Json();
		
		if(scores.first < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = scores.second;
			scores.second = scores.first;
			scores.first = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = scores.third_name;
			scores.third_name = scores.second_name;
			scores.second_name = scores.first_name;
			scores.first_name = name;
		}
		else if(scores.second < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = scores.second;
			scores.second = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = scores.third_name;
			scores.third_name = scores.second_name;
			scores.second_name = name;
		}
		else if(scores.third < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = scores.third_name;
			scores.third_name = name;
		}
		else if(scores.fourth < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = name;
		}
		else if(scores.fifth < highscore)
		{
			scores.fifth = highscore;
			
			scores.fifth_name = name;
		}
		
		writeFile(this.file, json.toJson(scores));
	}

	public static void writeFile(String fileName, String s) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s),
				false);
	}

	public static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}
}