package com.rs.game.minigames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.game.player.Player;

public final class AnniversaryEventMinigame {

	private static final List<Player> playersOn = Collections
			.synchronizedList(new ArrayList<Player>());
	public static int getPlayersCount() {
		return playersOn.size();
	}

	public static void addPlayer(Player player) {
		if (playersOn.contains(player)) {
			return;
		}
		playersOn.add(player);
	}

	public static void removePlayer(Player player) {
		playersOn.remove(player);
	}
	

	private AnniversaryEventMinigame() {

	}
}
