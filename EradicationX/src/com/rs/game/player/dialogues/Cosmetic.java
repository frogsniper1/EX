package com.rs.game.player.dialogues;

import com.rs.game.player.CosmeticItems;
import com.rs.cache.loaders.NPCDefinitions;

public class Cosmetic extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendEntityDialogue(Dialogue.SEND_1_TEXT_CHAT, new String[] {
				NPCDefinitions.getNPCDefinitions(3380).name,
				"Hi, I manage your cosmetic overrides on your player! Cosmetic Overrides are sets of gears that overwrite your real gear, just for the looks. Want to see the selection?  " }, IS_NPC, 3380, 9847);
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			sendOptionsDialogue("Open Manager?",
					"Yes",
					"No");
			stage = 2;
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				CosmeticItems.openInterface(player);
				player.setInterfaceAmount(0);
				end();
			}
			if (componentId == OPTION_2) {
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}