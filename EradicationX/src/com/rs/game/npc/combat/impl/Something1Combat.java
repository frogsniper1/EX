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

/**
 * 
 * @author bitch no1 im not known
 *   - Reworked Mercenary Mage combat!
 *  information: spawn NPC 8335 || he is build for multicombat so make sure it's a multizone
 *  you might want to lower or higher its hits.
 *
 */

public class Something1Combat extends CombatScript {

	public static final String[] ATTACKS = new String[] {
			"I will make you suffer!", "Death is your only option!",
			"Why fight back?", "It is time for you to die.",
			"IS THIS ALL YOU'VE GOT?" };

	@Override
	public Object[] getKeys() {
		return new Object[] { 8335 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(5);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(1979));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(2929), center);
			npc.setNextForceTalk(new ForceTalk("Obliterate!"));
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					for (Player player : World.getPlayers()) { 
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 20)) {
							if (!player.getMusicsManager().hasMusic(843))
								player.getMusicsManager().playMusic(843);
							delayHit(npc, 0, player,
									new Hit(npc, Utils.random(3000),HitLook.REGULAR_DAMAGE)); //attack with a delayed hit,change the 500 to change its max hit
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextAnimation(new Animation(1979));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(2191), center);
			npc.setNextForceTalk(new ForceTalk("How are the burns?"));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					for (Player player : World.getPlayers()) {
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 20)) {
							delayHit(npc, 0, player,
									new Hit(npc, Utils.random(3000),HitLook.REGULAR_DAMAGE)); //volcano attack, change the "400" to change this max hit
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
					"I think it's time to clean my room!"));
			WorldTasksManager.schedule(new WorldTask() {
				int count = 0;

				@Override
				public void run() {
					for (Player player : World.getPlayers()) { 
						if (Utils.DIRECTION_DELTA_X[dir] == 0) {
							if (player.getX() != center.getX())
								continue;
						}
						if (Utils.DIRECTION_DELTA_Y[dir] == 0) {
							if (player.getY() != center.getY())
								continue;
						}
						if (Utils.DIRECTION_DELTA_X[dir] != 0) {
							if (Math.abs(player.getX() - center.getX()) > 20)
								continue;
						}
						if (Utils.DIRECTION_DELTA_Y[dir] != 0) {
							if (Math.abs(player.getY() - center.getY()) > 20)
								continue;
						}
						delayHit(npc, 0, player,
								new Hit(npc, Utils.random(3000),HitLook.REGULAR_DAMAGE)); //area of effect attack. hits multiple players for max 500, again change the "500" to change its max hit
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
					getMagicHit(npc,getRandomMaxHit(npc, Utils.random(3000),NPCCombatDefinitions.MAGE, target))); //change the 300 to change hit again
			World.sendProjectile(npc, target, 2873, 34, 16, 40, 35, 16, 0);
			npc.setNextAnimation(new Animation(14221));
			npc.setNextForceTalk(new ForceTalk(ATTACKS[Utils.random(ATTACKS.length)]));
		} else if (attackStyle == 4) {
			npc.setNextAnimation(new Animation(1979));
			npc.setNextGraphics(new Graphics(444));
			npc.heal(20000);										 //change heal here and below
			npc.applyHit(new Hit(npc, 20000, HitLook.HEALED_DAMAGE));//he heals using this attack. he heals for 200 you can lower or higher it ofcourse
		}
		return 5;
	}

}