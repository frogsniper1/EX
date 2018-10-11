package com.rs.content.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.LendingManager;
import com.rs.game.player.Player;
import com.rs.utils.BLend;
import com.rs.utils.LendAuction;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;


public class LendingAuctionManager implements Serializable {
	
	private static final long serialVersionUID = 2147952414213571L;
	public static CopyOnWriteArrayList<LendAuction> auction;
	
	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File("data/AuctionList.ser");
		if (file.exists()) {
			try {
				auction = (CopyOnWriteArrayList<LendAuction>) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Lists Loaded!");
		auction = new CopyOnWriteArrayList<LendAuction>();
	}
	
	public static void addauction(LendAuction getauction) {
		auction.add(getauction);
		save();
	}
	
	public static void save() {
		try {
			SerializableFilesManager.
			storeSerializableClass(auction, new File("data/AuctionList.ser"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void remove(LendAuction getauction) {
		auction.remove(getauction);
		save();
	}
	
	public static void removeslot(String lender, String lendee, int num, int ranktype, long hours) {
		BLend blend = new BLend(lender, lendee, ranktype, Utils.currentTimeMillis() + (hours * 60 * 60 * 1000));
		LendingManager.lend(blend);
		auction.remove(num);
		save();
	}
	
	public static String getAuctioner(int num) {
		LendAuction getnum = auction.get(num);
		if (getnum != null)
			return getnum.getAuctioner();
		return null;
	}
	
	public static int getTicketAmount(int num) {
		LendAuction getnum = auction.get(num);
		if (getnum != null)
			return getnum.getTicketAmount();
		return 0;
	}
	
	public static int getSize() {
		return auction.size();
	}	
	
	public static int checkTime(int slot) {
		if (auction.size() <= slot)
			return -1;
		LendAuction auct = auction.get(slot);
		if (auct == null)
			return -1;
		int item = 0;
		if ((auct.getTicketAmount() > Utils.currentTimeMillis()) || auct.getTime() == 0) {
			switch (auct.getRankType()) {
			case 1:
				item = 6832;
				break;
			case 2:
				item = 6830;
				break;
			case 3:
				item = 6829;
				break;
			case 4:
				item = 6828;
				break;
			}
			String username = auct.getAuctioner();
			Player player = World.getPlayer(username);
			if (player != null) {
				player.sm("Your 24 hours of listing came to an end. Your rank was sent to your bank.");
				player.getBank().addItem(item, 1, true);
			} else {
				player = SerializableFilesManager.loadPlayer(username);
				if (player != null) {
				try {
					player.getBank().addItem(item, 1, false);
					player.auctionTimeup = "Your listing of "
							+ new Item(item).getName() + " wasn't auctioned off in 24 hours and has been sent to your bank.";
					SerializableFilesManager.storeSerializableClass(player,
							new File("data/playersaves/characters/" + username
									+ ".p"));		
				} catch (IOException e) {
					e.printStackTrace();
				}}
			}
			remove(auct);
			save();
			return 1;
		}
		return 0;
	}
	
	public static int getHours(int num) {
		LendAuction getnum = auction.get(num);
		if (getnum != null)
			return getnum.getHours();
		return 0;
	}
	
	public static LendAuction getLend(Player player) {
		for (LendAuction lend : auction)
			if (lend.getAuctioner().equals(player.getUsername()))
				return lend;
		return null;
	}	
	
	
	public static int getRankType(int num) {
		LendAuction getnum = auction.get(num);
		if (getnum != null)
			return getnum.getRankType();
		return 0;
	}	
	
	
}