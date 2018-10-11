package com.rs.game.player;

import com.rs.game.player.QuestManager.Quests;

public class RewardRequirements {
	
	private static int component;
	
	public static void handleButtons(Player player, int componentId) {
		switch (componentId) {
		case 1: // Close
			player.closeInterfaces();
			break;
		case 2: 
		case 3:
		case 4:
		case 5:
			component = componentId;
			openBuy(player);			
			break;
		case 6: // View Requirements for skiller cape
			sendRequirements(player, componentId);
			break;
		case 7: // View Requirements for max cape
			sendRequirements(player, componentId);
			break;
		case 8: // View Requirements for skiller master cape
			sendRequirements(player, componentId);
			break;
		case 9: // View Requirements for 200m master cape
			sendRequirements(player, componentId);
			break;
		case 10:
		case 11:
		case 12:	
			component = componentId;
			openBuy(player);		
			break;
		case 13: // View Requirements for completionist cape
			sendRequirements(player, componentId);
			break;
		case 14: // View Requirements for 10b xp cape
			sendRequirements(player, componentId);
			break;
		case 15: // View Requirements for amulet of completion
			player.sm("Click the trophy in your game tab to view the requirements for this item.");		
			break;
		}
	}

	public static void openBuy(Player player) {
		switch (component) {
		case 2: // Skiller cape
			player.getDialogueManager().startDialogue("SkillerCapePurchase");
			break;
		case 3: // Max cape
			player.getDialogueManager().startDialogue("MaxCapePurchase");
			break;
		case 4: // Skiller master cape
			player.getDialogueManager().startDialogue("SkillerMasterPurchase");
			break;
		case 5: // 200M Master cape
			player.getDialogueManager().startDialogue("200mMasterPurchase");
			break;
		case 10: // Completionist Cape
			player.getDialogueManager().startDialogue("CompletionistPurchase");
			break;
		case 11: // 10B XP Cape
			player.getDialogueManager().startDialogue("10BPurchase");
			break;
		case 12: // Amulet of completion
			player.getDialogueManager().startDialogue("AmuletofCompletionPurchase");
			break;
		}
	}
	
