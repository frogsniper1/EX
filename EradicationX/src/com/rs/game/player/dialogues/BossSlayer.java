package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.game.player.content.BossSlayerTask;
import com.rs.game.player.content.BossSlayerTask.BossTask;
import com.rs.utils.Utils;

public class BossSlayer extends Dialogue {

	int npcId;

	@Override
	public void start() {
		if (!player.isTalkedWithReaper()) {
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9830,
					"Hello, " + player.getDisplayName() + ". There's many corrupt monsters in our realm. "
							+ "Due to the great amount, I could use your help."
							+ " Would you be willing to assist me in reaping their tainted souls?");
		}else 
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9827,
					"You're back, hunter. What is it you request?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			if (!player.isTalkedWithReaper()) {
				stage = 0;
				sendPlayerDialogue(9827, "Who are you?");
			} else {
				stage = 8;
				sendPlayerDialogue(9810,
						"I need to check up on my task.");
			}
			break;
		case 0:
			stage = 1;
			sendEntityDialogue(
					IS_NPC,
					"Grim Reaper",
					12379,
					9810,
					"I'm the boss slayer master of "+Settings.SERVER_NAME+". I give out boss slayer tasks for you to complete, in return for some rewards.");
			break;
		case 1:
			stage = 2;
			sendPlayerDialogue(9827, "May I have a task, Grim Reaper?");
			break;
		case 2:
			stage = 3;
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9827,
					"Yes, let's see here...");
			break;
		case 3:
			if (player.getBossTask() == null) {
				BossSlayerTask.random(player, BossTask.BOSSTASKS);
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9827,
						"Your task is to kill: <col=FA1616>"
								+ player.getBossTask().getTaskAmount() + " "
								+ player.getBossTask().getName().toLowerCase()
								+ "</col>.");
				player.setTalkedWithReaper();
				if (player.getInventory().hasFreeSlots())
					player.getInventory().addItem(26574 ,1);
				else {
					player.sm("A gem has been placed in your bank.");
					player.getBank().addItem(26574, 1, true);
				}
				stage = 7;
			} else {
				BossSlayerTask.random(player, BossTask.BOSSTASKS);
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9827,
						"Your task is to kill: <col=FA1616>"
								+ player.getBossTask().getTaskAmount() + " "
								+ player.getBossTask().getName().toLowerCase()
								+ "</col>.");
				player.setTalkedWithReaper();
				if (player.getInventory().hasFreeSlots())
					player.getInventory().addItem(26574 ,1);
				else
					player.getBank().addItem(26574, 1, true);
				stage = 7;
			}
			break;
		case 4:
			stage = 7;
			sendPlayerDialogue(9810, "Thank you.");
			break;
		case 7: /* Offical end of Dialogue */
			end();
			break;
		case 8:
			stage = 10;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"I've finished my Task!", "Can you change my Task?",
					"I don't remember my Task.");
			break;
		case 10:
			switch (componentId) {
			case OPTION_1:
				stage = 11;
				sendPlayerDialogue(9845,
						"I have completed the task you assigned me!");
				break;
			case OPTION_2:
				stage = 12;
				sendPlayerDialogue(9810,
						"I would like to receive a new task from you.");
				break;
			case OPTION_3:
				stage = 21;
				sendPlayerDialogue(9827,
						"I can't seem to recall the task you gave me.");
				break;
			}
			break;
		case 11:
			stage = 13;
			if (player.getBossTask() == null) {
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9827,
						"Well done. You're a truly dedicated hunter. Would you like to have a new task?");
			} else {
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
						"No you haven't, foolish hunter.");
				stage = 7;
			}
			break;
		case 12:
			stage = 14;
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
					"Okay, I can assign a new task for a fee of 100M.");
			break;
		case 13:
			stage = 15;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "Yes", "No");
			break;
		case 14:
			stage = 16;
			sendOptionsDialogue("Do you want to change your task for 100M?",
					"Yes", "No, I'm fine with the task I have.");
			break;
		case 15:
			switch (componentId) {
			case OPTION_1:
				stage = 17;
				sendPlayerDialogue(9810, "Yes, please.");
				break;
			case OPTION_2:
				stage = 18;
				sendPlayerDialogue(9810, "No.");
				break;
			}
			break;
		case 16:
			switch (componentId) {
			case OPTION_1:
				stage = 19;
				int num = Utils.random(2);
				if (num == 3)
					sendPlayerDialogue(9810, "Yes, please.");
				else 
					sendPlayerDialogue(9877, "What a rip off... Fine, I'll buy it.");
				break;
			case OPTION_2:
				stage = 20;
				sendPlayerDialogue(9810, "No, I'm fine with the task I have.");
				break;
			}
			break;		
		case 17:
			BossSlayerTask.random(player, BossTask.BOSSTASKS);
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
					"Your new task is to kill: <col=FA1616> "
							+ player.getBossTask().getTaskAmount() + " "
							+ player.getBossTask().getName().toLowerCase() + ".");
			stage = 100;
			break;
		case 18:
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
					"Okay, see you later.");
			stage = 7;
			break;
		case 19:
			if (player.chargeMoney(100000000)) {
				BossSlayerTask.random(player, BossTask.BOSSTASKS);
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
						"Your new task is to kill "
								+ player.getBossTask().getTaskAmount() + " "
								+ player.getBossTask().getName().toLowerCase()
								+ ".");
				stage = 100;
			} else {
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
						"You don't have 100M gold, come back later!");
				stage = 7;
			}
			break;
		case 20:
			sendEntityDialogue(
					IS_NPC,
					"Grim Reaper",
					12379,
					9827,
					"Alright, suit yourself "+player.getDisplayName()+".");
			stage = 7;
			break;
		case 21:
			if (player.getBank().containsItem(26574))
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810, "Please use the Reaper's gem I provided you. It's in your bank.");
			else if (player.getInventory().containsItem(26574, 1))
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810, "Please use the Reaper's gem I provided you. It's in your inventory.");	
			else {
			sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810, "Please use the Reaper's gem I have provided you. It's in your inventory.");		
			player.getInventory().addItem(26574, 1);
			}
			stage = 23;
			break;
		case 23:
			sendPlayerDialogue(9845,
					"That's helpful. Thank you.");
			stage = 7;
			break;
		case 100:
			stage = 101;
			sendOptionsDialogue("Select an Option",
					"Can you triple the amount I need to kill?",
					"That's all, thanks.");
			break;
		case 101:
			switch (componentId) {
			case OPTION_1:
				stage = 7;
				sendEntityDialogue(IS_NPC, "Grim Reaper", 12379, 9810,
						"Very well then. You now have to kill: <col=FA1616> "
								+ player.getBossTask().tripleTaskAmount() + " "
								+ player.getBossTask().getName().toLowerCase() + ". You will receive triple the reward for finishing your task.");
				break;
			case OPTION_2:
				end();
				break;
			}	
		}
		
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}