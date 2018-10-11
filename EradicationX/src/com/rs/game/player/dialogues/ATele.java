package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue
// created by fatal resort - EradicationX
public class ATele extends Dialogue {

	public ATele() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Teleport to Armadyl?", "Teleport",
				"Stay here");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
            Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(5504, 4416, 3));				
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
