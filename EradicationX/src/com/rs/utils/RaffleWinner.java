package com.rs.utils;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.rs.game.player.Player;

public final class RaffleWinner implements Serializable {

	private static final long serialVersionUID = 5403480618483552509L;

	private String username;
	private String won;
	private int amount;
	private long time;

	private static RaffleWinner[] ranks;

	private static final String PATH = "data/RaffleWinners.ser";

	public RaffleWinner(String username, String won, int amount) {
		this.username = username;
		this.time = Utils.currentTimeMillis();
		this.won = won;
		this.amount = amount;
	}

	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				ranks = (RaffleWinner[]) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		ranks = new RaffleWinner[300];
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
		Date date;
		SimpleDateFormat format = new SimpleDateFormat();
        format = new SimpleDateFormat("MM/dd/yyyy");
        String DateToStr;

		for (int i = 10; i < 310; i++)
			player.getPackets().sendIComponentText(275, i, "");
		for (int i = 0; i < 300; i++) {
			if (ranks[i] == null)
				break;
			date = new Date(ranks[i].time);
	        DateToStr = format.format(date);
			String text =  "<col=000000>";
			player.getPackets().sendIComponentText(275, i + 11,
							text + "["+DateToStr+"] - "
									+ Utils.formatPlayerNameForDisplay(ranks[i].username)
									+ " won: " + ranks[i].amount + " " + ranks[i].won);
		}
		player.getPackets().sendIComponentText(275, 1,
				"Previous Raffle Winners");
		player.getInterfaceManager().sendInterface(275);
	}

	public static void sort() {
		Arrays.sort(ranks, new Comparator<RaffleWinner>() {
			@Override
			public int compare(RaffleWinner arg0, RaffleWinner arg1) {
				if (arg0 == null)
					return 1;
				if (arg1 == null)
					return -1;
				if (arg0.time < arg1.time)
					return 1;
				else if (arg0.time > arg1.time)
					return -1;
				else
					return 0;
			}

		});
	}

	public static void addWinner(String username, String won, int amount) {
		for (int i = 0; i < 300; i++) {
			if (ranks[i] == null) {
				ranks[i] = new RaffleWinner(username, won, amount);
				sort();
				save();
				break;
			}
		}
	}

}
