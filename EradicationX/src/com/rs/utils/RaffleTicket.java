package com.rs.utils;

import java.io.Serializable;


public class RaffleTicket implements Serializable {

	private static final long serialVersionUID = -1131989864651286325L;
	private String username;
	private int entry, persentry;

	public RaffleTicket(String username, int entry, int persentry) {
		this.username = username;
		this.entry = entry;
		this.persentry = persentry;
	}

	public String getUsername() {
		return username;
	}
	
	public int getEntry() {
		return entry;
	}
	
	public void setEntry(int num) {
		entry = num;
	}
	
	public int getPersEntry() {
		return persentry;
	}
	
	public void setPersEntry(int num) {
		persentry = num;
	}	

}
