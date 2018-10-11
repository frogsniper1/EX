package com.rs.game.player.dialogues;

import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.player.Skills;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class TwoHundredMMasterPurchase extends Dialogue {

	public TwoHundredMMasterPurchase() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Buy a 200M Master cape? [5M]",
				"Yes",
				"No");
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
					if (player.getSkills().getXp(Skills.ATTACK) >= 200000000
							&& player.getSkills().getXp(Skills.STRENGTH) >= 200000000
							&& player.getSkills().getXp(Skills.DEFENCE) >= 200000000
							&& player.getSkills().getXp(Skills.CONSTRUCTION) >= 200000000
							&& player.getSkills().getXp(Skills.HITPOINTS) >= 200000000
							&& player.getSkills().getXp(Skills.RANGE) >= 200000000
							&& player.getSkills().getXp(Skills.MAGIC) >= 200000000
							&& player.getSkills().getXp(Skills.RUNECRAFTING) >= 200000000
							&& player.getSkills().getXp(Skills.FISHING) >= 200000000
							&& player.getSkills().getXp(Skills.AGILITY) >= 200000000
							&& player.getSkills().getXp(Skills.COOKING) >= 200000000
							&& player.getSkills().getXp(Skills.PRAYER) >= 200000000
							&& player.getSkills().getXp(Skills.THIEVING) >= 200000000
							&& player.getSkills().getXp(Skills.DUNGEONEERING) >= 200000000
							&& player.getSkills().getXp(Skills.MINING) >= 200000000
							&& player.getSkills().getXp(Skills.SMITHING) >= 200000000
							&& player.getSkills().getXp(Skills.SUMMONING) >= 200000000
							&& player.getSkills().getXp(Skills.FARMING) >= 200000000
							&& player.getSkills().getXp(Skills.HUNTER) >= 200000000
							&& player.getSkills().getXp(Skills.SLAYER) >= 200000000
							&& player.getSkills().getXp(Skills.CRAFTING) >= 200000000
							&& player.getSkills().getXp(Skills.WOODCUTTING) >= 200000000
							&& player.getSkills().getXp(Skills.FIREMAKING) >= 200000000
							&& player.getSkills().getXp(Skills.FLETCHING) >= 200000000
							&& player.getSkills().getXp(Skills.HERBLORE) >= 200000000) {	
							if (player.chargeMoney(5000000)) {
								player.getInventory().addItem(28013, 1);
								player.sm("Nice job! Enjoy your cape!");
								player.getInterfaceManager().closeChatBoxInterface();
								if (player.getAnnouncement() == 0) {
									 World.sendWorldMessage
							("<img=18>[News]:<col=FF0000> " + player.getDisplayName() + "</col> just achieved <col=FF0000> 200M Experience in all skills!", false);
									 player.setNextGraphics(new Graphics(1634));
								}
								player.setAnnouncement(1);
							} else {
								player.sm("You need 5M in your inventory to purchase this.");
								player.getInterfaceManager().closeChatBoxInterface();
							}	
					} else {
						player.sm("You don't meet the requirements.");
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


