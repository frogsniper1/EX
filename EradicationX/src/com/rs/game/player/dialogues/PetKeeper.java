package com.rs.game.player.dialogues;

import com.rs.game.player.BossPets;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class PetKeeper extends Dialogue {

	public PetKeeper() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("View pets",
				"Original page",
				"More");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				BossPets.openInterface(player);
				end();
			} else if (componentId == OPTION_2) {
				stage = 2;
				sendOptionsDialogue("More pets", "Hard Mode Trio", "Hairymonkey", "Close");
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(27111, 1) || player.getBank().getItem(27111) != null) {
	    			player.sm("You already have this pet.");
	    			
	    		}
	    		if (player.getHMTrioKills() >= 2000)
	    			player.getInventory().addItem(27111, 1);
	    		else
	    			player.sm("You can't take this pet! You only have " + player.getHMTrioKills() + " HM Trio Kills.");
	    		
				end();
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(27110, 1) || player.getBank().getItem(27110) != null) {
	    			player.sm("You already have this pet.");
	    			
	    		}
	    		if (player.getHairymonkeykills() >= 2000)
	    			player.getInventory().addItem(27110, 1);
	    		else
	    			player.sm("You can't take this pet! You only have " + player.getHairymonkeykills() + " Hairymonkey Kills.");
	    		end();
			} else if (componentId == OPTION_3) {
				end();
			}
		}
	}
	

	@Override
	public void finish() {
	}

}


