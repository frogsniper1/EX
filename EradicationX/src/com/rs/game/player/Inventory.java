package com.rs.game.player;

import java.io.Serializable;

import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.utils.ItemExamines;
import com.rs.utils.Utils;

public final class Inventory implements Serializable {

	private static final long serialVersionUID = 8842800123753277093L;

	private ItemsContainer<Item> items;

	private transient Player player;

	public static final int INVENTORY_INTERFACE = 679;

	public Inventory() {
		items = new ItemsContainer<Item>(28, false);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void init() {
		player.getPackets().sendItems(93, items);
	}

	public boolean containsItemToolBelt(int id) {
    	return containsOneItem(id)/* || player.getToolbelt().containsItem(id)*/;
    }

    public boolean containsItemToolBelt(int id, int amount) {
		return containsItem(id, amount)/* || player.getToolbelt().containsItem(id)*/;
    }
	
	public boolean addItemDrop(int itemId, int amount, WorldTile tile) {
	if (itemId < 0 || amount < 0 || !Utils.itemExists(itemId) || !player.getControlerManager().canAddInventoryItem(itemId, amount))
	    return false;
	Item[] itemsBefore = items.getItemsCopy();
	if (!items.add(new Item(itemId, amount)))
	    World.addGroundItem(new Item(itemId, amount), tile, player, null, true, 180, true);
	else
	    refreshItems(itemsBefore);
	return true;
    }

    public boolean addItemDrop(int itemId, int amount) {
    	return addItemDrop(itemId, amount, new WorldTile(player));
    }
	
	public void unlockInventoryOptions() {
		player.getPackets().sendIComponentSettings(INVENTORY_INTERFACE, 0, 0,
				27, 4554126);
		player.getPackets().sendIComponentSettings(INVENTORY_INTERFACE, 0, 28,
				55, 2097152);
	}

	public void reset() {
		items.reset();
		init(); // as all slots reseted better just send all again
	}

	public void refresh(int... slots) {
		player.getPackets().sendUpdateItems(93, items, slots);
	}

	public boolean containsCoins(int amount) {
		if (player.getMoneyPouch() >= amount)
			return true;
		return items.contains(new Item(995, amount));
	}	
	
	public boolean addItem(int itemId, int amount) {
	int maxcashlimit = player.getInventory().getItems().getNumberOf(995);
	Item[] itemsBefore2 = items.getItemsCopy();
		if (maxcashlimit + amount > Integer.MAX_VALUE && itemId == 995|| maxcashlimit + amount < 0 && itemId == 995|| maxcashlimit < 0 && itemId == 995|| maxcashlimit == Integer.MAX_VALUE && itemId == 995) {
		player.getPackets().sendGameMessage("2100M coins were automatically turned into 21 100M Tickets.");
		items.remove(new Item(995, 2100000000));
		items.add(new Item(2996, 21));
		refreshItems(itemsBefore2);
		return true;
		}	
		if (itemId < 0
				|| amount < 0
				|| !Utils.itemExists(itemId)
				|| !player.getControlerManager().canAddInventoryItem(itemId,
						amount))
			return false;
		Item[] itemsBefore = items.getItemsCopy();
		if (!items.add(new Item(itemId, amount))) {
			items.add(new Item(itemId, items.getFreeSlots()));
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			refreshItems(itemsBefore);
			return false;
		}
		refreshItems(itemsBefore);
		return true;
	}

	public boolean addItem(Item item) {
	int maxcashlimit = player.getInventory().getItems().getNumberOf(995);	
		if (maxcashlimit < 0) {
		player.getPackets().sendGameMessage("You can't have more then 2147m in your inventory.");
		return false;
		}		
		if (item.getId() < 0
				|| item.getAmount() < 0
				|| !Utils.itemExists(item.getId())
				|| !player.getControlerManager().canAddInventoryItem(
						item.getId(), item.getAmount()))
			return false;
		Item[] itemsBefore = items.getItemsCopy();
		if (!items.add(item)) {
			items.add(new Item(item.getId(), items.getFreeSlots()));
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			refreshItems(itemsBefore);
			return false;
		}
		refreshItems(itemsBefore);
		return true;
	}

	public void deleteItem(int slot, Item item) {
		if (!player.getControlerManager().canDeleteInventoryItem(item.getId(),
				item.getAmount()))
			return;
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(slot, item);
		refreshItems(itemsBefore);
	}

	public boolean removeItems(Item... list) {
		for (Item item : list) {
			if (item == null)
				continue;
			deleteItem(item);
		}
		return true;
	}

	public void deleteItem(int itemId, int amount) {
		if (!player.getControlerManager()
				.canDeleteInventoryItem(itemId, amount))
			return;
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(new Item(itemId, amount));
		refreshItems(itemsBefore);
	}

	public void deleteItem(Item item) {
		if (!player.getControlerManager().canDeleteInventoryItem(item.getId(),
				item.getAmount()))
			return;
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(item);
		refreshItems(itemsBefore);
		if (item.getId() == 29947 || item.getId() == 27749 ||
				item.getId() == 27750 || item.getId() == 27751 ||
				item.getId() == 27752 || item.getId() == 27753) {
			
		}
		switch (item.getId()) {
		case 29947:
			player.getInventory().addItem(27749, 1);
			break;
		case 27749:
			player.getInventory().addItem(27750, 1);
			break;
		case 27750:
			player.getInventory().addItem(27751, 1);
			break;
		case 27751:
			player.getInventory().addItem(27752, 1);
			break;
		case 27752:
			player.getInventory().addItem(27753, 1);
			break;
		case 27753:
			player.sm("Your key crumbles to dust.");
			break;
		}
	}

	/*
	 * No refresh needed its client to who does it :p
	 */
	public void switchItem(int fromSlot, int toSlot) {
		Item[] itemsBefore = items.getItemsCopy();
		Item fromItem = items.get(fromSlot);
		Item toItem = items.get(toSlot);
		items.set(fromSlot, toItem);
		items.set(toSlot, fromItem);
		refreshItems(itemsBefore);
	}

	public void refreshItems(Item[] itemsBefore) {
		int[] changedSlots = new int[itemsBefore.length];
		int count = 0;
		for (int index = 0; index < itemsBefore.length; index++) {
			if (itemsBefore[index] != items.getItems()[index])
				changedSlots[count++] = index;
		}
		int[] finalChangedSlots = new int[count];
		System.arraycopy(changedSlots, 0, finalChangedSlots, 0, count);
		refresh(finalChangedSlots);
	}

	public ItemsContainer<Item> getItems() {
		return items;
	}

	public boolean hasFreeSlots() {
		return items.getFreeSlot() != -1;
	}

	public int getFreeSlots() {
		return items.getFreeSlots();
	}

	public int getNumerOf(int itemId) {
		return items.getNumberOf(itemId);
	}

	public Item getItem(int slot) {
		return items.get(slot);
	}

	public int getItemsContainerSize() {
		return items.getSize();
	}

	public boolean containsItems(Item[] item) {
		for (int i = 0; i < item.length; i++)
			if (!items.contains(item[i]))
				return false;
		return true;
	}
	
	public Item containsAtleastItem(int[] items) {
		Item[] item = new Item[items.length];
		for (int a = 0; a < items.length; a++) 
			item[a] = new Item(items[a], 1);
		for (int i = 0; i < item.length; i++) {
			if (this.items.containsOne(item[i])) {
				return item[i];
			}
		}
		return null;
	}

	public boolean containsItems(int[] itemIds, int[] ammounts) {
		int size = itemIds.length > ammounts.length ? ammounts.length
				: itemIds.length;
		for (int i = 0; i < size; i++)
			if (!items.contains(new Item(itemIds[i], ammounts[i])))
				return false;
		return true;
	}

	public boolean containsItem(int itemId, int ammount) {
		return items.contains(new Item(itemId, ammount));
	}

	public boolean containsOneItem(int... itemIds) {
		for (int itemId : itemIds) {
			if (items.containsOne(new Item(itemId, 1)))
				return true;
		}
		return false;
	}

	public void sendExamine(int slotId) {
		if (slotId >= getItemsContainerSize())
			return;
		Item item = items.get(slotId);
		if (item == null)
			return;
		player.getPackets().sendInventoryMessage(0, slotId,
				ItemExamines.getExamine(item));
	}

	public void refresh() {
		player.getPackets().sendItems(93, items);
	}

}
