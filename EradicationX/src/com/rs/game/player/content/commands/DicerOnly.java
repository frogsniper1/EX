package com.rs.game.player.content.commands;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.pet.Pets;
import com.rs.utils.DisplayNames;
import com.rs.utils.Utils;

public class DicerOnly {
	public static boolean processCommands(Player player, String[] cmd, boolean console, boolean clientCommand) {
		String name;
		if (clientCommand) {
		} else {
			if (cmd[0].equals("roll")) {
				player.getPackets().sendGameMessage("Rolling...");
	            player.setNextGraphics(new Graphics(2075));
	            player.setNextAnimation(new Animation(11900));
	            player.setNextForceTalk(new ForceTalk("I am a gayfucker."));
	            player.getPackets().sendGameMessage("I am a gayfucker.");
				return true;
			}
			if (cmd[0].equals("dicebag")) {
				player.sm("A dice bag has been placed in your inventory.");
				player.getInventory().addItem(15098, 1);
				return true;
			}
			if (cmd[0].equals("donorzone")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1973, 5043, 0));
                player.getPackets().sendGameMessage("<img=5> Donorzone.<img=5>");
				return true;
			}
            if (cmd[0].equals("train")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2408, 3851, 0));
                player.getPackets().sendGameMessage("<img=7> Training. <img=7>.");
                return true;
            }
			if (cmd[0].equals("trollname")) {
				name = "";
				for (int i = 1; i < cmd.length; i++) {
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
				name = Utils.formatPlayerNameForDisplay(name);
				if (name.length() < 3 || name.length() > 14) {
					player.getPackets().sendGameMessage("You can't use a name shorter than 3 or longer than 14 characters.");
					return true;
				}
				player.getPetManager().setTrollBabyName(name);
				if (player.getPet() != null && player.getPet().getId() == Pets.TROLL_BABY.getBabyNpcId()) {
					player.getPet().setName(name);
				}
				return true;
			}
			
			if (cmd[0].equals("title")) {
				if (cmd.length < 2) {
					player.getPackets().sendGameMessage("Use: ::title id");
					return true;
				}
				try {
					player.getAppearence().setTitle(Integer.valueOf(cmd[1]));
				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage("Use: ::title id");
				}
				return true;
			}
			
			if (cmd[0].equals("setdisplay")) {
				player.getTemporaryAttributtes().put("setdisplay", Boolean.TRUE); 
				player.getPackets().sendInputNameScript("Enter the display name you wish:"); 
				return true;
			}
			
			if (cmd[0].equals("donorcity")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3410, 3164, 0));
				return true;
			}
			
			if (cmd[0].equals("resetdisplay")) {
				DisplayNames.removeDisplayName(player);
				player.getPackets().sendGameMessage("Removed Display Name"); 
				return true;
			}
			if (cmd[0].equals("dicetitle")) {
				player.getAppearence().setTitle(4324324);
				return true;
			}
			if (cmd[0].equals("bank")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't bank while you're in this area.");
					return true;
				}
				player.stopAll();
				player.getBankT().openBank();
				return true;
			}
						
		}
		return false;
	}
}
