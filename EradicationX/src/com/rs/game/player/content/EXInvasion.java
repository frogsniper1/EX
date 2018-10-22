package com.rs.game.player.content;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class EXInvasion {
	
	/**
	 * The Player's Constructor
	 */
	Player player;
	
	public EXInvasion(Player player) {
		this.player = player;
	}
	
	/**
	 * Total Zombies Killed
	 */
	 
	public static short zombieskilled;

	/**
	 * Spawn the Daily Invasion
	 */
	 
	public static void spawnDailyInvasion() { 
		int[] xcolumn = {3978, 3976, 3974, 3972, 3964, 3962, 3960, 3958};
		for (int xcol : xcolumn) {
			for (int i = 4811; i < 4824; i++)
				spawnZombie(xcol, i);
		}
		World.spawnNPC(9622, new WorldTile(3968, 4817, 0), - 1, true, true); 
		World.sendWorldMessage("<img=5><col=ff0000>[Raid]: Zombies have attacked! Talk to Doomsayer to teleport and defend us!", false);
		World.sendWorldMessage("<img=5><col=ff0000>[Raid]: Invasion boss Dragith Nurn has spawned.", false);
	}
	
	private static void spawnZombie(int x, int y) {
		int[] zombieid = {8149, 8150, 8151, 8152, 8153};
		int random = Utils.getRandom(zombieid.length - 1);
		World.spawnNPC(zombieid[random], new WorldTile(x, y, 0), - 1, true, true); 
	}

}
