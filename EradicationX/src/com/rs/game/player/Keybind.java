package com.rs.game.player;

import java.io.Serializable;

// A single keybind - Fatal

public class Keybind implements Serializable {
	
	private static final long serialVersionUID = 541426055118406631L;
	
	public static final int F6 = 6, F7 = 7, F8 = 8, F9 = 9, F10 = 10, F11 = 11, F12 = 12, INSERT = 100, DELETE = 101, HOME = 102, END = 103;
	private String name;
	private int keyvalue;
	private int keyfunctionvalue;
	
	public Keybind() {
		setName("No command set");
		setKeyvalue(0);
		this.setKeyFunctionValue(0);
	}
	
	public Keybind(int keyvalue) {
		setName("No command set");
		this.keyvalue = keyvalue;
		this.setKeyFunctionValue(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyvalue() {
		return keyvalue;
	}

	public void setKeyvalue(int keyvalue) {
		this.keyvalue = keyvalue;
	}
	
	
	public static String getKeyName(int value) {
		switch (value) {
			case F6:
				return "F6";
			case F7:
				return "F7";
			case F8:
				return "F8";
			case F9:
				return "F9";
			case F10:
				return "F10";
			case F11:
				return "F11";
			case F12:
				return "F12";
			case HOME:
				return "Home";
			case INSERT:
				return "Insert";
			case DELETE:
				return "Delete";
			case END:
				return "End";
			default:
				return "Unknown value";
		}
	}
	public void setFunction(String name, int function) {
		setKeyFunctionValue(function);
		setName(name);
	}

	public int getKeyFunctionValue() {
		return keyfunctionvalue;
	}

	public void setKeyFunctionValue(int keyfunctionvalue) {
		this.keyfunctionvalue = keyfunctionvalue;
	}
	
	
}