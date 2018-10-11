package com.rs.game.player.content.commands;

import com.rs.game.player.Player;
import com.rs.game.WorldTile;

public class ForumMod {
	public static boolean processCommands(Player player, String[] cmd, boolean console, boolean clientCommand) {
		if (clientCommand) {
		} else {
			if (cmd[0].equals("sz") ||cmd[0].equals("supportzone") ) {
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				player.setNextWorldTile(new WorldTile(2847, 5152, 0));
				player.sm("<img=17><img=13>Welcome to the Staff Zone!<img=13><img=17>");
				return true;
			}			
		}
		return false;
	}
}
