package com.rs.game.player.quest;

import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class EliteChapterTwo extends Quest {

	private static final long serialVersionUID = 2533402884316193822L;
	
	private String[] buried;
	private int buriedAmount;
	private long skipDelay;
	
	public EliteChapterTwo() {
		super("The Elite: Chapter 2");
	}

	public void refresh(Player player) {
	}
	
	public void setComplete(Player player) {
		isComplete = true;
		player.getInterfaceManager().sendInterface(1236);
		if (player.getInventory().hasFreeSlots())
			player.getInventory().addItem(27747, 1);
		else
			player.getBank().addItem(27747, 1, true);
		player.sm("Scroll went into your into your inventory or bank.");
		player.sit(1236, 20, "The Elite: Chapter II");
		player.sit(1236, 21, "Quest complete! You can use your scroll of enchantment to turn a "
				+ "set of Obsidian Gloves elite. If you ever need another scroll, you can use Cyndrith's chest to buy another one.");
	}
	
	public boolean meetsRequirements(Player player) {
		if (player.getEliteChapterI().isComplete())
			return true;
		return false;
	}

	public String[] getBuried() {
		return buried;
	}

	public void setBuried(String[] buried) {
		this.buried = buried;
	}

	public int getBuriedAmount() {
		return buriedAmount;
	}


	public void setBuried(int index, String buriedAmount) {
		this.buried[index] = buriedAmount;
	}	
	
	public void setBuriedAmount(int buriedAmount) {
		this.buriedAmount = buriedAmount;
	}
	
	public void addSkipDelay(long time) {
	long currentTime = Utils.currentTimeMillis();
	if (currentTime > skipDelay) {
		skipDelay = time + currentTime;
	}
	}
	
	public long getSkipDelay() {
		return skipDelay;
	}

	public void setSkipDelay(long skipDelay) {
		this.skipDelay = skipDelay;
	}


}