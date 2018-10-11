package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class HardModeCopy extends CombatScript {
	
	private String[] DIALOGUES = new String[] { "Fatal",
											  "I'm not going to hold an event!",
											  "Like my wand? Shame that you'll never get it.", "Can't move, can you?",
											  "Freeze them all!", ""};		
	@Override
	public Object[] getKeys() {
		return new Object[] {15979};
	}
	private int getSpeed(NPC npc) {
		if (npc.getHitpoints() <= 10000 && npc.getHitpoints() >= 9000)
			npc.setNextForceTalk(new ForceTalk("Really low on health .-."));
		return npc.getHitpoints() > 10000 ? 9 : 6;
	}
	
	@Override
	public int attack(final NPC npc, Entity target) {
		WorldTile tile = target.getLastWorldTile();
		if (getSpeed(npc) == 9) {
			npc.setNextForceTalk(new ForceTalk(DIALOGUES[Utils.random(6)]));
			switch (Utils.getRandom(1)) {
			case 0:
				npc.setNextAnimation(new Animation(12658));
				npc.setNextForceTalk(new ForceTalk("Feel the undead!"));
				World.sendProjectile(npc, tile, 1338, 0, 0, 10, 5, 0, 0);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 0;
					@Override
					public void run() {
						count++;
						if (count == 6)
							this.stop();
						if (count > 5) {
							for (Player player : World.getPlayers()) { 
								if (player == null || player.isDead() || player.hasFinished())
									continue;
								if (player.withinDistance(tile, 1)) {
									player.applyHit(new Hit(player, 200, HitLook.DESEASE_DAMAGE));
									player.getPoison().makePoisoned(158, true);
								}
							}
						}
					}
				}, 0, 0);
				return 6;
			case 1:
				npc.setNextAnimation(new Animation(1978));
				delayHit(npc, 0, target,
						new Hit(npc, getRandomMaxHit(npc, 350,
								NPCCombatDefinitions.MAGE, target),
								HitLook.MAGIC_DAMAGE));
				target.addFreezeDelay(10000, true);
				target.setNextGraphics(new Graphics (369));
				World.sendProjectile(npc, target, 368, 60, 32, 50, 50, 0, 0);
				return 5;
			}
		} else {
			npc.setNextGraphics(new Graphics(2728));
			npc.setNextAnimation(new Animation(14221));
			WorldTile tile1 = new WorldTile(tile.getX()+1, tile.getY(), tile.getPlane());
			WorldTile tile2 = new WorldTile(tile.getX()-1, tile.getY(), tile.getPlane());
			WorldTile tile3 = new WorldTile(tile.getX(), tile.getY()+1, tile.getPlane());
			World.sendProjectile(npc, tile1, 393, 60, 32, 30, 50, 0, 0);
			World.sendProjectile(npc, tile2, 393, 60, 32, 30, 50, 0, 0);
			World.sendProjectile(npc, tile3, 393, 60, 32, 30, 50, 0, 0);
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;
				@Override
				public void run() {
					count++;
					if (count == 2)
						this.stop();
					if (count > 1) {
						for (Player player : World.getPlayers()) { 
							if (player == null || player.isDead() || player.hasFinished())
								continue;
							if (player.withinDistance(tile1, 1)) {
								player.setNextGraphics(new Graphics (506));
								player.applyHit(new Hit(npc, 90, HitLook.MAGIC_DAMAGE));
							}
							if (player.withinDistance(tile2, 1)) {
								player.setNextGraphics(new Graphics (506));
								player.applyHit(new Hit(npc, 90, HitLook.MAGIC_DAMAGE));
							}
							if (player.withinDistance(tile3, 1)) {
								player.setNextGraphics(new Graphics (506));
								player.applyHit(new Hit(npc, 90, HitLook.MAGIC_DAMAGE));
							}
						}
					}
				}
			}, 0, 1);
			return 4;
		}
		return getSpeed(npc);
	}

}

