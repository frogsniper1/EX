package com.rs.game.npc.instances;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

@SuppressWarnings("serial")
public class EliteChapterTwoDragith extends NPC {
	
	private Player player;
	
	public EliteChapterTwoDragith(int id, WorldTile tile, Player player) {
		super(id, tile, -1, true, true);	
		setForceAgressive(false);
		setRandomWalk(false);
		setNoDistanceCheck(true);
		setCapDamage(1000);
		setHitpoints(22500);
		this.player = player;
	}	
	

	@Override
	public void sendDeath(Entity source) {
		player.getEliteChapterII().setQuestStage(2);
		super.sendDeathNoDrop(source);
	}



}
