package com.rs.game.player.dialogues;

import com.rs.game.World;
import com.rs.game.player.dialogues.Dialogue;

/* created by Fatal Resort - EradicationX */
public class PickIronman extends Dialogue {
	

	public PickIronman() {
	}
	
	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Play as an Ironman? YOU WILL NOT BE ABLE TO REVERSE YOUR DECISION!",
				"Play normally", "<img=23> Become an Ironman <img=23>");
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {	
			if (componentId == OPTION_1) {
				World.sendWorldMessage("<col=E35D30>[<img=5>New Player]: "+player.getDisplayName() + " has joined EradicationX!", false);
				player.getControlerManager().forceStop();
				end();			
			} else {
				player.setIronMan(true);
				World.sendWorldMessage("<col=E35D30>[<img=5>New Player]: "+"An Ironman, <img=23><shad=BFBEC2>"+ player.getDisplayName() + " </shad>has joined EradicationX! ", false);
				player.getControlerManager().forceStop();
				end();	
			}

	}
	}
	
	@Override
	public void finish() {
	}

}
