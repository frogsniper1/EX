package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.utils.ShopsHandler;

public class TheEradicator extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		if (player.firsttime == 0) {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Greetings fellow Eradicator, how can i help you ?" }, IS_NPC, npcId, 13955);
	} else {
		stage = 2;
		sendOptionsDialogue("Select an option", "<img=18> Teleports",
				"<img=18> Eradicator Shop", "Turn my title on", "Restore Health & Special Attack", "Nevermind");

	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"What can you do?",
					"Nevermind.");
		}
		else if(stage == 0) {
			if(componentId == OPTION_1) {
				start();
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"Anything related to Eradicators, you come to me. I can teleport you to Eradicator-only bosses, grant you the Eradicator Title, open the Eradicator Shop, and restore your Health, Special Attack, and Prayer Points. " }, IS_NPC, npcId, 13955);
								player.firsttime = 1;
			}
			else {
				end();
			}
		} else if (stage == 2) {
			if(componentId == OPTION_1) {
				stage = 3;
				sendOptionsDialogue("Select a Teleport", "<img=18> Brutal Dragons",
						"<img=18> Tzhaar-Jad", "<img=18> Obsidian Champion", "Close");
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();					
				ShopsHandler.openShop(player, 52);
			} else if(componentId == OPTION_3) {
				player.getAppearence().setTitle(8198212); 
				player.getPackets().sendGameMessage("You have activated your Eradicator Title.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();				
				player.reset();
				player.sm("Your Special attack, Prayer points and health have been restored.");
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();				
		}
		} else if(stage == 3) {
			if(componentId == OPTION_1) {
				player.getDialogueManager().startDialogue("Brutals");				
			} else if (componentId == OPTION_2) {
				player.getDialogueManager().startDialogue("EradJadKing");		
			} else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("EradKing");  
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();					
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();	                
			} 
			}
		}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue




