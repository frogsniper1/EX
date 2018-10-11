package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class Xuans extends Dialogue {

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(
				npcId,
				9827,
				"Hello "
						+ Utils.formatPlayerNameForDisplay(player.getUsername())
						+ ", I'm Xuan. I sell all types of auras for the price of Loyalty Points. These points can only be obtained by staying logged in EradicationX.");
	}

	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Xuan", "Show me your Shop", "Show me your Shop 2",
					"How many points do i have?",
					"How do i get Loyalty Points?");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				ShopsHandler.openShop(player, 28);
				end();
			}
			if (componentId == OPTION_2) {
				ShopsHandler.openShop(player, 54);
				end();
			}
			if (componentId == OPTION_3) {
				sendNPCDialogue(npcId, 9827,
						"You currently have " + player.getLoyaltyPoints()
								+ " Loyalty Points.");
				stage = 3;
			}
			if (componentId == OPTION_4) {
				sendNPCDialogue(npcId, 9827,
						"The only way to get Loyalty Points is by playing "+Settings.SERVER_NAME+" for 30 minutes.");
				stage = 3;
			}
		} else if (stage == 3) {
			end();
		}
	}

	@Override
	public void finish() {

	}

}