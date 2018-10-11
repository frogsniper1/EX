package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;

import com.rs.Settings;
import com.rs.content.utils.IPMute;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.VoteReward;
import com.rs.game.player.content.custom.YellHandler;
import com.rs.utils.DisplayNamesKeep;
import com.rs.utils.DisplayNamesManager;
import com.rs.utils.LogMover;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class StaffCommands {

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
			if (cmd[0].equalsIgnoreCase("stare")) {
			   String othee2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                Player targeet2 = World.getPlayerByDisplayName(othee2);
                targeet2.getPackets().sendOpenURL("http://i3.kym-cdn.com/photos/images/original/000/291/502/5b7.gif");
                return true;
			}
			if (cmd[0].equalsIgnoreCase("dropboxinfo")) {
				player.getPackets().sendOpenURL("http://pastebin.com/XhhSA96h");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("home")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}			
				player.setNextWorldTile(new WorldTile(3968, 4823, 1));
            return true;
			}			
			if (cmd[0].equals("requestlogs")) {
				player.sm("Logs are being requested...");
				LogMover myRunnable = new LogMover(player);
		        Thread t = new Thread(myRunnable);
		        t.start();
				return true;
			}
			if (cmd[0].equals("checkc")) {
				name = "";
	        	DisplayNamesKeep keep = null;
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : "_");
	        	name = name.replaceAll("_", " ");
	        	keep = DisplayNamesManager.findNamesByDisplayName(name);
	        	if (SerializableFilesManager.containsPlayer(name.replaceAll(" ", "_"))) {
	        		if (keep != null)
	        			player.setPanelDisplayName(keep.getDisplayName());
	        		else {
	        			target = SerializableFilesManager.loadPlayer(name.replaceAll(" ", "_"));
	        			player.sm("They have: " + Utils.formatNumber( target.getLoyaltyPoints()) + " Loyalty Points");
						player.sm("They have: " + Utils.formatNumber( target.getTriviaPoints()) + " Trivia Points");
						player.sm("They have: " + Utils.formatNumber( target.getBossSlayerPoints()) + " Boss Slayer Points");
						player.sm("They have: " + Utils.formatNumber( target.getDeposittedBones()) + " Eradicated Bones");
						player.sm("They have: " + Utils.formatNumber( target.getCurrencyPouch().get100MTicket()) + " 100M Tickets");
						player.sm("They have: " + Utils.formatNumber( target.getCurrencyPouch().getInvasionTokens()) + " Invasion Tokens");
						player.sm("They have: " + Utils.formatNumber( target.getCurrencyPouch().getVoteTickets()) + " Vote Tickets");
						player.sm("They have: " + Utils.formatNumber( target.getCurrencyPouch().getEradicatedSeals()) + " Eradicated Seals");
						try {
							SerializableFilesManager.storeSerializableClass(target, new File("data/playersaves/characters/" + name	+ ".p"));
						} catch (IOException e) {
							e.printStackTrace();
						}
	        		}
	        		return true;
	        	}
	        	if (keep != null) {
	        		
	        	} else 
	        	player.sm("Couldn't find anyone by the name of " + name + ". Either the account doesn't exist or they haven't logged in after October 2015.");
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
			
			
			if (cmd[0].equals("kick")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null) {
					player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(name) + " is not logged in.");
					return true;
				}
				target.getSession().getChannel().close();
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
					if (!target.inInstance()) {	
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
					if (target.getMuteMarks() == 3) {
						player.sm("This player already has three mute marks. You have reset the loop.");
						player.sm("This player now has 1 mute mark.");
						target.setMuteMarks(1);
					} else {
					target.setMuteMarks(target.getMuteMarks() + 1);
					player.sm("You've given "+target.getDisplayName()+" a mute mark. They now have " + target.getMuteMarks() + " marks. (Three is a mute)");
					}
				} else {
					name = Utils.formatPlayerNameForProtocol(name);
					if (!SerializableFilesManager.containsPlayer(name)) {
						player.getPackets().sendGameMessage("Account name " + Utils.formatPlayerNameForDisplay(name)+ " doesn't exist.");
						return true;
				}
					player.sm("Why are you trying to mute someone offline? Anyways, this command isn't compatible for offline players. :[");
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
					player.getPackets().sendGameMessage("You cannot tele anywhere from here. You're in a controler " + player.getControlerManager().toString());
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
			if (cmd[0].equals("find")) {
				name = "";
	        	DisplayNamesKeep keep = null;
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : "_");
	        	name = name.replaceAll("_", " ");
	        	keep = DisplayNamesManager.findNamesByDisplayName(name);
	        	if (SerializableFilesManager.containsPlayer(name.replaceAll(" ", "_"))) {
	        		if (keep != null)
	        			player.setPanelDisplayName(keep.getDisplayName());
	        		else {
	        			target = SerializableFilesManager.loadPlayer(name.replaceAll(" ", "_"));
	        			if (!target.getDisplayName().equals(target.getUsername()))
	        				player.setPanelDisplayName(target.getDisplayName());
	        			else
	        				player.setPanelDisplayName(null);
						try {
							SerializableFilesManager.storeSerializableClass(target, new File("data/playersaves/characters/" + name	+ ".p"));
						} catch (IOException e) {
							e.printStackTrace();
						}
	        		}
	        		player.setPanelName(name.replaceAll(" ", "_"));
	        		player.sendStaffPanel();
	        		return true;
	        	}
	        	if (keep != null) {
	        		player.setPanelName(keep.getUsername());
	        		player.setPanelDisplayName(keep.getDisplayName());
	        		player.sendStaffPanel();
	        	} else 
	        	player.sm("Couldn't find anyone by the name of " + name + ". Either the account doesn't exist or they haven't logged in after October 2015.");
				return true;
			}
			
		}
		return false;
	}
}
