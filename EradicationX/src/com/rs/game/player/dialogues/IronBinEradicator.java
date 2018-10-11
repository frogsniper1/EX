package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class IronBinEradicator extends Dialogue {

	public IronBinEradicator() {
	}

	@Override
	public void start() {
		if (player.getDeposittedBones() > 25000)
			player.setDeposittedBones(0);
		stage = 1;
		sendOptionsDialogue("What would you like to redeem? You have " + player.getDeposittedBones() + " bones in the grinder.",
				"Enchanting Potion", "Nevermind");
		

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getDeposittedBones() < 100) {
					player.sm("You don't have enough Eradicator Bones for an Enchanting Potion, you need 100 bones. ");
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (player.getDeposittedBones() >= 100){
					player.setDeposittedBones(player.getDeposittedBones() - 100);
					player.sm("You bought an Enchanting Potion for 100 Eradicator Bones. This Potion can be used on an Overload to make an Eradicator Potion, you can also use it on Obsidian Shards to enchant them in order to make them useable in the store.");
					player.getInventory().addItem(29976, 1);
				}				
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {			
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} 
	}
	


	@Override
	public void finish() {
	}

}


