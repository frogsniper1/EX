package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;

public class ExtraBanker extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
	npcId = (Integer) parameters[0];
	if (!player.hasSecondBank()) {
		sendPlayerDialogue(HAPPY, "Hi! I'd like to access my bank, please.");
	} else {
		player.getSecondBank().openBank();
	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(SECRETIVE, "Wait a minute... you're not a banker...");
			break;
		case 1:	
			stage = 2;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"I am! Just not your primary banker."},
							IS_NPC, 45, HAPPY);	
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(CONFUSED, "Hold on. There's more than one branch of a bank?");
			break;
		case 3:
			stage = 4;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Just two. Your primary bank is accessed through every other method. I'm just available here."},
							IS_NPC, 45, HAPPY);	
			break;
		case 4:
			stage = 5;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"I'm useful for instances where you run out of bank space from your primary one. I'm ultimately"
							+ " just providing my services for extra storage."},
							IS_NPC, 45, HAPPY);	
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(HAPPY, "That seems handy. Could come in use for me at some point.");
			break;
		case 6:
			stage = 7;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Yup! Although you do need to pay a one time fee to get access to my bank. It'll cost you 10B Cash."},
							IS_NPC, 45, REGULAR);	
			break;
		case 7:
			stage = 8;
			sendOptionsDialogue("Buy this service?", "Buy a second bank for 10B Cash", "I don't need extra storage");
			break;
		case 8:
			if (componentId == OPTION_1) {
				if (player.getCurrencyPouch().spend100mTicket(100)) {
					player.setSecondBank(true);
					stage = 100;
					sendEntityDialogue(SEND_1_TEXT_CHAT, 
							new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
									"Enjoy! Thank you for your business."},
									IS_NPC, 45, HAPPY);	
				} else {
					stage = 100;
					sendEntityDialogue(SEND_1_TEXT_CHAT, 
							new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
									"You can't afford it. Come back later with 100 100M Tickets."},
									IS_NPC, 45, SAD);	
				}
			} else if (componentId == OPTION_2) {
				stage = 100;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"Ah, alright. If you ever change your mind, I'll be right here."},
								IS_NPC, 45, SAD);	
			}
			break;
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {
	}

}





