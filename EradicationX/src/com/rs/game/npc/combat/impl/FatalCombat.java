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

public class FatalCombat extends CombatScript {
	

	@Override
	public Object[] getKeys() {
		return new Object[] { 15003 };
	}

	@Override
	public int attack(final NPC npc, Entity target) {
		String name = "";
		if (target instanceof Player)
			name = ((Player) target).getDisplayName();		
		String[] ATTACKS = new String[] {
				"Stabbity stab stab!", "Joust with me!",
				"Wait a minute, how are you hitting through god mode?", "Consider yourself dead, " + name + ".",
				"If you let me live I'll give you staff :}? (I probably won't..)" };		
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.random(2);
		if (attackStyle == 0) {
			npc.setNextAnimation(new Animation(13049));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1999), center);			
			int chatTalk = Utils.random(5);
			npc.setNextForceTalk(new ForceTalk(ATTACKS[chatTalk]));
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
						if (player.withinDistance(center, 1)) {
							if (!player.getMusicsManager().hasMusic(171))
								player.getMusicsManager().playMusic(171);
							delayHit(npc, 0, player,
									new Hit(npc, getRandomMaxHit(npc, 400,
											NPCCombatDefinitions.MELEE, player),
											HitLook.MELEE_DAMAGE));
						}
					}
				}

			}, 0);
		} else if (attackStyle == 1) {
			npc.setNextGraphics(new Graphics(457));
			npc.setNextAnimation(new Animation(10961));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(1950), center);
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
									new Hit(npc, getRandomMaxHit(npc, 300,
											NPCCombatDefinitions.MELEE, player),
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
			if (npc.getHitpoints() < 4500) {
			npc.setNextAnimation(new Animation(829));
			npc.heal(250);
			npc.setNextForceTalk(new ForceTalk("Om nom nom nom."));
			} else {
				npc.setNextForceTalk(new ForceTalk("Oh crap! Out of food!.... ;;item 15272 28."));
			}
		} else if (attackStyle == 3) {
			npc.setNextAnimation(new Animation(15623));
			final WorldTile center = new WorldTile(target);
			World.sendGraphics(npc, new Graphics(91), center);			
			int chatTalk = Utils.random(5);
			npc.setNextForceTalk(new ForceTalk(ATTACKS[chatTalk]));
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
						if (player.withinDistance(center, 1)) {
							if (!player.getMusicsManager().hasMusic(171))
								player.getMusicsManager().playMusic(171);
							delayHit(npc, 0, player,
									new Hit(npc, getRandomMaxHit(npc, 350,
											NPCCombatDefinitions.MELEE, player),
											HitLook.MELEE_DAMAGE));
							delayHit(npc, 1, player,
									new Hit(npc,  getRandomMaxHit(npc, 100,
											NPCCombatDefinitions.MELEE, player),
											HitLook.MELEE_DAMAGE));
							delayHit(npc, 2, player,
									new Hit(npc,  getRandomMaxHit(npc, 100,
											NPCCombatDefinitions.MELEE, player),
											HitLook.MELEE_DAMAGE));							
						}
					}
				}

			}, 0);
		}
		return defs.getAttackDelay();
	}

}

