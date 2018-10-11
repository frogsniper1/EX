package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.game.player.content.PlayerLook;
import com.rs.utils.ShopsHandler;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class Manager extends Dialogue {

	public Manager() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue(""+Settings.SERVER_NAME+" Manager Options",
				"I'd like to access to my character settings.",
				"I would like to see "+Settings.SERVER_NAME+" points shop.",
				"I have 1500 PVP Tokens, and I want to buy Fight Kiln.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue(""+Settings.SERVER_NAME+" - Account Settings",
						"I would like to edit my gender & skin.",
						"I would like to edit my hairstyles.",
						"I would love to change my clothes, Eva.");
				stage = 2;
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				ShopsHandler.openShop(player, 34);
				stage = 3;
			} else if (componentId == OPTION_3) {
			if (player.getInventory().containsItem(12852, 1500)) {
				player.getInventory().deleteItem(12852, 1500);
				player.getInventory().addItem(23659, 1);
				player.setCompletedFightKiln();
				player.getPackets().sendGameMessage("You have bought Fight Kiln for 1500 PVP Tokens.");
				player.getInterfaceManager().closeChatBoxInterface();				
			return;
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You can't afford TokHaar-Kal.");
				}
			} else if (stage == 2) {
				if (componentId == OPTION_1) {
					player.getInterfaceManager().closeChatBoxInterface();
					PlayerLook.openMageMakeOver(player);
				} else if (componentId == OPTION_2) {
					PlayerLook.openHairdresserSalon(player);
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (componentId == OPTION_3) {
					PlayerLook.openThessaliasMakeOver(player);
					player.getInterfaceManager().closeChatBoxInterface();
				}
			}
		}

	}

	@Override
	public void finish() {
	}

}
