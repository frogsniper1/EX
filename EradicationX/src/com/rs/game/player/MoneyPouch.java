package com.rs.game.player;

import java.io.Serializable;

public class MoneyPouch implements Serializable {
	private static final long serialVersionUID = 1597252927535222069L;
	
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void refresh() {
		player.getPackets().sendRunScript(5560, getAmount(), "n");
	}
	
	
	public void sendScript(boolean add, int amount) {
		player.getPackets().sendRunScript(5561, add ? 1 : 0, "n", amount);
	}
	
	private transient Player player;
	public void setPlayer(Player player) {
		this.player = player;
	}
}
