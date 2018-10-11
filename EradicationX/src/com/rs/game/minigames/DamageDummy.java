package com.rs.game.minigames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.npc.Dummy;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.DummyRank;
import com.rs.utils.Utils;

public final class DamageDummy {

	private static final List<Player> playersOn = Collections
			.synchronizedList(new ArrayList<Player>());

	public static Dummy dummy;
	
	public static int getPlayersCount() {
		return playersOn.size();
	}

	public static void addPlayer(Player player) {
		if (playersOn.contains(player)) {
			return;
		}
		playersOn.add(player);
		startFight(player);
	}

	public static ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>(
				playersOn.size());
		for (Player player : playersOn) {
			if (player == null || player.isDead() || player.hasFinished()
					|| !player.isRunning())
				continue;
			possibleTarget.add(player);
		}
		return possibleTarget;
	}
	
	public static void removePlayer(Player player) {
		playersOn.remove(player);
	}
	
	public static void startFight(Player player) {
				dummy.transformIntoNPC(7891);
				dummy.setNextForceTalk(new ForceTalk ("Go!"));
				dummy.setPlayer(player);
				WorldTasksManager.schedule(new WorldTask() {
					private int count = 60;
					@Override
					public void run() {
						if (dummy.getId() == 15102)
							dummy.transformIntoNPC(7891);
						if (!playersOn.contains(player)) {
							dummy.setNextForceTalk(new ForceTalk("Alrighty then!"));
							dummy.setAttackedBy(null);
							dummy.setHitpoints(34463);
							dummy.transformIntoNPC(15102);
							stop();
						} else {
						if (dummy.getHitpoints() < 500)
							dummy.setHitpoints(700);
						if (count == 60)
							dummy.setNextForceTalk(new ForceTalk ("Start!!"));
						else
							dummy.setNextForceTalk(new ForceTalk (""+count));
						count--;
						if (count <= 0) {
							dummy.setNextForceTalk(new ForceTalk ("You dealt " + (Utils.formatNumber(34463 - dummy.getHitpoints()))+ " damage!"));
							if (player.getDummyDamage() < 34463 - dummy.getHitpoints()) {
								player.setDummyDamage(34463 - dummy.getHitpoints());
								player.sm("You have a new personal best! You dealt: " + Utils.formatNumber(player.getDummyDamage()) + " damage.");
								DummyRank.checkRank(player);
							}
							int reward = (34463 - dummy.getHitpoints()) / 20;
							player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() + reward);
							player.setSpellPower(player.getSpellPower() + (reward/4));
							player.sm("You receive "+ reward + " invasion tokens!");
							dummy.transformIntoNPC(15102);
							dummy.setAttackedBy(null);
							dummy.setHitpoints(34463);
							if (player != null) {
							player.setAttackedBy(null);
							player.setAttackedByDelay(Utils.currentTimeMillis());
							player.setDummyCooldown(Utils.currentTimeMillis());
							playersOn.remove(player);
							player.getControlerManager().forceStop();
							}
							stop();
						}
						}
					}
					
				}, 0, 1);
			}
		

	private DamageDummy() {

	}
}
