package com.rs.content.exchange;



import java.io.IOException;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ItemDefinitions.FileUtilities;
import com.rs.content.exchange.ItemOffer.OfferType;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.Utils;


public class ExchangeHandler {



	public static boolean handleButtons(final Player player, final int interfaceId, final int componentId) {

		final ItemOffer offer;

		switch(interfaceId) {

		case 105:

			switch(componentId) {

			/*

			 * Selling Button Ids

			 */

			case 32:

			case 48:

			case 64:

			case 83:

			case 102:

			case 121:
				player.getInventory().refresh();
				player.offerType = OfferType.SELL;

				player.setGESlot(getSlot(componentId));

				resetInterfaceConfigs(player);

				player.getPackets().sendConfig(1113, 1);

				player.getInterfaceManager().sendInventoryInterface(107);

				final Object[] params = new Object[]{ "", "", "", "", "Offer", -1, 0, 7, 4, 93, 7012370 };

				player.getPackets().sendRunScript(149, params);

				player.getPackets().sendItems(93, player.getInventory().getItems());

				player.getPackets().sendHideIComponent(107, 0, false);		

				player.getPackets().sendIComponentSettings(107, 18, 0, 27, 1026);

				int slot2 = getSlot(componentId);

				player.getPackets().sendConfig(1112, slot2);

				player.getPackets().sendHideIComponent(105, 196, true);

				return true;

				/*

				 * Buying Button Ids

				 */

			case 31:

			case 47:

			case 63:

			case 82:

			case 101:

			case 120:

				player.getTemporaryAttributtes().put("grand_exchange_buying", true);

				resetInterfaceConfigs(player);

				searchScript(player);

				player.offerType = OfferType.BUY;

				player.setGESlot(getSlot(componentId));

				return true;

			case 190:

				searchScript(player);
				player.getInventory().refresh();
				return true;

			case 186:

				offer = player.getGeOffers()[player.getGESlot()];

				try {

					for (String lines : FileUtilities.readFile("./data/items/grand_exchange/unbuyables.txt")) {

						String[] data = lines.split(" ");

						Item item = new Item(offer.getId());

						if (item.getDefinitions().getName().toLowerCase().contains(Utils.getCompleted(data, 0))) {

							player.getDialogueManager().startDialogue("SimpleMessage", "You can only buy or sell " + item.getDefinitions().getName() + "s with other players.");

							return true;

						}

					}

				} catch (IOException e) {

					e.printStackTrace();

				}

				if (offer.getPrice() * offer.getAmount() > Integer.MAX_VALUE ||

						offer.getPrice() * offer.getAmount() == Integer.MAX_VALUE ||

						offer.getPrice() * offer.getAmount() >= Integer.MAX_VALUE ||

						offer.getPrice() * offer.getAmount() < 0) {

					player.getPackets().sendGameMessage("The price of the item is too high!");

					return true;

				}

				if (offer.getPrice() == 0 || offer.getAmount() == 0 || offer.getPrice() * offer.getAmount() == 0) {

					player.getPackets().sendGameMessage("Invalid amount.");

					return true;

				}

				int coins = player.getInventory().getItems().getNumberOf(995);

				if (coins < offer.getAmount() * offer.getPrice() && offer.getType() == OfferType.BUY) {
					player.getInventory().refresh();
					player.getPackets().sendGameMessage("You do not have enough coins to purchase this item.");

					return true;

				}

				if (offer.getType() == OfferType.SELL && offer.getAmount() > player.getInventory().getItems().getNumberOf(offer.getId())) {
					player.getInventory().refresh();
					player.getPackets().sendGameMessage("You do not have enough of that item to sell.");

					return true;

				}

				if (offer.getType() == OfferType.BUY) {
					player.getInventory().refresh();
					player.getInventory().deleteItem(new Item(995, offer.getPrice() * offer.getAmount()));

					if (player.getInventory().getFreeSlots() < offer.getAmount() && ItemDefinitions.getItemDefinitions(offer.getId()).getCertId() != -1) {

						offer.setId(ItemDefinitions.getItemDefinitions(offer.getId()).getCertId());

					}

					player.getInventory().addItem(offer.getId(), offer.getAmount());

				} else {

					player.getInventory().deleteItem(new Item(offer.getId(), offer.getAmount()));

					player.getInventory().addItem(new Item(995, offer.getPrice() * offer.getAmount()));

				} 

				mainInterface(player);
				player.getInventory().refresh();
				if (!GrandExchange.getOffers().contains(offer))
					player.getInventory().refresh();
					GrandExchange.getOffers().add(offer);

				player.sendMessage("One or more of your Grand Exchange offers have been completed.");

				break;

			case 128:
				player.getInventory().refresh();
				resetInterfaceConfigs(player);

				if (player.offerType.equals(OfferType.SELL)) 

					player.getInterfaceManager().sendInventory();

				else

					player.getPackets().sendRunScript(573);

				break;

			case 157:

			case 160:
				player.getInventory().refresh();
				offer = player.getGeOffers()[player.getGESlot()];

				offer.increase();

				player.getPackets().sendConfig(1110, offer.getAmount());

				break;

			case 155:
				player.getInventory().refresh();
				offer = player.getGeOffers()[player.getGESlot()];

				offer.decrease();

				player.getPackets().sendConfig(1110, offer.getAmount());

				break;

			case 162:
				player.getInventory().refresh();
				offer = player.getGeOffers()[player.getGESlot()];

				offer.increase(10);

				player.getPackets().sendConfig(1110, offer.getAmount());

				break;

			case 164:
				player.getInventory().refresh();
				offer = player.getGeOffers()[player.getGESlot()];

				offer.increase(100);

				player.getPackets().sendConfig(1110, offer.getAmount());

				break;

			//case 166:

				//offer = player.getGeOffers()[player.getGESlot()];

				//if (offer.getType() == OfferType.BUY) {

				//	offer.increase(1000);

				//} else {

				//	offer.setAmount(player.getInventory().getItems().getNumberOf(offer.getId()));

				//}

				//player.getPackets().sendConfig(1110, offer.getAmount());

				//break;

			case 168:
				player.getInventory().refresh();
				offer = player.getGeOffers()[player.getGESlot()];

				player.getPackets().sendRunScript(108, new Object[] { "How many would you like to buy?" });

				player.getTemporaryAttributtes().put("grand_exchange_offer", offer);

				break;

			case 181:

			case 175:

			case 177:

			case 179:

			case 169:

			case 171:
				player.getInventory().refresh();
				player.getPackets().sendGameMessage("You need not to change the price; you recieve the item all the time.");

				return true;

			default:

				System.err.println("UNHANDLED GE BUTTON - " + componentId);

			}

			return true;

		}

		return false;

	}



