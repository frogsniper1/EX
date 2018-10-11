package com.rs.game.player.content.custom;

import com.rs.Settings;
import com.rs.game.player.Player;

public class CommandsBook {

	public static void sendBook(Player player) {
		player.getInterfaceManager().sendInterface(937);
		player.getPackets().sendIComponentText(937, 43, ""+Settings.SERVER_NAME+" Commands");
		player.getPackets().sendIComponentText(937, 69, "::empty");
		player.getPackets().sendIComponentText(937, 70, "::pkranks");
		player.getPackets().sendIComponentText(937, 71, "::kdr");
		player.getPackets().sendIComponentText(937, 72, "::players");
		player.getPackets().sendIComponentText(937, 73, "::help");
		player.getPackets().sendIComponentText(937, 74, "::donate");
		player.getPackets().sendIComponentText(937, 75, "::lockxp");
		player.getPackets().sendIComponentText(937, 76, "::hideyell");
		player.getPackets().sendIComponentText(937, 77, "::changepass");
		player.getPackets().sendIComponentText(937, 78, "::forums");
                player.getPackets().sendIComponentText(937, 799, "::train");
		player.getPackets().sendIComponentText(937, 79, "");
		player.getPackets().sendIComponentText(937, 80, "<img=8><col=ff0000>Donator Commands</col>");
		player.getPackets().sendIComponentText(937, 81, "::yell");
		player.getPackets().sendIComponentText(937, 82, "::title");
		player.getPackets().sendIComponentText(937, 83, "::setdisplay");
		player.getPackets().sendIComponentText(937, 84, "::donorcity");
		player.getPackets().sendIComponentText(937, 85, "::removedisplay");
		player.getPackets().sendIComponentText(937, 86, "::bank");
		player.getPackets().sendIComponentText(937, 87, "");
		player.getPackets().sendIComponentText(937, 88, "");
		player.getPackets().sendIComponentText(937, 89, "");
		player.getPackets().sendIComponentText(937, 90, "");
		// prev/next buttons
		player.getPackets().sendIComponentText(937, 91, "");
		player.getPackets().sendIComponentText(937, 92, "");
		return;
	}
}
