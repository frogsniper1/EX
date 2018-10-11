package com.rs.game.player.content;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;
import com.rs.game.npc.*;

public class SlayerTask implements Serializable {

	private static final long serialVersionUID = -3885979679549716755L;

	public enum Master {
		KURADAL(9085, new Object[][] {
				{ "Crawling hand", 1, 50, 100, 100.0 },
				{ "Abyssal demon", 85, 25, 130, 285.0 },
				{ "Nechryael", 80, 45, 85, 112.0 },
				{ "Aberrant spectre", 60, 30, 100, 115.0 },
				{ "Infernal mage", 45, 25, 60, 150.0 },
				{ "Ganodermic beast", 95, 50, 120, 330 },
				{ "Gargoyle", 75, 150, 200, 200.0 },
				{ "Jelly", 1, 25, 60, 100.0 },
				{ "Dark beast", 90, 40, 75, 310.0 },
				{ "Bloodveld", 50, 30, 100, 145.0 } });

		private int id;
		private Object[][] data;

		private Master(int id, Object[][] data) {
			this.id = id;
			this.data = data;
		}

		public static Master forId(int id) {
			for (Master master : Master.values()) {
				if (master.id == id) {
					return master;
				}
			}
			return null;
		}

		public int getId() {
			return id;
		}

	}

	private Master master;
	private int taskId;
	private int taskAmount;
	private int amountKilled;

	public SlayerTask(Master master, int taskId, int taskAmount) {
		this.master = master;
		this.taskId = taskId;
		this.taskAmount = taskAmount;
	}

	public String getName() {
		return (String) master.data[taskId][0];
	}

	public static SlayerTask random(Player player, Master master) {
		SlayerTask task = null;
		while (true) {
			int random = Utils.random(master.data.length - 1);
			int requiredLevel = (Integer) master.data[random][1];
			if (player.getSkills().getLevel(Skills.SLAYER) < requiredLevel) {
				continue;
			}
			int minimum = (Integer) master.data[random][2];
			int maximum = (Integer) master.data[random][3];
			if (task == null) {
				task = new SlayerTask(master, random, Utils.random(minimum,
						maximum));
				player.setTask(task);
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

	public void decreaseAmount() {
		taskAmount--;
	}

	public int getXPAmount() {
		Object obj = master.data[taskId][4];
		if (obj instanceof Double) {
			return (int) Math.round((Double) obj);
		}
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		return 0;
	}

	public Master getMaster() {
		return master;
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
		/*
		 * int[] checkpoints = new int[] { 2, 3, 4, 5, 10, 15, 25, 50, 75, 100,
		 * 125, 150 }; for (int i : checkpoints) { if
		 * (player.getTask().getAmountKilled() == i) {
		 * player.getPackets().sendGameMessage( "You're doing great, Only " +
		 * player.getTask().getTaskAmount() + " " +
		 * player.getTask().getName().toLowerCase() + " left to slay."); } }
		 */
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
