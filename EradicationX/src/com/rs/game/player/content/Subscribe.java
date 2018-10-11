package com.rs.game.player.content;

import com.rs.Settings;
import com.rs.game.player.Player;

public class Subscribe {
   public static int BOARD =  1066;

    public static void SubBoard(Player player) {
        if (!player.isDonator()) {
        	player.getInterfaceManager().sendInterface(BOARD);
        	player.getPackets().sendIComponentText(1066, 19, "Welcome to "+Settings.SERVER_NAME+"");
        	player.getPackets().sendIComponentText(1066, 20, 
        			"Welcome to "+Settings.SERVER_NAME+"! Get more power by donating! " 
        			+ "to hit the subscribe now button! You'll be able to get access to new armor, " 
        			+ "more weapons, a pet you can grow, new and exciting areas, and much much more! Subscribe today!");
        }
    }

    public static void detectPlayerStatus(Player player) {
    	if (player.starter == 0) {
			player.getDialogueManager().startDialogue("Starter");
			player.lock();
			player.chooseChar = 1;
			player.starter = 1;
		}
    }
    public static void handleButtons(Player player, int componentId) {
        if (componentId == 28) {
            player.getPackets().sendOpenURL("https://rspsdata.org/system/store?id=82");
            player.getInventory().refresh();
            player.closeInterfaces();
            detectPlayerStatus(player);
        }
        if (componentId == 35) {
            player.getPackets().sendOpenURL("https://rspsdata.org/system/store?id=82");
            player.getInventory().refresh();
            player.closeInterfaces();
            detectPlayerStatus(player);
        }
        if (componentId == 12) {	
            player.getInventory().refresh();
            player.closeInterfaces();
            detectPlayerStatus(player);
        }
    }
}
