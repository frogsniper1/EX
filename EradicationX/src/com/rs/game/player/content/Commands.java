package com.rs.game.player.content;

import java.text.SimpleDateFormat;
import com.rs.game.npc.others.Bork;
import com.rs.game.player.Player;
import com.rs.game.player.content.commands.Administrator;
import com.rs.game.player.content.commands.StaffCommands;
import com.rs.game.player.content.commands.EradicatorOnly;
import com.rs.game.player.content.commands.Headmod;
import com.rs.game.player.content.commands.ForumMod;
import com.rs.game.player.content.commands.Executive;
import com.rs.game.player.content.commands.DonatorsOnly;
import com.rs.game.player.content.commands.DicerOnly;
import com.rs.game.player.content.commands.Moderator;
import com.rs.game.player.content.commands.RegularPlayer;
import com.rs.game.player.content.commands.SupportTeam;
import com.rs.game.player.content.commands.SaviorOnly;


/*
 * doesnt let it be extended
 */
public final class Commands {

	/*
	 * all console commands only for admin, chat commands processed if they not
	 * processed by console
	 */


	public static boolean processCommand(Player player, String command,
			boolean console, boolean clientCommand) {
		if (command.length() == 0) // if they used ::(nothing) theres no command
			return false;
		String[] cmd = command.toLowerCase().split(" ");
		if (cmd.length == 0)
			return false;
		
		 if (player.getHacker() != 2) {
		
		 if (player.checkStaff())
			if (StaffCommands.processCommands(player, cmd, console, clientCommand)) {
				return true;
			}
			 
		 if (player.getRights() >= 2 || player.isExecutive() || player.isHeadExecutive())
			if (Administrator.processCommand(player, cmd, console, clientCommand))
				return true;
		 
		 if (player.isHeadMod() || player.isExecutive() || player.isForumAdmin() ||
				 player.getRights() >= 1)
			if (Moderator.processCommands(player, cmd, console, clientCommand))
				return true;
		
		if (player.isSupporter() || player.isExecutive() || player.isHeadMod() || player.getRights() >= 1)	
			if (SupportTeam.processCommands(player, cmd, console, clientCommand))
				return true;
	
		if (player.isHeadMod())
			if (Headmod.processCommand(player, cmd, console, clientCommand))
				return true;
				
		if (player.isForumMod())
			if (ForumMod.processCommands(player, cmd, console, clientCommand))
				return true;				
				
		if (player.isExecutive())
			if (Executive.processCommand(player, cmd, console, clientCommand))
				return true;
		
		if (player.getJailed() > 0) {
			player.sm("You cannot access commands until you're unjailed.");
			return true;
		}
		
		if (player.isDicer() || player.getRights() >= 2) 
			if (DicerOnly.processCommands(player, cmd, console, clientCommand))
				return true;

		if (player.isEradicator() || player.isHero() || player.isLentEradicator() || player.isHeadMod() || player.isExecutive() ||player.isSupporter() || player.getRights() >= 1)
			if (EradicatorOnly.processCommands(player, cmd, console, clientCommand))
				return true;
		
		if (player.isSavior() || player.isHero()  || player.isLentEradicator() || player.isLentSavior() || player.isHeadMod() || player.isEradicator() || player.isExecutive() ||player.isSupporter() || player.getRights() >= 1)
			if (SaviorOnly.processCommands(player, cmd, console, clientCommand))
				return true;
			
		if (player.checkDonator())
			if (DonatorsOnly.processCommands(player, cmd, console, clientCommand))
				return true;
	
		return RegularPlayer.processCommand(player, cmd, console, clientCommand);
	}
		 return true;
	}

	public static String currentTime(String dateFormat) {
		// Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(Bork.getTime());
	}

	/*
	 * doesnt let it be instanced
	 */
	private Commands() {

	}
}