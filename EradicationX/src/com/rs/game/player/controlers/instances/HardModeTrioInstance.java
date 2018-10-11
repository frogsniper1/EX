package com.rs.game.player.controlers.instances;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.instances.InstanceHardModeTrio;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class HardModeTrioInstance extends Controler {


	private int[] boundChuncks;
	private long minutes;
	private long seconds;
	private boolean instanceover = false;
	public int health;
	private WorldTile ORIGINAL_LOCATION =  new WorldTile(3810, 4691, 0);

	@Override
	public void start() {
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(474, 585, boundChuncks[0], boundChuncks[1], 8);	
		instanceover = false;
		player.setDestroyTimer(0);
		player.setisDestroytimer(false);
		player.joined.clear();
		try {
		loadInstance();
		sendInterfaces();
		} catch (Throwable t) {
			Logger.handle(t);
		}
		health = 45000;
		player.setTimer(3600);
	}
		 	
	private void finishTimer() {
		player.setTimer(0);
		player.setInstanceBooth(0);
		minutes = 0;
		seconds = 0;
	}
	
	
    private void startTimer() {
    	WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (instanceover)
					this.stop();
				if (player.getTimer() == 0 || player.getTimer() < 1) {
					player.sm("Your timer has ended! The NPCs will no longer spawn.");
					finishTimer();
					for (Player players: World.getPlayers()) {
						 if (players.getLeaderName() == player) {
	                    	players.setTimer(0);
	                    	players.setInstanceBooth(0);
	                    	}
	                }
					player.setInstanceBooth(0);
					player.setTimer(0);
					this.stop();
					exitInstance(true); 
					return;
				}
				if (player.getEnd() == true) {
					for (Player players: World.getPlayers()) {
	                    if (players.getLeaderName() == player) {
	                    	players.setInstanceEnd(true);
	        				players.setNextWorldTile(ORIGINAL_LOCATION);
	    					players.sm("The leader has disbanded the group!");
	                }
					}
					player.setInstanceEnd(false);
					player.setNextWorldTile(ORIGINAL_LOCATION);
					exitInstance(false);
					CoresManager.slowExecutor.schedule(new Runnable() {
						@Override
						public void run() {
							RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
						}
					}, 1200, TimeUnit.MILLISECONDS);
					this.stop();
					return;
				} else
				player.setTimer(player.getTimer() - 1);
				if (player.getInstanceBooth() > 0)
				player.setInstanceBooth(player.getTimer());
				for (Player players: World.getPlayers()) {
					 if (players.getLeaderName() == player) {
                    	players.setTimer(player.getTimer());
                    	}
                }				
				seconds = player.getTimer() % 60;
				minutes = TimeUnit.SECONDS.toMinutes(player.getTimer());	
			}
    	}, 0, 1);
    }
      
	
	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.sm("You lose 10 minutes from the timer because of your death.");
					player.setTimer(player.getTimer() - 600);
				} else if (loop == 3) {			
					player.reset();
					player.setNextWorldTile(getWorldTile(81-64, 76-64));
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}	
	
	
	@Override
	public void sendInterfaces() {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 8, 3021);	
    	CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (instanceover) {
					this.cancel();
					return;
				}
			 String String = java.lang.String.format("%02d:%02d", minutes, seconds);
				if (health > 0 && health <= 45000)
					player.getPackets().sendIComponentText(3021, 1, Utils.formatNumber(health));
				else
					player.getPackets().sendIComponentText(3021, 1, "Dead");
				player.getPackets().sendIComponentText(3021, 2, String);
			}
    	}, 0, 400);				
	}			
	
	@Override
	public boolean login() {
		player.joined.clear();
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(474, 585, boundChuncks[0], boundChuncks[1], 8);	
		player.setNextWorldTile(ORIGINAL_LOCATION);
		loadInstance();
		sendInterfaces();
		health = 45000;
		return false;
	}
	
	public void startWave() {
		try {
		new NPC(45, getWorldTile(81-64, 75-64), -1, false, true);
		new InstanceHardModeTrio(15977, getSpawnTile(0), player);
		new InstanceHardModeTrio(15978, getSpawnTile(1), player);
		new InstanceHardModeTrio(15979, getSpawnTile(2), player);
		} catch (Throwable t) {
			Logger.handle(t);
		}
	}
	
	
	public void loadInstance() {
		if (player.getHacker() == 2) {
			player.sm("Enter your pin.");
		} else {
		player.lock();
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				player.sm("Processing, please wait....");
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {	
						try {
						player.setForceMultiArea(true);
						player.unlock();
						player.setNextWorldTile(getWorldTile(81-64, 76-64));		
						startWave();
						startTimer();
						player.sm("Instance loaded. If the room is white, please relog.");
						} catch (Throwable t) {
							Logger.handle(t);
						}
					}
				}, 1);	
			}
			
		});
		
		}
	}

	public WorldTile getSpawnTile(int num) {
		switch (num) {
		case 0:
			return getWorldTile(79-64, 93-64);
		case 1:
			return getWorldTile(81-64, 93-64);
		case 2:
			return getWorldTile(83-64, 93-64);
		default:
			return getWorldTile(32, 30);
		}
	}




	@Override
	public void process() {
		if (!player.getInterfaceManager().containsInterface(3021)) 
			sendInterfaces();
		if (health > 0) {
			ArrayList<NPC> npcs = World.getNPCsNear(player);
			for (NPC npc : npcs) 
				npc.setHP(health);
		for (Player check : World.getPlayers()) {
			if (check == null)
				continue;
			if (check.withinDistance(player, 50) && !player.joined.contains(check)) {
				if (check.getControlerManager() == null)
					continue;
				if (check.getControlerManager().toString() == null)
					continue;
				if (check.getControlerManager().toString().toLowerCase().contains("hardmode"))
					player.joined.add(check);
			}
		}
		for (int i = 0; i < player.joined.size(); i++) {
			if (player.joined.get(i) == null)
				continue;
			if (!player.joined.get(i).withinDistance(player, 80)) {
				player.joined.remove(i);
				i--;
				continue;
			}
		}
		for (Player checker : player.joined) {
			if (checker == null)
				continue;
			if (!checker.withinDistance(player, 80))
				player.joined.remove(checker);
		}
		} else if (health <= 0) {
			ArrayList<NPC> npcs = World.getNPCsNear(player);
			for (NPC npc : npcs) 
				npc.sendDeath(player);
			health = 500000;
			ArrayList<Player> players = new ArrayList<Player>();
			for (Player p : World.getPlayers()) 
				if (player.withinDistance(p, 100)) 
					players.add(p);
			for (Player p : players) {
				p.setSpellPower(p.getSpellPower()+6);
			}
			Item item = null;
			int[] rares = {27754, 27755, 27756, 27757, 27758, 27759, 27760};
			if (Utils.random(75) == 50) {
				item = new Item(rares[Utils.random(7)], 1);
			} else
				item = new Item(2996, Utils.random(1, 2));
			Player winner = players.get(Utils.random(players.size()));
			winner.sm("You received: " + Utils.formatNumber(item.getAmount()) + " "+ item.getName()+ ".");
			if (winner.getInventory().hasFreeSlots()) {
				winner.getInventory().addItem(item);
			} else {
				winner.sm("Your loot has been sent to your bank.");
				winner.getBank().addItem(item.getId(), item.getAmount(), true);
			}
			if (item.getId() != 2996) {
			  if (winner.getMessageIcon() != 0) {
               	 World.sendWorldMessage("<img=5>[Drop Feed]<col=FF0000> <img=" + winner.getMessageIcon() + ">"+ winner.getDisplayName() + "</col> just recieved a <col=FF0000>"+ Utils.formatPlayerNameForDisplay(item.getName()) +"</col> drop!", false);            	 
			  	} else {
               	 World.sendWorldMessage("<img=5>[Drop Feed]<col=FF0000> "+ winner.getDisplayName() + "</col> just recieved a <col=FF0000>"+ Utils.formatPlayerNameForDisplay(item.getName()) +"</col> drop!", false);            	 
                }
			}
			for (Player p : players) {
				p.setHMTrioKills(p.getHMTrioKills()+1);
				p.sm( "You gain a HM Trio kill count. You now have "
                                    + p.getHMTrioKills()
                                    + " kills.");
				if (p.getHMTrioKills() % 100 == 0) {
				World.sendWorldMessage("<img=5>[Kill Count] colorhere " + p.getDisplayName() + " </col> has killed colorhere"+ p.getHMTrioKills() + " Hard Mode Trios!</col>", false);
				if (p.getHMTrioKills() == 100) p.sm("<col=32CF13>You can now access a title by ;;hmtriotitle");		
				}	
				p.sendLootBox(item, winner);
				if (!p.getDisplayName().equals(winner.getDisplayName()))
					p.sm(winner.getDisplayName()+ " received: " + Utils.formatNumber(item.getAmount()) + " "+ item.getName()+ ".");
			}
		}
	}
	
	public void exitInstance(boolean isTimeEnd) {
		player.setInstancePin(0);
		instanceover = true;
		player.setTimer(0);
		minutes = 0;
		seconds = 0;
		finishTimer();
		for (NPC npc : World.getNPCs()) {
			if (player.withinDistance(npc, 40))
				npc.sendDeathNoDrop(player);
		}
		player.setForceMultiArea(false);
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 11 : 8);
		removeControler();
		if (isTimeEnd) {
		player.sm("You have been removed from the instance.");
		player.setDestroyTimer(0);
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		player.setNextWorldTile(ORIGINAL_LOCATION);
		}
		/*if (isTimeEnd) {
			player.setDestroyTimer(60);
			for (Player players: World.getPlayers()) {
				 if (players.getLeaderName() == player) {
					 players.sm("You will be removed from the instance in 60 seconds.");
               	}
           }
			player.sm("You will be removed from the instance in 60 seconds.");
		   WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (player.getDestroyTimer() == 40) {
						player.sm("You will be removed from the instance in 40 seconds.");
					for (Player players: World.getPlayers()) {
						 if (players.getLeaderName() == player) {
							 players.sm("You will be removed from the instance in 40 seconds.");
		               	}
		           }
					}
					if (player.isDestroytimer() == true) {
						player.setisDestroytimer(false);
						RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
						this.stop();
						return;				
					}					
					if (player.getDestroyTimer() == 20) {
						player.sm("You will be removed from the instance in 20 seconds.");
					for (Player players: World.getPlayers()) {
						 if (players.getLeaderName() == player) {
							 players.sm("You will be removed from the instance in 20 seconds.");
		               	}
		           }
					}	
					if (player.getDestroyTimer() == 10) {
						player.sm("You will be removed from the instance in 10 seconds.");
					for (Player players: World.getPlayers()) {
						 if (players.getLeaderName() == player) {
							 players.sm("You will be removed from the instance in 10 seconds.");
		               	}
		           }
					}
					if (player.getDestroyTimer() == 0) {
						player.sm("You have been removed from the instance.");
					for (Player players: World.getPlayers()) {
						 if (players.getLeaderName() == player) {
								player.sm("You have been removed from the instance.");
		               	}
		           }
					
					this.stop();
						return;
					}
					player.setDestroyTimer(player.getDestroyTimer() - 1);	
					seconds = player.getTimer() % 60;
					minutes = TimeUnit.SECONDS.toMinutes(player.getTimer());
				}
			}, 0, 1);
		}*/
		}	

	@Override
	public void magicTeleported(int type) {
	}

	public WorldTile getWorldTile(int mapX, int mapY) {
		try {
		return new WorldTile(boundChuncks[0] * 8 + mapX, boundChuncks[1] * 8
				+ mapY, 0);
		} catch (Throwable t) {
			Logger.handle(t);
		}
		return new WorldTile(0,0,0);
	}
	public int getWorldTileX(int mapX) {
		return boundChuncks[0] * 8 + mapX;
	}	
	public int getWorldTileY(int mapY) {
		return boundChuncks[1] * 8 + mapY;
	}	
	

	@Override
	public boolean logout() {
		for (Player players: World.getPlayers()) {
			 if (players.getLeaderName() == player) {
            		players.setLeaderName(null);
            	}
        }
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder
						.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
		return false;

	}
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		if (object.getId() == 66205) {
			if (health < 45000 && (object.getY() < player.getY())) {
				player.sm("The fight has already begun. You cannot flee.");
				return false;
			}
			player.getDialogueManager().startDialogue("HardModeTrioPortal", object.getY());	
			return false;
		}
		if (object.getId() == 12351) {
			if (object.getY() < player.getY()) {
				if (health < 45000) {
					player.sm("The fight has already begun. You cannot flee.");
					return false;
				}
				player.setNextWorldTile(new WorldTile(player.getX(), player.getY()-2, player.getPlane()));
			} else
				player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+2, player.getPlane()));
			return false;
		}
		return true;
	}	

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To end the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To end the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To end the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}
}
