package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.QNames;



public class GypsyAris extends Dialogue {

	private int npcId;
	private EliteChapterOne ec1;
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);	
		if (player.getEliteChapterII().getQuestStage() >= 2) {
			sendPlayerDialogue(SAD, "Aris seems completely out of it after Dragith. I guess I'll just have to go back to Cyndrith.");
			stage = 100;
		} else {
		if (ec1.getQuestStage() == 9)
		sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Oh! You scared me!" },
						IS_NPC, 9361, NEARDEATH);		
		else if (ec1.getQuestStage() == 10) {
			stage = 10;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Ready to tell me the context, are you?" },
							IS_NPC, 9361, QUESTION);		
		} else if (ec1.getQuestStage() == 11) {
			stage = 11;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Ready to tell me the context, are you?" },
							IS_NPC, 9361, QUESTION);
		} else if (ec1.getQuestStage() == 11) {
			stage = 14;
			sendPlayerDialogue(QUESTION, "What was the spell again?");
		} else {
			stage = 100;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Hello" },
							IS_NPC, 9361, HAPPY);	
			}
		}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 1;
			sendPlayerDialogue(CONFUSED, "How the hell did that scare you?");
			break;
		case 1:
			stage = 2;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"I haven't seen anyone in Varrock in years." },
							IS_NPC, 9361, SAD);	
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(REGULAR, "Well... anyway, I'm here because I heard you can look at past memories of people.");		
			break;
		case 3:
			stage = 4;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Memories I can look into! But context, I must have." },
							IS_NPC, 9361, HAPPY);	
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(CONFUSED, "Why are you talking like that?");	
			break;
		case 5:
			stage = 6;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Fine I am talking! Aris doesn't understand what you mean." },
							IS_NPC, 9361, CONFUSED);	
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(REGULAR, "*Being alone all those years really messed her up.*");	
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(QUESTION, "Anyway, what type of context do you need to be able to look into the past?");	
			break;
		case 8:		
			stage = 9;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
				"Time and location Aris must have! Enough that should be." },
				IS_NPC, 9361, REGULAR);	
			break;
		case 9:
			stage = 100;
			ec1.setQuestStage(10);
			sendPlayerDialogue(REGULAR, "I'll see what I can do. I'll be right back.");
			break;
		case 10:
			stage = 100;
			sendPlayerDialogue(REGULAR, "Not yet.");
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(HAPPY, "Yes. He was at the Fremennik Dungeon exactly two years ago.");
			break;
		case 12:
			stage = 13;
			NPC n = World.getNPC(9361);
			n.setNextAnimation(new Animation(1335));
			n.setNextGraphics(new Graphics(1299));
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
					"Okay, you should write this down, piertotum locomotor lacarnum inflamari deletrius." },
					IS_NPC, 9361, REGULAR);	
			break;
		case 13:
			stage = 100;
			player.getEXQuestManager().setQuestStage(QNames.ELITE_CHAPTER_I, 12);
			sendPlayerDialogue(HAPPY, "Thanks!");
			break;
		case 14:
			stage = 100;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
					"Piertotum locomotor lacarnum inflamari deletrius." },
					IS_NPC, 9361, REGULAR);	
			break;
		default:
			end();
		}
		}

	@Override
	public void finish() {
		
	}

}
