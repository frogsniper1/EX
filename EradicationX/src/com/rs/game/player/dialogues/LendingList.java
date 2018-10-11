package com.rs.game.player.dialogues;



import com.rs.game.player.LendingManager;
import com.rs.game.player.RankLend;
import com.rs.utils.BLend;

/* created by Fatal Resort - EradicationX */
public class LendingList extends Dialogue {

	public LendingList() {
	}
	
	@Override
	public void start() {
		BLend lend = LendingManager.getLend(player);
		BLend lend2 = LendingManager.getLendTwo(player);
		BLend lend3 = LendingManager.getLendThree(player);
		BLend lend4 = LendingManager.getLendFour(player);
		String string1 = "";
		String string2 = "";
		String string3 = "";
		String string4 = "";
		if (player.checkLent()) {
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
			stage = 3;
			if (count != 0) {
			sendDialogue("You're currently carrying " + count + " lent ranks.",
					string1, string2, string3, string4);
			} else {
				player.setLentEradicator(false);
				player.setLentDonator(false);
				player.setLentSavior(false);
				player.setLentExtreme(false);
				sendDialogue("Your lent rank was removed.");
			}
		} else {
		stage = 1;
		sendOptionsDialogue("Which page would you like to view?", "View Page 1", "View Page 2", "View Page 3", "View Page 4", "View Page 5");
		}
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
			case 1:
				if (componentId == OPTION_1) {
					player.setViewpage(1);
					RankLend.openInterface(player);
					end();
				} else if (componentId == OPTION_2) {
					player.setViewpage(2);
					RankLend.openInterface(player);
					end();
				} else if (componentId == OPTION_3) {
					player.setViewpage(3);
					RankLend.openInterface(player);
					end();
				} else if (componentId == OPTION_4) {
					player.setViewpage(4);
					RankLend.openInterface(player);
					end();
				} else if (componentId == OPTION_5) {
					player.setViewpage(5);
					RankLend.openInterface(player);
					end();
				}				
				break;
			case 2:
				stage = 1;
				sendOptionsDialogue("Which page would you like to view?", "View Page 1", "View Page 2", "View Page 3", "View Page 4", "View Page 5");
				break;
			case 3:
				stage = 4;
				sendOptionsDialogue("What would you like to do?", "View auctions", "Discard my rank before time", "Nevermind");
				break;
			case 4:
				if (componentId == OPTION_1) {
					sendOptionsDialogue("Which page would you like to view?", "View Page 1", "View Page 2", "View Page 3", "View Page 4", "View Page 5");
					stage = 1;	
				} else if (componentId == OPTION_2) {
					sendOptionsDialogue("Are you sure you want to destroy your rank?", "Yes, I no longer need it.", "Nevermind, I'll wait.");
					stage = 5;
				} else if (componentId == OPTION_3) {
					end();
				}
				break;
			case 5:	
				if (componentId == OPTION_1) {
					LendingManager.removeTimer(player);
					end();
				} else
					end();
				break;
		}
	}
	
	@Override
	public void finish() {
	}

}
