package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class TrioKey extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select an option",
				"Hairymonkey",
				"Hard Mode Trio");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				end();
				player.getDialogueManager().startDialogue("Hairymonkey");
			} else if (componentId == OPTION_2) {
				end();
				Magic.sendNormalTeleportSpell(player, 0, 0.0, new WorldTile(3810, 4691, 0));
			}
		}
	}
	


	@Override
	public void finish() {
	}

}


