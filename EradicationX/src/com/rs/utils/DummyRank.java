package com.rs.utils;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import com.rs.game.player.Player;

public final class DummyRank implements Serializable {

	private static final long serialVersionUID = 5403480615483552509L;

	private String username;
	private int damage;

	private static DummyRank[] ranks;

	private static final String PATH = "data/dummyRanks.ser";

	public DummyRank(Player player) {
		this.username = player.getUsername();
		this.damage = player.getDummyDamage();
	}

	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				ranks = (DummyRank[]) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		ranks = new DummyRank[300];
	}

	public static final void save() {
		try {
			SerializableFilesManager.storeSerializableClass(ranks, new File(
					PATH));
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static void showRanks(Player player) {
		for (int i = 10; i < 310; i++)
			player.getPackets().sendIComponentText(275, i, "");
		for (int i = 0; i < 300; i++) {
			if (ranks[i] == null)
				break;
			String text;
			if (i >= 0 && i <= 2)
				text = "<img=4><col=ff0000><shad=000000>";
			else if (i <= 9)
				text = "<col=ff0000>";
			else if (i <= 49)
				text = "<col=38610B>";
			else
				text = "<col=000000>";
			player.getPackets()
					.sendIComponentText(
							275,
							i + 11,
							text
									+ "Top "
									+ (i + 1)
									+ " - "
									+ Utils.formatPlayerNameForDisplay(ranks[i].username)
									+ " - Damage Dealt: [" + Utils.formatNumber(ranks[i].damage) +"]");
		}
		player.getPackets().sendIComponentText(275, 1,
				"Damage Leaderboards");
		player.getInterfaceManager().sendInterface(275);
	}

	public static void sort() {
		Arrays.sort(ranks, new Comparator<DummyRank>() {
			@Override
			public int compare(DummyRank arg0, DummyRank arg1) {
				if (arg0 == null)
					return 1;
				if (arg1 == null)
					return -1;
				if (arg0.damage < arg1.damage)
					return 1;
				else if (arg0.damage > arg1.damage)
					return -1;
				else
					return 0;
			}

		});
	}

	public static void checkRank(Player player) {
		int kills = player.getDummyDamage();
		for (int i = 0; i < ranks.length; i++) {
			DummyRank rank = ranks[i];
			if (rank == null)
				break;
			if (rank.username.equalsIgnoreCase(player.getUsername())) {
				ranks[i] = new DummyRank(player);
				sort();
				return;
			}
		}
		for (int i = 0; i < ranks.length; i++) {
			DummyRank rank = ranks[i];
			if (rank == null) {
				ranks[i] = new DummyRank(player);
				sort();
				return;
			}
		}
		for (int i = 0; i < ranks.length; i++) {
			if (ranks[i].damage < kills) {
				ranks[i] = new DummyRank(player);
				sort();
				return;
			}
		}
	}

}
