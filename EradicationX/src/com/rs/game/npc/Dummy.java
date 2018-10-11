package com.rs.game.npc;

import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

@SuppressWarnings("serial")
public class Dummy extends NPC {

	private static Player player;
	
	public Dummy(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		setCantFollowUnderCombat(true);
		setRandomWalk(false);
		setForceAgressive(false);
	}

	@Override
	public void processNPC() {
		if (isDead())
			return;
		if (!getCombat().process())
			checkAgressivity();
	}
	
	public void setPlayer(Player p) {
		player = p;
	}
	
	public Player getPlayer() {
		return player;
	}

}
