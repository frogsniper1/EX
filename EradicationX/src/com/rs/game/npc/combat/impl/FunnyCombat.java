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

public class FunnyCombat extends CombatScript {

	public static final String[] ATTACKS = new String[] {
			"I will make you cry little warrior!", "Can you feel the fear consuming you?",
			"Why do you even try?", "RAAAAAAAAARGH!",
			"Hahahaha! IS THIS ALL YOU CAN DO!?" };

	@Override
	public Object[] getKeys() {
		return new Object[] { 15172 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(3);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(16960));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(540), center);
			npc.setNextForceTalk(new ForceTalk("Prepare to face death!"));
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					for (Player player : World.getPlayers()) { // lets just loop
																// all players
																// for massive
																// moves
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 3)) {
							if (!player.getMusicsManager().hasMusic(843))
								player.getMusicsManager().playMusic(843);
							delayHit(npc, 0, player,
									new Hit(npc, getRandomMaxHit(npc, 150,
											NPCCombatDefinitions.MAGE, player),
											HitLook.POISON_DAMAGE));
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextAnimation(new Animation(811));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(439), center);
			npc.setNextGraphics(new Graphics(6));
			npc.setNextForceTalk(new ForceTalk("Your allies think you are weak!..."));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					for (Player player : World.getPlayers()) { // lets just loop
																// all players
																// for massive
																// moves
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 1)) {
							delayHit(npc, 0, player,
									new Hit(npc, getRandomMaxHit(npc, 200,
											NPCCombatDefinitions.MAGE, player),
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
			delayHit(npc, 0, target,
					new Hit(npc, getRandomMaxHit(npc, 158,
							NPCCombatDefinitions.MAGE, target),
							HitLook.MELEE_DAMAGE));
			World.sendProjectile(npc, target, 2873, 34, 16, 40, 35, 16, 0);
			npc.setNextAnimation(new Animation(14221));
			npc.setNextForceTalk(new ForceTalk(ATTACKS[Utils
					.random(ATTACKS.length)]));
		} else if (attackStyle == 4) {
			npc.setNextGraphics(new Graphics(6));
			npc.heal(400);
			npc.setNextForceTalk(new ForceTalk("Your FEAR heals me! [+400]"));
		}
		return 4;
	}

}
