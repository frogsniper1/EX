package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class HairyCombat extends CombatScript {

	@Override
	public Object[] getKeys() {

		return new Object[] { 15961 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.random(3);
		if (attackStyle == 2) { // melee
			npc.setNextForceTalk(new ForceTalk("APPLE PIES!"));
			int distanceX = target.getX() - npc.getX();
			int distanceY = target.getY() - npc.getY();
			int size = npc.getSize();
			if (distanceX > size || distanceX < -1 || distanceY > size
					|| distanceY < -1)
				attackStyle = Utils.random(2); // set mage
			else {
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				delayHit(
						npc,
						1,
						target,
						getMeleeHit(
								npc,
								getRandomMaxHit(npc, defs.getMaxHit(),
										NPCCombatDefinitions.MELEE, target)));
				return defs.getAttackDelay();
			}
		}
		if (attackStyle == 1) { // range
			npc.setNextAnimation(new Animation(15241));
			target.setNextGraphics(new Graphics(43));
			target.setNextGraphics(new Graphics(538));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1677), center);
			npc.setNextForceTalk(new ForceTalk("Apple"));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					target.setNextGraphics(new Graphics(3000));
					delayHit(
							npc,
							1,
							target,
							getRangeHit(
									npc,
									getRandomMaxHit(npc, defs.getMaxHit() - 2,
											NPCCombatDefinitions.RANGE, target)));
				}
			}, 3);
		} else {
			npc.setNextAnimation(new Animation(1979));
			npc.setNextForceTalk(new ForceTalk("Apple Pie"));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					World.sendProjectile(npc, target, 2996, 80, 30, 40, 20, 5,
							0);
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							target.setNextGraphics(new Graphics(2741, 0, 100));
							delayHit(
									npc,
									0,
									target,
									getMagicHit(
											npc,
											getRandomMaxHit(npc,
													defs.getMaxHit() - 2,
													NPCCombatDefinitions.MAGE,
													target)));
						}

					}, 1);
				}
			}, 2);
		}

		return defs.getAttackDelay() + 2;
	}

}
