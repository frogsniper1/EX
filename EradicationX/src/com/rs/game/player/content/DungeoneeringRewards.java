package com.rs.game.player.content;

import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Fuzen Seth | Nexon
 *
 */
public class DungeoneeringRewards {
public static int ENOUGH = 200000;
public static int ENOUGH1 = 50000;
public static int TOKENS = 3;
	
				public static void HandleRapier(Player player) {
					if (player.getInventory().containsItem(TOKENS, ENOUGH)) {
						player.getInventory().addItem(18349, 1);
						player.getInventory().deleteItem(TOKENS, ENOUGH);
						player.getInventory().refresh();
					}
					else {
						player.sm("You must have 200,000 tickets to purchase this item.");
					}
				}
				public static void HandleStaff(Player player) {
					if (player.getInventory().containsItem(TOKENS, ENOUGH)) {
						player.getInventory().addItem(18355, 1);
						player.getInventory().deleteItem(TOKENS, ENOUGH);
						player.getInventory().refresh();
					}
					else {
						player.sm("You must have 200,000 tickets to purchase this item.");
					}
				}		public static void HandleBow(Player player) {
					if (player.getInventory().containsItem(TOKENS, ENOUGH)) {
						player.getInventory().addItem(18357, 1);
						player.getInventory().deleteItem(TOKENS, ENOUGH);
						player.getInventory().refresh();
					}
					else {
						player.sm("You must have 200,000 tickets to purchase this item.");
					}
				}
				public static void HandleMaul(Player player) {
					if (player.getInventory().containsItem(TOKENS, ENOUGH)) {
						player.getInventory().addItem(18353, 1);
						player.getInventory().deleteItem(TOKENS, ENOUGH);
						player.getInventory().refresh();
					}
					else {
						player.sm("You must have 200,000 tickets to purchase this item.");
					}
				}
				public static void HandleLongsword(Player player) {
					if (player.getInventory().containsItem(TOKENS, ENOUGH)) {
						player.getInventory().addItem(18351, 1);
						player.getInventory().deleteItem(TOKENS, ENOUGH);
						player.getInventory().refresh();
					}
					else {
						player.sm("You must have 200,000 tickets to purchase this item.");
					}
				}
				public static void HandleStreamNeck(Player player) {
					if (player.getInventory().containsItem(TOKENS, ENOUGH1)) {
						player.getInventory().addItem(18335, 1);
						player.getInventory().deleteItem(TOKENS, ENOUGH);
						player.getInventory().refresh();
					}
					else {
						player.sm("You must have 50,000 tickets to purchase this item.");
					}
				}
}
