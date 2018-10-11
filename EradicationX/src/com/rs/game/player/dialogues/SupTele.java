package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue
// created by fatal resort - EradicationX
public class SupTele extends Dialogue {

	public SupTele() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Teleport to Extreme Boss ?", "Teleport",
				"Stay here");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
			if(!player.checkExtreme()) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getPackets().sendGameMessage("You need to be an Extreme Donator to Access this Panel.");
			} else {
				player.getDialogueManager().startDialogue("Extreme");
			}
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();							
		} 

	}
	}
	@Override
	public void finish() {
	}

}
