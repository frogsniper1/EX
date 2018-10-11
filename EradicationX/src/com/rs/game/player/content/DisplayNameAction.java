package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
/**
 * @author JazzyYaYaYa | Fuzen Seth | Nexon
 */
public class DisplayNameAction {
public static int MoneyAmount = 500000;
	
	public static void RemoveDisplay (Player player) {
		player.setDisplayName(Utils.formatPlayerNameForDisplay(player.getUsername()));
	player.getInterfaceManager().closeChatBoxInterface();
	SerializableFilesManager.savePlayer(player);
	player.sm("Display name removed, please re-login to get back your old name!");
	}

	public static void ProcessChange (Player player) {
		if (player.chargeMoney(MoneyAmount)) {
			player.sm("You have changed your display name.");
			player.getInterfaceManager().closeChatBoxInterface();  
			player.getTemporaryAttributtes().put("setdisplay",
					  Boolean.TRUE); player.getPackets().sendInputNameScript(
					  "Enter the display name you wish:"); 
					  
		}
		else {
			player.getInterfaceManager().closeChatBoxInterface();
			player.sm("Changing display name will cost you 500k coins.");
		}
	}
	
}
