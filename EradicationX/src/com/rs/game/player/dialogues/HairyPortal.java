package com.rs.game.player.dialogues;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue
// created by fatal resort - EradicationX
public class HairyPortal extends Dialogue {

	public HairyPortal() {
	}

	@Override
	public void start() {
			stage = 1;
			sendOptionsDialogue("What would you like to do?", "End Instance", "Nevermind");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				sendDialogue("Are you sure you want to end your instance?",
							 "Note: You will be sent home",
							 "You will lose your current instance!",
							 "If you want to return, you'd need to buy a new instance.");
				stage = 2;
		} else if (componentId == OPTION_2) {
			end();
		}

		} else if (stage == 2) {
			sendOptionsDialogue("Please select an option.", "Yes.", "No.");
			stage = 3;
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				player.setInstanceEnd(true);
				player.setInstanceBooth(0);
				player.sm("Your instance has ended. You will now be sent to the original room.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} else if (stage == 4) {
			sendOptionsDialogue("Select an option", "Pay [40M]", "Permanent Buy [400M]", "Nevermind");
			stage = 5;
		} else if (stage == 5) {
			if (componentId == OPTION_1) {
				if (player.chargeMoney(40000000)) {
					player.setInstanceBooth(player.getTimer());
					player.sm("You've unlocked the bank. Access it by the portal.");
					end();
				} else {
					player.sm("You can't afford this.");
					end();
				}
			} else if (componentId == OPTION_2) {
				if (player.chargeMoney(400000000)) {
					player.setPermaBank(true);
					player.sm("You've unlocked the bank. Access it by the portal. Note: This is for all bosses.");
				} else  if (player.getCurrencyPouch().spend100mTicket(4)) {
					player.setPermaBank(true);
					player.sm("You've unlocked the bank. Access it by the portal. Note: This is for all bosses."); 
				} else {
					player.sm("You can't afford this.");
				}
					end();			
			} else if (componentId == OPTION_3) {
				end();
			}
		}
	}
	@Override
	public void finish() {
	}

}
