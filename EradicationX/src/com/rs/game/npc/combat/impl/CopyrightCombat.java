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

public class CopyrightCombat extends CombatScript {


	@Override
	public Object[] getKeys() {
		return new Object[] { 1900 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		int attackStyle = Utils.random(2);
		String name = "";
		if (target instanceof Player)
			name = ((Player) target).getDisplayName();		
		String[] ATTACKS = new String[] {
				"My Magic is love, I'm throwing my love at you. :)", "My wand is purple. I like purple.",
				"You'll never be as cool as me.", "Die, " + name + "!",
				"Is that the best you've got?" };				
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(1979));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1677), center);
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
									new Hit(npc,  getRandomMaxHit(npc, 400,
											NPCCombatDefinitions.MAGE, player),
											HitLook.MAGIC_DAMAGE));
						}
					}
				}

			}, 4);
		} else if (attackStyle == 1) {
			npc.setNextGraphics(new Graphics(457));
			npc.setNextAnimation(new Animation(10546));
			final WorldTile center = new WorldTile(target);
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
									new Hit(npc,  getRandomMaxHit(npc, 200,
											NPCCombatDefinitions.MAGE, player),
											HitLook.MELEE_DAMAGE));
						}
					}
					if (count++ == 2) {
						stop();
						return;
					}
				}
			}, 0, 2);
		} else if (attackStyle == 2) {
			if (npc.getHitpoints() < 3000) {
			npc.setNextAnimation(new Animation(829));
			npc.heal(150);
			npc.setNextForceTalk(new ForceTalk("One sec, gotta swallow this gigantic swordfish."));
			} else {
				npc.setNextForceTalk(new ForceTalk("Don't worry, I'm not a safer :)."));
			}			
		}
		return defs.getAttackDelay();
	}

}

