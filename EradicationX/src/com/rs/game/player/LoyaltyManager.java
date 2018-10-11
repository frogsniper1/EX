package com.rs.game.player;


public class LoyaltyManager {

	private static final int INTERFACE_ID = 1143;

	public LoyaltyManager(Player player) {
		
	}

	public void openLoyaltyStore(Player player) {
		player.getPackets().sendWindowsPane(INTERFACE_ID, 0);
	}

	public void startTimer() {
	
	}
}