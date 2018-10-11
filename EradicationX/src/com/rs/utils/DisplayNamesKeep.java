package com.rs.utils;

import java.io.Serializable;


public class DisplayNamesKeep implements Serializable {

	private static final long serialVersionUID = -1131979823451286325L;
	private String displayname;
	private String username;

	public DisplayNamesKeep(String username, String displayname) {
		this.username = username;
		this.displayname = displayname;
	}

	public String getDisplayName() {
		return displayname;
	}

	public String getUsername() {
		return username;
	}

}
