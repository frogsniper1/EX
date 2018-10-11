package com.rs.game.player.dialogues;


import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.QNames;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.item.Item;

public class CyndrithReveal extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		if (player.getSkills().getTotalXp() < 5000000000L) {
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Get away from me, noob!"},
							IS_NPC, 15840, AFRAID);		
			stage = 35;
		} else {
		EliteChapterOne ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
		ec1.refresh(player);
		if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 7 && ec1.isFinishedList())
				player.setQuestStage(QNames.ELITE_CHAPTER_I, 8);
		if (ec1.isComplete()) {
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Hey, hope you're using the enchantment nicely." },
							IS_NPC, 15840, HAPPY);
			stage = 100;
		} else {
		if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 2
				|| player.getQuestStage(QNames.ELITE_CHAPTER_I) == 3) {
			player.setQuestStage(QNames.ELITE_CHAPTER_I, player.getQuestStage(QNames.ELITE_CHAPTER_I)+1);
			stage = 100;
			sendDialogue("Cyndrith pretends as if you're not even there and looks at blank space.",
					"Perhaps you should talk to him later.");
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 4) {
			stage = 16;
			sendPlayerDialogue(ANNOYED, "...");
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 5) {
			stage = 26;
			sendPlayerDialogue(CONFUSED, "What's this?");
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) >= 6 &&
				player.getQuestStage(QNames.ELITE_CHAPTER_I) < 8) {
			stage = 30;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"How goes the list?" },
							IS_NPC, 15840, QUESTION);			
			if (!player.getInventory().containsItem(6196, 1) && !player.getBank().containsItem(6196)) {
				if (player.getInventory().hasFreeSlots()) {
					player.getInventory().addItem(6196, 1);
				} else {
					stage = 100;
					sendDialogue("Please make inventory space and talk to Cyndrith again.");
				}
			}	
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 8) {
			stage = 36;
			if (!player.getInventory().containsItem(13328, 1) && !player.getBank().containsItem(13328))
				player.getInventory().addItem(13328, 1);
			if (!player.getInventory().containsItem(27739, 1) && !player.getBank().containsItem(27742))
				player.getInventory().addItem(27739, 1);
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"How goes the list?" },
							IS_NPC, 15840, QUESTION);
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 9 || player.getQuestStage(QNames.ELITE_CHAPTER_I) == 11) {	
			stage = 100;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Go on then, talk to Gypsy Aris and see if she can help." }, IS_NPC, 15840, REGULAR);
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 10) {	
			stage = 48;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"What'd the gypsy say?" }, IS_NPC, 15840, QUESTION);
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) == 12) {	
			stage = 53;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Did the gypsy find out the enchantment?" }, IS_NPC, 15840, QUESTION);
		} else {
		sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello. What do you want?" }, IS_NPC, 15840, QUESTION);
		}
		}
		}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(CONFUSED, "Who are you?");
			break;
		case 0:
			stage = 1;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"I'm Cyndrith. Who are you?" }, IS_NPC, 15840, HAPPY);
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(HAPPY, "I'm " + player.getDisplayName() + ".");
			break;
		case 2:
			stage = 3;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Pleasure to meet you." }, IS_NPC, 15840, HAPPY);
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue("Select an Option", "What are you doing here?", "Tell me more about yourself.", 
					"What's that armor you're wearing?");
			break;
		case 4:
			switch (componentId) {
			case OPTION_1:
				stage = 5;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"I needed some time away from my home. There's not many other areas that are"
								+ " populated after the zombie invasion. I heard about this area, and figured I'd travel here." },
								IS_NPC, 15840, REGULAR);				
				break;
			case OPTION_2:
				stage = 7;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"Well, you know my name. What more would you need to know?" },
								IS_NPC, 15840, CONFUSED);					
				break;
			case OPTION_3:
				stage = 11;
				sendPlayerDialogue(QUESTION, "What's that armor you're wearing?");
				break;
			}
			break;
		case 5:
			stage = 6;
			sendPlayerDialogue(QUESTION, "Why do you need time away from your home?");
			break;
		case 6:
			stage = 3;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Complications." },
							IS_NPC, 15840, SECRETIVE);	
			break;	
		case 7:
			stage = 8;
			sendPlayerDialogue(REGULAR, "For starters, you could tell me more about the place you've come from.");
			break;
		case 8:
			stage = 9;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"..." },
							IS_NPC, 15840, SAD);	
			break;
		case 9:
			stage = 10;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"That is none of your business." },
							IS_NPC, 15840, ANGRY);	
			break;
		case 10:
			stage = 3;
			sendPlayerDialogue(AFRAID, "Okay! I won't ask again.");
			break;
		case 11:
			stage = 12;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Do you like it?" },
							IS_NPC, 15840, NICE);	
			break;
		case 12:
			stage = 13;
			sendOptionsDialogue("Do you like it?", "Yes", "No");
			break;
		case 13:
			switch (componentId) {
			case OPTION_1:
				stage = 19;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"How couldn't you? Not only does it provide the greatest advantages in combat, it looks amazing!" },
								IS_NPC, 15840, HAPPY);	
				break;
			case OPTION_2:
				stage = 15;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
								"Bah! I'm sure your " + (player.getEquipment().getChestId() != -1 ? new Item(player.getEquipment().getChestId()).getName() + " is "
										: "clothes are ") + "a million times worse." },
								IS_NPC, 15840, ANGRY);					
				break;
			}
			break;
		case 15:
			stage = 100;
			sendDialogue("Cyndrith pretends as if you're not even there and looks at blank space.",
					"Perhaps you should talk to him later.");
			player.setQuestStage(QNames.ELITE_CHAPTER_I, 2);
			break; 
		case 16:
			stage = 17;
			sendPlayerDialogue(SECRETIVE, "Your armor looks amazing.");
			break;
		case 17:
			stage = 18;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"It does, doesn't it?" },
							IS_NPC, 15840, HAPPY);
			break;
		case 18:
			stage = 19;
			sendPlayerDialogue(REGULAR, "Yep.");
			break;
		case 19:
			stage = 20;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Anyways, I'm sure you'd like to know how to get a set like mine." },
							IS_NPC, 15840, HAPPY);			
			break;
		case 20:
			stage = 21;
			sendPlayerDialogue(HAPPY, "Sure.");
			break;
		case 21:
			stage = 22;
			sendDialogue("Cyndrith takes out a papyrus from his bag and starts writing a list.");
			break;
		case 22:
			stage = 23;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Hmm... I'll get you started with the enchantment for the boots." },
							IS_NPC, 15840, CONFUSED);			
			break;
		case 23:
			stage = 24;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Alright!" },
							IS_NPC, 15840, HAPPY);				
			break;
		case 24:
			player.setQuestStage(QNames.ELITE_CHAPTER_I, 5);
			if (player.getInventory().hasFreeSlots()) {
				stage = 25;
				player.getInventory().addItem(6196, 1);
				sendDialogue("Cyndrith hands you a quickly written list on a papyrus.");
			} else {
				stage = 100;
				sendDialogue("Please make inventory space and talk to Cyndrith again.");
			}
			break;
		case 25:
			stage = 26;
			sendPlayerDialogue(CONFUSED, "What's this?");
			break;
		case 26:
			stage = 27;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"That's the list of things you need to do to get a pair of Obsidian boots enchanted." },
							IS_NPC, 15840, REGULAR);		
			if (!player.getInventory().containsItem(6196, 1) && !player.getBank().containsItem(6196)) {
				if (player.getInventory().hasFreeSlots()) {
					player.getInventory().addItem(6196, 1);
				} else {
					stage = 100;
					sendDialogue("Please make inventory space and talk to Cyndrith again.");
				}
			}		
			break;
		case 27:
			stage = 28;
			sendPlayerDialogue(QUESTION, "So, what you're wearing is just a modified set of Obsidian?");
			break;
		case 28:
			stage = 29;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Yes. Since the Obsidian set is already a powerful piece of equipment, It'd only make "
							+ "sense to build onto it. It took me ages to figure out this enchantment, and it does well." },
							IS_NPC, 15840, REGULAR);				
			break;
		case 29:
			stage = 100;
			player.setQuestStage(QNames.ELITE_CHAPTER_I, 6);
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Go on then, read it." },
							IS_NPC, 15840, HAPPY);				
			break;
		case 30:
			if (player.getEXQuestManager().getQuestStage(QNames.ELITE_CHAPTER_I) > 5) {
				stage = 31;
				sendPlayerDialogue(SAD, "I'm still working on it.");
			}
			break;
		case 31:
			stage = 32;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Well, keep at it! I'm sure you'll find the enchantment handy once it's done." },
							IS_NPC, 15840, HAPPY);						
			break;
		case 32:
			if (player.getInventory().containsItem(26126, 1) || player.getEquipment().getBootsId() == 26126 ||
				player.getBank().containsItem(26126)) {
				sendPlayerDialogue(HAPPY, "I sure will. I've got a pair of Obsidian boots waiting to be enchanted.");
				stage = 34;
			} else {
				stage = 33;
				sendPlayerDialogue(SAD, "I'm not so sure about that, I don't even have Obsidian boots to enchant.");
			}
			break;
		case 33:
			stage = 100;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Oh, that's a shame. Surely you'll obtain some in the future." },
							IS_NPC, 15840, CONFUSED);						
			break;
		case 34:
			stage = 100;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Great!"},
							IS_NPC, 15840, NICE);				
			break;
		case 35:
			stage = 100;
			sendDialogue("Cyndrith doesn't talk to anyone that doesn't have 5 Billion+  total experience.");
			break;
		case 36:
			stage = 37;
			sendPlayerDialogue(HAPPY, "I finished it!");
			break;
		case 37:
			stage = 38;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Great! Now you're ready for the next step." },
							IS_NPC, 15840, NICE);		
			break;
		case 38:
			stage = 39;
			sendPlayerDialogue(QUESTION, "What might that be?");
			break;
		case 39:
			stage = 40;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"The ink needs to be used on the scroll to write the incantation needed to "
							+ "enchant the obsidian boots."},
							IS_NPC, 15840, REGULAR);		
			break;
		case 40:
			stage = 41;
			sendPlayerDialogue(CONFUSED, "Right... What's the enchantment?");
			break;
		case 41:
			stage = 42;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"I'm not entirely sure. I hadn't written it down anywhere when I made the enchantment for myself."},
							IS_NPC, 15840, SECRETIVE);				
			break;
		case 42:
			stage = 43;
			player.getPackets().sendGlobalConfig(184, 1000);
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				public void run() {
					count++;
					if (count == 2)
						player.getPackets().sendGlobalConfig(184, 0);
				}
	    	}, 0, 1);
			player.setNextForceTalk(new ForceTalk("What!?"));
			NPC n = World.getNPC(15972);
			n.setNextAnimation(new Animation(2836));
			sendPlayerDialogue(ANGRY, "Why'd you make me do the list then?!");
			break;
		case 43:
			stage = 44;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Calm down! There's still some ways to get the enchantment."},
							IS_NPC, 15840, AFRAID);
			break;
		case 44:
			stage = 45;
			sendPlayerDialogue(QUESTION, "Like what?");
			break;
		case 45:
			stage = 46;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Gypsy Aris can use her skills to view the time when I enchanted my Obsidian boots. You "
							+ "should be able to find out what I said if she can."},
							IS_NPC, 15840, REGULAR);
			break;
		case 46:
			stage = 47;
			sendPlayerDialogue(QUESTION, "Where can I find her?");
			break;
		case 47:
			player.setQuestStage(QNames.ELITE_CHAPTER_I, 9);
			stage = 100;
			player.getInventory().addItem(8007, 10);
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
							"Teleport to Varrock. You'll find her there."},
							IS_NPC, 15840, REGULAR);			
			break;
		case 48:
			stage = 49;
			sendPlayerDialogue(REGULAR, "She said she needs some context about when you did it. Also, she's a loon.");
			break;
		case 49:
			stage = 50;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
				"Oh, alright. Well, I've been further even more decided to use even go need "
				+ "to do look more as anyone can. Can you really be far even as decided half as much to use "
				+ "go wish for that? My guess is that when one really been far even as decided once to use"
				+ " even go want power. In that case, I made my go-to look for that like make as much as possibl-"},
				IS_NPC, 15840, CONFUSED);	
			break;
		case 50:
			stage = 51;
			sendPlayerDialogue(ANGRY, "What are you talking about?! Just tell me when and where you were!");
			break;
		case 51:
			stage = 52;
			sendEntityDialogue(SEND_1_TEXT_CHAT, 
					new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
					"You could've just said that. I was at the Fremennik Dungeon around two years ago."},
					IS_NPC, 15840, REGULAR);				
			break;
		case 52:
			stage = 100;
			player.getEXQuestManager().setQuestStage(QNames.ELITE_CHAPTER_I, 11);
			sendPlayerDialogue(REGULAR, "Great. I'll go and tell Gypsy Aris.");
			break;
		case 53:
			stage = 54;
			sendOptionsDialogue("What was the enchantment?", "Piertotum locomotor lacarnum inflamari deletrius", 
					"Lataf elkcaws souyortsed yeknomyriah refeed", "Locomotor piertotum lacarnum inflamari deletrius",
					"Deletrius locomotor inflamari lacarnum piertotum", "Carlem aber camerinthum purchai gabindo");
			break;
		case 54:
			switch (componentId) {
			case OPTION_1:
				stage = 100;
				player.getEXQuestManager().setQuestStage(QNames.ELITE_CHAPTER_I, 13);
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"That sounds about right. Go ahead and write it down on the scroll."},
						IS_NPC, 15840, HAPPY);				
				break;
			case OPTION_2:
				stage = 53;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"That doesn't sound right. Are you sure that's right?"},
						IS_NPC, 15840, QUESTION);	
				break;
			case OPTION_3:
				stage = 53;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"That doesn't sound right. Are you sure that's right?"},
						IS_NPC, 15840, QUESTION);	
				break;
			case OPTION_4:
				stage = 53;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"That doesn't sound right. Are you sure that's right?"},
						IS_NPC, 15840, QUESTION);	
				break;
			case OPTION_5:
				stage = 53;
				sendEntityDialogue(SEND_1_TEXT_CHAT, 
						new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"That doesn't sound right. Are you sure that's right?"},
						IS_NPC, 15840, QUESTION);	
				break;
			}
			break;
		default:
			end();
		}
		}

	@Override
	public void finish() {
		
	}

}
