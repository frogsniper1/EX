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

public class KillerBossCombat extends CombatScript {

	public static final String[] ATTACKS = new String[] {
			"How ya doing?", "Trying to take what Daggonoth Kings had to offer me?!",
			"", "Good fight Ma boy!",
			"Aha! You want my rings? Face my strength!" };

	@Override
	public Object[] getKeys() {
		return new Object[] { 11751 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(5);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(16960));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(3189), center);
			npc.setNextForceTalk(new ForceTalk("Ouch, did that hurt? Sorry."));
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
									new Hit(npc, Utils.random(100),
											HitLook.REGULAR_DAMAGE));
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextAnimation(new Animation(811));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(3146), center);
			npc.setNextForceTalk(new ForceTalk("Trying to take what I got for killing the Daggonoth Kings?!"));
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
									new Hit(npc, Utils.random(50),
											HitLook.REGULAR_DAMAGE));
						}
					}
					if (count++ == 4) {
						stop();
						return;
					}
				}
			}, 0, 0);
		} else if (attackStyle == 2) {
			npc.setNextAnimation(new Animation(1979));
			final int dir = Utils.random(Utils.DIRECTION_DELTA_X.length);
			final WorldTile center = new WorldTile(npc.getX()
					+ Utils.DIRECTION_DELTA_X[dir] * 5, npc.getY()
					+ Utils.DIRECTION_DELTA_Y[dir] * 5, 0);
			npc.setNextForceTalk(new ForceTalk(
					"STORM NOW!"));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					for (Player player : World.getPlayers()) { // lets just loop
																// all players
																// for massive
																// moves
						if (Utils.DIRECTION_DELTA_X[dir] == 0) {
							if (player.getX() != center.getX())
								continue;
						}
						if (Utils.DIRECTION_DELTA_Y[dir] == 0) {
							if (player.getY() != center.getY())
								continue;
						}
						if (Utils.DIRECTION_DELTA_X[dir] != 0) {
							if (Math.abs(player.getX() - center.getX()) > 5)
								continue;
						}
						if (Utils.DIRECTION_DELTA_Y[dir] != 0) {
							if (Math.abs(player.getY() - center.getY()) > 5)
								continue;
						}
						delayHit(npc, 0, player,
								new Hit(npc, Utils.random(100),
										HitLook.REGULAR_DAMAGE));
					}
					if (count++ == 3) {
						stop();
						return;
					}
				}
			}, 0, 0);
			World.sendProjectile(npc, center, 2196, 0, 0, 5, 35, 0, 0);
		} else if (attackStyle == 3) {
			delayHit(
					npc,
					2,
					target,
					getMagicHit(
							npc,
							getRandomMaxHit(npc, Utils.random(300),
									NPCCombatDefinitions.MAGE, target)));
			World.sendProjectile(npc, target, 2873, 34, 16, 40, 35, 16, 0);
			npc.setNextAnimation(new Animation(14221));
			npc.setNextForceTalk(new ForceTalk(ATTACKS[Utils
					.random(ATTACKS.length)]));
		} else if (attackStyle == 4) {
			npc.setNextAnimation(new Animation(15535));
			npc.setNextGraphics(new Graphics(2197));
			npc.heal(70);
			npc.setNextForceTalk(new ForceTalk("[+70] HitPoints"));
		}
		return 5;
	}

}
