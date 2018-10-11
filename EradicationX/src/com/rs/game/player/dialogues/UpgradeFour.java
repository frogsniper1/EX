package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class UpgradeFour extends Dialogue {

	public UpgradeFour() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Upgrading to Tzhaar Whip 5 costs 3.5B",
				"Upgrade to Tzhaar Whip 5 for 3.5B",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(26195, 1) && player.getCurrencyPouch().spend100mTicket(35)) {		
				player.getInventory().deleteItem(26195, 1);
				player.getInventory().addItem(26196, 1);
				player.setNextGraphics(new Graphics (92));
				player.setNextForceTalk(new ForceTalk("I have upgraded my Tzhaar Whip 4 to Tzhaar Whip 5."));
				player.getInterfaceManager().closeChatBoxInterface();				
				return; 
				} else  {	
				player.sm("You do not have 35 100M tickets.");
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


