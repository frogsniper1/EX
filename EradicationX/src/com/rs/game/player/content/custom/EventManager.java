package com.rs.game.player.content.custom;

import com.rs.game.WorldTile;

public class EventManager {

	public static int eventtime;
	public static int eventlocationx;
	public static int eventlocationy;
	public static int eventlocationz;
	public static WorldTile EVENT_LOCATION;
	
	public static void setEventTime(int time) {
		if (time == 1) {
			eventtime = 1;
		} else if (time == 2) {
			eventtime = 0;
		}
	}	
	
	public static void Location(int x, int  y, int  z) {	
		EVENT_LOCATION = new WorldTile(x, y, z);
	}
	
	
	public static boolean isEventTime() {
		if (eventtime == 1) {
			return true;
		} else {
		return false;
	}
	}
}
