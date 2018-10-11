package com.rs.game.player.dialogues;

import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.player.Skills;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class SkillerMasterPurchase extends Dialogue {

	public SkillerMasterPurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a Skiller master cape? [4M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
					if (player.getSkills().getXp(Skills.RUNECRAFTING) >= 200000000
							&& player.getSkills().getXp(Skills.FISHING) >= 200000000
							&& player.getSkills().getXp(Skills.AGILITY) >= 200000000
							&& player.getSkills().getXp(Skills.COOKING) >= 200000000
							&& player.getSkills().getXp(Skills.THIEVING) >= 200000000
							&& player.getSkills().getXp(Skills.MINING) >= 200000000
							&& player.getSkills().getXp(Skills.SMITHING) >= 200000000
							&& player.getSkills().getXp(Skills.FARMING) >= 200000000
							&& player.getSkills().getXp(Skills.HUNTER) >= 200000000
							&& player.getSkills().getXp(Skills.SLAYER) >= 200000000
							&& player.getSkills().getXp(Skills.CRAFTING) >= 200000000
							&& player.getSkills().getXp(Skills.WOODCUTTING) >= 200000000
							&& player.getSkills().getXp(Skills.FIREMAKING) >= 200000000
							&& player.getSkills().getXp(Skills.FLETCHING) >= 200000000
							&& player.getSkills().getXp(Skills.CONSTRUCTION) >= 200000000
							&& player.getSkills().getXp(Skills.HERBLORE) >= 200000000) {	
							if (player.chargeMoney(4000000)) {
								player.getInventory().addItem(29948, 1);
								player.sm("Nice job! Enjoy your cape!");
								player.getInterfaceManager().closeChatBoxInterface();
								if (player.getAnnouncement() == 0) {
									 World.sendWorldMessage
							("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just achieved <col=FF0000> a Skiller Master Cape!", false);
									 player.setNextGraphics(new Graphics(1634));
								}
								player.setAnnouncement(1);}
							else {
								player.sm("You need 4M in your inventory to purchase this.");
								player.getInterfaceManager().closeChatBoxInterface();
							}
					} else {
						player.sm("You need 200M experience in all skills that aren't combat related.");
						player.getInterfaceManager().closeChatBoxInterface();
					}
			} else if (componentId == OPTION_2) {
				end();
			}
		}
	}

	@Override
	public void finish() {
	}

}


