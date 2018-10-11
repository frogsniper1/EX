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

public class DonatorsOnly {
	public static boolean processCommands(Player player, String[] cmd, boolean console, boolean clientCommand) {
		String name;
		if (clientCommand) {
		} else {
			if (cmd[0].equals("roll")) {
				player.getPackets().sendGameMessage("Rolling...");
	            player.setNextGraphics(new Graphics(2075));
	            player.setNextAnimation(new Animation(11900));
	            player.setNextForceTalk(new ForceTalk("I am a hoolyhoffersmackadunkamonalamdanughatant."));
	            player.getPackets().sendGameMessage("I am a gayfucker.");
				return true;
			}
			
			if (cmd[0].equals("settitle")) {
			player.getTemporaryAttributtes().put("customtitle", Boolean.TRUE);
			player.getPackets().sendRunScript(109,
					new Object[] { "Please enter the title you would like." });				
			return true;
			}
			
			if (cmd[0].equals("settitlecolor")) {
				player.getTemporaryAttributtes().put("titlecolor", Boolean.TRUE);
				player.getPackets().sendRunScript(109,
						new Object[] { "Please enter the title color in HEX format." });
			return true;
			}
			
			if (cmd[0].equals("secondbank") || cmd[0].equals("bank2")) {
				if (player.checkDonator()) {
					if (player.hasSecondBank()) {
						if (player.isInsideHairymonkey) {
							player.sm("You can't bank inside here.");
							return true;
						}
						if (!player.canSpawn()) {
							player.getPackets().sendGameMessage("You can't bank while you're in this area.");
							return true;
						}
						player.stopAll();
						player.getSecondBank().openBank();
					} else {
						player.sm("You must purchase a second bank by the extra banker at the home bank for 10B Cash.");
					}
				} else {
					player.sm("You need to have a donator rank to be able to use this command.");
				}
				return true;
			}
			
			if (cmd[0].equals("boxregular")) {
				if (player.isDonator()) {
				if (player.getTrade().isTrading()) {
					player.sm("Please finish trading before boxing ranks.");
					return true;
				}
				if (player.getInventory().getFreeSlots() < 1) {
				player.sm("You need more inventory space before doing this.");
				return true;
				} else {
				player.setDonator(false);
				player.getInventory().addItem(6832, 1);
				player.getAppearence().setTitle(0);				
				player.sm("You place your rank into the box.");
				return true;
				}
				} else { 
				player.sm("You can't do this.");
				return true;
				}
			}	
			if (cmd[0].equals("boxextreme")) {
				if (player.isExtremeDonator()) {
				if (player.getTrade().isTrading()) {
					player.sm("Please finish trading before boxing ranks.");
					return true;
				}
				if (player.getInventory().getFreeSlots() < 1) {
				player.sm("You need more inventory space before doing this.");
				return true;
				} else {
				player.setExtremeDonator(false);
				player.getInventory().addItem(6830, 1);
				player.getAppearence().setTitle(0);
				player.settitleshadecolor("");
				player.settitlenamecolor("");
				player.settitlecolor("");
				player.sm("You place your rank into the box.");
				return true;
				}
				} else { 
				player.sm("You can't do this.");
				return true;
				}
			}			
			if (cmd[0].equals("dz")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1973, 5043, 0));
                player.getPackets().sendGameMessage("<img=5> Donorzone.<img=5>");
				return true;
			}
			if (cmd[0].equals("regboss")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Regular");
				return true;
			}
			if (cmd[0].equals("extremeboss")) {
				if (player.isExtremeDonator() || player.isLentEradicator() 
						|| player.isLentDonator() || player.isLentExtreme() ||
						player.isLentSavior() || player.isSupporter() ||
						player.isExtremeDonator() || player.isSavior() || player.isEradicator()) {
					if (!player.canSpawn() || player.inInstance()) {
						player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
						return true;
					} else {
					player.getDialogueManager().startDialogue("Extreme");
					return true;
					}
				} else {
					player.sm("This is for extreme donators only.");
				}
				
				return true;
			}
			if (cmd[0].equals("superboss")) {
				player.sm("This was renamed to ;;extremeboss.");
				return true;
			}
			if (cmd[0].equals("ez")) {
				if (!player.isExtremeDonator() && !player.isLentExtreme()) {
					player.getPackets().sendGameMessage("This is an extreme donator zone only!");
					return true;
				}
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
                Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1874, 4946, 2));
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
				if (!player.isExtremeDonator()) {
					player.getPackets().sendGameMessage("This is an extreme donator only feature!");
					return true;
				}
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
					player.getAppearence().generateAppearenceData();
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
