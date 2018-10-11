package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.utils.ShopsHandler;

public class SuperMan extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		if (player.firsttime == 0) {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello," +player.getDisplayName()+ ". how can i help you ?" }, IS_NPC, npcId, 13955);
	} else {
		stage = 2;
		sendOptionsDialogue("Select an option", "<img=9> Teleports",
				"<img=9> Super Shop", "<img=9> Pet Food Shop", "Restore Health & Special Attack", "Nevermind");

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
				sendOptionsDialogue("Select a Teleport", "<img=9> Super Dragons",
						"<img=9> Sunfreet", "Close");
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();					
				ShopsHandler.openShop(player, 55);
			} else if(componentId == OPTION_3) {
				ShopsHandler.openShop(player, 56);
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
				player.getDialogueManager().startDialogue("Supers");	
			} else if (componentId == OPTION_2) {
				player.getDialogueManager().startDialogue("Sunfreet");		
			} else if (componentId == OPTION_3) {
				end();  
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




