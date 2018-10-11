package com.rs.game.player.dialogues;

import com.rs.game.Graphics;
import com.rs.game.World;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class SaviorRank extends Dialogue {

	public SaviorRank() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("This box has a Super Donator Stored.",
				"Claim the Rank",
				"Combine 3 Super Donator Ranks for Eradicator", "Convert to Coins",
				"Never mind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {		
			if (componentId == OPTION_1) {
			if (!player.isSavior() && player.getInventory().containsItem(6829, 1)) {
				player.setSavior(true);
				player.getInventory().deleteItem(6829, 1);
				player.setNextGraphics(new Graphics(2798));
				player.getInterfaceManager().closeChatBoxInterface();
			} else {
				player.sm("<col=AB0505> You already have that rank on your player! You can't claim any more.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(6829, 3)) {
				player.getInventory().deleteItem(6829, 3);				
				player.getInventory().addItem(6828, 1);	
				player.sm("You have created an <img=18> Eradicator Box.");
				player.getInterfaceManager().closeChatBoxInterface();
				} else {
				player.sm("You do not have enough Super Donator Donator Boxes to do this. You need 3 boxes.");
				}
			} else if (componentId == OPTION_3) {
				stage = 2;
				sendOptionsDialogue("Are you sure you want to do this?", "Yes [You get 260 100M Tickets]", "No");
			} else if (componentId == OPTION_4) {
				player.sm("You declined.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(6829, 1)) {
					player.getInventory().deleteItem(6829, 1);
					player.getInventory().addItem(2996, 260);
					World.sendWorldMessage("<col=F52E14>"+player.getDisplayName() + " destroyed a Super Donator Rank!", false);
				} else
					player.sm("You don't have a donator rank.");
				end();
			} else if (componentId == OPTION_2) {
				end();
			}
		}
	
}	@Override
	public void finish() {
	}

}


