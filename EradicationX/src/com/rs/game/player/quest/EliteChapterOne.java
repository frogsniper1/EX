package com.rs.game.player.quest;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class EliteChapterOne extends Quest {

	private static final long serialVersionUID = 2519202884313083822L;
	
	private boolean didBones;
	private boolean didEmptyScroll;	
	private boolean bottleWater;
	private boolean inkFeather;
	private boolean bottleAsh;
	private boolean finishedList;
	private int pinCombination;
	
	public EliteChapterOne() {
		super("The Elite: Chapter 1");
		setPinCombination(Utils.random(1, 5));
		setDidBones(false);
		setDidEmptyScroll(false);
	}

	
	public void showListProgress(Player player) {
		refresh(player);
		if (finishedList) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You look at the list, and everything is checked off. As you're about to put it away, it bursts into flames.");
			player.setQuestStage(QNames.ELITE_CHAPTER_I, 8);
			player.getInventory().deleteItem(6196, 1);
			return;
		}
		player.getInterfaceManager().sendInterface(275);
		for (int i = 0; i < 300; i++) {
		player.getPackets().sendIComponentText(275, i, "");
		}
		player.sit(275, 1, "Cyndrith's List");
		player.sit(275, 11, "<col="+(didEmptyScroll ? Settings.GREEN : Settings.RED)+"> Obtain an empty scroll & empty ink bottle from my chest");
		player.sit(275, 12, "<col="+(didBones ? Settings.GREEN : Settings.RED)+"> Crush the bones of Fatal, Copyright, and Something into ashes");
		player.sit(275, 13, "<col="+(bottleWater ? Settings.GREEN : Settings.RED)+"> Fill the empty ink bottle with water");
		player.sit(275, 14, "<col="+(bottleAsh ? Settings.GREEN : Settings.RED)+"> Fill the ink bottle with the Ashes of the Trio");
		player.sit(275, 15, "<col="+(inkFeather ? Settings.GREEN : Settings.RED)+"> Dip a feather into the ink bottle");
	}


	public boolean isDidBones() {
		return didBones;
	}


	public void setDidBones(boolean didBones) {
		this.didBones = didBones;
	}


	public boolean isDidEmptyScroll() {
		return didEmptyScroll;
	}


	public void setDidEmptyScroll(boolean didEmptyScroll) {
		this.didEmptyScroll = didEmptyScroll;
	}


	public void refresh(Player player) {
		if (bottleWater && bottleAsh && didBones && didEmptyScroll && inkFeather)
			finishedList = true;
		else 
			finishedList = false;
		if (player.getInventory().containsItem(13328, 1) || player.getBank().containsItem(13328))
			inkFeather = true;
		else
			inkFeather = false;	
		if (player.getInventory().containsItem(27741, 1) || player.getBank().containsItem(27741) ||
				(player.getInventory().containsItem(27742, 1) || player.getBank().containsItem(27742))||
				(player.getInventory().containsItem(13328, 1) || player.getBank().containsItem(13328)))
			bottleWater = true;
		else
			bottleWater = false;
		if (player.getInventory().containsItem(27742, 1) || player.getBank().containsItem(27742)||
				(player.getInventory().containsItem(13328, 1) || player.getBank().containsItem(13328)))
			bottleAsh = true;
		else
			bottleAsh = false;	
		if (pinCombination == 0)
			setPinCombination(Utils.random(1, 5));
		if (player.getInventory().containsItem(27738, 1) || player.getBank().containsItem(27738) || 
				player.getInventory().containsItem(27742, 1) || player.getBank().containsItem(27742)||
				(player.getInventory().containsItem(13328, 1) || player.getBank().containsItem(13328)))
			didBones = true;
		else
			didBones = false;
		if ((player.getInventory().containsItem(27739, 1) || player.getBank().containsItem(27739)) &&
				(player.getInventory().containsItem(27740, 1) || player.getBank().containsItem(27740)) || 
				(player.getInventory().containsItem(27741, 1) || player.getBank().containsItem(27741)) ||
				(player.getInventory().containsItem(27742, 1) || player.getBank().containsItem(27742)) ||
				(player.getInventory().containsItem(13328, 1) || player.getBank().containsItem(13328)))
			didEmptyScroll = true;
		else
			didEmptyScroll = false;
	}


	public int getPinCombination() {
		return pinCombination;
	}


	public void setPinCombination(int pinCombination) {
		this.pinCombination = pinCombination;
	}


	public boolean isBottleWater() {
		return bottleWater;
	}


	public void setBottleWater(boolean bottleWater) {
		this.bottleWater = bottleWater;
	}


	public boolean isBottleAsh() {
		return bottleAsh;
	}


	public void setBottleAsh(boolean bottleAsh) {
		this.bottleAsh = bottleAsh;
	}


	public boolean isFinishedList() {
		return finishedList;
	}

	public void setComplete(Player player) {
		isComplete = true;
		player.getInterfaceManager().sendInterface(1236);
		player.sit(1236, 20, "The Elite: Chapter I");
		player.sit(1236, 21, "Quest complete! You can use your scroll of enchantment to turn a "
				+ "set of Obsidian Boots elite. If you ever need another scroll, you can use Cyndrith's chest to buy another one.");
	}

	public void setFinishedList(boolean finishedList) {
		this.finishedList = finishedList;
	}


	public boolean isInkFeather() {
		return inkFeather;
	}


	public void setInkFeather(boolean inkFeather) {
		this.inkFeather = inkFeather;
	}



	
}