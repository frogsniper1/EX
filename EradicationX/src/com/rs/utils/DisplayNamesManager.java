package com.rs.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;

public class DisplayNamesManager implements Serializable {

	private static final long serialVersionUID = 2496224027921198991L;
	public static CopyOnWriteArrayList<DisplayNamesKeep> list;

	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File("data/savedDisplays.ser");
		if (file.exists()) {
			try {
				list = (CopyOnWriteArrayList<DisplayNamesKeep>) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list = new CopyOnWriteArrayList<DisplayNamesKeep>();
	}

	public static void add(DisplayNamesKeep names) {
		list.add(names);
		save();
	}

	public static void remove(DisplayNamesKeep names) {
		list.remove(names);
		save();
	}
	
	public static void addNames(Player player) {
		DisplayNamesKeep names = null;
		names = new DisplayNamesKeep(player.getUsername(), player.getDisplayName());
		add(names);
	}
	
	public static void removeName(Player player) {
		if (player.getDisplayName() != null) {
			if (findNamesByPlayer(player) != null)
				remove(findNamesByPlayer(player));
		}
	}
	
	public static String findUsernamebyDisplayName(String displayname) {
		String username = null;
		DisplayNamesKeep names = findNamesByDisplayName(displayname);
		if (names != null) 
			username = names.getUsername();
		return username;
	}
	
	public static DisplayNamesKeep findNamesByPlayer(Player player) {
		DisplayNamesKeep result = null;
		for (DisplayNamesKeep names : list) {
			if (player.getDisplayName().equals(names.getDisplayName()))
				result = names;
			else if (player.getUsername().equals(names.getUsername()))
				result = names;
		}
		return result;
	}
	
	public static DisplayNamesKeep findNamesByDisplayName(String displayname) {
		DisplayNamesKeep result = null;
		for (DisplayNamesKeep names : list) {
			if (displayname.toLowerCase().replaceAll("_", " ").equals(names.getDisplayName().toLowerCase().replaceAll("_", " "))) { 
				if (!SerializableFilesManager.containsPlayer(displayname))
					result = names;
			}
		}
		return result;		
	}
	
	public static DisplayNamesKeep findNamesByUsernname(String username) {
		DisplayNamesKeep result = null;
		for (DisplayNamesKeep names : list) {
			if (username.equals(names.getUsername()))
				result = names;
		}
		return result;		
	}
	
	
	public static void save() {
		try {
			SerializableFilesManager.storeSerializableClass(list, new File(
					"data/savedDisplays.ser"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

	
