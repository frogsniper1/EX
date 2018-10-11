package com.rs.game.player.dialogues;

import com.rs.game.Graphics;
import com.rs.game.World;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class ExtremeRank extends Dialogue {

	public ExtremeRank() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("This box has an Extreme Rank Stored.",
				"Claim the Rank",
				"Combine 2 Extreme Ranks for Super Donator", "Convert to Coins",
				"Never mind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
			if (!player.isExtremeDonator() && player.getInventory().containsItem(6830, 1)) {
				player.setExtremeDonator(true);
				player.getInventory().deleteItem(6830, 1);
				player.setNextGraphics(new Graphics(2798));
				player.getInterfaceManager().closeChatBoxInterface();
			} else {
				player.sm("<col=AB0505> You already have that rank on your player! You can't claim any more.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(6830, 2)) {
				player.getInventory().deleteItem(6830, 2);				
				player.getInventory().addItem(6829, 1);	
				player.sm("You have created a <img=9> Super Donator Box.");
				player.getInterfaceManager().closeChatBoxInterface();
				} else {
				player.sm("You do not have enough Extreme Donator Boxes to do this. You need 2 boxes.");
				}
			} else if (componentId == OPTION_3) {
				stage = 2;
				sendOptionsDialogue("Are you sure you want to do this?", "Yes [You get 165 100M Tickets]", "No");
			} else if (componentId == OPTION_4) {
				player.sm("You declined.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(6830, 1)) {
					player.getInventory().deleteItem(6830, 1);
					player.getInventory().addItem(2996, 165);
					World.sendWorldMessage("<col=F52E14>"+player.getDisplayName() + " destroyed an Extreme Donator Rank!", false);
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


