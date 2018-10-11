package com.rs.game.player.dialogues;

import com.rs.utils.ShopsHandler;

public class ExtremeShop1 extends Dialogue {

	int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendNPCDialogue(npcId, 9827, "<shad=FFFFFF>Welcome to Extreme-Shop1.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"<shad=000000>OPEN FUCKING EXTREMESHOP1!","<shad=FD3EDA>Nevermind.");
			break;
		case 0:
			if (componentId == OPTION_2) {
				stage = 1;
				sendPlayerDialogue(9827, "<shad=990033>Nevermind.");
			} else {
				stage = 2;
				sendPlayerDialogue(9827, "<shad=FF0000>I would like to OPEN FUCKING EXTREMESHOP1!");
			}
			break;
		case 1:
			stage = -2;
			sendNPCDialogue(npcId, 9827,
					"Well, please return if you change your mind.");
			break;
		case 2:
			stage = 3;
			sendNPCDialogue(
					npcId,
					9828,
					"Sure.Opening the EXTREME SHOP1!");
			break;
		case 3:
			stage = 4;
			sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE,
					"<shad=990033>COME ON!!!!!!!!",
					"<shad=FFFFFF>Pff... Nah i dont want this shitty shop...");
			break;
		case 4:
			if (componentId == OPTION_2) {
				end();
			} else {
				stage = 5;
				{
				ShopsHandler.openShop(player, 41);
			}
			end();
			break;
			}
		case 5:
			stage = 6;
			end();
			break;
		case 6:
			end();
		default:
			end();
			break;
		}
	}

	@Override
	public void finish() {

	}

}