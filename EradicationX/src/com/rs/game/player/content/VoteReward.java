package com.rs.game.player.content;

import com.motivoters.motivote.service.MotivoteRS;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.content.custom.DoubleVoteManager;
import com.rs.utils.VotingBoard;

public class VoteReward implements Runnable {
	
	private final static MotivoteRS motivote = new MotivoteRS("eradicationx", "82d158b4f6ec1fb5c332d28897af7a9f"); // replace API key and subdomain with yours.
    private String var;
    private Player player;

    public VoteReward(String var, Player player) {
        this.var = var;
        this.player = player;
    }

    public void run() {
    	boolean success = motivote.redeemVote(var);
		if (success) {
			WorldVote.setVotes(WorldVote.getVotes() + 1);
			if (!DoubleVoteManager.isFirstDayofMonth()) {
				player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() + 2);
				player.voteDisplayAmount++;
				player.sendVoteNotification();
			} else {
				player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() + 4);
				player.voteDisplayAmount+=2;
				player.sendVoteNotification();
			}
			if (WorldVote.getVotes() >= 200) {
				World.sendWorldMessage("<img=5><col=ff0000>[Global Vote]: Hourly 1.5x XP is now active. This XP stacks with every other bonus you have!", false);
				WorldVote.startReward();
			}
			player.setSpellPower(player.getSpellPower()+2);
			player.setVote(player.getVote()+2);
			VotingBoard.checkRank(player);
		}
		else {
			player.sm("Invalid auth supplied, please try again later.");
		}
		player.completed = false;
    }
}