package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.utils.ShopsHandler;

public class IronmanNPC extends Dialogue {

	private int npcId;
	
	@Override
	public void start() {
		if (player.ironfirsttime == 0) {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_1_TEXT_CHAT, 
				new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hello, Ironman. Mister 'I want a bigger challenge'! How's this for a challenge!?" }, IS_NPC, npcId, 13955);
	} else {
		stage = 2;
		sendOptionsDialogue("Select an option", "<img=18> Teleports",
				"<img=18> Ironman Shop", "Turn my title on", "Restore Health & Special Attack", "Nevermind");

	}
	}
	@Override
	public void run(int interfaceId, int componentId) {
		if(stage == -1) {
			stage = 0;
			player.applyRHit(new Hit(player, player.getHitpoints() - 1, HitLook.REGULAR_DAMAGE));
			sendPlayerDialogue(9810,
					"Ouch! What the hell was that for?");
		}
		else if(stage == 0) {
				player.setHitpoints(player.getMaxHitpoints());
				player.ironfirsttime = 1;
				start();
		} else if (stage == 2) {
			if(componentId == OPTION_1) {
				stage = 3;
				sendOptionsDialogue("Select a Teleport", "<img=18> Brutal Dragons",
						"<img=18> Tzhaar-Jad", "<img=18> Obsidian Champion", "Close");
			} else if(componentId == OPTION_2) {
				player.getInterfaceManager().closeChatBoxInterface();					
				ShopsHandler.openShop(player, 52);
			} else if(componentId == OPTION_3) {
				player.getAppearence().setTitle(8198215); 
				player.getPackets().sendGameMessage("You have activated your Ironman Title.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();				
				player.reset();
				player.sm("Your Special attack, Prayer points and health have been restored.");
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();				
		}
		} else if(stage == 3) {
			if(componentId == OPTION_1) {
				player.getDialogueManager().startDialogue("Brutals");				
			} else if (componentId == OPTION_2) {
				player.getDialogueManager().startDialogue("EradJadKing");		
			} else if (componentId == OPTION_3) {
				player.getDialogueManager().startDialogue("EradKing");  
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();					
			} else if (componentId == OPTION_5) {
				player.getInterfaceManager().closeChatBoxInterface();	                
			} 
			}
		}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
	}

}

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue




