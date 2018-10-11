package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class UpgradeSix extends Dialogue {

	public UpgradeSix() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Upgrading to Tzhaar Whip 7 costs 4.5B",
				"Upgrade to Tzhaar Whip 7 for 4.5B.",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(26197, 1) && player.getCurrencyPouch().spend100mTicket(45)) {		
				player.getInventory().deleteItem(26197, 1);
				player.getInventory().addItem(26198, 1);
				player.setNextGraphics(new Graphics (92));
				player.setNextForceTalk(new ForceTalk("I have upgraded my Tzhaar Whip 6 to Tzhaar Whip 7."));
				player.getInterfaceManager().closeChatBoxInterface();				
				return; 
				} else  {	
				player.sm("You do not have 45 100M tickets.");
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


