package com.rs.game.player.dialogues;

import com.rs.game.player.content.MaxedUser;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class MaxCapePurchase extends Dialogue {

	public MaxCapePurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a Max cape? [2.5M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				MaxedUser.CheckMaxed(player);		
				end();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().sendInterface(3013);
				end();
			}
		}
	}

	@Override
	public void finish() {
	}

}


