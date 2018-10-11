package com.rs.game.player.quest;

import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class EliteChapterThree extends Quest {

	private static final long serialVersionUID = 2533402443316193822L;
	
	private long skipDelay;
	
	public EliteChapterThree() {
		super("The Elite: Chapter 3");
	}

	public void refresh(Player player) {
	}
	
	public void setComplete(Player player) {
		isComplete = true;
		player.getInterfaceManager().sendInterface(1236);
		player.sit(1236, 20, "The Elite: Chapter III");
		player.sit(1236, 21, "Quest complete! You must now go on and complete what Cyndrith's Enchantments require. ");
	}

	public boolean meetsRequirements(Player player) {
		if (player.getEliteChapterII().isComplete())
			return true;
		return false;
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