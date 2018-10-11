package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class AmuletofCompletionPurchase extends Dialogue {

	public AmuletofCompletionPurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy an Amulet of Completion? [5M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
					if (player.checkAmuletofCompletion()) {

						if (player.chargeMoney(5000000)) {
							player.getInventory().addItem(6194, 1);
							player.sm("Nice job! Enjoy your wings!");
							player.getInterfaceManager().closeChatBoxInterface();
							if (!player.ammyannounce) {
								 World.sendWorldMessage
						("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just finished <col=FF0000>all achievements!", false);
								 player.setNextGraphics(new Graphics(1634));
									for (Player players: World.getPlayers()) {
					                    if (players != null && Utils.getDistance(player, players) < 14) {
					                        players.setNextForceTalk(new ForceTalk("Congratulations " + player.getDisplayName() + "!"));
					                    }
					                }
							}
							player.ammyannounce = true;
						} else {
							player.sm("You need 5M in your inventory to purchase this.");
							player.getInterfaceManager().closeChatBoxInterface();
						}
				} else {
					player.sm("You need to have completed all achievements in the trophy tab.");
					player.getInterfaceManager().closeChatBoxInterface();
				}
		
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().sendInterface(3013);
				end();
			}
		}
	}

	@Override
	public void finish() {
	}

}


