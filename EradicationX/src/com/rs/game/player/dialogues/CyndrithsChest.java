package com.rs.game.player.dialogues;

import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.QNames;

public class CyndrithsChest extends Dialogue {
	
	private EliteChapterOne quest;
	
	@Override
	public void start() {
		quest = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
		quest.refresh(player);
		if (player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I).isComplete()) {
			stage = 22;
			sendOptionsDialogue("What would you like to do?", "Buy Elite Scrolls", "Skip Quest Stages");//1800000
		} else {
		if (player.getQuestStage(QNames.ELITE_CHAPTER_I) < 6 || quest.isDidEmptyScroll()) {
			stage = 100;
			sendPlayerDialogue(CONFUSED, "I shouldn't be touching his chest without any reason.");
		} else if (player.getQuestStage(QNames.ELITE_CHAPTER_I) >= 6 && !quest.isDidEmptyScroll() && !quest.isFinishedList() &&
				!player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I).isComplete()) {
			if (player.getInventory().hasFreeSlots()) {
			stage = 1;
			sendPlayerDialogue(REGULAR, "There's a pin combination protecting the chest. Guess I'll give it a go.");
			} else {
				stage = 100;
				sendDialogue("Please free up an inventory space.");
			}
		}
		}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			stage = 2;
			sendOptionsDialogue("Try and get the chest open", "4123", "5534", "6895", "7384", "1337");
			break; 
		case 2:
			switch (componentId) {
			case OPTION_1:
				if (quest.getPinCombination() == 1)
					stage = 9;
				else
					stage = 3;
				sendDialogue("*click*");
				break;
			case OPTION_2:
				if (quest.getPinCombination() == 2)
					stage = 9;
				else
					stage = 3;
				sendDialogue("*click*");
				break;
			case OPTION_3:
				if (quest.getPinCombination() == 3)
					stage = 9;
				else
					stage = 3;
				sendDialogue("*click*");
				break;
			case OPTION_4:
				if (quest.getPinCombination() == 4)
					stage = 9;
				else
					stage = 3;
				sendDialogue("*click*");
				break;
			case OPTION_5:
				if (quest.getPinCombination() == 5)
					stage = 9;
				else
					stage = 3;
				sendDialogue("*click*");
				break;
			}
			break; 
		case 3:
			stage = 4;
			sendPlayerDialogue(HAPPY, "Yay!");
			break;
		case 4:
			stage = 5;
			sendDialogue("... *click* *click* *click* *click* *click* *click*");
			break;
		case 5:	
			stage = 6;
			sendPlayerDialogue(CONFUSED, "Huh?");
			break;
		case 6:
			stage = 7;
			sendDialogue("*CLUNK*");
			break;
		case 7:
			stage = 8;
			sendPlayerDialogue(AFRAID, "*Gulp*");
			break;
		case 8:
			player.applyHit(new Hit(player, player.getHitpoints(), HitLook.REGULAR_DAMAGE));
			player.setNextGraphics(new Graphics(506));
			player.sm("I'm guessing that wasn't the right combination.");
			player.stopAll();
			break;
		case 9:
			stage = 10;
			sendPlayerDialogue(HAPPY, "Yay!");
			break;	
		case 10:
			stage = 11;
			sendDialogue("You pull open the chest and find an empty scroll of enchantment. You check off something from Cyndrith's list.");
			EliteChapterOne quest = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
			quest.setDidEmptyScroll(true);
			player.getInventory().addItem(27739, 1);
			player.getInventory().addItem(27740, 1);
			break;
		case 20:
			stage = 21;
			sendOptionsDialogue("Buy scroll for 20B Cash?", "Boots Scroll" + (!player.getEliteChapterI().isComplete() ? " [LOCKED]" : "") 
					, "Gloves Scroll" + (!player.getEliteChapterII().isComplete() ? " [LOCKED]" : ""), 
					 "Cyndrith's Enchantments" + (!player.getEliteChapterIII().isComplete() ? " [LOCKED]" : "")
					, "Nevermind maaan" );
			break;
		case 21:
			switch (componentId) {
			case OPTION_1:
				stage = 100;
				if (!player.getEliteChapterI().isComplete()) {
					sendDialogue("This scroll is currently locked.");
					break;
				}
				if (player.getCurrencyPouch().spend100mTicket(200)) {
					player.getInventory().addItem(27743, 1);
					end();
				} else {
					sendDialogue("You can't afford another scroll.");
				}
				break;
			case OPTION_2:
				stage = 100;
				if (!player.getEliteChapterII().isComplete()) {
					sendDialogue("This scroll is currently locked.");
					break;
				}
				if (player.getCurrencyPouch().spend100mTicket(200)) {
					player.getInventory().addItem(27747, 1);
					end();
				} else {
					sendDialogue("You can't afford another scroll.");
				}
				break;
			case OPTION_3:
				stage = 100;
				if (!player.getEliteChapterIII().isComplete()) {
					sendDialogue("This scroll is currently locked.");
					break;
				}
				if (player.getCurrencyPouch().spend100mTicket(200)) {
					player.getInventory().addItem(27762, 1);
					end();
				} else {
					sendDialogue("You can't afford another scroll.");
				}
				break;
			case OPTION_4:
				end();
				stage = 100;
				break;
			}
			break;
		case 22:
			switch (componentId) {
			case OPTION_1:
				stage = 20;
				sendDialogue("Cyndrith stored some extra copies of the scroll of enchantment. Would you like to buy another scroll for 20B?");
				break;
			case OPTION_2:
				stage = 23;
				sendDialogue("Skipping quest stages skips a chunk of a quest part. This feature is there for people who're stuck on quest parts "
						+ "because they can't get out of Software/Safe Mode. There will be a 30 minute cooldown for doing the quest once you buy it."
						+" Skipping a stage costs 2.5B Cash.");
				break;
			}
			break;
		case 23:
			stage = 24;
			sendOptionsDialogue("Skip a stage in chapter II?", "Yes, give 2.5b cash", "No");
			break;
		case 24:
			switch (componentId) {
			case OPTION_1:
				if (player.getCurrencyPouch().canAfford100mTicket(25)) {
					switch (player.getEliteChapterII().getQuestStage()) {
					case 0:
					case 5:
					case 6:
					case 7:
					case 8:
					default:
						stage = 100;
						sendDialogue("You can't skip this stage. Only specific stages can be skipped.");
						break;
					case 1:
					case 2:
					case 3:
					case 4:
						stage = 100;
						player.getCurrencyPouch().spend100mTicket(25);
						sendDialogue("You skipped a stage.");
						player.getEliteChapterII().addSkipDelay(1800000);
						player.getEliteChapterII().setQuestStage(player.getEliteChapterII().getQuestStage()+1);
						break;
					}
				} else {
					stage = 100;
					sendDialogue("You can't afford to skip a stage.");
				}
				break;
			case OPTION_2:
				end();
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
