package com.rs.game.player;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.utils.Logger;
import com.rs.utils.RaffleWinner;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class Raffle implements Serializable {

	private static final long serialVersionUID = 2496224192921198991L;
	public static ArrayList<String> list;

	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File("data/Raffle.ser");
		if (file.exists()) {
			try {
				list = (ArrayList<String>) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Raffle list has loaded.");
		list = new ArrayList<String>();
	}

	public static void addRaffle(Player player, int amount) {
		while (amount > 0) {
			list.add(player.getUsername());
			amount--;
		}
		save();
	}
	

	public static void save() {
		try {
			SerializableFilesManager.storeSerializableClass(list, new File(
					"data/Raffle.ser"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void drawRaffle() {
		int entries = getEntries();
		int temp = getEntries();
		int winners = 1; int winner = 0;
		String winnername = null;
		while (temp >= 10000) {
			winners++;
			temp = temp - 10000;
		}
		while (winners > 1) {
			winner = Utils.getRandom(entries);
			Item item = new Item(pickRare());
				if (list.get(winner) != null) {
					winnername = list.get(winner);
					World.sendWorldMessage("<col=7E2CE8>[<img=5>Lottery]: "+ Utils.formatPlayerNameForDisplay(winnername)+" won " 
											+ item.getName() + " from the rare prize pool! Congratulations!", false);	
					handOutReward(winnername, item.getId(), 1);
					RaffleWinner.addWinner(winnername, item.getName(), 1);
				}
			winners--;
		}
		winner = Utils.getRandom(entries);
			if (list.get(winner) != null) {
				winnername = list.get(winner);
				World.sendWorldMessage("<col=7E2CE8>[<img=5>Lottery]: "+ Utils.formatPlayerNameForDisplay(winnername)+" won " 
										+ getPrizeCoins() + "00M from the 100M Ticket prize pool! Congratulations!", false);	
				handOutReward(winnername, 2996, getPrizeCoins());
				RaffleWinner.addWinner(winnername, "100m Tickets", getPrizeCoins());
			}
		deleteRaffle();
	}
	
	public synchronized static void deleteRaffle() {
		try {
			list.clear();
			save();
		}
		catch (Throwable t) {
			Logger.handle(t);
		}
	}	
	
	public static void handOutReward(String username, int id, int amount) {
			Player player = World.getPlayer(username);
			if (player != null) {
				player.sm("Your raffle reward was sent to your bank.");
				player.getBank().addItem(id, amount, true);
			} else {
				player = SerializableFilesManager.loadPlayer(username);
				try {
					player.getBank().addItem(id, amount, false);
					player.raffleMessage = "Congratulations, you won the weekly raffle! Your reward, "
							+ amount +" " + new Item(id).getName() + " has been added to your bank.";
					SerializableFilesManager.storeSerializableClass(player,
							new File("data/playersaves/characters/" + username
									+ ".p"));		
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	public static int getEntries() {
		int entries = 0;
		entries = list.size();
		return entries;
	}
	
	public static int getPersonalEntries(Player player) {
		int entries = 0;
		for (String raffle : list) {
			if (raffle.equalsIgnoreCase(player.getUsername()))
				entries++;
		}
		return entries;
	}
	
	public static int pickRare() {
		int random = Utils.getRandom(39);
		return Settings.RARES[random];
	}
	
	public static int getRaresEntered() {
		int entries = 0;
		entries = list.size();
		entries = entries / 10000;
		return entries;
	}
	
	public static void getRaffleEntries(Player player) {
		player.getTemporaryAttributtes().clear();
		player.getTemporaryAttributtes().put("raffleamountentry", Boolean.TRUE);
		player.getPackets().sendRunScript(108,
				new Object[] { "Enter the amount of 100M Tickets you would like to enter:" });		
	}
	
	public static int getPrizeCoins() {
		int entries = 0;
		entries = list.size();
		entries = entries % 10000;
		if (entries != 1)
		entries = (int)(entries*(90.0f/100.0f));
		return entries;		
	}
	
}

	
