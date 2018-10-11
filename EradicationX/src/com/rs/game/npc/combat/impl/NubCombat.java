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

public class NubCombat extends CombatScript {

	public static final String[] ATTACKS = new String[] {
			"I will make you cry, weak warrior!", "You're too easy!",
			"Let me guess, your Strength level is 5?", "Good fight Warrior!",
			"Your Combat level just makes me laugh!" };

	@Override
	public Object[] getKeys() {
		return new Object[] { 14260 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(5);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(811));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(2929), center);
			String name = "";
			if (target instanceof Player)
				name = ((Player) target).getDisplayName();
			npc.setNextForceTalk(new ForceTalk("Are you okay, " + name));
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
									new Hit(npc, Utils.random(50),
											HitLook.REGULAR_DAMAGE));
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextAnimation(new Animation(1979));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(2191), center);
			npc.setNextForceTalk(new ForceTalk("Unum te ignem!"));
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
									new Hit(npc, Utils.random(40),
											HitLook.REGULAR_DAMAGE));
						}
					}
					if (count++ == 10) {
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
								new Hit(npc, Utils.random(50),
										HitLook.REGULAR_DAMAGE));
					}
					if (count++ == 5) {
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
			npc.setNextAnimation(new Animation(1746));
			npc.setNextGraphics(new Graphics(444));
			npc.heal(50);
			npc.setNextForceTalk(new ForceTalk("<col=DB1616> Healed [50]"));
		}
		return 5;
	}

}
