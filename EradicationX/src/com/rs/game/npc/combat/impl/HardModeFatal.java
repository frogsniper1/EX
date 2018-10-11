package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.content.Foods;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class HardModeFatal extends CombatScript {
	
	private String[] DIALOGUES = new String[] { "Come on, let's kill these noobs.",
											  "I deny your suggestion!",
											  "No, I won't fix that bug!", "Die",
											  "Keep at it guys!", ""};		
	@Override
	public Object[] getKeys() {
		return new Object[] {15977};
	}
	private int getSpeed(NPC npc) {
		if (npc.getHitpoints() <= 10000 && npc.getHitpoints() >= 9000)
			npc.setNextForceTalk(new ForceTalk("I'm not done yet!"));
		return npc.getHitpoints() > 10000 ? 9 : 6;
	}
	
	@Override
	public int attack(final NPC npc, Entity target) {
		if (getSpeed(npc) == 9) {
			npc.setNextForceTalk(new ForceTalk(DIALOGUES[Utils.random(6)]));
			switch (Utils.random(3)) {
			case 0:
				npc.setNextForceTalk(new ForceTalk("Try and dodge this!"));
				npc.setNextAnimation(new Animation(1132));
				WorldTile tile = target.getLastWorldTile();
				World.sendGraphics(target, new Graphics(1205), tile);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 0;
					@Override
					public void run() {
						count++;
						if (count == 12)
							this.stop();
						if (count > 4) {
							for (Player player : World.getPlayers()) { 
								if (player == null || player.isDead() || player.hasFinished())
									continue;
								if (player.withinDistance(tile, 1)) {
									for (int i = 0; i < 2; i++)
										player.applyHit(new Hit(player, 50, HitLook.MELEE_DAMAGE));
								}
							}
						}
					}
				}, 0, 0);
				return 12;
			case 1:
				npc.setNextAnimation(new Animation(13049));
				target.applyHit(new Hit(target, getRandomMaxHit(npc, 400, NPCCombatDefinitions.MELEE, target),
								HitLook.MELEE_DAMAGE));
				return 5;
			case 2:
				boolean hasfood = false;
				if (target instanceof Player) {
					for (Item item : ((Player) target).getInventory().getItems().getItems()) {
						if (item == null)
							continue;
						if (Foods.isFood(item)) {
							hasfood = true;
							npc.setNextForceTalk(new ForceTalk("You might want to eat your food while you still have it."));
							npc.setNextAnimation(new Animation(861));
							((Player) target).getInventory().deleteItem(item.getId(), 1);
							break;
						}
					}
				}
				if (!hasfood) {
					npc.setNextAnimation(new Animation(13049));
					target.applyHit(new Hit(target, getRandomMaxHit(npc, 400, NPCCombatDefinitions.MELEE, target),
									HitLook.MELEE_DAMAGE));
					return 5;	
				}
				return 6;
			case 3:
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
			npc.setNextAnimation(new Animation(13049));
			target.applyHit(new Hit(target, getRandomMaxHit(npc, 350, NPCCombatDefinitions.MELEE, target),
							HitLook.CRITICAL_DAMAGE));
			return 2;
		}
		return getSpeed(npc);
	}

}

