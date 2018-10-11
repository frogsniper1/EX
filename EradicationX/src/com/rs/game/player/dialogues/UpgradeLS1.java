package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class UpgradeLS1 extends Dialogue {

	public UpgradeLS1() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Upgrading to Lightning Staff 2 costs 2B.",
				"Upgrade to Lightning Staff 2 for 2B.",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(25472, 1) && player.getCurrencyPouch().spend100mTicket(20)) {		
				player.getInventory().deleteItem(25472, 1);
				player.getInventory().addItem(25473, 1);
				player.setNextGraphics(new Graphics (92));
				player.setNextForceTalk(new ForceTalk("I have upgraded my Lightning Staff 1 to Lightning Staff 2."));
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


