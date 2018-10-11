package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class PkingTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Pking teleports", "<shad=FD3EDA>Magic Bank",
					"<shad=990033>Revenants", "Edgeville");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0D, new WorldTile(
						2539, 4716, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						3071, 3649, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						3087, 3496, 0));
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}