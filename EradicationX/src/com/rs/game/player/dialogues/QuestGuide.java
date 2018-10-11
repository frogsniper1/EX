package com.rs.game.player.dialogues;

import com.rs.game.player.controlers.StartTutorial;

public class QuestGuide extends Dialogue {

	int npcId;
	StartTutorial controler;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		controler = (StartTutorial) parameters[1];
		if (controler == null) {
			sendEntityDialogue(
					SEND_2_TEXT_CHAT,
					new String[] {
							"The Eradicator",
							"You have no reason to remain here. Go on now!",
							"If you're stuck, try relogging." },
					IS_NPC, npcId, 9830);
			stage = 7;
		} else {
			int s = controler.getStage();
			if (s == 0) {
				sendEntityDialogue(
						SEND_2_TEXT_CHAT,
						new String[] {
								"The Eradicator",
								"Ah, hello " + player.getDisplayName() + ".",
								"Welcome to EradicationX. I'm here to make sure", 
								"you know everything you need before you enter", 
								"this world."},
						IS_NPC, npcId, 9830);
			} else if (s == 2) {
				sendEntityDialogue(SEND_1_TEXT_CHAT, new String[] {
						"The Eradicator",
						"There's just one more thing before I can let you go. Would you like to play as an Ironman? "
						+ "In Ironman mode, you cannot trade with other players. Your interaction with the community will be limited." }, IS_NPC, npcId,
						9830);
				stage = 5;
			}
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			stage = 0;
			sendEntityDialogue(
				SEND_2_TEXT_CHAT,
				new String[] {
						"The Eradicator",
						"In the EX tab, the tab next to your inventory, You can",
						"access teleports to anywhere in the game. Bosses, Minigames, Training locations." },
				IS_NPC, npcId, 9830);
			break;
		case 0:
			stage = 1;
			sendEntityDialogue(
				SEND_4_TEXT_CHAT,
				new String[] {
						"The Eradicator",
						"You can also vote, change clothes, gender, and hair.",
						"Clicking the forums will take you to our forums.",
						"You can watch guides, catch up on our news and updates,",
						"and of course, communicate with fellow players." }, IS_NPC,
				npcId, 9830);
			break;
		case 1:
			stage = 9;
			sendEntityDialogue(
				SEND_3_TEXT_CHAT,
				new String[] {
						"The Eradicator",
						"There's much more, but i've told you enough to help you",
						"get started. Let's move on now, shall we?",
						"Go ahead and customize your character now!" }, IS_NPC, npcId,
				9830);
			break;
		case 5:
			player.getDialogueManager().startDialogue("PickIronman");
			break;
		case 9:
			stage = 2;
			controler.updateProgress();
			break;
		default:
			end();
		}
	}

	@Override
	public void finish() {

	}

}
