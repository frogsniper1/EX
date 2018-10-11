package com.rs.game.player;

import java.io.File;
import java.io.IOException;

import com.rs.content.utils.LendingAuctionManager;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;


public enum RankLend {;
	
	private static int getnum;
	private static int gettickamount;
	private static int gethours;
	private static String getname;
	
    public static void openInterface(Player player) {
        if (!player.getInterfaceManager().containsInterface(1157))
            player.getInterfaceManager().sendInterface(1157);
        player.getPackets().sendIComponentText(1157, 92, "Rank Lending");
        player.getPackets().sendIComponentText(1157, 33, "Rank Name");
        player.getPackets().sendIComponentText(1157, 34, "Time - Player Name - Price");
        if (player.getViewpage() == 1) {
        	sendList(player, 47, 48, 1, 0);
        	sendList(player, 50, 51, 2, 1);
        	sendList(player, 53, 54, 3, 2);
        	sendList(player, 56, 57, 4, 3);
        	sendList(player, 59, 60, 5, 4);
        	sendList(player, 62, 63, 6, 5);
        	sendList(player, 65, 66, 7, 6);
        	sendList(player, 68, 69, 8, 7);
        	sendList(player, 71, 72, 9, 8);
        	sendList(player, 75, 76, 10, 9);
        	player.getPackets().sendIComponentText(1157, 95, "Page 1");
        } else if (player.getViewpage() == 2) {
        	sendList(player, 47, 48, 11, 10);
        	sendList(player, 50, 51, 12, 11);
        	sendList(player, 53, 54, 13, 12);
        	sendList(player, 56, 57, 14, 13);
        	sendList(player, 59, 60, 15, 14);
        	sendList(player, 62, 63, 16, 15);
        	sendList(player, 65, 66, 17, 16);
        	sendList(player, 68, 69, 18, 17);
        	sendList(player, 71, 72, 19, 18);
        	sendList(player, 75, 76, 20, 19);
        	player.getPackets().sendIComponentText(1157, 95, "Page 2");	
        } else if (player.getViewpage() == 3) {
        	sendList(player, 47, 48, 21, 20);
        	sendList(player, 50, 51, 22, 21);
        	sendList(player, 53, 54, 23, 22);
        	sendList(player, 56, 57, 24, 23);
        	sendList(player, 59, 60, 25, 24);
        	sendList(player, 62, 63, 26, 25);
        	sendList(player, 65, 66, 27, 26);
        	sendList(player, 68, 69, 28, 27);
        	sendList(player, 71, 72, 29, 28);
        	sendList(player, 75, 76, 30, 29);
        	player.getPackets().sendIComponentText(1157, 95, "Page 3");	
        } else if (player.getViewpage() == 4) {
        	sendList(player, 47, 48, 31, 30);
        	sendList(player, 50, 51, 32, 31);
        	sendList(player, 53, 54, 33, 32);
        	sendList(player, 56, 57, 34, 33);
        	sendList(player, 59, 60, 35, 34);
        	sendList(player, 62, 63, 36, 35);
        	sendList(player, 65, 66, 37, 36);
        	sendList(player, 68, 69, 38, 37);
        	sendList(player, 71, 72, 39, 38);
        	sendList(player, 75, 76, 40, 39);
        	player.getPackets().sendIComponentText(1157, 95, "Page 4");
        } else if (player.getViewpage() == 5) {
        	sendList(player, 47, 48, 41, 40);
        	sendList(player, 50, 51, 42, 41);
        	sendList(player, 53, 54, 43, 42);
        	sendList(player, 56, 57, 44, 43);
        	sendList(player, 59, 60, 45, 44);
        	sendList(player, 62, 63, 46, 45);
        	sendList(player, 65, 66, 47, 46);
        	sendList(player, 68, 69, 48, 47);
        	sendList(player, 71, 72, 49, 48);
        	sendList(player, 75, 76, 50, 49);
        	player.getPackets().sendIComponentText(1157, 95, "Page 5");
        }    
    }
    
