package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.minigames.LegendsMinigame;

public class LegendsMinigameD extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello adventurer, can i help you ?" }, IS_NPC, npcId, 9827);
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == -1) {
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I heard about a new minigame",
					"Nevermind.");
		}
		else if(stage == 0) {
			if(componentId == OPTION_1) {
				stage = 1;
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"Yes, Its called LegendsMinigame." }, IS_NPC, npcId, 9827);
			}
			else {
				end();
			}
		}
		else if(stage == 1) {
			stage = 2;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"Okay, i want to try it.",
					"is there any rewards for completing this minigame?",
					"Nevermind.");
		}
		else if(stage == 2) {
			if(componentId == OPTION_1) {
				LegendsMinigame.enterLegendsMinigame(player);
				end();
			} else if(componentId == OPTION_2) {
				stage = 3;
				sendEntityDialogue(SEND_1_TEXT_CHAT,
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,//k listen here u may want to change what rewards it tells k? kk
								"Yes, For completing this minigame you REWARDS." }, IS_NPC, npcId, 9827);
			} else 
				end();
		}
		else if(stage == 3)
		{
			end();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}
