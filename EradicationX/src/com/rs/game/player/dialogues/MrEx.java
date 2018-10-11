package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class MrEx extends Dialogue {

	public MrEx() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("PK Teleports",
				"Edgeville",
				"Mage Bank",
				"Green Dragons",
				"Chaos Temple");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
							if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3086, 3512, 0));
			} else if (componentId == OPTION_2) {
							if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2538, 4715, 0));
			} else if (componentId == OPTION_3) {
							if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2984, 3632, 0));
			} else if (componentId == OPTION_4) {
							if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3236, 3619, 0));
			}
	}
}

	@Override
	public void finish() {
	}

}
