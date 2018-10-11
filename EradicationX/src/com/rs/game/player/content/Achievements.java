package com.rs.game.player.content;

import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 *
 */
public class Achievements {
	public static int DragonHuntMaster= 0;
	public static int AchievementInterface = -1; //<-- find one
	
	public static void HasAchieved (Player player) {
		
	}
	
	public static void DragonHuntMastery (Player player) {
		if (DragonHuntMaster == 0) {
			HasAchieved(player);
			DragonHuntMaster = 1;
		}
	}
}
