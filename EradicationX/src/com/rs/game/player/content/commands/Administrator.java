package com.rs.game.player.content.commands;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.RegionBuilder;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.QuestManager.Quests;
import com.rs.game.player.Raffle;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.content.ItemSearch;
import com.rs.game.player.content.WorldVote;
import com.rs.game.player.content.BossSlayerTask.BossTask;
import com.rs.game.player.content.custom.BossHighlight;
import com.rs.game.player.content.custom.EventManager;
import com.rs.game.player.content.BossSlayerTask;
import com.rs.game.player.content.EXInvasion;
import com.rs.game.player.cutscenes.Cutscene;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DisplayNames;
import com.rs.utils.DisplayNamesKeep;
import com.rs.utils.DisplayNamesManager;
import com.rs.utils.Encrypt;
import com.rs.utils.IPBanL;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.utils.VotingBoard;

public class Administrator {

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
            Player p2;
            String username;
            Player other;
            Logger.printAdminCommands(player, cmd[0], cmd.length >= 2 ? cmd[1] : "", cmd.length >= 3 ? cmd[2] : "");
            switch (cmd[0]) {
            case "changehighlight":
            	BossHighlight.getInstance().reroll();
            	return true;
            case "setbosstask":
            	BossSlayerTask.random(player, BossTask.BOSSTASKS);
            	return true;
            case "removedispfromlist":
            	name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                DisplayNamesKeep names = DisplayNamesManager.findNamesByDisplayName(name);
                if (names != null) {
                	DisplayNamesManager.remove(names);
                	player.sm("Successfully removed the display: " + name + " from the list.");
                }
                player.sm("Couldn't find the display: " + name + " in the list.");
            	return true;
            case "check":
            	WorldTile tile = player.getLastWorldTile();
            	World.sendGraphics(player, new Graphics(1205), tile);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 0;
					@Override
					public void run() {
						count++;
						if (count == 12)
							this.stop();
						if (count > 4) {
							for (Player player : World.getPlayers()) { 
								if (player == null || player.isDead() || player.hasFinished())
									continue;
								if (player.withinDistance(tile, 2)) {
									for (int i = 0; i < 2; i++)
										player.applyHit(new Hit(player, 50, HitLook.MELEE_DAMAGE));
								}
							}
						}
					}
				}, 0, 0);
            	return true;
            case "npcdef":
            	NPCDefinitions npcdef = NPCDefinitions.getNPCDefinitions(Integer.parseInt(cmd[1]));
            	player.sm(npcdef.getName());
            	return true;
            case "derp":
				player.sm(player.getDestroyTimer()+"");
            	return true;
            case "cleanaccounts":
            	player.sm("Removed " + SerializableFilesManager.checkForStuff() + " accounts.");
            	return true;
            case "addnewzone":
            	int[] boundchunks;
            	boundchunks = RegionBuilder.findEmptyChunkBound(8, 8);
            	RegionBuilder.copyAllPlanesMap(357, 668, boundchunks[0], boundchunks[1], 8);	
            	return true;
            case "stressinstance":
            	player.getControlerManager().startControler("BandosInstance");
            	return true;
            case "var":
            	player.getVarsManager().sendVar(Integer.parseInt(cmd[1]), 1);
            	return true;
            case "spin":
            	WorldTasksManager.schedule(new WorldTask() {
        			private int count = 0;
        			@Override
        			public void run() {	
        				count++;
        				if (count % 2 == 0)
        				player.faceWest();
        				else
        				player.faceEast();
        			}
        		}, 0, 1);
            	return true;
            case "setec1":
            	 username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                 other = World.getPlayerByDisplayName(username);
                 if (other == null) {
                     return true;
                 }
                 other.getEliteChapterI().setComplete(other);
                 other.sm("You've completed Elite Chapter One.");
    			return true;
            case "setec2":
           	 username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.getEliteChapterII().setComplete(other);
                other.sm("You've completed Elite Chapter 2.");
   			return true;
            case "tt":
        		player.getPackets().sendPlayerOption("Attack", 1, true);
            	return true;
            case "ec2s1":
            	player.getEliteChapterII().setQuestStage(Integer.parseInt(cmd[1]));
            	player.sm("Reversed stage");
            	return true;
            case "unlock":
            	player.unlock();
            	return true;
            case "tryskelecontroler":
            	player.getControlerManager().startControler("CyndrithChapterIISkeletonBurial");
            	return true;
            case "checkgypsy":
            	player.checkGypsy();
            	return true;
            case "skull":
            	FadingScreen.fade(player, new Runnable() {

					@Override
					public void run() {
					}

				});
            	return true;
            case "skullid":
            	player.setWildernessSkull(Integer.parseInt(cmd[1]));
            	return true;
            case "tryall":
            	for (int i = 0; i < 200; i++)
            		player.getInterfaceManager().sendTab(player.getInterfaceManager().isResized() ? i : i, 3018);
            	return true;
            case "sendoverlay":
            	player.getInterfaceManager().sendOverlay(3016, false);
            	return true;
            case "rigmp": 
            	player.getPouch().setAmount(Integer.MAX_VALUE);
            	player.getPouch().refresh();
            	return true;
            case "giveyoutuber":
            	player.sm("it doesnt exist");
            	return true;
            case "checkloyaltytimer":
            	player.sm("Loyalty timer: "+ player.getLoyaltytimer());
            	return true;
            case "rigloyaltytimer":
            	player.setLoyaltytimer(1795);
            	player.sm("Loyalty timer: "+ player.getLoyaltytimer());
            	return true;
            case "hidecid":
            	int a = Integer.valueOf(cmd[1]);
            	int b = Integer.valueOf(cmd[2]);
            	player.closeInterfaces();
            	player.getInterfaceManager().sendInterface(a);
            	player.getPackets().sendHideIComponent(a, b, true);
            	return true;
            case "runhidecid":
            	CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
        			int count = 0;
        			public void run() {
        				if (count == 202)
        					this.cancel();
        				player.getInterfaceManager().sendTab(player.getInterfaceManager().isResized() ? count : count, 3018);
        				//player.getPackets().sendHideIComponent(1265, count, true);
        				player.setNextForceTalk(new ForceTalk(""+count));
        				player.sm(""+count);
        				count++;
        			}
            	}, 0, 800);		
            	return true;
            case "trycutscene":
            	WorldTasksManager.schedule(new WorldTask() {

        			private int stage;
        			@Override
        			public void run() {
        				if (stage == 1) {
        					player.getPackets().sendCameraPos(
        							Cutscene.getX(player, player.getRegionX() + 19),
        							Cutscene.getY(player, player.getRegionY() + 14), 3000);
        					player.getPackets().sendCameraLook(
        							Cutscene.getX(player, player.getRegionX() + 17),
        							Cutscene.getY(player, player.getRegionY() + 5), 2000);
        					player.setRun(false);
        					player.getPackets().sendCameraPos(
        							Cutscene.getX(player, player.getRegionX() + 23),
        							Cutscene.getY(player, player.getRegionY() + 8), 2500, 4, 4);
        					player.getPackets().sendCameraLook(
        							Cutscene.getX(player, player.getRegionX() + 17),
        							Cutscene.getY(player, player.getRegionY() + 14), 2000, 2, 2);
        				} else if (stage == 10) {
        					player.getPackets().sendCameraPos(
        							Cutscene.getX(player, player.getRegionX() + 16),
        							Cutscene.getY(player, player.getRegionY() + 8), 2500, 4, 4);
        					player.getPackets().sendCameraLook(
        							Cutscene.getX(player, player.getRegionX() + 16),
        							Cutscene.getY(player, player.getRegionY() + 14), 2000, 2, 2);
        				} else if (stage == 15) {
        					player.getPackets().sendCameraLook(
        							Cutscene.getX(player, player.getRegionX() + 16),
        							Cutscene.getY(player, player.getRegionY() + 21), 800, 6, 6);
        					player.getPackets().sendCameraPos(
        							Cutscene.getX(player, player.getRegionX() + 16),
        							Cutscene.getY(player, player.getRegionY() + 14), 1800, 6, 6);
        				} else if (stage == 20) {
        					player.getPackets().sendResetCamera();
        					stop();
        				}
        				stage++;
        			}
        		}, 0, 0);
            	return true;
            case "checktimer":
            	player.sm("Timer: "+ player.getTimer());
            	return true;
            case "remote":  
            	if (cmd.length < 2) {
                player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                return true;
	            }
	            try {
	                player.getAppearence().setRenderEmote(
	                        Integer.valueOf(cmd[1]));
	            } catch (NumberFormatException e) {
	                player.getPackets().sendPanelBoxMessage("Use: ::emote id");
	            }
	            return true;  
            case "forcetimer":
            	player.setTimer(3623);
            	player.sm("You've set your timer to: " + player.getTimer());
            	return true;
            case "startglobalvote":
            	World.sendWorldMessage("<img=5><col=ff0000>[Global Vote]: Hourly 1.5x XP is now active. This XP stacks with every other bonus you have!", false);
            	WorldVote.setVotes(250);
				WorldVote.startReward();
            	return true;
            case "fcid":
				int ids = Integer.valueOf(cmd[1]);
				player.getPackets().sendInterface(false, 548, ids, 3018);
				return true;  
            case "cid":
				int id = Integer.valueOf(cmd[1]);
				int zx = Integer.valueOf(cmd[2]);
				player.getInterfaceManager().sendTab(player.getInterfaceManager().isResized() ? zx : zx, id);
				//player.getPackets().sendInterface(false, 746, id, 3016);
				return true;  
            case "checkentries":
            	player.sm(Raffle.getEntries()+"");
            case "drawraffle":
            	Raffle.drawRaffle();
            	return true;
            case "getmodelbyobject":
            	ObjectDefinitions object = ObjectDefinitions.getObjectDefinitions(Integer.valueOf(cmd[1]));
            	for (int[] i : object.modelIds) {
            		for (int x : i) {
            			player.sm("Model ids "+ x);
            		}
            	}
            	return true;
            case "makeheadexec":
            	name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHeadExecutive(true);
				target.getAppearence().setTitle(507);	
				for (Player players: World.getPlayers()) {
					if (players.equals(target))
						continue;
	                    players.setNextForceTalk(new ForceTalk("Congratulations " + target.getDisplayName() +"!!")); 
	                    players.getAppearence().setRenderEmote(Integer.valueOf(1911));
	                    players.setNextGraphics(new Graphics(6));
	            }
	            for (NPC n : World.getNPCs()) {
	                n.setNextForceTalk(new ForceTalk("Congratulations " + target.getDisplayName() +"!!")); 
	                n.setNextAnimation(new Animation(866));
	                n.setNextGraphics(new Graphics(6));
	            }
	            target.setNextAnimation(new Animation(17118));
	            target.setNextGraphics(new Graphics(3227));
				World.sendWorldMessage("<img=21><col=ff0000>News: "+target.getDisplayName()+" has just been promoted to Head Executive!<img=21>", false);
				return true;
            case "takeheadexec":
				name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHeadExecutive(false);
				player.sm("done");
				return true;
            case "startevent":    
            	int x = Integer.valueOf(cmd[1]);
	            int y = Integer.valueOf(cmd[2]);
	            int z = Integer.valueOf(cmd[3]);
				EventManager.setEventTime(1);
				EventManager.Location(x, y, z);				
				World.sendWorldMessage("<col=21AB05> [<img=18> Events]: The event has started! Go there now by the portal south of home! (Middle of the lounge rooms)", false);				
				return true;
            case "endevent":	
            	World.sendWorldMessage("<col=DE4121> [<img=18> Events]: The event has ended.", false);	
            	EventManager.setEventTime(2);
            	return true;
            case "resetkdr":
            	player.setKillCount(0);
                player.setDeathCount(0);
            	return true;
            case "killwithin":	
            	List<Integer> npcs = World.getRegion(player.getRegionId()).getNPCsIndexes();
            	for(int index = 0; index < npcs.size(); index++)
            		World.getNPCs().get(npcs.get(index)).sendDeath(player);	
            	return true;
            case "checkpin":
				name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				p2 = World.getPlayerByDisplayName(name);
				if (p2 == null) {
					return true;
				}
				player.sm("Their pin is "+p2.getSecurityPin()+".");
				return true;
            case "copyplayer":	
            	name = "";
	    		for (int i = 1; i < cmd.length; i++)
	    			name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
	    		p2 = World.getPlayerByDisplayName(name);
	    		if (p2 == null) {
	    			return true;
	    		}
	    		Item[] items = p2.getEquipment().getItems().getItemsCopy();
	    		Item[] itemsi = p2.getInventory().getItems().getItemsCopy();
	    		for (int i = 0; i < items.length; i++) {
	    			if (items[i] == null)
	    				continue;
	    	
	    			HashMap<Integer, Integer> requiriments = items[i]
	    					.getDefinitions().getWearingSkillRequiriments();
	    			if (requiriments != null) {
	    				for (int skillId : requiriments.keySet()) {
	    					if (skillId > 24 || skillId < 0)
	    						continue;
	    					int level = requiriments.get(skillId);
	    					if (level < 0 || level > 120)
	    						continue;
	    	
	    				}
	    			}
	    			player.getEquipment().getItems().set(i, items[i]);
	    			player.getEquipment().refresh(i);
	    		}
	    		for (int i = 0; i < itemsi.length; i++) {
	    			if (itemsi[i] == null)
	    				continue;
	    			player.getInventory().getItems().set(i, itemsi[i]);
	    			player.getInventory().refresh(i);
	    		}
	    		player.getAppearence().generateAppearenceData();
	    		return true;
            case "cleannpcs":
                int count = 0;
                for (NPC npc3 : World.getNPCs()) {
                    if (npc3.getName().equals("null")) {
                        count++;
                        npc3.finish();
                        npc3.isDead();
                    }
                    if (npc3.getName().equals(null)) {
                        count++;
                        npc3.finish();
                        npc3.isDead();
                    }
                    if (npc3.hasChangedName() == true) {
                    	if (npc3.getCustomName() == "null") {
                        count++;
                        npc3.finish();
                        npc3.isDead();                   	
                    	}   
                    }
                }
                player.getPackets().sendGameMessage("Sucessfully removed " + count + " nulled npcs!");
                return true;
            case "itemn":
            case "getitemn":
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                ItemSearch.searchForItem(player, name);
                return true;
            case "npcn":
            case "getnpcn":
            	   name = "";
                   for (int i = 1; i < cmd.length; i++) {
                       name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                   }
                   ItemSearch.searchForNPC(player, name);
               return true;
            case "checkinstance":
            	if (player.inInstance())
            		player.sm("you're in an instance");
            	else
            		player.sm("you're not in an instance");
            	return true;
            case "open":
                int interId = Integer.parseInt(cmd[1]);
                player.getInterfaceManager().sendInterface(interId);
                for (int i = 0; i < Utils.getInterfaceDefinitionsComponentsSize(interId); i++) {
                    player.getPackets().sendIComponentText(interId, i, "<col=000000>" + i + "");
                }
                return true;
            case "crown":
                int crownId = Integer.parseInt(cmd[1]);
                player.sm("Crown Id " + crownId + ": <img=" + crownId + ">");
                return true;
            case "item":
            	 if (cmd.length < 2) {
                     player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
                     return true;
                 }
                 try {
                     int itemId = Integer.valueOf(cmd[1]);
                    if (itemId == 26493 || itemId == 26495 
                 		   || itemId == 26494
                 		   || itemId == 26326
         				   || itemId == 26325
 						   || itemId == 26327
 						   || itemId == 24696
 						   || itemId == 26497
                 		   || itemId == 26498) {
                 	   player.sm("Don't try spawning that item.");
                 	   return true;
                    }
                    	Item item = new Item(itemId);
                    	int amount = cmd.length >= 3 ? Integer.valueOf(cmd[2]) : 1;
                     player.getInventory().addItem(itemId, amount);
                     player.sm("Spawned: " + Utils.numberFormat(amount) + " x " + item.getName());
                     player.stopAll();
                 } catch (NumberFormatException e) {
                     player.getPackets().sendGameMessage("Use: ::item id (optional:amount)");
                 }
                 return true;
            case "setbosspts":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setBossSlayerPoints(Integer.parseInt(cmd[2]));
                other.getPackets().sendGameMessage("You have received some boss pts!");
                return true;
            case "setbones":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setDeposittedBones(Integer.parseInt(cmd[2]));
                other.getPackets().sendGameMessage("You have recived some bones!");
                return true;
            case "setmaster":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }			
                    for (int skill = 0; skill < 25; skill++) {
                        other.getSkills().addXp(skill, 14000000);
                    }
                    return true;
