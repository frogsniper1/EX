package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class SkillingTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("Skilling Teleports", "Fishing",
					"Mining", "Agility",
					"Woodcutting", "More Options...");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Fishing Teleports", "Fishing Guild",
						"Lrc Fishing");
				stage = 6;
			}
			if (componentId == OPTION_2) {
				sendOptionsDialogue("Mining Teleports", "Normal Mining",
						"Lava Mining");
				stage = 5;
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Agility Teleports", "Gnome Agility",
						"Barbarian Outpost");
				stage = 3;
			}
			if (componentId == OPTION_4) {
				sendOptionsDialogue("Woodcutting Teleports", "Normal & Oaks",
						"Willows", "Yews & Ivy", "Magics");
				stage = 4;
			}
			if (componentId == OPTION_5) {
				stage = 2;
				sendOptionsDialogue("Skilling Teleports",
						"Runecrafting", "Summoning",
						"Hunter", "Disabled", "Even more options...");
			}

		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4294,
						6028, 1));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2209,
						5343, 0));
				end();
			}
			if (componentId == OPTION_3) {
				sendOptionsDialogue("Hunter Teleports", "Jungle",
						"Puro Puro");
				stage = 7;
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4248,
						3942, 2));
				end();
			}
			if (componentId == OPTION_5) {
				stage = 9;
				sendOptionsDialogue("Skilling Teleports",
						"Disabled", "Back to start" );
			}
			
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2470,
						3436, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552,
						3563, 0));
				end();
			}
		}
		
		else if (stage == 4) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3170,
						3424, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3092,
						3236, 0));
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3217,
						3500, 0));
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2702,
						3397, 0));
				end();
			}
		}
		
		else if (stage == 5) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3300,
						3312, 0));
				end();
			}
			if (componentId == OPTION_2) {
				 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2177,
        				 5664, 0));
    			end();
			}
		}
		
		else if (stage == 6) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599,
						3421, 0));
				end();
			}
			if (componentId == OPTION_2) {
				 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3648,
        				 5088, 0));
    			end();
			}
		}
				else if (stage == 7) {
			if (componentId == OPTION_1) {
						Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2526,
						2916, 0));
				end();
			}
			if (componentId == OPTION_2) {
				 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2591,
        				 4317, 0));
    			end();
			}
				}
		
		else if (stage == 9) {
			if (componentId == OPTION_2) {
				sendOptionsDialogue("Skilling Teleports", "Fishing",
						"Mining", "Agility",
						"Woodcutting", "More Options...");
				stage = 1;
			}
		}
	}

	@Override
	public void finish() {

	}

}