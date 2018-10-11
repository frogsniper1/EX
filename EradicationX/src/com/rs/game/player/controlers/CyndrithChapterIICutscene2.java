package com.rs.game.player.controlers;


import com.rs.Settings;
import com.rs.game.ForceTalk;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
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

public class CyndrithChapterIICutscene2 extends Controler {


	private int[] boundChuncks;

	public static void enterTrioInstance(Player player) {
		player.getControlerManager().startControler("CyndrithChapterIICutscene2", 1);
	}

	@Override
	public void start() {
		boundChuncks = RegionBuilder.findEmptyChunkBound(4, 4);
		RegionBuilder.copyAllPlanesMap(400, 436, boundChuncks[0], boundChuncks[1], 4);	
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
		FadingScreen.fade(player, new Runnable() {

			@Override
			public void run() {
			}

		});
		NPC npc = new NPC(15972, getWorldTile(74-64, 2), -1 , false);
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {
			private int count = 0;
			@Override
			public void run() {	
				count++;
				if (count == 2) {
					player.lock();
					player.setNextWorldTile(getWorldTile(74-64, 2));
					player.setRun(false);
				}
				if (count == 3)
					npc.addWalkSteps(getWorldTileX(74-64), getWorldTileY(8), -1, false);
				if (count == 4)
					player.addWalkSteps(getWorldTileX(74-64), getWorldTileY(7), -1, false);
				if (count == 7) {
					player.setNextForceTalk(new ForceTalk("What are we doing here?"));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.QUESTION, "What are we doing here?");
				}
				if (count == 10) {
					npc.faceSouth();
					npc.setNextForceTalk(new ForceTalk("This library has a lot of information that can prove to be valuable."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, "This library has a lot of information that can prove to be valuable.");
				}
				if (count == 11)
					npc.setNextForceTalk(new ForceTalk("This library has a lot of information that can prove to be valuable."));
				if (count == 15) {
					Dialogue.closeNoContinueDialogue(player);
					npc.faceEast();
				}
				if (count == 16) 
					npc.setNextAnimation(new Animation(832));
				if (count == 17) {
					npc.faceSouth();
					npc.setNextForceTalk(new ForceTalk("While you were gone, I came here and found a book on zombies."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, 
							"While you were gone, I came here and found a book on zombies.");
				}
				if (count == 18)
					npc.setNextAnimation(new Animation(832));
				if (count == 19)
					player.setNextAnimation(new Animation(1350));
				if (count == 22) {
					player.setNextAnimation(new Animation(3141));
					npc.setNextForceTalk(new ForceTalk("Generations before ours, zombies attacked all of Gielenor, just as it is now."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.SAD, 
							"Generations before ours, zombies attacked all of Gielenor, just as it is now.");
				}
				if (count == 23)
					npc.setNextForceTalk(new ForceTalk("Generations before ours, zombies attacked all of Gielenor, just as it is now."));
				if (count == 26) {
					npc.setNextForceTalk(new ForceTalk("Dragith's father was the one behind that invasion. His name was Denamket."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, 
							"Dragith's father was the one behind that invasion. His name was Denamket.");			
				}
				if (count == 27) {
					npc.setNextForceTalk(new ForceTalk("Dragith's father was the one behind that invasion. His name was Denamket."));
				}
				if (count == 31) {
					npc.setNextForceTalk(new ForceTalk("His capabilities were far above Dragith's."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, 
							"His capabilities were far above Dragith's.");		
				}
				if (count == 34) {
					npc.setNextForceTalk(new ForceTalk("And yet, he was defeated."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, 
							"And yet, he was defeated.");		
				}
				if (count == 37) {
					player.setNextForceTalk(new ForceTalk("How?"));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.QUESTION, "How?");
				}
				if (count == 40) {
					npc.setNextForceTalk(new ForceTalk("There was a wizard who made a staff powerful enough to kill it."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, 
							"There was a wizard who made a staff powerful enough to kill it.");	
				}
				if (count == 41)
					npc.setNextForceTalk(new ForceTalk("There was a wizard who made a staff powerful enough to kill it."));
				if (count == 44) {
					player.setNextForceTalk(new ForceTalk("So, that same staff could help us kill Dragith."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.QUESTION, "So, that same staff could help us kill Dragith.");
				}
				if (count == 45)
					player.setNextForceTalk(new ForceTalk("So, that same staff could help us kill Dragith."));
				if (count == 48) {
					player.setNextAnimation(new Animation(3141));
					npc.setNextForceTalk(new ForceTalk("Yes, but this book doesn't have the page that had the information of the staff. It's ripped out."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.REGULAR, 
							"Yes, but this book doesn't have the page that had the information of the staff. It's ripped out.");	
				}
				if (count == 49) 
					npc.setNextForceTalk(new ForceTalk("Yes, but this book doesn't have the page that had the information of the staff. It's ripped out."));
				if (count == 50) 
					npc.setNextForceTalk(new ForceTalk("Yes, but this book doesn't have the page that had the information of the staff. It's ripped out."));
				if (count == 53) {
					player.setNextForceTalk(new ForceTalk("I think I know who took it out of there."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.ANNOYED, "I think I know who took it out of there.");
				}
				if (count == 56) {
					player.setNextAnimation(new Animation(-1));
					npc.setNextForceTalk(new ForceTalk("Dragith."));
					player.setNextForceTalk(new ForceTalk("Dragith."));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.ANGRY, "Dragith.");	
				}
				if (count == 59) {
					FadingScreen.fade(player, new Runnable() {
						@Override
						public void run() {
						}
					});
				}
				if (count == 61) {
					player.getEliteChapterII().setQuestStage(5);
					player.setNextWorldTile(new WorldTile(3973, 4812,1));
					player.unlock();
					RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 4, 4);
					removeControler();
					Dialogue.closeNoContinueDialogue(player);
					stop();
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
	public boolean processNPCClick1(NPC npc) {
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
