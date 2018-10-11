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

public class HardModeSomething extends CombatScript {
	
	private String[] DIALOGUES = new String[] { "X[)",
											  "Bolt to the knee!",
											  "You're never going to kill us.", "No one is getting my crossbows!",
											  "We're going to end you all in an instant.", ""};		
	@Override
	public Object[] getKeys() {
		return new Object[] {15978};
	}
	private int getSpeed(NPC npc) {
		if (npc.getHitpoints() <= 10000 && npc.getHitpoints() >= 9000)
			npc.setNextForceTalk(new ForceTalk("Kill them, fast!"));
		return npc.getHitpoints() > 10000 ? 9 : 6;
	}
	
	@Override
	public int attack(final NPC npc, Entity target) {
		WorldTile tile = target.getLastWorldTile();
		if (getSpeed(npc) == 9) {
			npc.setNextForceTalk(new ForceTalk(DIALOGUES[Utils.random(6)]));
			switch (Utils.random(3)) {
			case 0:
				if (Utils.random(2) == 0) {
				npc.setNextAnimation(new Animation(4602));
				npc.setNextForceTalk(new ForceTalk("Here comes the shock!"));
				WorldTile tile1 = new WorldTile(tile.getX()+2, tile.getY(), tile.getPlane());
				WorldTile tile2 = new WorldTile(tile.getX()-3, tile.getY(), tile.getPlane());
				WorldTile tile3 = new WorldTile(tile.getX(), tile.getY(), tile.getPlane());	
				World.sendProjectile(npc, tile1, 1196, 60, 32, 30, 0, 0, 0);
				World.sendProjectile(npc, tile2, 1196, 60, 32, 30, 0, 0, 0);
				World.sendProjectile(npc, tile3, 1196, 60, 32, 30, 0, 0, 0);
				for (int i = 5; i < 100; i+=5) {
				World.sendProjectile(npc, tile1, 27, 38, 36, 41, i, 5, 0);
				World.sendProjectile(npc, tile2, 27, 38, 36, 41, i, 5, 0);
				World.sendProjectile(npc, tile3, 27, 38, 36, 41, i, 5, 0);
				}
				WorldTasksManager.schedule(new WorldTask() {
					int count = 0;
					@Override
					public void run() {
						count++;
						if (count == 6)
							this.stop();
						if (count == 2) {
							World.sendGraphics(npc, new Graphics(1194), tile1);
							World.sendGraphics(npc, new Graphics(1194), tile2);
							World.sendGraphics(npc, new Graphics(1194), tile3);
						}
						if (count > 2) {
							for (Player player : World.getPlayers()) { 
								if (player == null || player.isDead() || player.hasFinished())
									continue;
								if (player.withinDistance(tile1, 0) || player.withinDistance(tile2, 0) || player.withinDistance(tile3, 0)) {
									for (int i = 0; i < 3; i++)
										player.applyHit(new Hit(npc, 25, HitLook.RANGE_DAMAGE));
								}
							}
						}
					}
				}, 0, 0);
				return 10;
				} else {
					npc.setNextAnimation(new Animation(15241));
					target.setNextGraphics(new Graphics(538));	
					delayHit(npc, 0, target,
							new Hit(npc, getRandomMaxHit(npc, 250,
									NPCCombatDefinitions.RANGE, target),
									HitLook.RANGE_DAMAGE));
					delayHit(npc, 2, target,
							new Hit(npc, getRandomMaxHit(npc, 350,
									NPCCombatDefinitions.RANGE, target),
									HitLook.RANGE_DAMAGE));
					delayHit(npc, 3, target,
							new Hit(npc, getRandomMaxHit(npc, 350,
									NPCCombatDefinitions.RANGE, target),
									HitLook.RANGE_DAMAGE));
					delayHit(npc, 4, target,
							new Hit(npc, getRandomMaxHit(npc, 350,
									NPCCombatDefinitions.RANGE, target),
									HitLook.RANGE_DAMAGE));
					delayHit(npc, 5, target,
							new Hit(npc, getRandomMaxHit(npc, 350,
									NPCCombatDefinitions.RANGE, target),
									HitLook.RANGE_DAMAGE));
					delayHit(npc, 5, target,
							new Hit(npc, getRandomMaxHit(npc, 350,
									NPCCombatDefinitions.RANGE, target),
									HitLook.RANGE_DAMAGE));
					return 7;
				}
			case 1:
				npc.setNextAnimation(new Animation(4230));
				delayHit(npc, 0, target,
						new Hit(npc, getRandomMaxHit(npc, 350,
								NPCCombatDefinitions.RANGE, target),
								HitLook.RANGE_DAMAGE));
				World.sendProjectile(npc, target, 27, 38, 36, 41, 32, 5, 0);
				return 5;
			case 2:
				npc.setNextGraphics(new Graphics(1491));
				npc.setNextAnimation(new Animation(10505));
				target.applyHit(new Hit(target, getRandomMaxHit(npc, 500, NPCCombatDefinitions.MELEE, target),
									HitLook.MELEE_DAMAGE));
				if (target instanceof Player) { 
					if (((Player) target).getPrayer().hasPrayersOn()) {
						npc.setNextForceTalk(new ForceTalk("Take off your prayers."));
						((Player) target).setPrayerDelay(5000);
					}
				}
				return 5;
			}
		} else {
			npc.setNextAnimation(new Animation(4230));
			delayHit(npc, 0, target,
					new Hit(npc, getRandomMaxHit(npc, 200,
							NPCCombatDefinitions.RANGE, target),
							HitLook.CRITICAL_DAMAGE));
			delayHit(npc, 1, target,
					new Hit(npc, getRandomMaxHit(npc, 150,
							NPCCombatDefinitions.RANGE, target),
							HitLook.CRITICAL_DAMAGE));
			World.sendProjectile(npc, target, 27, 38, 36, 41, 32, 5, 0);
			World.sendProjectile(npc, target, 27, 38, 36, 41, 46, 5, 0);
			return 4;
		}
		return getSpeed(npc);
	}

}

