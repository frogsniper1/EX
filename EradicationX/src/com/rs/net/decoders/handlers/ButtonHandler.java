package com.rs.net.decoders.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.content.exchange.ExchangeHandler;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.Crucible;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.BossPets;
import com.rs.game.player.CombatDefinitions;
import com.rs.game.player.EmotesManager;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.KeybindSettings;
import com.rs.game.player.Player;
import com.rs.game.player.PreviewPreset;
import com.rs.game.player.RankLend;
import com.rs.game.player.RewardRequirements;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.FightPitsViewingOrb;
import com.rs.game.player.actions.HomeTeleport;
import com.rs.game.player.actions.Rest;
import com.rs.game.player.actions.Smithing.ForgingInterface;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.content.AdventurersLog;
import com.rs.game.player.content.ArtisanWorkshop;
import com.rs.game.player.content.ConstructFurniture;
import com.rs.game.player.content.CrystalSystem;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.PlayerDesign;
import com.rs.game.player.content.PlayerLook;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SandwichLadyHandler;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.SkillsDialogue;
import com.rs.game.player.content.Subscribe;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.custom.RunePortal;
import com.rs.game.player.dialogues.LevelUp;
import com.rs.game.player.dialogues.Transportation;
import com.rs.game.player.quest.QNames;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.net.decoders.WorldPacketsDecoder;
import com.rs.rss.ForumThread;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;

public class ButtonHandler {

     public static Integer[][] getItemSlotsKeptOnDeath (Player player, boolean atWilderness, boolean skulled, boolean protectPrayer) {
    	ArrayList<Integer> droppedItems = new ArrayList<Integer>();
    	ArrayList<Integer> protectedItems = new ArrayList<Integer>();
    	ArrayList<Integer> lostItems = new ArrayList<Integer>();
    	for (int i = 1; i < 44; i++) {
    	    Item item = i >= 16 ? player.getInventory().getItem(i - 16) : player.getEquipment().getItem(i - 1);
    	    if (item == null)
    	    	continue;
    	    int stageOnDeath = 1;
    	    if (!atWilderness && stageOnDeath == 1)
    	    	protectedItems.add(i);
    	    else if (stageOnDeath == -1)
    	    	lostItems.add(i);
    	    else
    	    	droppedItems.add(i);
    	}
    	int keptAmount = skulled ? 0 : 3;
    	if (protectPrayer)
    	    keptAmount++;
    	if (droppedItems.size() < keptAmount)
    	    keptAmount = droppedItems.size();
    		Collections.sort(droppedItems, new Comparator<Integer>() {
    	    @Override
    	    public int compare(Integer o1, Integer o2) {
	    		Item i1 = o1 >= 16 ? player.getInventory().getItem(o1 - 16) : player.getEquipment().getItem(o1 - 1);
	    		Item i2 = o2 >= 16 ? player.getInventory().getItem(o2 - 16) : player.getEquipment().getItem(o2 - 1);
	    		int price1 = player.getProtectPrice(i1);
	    		int price2 = player.getProtectPrice(i2);
	    		if (price1 > price2)
	    		    return -1;
	    		if (price1 < price2)
	    		    return 1;
	    		return 0;
    	    }
    	});
    	Integer[] keptItems = new Integer[keptAmount];
    	for (int i = 0; i < keptAmount; i++)
    	    keptItems[i] = droppedItems.remove(0);
    	return new Integer[][] { keptItems, droppedItems.toArray(new Integer[droppedItems.size()]), atWilderness ? new Integer[0] : protectedItems.toArray(new Integer[protectedItems.size()]), atWilderness ? new Integer[0] : lostItems.toArray(new Integer[lostItems.size()]) };
    }
     public static Item[][] getItemsKeptOnDeath(Player player, Integer[][] slots) {
    	ArrayList<Item> droppedItems = new ArrayList<Item>();
    	ArrayList<Item> keptItems = new ArrayList<Item>();
    	for (int i : slots[0]) { 
    	    Item item = i >= 16 ? player.getInventory().getItems().getItemsCopy()[i - 16] : player.getEquipment().getItems().getItemsCopy()[i - 1] ;
    	    if (item == null) 
    	    	continue;
	    	Item im = new Item(item.getId(), item.getAmount());
    	    if (item.getAmount() > 1) {
    	    	droppedItems.add(new Item(item.getId(), item.getAmount() - 1));
    	    	im.setAmount(1);
    	    }
    	    keptItems.add(im);
    	}
    	for (int i : slots[1]) { 
    	    Item item = i >= 16 ? player.getInventory().getItem(i - 16) : player.getEquipment().getItem(i - 1);
    	    if (item == null) 
    	    	continue;
    	    droppedItems.add(item);
    	}
    	for (int i : slots[2]) { //items protected by default
    	    Item item = i >= 16 ? player.getInventory().getItem(i - 16) : player.getEquipment().getItem(i - 1);
    	    if (item == null) 
    	    	continue;
    	    keptItems.add(item);
    	}
    	return new Item[][] { keptItems.toArray(new Item[keptItems.size()]), droppedItems.toArray(new Item[droppedItems.size()]) };
    }
    
	
	public static void sendItemsKeptOnDeath(Player player, boolean wilderness) {
		boolean skulled = player.hasSkull();
		Integer[][] slots = getItemSlotsKeptOnDeath(player, wilderness, skulled, player.getPrayer().usingPrayer(0, 10)
				|| player.getPrayer().usingPrayer(1, 0));
		Item[][] items = getItemsKeptOnDeath(player, slots);
		long riskedWealth = 0;
		long carriedWealth = 0;
		for (Item item : items[1])
		    carriedWealth = riskedWealth += player.getProtectPrice(item);
		for (Item item : items[0])
		    carriedWealth += player.getProtectPrice(item);
		if (slots[0].length > 0) {
		    for (int i = 0; i < slots[0].length; i++)
		    	player.getVarsManager().sendVarBit(9222 + i, slots[0][i]);
		    player.getVarsManager().sendVarBit(9227, slots[0].length);
		} else {
		    player.getVarsManager().sendVarBit(9222, -1);
		    player.getVarsManager().sendVarBit(9227, 1);
		}
		player.getVarsManager().sendVarBit(9226, wilderness ? 1 : 0);
		player.getVarsManager().sendVarBit(9229, skulled ? 1 : 0);
		StringBuffer text = new StringBuffer();
		player.getPackets().sendGlobalString(352, text.toString());
	}	
	
	public static void handleButtons(final Player player, InputStream stream,
			int packetId) {
		int interfaceHash = stream.readIntV2();
		int interfaceId = interfaceHash >> 16;
		if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
			// hack, or server error or client error
			// player.getSession().getChannel().close();
			return;
		}
		
		if (player.isDead()
				|| !player.getInterfaceManager().containsInterface(interfaceId))
			return;
		final int componentId = interfaceHash - (interfaceId << 16);
		if (componentId != 65535 && Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
			return;
		}

		final int slotId2 = stream.readUnsignedShort128();
		final int slotId = stream.readUnsignedShortLE128();
		if (!player.getControlerManager().processButtonClick(interfaceId, componentId, slotId, packetId))
			return;
		if (interfaceId == 1156) {
			if (player.getInterfaceAmount() == 20) {
				BossPets.handleButtons(player, componentId);
				return;
			} else {
			com.rs.game.player.CosmeticItems.handleButtons(player, componentId);
			return;
			}
		}
		
