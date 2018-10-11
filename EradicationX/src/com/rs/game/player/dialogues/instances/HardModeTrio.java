package com.rs.game.player.dialogues.instances;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;

/* created by Fatal Resort - EradicationX */
public class HardModeTrio extends Dialogue {

	@Override
	public void start() {
		player.setOutside(new WorldTile(3810, 4691, 0));
		stage = 1;
		sendOptionsDialogue("What would you like to do?",
				"Start a private instance", "Join an existing instance");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		player.instanceDialogue = "HardModeTrio";
		if (stage == 1) {	
			if (componentId == OPTION_1) {
					sendDialogue("A new session will start once you continue for 1 hour.",
							"If you die, you lose 10 minutes and spawn back into the zone.",
							"Do you wish to start? An instance costs a single Trio key charge.","",
							"Note: <col="+Settings.RED+"> Three players are recommended!");
				stage = 2;					
			} else if (componentId == OPTION_2) {	
				end();
				player.getTemporaryAttributtes().put("instance_name_hm", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the leader's name:" });									
			}

	} else if (stage == 2) {	
		sendOptionsDialogue("Choose a spawn rate", "Fast", "Medium", "Slow");
		stage = 9;					
		} else if (stage == 3) {
			sendOptionsDialogue("Use a Trio key charge for 1 hour?", "Pay", "Don't Pay.");
			stage = 7;
		} else if (stage == 9) {
			if (componentId == OPTION_1) {
				player.setSpawnRate(20);
				sendDialogue("Spawn Rate set to fast.");
				stage = 3;
			} else if (componentId == OPTION_2) {
				player.setSpawnRate(30);
				sendDialogue("Spawn Rate set to medium.");
				stage = 3;
			} else if (componentId == OPTION_3) {
				player.setSpawnRate(40);
				sendDialogue("Spawn Rate set to slow.");
				stage = 3;
			}				
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				Item item = player.getInventory().containsAtleastItem(new int[]{29947, 27749, 27750, 27751, 27752, 27753});
				if (item != null) {
					player.setInstancePin(1);
					player.getInventory().deleteItem(item);
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler("HardModeTrioInstance");
					end();
				} else {
					player.sm("Sorry, but you don't have a Trio key.");
					end();
				}				
			} else if (componentId == OPTION_2) {
				Item item = player.getInventory().containsAtleastItem(new int[]{29947, 27749, 27750, 27751, 27752, 27753});
				if (item != null) {
					player.setInstancePin(2);
					player.getInventory().deleteItem(item);
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler("HardModeTrioInstance");
					end();
				} else {
					player.sm("Sorry, but you don't have a Trio key.");
					end();
				}							
			} else if (componentId == OPTION_3) {
				player.setInstanceControler("HardModeTrioInstance");
				player.getTemporaryAttributtes().put("startinstancepinhard", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter a pin:" });							
			}
		} else if (stage == 7) {	
			if (componentId == OPTION_1) {
				Item item = player.getInventory().containsAtleastItem(new int[]{29947, 27749, 27750, 27751, 27752, 27753});
				if (item != null) {
					sendOptionsDialogue("Choose a level of protection", "Only I can enter.", "Anyone can enter.", "PIN protected.");
					stage = 4;						
				} else {
					player.sm("You can't afford this.");
					end();
				}
			} else if (componentId == OPTION_2) {
				end();	
			}
		}
	}
	
	@Override
	public void finish() {
	}

}
