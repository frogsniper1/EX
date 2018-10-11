package com.rs.game.player.dialogues;

import com.rs.game.Graphics;
import com.rs.game.World;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class EradicatorRank extends Dialogue {

	public EradicatorRank() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("This box has an Eradicator Rank Stored.",
				"Claim the Rank", "Convert to Coins",
				"Never mind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (!player.isEradicator() && player.getInventory().containsItem(6828, 1)) {
				player.getInventory().deleteItem(6828, 1);			
				player.setEradicator(true);
				player.setNextGraphics(new Graphics(2798));
				player.getInterfaceManager().closeChatBoxInterface();
			} else {
				player.sm("<col=AB0505> You already have that rank on your player! You can't claim any more.");
				player.getInterfaceManager().closeChatBoxInterface();
			}				
			} else if (componentId == OPTION_2) {		
				stage = 2;
				sendOptionsDialogue("Are you sure you want to do this?", "Yes [You get 825 100M Tickets]", "No");
			} else if (componentId == OPTION_3) {	
				player.getInterfaceManager().closeChatBoxInterface();
			}
		
	} else if (stage == 2) {
		if (componentId == OPTION_1) {
			if (player.getInventory().containsItem(6828, 1)) {
				player.getInventory().deleteItem(6828, 1);
				player.getInventory().addItem(2996, 825);
				World.sendWorldMessage("<col=F52E14>"+player.getDisplayName() + " destroyed an Eradicator Rank!", false);
			} else
				player.sm("You don't have an eradicator rank.");
			end();
		} else if (componentId == OPTION_2) {
			end();
		}
	}
	


}	@Override
	public void finish() {
	}

}


