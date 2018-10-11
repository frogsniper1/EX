package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class MeetingDoorThree extends Dialogue {

	public MeetingDoorThree() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Going inside or outside?",
				"Go Inside",
				"Go Outside",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.useStairs(-1, new WorldTile(4251, 3941, 1), 0, 1);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.useStairs(-1, new WorldTile(4250, 3941, 1), 0, 1);			
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	
	


}	@Override
	public void finish() {
	}

}


