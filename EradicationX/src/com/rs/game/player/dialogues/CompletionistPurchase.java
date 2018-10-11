package com.rs.game.player.dialogues;

import com.rs.game.player.content.MaxedUser;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class CompletionistPurchase extends Dialogue {

	public CompletionistPurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a completionist cape? [4M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
                MaxedUser.CheckCompletionist(player);
				end();
			} else if (componentId == OPTION_2) {
				end();
			}
		}
	}

	@Override
	public void finish() {
	}

}


