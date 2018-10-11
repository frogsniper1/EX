package com.rs.game.player.dialogues;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.player.Equipment;

public class HeadEnchantments extends Dialogue {
	
	@Override
	public void start() {
			stage = 1;
			if (player.getInventory().containsItem(26128,1) ||
					player.getInventory().containsItem(26130,1) ||
					player.getInventory().containsItem(26132,1))
				sendOptionsDialogue("What would you like to do?", "Enchant Melee Helmet", "Enchant Mage Helmet", "Enchant Ranged Helmet");
			else {
				stage = 100;
				sendDialogue("You need an Obsidian helmet to use this.");
				return;
			}
		}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(26128, 1)) {
					player.getInventory().deleteItem(27765, 1);
					player.getInventory().deleteItem(26128, 1);
					player.setNextGraphics(new Graphics(113));
					player.setNextAnimation(new Animation(713));
					player.getInventory().addItem(Equipment.EOBSIDIAN_WHELM, 1);	
					player.setNextForceTalk(new ForceTalk("Cathorn maxinum lacarnum inflamari deletrius!"));
					player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian helmet.");
				}else{
					stage = 100;
					sendDialogue("You need an Obsidian Warrior helmet to use this.");
					}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(26132, 1)) {
					player.getInventory().deleteItem(27765, 1);
					player.getInventory().deleteItem(26132, 1);
					player.setNextGraphics(new Graphics(113));
					player.setNextAnimation(new Animation(713));
					player.getInventory().addItem(Equipment.EOBSIDIAN_MHELM, 1);	
					player.setNextForceTalk(new ForceTalk("Anubedo delanar lacarnum inflamari deletrius!"));
					player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian helmet.");
				}else{
					stage = 100;
					sendDialogue("You need an Obsidian Mage helmet to use this.");
					}
			} else if (componentId == OPTION_3) {
				if (player.getInventory().containsItem(26130, 1)) {
					player.getInventory().deleteItem(27765, 1);
					player.getInventory().deleteItem(26130, 1);
					player.setNextGraphics(new Graphics(113));
					player.setNextAnimation(new Animation(713));
					player.getInventory().addItem(Equipment.EOBSIDIAN_RHELM, 1);	
					player.setNextForceTalk(new ForceTalk("Cu moren lacarnum inflamari deletrius!"));
					player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian helmet.");
				}else{
					stage = 100;
					sendDialogue("You need an Obsidian Ranger helmet to use this.");
					}
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
