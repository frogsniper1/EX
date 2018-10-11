package com.rs.game.npc.combat.impl;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.utils.Utils;

public class ZombiesCombat extends CombatScript{

	@Override
	public Object[] getKeys() {
		return new Object[] {73, 74, 75, 76, 77};
	}


	@Override
	public int attack(final NPC npc, final Entity target) {
		if(npc.getHitpoints() < npc.getMaxHitpoints()/2 && Utils.random(5) == 0) { //if lower than 50% hp, 1/5 prob of healing 10%
			npc.heal(20);
			npc.setNextForceTalk(new ForceTalk("I feel a tingley feeling.. MY HEALTH IS BOOSTED!"));
		}
		npc.setCombatLevel(50);
		npc.setHitpoints(290);
		npc.setName("Zombie");

		
		
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		if (Utils.getRandom(4) == 0) {
			switch (Utils.getRandom(3)) {
			case 0:
				npc.setNextForceTalk(new ForceTalk("WE WILL EAT YOUR BRAINS!"));
				break;
			case 1:
				npc.setNextForceTalk(new ForceTalk("The night will fall soon and we shall have meat in our hands."));
				break;
			case 2:
				npc.setNextForceTalk(new ForceTalk("Flesh we need flesh.."));
				break;
			}
		}
		if (Utils.getRandom(2) == 0) { // range magical attack
			for (Entity t : npc.getPossibleTargets()) {
				delayHit(
						npc,
						1,
						t,
						getRangeHit(
								npc,
								getRandomMaxHit(npc, 355,
										NPCCombatDefinitions.RANGE, t)));
				World.sendProjectile(npc, t, 1200, 41, 16, 41, 35, 16, 0);
				  target.applyHit(new Hit(target, Utils.random(50, 67), Hit.HitLook.REGULAR_DAMAGE));
			}
		} else { // melee attack
			npc.setNextAnimation(new Animation(defs.getAttackEmote()));
			delayHit(
					npc,
					0,
					target,
					getMeleeHit(
							npc,
							getRandomMaxHit(npc, defs.getMaxHit(),
									NPCCombatDefinitions.MELEE, target)));
		}
		return defs.getAttackDelay();
	}
	
}
