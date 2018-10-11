package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class MasterCapes extends Dialogue {

	@Override
	public void start() {
		ShopsHandler.openShop(player, 53);
		end();
	}

	public void run(int interfaceId, int componentId) {
		
	}

	@Override
	public void finish() {

	}

}