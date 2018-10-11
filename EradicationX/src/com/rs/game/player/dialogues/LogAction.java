package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class LogAction extends Dialogue {

	private int itemId;
	
	public LogAction() {
	}

	@Override
	public void start() {
		itemId = (Integer) parameters[0];
		stage = 1;
		sendOptionsDialogue("Select and action",
				"Knife", "Tinderbox");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				end();
				if (player.getToolbelt().contains(946)) {
					Fletch fletch = Fletching.isFletching(new Item(946), new Item(itemId));
					player.getDialogueManager().startDialogue("FletchingD", fletch);
				} else {
					player.sm("You need a knife to fletch.");
				}
			} else if (componentId == OPTION_2) {
				end();
				if (player.getToolbelt().contains(590)) {
					Firemaking.isFiremaking(player, itemId);
				} else {
					player.sm("You need a knife to fletch.");
				}
			}
		}
	}
	


	@Override
	public void finish() {
	}

}


