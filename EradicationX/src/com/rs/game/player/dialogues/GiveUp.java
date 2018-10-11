package com.rs.game.player.dialogues;

import com.rs.game.World;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class GiveUp extends Dialogue {

	public GiveUp() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Give up? You won't be able to become an Ironman EVER AGAIN!",
				"Yes. Delete my Ironman rank FOREVER",
				"No.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
					player.setIronMan(false);
					if (player.getSkills().getTotalXp() > 1000000000L) 
					World.sendWorldMessage("<img=23><col=D19B28>[News]: Lol this guy gave up Ironman Mode with "+player.getSkills().getTotalXp()+" XP: " + player.getDisplayName(),false);
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
			end();
		}
	}
	


	@Override
	public void finish() {
	}

}