	/**

	 * Show the Main InterfacePacket.

	 * 

	 * @param player

	 *            The Owner.

	 */

	public static void mainInterface(Player player) {
		player.getPackets().sendConfig(563, 4194304);
		player.getPackets().sendConfig(1112, -1);
		player.getPackets().sendConfig(1113, -1);
		player.getPackets().sendConfig(1109, -1);
		player.getPackets().sendConfig(1110, 0);
		player.getInterfaceManager().sendInterface(105);
	}



	/**

	 * Resets the Configurations of the InterfacePacket.

	 * 

	 * @param player

	 *            The Owner.

	 */

	private static void resetInterfaceConfigs(Player player) {

		player.getPackets().sendConfig2(1109, -1);

		player.getPackets().sendConfig2(1110, 0);

		player.getPackets().sendConfig2(1111, 0);

		player.getPackets().sendConfig2(1112, -1);

		player.getPackets().sendConfig2(1113, 0);

	}



	/**

	 * Show the Search InterfacePacket.

	 * 

	 * @param player

	 *            The Owner.

	 */

	private static void searchScript(Player player) {

		player.getPackets().sendConfig(1109, -1);

		player.getPackets().sendConfig(1112, 0);

		player.getPackets().sendConfig(1113, 0);

		player.getPackets().sendConfig1(1241, 16750848);

		player.getPackets().sendConfig1(1242, 15439903);

		player.getPackets().sendConfig1(741, -1);

		player.getPackets().sendConfig1(743, -1);

		player.getPackets().sendConfig1(744, 0);

		player.getPackets().sendInterface(true, 752, 7, 389);

		player.getPackets().sendRunScript(570, new Object[] { "Grand Exchange Item Search" });

		//		player.getIOSession().write(new ConfigPacket(player, 1109, -1));

		//		player.getIOSession().write(new ConfigPacket(player, 1112, 0));

		//		player.getIOSession().write(new ConfigPacket(player, 1113, 0));

		//		player.getIOSession().write(new CS2Config(player, 1241, 16750848));

		//		player.getIOSession().write(new CS2Config(player, 1242, 15439903));

		//		player.getIOSession().write(new CS2Config(player, 741, -1));

		//		player.getIOSession().write(new CS2Config(player, 743, -1));

		//		player.getIOSession().write(new CS2Config(player, 744, 0));

		//		player.getIOSession().write(new InterfacePacket(player, 752, 7, 389, false));

		//		player.getIOSession().write(new CS2Script(player, 570, "s", new Object[] { "Grand Exchange Item Search" }));

	}





	/**

	 * Gets the Box Slot id by switching the buttons,<br>	

	 * </br> Also helps you to figure the box slot configuration value.

	 * 

	 * @param componentId

	 * @return Slot id.

	 */

	private static int getSlot(int componentId) {

		switch (componentId) {

		case 31:

		case 32:

		case 19:

			return 0;

		case 47:

		case 35:

		case 48:

			return 1;

		case 63:

		case 51:

		case 64:

			return 2;

		case 82:

		case 83:

		case 70:

			return 3;

		case 101:

		case 102:

		case 89:

			return 4;

		case 120:

		case 108:

		case 121:

			return 5;

		default:

			return -1;

		}

	}





	public static void sendSellItem(Player player, int itemId, int slot, int componentId) {

		/*switch(componentId) {

		case 18:

			if (player.getInventory().containsItem(itemId, 1)) {
				player.getInventory().refresh();
				if (!ItemConstants.isTradeable(new Item(itemId)) || itemId == 995 || ItemDefinitions.getItemDefinitions(itemId).getGEPrice() == 0) {

					player.sendMessage("You cannot sell this item to the grand exchange.");

					return;

				}
				player.getInventory().refresh();
				final ItemOffer offer = new ItemOffer(player, itemId, player.offerType, (Integer) player.getGESlot());

				offer.setPrice((int) (offer.getPrice() * 0.75));

				player.getGeOffers()[offer.getSlot()] = offer;

				offer.getOwner().getPackets().sendConfig(1109, offer.getId());

				offer.getOwner().getPackets().sendConfig(1110, 1);

				offer.getOwner().getPackets().sendConfig( 1114, offer.getPrice());

				offer.getOwner().getPackets().sendConfig( 1115, offer.getDefinitions().getValue() / 3);

				offer.getOwner().getPackets().sendConfig(1116, offer.getDefinitions().getValue() / 4);

				offer.getOwner().getPackets().sendConfig(1111, offer.getPrice());

				offer.getOwner().getPackets().sendIComponentText(105, 143, ItemExamines.getExamine(new Item(itemId)));

			}

			return;

		}*/

	}

}