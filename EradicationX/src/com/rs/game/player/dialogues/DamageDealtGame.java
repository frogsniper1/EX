package com.rs.game.player.dialogues;

import com.rs.game.minigames.DamageDummy;
import com.rs.utils.Utils;

public final class DamageDealtGame extends Dialogue {

	@Override
	public void start() {
		sendDialogue("This minigame allows you to attack the dummy, while you",
				"must deal as much damage as possible before the time",
				"runs out! You'll only have one minute, and you", "can't try again until another 12 hours.", "You will receive Invasion tokens"," depending on how much damage you deal.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(
					"If you start now, you may not try again until another 12 hours!",
					"I'm ready, start now!", "I'm not ready.");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				if (DamageDummy.getPlayersCount() <= 0 && (Utils.currentTimeMillis() - player.getDummyCooldown()) > (1000 * 60 * 60 * 12))
				player.getControlerManager().startControler("DummyControler");	
				else if (DamageDummy.getPlayersCount() > 0)
				player.sm("Someone is already doing the minigame.");
				else
				player.sm("You must wait " + (((1000 * 60 * 60 * 12) - (Utils.currentTimeMillis() - player.getDummyCooldown())) / (1000 * 60 * 60)) + " more hours to play this minigame.");	
			}
			end();
		}

	}

	@Override
	public void finish() {
	}

}
