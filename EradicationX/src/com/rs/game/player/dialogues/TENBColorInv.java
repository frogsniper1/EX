package com.rs.game.player.dialogues;


// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class TENBColorInv extends Dialogue {

	public TENBColorInv() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a color",
				"Blue",
				"Red", "Yellow", "Green", "Next Page");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			switch (componentId) {
				case OPTION_1: // Blue
						if (player.getInventory().containsItem(27344, 1)) 
							player.getInventory().deleteItem(27344, 1);
						else if (player.getInventory().containsItem(27345, 1)) 
							player.getInventory().deleteItem(27345, 1); 
						else if (player.getInventory().containsItem(27346, 1)) 
							player.getInventory().deleteItem(27346, 1); 
						else if (player.getInventory().containsItem(27347, 1)) 
							player.getInventory().deleteItem(27347, 1); 
						else if (player.getInventory().containsItem(27348, 1)) 
							player.getInventory().deleteItem(27348, 1); 
						else if (player.getInventory().containsItem(27349, 1)) 
							player.getInventory().deleteItem(27349, 1); 
						else if (player.getInventory().containsItem(27350, 1)) 
							player.getInventory().deleteItem(27350, 1); 
						else if (player.getInventory().containsItem(27355, 1)) 
							player.getInventory().deleteItem(27355, 1); 	
						player.sm("You have switched your color to blue.");
						player.getInventory().addItem(27355, 1);
						end();
						break;
				case OPTION_2: // Red
					if (player.getInventory().containsItem(27344, 1)) 
						player.getInventory().deleteItem(27344, 1);
					else if (player.getInventory().containsItem(27345, 1)) 
						player.getInventory().deleteItem(27345, 1); 
					else if (player.getInventory().containsItem(27346, 1)) 
						player.getInventory().deleteItem(27346, 1); 
					else if (player.getInventory().containsItem(27347, 1)) 
						player.getInventory().deleteItem(27347, 1); 
					else if (player.getInventory().containsItem(27348, 1)) 
						player.getInventory().deleteItem(27348, 1); 
					else if (player.getInventory().containsItem(27349, 1)) 
						player.getInventory().deleteItem(27349, 1); 
					else if (player.getInventory().containsItem(27350, 1)) 
						player.getInventory().deleteItem(27350, 1); 
					else if (player.getInventory().containsItem(27355, 1)) 
						player.getInventory().deleteItem(27355, 1); 	
					player.sm("You have switched your color to red.");
					player.getInventory().addItem(27344, 1);
						end();
						break;
				case OPTION_3: // Yellow
					if (player.getInventory().containsItem(27344, 1)) 
						player.getInventory().deleteItem(27344, 1);
					else if (player.getInventory().containsItem(27345, 1)) 
						player.getInventory().deleteItem(27345, 1); 
					else if (player.getInventory().containsItem(27346, 1)) 
						player.getInventory().deleteItem(27346, 1); 
					else if (player.getInventory().containsItem(27347, 1)) 
						player.getInventory().deleteItem(27347, 1); 
					else if (player.getInventory().containsItem(27348, 1)) 
						player.getInventory().deleteItem(27348, 1); 
					else if (player.getInventory().containsItem(27349, 1)) 
						player.getInventory().deleteItem(27349, 1); 
					else if (player.getInventory().containsItem(27350, 1)) 
						player.getInventory().deleteItem(27350, 1); 
					else if (player.getInventory().containsItem(27355, 1)) 
						player.getInventory().deleteItem(27355, 1); 		
					player.sm("You have switched your color to yellow.");
					player.getInventory().addItem(27345, 1);
						end();
						break;
				case OPTION_4: // Green
					if (player.getInventory().containsItem(27344, 1)) 
						player.getInventory().deleteItem(27344, 1);
					else if (player.getInventory().containsItem(27345, 1)) 
						player.getInventory().deleteItem(27345, 1); 
					else if (player.getInventory().containsItem(27346, 1)) 
						player.getInventory().deleteItem(27346, 1); 
					else if (player.getInventory().containsItem(27347, 1)) 
						player.getInventory().deleteItem(27347, 1); 
					else if (player.getInventory().containsItem(27348, 1)) 
						player.getInventory().deleteItem(27348, 1); 
					else if (player.getInventory().containsItem(27349, 1)) 
						player.getInventory().deleteItem(27349, 1); 
					else if (player.getInventory().containsItem(27350, 1)) 
						player.getInventory().deleteItem(27350, 1); 
					else if (player.getInventory().containsItem(27355, 1)) 
						player.getInventory().deleteItem(27355, 1); 	
					player.sm("You have switched your color to green.");
					player.getInventory().addItem(27346, 1);
						end();
						break;
				case OPTION_5:	
					stage = 2;
					sendOptionsDialogue("Select a color", "White", "Black", "Orange", "Purple", "Go back");
			}
			break;
		case 2:
			switch (componentId) {
			case OPTION_1: // White
				if (player.getInventory().containsItem(27344, 1)) 
					player.getInventory().deleteItem(27344, 1);
				else if (player.getInventory().containsItem(27345, 1)) 
					player.getInventory().deleteItem(27345, 1); 
				else if (player.getInventory().containsItem(27346, 1)) 
					player.getInventory().deleteItem(27346, 1); 
				else if (player.getInventory().containsItem(27347, 1)) 
					player.getInventory().deleteItem(27347, 1); 
				else if (player.getInventory().containsItem(27348, 1)) 
					player.getInventory().deleteItem(27348, 1); 
				else if (player.getInventory().containsItem(27349, 1)) 
					player.getInventory().deleteItem(27349, 1); 
				else if (player.getInventory().containsItem(27350, 1)) 
					player.getInventory().deleteItem(27350, 1); 
				else if (player.getInventory().containsItem(27355, 1)) 
					player.getInventory().deleteItem(27355, 1); 	
				player.sm("You have switched your color to white.");
				player.getInventory().addItem(27347, 1);
					end();
					break;
			case OPTION_2: // Black
				if (player.getInventory().containsItem(27344, 1)) 
					player.getInventory().deleteItem(27344, 1);
				else if (player.getInventory().containsItem(27345, 1)) 
					player.getInventory().deleteItem(27345, 1); 
				else if (player.getInventory().containsItem(27346, 1)) 
					player.getInventory().deleteItem(27346, 1); 
				else if (player.getInventory().containsItem(27347, 1)) 
					player.getInventory().deleteItem(27347, 1); 
				else if (player.getInventory().containsItem(27348, 1)) 
					player.getInventory().deleteItem(27348, 1); 
				else if (player.getInventory().containsItem(27349, 1)) 
					player.getInventory().deleteItem(27349, 1); 
				else if (player.getInventory().containsItem(27350, 1)) 
					player.getInventory().deleteItem(27350, 1); 
				else if (player.getInventory().containsItem(27355, 1)) 
					player.getInventory().deleteItem(27355, 1); 	
				player.sm("You have switched your color to black.");
				player.getInventory().addItem(27348, 1);
					end();
					break;
			case OPTION_3: // Orange
				if (player.getInventory().containsItem(27344, 1)) 
					player.getInventory().deleteItem(27344, 1);
				else if (player.getInventory().containsItem(27345, 1)) 
					player.getInventory().deleteItem(27345, 1); 
				else if (player.getInventory().containsItem(27346, 1)) 
					player.getInventory().deleteItem(27346, 1); 
				else if (player.getInventory().containsItem(27347, 1)) 
					player.getInventory().deleteItem(27347, 1); 
				else if (player.getInventory().containsItem(27348, 1)) 
					player.getInventory().deleteItem(27348, 1); 
				else if (player.getInventory().containsItem(27349, 1)) 
					player.getInventory().deleteItem(27349, 1); 
				else if (player.getInventory().containsItem(27350, 1)) 
					player.getInventory().deleteItem(27350, 1); 
				else if (player.getInventory().containsItem(27355, 1)) 
					player.getInventory().deleteItem(27355, 1); 	
				player.sm("You have switched your color to orange.");
				player.getInventory().addItem(27349, 1);
					end();
					break;
			case OPTION_4: // Purple
				if (player.getInventory().containsItem(27344, 1)) 
					player.getInventory().deleteItem(27344, 1);
				else if (player.getInventory().containsItem(27345, 1)) 
					player.getInventory().deleteItem(27345, 1); 
				else if (player.getInventory().containsItem(27346, 1)) 
					player.getInventory().deleteItem(27346, 1); 
				else if (player.getInventory().containsItem(27347, 1)) 
					player.getInventory().deleteItem(27347, 1); 
				else if (player.getInventory().containsItem(27348, 1)) 
					player.getInventory().deleteItem(27348, 1); 
				else if (player.getInventory().containsItem(27349, 1)) 
					player.getInventory().deleteItem(27349, 1); 
				else if (player.getInventory().containsItem(27350, 1)) 
					player.getInventory().deleteItem(27350, 1); 
				else if (player.getInventory().containsItem(27355, 1)) 
					player.getInventory().deleteItem(27355, 1); 	
				player.sm("You have switched your color to purple.");
				player.getInventory().addItem(27350, 1);				
					end();
					break;
			case OPTION_5:	
				stage = 1;
				sendOptionsDialogue("Select a color",
					"Blue",
					"Red", "Yellow", "Green", "Next Page");	
			}
			break;
		}
	}
	


	@Override
	public void finish() {
	}

}


