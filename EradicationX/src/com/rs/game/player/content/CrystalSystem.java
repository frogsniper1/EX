package com.rs.game.player.content;

import com.rs.game.player.Player;

/**
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 */
public class CrystalSystem {
	public static int CFG = 1123; // <-- Crystal Key Rewards Interface.

	// private Player player; <-- Pointless add if needed
	public static void handleButtons(Player player, int componentId) {
		if (componentId == 4) {
		if (player.getInventory().containsItem(989, 1)) {
		player.getInventory().deleteItem(989, 1);
		player.getInventory().deleteItem(14664, 1);
		player.closeInterfaces();
		player.getInventory().addItem(1437, 350);
		player.getInventory().addItem(7937, 550);
		player.getInventory().refresh();
		player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
		}
		if (componentId == 86) {
		if (player.getInventory().containsItem(989, 1)) {
		player.getInventory().deleteItem(989, 1);
		player.getInventory().deleteItem(14664, 1);
		player.closeInterfaces();
		player.getInventory().addItem(1778, 350);
		player.getInventory().addItem(1780, 550);
		player.getInventory().refresh();
		player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
		}
		if (componentId == 90) {
		if (player.getInventory().containsItem(989, 1)) {
		player.getInventory().deleteItem(989, 1);
		player.getInventory().deleteItem(14664, 1);
		player.closeInterfaces();
		player.getInventory().addItem(2364, 35);
		player.getInventory().addItem(2362, 110);
		player.getInventory().refresh();
		player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
		}
		if (componentId == 82) {
		if (player.getInventory().containsItem(989, 1)) {
		player.getInventory().deleteItem(989, 1);
		player.getInventory().deleteItem(14664, 1);
		player.closeInterfaces();
		player.getInventory().addItem(1437, 350);
		player.getInventory().addItem(7937, 550);
		player.getInventory().refresh();
		player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
		}
		if (componentId == 94) {
		if (player.getInventory().containsItem(989, 1)) {
		player.getInventory().deleteItem(989, 1);
		player.getInventory().deleteItem(14664, 1);
		player.closeInterfaces();
		player.getInventory().addItem(208, 37);
		player.getInventory().addItem(212, 55);
		player.getInventory().refresh();
		player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
		}
		if (componentId == 98) {
		if (player.getInventory().containsItem(989, 1)) {
		player.getInventory().deleteItem(989, 1);
		player.getInventory().deleteItem(14664, 1);
		player.closeInterfaces();
		player.getInventory().addItem(452, 40);
		player.getInventory().addItem(450, 62);
		player.getInventory().refresh();
		player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
		}
		if (componentId == 102) {
		if (player.getInventory().containsItem(989, 1)) {
			player.getInventory().deleteItem(989, 1);
			player.getInventory().deleteItem(14664, 1);
			player.closeInterfaces();
			player.getInventory().addItem(5295, 70);
			player.getInventory().addItem(5297, 38);
			player.getInventory().refresh();
			player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
			}
		if (componentId == 106) {
		if (player.getInventory().containsItem(989, 1)) {
			player.getInventory().deleteItem(989, 1);
			player.getInventory().deleteItem(14664, 1);
			player.closeInterfaces();
			player.getInventory().addItem(392, 350);
			player.getInventory().addItem(15273, 550);
			player.getInventory().refresh();
			player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
			}
		if (componentId == 110) {
		if (player.getInventory().containsItem(989, 1)) {
			player.getInventory().deleteItem(989, 1);
			player.getInventory().deleteItem(14664, 1);
			player.closeInterfaces();
			player.getInventory().addItem(22340, 1);
			player.getInventory().refresh();
			player.sm("You have picked a reward.");
		} else {
			player.sm("Pick up your damn key idiot!");
		}
			}
	}
}
