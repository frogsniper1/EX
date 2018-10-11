package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class UpgradeOne extends Dialogue {

	public UpgradeOne() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Upgrading to Tzhaar Whip 2 costs 2B.",
				"Upgrade to Tzhaar Whip 2 for 2B.",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(26192, 1) && player.getCurrencyPouch().spend100mTicket(20)) {		
				player.getInventory().deleteItem(26192, 1);
				player.getInventory().addItem(26193, 1);
				player.setNextGraphics(new Graphics (92));
				player.setNextForceTalk(new ForceTalk("I have upgraded my Tzhaar Whip 1 to Tzhaar Whip 2."));
				player.getInterfaceManager().closeChatBoxInterface();				
				return; 
				} else  {	
				player.sm("You do not have 20 100M tickets.");
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


