package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class TrainingTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Training teleports",
					"<shad=00FF00>Rock Crabs (Low-Mid)",
					"<shad=FD3EDA>Greater Demons (Medium Level)",
					"<shad=05F7FF>Black Demons (High Level)",
					"<shad=FFCD05>Green Dragons (Dragon Bones)");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2410,
						3853, 0));
			if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3479,
						9488, 0));
			if (componentId == OPTION_3)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2866,
						9777, 0));
			if (componentId == OPTION_4)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3349,
						3668, 0));
			player.getControlerManager().startControler("Wilderness");
		}
	}

	@Override
	public void finish() {

	}

}