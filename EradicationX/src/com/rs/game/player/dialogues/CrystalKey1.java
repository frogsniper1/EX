package com.rs.game.player.dialogues;

import com.rs.game.item.Item;
import com.rs.utils.Colors;
import com.rs.utils.Utils;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class CrystalKey1 extends Dialogue {

	public CrystalKey1() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Crystal Key Chest",
				"Open Crystal Key Chest",
				"Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				if (player.getInventory().containsItem(989, 1)) {
					int i = 0;
					Item item = new Item(1);
					int[] CrystalKeyItems = {25056, 14577, 14578, 14579, 19749, 24154, 24155, 9013, 13642, 18747, 9084, 9096, 9097, 9098, 9099, 9100, 9101, 9102, 13105, 23713, 23714, 29944, 29945, 29946};
					int[] CrystalKeyItemsQ5 = {1464};
					int[] CrystalKeyItemsQ15 = {23352, 26737, 23400};
					int[] CrystalKeyItemsQ50 = {19819};
					int[] CrystalKeyItemsQ100 = {15263};
					int[] CrystalKeyItemsQ150 = {12852};
					int[] CrystalKeyItemsQ500 = {15273};
					switch (Utils.getRandom(7)) {
					case 0:
						i = Utils.getRandom(CrystalKeyItems.length -1);
						item = new Item(CrystalKeyItems[i]);
						break;
					case 1:
						i = Utils.getRandom(CrystalKeyItems.length -1);
						item = new Item(CrystalKeyItems[i]);
						break;
					case 2:
						i = Utils.getRandom(CrystalKeyItemsQ5.length -1);
						item = new Item(CrystalKeyItemsQ5[i], 3);
						break;
					case 3:
						i = Utils.getRandom(CrystalKeyItemsQ15.length -1);
						item = new Item(CrystalKeyItemsQ15[i], 15);
						break;
					case 4:
						i = Utils.getRandom(CrystalKeyItemsQ50.length -1);
						item = new Item(CrystalKeyItemsQ50[i], 50);
						break;
					case 5:
						i = Utils.getRandom(CrystalKeyItemsQ100.length -1);
						item = new Item(CrystalKeyItemsQ100[i], 100);
						break;
					case 6:
						i = Utils.getRandom(CrystalKeyItemsQ150.length -1);
						item = new Item(CrystalKeyItemsQ150[i], 150);
						break;
					case 7:
						i = Utils.getRandom(CrystalKeyItemsQ500.length -1);
						item = new Item(CrystalKeyItemsQ500[i], 500);
						break;
					}
				if (player.getPerksManager().keyExpert && Utils.random(100) >= 80) {
					player.sm(Colors.REALORANGE+"[PerksManager] Your Key Expert perk magically retain the Ckey!");
				}else{
					player.getInventory().deleteItem(989, 1);
				}
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().addItem(item);
				return;
				} else  {	
				player.sm("You do not have Crystal Key");
				player.getInterfaceManager().closeChatBoxInterface();				
				return;
				}
			} else if (componentId == OPTION_2) {
				player.sm("You decided to keep your Crystal Key.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}
	}
	


	@Override
	public void finish() {
	}

}