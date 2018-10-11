package com.rs.game.player.dialogues;

import com.rs.game.player.quest.QNames;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class BinSuper extends Dialogue {

	public BinSuper() {
	}

	@Override
	public void start() {
		if (player.getSuperDeposittedBones() > 25000)
			player.setSuperDeposittedBones(0);
		stage = 1;
		sendOptionsDialogue("What would you like to redeem? You have " + player.getSuperDeposittedBones() + " bones in the grinder.",
				"<img=9> Experience",
				"<img=9> Money", "Spell Power");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				stage = 2;
				sendOptionsDialogue("Spend 105 Super Bones on 1 Huge XP Lamp?", "Give 105 Super Bones for 1 Huge lamp", "Nevermind");
			} else if (componentId == OPTION_2) {
				player.getTemporaryAttributtes().put("superboneamtmoney", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on coins:" });				
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				if (player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_III)) {
					stage = 10;
					sendOptionsDialogue("How much would you like to buy?", "1 Spell Power [25 Bones]", "5 Spell Power [125 Bones]",
																			"10 Spell Power [250 Bones]", "25 Spell Power [625 Bones]",
																			"50 Spell Power [1250 Bones]");
				} else {
					stage = 100;
					sendDialogue("You need to complete The Elite: Chapter III for this option.");
				}
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				if (player.getInventory().hasFreeSlots()) {
				if (player.getSuperDeposittedBones() >= 105) {
					player.setSuperDeposittedBones(player.getSuperDeposittedBones() - 35); 
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
				player.getTemporaryAttributtes().put("superboneamtattack", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Strength
				player.getTemporaryAttributtes().put("superboneamtstrength", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Defence
				player.getTemporaryAttributtes().put("superboneamtdefence", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Constitution
				player.getTemporaryAttributtes().put("superboneamtconstitution", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_5) {
				stage = 5;
				sendOptionsDialogue("Select a Skill", "Ranged", "Prayer", "Magic", "Summoning", "Close");
			}			
		} else if (stage == 4) {
			if (componentId == OPTION_1) { // Agility
				player.getTemporaryAttributtes().put("superboneamtagility", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Herblore
				player.getTemporaryAttributtes().put("superboneamtherblore", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Thieving
				player.getTemporaryAttributtes().put("superboneamtthieving", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Crafting
				player.getTemporaryAttributtes().put("superboneamtcrafting", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 6;
				sendOptionsDialogue("Select a Skill", "Fletching", "Slayer", "Hunter", "Runecrafting", "Next Page");
			}	
		} else if (stage == 5) {
			if (componentId == OPTION_1) { // Ranged
				player.getTemporaryAttributtes().put("superboneamtranged", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Prayer
				player.getTemporaryAttributtes().put("superboneamtprayer", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Magic
				player.getTemporaryAttributtes().put("superboneamtmagic", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Summoning
				player.getTemporaryAttributtes().put("superboneamtsummoning", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();	
			}			
		} else if (stage == 6) {
			if (componentId == OPTION_1) { // Fletching
				player.getTemporaryAttributtes().put("superboneamtfletching", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Slayer
				player.getTemporaryAttributtes().put("superboneamtslayer", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Hunter
				player.getTemporaryAttributtes().put("superboneamthunter", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Runecrafting
				player.getTemporaryAttributtes().put("superboneamtrunecrafting", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 7;
				sendOptionsDialogue("Select a Skill", "Dungeoneering", "Construction", "Mining", "Smithing", "Next Page");
			}
		} else if (stage == 7) {
			if (componentId == OPTION_1) { // Dungeoneering
				player.getTemporaryAttributtes().put("superboneamtdungeoneering", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Construction
				player.getTemporaryAttributtes().put("superboneamtconstruction", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Mining
				player.getTemporaryAttributtes().put("superboneamtmining", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Smithing
				player.getTemporaryAttributtes().put("superboneamtsmithing", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 8;
				sendOptionsDialogue("Select a Skill", "Fishing", "Cooking", "Firemaking", "Woodcutting", "Next Page");
			}	
		} else if (stage == 8) {
			if (componentId == OPTION_1) { // Fishing
				player.getTemporaryAttributtes().put("superboneamtfishing", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_2) { // Cooking
				player.getTemporaryAttributtes().put("superboneamtcooking", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();				
			} else if (componentId == OPTION_3) { // Firemaking
				player.getTemporaryAttributtes().put("superboneamtfiremaking", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) { // Woodcutting
				player.getTemporaryAttributtes().put("superboneamtwoodcutting", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter the amount of bones you wish to use on Experience." });		
				player.getInterfaceManager().closeChatBoxInterface();			
			} else if (componentId == OPTION_5) {
				stage = 9;
				sendOptionsDialogue("Select a Skill", "Farming", "Back to first page", "Back to Main", "Close");
			}			
		} else if (stage == 9) {
			if (componentId == OPTION_1) { // Farming
				player.getTemporaryAttributtes().put("superboneamtfarming", Boolean.TRUE);
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
				if (player.getDeposittedBones() < 25) {
					stage = 11;
					sendDialogue("Not enough Super Bones.");
				} else if (player.getDeposittedBones() >= 25){
					player.setDeposittedBones(player.getDeposittedBones() - 25);
					player.setSpellPower(player.getSpellPower()+1);
					sendDialogue("You receive 1 Spell power.");
					stage = 11;
				}				
			} else if (componentId == OPTION_2) {
				if (player.getDeposittedBones() < 125) {
					stage = 11;
					sendDialogue("Not enough Super Bones.");
				} else if (player.getDeposittedBones() >= 125){
					player.setDeposittedBones(player.getDeposittedBones() - 125);
					player.setSpellPower(player.getSpellPower()+5);
					sendDialogue("You receive 5 Spell power.");
					stage = 11;
				}				
			} else if (componentId == OPTION_3) {
				if (player.getDeposittedBones() < 250) {
					stage = 11;
					sendDialogue("Not enough Super Bones.");
				} else if (player.getDeposittedBones() >= 250){
					player.setDeposittedBones(player.getDeposittedBones() - 250);
					player.setSpellPower(player.getSpellPower()+10);
					sendDialogue("You receive 10 Spell power.");
					stage = 11;
				}	
			} else if (componentId == OPTION_4) {
				if (player.getDeposittedBones() < 625) {
					stage = 11;
					sendDialogue("Not enough Super Bones.");
				} else if (player.getDeposittedBones() >= 625){
					player.setDeposittedBones(player.getDeposittedBones() - 625);
					player.setSpellPower(player.getSpellPower()+25);
					sendDialogue("You receive 25 Spell power.");
					stage = 11;
				}	
			} else if (componentId == OPTION_5) {
				if (player.getDeposittedBones() < 11250) {
					stage = 11;
					sendDialogue("Not enough Super Bones.");
				} else if (player.getDeposittedBones() >= 11250){
					player.setDeposittedBones(player.getDeposittedBones() - 11250);
					player.setSpellPower(player.getSpellPower()+50);
					sendDialogue("You receive 50 Spell power.");
					stage = 11;
				}	
			}
		}
	}
	


	@Override
	public void finish() {
	}

}


