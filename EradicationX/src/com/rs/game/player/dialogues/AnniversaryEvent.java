package com.rs.game.player.dialogues;

import com.rs.game.minigames.AnniversaryEventMinigame;

public final class AnniversaryEvent extends Dialogue {

	@Override
	public void start() {
		sendDialogue("Because of last year, some of this event will be",
				"programmed-in to make sure everything goes smoothly. To enter,",
				"You must have a skill total of 400 or over. You must also understand", 
				"You will not be able to move during this part of the event.", 
				"If you log out, your position will be filled.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			sendOptionsDialogue(
					"There are currently " + AnniversaryEventMinigame.getPlayersCount()
							+ " people inside. Do you want to join?",
					"Assign me a spot in the giveaway", "Stay here");
		} else if (stage == 0) {
			if (componentId == OPTION_1) {
				if (player.getSkills().getTotalLevel() > 400) {
				if (!player.wenttoevent)
					player.getControlerManager().startControler("AnniversaryEventControler", 69);	
				else
					player.sm("You've already went to this event.");
				} else {
					player.sm("lol nice lvl m8 lvl up plz");
				}
			}
			end();
		}

	}

	@Override
	public void finish() {
	}

}
