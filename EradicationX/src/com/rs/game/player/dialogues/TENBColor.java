package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.game.player.Equipment;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class TENBColor extends Dialogue {

	public TENBColor() {
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
		int cape = player.getEquipment().getCapeId();
		switch (stage) {
		case 1:
			switch (componentId) {
				case OPTION_1: // Blue
					if (cape == -1) {
						player.getInventory().deleteItem(27344 ,1);
						player.getInventory().deleteItem(27345 ,1);
						player.getInventory().deleteItem(27346 ,1);
						player.getInventory().deleteItem(27347 ,1);
						player.getInventory().deleteItem(27348 ,1);
						player.getInventory().deleteItem(27349 ,1);
						player.getInventory().deleteItem(27350 ,1);
						player.getInventory().deleteItem(27355 ,1);
					} else
						player.getEquipment().deleteItem(cape, 1);
						player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27355));	
						player.getEquipment().refresh(Equipment.SLOT_CAPE);
						player.getAppearence().generateAppearenceData();
						player.sm("Your cape has been set to blue.");
						end();
						break;
				case OPTION_2: // Red
					if (cape == -1) {
						player.getInventory().deleteItem(27344 ,1);
						player.getInventory().deleteItem(27345 ,1);
						player.getInventory().deleteItem(27346 ,1);
						player.getInventory().deleteItem(27347 ,1);
						player.getInventory().deleteItem(27348 ,1);
						player.getInventory().deleteItem(27349 ,1);
						player.getInventory().deleteItem(27350 ,1);
						player.getInventory().deleteItem(27355 ,1);
					} else
						player.getEquipment().deleteItem(cape, 1);
						player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27344));	
						player.getEquipment().refresh(Equipment.SLOT_CAPE);
						player.getAppearence().generateAppearenceData();
						player.sm("Your cape has been set to red.");
						end();
						break;
				case OPTION_3: // Yellow
					if (cape == -1) {
						player.getInventory().deleteItem(27344 ,1);
						player.getInventory().deleteItem(27345 ,1);
						player.getInventory().deleteItem(27346 ,1);
						player.getInventory().deleteItem(27347 ,1);
						player.getInventory().deleteItem(27348 ,1);
						player.getInventory().deleteItem(27349 ,1);
						player.getInventory().deleteItem(27350 ,1);
						player.getInventory().deleteItem(27355 ,1);
					} else
						player.getEquipment().deleteItem(cape, 1);
						player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27345));	
						player.getEquipment().refresh(Equipment.SLOT_CAPE);
						player.getAppearence().generateAppearenceData();
						player.sm("Your cape has been set to yellow.");
						end();
						break;
				case OPTION_4: // Green
					if (cape == -1) {
						player.getInventory().deleteItem(27344 ,1);
						player.getInventory().deleteItem(27345 ,1);
						player.getInventory().deleteItem(27346 ,1);
						player.getInventory().deleteItem(27347 ,1);
						player.getInventory().deleteItem(27348 ,1);
						player.getInventory().deleteItem(27349 ,1);
						player.getInventory().deleteItem(27350 ,1);
						player.getInventory().deleteItem(27355 ,1);
					} else
						player.getEquipment().deleteItem(cape, 1);
						player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27346));	
						player.getEquipment().refresh(Equipment.SLOT_CAPE);
						player.getAppearence().generateAppearenceData();
						player.sm("Your cape has been set to green.");
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
				if (cape == -1) {
					player.getInventory().deleteItem(27344 ,1);
					player.getInventory().deleteItem(27345 ,1);
					player.getInventory().deleteItem(27346 ,1);
					player.getInventory().deleteItem(27347 ,1);
					player.getInventory().deleteItem(27348 ,1);
					player.getInventory().deleteItem(27349 ,1);
					player.getInventory().deleteItem(27350 ,1);
					player.getInventory().deleteItem(27355 ,1);
				} else
					player.getEquipment().deleteItem(cape, 1);
					player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27347));	
					player.getEquipment().refresh(Equipment.SLOT_CAPE);
					player.getAppearence().generateAppearenceData();
					player.sm("Your cape has been set to white.");
					end();
					break;
			case OPTION_2: // Black
				if (cape == -1) {
					player.getInventory().deleteItem(27344 ,1);
					player.getInventory().deleteItem(27345 ,1);
					player.getInventory().deleteItem(27346 ,1);
					player.getInventory().deleteItem(27347 ,1);
					player.getInventory().deleteItem(27348 ,1);
					player.getInventory().deleteItem(27349 ,1);
					player.getInventory().deleteItem(27350 ,1);
					player.getInventory().deleteItem(27355 ,1);
				} else
					player.getEquipment().deleteItem(cape, 1);
					player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27348));	
					player.getEquipment().refresh(Equipment.SLOT_CAPE);
					player.getAppearence().generateAppearenceData();
					player.sm("Your cape has been set to black.");
					end();
					break;
			case OPTION_3: // Orange
				if (cape == -1) {
					player.getInventory().deleteItem(27344 ,1);
					player.getInventory().deleteItem(27345 ,1);
					player.getInventory().deleteItem(27346 ,1);
					player.getInventory().deleteItem(27347 ,1);
					player.getInventory().deleteItem(27348 ,1);
					player.getInventory().deleteItem(27349 ,1);
					player.getInventory().deleteItem(27350 ,1);
					player.getInventory().deleteItem(27355 ,1);
				} else
					player.getEquipment().deleteItem(cape, 1);
					player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27349));	
					player.getEquipment().refresh(Equipment.SLOT_CAPE);
					player.getAppearence().generateAppearenceData();
					player.sm("Your cape has been set to orange.");
					end();
					break;
			case OPTION_4: // Purple
				if (cape == -1) {
					player.getInventory().deleteItem(27344 ,1);
					player.getInventory().deleteItem(27345 ,1);
					player.getInventory().deleteItem(27346 ,1);
					player.getInventory().deleteItem(27347 ,1);
					player.getInventory().deleteItem(27348 ,1);
					player.getInventory().deleteItem(27349 ,1);
					player.getInventory().deleteItem(27350 ,1);
					player.getInventory().deleteItem(27355 ,1);
				} else
					player.getEquipment().deleteItem(cape, 1);
					player.getEquipment().getItems().set(Equipment.SLOT_CAPE, new Item(27350));	
					player.getEquipment().refresh(Equipment.SLOT_CAPE);
					player.getAppearence().generateAppearenceData();
					player.sm("Your cape has been set to purple.");
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


