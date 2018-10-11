package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.QuestManager.Quests;

/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth | Edited by Fatal Resort and Something
 *
 */
public class MaxedUser {
public static int MAXCAPE = 20767;
public static int ONE = 1;
public static int MAXHOOD = 20768;
public static int SKILLERCAPE = 10662;
public static int SKILLERHOOD = 9814;
public static int COMPLETIONISTCAPE = 20770; 
public static int COMPLETIONISTHOOD = 20771;
public static int COMPLETIONISTUT = 20769;



public static void CheckCompletionist(Player player) {
	if (player.getSkills().getLevelForXp(Skills.ATTACK) >= 99
	&& player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99
	&& player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99
	&& player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99
	&& player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99
	&& player.getSkills().getLevelForXp(Skills.RANGE) >= 99
	&& player.getSkills().getLevelForXp(Skills.MAGIC) >= 99
	&& player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99
	&& player.getSkills().getLevelForXp(Skills.FISHING) >= 99
	&& player.getSkills().getLevelForXp(Skills.AGILITY) >= 99
	&& player.getSkills().getLevelForXp(Skills.COOKING) >= 99
	&& player.getSkills().getLevelForXp(Skills.PRAYER) >= 99
	&& player.getSkills().getLevelForXp(Skills.THIEVING) >= 99
	&& player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 120
	&& player.getSkills().getLevelForXp(Skills.MINING) >= 99
	&& player.getSkills().getLevelForXp(Skills.SMITHING) >= 99
	&& player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99
	&& player.getSkills().getLevelForXp(Skills.FARMING) >= 99
	&& player.getSkills().getLevelForXp(Skills.HUNTER) >= 99
	&& player.getSkills().getLevelForXp(Skills.SLAYER) >= 99
	&& player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99
	&& player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99
	&& player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99
	&& player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99
	&& player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99
	&& player.isCompletedFightCaves()
	&& player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)) {
		player.isCompletionist = 1;
		if(player.chargeMoney(4000000)){		
		player.sm("Congrulations, you've achieved the Completionists cape!");
		player.getInventory().addItem(COMPLETIONISTCAPE, ONE);
		player.getInventory().addItem(COMPLETIONISTHOOD, ONE);
		player.getInventory().addItem(COMPLETIONISTUT, ONE);
	} else {
		player.sm("You need 4M in your inventory for this purchase.");
	}		
	}
	else {
		player.isCompletionist = 0; //<-- Added this for safety reasons.
		player.sm("You do not meet the requirements, or you have just claimed a cape recently.");		
	}
	}

	public static void CheckMaxed(Player player) {
		if (player.getSkills().getLevelForXp(Skills.ATTACK) >= 99
		&& player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99
		&& player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99
		&& player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99
		&& player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99
		&& player.getSkills().getLevelForXp(Skills.RANGE) >= 99
		&& player.getSkills().getLevelForXp(Skills.MAGIC) >= 99
		&& player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FISHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.AGILITY) >= 99
		&& player.getSkills().getLevelForXp(Skills.COOKING) >= 99
		&& player.getSkills().getLevelForXp(Skills.PRAYER) >= 99
		&& player.getSkills().getLevelForXp(Skills.THIEVING) >= 99
		&& player.getSkills().getLevelForXp(Skills.MINING) >= 99
		&& player.getSkills().getLevelForXp(Skills.SMITHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FARMING) >= 99
		&& player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 99
		&& player.getSkills().getLevelForXp(Skills.HUNTER) >= 99
		&& player.getSkills().getLevelForXp(Skills.SLAYER) >= 99
		&& player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99) {
			player.isMaxed = 1;
			player.isCompletionist = 0;
			if(player.chargeMoney(2500000)){	
			player.sm("Congratulations, you've achieved the Max cape!");
			player.getInventory().addItem(MAXCAPE, ONE);
			player.getInventory().addItem(MAXHOOD, ONE);
			} else {
				player.sm("You need 2.5M in your inventory to purchase this.");
			}
		}
		else {
			player.isMaxed = 0; //<-- Added this for safety reasons.
			player.sm("You do not meet the requirements, or you have just claimed a cape recently.");
		}
		}
		
	public static void CheckSkiller(Player player) {
		if (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99
		&& player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FISHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.AGILITY) >= 99
		&& player.getSkills().getLevelForXp(Skills.COOKING) >= 99
		&& player.getSkills().getLevelForXp(Skills.THIEVING) >= 99
		&& player.getSkills().getLevelForXp(Skills.MINING) >= 99
		&& player.getSkills().getLevelForXp(Skills.SMITHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FARMING) >= 99
		&& player.getSkills().getLevelForXp(Skills.HUNTER) >= 99
		&& player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99
		&& player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99
		&& player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99) {
			player.isSkiller = 1;		
			player.isMaxed = 0;
			player.isCompletionist = 0;
		if (player.chargeMoney(2000000)) {			
			player.sm("Congratulations, you've achieved the Skiller cape!");
			player.getInventory().addItem(SKILLERCAPE, ONE);
			player.getInventory().addItem(SKILLERHOOD, ONE);
		} else {
			player.sm("You need 2M coins in your inventory to purchase this.");
		}
		}
		else {
			player.isSkiller = 0; //<-- Added this for safety reasons.
			player.sm("You do not meet the requirements, or you have just claimed a cape recently.");
		}
		}		
		
}
