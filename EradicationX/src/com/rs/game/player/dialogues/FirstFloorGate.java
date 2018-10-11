package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class FirstFloorGate extends Dialogue {

	public FirstFloorGate() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Going inside, outside or boss?",
				"Go Inside",
				"Go Outside",
				"Quick-travel to Dragrith Nurn",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.useStairs(-1, new WorldTile(4249, 3932, 0), 0, 1);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.useStairs(-1, new WorldTile(4248, 3930, 0), 0, 1);			
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4224, 3947, 0));
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	
	


}	@Override
	public void finish() {
	}

}


