package com.rs.game.player.dialogues;

import com.rs.content.utils.LendingAuctionManager;
import com.rs.utils.LendAuction;

/* created by Fatal Resort - EradicationX */
public class LendaRankb extends Dialogue {

	public LendaRankb() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Are you sure you want to loan your rank?", "Yes.", "No.");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {	
			if (componentId == OPTION_1) {
				if (player.getRanklend() == 1) {
					if (player.isDonator() || player.getInventory().containsItem(6832, 1)) {
						if (player.isDonator())
							player.setDonator(false);
						else if (player.getInventory().containsItem(6832, 1))
							player.getInventory().deleteItem(6832,1);
						LendAuction lendauction = new LendAuction(player.getUsername(), player.getRanklend(), player.getLendhours() , player.getTicketamount());
						LendingAuctionManager.addauction(lendauction);
						player.sm("Your rank has been placed on the list.");
						end();
					} else {
						end();
						player.sm("You dropped the rank! Sneaky bastard.");
					}
				} else if (player.getRanklend() == 2) {
					if (player.isExtremeDonator() || player.getInventory().containsItem(6830, 1)) {
						if (player.isExtremeDonator())
							player.setExtremeDonator(false);
						else if (player.getInventory().containsItem(6830, 1))
							player.getInventory().deleteItem(6830,1);
						LendAuction lendauction = new LendAuction(player.getUsername(), player.getRanklend(), player.getLendhours() , player.getTicketamount());
						LendingAuctionManager.addauction(lendauction);
						player.sm("Your rank has been placed on the list.");
						end();						
					} else {
						end();
						player.sm("You dropped the rank! Sneaky bastard.");
					}
				} else if (player.getRanklend() == 3) {
					if (player.isSavior() || player.getInventory().containsItem(6829, 1)) {
						if (player.isSavior())
							player.setSavior(false);
						else if (player.getInventory().containsItem(6829, 1))
							player.getInventory().deleteItem(6829,1);
						LendAuction lendauction = new LendAuction(player.getUsername(), player.getRanklend(), player.getLendhours() , player.getTicketamount());
						LendingAuctionManager.addauction(lendauction);
						player.sm("Your rank has been placed on the list.");
						end();						
					} else {
						end();
						player.sm("You dropped the rank! Sneaky bastard.");
					}
				} else if (player.getRanklend() == 4) {
					if (player.isEradicator() || player.getInventory().containsItem(6828, 1)) {
						if (player.isEradicator())
							player.setEradicator(false);
						else if (player.getInventory().containsItem(6828, 1))
							player.getInventory().deleteItem(6828,1);
						LendAuction lendauction = new LendAuction(player.getUsername(), player.getRanklend(), player.getLendhours() , player.getTicketamount());
						LendingAuctionManager.addauction(lendauction);
						player.sm("Your rank has been placed on the list.");
						end();						
					} else {
						end();
						player.sm("You dropped the rank! Sneaky bastard.");
					}					
				}
			} else if (componentId == OPTION_2) {
				end();
				player.setRanklend(0);
				player.setTicketamount(0);
				player.setLendhours(0);
			} 
		}
	}
	
	@Override
	public void finish() {
	}

}
