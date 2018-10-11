package com.rs.game.player.dialogues;

import com.rs.game.player.quest.QNames;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class BinEradicator extends Dialogue {

	public BinEradicator() {
	}

	@Override
	public void start() {
		if (player.getDeposittedBones() > 25000)
			player.setDeposittedBones(0);
		stage = 1;
		sendOptionsDialogue("What would you like to redeem? You have " + player.getDeposittedBones() + " bones in the grinder.",
				"<img=18> Experience",
				"<img=18> Money","<img=18> Enchanting Potion", "Spell Power");
		

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendOptionsDialogue("Spend 35 Eradicated Bones on 1 Huge XP Lamp?", "Give 35 Eradicated Bones for 1 Huge lamp", "Nevermind");
			} else if (componentId == OPTION_2) {
				player.getTemporaryAttributtes().put("boneamountmoney", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on coins:" });				
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				if (player.getDeposittedBones() < 100) {
					player.sm("You don't have enough Eradicator Bones for an Enchanting Potion, you need 100 bones. ");
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (player.getDeposittedBones() >= 100){
					player.setDeposittedBones(player.getDeposittedBones() - 100);
					player.sm("You bought an Enchanting Potion for 100 Eradicator Bones. This Potion can be used on an Overload to make an Eradicator Potion, you can also use it on Obsidian Shards to enchant them in order to make them useable in the store.");
					player.getInventory().addItem(29976, 1);
				}				
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				if (player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_III)) {
					stage = 10;
					sendOptionsDialogue("How much would you like to buy?", "1 Spell Power [15 Bones]", "5 Spell Power [75 Bones]",
																			"10 Spell Power [150 Bones]", "25 Spell Power [375 Bones]",
																			"50 Spell Power [750 Bones]");
				} else {
					stage = 100;
					sendDialogue("You need to complete The Elite: Chapter III for this option.");
				}
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().hasFreeSlots()) {
				if (player.getDeposittedBones() >= 35) {
					player.setDeposittedBones(player.getDeposittedBones() - 35); 
					player.getInventory().addItem(23716, 1);
					end();
				} else {
					stage = 100;
					sendDialogue("You don't have 35 Eradicated bones.");
				}
				} else {
					stage = 100;
					sendDialogue("You must free up your inventory space.");
				}
			} else if (componentId == OPTION_2) {
				end();
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) { // Attack
				player.getTemporaryAttributtes().put("boneamountattack", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Strength
				player.getTemporaryAttributtes().put("boneamountstrength", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Defence
				player.getTemporaryAttributtes().put("boneamountdefence", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Constitution
				player.getTemporaryAttributtes().put("boneamountconstitution", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_5) {
				stage = 5;
				sendOptionsDialogue("Select a Skill", "Ranged", "Prayer", "Magic", "Summoning", "Close");
			}			
		} else if (stage == 4) {
			if (componentId == OPTION_1) { // Agility
				player.getTemporaryAttributtes().put("boneamountagility", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Herblore
				player.getTemporaryAttributtes().put("boneamountherblore", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Thieving
				player.getTemporaryAttributtes().put("boneamountthieving", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Crafting
				player.getTemporaryAttributtes().put("boneamountcrafting", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 6;
				sendOptionsDialogue("Select a Skill", "Fletching", "Slayer", "Hunter", "Runecrafting", "Next Page");
			}	
		} else if (stage == 5) {
			if (componentId == OPTION_1) { // Ranged
				player.getTemporaryAttributtes().put("boneamountranged", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Prayer
				player.getTemporaryAttributtes().put("boneamountprayer", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Magic
				player.getTemporaryAttributtes().put("boneamountmagic", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Summoning
				player.getTemporaryAttributtes().put("boneamountsummoning", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();	
			}			
		} else if (stage == 6) {
			if (componentId == OPTION_1) { // Fletching
				player.getTemporaryAttributtes().put("boneamountfletching", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Slayer
				player.getTemporaryAttributtes().put("boneamountslayer", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Hunter
				player.getTemporaryAttributtes().put("boneamounthunter", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Runecrafting
				player.getTemporaryAttributtes().put("boneamountrunecrafting", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 7;
				sendOptionsDialogue("Select a Skill", "Dungeoneering", "Construction", "Mining", "Smithing", "Next Page");
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1) { // Dungeoneering
				player.getTemporaryAttributtes().put("boneamountdungeoneering", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Construction
				player.getTemporaryAttributtes().put("boneamountconstruction", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Mining
				player.getTemporaryAttributtes().put("boneamountmining", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Smithing
				player.getTemporaryAttributtes().put("boneamountsmithing", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 8;
				sendOptionsDialogue("Select a Skill", "Fishing", "Cooking", "Firemaking", "Woodcutting", "Next Page");
			}	
		} else if (stage == 8) {
			if (componentId == OPTION_1) { // Fishing
				player.getTemporaryAttributtes().put("boneamountfishing", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Cooking
				player.getTemporaryAttributtes().put("boneamountcooking", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Firemaking
				player.getTemporaryAttributtes().put("boneamountfiremaking", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Woodcutting
				player.getTemporaryAttributtes().put("boneamountwoodcutting", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 9;
				sendOptionsDialogue("Select a Skill", "Farming", "Back to first page", "Back to Main", "Close");
			}			
		} else if (stage == 9) {
			if (componentId == OPTION_1) { // Farming
				player.getTemporaryAttributtes().put("boneamountfarming", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) {
				stage = 4;			
			} else if (componentId == OPTION_3) {
				stage = 2;
			} else if (componentId == OPTION_4) {	
				player.getInterfaceManager().closeChatBoxInterface();			
			}			
		} else if (stage == 10) {
			if (componentId == OPTION_1) {
				if (player.getDeposittedBones() < 15) {
					stage = 11;
					sendDialogue("Not enough Eradicator Bones.");
				} else if (player.getDeposittedBones() >= 15){
					player.setDeposittedBones(player.getDeposittedBones() - 15);
					player.setSpellPower(player.getSpellPower()+1);
					sendDialogue("You receive 1 Spell power.");
					stage = 11;
				}				
			} else if (componentId == OPTION_2) {
				if (player.getDeposittedBones() < 75) {
					stage = 11;
					sendDialogue("Not enough Eradicator Bones.");
				} else if (player.getDeposittedBones() >= 75){
					player.setDeposittedBones(player.getDeposittedBones() - 75);
					player.setSpellPower(player.getSpellPower()+5);
					sendDialogue("You receive 5 Spell power.");
					stage = 11;
				}				
			} else if (componentId == OPTION_3) {
				if (player.getDeposittedBones() < 150) {
					stage = 11;
					sendDialogue("Not enough Eradicator Bones.");
				} else if (player.getDeposittedBones() >= 150){
					player.setDeposittedBones(player.getDeposittedBones() - 150);
					player.setSpellPower(player.getSpellPower()+10);
					sendDialogue("You receive 10 Spell power.");
					stage = 11;
				}	
			} else if (componentId == OPTION_4) {
				if (player.getDeposittedBones() < 375) {
					stage = 11;
					sendDialogue("Not enough Eradicator Bones.");
				} else if (player.getDeposittedBones() >= 375){
					player.setDeposittedBones(player.getDeposittedBones() - 375);
					player.setSpellPower(player.getSpellPower()+25);
					sendDialogue("You receive 25 Spell power.");
					stage = 11;
				}	
			} else if (componentId == OPTION_5) {
				if (player.getDeposittedBones() < 750) {
					stage = 11;
					sendDialogue("Not enough Eradicator Bones.");
				} else if (player.getDeposittedBones() >= 750){
					player.setDeposittedBones(player.getDeposittedBones() - 750);
					player.setSpellPower(player.getSpellPower()+50);
					sendDialogue("You receive 50 Spell power.");
					stage = 11;
				}	
			}
		} else if (stage == 11) {
			stage = 1;
			sendOptionsDialogue("What would you like to redeem? You have " + player.getDeposittedBones() + " bones in the grinder.",
					"<img=18> Experience",
					"<img=18> Money","<img=18> Enchanting Potion", "Spell Power");
		} else if (stage == 100)
			end();
	}
	


	@Override
	public void finish() {
	}

}


