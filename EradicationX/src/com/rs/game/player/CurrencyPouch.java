package com.rs.game.player;

import java.io.Serializable;

import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.utils.Utils;

public class CurrencyPouch implements Serializable {
	private static final long serialVersionUID = 1597252927541432069L;
	
	public static final byte HMTICKET = 0, INVASION = 1, VOTE = 2, SEAL = 3;
	
	private int[] currencies;
	
	private transient Player player;
	
	public CurrencyPouch() {
		currencies = new int[4];
	}

	public int[] getCurrencies() {
		World.sendWorldMessage(""+currencies.length, false);
		int[] tempCurrencies;
		tempCurrencies = new int[5];
		if (currencies.length == 4) {
			for (int i = 0; i < 4; i++) {
				tempCurrencies[i] = currencies[i];
			}
			currencies = new int[5];
			currencies = tempCurrencies;
			return currencies;
		} else 
			return currencies;
	}
	
	public void openInterface() {
		player.getPackets().sendIComponentText(3019, 5, "100M Ticket <br>"+Utils.formatNumber(currencies[HMTICKET]));
		player.getPackets().sendIComponentText(3019, 6, "Invasion Tokens <br>"+Utils.formatNumber(currencies[INVASION]));
		player.getPackets().sendIComponentText(3019, 7, "Vote Tickets <br>"+Utils.formatNumber(currencies[VOTE]));
		player.getPackets().sendIComponentText(3019, 8, "Eradicated Seals <br>"+Utils.formatNumber(currencies[SEAL]));
	}

	public void setCurrencies(int[] currencies) {
		this.currencies = currencies;
		openInterface();
	}
	
	public void set100MTicket(int amount) {
		if (currencies[HMTICKET] > amount)
			player.sm(Utils.formatNumber(currencies[HMTICKET] - amount) + " 100M Tickets have been removed from your pouch.");
		else if (currencies[HMTICKET] < amount)
			player.sm(Utils.formatNumber(amount - currencies[HMTICKET]) + " 100M Tickets have been added to your pouch.");
		currencies[HMTICKET] = amount;
		openInterface();
	}
	
	public void setInvasionTokens(int amount) {
		if (currencies[INVASION] > amount)
			player.sm(Utils.formatNumber(currencies[INVASION] - amount) + " Invasion Tokens have been removed from your pouch.");
		else if (currencies[INVASION] < amount)
			player.sm(Utils.formatNumber(amount - currencies[INVASION]) + " Invasion Tokens have been added to your pouch.");
		currencies[INVASION] = amount;
		openInterface();
	}
	
	public void setVoteTickets(int amount) {
		if (currencies[VOTE] > amount)
			player.sm(Utils.formatNumber(currencies[VOTE] - amount) + " Vote Tickets have been removed from your pouch.");
		else if (currencies[VOTE] < amount)
			player.sm(Utils.formatNumber(amount - currencies[VOTE]) + " Vote Tickets have been added to your pouch.");
		currencies[VOTE] = amount;
		openInterface();
	}
	
	public void setEradicatedSeals(int amount) {
		if (currencies[SEAL] > amount)
			player.sm(Utils.formatNumber(currencies[SEAL] - amount) + " Eradicated Seals have been removed from your pouch.");
		else if (currencies[SEAL] < amount)
			player.sm(Utils.formatNumber(amount - currencies[SEAL]) + " Eradicated Seals have been added to your pouch.");
		currencies[SEAL] = amount;
		openInterface();
	}
	
	public boolean spend100mTicket(int amount) {
		if (currencies[HMTICKET] >= amount) {
			set100MTicket(get100MTicket() - amount);
			return true;
		} else if (player.getInventory().getNumerOf(2996) >= amount) {
			player.getInventory().deleteItem(2996, amount);
			return true;
		}
		if ((currencies[HMTICKET] + player.getInventory().getNumerOf(2996)) >= amount) {
			int temp = amount;
			temp = currencies[HMTICKET];
			set100MTicket(0);
			amount = amount - temp;
			player.getInventory().deleteItem(2996, amount);
			return true;
		}
		return false;
	}
	
	public boolean spendInvasionTokens(int amount) {
		if (currencies[INVASION] >= amount) {
			setInvasionTokens(getInvasionTokens() - amount);
			return true;
		} else if (player.getInventory().getNumerOf(19819) >= amount) {
			player.getInventory().deleteItem(19819, amount);
			return true;
		}
		if ((currencies[INVASION] + player.getInventory().getNumerOf(19819)) >= amount) {
			int temp = amount;
			temp = currencies[INVASION];
			setInvasionTokens(0);
			amount = amount - temp;
			player.getInventory().deleteItem(19819, amount);
			return true;
		}
		return false;
	}
	
	public void addToPouch(Item item) {
		if (item == null)
			return;
		switch (item.getId()) {
		case 1464:
			setVoteTickets(currencies[VOTE] + item.getAmount());
			break;
		case 12852:
			setEradicatedSeals(currencies[SEAL] + item.getAmount());
			break;
		case 2996:
			set100MTicket(currencies[HMTICKET] + item.getAmount());
			break;
		case 19819:
			setInvasionTokens(currencies[INVASION] + item.getAmount());
			break;
		}
	}
	
	public boolean spendVoteTickets(int amount) {
		if (currencies[VOTE] >= amount) {
			setVoteTickets(getVoteTickets() - amount);
			return true;
		} else if (player.getInventory().getNumerOf(1464) >= amount) {
			player.getInventory().deleteItem(1464, amount);
			return true;
		}
		if ((currencies[VOTE] + player.getInventory().getNumerOf(1464)) >= amount) {
			int temp = amount;
			temp = currencies[VOTE];
			setVoteTickets(0);
			amount = amount - temp;
			player.getInventory().deleteItem(1464, amount);
			return true;
		}
		return false;
	}
	
	public boolean spendEradicatedSeals(int amount) {
		if (currencies[SEAL] >= amount) {
			setEradicatedSeals(getEradicatedSeals() - amount);
			return true;
		} else if (player.getInventory().getNumerOf(12852) >= amount) {
			player.getInventory().deleteItem(12852, amount);
			return true;
		}
		if ((currencies[SEAL] + player.getInventory().getNumerOf(12852)) >= amount) {
			int temp = amount;
			temp = currencies[SEAL];
			setEradicatedSeals(0);
			amount = amount - temp;
			player.getInventory().deleteItem(12852, amount);
			return true;
		}
		return false;
	}
	
	public boolean canAfford100mTicket(int amount) {
		if (currencies[HMTICKET] >= amount) {
			return true;
		} else if (player.getInventory().getNumerOf(2996) >= amount) {
			return true;
		}
		if ((currencies[HMTICKET] + player.getInventory().getNumerOf(2996)) >= amount) {
			return true;
		}
		return false;
	}
	
	public boolean canAffordVoteTicket(int amount) {
		if (currencies[VOTE] >= amount) {
			return true;
		} else if (player.getInventory().getNumerOf(1464) >= amount) {
			return true;
		}
		if ((currencies[VOTE] + player.getInventory().getNumerOf(1464)) >= amount) {
			return true;
		}
		return false;
	}
	
	public int get100MTicket() {
		return currencies[HMTICKET];
	}
	
	public int getInvasionTokens() {
		return currencies[INVASION];
	}
	
	public int getVoteTickets() {
		return currencies[VOTE];
	}
	
	public int getEradicatedSeals() {
		return currencies[SEAL];
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}