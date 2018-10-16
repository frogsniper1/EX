package com.rs.game.player;

import java.io.Serializable;
import java.util.ArrayList;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;
import com.rs.utils.Utils;

public class KeybindFunctions implements Serializable {
	
	private static final long serialVersionUID = 533796055118406631L;
	
	public ArrayList<Keybind> entries;
	
	private boolean keybindsToggle;
	private boolean escapeToggle;
	private transient Player player;
	private transient String tempMessage;
	
	public KeybindFunctions(Player player) {
		entries = new ArrayList<Keybind>();
		entries.add(new Keybind(Keybind.F6));
		entries.add(new Keybind(Keybind.F7));
		entries.add(new Keybind(Keybind.F8));
		entries.add(new Keybind(Keybind.F9));
		entries.add(new Keybind(Keybind.F10));
		entries.add(new Keybind(Keybind.F11));
		entries.add(new Keybind(Keybind.F12));
		entries.add(new Keybind(Keybind.HOME));
		entries.add(new Keybind(Keybind.INSERT));
		entries.add(new Keybind(Keybind.DELETE));
		entries.add(new Keybind(Keybind.END));
		this.player = player;
		keybindsToggle = true;
		escapeToggle = true;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		tempMessage = "";
	}
	
	public Keybind getKeybind(int key) {
		for (Keybind keybind : entries) {
			if (keybind.getKeyvalue() == key)
				return keybind;
		}
		return null;
	}
	