		if (interfaceId == 746 || interfaceId == 548) {
			if ((interfaceId == 548 && componentId == 150) || (interfaceId == 746 && componentId == 194)) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getInterfaceManager().closeScreenInterface();
					return;
				}
				player.getInterfaceManager().sendInterface(3003);
				return;
			}
			if (interfaceId == 746 && componentId == 75
					|| interfaceId == 548 && componentId == 99) {
				player.getPackets().sendIComponentText(930, 10, player.getDisplayName()+"'s Boss Kill Counts</col>");
				player.getPackets().sendIComponentText(930, 16, "Loading, Please wait...");
				player.tasktab = 1;
				return;
			}else if (interfaceId == 746 && componentId == 88
					|| interfaceId == 548 && componentId == 74) {
				player.getPackets().sendIComponentText(930, 10, player.getDisplayName()+"'s Achievements");
				player.getPackets().sendIComponentText(930, 16, "Loading, Please wait...");
				player.tasktab = 2;
				return;
			}
		}
		if (interfaceId == 3006) {
			if (componentId == 1) {
				player.getInterfaceManager().closeManagement();
				return;
			}
		}
		if (interfaceId == 3022)
			player.getInterfaceManager().closeScreenInterface();
		if (interfaceId == 3008) {
			if (componentId == 1) { // Save P1
				player.setSaveP(1);
				player.getInterfaceManager().sendInterface(3009);
			} else if (componentId == 2) { // Save P2
				player.setSaveP(2);
				player.getInterfaceManager().sendInterface(3009);
			} else if (componentId == 3) { // Save P3
				player.setSaveP(3);
				player.getInterfaceManager().sendInterface(3009);			
			} else if (componentId == 4) { // Save P4
				player.setSaveP(4);
				player.getInterfaceManager().sendInterface(3009);		
			} else if (componentId == 5) { // Name P1
				player.getTemporaryAttributtes().put("setpresetname0", Boolean.TRUE); 
				player.getPackets().sendInputNameScript("Enter the name you want for this preset:"); 
			} else if (componentId == 6) { // Name P2
				player.getTemporaryAttributtes().put("setpresetname1", Boolean.TRUE); 
				player.getPackets().sendInputNameScript("Enter the name you want for this preset:"); 				
			} else if (componentId == 7) { // Name P3
				player.getTemporaryAttributtes().put("setpresetname2", Boolean.TRUE); 
				player.getPackets().sendInputNameScript("Enter the name you want for this preset:"); 				
			} else if (componentId == 8) { // Name P4
				player.getTemporaryAttributtes().put("setpresetname3", Boolean.TRUE); 
				player.getPackets().sendInputNameScript("Enter the name you want for this preset:"); 		
			} else if (componentId == 9) { // Load P1 
				player.getBankPreset().loadPreset();
			} else if (componentId == 10) { // Load P2
				player.getBankPreset2().loadPreset();
			} else if (componentId == 11) { // Load P3
				player.getBankPreset3().loadPreset();
			} else if (componentId == 12) { // Load P4
				player.getBankPreset4().loadPreset();
			} else if (componentId == 14) { // Reset P1
				player.getDialogueManager().startDialogue("ResetP1");
			} else if (componentId == 15) { // Reset P2
				player.getDialogueManager().startDialogue("ResetP2");
			} else if (componentId == 16) { // Reset P3
				player.getDialogueManager().startDialogue("ResetP3");
			} else if (componentId == 17) { // Reset P4
				player.getDialogueManager().startDialogue("ResetP4");
			} else if (componentId == 18) { // Preview P1	
				PreviewPreset.openInterface(player, player.getBankPreset());
			} else if (componentId == 19) { // Preview P2	
				PreviewPreset.openInterface(player, player.getBankPreset2());
			} else if (componentId == 20) { // Preview P3
				PreviewPreset.openInterface(player, player.getBankPreset3());
			} else if (componentId == 21) { // Preview P4	
				PreviewPreset.openInterface(player, player.getBankPreset4());
			} else if (componentId == 26) { // Quick Select P1
				player.getBankPreset().changeQuickSelect(1);
					player.sm("The Quick button " + player.getBankPreset().getQuickSelect() + " has been set for preset 1.");
				player.sendBankPreset();
			} else if (componentId == 27) { // Quick Select P2
				player.getBankPreset2().changeQuickSelect(2);
					player.sm("The Quick button " + player.getBankPreset().getQuickSelect() + " has been set for preset 1.");
				player.sendBankPreset();
			} else if (componentId == 28) { // Quick Select P3
				player.getBankPreset3().changeQuickSelect(3);
					player.sm("The Quick button " + player.getBankPreset().getQuickSelect() + " has been set for preset 1.");
				player.sendBankPreset();
			} else if (componentId == 29) { // Quick Select P4
				player.getBankPreset4().changeQuickSelect(4);
					player.sm("The Quick button " + player.getBankPreset().getQuickSelect() + " has been set for preset 1.");
				player.sendBankPreset();
			} else if (componentId == 30) {
				player.getInterfaceManager().closeScreenInterface(); 
				player.getBankT().openBank();
					return;
			} 
		}
		
		if (interfaceId == 3010) {
			if (componentId == 1) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(3008);
				player.sendBankPreset();
			}
		}
		
		if (interfaceId == 3014) {
			if (componentId == 1)
				player.closeInterfaces();
			else if (componentId == 2) {
				player.getInterfaceManager().sendInterface(1236);
				player.sit(1236, 20, "The Elite: Chapter I");
				switch (player.getEXQuestManager().getQuestStage(QNames.ELITE_CHAPTER_I)) {
				case 2:
				case 3:
					player.sit(1236, 21, "Cyndrith is ignoring me. I should try and talk to him again, maybe he'll listen.");
					break;
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					player.sit(1236, 21, "Cyndrith gave me a list of what to do. I should work on finishing it.");
					break;
				case 9:
					player.sit(1236, 21, "Cyndrith told me to go visit Gypsy Aris in Varrock. She might be able to look at Cyndrith's memory and figure out the"
							+ "enchantment spell.");
					break;
				case 10:
					player.sit(1236, 21, "Gypsy Aris told me to get some context she can use to locate the memory. I should talk to Cyndrith.");
					break;
				case 11:
					player.sit(1236, 21, "I need go to to Gypsy Aris and tell her what Cyndrith told me.");
					break;
				case 12:
					player.sit(1236, 21, "I found out the enchantment spell. I need to talk to Cyndrith to confirm if what she told me is correct.");
					break;
				case 13:
					player.sit(1236, 21, "Cyndrith confirmed that the spell is correct. I just need to write the enchantment on the scroll now.");	
					break;
				default:
					player.sit(1236, 21, "Recently, A man named Cyndrith has been residing in our home. He's been well known for the equipment he possesses. I should go have a talk with him. <br><br><col=1ED63A>Rewards: Elite scroll: Obsidian Boots - "
							+ "An additive to obsidian boots that increases health capacity, strength, and provides a set boost.");
					break;
				}
				if (player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I).isComplete())
					player.sit(1236, 21, "Quest complete! You can use your scroll of enchantment to turn a "
							+ "set of Obsidian Boots elite. If you ever need another scroll, you can use Cyndrith's chest to buy another one.");
			} else if (componentId == 4) {
				player.getInterfaceManager().sendInterface(1236);
				player.sit(1236, 20, "The Elite: Chapter II");
				switch (player.getEliteChapterII().getQuestStage()) {
				case 0:
					player.sit(1236, 21, "Requirement: The Elite: Chapter I <br><br><col=1ED63A>Rewards: Elite scroll: Obsidian Gloves - "
						+ "An additive to obsidian gloves that increases health capacity, strength, and provides a set boost.");
					break;
				case 1:
					player.sit(1236, 21, "I'm going to go talk to Gypsy Aris and see if she can look for a vision for the rest of the enchantments since Cyndrith is being a dick.");
					break;
				case 2:
					player.sit(1236, 21, "That didn't go as planned. Dragith came alone, and attacked Gypsy Aris. Good thing I was there. Or she would've died. Aris is traumatized now, so "
							+ "I guess I have to go to Cyndrith for the enchantments.");
					break;
				case 3:
					player.sit(1236, 21, "That escalated quickly. I caught Cyndrith back in his old home and saw him mourning. He spotted me and beat me up. I should go talk to him at home.");
					break;
				case 4:
					player.sit(1236, 21, "Buried corpses for Cyndrith. How far I'm going for armor.");
					break;
				case 5:
					player.sit(1236, 21, "Found out about Zombies by Cyndrith. He said he's going to give me the enchantment if I help him.");
					break;
				case 6:
					player.sit(1236, 21, "Going to confront Dragith downstairs of home.");
					break;
				case 7:
					player.sit(1236, 21, "We defeated Dragith and she gave us the key. Cyndrith went to go get the last page from her crypt.");
					break;
				case 8:
					player.sit(1236, 21, "Quest complete! You can use your scroll of enchantment to turn a "
						+ "set of Obsidian Gloves elite. If you ever need another scroll, you can use Cyndrith's chest to buy another one.");
					break;
				case 13:
					player.sit(1236, 21, "");
					break;
				default:
					player.sit(1236, 21, "");
					break;
				}
			} else if (componentId == 6) {
				player.getInterfaceManager().sendInterface(1236);
				player.sit(1236, 20, "The Elite: Chapter III");
				switch (player.getEliteChapterIII().getQuestStage()) {
				case 0:
					player.sit(1236, 21, "Requirement: The Elite: Chapter II <br><br><col=1ED63A>Rewards: Elite scroll: Obsidian Platebody - "
						+ "An additive to obsidian platebody that increases health capacity, strength, and provides a set boost.");
					break;
				case 1:
					player.sit(1236, 21, "I'm going to go talk to Gypsy Aris and see if she can look for a vision for the rest of the enchantments since Cyndrith is being a dick.");
					break;
				case 2:
					player.sit(1236, 21, "That didn't go as planned. Dragith came alone, and attacked Gypsy Aris. Good thing I was there. Or she would've died. Aris is traumatized now, so "
							+ "I guess I have to go to Cyndrith for the enchantments.");
					break;
				case 3:
					player.sit(1236, 21, "That escalated quickly. I caught Cyndrith back in his old home and saw him mourning. He spotted me and beat me up. I should go talk to him at home.");
					break;
				case 4:
					player.sit(1236, 21, "Buried corpses for Cyndrith. How far I'm going for armor.");
					break;
				case 5:
					player.sit(1236, 21, "Found out about Zombies by Cyndrith. He said he's going to give me the enchantment if I help him.");
					break;
				case 6:
					player.sit(1236, 21, "Going to confront Dragith downstairs of home.");
					break;
				case 7:
					player.sit(1236, 21, "We defeated Dragith and she gave us the key. Cyndrith went to go get the last page from her crypt.");
					break;
				case 8:
					player.sit(1236, 21, "Quest complete! You can use your scroll of enchantment to turn a "
						+ "set of Obsidian Gloves elite. If you ever need another scroll, you can use Cyndrith's chest to buy another one.");
					break;
				case 13:
					player.sit(1236, 21, "");
					break;
				default:
					player.sit(1236, 21, "");
					break;
				}
				}
		}
		
		if (interfaceId == 3011){
			if (componentId == 1)
				player.closeInterfaces();
			else
				ForumThread.handleButtons(player, componentId);
		}
		
		if (interfaceId == 3013) {
			RewardRequirements.handleButtons(player, componentId);
		}
		
		if (interfaceId == 3017 || interfaceId == 3018) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.getPackets().sendGlobalConfig(168, 15);
					player.getCurrencyPouch().openInterface();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.getDialogueManager().startDialogue("WithdrawCurrencyPouch");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					player.sm("You have: " + Utils.formatNumber( player.getLoyaltyPoints()) + " Loyalty Points");
					player.sm("You have: " + Utils.formatNumber( player.getTriviaPoints()) + " Trivia Points");
					player.sm("You have: " + Utils.formatNumber( player.getBossSlayerPoints()) + " Boss Slayer Points");
					if (player.checkEradicator())
					player.sm("You have: " + Utils.formatNumber( player.getDeposittedBones()) + " Eradicated Bones");
					player.sm("You have: " + Utils.formatNumber( player.getCurrencyPouch().get100MTicket()) + " 100M Tickets");
					player.sm("You have: " + Utils.formatNumber( player.getCurrencyPouch().getInvasionTokens()) + " Invasion Tokens");
					player.sm("You have: " + Utils.formatNumber( player.getCurrencyPouch().getVoteTickets()) + " Vote Tickets");
					player.sm("You have: " + Utils.formatNumber( player.getCurrencyPouch().getEradicatedSeals()) + " Eradicated Seals");
				}
			}
		}
		
		if (interfaceId == 3019) {
			switch (componentId) {
			case 1:
				if (player.getCurrencyPouch().get100MTicket() == 0) {
					player.sm("You don't have any 100M Tickets to withdraw.");
					break;
				}
				player.getTemporaryAttributtes().put("hundredm_remove", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"100M Tickets: " + Utils.formatNumber(player.getCurrencyPouch().get100MTicket())});
				break;
			case 2:
				if (player.getCurrencyPouch().getInvasionTokens() == 0) {
					player.sm("You don't have any Invasion Tokens to withdraw.");
					break;
				}
				player.getTemporaryAttributtes().put("invasiontok_remove", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Invasion Tokens: " + Utils.formatNumber(player.getCurrencyPouch().getInvasionTokens())});
				break;
			case 3:
				if (player.getCurrencyPouch().getVoteTickets() == 0) {
					player.sm("You don't have any Vote Tickets to withdraw.");
					break;
				}
				player.getTemporaryAttributtes().put("votetick_remove", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Vote Tickets: " + Utils.formatNumber(player.getCurrencyPouch().getVoteTickets())});
				break;
			case 4:
				if (player.getCurrencyPouch().getEradicatedSeals() == 0) {
					player.sm("You don't have any Eradicated Seals to withdraw.");
					break;
				}
				player.getTemporaryAttributtes().put("eradicatedseal_remove", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Eradicated Seals: " + Utils.formatNumber(player.getCurrencyPouch().getEradicatedSeals())});
				break;
			}
		}
		
		if (interfaceId == 3009) {
			if (componentId == 1) { // Save inventory
				switch (player.getSaveP()) {
					case 1:
						player.getBankPreset().saveInventory();
						break;
					case 2:
						player.getBankPreset2().saveInventory();
						break;
					case 3:
						player.getBankPreset3().saveInventory();
						break;
					case 4:
						player.getBankPreset4().saveInventory();
						break;
					default:
						return;
				}
				player.sm("You've saved your current inventory to your preset. Click preview to review it.");
				player.getInterfaceManager().sendInterface(3008);
				player.sendBankPreset();
			} else if (componentId == 2) { // Save Equipment
				switch (player.getSaveP()) {
				case 1:
					player.getBankPreset().saveEquipment();
					break;
				case 2:
					player.getBankPreset2().saveEquipment();
					break;
				case 3:
					player.getBankPreset3().saveEquipment();
					break;
				case 4:
					player.getBankPreset4().saveEquipment();
					break;
				default:
					return;
				}				
				player.sm("You've saved your current equipment to your preset. Click preview to review it.");
				player.getInterfaceManager().sendInterface(3008);
				player.sendBankPreset();
			} else if (componentId == 3) { // Save Both
				switch (player.getSaveP()) {
				case 1:
					player.getBankPreset().saveEquipment();
					player.getBankPreset().saveInventory();
					break;
				case 2:
					player.getBankPreset2().saveEquipment();
					player.getBankPreset2().saveInventory();
					break;
				case 3:
					player.getBankPreset3().saveEquipment();
					player.getBankPreset3().saveInventory();
					break;
				case 4:
					player.getBankPreset4().saveEquipment();
					player.getBankPreset4().saveInventory();
					break;
				default:
					return;
				}
				player.sm("You've saved your current inventory and equipment to your preset. Click preview to review it.");
				player.getInterfaceManager().sendInterface(3008);
				player.sendBankPreset();
			} else if (componentId == 4) {
			player.getInterfaceManager().closeScreenInterface(); 	
			player.getInterfaceManager().sendInterface(3008);
			player.sendBankPreset();
			return;
			}
		}
		if (interfaceId == 3007) { 
			if (componentId == 13) {
				player.getInterfaceManager().closeScreenInterface(); 
				return;
				}
			if (player.checkStaff()) {
			if (componentId == 12) { // Ban
				if (player.getRights() > 0 || player.isHeadMod() || player.isHeadExecutive() || player.isExecutive()) {
			    Player target = World.getPlayer(player.getPanelName());
                if (target != null) {
                    target.setPermBanned(true);
                    target.getPackets().sendGameMessage("You've been banned by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
                    player.getPackets().sendGameMessage("You have perm banned: " + target.getDisplayName() + ".");
                    target.getSession().getChannel().close();
                    SerializableFilesManager.savePlayer(target);
                } else {
					target = SerializableFilesManager.loadPlayer(player.getPanelName().replaceAll(" ", "_"));
                    target.setPermBanned(true);
                    player.getPackets().sendGameMessage("You have perm banned: " + Utils.formatPlayerNameForDisplay(player.getPanelName()) + ".");
                    try {
						SerializableFilesManager.storeSerializableClass(target, new File("data/playersaves/characters/" + player.getPanelName().replaceAll(" ", "_") + ".p"));
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
                } else {
                	player.sm("Only moderators and up can ban.");
                }
			}
			if (componentId == 11) { // Jail
				Player target = World.getPlayer(player.getPanelName());
				if (target != null) {
					target.setJailed(Utils.currentTimeMillis() + (24 * 60 * 60 * 1000));
					target.getControlerManager().startControler("JailControler");
					target.getPackets().sendGameMessage("You've been Jailed for 24 hours by " + Utils.formatPlayerNameForDisplay(player.getUsername()) + ".");
					player.getPackets().sendGameMessage("You have Jailed 24 hours: " + target.getDisplayName() + ".");
					SerializableFilesManager.savePlayer(target);
				} else {
					player.sm("That ain't gonna work if he ain't on.");
				}
			}
			if (componentId == 10) { // Mute
				Player target = World.getPlayer(player.getPanelName());
				if (target != null) {
					if (target.getMuteMarks() == 3) {
						player.sm("This player already has three mute marks. You have reset the loop.");
						player.sm("This player now has 1 mute mark.");
						target.setMuteMarks(1);
					} else {
					target.setMuteMarks(target.getMuteMarks() + 1);
					player.sm("You've given "+target.getDisplayName()+" a mute mark. They now have " + target.getMuteMarks() + " marks. (Three is a mute)");
					}
				} else {
					if (!SerializableFilesManager.containsPlayer(player.getPanelName())) {
						player.getPackets().sendGameMessage("Account name " + Utils.formatPlayerNameForDisplay(player.getPanelName())+ " doesn't exist.");
				}
					player.sm("Why are you trying to mute someone offline? Anyways, this command isn't compatible for offline players...<br><br><br><br> :[");
				}
			}			
			if (componentId == 9) { // View Inventory
				Player target = World.getPlayer(player.getPanelName());
				boolean loggedOut = false;
				if (target == null) {
					loggedOut = true;
					target = SerializableFilesManager.loadPlayer(player.getPanelName());
				}
				String contentsFinal = "";
				String inventoryContents = "";
				int contentsAmount;
				int freeSlots = target.getInventory().getFreeSlots();
				int usedSlots = 28 - freeSlots;
				for (int i = 0; i < 28; i++) {
					if (target.getInventory().getItem(i) == null) {
						contentsAmount = 0;
						inventoryContents = "";
					} else {
						int id = target.getInventory().getItem(i).getId();
						contentsAmount = target.getInventory().getItems().getNumberOf(id);
						inventoryContents = "Slot " + (i + 1) + " - " + target.getInventory().getItem(i).getName() + " - " + contentsAmount + "<br>";
					}
					contentsFinal += inventoryContents;
				}
				player.getInterfaceManager().sendInterface(1166);
				player.getPackets().sendIComponentText(1166, 1, contentsFinal);
				player.getPackets().sendIComponentText(1166, 2, usedSlots + " / 28 Inventory slots used.");
				player.getPackets().sendIComponentText(1166, 23, target.getDisplayName() +"</shad></col>");
				if (loggedOut) {
					try {
						SerializableFilesManager.storeSerializableClass(target, new File("data/playersaves/characters/" + player.getPanelName() + ".p"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (componentId == 8) { // Bank
				player.getDialogueManager().startDialogue("CheckBank");
			}
			} else {
				player.sm("You can't access this, as you're not a staff member.");
			}
			return;
		}
		
		if (interfaceId == 3012) {
			if (componentId == 1) {
				player.closeInterfaces();
				return;
			} 
			KeybindSettings.handleButtons(player, componentId);
		}
		
		if (interfaceId == 3003) {
			if (componentId == 1) { // Close
				player.getInterfaceManager().closeScreenInterface();
				return;
			} else if (componentId == 2) { // Graphics Settings
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);		
				return;
			} else if (componentId == 3) { // Player Settings
				player.getInterfaceManager().closeScreenInterface();
				player.getDialogueManager().startDialogue("Noticeboard", 230);
				return;
			} else if (componentId == 4) { // Keybind Settings
			//	player.sm("Currently under construction.");
				KeybindSettings.openInterface(player);
			} else if (componentId == 5) { // Vote
				player.getInterfaceManager().closeScreenInterface();
				player.sm("The vote website has opened.");
				player.getPackets().sendOpenURL("http://www.eradicationx.com/vote");
				return;
			} else if (componentId == 6) { // Donate
				player.getInterfaceManager().closeScreenInterface();
				player.sm("The donate website has opened.");
				player.getPackets().sendOpenURL("http://rsps-pay.com/store.php?id=2&tab=0");		
				return;
			} else if (componentId == 7) { // Forums
				player.getInterfaceManager().closeScreenInterface();
				player.sm("The forums have opened.");
				player.getPackets().sendOpenURL("http://www.eradicationx.com/forums");			
				return;
			} else if (componentId == 8) { // Logout
				if (!player.hasFinished())
					player.logout(false);
				return;
			}
		}
		if (interfaceId == 1310) {
			Player target = (Player) player.getTemporaryAttributtes().get(
					"DuelTarget");
			if (target == null)
				RankLend.handleOfferButtons(player, componentId);
			else {
				if (componentId == 0)
					DuelControler.challenge(player);
				else if (componentId == 1)
					player.getInterfaceManager().closeScreenInterface();
			}
			return;
		}
		if (interfaceId == 34) {// notes interface
			if (componentId == 35 || componentId == 37 || componentId == 39 || componentId == 41) {
				player.getNotes().colour((componentId - 35) / 2);
			    player.getPackets().sendHideIComponent(34, 16, true);
			} else if (componentId == 3) {
				player.getPackets().sendInputLongTextScript("Add note:");
			    player.getTemporaryAttributtes().put("entering_note", Boolean.TRUE);
			} else if (componentId == 9) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (player.getNotes().getCurrentNote() == slotId)
				    	player.getNotes().removeCurrentNote();
				    else
				    	player.getNotes().setCurrentNote(slotId);
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.getPackets().sendInputLongTextScript("Edit note:");
					player.getNotes().setCurrentNote(slotId);
					player.getTemporaryAttributtes().put("editing_note", Boolean.TRUE);
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					player.getNotes().setCurrentNote(slotId);
				    player.getPackets().sendHideIComponent(34, 16, false);
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					player.getNotes().delete(slotId);
				}
			} else if (componentId == 11) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					player.getNotes().delete();
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.getNotes().deleteAll();
				}
			}
			return;
		}
		
		if (interfaceId == 190 && componentId == 15) {
			if (slotId == 68) {
				if (player.spokeToWarrior == false
						&& player.spokeToShamus == false) {
					player.getInterfaceManager().sendInterface(275);
					player.getPackets().sendIComponentText(275, 1,
							"Lost-City Quest");
					player.getPackets().sendIComponentText(275, 10, "");
					player.getPackets().sendIComponentText(275, 11,
							"Speak to the Warrior West of Draynor");
					player.getPackets().sendIComponentText(275, 12,
							"<u>Requirements</u>");
					player.getPackets().sendIComponentText(275, 13,
							"<col=ffff00>31 Crafting, 36 Woodcutting</col>");
					player.getPackets().sendIComponentText(275, 14,
							"-=-Tips-=-");
					player.getPackets()
							.sendIComponentText(275, 15,
									"Use the 'Quests & Minigames' teleport at home to start the quest.");
					player.getPackets()
							.sendIComponentText(275, 16,
									"The lodestone works, remember to take full use of it.");
					player.getPackets()
							.sendIComponentText(275, 17,
									"You will need the skills required to complete the quest");
					player.getPackets()
							.sendIComponentText(275, 18,
									"The Monk Of Entrana removes everything in your inventory.");
					player.getPackets().sendIComponentText(275, 19, "");
					player.getPackets().sendIComponentText(275, 20, "");
				} else if (player.spokeToWarrior == true
						&& player.spokeToShamus == false) {
					player.getInterfaceManager().sendInterface(275);
					player.getPackets().sendIComponentText(275, 1,
							"Lost-City Quest");
					player.getPackets().sendIComponentText(275, 10, "");
					player.getPackets()
							.sendIComponentText(275, 11,
									"Shamus appears to be in one of the trees around this location.");
					player.getPackets()
							.sendIComponentText(275, 12,
									"The Warrior told me the tree displays 'Chop Tree'");
					player.getPackets().sendIComponentText(275, 13, "");
					player.getPackets().sendIComponentText(275, 14,
							"-=-Tips-=-");
					player.getPackets()
							.sendIComponentText(275, 15,
									"Use the 'Quests & Minigames' teleport at home to start the quest.");
					player.getPackets()
							.sendIComponentText(275, 16,
									"The lodestone works, remember to take full use of it.");
					player.getPackets()
							.sendIComponentText(275, 17,
									"You will need the skills required to complete the quest");
					player.getPackets()
							.sendIComponentText(275, 18,
									"The Monk Of Entrana removes everything in your inventory.");
					player.getPackets().sendIComponentText(275, 19, "");
					player.getPackets().sendIComponentText(275, 20, "");
				} else if (player.spokeToWarrior == true
						&& player.spokeToShamus == true) {
					player.getInterfaceManager().sendInterface(275);
					player.getPackets().sendIComponentText(275, 1,
							"Lost-City Quest");
					player.getPackets().sendIComponentText(275, 10, "");
					player.getPackets()
							.sendIComponentText(275, 11,
									"I should go find the Monk of Entrana, Who is located at Port Sarim.");
					player.getPackets().sendIComponentText(275, 12, "");
					player.getPackets().sendIComponentText(275, 13, "");
					player.getPackets().sendIComponentText(275, 14,
							"-=-Tips-=-");
					player.getPackets()
							.sendIComponentText(275, 15,
									"Use the 'Quests & Minigames' teleport at home to start the quest.");
					player.getPackets()
							.sendIComponentText(275, 16,
									"The lodestone works, remember to take full use of it.");
					player.getPackets()
							.sendIComponentText(275, 17,
									"You will need the skills required to complete the quest");
					player.getPackets()
							.sendIComponentText(275, 18,
									"The Monk Of Entrana removes everything in your inventory.");
					player.getPackets().sendIComponentText(275, 19, "");
					player.getPackets().sendIComponentText(275, 20, "");
				} else if (player.spokeToWarrior == true
						&& player.spokeToShamus == true
						&& player.spokeToMonk == true) {
					player.getInterfaceManager().sendInterface(275);
					player.getPackets().sendIComponentText(275, 1,
							"Lost-City Quest");
					player.getPackets()
							.sendIComponentText(275, 10,
									"The other side of Entrana is a ladder which leads to a cave");
					player.getPackets()
							.sendIComponentText(275, 11,
									"I should go down the ladder and search for the dramen tree.");
					player.getPackets()
							.sendIComponentText(275, 12,
									"In order to chop the dramen tree I must have a axe.");
					player.getPackets().sendIComponentText(275, 13,
							"The zombies must drop a axe of some sort.");
					player.getPackets().sendIComponentText(275, 14,
							"-=-Tips-=-");
					player.getPackets()
							.sendIComponentText(275, 15,
									"Use the 'Quests & Minigames' teleport at home to start the quest.");
					player.getPackets()
							.sendIComponentText(275, 16,
									"The lodestone works, remember to take full use of it.");
					player.getPackets()
							.sendIComponentText(275, 17,
									"You will need the skills required to complete the quest");
					player.getPackets()
							.sendIComponentText(275, 18,
									"The Monk Of Entrana removes everything in your inventory.");
					player.getPackets().sendIComponentText(275, 19, "");
					player.getPackets().sendIComponentText(275, 20, "");
				} else if (player.spokeToWarrior == true
						&& player.spokeToShamus == true
						&& player.spokeToMonk == true && player.lostCity == 1) {
					player.getInterfaceManager().sendInterface(275);
					player.getPackets().sendIComponentText(275, 1,
							"Lost-City Quest");
					player.getPackets().sendIComponentText(275, 10, "");
					player.getPackets().sendIComponentText(275, 11, "");
					player.getPackets().sendIComponentText(275, 12,
							"Congratulations Quest Complete!");
					player.getPackets().sendIComponentText(275, 13, "");
					player.getPackets().sendIComponentText(275, 14, "");
					player.getPackets().sendIComponentText(275, 15, "");
					player.getPackets().sendIComponentText(275, 16, "");
					player.getPackets().sendIComponentText(275, 17, "");
					player.getPackets().sendIComponentText(275, 18, "");
					player.getPackets().sendIComponentText(275, 19, "");
					player.getPackets().sendIComponentText(275, 20, "");
				}
			}
		}
		if (interfaceId == 397) {
			ConstructFurniture.handleButtons(player, componentId);
		}
		if (interfaceId == 1066) {
			Subscribe.handleButtons(player, componentId);
		}
		if (interfaceId == 297) {
			SandwichLadyHandler.handleButtons(player, componentId);
		}

		if (interfaceId == 1253 || interfaceId == 1252 || interfaceId == 1139) {
			player.getSqueal().processClick(packetId, interfaceId, componentId,
					slotId, slotId2);
			return;
		}

		if (interfaceId == 1123) {
			CrystalSystem.handleButtons(player, componentId);
		}
		if (interfaceId == 1143) {
			if (componentId == 103) {
				player.closeInterfaces();
			}
		}
		if (interfaceId == 506) {
			RunePortal.handleButtons(player, componentId);
		}

		if (interfaceId == 1072) {
			ArtisanWorkshop.handleButtons(player, componentId);
		}

		if (interfaceId == 1264) {
			if (componentId == 0) {
				player.closeInterfaces();
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 0);
			}
		}
		if (interfaceId == 548 || interfaceId == 746) {
			if ((interfaceId == 548 && componentId == 148)
					|| (interfaceId == 746 && componentId == 199)) {

			} else if ((interfaceId == 548 && componentId == 17)
					|| (interfaceId == 746 && componentId == 54)) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getSkills().switchXPDisplay();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getSkills().switchXPPopup();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getSkills().setupXPCounter();
			} else if ((interfaceId == 746 && componentId == 207)
					|| (interfaceId == 548 && componentId == 159)) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					if (player.getInterfaceManager().containsScreenInter()) {
						player.getPackets()
								.sendGameMessage(
										"Please finish what you're doing before opening the price checker.");
						return;
					}
					player.stopAll();
					player.getPriceCheckManager().openPriceCheck();
				}
			}
			if (ExchangeHandler.handleButtons(player, interfaceId, componentId)) {
				return;
			}
			if (interfaceId == 746 && componentId == 89 ||
					interfaceId == 548 && componentId == 75)
				player.getCurrencyPouch().openInterface();
		} else if (interfaceId == 107) {
			ExchangeHandler.sendSellItem(player, slotId2, slotId, componentId);
			return;
		}
		if (ExchangeHandler.handleButtons(player, interfaceId, componentId)) {
			return;
		}
		
		if (interfaceId == 746) {
			if (componentId == 207) {
				if (packetId == 5) {
					player.sm("Your pouch currently contains " + Utils.formatNumber(player.getPouch().getAmount()) + " coins.");
				}
				if (packetId == 14) { 
					player.getPackets().sendRunScript(5557, 1);
					player.getPackets().sendRunScript(5560, player.getPouch().getAmount(), "n");
				} else if (packetId == 67) {
					if (player.getTrade().isTrading()) {
						player.sm("Please finish trading before withdrawing money from your pouch.");
					} else {
					player.getTemporaryAttributtes().put("money_remove", Boolean.TRUE);	
					player.getPackets().sendRunScript(108, new Object[] { "Money pouch currently contains: "
							+ Utils.formatNumber(player.getPouch().getAmount()) + " coins"});
					
					}
				}
			}
		} else if (interfaceId == 548) {
			if (componentId == 159) {
				if (packetId == 14) {
					player.getPackets().sendRunScript(5557, 1);
					player.getPackets().sendRunScript(5560, player.getPouch().getAmount(), "n");
				} else if (packetId == 67) {
					player.getTemporaryAttributtes().put("money_remove", Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			}
		} else if (interfaceId == 182) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6 || componentId == 13)
				if (!player.hasFinished())
					player.logout(componentId == 6);
		} else if (interfaceId == 1165) {
			// if (componentId == 22)
			// Summoning.closeDreadnipInterface(player);
		} else if (interfaceId == 880) {
			if (componentId >= 7 && componentId <= 19)
				Familiar.setLeftclickOption(player, (componentId - 7) / 2);
			else if (componentId == 21)
				Familiar.confirmLeftOption(player);
			else if (componentId == 25)
				Familiar.setLeftclickOption(player, 7);
		} else if (interfaceId == 1089) {
			if (componentId == 30)
				player.getTemporaryAttributtes().put("clanflagselection",
						slotId);
			else if (componentId == 26) {
				Integer flag = (Integer) player.getTemporaryAttributtes()
						.remove("clanflagselection");
				player.stopAll();
				if (flag != null)
					ClansManager.setClanFlagInterface(player, flag);
			}
		} else if (interfaceId == 1096) {
			if (componentId == 41)
				ClansManager.viewClammateDetails(player, slotId);
			else if (componentId == 94)
				ClansManager.switchGuestsInChatCanEnterInterface(player);
			else if (componentId == 95)
				ClansManager.switchGuestsInChatCanTalkInterface(player);
			else if (componentId == 96)
				ClansManager.switchRecruitingInterface(player);
			else if (componentId == 97)
				ClansManager.switchClanTimeInterface(player);
			else if (componentId == 124)
				ClansManager.openClanMottifInterface(player);
			else if (componentId == 131)
				ClansManager.openClanMottoInterface(player);
			else if (componentId == 240)
				ClansManager.setTimeZoneInterface(player, -720 + slotId * 10);
			else if (componentId == 262)
				player.getTemporaryAttributtes().put("editclanmatejob", slotId);
			else if (componentId == 276)
				player.getTemporaryAttributtes()
						.put("editclanmaterank", slotId);
			else if (componentId == 309)
				ClansManager.kickClanmate(player);
			else if (componentId == 318)
				ClansManager.saveClanmateDetails(player);
			else if (componentId == 290)
				ClansManager.setWorldIdInterface(player, slotId);
			else if (componentId == 297)
				ClansManager.openForumThreadInterface(player);
			else if (componentId == 346)
				ClansManager.openNationalFlagInterface(player);
			else if (componentId == 113)
				ClansManager.showClanSettingsClanMates(player);
			else if (componentId == 120)
				ClansManager.showClanSettingsSettings(player);
			else if (componentId == 386)
				ClansManager.showClanSettingsPermissions(player);
			else if (componentId >= 395 && componentId <= 475) {
				int selectedRank = (componentId - 395) / 8;
				if (selectedRank == 10)
					selectedRank = 125;
				else if (selectedRank > 5)
					selectedRank = 100 + selectedRank - 6;
				ClansManager.selectPermissionRank(player, selectedRank);
			} else if (componentId == 489)
				ClansManager.selectPermissionTab(player, 1);
			else if (componentId == 498)
				ClansManager.selectPermissionTab(player, 2);
			else if (componentId == 506)
				ClansManager.selectPermissionTab(player, 3);
			else if (componentId == 514)
				ClansManager.selectPermissionTab(player, 4);
			else if (componentId == 522)
				ClansManager.selectPermissionTab(player, 5);
		} else if (interfaceId == 1105) {
			if (componentId == 63 || componentId == 66)
				ClansManager.setClanMottifTextureInterface(player,
						componentId == 66, slotId);
			else if (componentId == 189) {
				player.getPackets().sendHideIComponent(1105, 35, false);
				player.getPackets().sendHideIComponent(1105, 36, false);
				player.getPackets().sendHideIComponent(1105, 37, false);
				player.getPackets().sendHideIComponent(1105, 37, false);
				player.getPackets().sendHideIComponent(1105, 38, false);
				player.getPackets().sendHideIComponent(1105, 39, false);
				player.getPackets().sendHideIComponent(1105, 43, false);
				player.getPackets().sendHideIComponent(1105, 44, false);
				player.getPackets().sendHideIComponent(1105, 45, false);
			} else if (componentId == 177) {
				player.getPackets().sendHideIComponent(1105, 62, false);
				player.getPackets().sendHideIComponent(1105, 63, false);
				player.getPackets().sendHideIComponent(1105, 69, false);
			} else if (componentId == 35)
				ClansManager.openSetMottifColor(player, 0);
			else if (componentId == 80)
				ClansManager.openSetMottifColor(player, 1);
			else if (componentId == 92)
				ClansManager.openSetMottifColor(player, 2);
			else if (componentId == 104)
				ClansManager.openSetMottifColor(player, 3);// try
			else if (componentId == 120)
				player.stopAll();
		} else if (interfaceId == 1157) {
			RankLend.handleButtons(player, componentId);
		} else if (interfaceId == 1110) {
			if (componentId == 82)
				ClansManager.joinClanChatChannel(player);
			else if (componentId == 75)
				ClansManager.openClanDetails(player);
			else if (componentId == 78)
				ClansManager.openClanSettings(player);
			else if (componentId == 91)
				ClansManager.joinGuestClanChat(player);
			else if (componentId == 95)
				ClansManager.banPlayer(player);
			else if (componentId == 99)
				ClansManager.unbanPlayer(player);
			else if (componentId == 11)
				ClansManager.unbanPlayer(player, slotId);
			else if (componentId == 109)
				ClansManager.leaveClan(player);
			else if (interfaceId == 1265) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
				if (shop == null)
					return;
				/*Integer slot = (Integer) player.getTemporaryAttributtes().get(
						"ShopSelectedSlot");*/
				boolean isBuying = (boolean) player.getTemporaryAttributtes()
						.get("shop_buying") == true;
				/*int amount = (int) player.getTemporaryAttributtes().get(
						"amount_shop");*/
				if (componentId == 20) {
					player.getTemporaryAttributtes().put("ShopSelectedSlot",
							slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendInfo(player, slotId, isBuying, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.handleShop(player, slotId, 1);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.handleShop(player, slotId, 5);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.handleShop(player, slotId, 10);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
						shop.handleShop(player, slotId, 50);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
						shop.handleShop(player, slotId, 500);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
						shop.handleShop(player, slotId, 10000);
				} else if (componentId == 201) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 208) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 15) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 214) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 217) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 220) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 211) {
					player.getPackets().sendGameMessage(
							"Right click instead to buy/sell");
				} else if (componentId == 29) {
					player.getTemporaryAttributtes().put("shop_buying", false);
					player.getTemporaryAttributtes().put("amount_shop", 1);
				} else if (componentId == 28) {
					player.getTemporaryAttributtes().put("shop_buying", true);
					player.getTemporaryAttributtes().put("amount_shop", 1);
				}
			} else if (interfaceId == 1266) {
				if (componentId == 0) {
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
							player.getTemporaryAttributtes().put("x_amount_shop", slotId);
							player.getPackets().sendRunScript(108,
									new Object[] { "Enter the amount you wish to sell:" });		
					}
					else {
						Shop shop = (Shop) player.getTemporaryAttributtes()
								.get("Shop");
						if (shop == null)
							return;
						if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
							shop.sendValue(player, slotId);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
							shop.sell(player, slotId, 1,false);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
							shop.sell(player, slotId, 5,false);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
							shop.sell(player, slotId, 10,false);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
							shop.sell(player, slotId, 50,false);
						else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
							player.sm("");
					}
				}
			}
		} else if (interfaceId == 662) {
			if (player.getFamiliar() == null) {
				if (player.getPet() == null) {
					return;
				}
				if (componentId == 49)
					player.getPet().call();
				else if (componentId == 51)
					player.getDialogueManager().startDialogue("DismissD");
				return;
			}
			if (componentId == 49)
				player.getFamiliar().call();
			else if (componentId == 51)
				player.getDialogueManager().startDialogue("DismissD");
			else if (componentId == 67)
				player.getFamiliar().takeBob();
			else if (componentId == 69)
				player.getFamiliar().renewFamiliar();
			else if (componentId == 74) {
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
					player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().hasSpecialOn())
					player.getFamiliar().submitSpecial(player);
			}
		} else if (interfaceId == 747) {
			if (componentId == 8) {
				Familiar.selectLeftOption(player);
			} else if (player.getPet() != null) {
				if (componentId == 11 || componentId == 20) {
					player.getPet().call();
				} else if (componentId == 12 || componentId == 21) {
					player.getDialogueManager().startDialogue("DismissD");
				} else if (componentId == 10 || componentId == 19) {
					player.getPet().sendFollowerDetails();
				}
			} else if (player.getFamiliar() != null) {
				if (componentId == 11 || componentId == 20)
					player.getFamiliar().call();
				else if (componentId == 12 || componentId == 21)
					player.getDialogueManager().startDialogue("DismissD");
				else if (componentId == 13 || componentId == 22)
					player.getFamiliar().takeBob();
				else if (componentId == 14 || componentId == 23)
					player.getFamiliar().renewFamiliar();
				else if (componentId == 19 || componentId == 10)
					player.getFamiliar().sendFollowerDetails();
				else if (componentId == 18) {
					if (player.getFamiliar().getSpecialAttack() == SpecialAttack.CLICK)
						player.getFamiliar().setSpecial(true);
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(player);
				}
			}

		} else if (interfaceId == 309)
			PlayerLook.handleHairdresserSalonButtons(player, componentId,
					slotId);
		else if (interfaceId == 729)
			PlayerLook.handleThessaliasMakeOverButtons(player, componentId,
					slotId);
		else if (interfaceId == 187) {
			if (componentId == 1) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getMusicsManager().playAnotherMusic(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getMusicsManager().sendHint(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getMusicsManager().addToPlayList(slotId / 2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getMusicsManager().removeFromPlayList(slotId / 2);
			} else if (componentId == 4)
				player.getMusicsManager().addPlayingMusicToPlayList();
			else if (componentId == 10)
				player.getMusicsManager().switchPlayListOn();
			else if (componentId == 11)
				player.getMusicsManager().clearPlayList();
			else if (componentId == 13)
				player.getMusicsManager().switchShuffleOn();
		} else if (interfaceId == 275) {
			if (componentId == 14) {
				player.getPackets().sendOpenURL(Settings.WEBSITE_LINK);
			}
		} else if ((interfaceId == 590 && componentId == 8) || interfaceId == 464) {
			player.getEmotesManager().useBookEmote(interfaceId == 464 ? componentId : EmotesManager.getId(slotId, packetId));
		} else if (interfaceId == 192) {
			if (componentId == 2)
				player.getCombatDefinitions().switchDefensiveCasting();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 11)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId == 13)
				player.getCombatDefinitions().switchShowSkillSpells();
			else if (componentId >= 15 & componentId <= 17)
				player.getCombatDefinitions()
						.setSortSpellBook(componentId - 15);
			else
				Magic.processNormalSpell(player, componentId, packetId);
		} else if (interfaceId == 334) {
			if (componentId == 22)
				player.closeInterfaces();
			else if (componentId == 21)
				player.getTrade().accept(false);
		} else if (interfaceId == 335) {
			if (componentId == 18)
				player.getTrade().accept(true);
			else if (componentId == 20)
				player.closeInterfaces();
			else if (componentId == 63) {
				if (player.getPouch().getAmount() <= 0) {
					player.sm("You don't have any money to withdraw.");
					return;
				}
				player.getTemporaryAttributtes().clear();
				player.getTemporaryAttributtes().put("trade_moneypouch", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Coins: " + Utils.formatNumber(player.getPouch().getAmount())});
			} else if (componentId == 64) {
				if (player.getCurrencyPouch().get100MTicket() <= 0) {
					player.sm("You don't have any 100M Tickets to withdraw.");
					return;
				}
				player.getTemporaryAttributtes().clear();
				player.getTemporaryAttributtes().put("trade_100m", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"100M Tickets: " + Utils.formatNumber(player.getCurrencyPouch().get100MTicket())});
			} else if (componentId == 65) {
				if (player.getCurrencyPouch().getInvasionTokens() <= 0) {
					player.sm("You don't have any Invasion Tokens to withdraw.");
					return;
				}
				player.getTemporaryAttributtes().clear();
				player.getTemporaryAttributtes().put("trade_invasion", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Invasion Tokens: " + Utils.formatNumber(player.getCurrencyPouch().getInvasionTokens())});
			} else if (componentId == 66) {
				if (player.getCurrencyPouch().getVoteTickets() <= 0) {
					player.sm("You don't have any Vote Tickets to withdraw.");
					return;
				}
				player.getTemporaryAttributtes().clear();
				player.getTemporaryAttributtes().put("trade_vote", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Vote Tickets: " + Utils.formatNumber(player.getCurrencyPouch().getVoteTickets())});
			} else if (componentId == 67) {
				if (player.getCurrencyPouch().getEradicatedSeals() <= 0) {
					player.sm("You don't have any Eradicated Seals to withdraw.");
					return;
				}
				player.getTemporaryAttributtes().clear();
				player.getTemporaryAttributtes().put("trade_seals", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] 
				{"Eradicated Seals: " + Utils.formatNumber(player.getCurrencyPouch().getEradicatedSeals())});
			} else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().removeItem(slotId, 1, false, null);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().removeItem(slotId, 5, false, null);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().removeItem(slotId, 10, false, null);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().removeItem(slotId, Integer.MAX_VALUE, false, null);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("trade_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("trade_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, false);
			} else if (componentId == 35) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().sendValue(slotId, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getTrade().sendExamine(slotId, true);
			}

		} else if (interfaceId == 336) { 
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getTrade().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getTrade().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getTrade().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getTrade().addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("trade_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("trade_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getTrade().sendValue(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 300) {
			ForgingInterface.handleIComponents(player, componentId);
		} else if (interfaceId == 206) {
			if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().removeItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("pc_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			}
		} else if (interfaceId == 672) {
			if (componentId == 16) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					Summoning.createPouch(player, slotId2, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					Summoning.createPouch(player, slotId2, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					Summoning.createPouch(player, slotId2, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					Summoning.createPouch(player, slotId2, Summoning.amountCanCreate(player, slotId2, 28));
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					Summoning.createPouch(player, slotId2, Summoning.amountCanCreate(player, slotId2, 28));// x
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET) {
					player.getPackets().sendGameMessage(
							"You currently need "
									+ ItemDefinitions.getItemDefinitions(
											slotId2)
											.getCreateItemRequirements());
				}
			}
		} else if (interfaceId == 207) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getPriceCheckManager().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getPriceCheckManager().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getPriceCheckManager().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getPriceCheckManager().addItem(slotId,
							Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("pc_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("pc_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 665) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().addItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
							.addItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bob_isRemove");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 671) {
			if (player.getFamiliar() == null
					|| player.getFamiliar().getBob() == null)
				return;
			if (componentId == 27) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFamiliar().getBob().removeItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFamiliar().getBob()
							.removeItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bob_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bob_isRemove",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				}
			} else if (componentId == 29)
				player.getFamiliar().takeBob();
		} else if (interfaceId == 916) {
			SkillsDialogue.handleSetQuantityButtons(player, componentId);
		} else if (interfaceId == 193) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId >= 9 && componentId <= 11)
				player.getCombatDefinitions().setSortSpellBook(componentId - 9);
			else if (componentId == 18)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processAncientSpell(player, componentId, packetId);
		} else if (interfaceId == 430) {
			if (componentId == 5)
				player.getCombatDefinitions().switchShowCombatSpells();
			else if (componentId == 7)
				player.getCombatDefinitions().switchShowTeleportSkillSpells();
			else if (componentId == 9)
				player.getCombatDefinitions().switchShowMiscallaneousSpells();
			else if (componentId >= 11 & componentId <= 13)
				player.getCombatDefinitions()
						.setSortSpellBook(componentId - 11);
			else if (componentId == 20)
				player.getCombatDefinitions().switchDefensiveCasting();
			else
				Magic.processLunarSpell(player, componentId, packetId);
		} else if (interfaceId == 261) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 22) {
				if (player.getInterfaceManager().containsScreenInter()) {
					player.getPackets()
							.sendGameMessage(
									"Please close the interface you have open before setting your graphic options.");
					return;
				}
				player.stopAll();
				player.getInterfaceManager().sendInterface(742);
			} else if (componentId == 12)
				player.switchAllowChatEffects();
			else if (componentId == 13) { // chat setup
				player.getInterfaceManager().sendSettings(982);
			} else if (componentId == 14)
				player.switchMouseButtons();
			else if (componentId == 24) // audio options
				player.getInterfaceManager().sendSettings(429);
			else if (componentId == 26)
				AdventurersLog.open(player);
		} else if (interfaceId == 429) {
			if (componentId == 18)
				player.getInterfaceManager().sendSettings();
		} else if (interfaceId == 3002) {
			if (componentId == 7)
				player.getInterfaceManager().closeScreenInterface();
			else if (componentId == 8)
				player.getPackets().sendOpenURL("http://www.eradicationx.com/vote");
			else if (componentId == 9)
				player.getPackets().sendOpenURL("http://rsps-pay.com/store.php?id=2");
			else if (componentId == 10)
				player.getPackets().sendOpenURL("http://eradicationx.com/forums/showthread.php?2112-Updates-3-26-15-Boss-Pets-and-Loot-Beams!&p=9415#post9415");
		} else if (interfaceId == 982) {
			if (componentId == 5)
				player.getInterfaceManager().sendSettings();
			else if (componentId == 41)
				player.setPrivateChatSetup(player.getPrivateChatSetup() == 0 ? 1
						: 0);
			else if (componentId >= 17 && componentId <= 36)
				player.setClanChatSetup(componentId - 17);
			else if (componentId >= 49 && componentId <= 66)
				player.setPrivateChatSetup(componentId - 48);
			else if (componentId >= 72 && componentId <= 91)
				player.setFriendChatSetup(componentId - 72);
		} else if (interfaceId == 271) {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					if (componentId == 8 || componentId == 42)
						player.getPrayer().switchPrayer(slotId);

					else if (componentId == 43
							&& player.getPrayer().isUsingQuickPrayer())
						player.getPrayer().switchSettingQuickPrayer();
				}
			});
		} else if (interfaceId == 320) {
			player.stopAll();
			int lvlupSkill = -1;
			int skillMenu = -1;
			switch (componentId) {
			case 150: // Attack
				skillMenu = 1;
				if (player.getTemporaryAttributtes().remove("leveledUp[0]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 1);
				} else {
					lvlupSkill = 0;
					player.getPackets().sendConfig(1230, 10);
				}
				break;
			case 9: // Strength
				skillMenu = 2;
				if (player.getTemporaryAttributtes().remove("leveledUp[2]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 2);
				} else {
					lvlupSkill = 2;
					player.getPackets().sendConfig(1230, 20);
				}
				break;
			case 22: // Defence
				skillMenu = 5;
				if (player.getTemporaryAttributtes().remove("leveledUp[1]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 5);
				} else {
					lvlupSkill = 1;
					player.getPackets().sendConfig(1230, 40);
				}
				break;
			case 40: // Ranged
				skillMenu = 3;
				if (player.getTemporaryAttributtes().remove("leveledUp[4]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 3);
				} else {
					lvlupSkill = 4;
					player.getPackets().sendConfig(1230, 30);
				}
				break;
			case 58: // Prayer
				if (player.getTemporaryAttributtes().remove("leveledUp[5]") != Boolean.TRUE) {
					skillMenu = 7;
					player.getPackets().sendConfig(965, 7);
				} else {
					lvlupSkill = 5;
					player.getPackets().sendConfig(1230, 60);
				}
				break;
			case 71: // Magic
				if (player.getTemporaryAttributtes().remove("leveledUp[6]") != Boolean.TRUE) {
					skillMenu = 4;
					player.getPackets().sendConfig(965, 4);
				} else {
					lvlupSkill = 6;
					player.getPackets().sendConfig(1230, 33);
				}
				break;
			case 84: // Runecrafting
				if (player.getTemporaryAttributtes().remove("leveledUp[20]") != Boolean.TRUE) {
					skillMenu = 12;
					player.getPackets().sendConfig(965, 12);
				} else {
					lvlupSkill = 20;
					player.getPackets().sendConfig(1230, 100);
				}
				break;
			case 102: // Construction
				skillMenu = 22;
				if (player.getTemporaryAttributtes().remove("leveledUp[21]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 22);
				} else {
					lvlupSkill = 21;
					player.getPackets().sendConfig(1230, 698);
				}
				break;
			case 145: // Hitpoints
				skillMenu = 6;
				if (player.getTemporaryAttributtes().remove("leveledUp[3]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 6);
				} else {
					lvlupSkill = 3;
					player.getPackets().sendConfig(1230, 50);
				}
				break;
			case 15: // Agility
				skillMenu = 8;
				if (player.getTemporaryAttributtes().remove("leveledUp[16]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 8);
				} else {
					lvlupSkill = 16;
					player.getPackets().sendConfig(1230, 65);
				}
				break;
			case 28: // Herblore
				skillMenu = 9;
				if (player.getTemporaryAttributtes().remove("leveledUp[15]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 9);
				} else {
					lvlupSkill = 15;
					player.getPackets().sendConfig(1230, 75);
				}
				break;
			case 46: // Thieving
				skillMenu = 10;
				if (player.getTemporaryAttributtes().remove("leveledUp[17]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 10);
				} else {
					lvlupSkill = 17;
					player.getPackets().sendConfig(1230, 80);
				}
				break;
			case 64: // Crafting
				skillMenu = 11;
				if (player.getTemporaryAttributtes().remove("leveledUp[12]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 11);
				} else {
					lvlupSkill = 12;
					player.getPackets().sendConfig(1230, 90);
				}
				break;
			case 77: // Fletching
				skillMenu = 19;
				if (player.getTemporaryAttributtes().remove("leveledUp[9]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 19);
				} else {
					lvlupSkill = 9;
					player.getPackets().sendConfig(1230, 665);
				}
				break;
			case 90: // Slayer
				skillMenu = 20;
				if (player.getTemporaryAttributtes().remove("leveledUp[18]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 20);
				} else {
					lvlupSkill = 18;
					player.getPackets().sendConfig(1230, 673);
				}
				break;
			case 108: // Hunter
				skillMenu = 23;
				if (player.getTemporaryAttributtes().remove("leveledUp[22]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 23);
				} else {
					lvlupSkill = 22;
					player.getPackets().sendConfig(1230, 689);
				}
				break;
			case 140: // Mining
				skillMenu = 13;
				if (player.getTemporaryAttributtes().remove("leveledUp[14]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 13);
				} else {
					lvlupSkill = 14;
					player.getPackets().sendConfig(1230, 110);
				}
				break;
			case 135: // Smithing
				skillMenu = 14;
				if (player.getTemporaryAttributtes().remove("leveledUp[13]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 14);
				} else {
					lvlupSkill = 13;
					player.getPackets().sendConfig(1230, 115);
				}
				break;
			case 34: // Fishing
				skillMenu = 15;
				if (player.getTemporaryAttributtes().remove("leveledUp[10]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 15);
				} else {
					lvlupSkill = 10;
					player.getPackets().sendConfig(1230, 120);
				}
				break;
			case 52: // Cooking
				skillMenu = 16;
				if (player.getTemporaryAttributtes().remove("leveledUp[7]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 16);
				} else {
					lvlupSkill = 7;
					player.getPackets().sendConfig(1230, 641);
				}
				break;
			case 130: // Firemaking
				skillMenu = 17;
				if (player.getTemporaryAttributtes().remove("leveledUp[11]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 17);
				} else {
					lvlupSkill = 11;
					player.getPackets().sendConfig(1230, 649);
				}
				break;
			case 125: // Woodcutting
				skillMenu = 18;
				if (player.getTemporaryAttributtes().remove("leveledUp[8]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 18);
				} else {
					lvlupSkill = 8;
					player.getPackets().sendConfig(1230, 660);
				}
				break;
			case 96: // Farming
				skillMenu = 21;
				if (player.getTemporaryAttributtes().remove("leveledUp[19]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 21);
				} else {
					lvlupSkill = 19;
					player.getPackets().sendConfig(1230, 681);
				}
				break;
			case 114: // Summoning
				skillMenu = 24;
				if (player.getTemporaryAttributtes().remove("leveledUp[23]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 24);
				} else {
					lvlupSkill = 23;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			case 120: // Dung
				skillMenu = 25;
				if (player.getTemporaryAttributtes().remove("leveledUp[24]") != Boolean.TRUE) {
					player.getPackets().sendConfig(965, 25);
				} else {
					lvlupSkill = 24;
					player.getPackets().sendConfig(1230, 705);
				}
				break;
			}

			/*
			 * player.getInterfaceManager().sendInterface( lvlupSkill != -1 ?
			 * 741 : 499);
			 */
			player.getInterfaceManager().sendScreenInterface(317, 1218);
			player.getPackets().sendInterface(false, 1218, 1, 1217); // seems to
																		// fix
			if (lvlupSkill != -1)
				LevelUp.switchFlash(player, lvlupSkill, false);
			if (skillMenu != -1)
				player.getTemporaryAttributtes().put("skillMenu", skillMenu);
		} else if (interfaceId == 1218) {
			if ((componentId >= 33 && componentId <= 55) || componentId == 120
					|| componentId == 151 || componentId == 189)
				player.getPackets().sendInterface(false, 1218, 1, 1217); // seems
				player.getInterfaceManager().sendCurrencyPouch();// to
																			// fix
		} else if (interfaceId == 499) {
			int skillMenu = -1;
			if (player.getTemporaryAttributtes().get("skillMenu") != null)
				skillMenu = (Integer) player.getTemporaryAttributtes().get(
						"skillMenu");
			if (componentId >= 10 && componentId <= 25)
				player.getPackets().sendConfig(965,
						((componentId - 10) * 1024) + skillMenu);
			else if (componentId == 29)
				// close inter
				player.stopAll();

		} else if (interfaceId == 387) {
			if (player.getInterfaceManager().containsInventoryInter())
				return;
			if (componentId == 6) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int hatId = player.getEquipment().getHatId();
					if (hatId == 24437 || hatId == 24439 || hatId == 24440
							|| hatId == 24441) {
						player.getDialogueManager().startDialogue(
								"FlamingSkull",
								player.getEquipment().getItem(
										Equipment.SLOT_HAT), -1);
						return;
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_HAT);
			} else if (componentId == 41) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(1178);
			} else if (componentId == 9) {

				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 27344 || capeId == 27345
							|| capeId == 27346 || capeId == 27347
							|| capeId == 27348 || capeId == 27349
							|| capeId == 27350 || capeId == 27355) {
						player.getDialogueManager().startDialogue("TENBColor");
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771 || capeId == 28013) {
						player.getSkills().restoreSummoning();
						player.setNextAnimation(new Animation(8502));
						player.setNextGraphics(new Graphics(1308));
						player.getPackets()
								.sendGameMessage(
										"You restored your Summoning points with your awesome cape!",
										true);
					}
				}
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20769 || capeId == 20771 || capeId == 28013)
						SkillCapeCustomizer.startCustomizing(player, capeId);
					if (capeId == 28013)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int capeId = player.getEquipment().getCapeId();
					if (capeId == 20767)
						SkillCapeCustomizer.startCustomizing(player, capeId);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			} else if (componentId == 12) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3087, 3496, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					} else if (amuletId == 1704 || amuletId == 10352)
						player.getPackets()
								.sendGameMessage(
										"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(2918, 3176, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3105, 3251, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					int amuletId = player.getEquipment().getAmuletId();
					if (amuletId <= 1712 && amuletId >= 1706
							|| amuletId >= 10354 && amuletId <= 10361) {
						if (Magic.sendItemTeleportSpell(player, true,
								Transportation.EMOTE, Transportation.GFX, 4,
								new WorldTile(3293, 3163, 0))) {
							Item amulet = player.getEquipment().getItem(
									Equipment.SLOT_AMULET);
							if (amulet != null) {
								amulet.setId(amulet.getId() - 2);
								player.getEquipment().refresh(
										Equipment.SLOT_AMULET);
							}
						}
					}
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
			} else if (componentId == 15) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					int weaponId = player.getEquipment().getWeaponId();
					if (weaponId == 15484)
						player.getInterfaceManager().gazeOrbOfOculus();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
			} else if (componentId == 18)
				ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
			else if (componentId == 21)
				ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
			else if (componentId == 24)
				ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
			else if (componentId == 27)
				ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
			else if (componentId == 30)
				ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
			else if (componentId == 33)
				ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
			else if (componentId == 36)
				ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
			else if (componentId == 45) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					if (player.getAuraManager().isActivated()) {
						player.sm("Your aura is currently active. Please deactivate it first.");
						return; 
						}
					ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
					player.getAuraManager().removeAura();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getEquipment().sendExamine(Equipment.SLOT_AURA);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getAuraManager().activate();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getAuraManager().sendAuraRemainingTime();
			} else if (componentId == 40) {
				player.stopAll();
				player.getInterfaceManager().sendInterface(17);
				sendItemsKeptOnDeath(player, true);
				player.sm("Note: You only lose your items in PVP areas. The items shown will only occur when you're in PVP.");
			} else if (componentId == 37) {
				openEquipmentBonuses(player, false);
			}
		} else if (interfaceId == 17) {
			if (componentId == 28)
				player.sm("You don't lose items outside of wilderness.");
		} else if (interfaceId == 1265) {
			Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
			if (shop == null)
				return;
			Integer slot = (Integer) player.getTemporaryAttributtes().get(
					"ShopSelectedSlot");
			Integer amount = (Integer) player.getTemporaryAttributtes().get(
					"amount_shop");
			boolean isBuying = player.getTemporaryAttributtes().get(
					"shop_buying") != null;
			if (componentId == 20) {
				player.getTemporaryAttributtes()
						.put("ShopSelectedSlot", slotId);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					shop.sendInfo(player, slotId, isBuying, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					shop.handleShop(player, slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					shop.handleShop(player, slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					shop.handleShop(player, slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					shop.handleShop(player, slotId, 50);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					shop.handleShop(player, slotId, 10000);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					shop.handleShop(player, slotId, 500);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					shop.sendExamine(player, slotId);
			} else if (componentId == 255) {
				if (player.shopAmount != -1 && player.shopSlotId != -1) {
					shop.buy(player, slot, amount);
				} else 
					player.sm("You must select an item first.");
			} else if (componentId == 256) {
				if (player.shopAmount != -1 && player.shopSlotId != -1) {
				player.getTemporaryAttributtes().put("shopamount", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Enter an amount" });
				} else 
					player.sm("You must select an item first.");
			} else if (componentId == 201) {
				if (slot == null)
					return;
				if (isBuying)
					shop.buy(player, slot, shop.getAmount());
				else {
					shop.sell(player, slot, shop.getAmount(), false);
					player.getPackets().sendConfig(2563, 0);
					player.getPackets().sendConfig(2565, 1); // this is to
																// update the
																// tab.
				}
				shop.setAmount(player, 1);
			} else if (componentId == 201) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 208) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 15) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 214) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 217) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 220) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 211) {
				player.getPackets().sendGameMessage(
						"Right click instead to buy/sell");
			} else if (componentId == 29) {
				player.getPackets().sendConfig(2561, 93);
				player.getTemporaryAttributtes().remove("shop_buying");
				shop.setAmount(player, 1);
			} else if (componentId == 28) {
				player.getTemporaryAttributtes().put("shop_buying", true);
				shop.setAmount(player, 1);
			}
		} else if (interfaceId == 1266) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					player.getTemporaryAttributtes().put("x_amount_shop", slotId);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter the amount you wish to sell:" });		
				} else {
					Shop shop = (Shop) player.getTemporaryAttributtes().get(
							"Shop");
					if (shop == null)
						return;
					player.getPackets().sendConfig(2563, slotId);
					if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
						shop.sendValue(player, slotId);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
						shop.sell(player, slotId, 1,false);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
						shop.sell(player, slotId, 5,false);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
						shop.sell(player, slotId, 10,false);
					else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
						shop.sell(player, slotId, 50,false);
					}
				}	
			}
		} else if (interfaceId == 640) {
			if (componentId == 18 || componentId == 22) {
				player.getTemporaryAttributtes().put("WillDuelFriendly", true);
				player.getPackets().sendConfig(283, 67108864);
			} else if (componentId == 19 || componentId == 21) {
				player.getTemporaryAttributtes().put("WillDuelFriendly", false);
				player.getPackets().sendConfig(283, 134217728);
			} else if (componentId == 20) {
				DuelControler.challenge(player);
			}
		} else if (interfaceId == 650) {
			if (componentId == 15) {
				player.stopAll();
				player.setNextWorldTile(new WorldTile(2974, 4384, player
						.getPlane()));
				player.getControlerManager().startControler(
						"CorpBeastControler");
			} else if (componentId == 16)
				player.closeInterfaces();
		} else if (interfaceId == 667) {
			if (componentId == 9 && slotId == 0) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_HAT);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 1) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_CAPE);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 2) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_AMULET);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 3) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_WEAPON);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 4) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_CHEST);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 5) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_SHIELD);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 7) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_LEGS);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 9) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_HANDS);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 10) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_FEET);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 12) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_RING);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 13) {
				ButtonHandler.sendRemove(player, Equipment.SLOT_ARROWS);
				ButtonHandler.refreshEquipBonuses(player);
			} else if (componentId == 9 && slotId == 14) {
				if (player.getAuraManager().isActivated()) {
					player.sm("Your aura is currently active. Please deactivate it first.");
					return; 
				}
				ButtonHandler.sendRemove(player, Equipment.SLOT_AURA);
				ButtonHandler.refreshEquipBonuses(player);
			}
			if (componentId == 14) {
				if (slotId >= 14)
					return;
				Item item = player.getEquipment().getItem(slotId);
				if (item == null)
					return;
				if (packetId == 3)
					player.getPackets().sendGameMessage(
							ItemExamines.getExamine(item));
				else if (packetId == 216) {
					sendRemove(player, slotId);
					ButtonHandler.refreshEquipBonuses(player);
				}
			} else if (componentId == 46 && player.getTemporaryAttributtes().remove("Banking") != null) {
				player.setBankEquip(false);
				player.getBankT().openBank();
			}
		} else if (interfaceId == 670) {
			if (componentId == 0) {
				if (slotId >= player.getInventory().getItemsContainerSize())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (sendWear(player, slotId, item.getId()))
						ButtonHandler.refreshEquipBonuses(player);
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) { // inventory
			if (componentId == 0) {
				if (slotId > 27
						|| player.getInterfaceManager()
								.containsInventoryInter())
					return;
				Item item = player.getInventory().getItem(slotId);
				if (item == null || item.getId() != slotId2)
					return;
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					InventoryOptionsHandler.handleItemOption1(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					InventoryOptionsHandler.handleItemOption2(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					InventoryOptionsHandler.handleItemOption3(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					InventoryOptionsHandler.handleItemOption4(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					InventoryOptionsHandler.handleItemOption5(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					InventoryOptionsHandler.handleItemOption6(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON7_PACKET)
					InventoryOptionsHandler.handleItemOption7(player, slotId,
							slotId2, item);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					InventoryOptionsHandler.handleItemOption8(player, slotId,
							slotId2, item);
			}
		} else if (interfaceId == 742) {
			if (componentId == 46) // close
				player.stopAll();
		} else if (interfaceId == 743) {
			if (componentId == 20) // close
				player.stopAll();
		} else if (interfaceId == 741) {
			if (componentId == 9) // close
				player.stopAll();
		} else if (interfaceId == 749) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) // activate
					player.getPrayer().switchQuickPrayers();
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) // switch
					player.getPrayer().switchSettingQuickPrayer();
			}
		} else if (interfaceId == 750) {
			if (componentId == 4) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET) {
					if (!player.isLocked())
						player.toogleRun(player.isResting() ? false : true);
					if (player.isResting())
						player.stopAll();
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					if (player.isResting()) {
						player.stopAll();
						return;
					}
					long currentTime = Utils.currentTimeMillis();
					if (player.getEmotesManager().getNextEmoteEnd() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an emote.");
						return;
					}
					if (player.getLockDelay() >= currentTime) {
						player.getPackets().sendGameMessage(
								"You can't rest while perfoming an action.");
						return;
					}
					player.stopAll();
					player.getActionManager().setAction(new Rest());
				}
			}
		} else if (interfaceId == 11) {
			if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getInventory().sendExamine(slotId);
			} else if (componentId == 18)
				player.getBank().depositAllInventory(false);
			else if (componentId == 20)
				player.getBank().depositAllEquipment(false);
		} else if (interfaceId == 762) {
			if (componentId == 15)
				player.getBank().switchInsertItems();
			else if (componentId == 19)
				player.getBank().switchWithdrawNotes();
			else if (componentId == 33)
				player.getBank().depositAllInventory(true);
			else if (componentId == 35)
				player.getBank().depositMoneyPouch();
			else if (componentId == 37)
				player.getBank().depositAllEquipment(true);
			else if (componentId == 46) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(767);
				player.setCloseInterfacesEvent(new Runnable() {
					@Override
					public void run() {
						player.getBankT().openBank();
					}
				});
			} else if (componentId == 124) {
				player.closeInterfaces();
				player.getInterfaceManager().sendInterface(3008);
				player.sendBankPreset();
			} else if (componentId == 126) {
				if (player.getQuickSelect() != null)
					player.getQuickSelect().loadPreset();
				else
					player.sm("You don't have any preset set with this quick-key. Please open the preset options to set one.");
			} else if (componentId == 128) {
				if (player.getQuickSelect2() != null)
					player.getQuickSelect2().loadPreset();
				else
					player.sm("You don't have any preset set with this quick-key. Please open the preset options to set one.");
			} else if (componentId >= 46 && componentId <= 64) {
				int tabId = 9 - ((componentId - 46) / 2);
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().setCurrentTab(tabId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().collapse(tabId);
			} else if (componentId == 95) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().withdrawItem(slotId, 1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().withdrawItem(slotId, 5);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().withdrawItem(slotId, 10);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().withdrawLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().put("bank_isWithdraw",
							Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().withdrawItem(slotId, Integer.MAX_VALUE);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON6_PACKET)
					player.getBank().withdrawItemButOne(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getBank().sendExamine(slotId);

			} else if (componentId == 119) {
				openEquipmentBonuses(player, true);
			}
		} else if (interfaceId == 763) {
			if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
					player.getBank().depositItem(slotId, 1, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getBank().depositItem(slotId, 5, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getBank().depositItem(slotId, 10, true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getBank().depositLastAmount(slotId);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
					player.getTemporaryAttributtes().put("bank_item_X_Slot",
							slotId);
					player.getTemporaryAttributtes().remove("bank_isWithdraw");
					player.getPackets().sendRunScript(108,
							new Object[] { "Enter Amount:" });
				} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET)
					player.getBank().depositItem(slotId, Integer.MAX_VALUE,
							true);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
					player.getInventory().sendExamine(slotId);
			}
		} else if (interfaceId == 767) {
			if (componentId == 10)
				player.getBankT().openBank();
		} else if (interfaceId == 884) {
			if (componentId == 4) {
				int weaponId = player.getEquipment().getWeaponId();
				if (player.hasInstantSpecial(weaponId)) {
					player.performInstantSpecial(weaponId);
					return;
				}
				submitSpecialRequest(player);
			} else if (componentId >= 7 && componentId <= 10)
				player.getCombatDefinitions().setAttackStyle(componentId - 7);
			else if (componentId == 11)
				player.getCombatDefinitions().switchAutoRelatie();
		} else if (interfaceId == 755) {
			if (componentId == 44)
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, 2);
			else if (componentId == 42) {
				player.getHintIconsManager().removeAll();// TODO find hintIcon
															// index
				player.getPackets().sendConfig(1159, 1);
			}
		} else if (interfaceId == 20) 
			SkillCapeCustomizer.handleSkillCapeCustomizer(player, componentId);
		 else if (interfaceId == 1056) {
			if (componentId == 173)
				player.getInterfaceManager().sendInterface(917);
		} else if (interfaceId == 751) {
			if (componentId == 26) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setPrivateStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setPrivateStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setPrivateStatus(2);
			} else if (componentId == 32) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setFilterGame(false);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setFilterGame(true);
			} else if (componentId == 29) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setPublicStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setPublicStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setPublicStatus(2);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
					player.setPublicStatus(3);
			} else if (componentId == 0) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.getFriendsIgnores().setFriendsChatStatus(2);
			} else if (componentId == 14) {
				player.getInterfaceManager().sendInterface(275);
				int number = 0; int dicer = 0; int staff = 0;
				for (int i = 0; i < 100; i++) {
				player.getPackets().sendIComponentText(275, i, "");
				}
				for(Player p5 : World.getPlayers()) {
				if(p5 == null)
				continue;
				if (p5.isDicer())
					dicer++;
				if (p5.checkStaff())
					staff++;
				number++;
				String titles = "";
				String ironman = "";
				titles = "[Player]: ";
				if (p5.isIronMan()) {
					ironman = "<img=23>";
					titles = "<col=6D6F78><img=23>[Ironman]: ";
				}
				if (p5.isDonator())
				titles = "["+ironman+"<img=10><col=268703>Donator]: ";				
				if (p5.isExtremeDonator()) 
				titles = "["+ironman+"<img=8><col=DB1A1A>Extreme Donator</col>]: ";
				if (p5.isSavior()) 
				titles = "["+ironman+"<img=9><col=33B8D6>Super Donator</col>]: ";
				if (p5.isEradicator()) 
				titles = "["+ironman+"<img=18><col=02385E>Eradicator</col>]: ";		
				if (p5.isDicer()) 
				titles = "["+ironman+"<img=11><col=C423C4>Dicer</col>]: ";	
				if (p5.isHero()) 
				titles = "["+ironman+"<img=22><col=000000>Hero</col>]: ";			
				if (p5.isSupporter())
				titles = "["+ironman+"<img=13><col=00ff48>Supporter</col>]: ";
				if (p5.isForumMod())
				titles = "["+ironman+"<img=20><col=FF8930>Forum Moderator</col>]: ";
				if (p5.getRights() == 1) 
				titles = "["+ironman+"<img=0><col=559568>Moderator</col>]: ";
				if (p5.isHeadMod())
				titles = "["+ironman+"<img=16>Head Moderator</col>]: ";
				if (p5.isForumAdmin())
					titles = "["+ironman+"<img=24>Forum Admin</col>]: ";
				if (p5.isExecutive() || p5.getRights() == 2)
				titles = "["+ironman+"<img=17>Executive</col>]: ";
				if (p5.isHeadExecutive())
				titles = "["+ironman+"<img=21>Head Executive</col>]: ";
				if (p5.getRights() == 7) 
				titles = "["+ironman+"<img=7> <col=00FFFF>Owner/Developer</col>]: ";
				player.getPackets().sendIComponentText(275, (14+number), number+". "+titles + ""+ p5.getDisplayName());
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX's Players");
				player.getPackets().sendIComponentText(275, 10, " ");
				player.getPackets().sendIComponentText(275, 11, "Players Online: "+number);
				player.getPackets().sendIComponentText(275, 12, "Dicers Online: "+dicer);
				player.getPackets().sendIComponentText(275, 13, "Staff Online: "+staff);
				player.getPackets().sendGameMessage(
						"There are currently " + World.getPlayers().size()
								+ " players playing " + Settings.SERVER_NAME
								+ ".");
			} else if (componentId == 23) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setClanStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setClanStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setClanStatus(2);
			} else if (componentId == 20) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
					player.setTradeStatus(0);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
					player.setTradeStatus(1);
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
					player.setTradeStatus(2);
			} else if (componentId == 17) {
				if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
					player.setViewStats(true);
					player.sendDefaultPlayersOptions();
				}
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET){	
					player.sendDefaultPlayersOptions();
				}
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
					player.setViewStats(false);
					player.sendDefaultPlayersOptions();
				}
				else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
					// ASSIST XP Earned/Time
				}
			}
		} else if (interfaceId == 1163 || interfaceId == 1164
				|| interfaceId == 1168 || interfaceId == 1170
				|| interfaceId == 1173)
			player.getDominionTower().handleButtons(interfaceId, componentId);
		else if (interfaceId == 900)
			PlayerLook.handleMageMakeOverButtons(player, componentId);
		else if (interfaceId == 1028)
			PlayerDesign.handle(player, componentId, slotId);
		else if (interfaceId == 1108 || interfaceId == 1109)
			player.getFriendsIgnores().handleFriendChatButtons(interfaceId,
					componentId, packetId);
		else if (interfaceId == 1079)
			player.closeInterfaces();
		else if (interfaceId == 374) {
			if (componentId >= 5 && componentId <= 9)
				player.setNextWorldTile(new WorldTile(
						FightPitsViewingOrb.ORB_TELEPORTS[componentId - 5]));
			else if (componentId == 15)
				player.stopAll();
		} else if (interfaceId == 1092) {
			player.stopAll();
			WorldTile destTile = null;
			switch (componentId) {
			case 47:
				destTile = HomeTeleport.LUMBRIDGE_LODE_STONE;
				break;
			case 42:
				destTile = HomeTeleport.BURTHORPE_LODE_STONE;
				break;
			case 39:
				destTile = HomeTeleport.LUNAR_ISLE_LODE_STONE;
				break;
			case 7:
				destTile = HomeTeleport.BANDIT_CAMP_LODE_STONE;
				break;
			case 50:
				destTile = HomeTeleport.TAVERLY_LODE_STONE;
				break;
			case 40:
				destTile = HomeTeleport.ALKARID_LODE_STONE;
				break;
			case 51:
				destTile = HomeTeleport.VARROCK_LODE_STONE;
				break;
			case 45:
				destTile = HomeTeleport.EDGEVILLE_LODE_STONE;
				break;
			case 46:
				destTile = HomeTeleport.FALADOR_LODE_STONE;
				break;
			case 48:
				destTile = HomeTeleport.PORT_SARIM_LODE_STONE;
				break;
			case 44:
				destTile = HomeTeleport.DRAYNOR_VILLAGE_LODE_STONE;
				break;
			case 41:
				destTile = HomeTeleport.ARDOUGNE_LODE_STONE;
				break;
			case 43:
				destTile = HomeTeleport.CATHERBAY_LODE_STONE;
				break;
			case 52:
				destTile = HomeTeleport.YANILLE_LODE_STONE;
				break;
			case 49:
				destTile = HomeTeleport.SEERS_VILLAGE_LODE_STONE;
				break;
			}
			if (destTile != null)
				player.getActionManager().setAction(new HomeTeleport(destTile));
		} else if (interfaceId == 1214)
			player.getSkills().handleSetupXPCounter(componentId);
		else if (interfaceId == 1292) {
			if (componentId == 12)
				Crucible.enterArena(player);
			else if (componentId == 13)
				player.closeInterfaces();
		}
		if (Settings.DEBUG)
			Logger.log("ButtonHandler", "InterfaceId " + interfaceId
					+ ", componentId " + componentId + ", slotId " + slotId
					+ ", slotId2 " + slotId2 + ", PacketId: " + packetId);
	}

	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		player.stopAll(false, false);
		Item item = player.getEquipment().getItem(slotId);
		if (item == null
				|| !player.getInventory().addItem(item.getId(),
						item.getAmount()))
			return;
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearence().generateAppearenceData();
		if (Runecrafting.isTiara(item.getId()))
			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}

	public static boolean sendWear(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(
						player.getAppearence().isMale())) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		int targetSlot = Equipment.getItemSlot(itemId);
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return true;
		}
		if (!ItemConstants.canWear(item, player))
			return true;
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return true;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments) {
						player.getPackets()
								.sendGameMessage(
										"You are not high enough level to use this item.");
					}
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return true;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.stopAll(false, false);
		player.getInventory().deleteItem(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(5).getId(),
						player.getEquipment().getItem(5).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().addItem(
						player.getEquipment().getItem(3).getId(),
						player.getEquipment().getItem(3).getAmount())) {
					player.getInventory().getItems().set(slotId, item);
					player.getInventory().refresh(slotId);
					return true;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
						.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
						.getItems()
						.set(slotId,
								new Item(player.getEquipment()
										.getItem(targetSlot).getId(), player
										.getEquipment().getItem(targetSlot)
										.getAmount()));
				player.getInventory().refresh(slotId);
			} else
				player.getInventory().addItem(
						new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if (targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		player.getAppearence().generateAppearenceData();
		player.getPackets().sendSound(2240, 0, 1);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static boolean sendWear2(Player player, int slotId, int itemId) {
		if (player.hasFinished() || player.isDead())
			return false;
		player.stopAll(false, false);
		Item item = player.getInventory().getItem(slotId);
		if (item == null || item.getId() != itemId)
			return false;
		if (item.getDefinitions().isNoted()
				|| !item.getDefinitions().isWearItem(
						player.getAppearence().isMale()) && itemId != 4084) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}

		int targetSlot = Equipment.getItemSlot(itemId);
		if (itemId == 4084)
			targetSlot = 3;
		if (targetSlot == -1) {
			player.getPackets().sendGameMessage("You can't wear that.");
			return false;
		}
		if (!ItemConstants.canWear(item, player))
			return false;
		boolean isTwoHandedWeapon = targetSlot == 3
				&& Equipment.isTwoHandedWeapon(item);
		if (isTwoHandedWeapon && !player.getInventory().hasFreeSlots()
				&& player.getEquipment().hasShield()) {
			player.getPackets().sendGameMessage(
					"Not enough free space in your inventory.");
			return false;
		}
		HashMap<Integer, Integer> requiriments = item.getDefinitions()
				.getWearingSkillRequiriments();
		boolean hasRequiriments = true;
		if (requiriments != null) {
			for (int skillId : requiriments.keySet()) {
				if (skillId > 24 || skillId < 0)
					continue;
				int level = requiriments.get(skillId);
				if (level < 0 || level > 120)
					continue;
				if (player.getSkills().getLevelForXp(skillId) < level) {
					if (hasRequiriments)
						player.getPackets()
								.sendGameMessage(
										"You are not high enough level to use this item.");
					hasRequiriments = false;
					String name = Skills.SKILL_NAME[skillId].toLowerCase();
					player.getPackets().sendGameMessage(
							"You need to have a"
									+ (name.startsWith("a") ? "n" : "") + " "
									+ name + " level of " + level + ".");
				}

			}
		}
		if (!hasRequiriments)
			return false;
		if (!player.getControlerManager().canEquip(targetSlot, itemId))
			return false;
		player.getInventory().getItems().remove(slotId, item);
		if (targetSlot == 3) {
			if (isTwoHandedWeapon && player.getEquipment().getItem(5) != null) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(5))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(5, null);
			}
		} else if (targetSlot == 5) {
			if (player.getEquipment().getItem(3) != null
					&& Equipment.isTwoHandedWeapon(player.getEquipment()
							.getItem(3))) {
				if (!player.getInventory().getItems()
						.add(player.getEquipment().getItem(3))) {
					player.getInventory().getItems().set(slotId, item);
					return false;
				}
				player.getEquipment().getItems().set(3, null);
			}

		}
		if (player.getEquipment().getItem(targetSlot) != null
				&& (itemId != player.getEquipment().getItem(targetSlot).getId() || !item
						.getDefinitions().isStackable())) {
			if (player.getInventory().getItems().get(slotId) == null) {
				player.getInventory()
						.getItems()
						.set(slotId,
								new Item(player.getEquipment()
										.getItem(targetSlot).getId(), player
										.getEquipment().getItem(targetSlot)
										.getAmount()));
			} else
				player.getInventory()
						.getItems()
						.add(new Item(player.getEquipment().getItem(targetSlot)
								.getId(), player.getEquipment()
								.getItem(targetSlot).getAmount()));
			player.getEquipment().getItems().set(targetSlot, null);
		}
		if (targetSlot == Equipment.SLOT_AURA)
			player.getAuraManager().removeAura();
		int oldAmt = 0;
		if (player.getEquipment().getItem(targetSlot) != null) {
			oldAmt = player.getEquipment().getItem(targetSlot).getAmount();
		}
		Item item2 = new Item(itemId, oldAmt + item.getAmount());
		player.getEquipment().getItems().set(targetSlot, item2);
		player.getEquipment().refresh(targetSlot,
				targetSlot == 3 ? 5 : targetSlot == 3 ? 0 : 3);
		if (targetSlot == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
		player.getCharges().wear(targetSlot);
		return true;
	}

	public static void submitSpecialRequest(final Player player) {
		try {
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.getCombatDefinitions()
							.switchUsingSpecialAttack();
				}
			}, 1);
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static void sendWear(Player player, int[] slotIds) {
		if (player.hasFinished() || player.isDead())
			return;
		boolean worn = false;
		Item[] copy = player.getInventory().getItems().getItemsCopy();
		for (int slotId : slotIds) {
			Item item = player.getInventory().getItem(slotId);
			if (item == null)
				continue;
			if (sendWear2(player, slotId, item.getId()))
				worn = true;
		}
		player.getInventory().refreshItems(copy);
		if (worn) {
			player.getAppearence().generateAppearenceData();
			player.getPackets().sendSound(2240, 0, 1);
		}
	}

	public static void openEquipmentBonuses(final Player player, boolean banking) {	
		if (banking)
			player.setBankEquip(true);
		player.stopAll();
		player.getInterfaceManager().sendInventoryInterface(670);
		for (int i = 0; i <= 1; i++) { // Such a cheap way to fix the bank interface, but it works :)
		player.getInterfaceManager().sendInterface(667);
		player.getPackets().sendConfigByFile(4894, banking ? 1 : 0);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93, 4, 7,
				"Equip", "Compare", "Stats", "Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0, 27, 0,
				1, 2, 3);
		player.getPackets().sendUnlockIComponentOptionSlots(667, 9, 0, 14, 0);
		player.getPackets().sendIComponentSettings(667, 14, 0, 13, 1030);		
		refreshEquipBonuses(player);
		}
		if (banking) {
			player.getTemporaryAttributtes().put("Banking", Boolean.TRUE);
			player.setCloseInterfacesEvent(new Runnable() {
				@Override
				public void run() {
					player.getTemporaryAttributtes().remove("Banking");
				}
			});
		}
	}	
	public static void openEquipmentBonuses2(final Player player, boolean banking) {
		player.stopAll();
		player.getInterfaceManager().sendInventoryInterface(670);
		player.getInterfaceManager().sendInterface(667);
		player.getPackets().sendConfigByFile(4894, banking ? 1 : 0);
		player.getPackets().sendItems(93, player.getInventory().getItems());
		player.getPackets().sendInterSetItemsOptionsScript(670, 0, 93, 4, 7,
				"Equip", "Compare", "Stats", "Examine");
		player.getPackets().sendUnlockIComponentOptionSlots(670, 0, 0, 27, 0,//1710
				1, 2, 3);
		player.getPackets().sendIComponentSettings(667, 14, 0, 13, 1030);
		refreshEquipBonuses(player);
		if (banking) {
			player.getTemporaryAttributtes().put("Banking", Boolean.TRUE);
			player.setCloseInterfacesEvent(new Runnable() {
				@Override
				public void run() {
					player.getTemporaryAttributtes().remove("Banking");
				}

			});
		}
	}

	public static void refreshEquipBonuses(Player player) {
		player.getPackets().sendIComponentText(667, 28,
				"Stab: " + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 29,
				"Slash: " + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 30,
				"Crush: " + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 31,
				"Magic: " + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 32,
				"Range: " + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 33,
				"Stab: " + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 34,
				"Slash: " + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 35,
				"Crush: " + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 36,
				"Magic: " + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 37,
				"Range: " + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets()
				.sendIComponentText(
						667,
						38,
						"Summoning: +"
								+ player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets()
				.sendIComponentText(
						667,
						39,
						"Absorb Melee: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS]
								+ "%");
		player.getPackets()
				.sendIComponentText(
						667,
						40,
						"Absorb Magic: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS]
								+ "%");
		player.getPackets()
				.sendIComponentText(
						667,
						41,
						"Absorb Ranged: +"
								+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS]
								+ "%");
		player.getPackets().sendIComponentText(667, 42,
				"Strength: " + player.getCombatDefinitions().getBonuses()[14]);
		player.getPackets()
				.sendIComponentText(
						667,
						43,
						"Ranged Str: "
								+ player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 44,
				"Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(
				667,
				45,
				"Magic Damage: +"
						+ player.getCombatDefinitions().getBonuses()[17] + "%");
	}

	public static void sendMissionBoard(Player player) {
		// TODO Auto-generated method stub

	}

	public static void openSkillGuide(Player player) {
		player.getInterfaceManager().sendScreenInterface(317, 1218);
		player.getPackets().sendInterface(false, 1218, 1, 1217); // seems to fix
	}
}
