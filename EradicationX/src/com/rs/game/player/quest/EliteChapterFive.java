package com.rs.game.player.quest;

import com.rs.game.player.Player;


public class EliteChapterFive extends Quest {

	private static final long serialVersionUID = 7743402443316193822L;
	
	public EliteChapterFive() {
		super("The Elite: Chapter 5");
	}

	public void refresh(Player player) {
	}
	
	public void setComplete(Player player) {
		isComplete = true;
	}

	public boolean meetsRequirements(Player player) {
		return false;
	}

}