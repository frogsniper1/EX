package com.rs.utils;

import java.util.Calendar;
import java.util.Locale;

public class RaffleManager {

	
	public static String Day() {
		Calendar cal = Calendar.getInstance();
		return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
	}
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
	public static boolean isDrawingTime() {
		if (Day().equalsIgnoreCase("mon") && hourOfDay() == 00 && minute() == 00 && second() == 00) 
			return true;
		return false;
	}
}
	