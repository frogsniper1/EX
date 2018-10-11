package com.rs.game.player.controlers;


import com.rs.Settings;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;

public class CyndrithChapterIICutscene extends Controler {


	private int[] boundChuncks;

	public static void enterTrioInstance(Player player) {
		player.getControlerManager().startControler("CyndrithChapterIICutscene", 1);
	}

	@Override
	public void start() {
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(400, 465, boundChuncks[0], boundChuncks[1], 8);	
		try {
		loadInstance();
		} catch (Throwable t) {
			Logger.handle(t);
		}
	}
	
	public void loadInstance() {
		player.getPackets().sendGlobalConfig(184, 300);
		if (player.getHacker() == 2) {
			player.sm("Enter your pin.");
		} else {
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {
			private int count = 0;
			NPC npc = World.getNPCNear(15972, player);
			@Override
			public void run() {	
				count++;
				if (count == 1) {
					player.lock();
					npc.setNextForceTalk(new ForceTalk("This is pointless..."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.SAD, "This is pointless...");
				}
				if (count == 3) {	
					npc.setNextAnimation(new Animation(9597));
					npc.setNextGraphics(new Graphics(1680));
				}
				if (count == 5) {
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.REGULAR, "Looks like he dropped a teleport tablet on the ground in a hurry.");
					player.setNextAnimation(new Animation(827));
				}
				if (count == 8) {
					player.setNextAnimation(new Animation(9597));
					player.setNextGraphics(new Graphics(1680));
				}
				if (count == 10) {
					Dialogue.closeNoContinueDialogue(player);
					player.getPackets().sendGlobalConfig(184, 0);
					player.setNextWorldTile(getWorldTile(35, 21));	
					npc = new NPC(15972, getWorldTile(29,22), -1, true);
				}
				if (count == 12) {
					player.setNextForceTalk(new ForceTalk("Where am I?"));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.QUESTION, "Where am I?");
					player.faceNorth();
				}
				if (count == 14) {
					player.setNextForceTalk(new ForceTalk("I've never seen this place before."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.QUESTION, "I've never seen this place before.");
					player.faceSouth();
				}
				if (count == 16) {
					player.faceWest();
					Dialogue.closeNoContinueDialogue(player);
				}
				if (count == 18) {
					player.setNextForceTalk(new ForceTalk("There's Cyndrith."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.REGULAR, "There's Cyndrith.");
				}
				if (count == 20) {
					player.setRun(false);
					player.addWalkSteps(getWorldTileX(97-64), getWorldTileY(84-64), -1, false);
					Dialogue.closeNoContinueDialogue(player);
				}
				if (count == 22) {
					npc.addWalkSteps(getWorldTileX(92-64), getWorldTileY(87-64), -1, false);
				}
				if (count == 23) {
					npc.addWalkSteps(getWorldTileX(90-64), getWorldTileY(87-64), -1, false);
				}
				if (count == 25) {
					npc.addWalkSteps(getWorldTileX(90-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 25) {
					player.addWalkSteps(getWorldTileX(97-64), getWorldTileY(85-64), -1, false);
				}
				if (count == 26) {
					player.addWalkSteps(getWorldTileX(93-64), getWorldTileY(83-64), -1, false);
				}
				if (count == 27) {
					player.addWalkSteps(getWorldTileX(92-64), getWorldTileY(83-64), -1, false);
				}
				if (count == 29) {
					player.faceSouth();
				}
				if (count == 30) {
					npc.faceWest();
					npc.setNextAnimation(new Animation(860));
				}
				if (count == 33) {
					npc.addWalkSteps(getWorldTileX(90-64), getWorldTileY(90-64), -1, false);
				}
				if (count == 35) {
					player.addWalkSteps(getWorldTileX(92-64), getWorldTileY(82-64), -1, false);
				}
				if (count == 36) {
					player.addWalkSteps(getWorldTileX(90-64), getWorldTileY(82-64), -1, false);
				}
				if (count == 37) {
					player.addWalkSteps(getWorldTileX(91-64), getWorldTileY(87-64), -1, false);
				}
				if (count == 40) {
					player.getPackets().sendGlobalConfig(184, 300);
					player.faceNorth();
				}
				if (count == 41) {
					npc.faceEast();
					npc.setNextForceTalk(new ForceTalk("I didn't even bury them."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.SAD, "I didn't even bury them.");
				}
				if (count == 42)
					npc.faceWest();
				if (count == 44) {
					npc.setNextForceTalk(new ForceTalk("I just ran."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.SAD, "I just ran.");
				}
				if (count == 48) {
					player.addWalkSteps(getWorldTileX(90-64), getWorldTileY(87-64), -1, false);
				}
				if (count == 49)
					player.addWalkSteps(getWorldTileX(90-64), getWorldTileY(89-64), -1, false);
				if (count == 50) {
					player.setNextForceTalk(new ForceTalk("Cyndrith..."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.SAD, "Cyndrith...");
					player.faceNorth();
					npc.faceSouth();
				}
				if (count == 53) {
					npc.setNextForceTalk(new ForceTalk("How the hell did you get here?"));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.ANGRY, "How the hell did you get here?");
				}
				if (count == 56) {
					npc.setNextForceTalk(new ForceTalk("Why don't you just mind your own damn business?"));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.ANGRY, "Why don't you just mind your own damn business?");
				}
				if (count == 57) {
					npc.setNextAnimation(new Animation(422));
					player.setNextAnimation(new Animation(424));
				}
				if (count == 58) {
					Dialogue.closeNoContinueDialogue(player);
				}
				if (count == 59) {
					npc.setNextAnimation(new Animation(423));
					player.setNextAnimation(new Animation(424));
					player.setNextForceTalk(new ForceTalk("Argh!"));
				}
				if (count == 60) {
					npc.setNextAnimation(new Animation(9597));
					npc.setNextGraphics(new Graphics(1680));
					npc.finish();
				}
				if (count == 61) {
					player.setNextAnimation(new Animation(3094));
				}
				if (count == 62) {
					FadingScreen.fade(player, new Runnable() {

						@Override
						public void run() {
						}

					});
				}
				if (count == 64) {
					player.setNextForceTalk(new ForceTalk("I'm guessing he went back home."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.REGULAR, "I'm guessing he went back home.");
				}
				if (count == 66) {
					Dialogue.closeNoContinueDialogue(player);
					player.setNextAnimation(new Animation(9597));
					player.setNextGraphics(new Graphics(1680));
					player.getPackets().sendGlobalConfig(184, 0);
				}
				if (count == 68) {
					player.unlock();
					stop();
					removeControler();
					processMagicTeleport(null);
					player.getEliteChapterII().setQuestStage(3);
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
	public boolean processNPCClick1(NPC npc) {
		return false;
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
