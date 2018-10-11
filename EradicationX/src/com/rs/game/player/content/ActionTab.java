package com.rs.game.player.content;

import com.rs.game.player.Player;
import com.rs.game.player.QuestManager.Quests;
import com.rs.game.player.quest.QNames;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * 
 * @ Edited by EradicationX
 */
public class ActionTab {
	

	public static void sendTab(final Player player) {
		player.tasktab = 1;
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (player.tasktab == 1) {
				player.getPackets().sendIComponentText(930, 10, player.getDisplayName()+"'s Boss Kill Counts</col>");
				player.getPackets().sendIComponentText(930, 16, 
					"Fatal Resort KC: <col=FFFFFF>"+player.getFatalKills()+" </col><br>" +
					"Something KC: <col=FFFFFF>"+player.getSomethingKills()+" </col><br>" +
					"Copyright KC: <col=FFFFFF>"+player.getCopyrightKills()+" </col><br>" +
					"Hairymonkey KC: <col=FFFFFF>"+player.getHairymonkeykills()+" </col><br>" +
					"Hard Mode Trio KC: <col=FFFFFF>"+player.getHMTrioKills()+" </col><br>" +
					"Deathlotus Ninja KC: <col=FFFFFF>"+player.getGenoKills()+" </col><br>" +
					"Seasinger Mage KC: <col=FFFFFF>"+player.getRajjKills()+" </col><br>" +
					"Blink KC: <col=FFFFFF>"+player.getBlinkKills()+" </col><br>" +
					"Wildywyrm KC: <col=FFFFFF>"+player.getWyrmKills()+" </col><br>" +
					"Necrolord KC: <col=FFFFFF>"+player.getNecrolordKills()+" </col><br>" +
					"Avatar KC: <col=FFFFFF>"+player.getAvatarKills()+" </col><br>" +
					"Fear KC: <col=FFFFFF>"+player.getFearKills()+" </col><br>" +
					"Maximum Gradum KC: <col=FFFFFF>"+player.getGradumKills()+" </col><br>" +
					"Wilderness Boss KC: <col=FFFFFF>"+player.getWildyBossKills()+" </col><br>" +
					"Corporeal Beast KC: <col=FFFFFF>"+player.getCorporealKills()+"</col><br>" +					
					"Nex KC: <col=FFFFFF>"+player.getNexKills()+" </col><br>" +
					"Saradomin KC: <col=FFFFFF>"+player.getSaradominKills()+" </col><br>" +
					"Bandos KC: <col=FFFFFF>"+player.getBandosKills()+" </col><br>" +
					"Zamorak KC: <col=FFFFFF>"+player.getZamorakKills()+" </col><br>" +
					"Armadyl KC: <col=FFFFFF>"+player.getArmadylKills()+" </col><br>" +
					"Eradicator KC: <col=FFFFFF>"+player.getEradicatorBossKills()+" </col><br>" +						
					"Regular Boss KC: <col=FFFFFF>"+player.getRegularBossKills()+" </col><br>" +					
					"Extreme Boss KC: <col=FFFFFF>"+player.getExtremeBossKills()+"</col><br>" +
					"Sunfreet Boss KC: <col=FFFFFF>"+player.getSunfreetKills()+"</col><br>" +						
					"TzHaar-Jad KC: <col=FFFFFF>"+player.getJadKills()+"</col><br>" +
					"Obsidian King KC: <col=FFFFFF>"+player.getObsidianKingKills()+"</col>");
				} else if (player.tasktab == 2) {
					player.getPackets().sendIComponentText(930, 10, player.getDisplayName()+"'s Achievements");
					player.getPackets().sendIComponentText(930, 16, 
					(player.getBossSlayerCount(false) >= 50 ? "-<col=0DFF00>" : "-<col=FF0000>") +" Complete 50 Boss Slayer Tasks [" +player.getBossSlayerCount(true) +"/50]<br></col>" +
					(player.getSlayerCount(false) >= 50 ? "-<col=0DFF00>" : "-<col=FF0000>") +" Complete 50 Slayer Tasks [" +player.getSlayerCount(true) +"/50]<br></col>" +
					(player.getDummyDamage() >= 10000 ? "-<col=0DFF00>" : "-<col=FF0000>") +" Deal over 10,000 damage on the Melee Dummy<br></col>" +
					(player.getDropbeam() >= 6 ? "-<col=0DFF00>" : "-<col=FF0000>") +" Unlock all available loot beams <br></col>" +
					(player.getAmountThieved(false) >= 50000000 ? "-<col=0DFF00>" : "-<col=FF0000>") +" Thieve 50M worth of items from thieving stalls ["+Utils.formatNumber(player.getAmountThieved(true))+"/50,000,000]<br></col>" +
					(player.check10BRequirements() ? "-<col=0DFF00>" : "-<col=FF0000>") +" Gain 10,000,000,000 XP<br></col>" +
					(player.isKilledDragithNurn() ? "-<col=0DFF00>" : "-<col=FF0000>") +" Kill Dragith Nurn (Big)<br></col>" +
					(player.isKilledQueenBlackDragon() ? "-<col=0DFF00>" : "-<col=FF0000>") +" Kill QBD<br></col>" +
					(player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM) ? "-<col=0DFF00>" : "-<col=FF0000>") +" Kill Nomad<br></col>" +
					(player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_I) ? "-<col=0DFF00>" : "-<col=FF0000>") +" Do Quest: Elite Chapter 1<br></col>" +
					(player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_II) ? "-<col=0DFF00>" : "-<col=FF0000>") +" Do Quest: Elite Chapter 2<br></col>" +
					(player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_III) ? "-<col=0DFF00>" : "-<col=FF0000>") +" Do Quest: Elite Chapter 3<br></col>" +
					(player.isCompletedFightCaves() ? "-<col=0DFF00>" : "-<col=FF0000>") +" Complete Fight Caves<br></col>");	
				}
			}
		}, 0, 3);
	}
	
}