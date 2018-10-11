package com.rs.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class LendAuction implements Serializable {
	
	private static final long serialVersionUID = 92333222133454123L;
	private String auctioner;
	private int ranktype;
	private int hours;
	private int ticketamount;
	private long time;
	
	public LendAuction(String auctioner, int ranktype, int hours, int ticketamount) {
		this.auctioner = auctioner;
		this.ranktype = ranktype;
		this.hours = hours;
		this.ticketamount = ticketamount;
		setTime(Utils.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
	}
	
	public String getAuctioner() {
		return auctioner;
	}
	public int getRankType() {
		return ranktype;
	}
	public int getTicketAmount() {
		return ticketamount;
	}
	public int getHours() {
		return hours;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}