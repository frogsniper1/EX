package com.rs.game.player.dialogues;

import com.rs.game.player.Skills;

public class Hans extends Dialogue {

	@Override
	public void start() {
		
		if (player.getEquipment().wearingArmour()) {
			sendDialogue("Please remove your armour first.");
			stage = -2;
		} else {
		sendOptionsDialogue("Do you want to reset your Defence?", "Reset Defence", "Never mind");
		stage = 1;
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getSkills().set(Skills.DEFENCE, 1);
				player.getSkills().setXp(Skills.DEFENCE, Skills.getXPForLevel(1));
				player.getAppearence().generateAppearenceData();
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();				
			}
			
		} else if (stage == -2) {
			end();
		}
	}
	

	@Override
	public void finish() {
	}

}