package com.rs.utils;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import com.rs.game.player.Player;

public final class VotingBoard implements Serializable {

	private static final long serialVersionUID = 5403480618483552509L;

	private String username;
	private int votes;

	private static VotingBoard[] ranks;

	private static final String PATH = "data/votingBoard.ser";

	public VotingBoard(Player player) {
		this.username = player.getUsername();
		this.votes = player.getVote();
	}

	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				ranks = (VotingBoard[]) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		ranks = new VotingBoard[300];
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
				text = "<col=ff9900>";
			else if (i <= 9)
				text = "<col=ff0000>";
			else if (i <= 49)
				text = "<col=38610B>";
			else
				text = "<col=000000>";
			player.getPackets()
					.sendIComponentText(
							275,
							i + 10,
							text
									+ "Top "
									+ (i + 1)
									+ " - "
									+ Utils.formatPlayerNameForDisplay(ranks[i].username)
									+ " - votes: " + ranks[i].votes);
		}
		player.getPackets().sendIComponentText(275, 1,
				"Votes Leaderboard");
		player.getInterfaceManager().sendInterface(275);
	}

	public static void sort() {
		Arrays.sort(ranks, new Comparator<VotingBoard>() {
			@Override
			public int compare(VotingBoard arg0, VotingBoard arg1) {
				if (arg0 == null)
					return 1;
				if (arg1 == null)
					return -1;
				if (arg0.votes < arg1.votes)
					return 1;
				else if (arg0.votes > arg1.votes)
					return -1;
				else
					return 0;
			}

		});
	}

	public static void checkRank(Player player) {
		int votes = player.getVote();
		for (int i = 0; i < ranks.length; i++) {
			VotingBoard rank = ranks[i];
			if (rank == null)
				break;
			if (rank.username.equalsIgnoreCase(player.getUsername())) {
				ranks[i] = new VotingBoard(player);
				sort();
				return;
			}
		}
		for (int i = 0; i < ranks.length; i++) {
			VotingBoard rank = ranks[i];
			if (rank == null) {
				ranks[i] = new VotingBoard(player);
				sort();
				return;
			}
		}
		for (int i = 0; i < ranks.length; i++) {
			if (ranks[i].votes < votes) {
				ranks[i] = new VotingBoard(player);
				sort();
				return;
			}
		}
	save();
	}
	

}
