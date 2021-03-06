package com.rs.game.player.dialogues;

import com.rs.game.player.BankPreset;

public class ResetBankPreset2 extends Dialogue {

	public ResetBankPreset2() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Reset Preset?",
				"Yes",
				"No");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.setBankPreset2(new BankPreset(player));
				player.sm("Preset 2 has been completely reset.");
				player.sendBankPreset();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
		end();
	}
	


	@Override
	public void finish() {
	}

}


