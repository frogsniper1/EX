package com.rs.game.player.content;

import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.PriceManager;
import com.rs.game.player.controlers.CorpBeastControler;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.utils.Utils;

public class ItemsKeptOnDeath {

	private static ItemsContainer<Item> rewards = new ItemsContainer<>(28,
			false);

	public static int getRiskedWealth(ItemsContainer<Item> lostItems, Player player) {
		long amount = 0;
		for (Item item : lostItems.toArray()) {
			if (item != null) {
				if (player.getProtectPrice(item) != -1)
					amount += player.getProtectPrice(item) * item.getAmount();
				else
					amount += PriceManager.getBuyPrice(item) * item.getAmount();
			}
		}
		if (amount < 0 || amount > Integer.MAX_VALUE) {
			amount = Integer.MAX_VALUE;
		}
		return (int) amount;
	}

	public static void showInterface(Player player) {
	/*	if (!player.hasSkull()) {
			player.sm("You must be risking items to use this feature.");
			return;
		}*/
		if (player.getInterfaceManager().containsScreenInter()) {
			player.getPackets()
					.sendGameMessage(
							"Please close the interface you have opened before using Items Kept On Death interface.");
			return;
		}
		if (player.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
			player.getPackets()
					.sendGameMessage(
							"You can't open the interface until 10 seconds after the end of combat.");
			return;
		}
		CopyOnWriteArrayList<Item> containedItems = new CopyOnWriteArrayList<Item>();
		rewards.clear();
		/*
		 * keptItems.clear(); containedItems.clear();
		 */
		int keptAmount = 3;
		if (!(player.getControlerManager().getControler() instanceof CorpBeastControler)
				&& !(player.getControlerManager().getControler() instanceof CrucibleControler)) {
			keptAmount = player.hasSkull() ? 0 : 3;
		} else {
			keptAmount = 0;
		}
		if (player.getPrayer().usingPrayer(0, 10)
				|| player.getPrayer().usingPrayer(1, 0))
			keptAmount++;
		Item lastItem = new Item(1, 1);
		for (int i = 0; i < 14; i++) {
			if (player.getEquipment().getItem(i) != null
					&& player.getEquipment().getItem(i).getId() != -1
					&& player.getEquipment().getItem(i).getAmount() != -1)
				containedItems
						.add(new Item(player.getEquipment().getItem(i).getId(),
								player.getEquipment().getItem(i).getAmount()));
		}
		for (int i = 0; i < 28; i++) {
			if (player.getInventory().getItem(i) != null
					&& player.getInventory().getItem(i).getId() != -1
					&& player.getInventory().getItem(i).getAmount() != -1)
				containedItems
						.add(new Item(player.getInventory().getItem(i).getId(),
								player.getInventory().getItem(i).getAmount()));
		}
		if (containedItems.isEmpty()) {
			player.getPackets().sendGameMessage("You're not risking anything.");
			return;
		}
		if (keptAmount == 0) {
			player.getPackets()
					.sendGameMessage(
							"You're not protecting any items.");
			return;
		}
		int itemsToProtect = 0;
		for (int i = 0; i < keptAmount; i++) {
			for (Item item : containedItems) {
				int price = player.getProtectPrice(item);
				if (price >= lastItem.getDefinitions().getValue()) {
					lastItem = item;
				}
			}
			if (itemsToProtect == keptAmount
					|| itemsToProtect > containedItems.size())
				break;
			rewards.add(lastItem);
			containedItems.remove(lastItem);
			itemsToProtect++;
			lastItem = new Item(1, 1);
		}
		player.getInterfaceManager().sendInterface(17);
		player.getPackets().sendInterSetItemsOptionsScript(364, 4, 90, 3, 4,
				"Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(364, 4, 0, 12, 0,
				1, 2, 3);
		player.getPackets().sendItems(90, rewards);
		player.sm("You'll be protecting "+ rewards.get(0).getName() + ".");
	}

}