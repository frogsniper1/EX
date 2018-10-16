package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.Equipment;
import com.rs.game.item.Item;
import com.rs.net.decoders.handlers.ButtonHandler;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class Starter extends Dialogue {

	public Starter() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Welcome to EradicationX!", "Let's Play! (Skip Tut)", "Let's Play! (Opens Help)"
				);

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
			for (Player players: World.getPlayers()) {
	players.getPackets().sendGameMessage("<img=5>[Player Feed] <col=B40404> " + player.getDisplayName() + " </col> has joined <col=B40404>EradicationX!</col>");
			}					
			player.getEquipment().getItems()
					.set(Equipment.SLOT_CAPE, new Item(24299));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_HAT, new Item(1155));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_FEET, new Item(3105));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_WEAPON, new Item(1321));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_CHEST, new Item(1117));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_LEGS, new Item(1075));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_AMULET, new Item(1712));
				player.getInventory().addItem(995, 30000000);
				player.getInventory().addItem(4587, 1);
				player.getInventory().addItem(1323, 1);
				player.getInventory().addItem(10551, 1);
				player.getInventory().addItem(1079, 1);
				player.getInventory().addItem(25755, 1);
				player.getInventory().addItem(22340, 2);
				player.getInventory().addItem(10828, 1);				
				player.getInventory().addItem(1333, 1);
				player.getInventory().addItem(7462, 1);
				player.getInventory().addItem(11732, 1);
				player.getBank().addItem(863, 1200, true);
				player.getBank().addItem(868, 1500, true);
				player.getBank().addItem(892, 1200, true);
				player.getBank().addItem(9185, 1, true);
				player.getBank().addItem(8734, 1, true);
				player.getBank().addItem(24379, 1, true);
				player.getBank().addItem(24382, 1, true);
				player.getBank().addItem(861, 1, true);
				player.getBank().addItem(6177, 1, true);
				player.getBank().addItem(25034, 1, true);
				player.getBank().addItem(2445, 1000, true);
				player.getBank().addItem(882, 1000, true);
				player.getBank().addItem(841, 1, true);
				player.getBank().addItem(15272, 1000, true);
				player.getBank().addItem(9245, 850, true);
				player.getBank().addItem(554, 5000, true);
				player.getBank().addItem(555, 5000, true);
				player.getBank().addItem(556, 5000, true);
				player.getBank().addItem(557, 5000, true);
				player.getBank().addItem(558, 5000, true);
				player.getBank().addItem(559, 5000, true);
				player.getBank().addItem(560, 5000, true);
				player.getBank().addItem(561, 5000, true);
				player.getBank().addItem(562, 5000, true);
				player.getBank().addItem(563, 5000, true);
				player.getBank().addItem(564, 5000, true);
				player.getBank().addItem(565, 5000, true);
				player.getBank().addItem(566, 5000, true);				
				player.getEquipment().refresh(0);
				player.getEquipment().refresh(1);
				player.getEquipment().refresh(2);
				player.getEquipment().refresh(3);
				player.getEquipment().refresh(4);
				player.getEquipment().refresh(5);
				player.getEquipment().refresh(6);
				player.getEquipment().refresh(7);
				player.getEquipment().refresh(8);
				player.getEquipment().refresh(9);
				player.getEquipment().refresh(10);
				player.getEquipment().refresh(11);
				player.getEquipment().refresh(12);
				player.getEquipment().refresh(13);
				player.getEquipment().refresh(14);
				player.getEquipment().refresh();
				ButtonHandler.refreshEquipBonuses(player);
				player.getAppearence().generateAppearenceData();				
				player.getInventory().refresh();
				player.unlock();
				player.sm("Supplies for Magic and Ranged have been added to your bank!");
				player.getPackets().sendGameMessage("<col=084FA1> Start playing by clicking the Quest Tab that has a picture of 'EX!'");					
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
			for (Player players: World.getPlayers()) {
	players.getPackets().sendGameMessage("<img=5>[Player Feed] "+players.fontColor()+" " + player.getDisplayName() + " </col> has joined "+players.fontColor()+"EradicationX!</col>");
			}			
					player.getPackets().sendGameMessage(""+player.fontColor()+" Start playing by clicking the Quest Tab that has a picture of 'EX!'");			
			player.getEquipment().getItems()
					.set(Equipment.SLOT_CAPE, new Item(24299));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_HAT, new Item(1155));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_HANDS, new Item(7462));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_RING, new Item(2552));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_FEET, new Item(3105));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_WEAPON, new Item(1321));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_CHEST, new Item(1117));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_LEGS, new Item(1075));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_SHIELD, new Item(3842));
			player.getEquipment().getItems()
					.set(Equipment.SLOT_AMULET, new Item(1712));
				player.getInventory().addItem(995, 30000000);
				player.getInventory().addItem(4587, 1);
				player.getInventory().addItem(1323, 1);
				player.getInventory().addItem(10551, 1);
				player.getInventory().addItem(1079, 1);
				player.getInventory().addItem(25755, 1);
				player.getInventory().addItem(22340, 2);
				player.getInventory().addItem(1333, 1);
				player.getInventory().addItem(7462, 1);
				player.getInventory().addItem(11732, 1);
				player.getBank().addItem(863, 1200, true);
				player.getBank().addItem(868, 1500, true);
				player.getBank().addItem(892, 1200, true);
				player.getBank().addItem(9185, 1, true);
				player.getBank().addItem(8734, 1, true);
				player.getBank().addItem(24379, 1, true);
				player.getBank().addItem(24382, 1, true);
				player.getBank().addItem(861, 1, true);
				player.getBank().addItem(6177, 1, true);
				player.getBank().addItem(25034, 1, true);
				player.getBank().addItem(2445, 1000, true);
				player.getBank().addItem(882, 1000, true);
				player.getBank().addItem(841, 1, true);
				player.getBank().addItem(15272, 1000, true);
				player.getBank().addItem(405, 2, true);
				player.getBank().addItem(9245, 850, true);
				player.getBank().addItem(554, 5000, true);
				player.getBank().addItem(555, 5000, true);
				player.getBank().addItem(556, 5000, true);
				player.getBank().addItem(557, 5000, true);
				player.getBank().addItem(558, 5000, true);
				player.getBank().addItem(559, 5000, true);
				player.getBank().addItem(560, 5000, true);
				player.getBank().addItem(561, 5000, true);
				player.getBank().addItem(562, 5000, true);
				player.getBank().addItem(563, 5000, true);
				player.getBank().addItem(564, 5000, true);
				player.getBank().addItem(565, 5000, true);
				player.getBank().addItem(566, 5000, true);				
				player.getEquipment().refresh(0);
				player.getEquipment().refresh(1);
				player.getEquipment().refresh(2);
				player.getEquipment().refresh(3);
				player.getEquipment().refresh(4);
				player.getEquipment().refresh(5);
				player.getEquipment().refresh(6);
				player.getEquipment().refresh(7);
				player.getEquipment().refresh(8);
				player.getEquipment().refresh(9);
				player.getEquipment().refresh(10);
				player.getEquipment().refresh(11);
				player.getEquipment().refresh(12);
				player.getEquipment().refresh(13);
				player.getEquipment().refresh(14);
				player.getEquipment().refresh();
				ButtonHandler.refreshEquipBonuses(player);
				player.getAppearence().generateAppearenceData();				
				player.getInventory().refresh();
				player.unlock();
				//player.getPackets().sendOpenURL("http://eradicationx.com/forums/index.php?/topic/35-server-rules/#entry87");				
				player.sm("Your current combat class is: Fighter.");
				player.sm("Supplies for Magic and Ranged have been added to your bank!");				
				player.getInterfaceManager().closeChatBoxInterface();			
			
			
			}
		}
	}


	@Override
	public void finish() {
	}

}
