package com.rs.game.minigames;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.others.LegendsNPCs;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class LegendsMinigame extends Controler {

	
	public static final WorldTile OUTSIDE = new WorldTile(3120,3120,0);

	private final int[][] WAVES = {//here are the waves u can add edit monsters in each wave and the waves number
			 {795}//wave 1{npc1,npc2,npc3,etc..}
			,{7134}//wave 2
			,{13460}//etc..
			,{6358}
			,{13646,14609}
			,{14514}
			,{3200}
			,{6260}
			,{5247}
			,{14836}
			,{3200}
			,{6203}
			,{50}
			,{14466}
			,{2745}
			,{10141,9176}
			,{12878}
			,{8528}
			,{6247}
			,{8351}
			,{6203}
			,{11872}
			,{7134,13460,14514,14836}
			,{2745,2745}
			,{8528,9176,14546,14516}
			,{8596,3200,14466}
			,{14514,8133,15225}
			,{11872,14546,9176,5247,795,14609,3200}
	};
	
	

	private int[] boundChuncks;
	private Stages stage;
	private boolean logoutAtEnd;
	private boolean login;
	private int aliveNPCSCount;
	public boolean spawned;

	public static void enterLegendsMinigame(Player player) {
		if(!hasRequirments(player)) 
			return;
		player.getControlerManager().startControler("LegendsMinigame", 1); //start at wave 1
	}
	
	public static boolean hasRequirments(Player player) {
		return true;
	}
	
	private static enum Stages {
		LOADING,
		RUNNING,
		DESTROYING
	}

	@Override
	public void start() {
		loadCave(false);
	}

	@Override
	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
		if(stage != Stages.RUNNING)
			return false;
		if(interfaceId == 182 && (componentId == 6 || componentId == 13)) {
			if(!logoutAtEnd) {
				logoutAtEnd = true;
				player.getPackets().sendGameMessage("<col=ff0000>You will be logged out automatically at the end of this wave.");
				player.getPackets().sendGameMessage("<col=ff0000>If you log out sooner, you will have to repeat this wave.");
			}else
				player.forceLogout();
			return false;
		}
		return true;
	}

	/**
	 * return process normaly
	 */
	@Override
	public boolean processObjectClick1(WorldObject object) {
		if(object.getId() == 45803) {
			if(stage != Stages.RUNNING)
				return false;
			exitCave(1);
			return false;
		}
		return true;
	}


	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean login() {
		loadCave(true);
		return false;
	}


	public WorldObject exitPortal;
	
	public void loadCave(final boolean login) {
		this.login = login;
		stage = Stages.LOADING;
		player.lock(); //locks player
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//finds empty map bounds
				boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8); 
				//copys real map into the empty map
				//552 640
				RegionBuilder.copyAllPlanesMap(338, 546, boundChuncks[0], boundChuncks[1], 8);
				
				exitPortal = new WorldObject(45803, 10, 0, getWorldTile(16, 5));
				
				player.setNextWorldTile(!login ? getWorldTile(16, 6) : getWorldTile(16, 6) );
				//1delay because player cant walk while teleing :p, + possible issues avoid
				WorldTasksManager.schedule(new WorldTask()  {
					@Override
					public void run() {
						if(!World.isSpawnedObject(exitPortal))
							World.spawnObject(exitPortal, true);
						player.getPackets().sendGameMessage("Goodluck for the beggest battle in your life!");
						player.setForceMultiArea(true);
						player.unlock(); //unlocks player
						stage = Stages.RUNNING;
					}

				}, 1);
				if(!login) {
					/*
					 * lets stress less the worldthread, also fastexecutor used for mini stuff
					 */
					CoresManager.fastExecutor.schedule(new TimerTask() {

						@Override
						public void run() {
							if(stage != Stages.RUNNING)
								return;
							try {
								startWave();
							} catch (Throwable t) {
								Logger.handle(t);
							}
						}
					}, 6000);
				}
			}
		});
	}

	public WorldTile getSpawnTile() {
		switch(Utils.random(5)) {
		case 0:
			return getWorldTile(25, 25);
		case 1:
			return getWorldTile(9, 25);
		case 2:
			return getWorldTile(16, 14);
		case 3:
			return getWorldTile(8, 9);
		default:
			return getWorldTile(23, 9);
		}
	}


	@Override
	public void moved() {
		if(stage != Stages.RUNNING || !login)
			return;
		login = false;
		setWaveEvent();
	}

	public void startWave() {
		int currentWave = getCurrentWave();
		if(currentWave > WAVES.length) {
			win();
			return;
		}
		aliveNPCSCount = WAVES[currentWave-1].length;
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 0, 316);
		player.getPackets().sendConfig(639, currentWave);
		if(stage != Stages.RUNNING)
			return;
		for(int id : WAVES[currentWave-1]) {
			new LegendsNPCs(id, this.getSpawnTile(), -1, true, true, this);
		}
		spawned = true;
	}

	public void removeNPC() {
		if(stage != Stages.RUNNING)
			return;
		aliveNPCSCount--;
		if(aliveNPCSCount == 0) 
			nextWave();
	}
	
	public void win() {
		if(stage != Stages.RUNNING)
			return;
		exitCave(4);
	}


	public void nextWave() {
		setCurrentWave(getCurrentWave()+1);
		if(logoutAtEnd) {
			player.forceLogout();
			return;
		}
		setWaveEvent();
	}

	public void setWaveEvent() {
		CoresManager.fastExecutor.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					if(stage != Stages.RUNNING)
						return;
					startWave();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 600);
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
					player.getPackets().sendGameMessage("You have been defeated!");
				} else if (loop == 3) {
					player.reset();
					exitCave(1);
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
	public void magicTeleported(int type) {
		exitCave(2);
	}

	/*
	 * logout or not. if didnt logout means lost, 0 logout, 1, normal,  2 tele
	 */
	public void exitCave(int type) {
		stage = Stages.DESTROYING;
		WorldTile outside = new WorldTile(Settings.RESPAWN_PLAYER_LOCATION); //radomizes alil
		World.removeObject(exitPortal, true);
		if(type == 0 || type == 2)
			player.setLocation(outside);
		else {
			player.setForceMultiArea(false);
			player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ?  11 : 0);
			if(type == 1 || type == 4) {
				player.setNextWorldTile(outside);
				if(type == 4) {
					player.reset();
					player.getPackets().sendGameMessage("You were victorious!!");
					sendRewards();
					} else if(getCurrentWave() == 1) 
					player.getPackets().sendGameMessage("You had your try, Goodluck next time!");
				else{
					int coins = Math.round((100000000 * getCurrentWave()) / 29);
					if(!player.getInventory().addItem(995, coins)) 
						World.addGroundItem(new Item(995, coins), new WorldTile(player), player, null, true, 180,true);
					player.getPackets().sendGameMessage("You recieve "+coins+" coins for making it to wave "+ getCurrentWave()+".");
				}
			}
			removeControler();
		}
		/*
		 * 1200 delay because of leaving
		 */
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
		
	}

	private void sendRewards() {//here you put what rewards u get for completing minigame
		
	}

	/*
	 * gets worldtile inside the map
	 */
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChuncks[0]*8 + mapX, boundChuncks[1]*8 + mapY, 0);
	}


	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean logout() {
		/*
		 * only can happen if dungeon is loading and system update happens
		 */
		if(stage != Stages.RUNNING)
			return false;
		exitCave(0);
		return false;

	}
	 @Override
		public boolean processMagicTeleport(WorldTile toTile) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the Legends Minigame!");
			return false;
		}
		
		@Override
		public boolean processItemTeleport(WorldTile toTile) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the Legends Minigame!");
			return false;
		}
		
		@Override
		public boolean processObjectTeleport(WorldTile toTile) {
	             player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the Legends Minigame!");
			return false;
	}
		
	public int getCurrentWave() {
		if (getArguments() == null || getArguments().length == 0) 
			return 0;
		return (Integer) getArguments()[0];
	}

	public void setCurrentWave(int wave) {
		if(getArguments() == null || getArguments().length == 0)
			this.setArguments(new Object[1]);
		getArguments()[0] = wave;
	}

	@Override
	public void forceClose() {
		/*
		 * shouldnt happen
		 */
		if(stage != Stages.RUNNING)
			return;
		exitCave(2);
	}
}