	public void doAction(int key) {
		Keybind kb = getKeybind(key);
		if (!Utils.getStringByKey(key).equals("") && !player.getInterfaceManager().containsChatBoxInter()) {
			tempMessage += Utils.getStringByKey(key);
		}
		if (key == 84)
			tempMessage = "";
		if (kb == null && key != 13)
			return;
		if (key == 13) {
			if (!hasEscapeToggle())
				return;
			if (!tempMessage.equals("")) {
				tempMessage = "";
				return;
			}
			if (player.getInterfaceManager().containsChatBoxInter())
				player.getInterfaceManager().closeChatBoxInterface();
			else if (player.getInterfaceManager().containsScreenInter()) {
				player.stopAll();
			} else
				player.getInterfaceManager().sendInterface(3003);
			return;
		}
		if (player.getInterfaceManager().containsChatBoxInter()) {
			return;
		}
		if (player.getInterfaceManager().containsScreenInter()) {
			player.sm("Please finish what you're doing before using a keybind.");
			return;
		}
		if (player.getJailed() > 0) {
			player.sm("You cannot access keybinds until you're unjailed.");
			return;			
		}
		switch (kb.getKeyFunctionValue()) {
		case 1: // Home Teleport
			if (!player.canSpawn()) {
				player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				return;
			}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3968, 4823, 1));
			break;
		case 2: // Boss Teleport
			player.getDialogueManager().startDialogue("PortalTeleport", 100);
			break;
		case 3: // Quest interface
			player.getEXQuestManager().sendQuestInterface();
            break;
		case 4: // Player management
            player.getDialogueManager().startDialogue("Noticeboard", 230);
			break;
		case 5: // 100m command
			int tickets = 0;
			int coins = 0;
			if (player.getInventory().containsItem(995, 100000000)) {
			if (player.getTrade().isTrading()) {
				player.sm("Please finish trading before converting your coins.");
				return;
			}
			if (player.getInventory().hasFreeSlots() || player.getCurrencyPouch().canAfford100mTicket(1)) {	
			tickets = (player.getInventory().getItems().getNumberOf(995)) / 100000000;
			coins = tickets * 100000000;
			player.getInventory().deleteItem(995, coins);
			player.getInventory().addItem(2996, tickets);
			player.sm(Utils.formatNumber(coins) + " coins have been exchanged for " + tickets +" 100M Tickets.");
			} else {
				player.sm("You don't have enough inventory space for this command!");
				return;
			}
			} else 
			player.getPackets().sendGameMessage("<col=FFFF00>Tickets are 100m each.</col>");
			return;
		case 6: // shops
			if (!player.canSpawn()) {
				player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				return;
			}
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3968, 4822, 2));
			player.getPackets().sendGameMessage("<img=5> Shops. <img=5>");
			return;
		case 7: // vote
			player.getPackets().sendOpenURL(Settings.VOTE_LINK);
			player.getPackets().sendGameMessage("<col=0fffff> Please do ::claim to receive your reward once voting on all of the pages.");
			return;
		case 8: // forums
			player.getPackets().sendOpenURL(Settings.FORUMS_LINK);
			return;
		case 9: // bank hopefully this works
			if (player.isExtremeDonator() || player.isLentEradicator() 
					|| player.isLentDonator() || player.isLentExtreme() ||
					player.isLentSavior() || player.isSupporter() ||
					player.isExtremeDonator() || player.isDonator() ||
					player.isSavior() || player.isEradicator()) {
			if (player.isInsideHairymonkey) {
				player.sm("You can't bank inside here.");
				return;
			}
			if (player.isIronMan())
				return;
			if (!player.canSpawn()) {
				player.getPackets().sendGameMessage("You can't bank while you're in this area.");
				return;
			}
			player.stopAll();
			player.getBankT().openBank();
			} else {
				player.sm("This is for donators only.");
			}
			return;
		case 10: // dice
			if (!player.canSpawn()) {
				player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				return;
			}
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2196, 4961, 0));
            return;
		case 11: // chill
			if (!player.canSpawn()) {
				player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				return;
			}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4382, 5917, 0));
        return;
		case 12: // Clan wars
			if (!player.canSpawn()) {
				player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				return;
			}			
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2991, 9679, 0));
        return;
		case 13: // price guide
			player.getPackets().sendOpenURL(Settings.FORUMS_LINK);
			return;
		case 14: // withdraw from money pouch
			if (player.getTrade().isTrading()) {
				player.sm("Please finish trading before withdrawing money from your pouch.");
			} else {
				player.getTemporaryAttributtes().put("money_remove", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] { "Money pouch currently contains: "
																		+ Utils.formatNumber(player.getPouch().getAmount()) + " coins"});
			}
			break;
		case 15: // WithdrawCurrencyPouch
			player.getDialogueManager().startDialogue("WithdrawCurrencyPouch");
			break;
		case 16: // eradzone
			if (player.checkEradicator()) {
			
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182, 5713, 0)); 	
				player.getPackets().sendGameMessage("Eradicator Zone");
					return;
			} else {
				player.sm("You're not an eradicator.");
			}
			break;
		case 17: // superzone hopefully this works
			if (player.checkSuper()) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3363, 3340, 0));
					return;
			} else {
				player.sm("This is for super donators only.");
			}
			return;
		case 18: // raffle dialogue
			player.getDialogueManager().startDialogue("LotteryDialogue");
			break;
		case 19: // cosmetic dialogue
			CosmeticItems.openInterface(player);
			player.setInterfaceAmount(0);
			break;
		case 20: // Lend a Rank
			player.getDialogueManager().startDialogue("LendaRank");
			break;
		case 21: // Borrow a Rank
			if (!player.isIronMan()) {
				if (player.getCollectLoanMoney() > 0)
					 player.getDialogueManager().startDialogue("CollectMoney", 12);
				else
				 player.getDialogueManager().startDialogue("LendingList");	
				} else {
					player.sm("The rank lender does not deal with ironmen.");
				}
			break;
		case 22:
			if (!player.canSpawn() || player.inInstance()) {
				player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
				return;
			}
			Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2214, 3304, 0));
			return;
		case 23:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(9);
			else
				player.getPrayer().switchPrayer(19);
			break;
		case 24:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(7);
			else
				player.getPrayer().switchPrayer(17);
			break;
		case 25:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(8);
			else
				player.getPrayer().switchPrayer(18);
			break;
		case 26:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(19);
			else
				player.sm("You must be on curses to use turmoil.");
			break;
		case 27:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(18);
			else
				player.sm("You must be on curses to use soulsplit.");
			break;
		case 28:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(0);
			else
				player.getPrayer().switchPrayer(10);
			break;
		case 29:
			if (player.getPrayer().isAncientCurses())
				player.getPrayer().switchPrayer(5);
			else
				player.sm("You must be on curses to use berserker.");
			break;
		case 30:
			player.getPrayer().switchQuickPrayers();
			break;
		}
		}
	
	public void setKeyFunction(int key, int function, String name) {
		Keybind kb = getKeybind(key);
		kb.setFunction(name, function);
		player.sm("Set keybind " + Keybind.getKeyName(key) + " to the function: "
				+ name + " .");
	}

	public boolean getKeybindsToggle() {
		return keybindsToggle;
	}

	public boolean setKeybindsToggle() {
		keybindsToggle = !keybindsToggle;
		return keybindsToggle;
	}

	public boolean hasEscapeToggle() {
		return escapeToggle;
	}

	public boolean setEscapeToggle() {
		escapeToggle = !escapeToggle;
		return escapeToggle;
	}

	public String getTempMessage() {
		return tempMessage;
	}

	public void setTempMessage(String tempMessage) {
		this.tempMessage = tempMessage;
	}
	
}