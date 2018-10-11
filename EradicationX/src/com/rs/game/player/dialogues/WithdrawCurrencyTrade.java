package com.rs.game.player.dialogues;

import com.rs.utils.Utils;

public class WithdrawCurrencyTrade extends Dialogue {

	public WithdrawCurrencyTrade() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Which currency would you like to add?", "Coins: " + Utils.formatNumber(player.getPouch().getAmount()),
				"100M Tickets: " + Utils.formatNumber(player.getCurrencyPouch().get100MTicket()),
				"Invasion Tokens: " + Utils.formatNumber(player.getCurrencyPouch().getInvasionTokens()),
				"Vote Tickets: " + Utils.formatNumber(player.getCurrencyPouch().getVoteTickets()), 
				"Eradicated Seals: " + Utils.formatNumber(player.getCurrencyPouch().getEradicatedSeals()));

	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case 1:
			switch (componentId) {
				case OPTION_1:
					player.getTemporaryAttributtes().put("trade_moneypouch", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Coins: " + Utils.formatNumber(player.getPouch().getAmount())});
					end();
					break;
				case OPTION_2:
					player.getTemporaryAttributtes().put("trade_100m", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"100M Tickets: " + Utils.formatNumber(player.getCurrencyPouch().get100MTicket())});
					end();
					break;
				case OPTION_3:
					player.getTemporaryAttributtes().put("trade_invasion", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Invasion Tokens: " + Utils.formatNumber(player.getCurrencyPouch().getInvasionTokens())});
					end();
					break;
				case OPTION_4:
					player.getTemporaryAttributtes().put("trade_vote", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Vote Tickets: " + Utils.formatNumber(player.getCurrencyPouch().getVoteTickets())});
					end();
					break;
				case OPTION_5:
					player.getTemporaryAttributtes().put("trade_seals", Boolean.TRUE);
					player.getPackets().sendRunScript(108, new Object[] 
					{"Eradicated Seals: " + Utils.formatNumber(player.getCurrencyPouch().getEradicatedSeals())});
					end();
					break;
			
			}
			break;	
		}
	}
	


	@Override
	public void finish() {
	}

}

