package com.rs.game.player.quest;

import java.io.Serializable;

public class Quest implements Serializable {
	
	private static final long serialVersionUID = -4485977731253271252L;
	
	protected int questStage;
	protected boolean isComplete;
	protected String questName;
	
	public Quest(String quest) {
		questStage = 0;
		isComplete = false;
		questName = quest;
	}
	
	public boolean isStarted() {
		if (questStage == 0)
			return false;
		return true;
	}
	
	public boolean isComplete() {
		return isComplete;
	}
	
	public void setComplete() {
		isComplete = true;
	}
	
	public int getQuestStage() {
		return questStage;
	}
	
	public void setQuestStage(int stage) {
		questStage = stage;
	}
	
	public String getName() {
		return questName;
	}
	
	
}