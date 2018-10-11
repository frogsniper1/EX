package com.rs.game.player.controlers;

import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.minigames.AnniversaryEventMinigame;

public class AnniversaryEventControler extends Controler {

	@Override
	public void start() {
		AnniversaryEventMinigame.addPlayer(player);
		if (AnniversaryEventMinigame.getPlayersCount() > 0 && AnniversaryEventMinigame.getPlayersCount() <= 13)
			player.setNextWorldTile(new WorldTile(3215 + AnniversaryEventMinigame.getPlayersCount(), 2806, 0));
		else if (AnniversaryEventMinigame.getPlayersCount() >= 14 && AnniversaryEventMinigame.getPlayersCount() <= 27)
				player.setNextWorldTile(new WorldTile(3215 + AnniversaryEventMinigame.getPlayersCount(), 2803, 0));
		else if (AnniversaryEventMinigame.getPlayersCount() >= 28 && AnniversaryEventMinigame.getPlayersCount() <= 41)
			player.setNextWorldTile(new WorldTile(3215 + AnniversaryEventMinigame.getPlayersCount(), 2801, 0));
		else if (AnniversaryEventMinigame.getPlayersCount() >= 42 && AnniversaryEventMinigame.getPlayersCount() <= 55)
			player.setNextWorldTile(new WorldTile(3215 + AnniversaryEventMinigame.getPlayersCount(), 2799, 0));
		else if (AnniversaryEventMinigame.getPlayersCount() >= 56 && AnniversaryEventMinigame.getPlayersCount() <= 69)
			player.setNextWorldTile(new WorldTile(3215 + AnniversaryEventMinigame.getPlayersCount(), 2797, 0));
		else if (AnniversaryEventMinigame.getPlayersCount() >= 70 && AnniversaryEventMinigame.getPlayersCount() <= 83)
			player.setNextWorldTile(new WorldTile(3215 + AnniversaryEventMinigame.getPlayersCount(), 2795, 0));
		player.stopAll();
		player.addFreezeDelay(2147483647, true);
		sendInterfaces();
	}

	@Override
	public boolean logout() {
		AnniversaryEventMinigame.removePlayer(player);
		removeControler();
		return false; 
	}

	@Override
	public boolean login() {
		AnniversaryEventMinigame.addPlayer(player);
		sendInterfaces();
		return false; 
	}

	@Override
	public void sendInterfaces() {
	}

	@Override
	public boolean sendDeath() {
		remove();
		removeControler();
		return false;
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		if (object.getId() == 39478) 
			player.getDialogueManager().startDialogue("SimpleMessage", "You can't use this at the moment.");
		return true;
	}	

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To end the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To end the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		player.sm("You can't teleport out of the instance. To end the instance, click the portal.");
		player.getInterfaceManager().closeChatBoxInterface();
		return false;
	}
	@Override
	public void forceClose() {
		remove();
	}

	public void remove() {
		AnniversaryEventMinigame.removePlayer(player);
	}
}
