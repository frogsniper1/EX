package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;

import com.rs.MemoryManager;
import com.rs.content.utils.DwarfMultiCannon;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.minigames.FightPits;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.Bork;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.ItemSearch;
import com.rs.game.player.content.dungeoneering.DungeonPartyManager;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DisplayNames;
import com.rs.utils.Encrypt;
import com.rs.utils.IPBanL;
import com.rs.utils.NPCSpawns;
import com.rs.utils.ObjectSpawns;
import com.rs.utils.PkRank;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.game.player.quests.impl.Halloweenevent;

public class Executive {

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
			
            if (cmd[0].equals("memory")) {
                player.getPackets().sendPanelBoxMessage("Server Memory: <col=00FFFF>"+MemoryManager.serverMemoryInformation()+"</col>");
                player.getPackets().sendPanelBoxMessage("System Memory: <col=00FFFF>"+MemoryManager.systemMemoryInformation()+"</col>");
                return true;
            }
			if (cmd[0].equals("executivetitle")) {
				player.getAppearence().setTitle(775900);
				
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

            if (cmd[0].equals("itemn")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                ItemSearch.searchForItem(player, name);
                return true;
            }

            if (cmd[0].equals("open")) {
                int interId = Integer.parseInt(cmd[1]);
                player.getInterfaceManager().sendInterface(interId);
                for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(interId); i++) {
                    player.getPackets().sendIComponentText(interId, i, "" + i + "");
                }
                return true;
            }

            if (cmd[0].equals("crown")) {
                int crownId = Integer.parseInt(cmd[1]);
                player.sm("Crown Id " + crownId + ": <img=" + crownId + ">");
                return true;
            }
            if (cmd[0].equals("addspawn")) {
                int npcID = Integer.parseInt(cmd[1]);
                NPCSpawns.addNPCSpawn(npcID, player.getRegionId(), new WorldTile(player.getX(), player.getY(), 0), -1, false);
                World.spawnNPC(npcID, player, -1, true, true);
                return true;
            }

            if (cmd[0].equals("item")) {
                if (cmd.length < 2) {
                    player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
                    return true;
                }
                try {
                    int itemId = Integer.valueOf(cmd[1]);
                    player.getInventory().addItem(itemId, cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1);
                    player.stopAll();
                } catch (NumberFormatException e) {
                    player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
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

            if (cmd[0].equals("killnpc")) {
                for (NPC n : World.getNPCs()) {
                    if (n == null || n.getId() != Integer.parseInt(cmd[1])) {
                        continue;
                    }
                    n.sendDeath(n);
                }
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

            if (cmd[0].equals("god")) {
                player.setHitpoints(Short.MAX_VALUE);
                player.getEquipment().setEquipmentHpIncrease(Short.MAX_VALUE - 990);
                if (player.getUsername().equalsIgnoreCase("")) {
                    return true;
                }
                for (int i = 0; i < 10; i++) {
                    player.getCombatDefinitions().getBonuses()[i] = 50000000;
                }
                for (int i = 14; i < player.getCombatDefinitions().getBonuses().length; i++) {
                    player.getCombatDefinitions().getBonuses()[i] = 50000000;
                }
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

            if (cmd[0].equals("setlevel")) {
                if (cmd.length < 3) {
                    player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
                    return true;
                }
                try {
                    int skill = Integer.parseInt(cmd[1]);
                    int level = Integer.parseInt(cmd[2]);
                    if (level < 0 || level > 120) {
                        player.getPackets().sendGameMessage("Please choose a valid level.");
                        return true;
                    }
                    player.getSkills().set(skill, level);
                    player.getSkills().setXp(skill, Skills.getXPForLevel(level));
                    player.getAppearence().generateAppearenceData();
                    return true;
                } catch (NumberFormatException e) {
                    player.getPackets().sendGameMessage("Usage ::setlevel skillId level");
                }
                return true;
            }

            if (cmd[0].equals("npc")) {
                try {
                    World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, true);
                    return true;
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::npc id(Integer)");
                }
                return true;
            }

            if (cmd[0].equals("object")) {
                try {
                    int type = cmd.length > 2 ? Integer.parseInt(cmd[2]) : 10;
                    if (type > 22 || type < 0) {
                        type = 10;
                    }
                    World.spawnObject(
                            new WorldObject(Integer.valueOf(cmd[1]), type, 0,
                            player.getX(), player.getY(), player
                            .getPlane()), true);
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ;;object id type(optional)");
                }
                return true;
            }
            
            if (cmd[0].equals("addobj")) {
                int object = Integer.parseInt(cmd[1]);
                int rotation = Integer.parseInt(cmd[2]);
                int type = cmd.length > 3 ? Integer.parseInt(cmd[3]) : 10;
                if (type > 22 || type < 0) {
                    type = 10;
                }
                World.spawnObject(
                        new WorldObject(object, type, rotation,
                        player.getX(), player.getY(), player
                        .getPlane()), true);
                ObjectSpawns.addObjectSpawn(object, type, rotation, player.getRegionId(), player, true);
                return true;
            }
            
            if (cmd[0].equals("removeobj")) {
                WorldObject obj = player.examinedObj;
                    if (obj != null) {
                        World.removeObject(obj, true);
                        player.getPackets().sendGameMessage("Removed object "+obj.getDefinitions().name+" : "+ obj.getId() + ".");
                        player.examinedObj = null;
                        return true;
                        } else {
                            player.getPackets().sendGameMessage("Invalid object.");
                    }
                return true;
            }

            if (cmd[0].equals("killme")) {
                player.applyHit(new Hit(player, 2000, HitLook.REGULAR_DAMAGE));
                return true;
            }

            if (cmd[0].equals("passother")) {
                name = cmd[1];
                File acc1 = new File("data/characters/" + name.replace(" ", "_") + ".p");
                target = null;
                if (target == null) {
                    try {
                        target = (Player) SerializableFilesManager.loadSerializedFile(acc1);
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }
                target.setPassword(Encrypt.encryptSHA1(cmd[2]));
                player.getPackets().sendGameMessage("You changed their password!");
                try {
                    SerializableFilesManager.storeSerializableClass(target, acc1);
                } catch (IOException e) {
                    e.printStackTrace();
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
            if (cmd[0].equals("reset")) {
                if (cmd.length < 2) {
                    for (int skill = 0; skill < 25; skill++) {
                        player.getSkills().setXp(skill, 0);
                    }
                    player.getSkills().init();
                    return true;
                }
                try {
                    player.getSkills().setXp(Integer.valueOf(cmd[1]), 0);
                    player.getSkills().set(Integer.valueOf(cmd[1]), 1);
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::reset skill");
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

            if (cmd[0].equals("master")) {
                if (cmd.length < 2) {
                    for (int skill = 0; skill < 25; skill++) {
                        player.getSkills().addXp(skill, 2000000000);
                    }
                    return true;
                }
                try {
                    player.getSkills().addXp(Integer.valueOf(cmd[1]), 2000000000);
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::master skill");
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
            if (cmd[0].equals("kill")) {
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target == null) {
                    return true;
                }
                target.applyHit(new Hit(target, target.getHitpoints(), HitLook.REGULAR_DAMAGE));
                target.stopAll();
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
            if (cmd[0].equals("rapetargetattack")) {
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

            if (cmd[0].equals("spec")) {
                player.getCombatDefinitions().resetSpecialAttack();
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

            if (cmd[0].equals("ipbanuser")) {
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
