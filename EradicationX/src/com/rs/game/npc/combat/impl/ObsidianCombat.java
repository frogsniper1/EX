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
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class ObsidianCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 1895, 11 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(7);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(4230));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1194), center);
			npc.setNextForceTalk(new ForceTalk("You will never kill me!"));
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
							if (!player.getMusicsManager().hasMusic(171))
								player.getMusicsManager().playMusic(171);
							delayHit(npc, 0, player,
									new Hit(npc, Utils.random(300),
											HitLook.RANGE_DAMAGE));
						}
						if (player.getFamiliar() != null) {
							if (player.getFamiliar().withinDistance(center, 3)) {
							delayHit(npc, 0, player.getFamiliar(),
									new Hit(npc, Utils.random(150),
											HitLook.MAGIC_DAMAGE));
							}
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextGraphics(new Graphics(457));
			npc.setNextAnimation(new Animation(1979));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1019), center);
			npc.setNextForceTalk(new ForceTalk("You will die!"));
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
									new Hit(npc, Utils.random(150),
											HitLook.MAGIC_DAMAGE));
						}
						if (player.getFamiliar() != null) {
							if (player.getFamiliar().withinDistance(center, 3)) {
							delayHit(npc, 0, player.getFamiliar(),
									new Hit(npc, Utils.random(150),
											HitLook.MAGIC_DAMAGE));
							}
						}
					}
					if (count++ == 0) {
						stop();
						return;
					}
				}
			}, 0, 0);
		} else if (attackStyle == 2) {
			npc.setNextAnimation(new Animation(829));
			npc.heal(250);
			npc.setNextForceTalk(new ForceTalk("+250"));
		}
		return 1;
	}

}

