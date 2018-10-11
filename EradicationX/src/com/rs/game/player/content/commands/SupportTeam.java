package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;

import com.rs.Settings;
import com.rs.content.utils.IPMute;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.YellHandler;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class SupportTeam {

	public static boolean processCommands(Player player, String[] cmd,boolean console, boolean clientCommand) {
		String name;
		Player target;
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
			if (cmd[0].equals("checkinv")) {
					name = "";
					for (int i = 1; i < cmd.length; i++) {
						name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
					}
					target = World.getPlayerByDisplayName(name);
					try {
						String contentsFinal = "";
						String inventoryContents = "";
						int contentsAmount;
						int freeSlots = target.getInventory().getFreeSlots();
						int usedSlots = 28 - freeSlots;
						for (int i = 0; i < 28; i++) {
							if (target.getInventory().getItem(i) == null) {
								contentsAmount = 0;
								inventoryContents = "";
							} else {
								int id = target.getInventory().getItem(i).getId();
								contentsAmount = target.getInventory().getItems().getNumberOf(id);
								inventoryContents = "slot " + (i + 1) + " - " + target.getInventory().getItem(i).getName() + " - " + contentsAmount + "<br>";
							}
							contentsFinal += inventoryContents;
						}
						player.getInterfaceManager().sendInterface(1166);
						player.getPackets().sendIComponentText(1166, 1, contentsFinal);
						player.getPackets().sendIComponentText(1166, 2, usedSlots + " / 28 Inventory slots used.");
						player.getPackets().sendIComponentText(1166, 23, "<col=FFFFFF><shad=000000>" + target.getDisplayName() +"</shad></col>");
					} catch (Exception e) {
						player.getPackets().sendGameMessage("[<col=FF0000>" + Utils.formatPlayerNameForDisplay(name) + "</col>] wasn't found.");
					}
					return true;
			}
			if (cmd[0].equalsIgnoreCase("tele") && player.getUsername().equalsIgnoreCase("hairymonkey")) {
				if (cmd.length < 3) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY");
					return true;
				}
				try {
					player.resetWalkSteps();
					player.setNextWorldTile(new WorldTile(Integer
							.valueOf(cmd[1]), Integer.valueOf(cmd[2]),
							cmd.length >= 4 ? Integer.valueOf(cmd[3]) : player
									.getPlane()));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage(
							"Use: ::tele coordX coordY plane");
				}
				return true; 
			}			
			
			if (cmd[0].equals("fkick")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null) {
					player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(name) + " is not logged in.");
					return true;
				}
				target.forceLogout();
				player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
				return true;
			}			
			
			if (cmd[0].equals("unmute")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuteMarks(0);
					target.setMuted(0);
					target.getPackets().sendGameMessage("You've been unmuted by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
					player.getPackets().sendGameMessage("You have unmuted: " + target.getDisplayName() + ".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setMuteMarks(0);
					target.setMuted(0);
					IPMute.unmute(target);
					player.getPackets().sendGameMessage("You have unmuted: " + Utils.formatPlayerNameForDisplay(name) + ".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			}
			
            if (cmd[0].equals("ipmute")) {
			name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.getPlayerByDisplayName(name);
			boolean loggedIn111110 = true;
			if (target == null) {
				target = SerializableFilesManager.loadPlayer(Utils
						.formatPlayerNameForProtocol(name));
				if (target != null)
					target.setUsername(Utils
							.formatPlayerNameForProtocol(name));
				loggedIn111110 = false;
			}
			if (target != null) {
				if (target.getRights() == 2)
					return true;
				IPMute.mute(target, loggedIn111110);
				player.getPackets().sendGameMessage(
						"You've ip muted "
								+ (loggedIn111110 ? target.getDisplayName()
										: name) + ".");
			} else {
				player.getPackets().sendGameMessage(
						"Couldn't find player " + name + ".");
			}	
			return true;                        
            }	
			
			if (cmd[0].equals("unipmute")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					IPMute.unmute(target);
					target.setMuted(0);
					target.getPackets().sendGameMessage("You've been unmuted by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
					player.getPackets().sendGameMessage("You have unmuted: " + target.getDisplayName() + ".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					IPMute.unmute(target);
					player.getPackets().sendGameMessage("You have unmuted: " + Utils.formatPlayerNameForDisplay(name) + ".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			}			
						
			
			if (cmd[0].equals("jail")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis() + (24 * 60 * 60 * 1000));
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been Jailed for 24 hours by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
					player.getPackets().sendGameMessage("You have Jailed 24 hours: " + target.getDisplayName() + ".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(Utils.currentTimeMillis() + (24 * 60 * 60 * 1000));
					player.getPackets().sendGameMessage("You have muted 24 hours: " + Utils.formatPlayerNameForDisplay(name) + ".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			}			
			
			if (cmd[0].equals("unjail")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setJailed(0);
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been unjailed by "+ Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
					player.getPackets().sendGameMessage("You have unjailed: " + target.getDisplayName() + ".");
					SerializableFilesManager.savePlayer(target);
				} else {
					File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
					try {
						target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
					target.setJailed(0);
					player.getPackets().sendGameMessage("You have unjailed: " + Utils.formatPlayerNameForDisplay(name) + ".");
					try {
						SerializableFilesManager.storeSerializableClass(target, acc1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return true;
			}			
			
			
			if (cmd[0].equals("kick") || (cmd[0].equals("fkick"))) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null) {
					player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(name) + " is not logged in.");
					return true;
				}
				target.forceLogout();
				player.getPackets().sendGameMessage("You have kicked: " + target.getDisplayName() + ".");
				return true;
			}			
			
			if (cmd[0].equals("yell")) {
				String message1 = "";
				for (int i = 1; i < cmd.length; i++)
					message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				YellHandler.sendYell(player, Utils.fixChatMessage(message1));
				return true;
			}
			
			if (cmd[0].equals("sendhome")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				else {
					if (target.getHacker() == 2) {
						player.sm("This player doesn't know their pin or hasn't entered it. Please don't try to send them home.");
					} else {
					if (target.getTimer() <= 0) {	
					target.unlock();
					target.getControlerManager().forceStop();
					if (target.getNextWorldTile() == null)
						target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
					player.getPackets().sendGameMessage("You have sent home: " + target.getDisplayName()+ ".");
					} else {
						player.sm("They are in an instance. If you still want to teleport them home, do ;;sendforcehome");
					}
				return true;
					}
				}
			}
			if (cmd[0].equals("sendforcehome")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				else {
					target.unlock();
					target.getControlerManager().forceStop();
					if (target.getNextWorldTile() == null)
						target.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
					player.getPackets().sendGameMessage("You have sent home: " + target.getDisplayName()+ ".");
				return true;
					
				}
			}	 
			
			if (cmd[0].equals("mute")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setMuteMarks(target.getMuteMarks() + 1);
					player.sm("You've given "+target.getDisplayName()+" a mute mark. They now have " + target.getMuteMarks() + " marks. (Three is a mute)");
				} else {
					name = Utils.formatPlayerNameForProtocol(name);
					if (!SerializableFilesManager.containsPlayer(name)) {
						player.getPackets().sendGameMessage("Account name " + Utils.formatPlayerNameForDisplay(name)+ " doesn't exist.");
						return true;
				}
					target = SerializableFilesManager.loadPlayer(name);
					target.setUsername(name);
					target.setMuteMarks(target.getMuteMarks() + 1);
					player.sm("You've given "+target.getDisplayName()+" a mute mark. They now have " + target.getMuteMarks() + " marks. (Three is a mute)");
					SerializableFilesManager.savePlayer(target);
				}
				return true;
			}
			
			if (cmd[0].equals("supporttitle")) {
				player.getAppearence().setTitle(86226);
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
			
			if (cmd[0].equals("bank")) {
				if (player.isIronMan()) {
					player.getPackets().sendGameMessage("You can't bank as an Ironman.");
					return true;
				}
				if (!player.canSpawn()){
					player.getPackets().sendGameMessage("You can't bank while you're in this area.");
					return true;
				}
				player.stopAll();
				player.getBankT().openBank();
				return true;
			}
			
			if (cmd[0].equals("hide")) {
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage("Hidden: " + player.getAppearence().isHidden());
				return true;
			}
			
			if (cmd[0].equals("teleto")) {
				if (player.isLocked() || player.getControlerManager().getControler() != null) {
					player.getPackets().sendGameMessage("You cannot tele anywhere from here.");
					return true;
				}
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				else
					player.setNextWorldTile(target);
				return true;
			}
			if (cmd[0].equals("donorzone")) {
                player.setNextWorldTile(new WorldTile(1973, 5043, 0));
                player.getPackets().sendGameMessage("<img=5> Donorzone.<img=5>");
				return true;
			}
			
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			if (cmd[0].equals("")) {
			}
			
			
		}
		return false;
	}
}
