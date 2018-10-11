package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public class BossSlayerTask implements Serializable {

	private static final long serialVersionUID = -3888979679549716755L;
	
	public enum BossTask {
		BOSSTASKS(12379, new Object[][] { 
			/* Bandos */	{ 6260, 10, 30, 1000.0 },
			/* Saradomin */	{ 6247, 10, 30, 1000.0 },
			/* Zamorak*/	{ 6203, 10, 30, 1000.0 },
			/* Armadyl*/	{ 6222, 10, 30, 1000.0 },
			/*Fatal*/	    { 15003, 10, 25, 1250.0 },
			/*Something*/	{ 15002, 10, 25, 1250.0 },
			/*Copyright*/	{ 1900, 10, 25, 1250.0 },
			/*Seasinger*/	{ 15009, 10, 25, 1250.0 },
			/*DL Ninja*/	{ 15006, 10, 25, 1250.0 },
			/*Maximum Gradum*/	{ 14260, 15, 35, 150.0 },
			/*Eradicator*/	{ 11872, 5, 15, 1150.0 },
			/*Blink*/	{ 12878, 5, 15, 1100.0 },
			/*Fear*/	{ 15172, 3, 12, 1000.0 },
			/*Necrolord*/	{ 11751, 7, 14, 1000.0 },
			/*Corp*/	{ 8133, 1, 2, 4500.0 },
			/*Wildywyrm*/	{ 3334, 1, 5, 800.0 },
			/*AOD*/	{ 8596, 15, 30, 300.0 },
			/*QBD*/	{ 15454, 1, 1, 4800.0 },
			/*STQ*/	{ 3847, 15, 40, 500.0 },
			/*Hati*/	{ 13460, 5, 10, 1000.0 }, });
		
		private int id;
		private Object[][] data;
		private BossTask(int id, Object[][] data) {
			this.id = id;
			this.data = data;
		}
		public static BossTask forId(int id) {
			for (BossTask master : BossTask.values()) {
				if (master.id == id) {
					return master;
				}
			}
			return null;
		}		
		
	}
	
	private BossTask task;
	private int taskId;
	private int taskAmount;
	private int amountKilled;
	private int npcId;
	private boolean tripled = false;
	
	public BossSlayerTask(BossTask task, int taskId, int taskAmount, int npcId) {
		this.task = task;
		this.taskId = taskId;
		this.taskAmount = taskAmount;
		this.npcId = npcId;
	}	
	
	public boolean isTripled() {
		return tripled;
	}
	
	public int getNPCId() {
		return npcId;
	}
	
	public String getName() {
        switch (npcId) {
        case 6260:
        	return "Bandos Bosses";
        case 6247:
        	return "Saradomin Bosses";
        case 6203:
        	return "Zamorak Bosses";
        case 6222:
        	return "Armadyl Bosses";
        case 15003:
        	return "Fatal Resort Bosses";
        case 15002:
        	return "Something Bosses";
        case 1900:
        	return "Copyright Bosses";
        case 15009:
        	return "Seasinger Bosses";
        case 15006:
        	return "Deathlotus Ninja Bosses";
        case 14260:
        	return "Maximum Gradum Bosses";
        case 11872:
        	return "Eradicator Bosses";
        case 12878:
        	return "Blink Bosses";
        case 15172:
        	return "Fear Bosses";
        case 11751:
        	return "Necrolord Bosses";
        case 8133:
        	return "Corporeal Beast Bosses";
        case 3334:
        	return "Wildywyrm Bosses";
        case 8596:
        	return "Avatar of Destruction Bosses";
        case 3847:
        	return "Sea Troll Queen Bosses";
        case 13460:
        	return "Hati Bosses";
        case 15454:
        	return "Queen Black Dragons";
        }
        return null;
	}

	public static BossSlayerTask random(Player player, BossTask master) {
		BossSlayerTask task = null;
		while (true) {
			int random = Utils.random(master.data.length - 1);
			int minimum = (Integer) master.data[random][1];
			int maximum = (Integer) master.data[random][2];
			int npcId = (Integer) master.data[random][0];
			if (task == null) {
				task = new BossSlayerTask(master, random, Utils.random(minimum,
						maximum), npcId);
				player.setBossTask(task);
			}
			break;
		}
		return task;
	}

	public int getTaskId() {
		return taskId;
	}

	public int getTaskAmount() {
		return taskAmount;
	}
	
	public int tripleTaskAmount() {
		taskAmount = taskAmount * 3;
		tripled = true;
		return taskAmount;
	}
	
	public void decreaseAmount() {
		taskAmount--;
	}

	public int getXPAmount() {
		Object obj = task.data[taskId][3];
		if (obj instanceof Double) {
			return (int) Math.round((Double) obj);
		}
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		return 0;
	}

	public BossTask getBossTask() {
		return task;
	}

	/**
	 * @return the amountKilled
	 */
	public int getAmountKilled() {
		return amountKilled;
	}

	/**
	 * @param amountKilled
	 *            the amountKilled to set
	 */
	public void setAmountKilled(int amountKilled) {
		this.amountKilled = amountKilled;
	}

	public void onMonsterDeath(Player player, NPC n) {
		player.getSkills().addXp(Skills.SLAYER, player.getTask().getXPAmount());
		player.getTask().decreaseAmount();

		player.getTask()
				.setAmountKilled(player.getTask().getAmountKilled() + 1);
		player.getPackets().sendGameMessage(
				"You need to defeat " + player.getTask().getTaskAmount() + " "
						+ player.getTask().getName().toLowerCase()
						+ " to complete your task.");
		if (player.getTask().getTaskAmount() < 1) {
			player.setTask(null);
			if (player.getEquipment().getRingId() == 13281) {
				player.setSlayerPoints(player.getSlayerPoints() + 40);
				player.getPackets()
						.sendGameMessage(
								"You have finished your slayer task, talk to Kuradal for a new task.");
				player.getPackets().sendGameMessage(
						"Kuradal rewarded you 40 slayer points! You now have "
								+ player.getSlayerPoints() + " slayer points.");
			} else {
				player.setSlayerPoints(player.getSlayerPoints() + 20);
				player.getPackets()
						.sendGameMessage(
								"You have finished your slayer task, talk to Kuradal for a new task.");
				player.getPackets().sendGameMessage(
						"Kuradal rewarded you 20 slayer points! You now have "
								+ player.getSlayerPoints() + " slayer points.");
			}
		}
	}	
	
}