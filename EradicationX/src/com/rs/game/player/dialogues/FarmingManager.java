package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.utils.ShopsHandler;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class FarmingManager extends Dialogue {

	public FarmingManager() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select an option",
				"Farming Supplies",
				"Farming Seeds",
				"Farming Teleports");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();			
				ShopsHandler.openShop(player, 45);	 
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
				ShopsHandler.openShop(player, 46);	 
			} else if (componentId == OPTION_3) {
			stage = 2;
			sendOptionsDialogue("Farming Teleports", "South Falador Patch", "Ardougne Patch", "Catherby Patch", "Canifis Patch");			 
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3052, 3305, 0));
			} else if (componentId == OPTION_2) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2663, 3374, 0));
			} else if (componentId == OPTION_3) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2811, 3466, 0));
			} else if (componentId == OPTION_4) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3603, 3530, 0));		
			}
			}
	


}	@Override
	public void finish() {
	}

}


