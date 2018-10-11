package com.rs.game.player.dialogues;

import com.rs.utils.Utils;

public class WithdrawCurrencyPouch extends Dialogue {

	public WithdrawCurrencyPouch() {
	}

	@Override
	public void start() {
		if (player.getTrade().isTrading()) {
			player.sm("Please finish trading before withdrawing currencies.");
			stage = 100;
		} else {
		stage = 1;
		sendOptionsDialogue("Which currency would you like to withdraw?",
				"100M Tickets: " + Utils.formatNumber(player.getCurrencyPouch().get100MTicket()),
				"Invasion Tokens: " + Utils.formatNumber(player.getCurrencyPouch().getInvasionTokens()),
				"Vote Tickets: " + Utils.formatNumber(player.getCurrencyPouch().getVoteTickets()), 
				"Eradicated Seals: " + Utils.formatNumber(player.getCurrencyPouch().getEradicatedSeals()), "Close");
		}

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			switch (componentId) {
				case OPTION_1:
					player.getTemporaryAttributtes().put("hundredm_remove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"100M Tickets: " + Utils.formatNumber(player.getCurrencyPouch().get100MTicket())});
					end();
					break;
				case OPTION_2:
					player.getTemporaryAttributtes().put("invasiontok_remove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Invasion Tokens: " + Utils.formatNumber(player.getCurrencyPouch().getInvasionTokens())});
					end();
					break;
				case OPTION_3:
					player.getTemporaryAttributtes().put("votetick_remove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Vote Tickets: " + Utils.formatNumber(player.getCurrencyPouch().getVoteTickets())});
					end();
					break;
				case OPTION_4:
					player.getTemporaryAttributtes().put("eradicatedseal_remove", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Eradicated Seals: " + Utils.formatNumber(player.getCurrencyPouch().getEradicatedSeals())});
					end();
					break;
				case OPTION_5:
					end();
					break;
			
			}
			break;	
		default:
			end();
		break;
		}
	}
	


	@Override
	public void finish() {
	}

}

