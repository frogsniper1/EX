package com.rs.game.player.dialogues;

import com.rs.game.player.content.DungeoneeringRewards;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class Ariane extends Dialogue {

	public Ariane() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Pick a Dungeoneering Reward", "Chaotic Rapier (200K)",
				"Chaotic Maul (200K)", "Chaotic Staff (200K)", "Chaotic Crossbow (200K)",
				"Next Page");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				DungeoneeringRewards.HandleRapier(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				DungeoneeringRewards.HandleMaul(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				DungeoneeringRewards.HandleStaff(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				DungeoneeringRewards.HandleBow(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Pick a Dungeoneering Reward",
						"Arcane Stream Necklace (50K)",
						"How do i get these tickets?", "Nevermind.");
				stage = 4;
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) { // Skilling Locations
				DungeoneeringRewards.HandleStreamNeck(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {// Dungeon & Cave Teleports
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3)
				player.getInterfaceManager().closeChatBoxInterface();
		}
	}

	@Override
	public void finish() {
	}

}
