package com.rs.game.npc.instances;

import com.rs.game.Entity;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;

@SuppressWarnings("serial")
public class NoRespawn extends NPC {
	
	public NoRespawn(int id, WorldTile tile, Player player) {
		super(id, tile, -1, true, true);	
	}	
	

	@Override
	public void sendDeath(Entity source) {
		super.sendDeathNoDrop(source);
	}



}
