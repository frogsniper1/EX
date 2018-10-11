package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.player.Skills;
import com.rs.game.player.content.MaxedUser;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue
// edited by fatal resort and something - EradicationX
public class SkillRack extends Dialogue {

	public SkillRack() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Pick the cape you want to buy.", "Buy Completionist Cape [10M]",
				"Buy Max Cape [2.5M]", "Buy Skiller Cape [2M]", "Buy 200M Master Cape [5M]", "Next Page");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
                MaxedUser.CheckCompletionist(player);	
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) {
				MaxedUser.CheckMaxed(player);
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) {
				MaxedUser.CheckSkiller(player);
				player.getInterfaceManager().closeChatBoxInterface();		
			} else if (componentId == OPTION_4) {
				if (player.chargeMoney(5000000)) {
					if (player.getSkills().getXp(Skills.ATTACK) >= 200000000
							&& player.getSkills().getXp(Skills.STRENGTH) >= 200000000
							&& player.getSkills().getXp(Skills.DEFENCE) >= 200000000
							&& player.getSkills().getXp(Skills.CONSTRUCTION) >= 200000000
							&& player.getSkills().getXp(Skills.HITPOINTS) >= 200000000
							&& player.getSkills().getXp(Skills.RANGE) >= 200000000
							&& player.getSkills().getXp(Skills.MAGIC) >= 200000000
							&& player.getSkills().getXp(Skills.RUNECRAFTING) >= 200000000
							&& player.getSkills().getXp(Skills.FISHING) >= 200000000
							&& player.getSkills().getXp(Skills.AGILITY) >= 200000000
							&& player.getSkills().getXp(Skills.COOKING) >= 200000000
							&& player.getSkills().getXp(Skills.PRAYER) >= 200000000
							&& player.getSkills().getXp(Skills.THIEVING) >= 200000000
							&& player.getSkills().getXp(Skills.DUNGEONEERING) >= 200000000
							&& player.getSkills().getXp(Skills.MINING) >= 200000000
							&& player.getSkills().getXp(Skills.SMITHING) >= 200000000
							&& player.getSkills().getXp(Skills.SUMMONING) >= 200000000
							&& player.getSkills().getXp(Skills.FARMING) >= 200000000
							&& player.getSkills().getXp(Skills.HUNTER) >= 200000000
							&& player.getSkills().getXp(Skills.SLAYER) >= 200000000
							&& player.getSkills().getXp(Skills.CRAFTING) >= 200000000
							&& player.getSkills().getXp(Skills.WOODCUTTING) >= 200000000
							&& player.getSkills().getXp(Skills.FIREMAKING) >= 200000000
							&& player.getSkills().getXp(Skills.FLETCHING) >= 200000000
							&& player.getSkills().getXp(Skills.HERBLORE) >= 200000000) {	
								player.getInventory().addItem(28013, 1);
								player.sm("Nice job! Enjoy your cape!");
								player.getInterfaceManager().closeChatBoxInterface();
								if (player.getAnnouncement() == 0) {
									 World.sendWorldMessage
							("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just achieved <col=FF0000> 5B Experience!", false);
									 player.setNextGraphics(new Graphics(1634));
								}
								player.setAnnouncement(1);
					} else {
						player.sm("You lied to me! You aren't 5B XP :(");
						player.getInterfaceManager().closeChatBoxInterface();
					}
				} else {
					player.sm("Sorry, you can't access this cape yet!");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_5) {
				stage = 2;
				sendOptionsDialogue("Pick the cape you want to buy.", "Buy Skiller Master Cape [10M]",
						"Buy 10b XP Wings [5M]", "Buy Amulet of Completion [5M]", "Close");							
		} 

	} else if (stage == 2) {
		if (componentId == OPTION_1) {
			if (player.chargeMoney(10000000)) {
			if (player.getSkills().getXp(Skills.RUNECRAFTING) >= 200000000
					&& player.getSkills().getXp(Skills.FISHING) >= 200000000
					&& player.getSkills().getXp(Skills.AGILITY) >= 200000000
					&& player.getSkills().getXp(Skills.COOKING) >= 200000000
					&& player.getSkills().getXp(Skills.THIEVING) >= 200000000
					&& player.getSkills().getXp(Skills.MINING) >= 200000000
					&& player.getSkills().getXp(Skills.SMITHING) >= 200000000
					&& player.getSkills().getXp(Skills.FARMING) >= 200000000
					&& player.getSkills().getXp(Skills.HUNTER) >= 200000000
					&& player.getSkills().getXp(Skills.SLAYER) >= 200000000
					&& player.getSkills().getXp(Skills.CRAFTING) >= 200000000
					&& player.getSkills().getXp(Skills.WOODCUTTING) >= 200000000
					&& player.getSkills().getXp(Skills.FIREMAKING) >= 200000000
					&& player.getSkills().getXp(Skills.FLETCHING) >= 200000000
					&& player.getSkills().getXp(Skills.CONSTRUCTION) >= 200000000
					&& player.getSkills().getXp(Skills.HERBLORE) >= 200000000) {		
						player.getInventory().addItem(29948, 1);
						player.sm("Nice job! Enjoy your cape!");
						player.getInterfaceManager().closeChatBoxInterface();
						if (player.getAnnouncement() == 0) {
							 World.sendWorldMessage
					("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just achieved <col=FF0000> a Skiller Master Cape!", false);
							 player.setNextGraphics(new Graphics(1634));
						}
						player.setAnnouncement(1);
			} else {
				player.sm("You need 200M experience in all skills that aren't combat related.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else {
			player.sm("Sorry, you can't access this cape yet!");
			player.getInterfaceManager().closeChatBoxInterface();
		}
		} else if (componentId == OPTION_2) {
			if (player.chargeMoney(5000000)) {
				if (player.check10BRequirements()) {				
						player.getInventory().addItem(27355, 1);
						player.sm("Nice job! Enjoy your wings!");
						player.getInterfaceManager().closeChatBoxInterface();
						if (player.getAnnouncement2() == 0) {
							 World.sendWorldMessage
					("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just achieved <col=FF0000> 10B XP!", false);
							 player.setNextGraphics(new Graphics(1634));
								for (Player players: World.getPlayers()) {
				                    if (players != null && Utils.getDistance(player, players) < 14) {
				                        players.setNextForceTalk(new ForceTalk("Congratulations " + player.getDisplayName() + " on 10B Experience!"));
				                    }
				                }
						}
						player.setAnnouncement2(1);
			} else {
				player.sm("You need 400M experience in all skills.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else {
			player.sm("Sorry, you can't access this cape yet!");
			player.getInterfaceManager().closeChatBoxInterface();
		}		
		} else if (componentId == OPTION_3) {
			if (player.chargeMoney(5000000)) {
				if (player.checkAmuletofCompletion()) {				
						player.getInventory().addItem(6194, 1);
						player.sm("Nice job! Enjoy your wings!");
						player.getInterfaceManager().closeChatBoxInterface();
						if (!player.ammyannounce) {
							 World.sendWorldMessage
					("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just finished <col=FF0000>all achievements!", false);
							 player.setNextGraphics(new Graphics(1634));
								for (Player players: World.getPlayers()) {
				                    if (players != null && Utils.getDistance(player, players) < 14) {
				                        players.setNextForceTalk(new ForceTalk("Congratulations " + player.getDisplayName() + "!"));
				                    }
				                }
						}
						player.ammyannounce = true;
			} else {
				player.sm("You need 400M experience in all skills and must have completed all achievements in the trophy tab.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else {
			player.sm("Sorry, you can't access this amulet yet!");
			player.getInterfaceManager().closeChatBoxInterface();
		}					
		} else if (componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	}
	}
	@Override
	public void finish() {
	}

}
