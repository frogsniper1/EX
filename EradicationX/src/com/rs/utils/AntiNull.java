package com.rs.utils;

import java.util.concurrent.CopyOnWriteArrayList;


public class AntiNull  {

	public static CopyOnWriteArrayList<NameTime> list;
	
	public AntiNull() {
		list = new CopyOnWriteArrayList<NameTime>();
	}

	public static void add(NameTime names) {
		if (list == null)
			list = new CopyOnWriteArrayList<NameTime>();
		list.add(names);
	}

	public static void remove(NameTime names) {
		list.remove(names);
	}
	
	public static void selfDestructIn25Secs(NameTime name) {
		add(name);
	}
	
	public static boolean containsName(String displayname) {
		if (list != null ) {
		for (NameTime s : list) {
			if (s.getName().equalsIgnoreCase(displayname)) {
				if (s.getTime() > (Utils.currentTimeMillis() - 24000))
					return true;
				else {
					list.remove(s);
					return false;
				}
			}
		}
		}
		return false;
	}	
}

	
