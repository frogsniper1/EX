package com.rs.game.npc.combat.impl;

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

public class EliteChapterIIDragith extends CombatScript {

	public static final String[] ATTACKS = new String[] {
		"Raargh!", "You're a fool, Cyndrith.",
			"Run away before I end your life for good." ,"You're useless! Both of you!", "You won't win!", "Die!"};

	@Override
	public Object[] getKeys() {
		return new Object[] { 15976 };
	}
	
	private Hit isBig(NPC npc) {
		int num = Utils.getRandom(2);
		if (num == 2)
			return new Hit(npc, Utils.random(90), HitLook.DESEASE_DAMAGE);
		if (num == 1)
			return new Hit(npc, Utils.random(90), HitLook.REGULAR_DAMAGE);
		return new Hit(npc, Utils.random(250), HitLook.MAGIC_DAMAGE);
	}
	
	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(3);
		int dialogue = Utils.getRandom(4);
		if (attackStyle == 0) {
			final WorldTile center = new WorldTile(target);
			npc.setNextGraphics(new Graphics(1009));
			npc.setNextForceTalk(new ForceTalk(ATTACKS[dialogue]));
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {		 
					for (Player player : World.getPlayers()) {
						if (player == null || player.isDead()
								|| player.hasFinished())
							continue;
						if (player.withinDistance(center, 1)) {					
							delayHit(npc, 0, player,
									isBig(npc));
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(129), center);
			npc.setNextForceTalk(new ForceTalk(ATTACKS[dialogue]));
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
									isBig(npc));
						}
					}
					if (count++ == 2) {
						stop();
						return;
					}
				}
			}, 0, 0);
		} 
		return 4;
	}
	

}

