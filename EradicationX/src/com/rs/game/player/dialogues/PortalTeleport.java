package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.cities.tzhaar.LavaMine;
import com.rs.game.player.controlers.NomadsRequiem;

/* Referenced classes of package com.rs.game.player.dialogues:
            Dialogue
 edited by Fatal Resort and Something - EradicationX */
public class PortalTeleport extends Dialogue {

	public PortalTeleport() {
	}

	@Override
	public void start() {
		int x = (Integer) parameters[0];
		if (x == 100) {
			sendOptionsDialogue("Boss Teleports | Page: 1", "Godwar Bosses",
					"Combat Trio", "Seasinger", 
					"Death Lotus", "Next Page");
			stage = 9;
		}else {
		stage = 1;
		sendOptionsDialogue("Pick a teleport type", "Training locations",
				"Minigame locations", "Boss Locations", "<img=10> Donator Teleports<img=8>",
				"Next Page");
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Where would you like to go?",
						"Jastiszo: Rock Crabs", "Harmony: Dead Zombie Island", "Canifis Ghouls",
						"Hill Giants");
				stage = 2;
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Where would you like to go?",
						"Clan Wars", "Barrows", "Fight Caves", "Fight Pits");
				stage = 3;
			} else if (componentId == OPTION_3) {
				sendOptionsDialogue("Boss Teleports | Page: 1", "Godwar Bosses",
						"Combat Trio", "Seasinger", 
						"Death Lotus", "Next Page");
				stage = 9;
			} else if (componentId == OPTION_4) {
			if (player.isDonator() || player.isEradicator() || player.isDicer() || player.isExtremeDonator() || player.isSavior()
					|| player.isLentDonator() || player.isLentEradicator() || player.isDicer() || player.isLentExtreme() || player.isLentSavior() || player.isHeadMod()) {
				sendOptionsDialogue("Donator Teleports",
						"<img=10> Donator Boss Teles", "<img=10><img=8> Donator Zone", "<img=8> Extreme Zone", "<img=9> Super Teleports", "<img=18> Eradicator Teleports");
				stage = 20;
				} else if (!player.isDonator() || !player.isDicer() || !player.isExtremeDonator() || !player.isSavior() || !player.isEradicator() || 
						!player.isLentDonator() || !player.isDicer() || !player.isLentExtreme() || !player.isLentSavior() || !player.isLentEradicator()) { 
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You do not have Privilages for this Panel. To donate, please type in ::donate.");
				}
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Where would you like to go?",
						"Skilling Locations",
						"Dungeon & Cave Teleports & Slayer", "Dungeoneer and PVP tokens", "Shops");
				stage = 4;
			}
		} else if (stage == 20) {
			if (componentId == OPTION_1) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2237, 3323, 0));
			} else if (componentId == OPTION_2) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1973, 5043, 0));
			} else if (componentId == OPTION_3) {
				if (player.isExtremeDonator() || player.isSavior() || player.isEradicator()
						|| player.isLentExtreme() || player.isLentSavior() || player.isLentEradicator()) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1872, 4944, 2)); 
				} else {
					player.getInterfaceManager().closeChatBoxInterface();					
					player.sm("You need to be an Extreme Donator to access this panel.");
				}
			} else if (componentId == OPTION_4) {
			if (player.isSavior() || player.isHeadMod() || player.isEradicator() || player.isLentSavior() || player.isLentEradicator()) {
				sendOptionsDialogue("Where would you like to go?", 
							"Sunfreet", "Super Dragons", "Super Zone", "Close");
				stage = 7;		
			} else {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getPackets().sendGameMessage("You need to be a Super Donator to Access this panel.");
			}
			} else if (componentId == OPTION_5) {
				if (player.isEradicator() || player.isLentEradicator() || player.isOwner()) {				
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182, 5713, 0)); 	
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.getPackets().sendGameMessage("You need to be an Eradicator rank to Access this panel.");					
				}
				}
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
					player.getDialogueManager().startDialogue("Sunfreet");				
			} else if (componentId == OPTION_2) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
					player.getDialogueManager().startDialogue("Supers");
			} else if (componentId == OPTION_3) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3363, 3342, 0)); 	
			}
		} else if (stage == 9) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("The Bosses of GodWars",
				"Nex", "Bandos", "Saradomin",
				"Zamorak", "Armadyl");
				stage = 5;
			}	else if (componentId == OPTION_2) {
				player.getDialogueManager().startDialogue("Trio");
			}	else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("Rajj");
			}	else if (componentId == OPTION_4) {
				player.getDialogueManager().startDialogue("Geno");
			}	else if (componentId == OPTION_5) {
				stage = 10;
				sendOptionsDialogue("Boss Teleports | Page: 2",
						"Maxiumum Gradum", "Eradicator",
						"Blink", "Fear", "Next page");
			}
		} else if (stage == 15) {	
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2867, 5216, 0));
				player.sm("Welcome to Blink.");
			} else if (componentId == OPTION_2) {
				
			} else if (componentId == OPTION_3) {	
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2903, 5203, 0)); 
			} else if (componentId == OPTION_2) {
				end();
				player.getDialogueManager().startDialogue("Bandos");
			} else if (componentId == OPTION_3) {			
				player.getDialogueManager().startDialogue("Saradomin");
			} else if (componentId == OPTION_4) {
				player.getDialogueManager().startDialogue("Zamorak");
			} else if (componentId == OPTION_5) {
				player.getDialogueManager().startDialogue("Armadyl");
		}			
		} else if (stage == 10) {
			if (componentId == OPTION_1) {
				player.getDialogueManager().startDialogue("Gradum");
			} else if (componentId == OPTION_2) {
				player.getDialogueManager().startDialogue("EradicatorBoss");
			} else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("Blink");
			} else if (componentId == OPTION_4) {
				player.getDialogueManager().startDialogue("Fear");
			} else if (componentId == OPTION_5) {
				stage = 11;
				sendOptionsDialogue("Boss Teleports | Page: 3",
						"Necrolord (DK)", "Corporeal Beast", "Queen Black Dragon", "Nomad", "Next Page");
			}
		} else if (stage == 11) {
			if (componentId == OPTION_1) {
				player.getDialogueManager().startDialogue("Necrolord");
			} else if (componentId == OPTION_2) {
				player.getDialogueManager().startDialogue("Corp");
			} else if (componentId == OPTION_3) {
				  Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1199, 6499, 0));
            } else if (componentId == OPTION_4) {
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1890, 3164, 0));
			} else if (componentId == OPTION_5) {
			stage = 6;
			sendOptionsDialogue("Where would you like to go?", "Wildywyrm [PvP]", "Avatar of Destruction", "Sea Troll Queen", "Hati");
			}
		} else if (stage == 6) {
			if (componentId == OPTION_1) {				
				player.getDialogueManager().startDialogue("Wyrm");
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2570, 10291, 0));
			} else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("STQ");
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2703, 3628, 0));
			}						
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2993, 9679, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3566, 3293, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4610, 5130, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4603, 5063, 0));						
			}
			
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2408, 3851, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3797, 2873, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3419, 3508, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3116, 9849, 0));
			}
			
		} else if (stage == 4) {
			if (componentId == OPTION_1) { // Skilling Locations
				stage = 12;
				sendOptionsDialogue("Skilling Locations | Page: 1",
						"Gnome Agility Course", "Al-Kharid Mining",
						"Lava-Flow Mining",
						"Fishing & Cooking", "Next page");
			} else if (componentId == OPTION_2) {// Dungeon & Cave Teleports
				stage = 14;
				sendOptionsDialogue("Where would you like to go?",
						"<col=E32424>[PVP] Revenants Dungeon", "Slayer Tower",
						"Taverly Dungeon",
						"Ganodermic Dungeon", "Next page");
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1826, 5161, 2));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3968, 4822, 2));
			}
		} else if (stage == 80) {
			if (componentId == OPTION_1) {
				NomadsRequiem.enterNomadsRequiem(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 14) {
			if (componentId == OPTION_1) { // Dungeon & Cave
				sendOptionsDialogue("Warning: This area contains PvP", "Teleport", "Stay here");
				stage = 34;
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3428, 3539, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2884, 9801, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4725, 5465, 0));
			} else if (componentId == OPTION_5){ 
			stage = 8;
			sendOptionsDialogue("Dungeons | Page 2:", "Icy Caverns: Glacors");
			}
		} else if (stage == 34) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3065, 3649, 0));
			} else if (componentId == OPTION_2) {
				end();
			}
		} else if (stage == 8) {
			if (componentId == OPTION_1) {	
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4182, 5727, 0));
			} else if (componentId == OPTION_1) {		
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2710, 9466, 0));
			}
		} else if (stage == 12) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2466, 3438, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3299, 3303, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(LavaMine.Lava_Tele));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2599, 3421, 0));
			} else if (componentId == OPTION_5) {
				stage = 13;
				sendOptionsDialogue("Skilling Locations | Page: 2",
						"Summoning: Taverley", "Hunter: Puro-Puro",
						"Barbarian Outpost",
						"Runecrafting: Runespan", "Next");
			}
		} else if (stage == 13) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2924, 3441, 0));
			} else if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2426, 4445, 0));
			} else if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2552, 3561, 0));
			} else if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4294, 6028, 1));
			} else if (componentId == OPTION_5) {
				stage = 44;
				sendOptionsDialogue("Skilling Locations | Page: 3", "Woodcutting", "Living rock cavern");
			}
		} else if (stage == 44) {
			if (componentId == OPTION_1)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2725, 3476, 0));
			else if (componentId == OPTION_2)
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3651, 5122, 0));
		} else if (stage == 50) {
			if (componentId == OPTION_1) {
				end();
			} else if (componentId == OPTION_2) {
				stage = 1;
			}
		}

	}
	@Override
	public void finish() {
	}

}