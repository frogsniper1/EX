package com.rs.game.player;

import com.rs.game.player.Player;

public class KillCount {
	
    public static void openInterface(Player player) {
        if (!player.getInterfaceManager().containsInterface(1156))
            player.getInterfaceManager().sendInterface(1156);
        
        sendString(player, new int[]{ 108, 109, 90  }, "Copyright Slayer", "Unlock: 100 Copyright Kills", "Apply");
        sendString(player, new int[]{ 113, 114, 206 }, "Fatal Resort Slayer", "Unlock: 100 Fatal Resort Kills", "Apply");
        sendString(player, new int[]{ 137, 138, 254 }, "Something Slayer", "Unlock: 100 Something Kills", "Apply");
        sendString(player, new int[]{ 110, 111, 200 }, "Eradicator Slayer", "Unlock: 100 Eradicator Kills", "Apply");
        sendString(player, new int[]{ 116, 117, 212 }, "Deathlotus Ninja Slayer", "Unlock: 100 Deathlotus Ninja Kills", "Apply");
        sendString(player, new int[]{ 134, 135, 248 }, "Seasinger Mage Slayer", "Unlock: 100 Seasinger Mage Kills", "Apply");
        sendString(player, new int[]{ 122, 123, 230 }, "Fear Slayer", "Unlock: 100 Fear Kills", "Apply");
        sendString(player, new int[]{ 128, 129, 236 }, "Blink Slayer", "Unlock: 100 Blink Kills", "Apply");
        sendString(player, new int[]{ 125, 126, 224 }, "Extreme Boss Slayer", "Unlock: 100 Extreme boss Kills", "Apply");
        sendString(player, new int[]{ 146, 147, 272 }, "Maximum Gradum Slayer", "Unlock: 100 Maximum Gradum Kills", "Apply");
        sendString(player, new int[]{ 143, 144, 266 }, "Regular Boss Slayer", "Unlock: 100 Regular Bosses", "Apply");
        sendString(player, new int[]{ 119, 120, 218 }, "Armadyl Slayer", "Unlock: 100 Armadyl Kills", "Apply");
        sendString(player, new int[]{ 131, 132, 242 }, "Zamorak Slayer", "Unlock: 100 Zamorak Kills", "Apply");
        sendString(player, new int[]{ 140, 141, 260 }, "Bandos Slayer", "Unlock: 100 Bandos Kills", "Apply");
        sendString(player, new int[]{ 149, 150, 278 }, "Saradomin Slayer", "Unlock: 100 Saradomin Kills", "Apply");
        sendString(player, new int[]{ 152, 153, 284 }, "Sunfreet Slayer", "Unlock: 100 Sunfreet Kills", "Apply");
        sendString(player, new int[]{ 167, 168, 308 }, "Nex Slayer", "Unlock: 100 Nex Kills", "Apply");
        sendString(player, new int[]{ 155, 157, 290 }, "Tzhaar-Jad Slayer", "Unlock: 100 Tzhaar-Jad Kills", "Apply");
        sendString(player, new int[]{ 159, 161, 296 }, "Obsidian King Slayer", "Unlock: 100 Obsidian King Kills", "Apply");
        sendString(player, new int[]{ 163, 165, 302 }, "Wilderness Boss Slayer", "Unlock: 100 Wilderness Boss Kills", "Apply");
        sendString(player, new int[]{ 170, 171, 314 }, "<img=4> Master Slayer", "Unlock: 100 Kills in all Bosses Except Donators", "Apply");
        sendString(player, new int[]{ 318, 319, 326 }, "Clear Title", "Clear your current title", "Apply");
        
        sendString(player, new int[]{ 156, 164, 160 }, null, null, null); // leave null
        
    }
    
    public static void sendString(Player player, int[] cIds, String title, String desc, String button) {
        player.getPackets().sendIComponentText(1156, cIds[0], title == null ? "" : title);
        player.getPackets().sendIComponentText(1156, cIds[1], desc == null ? "" : desc);
        player.getPackets().sendIComponentText(1156, cIds[2], button == null ? "" : button);
    }
}
