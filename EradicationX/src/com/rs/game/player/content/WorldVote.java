package com.rs.game.player.content;

import java.io.File;
import java.io.Serializable;

import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class WorldVote implements Serializable {
	
	private static final long serialVersionUID = 5403233618483552509L;

	private int worldvotecount;
	private long time;
	
	private static WorldVote worldvote;

	private static final String PATH = "data/worldVote.ser";

	public WorldVote() {
		worldvotecount = 0;
		time = 0;
	}

	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				worldvote = (WorldVote) SerializableFilesManager.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		worldvote = new WorldVote();
	}

	
	public static final void save() {
		try {
			SerializableFilesManager.storeSerializableClass(worldvote, new File(
					PATH));
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}
	
	public static boolean isActive() {
		if (worldvote.time > (Utils.currentTimeMillis() - (1 * 60 * 60 * 1000)))
			return true;
		return false;
	}
	
	public static void startReward() {
		worldvote.worldvotecount = 0;
		worldvote.time = Utils.currentTimeMillis();
		save();
	}

	public static int getVotes() {
		return worldvote.worldvotecount;
	}

	public static void setVotes(int worldvotecount) {
		worldvote.worldvotecount = worldvotecount;
		save();
	}

	public static long getTime() {
		return worldvote.time;
	}

	public static void setTime(long time) {
		worldvote.time = time;
	}

}
