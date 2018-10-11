package com.rs.game.player.controlers;


import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.instances.NoRespawn;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;

public class CyndrithChapterIISkeletonBurial extends Controler {


	private int[] boundChuncks;
	
	public static void enterTrioInstance(Player player) {
		player.getControlerManager().startControler("CyndrithChapterIISkeletonBurial", 1);
	}

	@Override
	public void start() {
		player.getEliteChapterII().setBuried(new String[10]); 
		for (int i = 0; i < player.getEliteChapterII().getBuried().length; i++) 
			player.getEliteChapterII().setBuried(i, "");
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(400, 465, boundChuncks[0], boundChuncks[1], 8);	
		try {
		loadInstance();
		} catch (Throwable t) {
			Logger.handle(t);
		}
	}
	
	public void loadInstance() {
		if (player.getHacker() == 2) {
			player.sm("Enter your pin.");
		} else {
		player.getPackets().sendGlobalConfig(184, 300);
		WorldTasksManager.schedule(new WorldTask() {
			private int count = 0;
			NPC npc = World.getNPCNear(15972, player);
			@Override
			public void run() {	
				count++;
				if (count == 1) {
					player.lock();
					npc.setNextForceTalk(new ForceTalk("Take this tablet, teleport there with it."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, "Take this tablet, teleport there with it.");
					npc.setNextAnimation(new Animation(832));
				}
				if (count == 4) {	
					npc.setNextForceTalk(new ForceTalk("Thank you for doing this, " + player.getDisplayName()));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.SAD, "Thank you for doing this, " + player.getDisplayName());
				}
				if (count == 7) {
					player.setNextAnimation(new Animation(9597));
					player.setNextGraphics(new Graphics(1680));
				}
				if (count == 9) {
					Dialogue.closeNoContinueDialogue(player);
					player.getDialogueManager().startDialogue("SimpleMessage", "Go through Cyndrith's village and bury all of the remains you see using your spade.");
					player.sm("Go through Cyndrith's village and bury all of the remains you see using your spade.");
					player.getPackets().sendGlobalConfig(184, 0);
					player.unlock();
					player.setNextWorldTile(getWorldTile(35, 21));	
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
//		RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
//		player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
//		removeControler();
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
	public boolean processItemClick1(Item object) {
		if (object.getId() == 952) {
			player.resetWalkSteps();
			player.setNextAnimation(new Animation(830));
			player.lock();
			if (checkForCorpse(player.getLastWorldTile())) {
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
					
						if (World.getNPCNear(15975, player) != null) {
							player.sm("Kill the zombies before continuing!");
							stop();
							player.unlock();
							return;
						} 
						if (count == 0) 
						player.sm("You start digging a grave...");
						else if (count < 4) {
							player.setNextAnimation(new Animation(830));
						} else { 
							player.sm("You've buried the corpse.");
							player.getEliteChapterII().setBuriedAmount(player.getEliteChapterII().getBuriedAmount()+1);
							player.sm("Corpse buried: "+player.getEliteChapterII().getBuriedAmount()+"/"+"10");
							World.spawnObject(new WorldObject(0, 10, 1, player.getLastWorldTile().getX(), player.getLastWorldTile().getY(),  0), true);
							if (player.getEliteChapterII().getBuriedAmount() == 10) {
								player.sm("You buried the corposes. You should return to Cyndrith and see how"
										+ " he's doing with the enchantment.");
								player.getDialogueManager().startDialogue("SimpleMessage", "You buried the corposes. You should return to Cyndrith and see how"
										+ " he's doing with the enchantment.");
								player.unlock();
								stop();
								removeControler();
								processMagicTeleport(null);
								player.getEliteChapterII().setQuestStage(4);
								return;
							}
							if (player.getEliteChapterII().getBuriedAmount() >= 5) {
								NPC n = new NoRespawn(15975, player.getLastWorldTile(), player);
								n.setForceAgressive(true);
								n.setTarget(player);
								player.sm("A wild zombie appears!");
								player.unlock();
								this.stop();
								return;
							}
							this.stop();
							player.unlock();
						}
						count++;
					
				}		
			}, 0, 1);
		
		} else {
			player.sm("You find nothing.");
			player.unlock();
		}
			return false;
		}
		return true;
	}			
	
	private boolean checkForCorpse(WorldTile tile) {
		int x = tile.getX();
		int y = tile.getY();
		while (x > 127)
			x -= 64;
		while (y > 127) 
			y -= 64;
		String coord = x+""+y;
		switch (coord) {
		case "8492":
		case "8690":
		case "8890":
		case "9291":
		case "9489":
		case "9589":
		case "10289":
		case "10279":
		case "10080":
		case "10378":
		case "8372":
		case "7291":
		case "7190":
		case "8572":
		case "7681":
			for (int i = 0; i < player.getEliteChapterII().getBuried().length; i++)  {
				if (player.getEliteChapterII().getBuried()[i].equals(coord))
					return false;
			}
			player.getEliteChapterII().setBuried(player.getEliteChapterII().getBuriedAmount(), coord);
			return true;
		}
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
