package com.rs.game.player.content;

import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.PriceManager;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSetsKeyGenerator;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class Shop {

	private static final int MAIN_STOCK_ITEMS_KEY = ItemSetsKeyGenerator
			.generateKey();

	private static final int MAX_SHOP_ITEMS = 40;
	public static final int COINS = 995;

	private String name;
	private Item[] mainStock;
	private int[] defaultQuantity;
	private Item[] generalStock;
	private int money;
	private int amount;
	private int shopId;

	private CopyOnWriteArrayList<Player> viewingPlayers;

	public Shop(String name, int money, Item[] mainStock,
			boolean isGeneralStore, int shopId) {
		viewingPlayers = new CopyOnWriteArrayList<Player>();
		this.name = name;
		this.money = money;
		this.mainStock = mainStock;
		this.shopId = shopId;
		defaultQuantity = new int[mainStock.length];
		for (int i = 0; i < defaultQuantity.length; i++)
			defaultQuantity[i] = mainStock[i].getAmount();
		if (isGeneralStore && mainStock.length < MAX_SHOP_ITEMS)
			generalStock = new Item[MAX_SHOP_ITEMS - mainStock.length];
	}

	public boolean isGeneralStore() {
		return generalStock != null;
	}

	public int getShopId() {
		return shopId;
	}

	public static int[][] loyaltyPrices = { { 20958, 5000 }, { 22268, 9000 },
			{ 20962, 5000 }, { 22270, 10000 }, { 20967, 5000 },
			{ 22272, 8000 }, { 22280, 5000 }, { 22282, 9000 }, { 22284, 5000 },
			{ 22286, 8000 }, { 20966, 5000 }, { 22274, 10000 },
			{ 20965, 5000 }, { 22276, 8000 }, { 22288, 5000 }, { 22290, 8000 },
			{ 22292, 5000 }, { 22294, 10000 }, { 22300, 7000 },
			{ 22296, 5000 }, { 22298, 10000 }, { 22302, 9000 },
			{ 23846, 9000 }, { 23854, 9000 }, { 23874, 9000 }, { 23876, 9000 } };

	public static int[][] bossslayerPrices = { { 6190, 600 }, { 1763, 200 },
			{ 1765, 200 }, { 1767, 200 }, { 1771, 200 }, { 15488, 300 },
			{ 15490, 300 }, { 6192, 3000 }, { 6193, 3000 }, { 22282, 9000 },
			{ 22282, 9000 }, { 27746, 700 }, { 27748, 800 } };

	public static int[][] votePrices = { { 27356, 5 }, { 27357, 5 },
			{ 27358, 10 }, { 24155, 5 }, { 2996, 5 }, { 27358, 5 },
			{ 27359, 5 }, { 23716, 5 } };

	public static int[][] loyaltyPrices2 = { { 23880, 5000 }, { 23882, 5000 },
			{ 23884, 5000 }, { 23886, 5000 }, { 23886, 5000 }, { 23888, 5000 },
			{ 23890, 5000 }, { 23892, 5000 }, { 23894, 5000 } };

	public static int[][] eradSealsPrices = { { 26705, 2500 }, { 23659, 1500 },
			{ 18349, 1500 }, { 18351, 1500 }, { 18353, 1500 }, { 18355, 1500 },
			{ 22358, 1700 }, { 22366, 1700 }, { 22367, 1700 }, { 22368, 1700 },
			{ 22369, 1700 }, { 22359, 1700 }, { 18357, 1500 }, { 18355, 1500 },
			{ 18359, 1500 }, { 19780, 2000 }, { 25991, 1500 }, { 25993, 1500 },
			{ 25995, 1500 }, { 19669, 2500 }, { 22360, 1700 }, { 22361, 1700 },
			{ 22362, 1700 }, { 22363, 1700 }, { 22364, 1700 }, { 22365, 1700 }, };

	public void addPlayer(final Player player) {
		viewingPlayers.add(player);
		player.getTemporaryAttributtes().put("Shop", this);
		player.setCloseInterfacesEvent(new Runnable() {
			@Override
			public void run() {
				player.shopAmount = -1;
				player.shopSlotId = -1;
				viewingPlayers.remove(player);
				player.getTemporaryAttributtes().remove("Shop");
				player.getTemporaryAttributtes().remove("shop_buying");
				player.getTemporaryAttributtes().remove("amount_shop");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		});
		player.getPackets().sendConfig(118, MAIN_STOCK_ITEMS_KEY);
		player.getPackets().sendConfig(1496, -1);
		player.getPackets().sendConfig(532, money);
		player.getPackets().sendConfig(2565, 0);
		sendStore(player);
		player.getPackets().sendGlobalConfig(199, -1);
		player.getInterfaceManager().sendInterface(1265);
		player.getPackets().sendHideIComponent(1265, 60, true);
		player.getPackets().sendHideIComponent(1265, 23, true);
		player.getPackets().sendHideIComponent(1265, 24, true);
		player.sit(1265, 252, "Select an item");
		player.sit(1265, 253, "");
		player.sit(1265, 254, "");
		for (int i = 0; i < MAX_SHOP_ITEMS; i++)
			player.getPackets().sendGlobalConfig(
					946 + i,
					i < defaultQuantity.length ? defaultQuantity[i]
							: generalStock != null ? 0 : -1);// prices
		player.getPackets().sendGlobalConfig(1241, 16750848);
		player.getPackets().sendGlobalConfig(1242, 15439903);
		player.getPackets().sendGlobalConfig(741, -1);
		player.getPackets().sendGlobalConfig(743, -1);
		player.getPackets().sendGlobalConfig(744, 0);
		if (generalStock != null)
			player.getPackets().sendHideIComponent(1265, 19, false);
		player.getPackets().sendIComponentSettings(1265, 20, 0,
				getStoreSize() * 6, 1150);
		player.getPackets().sendIComponentSettings(1265, 26, 0,
				getStoreSize() * 6, 82903066);
		sendInventory(player);
		player.sit(1265, 85, name);
		player.getTemporaryAttributtes().put("shop_buying", true);
		player.getTemporaryAttributtes().put("amount_shop", 1);
	}

	public void sendInventory(Player player) {
		player.getInterfaceManager().sendInventoryInterface(1266);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendUnlockIComponentOptionSlots(1266, 0, 0, 27, 0,
				1, 2, 3, 4, 5);
		player.getPackets().sendInterSetItemsOptionsScript(1266, 0, 93, 4, 7,
				"Value", "Sell 1", "Sell 5", "Sell 10", "Sell 50", "Sell X");
	}

	// cid 67

	public void buy(Player player, int slotId, int quantity) {
		if (getShopId() == 51) {
			player.sm("You cannot buy items from this store, only sell.");
			return;
		}
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		if (item.getAmount() == 0) {
			player.sm("There is no stock of that item at the moment.");
			return;
		}
		int price = PriceManager.getBuyPrice(item);
		int amountCoins = player.getInventory().getItems().getNumberOf(money);
		int maxQuantity = amountCoins / price;
		int buyQ = item.getAmount() > quantity ? quantity : item.getAmount();
		// int amountInPouch = player.money;
		// int maxPouch = amountInPouch / price;
		boolean enoughCoins = maxQuantity >= buyQ;
		// boolean enoughInPouch = maxPouch >= buyQ;
		if (item.getDefinitions().isStackable()) {
			if (!player.getInventory().hasFreeSlots()) {
				player.sm("There's not enough space in your inventory for this purchase.");
				return;
			}
		} else {
			if (player.getInventory().getFreeSlots() < buyQ) {
				player.sm("There's not enough space in your inventory for this purchase.");
				return;
			}
		}
		if (money != 995) {
			if (sendUniqueBuy(player, item, quantity, price, money)) {
				return;
			}
		}
		if (money == 995) {
			sendCoinsBuy(player, item, quantity, price);
			return;
		}
		if (!enoughCoins) {
			player.sm("You don't have enough "
					+ new Item(money).getName().toLowerCase() + ".");
			return;
		} else if (quantity > buyQ)
			player.sm("The shop has run out of stock.");
		if (buyQ != 0) {
			int totalPrice = price * buyQ;
			if (amountCoins + price > 0) {
				if (enoughCoins) {
					player.getInventory().addItem(item.getId(), buyQ);
					player.getInventory().deleteItem(money, totalPrice);
				} else {
					player.getInventory().deleteItem(money, totalPrice);
					player.getInventory().addItem(item.getId(), buyQ);
				}
				item.setAmount(item.getAmount() - buyQ);
				if (item.getAmount() <= 0 && slotId >= mainStock.length)
					generalStock[slotId - mainStock.length] = null;
				refreshShop();
				sendInventory(player);
			}
			if (quantity == 500)
				player.getTemporaryAttributtes().put("last_shop_purchase",
						Utils.currentTimeMillis() + 10000);
		}
		player.sm("You bought "
				+ (buyQ > 1 ? Utils.formatNumber(buyQ) : Utils.writeAnorA(item
						.getName())) + " " + item.getName() + " for "
				+ Utils.formatNumber(price * buyQ) + " "
				+ new Item(money).getName().toLowerCase() + ".");
	}

	private void sendCoinsBuy(Player player, Item item, int quantity, int price) {
		int total = quantity * price;
		if (item.getAmount() < quantity) {
			player.sm("That shop has run out of stock.");
			return;
		}
		if (player.getPouch().getAmount() >= total) {
			player.getPouch().setAmount(player.getPouch().getAmount() - total);
			player.getPouch().sendScript(false, total);
			player.getPouch().refresh();
			player.getInventory().addItem(item.getId(), quantity);
			player.sm("You bought "
					+ (quantity > 1 ? Utils.formatNumber(quantity) : Utils
							.writeAnorA(item.getName())) + " " + item.getName()
					+ " for " + Utils.formatNumber(price * quantity) + " "
					+ new Item(money).getName().toLowerCase() + ".");
			player.sm(Utils.formatNumber(total)
					+ " coins were removed from your money pouch.");
			item.setAmount(item.getAmount() - quantity);
			refreshShop();
			return;
		}
		if (player.getInventory().getItems().getNumberOf(money) >= total) {
			player.getInventory().deleteItem(money, total);
			player.getInventory().addItem(item.getId(), quantity);
			player.sm("You bought "
					+ (quantity > 1 ? Utils.formatNumber(quantity) : Utils
							.writeAnorA(item.getName())) + " " + item.getName()
					+ " for " + Utils.formatNumber(price * quantity) + " "
					+ new Item(money).getName().toLowerCase() + ".");
			item.setAmount(item.getAmount() - quantity);
			refreshShop();
			return;
		}
		player.sm("You don't have enough coins for this purchase.");
	}

	private boolean sendUniqueBuy(Player player, Item item, int quantity,
			int price, int id) {
		for (int i11 = 0; i11 < loyaltyPrices.length; i11++) {
			loyaltyShop = 1;
			if (item.getId() == loyaltyPrices[i11][0] && shopId == 28) {
				if (player.getLoyaltyPoints() < loyaltyPrices[i11][1]
						* quantity) {
					player.sm("You need "
							+ Utils.formatNumber(loyaltyPrices[i11][1]
									* quantity)
							+ " Loyalty Points to buy this item!");
					return true;
				}
				loyaltyShop = 1;
				player.sm("You bought "
						+ (quantity > 1 ? quantity : Utils.writeAnorA(item
								.getName())) + " " + item.getName() + " for "
						+ Utils.formatNumber(loyaltyPrices[i11][1] * quantity)
						+ " loyalty points.");
				player.getInventory().addItem(loyaltyPrices[i11][0], quantity);
				player.setLoyaltyPoints(player.getLoyaltyPoints()
						- loyaltyPrices[i11][1] * quantity);
				return true;
			}
		}
		for (int i11 = 0; i11 < bossslayerPrices.length; i11++) {
			bossslayerShop = 1;
			if (item.getId() == bossslayerPrices[i11][0] && shopId == 58) {
				if (player.getBossSlayerPoints() < bossslayerPrices[i11][1]
						* quantity) {
					player.sm("You need "
							+ Utils.formatNumber(bossslayerPrices[i11][1]
									* quantity)
							+ " Boss Slayer Points to buy this item!");
					return true;
				}
				bossslayerShop = 1;
				player.sm("You bought "
						+ (quantity > 1 ? quantity : Utils.writeAnorA(item
								.getName()))
						+ " "
						+ item.getName()
						+ " for "
						+ Utils.formatNumber(bossslayerPrices[i11][1]
								* quantity) + " boss slayer points.");
				player.getInventory().addItem(bossslayerPrices[i11][0],
						quantity);
				player.setBossSlayerPoints(player.getBossSlayerPoints()
						- bossslayerPrices[i11][1] * quantity);
				return true;
			}
		}
		for (int i11 = 0; i11 < votePrices.length; i11++) {
			voteShop = 1;
			if (item.getId() == votePrices[i11][0] && shopId == 41) {
				if (player.getCurrencyPouch().spendVoteTickets(
						votePrices[i11][1] * quantity)) {
					voteShop = 1;
					player.sm("You bought "
							+ (quantity > 1 ? quantity : Utils.writeAnorA(item
									.getName())) + " " + item.getName()
							+ " for "
							+ Utils.formatNumber(votePrices[i11][1] * quantity)
							+ " vote tickets.");
					player.getInventory().addItem(votePrices[i11][0], quantity);
					return true;
				} else {
					player.sm("You need " + votePrices[i11][1] * quantity
							+ " Vote Tickets to buy this item!");
				}
				return true;
			}
		}
		for (int i11 = 0; i11 < eradSealsPrices.length; i11++) {
			if (item.getId() == eradSealsPrices[i11][0] && shopId == 34) {
				if (player.getCurrencyPouch().spendEradicatedSeals(
						eradSealsPrices[i11][1] * quantity)) {
					player.sm("You bought "
							+ (quantity > 1 ? quantity : Utils.writeAnorA(item
									.getName()))
							+ " "
							+ item.getName()
							+ " for "
							+ Utils.formatNumber(eradSealsPrices[i11][1]
									* quantity) + " eradicated seals.");
					player.getInventory().addItem(eradSealsPrices[i11][0],
							quantity);
					return true;
				} else {
					player.sm("You need " + eradSealsPrices[i11][1] * quantity
							+ " Eradicated Seals to buy this item!");
				}
			}
		}
		for (int i11 = 0; i11 < loyaltyPrices2.length; i11++) {
			loyaltyShop2 = 1;
			if (item.getId() == loyaltyPrices2[i11][0] && shopId == 54) {
				if (player.getLoyaltyPoints() < loyaltyPrices2[i11][1]
						* quantity) {
					player.sm("You need " + loyaltyPrices2[i11][1] * quantity
							+ " Loyalty Points to buy this item!");
					return true;
				}
				loyaltyShop2 = 1;
				player.sm("You have bought a "
						+ item.getDefinitions().getName()
						+ " from the loyalty store.");
				player.getInventory().addItem(loyaltyPrices2[i11][0], quantity);
				player.setLoyaltyPoints(player.getLoyaltyPoints()
						- loyaltyPrices2[i11][1] * quantity);
				return true;
			}
		}
		switch (id) {
		case 1464:
			if (player.getCurrencyPouch().spendVoteTickets(price * quantity)) {
				player.sm("You bought "
						+ (quantity > 1 ? quantity : Utils.writeAnorA(item
								.getName())) + " " + item.getName() + " for "
						+ Utils.formatNumber(price * quantity)
						+ " vote tickets.");
				player.getInventory().addItem(item.getId(), quantity);
			} else {
				player.sm("You need " + (price * quantity)
						+ " Vote Tickets to buy this item!");
			}
			return true;
		case 19819:
			if (player.getCurrencyPouch().spendInvasionTokens(price * quantity)) {
				player.sm("You bought "
						+ (quantity > 1 ? quantity : Utils.writeAnorA(item
								.getName())) + " " + item.getName() + " for "
						+ Utils.formatNumber(price * quantity)
						+ " Invasion tokens.");
				player.getInventory().addItem(item.getId(), quantity);
			} else {
				player.sm("You need " + (price * quantity)
						+ " Invasion Tokens to buy this item!");
			}
			return true;
		case 12852:
			if (player.getCurrencyPouch()
					.spendEradicatedSeals(price * quantity)) {
				player.sm("You bought "
						+ (quantity > 1 ? quantity : Utils.writeAnorA(item
								.getName())) + " " + item.getName() + " for "
						+ Utils.formatNumber(price * quantity)
						+ " Eradicated seals.");
				player.getInventory().addItem(item.getId(), quantity);
			} else {
				player.sm("You need " + (price * quantity)
						+ " Eradicated Seals to buy this item!");
			}
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void restoreItems() {
		boolean needRefresh = false;
		for (int i = 0; i < mainStock.length; i++) {
			if (mainStock[i].getAmount() < defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + 1);
				needRefresh = true;
			} else if (mainStock[i].getAmount() > defaultQuantity[i]) {
				mainStock[i].setAmount(mainStock[i].getAmount() + -1);
				needRefresh = true;
			}
		}
		if (generalStock != null) {
			for (int i = 0; i < generalStock.length; i++) {
				Item item = generalStock[i];
				if (item == null)
					continue;
				item.setAmount(item.getAmount() - 1);
				if (item.getAmount() <= 0)
					generalStock[i] = null;
				needRefresh = true;
			}
		}
		if (needRefresh)
			refreshShop();
	}

	private boolean addItem(int itemId, int quantity) {
		if (getShopId() == 1)
			return false;
		if (getShopId() == 51)
			return true;
		for (Item item : mainStock) {
			if (item.getId() == itemId) {
				item.setAmount(item.getAmount() + quantity);
				refreshShop();
				return true;
			}
		}
		if (generalStock != null) {
			for (Item item : generalStock) {
				if (item == null)
					continue;
				if (item.getId() == itemId) {
					item.setAmount(item.getAmount() + quantity);
					refreshShop();
					return true;
				}
			}
			for (int i = 0; i < generalStock.length; i++) {
				if (generalStock[i] == null) {
					generalStock[i] = new Item(itemId, quantity);
					refreshShop();
					return true;
				}
			}
		}
		return false;
	}

	public void sell(Player player, int slotId, int quantity, boolean isX) {
		if (quantity == 0)
			quantity = 1;
		if (getShopId() == 34 || getShopId() == 41 || getShopId() == 13
				|| getShopId() == 52)
			return;
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		int originalId = item.getId();
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (ItemConstants.getItemDefaultCharges(item.getId()) != -1
				|| !ItemConstants.isTradeable(item) || item.getId() == money) {
			player.sm("You can't sell this item.");
			return;
		}
		int maxcashlimit = player.getInventory().getItems().getNumberOf(995);
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && generalStock == null) {
			player.sm("You can't sell that item to this shop.");
			return;
		}
		int price = PriceManager.getSellPrice(item);
		int moneycheck = price * quantity;
		for (int i = 0; i < quantity; i++) {
			if (price * i >= Integer.MAX_VALUE || price * i < 0) {
				player.sm("You can't sell that many due to the max cash limit.");
				i = quantity;
				return;
			}
		}
		if (price * quantity < 0) {
			player.sm("You can't sell this many at once. You know, unless you want "
					+ Utils.formatNumber((price * quantity))
					+ " coins for that.");
			return;
		}
		if ((moneycheck + player.getPouch().getAmount() > Integer.MAX_VALUE || moneycheck
				+ player.getPouch().getAmount() < 0)
				&& (moneycheck + maxcashlimit > Integer.MAX_VALUE || moneycheck
						+ maxcashlimit < 0)) {
			player.sm("You can't have more than 2147m.");
			return;
		}
		if ((moneycheck + player.getPouch().getAmount() > Integer.MAX_VALUE || moneycheck
				+ player.getPouch().getAmount() < 0)) {
			if (!player.getInventory().hasFreeSlots()
					&& (player.getInventory().getNumerOf(995) <= 0)) {
				player.sm("Not enough space in your inventory.");
				return;
			}
		}
		int numberOff = player.getInventory().getItems()
				.getNumberOf(originalId);
		if (quantity > numberOff)
			quantity = numberOff;
		if (!addItem(item.getId(), quantity)) {
			player.sm("Shop is currently full.");
			return;
		}
		player.getInventory().deleteItem(originalId, quantity);
		if (money == 995) {
			if ((player.getPouch().getAmount() + (price * quantity)) <= Integer.MAX_VALUE
					&& (player.getPouch().getAmount() + (price * quantity)) > 0) {
				player.getPouch().sendScript(true, price * quantity);
				player.sm(Utils.formatNumber(price * quantity)
						+ " coins were added to your money pouch.");
				player.getPouch().setAmount(
						player.getPouch().getAmount() + price * quantity);
				player.getPouch().refresh();
			} else
				player.getInventory().addItem(money, price * quantity);
		} else if (money == 2996) {
			if ((player.getCurrencyPouch().get100MTicket() + (price * quantity)) <= Integer.MAX_VALUE
					&& (player.getCurrencyPouch().get100MTicket() + (price * quantity)) > 0) {
				player.getCurrencyPouch().set100MTicket(
						player.getCurrencyPouch().get100MTicket() + price
								* quantity);
			} else
				player.getInventory().addItem(money, price * quantity);
		} else if (money == 1464) {
			if ((player.getCurrencyPouch().getVoteTickets() + (price * quantity)) <= Integer.MAX_VALUE
					&& (player.getCurrencyPouch().getVoteTickets() + (price * quantity)) > 0) {
				player.getCurrencyPouch().setVoteTickets(
						player.getCurrencyPouch().getVoteTickets() + price
								* quantity);
			} else
				player.getInventory().addItem(money, price * quantity);
		} else if (money == 19819) {
			if ((player.getCurrencyPouch().getInvasionTokens() + (price * quantity)) <= Integer.MAX_VALUE
					&& (player.getCurrencyPouch().getInvasionTokens() + (price * quantity)) > 0) {
				player.getCurrencyPouch().setInvasionTokens(
						player.getCurrencyPouch().getInvasionTokens() + price
								* quantity);
			} else
				player.getInventory().addItem(money, price * quantity);
		} else if (money == 12852) {
			if ((player.getCurrencyPouch().getEradicatedSeals() + (price * quantity)) <= Integer.MAX_VALUE
					&& (player.getCurrencyPouch().getEradicatedSeals() + (price * quantity)) > 0) {
				player.getCurrencyPouch().setEradicatedSeals(
						player.getCurrencyPouch().getEradicatedSeals() + price
								* quantity);
			} else
				player.getInventory().addItem(money, price * quantity);
		} else
			player.getInventory().addItem(money, price * quantity);
		Logger.printShopLog(player, new Item(originalId), price * quantity,
				new Item(money));
	}

	public static int loyaltyShop = 1;

	public static int bossslayerShop = 1;

	public static int loyaltyShop2 = 1;

	public static int voteShop = 1;

	public void sendValue(Player player, int slotId) {
		if (player.getInventory().getItemsContainerSize() < slotId)
			return;
		Item item = player.getInventory().getItem(slotId);
		if (item == null)
			return;
		if (item.getDefinitions().isNoted())
			item = new Item(item.getDefinitions().getCertId(), item.getAmount());
		if (item.getDefinitions().isNoted() || !ItemConstants.isTradeable(item)
				|| item.getId() == money) {
			player.sm("You can't sell this item.");
			return;
		}
		int dq = getDefaultQuantity(item.getId());
		if (dq == -1 && generalStock == null) {
			player.sm("You can't sell this item to this shop.");
			return;
		}
		int price = PriceManager.getSellPrice(item);
		if (money == 995)
			player.sm(item.getDefinitions().getName()
					+ ": shop will buy for: "
					+ price
					+ " "
					+ ItemDefinitions.getItemDefinitions(money).getName()
							.toLowerCase() + ". Right-click the item to sell.");

	}

	public int getDefaultQuantity(int itemId) {
		for (int i = 0; i < mainStock.length; i++)
			if (mainStock[i].getId() == itemId)
				return defaultQuantity[i];
		return -1;
	}

	public void sendInfo(Player player, int slotId, boolean isBuying,
			int quantity) {
		if (slotId >= getStoreSize())
			return;
		Item[] stock = isBuying ? mainStock : player.getInventory().getItems()
				.getItems();
		Item item = slotId >= stock.length ? generalStock[slotId - stock.length]
				: stock[slotId];
		if (item == null)
			return;
		amount = quantity;
		player.getTemporaryAttributtes().put("amount_shop", amount);
		player.shopAmount = quantity;
		player.shopSlotId = slotId;
		int price = isBuying ? PriceManager.getBuyPrice(item) : PriceManager
				.getSellPrice(item);
		if ((quantity * price > Integer.MAX_VALUE) || (quantity * price < 0)) {
			quantity = 1;
			amount = 1;
			player.shopAmount = 1;
			player.getTemporaryAttributtes().put("amount_shop", 1);
			player.sm("The price went too high!");
			return;
		}
		for (int i = 0; i < quantity; i++) {
			if (price * i >= Integer.MAX_VALUE || price * i < 0) {
				player.sm("The price went too high!");
				i = quantity;
				quantity = 1;
				amount = 1;
				player.getTemporaryAttributtes().put("amount_shop", 1);
				player.shopAmount = 1;
				return;
			}
		}
		for (int i = 0; i < loyaltyPrices.length; i++) {
			if (item.getId() == loyaltyPrices[i][0] && shopId == 28) {
				player.sit(1265, 252, "Item: " + item.getName());
				player.sit(1265, 253, "Quantity: " + amount);
				if (amount == 1)
					player.sit(1265, 254,
							"Price: " + Utils.numberFormat(loyaltyPrices[i][1])
									+ " Loyalty points");
				else
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(loyaltyPrices[i][1]
											* amount) + " Loyalty points ("
									+ Utils.numberFormat(loyaltyPrices[i][1])
									+ " each)");
				return;
			}
		}
		for (int i = 0; i < bossslayerPrices.length; i++) {
			if (item.getId() == bossslayerPrices[i][0] && shopId == 58) {
				player.sit(1265, 252, "Item: " + item.getName());
				player.sit(1265, 253, "Quantity: " + amount);
				if (amount == 1)
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(bossslayerPrices[i][1])
									+ " Boss slayer points");
				else
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(bossslayerPrices[i][1]
											* amount)
									+ " Boss slayer points ("
									+ Utils.numberFormat(bossslayerPrices[i][1])
									+ " each)");
				return;
			}
		}
		for (int i = 0; i < eradSealsPrices.length; i++) {
			if (item.getId() == eradSealsPrices[i][0] && shopId == 34) {
				player.sit(1265, 252, "Item: " + item.getName());
				player.sit(1265, 253, "Quantity: " + amount);
				if (amount == 1)
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(eradSealsPrices[i][1])
									+ " Eradicated seals");
				else
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(eradSealsPrices[i][1]
											* amount)
									+ " Eradicated seals ("
									+ Utils.numberFormat(bossslayerPrices[i][1])
									+ " each)");
				return;
			}
		}
		for (int i = 0; i < votePrices.length; i++) {
			if (item.getId() == votePrices[i][0] && shopId == 41) {
				player.sit(1265, 252, "Item: " + item.getName());
				player.sit(1265, 253, "Quantity: " + amount);
				if (amount == 1)
					player.sit(1265, 254,
							"Price: " + Utils.numberFormat(votePrices[i][1])
									+ " Vote tickets");
				else
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(votePrices[i][1]
											* amount) + " Vote tickets ("
									+ Utils.numberFormat(votePrices[i][1])
									+ " each)");
				return;
			}
		}
		for (int i = 0; i < loyaltyPrices2.length; i++) {
			if (item.getId() == loyaltyPrices2[i][0] && shopId == 54) {
				player.sit(1265, 252, "Item: " + item.getName());
				player.sit(1265, 253, "Quantity: " + amount);
				if (amount == 1)
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(loyaltyPrices2[i][1])
									+ " Loyalty points");
				else
					player.sit(
							1265,
							254,
							"Price: "
									+ Utils.numberFormat(loyaltyPrices2[i][1]
											* amount) + " Loyalty points ("
									+ Utils.numberFormat(loyaltyPrices2[i][1])
									+ " each)");
				return;
			}
		}
		player.sit(1265, 252, "Item: " + item.getName());
		player.sit(1265, 253, "Quantity: " + amount);
		if (amount == 1)
			player.sit(1265, 254, "Price: " + Utils.numberFormat(price) + " "
					+ ItemDefinitions.getItemDefinitions(money).getName());
		else
			player.sit(
					1265,
					254,
					"Price: "
							+ Utils.numberFormat(price * quantity)
							+ " "
							+ ItemDefinitions.getItemDefinitions(money)
									.getName() + " ("
							+ Utils.numberFormat(price) + " each)");
		if (!isBuying)
			player.sm(item.getDefinitions().getName()
					+ ": shop will "
					+ "sell"
					+ " for "
					+ Utils.numberFormat(price)
					+ " "
					+ ItemDefinitions.getItemDefinitions(money).getName()
							.toLowerCase() + ".");
	}

	public void sendExamine(Player player, int slotId) {
		if (slotId >= getStoreSize())
			return;
		Item item = slotId >= mainStock.length ? generalStock[slotId
				- mainStock.length] : mainStock[slotId];
		if (item == null)
			return;
		player.sm(ItemExamines.getExamine(item));
	}

	public void refreshShop() {
		for (Player player : viewingPlayers) {
			sendStore(player);
			player.getPackets().sendIComponentSettings(620, 25, 0,
					getStoreSize() * 6, 1150);
		}
	}

	public int getStoreSize() {
		return mainStock.length
				+ (generalStock != null ? generalStock.length : 0);
	}

	public void sendStore(Player player) {
		Item[] stock = new Item[mainStock.length
				+ (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(mainStock, 0, stock, 0, mainStock.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, mainStock.length,
					generalStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	public void sendSellStore(Player player, Item[] inventory) {
		Item[] stock = new Item[inventory.length
				+ (generalStock != null ? generalStock.length : 0)];
		System.arraycopy(inventory, 0, stock, 0, inventory.length);
		if (generalStock != null)
			System.arraycopy(generalStock, 0, stock, inventory.length,
					generalStock.length);
		player.getPackets().sendItems(MAIN_STOCK_ITEMS_KEY, stock);
	}

	/**
	 * Checks if the player is buying an item or selling it.
	 * 
	 * @param player
	 *            The player
	 * @param slotId
	 *            The slot id
	 * @param amount
	 *            The amount
	 */
	public void handleShop(Player player, int slotId, int amount) {
		boolean isBuying = player.getTemporaryAttributtes().get("shop_buying") != null;
		if (isBuying) {
			sendInfo(player, slotId, isBuying, amount);
		} else
			sell(player, slotId, amount, false);
	}

	public Item[] getMainStock() {
		return this.mainStock;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(Player player, int amount) {
		this.amount = amount;
		player.sit(1265, 67, String.valueOf(amount));
	}
}