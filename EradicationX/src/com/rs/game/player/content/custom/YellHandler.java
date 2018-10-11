package com.rs.game.player.content.custom;

import com.rs.game.World;
import com.rs.game.player.Player;

public class YellHandler {
	
	public static boolean isValidText(Player player, String message) {
		String[] invalid = { "<euro", "<img", "<img=", "<col", "<col=", "<shad", "<shad=", "<str>", "<u>" };
		for (String s : invalid)
			if (message.contains(s)) {
				player.getPackets().sendGameMessage("You cannot add additional code to the message.");
				return false;
			}
		return true;
	}

	public static void sendYell(Player player, String message) {
		if (isValidText(player, message)) {
			for (Player players : World.getPlayers()) {
				if (players == null || !players.isRunning())
					continue;
				players.getPackets().sendGameMessage(""+RightsManager.getInfo(player)+": "+message+"");
				}
		}
		return;
	}
	
}