    public static void sendString(Player player, int[] cIds, String rank, String time, String name, String price) {
        player.getPackets().sendIComponentText(1157, cIds[0], rank);
        player.getPackets().sendIComponentText(1157, cIds[1], "" + time + " - " + name + " - " + price + "");
    }
    
    public static void sendList(Player player, int cid1, int cid2, int size, int slot) {
    	 if (LendingAuctionManager.checkTime(slot) == 1) {
    		 openInterface(player);
    		 return;
    	 }
    	 if (LendingAuctionManager.checkTime(slot) == -1)
    		 return;
    	sendString(player, new int[]{cid1, cid2}, locateRankName(slot), LendingAuctionManager.getHours(slot) + " hours", 
    			Utils.formatPlayerNameForDisplay(LendingAuctionManager.getAuctioner(slot)), 
    			LendingAuctionManager.getTicketAmount(slot) + " 100M Tickets");
    }
    
    private static void showOffer(Player player, int num, int page) {
    	if (num < LendingAuctionManager.getSize()) {
    		gethours = LendingAuctionManager.getHours(num);
    		gettickamount = LendingAuctionManager.getTicketAmount(num);
    		getname = LendingAuctionManager.getAuctioner(num);
	        if (!player.getInterfaceManager().containsInterface(1310))
	            player.getInterfaceManager().sendInterface(1310);
	        player.getPackets().sendIComponentText(1310, 5, "You are borrowing:");
	        player.getPackets().sendIComponentText(1310, 6, ""+ RankLend.locateRankName(num) +" from "
	        + Utils.formatPlayerNameForDisplay(LendingAuctionManager.getAuctioner(num)));
	        player.getPackets().sendIComponentText(1310, 7, "For: ");
	        player.getPackets().sendIComponentText(1310, 8, ""+LendingAuctionManager.getHours(num)+
	        		" hours");
	        player.getPackets().sendIComponentText(1310, 9, ""+LendingAuctionManager.getTicketAmount(num)+
	        		" 100M Tickets");
	        player.getPackets().sendIComponentText(1310, 10, "");
	        player.getPackets().sendIComponentText(1310, 50, "Decline");
    	} else {
    		player.sm("The list doesn't go that far!");
    	}
    	getnum = num;
    }
    
