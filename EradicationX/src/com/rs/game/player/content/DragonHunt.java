package com.rs.game.player.content;

import com.rs.game.player.Player;
/**
 * 
 * @author JazzyYaYaYa | Nexon | Fuzen Seth
 *
 */
public class DragonHunt {
//Dragons and other INTS.
	/* Add the NPC ids here */
public static int GREEN_DRAGON = -1;
public static int BLUE_DRAGON = -1;	
public static int RED_DRAGON = -1;
public static int BLACK_DRAGON = -1;
public static int DRAGON_KING = -1;
/* Keys */
public static int MAIN_KEY = -1;
public static int BLUE_KEY = -1;
public static int GREEN_KEY = -1;
public static int RED_KEY = -1;
public static int BLACK_KEY = -1;
public static int KEY_AMOUNT = 1;

		public static void openChest(Player p) {
				if (p.getInventory().containsItem(MAIN_KEY, KEY_AMOUNT)) {
					p.sm("Magical chest's power notices your magical key...");
/*Other Actions*/
	}
					else {
					p.sm("Machine didn't detect all the four keys.");
				}
			}
		}
