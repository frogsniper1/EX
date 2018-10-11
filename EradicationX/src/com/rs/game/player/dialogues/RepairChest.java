package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue
//created by Fatal Resort

public class RepairChest extends Dialogue {

	public RepairChest() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a set.",
				"Repair my Torva",
				"Repair my Pernix", "Repair my Virtus");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendOptionsDialogue("Please select the piece that's broken.",
						"Torva Full Helm", "Torva Platebody", "Torva Platelegs", "Full Set");
				stage = 2;				
			} else if (componentId == OPTION_2) {
				sendOptionsDialogue("Please select the piece that's broken.",
						"Pernix Cowl", "Pernix Body", "Pernix Chaps", "Zaryte Bow", "Full Set (No Zaryte)");
				stage = 3;				
			} else if (componentId == OPTION_3) {
				sendOptionsDialogue("Please select the piece that's broken.",
						"Virtus Mask", "Virtus Body", "Virtus Legs", "Full Set");
				stage = 4;
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(20138, 1)) {		
				player.getInventory().deleteItem(20138, 1);
				player.getInventory().addItem(20135, 1);
				player.sm("<col=EDFAE2>You trade in your broken Torva Helmet for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken torva helmet.");
				player.getInterfaceManager().closeChatBoxInterface();	
	
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(20142, 1)) {		
				player.getInventory().deleteItem(20142, 1);
				player.getInventory().addItem(20139, 1);
				player.sm("<col=EDFAE2>You trade in your broken Torva Body for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken torva body.");
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if (componentId == OPTION_3) {
				if (player.getInventory().containsItem(20146, 1)) {		
				player.getInventory().deleteItem(20146, 1);
				player.getInventory().addItem(20143, 1);
				player.sm("<col=EDFAE2>You trade in your broken Torva Legs for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have broken torva legs.");
				player.getInterfaceManager().closeChatBoxInterface();
			
			} else if (componentId == OPTION_4) {
				if (player.getInventory().containsItem(20138, 1) && player.getInventory().containsItem(20142, 1) && player.getInventory().containsItem(20146, 1)) {		
				player.getInventory().deleteItem(20138, 1);
				player.getInventory().deleteItem(20142, 1);
				player.getInventory().deleteItem(20146, 1);
				player.getInventory().addItem(20135, 1);
				player.getInventory().addItem(20139, 1);
				player.getInventory().addItem(20143, 1);
				player.sm("<col=EDFAE2>You trade in your broken Torva for a new set.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have full broken Torva.");
				player.getInterfaceManager().closeChatBoxInterface();			
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(20150, 1)) {		
				player.getInventory().deleteItem(20150, 1);
				player.getInventory().addItem(20147, 1);
				player.sm("<col=EDFAE2>You trade in your broken Pernix Cowl for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken Pernix Cowl.");
				player.getInterfaceManager().closeChatBoxInterface();	
	
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(20154, 1)) {		
				player.getInventory().deleteItem(20154, 1);
				player.getInventory().addItem(20151, 1);
				player.sm("<col=EDFAE2>You trade in your broken Pernix Body for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken Pernix body.");
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if (componentId == OPTION_3) {
				if (player.getInventory().containsItem(20158, 1)) {		
				player.getInventory().deleteItem(20158, 1);
				player.getInventory().addItem(20155, 1);
				player.sm("<col=EDFAE2>You trade in your broken Pernix Legs for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have broken Pernix legs.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				if (player.getInventory().containsItem(20174, 1)) {		
				player.getInventory().deleteItem(20174, 1);
				player.getInventory().addItem(20171, 1);
				player.sm("<col=EDFAE2>You trade in your broken Zaryte Bow for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken Zaryte bow .");
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				if (player.getInventory().containsItem(20150, 1) && player.getInventory().containsItem(20154, 1) && player.getInventory().containsItem(20158, 1)) {		
				player.getInventory().deleteItem(20150, 1);
				player.getInventory().deleteItem(20154, 1);
				player.getInventory().deleteItem(20158, 1);
				player.getInventory().addItem(20147, 1);
				player.getInventory().addItem(20151, 1);
				player.getInventory().addItem(20155, 1);
				player.sm("<col=EDFAE2>You trade in your broken Pernix for a new set.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have full broken Pernix.");
				player.getInterfaceManager().closeChatBoxInterface();			
			}
		} else if (stage == 4) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(20162, 1)) {		
				player.getInventory().deleteItem(20162, 1);
				player.getInventory().addItem(20159, 1);
				player.sm("<col=EDFAE2>You trade in your broken Virtus Mask for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken Virtus Mask.");
				player.getInterfaceManager().closeChatBoxInterface();	
	
			} else if (componentId == OPTION_2) {
				if (player.getInventory().containsItem(20166, 1)) {		
				player.getInventory().deleteItem(20166, 1);
				player.getInventory().addItem(20163, 1);
				player.sm("<col=EDFAE2>You trade in your broken Virtus Body for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have a broken Virtus body.");
				player.getInterfaceManager().closeChatBoxInterface();
				
			} else if (componentId == OPTION_3) {
				if (player.getInventory().containsItem(20170, 1)) {		
				player.getInventory().deleteItem(20170, 1);
				player.getInventory().addItem(20167, 1);
				player.sm("<col=EDFAE2>You trade in your broken Virtus Legs for a new one.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have broken Virtus legs.");
				player.getInterfaceManager().closeChatBoxInterface();
			
			} else if (componentId == OPTION_4) {
				if (player.getInventory().containsItem(20162, 1) && player.getInventory().containsItem(20166, 1) && player.getInventory().containsItem(20170, 1)) {		
				player.getInventory().deleteItem(20162, 1);
				player.getInventory().deleteItem(20166, 1);
				player.getInventory().deleteItem(20170, 1);
				player.getInventory().addItem(20159, 1);
				player.getInventory().addItem(20163, 1);
				player.getInventory().addItem(20167, 1);
				player.sm("<col=EDFAE2>You trade in your broken Virtus for a new set.");
				player.getInterfaceManager().closeChatBoxInterface();				 
				}	
				player.sm("You don't have full broken Virtus.");
				player.getInterfaceManager().closeChatBoxInterface();			
			}
			}
			
			

}	@Override
	public void finish() {
	}

}


