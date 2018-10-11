package com.rs.game.player.dialogues;

import com.rs.game.player.content.TicketSystem;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class TicketTableLeft extends Dialogue {

	public TicketTableLeft() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Are you ready to attend a player?",
				"Yes",
				"No");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				TicketSystem.answerTicket(player);
				player.getInterfaceManager().closeChatBoxInterface();	
				return;
			} else if (componentId == OPTION_2) {
				player.sm("Disgrace! :(");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}

	


}	@Override
	public void finish() {
	}

}