    public static void handleButtons(Player player, int buttonId) {
		if (buttonId == 0 && player.getViewpage() == 1) {
			showOffer(player, 0, player.getViewpage());
		} else if (buttonId == 1 && player.getViewpage() == 1) {
			showOffer(player, 1, player.getViewpage());
		} else if (buttonId == 2 && player.getViewpage() == 1) {
			showOffer(player, 2, player.getViewpage());
		} else if (buttonId == 3 && player.getViewpage() == 1) {
			showOffer(player, 3, player.getViewpage());
		} else if (buttonId == 4 && player.getViewpage() == 1) {
			showOffer(player, 4, player.getViewpage());
		} else if (buttonId == 5 && player.getViewpage() == 1) {
			showOffer(player, 5, player.getViewpage());
		} else if (buttonId == 6 && player.getViewpage() == 1) {
			showOffer(player, 6, player.getViewpage());
		} else if (buttonId == 7 && player.getViewpage() == 1) {
			showOffer(player, 7, player.getViewpage());
		} else if (buttonId == 8 && player.getViewpage() == 1) {
			showOffer(player, 8, player.getViewpage());
		} else if (buttonId == 9 && player.getViewpage() == 1) {
			showOffer(player, 9, player.getViewpage());	
		} else if (buttonId == 0 && player.getViewpage() == 2) {
			showOffer(player, 10, player.getViewpage());
		} else if (buttonId == 1 && player.getViewpage() == 2) {
			showOffer(player, 11, player.getViewpage());
		} else if (buttonId == 2 && player.getViewpage() == 2) {
			showOffer(player, 12, player.getViewpage());
		} else if (buttonId == 3 && player.getViewpage() == 2) {
			showOffer(player, 13, player.getViewpage());
		} else if (buttonId == 4 && player.getViewpage() == 2) {
			showOffer(player, 14, player.getViewpage());
		} else if (buttonId == 5 && player.getViewpage() == 2) {
			showOffer(player, 15, player.getViewpage());
		} else if (buttonId == 6 && player.getViewpage() == 2) {
			showOffer(player, 16, player.getViewpage());
		} else if (buttonId == 7 && player.getViewpage() == 2) {
			showOffer(player, 17, player.getViewpage());
		} else if (buttonId == 8 && player.getViewpage() == 2) {
			showOffer(player, 18, player.getViewpage());
		} else if (buttonId == 9 && player.getViewpage() == 2) {
			showOffer(player, 19, player.getViewpage());	
		} else if (buttonId == 0 && player.getViewpage() == 3) {
			showOffer(player, 20, player.getViewpage());
		} else if (buttonId == 1 && player.getViewpage() == 3) {
			showOffer(player, 21, player.getViewpage());
		} else if (buttonId == 2 && player.getViewpage() == 3) {
			showOffer(player, 22, player.getViewpage());
		} else if (buttonId == 3 && player.getViewpage() == 3) {
			showOffer(player, 23, player.getViewpage());
		} else if (buttonId == 4 && player.getViewpage() == 3) {
			showOffer(player, 24, player.getViewpage());
		} else if (buttonId == 5 && player.getViewpage() == 3) {
			showOffer(player, 25, player.getViewpage());
		} else if (buttonId == 6 && player.getViewpage() == 3) {
			showOffer(player, 26, player.getViewpage());
		} else if (buttonId == 7 && player.getViewpage() == 3) {
			showOffer(player, 27, player.getViewpage());
		} else if (buttonId == 8 && player.getViewpage() == 3) {
			showOffer(player, 28, player.getViewpage());
		} else if (buttonId == 9 && player.getViewpage() == 3) {
			showOffer(player, 29, player.getViewpage());	
		} else if (buttonId == 0 && player.getViewpage() == 4) {
			showOffer(player, 30, player.getViewpage());
		} else if (buttonId == 1 && player.getViewpage() == 4) {
			showOffer(player, 31, player.getViewpage());
		} else if (buttonId == 2 && player.getViewpage() == 4) {
			showOffer(player, 32, player.getViewpage());
		} else if (buttonId == 3 && player.getViewpage() == 4) {
			showOffer(player, 33, player.getViewpage());
		} else if (buttonId == 4 && player.getViewpage() == 4) {
			showOffer(player, 34, player.getViewpage());
		} else if (buttonId == 5 && player.getViewpage() == 4) {
			showOffer(player, 35, player.getViewpage());
		} else if (buttonId == 6 && player.getViewpage() == 4) {
			showOffer(player, 36, player.getViewpage());
		} else if (buttonId == 7 && player.getViewpage() == 4) {
			showOffer(player, 37, player.getViewpage());
		} else if (buttonId == 8 && player.getViewpage() == 4) {
			showOffer(player, 38, player.getViewpage());
		} else if (buttonId == 9 && player.getViewpage() == 4) {
			showOffer(player, 39, player.getViewpage());	
		} else if (buttonId == 0 && player.getViewpage() == 5) {
			showOffer(player, 40, player.getViewpage());
		} else if (buttonId == 1 && player.getViewpage() == 5) {
			showOffer(player, 41, player.getViewpage());
		} else if (buttonId == 2 && player.getViewpage() == 5) {
			showOffer(player, 42, player.getViewpage());
		} else if (buttonId == 3 && player.getViewpage() == 5) {
			showOffer(player, 43, player.getViewpage());
		} else if (buttonId == 4 && player.getViewpage() == 5) {
			showOffer(player, 44, player.getViewpage());
		} else if (buttonId == 5 && player.getViewpage() == 5) {
			showOffer(player, 45, player.getViewpage());
		} else if (buttonId == 6 && player.getViewpage() == 5) {
			showOffer(player, 46, player.getViewpage());
		} else if (buttonId == 7 && player.getViewpage() == 5) {
			showOffer(player, 47, player.getViewpage());
		} else if (buttonId == 8 && player.getViewpage() == 5) {
			showOffer(player, 48, player.getViewpage());
		} else if (buttonId == 9 && player.getViewpage() == 5) {
			showOffer(player, 49, player.getViewpage());	
		}
    }    
    
