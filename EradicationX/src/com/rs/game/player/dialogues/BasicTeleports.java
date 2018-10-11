package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class BasicTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Basic teleports", "<shad=FD3EDA>Home",
					"<shad=990033>Shops", "<shad=000000>Train", "<shad=FF0000>GanoDung", "<shad=ff00ff>Slayer Tower");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0D, new WorldTile(
						4248, 3942, 2));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						2406, 4721, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						2408, 3851, 0));
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						4725, 5465, 0));
				end();
			}
			if (componentId == OPTION_5) {
				Magic.sendNormalTeleportSpell(player, 0, 0.0D, new WorldTile(
						3429, 3538, 0));
				end();
			}
		}
	}

	@Override
	public void finish() {

	}

}