package com.rs.game.player;

import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.custom.PriceManager;
import com.rs.utils.EconomyPrices;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public class Trade {

	private Player player, target;
	private ItemsContainer<Item> items;
	private boolean tradeModified;
	private boolean accepted;
	public Trade(Player player) {
		this.player = player; // player reference
		items = new ItemsContainer<Item>(28, false);
	}

	/*
	 * called to both players
	 */
	public void openTrade(Player target) {
		synchronized (this) {
			synchronized (target.getTrade()) {
				int total = 0;
				int reqTotal = 400;
				for (int i = 0; i < 25; i++) {
					total += player.getSkills().getLevel(i);
				}
				if (player.isIronMan()) {
					player.sm("Ironman accounts can't trade.");
					return;
				}
				if (total < reqTotal) {
					player.sendMessage("You must have a total level of 400 to trade!");
					return;
				}
				this.target = target;	
				int ttotal = 0;
				int treqTotal = 400;
				for (int i = 0; i < 25; i++) {
					ttotal += target.getSkills().getLevel(i);
				}
				if  (ttotal < treqTotal) {
					player.sm("The player doesn't have 400 total level.");
					return;
				}
				if (target.isIronMan()) {
					player.sm(target.getDisplayName() +" has an Ironman account.");
					return;
				}
				player.getPackets().sendIComponentText(335, 17,
						"Trading With: " + target.getDisplayName());
				player.getPackets().sendGlobalString(203,
						target.getDisplayName());
				sendInterItems();
				sendOptions();
				sendTradeModified();
				refreshFreeInventorySlots();
				refreshTradeWealth();
				refreshStageMessage(true);
				player.getInterfaceManager().sendInterface(335);
				player.getPackets().sendHideIComponent(335, 53, true);
				player.getPackets().sendIComponentText(335, 63, "<col=123456>+ Currency Pouch");
				player.getInterfaceManager().sendInventoryInterface(336);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						closeTrade(CloseTradeStage.CANCEL);
					}
				});
			}
		}
	}

	public void removeItem(final int slot, int amount, boolean bypass, Player oldtarg) {
		synchronized (this) {
			if (!isTrading() && !bypass)
				return;
			if (bypass) {
				Player p = player;
				if (oldtarg != null)
					p = oldtarg;
				Item item = items.get(slot);
				if (item == null)
					return;
				if (item.getId() == 995) {
					if ((p.getPouch().getAmount() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(p.getPouch().getAmount() + (item.getAmount())) > 0) {		
							p.getPouch().sendScript(true, item.getAmount());
							p.sm(Utils.formatNumber(item.getAmount()) + " coins were added to your money pouch.");
							p.getPouch().setAmount(p.getPouch().getAmount() + item.getAmount());
							p.getPouch().refresh();
					} else 
						p.getInventory().addItem(item);
				} else if (item.getId() == 2996) {
					if ((p.getCurrencyPouch().get100MTicket() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(p.getCurrencyPouch().get100MTicket() + (item.getAmount())) > 0) {		
							p.getCurrencyPouch().set100MTicket(p.getCurrencyPouch().get100MTicket() + item.getAmount());
					} else 
						p.getInventory().addItem(item);
				} else if (item.getId() == 19819) {
					if ((p.getCurrencyPouch().getInvasionTokens() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(p.getCurrencyPouch().getInvasionTokens() + (item.getAmount())) > 0) {		
							p.getCurrencyPouch().setInvasionTokens(p.getCurrencyPouch().getInvasionTokens() + item.getAmount());
					} else 
						p.getInventory().addItem(item);
				} else if (item.getId() == 1464) {
					if ((p.getCurrencyPouch().getVoteTickets() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(p.getCurrencyPouch().getVoteTickets() + (item.getAmount())) > 0) {		
							p.getCurrencyPouch().setVoteTickets(p.getCurrencyPouch().getVoteTickets() + item.getAmount());
					} else 
						p.getInventory().addItem(item);
				} else if (item.getId() == 12852) {
					if ((p.getCurrencyPouch().getEradicatedSeals() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(p.getCurrencyPouch().getEradicatedSeals() + (item.getAmount())) > 0) {		
							p.getCurrencyPouch().setEradicatedSeals(p.getCurrencyPouch().getEradicatedSeals() + item.getAmount());
					} else 
						p.getInventory().addItem(item);
				} else
				p.getInventory().addItem(item);				
				return;
			}
			synchronized (target.getTrade()) {
				Item item = items.get(slot);
				if (item == null)
					return;
				Item[] itemsBefore = items.getItemsCopy();
				int maxAmount = items.getNumberOf(item);
				if (amount < maxAmount)
					item = new Item(item.getId(), amount);
				else
					item = new Item(item.getId(), maxAmount);
				items.remove(slot, item);
				if (item.getId() == 995) {
					if ((player.getPouch().getAmount() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(player.getPouch().getAmount() + (item.getAmount())) > 0) {		
							player.getPouch().sendScript(true, item.getAmount());
							player.sm(Utils.formatNumber(item.getAmount()) + " coins were added to your money pouch.");
							player.getPouch().setAmount(player.getPouch().getAmount() + item.getAmount());
							player.getPouch().refresh();
					} else 
						player.getInventory().addItem(item);
				} else if (item.getId() == 2996) {
					if ((player.getCurrencyPouch().get100MTicket() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(player.getCurrencyPouch().get100MTicket() + (item.getAmount())) > 0) {		
							player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() + item.getAmount());
					} else 
						player.getInventory().addItem(item);
				} else if (item.getId() == 19819) {
					if ((player.getCurrencyPouch().getInvasionTokens() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(player.getCurrencyPouch().getInvasionTokens() + (item.getAmount())) > 0) {		
							player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() + item.getAmount());
					} else 
						player.getInventory().addItem(item);
				} else if (item.getId() == 1464) {
					if ((player.getCurrencyPouch().getVoteTickets() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(player.getCurrencyPouch().getVoteTickets() + (item.getAmount())) > 0) {		
							player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() + item.getAmount());
					} else 
						player.getInventory().addItem(item);
				} else if (item.getId() == 12852) {
					if ((player.getCurrencyPouch().getEradicatedSeals() + (item.getAmount())) <= Integer.MAX_VALUE &&
							(player.getCurrencyPouch().getEradicatedSeals() + (item.getAmount())) > 0) {		
							player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() + item.getAmount());
					} else 
						player.getInventory().addItem(item);
				} else
				player.getInventory().addItem(item);
				refreshItems(itemsBefore);
				cancelAccepted();
				setTradeModified(true);
			}
		}
	}

	public void sendFlash(int slot) {
		player.getPackets().sendInterFlashScript(335, 33, 4, 7, slot);
		target.getPackets().sendInterFlashScript(335, 36, 4, 7, slot);
	}

	public void cancelAccepted() {
		boolean canceled = false;
		if (accepted) {
			accepted = false;
			canceled = true;
		}
		if (target.getTrade().accepted) {
			target.getTrade().accepted = false;
			canceled = true;
		}
		if (canceled)
			refreshBothStageMessage(canceled);
	}
	
	public void addCoins(int amount, int id) {
		synchronized (this) {
			Item[] itemsBefore = items.getItemsCopy();
			Item money = new Item(id);
			int check = 0;
			for (Item i : itemsBefore) {
				if (i != null) {
					check++;
					if (check == 28) {
						player.sm("Not enough space in the trade screen.");
						return;
					}
					if ((i.getAmount() + amount > Integer.MAX_VALUE) ||
							(i.getAmount() + amount < 0))  
						return;
				if (i.getId() == id)
					money.setAmount(i.getAmount());
				}
			}
			int xamount = amount;
			if (id == 995)
				xamount = player.getPouch().getAmount();
			else if (id == 2996)
				xamount = player.getCurrencyPouch().get100MTicket();
			else if (id == 19819)
				xamount = player.getCurrencyPouch().getInvasionTokens();
			else if (id == 1464)
				xamount = player.getCurrencyPouch().getVoteTickets();
			else if (id == 12852)
				xamount = player.getCurrencyPouch().getEradicatedSeals();
			if (xamount == 0)
				return;
			Item item = new Item(id, amount);
			if (xamount < amount) {
				if (id == 995)
					amount = player.getPouch().getAmount();
				else if (id == 2996)
					amount = player.getCurrencyPouch().get100MTicket();
				else if (id == 19819)
					amount = player.getCurrencyPouch().getInvasionTokens();
				else if (id == 1464)
					amount = player.getCurrencyPouch().getVoteTickets();
				else if (id == 12852)
					amount = player.getCurrencyPouch().getEradicatedSeals();
				if (((money.getAmount() + amount) > Integer.MAX_VALUE) || ((money.getAmount() + amount) < 0)) 
					return;
				if (id == 995) {
					item = new Item(id, player.getPouch().getAmount());
					player.getPouch().sendScript(false, player.getPouch().getAmount());
					player.getPouch().setAmount(0);
					player.getPouch().refresh();
				} else if (id == 2996) {
					item = new Item(id, player.getCurrencyPouch().get100MTicket());
					player.getCurrencyPouch().set100MTicket(0);
				} else if (id == 19819) {
					item = new Item(id, player.getCurrencyPouch().getInvasionTokens());
					player.getCurrencyPouch().setInvasionTokens(0);
				} else if (id == 1464) {
					item = new Item(id, player.getCurrencyPouch().getVoteTickets());
					player.getCurrencyPouch().setVoteTickets(0);
				} else if (id == 12852) {
					item = new Item(id, player.getCurrencyPouch().getEradicatedSeals());
					player.getCurrencyPouch().setEradicatedSeals(0);
				}
				items.add(item);
				refreshItems(itemsBefore);
				return;
			}
			if (money.getAmount() == 1) {
			} else if (((money.getAmount() + amount) > Integer.MAX_VALUE) || ((money.getAmount() + amount) < 0)) {
				amount = Integer.MAX_VALUE - money.getAmount();
				if (amount == 0) 
					return;
				item.setAmount(Integer.MAX_VALUE);
				if (id == 995) {
					player.getPouch().setAmount(player.getPouch().getAmount() - amount);
					player.getPouch().sendScript(false, amount);
					player.getPouch().refresh();
				} else if (id == 2996) {
					player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() - amount);
				} else if (id == 19819) {
					player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() - amount);
				} else if (id == 1464) {
					player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() - amount);
				} else if (id == 12852) {
					player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() - amount);
				}
				items.set(items.getThisItemSlot(item), item);
				refreshItems(itemsBefore);
				return;
			}
			
			if (id == 995) {
				if (player.getPouch().getAmount() >= amount) {
					player.getPouch().setAmount(player.getPouch().getAmount() - amount);
					player.getPouch().sendScript(false, amount);
					player.getPouch().refresh();
					items.add(item);
					refreshItems(itemsBefore);
				} else {
					player.sm("You have no money in your pouch.");
					return;
				}
			} else if (id == 2996) {
				if (player.getCurrencyPouch().get100MTicket() >= amount) {
					player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() - amount);
					items.add(item);
					refreshItems(itemsBefore);
				} else {
					player.sm("You have no more in your pouch.");
					return;
				}
			} else if (id == 19819) {
				if (player.getCurrencyPouch().getInvasionTokens() >= amount) {
					player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() - amount);
					items.add(item);
					refreshItems(itemsBefore);
				} else {
					player.sm("You have no more in your pouch.");
					return;
				}
			} else if (id == 1464) {
				if (player.getCurrencyPouch().getVoteTickets() >= amount) {
					player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() - amount);
					items.add(item);
					refreshItems(itemsBefore);
				} else {
					player.sm("You have no more in your pouch.");
					return;
				}
			} else if (id == 12852) {
				if (player.getCurrencyPouch().getEradicatedSeals() >= amount) {
					player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() - amount);
					items.add(item);
					refreshItems(itemsBefore);
				} else {
					player.sm("You have no more in your pouch.");
					return;
				}
			}
		}
	}
	
	public void addItem(int slot, int amount) {
		synchronized (this) {
			if (!isTrading())
				return;
			synchronized (target.getTrade()) {
				Item item = player.getInventory().getItem(slot);
				if (item == null)
					return;
				if (!ItemConstants.isTradeable(item)
						&& (player.getRights() < 2)) {
					player.getPackets()
							.sendGameMessage(
									"<col=FFF0000>You can't trade the item you're trying to trade.");
					return;
				}
				Item[] itemsBefore = items.getItemsCopy();
				int check = 0;
				for (Item i : itemsBefore) {
					if (i != null) {
						check++;
						if (check == 28) {
							player.sm("Not enough space in the trade screen.");
							return;
						}
						if ((i.getAmount() + amount > Integer.MAX_VALUE) ||
								(i.getAmount() + amount < 0) && amount != Integer.MAX_VALUE) {
							player.sm("Not enough space in the trade screen.");
							return;
						}
					}
				}
				int maxAmount = player.getInventory().getItems()
						.getNumberOf(item);
				if (amount < maxAmount)
					item = new Item(item.getId(), amount);
				else
					item = new Item(item.getId(), maxAmount);
				items.add(item);
				player.getInventory().deleteItem(slot, item);
				refreshItems(itemsBefore);
				cancelAccepted();
			}
		}
	}

	public void refreshItems(Item[] itemsBefore) {
		int[] changedSlots = new int[itemsBefore.length];
		int count = 0;
		for (int index = 0; index < itemsBefore.length; index++) {
			Item item = items.getItems()[index];
			if (itemsBefore[index] != item) {
				if (itemsBefore[index] != null
						&& (item == null
								|| item.getId() != itemsBefore[index].getId() || item
								.getAmount() < itemsBefore[index].getAmount()))
					sendFlash(index);
				changedSlots[count++] = index;
			}
		}
		int[] finalChangedSlots = new int[count];
		System.arraycopy(changedSlots, 0, finalChangedSlots, 0, count);
		refresh(finalChangedSlots);
		refreshFreeInventorySlots();
		refreshTradeWealth();
	}

	public void sendOptions() {
		player.getPackets().sendInterSetItemsOptionsScript(336, 0, 93, 4, 7,
				"Offer", "Offer-5", "Offer-10", "Offer-All", "Offer-X",
				"Value<col=FF9040>", "Lend");
		player.getPackets().sendIComponentSettings(336, 0, 0, 27, 1278);
		player.getPackets().sendInterSetItemsOptionsScript(335, 32, 90, 4, 7,
				"Remove", "Remove-5", "Remove-10", "Remove-All", "Remove-X",
				"Value");
		player.getPackets().sendIComponentSettings(335, 32, 0, 27, 1150);
		player.getPackets().sendInterSetItemsOptionsScript(335, 35, 90, true,
				4, 7, "Value");
		player.getPackets().sendIComponentSettings(335, 35, 0, 27, 1026);

	}

	public boolean isTrading() {
		return target != null;
	}

	public void setTradeModified(boolean modified) {
		if (modified == tradeModified)
			return;
		tradeModified = modified;
		sendTradeModified();
	}

	public void sendInterItems() {
		player.getPackets().sendItems(90, items);
		target.getPackets().sendItems(90, true, items);
	}

	public void refresh(int... slots) {
		player.getPackets().sendUpdateItems(90, items, slots);
		target.getPackets().sendUpdateItems(90, true, items.getItems(), slots);
	}

	public void accept(boolean firstStage) {
		synchronized (this) {
			if (target == null)
				return;
			if (!target.getTrade().isTrading()) {
				return;
			}
			for (Player p : World.getPlayers()) {
				if (p == null || p.equals(player)) 
					continue;
				Player target = p.getTrade().getTarget();
				if (target == null)
					continue;
				if (target.equals(player.getTrade().getTarget())) {
					player.setCloseInterfacesEvent(null);
					player.closeInterfaces();
					player.getTrade().closeTrade(CloseTradeStage.CANCEL);
					return;
				}
			}
			if (!isTrading())
				return;
			synchronized (target.getTrade()) {
				if (target.getTrade().accepted) {
					if (firstStage) {
						if (nextStage())
							target.getTrade().nextStage();
					} else {
						player.setCloseInterfacesEvent(null);
						player.closeInterfaces();
						closeTrade(CloseTradeStage.DONE);
					}
					return;
				}
				accepted = true;
				refreshBothStageMessage(firstStage);
			}
		}
	}

	public void sendValue(int slot, boolean traders) {
		if (!isTrading())
			return;
		Item item = traders ? target.getTrade().items.get(slot) : items
				.get(slot);
		if (item == null)
			return;
		if (!ItemConstants.isTradeable(item)) {
			player.getPackets().sendGameMessage("That item isn't tradeable.");
			return;
		}
		int price = EconomyPrices.getPrice(item.getId());
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName() + ": market price is " + price
						+ " coins.");
	}

	public void sendValue(int slot) {
		Item item = player.getInventory().getItem(slot);
		if (item == null)
			return;
		if (!ItemConstants.isTradeable(item)) {
			player.getPackets().sendGameMessage("That item isn't tradeable.");
			return;
		}
		int price = EconomyPrices.getPrice(item.getId());
		player.getPackets().sendGameMessage(
				item.getDefinitions().getName() + ": market price is " + price
						+ " coins.");
	}

	public void sendExamine(int slot, boolean traders) {
		if (!isTrading())
			return;
		Item item = traders ? target.getTrade().items.get(slot) : items
				.get(slot);
		if (item == null)
			return;
		player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
	}

	public boolean nextStage() {
		if (!isTrading())
			return false;
		int amount = player.getInventory().getItems().getUsedSlots() + target.getTrade().items.getUsedSlots();
		if (target.getTrade().items.getNumberOf(1464) > 0)
			amount--;
		if (target.getTrade().items.getNumberOf(19819) > 0)
			amount--;
		if (target.getTrade().items.getNumberOf(12852) > 0)
			amount--;
		if (target.getTrade().items.getNumberOf(2966) > 0)
			amount--;
		if (target.getTrade().items.getNumberOf(995) > 0) {
		if ((player.getPouch().getAmount() + target.getTrade().items.getNumberOf(995) > Integer.MAX_VALUE) ||
				((player.getPouch().getAmount() + target.getTrade().items.getNumberOf(995)) < 0)) { 
			} else 
				amount--;
		}
		if (amount > 28) {
			player.setCloseInterfacesEvent(null);
			player.closeInterfaces();
			closeTrade(CloseTradeStage.NO_SPACE);
			return false;
		}
		Item tmoney = new Item(1);
		for (int i = 0; i < items.getSize(); i++) {
			if (target.getTrade().items.get(i) != null) {
			if (target.getTrade().items.get(i).getId() == 995 && target.getTrade().items.get(i).getAmount() > 1) {
				tmoney = new Item(995, target.getTrade().items.get(i).getAmount());
			}
			}
		}		
		Item money = new Item(1);
		for (int i = 0; i < items.getSize(); i++) {
			if (items.get(i) != null) {
			if (items.get(i).getId() == 995 && items.get(i).getAmount() > 1) {
				money = new Item(995, items.get(i).getAmount());
			}
			}
		}
		if (money.getId() == 995 || tmoney.getId() == 995) {
			if ((money.getAmount() + tmoney.getAmount() > Integer.MAX_VALUE) ||
				(money.getAmount() + tmoney.getAmount() < 0) ||
				(!player.hasCoinSpace(tmoney.getAmount())) ||
				(!target.hasCoinSpace(money.getAmount()))) {
					player.setCloseInterfacesEvent(null);
					player.closeInterfaces();
					closeTrade(CloseTradeStage.OVERMAX);
					return false;	
			}
		}
		if (money.getId() != 995) {
		if (player.getInventory().getItems().goesOverAmount(items)) {
			player.setCloseInterfacesEvent(null);
			player.closeInterfaces();
			closeTrade(CloseTradeStage.OVERMAX);
			return false;
		}
		if (target.getInventory().getItems().goesOverAmount(items)) {
			target.setCloseInterfacesEvent(null);
			target.closeInterfaces();
			closeTrade(CloseTradeStage.OVERMAX);
			return false;
		}		
		}
		accepted = false;
		player.getInterfaceManager().sendInterface(334);
		player.getPackets().sendHideIComponent(334, 67, true);
		player.getInterfaceManager().closeInventoryInterface();
		player.getPackets().sendHideIComponent(334, 55,
				!(tradeModified || target.getTrade().tradeModified));
		refreshBothStageMessage(false);
		return true;
	}

	public void refreshBothStageMessage(boolean firstStage) {
		refreshStageMessage(firstStage);
		target.getTrade().refreshStageMessage(firstStage);
	}

	public void refreshStageMessage(boolean firstStage) {
		player.getPackets().sendIComponentText(firstStage ? 335 : 334,
				firstStage ? 39 : 34, getAcceptMessage(firstStage));
	}

	public String getAcceptMessage(boolean firstStage) {
		if (accepted)
			return "Waiting for other player...";
		if (target.getTrade().accepted)
			return "Other player has accepted.";
		return firstStage ? "" : "Are you sure you want to make this trade?";
	}

	public void sendTradeModified() {
		player.getPackets().sendConfig(1042, tradeModified ? 1 : 0);
		target.getPackets().sendConfig(1043, tradeModified ? 1 : 0);
	}

	public void refreshTradeWealth() {
		int wealth = getTradeWealth();
		player.getPackets().sendGlobalConfig(729, wealth);
		target.getPackets().sendGlobalConfig(697, wealth);
	}

	public void refreshFreeInventorySlots() {
		int freeSlots = player.getInventory().getFreeSlots();
		target.getPackets().sendIComponentText(
				335,
				23,
				"has " + (freeSlots == 0 ? "no" : freeSlots) + " free"
						+ "<br>inventory slots");
	}

	public int getTradeWealth() {
		int wealth = 0;
		for (Item item : items.getItems()) {
			if (item == null)
				continue;
			wealth += PriceManager.getBuyPrice(item) * item.getAmount();
		}
		return wealth;
	}

	private static enum CloseTradeStage {
		CANCEL, NO_SPACE, DONE, OVERMAX
	}

	public Player getTarget() {
		return target;
	}
	
	public void addToPouch() {
		for (Item item: items.getItems()) {
			if (item != null) {
				switch (item.getId()) {
				case 2996:
				case 12852:
				case 1464:
				case 19819:
					player.getCurrencyPouch().addToPouch(item);
					items.remove(item);
					break;
				case 995:
					if ((player.getPouch().getAmount() + (item.getAmount())) <= Integer.MAX_VALUE &&
					(player.getPouch().getAmount() + (item.getAmount())) > 0) {		
						player.getPouch().sendScript(true, item.getAmount());
						player.sm(Utils.formatNumber(item.getAmount()) + " coins were added to your money pouch.");
						player.getPouch().setAmount(player.getPouch().getAmount() + item.getAmount());
						player.getPouch().refresh();
					} else 
						player.getInventory().addItem(item);
					items.remove(item);
					break;
				}
			}
		}
	}
	
	public void addToPouch(Player target, ItemsContainer<Item> items, Player player) {
		for (Item item: items.getItems()) {
			if (item != null) {
				switch (item.getId()) {
				case 2996:
				case 12852:
				case 1464:
				case 19819:
					target.getCurrencyPouch().addToPouch(item);
					items.remove(item);
					Logger.printTradeLog(target, player, item);
					break;
				case 995:
					if ((target.getPouch().getAmount() + (item.getAmount())) <= Integer.MAX_VALUE &&
					(target.getPouch().getAmount() + (item.getAmount())) > 0) {		
						target.getPouch().sendScript(true, item.getAmount());
						target.sm(Utils.formatNumber(item.getAmount()) + " coins were added to your money pouch.");
						target.getPouch().setAmount(target.getPouch().getAmount() + item.getAmount());
						target.getPouch().refresh();
					} else 
						target.getInventory().addItem(item);
					Logger.printTradeLog(target, player, item);
					items.remove(item);
					break;
				}
			}
		}
	}	
	
	public void closeTrade(CloseTradeStage stage) {
		synchronized (this) {
			if (target == null)
				return;
			synchronized (target.getTrade()) {
				Player oldTarget = target;
				target = null;
				tradeModified = false;
				accepted = false;
				if (CloseTradeStage.DONE != stage) { // Players get their own items back
					addToPouch();
					player.getInventory().getItems().addAll(items);
					player.getInventory().init();
					items.clear();
				} else { // Players completing a trade, transfer items
					addToPouch(player, oldTarget.getTrade().items, oldTarget);
					addToPouch(oldTarget, items, player);
					player.getPackets().sendGameMessage("Accepted trade.");
					Logger.printTradeLog(player, oldTarget, oldTarget.getTrade().items.toArray());
					player.getInventory().getItems().addAll(oldTarget.getTrade().items);
					player.getInventory().init();
					oldTarget.getTrade().items.clear();
				}
				if (oldTarget.getTrade().isTrading()) {
					oldTarget.setCloseInterfacesEvent(null);
					oldTarget.closeInterfaces();
					oldTarget.getTrade().closeTrade(stage);
					player.setCloseInterfacesEvent(null);
					player.closeInterfaces();
					player.getTrade().closeTrade(stage);
					if (CloseTradeStage.CANCEL == stage)
						oldTarget.getPackets().sendGameMessage("<col=ff0000>Other player declined trade!");
					else if (CloseTradeStage.NO_SPACE == stage) {
						player.getPackets().sendGameMessage("You don't have enough space in your inventory for this trade.");
						oldTarget.getPackets().sendGameMessage("Other player doesn't have enough space in their inventory for this trade.");
					} else if (CloseTradeStage.OVERMAX == stage) {
						player.getPackets().sendGameMessage("Over maximum capacity of item!");
						oldTarget.getPackets().sendGameMessage("Over maximum capacity of item!");
					}
				}
			}
			}
	}

}
