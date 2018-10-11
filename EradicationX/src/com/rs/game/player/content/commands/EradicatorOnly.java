package com.rs.game.player.content.commands;

import com.rs.content.utils.IPMute;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.custom.YellHandler;
import com.rs.game.player.content.pet.Pets;
import com.rs.utils.DisplayNames;
import com.rs.utils.Utils;

public class EradicatorOnly {
	
	public static boolean processCommands(Player player, String[] cmd, boolean console, boolean clientCommand) {
		String name;

		
		if (clientCommand) {
		} else {
			if (cmd[0].equals("brutals")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.sm(player.getTimer()+"");
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
					player.getDialogueManager().startDialogue("Brutals");
                                return true;
				
			}
			if (cmd[0].equals("eradjad")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
					player.getDialogueManager().startDialogue("EradJadKing");
                                return true;
				
			}
			if (cmd[0].equals("eradking")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
					player.getDialogueManager().startDialogue("EradKing");
                                return true;
				
			}
			if (cmd[0].equals("boxeradicator") || cmd[0].equals("boxerad")) {
				if (player.isEradicator()) {
				if (player.getTrade().isTrading()) {
					player.sm("Please finish trading before boxing ranks.");
					return true;
				}
				if (player.getInventory().getFreeSlots() < 1) {
				player.sm("You need more inventory space before doing this.");
				return true;
				} else {
				player.settitleshadecolor("");
				player.settitlenamecolor("");
				player.settitlecolor("");	
				player.getAppearence().setTitle(0);
				player.setEradicator(false);
				player.getInventory().addItem(6828, 1);
				player.sm("You place your rank into the box.");
				return true;
				}
				} else { 
				player.sm("You can't do this.");
				return true;
				}
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
			if (cmd[0].equals("donorboss")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Regular");
				return true;
			}
			if (cmd[0].equals("ez")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1874, 4946, 2));
                player.getPackets().sendGameMessage("<img=7> extreme donator zone");
				return true;
			}
            if (cmd[0].equals("eradzone")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182, 5713, 0)); 	
				player.getPackets().sendGameMessage("Eradicator Zone");
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
			
			if (cmd[0].equals("eradicatortitle")) {
				player.getAppearence().setTitle(8198212); 
				player.getPackets().sendGameMessage("You have activated your Eradicator Title.");				
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
