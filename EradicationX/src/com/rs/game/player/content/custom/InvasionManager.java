package com.rs.game.player.content.custom;

import java.util.Calendar;

public class InvasionManager {

	public static int hourOfDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	public static int minute() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MINUTE);
	}	
	public static int second() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.SECOND);
	}		
	public static boolean isInvasionTimeNight() {
		if (hourOfDay() == 12 && minute() == 00 && second() == 00) {
			return true;
		} else {
		return false;
	}
	}
	public static boolean isInvasionTimeNight2() {
		if (hourOfDay() == 18 && minute() == 00 && second() == 00) {
			return true;
		} else {
		return false;
	}
	}	
	public static boolean isInvasionTimeMorning() {
		if (hourOfDay() == 0 && minute() == 00 && second() == 00) {
			return true;
		} else {
		return false;
	}
	}
	public static boolean isInvasionTimeMorning2() {
		if (hourOfDay() == 6 && minute() == 00 && second() == 00) {
			return true;
		} else {
		return false;
	}
	}	
}