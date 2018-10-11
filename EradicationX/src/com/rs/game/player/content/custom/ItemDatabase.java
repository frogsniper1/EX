package com.rs.game.player.content.custom;

import java.io.IOException;

import com.rs.cache.loaders.ItemDefinitions.FileUtilities;
import com.rs.game.player.Player;

public class ItemDatabase {

	public static void search(Player p, String name) {
		String itemName = name.toLowerCase();
		int results = 0;
			try {
				for (String lines : FileUtilities.readFile("./information/itemList.txt")) {
					String[] data = lines.split(" - ");
					if (lines.toLowerCase().contains(itemName)) {
						results++;
						if (results > 250) {
							p.getPackets().consoleMessage("Found 250+ results. Only 250 are shown.");
							return;
						}
						p.getPackets().consoleMessage("<col=00FF00>"+data[0]+"</col> - "+data[1]+"");
					}
				}
				p.getPackets().consoleMessage("<col=FF0000>Found "+results+" matches for '"+itemName+"'</col>.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
	}
}
