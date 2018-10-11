package com.rs.game.player.controlers;


import com.rs.Settings;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.instances.EliteChapterTwoDragith;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class GypsyScene extends Controler {


	private int[] boundChuncks;

	public static void enterTrioInstance(Player player) {
		player.getControlerManager().startControler("GypsyScene", 1);
	}

	@Override
	public void start() {
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(400, 427, boundChuncks[0], boundChuncks[1], 8);	
		World.spawnObject(new WorldObject(0, 10, 1, getWorldTileX(1), getWorldTileY(7),  0), true);
		try {
		loadInstance();
		} catch (Throwable t) {
			Logger.handle(t);
		}
	}
	
	public void startWave() {
		try {
		new EliteChapterTwoDragith(15235, getSpawnTile(0), player);
		new NPC(9361, getSpawnTile(1), -1, true);
		} catch (Throwable t) {
			Logger.handle(t);
		}
	}
	
	
	public void loadInstance() {
		if (player.getHacker() == 2) {
			player.sm("Enter your pin.");
		} else {
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {
			private int count = 0;
			private int oldcount = 0;
			@Override
			public void run() {	
				count++;
				NPC npc;
				NPC dragith = null;
				if (count == 2) {	
					startWave();
					npc = World.getNPCNear(9361, player);
					dragith = World.getNPCNear(15235, player);
					dragith.setLocked(true);
					npc.setHitpoints(2250000);
					npc.setNextForceTalk(new ForceTalk("Dragith Nurn find me!"));
					Dialogue.sendNPCDialogueNoContinue(player, 9361, Dialogue.AFRAID, "Dragith Nurn find me! ");
				}
				if (count == 4) {
					npc = World.getNPCNear(9361, player);
					npc.setNextForceTalk(new ForceTalk("Over it is for me, I believe..."));
					Dialogue.sendNPCDialogueNoContinue(player, 9361, Dialogue.AFRAID, "Over it is for me, I believe...");
				}
				if (count == 6) {
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.QUESTION, "What?");
					player.setNextForceTalk(new ForceTalk("What?"));
				}
				if (count == 8) {
					player.faceEast();
				}
				if (count == 10) {
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.AFRAID, "Oh my...");
					player.setNextForceTalk(new ForceTalk("Oh my..."));
					player.faceEast();
				}
				if (count == 14) {
					Dialogue.closeNoContinueDialogue(player);
					player.unlock();
					dragith = World.getNPCNear(15235, player);
					dragith.setLocked(false);
				}
				if (player.getControlerManager().getControler() != null) {
				if (player.getControlerManager().getControler().toString().toLowerCase().contains("gypsy")) {
				if (count > 14) {
					if (count == 15 ||
							count - oldcount > 2) {
					oldcount = count;
					npc = World.getNPCNear(9361, player);
					dragith = World.getNPCNear(15235, player);
					if (dragith == null) {
						player.getEliteChapterII().setQuestStage(2);
						RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
						player.setNextWorldTile(new WorldTile(3211, 3424 ,0));
						player.sm("You were able to kill dragith before he managed to kill Gypsy Aris!");
						removeControler();
						stop();
						return;
					}
					float percent = (float) npc.getHitpoints()/2250000 * 100;
					if ((int) percent <= 0) {
						player.sm("<col=FF2424><shad=000000>Aris died before you could kill Dragith!");
						processObjectClick1(null);
						stop();
						return;
					}
					String s = ""+percent;
					s = s.substring(0, 5);
					player.sm("Gypsy Aris' health: " + s + "% remaining");
					npc.applyHit(new Hit(npc, 20000, HitLook.MAGIC_DAMAGE));
					npc.setNextGraphics(new Graphics(1028));
					int rand = Utils.getRandom(3);
					if (rand == 2) {
						npc.setNextForceTalk(new ForceTalk("Ahhh!"));
						npc.applyHit(new Hit(npc, 23000, HitLook.MAGIC_DAMAGE));
					} else if (rand == 1) {
						npc.setNextForceTalk(new ForceTalk("Help!"));
						npc.applyHit(new Hit(npc, 17000, HitLook.MAGIC_DAMAGE));
					}
					}
				}
				}
				}
				if (count == 1) {
				try {
				player.setNextWorldTile(getWorldTile(5, 8));			
				} catch (Throwable t) {
					Logger.handle(t);
				}
				}
			}
		}, 0, 1);
		}
	}

	public WorldTile getSpawnTile(int num) {
		switch (num) {
		case 0:
			return getWorldTile(9, 8);
		case 1:
			return getWorldTile(3, 8);
		case 2:
			return getWorldTile(22, 18);
		default:
			return getWorldTile(32, 30);
		}
	}
	
	@Override
	public void process() {

	}
	
	@Override
	public boolean login() {
		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		removeControler();
		return false;
	}	
	
	@Override
	public boolean logout() {
		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		return false;
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
	public boolean processObjectClick1(WorldObject object) {
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		removeControler();
		return true;
	}	

	@Override
	public boolean sendDeath() {
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		removeControler();
		super.sendDeath();
		return true;
	}
	
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		removeControler();
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		removeControler();
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
		removeControler();
		return false;
	}	
	
}
