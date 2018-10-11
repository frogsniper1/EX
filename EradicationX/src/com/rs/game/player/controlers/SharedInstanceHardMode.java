package com.rs.game.player.controlers;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.controlers.instances.HardModeTrioInstance;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class SharedInstanceHardMode extends Controler {

	private long minutes;
	private long seconds;
	private boolean instanceover;
	private Player leader;


	@Override
	public void start() {
		player.setDestroyTimer(0);
		player.setisDestroytimer(false);
		leader = player.getLeaderName();
		player.setTimer(leader.getTimer());
		try {
		sendInterfaces();
		loadInstance(false);
		} catch (Throwable t) {
			Logger.handle(t);
		}
		instanceover = false;
		player.setSpawnRate(leader.getSpawnRate());
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
					player.setNextAnimation(new Animation(17768));
					player.setNextGraphics(new Graphics(3425));
				} else if (loop == 1) {
					player.sm("You lose 10 minutes from the timer because of your death.");
					leader.sm("A player has died, you lose 10 minutes from your timer. You may kick the player with the portal if needed.");
					leader.setTimer(leader.getTimer() - 600);
				} else if (loop == 3) {			
					player.reset();
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 2);
		return false;
	}	
	
	@Override
	public boolean processButtonClick(int interfaceId, int componentId,
			int slotId, int packetId) {
		if (interfaceId == 182 && (componentId == 6 || componentId == 13)) {
				player.forceLogout();
			return false;
		}
		return true;
	}
	
	@Override
	public void sendInterfaces() {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 8, 3021);	
    	CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (instanceover == true) {
					this.cancel();
					return;
				}
				if (leader == null) {
					this.cancel();
					return;
				}
				if (leader.getControlerManager().getControler() == null) {
					exitInstance(false);
					player.setNextWorldTile(player.getOutside());
					this.cancel();
					return;
				}
				if (leader.getControlerManager().toString().toLowerCase().contains("hardmode")) {
					HardModeTrioInstance hm = (HardModeTrioInstance) leader.getControlerManager().getControler();
					if (hm.health > 0 && hm.health <= 45000)
						player.getPackets().sendIComponentText(3021, 1, Utils.formatNumber(hm.health));
					else
						player.getPackets().sendIComponentText(3021, 1, "Dead");
				}
				minutes = TimeUnit.SECONDS.toMinutes(leader.getTimer());
				seconds = leader.getTimer() % 60;
				String String = java.lang.String.format("%02d:%02d", minutes, seconds);
				player.getPackets().sendIComponentText(3021, 2, " " + String);
			}
    	}, 0, 500);				
	}			
	
	@Override
	public boolean login() {
		player.setNextWorldTile(player.getOutside());	
		exitInstance(false);
		return true;
	}
	
	
	public void loadInstance(final boolean login) {
		if (player.getHacker() == 2) {
			player.sm("Enter your pin.");
		} else {
		player.lock();
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						player.unlock();
					}

				}, 1);
					CoresManager.fastExecutor.schedule(new TimerTask() {

						@Override
						public void run() {
							try {
								player.setForceMultiArea(leader.isForceMultiArea());
								player.setNextWorldTile(leader);
							} catch (Throwable t) {
								Logger.handle(t);
							}
						}
					}, 1500);
			}
			
		});
		
		}
	}

	@Override
	public void process() {
		if (player.getLeaderName() == null) {
			if (instanceover == false) {
			player.sm("Could not locate leader.");
			exitInstance(false);
			player.setNextWorldTile(player.getOutside());	
			}
		} 
		if (leader.getTimer() <= 0 && player.getEnd() == false) {
			exitInstance(true);
		}
		if (player.getEnd() == true) {
			exitInstance(false);
			player.setNextWorldTile(player.getOutside());				
			player.setInstanceEnd(false);
		}
		if (player.getInstanceKick() == true) {
			player.setInstanceKick(false);
			exitInstance(false);
			player.setNextWorldTile(player.getOutside());			
		}
		if (!player.getInterfaceManager().containsInterface(3021) && instanceover == false) {
			sendInterfaces();
		}
	}
	
	public void exitInstance(boolean isTimeEnd) {
		player.setLeaderName(null);
		instanceover = true;
		player.setTimer(0);
		minutes = 0;
		seconds = 0;
		player.setForceMultiArea(false);
		player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 11 : 8);
		removeControler();
		if (isTimeEnd) {
			player.sm("You have been removed from the instance.");
			player.setNextWorldTile(player.getOutside());
			return;
		}
		/*
		if (isTimeEnd == true) {
			player.setDestroyTimer(40);
			player.sm("You will be removed from the instance in 40 seconds.");
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (player.isOutside() == true)
						player.setisDestroytimer(true);
					if (player.isDestroytimer() == true) {
						player.sm("The timer has stopped.");
						player.setisDestroytimer(false);
						this.stop();
						return;				
					}			
					if (player.getDestroyTimer() == 20) {
						player.sm("You will be removed from the instance in 20 seconds.");
					}	
					if (player.getDestroyTimer() == 10) {
						player.sm("You will be removed from the instance in 10 seconds.");
					}
					if (player.getDestroyTimer() == 0) {
						player.sm("You have been removed from the instance.");
					player.setNextWorldTile(player.getOutside());
					this.stop();
					return;
					}
					player.setDestroyTimer(player.getDestroyTimer() - 1);	
				}
			}, 0, 1);
		}*/	
	}	

	@Override
	public void magicTeleported(int type) {
	}
	

	@Override
	public boolean logout() {
		return false;
	}
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		if (object.getId() == 66205) {
			if (object.getY() < player.getY()) {
				if (leader == null) 
					return true;
				if (leader.getControlerManager().getControler() == null)
					return true;
				if (leader.getControlerManager().getControler().toString().toLowerCase().contains("hardmode")) {
					HardModeTrioInstance hm = (HardModeTrioInstance) leader.getControlerManager().getControler();
					if (hm.health < 45000) {
						player.sm("The fight has already begun. You cannot flee.");
						return false;
					}
				}
			}
			player.getDialogueManager().startDialogue("PartnerPortalHM", object.getY());	
			return false;
		}
		if (object.getId() == 12351) {
			if (object.getY() < player.getY()) {
				if (leader == null) 
					return true;
				if (leader.getControlerManager().getControler() == null)
					return true;
				if (leader.getControlerManager().getControler().toString().toLowerCase().contains("hardmode")) {
					HardModeTrioInstance hm = (HardModeTrioInstance) leader.getControlerManager().getControler();
					if (hm.health < 45000) {
						player.sm("The fight has already begun. You cannot flee.");
						return false;
					}
				}
				player.setNextWorldTile(new WorldTile(player.getX(), player.getY()-2, player.getPlane()));
			} else {
				player.setNextWorldTile(new WorldTile(player.getX(), player.getY()+2, player.getPlane()));
			}
			return false;
		}
		return true;
	}	

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To exit the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To exit the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To exit the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

}
