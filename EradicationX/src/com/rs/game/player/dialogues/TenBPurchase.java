package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.Utils;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class TenBPurchase extends Dialogue {

	public TenBPurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a 10B XP cape? [5M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
					if (player.check10BRequirements()) {	
						if (player.chargeMoney(5000000)) {
							player.getInventory().addItem(27355, 1);
							player.sm("Nice job! Enjoy your wings!");
							player.getInterfaceManager().closeChatBoxInterface();
							if (player.getAnnouncement2() == 0) {
								 World.sendWorldMessage
								 ("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just achieved <col=FF0000> 10B XP!", false);
								 player.setNextGraphics(new Graphics(1634));
									for (Player players: World.getPlayers()) {
					                    if (players != null && Utils.getDistance(player, players) < 14) {
					                        players.setNextForceTalk(new ForceTalk("Congratulations " + player.getDisplayName() + " on 10B Experience!"));
					                    }
					                }
							}
							player.setAnnouncement2(1);
						} else {
							player.sm("You need 5M coins in your inventory to purchase this.");
							player.getInterfaceManager().closeChatBoxInterface();
						}		
				} else {
					player.sm("You need 400M experience in all skills.");
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


