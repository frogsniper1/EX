package com.rs.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.rs.game.player.Player;
import com.rs.game.player.content.FriendChatsManager;

public final class DisplayNames {

	private static ArrayList<String> cachedNames;

	private static final String PATH = "data/displayNames.ser";

	private DisplayNames() {

	}

	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File(PATH);
		if (file.exists())
			try {
				cachedNames = (ArrayList<String>) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Throwable e) {
				Logger.handle(e);
			}
		cachedNames = new ArrayList<String>();
	}

	public static void save() {
		try {
			SerializableFilesManager.storeSerializableClass(cachedNames,
					new File(PATH));
		} catch (IOException e) {
			Logger.handle(e);
		}
	}

	public static boolean setDisplayName(Player player, String displayName) {
		displayName = displayName.replaceAll("_", " ");
		synchronized (cachedNames) {
			ArrayList<String> names = new ArrayList<String>();
			for (String s: cachedNames) 
				names.add(s.toLowerCase());
			if ((SerializableFilesManager.containsPlayer(Utils
					.formatPlayerNameForProtocol(displayName))
					|| names.contains(displayName.toLowerCase())|| cachedNames.contains(displayName) || !cachedNames
						.add(displayName)))
				return false;
			if (player.hasDisplayName()) {
				cachedNames.remove(player.getDisplayName());
				DisplayNamesManager.removeName(player);
			}
		}
		player.setDisplayName(displayName);
		DisplayNamesManager.addNames(player);
		FriendChatsManager.refreshChat(player);
		player.getAppearence().generateAppearenceData();
		return true;
	}
	
	public static boolean hasMultiple(Player player) {
		synchronized (cachedNames) {
			ArrayList<String> names = new ArrayList<String>();
			for (String s: cachedNames) {
				if (names.contains(s.toLowerCase()) && player.getDisplayName().toLowerCase().equals(s.toLowerCase()))
					return true;
				names.add(s.toLowerCase());
			}
		}
		return false;
	}

	public static boolean removeDisplayName(Player player) {
		if (!player.hasDisplayName())
			return false;
		synchronized (cachedNames) {
			cachedNames.remove(player.getDisplayName());
		}
		DisplayNamesManager.removeName(player);
		player.setDisplayName(null);
		player.getAppearence().generateAppearenceData();
		return true;
	}
}
