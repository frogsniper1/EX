package com.rs.game.player.dialogues;

import com.rs.utils.Utils;

public class CyndrithChapterIII extends Dialogue {

	@Override
	public void start() {
		if (player.getEliteChapterIII().getSkipDelay() > Utils.currentTimeMillis()) {
			stage = 100;
			sendDialogue("You're currently on skip cooldown. These cooldowns are 30 minutes long.");
			return;
		}
		if (!player.getEliteChapterIII().meetsRequirements(player)) {
			stage = 100;
			sendDialogue("You do not meet the requirements for The Elite Chapter: III");
			return;
		} else if (player.getEliteChapterIII().getQuestStage() == 0) {
			sendDialogue("You are now beginning: The Elite Chapter: III.");
		} else if (player.getEliteChapterIII().getQuestStage() == 8) { 
			if (!player.getBank().containsItem(27762) && !player.getInventory().containsItem(27762, 1)) {
				player.getBank().addItem(27762, 1, true);
				sendDialogue("Cyndrith's Enchantments has been restored. It's in your bank.");
			}
			sendDialogue("If you ever lose Cyndrith's Enchantments, you may talk to him to get another one.");
			stage = 100;
		}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendPlayerDialogue(REGULAR, "Hey, Cyndrith. Let's get on with ending Dragith once and for all.");
			break;
		case 0:
			stage = 1;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "Actually, since you've been gone for so long, I decided to take things on my own.");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "I killed her.");
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(AFRAID, "What? How?");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "It's simple, really.");
			break;
		case 4:
			stage = 5;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "I went to her crypt and low and behold, her weak form, sitting there licking her wounds.");
			break;
		case 5:
			stage = 6;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "So I walked up to her and snapped her neck like a twig.");
			break;
		case 6:
			stage = 7;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "Anyway, I won't be needing your help anymore. I don't see why I should go through the trouble of charging up some enchantment scrolls for you.");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(HAPPY, "Please?");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue("Cyndrith", 15840, NICE, "Why didn't you say that before? Sure thing!");
			break;
		case 9:
			stage = 10;
			sendDialogue("Cyndrith goes through his chest and picks up some scrolls.");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Here you go!");
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(HAPPY, "Great, thanks!");			
			break;
		case 12:
			stage = 13;
			sendPlayerDialogue(SAD, "That felt a bit easy, though.");			
			break;
		case 13:
			stage = 14;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Don't worry, these scrolls aren't charged. You'll have to charge them yourself. Have fun with that!");
			break;
		case 14:
			stage = 15;
			sendPlayerDialogue(SAD, "Aw, darn!");			
			break;
		case 15:
			stage = 100;
			player.getEliteChapterIII().setQuestStage(8);
			player.getEliteChapterIII().setComplete(player);
			if (player.getInventory().hasFreeSlots())
				player.getInventory().addItem(27762, 1);
			else {
				sendDialogue("Cyndrith's scrolls were added to your bank.");
				player.getBank().addItem(27762, 1, true);
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
