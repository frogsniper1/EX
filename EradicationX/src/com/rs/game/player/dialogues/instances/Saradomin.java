package com.rs.game.player.dialogues.instances;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.dialogues.Dialogue;

/* created by Fatal Resort - EradicationX */
public class Saradomin extends Dialogue {
	

	public void setPerma(boolean bool) {
		player.setPermaSaradomin(bool);
	}
	private String getString = "SaradominInstance";
	public void startControler() {
		player.getControlerManager().startControler("SaradominInstance");	
	}
	public Saradomin() {
	}
	
	@Override
	public void start() {
		player.instanceDialogue = "Saradomin";
		player.setOutside(new WorldTile(2923, 5258, 0));
		stage = 1;
		sendOptionsDialogue("What would you like to do?",
				"Enter boss room.", "Start a private instance.", "Join an existing instance.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		boolean getPerma = player.getPermaSaradomin();
		if (stage == 1) {	
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2920, 5254, 0));
			} else if (componentId == OPTION_2) {
				if (getPerma == true) {
					sendDialogue("A new session will start once you",
							"continue for 1 hour. Do you wish to start?",
							"Note: You only lose the instance if the exit portal is clicked.",
							"If you die, you lose 10 minutes and spawn back into the zone.");
				stage = 2;					
				} else {
				sendDialogue("Starting a new session against this foe cost",
						"200,000,000 coins for 1 hour. Do you wish to pay?",
						"Note: You only lose the instance if the exit portal is clicked.",
						"If you die, you lose 10 minutes and spawn back into the zone.");
				stage = 2;
				}
			} else if (componentId == OPTION_3) {	
				player.getTemporaryAttributtes().put("instance_name", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the leader's name:" });									
			}

	} else if (stage == 2) {	
		sendOptionsDialogue("Choose a spawn rate", "Fast", "Medium", "Slow");
		stage = 9;					
		} else if (stage == 3) {
			sendOptionsDialogue("Pay 200M for 1 hour?", "Pay [200M]", "Don't Pay.", "Permanently unlock [4B]");
			stage = 7;
		} else if (stage == 9) {
			if (componentId == OPTION_1) {
				player.setSpawnRate(5);
				sendDialogue("Spawn Rate set to fast.");
				if (getPerma == true) {
					stage = 10;
				} else {
					stage = 3;
				}
			} else if (componentId == OPTION_2) {
				player.setSpawnRate(35);
				sendDialogue("Spawn Rate set to medium.");
				if (getPerma == true) {
					stage = 10;
				} else {
					stage = 3;
				}
			} else if (componentId == OPTION_3) {
				player.setSpawnRate(48);
				sendDialogue("Spawn Rate set to slow.");
				if (getPerma == true) {
					stage = 10;
				} else {
					stage = 3;
				}
			}		
		} else if (stage == 10) {
			sendOptionsDialogue("Choose a level of protection", "Only I can enter.", "Anyone can enter.", "PIN protected.");
			stage = 8;
		} else if (stage == 8) {
			if (componentId == OPTION_1) {
					player.setInstancePin(1);
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					startControler();
					end();
			} else if (componentId == OPTION_2) {
					player.setInstancePin(2);
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					startControler();
					end();
			} else if (componentId == OPTION_3) {
				player.setInstanceControler(getString);
				player.getTemporaryAttributtes().put("startfreeinstancepin", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter your new pin:" });							
			}			
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				if (player.chargeMoney(200000000)) {
					player.setInstancePin(1);
					player.sm("200,000,000 coins were deducted from your inventory.");
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					startControler();
					end();
				} else if (player.getCurrencyPouch().spend100mTicket(2)) {
					player.setInstancePin(1);
					 
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					startControler();
					end();
				} else {
					player.sm("Sorry, but you don't have enough money.");
					end();
				}				
			} else if (componentId == OPTION_2) {
				if (player.chargeMoney(200000000)) {
					player.setInstancePin(2);
					player.sm("200,000,000 coins were deducted from your inventory.");
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					startControler();
					end();
				} else if (player.getCurrencyPouch().spend100mTicket(2)) {
					player.setInstancePin(2);
					 
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					startControler();
					end();
				} else {
					player.sm("Sorry, but you don't have enough money.");
					end();
				}				
			} else if (componentId == OPTION_3) {
				player.setInstanceControler(getString);
				player.getTemporaryAttributtes().put("startinstancepin", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter your new pin:" });							
			}
		} else if (stage == 5) {
			sendOptionsDialogue("Unlock forever?", "Yes, Pay 4B.", "No, Don't Pay.");
			stage = 6;
		} else if (stage == 6) {
			if (componentId == OPTION_1) {
				if (player.getCurrencyPouch().spend100mTicket(40)) {
					setPerma(true);
					 
					player.sm("You've permenantly unlocked the instance.");
					end();
				} else {
					player.sm("Sorry, but you don't have enough money.");
					end();
				}						
			} else if (componentId == OPTION_2) {
				end();
			}
		} else if (stage == 7) {	
			if (componentId == OPTION_1) {
				if (player.checkMoney(200000000) || player.getCurrencyPouch().canAfford100mTicket(2)) {
					sendOptionsDialogue("Choose a level of protection", "Only I can enter.", "Anyone can enter.", "PIN protected.");
					stage = 4;						
				} else {
					player.sm("You can't afford this.");
					end();
				}
			} else if (componentId == OPTION_2) {
				end();	
			} else if (componentId == OPTION_3) {
				sendDialogue("Unlocking this instance forever will allow you to", "access the private instance for free", "forever. This costs 4B. Do you wish to proceed?");
				stage = 5;
			}
		}
	}
	
	@Override
	public void finish() {
	}

}
