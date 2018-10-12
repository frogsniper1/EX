package com.rs.rss;

import com.rs.Settings;
import com.rs.game.player.Player;

public class ForumThread {
	
	public static void openInterface(Player player) {
		player.threadnumber = 1;
		player.getInterfaceManager().sendInterface(3011);
		showThread(player);
	}
	
	public static void handleButtons(Player player, int componentId) {
		if (componentId == 2) {
			if (player.threadnumber < Settings.feed.getMessages().size() - 1)
				player.threadnumber++;
			else
				player.threadnumber = 1;
		} else if (componentId ==3) {
			if (player.threadnumber > 1)
				player.threadnumber--;
			else
				player.threadnumber = Settings.feed.getMessages().size() - 1;
		} else if (componentId == 7) {
			player.getPackets().sendOpenURL(Settings.feed.getMessages().get(player.threadnumber - 1).getLink());
		}
		showThread(player);
	}
	
	public static void showThread(Player player) {
		String description = "";
		description = Settings.feed.getMessages().get(player.threadnumber - 1).getDescription();
		description = description.replaceAll("\\<.*?\\>", "");
		description = description.replaceAll("~", "<br>-");
		if (description.length() > 810) {
			description = description.substring(0, 510);
			description += "..."; 
		}
		String backuptitle = description;
		backuptitle = backuptitle.substring(7, backuptitle.length());
		if (backuptitle.indexOf("<br>") != -1)
		backuptitle = backuptitle.substring(0, backuptitle.indexOf("<br>"));
		if (player.threadnumber == 1) {
			player.getPackets().sendIComponentText(3011, 5," <img=7> " +Settings.feed.getMessages().get(player.threadnumber-1).getTitle());
			player.getPackets().sendIComponentText(3011, 4, description);
			player.getPackets().sendIComponentText(3011, 6, "News Thread " + player.threadnumber + "/" + (Settings.feed.getMessages().size() - 1));
		} else
		player.getPackets().sendIComponentText(3011, 5," <img=7> " +Settings.feed.getMessages().get(player.threadnumber-1).getTitle());
		player.getPackets().sendIComponentText(3011, 4, description);
		player.getPackets().sendIComponentText(3011, 6, "News Thread " + player.threadnumber + "/" + (Settings.feed.getMessages().size() - 1));
	}
	
}