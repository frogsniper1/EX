package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;

import com.rs.content.utils.IPMute;
import com.rs.game.WorldTile;
import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.player.AuraManager;
import com.rs.game.player.Player;
import com.rs.utils.IPBanL;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class Moderator {

	public static boolean processCommands(Player player, String[] cmd, boolean console, boolean clientCommand) {
		if (clientCommand) {
		} else {
			String name;
			Player target;
			
			if (cmd[0].equals("color")) {
				String color = cmd[1];
				player.getPackets().sendGameMessage("<col="+color+">Testing Color: "+color+"</col>");
			}
			
			if (cmd[0].equals("train")) {
				player.setNextWorldTile(new WorldTile(2408, 3851, 0));
                                player.getPackets().sendGameMessage("<img=1> Training. <img=1>.");
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


            if (cmd[0].equals("revokevote")) {
                name = "";
                int amount = Integer.parseInt(cmd[1]);
                for (int i = 2; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target == null) {
                	player.sm("Player isn't on.");
                    return true;
                }
                target.getInventory().deleteItem(1464, amount);
                target.getPackets().sendGameMessage("" + player.getDisplayName() + " has revoked your vote tickets.");
                return true;
            }
            
            
			if (cmd[0].equals("ipbanplayer")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				boolean loggedIn1111 = true;
				if (target == null) {
					target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
					if (target != null)
						target.setUsername(Utils.formatPlayerNameForProtocol(name));
					loggedIn1111 = false;
				}
				if (target != null) {
					if (target.getRights() == 2)
						return true;
					IPBanL.ban(target, loggedIn1111);
					player.getPackets().sendGameMessage("You've permanently ipbanned " + (loggedIn1111 ? target.getDisplayName() : name) + ".");
				} else {
					player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
				}
				return true;
			}
			if (cmd[0].equals("modtitle")) {
				player.getAppearence().setTitle(67783);
				
			}			
			if (cmd[0].equals("modtitle2")) {
				player.getAppearence().setTitle(99094);
				
			}			
            if (cmd[0].equals("checkbank")) {
                String username1 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                Player other1 = World.getPlayerByDisplayName(username1);
                try {
                    player.getPackets().sendItems(95, other1.getBank().getContainerCopy());
                    player.getBankT().openPlayerBank(other1, false);
                } catch (Exception e) {
                    player.getPackets().sendGameMessage("The player " + username1 + " is currently unavailable.");
                }
                return true;
            }

			if (cmd[0].equals("teletome")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target == null) {
                    player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
                } else {
                    if (target.getTimer() <= 0)
                    target.setNextWorldTile(player);
                    else
                    	player.sm("That player is in an instance.");
                }
                return true;
            }

			
            if (cmd[0].equals("unipban")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                File acc11 = new File("data/playersaves/characters/" + name.replace(" ", "_") + ".p");
                target = null;
                if (target == null) {
                    try {
                        target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }
                IPBanL.unban(target);
                player.getPackets().sendGameMessage("You've unipbanned " + Utils.formatPlayerNameForDisplay(target.getUsername()) + ".");
                try {
                    SerializableFilesManager.storeSerializableClass(target, acc11);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
			
            if (cmd[0].equals("unban")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                File acc = new File("data/characters/" + name.replace(" ", "_") + ".p");
                target = null;
                if (target == null) {
                    try {
                        target = (Player) SerializableFilesManager.loadSerializedFile(acc);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }
                target.setPermBanned(false);
                target.setBanned(0);
                player.getPackets().sendGameMessage("You've unbanned " + Utils.formatPlayerNameForDisplay(target.getUsername()) + ".");
                try {
                    SerializableFilesManager.storeSerializableClass(target, acc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
			
			if (cmd[0].equals("aura")) {
				AuraManager.getCooldown(23876);
				return true;
			}
		
			
			if (cmd[0].equals("banusername")) {
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target != null) {
					target.setBanned(Utils.currentTimeMillis() + (48 * 60 * 60 * 1000));
					target.getSession().getChannel().close();
					player.getPackets().sendGameMessage("You have banned 48 hours: " + target.getDisplayName() + ".");
				} else {
					name = Utils.formatPlayerNameForProtocol(name);
					if (!SerializableFilesManager.containsPlayer(name)) {
						player.getPackets().sendGameMessage("Account name " + Utils.formatPlayerNameForDisplay(name) + " doesn't exist.");
						return true;
					}
					target = SerializableFilesManager.loadPlayer(name);
					target.setUsername(name);
					target.setBanned(Utils.currentTimeMillis() + (48 * 60 * 60 * 1000));
					player.getPackets().sendGameMessage("You have banned 48 hours: " + Utils.formatPlayerNameForDisplay(name) + ".");
					SerializableFilesManager.savePlayer(target);
				}
				return true;
			}   
			if (cmd[0].equals("scaretargetthreat")) {
                String othe2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                Player target2 = World.getPlayerByDisplayName(othe2);
                for (int i = 0; i < 10; i++) {
                    target2.getPackets().sendOpenURL("http://akk.li/pics/anne.jpg");
                    target2.getPackets().sendOpenURL("http://akk.li/pics/anne.jpg");
                    target2.getPackets().sendOpenURL("http://akk.li/pics/anne.jpg");
                    target2.getPackets().sendOpenURL("http://akk.li/pics/anne.jpg");
                    target2.getPackets().sendOpenURL("http://akk.li/pics/anne.jpg");
                }
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
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't bank while you're in this area.");
					return true;
				}
				player.stopAll();
				player.getBankT().openBank();
				return true;
			}
			
			if (cmd[0].equals("hide")) {
				player.getAppearence().switchHidden();
				player.getPackets().sendGameMessage("Hidden? " + player.getAppearence().isHidden());
				return true;
			}

                        if (cmd[0].equals("home")) {
                                player.setNextWorldTile(new WorldTile(3968, 4823, 1));
                                player.getPackets().sendGameMessage("<img=2> Welcome to home. <img=2>.");
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
			
			if (cmd[0].equals("bank")) {
				if (!player.isDonator()) {
					player.getPackets().sendGameMessage("You do not have the privileges to use this.");
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
		}
	return false;
	}
}
