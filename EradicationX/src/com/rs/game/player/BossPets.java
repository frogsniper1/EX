package com.rs.game.player;

import com.rs.game.player.Player;

public class BossPets {
	
    public static void openInterface(Player player) {
    	player.setInterfaceAmount(20);
        if (!player.getInterfaceManager().containsInterface(1156))
            player.getInterfaceManager().sendInterface(1156);
        sendString(player, new int[]{ 108, 109, 90  }, "Copyright Pet", "Unlock: 2000 Copyright Kills", "Buy");
        sendString(player, new int[]{ 113, 114, 206 }, "Fatal Resort Pet", "Unlock: 2000 Fatal Resort Kills", "Buy");
        sendString(player, new int[]{ 137, 138, 254 }, "Something Pet", "Unlock: 2000 Something Kills", "Buy");
        sendString(player, new int[]{ 110, 111, 200 }, "Eradicator Boss Pet", "Unlock: 2000 Eradicator Kills", "Buy");
        sendString(player, new int[]{ 116, 117, 212 }, "Deathlotus Ninja Pet", "Unlock: 2000 Deathlotus Ninja Kills", "Buy");
        sendString(player, new int[]{ 134, 135, 248 }, "Seasinger Mage Pet", "Unlock: 2000 Seasinger Mage Kills", "Buy");
        sendString(player, new int[]{ 122, 123, 230 }, "Fear Pet", "Unlock: 2000 Fear Kills", "Buy");
        sendString(player, new int[]{ 128, 129, 236 }, "Blink Pet", "Unlock: 2000 Blink Kills", "Buy");
        sendString(player, new int[]{ 125, 126, 224 }, "Extreme Boss Pet", "Unlock: 2000 Extreme boss Kills", "Buy");
        sendString(player, new int[]{ 146, 147, 272 }, "Maximum Gradum Pet", "Unlock: 2000 Maximum Gradum Kills", "Buy");
        sendString(player, new int[]{ 143, 144, 266 }, "Regular Boss Pet", "Unlock: 2000 Regular Bosses", "Buy");
        sendString(player, new int[]{ 119, 120, 218 }, "Armadyl Pet", "Unlock: 2000 Armadyl Kills", "Buy");
        sendString(player, new int[]{ 131, 132, 242 }, "Zamorak Pet", "Unlock: 2000 Zamorak Kills", "Buy");
        sendString(player, new int[]{ 140, 141, 260 }, "Bandos Pet", "Unlock: 2000 Bandos Kills", "Buy");
        sendString(player, new int[]{ 149, 150, 278 }, "Saradomin Pet", "Unlock: 2000 Saradomin Kills", "Buy");
        sendString(player, new int[]{ 152, 153, 284 }, "Sunfreet Pet", "Unlock: 2000 Sunfreet Kills", "Buy");
        sendString(player, new int[]{ 167, 168, 308 }, "Nex Pet", "Unlock: 2000 Nex Kills", "Buy");
        sendString(player, new int[]{ 155, 157, 290 }, "Erad-Jad Pet", "Unlock: 2000 Tzhaar-Jad Kills", "Buy");
        sendString(player, new int[]{ 159, 161, 296 }, "Obsidian King Pet", "Unlock: 2000 Obsidian King Kills", "Buy");
        sendString(player, new int[]{ 163, 165, 302 }, "Wildywyrm Pet", "Unlock: 2000 Wilderness Boss Kills", "Buy");
        sendString(player, new int[]{ 170, 171, 314 }, "Coporeal Pet", "Unlock: 2000 Corporeal Beast Kills", "Buy");
        sendString(player, new int[]{ 318, 319, 326 }, "Avatar of Destruction Pet", "Unlock: 2000 Avatar of Destruction Kills", "Buy");
        
        sendString(player, new int[]{ 156, 164, 160 }, null, null, null); // leave null
        
    }
    
