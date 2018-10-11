package com.rs.game.player.content.custom;

import java.util.Calendar;

public class DoubleVoteManager {

	public static int Day() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
		
	public static boolean isFirstDayofMonth() {
		if (Day() == 2 || Day() == 1) {
			return true;
		} else {
		return false;
	}
	}	
}