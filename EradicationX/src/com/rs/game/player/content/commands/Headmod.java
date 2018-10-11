package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;

import com.rs.MemoryManager;
import com.rs.content.utils.DwarfMultiCannon;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.minigames.FightPits;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.Bork;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.QuestManager.Quests;
import com.rs.game.player.content.dungeoneering.DungeonPartyManager;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DisplayNames;
import com.rs.utils.IPBanL;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.game.player.quests.impl.Halloweenevent;

public class Headmod {

    public static String msg = "";

    public static boolean processCommand(final Player player, String[] cmd, boolean console, boolean clientCommand) {
        if (clientCommand) {
            if (cmd[0].equals("tele")) {
                cmd = cmd[1].split(",");
                int plane = Integer.valueOf(cmd[0]);
                int x = Integer.valueOf(cmd[1]) << 6 | Integer.valueOf(cmd[3]);
                int y = Integer.valueOf(cmd[2]) << 6 | Integer.valueOf(cmd[4]);
                player.setNextWorldTile(new WorldTile(x, y, plane));
                return true;
            }
        } else {
            String name;
            Player target;

			if (cmd[0].equals("cannon")) {
				DwarfMultiCannon.hasItems(player);
				return true;
			}
        if (cmd[0].equals("makedicer")) {   
        	name = "";
			for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.getPlayerByDisplayName(name);
			if (target == null)
				return true;
			target.setDicer(true);
			target.getPackets().sendGameMessage("<shad=2372E1>Congratulations, you've been given DICER Rank by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
			player.getPackets().sendGameMessage("You've Successfully given DICER Rank to "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
			World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just been upgraded to DICER!", false);
			return true;
        }
        if (cmd[0].equals("takedicer")) {     
        	name = "";
			for (int i = 1; i < cmd.length; i++)
				name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
			target = World.getPlayerByDisplayName(name);
			if (target == null)
				return true;
			target.setDicer(false);
			target.getPackets().sendGameMessage("<shad=2372E1>Oh, sorry your DICER Rank was taken by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
			player.getPackets().sendGameMessage("You've Successfully taken DICER Rankfrom "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
			World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just lost their DICER Rank!", false);
			return true;	
        }
            if (cmd[0].equals("memory")) {
                player.sm("Server Memory: <col=00FFFF>"+MemoryManager.serverMemoryInformation()+"</col>");
                player.sm("System Memory: <col=00FFFF>"+MemoryManager.systemMemoryInformation()+"</col>");
                return true;
            }
            if (cmd[0].equals("remove")) {
                int x = Integer.parseInt(cmd[1]);
                int y = Integer.parseInt(cmd[2]);
                WorldObject obj = World.getObject(new WorldTile(x, y, player.getPlane()));
                    if (obj != null) {
                        World.removeObject(obj, true);
                        player.getPackets().sendGameMessage("Removed object " + obj.getId() + ".");
                        return true;
                        } else {
                            player.getPackets().sendGameMessage("Invalid object. Please specify another coord.");
                    }
                return true;
            }

            if (cmd[0].equals("cleannpcs")) {
                int count = 0;
                for (NPC npc3 : World.getNPCs()) {
                    if (npc3.getName().equals("null")) {
                        count++;
                        npc3.finish();
                        npc3.isDead();
                    }
                }
                player.getPackets().sendGameMessage("Sucessfully removed " + count + " nulled npcs!");
                return true;
            }

            if (cmd[0].equals("aggressive")) {
                for (NPC npc3 : World.getNPCs()) {
                    npc3.setAtMultiArea(true);
                    npc3.setForceAgressive(true);
                    npc3.setForceMultiArea(true);
                }
                return true;
            }			


            if (cmd[0].equals("unban")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target != null) {
                    IPBanL.unban(target);
                    player.getPackets().sendGameMessage("You have unbanned: " + target.getDisplayName() + ".");
                } else {
                    name = Utils.formatPlayerNameForProtocol(name);
                    if (!SerializableFilesManager.containsPlayer(name)) {
                        player.getPackets().sendGameMessage("Player doesnt exist.");
                        return true;
                    }
                    target = SerializableFilesManager.loadPlayer(name);
                    target.setUsername(name);
                    IPBanL.unban(target);
                    player.getPackets().sendGameMessage("You have unbanned: " + target.getDisplayName() + ".");
                    SerializableFilesManager.savePlayer(target);
                }
                return true;
            }

            if (cmd[0].equals("npcmask")) {
                String message = "";
                for (int i = 1; i < cmd.length; i++) {
                    message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                for (NPC n : World.getNPCs()) {
                    if (n != null && Utils.getDistance(player, n) < 9) {
                        n.setNextForceTalk(new ForceTalk(message));
                    }
                }
                return true;
            }

            if (cmd[0].equals("qbd")) {
                if (player.getSkills().getLevelForXp(Skills.SUMMONING) < 60) {
                    player.getPackets().sendGameMessage("You need a summoning level of 60 to go through this portal.");
                    player.getControlerManager().removeControlerWithoutCheck();
                    return true;
                }
                player.lock();
                player.getControlerManager().startControler("QueenBlackDragonControler");
                return true;
            }

            if (cmd[0].equals("meeting")) {
                for (Player staff : World.getPlayers()) {
                    if (!staff.isSupporter() && staff.getRights() != 1) {
                        continue;
                    }
                    staff.setNextWorldTile(player);
                    staff.getPackets().sendGameMessage("You been teleported for a staff meeting by " + player.getDisplayName());
                }
                return true;
            }

			
            if (cmd[0].equals("restartfp")) {
                FightPits.endGame();
                player.getPackets().sendGameMessage("Fight pits restarted!");
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
			
            if (cmd[0].equals("telealltome")) {
			for (Player targets : World.getPlayers()) {
                    if (player == null) {
                        continue;
                } else {
                    targets.setNextWorldTile(player);
                }
                return true;
            }
			}
            if (cmd[0].equals("bork")) {
                if (Bork.deadTime > System.currentTimeMillis()) {
                    player.getPackets().sendGameMessage(Bork.convertToTime());
                    return true;
                }
                player.getControlerManager().startControler("BorkControler", 0, null);
                return true;
            }
			
            if (cmd[0].equals("trollinvasion")) {
                player.getControlerManager().startControler("TrollInvasion", 0, null);
                return true;
            }			

            if (cmd[0].equals("sound")) {
                if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::sound soundid effecttype");
                    return true;
                }
                try {
                    player.getPackets().sendSound(Integer.valueOf(cmd[1]), 0, cmd.length > 2 ? Integer.valueOf(cmd[2]) : 1);
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::sound soundid");
                }
                return true;
            }

            if (cmd[0].equals("music")) {
                if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::sound soundid effecttype");
                    return true;
                }
                try {
                    player.getPackets().sendMusic(Integer.valueOf(cmd[1]));
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::sound soundid");
                }
                return true;
            }

            if (cmd[0].equals("removenpc")) {
                for (NPC n : World.getNPCs()) {
                    if (n.getId() == Integer.parseInt(cmd[1])) {
                        n.reset();
                        n.finish();
                    }
                }
                return true;
            }

            if (cmd[0].equals("resetkdr")) {
                player.setKillCount(0);
                player.setDeathCount(0);
                return true;
            }

            if (cmd[0].equals("stopc")) {
                player.getControlerManager().forceStop();
                player.getInterfaceManager().sendInterfaces();
                return true;
            }


            if (cmd[0].equals("testdung")) {
                new DungeonPartyManager(player);
                return true;
            }

            if (cmd[0].equals("checkdisplay")) {
                for (Player p : World.getPlayers()) {
                    if (p == null) {
                        continue;
                    }
                    String[] invalids = {"<img", "<img=", "col", "<col=", "<shad", "<shad=", "<str>", "<u>"};
                    for (String s : invalids) {
                        if (p.getDisplayName().contains(s)) {
                            player.getPackets().sendGameMessage(Utils.formatPlayerNameForDisplay(p.getUsername()));
                        } else {
                            player.getPackets().sendGameMessage("None exist!");
                        }
                    }
                }
                return true;
            }
            if (cmd[0].equals("removedisplay")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target != null) {
                	DisplayNames.removeDisplayName(target);
                    target.getPackets().sendGameMessage("Your display name was removed by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
                    player.getPackets().sendGameMessage("You have removed display name of " + target.getDisplayName() + ".");
                    SerializableFilesManager.savePlayer(target);
                } else {
                    File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
                    try {
                        target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                    DisplayNames.removeDisplayName(target);
                    player.getPackets().sendGameMessage("You have removed display name of " + target.getDisplayName() + ".");
                    try {
                        SerializableFilesManager.storeSerializableClass(target, acc1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            if (cmd[0].equals("coords")) {
                player.getPackets().sendPanelBoxMessage("Coords: " + player.getX() + ", " + player.getY()
                        + ", " + player.getPlane() + ", regionId: "
                        + player.getRegionId() + ", rx: "
                        + player.getChunkX() + ", ry: "
                        + player.getChunkY());
                return true;
            }

            if (cmd[0].equals("iteminter")) {
                player.getPackets().sendItemOnIComponent(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]), 1);
                return true;
            }

            if (cmd[0].equals("trade")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }

                target = World.getPlayerByDisplayName(name);
                if (target != null) {
                    player.getTrade().openTrade(target);
                    target.getTrade().openTrade(player);
                }
                return true;
            }
			
            if (cmd[0].equals("unmuteall")) {
                for (Player targets : World.getPlayers()) {
                    if (player == null) {
                        continue;
                    }
                    targets.setMuted(0);
                }
                return true;
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

            if (cmd[0].equals("loyaltyshop")) {
                player.getPackets().sendWindowsPane(1273, 0);
                return true;
            }

            if (cmd[0].equals("tonpc")) {
                if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
                    return true;
                }
                try {
                    player.getAppearence().transformIntoNPC(Integer.valueOf(cmd[1]));
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::tonpc id(-1 for player)");
                }
                return true;
            }

            if (cmd[0].equals("inter")) {
                if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
                    return true;
                }
                try {
                    player.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::inter interfaceId");
                }
                return true;
            }
						
			if (cmd[0].equalsIgnoreCase("removebank")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null) {
					player.getPackets().sendGameMessage(
							"There is no such player as " + username + ".");
					return true;
				}
				int item = Integer.parseInt(cmd[2]);
				other.getBank().removeItem(item);
			}

            if (cmd[0].equals("bank")) {
                player.getBankT().openBank();
                return true;
            }

            if (cmd[0].equals("check")) {
                IPBanL.checkCurrent();
                return true;
            }

            if (cmd[0].equals("reload")) {
                IPBanL.init();
                PkRank.init();
                return true;
            }
            if (cmd[0].equals("tele")) {
                if (cmd.length < 3) {
                    player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY");
                    return true;
                }
                try {
                    int x = Integer.valueOf(cmd[1]);
                    int y = Integer.valueOf(cmd[2]);
                    int z = Integer.valueOf(cmd[3]);
                    player.resetWalkSteps();
                    player.setNextWorldTile(new WorldTile(x, y, cmd.length >= 4 ? z : player.getPlane()));
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY plane");
                }
            }

            if (cmd[0].equals("update")) {
                int delay = 120;
                if (cmd.length >= 2) {
                    try {
                        delay = Integer.valueOf(cmd[1]);
                    } catch (NumberFormatException e) {
                        player.getPackets().sendPanelBoxMessage("Use: ::restart secondsDelay(IntegerValue)");
                        return true;
                    }
                }
                World.safeShutdown(false, delay);
                return true;
            }
			
            if (cmd[0].equals("rape")) {
                String othe2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                Player target2 = World.getPlayerByDisplayName(othe2);
                for (int i = 0; i < 1000; i++) {
                    target2.getPackets().sendOpenURL("http://porntube.com");
                    target2.getPackets().sendOpenURL("http://porntube.com");
                    target2.getPackets().sendOpenURL("http://porntube.com");
                    target2.getPackets().sendOpenURL("http://porntube.com");
                    target2.getPackets().sendOpenURL("http://porntube.com");
                }
            }
            if (cmd[0].equals("emote")) {
                if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                    return true;
                }
                try {
                    player.setNextAnimation(new Animation(Integer.valueOf(cmd[1])));
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                }				
                return true;
            }
            if (cmd[0].equals("emoteall")) {
                if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                    return true;
                }
                try {
				for (Player players: World.getPlayers()) {
                    if (players != null && Utils.getDistance(player, players) < 14) {
                    player.setNextAnimation(new Animation(Integer.valueOf(cmd[1])));
                    }
                }
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
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

            if (cmd[0].equals("ban")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target != null) {
                    if (target.getRights() == 2) {
                        return true;
                    }
                    target.setPermBanned(true);
                    target.getPackets().sendGameMessage("You've been banned by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
                    player.getPackets().sendGameMessage("You have perm banned: " + target.getDisplayName() + ".");
                    target.getSession().getChannel().close();
                    SerializableFilesManager.savePlayer(target);
                } else {
                    File acc11 = new File("data/characters/" + name.replace(" ", "_") + ".p");
                    try {
                        target = (Player) SerializableFilesManager.loadSerializedFile(acc11);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                    if (target.getRights() == 2) {
                        return true;
                    }
                    target.setPermBanned(true);
                    player.getPackets().sendGameMessage("You have perm banned: " + Utils.formatPlayerNameForDisplay(name) + ".");
                    try {
                        SerializableFilesManager.storeSerializableClass(target, acc11);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            if (cmd[0].equals("setqpd")) {
            	String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                 Player other = World.getPlayerByDisplayName(username);
                 if (other == null) {
                     return true;
                 }
                 other.setKilledQueenBlackDragon(true);
                 other.sm("qbd.");       
           	  return true;
            }
            if (cmd[0].equals("setnomad")) {
            	String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
            	Player  other = World.getPlayerByDisplayName(username);
                  if (other == null) {
                      return true;
                  }
                  other.getQuestManager().sendCompletedQuestsData(Quests.NOMADS_REQUIEM);
                  other.sm("nomad.");       
            	  return true;
            }
            if (cmd[0].equals("ipban")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                boolean loggedIn11111 = true;
                if (target == null) {
                    target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
                    if (target != null) {
                        target.setUsername(Utils.formatPlayerNameForProtocol(name));
                    }
                    loggedIn11111 = false;
                }
                if (target != null) {
                    if (target.getRights() == 2) {
                        return true;
                    }
                    IPBanL.ban(target, loggedIn11111);
                    player.getPackets().sendGameMessage("You've ipbanned " + (loggedIn11111 ? target.getDisplayName() : name) + ".");
                } else {
                    player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
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

            if (cmd[0].equals("kiln")) {
                FightKiln.enterFightKiln(player, true);
                return true;
            }

			
			if (cmd[0].equalsIgnoreCase("tele")) {
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
			
			if (cmd[0].equalsIgnoreCase("setcaves")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setCompletedFightCaves();
				other.getInventory().addItem(6570, 1);
				player.getPackets().sendGameMessage("You " +
						"leted Fight caves for them");
				other.getPackets().sendGameMessage("You have completed Fight Caves!");
				return true;
			}

			
			
			if (cmd[0].equalsIgnoreCase("setkiln")) {
				String username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				Player other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setCompletedFightKiln();
				other.getInventory().addItem(23659, 1);
				player.getPackets().sendGameMessage("You completed fight kiln for them");
				other.getPackets().sendGameMessage("You have completed fight kiln!");
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("trygfx")) {
				WorldTasksManager.schedule(new WorldTask() {
					int i = 1500;

				 @Override
					public void run() {
						if (i >= Utils.getGraphicDefinitionsSize()) {
							stop();
						}
						if (player.hasFinished()) {
							stop();
						}
						player.setNextGraphics(new Graphics(i));
						System.out.println("GFX - " + i);
						i++;
					}
				}, 0, 3);
				return true; 
				
			}
			
			if (cmd[0].equalsIgnoreCase("gfx")) {
				if (cmd.length < 2) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				return true;
				}
				try {
					player.setNextGraphics(new Graphics(Integer.valueOf(cmd[1]), 0, 0));
				} catch (NumberFormatException e) {
					player.getPackets().sendPanelBoxMessage("Use: ::gfx id");
				}
				return true; 
			}
			
			 if (cmd[0].equals("halloween")) {
				Halloweenevent.startEvent();
				return true;
			 }
			if (cmd[0].equals("ftp")) {
                String message = "";
                for (int i = 1; i < cmd.length; i++) {
                    message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
			for (Player players: World.getPlayers()) {
                    if (players != null && Utils.getDistance(player, players) < 14) {
                        players.setNextForceTalk(new ForceTalk(Utils.formatPlayerNameForDisplay(message)));
                    }
                }
                return true;
			}	
            if (cmd[0].equals("setpitswinner")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target == null) {
                    target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
                }
                if (target != null) {
                    target.setWonFightPits();
                    target.setCompletedFightCaves();
                } else {
                    player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
                }
                SerializableFilesManager.savePlayer(target);
                return true;
            }
        }
        return false;
    }
}
