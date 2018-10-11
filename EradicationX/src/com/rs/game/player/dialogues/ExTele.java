package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue
// created by fatal resort - EradicationX
public class ExTele extends Dialogue {

	public ExTele() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Teleport to Regular Boss ?", "Teleport",
				"Stay here");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getInterfaceManager().closeChatBoxInterface();	
				player.getDialogueManager().startDialogue("Regular");			
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();							
		} 

	}
	}
	@Override
	public void finish() {
	}

}
