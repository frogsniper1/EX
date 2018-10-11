package com.rs.game.player.dialogues;

import com.rs.content.utils.LendingAuctionManager;
import com.rs.game.player.LendingManager;
import com.rs.utils.BLend;
import com.rs.utils.LendAuction;

/* created by Fatal Resort - EradicationX */
public class LendaRank extends Dialogue {

	public LendaRank() {
	}

	@Override
	public void start() {
		BLend lend = LendingManager.hasLendedOutOne(player);
		BLend lend2 = LendingManager.hasLendedOutTwo(player);
		BLend lend3 = LendingManager.hasLendedOutThree(player);
		BLend lend4 = LendingManager.hasLendedOutFour(player);
		String string1 = "";
		String string2 = "";
		String string3 = "";
		String string4 = "";
		if (lend != null) {
			int count = 0;
			boolean donor = false;
			boolean extreme = false;
			boolean superdonor = false;
			boolean eradicator = false;
			if (lend != null) {
			switch (lend.getItem()) {
			case 1:
				donor = true;
				count++;
				string1 = donor == true ? "<img=19>: " +LendingManager.getHoursLeft(lend.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 2:
				extreme = true;
				count++;
				string1 = extreme == true ? "<img=15>: " +LendingManager.getHoursLeft(lend.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 3:
				superdonor = true;
				count++;
				string1 = superdonor == true ? "<img=12>: " +LendingManager.getHoursLeft(lend.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 4:
				eradicator = true;
				count++;
				string1 = eradicator == true ? "<img=14>: " +LendingManager.getHoursLeft(lend.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend.getTime()) +
						" minutes left on your current rank." : "";
				break;
			}
			}
			if (lend2 != null) {
			switch (lend2.getItem()) {
			case 1:
				donor = true;
				count++;
				string2 = donor == true ? "<img=19>: " +LendingManager.getHoursLeft(lend2.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend2.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 2:
				extreme = true;
				count++;
				string2 = extreme == true ? "<img=15>: " +LendingManager.getHoursLeft(lend2.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend2.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 3:
				superdonor = true;
				count++;
				string2 = superdonor == true ? "<img=12>: " +LendingManager.getHoursLeft(lend2.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend2.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 4:
				eradicator = true;
				count++;
				string2 = eradicator == true ? "<img=14>: " +LendingManager.getHoursLeft(lend2.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend2.getTime()) +
						" minutes left on your current rank." : "";
				break;
			}
			}
			if (lend3 != null) {
			switch (lend3.getItem()) {
			case 1:
				donor = true;
				count++;
				string3 = donor == true ? "<img=19>: " +LendingManager.getHoursLeft(lend3.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend3.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 2:
				extreme = true;
				count++;
				string3 = extreme == true ? "<img=15>: " +LendingManager.getHoursLeft(lend3.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend3.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 3:
				superdonor = true;
				count++;
				string3 = superdonor == true ? "<img=12>: " +LendingManager.getHoursLeft(lend3.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend3.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 4:
				eradicator = true;
				count++;
				string3 = eradicator == true ? "<img=14>: " +LendingManager.getHoursLeft(lend3.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend3.getTime()) +
						" minutes left on your current rank." : "";
				break;
			}
			}
			if (lend4 != null) {
			switch (lend4.getItem()) {
			case 1:
				donor = true;
				count++;
				string4 = donor == true ? "<img=19>: " +LendingManager.getHoursLeft(lend4.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend4.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 2:
				extreme = true;
				count++;
				string4 = extreme == true ? "<img=15>: " +LendingManager.getHoursLeft(lend4.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend4.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 3:
				superdonor = true;
				count++;
				string4 = superdonor == true ? "<img=12>: " +LendingManager.getHoursLeft(lend4.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend4.getTime()) +
						" minutes left on your current rank." : "";
				break;
			case 4:
				eradicator = true;
				count++;
				string4 = eradicator == true ? "<img=14>: " +LendingManager.getHoursLeft(lend4.getTime())+ 
						" hours, " + LendingManager.getMinutesLeft(lend4.getTime()) +
						" minutes left on your current rank." : "";
				break;
			}
			}
			stage = 2;
			sendDialogue("You're currently lending " + count + " or more ranks.",
					string1, string2, string3, string4);
		} else {
		stage = 1;
		sendOptionsDialogue("Which rank are you putting for loan?", "<col=1EA642><img=10> Regular Rank", "<col=BA0F0F><img=8> Extreme Rank", 
							"<col=40D4D6><img=9> Super Donator", "<col=0A7DAD><img=18> Eradicator Rank", "Cancel listing");
	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 2) {
			stage = 1;
			sendOptionsDialogue("Which rank are you putting for loan?", "<col=1EA642><img=10> Regular Rank", "<col=BA0F0F><img=8> Extreme Rank", 
								"<col=40D4D6><img=9> Super Donator", "<col=0A7DAD><img=18> Eradicator Rank", "Cancel listing");			
		}
		if (stage == 1) {	
			if (componentId == OPTION_1) {
				if (player.isDonator() || player.getInventory().containsItem(6832, 1)) {
					player.setRanklend(1);
				end();
				player.getTemporaryAttributtes().put("hours", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter the amount of hours. 24 Hours Maximum." });
				} else {
					end();
					player.sm("You don't have this rank.");
				}
			} else if (componentId == OPTION_2) {
				if (player.isExtremeDonator() || player.getInventory().containsItem(6830, 1)) {
					player.setRanklend(2);
				end();
				player.getTemporaryAttributtes().put("hours", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter the amount of hours. 24 Hours Maximum." });
				} else {
					end();
					player.sm("You don't have this rank.");
				}
			} else if (componentId == OPTION_3) {
				if (player.isSavior() || player.getInventory().containsItem(6829, 1)) {
					player.setRanklend(3);
				end();
				player.getTemporaryAttributtes().put("hours", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter the amount of hours. 24 Hours Maximum." });
				} else {
					end();
					player.sm("You don't have this rank.");
				}
			} else if (componentId == OPTION_4) {
				if (player.isEradicator() || player.getInventory().containsItem(6828, 1)) {
					player.setRanklend(4);
				end();
				player.getTemporaryAttributtes().put("hours", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter the amount of hours. 24 Hours Maximum." });
				} else {
					end();
					player.sm("You don't have this rank.");
				}
			} else if (componentId == OPTION_5) {
				sendOptionsDialogue("Are you sure you want to remove?", "Yes", "No");
				stage = 5;
			}
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				LendAuction lend = LendingAuctionManager.getLend(player);
				if (lend == null) {
					end();
					player.sm("No listing was found.");
				} else {
				int num = lend.getRankType();
				if (num == 1) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(6832, 1);
					else
						player.getBank().addItem(6832,1,true);
				} else if (num == 2) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(6830, 1);
					else
						player.getBank().addItem(6830,1,true);
				} else if (num == 3) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(6829, 1);
					else
						player.getBank().addItem(6829,1,true);
				} else if (num == 4) {
					if (player.getInventory().hasFreeSlots())
						player.getInventory().addItem(6828, 1);
					else
						player.getBank().addItem(6828,1,true);
				}
				LendingAuctionManager.remove(lend);
				end();
				player.sm("Your listing was removed. If you had no free space, the rank went to your bank.");
				}
			} else if (componentId == OPTION_2) {
				end();
			}
		}
	}
	
	@Override
	public void finish() {
	}

}
