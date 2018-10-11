package com.rs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import com.rs.game.player.Player;
import com.rs.game.player.content.clans.Clan;

public class SerializableFilesManager {

	private static final String PATH = "data/playersaves/characters/";
	private static final String BACKUP_PATH = "data/playersaves/charactersBackup/";
	public static final String CLAN_PATH = "data/clans/";

	public synchronized static final boolean containsPlayer(String username) {
		return new File(PATH + username + ".p").exists();
	}

	public synchronized static Player loadPlayer(String username) {
		try {
			return (Player) loadSerializedFile(new File(PATH + username + ".p"));
		} catch (Throwable e) {
			Logger.handle(e);
		}
		try {
			Logger.log("SerializableFilesManager", "Recovering account: "
					+ username);
			return (Player) loadSerializedFile(new File(BACKUP_PATH + username
					+ ".p"));
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}
	
	public synchronized static int checkForStuff() {
		int count = 0;
		final File file = new File(PATH);
		Player p = null;
		for(final File child : file.listFiles()) {
		     try {
			 p = (Player) loadSerializedFile(child);
				if (p.starter == 0) {
					deleteSerializedFile(child);
					count++;
				}
			} catch (Throwable e) {
				continue;
			}
		}
		return count;
	}

	public static boolean createBackup(String username) {
		try {
			Utils.copyFile(new File(PATH + username + ".p"), new File(
					BACKUP_PATH + username + ".p"));
			return true;
		} catch (Throwable e) {
			Logger.handle(e);
			return false;
		}
	}

	public synchronized static void savePlayer(Player player) {
		try {
			storeSerializableClass(player, new File(PATH + player.getUsername()
					+ ".p"));
		} catch (ConcurrentModificationException e) {
			// happens because saving and logging out same time
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static final Object loadSerializedFile(File f) throws IOException,
			ClassNotFoundException {
		if (!f.exists())
			return null;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
		Object object = in.readObject();
		in.close();
		return object;
	}
	
	public static final void deleteSerializedFile(File f) throws IOException, ClassNotFoundException {
		if (!f.exists())
			return;
		System.out.println("Deleted account: " + f.getName());
		f.delete();
	}

	public static final void storeSerializableClass(Serializable o, File f)
			throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(o);
		out.close();
	}
	

	private SerializableFilesManager() {

	}
	
	public synchronized static boolean containsClan(String name) {
		return new File(CLAN_PATH + name + ".c").exists();
	}

	public synchronized static Clan loadClan(String name) {
		try {
			return (Clan) loadSerializedFile(new File(CLAN_PATH + name + ".c"));
		}
		catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}

	public synchronized static void saveClan(Clan clan) {
		try {
			storeSerializableClass(clan, new File(CLAN_PATH + clan.getClanName() + ".c"));
		}
		catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public synchronized static void deleteClan(Clan clan) {
		try {
			new File(CLAN_PATH + clan.getClanName() + ".c").delete();
		}
		catch (Throwable t) {
			Logger.handle(t);
		}
	}	

}
