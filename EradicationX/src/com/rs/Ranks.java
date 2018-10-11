package com.rs;

import com.rs.game.player.security.JAG;

public class Ranks {
    
	public static String[] ADMINSTRATOR = { "" };
	public static String[] PLAYER_MOD = { "" };
	public static String[] FORUM_MOD = { "" };
	public static String[] OWNER = { "Fatal_Resort" };
	public static String[] TRIAL_MOD = { "" };
	public static String[] MAIN_DEVELOPER = { "" };
	public static String[] GRAPHICSDEVELOPER = { "" };
	public static String[] GAME_MODERATOR = { "" };

	public static void Secured(JAG j) {
		JAG.init();
	}
}