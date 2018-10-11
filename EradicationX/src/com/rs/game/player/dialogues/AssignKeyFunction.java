package com.rs.game.player.dialogues;

import com.rs.game.player.Keybind;
import com.rs.game.player.KeybindSettings;

public class AssignKeyFunction extends Dialogue {

	public AssignKeyFunction() {
	}
	
	private int key;

	@Override
	public void start() {
		key = (Integer) parameters[0];
		stage = 1;
		sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
				"Home Teleport",
				"Boss Teleports", "Quests Interface", "Player Management", "Next Page: 2");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			switch (componentId) {
				case OPTION_1:
					player.getKeyFunction().setKeyFunction(key, 1, "Home Teleport");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_2:
					player.getKeyFunction().setKeyFunction(key, 2, "Boss Teleport");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_3:
					player.getKeyFunction().setKeyFunction(key, 3, "Quests");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_4:
					player.getKeyFunction().setKeyFunction(key, 4, "Management");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_5:
					stage = 2;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
										"100M Ticket", "Shops", "Vote", "Forums",
										"Next Page: 3");
					break;
			}
			break;
		case 2:			
			switch (componentId) {
				case OPTION_1:
					player.getKeyFunction().setKeyFunction(key, 5, "100M Ticket");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_2:
					player.getKeyFunction().setKeyFunction(key, 6, "Shop Teleport");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_3:
					player.getKeyFunction().setKeyFunction(key, 7, "Vote");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_4:
					player.getKeyFunction().setKeyFunction(key, 8, "Forums");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_5:
					stage = 3;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
										"Bank", "Dice", "Chill", "Clan Wars",
										"Next Page: 4");
					break;
			}			
			break;
		case 3:
			switch (componentId) {
				case OPTION_1:
					player.getKeyFunction().setKeyFunction(key, 9, "Bank");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_2:
					player.getKeyFunction().setKeyFunction(key, 10, "Dice");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_3:
					player.getKeyFunction().setKeyFunction(key, 11, "Chill");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_4:
					player.getKeyFunction().setKeyFunction(key, 12, "Clan Wars");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_5:
					stage = 4;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
										"Price Check Thread", "Withdraw from Money Pouch", "Withdraw from Currency Pouch", "Eradzone",
										"Next Page: 5");
					break;
			}			
			break;
		case 4:
			switch (componentId) {
				case OPTION_1:
					player.getKeyFunction().setKeyFunction(key, 13, "Price Check");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_2:
					player.getKeyFunction().setKeyFunction(key, 14, "Withdraw from money pouch");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_3:
					player.getKeyFunction().setKeyFunction(key, 15, "Withdraw from Currency Pouch");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_4:
					player.getKeyFunction().setKeyFunction(key, 16, "Eradzone");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_5:
					stage = 5;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
										"Superzone", "Raffle", "Cosmetics Manager", "Lend a rank",
										"Next Page: 6");
					break;
			}			
			break;
		case 5:
			switch (componentId) {
				case OPTION_1:
					player.getKeyFunction().setKeyFunction(key, 17, "Superzone");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_2:
					player.getKeyFunction().setKeyFunction(key, 18, "Raffle");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_3:
					player.getKeyFunction().setKeyFunction(key, 19, "Cosmetics Manager");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_4:
					player.getKeyFunction().setKeyFunction(key, 20, "Lend a rank");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_5:
					stage = 6;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
										"Borrow a rank", "Skilling", "Prayer Switches", "Erase keybind function",
										"Back to Page: 1");
					break;
			}			
			break;
		case 6:
			switch (componentId) {
				case OPTION_1:
					player.getKeyFunction().setKeyFunction(key, 21, "Borrow a rank");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_2:
					player.getKeyFunction().setKeyFunction(key, 22, "Skilling");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_3:
					stage = 8;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
							"Protect from Melee",
							"Protect from Magic", "Protect from Ranged", "Turmoil", "Next Page: 2");
					break;
				case OPTION_4:
					player.getKeyFunction().setKeyFunction(key, 0, "No command set");
					KeybindSettings.openInterface(player);
					end();
					break;
				case OPTION_5:
					stage = 1;
					sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
							"Home Teleport",
							"Boss Teleports", "Quests Interface", "Player Management", "Next Page: 2");
					break;
			}			
			break;
		case 8:
			switch (componentId) {
			case OPTION_1:
				player.getKeyFunction().setKeyFunction(key, 23, "Protect from Melee");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_2:
				player.getKeyFunction().setKeyFunction(key, 24, "Protect from Magic");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_3:
				player.getKeyFunction().setKeyFunction(key, 25, "Protect from Range");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_4:
				player.getKeyFunction().setKeyFunction(key, 26, "Turmoil");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_5:
				stage = 9;
				sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
									"Soul Split", "Protect Item", "Berserker", "Quick Prayers",
									"Back to main selection");
				break;
			}
			break;
		case 9:
			switch (componentId) {
			case OPTION_1:
				player.getKeyFunction().setKeyFunction(key, 27, "Soul Split");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_2:
				player.getKeyFunction().setKeyFunction(key, 28, "Protect Item");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_3:
				player.getKeyFunction().setKeyFunction(key, 29, "Berserker");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_4:
				player.getKeyFunction().setKeyFunction(key, 30, "Quick Prayers");
				KeybindSettings.openInterface(player);
				end();
				break;
			case OPTION_5:
				stage = 1;
				sendOptionsDialogue("Set a function with to key: " + Keybind.getKeyName(key),
						"Home Teleport",
						"Boss Teleports", "Quests Interface", "Player Management", "Next Page: 2");
				break;
			}
			break;
		}
	}
	


	@Override
	public void finish() {
	}

}