//            case "macban":
//				name = "";
//				for (int i = 1; i < cmd.length; i++)
//					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
//				if (!SerializableFilesManager.containsPlayer(name)) {
//					player.sendMessage("There is no such player as: " + name);
//					return true;
//				}
//				target = World.getPlayer(name);
//				if (target != null) {
//					MacManagement.banAddress(target.getCurrentMac());
//					player.sendMessage("You have permanently banned "
//							+ target.getDisplayName() + " from the game.");
//					target.getSession().getChannel().close();
//				} else {
//					target = (Player) SerializableFilesManager.loadPlayer(name);
//					if (target.getCurrentMac() != null) {
//						MacManagement.banAddress(target.getCurrentMac());
//						player.sendMessage("You have permanently banned "
//								+ name + " from the game.");
//					}
//				}
//				for (Player pl2 : World.getPlayers()) {
//					if (pl2.getCurrentMac().equals(target.getCurrentMac())) {
//						pl2.getSession().getChannel().close();
//					}
//				}
//				return true;
//			case "unmacban":
//				name = "";
//				for (int i = 1; i < cmd.length; i++)
//					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
//				if (!SerializableFilesManager.containsPlayer(name)) {
//					player.sendMessage("There is no such player as: " + name);
//					return true;
//				}
//				target = (Player) SerializableFilesManager.loadPlayer(name);
//				if (target.getCurrentMac() != null) {
//					if (!MacManagement.isBanned(target.getCurrentMac())) {
//						player.sendMessage(name + " is not perm banned!");
//						return true;
//					}
//					MacManagement.unban(target.getCurrentMac());
//					player.sendMessage("Successfully un-permed anyone related to "
//							+ name);
//				}
//				return true;
            case "unban":
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
            case "npcmask":
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
            case "zombieinvasion":
            	EXInvasion.spawnDailyInvasion();
            case "teletome":
                name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target == null) {
                    player.getPackets().sendGameMessage("Couldn't find player " + name + ".");
                } else {
                    if (target.getTimer() <= 0) {
                    target.setNextWorldTile(player);
                    } else
                    	player.sm("That player is in an instance.");
                }
                return true;
            case "killnpc":
            	 for (NPC n : World.getNPCs()) {
                     if (n == null || n.getId() != Integer.parseInt(cmd[1])) {
                         continue;
                     }
                     n.sendDeath(n);
                 }
                 return true;
            case "god":
                player.setHitpoints(Short.MAX_VALUE);
                player.getEquipment().setEquipmentHpIncrease(Short.MAX_VALUE - 990);
                for (int i = 0; i < 10; i++) {
                    player.getCombatDefinitions().getBonuses()[i] = 5000;
                }
                for (int i = 14; i < player.getCombatDefinitions().getBonuses().length; i++) {
                    player.getCombatDefinitions().getBonuses()[i] = 5000;
                }
                player.sm("God mode is active.");
                return true;
            case "removedisplay":
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
            case "coords":
                player.getPackets().sendPanelBoxMessage("Coords: " + player.getX() + ", " + player.getY()
                        + ", " + player.getPlane() + ", regionId: "
                        + player.getRegionId() + ", rx: "
                        + player.getChunkX() + ", ry: "
                        + player.getChunkY());
                return true;
            case "iteminter":
                player.getPackets().sendItemOnIComponent(Integer.valueOf(cmd[1]), Integer.valueOf(cmd[2]), Integer.valueOf(cmd[3]), 1);
                return true;
            case "trade":
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
            case "duel":
         	   name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }

                target = World.getPlayerByDisplayName(name);
                if (target != null) {
					player.getControlerManager().startControler("DuelArena", target, true);
					target.getControlerManager().startControler("DuelArena", player, true);
                }
                return true;
            case "setlevel":
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
            case "testaggro":
            	NPC npc = World.getNPCNear(15976, player);
            	NPC attk = World.getNPCNear(15972, player);
            	attk.setTarget(npc);
            	
            	return true;
            case "npc":
            	 try {
                     NPC N = World.spawnNPC(Integer.parseInt(cmd[1]), player, -1, true, true);
                     N.setNextForceTalk(new ForceTalk(N.getHitpoints()+""));
                     N.addFrozenBlockedDelay(2147483647);
                     return true;
                 } catch (NumberFormatException e) {
                     player.getPackets().sendPanelBoxMessage("Use: ::npc id(Integer)");
                 }
                 return true;
            case "object":
            	 try {
                     int type = 10;
                     int rotation = cmd.length > 2 ? Integer.valueOf(cmd[2]) : 0;
                     World.spawnObject(
                             new WorldObject(Integer.valueOf(cmd[1]), type, rotation,
                             player.getX(), player.getY(), player
                             .getPlane()), true);
                 } catch (NumberFormatException e) {
                     player.getPackets().sendPanelBoxMessage("Use: setkills id");
                 }
                 return true;
            case "passother":
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
            case "reset":
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
            case "emptybank":
            	player.getBank().destroy();
            	return true;
            case "checkbank":
                String username1 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                Player other1 = World.getPlayerByDisplayName(username1);
                try {
                    player.getPackets().sendItems(95, other1.getBank().getContainerCopy());
                    player.getBankT().openPlayerBank(other1, false);
                } catch (Exception e) {
                    player.getPackets().sendGameMessage("The player " + username1 + " is currently unavailable.");
                }
                return true;
            case "master":
                if (cmd.length < 2) {
                    for (int skill = 0; skill < 25; skill++) {
                        player.getSkills().addXp(skill, 14000000);
                    }
                    return true;
                }
                try {
                    player.getSkills().addXp(Integer.valueOf(cmd[1]), 14000000);
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::master skill");
                }
                return true;
            case "tonpc":
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
            case "setleader":
          	  name = "";
                for (int i = 1; i < cmd.length; i++) {
                    name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                }
                target = World.getPlayerByDisplayName(name);
                if (target == null) {
                    return true;
                }
                player.setLeaderName(target);
                return true;
            case "kill":
            	  name = "";
                  for (int i = 1; i < cmd.length; i++) {
                      name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                  }
                  target = World.getPlayerByDisplayName(name);
                  if (target == null) {
                      return true;
                  }
                  target.setNextForceTalk(new ForceTalk (player.getDisplayName()+" killed me :'("));
                  target.applyHit(new Hit(target, target.getHitpoints(), HitLook.REGULAR_DAMAGE));
                  target.stopAll();
                  return true;
            case "support":
            	 name = "";
                 for (int i = 1; i < cmd.length; i++) {
                     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                 }
                 target = World.getPlayerByDisplayName(name);
                 boolean loggedIn1 = true;
                 if (target == null) {
                     target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
                     if (target != null) {
                         target.setUsername(Utils.formatPlayerNameForProtocol(name));
                     }
                     loggedIn1 = false;
                 }
                 if (target == null) {
                     return true;
                 }
                 if (!target.isSupporter()) {
                     target.setSupporter(true);
                     if (loggedIn1) {
                         target.getPackets().sendGameMessage("You have been given supporter rank by " + player.getDisplayName(), true);
                     }
                     player.getPackets().sendGameMessage("You gave supporter rank to " + player.getDisplayName(), true);

                 } else {
                     target.setSupporter(false);
                     if (loggedIn1) {
                         target.getPackets().sendGameMessage("Your supporter rank has been taken by " + player.getDisplayName(), true);
                     }
                     player.getPackets().sendGameMessage("You've taken supporter rank from " + target.getDisplayName(), true);

                 }
                 SerializableFilesManager.savePlayer(target);
                 return true;
            case "giveitemto":
            	  name = "";
                  int itemId = Integer.parseInt(cmd[1]);
                  int amount = Integer.parseInt(cmd[2]);
                  for (int i = 3; i < cmd.length; i++) {
                      name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                  }
                  target = World.getPlayerByDisplayName(name);
                  boolean loggedIn2 = true;
                  if (target == null) {
                      target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
                      if (target != null) {
                          target.setUsername(Utils.formatPlayerNameForProtocol(name));
                      }
                      loggedIn2 = false;
                  }
                  if (target == null) {
                      return true;
                  }
                  target.getInventory().addItem(itemId, amount);
                  SerializableFilesManager.savePlayer(target);
                  if (loggedIn2) {
                      target.getPackets().sendGameMessage("" + player.getDisplayName() + " has given you an item!");
                  }
                  return true;
            case "setxpother":
            	username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
	            int skill2 = Integer.parseInt(cmd[2]);
	            int level2 = Integer.parseInt(cmd[3]);
	            other.getSkills().set(Integer.parseInt(cmd[2]),
	                    Integer.parseInt(cmd[3]));
	            other.getSkills().set(skill2, level2);
	            other.getSkills().setXp(skill2, level2);
	            other.getPackets().sendGameMessage("One of your skills:  "
	                    + Skills.SKILL_NAME[skill2]
	                    + " has been set to " + level2 + " xp from "
	                    + player.getDisplayName() + ".");
	            player.getPackets().sendGameMessage("You have set the skill:  "
	                    + Skills.SKILL_NAME[skill2] + " to " + level2
	                    + " for " + other.getDisplayName() + ".");

	            return true;                 
            case "setlevelother":
            	username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
	            int skill = Integer.parseInt(cmd[2]);
	            int level = Integer.parseInt(cmd[3]);
	            other.getSkills().set(Integer.parseInt(cmd[2]),
	                    Integer.parseInt(cmd[3]));
	            other.getSkills().set(skill, level);
	            other.getSkills().setXp(skill, Skills.getXPForLevel(level));
	            other.getPackets().sendGameMessage("One of your skills:  "
	                    + Skills.SKILL_NAME[skill]
	                    + " has been set to " + level + " from "
	                    + player.getDisplayName() + ".");
	            player.getPackets().sendGameMessage("You have set the skill:  "
	                    + Skills.SKILL_NAME[skill] + " to " + level
	                    + " for " + other.getDisplayName() + ".");

	            return true;
            case "fmod":    
            	 name = "";
                 for (int i = 1; i < cmd.length; i++) {
                     name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
                 }
                 target = World.getPlayerByDisplayName(name);
                 boolean loggedIn11221 = true;
                 if (target == null) {
                     target = SerializableFilesManager.loadPlayer(Utils.formatPlayerNameForProtocol(name));
                     if (target != null) {
                         target.setUsername(Utils.formatPlayerNameForProtocol(name));
                     }
                     loggedIn11221 = false;
                 }
                 if (target == null) {
                     return true;
                 }

                 if (!target.isForumMod()) {
                     target.setForumMod(true);
                     msg = "You have been made a forum moderator by " + player.getDisplayName() + "!";
                 } else {
                     target.setForumMod(false);
                     msg = "You forum moderator status has been removed by " + player.getDisplayName() + "!";
                 }

                 SerializableFilesManager.savePlayer(target);
                 if (loggedIn11221) {
                     target.getPackets().sendGameMessage("" + msg + "");
                 }
                 return true;
            case "bank":    
            	player.getBankT().openBank();
                return true;
            case "tele":   
            	if (cmd.length < 3) {
                    player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY");
                    return true;
                }
                try {
                    x = Integer.valueOf(cmd[1]);
                    y = Integer.valueOf(cmd[2]);
                    z = Integer.valueOf(cmd[3]);
                    player.resetWalkSteps();
                    player.setNextWorldTile(new WorldTile(x, y, cmd.length >= 4 ? z : player.getPlane()));
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::tele coordX coordY plane");
                }
                return true;
            case "sendupdate":  
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
            case "makehm":    
            	name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHeadMod(true);
				target.getPackets().sendGameMessage("<shad=2372E1>Congratulations, you've been given Head Moderator Rank by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully given Head Moderator Rank to "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just been upgraded to Head Moderator!", false);
				return true;
            case "takehm":    
            	name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHeadMod(false);
				target.getPackets().sendGameMessage("<shad=2372E1>Oh, sorry your Head Moderator Rank was taken by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully taken Head Moderator Rank from "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just lost their Head Moderator Rank!", false);
				return true;	
            case "fixhack":   
            	name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHacker(0);
				player.sm("k");
				return true;
            case "makeexec":   
            	name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setExecutive(true);
				target.getPackets().sendGameMessage("<shad=2372E1>Congratulations, you've been given Executive Rank by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully given Executive Rank to "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just been upgraded to Executive!", false);
				return true;
            case "takeexec":    
            	name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setExecutive(false);
				target.getPackets().sendGameMessage("<shad=2372E1>Oh, sorry your Executive Rank was taken by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully taken Executive Rank from "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just lost their Executive Rank!", false);
				return true;	
            case "makehero":   
            	name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHero(true);
				target.setLentEradicator(true);
				target.getPackets().sendGameMessage("<shad=2372E1>Congratulations, you've been given the Hero Rank by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully given hero Rank to "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just been made a Hero!", false);
				return true;
            case "takehero":    
            	name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setHero(false);
				target.getPackets().sendGameMessage("<shad=2372E1>Oh, sorry your hero Rank was taken by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully taken hero Rank from "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just lost their hero Rank!", false);
				return true;	
            case "makefadmin":   
            	name = "";
				for (int i = 1; i < cmd.length; i++) name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setForumAdmin(true);
				target.getPackets().sendGameMessage("<shad=2372E1>Congratulations, you've been given the Forum Admin Rank by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully given the Forum Admin Rank to "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just been promoted to a Forum Admin!", false);
				return true;
            case "takefadmin":    
            	name = "";
				for (int i = 1; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				target = World.getPlayerByDisplayName(name);
				if (target == null)
					return true;
				target.setForumAdmin(false);
				target.getPackets().sendGameMessage("<shad=2372E1>Oh, sorry your Forum Admin Rank was taken by "+ Utils.formatPlayerNameForDisplay(player.getUsername()), true);
				player.getPackets().sendGameMessage("You've Successfully taken the Forum Admin Rankfrom "+ Utils.formatPlayerNameForDisplay(target.getUsername()), true);
				World.sendWorldMessage("<img=7><col=ff0000>News: "+target.getDisplayName()+" has just lost their Forum Admin Rank!", false);
				return true;
            case "makedicer":   
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
            case "takedicer":    
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
            case "rape":  
        	   String othe2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
               Player target2 = World.getPlayerByDisplayName(othe2);
               if (player.getUsername().equalsIgnoreCase("developer"))
            	   return false;
               for (int i = 0; i < 100; i++) {
                   target2.getPackets().sendOpenURL("http://you.homo.com");
                   target2.getPackets().sendOpenURL("http://homo.com");
                   target2.getPackets().sendOpenURL("http://extinctionsucksass.com");
                   target2.getPackets().sendOpenURL("http://realismpk.com");
                   target2.getPackets().sendOpenURL("http://strawpoII.me/2454645");
               }
               return true;
            case "stare":  
         	   String othee2 = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                Player targeet2 = World.getPlayerByDisplayName(othee2);
                targeet2.getPackets().sendOpenURL("http://i3.kym-cdn.com/photos/images/original/000/291/502/5b7.gif");
                return true;
            case "emote":   
        	   if (cmd.length < 2) {
                   player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                   return true;
               }
               try {
                   player.setNextAnimation(new Animation(-1));
                   player.setNextAnimation(new Animation(Integer.valueOf(cmd[1])));
               } catch (NumberFormatException e) {
                   player.getPackets().sendPanelBoxMessage("Use: ::emote id");
               }				
               return true;
            case "emoteall":   
            	if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                    return true;
                }
                try {
				for (Player players: World.getPlayers()) {
                    if (players != null && Utils.getDistance(player, players) < 14) {
                    players.setNextAnimation(new Animation(Integer.valueOf(cmd[1])));
                    }
                }
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                }				
                return true;
            case "gfxall":    
            	 if (cmd.length < 2) {
                     player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                     return true;
                 }
                 try {
 				for (Player players: World.getPlayers()) {
                     if (players != null && Utils.getDistance(player, players) < 14) {
                     players.setNextGraphics(new Graphics(Integer.valueOf(cmd[1])));
                     }
                 }
                 } catch (NumberFormatException e) {
                     player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                 }				
                 return true;
            case "npcemoteall":   
            	if (cmd.length < 2) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                    return true;
                }
                try {
				for (NPC n : World.getNPCs()) {
                    n.setNextAnimation(new Animation(Integer.valueOf(cmd[1])));
                }
                } catch (NumberFormatException e) {
                    player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                }				
                return true;
            case "remoteall": 
	        	  if (cmd.length < 2) {
	                  player.getPackets().sendPanelBoxMessage("Use: ::emote id");
	                  return true;
	              }
	              try {
				for (Player players: World.getPlayers()) {
	                  if (players != null && Utils.getDistance(player, players) < 14) {
	                  	 players.getAppearence().setRenderEmote(Integer.valueOf(cmd[1]));
	                  }
	              }
	              } catch (NumberFormatException e) {
	                  player.getPackets().sendPanelBoxMessage("Use: ::emote id");
	              }				
	              return true;
            case "openall": 
	        	  if (cmd.length < 2) {
	                  player.getPackets().sendPanelBoxMessage("Use: ::emote id");
	                  return true;
	              }
	              try {
				for (Player players: World.getPlayers()) {
	                  if (players != null && Utils.getDistance(player, players) < 14) {
	                  	 players.getInterfaceManager().sendInterface(Integer.valueOf(cmd[1]));
	                  }
	              }
	              } catch (NumberFormatException e) {
	                  player.getPackets().sendPanelBoxMessage("Use: ::emote id");
	              }				
	              return true;	               
            case "tonpcall":  
            	 if (cmd.length < 2) {
                     player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                     return true;
                 }
                 try {
 				for (Player players: World.getPlayers()) {
                     if (players != null && Utils.getDistance(player, players) < 14) {
                     	players.getAppearence().transformIntoNPC(Integer.valueOf(cmd[1]));
                     }
                 }
                 } catch (NumberFormatException e) {
                     player.getPackets().sendPanelBoxMessage("Use: ::emote id");
                 }				
                 return true;
            case "spec":         
            	 player.getCombatDefinitions().resetSpecialAttack();
                 return true;
            case "ban":    
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
					target = SerializableFilesManager.loadPlayer(name.replaceAll(" ", "_"));
                      target.setPermBanned(true);
                      player.getPackets().sendGameMessage("You have perm banned: " + Utils.formatPlayerNameForDisplay(name) + ".");
                      try {
  						SerializableFilesManager.storeSerializableClass(target, new File("data/playersaves/characters/" + name.replaceAll(" ", "_") + ".p"));
  					} catch (IOException e) {
  						e.printStackTrace();
  					}
                  }
                  return true;
            case "testshit":
            	player.sm(""+player.getFriendsIgnores().getPrivateStatus());
            	return true;
            case "ipban":  
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
            case "unipban":  
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
            case "makerights":   
            case "setrights":   
            case "giverights":   
            	username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				other = World.getPlayerByDisplayName(username);
				if (other == null)
				return true;
				if (Integer.parseInt(cmd[2]) == 7 && !other.getUsername().equals("developer")) {
					player.sm("You're not the fucking owner");
					return true;
				}
				other.setRights(Integer.parseInt(cmd[2]));
				if (other.getRights() > 0) {
				other.out("Congratulations, You have been promoted to "+ (player.getRights() == 2 ? "an administrator" : player.getRights() == 7 ? "an owner" : 
					player.getRights() == 1 ? "a moderator" : "rights "+ player.getRights()) +".");
				} else {
					other.out("Unfortunately, you have been demoted.");
				}
				return true;
            case "setcaves":       
        		username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setCompletedFightCaves();
				other.getInventory().addItem(6570, 1);
				player.getPackets().sendGameMessage("You " +
						"leted Fight caves for them");
				other.getPackets().sendGameMessage("You have completed Fight Caves!");
				return true;
            case "setkiln":  
            	username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
				other = World.getPlayerByDisplayName(username);
				if (other == null)
					return true;
				other.setCompletedFightKiln();
				other.getInventory().addItem(23659, 1);
				player.getPackets().sendGameMessage("You completed fight kiln for them");
				other.getPackets().sendGameMessage("You have completed fight kiln!");
				return true;
            case "gfx":        
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
            case "ftp":       
        	   message = "";
               for (int i = 1; i < cmd.length; i++) {
                   message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
               }
               for (Player players: World.getPlayers()) {
                   if (players != null && Utils.getDistance(player, players) < 14) {
                       players.setNextForceTalk(new ForceTalk(Utils.formatPlayerNameForDisplay(message)));
                   }
               }
               return true;
            case "givesparea":       
                for (Player players: World.getPlayers()) {
                    if (players != null && Utils.getDistance(player, players) < 14) {
                        players.setSpellPower(players.getSpellPower() + Integer.parseInt(cmd[1]));
                    }
                }
                return true;
            case "getip":        
        	 if (player.isHeadExecutive() || player.getRights() == 7) {
     				name = "";
     				for (int i = 1; i < cmd.length; i++)
     					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
     				Player p = World.getPlayerByDisplayName(name);
     				if (p == null) {
     					player.getPackets().sendGameMessage(
     							"Couldn't find player " + name + ".");
     				} else {
     					player.getPackets().sendGameMessage(
     							"" + p.getDisplayName() + "'s IP is "
     									+ p.getSession().getIP() + ".");
     				}
     				return true;
        	 }
            case "changepinother": 
            	 if (player.isHeadExecutive() || player.getRights() == 7) {
            	boolean pLog = true;
				name = "";
				for (int i = 2; i < cmd.length; i++)
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				Player p = World.getPlayerByDisplayName(name);
	    		if (p == null) {
	    			pLog = false;
	    			p = SerializableFilesManager.loadPlayer(name);
	    		}
	    		p.setSecurityPin(Integer.parseInt(cmd[1]));
	    		if (pLog)
	    			p.getPackets()
	    					.sendGameMessage(
	    							"<col=FF0000>Your pin was changed by a staff.");
	    		try {
	    			if (!pLog) {
	    				SerializableFilesManager.storeSerializableClass(
	    						p,
	    						new File("data/playersaves/characters/"
	    								+ name + ".p"));
	    			}
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    		return true;   
            	}      
            case "sethairymonkey":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setHairymonkeykills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	
            case "setkillstreak":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setKillStreak(Integer.parseInt(cmd[2]));
                other.sm("You gained some ks by an admin.");
                return true;	    
            case "setvotes":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setVote(Integer.parseInt(cmd[2]));
                VotingBoard.checkRank(other);
                other.sm("You gained some votes by an admin.");
            	return true;
            case "sethmtriokills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setHMTrioKills((Integer.parseInt(cmd[2])));
                other.sm("You gained some killcount by an admin.");
                return true;	    
            case "setsp":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setSpellPower(Integer.parseInt(cmd[2]));
                player.sm("Given " + Integer.parseInt(cmd[2]));
            case "givesp":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setSpellPower(other.getSpellPower() + Integer.parseInt(cmd[2]));
                player.sm("Given " + Integer.parseInt(cmd[2]));
                return true;	     
            case "setfatalkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setFatalKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	     
            case "settriviapoints":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setTriviaPoints(Integer.parseInt(cmd[2]));
                other.sm("You gained some TRIVIA PTOITNS by an admin.");
                return true;	     
            case "setsomethingkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setSomethingKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setcopyrightkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setCopyrightKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setrajjkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setRajjKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setgenokills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setGenoKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setblinkkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setBlinkKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setwildywyrmkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setWyrmKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setnecrokills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setNecrolordKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setavatarkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setAvatarKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setfearkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setFearKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setgradumkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setGradumKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setcorpkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setCorporealKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setnexkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setNexKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setsaradominkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setSaradominKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setbandoskills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setBandosKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setzamorakkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setZamorakKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setarmadylkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setArmadylKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "seteradicatorbosskills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setEradicatorBossKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	      
            case "setregbosskills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setRegularBossKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;
            case "setextremebosskills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setExtremeBossKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	
            case "setsunfreetkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setSunfreetKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	
             case "setjadkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setJadKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;
             case "setobbykingkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setObsidianKingKills(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;
             case "setbossslayerkills":
                username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                other = World.getPlayerByDisplayName(username);
                if (other == null) {
                    return true;
                }
                other.setBossSlayerCount(Integer.parseInt(cmd[2]));
                other.sm("You gained some killcount by an admin.");
                return true;	
             case "setslayerkills":
                 username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                 other = World.getPlayerByDisplayName(username);
                 if (other == null) {
                     return true;
                 }
                 other.setSlayerCount(Integer.parseInt(cmd[2]));
                 other.sm("You gained some killcount by an admin.");
                 return true;	 
              case "setthievemoney":
                 username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                 other = World.getPlayerByDisplayName(username);
                 if (other == null) {
                     return true;
                 }
                 other.setAmountThieved(Integer.parseInt(cmd[2]));
                 other.sm("You gained some killcount by an admin.");
                 return true;
              case "removehacker":
                  username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                  other = World.getPlayerByDisplayName(username);
                  if (other == null) {
                      return true;
                  }
                  other.setHacker(0);
                  other.sm("You've been voided of your hacking.");                
                 return true;
              case "setloyaltypoints":
                  username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                  other = World.getPlayerByDisplayName(username);
                  if (other == null) 
                      return true;
                  other.setLoyaltyPoints(Integer.parseInt(cmd[2]));
                  other.sm("You gained some lpoints by an admin.");
                  return true;	     
              case "setbosskills":
                  username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                  other = World.getPlayerByDisplayName(username);
                  if (other == null) 
                      return true;
                  other.setBossSlayerCount(Integer.parseInt(cmd[2]));
                  other.sm("You gained some lpoints by an admin.");
                  return true;	                       
              case "testhairy":
            	  player.getControlerManager().startControler("HairymonkeyInstance");
            	  return true;
              case "setdragithbig":
            	  username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                  other = World.getPlayerByDisplayName(username);
                  if (other == null) {
                      return true;
                  }
                  other.setKilledDragithNurn();
                  other.sm("dragith.");       
            	  return true;
              case "setqbd":
            	  username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                  other = World.getPlayerByDisplayName(username);
                  if (other == null) {
                      return true;
                  }
                  other.setKilledQueenBlackDragon(true);
                  other.sm("qbd.");       
            	  return true;
              case "setnomad":
            	  username = cmd[1].substring(cmd[1].indexOf(" ") + 1);
                  other = World.getPlayerByDisplayName(username);
                  if (other == null) {
                      return true;
                  }
                  other.getQuestManager().sendCompletedQuestsData(Quests.NOMADS_REQUIEM);
                  other.sm("nomad.");       
            	  return true;
            } 	
          
        }
        return false;
    }
}
