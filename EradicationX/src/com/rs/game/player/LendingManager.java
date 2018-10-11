package com.rs.game.player;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.BLend;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class LendingManager implements Serializable {

	private static final long serialVersionUID = 2496224027921198991L;
	public static CopyOnWriteArrayList<BLend> list;

	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File("data/RankBLends.ser");
		if (file.exists()) {
			try {
				list = (CopyOnWriteArrayList<BLend>) SerializableFilesManager
						.loadSerializedFile(file);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Lent ranks loaded!");
		list = new CopyOnWriteArrayList<BLend>();
	}

	public static void lend(BLend lend) {
		list.add(lend);
		save();
	}

	public static boolean hasLendedRank(Player player) {
		for (BLend lend : list) {
			if (lend.getLendee().equals(player.getUsername()))
				return true;
		}
		return false;
	}

	public static boolean hasLendedOut(Player player) {
		for (BLend lend : list) {
			if (lend.getLender().equals(player.getUsername()))
				return true;
		}
		return false;
	}	
	
	public static BLend hasLendedOutOne(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLender().equals(player.getUsername())) {
				count++;
				if (count == 1)
				return lend;
			}
		}
		return null;
	}	
	
	public static BLend hasLendedOutTwo(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLender().equals(player.getUsername())) {
				count++;
				if (count == 2)
				return lend;
			}
		}
		return null;
	}
	
	public static BLend hasLendedOutThree(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLender().equals(player.getUsername())) {
				count++;
				if (count == 3)
				return lend;
			}
		}
		return null;
	}
	
	public static BLend hasLendedOutFour(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLender().equals(player.getUsername())) {
				count++;
				if (count == 4)
				return lend;
			}
		}
		return null;
	}		
	
	public static BLend getLend(Player player) {
		for (BLend lend : list)
			if (lend.getLendee().equals(player.getUsername()))
				return lend;
		return null;
	}
	
	public static BLend getLendTwo(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLendee().equals(player.getUsername())) {
				count++;
				if (count == 2)
				return lend;
			}
		}
		return null;
	}
	
	public static BLend getLendThree(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLendee().equals(player.getUsername())) {
				count++;
				if (count == 3)
				return lend;
			}
		}
		return null;
	}
	
	public static BLend getLendFour(Player player) {
		int count = 0;
		for (BLend lend : list) {
			if (lend.getLendee().equals(player.getUsername())) {
				count++;
				if (count == 4)
				return lend;
			}
		}
		return null;
	}	

	public static BLend getHasLendedItemsOut(Player player) {
		for (BLend lend : list)
			if (lend.getLender().equals(player.getUsername()))
				return lend;
		return null;
	}

	public static boolean hasLendedOn(Player player) {
		if (player.isLentDonator() || player.isLentExtreme() || player.isLentSavior() || player.isLentEradicator()) {
			return true;
		}
		return false;
	}

	public static void remove(BLend lend) {
		list.remove(lend);
		save();
	}

	public static void save() {
		try {
			SerializableFilesManager.storeSerializableClass(list, new File(
					"data/RankBLends.ser"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getHoursLeft(long millis) {
		long mil = millis - Utils.currentTimeMillis();
		long sec = (mil / 1000);
		long min = (sec / 60);
		int hour = 0;
		while (min > 60) {
			hour++;
			min -= 60;
		}
		return (int) hour;
	}

	public static int getMinutesLeft(long millis) {
		long mil = millis - Utils.currentTimeMillis();
		long sec = (mil / 1000);
		long min = (sec / 60);
		while (min > 60) {
			min -= 60;
		}
		return (int) min;
	}
	
	public static void removeTimer(Player player) {
		for (BLend lend : list) {
			if (lend.getLendee().equals(player.getUsername())) {
				lend.setTime(Utils.currentTimeMillis());
				player.sm("Your rank has been discarded.");
				return;
			}
		}
	}

	public static boolean isLendedItem(Player player, int Rank) {
		for (BLend lend : list)
			if (lend.getItem() == Rank && lend.getLendee().equals(player.getUsername()))
				return true;
		return false;
	}

	
	public void getRemoveRank(BLend lend, Player lendee) {
		switch (lend.getItem()) {
		case 1:
			lendee.setLentDonator(false);
			break;
		case 2:
			lendee.setLentExtreme(false);
			break;
		case 3:
			lendee.setLentSavior(false);
			break;
		case 4: 
			lendee.setLentEradicator(false);
			break;
		default:
			break;
		}
	}

	public void process() {
		List<BLend> toRemove = new ArrayList<BLend>();
		for (BLend lend : list) {
			if (lend.getTime() <= Utils.currentTimeMillis()) {
				toRemove.add(lend);
				boolean lenderLog = true;
				boolean lendeeLog = true;
				Player lender = World.getPlayer(lend.getLender());
				Player lendee = World.getPlayer(lend.getLendee());
				if (lender == null) {
					lenderLog = false;
					lender = SerializableFilesManager.loadPlayer(lend
							.getLender());
				}
				if (lendee == null) {
					lendeeLog = false;
					lendee = SerializableFilesManager.loadPlayer(lend
							.getLendee());
				}
				getRemoveRank(lend, lendee);
				lender.senttoBank = true;
				if (lenderLog) {
					if (lend.getItem() == 1) {
						lender.getBank().addItem(6832, 1, true);
					} else if (lend.getItem() == 2) {
						lender.getBank().addItem(6830, 1, true);
					} else if (lend.getItem() == 3) {
						lender.getBank().addItem(6829, 1, true);
					} else if (lend.getItem() == 4) {
						lender.getBank().addItem(6828, 1, true);
					}
					lender.sm("<col=FF0000>A rank you lent out is now available to be collected.");
				}
				if (lendeeLog)
					lendee.sm("<col=FF0000>A rank you borrowed has been returned to the owner.");
				try {
					if (!lenderLog) {
						if (lend.getItem() == 1) {
							lender.getBank().addItem(6832, 1, false);
						} else if (lend.getItem() == 2) {
							lender.getBank().addItem(6830, 1, false);
						} else if (lend.getItem() == 3) {
							lender.getBank().addItem(6829, 1, false);
						} else if (lend.getItem() == 4) {
							lender.getBank().addItem(6828, 1, false);
						}
						lender.lendMessage = 1;
						SerializableFilesManager.storeSerializableClass(lender,
								new File("data/playersaves/characters/" + lend.getLender()
										+ ".p"));
					}
					if (!lendeeLog) {
						lendee.lendMessage = 2;
						SerializableFilesManager.storeSerializableClass(lendee,
								new File("data/playersaves/characters/" + lend.getLendee()
										+ ".p"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		list.removeAll(toRemove);
		if (toRemove.size() > 0)
			save();
		toRemove.clear();
	}
}

	
