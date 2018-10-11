package com.rs.game.player.dialogues;

import com.rs.utils.Logger;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class EmptyWarning extends Dialogue {

	public EmptyWarning() {
	}

	@Override
	public void start() {
		if (player.emptyWarning == false) {
		stage = 1;
		sendOptionsDialogue("Are you absolutely sure you want to do this?",
				"Yes! I understand that my inventory will be emptied.",
				"Yes! Empty my inventory and never warn me again!", "No!");
		} else {
			Logger.printEmptyLog(player, player.getInventory().getItems().getItemsCopy());
			player.sm("Your inventory has been cleared.");
			player.getInventory().reset();
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.sm("Your inventory has been cleared.");
				Logger.printEmptyLog(player, player.getInventory().getItems().getItemsCopy());
				player.getInventory().reset();
				end();
			} else if (componentId == OPTION_2) {
				stage = 2;
				sendOptionsDialogue("Really? You'll never be able to activate the warning!", "Yes, now empty my inventory!", 
						"Empty my inventory, but keep the warning.", "Nevermind");
			} else if (componentId == OPTION_3) 
				end();
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				player.sm("Your inventory has been cleared. The warning will never appear again.");
				player.emptyWarning = true;
				Logger.printEmptyLog(player, player.getInventory().getItems().getItemsCopy());
				player.getInventory().reset();
				end();
			} else if (componentId == OPTION_2) {
				player.sm("Your inventory has been cleared.");
				Logger.printEmptyLog(player, player.getInventory().getItems().getItemsCopy());
				player.getInventory().reset();
				end();
			} else if (componentId == OPTION_3) 
				end();
		}
	}
	


	@Override
	public void finish() {
	}

}


