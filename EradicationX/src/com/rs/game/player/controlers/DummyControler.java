package com.rs.game.player.controlers;

import com.rs.game.minigames.DamageDummy;

public class DummyControler extends Controler {

	@Override
	public void start() {
		DamageDummy.addPlayer(player);
	}

	@Override
	public boolean logout() {
		DamageDummy.removePlayer(player);
		return true; 
	}
}
