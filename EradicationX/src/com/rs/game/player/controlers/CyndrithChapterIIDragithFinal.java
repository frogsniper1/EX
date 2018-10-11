package com.rs.game.player.controlers;


import com.rs.Settings;
import com.rs.game.Animation;
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
import com.rs.game.npc.instances.NoRespawn;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.controlers.Controler;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class CyndrithChapterIIDragithFinal extends Controler {


	private int[] boundChuncks;

	public static void enterTrioInstance(Player player) {
		player.getControlerManager().startControler("CyndrithChapterIIDragithFinal", 1);
	}

	@Override
	public void start() {
		boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8);
		RegionBuilder.copyAllPlanesMap(493, 600, boundChuncks[0], boundChuncks[1], 8);	
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
		FadingScreen.fade(player, new Runnable() {

			@Override
			public void run() {
			}

		});
		NPC npc = new NPC(15972, getWorldTile(100-64, 91-64), -1 , false);
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {
			private int count = 0;
			NPC dragith = null;
			@Override
			public void run() {	
				count++;
				if (player.getControlerManager().getControler() != null) {
				if (player.getControlerManager().getControler().toString().toLowerCase().contains("final")) {
				if (count == 2) {
					player.setNextWorldTile(getWorldTile(100-64, 91-64));	
				}
				if (count == 4)
					npc.addWalkSteps(getWorldTileX(99-64), getWorldTileY(91-64), -1, false);
				if (count == 5) {
					npc.faceEast();
					player.faceWest();
					npc.setNextForceTalk(new ForceTalk("Where is she?"));
					Dialogue.sendNPCDialogueNoContinue(player, "Cyndrith", 15840, Dialogue.ANGRY, "Where is she?");
				}
				if (count == 8) {
					player.setNextForceTalk(new ForceTalk("I'm not sure. She's usually in the center."));
					Dialogue.sendPlayerDialogueNoContinue(player, Dialogue.REGULAR, "I'm not sure. She's usually in the center.");
				}
				if (count == 9)
					player.setNextForceTalk(new ForceTalk("I'm not sure. She's usually in the center."));
				if (count == 11)
					Dialogue.closeNoContinueDialogue(player);
				if (count == 12) {
					npc.setRun(true);
					player.setRun(true);
					npc.addWalkSteps(getWorldTileX(100-64), getWorldTileY(89-64), -1, false);
				}
				if (count == 13)
					npc.addWalkSteps(getWorldTileX(100-64), getWorldTileY(84-64), -1, false);
				if (count == 13) {
					player.addWalkSteps(getWorldTileX(98-64), getWorldTileY(89 - 64), -1, false);
					player.setNextForceTalk(new ForceTalk("Don't just run in!"));
				}
				if (count == 14)
					npc.addWalkSteps(getWorldTileX(88-64), getWorldTileY(84-64), -1, false);
				if (count == 15)
					player.setNextForceTalk(new ForceTalk("Orrr run in."));
				if (count == 16)
					player.addWalkSteps(getWorldTileX(88-64), getWorldTileY(89-64), -1, false);
				if (count == 18)
					npc.faceSouth();
				if (count == 19) {
					npc.faceNorth();
					player.faceSouth();
					npc.setNextForceTalk(new ForceTalk("She's not here "+player.getDisplayName()+ "!"));
					player.sm("Cyndrith: She's not here "+player.getDisplayName().toLowerCase()+ "!");
				}
				if (count == 21) 
					player.setNextForceTalk(new ForceTalk("I don't know!"));
				if (count == 22)
					npc.addWalkSteps(getWorldTileX(78-64), getWorldTileY(84-64), -1, false);
				if (count == 23)
					player.addWalkSteps(getWorldTileX(76-64), getWorldTileY(89-64), -1, false);
				if (count == 26)
					player.addWalkSteps(getWorldTileX(76-64), getWorldTileY(84-64), -1, false);
				if (count == 27)
					player.addWalkSteps(getWorldTileX(77-64), getWorldTileY(84-64), -1, false);
				if (count == 28) {
					npc.setNextForceTalk(new ForceTalk("We need to find her."));
					player.sm("Cyndrith: We need to find her.");	
				}
				if (count == 30) {
					player.setNextForceTalk(new ForceTalk("I know that."));
				}
				if (count == 33) {
					dragith = new NoRespawn(15976, getWorldTile(88-64, 81-64), player);
					dragith.setForceAgressive(false);
					dragith.setRandomWalk(false);
					dragith.setNextGraphics(new Graphics(2603));
					dragith.setLocked(true);
					player.setNextForceTalk(new ForceTalk("She's here! Hide!"));
				}
				if (count == 36) {
					player.addWalkSteps(getWorldTileX(76-64), getWorldTileY(84-64), -1, false);
					npc.addWalkSteps(getWorldTileX(76-64), getWorldTileY(84-64), -1, false);
					dragith.addWalkSteps(getWorldTileX(88-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 37) {
					player.addWalkSteps(getWorldTileX(76-64), getWorldTileY(80-64), -1, false);
					npc.addWalkSteps(getWorldTileX(76-64), getWorldTileY(81-64), -1, false);
				}
				if (count == 38) {
					player.faceNorth();
					npc.faceNorth();
					dragith.addWalkSteps(getWorldTileX(84-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 40) {
					dragith.setNextGraphics(new Graphics(1009));
					dragith.faceSouth();
				}
				if (count == 39)
					npc.setNextAnimation(new Animation(9025));
				if (count == 40)
					player.setNextAnimation(new Animation(9025));
				if (count == 41) {
					for (int i = 75; i < 87; i++) {
						if (i != 78 && i != 84)
							spawnZombie(84, i);
					}
				}
				if (count == 42) {
					dragith.addWalkSteps(getWorldTileX(82-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 43) {
					dragith.setNextGraphics(new Graphics(1009));
					dragith.faceSouth();
				}
				if (count == 44) {
					for (int i = 75; i < 87; i++) {
						if (i != 78 && i != 84)
							spawnZombie(82, i);
					}
				}
				if (count == 45) {
					dragith.addWalkSteps(getWorldTileX(80-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 46) {
					dragith.setNextGraphics(new Graphics(1009));
					dragith.faceSouth();
				}
				if (count == 47) {
					for (int i = 75; i < 87; i++) {
						if (i != 78 && i != 84)
							spawnZombie(80, i);
					}
				}
				if (count == 48) {
					dragith.addWalkSteps(getWorldTileX(78-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 49) {
					dragith.setNextGraphics(new Graphics(1009));
					dragith.faceSouth();
				}
				if (count == 50) {
					for (int i = 75; i < 87; i++) {
						if (i != 78 && i != 84)
							spawnZombie(78, i);
					}
				}
				if (count == 52)
					dragith.addWalkSteps(getWorldTileX(76-64), getWorldTileY(84-64), -1, false);
				if (count == 55)
					dragith.addWalkSteps(getWorldTileX(88-64), getWorldTileY(84-64), -1, false);
				if (count == 58) {
					npc.setNextForceTalk(new ForceTalk("I'll get her while her guard's down."));
					player.sm("Cyndrith: I'll get her while her guard's down.");
				}
				if (count == 59)
					npc.setNextAnimation(new Animation(-1));
				if (count == 60) {
					npc.setRun(false);
					npc.addWalkSteps(getWorldTileX(76-64), getWorldTileY(84-64), -1, false);
				}
				if (count == 61)
					npc.addWalkSteps(getWorldTileX(85-64), getWorldTileY(84-64), -1, false);
				if (count == 65) {
					dragith.setNextForceTalk(new ForceTalk("I know you're there."));
					player.sm("Dragith: I know you're there.");
				}
				if (count == 66) 
					dragith.faceWest();
				if (count == 67) {
					dragith.setNextGraphics(new Graphics(122));
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					player.setNextAnimation(new Animation(-1));
				}
				if (count == 68) {
					player.setNextWorldTile(getWorldTile(86-64, 83-64));	
					npc.setNextWorldTile(getWorldTile(86-64, 85-64));
				} 
				if (count == 69) {
					npc.faceEast();
					player.faceEast();
					npc.setNextAnimation(new Animation(1128));
				}
				if (count == 70)  {
					player.setNextAnimation(new Animation(1128));
					player.setNextForceTalk(new ForceTalk("I can't... move."));
				}
				if (count == 71) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
				}
				if (count == 72) {
					dragith.setNextForceTalk(new ForceTalk("Well,"));
					player.sm("Dragith: Well,");
				}
				if (count == 73) {
					dragith.setNextForceTalk(new ForceTalk("What are two little imbeciles doing here, all alone, without any backup?"));
					player.sm("Dragith: What are two little imbeciles doing here, all alone, without any backup?");
				}
				if (count == 74)
					dragith.setNextForceTalk(new ForceTalk("What are two little imbeciles doing here, all alone, without any backup?"));
				if (count == 75) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
				}
				if (count == 77) {
					dragith.setNextForceTalk(new ForceTalk("Look behind you. Do you really think just you two can take all of us at once?"));
					player.sm("Dragith: Look behind you. Do you really think just you two can take all of us at once?");
				}
				if (count == 78)
					dragith.setNextForceTalk(new ForceTalk("Look behind you. Do you really think just you two can take all of us at once?"));
				if (count == 79) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
				}
				if (count == 82) {
					dragith.setNextForceTalk(new ForceTalk("You can't kill me. You can keep trying and you'll get nowhere."));
					player.sm("Dragith: You can't kill me. You can keep trying and you'll get nowhere.");
				}
				if (count == 83)
					dragith.setNextForceTalk(new ForceTalk("You can't kill me. You can keep trying and you'll get nowhere."));
				if (count == 84) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
				}
				if (count == 85) {
					npc.setNextForceTalk(new ForceTalk("Trust me, we'll kill you. We know how."));
					player.sm("Cyndrith: Trust me, we'll kill you. We know how.");
				}
				if (count == 86)
					npc.setNextForceTalk(new ForceTalk("Trust me, we'll kill you. We know how."));
				if (count == 89) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("Actually, you don't."));
					player.sm("Dragith: Actually, you don't.");
				}
				if (count == 92) {
					dragith.setNextForceTalk(new ForceTalk("You still need the 'missing page' from your book."));
					player.sm("Dragith: You still need the 'missing page' from your book.");
				}
				if (count == 94){
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
				}
				if (count == 95) {
					player.setNextForceTalk(new ForceTalk("How did you know that?"));
				}
				if (count == 97) {
					dragith.setNextForceTalk(new ForceTalk("There isn't anything that I don't know of."));
					player.sm("Dragith: There isn't anything that I don't know of.");
				}
				if (count == 98) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("There isn't anything that I don't know of."));
				}
				if (count == 102) {
					dragith.setNextForceTalk(new ForceTalk("Let's make this interesting."));
					player.sm("Dragith: Let's make this interesting.");
				}
				if (count == 105) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("If you beat me here, I'll give you the key to my crypt."));
					player.sm("Dragith: If you beat me here, I'll give you the key to my crypt.");
				}
				if (count == 106)
					dragith.setNextForceTalk(new ForceTalk("If you beat me here, I'll give you the key to my crypt."));
				if (count == 110) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("The crypt has the last page to the book. You do what you want with it."));
					player.sm("Dragith: The crypt has the last page to the book. You do what you want with it.");
				}
				if (count == 111)
					dragith.setNextForceTalk(new ForceTalk("The crypt has the last page to the book. You do what you want with it."));
				if (count == 114) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("How does that sound?"));
					player.sm("Dragith: How does that sound?");
				}
				if (count == 117) {
					npc.setNextForceTalk(new ForceTalk("Easy."));
					player.sm("Cyndrith: Easy.");
				}
				if (count == 118) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
				}
				if (count == 120) {
					dragith.setNextForceTalk(new ForceTalk("Huh, don't I know you?"));
					player.sm("Dragith: Huh, don't I know you?");
				} 
				if (count == 123) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("Right. You're the guy who ran away from your home while everyone else"));
					player.sm("Dragith: Right. You're the guy who ran away from your home while everyone else");
				}
				if (count == 124)
					dragith.setNextForceTalk(new ForceTalk("Right. You're the guy who ran away from your home while everyone else"));
				if (count == 127) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("Died trying to defend it!"));
					player.sm("Dragith: Died trying to defend it!");
				}
				if (count == 130) {
					npc.setNextGraphics(new Graphics(91));
					player.setNextGraphics(new Graphics(91));
					dragith.setNextForceTalk(new ForceTalk("This should be easy."));
					player.sm("Dragith: This should be easy.");
				}
				if (count == 132) {
					dragith.setNextForceTalk(new ForceTalk("You're a joke. Coward."));
					player.sm("Dragith: You're a joke. Coward.");
				}
				if (count == 135) {
                	npc.setNextAnimation(new Animation(16382));
                	npc.setNextGraphics(new Graphics(3014));
				}
				if (count == 136) {
					player.setNextForceTalk(new ForceTalk("What are you doing?"));
					player.faceNorth();
				}
				if (count == 137) {
					npc.setNextWorldTile(getWorldTile(83-64, 81-64));	
					player.faceWest();
				}
				if (count == 138) {
					dragith.setNextForceTalk(new ForceTalk("What!?"));
					player.sm("Dragith: What!?");
				}
				if (count == 141) {
					for (NPC n : World.getNPCs()) {
						if (n.withinDistance(player, 100)) {
						if (n.getId() == 8149 || n.getId() == 8150 || n.getId() == 8151 || n.getId() == 8152 || n.getId() == 8153) {
							n.setNextGraphics(new Graphics(2442));
							int rand = Utils.getRandom(2);
							if (rand == 2)
								n.setNextForceTalk(new ForceTalk("Raaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!!!"));
							else if (rand == 1)
								n.setNextForceTalk(new ForceTalk("ROARGHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH!!!"));
							else
								n.setNextForceTalk(new ForceTalk("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!"));
						}
						}
					}
				}
				if (count == 143) {
					for (NPC n : World.getNPCs()) {
						if (n.withinDistance(player, 100)) {
						if (n.getId() == 8149 || n.getId() == 8150 || n.getId() == 8151 || n.getId() == 8152 || n.getId() == 8153) {
							int rand = Utils.getRandom(2);
							if (rand == 2)
								n.setNextForceTalk(new ForceTalk("Raaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!!!"));
							else if (rand == 1)
								n.setNextForceTalk(new ForceTalk("ROARGHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH!!!"));
							else
								n.setNextForceTalk(new ForceTalk("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!"));
							n.sendDeathNoDrop(null);
						}
						}
					}
				}
				if (count == 145) {
					player.setNextForceTalk(new ForceTalk("Holy Shit!"));
				}
				if (count == 146) {
					player.faceEast();
					npc.setNextWorldTile(getWorldTile(87-64, 84-64));
				}
				if (count == 147) {
					npc.setNextForceTalk(new ForceTalk("YOU'LL PAY FOR EVERYTHING YOU'VE DONE!"));
					player.sm("Cyndrith: YOU'LL PAY FOR EVERYTHING YOU'VE DONE!");
					npc.setNextAnimation(new Animation(11991));
					dragith.setNextGraphics(new Graphics(1927));
					dragith.applyHit(new Hit(npc, 10000, HitLook.CRITICAL_DAMAGE));
					dragith.applyHit(new Hit(npc, 15000, HitLook.CRITICAL_DAMAGE));
					dragith.applyHit(new Hit(npc, 15000, HitLook.CRITICAL_DAMAGE));
				}
				if (count == 148) {
					dragith.setNextForceTalk(new ForceTalk("Argh!"));
					player.sm("Dragith: Argh!");
				}
				if (count == 150)  {
					npc.setTarget(dragith);
					player.setNextWorldTile(getWorldTile(89-64, 84-64));
					player.unlock();
					dragith.setRandomWalk(false);
					dragith.setLocked(false);
					dragith.setTarget(player);
					dragith.setForceMultiAttacked(true);
					dragith.addFreezeDelay(2147483647);
					npc.setForceMultiAttacked(true);
					npc.setLocked(false);
					npc.setRandomWalk(false);
				}
				if (npc.getCombat().getTarget() != null && count > 150) {
					dragith.setTarget(player);
				if (count > 150 && count < 1500 && !dragith.withinDistance(player, 1)) {
					dragith.setForceMultiAttacked(true);
					npc.setForceMultiAttacked(true);
					dragith.setNextForceTalk(new ForceTalk("Get over here!"));
					player.setNextWorldTile(getWorldTile(89-64, 84-64));
					player.applyHit(new Hit(npc, 100, HitLook.POISON_DAMAGE));
				}
				} else if (count > 150 && count < 1500){
					count = 1500;
				}
				if (count == 1500) {
					dragith = new NoRespawn(15976, getWorldTile(88-64, 84-64), player);
					dragith.setLocked(true);
					npc.setLocked(true);
					player.lock();
					dragith.setNextForceTalk(new ForceTalk("Stop! You win, you win!"));
				}
				if (count == 1503) {
					dragith.setNextForceTalk(new ForceTalk("A deal is a deal, here's your key. I guess we'll meet again."));
				}
				if (count == 1505) {
					FadingScreen.fade(player, new Runnable() {
						@Override
						public void run() {
						}
					});
				}
				if (count == 1507) {
					player.getEliteChapterII().setQuestStage(7);
					player.setNextWorldTile(new WorldTile(3973, 4812,1));
					player.unlock();
					RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
					removeControler();
					Dialogue.closeNoContinueDialogue(player);
					stop();
				}
			} else
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
	
	private void spawnZombie(int x, int y) {
		int[] zombieid = {8149, 8150, 8151, 8152, 8153};
		int random = Utils.getRandom(zombieid.length - 1);
		NPC n = new NoRespawn(zombieid[random], getWorldTile(x-64,y-64), player);
		n.setLocked(true);
		n.faceEast();
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
