package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class SouthWestStairs extends Dialogue {

	public SouthWestStairs() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Going up or down?",
				"Up",
				"Down",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.useStairs(-1, new WorldTile(3955, 4805, 2), 0, 1);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.useStairs(-1, new WorldTile(4236, 3934, 0), 0, 1);			
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	
	


}	@Override
	public void finish() {
	}

}