    public static String locateRankName(int num) {
    	switch (LendingAuctionManager.getRankType(num)) {
    	case 1:
    		return "<img=10><col=2B9626>Regular";
    	case 2:
    		return "<img=8><col=DB1D1D>Extreme";
    	case 3:
    		return "<img=9><col=32D9D9>Super Donator";
    	case 4:
    		return "<img=18><col=A33BD4>Eradicator";
    	default:
    		return null;
    	}
    }   
    
    private static void setLentRank(Player player, int num) {
    	switch (LendingAuctionManager.getRankType(num)) {
    	case 1:
    		player.setLentDonator(true);
    		break;
    	case 2:
    		player.setLentExtreme(true);
    		break;
    	case 3:
    		player.setLentSavior(true);
    		break;
    	case 4:
    		player.setLentEradicator(true);
    		break;
    	}
    }
    
    private static void grantLoan(Player player, int num) {
    	long hours = LendingAuctionManager.getHours(num);
		if (player.getCurrencyPouch().spend100mTicket(LendingAuctionManager.getTicketAmount(num))) {
			setLentRank(player, num);
			boolean lenderLog = true;
			Player auctioner = World.getPlayer(LendingAuctionManager.getAuctioner(num));
			if (auctioner == null) {
				lenderLog = false;
				auctioner = SerializableFilesManager.loadPlayer(LendingAuctionManager.getAuctioner(num));
			} else {
				auctioner.auctionMessage = 1;
				auctioner.sm("Your rank was lent out by " +player.getDisplayName()+"! Go to the Rank Lender to claim your money.");
				auctioner.setCollectLoanMoney(auctioner.getCollectLoanMoney() + LendingAuctionManager.getTicketAmount(num));
			}	
			player.sm("You are now borrowing a rank. Talk to the Rank Lender to view your remaining time on the rank.");
			try {
				if (!lenderLog) {
					auctioner.setCollectLoanMoney(auctioner.getCollectLoanMoney() + LendingAuctionManager.getTicketAmount(num));
					auctioner.auctionMessage = 1;
					SerializableFilesManager.storeSerializableClass(auctioner,
							new File("data/playersaves/characters/" + LendingAuctionManager.getAuctioner(num)
									+ ".p"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
			LendingAuctionManager.removeslot(LendingAuctionManager.getAuctioner(num),
					player.getUsername(), num, LendingAuctionManager.getRankType(num), hours);
			player.closeInterfaces();	
		} else {
			player.sm("You don't have enough money to loan this rank.");
			player.closeInterfaces();
		}
	}   
	
	private static boolean checkifOverride(Player player, int num) {
    	switch (LendingAuctionManager.getRankType(num)) {
    	case 1:
    		if (player.isLentDonator())
				return true;
    	case 2:
    		if (player.isLentExtreme())
				return true;
    	case 3:
      		if (player.isLentSavior())
				return true;
    	case 4:
     		if (player.isLentEradicator())
				return true;
    	}
		return false;
	}
	
    public static void handleOfferButtons(Player player, int buttonId) {
		if (buttonId == 0) {
			if (checkifOverride(player, getnum) == false) {
			if (LendingAuctionManager.getAuctioner(getnum).equals(getname) 
					&& LendingAuctionManager.getTicketAmount(getnum) == gettickamount &&
					LendingAuctionManager.getHours(getnum) == gethours) {
			if (LendingAuctionManager.getAuctioner(getnum).equals(player.getUsername())) {
				player.sm("You can't borrow your own rank.");
			} else {
			grantLoan(player, getnum);
			}
			} else {
				player.sm("Too slow! Someone else already borrowed this rank.");
				player.closeInterfaces();
			}
			} else {
				player.sm("You must wait until your current rank is finished before lending the same type of rank.");
			}
		} else if (buttonId == 1) {
			player.closeInterfaces();
		}
    }     
    
}
