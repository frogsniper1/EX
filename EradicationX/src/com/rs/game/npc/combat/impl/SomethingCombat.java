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

public class SomethingCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 15002 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		String name = "";
		if (target instanceof Player)
			name = ((Player) target).getDisplayName();		
		String[] ATTACKS = new String[] {
				"Pew pew", "Leave me alone! My bows!",
				"Die already!", ";;kill " + name,
				"Bolt to the knee!" };				
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.random(2);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(4230));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(753), center);
			int chatTalk = Utils.random(5);
			npc.setNextForceTalk(new ForceTalk(ATTACKS[chatTalk]));
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					for (Player player : World.getPlayers()) {
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 1)) {
							if (!player.getMusicsManager().hasMusic(171))
								player.getMusicsManager().playMusic(171);
							delayHit(npc, 0, player,
									new Hit(npc, getRandomMaxHit(npc, 380,
											NPCCombatDefinitions.RANGE, player),
											HitLook.RANGE_DAMAGE));
						}
					}
				}

			}, 0);
		} else if (attackStyle == 1) {
			npc.setNextGraphics(new Graphics(457));
			npc.setNextAnimation(new Animation(4230));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(756), center);
			int chatTalk = Utils.random(5);
			npc.setNextForceTalk(new ForceTalk(ATTACKS[chatTalk]));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					for (Player player : World.getPlayers()) {
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 1)) {
							delayHit(npc, 0, player,
									new Hit(npc, getRandomMaxHit(npc, 205,
											NPCCombatDefinitions.RANGE, player),
											HitLook.MAGIC_DAMAGE));
						}
					}
					if (count++ == 2) {
						stop();
						return;
					}
				}
			}, 0, 0);
		} else if (attackStyle == 2) {
					if (npc.getHitpoints() < 3000) {
						npc.setNextAnimation(new Animation(829));
						npc.heal(250);
						npc.setNextForceTalk(new ForceTalk("Phew, got some food left :D"));
						} else {
							npc.setNextForceTalk(new ForceTalk("Guess I don't need to eat right now. I'm not a safer, after all!"));
						}					
					
		}
		return defs.getAttackDelay();
	}

}

