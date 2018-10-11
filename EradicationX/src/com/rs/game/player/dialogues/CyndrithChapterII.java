package com.rs.game.player.dialogues;

import com.rs.utils.Utils;

public class CyndrithChapterII extends Dialogue {

	@Override
	public void start() {
		if (player.getEliteChapterII().getSkipDelay() > Utils.currentTimeMillis()) {
			stage = 100;
			sendDialogue("You're currently on skip cooldown. These cooldowns are 30 minutes long.");
			return;
		}
		if (!player.getEliteChapterII().meetsRequirements(player)) {
			stage = 100;
			sendDialogue("You do not meet the requirements for The Elite Chapter: II");
			return;
		} else if (player.getEliteChapterII().getQuestStage() == 0) 
			sendDialogue("You are now beginning: The Elite Chapter: II. This quest cannot be completed on software mode. You must be on OpenGL or DirectX.");
		else if (player.getEliteChapterII().getQuestStage() == 1) {
			stage = 100;
			if (player.getInventory().hasFreeSlots() && !player.getInventory().containsItem(8007, 1))
				player.getInventory().addItem(8007, 1);
			sendNPCDialogue("Cyndrith", 15840, ANNOYED, "Well go then. Talk to your gypsy.");
		} else if (player.getEliteChapterII().getQuestStage() == 2) { 
			stage = 15;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Back so soon?");
		} else if (player.getEliteChapterII().getQuestStage() == 3) {
			stage = 25;
			sendPlayerDialogue(QUESTION, "Are you okay, Cyndrith?");
		} else if (player.getEliteChapterII().getQuestStage() == 4) {
			stage = 38;
			sendNPCDialogue("Cyndrith", 15840, QUESTION, "How'd it go?");
		} else if (player.getEliteChapterII().getQuestStage() == 5) {
			stage = 46;
			sendPlayerDialogue(QUESTION, "Now what?");
		} else if (player.getEliteChapterII().getQuestStage() == 6) {
			stage = 100;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "Meet me downstairs.");
		} else if (player.getEliteChapterII().getQuestStage() == 7) {
			stage = 51;
			sendPlayerDialogue(QUESTION, "Did you go to the crypts?");
		} else if (player.getEliteChapterII().getQuestStage() == 8) { 
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Hey, hope you're using the enchantment nicely.");
			stage = 100;
		}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Welcome back, " + player.getDisplayName() + "!");
			break;
		case 0:
			stage = 1;
			sendPlayerDialogue(HAPPY, "Thanks!");
			break;
		case 1:
			stage = 2;
			sendNPCDialogue("Cyndrith", 15840, QUESTION, "I'm hoping you're enjoying your enchantment?");
			break;
		case 2:
			stage = 3;
			sendPlayerDialogue(HAPPY, "I am, of course.");
			break;
		case 3:
			stage = 4;
			sendNPCDialogue("Cyndrith", 15840, NICE, "That's great to hear.");
			break;
		case 4:
			stage = 5;
			sendPlayerDialogue(SECRETIVE, "Yeah! It's great, but...");
			break;	
		case 5:
			stage = 6;
			sendNPCDialogue("Cyndrith", 15840, QUESTION, "But what?");
			break;
		case 6:
			stage = 7;
			sendPlayerDialogue(QUESTION, "What about the rest of the enchantment?");
			break;
		case 7:
			stage = 8;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "Now, now! Don't get too greedy.");
			break;
		case 8:
			stage = 9;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "I was nice enough to help you obtain the enchantment for the boots. I definitely didn't plan on handing you the rest.");
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(SAD, "What am I supposed to do with just enchanted boots? Both of us know the whole set capabilities won't be unlocked without the rest.");
			break;
		case 10:
			stage = 11;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Don't get all gloomy just yet. I wasn't planning on handing you everything, but I'll help you get the rest if you help me out with some things.");
			break;
		case 11:
			stage = 12;
			sendPlayerDialogue(SECRETIVE, "Or...");
			break;
		case 12:
			stage = 13;
			sendPlayerDialogue(LAUGH, "I'll just go back to Gypsy Aris and make her look back into your past for the rest of the enchantments.");
			break;
		case 13:
			stage = 14;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "Well actually-");
			break;
		case 14:
			stage = 100;
			sendPlayerDialogue(HAPPY, "Nope! I'm done listening to you. I'm going to Varrock right now and talking to her.");
			if (player.getInventory().hasFreeSlots() && !player.getInventory().containsItem(8007, 1))
				player.getInventory().addItem(8007, 1);
			player.getEliteChapterII().setQuestStage(1);
			break;
		case 15:
			stage = 16;
			sendPlayerDialogue(ANGRY, "Trust me, I wouldn't have had to if Dragith came to end Aris' life. She isn't in the condition to talk, now I guess I'm stuck with you.");
			break;
		case 16:
			stage = 17;
			sendNPCDialogue("Cyndrith", 15840, QUESTION, "Did you just say Dragith?");
			break;
		case 17:
			stage = 18;
			sendPlayerDialogue(QUESTION, "Yeah, what's it to you?");
			break;
		case 18:
			stage = 19;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "That bitch is the one responsible for destroying my home.");
			break;
		case 19:
			stage = 20;
			sendPlayerDialogue(QUESTION, "That's why you came to our castle?");
			break;
		case 20:
			stage = 21;
			sendNPCDialogue("Cyndrith", 15840, SAD, "Yes. The zombie invasion left my home in rubble.");
			break;
		case 21:
			stage = 22;
			sendPlayerDialogue(SAD, "I'm sorry to hear that.");
			break;
		case 22:
			stage = 23;
			sendPlayerDialogue(HAPPY, "On the bright side, I killed Dragith.");
			break;
		case 23:
			stage = 24;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "It's not as simple as that. You may think you've killed her, but she'll always be back.");
			break;
		case 24:
			stage = 100;
			end();
			player.getControlerManager().startControler("CyndrithChapterIICutscene");
			break;
		case 25:
			stage = 26;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "Yes, I'm alright. I'm sorry for my actions. I was caught a bit off guard.");
			break;
		case 26:
			stage = 27;
			sendPlayerDialogue(HAPPY, "Is there anything I could do to help? We could try and end Dragith's chaos once and for all.");
			break;
		case 27:
			stage = 28;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "I meant it when I said it's pointless. Neither of us are capable enough to find out how to end her.");
			break;
		case 28:
			stage = 29;
			sendNPCDialogue("Cyndrith", 15840, QUESTION, "You still wanted the rest of the Elite Obsidian, didn't you?");
			break;
		case 29:
			stage = 30;
			sendPlayerDialogue(QUESTION, "Well, yes. You said you'd help me get rest of the enchantments if I helped you with 'some things'?");
			break;
		case 30:
			stage = 31;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "That's right. I can't promise the same enchantment I did on my own armor, but I can probably come up with "
					+ "something very similar.");
			break;
		case 31:
			if (player.getInventory().hasFreeSlots()) {
				stage = 32;
				sendNPCDialogue("Cyndrith", 15840, QUESTION, "Will you do something for me now? While you're working I'll try and see what I can do for another"
						+ " enchantment.");
			} else {
				stage = 100;
				sendDialogue("You need more inventory space for this next step. Please get one free slot and talk to Cyndrith again.");
			}
			break;
		case 32:
			stage = 33;
			sendPlayerDialogue(REGULAR, "I guess I can.");
			break;
		case 33:
			stage = 34;
			player.getInventory().addItem(952, 1);
			sendNPCDialogue("Cyndrith", 15840, SECRETIVE, "Take this spade. You're going to need it.");
			break;
		case 34:
			stage = 35;
			sendPlayerDialogue(AFRAID, "What're you planning on making me do?");
			break;
		case 35:
			stage = 36;
			sendNPCDialogue("Cyndrith", 15840, SAD, "You saw how my home was. Everyone who was there died except me. I can't bare see their remains again. "
					+ "I need you to bury all of the fallen.");
			break;
		case 36:
			stage = 37;
			sendPlayerDialogue(NICE, "It's alright. I can do that.");
			break;
		case 37:
			stage = 100;
			end();
			player.getControlerManager().startControler("CyndrithChapterIISkeletonBurial");
			break;
		case 38:
			stage = 39;
			sendPlayerDialogue(REGULAR, "I buried them. They're at rest.");
			break;
		case 39:
			stage = 40;
			sendNPCDialogue("Cyndrith", 15840, NICE, "Thank you for that.");
			break;
		case 40:
			stage = 41;
			sendPlayerDialogue(HAPPY, "It's fine, really.");
			break;
		case 41:
			stage = 42;
			sendPlayerDialogue(HAPPY, "Did you find out anything on the enchantment?");
			break;
		case 42:
			stage = 43;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "I found a way. And I promise, I'll give you the enchantments. But as I said before, I'll"
					+ " only give you the enchantments in return for your help.");
			break;
		case 43:
			stage = 44;
			sendPlayerDialogue(QUESTION, "That's fair.");
			break;
		case 44:
			stage = 45;
			sendNPCDialogue("Cyndrith", 15840, QUESTION, "Come with me.");
			break;
		case 45:
			stage = 100;
			player.getControlerManager().startControler("CyndrithChapterIICutscene2");
			end();
			break;
		case 46:
			stage = 47;
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "We need to find Dragith and get the missing page.");
			break;
		case 47:
			stage = 48;
			sendNPCDialogue("Cyndrith", 15840, CONFUSED, "I don't know where we could find her, though.");
			break;
		case 48:
			stage = 49;
			sendPlayerDialogue(HAPPY, "I know. She's tried to invade this castle so many times, and she never succeeds.");
			break;
		case 49:
			stage = 50;
			sendPlayerDialogue(REGULAR, "We could probably find her on the ground floor.");
			break;
		case 50:
			stage = 100;
			player.getEliteChapterII().setQuestStage(6);
			sendNPCDialogue("Cyndrith", 15840, ANGRY, "Let's go then! I'll meet you there.");
			break;
		case 51:
			stage = 52;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "Yes.");
			break;
		case 52:
			stage = 53;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "It was the Lumbridge Catacombs. Her crypt was there and it had a small chest. The chest "
					+ "had the page stored in it along with other relics.");
			break;
		case 53:
			stage = 54;
			sendPlayerDialogue(QUESTION, "What does the last page show?");
			break;
		case 54:
			stage = 55;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "It gives information on the staff that killed Denamkut.");
			break;
		case 55:
			stage = 56;
			sendNPCDialogue("Cyndrith", 15840, REGULAR, "It's called the Catalytic Staff. I'll tell you more about it later.");
			break;
		case 56:
			stage = 57;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "Anyways, thanks for helping me out, " + player.getDisplayName() +". I don't know what I would've done without you.");
			break;
		case 57:
			stage = 58;
			sendNPCDialogue("Cyndrith", 15840, HAPPY, "As promised, I'll give you another piece of enchantment. It's for the gloves. Here you go.");
			break;
		case 58:
			stage = 100;
			player.getEliteChapterII().setQuestStage(8);
			player.getEliteChapterII().setComplete(player);
			sendPlayerDialogue(HAPPY, "Thanks! I'll talk to you soon.");
			break;
		default:
			end();
		}
		}

	@Override
	public void finish() {
		
	}

}
