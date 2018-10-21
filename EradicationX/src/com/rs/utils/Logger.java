package com.rs.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.rs.game.tasks.WorldTasksManager;
import com.rs.net.ServerChannelHandler;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

public final class Logger {

	public static void handle(Throwable throwable) {
		System.out.println("ERROR! THREAD NAME: "
				+ Thread.currentThread().getName());
		throwable.printStackTrace();
	}

	public static void debug(long processTime) {
		log(Logger.class, "---DEBUG--- start");
		log(Logger.class, "WorldProcessTime: " + processTime);
		log(Logger.class,
				"WorldRunningTasks: " + WorldTasksManager.getTasksCount());
		log(Logger.class,
				"ConnectedChannels: "
						+ ServerChannelHandler.getConnectedChannelsSize());
		log(Logger.class, "---DEBUG--- end");
	}

	public static void log(Object classInstance, Object message) {
		log(classInstance.getClass().getSimpleName(), message);
	}

	public static void printEmptyLog(Player player, Item[] items) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/Empties.txt", true));
			
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + " "
                    + Calendar.getInstance().getTimeZone().getDisplayName()
                    + "] Display: "+ player.getDisplayName() +", Login: " + player.getUsername() + ", emptied the following stuff:");
			bf.newLine();
			
			for (int i = 0; i < items.length; i++) {
				if (items[i] == null)
					continue;
				Item item = new Item(items[i].getId(), items[i].getAmount());
				bf.append(Utils.formatNumber(items[i].getAmount())+" "+item.getName()+"");
				bf.newLine();
			}
			
			bf.append("=======================");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
			//nothing.
		}
	}	
	
	public static void printTradeLog(Player player, Player target, Item[] items) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/Trades.txt", true));
			
			String username = Utils.formatPlayerNameForDisplay(player.getUsername());
			String tradeWith = Utils.formatPlayerNameForDisplay(target.getUsername());
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + " "
                    + Calendar.getInstance().getTimeZone().getDisplayName()
                    + "] Display: "+ player.getDisplayName() +", Login: " + player.getUsername() + ", trading with Display: "+ target.getDisplayName() +", Login: " + target.getUsername() + ",");
			bf.newLine();
			bf.append("(To: "+username+", From: "+tradeWith+")");
			bf.newLine();
			
			for (int i = 0; i < items.length; i++) {
				if (items[i] == null)
					continue;
				Item item = new Item(items[i].getId(), items[i].getAmount());
				bf.append(Utils.formatNumber(items[i].getAmount())+" "+item.getName()+"");
				bf.newLine();
			}
			
			bf.append("=======================");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
			//nothing.
		}
	}
	
	public static void printTradeLog(Player player, Player target, Item items) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/Trades.txt", true));
			bf.append("Display: "+ player.getDisplayName() +", Login: " + player.getUsername() + ","+ " got x"+
						items.getAmount()+" "+ items.getName() + " from Display: "+ target.getDisplayName() +", Login: " + target.getDisplayName() + ", [Rest of the trade below]");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
			//nothing.
		}
	}
	
	
	public static void printShopLog(Player player, Item item, int amount, Item item2) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/ShopLogs.txt", true));
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] Display: "+ player.getDisplayName() +", Login: " + player.getUsername() +" sold "+item.getAmount() + " " +item.getName()+ " for " + Utils.formatNumber(amount) + " " + item2.getName());		
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}		
	
	public static void printAdminCommands(Player player, String str, String str2, String str3) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/AdminLogs.txt", true));
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "]: "+ player.getUsername() + " " + str+ " "+str2+ " "+str3+ " ");
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}	
	
	public static void printAlchLog(Player player, Item item, int amount, Item item2) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/AlchLogs.txt", true));
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] Display: "+ player.getDisplayName() +", Login: " + player.getUsername() +" alched " +item.getName()+ " for " + Utils.formatNumber(amount) + " " + item2.getName());		
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}
	
	public static void printDiceLog(Player player, int amount) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/DiceRolls.txt", true));
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] Display: "+ player.getDisplayName() +", Login: " + player.getUsername() +" rolled "+ amount + " on the percentile die.");		
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}		
	
	public static void printDropDump(String name, double rate, String npc) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/NPCDrops.txt", true));
			
			bf.append(npc+":   Item: " + name + "    Drop Rate: " + rate + "%");		
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}		
	
	public static void printDonateLog(String name, String message, int price) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/Donate.txt", true));
			
			bf.append(name+":   bought " + message+" for "+price+"$");		
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}		
	
	public static void printDropLog(Player player, Player target, Player loser, Item item) {
		try {
			if (player.equals(target) && loser == null)
				return;
			BufferedWriter bf = new BufferedWriter(new FileWriter("data/logs/PlayerLoots.txt", true));
			String looter = Utils.formatPlayerNameForDisplay(player.getUsername());
			String lootee = Utils.formatPlayerNameForDisplay(target.getUsername());
			if (loser != null)
			lootee = Utils.formatPlayerNameForDisplay(loser.getUsername());
			bf.append("[" + DateFormat.getDateTimeInstance().format(new Date())
                    + "] "+looter+" looted "+lootee+"'s "+ item.getName() + " (x"+item.getAmount()+")");			
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException ignored) {
		}
	}	
	
	public static void saveDisplayName(Player player) {
		try {
			if (player.getDisplayName() != null) {
				BufferedWriter bf = new BufferedWriter(new FileWriter("data/DisplayNames/UsedNames.txt", true));
				bf.append(player.getDisplayName() + ":" + player.getUsername());			
				bf.newLine();
				bf.flush();
				bf.close();
			}
		} catch (IOException ignored) {
		}
	}		
	
	public static void log(String className, Object message) {
		String text = "[" + className + "]" + " " + message.toString();
		System.out.println(text);
	}

	private Logger() {

	}

}