	private static void sendRequirements(Player player, int componentId) {
		player.getInterfaceManager().sendInterface(275);
		for (int i = 10; i < 310; i++)
			player.sit(275, i, "");
		switch (componentId) {

		case 6:
			player.sit(275, 1, "Requirements for Skiller cape");
			player.sit(275, 12, (player.getSkills().getLevelForXp(Skills.AGILITY) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Agility");
			player.sit(275, 13, (player.getSkills().getLevelForXp(Skills.CONSTRUCTION) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Construction");
			player.sit(275, 14, (player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Runecrafting");
			player.sit(275, 15, (player.getSkills().getLevelForXp(Skills.FISHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Fishing");
			player.sit(275, 16, (player.getSkills().getLevelForXp(Skills.AGILITY) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Agility");
			player.sit(275, 17, (player.getSkills().getLevelForXp(Skills.COOKING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Cooking");
			player.sit(275, 18, (player.getSkills().getLevelForXp(Skills.THIEVING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Thieving");
			player.sit(275, 19, (player.getSkills().getLevelForXp(Skills.MINING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Mining");
			player.sit(275, 20, (player.getSkills().getLevelForXp(Skills.SMITHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Smithing");
			player.sit(275, 21, (player.getSkills().getLevelForXp(Skills.FARMING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Farming");
			player.sit(275, 22, (player.getSkills().getLevelForXp(Skills.HUNTER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Hunter");
			player.sit(275, 23, (player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Crafting");
			player.sit(275, 24, (player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Woodcutting");
			player.sit(275, 25, (player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Firemaking");
			player.sit(275, 26, (player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Fletching");
			player.sit(275, 27, (player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Herblore");
			break;
		case 7:
			player.sit(275, 1, "Requirements for Max cape");	
			player.sit(275, 12, (player.getSkills().getLevelForXp(Skills.ATTACK) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Attack");		
			player.sit(275, 13, (player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Strength");		
			player.sit(275, 14, (player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Defence");		
			player.sit(275, 15, (player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Hitpoints");		
			player.sit(275, 16, (player.getSkills().getLevelForXp(Skills.RANGE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Range");		
			player.sit(275, 17, (player.getSkills().getLevelForXp(Skills.MAGIC) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Magic");		
			player.sit(275, 18, (player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Runecrafting");		
			player.sit(275, 19, (player.getSkills().getLevelForXp(Skills.FISHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Fishing");		
			player.sit(275, 20, (player.getSkills().getLevelForXp(Skills.AGILITY) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Agility");		
			player.sit(275, 21, (player.getSkills().getLevelForXp(Skills.COOKING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Cooking");		
			player.sit(275, 22, (player.getSkills().getLevelForXp(Skills.PRAYER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Prayer");		
			player.sit(275, 23, (player.getSkills().getLevelForXp(Skills.THIEVING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Thieving");		
			player.sit(275, 24, (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Dungeoneering");		
			player.sit(275, 25, (player.getSkills().getLevelForXp(Skills.MINING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Mining");		
			player.sit(275, 26, (player.getSkills().getLevelForXp(Skills.SMITHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Smithing");		
			player.sit(275, 27, (player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Summoning");		
			player.sit(275, 28, (player.getSkills().getLevelForXp(Skills.FARMING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Farming");		
			player.sit(275, 29, (player.getSkills().getLevelForXp(Skills.HUNTER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Hunter");		
			player.sit(275, 30, (player.getSkills().getLevelForXp(Skills.SLAYER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Slayer");		
			player.sit(275, 31, (player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Crafting");		
			player.sit(275, 32, (player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Woodcutting");		
			player.sit(275, 33, (player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Firemaking");		
			player.sit(275, 34, (player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Fletching");		
			player.sit(275, 35, (player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Herblore");		
			break;
		case 8:
			player.sit(275, 1, "Requirements for Skiller master cape");	
			player.sit(275, 12, (player.getSkills().getXp(Skills.AGILITY) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Agility");
			player.sit(275, 13, (player.getSkills().getXp(Skills.CONSTRUCTION) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Construction");
			player.sit(275, 14, (player.getSkills().getXp(Skills.RUNECRAFTING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Runecrafting");
			player.sit(275, 15, (player.getSkills().getXp(Skills.FISHING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Fishing");
			player.sit(275, 16, (player.getSkills().getXp(Skills.AGILITY) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Agility");
			player.sit(275, 17, (player.getSkills().getXp(Skills.COOKING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Cooking");
			player.sit(275, 18, (player.getSkills().getXp(Skills.THIEVING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Thieving");
			player.sit(275, 19, (player.getSkills().getXp(Skills.MINING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Mining");
			player.sit(275, 20, (player.getSkills().getXp(Skills.SMITHING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Smithing");
			player.sit(275, 21, (player.getSkills().getXp(Skills.FARMING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Farming");
			player.sit(275, 22, (player.getSkills().getXp(Skills.HUNTER) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Hunter");
			player.sit(275, 23, (player.getSkills().getXp(Skills.CRAFTING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Crafting");
			player.sit(275, 24, (player.getSkills().getXp(Skills.WOODCUTTING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Woodcutting");
			player.sit(275, 25, (player.getSkills().getXp(Skills.FIREMAKING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Firemaking");
			player.sit(275, 26, (player.getSkills().getXp(Skills.FLETCHING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Fletching");
			player.sit(275, 27, (player.getSkills().getXp(Skills.HERBLORE) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Herblore");			
			break;
		case 9:
			player.sit(275, 1, "Requirements for 200M mastercape");	
			player.sit(275, 12, (player.getSkills().getXp(Skills.ATTACK) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Attack");		
			player.sit(275, 13, (player.getSkills().getXp(Skills.STRENGTH) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Strength");		
			player.sit(275, 14, (player.getSkills().getXp(Skills.DEFENCE) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Defence");		
			player.sit(275, 15, (player.getSkills().getXp(Skills.HITPOINTS) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Hitpoints");		
			player.sit(275, 16, (player.getSkills().getXp(Skills.RANGE) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Range");		
			player.sit(275, 17, (player.getSkills().getXp(Skills.MAGIC) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Magic");		
			player.sit(275, 18, (player.getSkills().getXp(Skills.RUNECRAFTING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Runecrafting");		
			player.sit(275, 19, (player.getSkills().getXp(Skills.FISHING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Fishing");		
			player.sit(275, 20, (player.getSkills().getXp(Skills.AGILITY) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Agility");		
			player.sit(275, 21, (player.getSkills().getXp(Skills.COOKING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Cooking");		
			player.sit(275, 22, (player.getSkills().getXp(Skills.PRAYER) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Prayer");		
			player.sit(275, 23, (player.getSkills().getXp(Skills.THIEVING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Thieving");		
			player.sit(275, 24, (player.getSkills().getXp(Skills.DUNGEONEERING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Dungeoneering");		
			player.sit(275, 25, (player.getSkills().getXp(Skills.MINING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Mining");		
			player.sit(275, 26, (player.getSkills().getXp(Skills.SMITHING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Smithing");		
			player.sit(275, 27, (player.getSkills().getXp(Skills.SUMMONING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Summoning");		
			player.sit(275, 28, (player.getSkills().getXp(Skills.FARMING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Farming");		
			player.sit(275, 29, (player.getSkills().getXp(Skills.HUNTER) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Hunter");		
			player.sit(275, 30, (player.getSkills().getXp(Skills.SLAYER) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Slayer");		
			player.sit(275, 31, (player.getSkills().getXp(Skills.CRAFTING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Crafting");		
			player.sit(275, 32, (player.getSkills().getXp(Skills.WOODCUTTING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Woodcutting");		
			player.sit(275, 33, (player.getSkills().getXp(Skills.FIREMAKING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Firemaking");		
			player.sit(275, 34, (player.getSkills().getXp(Skills.FLETCHING) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Fletching");		
			player.sit(275, 35, (player.getSkills().getXp(Skills.HERBLORE) >= 200000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 200M XP for Herblore");		
			break;
		case 13:
			player.sit(275, 1, "Requirements for Completionist cape");	
			player.sit(275, 12, (player.isCompletedFightKiln() ? "<col=0DFF00>" : "<col=FF0000>") + "- Obtain a Kiln cape");		
			player.sit(275, 13, (player.isCompletedFightCaves() ? "<col=0DFF00>" : "<col=FF0000>") + "- Obtain a fire cape");		
			player.sit(275, 14, (player.isKilledQueenBlackDragon() ? "<col=0DFF00>" : "<col=FF0000>") + "- Kill Queen Black Dragon");		
			player.sit(275, 15, (player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM) ? "<col=0DFF00>" : "<col=FF0000>") + "- Kill Nomad");		
			player.sit(275, 16, (player.getSkills().getLevelForXp(Skills.ATTACK) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Attack");		
			player.sit(275, 17, (player.getSkills().getLevelForXp(Skills.STRENGTH) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Strength");		
			player.sit(275, 18, (player.getSkills().getLevelForXp(Skills.DEFENCE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Defence");		
			player.sit(275, 19, (player.getSkills().getLevelForXp(Skills.HITPOINTS) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Hitpoints");		
			player.sit(275, 20, (player.getSkills().getLevelForXp(Skills.RANGE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Range");		
			player.sit(275, 21, (player.getSkills().getLevelForXp(Skills.MAGIC) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Magic");		
			player.sit(275, 22, (player.getSkills().getLevelForXp(Skills.RUNECRAFTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Runecrafting");		
			player.sit(275, 23, (player.getSkills().getLevelForXp(Skills.FISHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Fishing");		
			player.sit(275, 24, (player.getSkills().getLevelForXp(Skills.AGILITY) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Agility");		
			player.sit(275, 25, (player.getSkills().getLevelForXp(Skills.COOKING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Cooking");		
			player.sit(275, 26, (player.getSkills().getLevelForXp(Skills.PRAYER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Prayer");		
			player.sit(275, 27, (player.getSkills().getLevelForXp(Skills.THIEVING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Thieving");		
			player.sit(275, 28, (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) >= 120 ? "<col=0DFF00>" : "<col=FF0000>") + "- 120 Dungeoneering");		
			player.sit(275, 29, (player.getSkills().getLevelForXp(Skills.MINING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Mining");		
			player.sit(275, 30, (player.getSkills().getLevelForXp(Skills.SMITHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Smithing");		
			player.sit(275, 31, (player.getSkills().getLevelForXp(Skills.SUMMONING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Summoning");		
			player.sit(275, 32, (player.getSkills().getLevelForXp(Skills.FARMING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Farming");		
			player.sit(275, 33, (player.getSkills().getLevelForXp(Skills.HUNTER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Hunter");		
			player.sit(275, 34, (player.getSkills().getLevelForXp(Skills.SLAYER) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Slayer");		
			player.sit(275, 35, (player.getSkills().getLevelForXp(Skills.CRAFTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Crafting");		
			player.sit(275, 36, (player.getSkills().getLevelForXp(Skills.WOODCUTTING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Woodcutting");		
			player.sit(275, 37, (player.getSkills().getLevelForXp(Skills.FIREMAKING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Firemaking");		
			player.sit(275, 38, (player.getSkills().getLevelForXp(Skills.FLETCHING) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Fletching");		
			player.sit(275, 39, (player.getSkills().getLevelForXp(Skills.HERBLORE) >= 99 ? "<col=0DFF00>" : "<col=FF0000>") + "- 99 Herblore");		
			break;
		case 14:
			player.sit(275, 1, "Requirements for 10B XP cape");	
			player.sit(275, 12, (player.getSkills().getXp(Skills.ATTACK) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Attack");		
			player.sit(275, 13, (player.getSkills().getXp(Skills.STRENGTH) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Strength");		
			player.sit(275, 14, (player.getSkills().getXp(Skills.DEFENCE) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Defence");		
			player.sit(275, 15, (player.getSkills().getXp(Skills.HITPOINTS) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Hitpoints");		
			player.sit(275, 16, (player.getSkills().getXp(Skills.RANGE) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Range");		
			player.sit(275, 17, (player.getSkills().getXp(Skills.MAGIC) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Magic");		
			player.sit(275, 18, (player.getSkills().getXp(Skills.RUNECRAFTING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Runecrafting");		
			player.sit(275, 19, (player.getSkills().getXp(Skills.FISHING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Fishing");		
			player.sit(275, 20, (player.getSkills().getXp(Skills.AGILITY) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Agility");		
			player.sit(275, 21, (player.getSkills().getXp(Skills.COOKING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Cooking");		
			player.sit(275, 22, (player.getSkills().getXp(Skills.PRAYER) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Prayer");		
			player.sit(275, 23, (player.getSkills().getXp(Skills.THIEVING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Thieving");		
			player.sit(275, 24, (player.getSkills().getXp(Skills.DUNGEONEERING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Dungeoneering");		
			player.sit(275, 25, (player.getSkills().getXp(Skills.MINING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Mining");		
			player.sit(275, 26, (player.getSkills().getXp(Skills.SMITHING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Smithing");		
			player.sit(275, 27, (player.getSkills().getXp(Skills.SUMMONING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Summoning");		
			player.sit(275, 28, (player.getSkills().getXp(Skills.FARMING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Farming");		
			player.sit(275, 29, (player.getSkills().getXp(Skills.HUNTER) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Hunter");		
			player.sit(275, 30, (player.getSkills().getXp(Skills.SLAYER) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Slayer");		
			player.sit(275, 31, (player.getSkills().getXp(Skills.CRAFTING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Crafting");		
			player.sit(275, 32, (player.getSkills().getXp(Skills.WOODCUTTING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Woodcutting");		
			player.sit(275, 33, (player.getSkills().getXp(Skills.FIREMAKING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Firemaking");		
			player.sit(275, 34, (player.getSkills().getXp(Skills.FLETCHING) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Fletching");		
			player.sit(275, 35, (player.getSkills().getXp(Skills.HERBLORE) >= 400000000 ? "<col=0DFF00>" : "<col=FF0000>") + "- 400M XP for Herblore");					
			break;
		}
	}
	
}