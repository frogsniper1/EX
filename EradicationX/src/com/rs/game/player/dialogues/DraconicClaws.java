package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class DraconicClaws extends Dialogue {

	public DraconicClaws() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Upgrading to Draconic costs 25K Eradicated Seals",
				"Upgrade to Draconic for 25K Eradicated Seals",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(12852, 25000) && player.getInventory().containsItem(26705, 1)) {		
				player.getInventory().deleteItem(26705, 1);
				player.getInventory().deleteItem(12852, 25000);
				player.getInventory().addItem(28045, 1);
				player.sm("<col=EDFAE2>You have upgraded Dragon Claws to Draconic.");
				player.getInterfaceManager().closeChatBoxInterface();				
				return; 
				}else {	
				player.sm("You do not have 25K Eradicated Seals, prove yourself by obtaining them.");
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


