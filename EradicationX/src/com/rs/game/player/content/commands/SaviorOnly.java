package com.rs.game.player.content.commands;

import com.rs.content.utils.IPMute;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.player.content.custom.YellHandler;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.pet.Pets;
import com.rs.utils.DisplayNames;
import com.rs.utils.Utils;

public class SaviorOnly {
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
			if (cmd[0].equals("donorzone")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1973, 5043, 0));
                player.getPackets().sendGameMessage("<img=5> Donorzone.<img=5>");
				return true;
			}
			if (cmd[0].equals("boxsavior") || cmd[0].equals("boxsuper")) {
				if (player.isSavior()) {
				if (player.getTrade().isTrading()) {
					player.sm("Please finish trading before boxing ranks.");
					return true;
				}
				if (player.getInventory().getFreeSlots() < 1) {
				player.sm("You need more inventory space before doing this.");
				return true;
				} else {
				player.setSavior(false);
				player.settitleshadecolor("");
				player.settitlenamecolor("");
				player.settitlecolor("");				
				player.getAppearence().setTitle(0);
				player.getInventory().addItem(6829, 1);
				player.sm("You place your rank into the box.");
				return true;
				}
				} else { 
				player.sm("You can't do this.");
				return true;
				}
			}			
			if (cmd[0].equals("ez")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1874, 4946, 2));
                player.getPackets().sendGameMessage("<img=5> extreme donator zone");
				return true;
			}
			if (cmd[0].equals("sunfreet") || cmd[0].equals("saviorboss")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
					player.getDialogueManager().startDialogue("Sunfreet");
                                return true;
				
			}
			if (cmd[0].equals("supers")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
					player.getDialogueManager().startDialogue("Supers");
                                return true;
				
			}	
			if (cmd[0].equals("superzone") || cmd[0].equals("saviorzone") || cmd[0].equals("szone")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				 Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3363, 3340, 0));
                                return true;
			}			
			
            if (cmd[0].equals("train")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2408, 3851, 0));
                player.getPackets().sendGameMessage("<img=5> Training. <img=5>.");
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

			if (cmd[0].equals("yell")) {
				if (IPMute.isMuted(player.getSession().getIP())) {
					player.getPackets().sendGameMessage(
							"You're account has been permanently IP Muted.");
					return true;
				}
				if (player.getMuted() > Utils.currentTimeMillis()) {
					player.getPackets().sendGameMessage("You're temporarily muted and cannot yell.");
					return true;
				}
				String message1 = "";
				for (int i = 1; i < cmd.length; i++)
					message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				YellHandler.sendYell(player, Utils.fixChatMessage(message1));
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
			
			if (cmd[0].equals("bank")) {
				if (player.isInsideHairymonkey) {
					player.sm("You can't bank inside here.");
					return true;
				}
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
