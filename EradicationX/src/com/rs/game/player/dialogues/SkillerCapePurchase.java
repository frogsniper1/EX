package com.rs.game.player.dialogues;

import com.rs.game.player.content.MaxedUser;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class SkillerCapePurchase extends Dialogue {

	public SkillerCapePurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a Skiller cape? [2M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				MaxedUser.CheckSkiller(player);		
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


