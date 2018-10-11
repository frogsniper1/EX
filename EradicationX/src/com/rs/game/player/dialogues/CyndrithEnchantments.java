package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.player.content.custom.BossHighlight;
import com.rs.utils.Utils;

public class CyndrithEnchantments extends Dialogue {
	
	private int[] power = {25000, 50000, 75000};
	
	@Override
	public void start() {
			stage = 1;
			if (player.getEliteChapterIII().isComplete())
				sendOptionsDialogue("What would you like to do?", "Information", "Use Spell Power", "Check Spell Power", "Withdraw enchantments", "Boss Highlight");
			else {
				player.sm("You must complete Elite Chapter III to interact with this item.");
				return;
			}
		}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 0:
			stage = 1;
			sendOptionsDialogue("What would you like to do?", "Information", "Use Spell Power", "Check Spell Power", "Withdraw enchantments", "Boss Highlight");
			break;
		case 1:
			if (componentId == OPTION_1) {
				stage = 0;
				sendDialogue("This scroll contains enchantments for Elite Obsidian platebody, platelegs, and the three helmets. They are not charged. <col=E32D2D>Spell power</col> is required to be able to get the enchantment scrolls. All bosses drop spell power. Events held by staff may reward you with spell power. Zombie Invasion, and Voting.");
			} else if (componentId == OPTION_2) {
				if (power[player.getSpellTrait()] - player.getSpellPower() > 0) {
					stage = 0;
				} else
					stage = 2;
				sendDialogue("You need " + Utils.formatNumber(power[player.getSpellTrait()]) + " Spell Power for an enchantment scroll. You currently have " + Utils.formatNumber(player.getSpellPower()) + " spell power." +
						(power[player.getSpellTrait()] - player.getSpellPower() > 0 ? " You currently cannot afford an enchantment." : ""));
			} else if (componentId == OPTION_3) {
				stage = 0;
				sendDialogue("You currently have " + Utils.formatNumber(player.getSpellPower()) + " Spell Power.");
			} else if (componentId == OPTION_4) {
				stage = 4;
				sendOptionsDialogue("Which enchantment would you like to withdraw?", "Helmet" + (player.isEnchantHelm() ? " [Unlocked]" : " [Locked]"), 
																					"Body" + (player.isEnchantBody() ? " [Unlocked]" : " [Locked]"), 
																					"Legs" + (player.isEnchantLegs() ? " [Unlocked]" : " [Locked]"));
			} else if (componentId == OPTION_5) {
				stage = 0;
				sendDialogue("The current boss on highlight is " + NPCDefinitions.getNPCDefinitions(BossHighlight.getInstance().getBoss()).getName() + ". This boss will drop 1.5x the amount of spell power. Your boss tasks also apply a 1.5x boost to spell power drops. These effects stack if you have the same task as the boss highlight." );
			}
			break;
		case 2:
			stage = 3;
			sendOptionsDialogue("Would you like to buy an enchantment?", "Helmet", "Platebody", "Platelegs", "No");
			break;
		case 3:
			switch (componentId) {
			case OPTION_1:
				stage = 0;
				if (player.isEnchantHelm()) {
					end();
					player.sm("You've already unlocked this.");
				} else {
				player.setEnchantHelm(true);
				player.setSpellPower(player.getSpellPower() - power[player.getSpellTrait()]);
				player.setSpellTrait(player.getSpellTrait()+1);
				if (player.getInventory().hasFreeSlots())
					player.getInventory().addItem(27765, 1);
				else
					player.getBank().addItem(27765, 1, true);
				World.sendWorldMessage(
						"<img=6><col=ff8c38>News: " + player.getDisplayName()
								+ " has unlocked an enchantment!", false);
				end();
				}
				break;
			case OPTION_2:
				stage = 0;
				if (player.isEnchantBody()) {
					end();
					player.sm("You've already unlocked this.");
				}else {
				player.setEnchantBody(true);
				player.setSpellPower(player.getSpellPower() - power[player.getSpellTrait()]);
				player.setSpellTrait(player.getSpellTrait()+1);
				if (player.getInventory().hasFreeSlots())
					player.getInventory().addItem(27761, 1);
				else
					player.getBank().addItem(27761, 1, true);
				player.sm("Scroll went into your into your inventory or bank.");
				World.sendWorldMessage(
						"<img=6><col=ff8c38>News: " + player.getDisplayName()
								+ " has unlocked an enchantment!", false);
				end();
				}
				break;
			case OPTION_3:
				stage = 0;
				if (player.isEnchantLegs()) {
					end();
					player.sm("You've already unlocked this.");
				}else {
				player.setEnchantLegs(true);
				player.setSpellPower(player.getSpellPower() - power[player.getSpellTrait()]);
				player.setSpellTrait(player.getSpellTrait()+1);
				if (player.getInventory().hasFreeSlots())
					player.getInventory().addItem(27764, 1);
				else
					player.getBank().addItem(27764, 1, true);
				player.sm("Scroll went into your into your inventory or bank.");
				World.sendWorldMessage(
						"<img=6><col=ff8c38>News: " + player.getDisplayName()
								+ " has unlocked an enchantment!", false);
				end();
				}
				break;
			case OPTION_4:
				stage = 1;
				break;
			}
			break;
		case 4:
			if (componentId == OPTION_1) {
				stage = 100;
				if (player.isEnchantHelm()) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(27765, 1);
					else
						player.getBank().addItem(27765, 1, true);
					sendDialogue("Scroll went into your into your inventory or bank.");
				} else {
					stage = 100;
					end();
				}
			} else if (componentId == OPTION_2) {
				stage = 100;
				if (player.isEnchantBody()) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(27761, 1);
					else
						player.getBank().addItem(27761, 1, true);
					sendDialogue("Scroll went into your into your inventory or bank.");
				} else {
					stage = 100;
					end();
				}
			} else if (componentId == OPTION_3) {
				stage = 100;
				if (player.isEnchantLegs()) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(27764, 1);
					else
						player.getBank().addItem(27764, 1, true);
					sendDialogue("Scroll went into your into your inventory or bank.");
				} else {
					end();
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