    public static void sendString(Player player, int[] cIds, String title, String desc, String button) {
        player.getPackets().sendIComponentText(1156, cIds[0], title == null ? "" : title);
        player.getPackets().sendIComponentText(1156, cIds[1], desc == null ? "" : desc);
        player.getPackets().sendIComponentText(1156, cIds[2], button == null ? "" : button);
    }
    public static void handleButtons(Player player, int buttonId) {
    	if (buttonId == 88) {
    		if (player.getInventory().containsItem(27088, 1) || player.getBank().getItem(27088) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getCopyrightKills() >= 2000)
    			player.getInventory().addItem(27088, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getCopyrightKills() + " Copyright Kills.");
    		return;
    	}
 
        if (buttonId == 115) {
    		if (player.getInventory().containsItem(27086, 1) || player.getBank().getItem(27086) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getFatalKills() >= 2000)
    			player.getInventory().addItem(27086, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getFatalKills() + " Fatal Resort Kills.");
    		return;
        }
        
        if (buttonId == 139) {
    		if (player.getInventory().containsItem(27087, 1) || player.getBank().getItem(27087) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getSomethingKills() >= 2000)
    			player.getInventory().addItem(27087, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getSomethingKills() + " Something Kills.");
    		return;
        }
        
        if (buttonId == 112) {
    		if (player.getInventory().containsItem(27097, 1) || player.getBank().getItem(27097) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getEradicatorBossKills() >= 2000)
    			player.getInventory().addItem(27097, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getEradicatorBossKills() + " Eradicator Boss Kills.");
    		return;
        }
        
        if (buttonId == 118) {
    		if (player.getInventory().containsItem(27095, 1) || player.getBank().getItem(27095) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getGenoKills() >= 2000)
    			player.getInventory().addItem(27095, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getGenoKills() + " Deathlotus Kills.");
    		return;
        }
        
        if (buttonId == 136) {
    		if (player.getInventory().containsItem(27094, 1) || player.getBank().getItem(27094) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getRajjKills() >= 2000)
    			player.getInventory().addItem(27094, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getRajjKills() + " Seasinger Kills.");
    		return;
        }
        
        if (buttonId == 124) {
    		if (player.getInventory().containsItem(27099, 1) || player.getBank().getItem(27099) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getFearKills() >= 2000)
    			player.getInventory().addItem(27099, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getFearKills() + " Fear Kills.");
    		return;
        }

        if (buttonId == 130) {
    		if (player.getInventory().containsItem(27098, 1) || player.getBank().getItem(27098) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getBlinkKills() >= 2000)
    			player.getInventory().addItem(27098, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getBlinkKills() + " Blink Kills.");
    		return;
        }

        if (buttonId == 127) {
    		if (player.getInventory().containsItem(27105, 1) || player.getBank().getItem(27105) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getExtremeBossKills() >= 2000)
    			player.getInventory().addItem(27105, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getExtremeBossKills() + " Extreme Boss Kills.");
    		return;
        }

        if (buttonId == 145) {
    		if (player.getInventory().containsItem(27104, 1) || player.getBank().getItem(27104) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getRegularBossKills() >= 2000)
    			player.getInventory().addItem(27104, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getRegularBossKills() + " Regular Boss Kills.");
    		return;
        }

        if (buttonId == 148) {
    		if (player.getInventory().containsItem(27096, 1) || player.getBank().getItem(27096) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getGradumKills() >= 2000)
    			player.getInventory().addItem(27096, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getGradumKills() + " Maximum Gradum Kills.");
    		return;
        }	
        
        if (buttonId == 121) {
    		if (player.getInventory().containsItem(27093, 1) || player.getBank().getItem(27093) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getArmadylKills() >= 2000)
    			player.getInventory().addItem(27093, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getArmadylKills() + " Armadyl Kills.");
    		return;
        }
        


        if (buttonId == 133) {
    		if (player.getInventory().containsItem(27092, 1) || player.getBank().getItem(27092) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getZamorakKills() >= 2000)
    			player.getInventory().addItem(27092, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getZamorakKills() + " Zamorak Kills.");
    		return;
        }
        

        if (buttonId == 142) {
    		if (player.getInventory().containsItem(27090, 1) || player.getBank().getItem(27090) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getBandosKills() >= 2000)
    			player.getInventory().addItem(27090, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getBandosKills() + " Bandos Kills.");
    		return;
        }

        if (buttonId == 151) {
    		if (player.getInventory().containsItem(27091, 1) || player.getBank().getItem(27091) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getSaradominKills() >= 2000)
    			player.getInventory().addItem(27091, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getSaradominKills() + " Saradomin Kills.");
    		return;
        }
        
        if (buttonId == 154) {
    		if (player.getInventory().containsItem(27106, 1) || player.getBank().getItem(27106) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getSunfreetKills() >= 2000)
    			player.getInventory().addItem(27106, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getSunfreetKills() + " Sunfreet Kills.");
    		return;
        }      

        if (buttonId == 169) {
    		if (player.getInventory().containsItem(27089, 1) || player.getBank().getItem(27089) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getNexKills() >= 2000)
    			player.getInventory().addItem(27089, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getNexKills() + " Nex Kills.");
    		return;
        }

        if (buttonId == 158) {
    		if (player.getInventory().containsItem(27107, 1) || player.getBank().getItem(27107) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getJadKills() >= 2000)
    			player.getInventory().addItem(27107, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getJadKills() + " Erad Jad Kills.");
    		return;
        }        
        
        if (buttonId == 162) {
    		if (player.getInventory().containsItem(27108, 1) || player.getBank().getItem(27108) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getObsidianKingKills() >= 2000)
    			player.getInventory().addItem(27108, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getObsidianKingKills() + " Obsidian King Kills.");
    		return;
        }

        if (buttonId == 166) {
    		if (player.getInventory().containsItem(27102, 1) || player.getBank().getItem(27102) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getWyrmKills() >= 2000)
    			player.getInventory().addItem(27102, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getWyrmKills() + " Wildywyrm Kills.");
    		return;    	
        }      
        
        if (buttonId == 172) {
    		if (player.getInventory().containsItem(27101, 1) || player.getBank().getItem(27101) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getCorporealKills() >= 2000)
    			player.getInventory().addItem(27101, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getCorporealKills() + " Corporeal Kills.");
    		return;    	
        }

        if (buttonId == 320) {
    		if (player.getInventory().containsItem(27103, 1) || player.getBank().getItem(27103) != null) {
    			player.sm("You already have this pet.");
    			return;
    		}
    		if (player.getAvatarKills() >= 2000)
    			player.getInventory().addItem(27103, 1);
    		else
    			player.sm("You can't take this pet! You only have " + player.getAvatarKills() + " Avatar of Destruction Kills.");
    		return;
        }
    }
}
