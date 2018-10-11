package com.rs.game.player.quest;

import com.rs.game.player.Player;


public class EliteChapterFour extends Quest {

	private static final long serialVersionUID = 3185982443316193822L;
	
	public EliteChapterFour() {
		super("The Elite: Chapter 4");
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