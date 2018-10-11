package com.rs.game.player.content.custom;

import java.util.Calendar;

public class DoubleXpManager {

	public static int dayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static boolean isWeekend() {

		return dayOfWeek() == 1 ? true: 
			   dayOfWeek() == 6 ? true:
			   dayOfWeek() == 7 ? true: false;
	}
	
}
