package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class CombineKey extends Dialogue {

	public CombineKey() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Combining key parts costs 350M.",
				"Yes combine my key parts for 350M.",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(29944, 1) && player.getInventory().containsItem(29945, 1) && player.getInventory().containsItem(29946, 1) && player.chargeMoney(350000000)) {		
				player.getInventory().deleteItem(29944, 1);
				player.getInventory().deleteItem(29945, 1);
				player.getInventory().deleteItem(29946, 1);
				player.getInventory().addItem(29947, 1);
				player.setNextGraphics(new Graphics (92));
				player.setNextForceTalk(new ForceTalk("I have combined my key parts to Trio Key."));
				player.getInterfaceManager().closeChatBoxInterface();				
				return; 
				} else  {	
				player.sm("You do not have 350M Coins or a key part.");
				player.getInterfaceManager().closeChatBoxInterface();				
				return;
				}
			} else if (componentId == OPTION_2) {
				player.sm("You declined.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	}
	


	@Override
	public void finish() {
	}

}