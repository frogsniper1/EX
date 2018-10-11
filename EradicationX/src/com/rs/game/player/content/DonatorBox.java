package com.rs.game.player.content;


import com.rs.game.player.Player;


/**
 * this isn't more than a base and it's quickly made ... good luck.
 * @author <saddam>
 * @date 6/2/2014 @time 4:47 am
 */


public class DonatorBox {
    
    /**
     * The item list, edit these id's and remember the more u add the same item , the more it rate increase! :)
     * Partyhats/hween/ags/lucky ags/claws/lucky claws/lucky whip/all rings (i) and ice amulet(09) :)
     */
    private static int randomItemList[] = { 
        15017, 15017, 15017, 15017, 15017,
        15018, 15018, 15018, 15018, 15018,
        15020, 15020, 15020, 15020, 15020, 
        15220, 15220, 15220, 15220, 15220,
        23679, 23679, 23683, 23683, 23691,
        23691, 11694, 11694, 14484, 14484,
        15418, 15418, 15418, 15418, 15418,
        1038, 1040, 1042, 1044, 1046, 1048,
        1053, 1055, 
        1057
    };
    
    /**
     * Selects a random number from above
     */
    public static int giveRandomItem() {
        return randomItemList[(int) (Math.random()*randomItemList.length)];
    }
    
    
    /**
     * Add more than one number to make it more common, or to make 
     * other numbers less common.
     */
    private static int RandomAmounts[] = { 
        1,1,1,1,1,1,1,1,1,
        1,1,1,1,1,1,1,
        1,1,1,1,
        1,1,
        1
    };
    
    /**
     * Selects an amount randomly from the above
     */
    public static int getRandomAmount() {
        return RandomAmounts[(int) (Math.random()*RandomAmounts.length)];
    }
    
    /**
     * Separte the message for rare items/ or the item you excatly want xD ex 1038 is a phat when someone get it, another message will popup
     */
    public static void Reward(Player p) {
        int item = giveRandomItem();
        
        if (item == 1038) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOM item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1040) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1042) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1044) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1046) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1048) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		if (item == 1050) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1053) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1055) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		if (item == 1057) {
        p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" Has just opened his donation box and won a RANDOME item.");
        p.getInventory().addItem(995, 5000000);
		}
		} else {
			p.getInventory().addItem(item, 1);
			p.getPackets().sendGameMessage("<img=5> [<col=0000FF>Donation Box</col>] <col=FF0000>"+p.getDisplayName()+" has just opened his donation box and won a RANDOME item.");
		}
	}
}
