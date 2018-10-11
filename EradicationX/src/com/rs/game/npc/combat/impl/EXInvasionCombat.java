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

public class EXInvasionCombat extends CombatScript {

	public static final String[] ATTACKS = new String[] {
			"I've killed the entire human race of Gielinor! What makes you think you can kill me?", "Even if you killed my army of the dead, I can take care of all of you!",
			"There's no way you can stop me.", "Die!",
			"I will grant my master's last wish and kill the rest of you!" };

	@Override
	public Object[] getKeys() {
		return new Object[] { 9622, 15235 };
	}
	
	private Hit isBig(NPC npc) {
		if (npc.getId() == 9622)
			return new Hit(npc, Utils.random(150), HitLook.MAGIC_DAMAGE);
		else 
			return new Hit(npc, Utils.random(120), HitLook.POISON_DAMAGE);
	}
	
	private int isBigTimer(NPC npc) {
		if (npc.getId() != 9622)
			return 4;
		else
			return 3;
	}
	
	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(3);
		int dialogue = Utils.getRandom(4);
		if (attackStyle == 0) {
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1677), center);
			npc.setNextForceTalk(new ForceTalk(ATTACKS[dialogue]));
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
									isBig(npc));
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextGraphics(new Graphics(457));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1019), center);
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
		} else if (attackStyle == 2) {
			Hit heal = new Hit(npc, 150, HitLook.HEALED_DAMAGE);
			npc.applyHit(heal);
			npc.heal(150);
			npc.setNextForceTalk(new ForceTalk("<col=E31919>Your attacks only aid me!"));
		}
		return isBigTimer(npc);
	}
	

}

