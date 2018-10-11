package com.rs.game.player;

public class PublicChatMessage extends ChatMessage {

	private String message;
	private int effects;

	public PublicChatMessage(String message, int effects) {
		super(message);
		this.effects = effects;
	}

	public int getEffects() {
		return effects;
	}

	public String getMessage() {
		return message;
	}

}
