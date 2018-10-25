package com.rs.game.player.dialogues.impl;

import com.rs.game.npc.NPC;
import com.rs.game.npc.others.AntiBot;
import com.rs.game.player.dialogues.Dialogue;

public class AntiBotD extends Dialogue {

	private int npcId;
	NPC npc;

	@Override
	public void start() {
		npcId = (int) parameters[0];
		npc = (NPC) parameters[1];
		sendNPCDialogue(npcId, 9827, "Just checking by, seeing your too serious with what your doing :D");
		stage = 1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		switch(stage){
		case 1:
			if (npcId == 8122) {
				AntiBot antibot = (AntiBot) npc;
				antibot.giveReward(player);
				end();
			}
			break;
		}
	}

	@Override
	public void finish() {

	}

}
