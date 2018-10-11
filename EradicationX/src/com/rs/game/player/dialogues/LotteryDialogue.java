package com.rs.game.player.dialogues;

import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.Raffle;

public class LotteryDialogue extends Dialogue {

	public LotteryDialogue() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Would you like to enter the raffle? Each ticket costs one 100M Ticket.",
				"Buy a raffle ticket",
				"How does the raffle work?", "How many tickets do I have entered?", "What is the prize pool?", "When will the winner be picked?");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			switch (componentId) {
				case OPTION_1:
					Raffle.getRaffleEntries(player);
					end();
					break;
				case OPTION_2:
					stage = 2;
					sendDialogue("You must have bought a raffle ticket to be entered in.",  
							"You may buy as many tickets as you want.", "Each time the prize pool reaches 10K 100m Tickets, a winner will",
							"receive a rare instead of the cash prize.", "For every time a rare is reached, there will be more than one winner.", 
							"For example, if the raffle reaches 1.5K, A rare will be given out, and the rest of the money will be given to another winner.");
					break;
				case OPTION_3:
					stage = 2;
					sendDialogue("You currently have <col=E32424>" + Raffle.getPersonalEntries(player) + " tickets </col>entered in the weekly raffle.");
					break;
				case OPTION_4:
					stage= 2;
					sendDialogue("The weekly prize pool is now at <col=E32424>" + Raffle.getEntries() + " </col>100M Tickets.", 
							"There is a possibility of <col=E32424>"+ Raffle.getRaresEntered() +" rares</col>, one to each winner,", 
							"and <col=E32424>" + Raffle.getPrizeCoins() + "</col> 100M Tickets to one winner!", 
							"", "Type in ;;rares to see a list of possible rares");
					break; 
				case OPTION_5:
					stage = 2;
					sendDialogue("", "The raffle winner is picked weekly on:" , " Sunday 12 AM, Server Time. Found in the EX tab", "", 
							"If you happen to have won, you will be notified when you log in.", 
							"You claim the prize via the Raffle man.");
					break;
			}
			break;
		case 2:
			start();
			break;
		case 3:
			switch (componentId) {
				case OPTION_1:
					stage = 2;
					if (player.getCurrencyPouch().spend100mTicket(1)) {
						sendDialogue("You have entered the raffle.", "A 100M Ticket has been removed from your inventory.");
						Raffle.addRaffle(player, 1);
		                for (NPC n : World.getNPCs()) {
		                    if (n.getId() == 15971) {
		                        n.setNextForceTalk(new ForceTalk("The weekly prize pool is now at " + Raffle.getEntries() + " 100M Tickets!"));
		                    }
		                }
					} else {
						sendDialogue("You do not have enough money to enter the raffle!", "We only accept 100M Tickets.", "Be sure to turn your coins to tickets", 
								"by typing in ::100MTicket.");
					}
					break;
				case OPTION_2:
					stage = 4;
					sendOptionsDialogue("Are you sure you want to do this? 100 100M Tickets will be removed from your inventory.", "Yes", "No");
					break;
				case OPTION_3:
					end();
					break;
			}
			break;
		case 4:
			switch (componentId) {
				case OPTION_1:
					stage = 2;
					if (player.getCurrencyPouch().spend100mTicket(100)) {
						sendDialogue("You have entered the raffle.", "100 100M Tickets has been removed from your inventory.");
						Raffle.addRaffle(player, 100);
		                for (NPC n : World.getNPCs()) {
		                    if (n.getId() == 15971) {
		                        n.setNextForceTalk(new ForceTalk("The weekly prize pool is now at " + Raffle.getEntries() + " 100M Tickets!"));
		                    }
		                }
					} else {
						sendDialogue("You do not have enough money to enter the raffle!", "We only accept 100M Tickets.", "Be sure to turn your coins to tickets", 
								"by typing in ::100MTicket.");
					}					
					break;
				case OPTION_2:
					end();
					break;
			}
			break;
		}
	


}	@Override
	public void finish() {
	}

}


