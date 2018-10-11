package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;

public class CollectMoney extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello, " + player.getDisplayName() + ". You loaned out your rank successfully. Here's your money." }, IS_NPC, 2241, 9844);
		stage = 1;

	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			stage = 3;
			sendDialogue(player.getInventory().hasFreeSlots() ? player.getCollectLoanMoney() +" 100M Tickets were placed in your inventory." : player.getCollectLoanMoney() +" 100M Tickets were placed in your bank.");
			if (player.getInventory().hasFreeSlots() == true) {
				player.getInventory().addItem(2996, player.getCollectLoanMoney());
				player.setCollectLoanMoney(0);
				player.auctionMessage = 0;
			} else {
				player.getBank().addItem(2996, player.getCollectLoanMoney(), true);
				player.setCollectLoanMoney(0);
				player.auctionMessage = 0;
			}		
		} else if(stage == 3) {
			end();
			}
		}

	@Override
	public void finish() {		
	}

}




