package com.rs.game.player.content.custom;

import com.rs.game.player.KillCount;
import com.rs.game.player.Player;
import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

/**
 *
 * @author Administrator
 */
public class RunePortal {
  
    public static void handleButtons(Player player, int componentId) {
        if (componentId == 2) {
        	if (player.inInstance() || !player.canSpawn() || player.getJailed() > 0)
            	player.sm("You can't access teleports at the moment.");         
        	else
        	player.getDialogueManager().startDialogue("PortalTeleport", 230);
            return;
        }
        else if (componentId == 4) {
			player.getEXQuestManager().sendQuestInterface();
            return;
        }
        else if (componentId == 6) {
            player.getDialogueManager().startDialogue("Noticeboard", 230);
            return;
        }
        else if (componentId == 8) {
				Magic.sendNormalTeleportSpell(player, 0, 0, Settings.PLAYER_HOME_LOCATION);
            return;
        }
        else if (componentId == 10) {
 				player.getPackets().sendOpenURL("http://eradication-reborn.com/forums/");
            return;
        }
        else if (componentId == 12) {
				player.getInterfaceManager().sendInterface(275);
					for (int i = 0; i < 100; i++) {
				player.getPackets().sendIComponentText(275, i, "");
				}			
				player.getPackets().sendIComponentText(275, 1, "EradicationX");	
				player.getPackets().sendIComponentText(275, 10, "");				
				player.getPackets().sendIComponentText(275, 11, "");
				player.getPackets().sendIComponentText(275, 12, "<col=000000><u>Commands</u>");				
				player.getPackets().sendIComponentText(275, 13, "<col=000000>;;recov, sets recovery answer");
				player.getPackets().sendIComponentText(275, 14, "<col=000000>;;setrecov, sets recovery question");
				player.getPackets().sendIComponentText(275, 15, "<col=000000>;;empty, empties your items in inventory");
				player.getPackets().sendIComponentText(275, 16, "<col=000000>;;kdr, Kill/Death Ratio");
				player.getPackets().sendIComponentText(275, 17, "<col=000000>;;players, shows players online");
				player.getPackets().sendIComponentText(275, 18, "<col=000000>;;donate, redirects you to our donation ");
				player.getPackets().sendIComponentText(275, 19, "<col=000000>;;claimweb, claims your donation reward");
				player.getPackets().sendIComponentText(275, 20, "<col=000000>;;vote, takes you to our voting page");
				player.getPackets().sendIComponentText(275, 21, "<col=000000>;;chill, takes you to chill");
				player.getPackets().sendIComponentText(275, 22, "<col=000000>;;lockxp, locks and unlocks experience");
				player.getPackets().sendIComponentText(275, 23, "<col=000000>;;hideyell, hides yell");
				player.getPackets().sendIComponentText(275, 24, "<col=000000>;;forums, takes you to forums");
				player.getPackets().sendIComponentText(275, 25, "<col=000000>;;rules, opens rules ingame and also forum post");
				player.getPackets().sendIComponentText(275, 26, "<col=000000>;;donatedamount, shows you how much you've donated");
				player.getPackets().sendIComponentText(275, 27, "<col=000000>;;ticket, use it if you need help from a staff member");
				player.getPackets().sendIComponentText(275, 28, "<col=000000>;;hidedice, hides the dice rolls");
				player.getPackets().sendIComponentText(275, 29, "<col=000000>;;changepassword, changes password");
				player.getPackets().sendIComponentText(275, 30, "<col=000000>;;bandos, takes you to bandos");
				player.getPackets().sendIComponentText(275, 31, "<col=000000>;;saradomin, takes you to saradomin");
				player.getPackets().sendIComponentText(275, 32, "<col=000000>;;zamorak, takes you to zamorak");
				player.getPackets().sendIComponentText(275, 33, "<col=000000>;;armadyl, takes you to armadyl");
				player.getPackets().sendIComponentText(275, 34, "<col=000000>;;trio, takes you to trio");
				player.getPackets().sendIComponentText(275, 35, "<col=000000>;;checkobby, checks if the obby effect is enabled");
				player.getPackets().sendIComponentText(275, 36, "<col=000000>;;checktbox, gives list of items from the new donation boxes");
				player.getPackets().sendIComponentText(275, 37, "<col=000000>;;checkcbox, gives list of items from the new donation boxes");
				player.getPackets().sendIComponentText(275, 38, "<col=000000>;;resettimer, instantly exits instance");
				player.getPackets().sendIComponentText(275, 39, "<col=000000>;;creationtime, tells you the date when you created your account");
				player.getPackets().sendIComponentText(275, 40, "<col=000000>;;vettitle, gives you veteran title");
				player.getPackets().sendIComponentText(275, 41, "<col=000000>;;boxinggloves, gives you boxing gloves");
				player.getPackets().sendIComponentText(275, 42, "<col=000000>;;changepin, changes pin");
				player.getPackets().sendIComponentText(275, 43, "<col=000000>;;boxinggloves, gives you boxing gloves");
				player.getPackets().sendIComponentText(275, 44, "<col=000000>;;droprate, takes you to the forum post with all drop rates");
				player.getPackets().sendIComponentText(275, 45, "<col=000000>;;donatorperks, takes you to the forum post with all donator perks");
				player.getPackets().sendIComponentText(275, 46, "");
				player.getPackets().sendIComponentText(275, 47, "<col=000000><u>Donators only</u>");
				player.getPackets().sendIComponentText(275, 48, "<col=000000>;;dz");
				player.getPackets().sendIComponentText(275, 49, "<col=000000>;;regboss");
				player.getPackets().sendIComponentText(275, 50, "<col=000000>;;title 1-100");
				player.getPackets().sendIComponentText(275, 51, "<col=000000>;;boxregular");
				player.getPackets().sendIComponentText(275, 52, "<col=000000><u>Extremes only</u>");				
				player.getPackets().sendIComponentText(275, 53, "<col=000000>;;ez");
				player.getPackets().sendIComponentText(275, 54, "<col=000000>;;extremeboss");
				player.getPackets().sendIComponentText(275, 55, "<col=000000>;;boxextreme");
				player.getPackets().sendIComponentText(275, 56, "<col=000000><u>Supers only</u>");	
				player.getPackets().sendIComponentText(275, 57, "<col=000000>;;superzone");
				player.getPackets().sendIComponentText(275, 58, "<col=000000>;;sunfreet");	
				player.getPackets().sendIComponentText(275, 59, "<col=000000>;;supers");	
				player.getPackets().sendIComponentText(275, 60, "<col=000000>;;boxsuper");
				player.getPackets().sendIComponentText(275, 61, "<col=000000><u>Eradicators Only </u>");	
				player.getPackets().sendIComponentText(275, 62, "<col=000000>;;brutals");
				player.getPackets().sendIComponentText(275, 63, "<col=000000>;;eradzone");	
				player.getPackets().sendIComponentText(275, 64, "<col=000000>;;boxeradicator");	
				player.getPackets().sendIComponentText(275, 65, "<col=000000><u>Ironmen Only </u>");	
				player.getPackets().sendIComponentText(275, 66, "<col=000000>;;iz");	
				player.getPackets().sendIComponentText(275, 67, "<col=000000>;;giveup, <u>WARNING</u> this will take off your ironman rank.");		
				player.getPackets().sendIComponentText(275, 68, "<col=000000>;;worldvote, see the current state of world vote");				
            return;
        }
        else if (componentId == 14) {
        	player.setInterfaceAmount(1);
        	KillCount.openInterface(player);
            return;
        }
    }
    
    
}
