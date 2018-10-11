package com.rs.game.player.dialogues;

import com.rs.game.World;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class TriviaShop extends Dialogue {

	public TriviaShop() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("What would you like to buy?",
				"50 x Eradicated Seals [1 Point]",
				"30 x Invasion Tokens [1 Point]",
				"1 x Vote Tickets [2 Point]", 
				"1 x Huge XP lamp [15 Points]", "Next Page");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		int pt = player.getTriviaPoints();
		switch (stage) {
			case 1:
				switch (componentId) {
					case OPTION_1:
						if (pt >= 1) {
							player.sm("You have bought 50 x Eradicated Seals.");
							if (player.getInventory().hasFreeSlots()) {
								player.getInventory().addItem(12852, 50);
							} else {
								player.getBank().addItem(12852, 50, true);
								player.sm("Your item has been sent to your bank.");
							}
							player.setTriviaPoints(player.getTriviaPoints() - 1);
							end();
						} else {
							player.sm("You don't have enough points to buy that.");
							end();
						}
						break;
					case OPTION_2:
						if (pt >= 1) {
							player.sm("You have bought 30 x Invasion Token Seals.");
							if (player.getInventory().hasFreeSlots()) {
								player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() + 30);
							} else {
								player.getBank().addItem(19819, 30, true);
								player.sm("Your item has been sent to your bank.");
							}
							player.setTriviaPoints(player.getTriviaPoints() - 1);
							end();
						} else {
							player.sm("You don't have enough points to buy that.");
							end();
						}
						break;
					case OPTION_3:
						if (pt >= 2) {
							player.sm("You have bought 1 x Vote Ticket.");
							player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() + 1);
							player.setTriviaPoints(player.getTriviaPoints() - 2);
							end();
						} else {
							player.sm("You don't have enough points to buy that.");
							end();
						}
						break;
					case OPTION_4:
						if (pt >= 15) {
							player.sm("You have bought 1 x Huge XP Lamp.");
							if (player.getInventory().hasFreeSlots()) {
								player.getInventory().addItem(23716, 1);
							} else {
								player.getBank().addItem(23716, 1, true);
								player.sm("Your item has been sent to your bank.");
							}
							player.setTriviaPoints(player.getTriviaPoints() - 15);
							end();
						} else {
							player.sm("You don't have enough points to buy that.");
							end();
						}
						break;
					case OPTION_5:
						stage = 2;
						sendOptionsDialogue("What would you like to buy?",
								"Citarede Robes (Cosmetic) [225 Points]","Master Trivia Title [250 Points]","Previous Page");
						break;
				}
				break;
			case 2:
				switch (componentId) {
					case OPTION_1:
						if (pt >= 225) {
								World.sendWorldMessage("<img=5> <col=108F4D>[Trivia] " + player.getDisplayName() + " just bought Citharede Robes!", false);
								player.getBank().addItem(22413, 1, true);
								player.getBank().addItem(22414, 1, true);
								player.getBank().addItem(22415, 1, true);
								player.getBank().addItem(22417, 1, true);
								player.sm("Your item has been sent to your bank.");
							player.setTriviaPoints(player.getTriviaPoints() - 225);
							end();
						} else {
							player.sm("You don't have enough points to buy that.");
							end();
						}
						break;
					case OPTION_2:
						if (pt >= 250) {
							World.sendWorldMessage("<img=5> <col=108F4D>[Trivia] " + player.getDisplayName() + " just bought the Master Trivia Title!!!", false);
							player.sm("You have bought the master trivia title. Activate it by ;;mastertrivia");
							player.setTriviamaster(true);
							player.setTriviaPoints(player.getTriviaPoints() - 250);
							end();
						} else {
							player.sm("You don't have enough points to buy that.");
							end();
						}
						break;
					case OPTION_3:
						stage = 1;
						sendOptionsDialogue("What would you like to buy?",
								"50 x Eradicated Seals [1 Point]",
								"30 x Invasion Tokens [1 Point]",
								"1 x Vote Tickets [2 Point]", 
								"1 x Huge XP lamp [15 Points]", "Next Page");
						break;
				}
				break;
			}
	}
	


	@Override
	public void finish() {
	}

}


