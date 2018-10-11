package com.rs.game.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rs.game.player.quest.EliteChapterFive;
import com.rs.game.player.quest.EliteChapterFour;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.EliteChapterThree;
import com.rs.game.player.quest.EliteChapterTwo;
import com.rs.game.player.quest.QNames;
import com.rs.game.player.quest.Quest;

public final class EXQuestManager implements Serializable {
	
	private static final long serialVersionUID = -4485932531253271252L;
	
	private transient Player player;
	private List<Quest> quests;

	public EXQuestManager() {
		quests = new ArrayList<Quest>();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void sendQuestInterface() {	
		player.getInterfaceManager().sendInterface(3014);
		player.sit(3014, 3, "1    The Elite: Chapter I");
		player.sit(3014, 5, "2    The Elite: Chapter II");
		player.sit(3014, 7, "3    The Elite: Chapter III");
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void updateQuestList() {
		if (getQuest(QNames.ELITE_CHAPTER_I) == null)
			quests.add(new EliteChapterOne());
		if (getQuest(QNames.ELITE_CHAPTER_II) == null)
			quests.add(new EliteChapterTwo());
		if (getQuest(QNames.ELITE_CHAPTER_III) == null)
			quests.add(new EliteChapterThree());
		if (getQuest(QNames.ELITE_CHAPTER_IV) == null)
			quests.add(new EliteChapterFour());
		if (getQuest(QNames.ELITE_CHAPTER_V) == null)
			quests.add(new EliteChapterFive());
	}	
	
	public void setQuestStage(String name, int stage) {
		for (Quest q : quests) {
			if (q.getName().equalsIgnoreCase(name)) {
				q.setQuestStage(stage);
				return;
			}
		}
	}
	
	public boolean isComplete(String name) {
		for (Quest q : quests) {
			if (q.getName().equalsIgnoreCase(name))
				return q.isComplete();
		}
		return false;	
	}
	
	public int getQuestStage(String name) {
		for (Quest q : quests) {
			if (q.getName().equalsIgnoreCase(name))
				return q.getQuestStage();
		}
		return 0;		
	}
	
	public Quest getQuest(String name) {
		for (Quest q : quests) {
			if (q.getName().equalsIgnoreCase(name))
				return q;
		}
		return null;
	} 
 
}
