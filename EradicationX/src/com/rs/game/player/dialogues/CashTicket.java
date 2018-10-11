package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class CashTicket extends Dialogue {

	public CashTicket() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a 100M Ticket?",
				"Yes.",
				"No.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if(player.getInventory().containsItem(995, 100000000)){		
				player.getInventory().deleteItem(995, 100000000);
				player.getInventory().addItem(2996, 1);
				player.sm("<col=EDFAE2>You trade in 100,000,000 coins for a 100M Ticket.");
				player.getInterfaceManager().closeChatBoxInterface();				
				return; 
				}else if(!player.getInventory().containsItem(995, 100000000)){	
				player.sm("Not enough money! If your money is in your pouch, place it in your inventory.");
				player.getInterfaceManager().closeChatBoxInterface();				
				return;
			} else if (componentId == OPTION_2) {
				player.sm("You declined.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	}
	


}	@Override
	public void finish() {
	}

}


