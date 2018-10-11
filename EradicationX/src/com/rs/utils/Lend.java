package com.rs.utils;

import java.io.Serializable;


public class Lend implements Serializable {

	private static final long serialVersionUID = -1131979864451286325L;
	private String lender;
	private String lendee;
	private int Rank;
	private long timeTill;

	public Lend(String lender, String lendee, int Rank, long timeTill) {
		this.lender = lender;
		this.lendee = lendee;
		this.Rank = Rank;
		this.timeTill = timeTill;
	}

	public String getLender() {
		return lender;
	}

	public String getLendee() {
		return lendee;
	}

	public int getItem() {
		return Rank;
	}

	public long getTime() {
		return timeTill;
	}

}
