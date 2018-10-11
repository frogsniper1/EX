package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.player.Player;

/**
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 */
public class ConstructFurniture {
	public static int CFG = 397; // <-- This is the construction skill board.

	// private Player player; <-- Pointless add if needed

	public static void handleButtons(Player player, int componentId) {
		if (componentId == 45 && player.getInventory().containsItem(960, 2)) {
			player.getSkills().addXp(22, 75);
			player.sm("You build: 1x armchair.");
			player.setNextAnimation(new Animation(883));
			player.getInventory().deleteItem(960, 2);
			player.getInventory().refresh();
			player.closeInterfaces();
		} else {
			player.sm("You will need: 2 planks to build a chair.");
		}
		if (componentId == 46 && player.getInventory().containsItem(960, 4)) {
			player.getSkills().addXp(22, 111);
			player.sm("You build: 1x bookcase.");
			player.getInventory().deleteItem(960, 4);
			player.setNextAnimation(new Animation(883));
			player.closeInterfaces();
			return;
		} else {
			player.sm("You will need: 4 planks to build a bookcase.");
		}
		if (componentId == 47 && player.getInventory().containsItem(8778, 4)) {
			player.getSkills().addXp(22, 165);
			player.sm("You build: 1x Beer Barrels.");
			player.getInventory().deleteItem(8778, 4);
			player.setNextAnimation(new Animation(883));
			player.closeInterfaces();
		} else {
			player.sm("You will need: 3 oak planks to build a Beer Barrels.");
		}
		if (componentId == 47 && player.getInventory().containsItem(8778, 8)) {
			player.getSkills().addXp(22, 165);
			player.sm("You build: 1x Kitchen Table.");
			player.setNextAnimation(new Animation(883));
			player.getInventory().deleteItem(8778, 8);
			player.closeInterfaces();
		} else {
			player.sm("You will need: 8 oak planks to build a Kitchen Table.");
		}
		if (componentId == 49 && player.getInventory().containsItem(8778, 4)
				&& player.getInventory().containsItem(960, 1)) {
			player.getSkills().addXp(22, 265);
			player.sm("You build: 1x Dining Table.");
			player.getInventory().deleteItem(8778, 4);
			player.getInventory().deleteItem(960, 1);
			player.setNextAnimation(new Animation(883));
			player.closeInterfaces();
		} else {
			player.sm("You will need: 8 oak planks and 1x plank to build a Dining Table.");
		}
		if (componentId == 50 && player.getInventory().containsItem(8780, 2)
				&& player.getInventory().containsItem(960, 1)) {
			player.getSkills().addXp(22, 400);
			player.sm("You build: 1x Dining bench.");
			player.setNextAnimation(new Animation(883));
			player.getInventory().deleteItem(8780, 2);
			player.getInventory().deleteItem(960, 1);
			player.closeInterfaces();
		} else {
			player.sm("You will need: 2 oak planks and 2x teak plank to build a Dining bench.");
		}
	}
}
