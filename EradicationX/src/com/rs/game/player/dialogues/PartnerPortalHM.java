package com.rs.game.player.dialogues;

import java.util.ArrayList;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

// created by Fatal Resort - eradicationx.com

public class PartnerPortalHM extends Dialogue {

	public PartnerPortalHM() {
	}
	private int y;

	@Override
	public void start() {
		y = (Integer) parameters[0];
		sendOptionsDialogue("Select an Option", player.getY() < y ? "Enter" : "Leave", "Leave Instance");
		stage = 1;

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getY() > y) 
					player.setNextWorldTile(new WorldTile(player.getX(), player.getY()-3, player.getPlane()));
				else {
					ArrayList<NPC> npcs = World.getNPCsNear(player);
					for (NPC npc : npcs)  {
						if (npc.getId() == 45)
							continue;
						npc.setTarget(player);
					}
					player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+3, player.getPlane()));
					}
				end();
			} else if (componentId == OPTION_2) {
			stage = 2;
			sendOptionsDialogue("Are you sure you want to leave?", "Yes", "No");
			}
	  	} else if (stage == 2) {
			if (componentId == OPTION_1) {
				player.setInstanceEnd(true);
				player.sm("Your instance has ended. You will now be sent to the original room.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}

	}
	@Override
	public void finish() {
	}

}
