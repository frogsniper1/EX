package com.rs.game.player.dialogues;

import com.rs.Settings;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.content.DisplayNameAction;
import com.rs.game.player.content.PlayerLook;
import com.rs.utils.DisplayNames;
import com.rs.utils.Utils;



public class Noticeboard extends Dialogue {
	
	public Noticeboard() {
	}

	@Override
	public void start() {
		stage = 2;
		sendOptionsDialogue("Character Settings",
				"Change Display Name", "Reset Display Name",
				"Configure Donator Title",
				"Configure Donator Yell", "Next page <img=2>");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 2) {
			if (componentId == OPTION_1) {
				DisplayNameAction.ProcessChange(player);
			} else if (componentId == OPTION_2) {
				DisplayNames.removeDisplayName(player);
				player.getPackets().sendGameMessage("[EX]: Removed Display Name"); 
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				if (player.isDonator() || player.isExtremeDonator()|| player.isSavior()|| player.checkEradicator() || player.isLentDonator() || player.isLentExtreme()
						|| player.isLentSavior()|| player.isLentEradicator()
						|| player.isSupporter()|| player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {
				sendOptionsDialogue("Title Settings",
						"<img=8>Set Title Color [HEX]", "<img=10>Set Title",
						"<img=9>Set Name Color [HEX]",
						"<img=18>Set Shade [HEX]", "Reset customization");
				stage = 4;
				} else {
					player.sm("Sorry, you must be at least a regular donator to access this feature.");
					player.getInterfaceManager().closeChatBoxInterface();
				}
			} else if (componentId == OPTION_4) {
				if (player.isDonator() || player.isExtremeDonator()|| player.isSavior()|| player.checkEradicator() || player.isLentDonator() || player.isLentExtreme()
						|| player.isLentSavior()|| player.isLentEradicator()
						|| player.isSupporter()|| player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {
				sendOptionsDialogue("Title Settings",
						"<img=10>Set Title", "<img=8>Set Title Color [HEX]",
						"<img=9>Set Shade [HEX]", "Reset customization");
				stage = 9;
				} else {
					player.sm("Sorry, you must be at least a regular donator to access this feature.");
					player.getInterfaceManager().closeChatBoxInterface();
				}				
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Character Settings", "Configure Loot Beams",
						"I would like to change my hair.", "Change clothes", "Change skin color or gender", "Change shoes");
				stage = 3;
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
					int beamnum = player.getDropbeam()+1;
					if (player.getDropbeam() != 6) {
					sendOptionsDialogue("Unlocking Beam #" +beamnum + " will cost you 750M", 
							"Pay 750M", "Nevermind");
					stage = 6;
					} else {
					player.sm("This is no use for you.");
					end();
					}
			} else if (componentId == OPTION_2) {
				if (player.getDropbeam() != 1) {
					sendOptionsDialogue("Select a loot beam you'd like to activate", "Loot Beam 1","Loot Beam 2", 
							player.getDropbeam() < 3 ? "Not Available" : "Loot Beam 3",
							player.getDropbeam() < 4 ? "Not Available" : "Loot Beam 4", "Next Page");
					stage = 7;
				} else 
					end();
			} else if (componentId == OPTION_3) {
				player.getPackets().sendOpenURL(Settings.FORUMS_LINK);
				end();
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1) {
				if (player.getDropbeam() >= 1) {
					player.sm("Activated loot beam #1");
					player.setSelectedbeam(1);
				} else 
					player.sm("You can't activate this.");
				end();
			} else if (componentId == OPTION_2) {
				if (player.getDropbeam() >= 2) {
					player.sm("Activated loot beam #2");
					player.setSelectedbeam(2);
				} else 
					player.sm("You can't activate this.");		
				end();
			} else if (componentId == OPTION_3) {
				if (player.getDropbeam() >= 3) {
					player.sm("Activated loot beam #3");
					player.setSelectedbeam(3);
				} else 
					player.sm("You can't activate this.");		
				end();
			} else if (componentId == OPTION_4) {
				if (player.getDropbeam() >= 4) {
					player.sm("Activated loot beam #4");
					player.setSelectedbeam(4);
				} else 
					player.sm("You can't activate this.");	
				end();
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Select a loot beam you'd like to activate", 
						player.getDropbeam() < 4 ? "Not Available" : "Loot Beam 4",
						player.getDropbeam() < 5 ? "Not Available" : "Loot Beam 5",
						player.getDropbeam() < 6 ? "Not Available" : "Loot Beam 6");
				stage = 8;
			}
		} else if (stage == 8) {	
			if (componentId == OPTION_1) {
				if (player.getDropbeam() >= 4) {
					player.sm("Activated loot beam #4");
					player.setSelectedbeam(4);
				} else 
					player.sm("You can't activate this.");
				end();
			} else if (componentId == OPTION_2) {
				if (player.getDropbeam() >= 5) {
					player.sm("Activated loot beam #5");
					player.setSelectedbeam(5);
				} else 
					player.sm("You can't activate this.");		
				end();
			} else if (componentId == OPTION_3) {
				if (player.getDropbeam() >= 6) {
					player.sm("Activated loot beam #6");
					player.setSelectedbeam(6);
				} else 
					player.sm("You can't activate this.");		
				end();
			}			
		} else if (stage == 6) {
			if (componentId == OPTION_1)  {
				switch (player.getDropbeam()) {
				case 1:
					if (player.chargeMoney(750000000)) {
		    			  World.sendGraphics(null, new Graphics(453), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(383), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
						player.sm("Congratulations, you've unlocked loot beam 2!");
						player.setDropbeam(2);
						player.setSelectedbeam(2);
						end();
						break;
					} else {
						player.sm("You couldn't afford this loot beam. You need 750M coins. You only have "
						+ player.getInventory().getNumerOf(995) + " coins.");
						end();
						break;
					}
				case 2:
					if (player.chargeMoney(750000000)) {
		    			  World.sendGraphics(null, new Graphics(531), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(453), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(383), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
						player.sm("Congratulations, you've unlocked loot beam 3!");
						player.setDropbeam(3);	
						player.setSelectedbeam(3);
						end();
						break;
					} else {
						player.sm("You couldn't afford this loot beam. You need 750M coins. You only have "
					+ player.getInventory().getNumerOf(995) + " coins.");
						end();
						break;
					}
				case 3:
					if (player.chargeMoney(750000000)) {
		    			  World.sendGraphics(null, new Graphics(538), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(531), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(453), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(383), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
						player.sm("Congratulations, you've unlocked loot beam 4!");
						player.setDropbeam(4);	
						player.setSelectedbeam(4);
						end();
						break;
					} else {
						player.sm("You couldn't afford this loot beam. You need 750M coins. You only have "
					+ player.getInventory().getNumerOf(995) + " coins.");
						end();
						break;
					}
				case 4:
					if (player.chargeMoney(750000000)) {
						  World.sendGraphics(null, new Graphics(550), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(538), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(531), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(453), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(383), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
						player.sm("Congratulations, you've unlocked loot beam 5!");
						player.setDropbeam(5);	
						player.setSelectedbeam(5);
						end();
						break;
					} else {
						player.sm("You couldn't afford this loot beam. You need 750M coins. You only have "
					+ player.getInventory().getNumerOf(995) + " coins.");
						end();
						break;
					}
				case 5:	
					if (player.chargeMoney(750000000)) {
		    			  World.sendGraphics(null, new Graphics(574), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(550), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(538), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(531), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(453), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
		    			  World.sendGraphics(null, new Graphics(383), new WorldTile(player.getX(), player.getY()-1, player.getPlane()));
						player.sm("Congratulations, you've unlocked loot beam 6!");
						player.setDropbeam(6);
						player.setSelectedbeam(6);
						end();
						break;
					} else {
						player.sm("You couldn't afford this loot beam. You need 750M coins. You only have "
					+ player.getInventory().getNumerOf(995) + " coins.");
						end();
						break;
					}		
				case 6: 
					player.sm("That command is no use for you.");
					end();
					break;
				}
			} else if (componentId == OPTION_2) {
				
			} else if (componentId == OPTION_3) {
				
			} 
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				int beamnum = player.getDropbeam() + 1;
				sendOptionsDialogue("You currently have Loot Beam #" + player.getDropbeam(),
						player.getDropbeam() != 6 ? "Upgrade to Loot Beam #" + beamnum : "Already fully upgraded",
						player.getDropbeam() >= 2 ? "Change current Loot Beam" : "Nevermind",
						"Show me how each Loot Beam looks");
				stage = 5;
			} else if (componentId == OPTION_2) {
				PlayerLook.openHairdresserSalon(player);
				player.sm("Make sure you don't wear any items!");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				player.sm("Make sure you don't wear any items!");
				PlayerLook.openThessaliasMakeOver(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				player.sm("Make sure you don't wear any items!");
				PlayerLook.openMageMakeOver(player);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_5)	{
				short[] MALE_BOOTS = { 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 42, 43 };
				short[] FEMALE_BOOTS = { 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549, 550, 551, 552, 553, 554, 555, 79, 80 };
				player.getAppearence().setBodyStyle(6, !player.getAppearence().isMale() ? FEMALE_BOOTS[Utils.random(FEMALE_BOOTS.length) - 1] : MALE_BOOTS[Utils.random(MALE_BOOTS.length) - 1]);
				player.getAppearence().generateAppearenceData();
				player.sm("You change your boots.");
				end();
			}
		}	else if (stage == 4) {
			if (componentId == OPTION_1) { // Title Color [HEX]
				if (player.isExtremeDonator()|| player.isSavior()|| player.checkEradicator() || player.isLentExtreme()|| player.isLentSavior()|| player.isLentEradicator()
						|| player.isSupporter()|| player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {
				player.getTemporaryAttributtes().put("titlecolor", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the title color in HEX format (Colorpicker.com)" });
				} else {
					player.sm("Sorry, you must be at least an extreme donator to access this feature.");
				}
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) { // Title				
				player.getTemporaryAttributtes().put("customtitle", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the title you would like. (Write empty to make it blank)" });	
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) { // Name Color [HEX]
				if (player.isSavior()|| player.checkEradicator() || player.isLentSavior()|| player.isLentEradicator()
						|| player.isSupporter()|| player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {				
				player.getTemporaryAttributtes().put("titlenamecolor", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the name color in HEX format (Colorpicker.com)" });
				} else {
					player.sm("Sorry, you must be at least a super donator to access this feature.");
				}
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Shade Color [HEX]
				if (player.checkEradicator() || player.isSupporter() || 
						player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {					
				player.getTemporaryAttributtes().put("shadenamecolor", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the shade color in HEX format (Colorpicker.com)" });	
				} else {
					player.sm("Sorry, you must be at least an eradicator to access this feature.");					
				}
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_5) { // Reset Customization
				player.settitleshadecolor("");
				player.settitlenamecolor("");
				player.settitlecolor("C12006");
				player.settitleshadecolor("");
				player.getAppearence().setTitle(0);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("All of your settings have been reset.");
			}
		} else if (stage == 9) {
			if (componentId == OPTION_1) { // Title Color [HEX]
				player.getTemporaryAttributtes().put("yellcustomtitle", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the title you would like." });	
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) { // Title	
				if (player.isExtremeDonator()|| player.isSavior()|| player.checkEradicator()
						|| player.isSupporter()|| player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {
					player.getTemporaryAttributtes().put("yelltitlecolor", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the title color in HEX format (Colorpicker.com)" });	
				} else {
					player.sm("Sorry, you must be at least an extreme donator to access this feature.");
				}
			} else if (componentId == OPTION_3) { // Shade Color [HEX]
				if (player.isSavior()|| player.checkEradicator()
						|| player.isSupporter()|| player.isHeadMod() || player.isExecutive() || player.getRights() > 0) {					
				player.getTemporaryAttributtes().put("yellshadenamecolor", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the shade color in HEX format (Colorpicker.com)" });	
				} else {
					player.sm("Sorry, you must be at least an eradicator to access this feature.");					
				}
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Reset Customization
				player.setYellShade(null);
				player.setYellTitle(null);
				player.setYellColor(null);
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("All of your yell settings have been reset.");
			}		
		}

	}

	@Override
	public void finish() {
	}

}
