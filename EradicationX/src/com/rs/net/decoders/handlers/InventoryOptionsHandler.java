package com.rs.net.decoders.handlers;

import java.util.List;

import com.rs.Settings;
import com.rs.cores.WorldThread;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World; 
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.CrystalChest;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.npc.pet.Pet;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.BoxAction;
import com.rs.game.player.actions.BoxAction.HunterEquipment;
import com.rs.game.player.actions.Firemaking;
import com.rs.game.player.actions.Fletching;
import com.rs.game.player.actions.Fletching.Fletch;
import com.rs.game.player.actions.GemCutting;
import com.rs.game.player.actions.GemCutting.Gem;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.actions.Herblore;
import com.rs.game.player.actions.LeatherCrafting;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.actions.Summoning.Pouches;
import com.rs.game.player.content.AncientEffigies;
import com.rs.game.player.content.ArmourSets;
import com.rs.game.player.content.ArmourSets.Sets;
import com.rs.game.player.content.Burying.Bone;
import com.rs.game.player.content.Dicing;
import com.rs.game.player.content.DonatorBox;
import com.rs.game.player.content.Foods;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Pots;
import com.rs.game.player.content.Runecrafting;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.game.player.content.custom.PriceManager;
import com.rs.game.player.content.mission.Entrance;
import com.rs.game.player.controlers.Barrows;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.quest.EliteChapterOne;
import com.rs.game.player.quest.QNames;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

public class InventoryOptionsHandler {

	public static void handleItemOption2(final Player player, final int slotId,
			final int itemId, Item item) {
		if (Firemaking.isFiremaking(player, itemId))
			return;
		if (itemId == 15262) {
			int shardbox = player.getInventory().getNumerOf(15262);
			if ((shardbox * 5000 + player.getInventory().getNumerOf(12183)) > Integer.MAX_VALUE ||
					(shardbox * 5000 + player.getInventory().getNumerOf(12183)) < 0) {
				player.sm("Not enough space in your inventory.");
				return;
			}
			player.getInventory().deleteItem(15262, shardbox);
			player.getInventory().addItem(12183, 5000 * shardbox);
			player.sm("You open the spirit shard packs and find " + Utils.formatNumber(shardbox * 5000) + " spirit shards.");
			return;
		}
		if (itemId == 15098) {
			player.sendDiceHistory();
			return;
		}
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.emptyPouch(player, pouch);
			player.stopAll(false);
		} else if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, true); 
			return;
		} else {
			if (player.isEquipDisabled())
				return;
			long passedTime = Utils.currentTimeMillis() - WorldThread.LAST_CYCLE_CTM;
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					List<Integer> slots = player.getSwitchItemCache();
					int[] slot = new int[slots.size()];
					for (int i = 0; i < slot.length; i++)
						slot[i] = slots.get(i);
					player.getSwitchItemCache().clear();
					ButtonHandler.sendWear(player, slot);
					player.stopAll(false, true, false);
				}
			}, passedTime >= 600 ? 0 : passedTime > 330 ? 1 : 0);
			if (player.getSwitchItemCache().contains(slotId))
				return;
			player.getSwitchItemCache().add(slotId);
		}
		/*} else {
			if (player.isEquipDisabled())
				return;
			long passedTime = Utils.currentTimeMillis()
					- WorldThread.LAST_CYCLE_CTM;
			WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					List<Integer> slots = player.getSwitchItemCache();
					int[] slot = new int[slots.size()];
					for (int i = 0; i < slot.length; i++)
						slot[i] = slots.get(i);
					player.getSwitchItemCache().clear();
					ButtonHandler.sendWear(player, slot);
					player.stopAll(false, true, false);
					
				}
			}, passedTime >= 600 ? 0 : passedTime > 330 ? 2 : 1);
			if (player.getSwitchItemCache().contains(slotId))
				return;
			player.getSwitchItemCache().add(slotId);
		}*/
	}
	public static void dig(final Player player) {
		player.resetWalkSteps();
		player.setNextAnimation(new Animation(830));
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
				if (Barrows.digIntoGrave(player))
					return;
				if(player.getX() == 3005 && player.getY() == 3376
						|| player.getX() == 2999 && player.getY() == 3375
						|| player.getX() == 2996 && player.getY() == 3377
						|| player.getX() == 2989 && player.getY() == 3378
						|| player.getX() == 2987 && player.getY() == 3387
						|| player.getX() == 2984 && player.getY() == 3387) {
					//mole
					player.setNextWorldTile(new WorldTile(1752, 5137, 0));
					player.getPackets().sendGameMessage("You seem to have dropped down into a network of mole tunnels.");
					return;
				}
				player.getPackets().sendGameMessage("You find nothing.");
			}
			
		});
	}

	public static void handleItemOption1(Player player, final int slotId,
			final int itemId, Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		 if (!player.getControlerManager().processInventoryClick1(item)) {
             return;
         }
		player.stopAll(false);
		if (Foods.eat(player, item, slotId))
			return;
		if (itemId >= 15086 && itemId <= 15100) {
			Dicing.handleRoll(player, itemId, true); 
			return;
		}
		/*
		Eradicator Rank in a box
		*/
		if (itemId == 6828) { 
			player.getDialogueManager().startDialogue("EradicatorRank");				
		}
		/*
		Savior Rank in a box
		*/		
		if (itemId == 6829) {
			player.getDialogueManager().startDialogue("SaviorRank");						
		}
		/*
		Extreme Donator Rank in a box
		*/		
		if (itemId == 6830) {
			player.getDialogueManager().startDialogue("ExtremeRank");						
		}
		/*
		Donator Rank in a box
		*/		
		if (itemId == 6832) {
			player.getDialogueManager().startDialogue("DonatorRank");						
		}
		/*
		Trio Key
		*/
		if (itemId == 29944) {
			player.getDialogueManager().startDialogue("CombineKey");						
		}
		if (itemId == 29945) {
			player.getDialogueManager().startDialogue("CombineKey");						
		}
		if (itemId == 29946) {
			player.getDialogueManager().startDialogue("CombineKey");						
		}
		if (itemId == 29947 || (itemId >= 27749 && itemId <= 27753)) {
			if (player.inInstance() || !player.canSpawn() || player.getJailed() > 0)
            	player.sm("You can't access teleports at the moment.");         
        	else
        		player.getDialogueManager().startDialogue("TrioKey");
		}
		/*
		Trio Key end
		*/
		if (itemId == 5733) {
			player.sm("Ew, it's rotten! Don't touch it.");
			//player.getDialogueManager().startDialogue("Potato");						
		}		
		if (itemId == 6831) {
			DonatorBox.Reward(player);
		player.getInventory().deleteItem(6830, 1);			
		}
		if (itemId == 26574) {
			player.sm("You currently have "+player.getBossSlayerPoints() + " boss slayer points."); 
			if (player.getBossTask() != null)
            player.sm("You need to defeat "+ player.getBossTask().getTaskAmount() + " more "
                    + player.getBossTask().getName().toLowerCase() + " to complete your task.");
			else
			player.sm("You don't have a task. Go to the grim reaper to get a new one.");
		}
		
		// Rune boxes 
		// 555 water, 560 death, 9075 astral, 565 blood, 557 earth, 566 soul		
		if (itemId == 27370) {
			if (((player.getInventory().containsItem(555, 1) &&
					player.getInventory().containsItem(560, 1) &&
					player.getInventory().containsItem(565, 1)) ||
					player.getInventory().getFreeSlots() >= 3) ||				
				((player.getInventory().containsItem(555, 1) &&
						player.getInventory().containsItem(560, 1) || player.getInventory().containsItem(565, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||				
				((player.getInventory().containsItem(555, 1) &&
						player.getInventory().getFreeSlots() >= 2)) ||						
				((player.getInventory().containsItem(565, 1) &&
						player.getInventory().containsItem(560, 1) || player.getInventory().containsItem(555, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||				
				((player.getInventory().containsItem(565, 1) &&
						player.getInventory().getFreeSlots() >= 2)) ||					
				((player.getInventory().containsItem(560, 1) &&
						player.getInventory().containsItem(555, 1) || player.getInventory().containsItem(565, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||				
				((player.getInventory().containsItem(560, 1) &&
						player.getInventory().getFreeSlots() >= 2))) {		
			player.getInventory().deleteItem(27370, 1);
			player.getInventory().addItem(555, 6000);
			player.getInventory().addItem(560, 4000);
			player.getInventory().addItem(565, 2000);
			player.sm("You open the crate and find...");
			player.sm("6,000 Water runes, 2,000 Blood runes, and 4,000 Death runes.");
			} else {
				player.sm("Not enough space in your inventory.");
			}
			return;
		}
		if (itemId == 27371) {
			if (((player.getInventory().containsItem(566, 1) &&
					player.getInventory().containsItem(560, 1) &&
					player.getInventory().containsItem(565, 1)) ||
					player.getInventory().getFreeSlots() >= 3) ||				
				((player.getInventory().containsItem(566, 1) &&
						player.getInventory().containsItem(560, 1) || player.getInventory().containsItem(565, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||				
				((player.getInventory().containsItem(566, 1) &&
						player.getInventory().getFreeSlots() >= 2)) ||						
				((player.getInventory().containsItem(565, 1) &&
						player.getInventory().containsItem(560, 1) || player.getInventory().containsItem(566, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||				
				((player.getInventory().containsItem(565, 1) &&
						player.getInventory().getFreeSlots() >= 2)) ||						
				((player.getInventory().containsItem(560, 1) &&
						player.getInventory().containsItem(566, 1) || player.getInventory().containsItem(565, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||	
				((player.getInventory().containsItem(560, 1) &&
						player.getInventory().getFreeSlots() >= 2))) {			
			player.getInventory().deleteItem(27371, 1);
			player.getInventory().addItem(566, 1000);
			player.getInventory().addItem(560, 4000);
			player.getInventory().addItem(565, 4000);
			player.sm("You open the crate and find...");
			player.sm("1,000 Soul runes, 4,000 Blood runes, and 4,000 Death runes.");
			} else {
				player.sm("Not enough space in your inventory.");
			}
			return;
		}
		if (itemId == 27372) {
			if (((player.getInventory().containsItem(9075, 1) &&
					player.getInventory().containsItem(560, 1) &&
					player.getInventory().containsItem(557, 1)) ||
					player.getInventory().getFreeSlots() >= 3) ||
				((player.getInventory().containsItem(9075, 1) &&
						player.getInventory().containsItem(560, 1) || player.getInventory().containsItem(557, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||		
				((player.getInventory().containsItem(9075, 1) &&
						player.getInventory().getFreeSlots() >= 2)) ||					
				((player.getInventory().containsItem(557, 1) &&
						player.getInventory().containsItem(560, 1) || player.getInventory().containsItem(9075, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||	
				((player.getInventory().containsItem(557, 1) &&
						player.getInventory().getFreeSlots() >= 2)) ||		
				((player.getInventory().containsItem(560, 1) &&
						player.getInventory().containsItem(9075, 1) || player.getInventory().containsItem(557, 1)) &&
						player.getInventory().getFreeSlots() >= 1) ||
				((player.getInventory().containsItem(560, 1) &&
						player.getInventory().getFreeSlots() >= 2))) {		
			player.getInventory().deleteItem(27372, 1);
			player.getInventory().addItem(9075, 4000);
			player.getInventory().addItem(560, 2000);
			player.getInventory().addItem(557, 10000);
			player.sm("You open the crate and find...");
			player.sm("10,000 Earth runes, 4,000 Astral runes, and 2,000 Death runes.");
			} else {
				player.sm("Not enough space in your inventory.");
			}
			return;
		}		
		
		  

		if (itemId == 6190) {
			if (player.getLearnedRocktailSoup())
				player.sm("You can't learn the same ability again!");
			else {
				player.getInventory().deleteItem(6190, 1);
				player.sm("You've learned the ability to cook rocktail soups! Use a rocktail on a stew to start creating them!");
				player.setLearnedRocktailSoup(true);
			}
		}
		if (itemId == 27746) {
			if (player.hasLearnedAutoLootSeals())
				player.sm("You can't learn the same ability again!");
			else {
				player.getInventory().deleteItem(27746, 1);
				player.sm("You've learned the ability to automatically loot eradicated seals! They'll go straight into your pouch without having to loot them.");
				player.setLearnedAutoLootSeals(true);
			}
		}
		if (itemId == 27748) {
			if (player.hasLearnedPermTurmBoost())
				player.sm("You can't learn the same ability again!");
			else {
				player.getInventory().deleteItem(27748, 1);
				player.sm("You've learned the ability to permanently boost turmoil's bonuses to its full potential!");
				player.setLearnedPermTurmBoost(true);
			}
		}
		if (itemId == 15246) {
			player.getInventory().deleteItem(15246, 1);
			player.getBank().addItem(995, 50000000, true);
			player.getBank().addItem(1321, 1, true);
			player.getBank().addItem(25743, 1, true);
			player.getBank().addItem(1712, 1, true);
			player.getBank().addItem(3105, 1, true);
			player.getBank().addItem(24299, 1, true);
			player.getBank().addItem(841, 1, true);
			player.getBank().addItem(882, 5000, true);
			player.getBank().addItem(15272, 500, true);
			player.sm("Items from the starter pack have been sent to your bank. Use the money to buy gear from our shops!");
		}
		if (itemId == 27735 || itemId == 27736 || itemId == 27737) {
			if (player.getQuestStage(QNames.ELITE_CHAPTER_I) < 6)
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I've stooped as low as picking up corpses. Eh, might as well keep it on me.");
			else if (player.getInventory().containsItem(27735, 1) && player.getInventory().containsItem(27736, 1) && player.getInventory().containsItem(27737, 1)) {
				player.getInventory().deleteItem(27735, 1);
				player.getInventory().deleteItem(27736, 1);
				player.getInventory().deleteItem(27737, 1);
				player.getInventory().addItem(27738, 1);
				player.setQuestStage(QNames.ELITE_CHAPTER_I, 7);
				player.getDialogueManager().startDialogue("SimpleMessage", "You've checked off something from Cyndrith's list.");
				player.sm("You grind up the bones to dust.");
			} else {	
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I'll need all three remains to get the ashes.");
			}
		}
		if (itemId == 27738) {
			EliteChapterOne ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
			ec1.refresh(player);
			if (!ec1.isBottleAsh()) {
				if (player.getInventory().containsItem(27741, 1)) {
					player.getInventory().deleteItem(27738, 1);
					player.getInventory().deleteItem(27741, 1);
					player.getInventory().addItem(27742, 1);
				} else {
					player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need an ink bottle filled with water. Cyndrith's chest has the bottle in it.");
				}
			}
		}
		if (itemId == 27743) {
			if (player.getInventory().containsItem(26126, 1)) {
				player.getInventory().deleteItem(27743, 1);
				player.getInventory().deleteItem(26126, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EOBSIDIAN_BOOTS, 1);	
				player.setNextForceTalk(new ForceTalk("Piertotum locomotor lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian boots.");
				
			}else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any obsidian boots that can be enchanted.");
		}
		if (itemId == 27747) {
			if (player.getInventory().containsItem(26124, 1)) {
				player.getInventory().deleteItem(27747, 1);
				player.getInventory().deleteItem(26124, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EOBSIDIAN_GLOVES, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian gloves.");
				
			}else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any obsidian gloves that can be enchanted.");
		}
		if (itemId == 27761) {
			if (player.getInventory().containsItem(26140, 1)) {
				player.getInventory().deleteItem(27761, 1);
				player.getInventory().deleteItem(26140, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EOBSIDIAN_BODY, 1);	
				player.setNextForceTalk(new ForceTalk("Mikiliyaru candothrod lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian platebody.");
				
			}else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have an obsidian platebody that can be enchanted.");
		}
		if (itemId == 27764) {
			if (player.getInventory().containsItem(26136, 1)) {
				player.getInventory().deleteItem(27764, 1);
				player.getInventory().deleteItem(26136, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EOBSIDIAN_LEGS, 1);	
				player.setNextForceTalk(new ForceTalk("Sinodurm falopdu lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian platelegs.");
				
			}else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any obsidian platelegs that can be enchanted.");
		}//Make obby helm dialogue
		if (itemId == 27765) {
			player.getDialogueManager().startDialogue("HeadEnchantments");
		}
		if (itemId == 27754) {
			if (player.getInventory().containsItem(26031, 1)) {
				player.getInventory().deleteItem(27754, 1);
				player.getInventory().deleteItem(26031, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EROYALCOURT_RAPIER_OH, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the off-hand royal court rapier.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any off-hand royal court rapiers that can be enchanted.");
		}
		if (itemId == 27755) {
			if (player.getInventory().containsItem(25112, 1)) {
				player.getInventory().deleteItem(27755, 1);
				player.getInventory().deleteItem(25112, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EROYALCOURT_RAPIER, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the royal court rapier.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any royal court rapiers that can be enchanted.");
		}
		if (itemId == 27756) {
			if (player.getInventory().containsItem(29977, 1)) {
				player.getInventory().deleteItem(27756, 1);
				player.getInventory().deleteItem(29977, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EASCENSION_CBOW_OH, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the off-hand ascension crossbow.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any off-hand ascension crossbows that can be enchanted.");
		}
		if (itemId == 27757) {
			if (player.getInventory().containsItem(29980, 1)) {
				player.getInventory().deleteItem(27757, 1);
				player.getInventory().deleteItem(29980, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EASCENSION_CBOW, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the ascension crossbow.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any ascension crossbows that can be enchanted.");
		}
		if (itemId == 27758) {
			if (player.getInventory().containsItem(25654, 1)) {
				player.getInventory().deleteItem(27758, 1);
				player.getInventory().deleteItem(25654, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EVIRTUS_WAND, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the virtus wand.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any virtus wands that can be enchanted.");
		}
		if (itemId == 27759) {
			if (player.getInventory().containsItem(25664, 1)) {
				player.getInventory().deleteItem(27759, 1);
				player.getInventory().deleteItem(25664, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EVIRTUS_BOOK, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the virtus book.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any virtus books that can be enchanted.");
		}
		if (itemId == 27760) {
			if (player.getInventory().containsItem(26134, 1)) {
				player.getInventory().deleteItem(27760, 1);
				player.getInventory().deleteItem(26134, 1);
				player.setNextGraphics(new Graphics(113));
				player.setNextAnimation(new Animation(713));
				player.getInventory().addItem(Equipment.EOBSIDIAN_SHIELD, 1);	
				player.setNextForceTalk(new ForceTalk("Unudorum induramas lacarnum inflamari deletrius!"));
				player.getDialogueManager().startDialogue("SimpleMessage", "You feel a burst of power eminating from the obsidian shield.");
				
			} else
			player.getDialogueManager().startDialogue("SimpleMessage", "You don't have any obsidian shields that can be enchanted.");
		}
		if (itemId == 27740) {
			EliteChapterOne ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
			ec1.refresh(player);
			if (!ec1.isBottleWater()) {
				if (player.getInventory().containsItem(227, 1)) {
					player.getInventory().deleteItem(227, 1);
					player.getInventory().deleteItem(27740, 1);
					player.getInventory().addItem(27741, 1);
				} else {
					player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need a vial of water to fill this ink bottle up. I think the herblore shop has some.");
				}
			}
		}
		if (itemId == 27763) {
			if (!player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_III)) {
				player.sm("You must complete The Elite: Chapter III to use this item.");
				return;
			}
			player.setSpellPower(item.getAmount());
			player.getInventory().deleteItem(item);
			player.setNextGraphics(new Graphics(113));
			player.setNextAnimation(new Animation(713));
			player.sm("You now have " + player.getSpellPower() +  " Spell power.");
		}
		if (itemId == 27762) {
			player.getDialogueManager().startDialogue("CyndrithEnchantments");
		}
		if (itemId == 27741) {
			EliteChapterOne ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
			ec1.refresh(player);
			if (!ec1.isBottleAsh()) {
				if (player.getInventory().containsItem(27738, 1)) {
					player.getInventory().deleteItem(27738, 1);
					player.getInventory().deleteItem(27741, 1);
					player.getInventory().addItem(27742, 1);
				} else {
					player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need the ashes from the trio to fill this vial up.");
				}
			}
		}
		if (itemId == 15262) {
			player.sm("You open the spirit shard pack and find 5,000 spirit shards.");
			if ((5000 + player.getInventory().getNumerOf(12183)) > Integer.MAX_VALUE ||
					(5000 + player.getInventory().getNumerOf(12183)) < 0) {
				player.sm("Not enough space in your inventory.");
				return;
			}
			player.getInventory().deleteItem(15262, 1);
			player.getInventory().addItem(12183, 5000);
			return;
		}
		if (itemId == 27742) {
			EliteChapterOne ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
			ec1.refresh(player);
			if (ec1.isBottleAsh()) {
				if (player.getInventory().containsItem(314, 1)) {
					player.getInventory().deleteItem(27742, 1);
					player.getInventory().deleteItem(314, 1);
					player.getInventory().addItem(13328, 1);
					player.getDialogueManager().startDialogue("SimpleMessage", "You've checked off something from Cyndrith's list.");
				} else {
					player.getDialogueManager().startDialogue("SimplePlayerMessage", "I need a feather to make a quill out of this ink. The fishing shop sells some.");
				}
			}
		}
		if (itemId == 27739) {
			if (player.getEXQuestManager().getQuestStage(QNames.ELITE_CHAPTER_I) != 13)
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I don't know what to write on here.");
			else {	
				EliteChapterOne ec1 = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
				player.getInventory().deleteItem(27739, 1);
				player.getInventory().addItem(27743, 1);
				player.getDialogueManager().startDialogue("SimpleMessage", "You write down the enchantment.");	
				ec1.setComplete(player);
			}
				
		}
		if (itemId == 6196) {
			if (player.getQuestStage(QNames.ELITE_CHAPTER_I) <= 5) {
				player.getDialogueManager().startDialogue("SimplePlayerMessage", "I'm not sure what to do with this list. Maybe I should talk to Cyndrith first.");
				return;
				}
			if (player.getEXQuestManager().isComplete(QNames.ELITE_CHAPTER_I)){
				player.sm("You no longer need this list.");
				return;
				}
			EliteChapterOne quest = (EliteChapterOne) player.getEXQuestManager().getQuest(QNames.ELITE_CHAPTER_I);
			quest.showListProgress(player);
		}
		if (itemId == 27744) {
			if (!player.getInventory().hasFreeSlots()) {
				player.sm("Not enough space in your inventory.");
				return;
			}
			int[] trimmedItems = {2615, 6617,6619,6623,6625,6629,4125,2583,2585,2587,2589,2591,
					2593,2595,2597,4129,2599,2601,2603,2605,2607,2609,2611,2613,2623,2625,
					2627,2629,2617,2619,2621,19428,19431,19437,19440,19413,19416,19422,19425,
					2661,2663,2665,2667,2653,2655,2657,2659,2671,2673,2675,2669,19398,19401,
					19407,19410,19336,19337,19338,19340,19341,19342,19343,19345,7368,7364,7362,
					7366,7380,7372,7378,7370,7376,7384,7382,7374,10368,10370,10372,10374,10376,
					10378,10380,10382,10384,10386,10388,10390,19443,19445,19447,19449,19452,19454,
					19456,19458,19459,19461,19463,19465};
			int i = Utils.getRandom(trimmedItems.length - 1);
			player.getInventory().deleteItem(27744, 1);
	        Item getLoot = new Item(trimmedItems[i]);
			World.sendWorldMessage("<img=6>[Trimmed Box] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"
			+ getLoot.getDefinitions().getName()+"</col>!", false);
			player.getInventory().addItem(trimmedItems[i], 1);
		}
		if (itemId == 27745) {
			if (!player.getInventory().hasFreeSlots()) {
				player.sm("Not enough space in your inventory.");
				return;
			}
			int[] trimmedItems = {22568,22578,22588,22598,22610,22620,22630,22640,
					20125,20127,20129,20131,20133,24329,24330,24331,24332,24333,18705,18706,
					18707,18708,18709,24560,24561,24562,24563,24564,24555,24556,24557,24558,
					24559,19706,19707,19708,25816,25817,25818,25819,25820,25845,
					25847,25849,25851,25853,25835,25837,25839,25841,25843,25825,25827,25829,25831,25833};
			int i = Utils.getRandom(trimmedItems.length - 1);
			player.getInventory().deleteItem(27745, 1);
	        Item getLoot = new Item(trimmedItems[i]);
			World.sendWorldMessage("<img=6>[Cosmetic Box] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"
			+ getLoot.getDefinitions().getName()+"</col>!", false);
			player.getInventory().addItem(trimmedItems[i], 1);
		}
		if (player.getToolbelt().contains(1755)) {
		switch (itemId) {
		case 1625:
			GemCutting.cut(player, Gem.OPAL);
			return;
		case 1627:
			GemCutting.cut(player, Gem.JADE);
			return;
		case 1629:
			GemCutting.cut(player, Gem.RED_TOPAZ);
			return;
		case 1623:
			GemCutting.cut(player, Gem.SAPPHIRE);
			return;
		case 1621:
			GemCutting.cut(player, Gem.EMERALD);
			return;
		case 1619:
			GemCutting.cut(player, Gem.RUBY);
			return;
		case 1617:
			GemCutting.cut(player, Gem.DIAMOND);
			return;
		case 1631:
			GemCutting.cut(player, Gem.DRAGONSTONE);
			return;
		case 6571:
			GemCutting.cut(player, Gem.ONYX);
			return;
		}
		} 
		if (item.getName().toLowerCase().contains("logs")) {
			player.getDialogueManager().startDialogue("LogAction", itemId);
		}
		if (LeatherCrafting.getHide(itemId)) {
			LeatherCrafting.handleItemOnItem(player, item, new Item(1733));
			return;
		}
		if (itemId == 6199) {
			int freeSlots = player.getInventory().getFreeSlots();
			if (freeSlots < 1) {
				player.sm("Not enough space in your inventory.");
				return;
			} else {
			player.setNextGraphics(new Graphics(306));
			player.setNextAnimation(new Animation(15562));
			int[] RandomItems = {1037,14595,14603,1050,23679,23680,23681,11700,
					23683,23684,23685,23686,23687,23688,23689,11730,23691,23692,
					23693,23694,23695,23696,23697,23698,23699,23700,25031,25028,
					25034,2617,2615,27359, 20822,20824,20825,20826,20823,16955,
					16425,20135,20139,20143,20159,20163,20167,20147,20151,20155,
					18349,18351,18355,18359,18357,16669,17239,1053,1055,1057,
					19281,19284,19287,19290,19293,19296,19299,19302,19305,
					11789,24359,24360,24361,24362,24363,24639,24641,24643,
					24645,24647,24649,24651,28821,28822,28818,28820,28812, 17361,
					28813,27994}; 
		int i = Utils.getRandom(RandomItems.length - 1);
		player.getInventory().deleteItem(6199, 1);
        player.getInventory().addItem(RandomItems[i], 1);
        Item getLoot = new Item(RandomItems[i]);
		player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() + 1);
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
			players.getPackets().sendGameMessage("<img=6>[Mystery Box] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"+ getLoot.getDefinitions().getName()+"</col> and 100M!");
				}
			return;
			}
		}
		if (itemId == 6542) {
			int freeSlots = player.getInventory().getFreeSlots();
			if (freeSlots < 1 && !player.getInventory().containsItem(2996, 1)) {
				player.sm("Not enough space in your inventory.");
				return;
			} else {
			player.setNextGraphics(new Graphics(306));
			player.setNextAnimation(new Animation(15562));
			int[] RandomItems = {/*Pink*/ 29997, /*Bronz*/29996, /*Cyan*/29995,
					/*camo*/29993, /*aqua*/29994, /*Orange*/29992, /*Party*/29985, 
					/*blk*/28014, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996,
					/*100m*/2996, /*100m*/2996, /*100m*/2996, /*100m*/2996, 
					/*100m*/2996, /*100m*/2996}; 
		int i = Utils.random(RandomItems.length -1);
		player.getInventory().deleteItem(6542, 1);
        player.getInventory().addItem(RandomItems[i], 1);
        Item getLoot = new Item(RandomItems[i]);
		player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() + 1);
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
			players.getPackets().sendGameMessage("<img=6>[Present] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"+ getLoot.getDefinitions().getName()+"</col> and 100M!");
				}
			return;
			}
		}		
		if (itemId == 24352) {
			player.sm("Right click and use this item on an infinity or dragon armor to transform it into dragonbone gear.");
		}
		if (itemId == 10181) {
			int[] VoteItems = {20044, 20045, 20046, 13101, 24110, 24112, 24114, 10400, 10402, 10404, 10406, 10408, 10410, 10412, 10414, 10416, 10418,
					10420, 10422, 10424, 10426, 10428, 10430, 10432, 10434, 10436, 10438, 10364, 10366, 10362,
					7806, 7809, 10440, 10442, 10444, 10470, 10472, 10474, 19392, 19394, 19396, 10458, 10460,
					10462, 19380, 19382, 19384, 10464, 10466, 10468, 19386, 19388, 19390};
        int i = Utils.getRandom(VoteItems.length -1);
		player.getInventory().deleteItem(10181, 1);
        player.getInventory().addItem(VoteItems[i], 1);
        Item getLoot = new Item(VoteItems[i]);

			player.getPackets().sendGameMessage("<img=6>[Vote Mystery] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"+ getLoot.getDefinitions().getName()+"!</col>");

			return;
		}	
		if (itemId == 27356) {
		int[] GWDItems = {25022, 11696, 11704, 11726, 11728, 11694, 11718, 11720, 11722, 24992, 24995, 24998, 25004, 25007, 11700, 25028, 25031, 25034, 27996, 27997};
		int i = Utils.getRandom(GWDItems.length -1);
		Item getLoot = new Item(GWDItems[i]);
		player.getInventory().deleteItem(27356, 1);
		 player.getInventory().addItem(GWDItems[i], 1);
				player.getPackets().sendGameMessage("<img=6>[Vote Feed] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"+ getLoot.getDefinitions().getName()+"</col> from a GWD Box.");
		 return;
		}
		if (itemId == 27358) {
		player.getInventory().deleteItem(27358, 1);
		player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() + 1500);
				player.getPackets().sendGameMessage("<img=6>[Vote Feed] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>1,500 Eradicated Seals.");
			
		 return;
		}
		if (itemId == 27359) {
		player.getInventory().deleteItem(27359, 1);
		player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() +  400);
				player.getPackets().sendGameMessage("<img=6>[Vote Feed] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>400 Invasion Tokens.");
			
		 return;
		}			
		if (itemId == 27357) {
		int[] GWDItems = {15017, 15018, 15019, 15020, 15220};
		int i = Utils.getRandom(GWDItems.length -1);
		Item getLoot = new Item(GWDItems[i]);
		player.getInventory().deleteItem(27357, 1);
		 player.getInventory().addItem(GWDItems[i], 1);
				player.getPackets().sendGameMessage("<img=6>[Vote Feed] <col=C92500>" + player.getDisplayName() + "</col> has received <col=C92500>"+ getLoot.getDefinitions().getName()+"</col> from an Imbued Box.");
			
		 return;
		}			
		if (itemId == 29922) {
		player.getInventory().addItem(10728, 1);
		player.getInventory().addItem(10727, 1);
		player.getInventory().addItem(10726, 1);
		player.getInventory().addItem(10725, 1);
		player.getInventory().addItem(10724, 1);
		player.getInventory().deleteItem(29922, 1);
			for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
			players.getPackets().sendGameMessage("<img=6><col=ff00ff>[Halloween event]: " + player.getDisplayName() + "</col> has received a"+player.fontColor()+ " Skeleton Set</col> for completing halloween event.");
				}
			return;
		}		
		if (itemId == 18092) {
		player.getInventory().deleteItem(18092, 1);
		Entrance.MissionTeleport(player, 4311, 5465, 0);
		player.sm("Talk to Pumpkin Pete");
		}
		if (Pots.pot(player, item, slotId))
			return;
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			if (itemId == 5509)
				pouch = 0;
			if (itemId == 5510)
				pouch = 1;
			if (itemId == 5512)
				pouch = 2;
			if (itemId == 5514)
				pouch = 3;
			Runecrafting.fillPouch(player, pouch);
			return;
		}
		if (itemId == 22370) {
			Summoning.openDreadnipInterface(player);
		}
		if (itemId == 299) {
			if (player.isLocked())
				return;
			if (World.getObject(new WorldTile(player), 10) != null) {
				player.getPackets().sendGameMessage("You cannot plant flowers here..");
				return;
			}
			final Player thisman = player;
			final double random = Utils.getRandomDouble(100);
			final WorldTile tile = new WorldTile(player);
			int flower = Utils.random(2980, 2987);
			if (random < 0.2) {
				flower = Utils.random(2987, 2989);
			}
			if (player.getUsername().equals("Igetwhites"))
				flower = 2987;
			if (!player.addWalkSteps(player.getX() - 1, player.getY(), 1))
				if (!player.addWalkSteps(player.getX() + 1, player.getY(), 1))
					if (!player.addWalkSteps(player.getX(), player.getY() + 1, 1))
						player.addWalkSteps(player.getX(), player.getY() - 1, 1);
			player.getInventory().deleteItem(299, 1);
			final WorldObject flowerObject = new WorldObject(flower, 10, Utils.getRandom(4), tile.getX(), tile.getY(), tile.getPlane());
			World.spawnTemporaryObject(flowerObject, 45000);
			player.lock();
			WorldTasksManager.schedule(new WorldTask() {
				int step;

				@Override
				public void run() {
					if (thisman == null || thisman.hasFinished())
						stop();
					if (step == 1) {
						thisman.getDialogueManager().startDialogue("FlowerPickup", flowerObject);
						thisman.setNextFaceWorldTile(tile);
						thisman.unlock();
						stop();
					}
					step++;
				}
			}, 0, 0);

		}
		if (itemId == 14664) {
			player.lock(3);
			player.getInterfaceManager().sendInterface(1123);
			player.getInventory().deleteItem(14664, 1);
			player.sm("Random event gift box opens... please, select a reward.");
		}
		if (itemId == 22340) {
			player.getDialogueManager().startDialogue("XPBook");
		}
		if (itemId == 952) {// spade
			dig(player);
			return;
		}
		if (itemId == 26122) {
			if (player.getInventory().containsItem(29976, 1)) {
				player.getInventory().deleteItem(26122, 1);
				player.getInventory().deleteItem(29976, 1);				
				player.getInventory().addItem(29975, 1);
			  } else {
				  player.sm("You need an Enchanting Potion in your inventory to do this action, you can get a potion from the bone grinder.");
			return;
			}
		}		
		if (itemId == 2996) {
			if (!player.getInventory().hasFreeSlots() && !player.getInventory().containsItem(995, 1)) {
				player.sm("Not enough inventory space.");
				return;
			}
			if (player.getInventory().containsItem(995, 2047483647)) {
			  player.sm("You've exceeded the capacity of coins possible to carry! Consider spending it or bank it.");
			  } else {
				player.getInventory().deleteItem(2996, 1);
				player.getInventory().addItem(995, 100000000);
				return;
			}
		}		
		if (itemId == 405) {
				player.getInventory().deleteItem(405, 1);
				player.getInventory().addItem(995, 1000000);
			return;
		}
		if (itemId == 7509) {//rock cake
			if (player.getHitpoints() - 10 > 0) {
				player.applyRHit(new Hit(player, 10, HitLook.REGULAR_DAMAGE));
				player.setNextForceTalk(new ForceTalk("That hit the spot!"));
				} else {
					player.setNextForceTalk(new ForceTalk("That cake could've killed me! :O"));
				}
			return;
		}	
		if (itemId == 229) {
				player.getInventory().deleteItem(229, 28);
			return;
		}			
		if (HerbCleaning.clean(player, item, slotId))
			return;
		Bone bone = Bone.forId(itemId);
		if (bone != null) {
			Bone.bury(player, slotId);
			return;
		}
		if (Magic.useTabTeleport(player, itemId))
			return;
		if (itemId == AncientEffigies.SATED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.GORGED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.NOURISHED_ANCIENT_EFFIGY
				|| itemId == AncientEffigies.STARVED_ANCIENT_EFFIGY)
			player.getDialogueManager().startDialogue("AncientEffigiesD",
					itemId);
		else if (itemId == 4155)
			player.getDialogueManager().startDialogue("EnchantedGemDialouge");
		else if (itemId >= 23653 && itemId <= 23658)
			FightKiln.useCrystal(player, itemId);
		if (itemId == 24155) {
			player.getSqueal().giveEarnedSpins(2);
			player.getPackets().sendIComponentText(1139, 10, " "+ player.getSqueal().getTotalSpins() +" ");
			player.getInventory().deleteItem(24155, 1);
		// Wilderness Teleport
		} else if (itemId == 29979) {
			if (player.getTeleBlockDelay() <= 0) {
				player.setNextAnimation(new Animation(2413));		
				player.setNextGraphics(new Graphics(1927));		
				player.unlock();
				player.getControlerManager().forceStop();
				if (player.getNextWorldTile() == null)
				player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);		
				player.getInventory().deleteItem(29979, 1);				
			} else {
				player.sm("You are Teleport Blocked and cannot use the tablet!" + player.getTeleBlockDelay());	
			}
		} else if (itemId == 24154) {
			player.getSqueal().giveEarnedSpins(1);
			player.getPackets().sendIComponentText(1139, 10, " "+ player.getSqueal().getTotalSpins() +" ");
			player.getInventory().deleteItem(24154, 1);
		} else if (itemId == 23713) {
			ShopsHandler.openShop(player, 47);
		} else if (itemId == 23714) {
			ShopsHandler.openShop(player, 48);
		} else if (itemId == 23715) {
			ShopsHandler.openShop(player, 49);
		} else if (itemId == 23716) {
			ShopsHandler.openShop(player, 50);		
		} else if (itemId == 23717) {
			player.getSkills().addXp(Skills.ATTACK, 2500);
			player.getInventory().deleteItem(23717, 1);
		} else if (itemId == 23721) {
			player.getSkills().addXp(Skills.STRENGTH, 2500);
			player.getInventory().deleteItem(23721, 1);
		} else if (itemId == 23725) {
			player.getSkills().addXp(Skills.DEFENCE, 2500);
			player.getInventory().deleteItem(23725, 1);
		} else if (itemId == 23729) {
			player.getSkills().addXp(Skills.RANGE, 2500);
			player.getInventory().deleteItem(23729, 1);
		} else if (itemId == 23733) {
			player.getSkills().addXp(Skills.MAGIC, 2500);
			player.getInventory().deleteItem(23733, 1);
		} else if (itemId == 23737) {
			player.getSkills().addXp(Skills.PRAYER, 2500);
			player.getInventory().deleteItem(23737, 1);
		} else if (itemId == 23741) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 2500);
			player.getInventory().deleteItem(23741, 1);
		} else if (itemId == 23745) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 2500);
			player.getInventory().deleteItem(23745, 1);
		} else if (itemId == 23749) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 1000);
			player.getInventory().deleteItem(23749, 1);
		} else if (itemId == 23753) {
			player.getSkills().addXp(Skills.HITPOINTS, 2500);
			player.getInventory().deleteItem(23753, 1);
		} else if (itemId == 23757) {
			player.getSkills().addXp(Skills.AGILITY, 2500);
			player.getInventory().deleteItem(23757, 1);
		} else if (itemId == 23761) {
			player.getSkills().addXp(Skills.HERBLORE, 2500);
			player.getInventory().deleteItem(23761, 1);
		} else if (itemId == 23765) {
			player.getSkills().addXp(Skills.THIEVING, 2500);
			player.getInventory().deleteItem(23765, 1);
		} else if (itemId == 23769) {
			player.getSkills().addXp(Skills.CRAFTING, 2500);
			player.getInventory().deleteItem(23769, 1);
		} else if (itemId == 23774) {
			player.getSkills().addXp(Skills.FLETCHING, 2500);
			player.getInventory().deleteItem(23774, 1);
		} else if (itemId == 23778) {
			player.getSkills().addXp(Skills.SLAYER, 2500);
			player.getInventory().deleteItem(23778, 1);
		} else if (itemId == 23782) {
			player.getSkills().addXp(Skills.HUNTER, 2500);
			player.getInventory().deleteItem(23782, 1);
		} else if (itemId == 23786) {
			player.getSkills().addXp(Skills.MINING, 2500);
			player.getInventory().deleteItem(23786, 1);
		} else if (itemId == 23790) {
			player.getSkills().addXp(Skills.SMITHING, 2500);
			player.getInventory().deleteItem(23790, 1);
		} else if (itemId == 23794) {
			player.getSkills().addXp(Skills.FISHING, 2500);
			player.getInventory().deleteItem(23794, 1);
		} else if (itemId == 23798) {
			player.getSkills().addXp(Skills.COOKING, 2500);
			player.getInventory().deleteItem(23798, 1);
		} else if (itemId == 23802) {
			player.getSkills().addXp(Skills.FIREMAKING, 2500);
			player.getInventory().deleteItem(23802, 1);
		} else if (itemId == 23806) {
			player.getSkills().addXp(Skills.WOODCUTTING, 2500);
			player.getInventory().deleteItem(23806, 1);
		} else if (itemId == 23810) {
			player.getSkills().addXp(Skills.FARMING, 2500);
			player.getInventory().deleteItem(23810, 1);
		} else if (itemId == 23814) {
			player.getSkills().addXp(Skills.SUMMONING, 2500);
			player.getInventory().deleteItem(23814, 1);
		} else if (itemId == 23718) {
			player.getSkills().addXp(Skills.ATTACK, 5000);
			player.getInventory().deleteItem(23718, 1);
		} else if (itemId == 23722) {
			player.getSkills().addXp(Skills.STRENGTH, 5000);
			player.getInventory().deleteItem(23722, 1);
		} else if (itemId == 23726) {
			player.getSkills().addXp(Skills.DEFENCE, 5000);
			player.getInventory().deleteItem(23726, 1);
		} else if (itemId == 23730) {
			player.getSkills().addXp(Skills.RANGE, 5000);
			player.getInventory().deleteItem(23730, 1);
		} else if (itemId == 23734) {
			player.getSkills().addXp(Skills.MAGIC, 5000);
			player.getInventory().deleteItem(23734, 1);
		} else if (itemId == 23738) {
			player.getSkills().addXp(Skills.PRAYER, 5000);
			player.getInventory().deleteItem(23738, 1);
		} else if (itemId == 23742) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 5000);
			player.getInventory().deleteItem(23742, 1);
		} else if (itemId == 23746) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 5000);
			player.getInventory().deleteItem(23746, 1);
		} else if (itemId == 23750) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 5000);
			player.getInventory().deleteItem(23750, 1);
		} else if (itemId == 23754) {
			player.getSkills().addXp(Skills.HITPOINTS, 5000);
			player.getInventory().deleteItem(23754, 1);
		} else if (itemId == 23758) {
			player.getSkills().addXp(Skills.AGILITY, 5000);
			player.getInventory().deleteItem(23758, 1);
		} else if (itemId == 23762) {
			player.getSkills().addXp(Skills.HERBLORE, 5000);
			player.getInventory().deleteItem(23762, 1);
		} else if (itemId == 23766) {
			player.getSkills().addXp(Skills.THIEVING, 5000);
			player.getInventory().deleteItem(23766, 1);
		} else if (itemId == 23770) {
			player.getSkills().addXp(Skills.CRAFTING, 5000);
			player.getInventory().deleteItem(23770, 1);
		} else if (itemId == 23775) {
			player.getSkills().addXp(Skills.FLETCHING, 5000);
			player.getInventory().deleteItem(23775, 1);
		} else if (itemId == 23779) {
			player.getSkills().addXp(Skills.SLAYER, 5000);
			player.getInventory().deleteItem(23779, 1);
		} else if (itemId == 23783) {
			player.getSkills().addXp(Skills.HUNTER, 5000);
			player.getInventory().deleteItem(23783, 1);
		} else if (itemId == 23787) {
			player.getSkills().addXp(Skills.MINING, 5000);
			player.getInventory().deleteItem(23787, 1);
		} else if (itemId == 23791) {
			player.getSkills().addXp(Skills.SMITHING, 5000);
			player.getInventory().deleteItem(23791, 1);
		} else if (itemId == 23795) {
			player.getSkills().addXp(Skills.FISHING, 5000);
			player.getInventory().deleteItem(23795, 1);
		} else if (itemId == 23799) {
			player.getSkills().addXp(Skills.COOKING, 5000);
			player.getInventory().deleteItem(23799, 1);
		} else if (itemId == 23803) {
			player.getSkills().addXp(Skills.FIREMAKING, 5000);
			player.getInventory().deleteItem(23803, 1);
		} else if (itemId == 23807) {
			player.getSkills().addXp(Skills.WOODCUTTING, 5000);
			player.getInventory().deleteItem(23807, 1);
		} else if (itemId == 23811) {
			player.getSkills().addXp(Skills.FARMING, 5000);
			player.getInventory().deleteItem(23811, 1);
		} else if (itemId == 23815) {
			player.getSkills().addXp(Skills.SUMMONING, 5000);
			player.getInventory().deleteItem(23815, 1);
		} else if (itemId == 23719) {
			player.getSkills().addXp(Skills.ATTACK, 7500);
			player.getInventory().deleteItem(23719, 1);
		} else if (itemId == 23723) {
			player.getSkills().addXp(Skills.STRENGTH, 7500);
			player.getInventory().deleteItem(23723, 1);
		} else if (itemId == 23727) {
			player.getSkills().addXp(Skills.DEFENCE, 7500);
			player.getInventory().deleteItem(23727, 1);
		} else if (itemId == 23731) {
			player.getSkills().addXp(Skills.RANGE, 7500);
			player.getInventory().deleteItem(23731, 1);
		} else if (itemId == 23735) {
			player.getSkills().addXp(Skills.MAGIC, 7500);
			player.getInventory().deleteItem(23735, 1);
		} else if (itemId == 23739) {
			player.getSkills().addXp(Skills.PRAYER, 7500);
			player.getInventory().deleteItem(23739, 1);
		} else if (itemId == 23743) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 7500);
			player.getInventory().deleteItem(23743, 1);
		} else if (itemId == 23747) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 7500);
			player.getInventory().deleteItem(23747, 1);
		} else if (itemId == 23751) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 7500);
			player.getInventory().deleteItem(23751, 1);
		} else if (itemId == 23755) {
			player.getSkills().addXp(Skills.HITPOINTS, 7500);
			player.getInventory().deleteItem(23755, 1);
		} else if (itemId == 23759) {
			player.getSkills().addXp(Skills.AGILITY, 7500);
			player.getInventory().deleteItem(23759, 1);
		} else if (itemId == 23763) {
			player.getSkills().addXp(Skills.HERBLORE, 7500);
			player.getInventory().deleteItem(23763, 1);
		} else if (itemId == 23767) {
			player.getSkills().addXp(Skills.THIEVING, 7500);
			player.getInventory().deleteItem(23767, 1);
		} else if (itemId == 23771) {
			player.getSkills().addXp(Skills.CRAFTING, 7500);
			player.getInventory().deleteItem(23771, 1);
		} else if (itemId == 23776) {
			player.getSkills().addXp(Skills.FLETCHING, 7500);
			player.getInventory().deleteItem(23776, 1);
		} else if (itemId == 23780) {
			player.getSkills().addXp(Skills.SLAYER, 7500);
			player.getInventory().deleteItem(23780, 1);
		} else if (itemId == 23784) {
			player.getSkills().addXp(Skills.HUNTER, 7500);
			player.getInventory().deleteItem(23784, 1);
		} else if (itemId == 23788) {
			player.getSkills().addXp(Skills.MINING, 7500);
			player.getInventory().deleteItem(23788, 1);
		} else if (itemId == 23792) {
			player.getSkills().addXp(Skills.SMITHING, 7500);
			player.getInventory().deleteItem(23792, 1);
		} else if (itemId == 23796) {
			player.getSkills().addXp(Skills.FISHING, 7500);
			player.getInventory().deleteItem(23796, 1);
		} else if (itemId == 23800) {
			player.getSkills().addXp(Skills.COOKING, 7500);
			player.getInventory().deleteItem(23800, 1);
		} else if (itemId == 23804) {
			player.getSkills().addXp(Skills.FIREMAKING, 7500);
			player.getInventory().deleteItem(23804, 1);
		} else if (itemId == 23808) {
			player.getSkills().addXp(Skills.WOODCUTTING, 7500);
			player.getInventory().deleteItem(23808, 1);
		} else if (itemId == 23812) {
			player.getSkills().addXp(Skills.FARMING, 7500);
			player.getInventory().deleteItem(23812, 1);
		} else if (itemId == 23816) {
			player.getSkills().addXp(Skills.SUMMONING, 7500);
			player.getInventory().deleteItem(23816, 1);
		} else if (itemId == 23720) {
			player.getSkills().addXp(Skills.ATTACK, 10000);
			player.getInventory().deleteItem(23720, 1);
		} else if (itemId == 23724) {
			player.getSkills().addXp(Skills.STRENGTH, 10000);
			player.getInventory().deleteItem(23724, 1);
		} else if (itemId == 23728) {
			player.getSkills().addXp(Skills.DEFENCE, 10000);
			player.getInventory().deleteItem(23728, 1);
		} else if (itemId == 23732) {
			player.getSkills().addXp(Skills.RANGE, 10000);
			player.getInventory().deleteItem(23732, 1);
		} else if (itemId == 23736) {
			player.getSkills().addXp(Skills.MAGIC, 10000);
			player.getInventory().deleteItem(23736, 1);
		} else if (itemId == 23740) {
			player.getSkills().addXp(Skills.PRAYER, 10000);
			player.getInventory().deleteItem(23740, 1);
		} else if (itemId == 23744) {
			player.getSkills().addXp(Skills.RUNECRAFTING, 10000);
			player.getInventory().deleteItem(23744, 1);
		} else if (itemId == 23748) {
			player.getSkills().addXp(Skills.CONSTRUCTION, 10000);
			player.getInventory().deleteItem(23748, 1);
		} else if (itemId == 23752) {
			player.getSkills().addXp(Skills.DUNGEONEERING, 10000);
			player.getInventory().deleteItem(23752, 1);
		} else if (itemId == 23756) {
			player.getSkills().addXp(Skills.HITPOINTS, 10000);
			player.getInventory().deleteItem(23756, 1);
		} else if (itemId == 23760) {
			player.getSkills().addXp(Skills.AGILITY, 10000);
			player.getInventory().deleteItem(23760, 1);
		} else if (itemId == 23764) {
			player.getSkills().addXp(Skills.HERBLORE, 10000);
			player.getInventory().deleteItem(23764, 1);
		} else if (itemId == 23768) {
			player.getSkills().addXp(Skills.THIEVING, 10000);
			player.getInventory().deleteItem(23768, 1);
		} else if (itemId == 23773) {
			player.getSkills().addXp(Skills.CRAFTING, 10000);
			player.getInventory().deleteItem(23773, 1);
		} else if (itemId == 23777) {
			player.getSkills().addXp(Skills.FLETCHING, 10000);
			player.getInventory().deleteItem(23777, 1);
		} else if (itemId == 23781) {
			player.getSkills().addXp(Skills.SLAYER, 10000);
			player.getInventory().deleteItem(23781, 1);
		} else if (itemId == 23785) {
			player.getSkills().addXp(Skills.HUNTER, 10000);
			player.getInventory().deleteItem(23785, 1);
		} else if (itemId == 23789) {
			player.getSkills().addXp(Skills.MINING, 10000);
			player.getInventory().deleteItem(23789, 1);
		} else if (itemId == 23793) {
			player.getSkills().addXp(Skills.SMITHING, 10000);
			player.getInventory().deleteItem(23793, 1);
		} else if (itemId == 23797) {
			player.getSkills().addXp(Skills.FISHING, 10000);
			player.getInventory().deleteItem(23797, 1);
		} else if (itemId == 23801) {
			player.getSkills().addXp(Skills.COOKING, 10000);
			player.getInventory().deleteItem(23801, 1);
		} else if (itemId == 23805) {
			player.getSkills().addXp(Skills.FIREMAKING, 10000);
			player.getInventory().deleteItem(23805, 1);
		} else if (itemId == 23809) {
			player.getSkills().addXp(Skills.WOODCUTTING, 10000);
			player.getInventory().deleteItem(23809, 1);
		} else if (itemId == 23813) {
			player.getSkills().addXp(Skills.FARMING, 10000);
			player.getInventory().deleteItem(23813, 1);
		} else if (itemId == 23817) {
			player.getSkills().addXp(Skills.SUMMONING, 10000);
			player.getInventory().deleteItem(23817, 1);
		} else if (itemId == 24300) {
			player.getSkills().addXp(Skills.ATTACK, 100000);
			player.getSkills().addXp(Skills.STRENGTH, 100000);
			player.getSkills().addXp(Skills.DEFENCE, 100000);
			player.getSkills().addXp(Skills.RANGE, 100000);
			player.getSkills().addXp(Skills.MAGIC, 100000);
			player.getSkills().addXp(Skills.PRAYER, 100000);
			player.getSkills().addXp(Skills.RUNECRAFTING, 100000);
			player.getSkills().addXp(Skills.CONSTRUCTION, 100000);
			player.getSkills().addXp(Skills.DUNGEONEERING, 100000);
			player.getSkills().addXp(Skills.HITPOINTS, 100000);
			player.getSkills().addXp(Skills.AGILITY, 100000);
			player.getSkills().addXp(Skills.HERBLORE, 100000);
			player.getSkills().addXp(Skills.THIEVING, 100000);
			player.getSkills().addXp(Skills.CRAFTING, 100000);
			player.getSkills().addXp(Skills.FLETCHING, 100000);
			player.getSkills().addXp(Skills.SLAYER, 100000);
			player.getSkills().addXp(Skills.HUNTER, 100000);
			player.getSkills().addXp(Skills.MINING, 100000);
			player.getSkills().addXp(Skills.SMITHING, 100000);
			player.getSkills().addXp(Skills.FISHING, 100000);
			player.getSkills().addXp(Skills.COOKING, 100000);
			player.getSkills().addXp(Skills.FIREMAKING, 100000);
			player.getSkills().addXp(Skills.WOODCUTTING, 100000);
			player.getSkills().addXp(Skills.FARMING, 100000);
			player.getSkills().addXp(Skills.SUMMONING, 100000);
			player.getInventory().deleteItem(24300, 1);
		} else if (itemId == 1856) {// Information Book
			player.getInterfaceManager().sendInterface(275);
			player.getPackets()
					.sendIComponentText(275, 2, Settings.SERVER_NAME);
			player.getPackets().sendIComponentText(275, 16,
					"Welcome to " + Settings.SERVER_NAME + ".");
			player.getPackets().sendIComponentText(275, 17,
					"If want some an item use command ::item id.");
			player.getPackets().sendIComponentText(275, 18,
					"If you don't have an item list you can find ids");
			player.getPackets().sendIComponentText(275, 19,
					"at http://itemdb.biz");
			player.getPackets().sendIComponentText(275, 20,
					"You can change your prayers and spells at home.");
			player.getPackets().sendIComponentText(275, 21,
					"If you need any help, do ::ticket. (Don't abuse it)");
			player.getPackets().sendIComponentText(275, 22,
					"at start of your message on public chat.");
			player.getPackets().sendIComponentText(275, 22,
					"By the way you can compare your ::score with your mates.");
			player.getPackets().sendIComponentText(275, 23,
					"Oh and ye, don't forget to ::vote and respect rules.");
			player.getPackets().sendIComponentText(275, 24, "");
			player.getPackets().sendIComponentText(275, 25,
					"Forums: " + Settings.WEBSITE_LINK);
			player.getPackets().sendIComponentText(275, 26, "");
			player.getPackets().sendIComponentText(275, 27,
					"Enjoy your time on " + Settings.SERVER_NAME + ".");
			player.getPackets().sendIComponentText(275, 28,
					"<img=1> Staff Team");
			player.getPackets().sendIComponentText(275, 29, "");
			player.getPackets().sendIComponentText(275, 30, "");
			player.getPackets().sendIComponentText(275, 14,
					"<u>Visit Website</u>");
			for (int i = 31; i < 300; i++)
				player.getPackets().sendIComponentText(275, i, "");
		} else if (itemId == HunterEquipment.BOX.getId()) // almost done
			player.getActionManager().setAction(new BoxAction(HunterEquipment.BOX));
		else if (itemId == HunterEquipment.BRID_SNARE.getId())
			player.getActionManager().setAction(
					new BoxAction(HunterEquipment.BRID_SNARE));
		else if (item.getDefinitions().getName().startsWith("Burnt")) 
			player.getDialogueManager().startDialogue("SimplePlayerMessage", "Ugh, this is inedible.");
		
		if (Settings.DEBUG)
			Logger.log("ItemHandler", "Item Select:" + itemId + ", Slot Id:"
					+ slotId);
	}

	/*
	 * returns the other
	 */
	public static Item contains(int id1, Item item1, Item item2) {
		if (item1.getId() == id1)
			return item2;
		if (item2.getId() == id1)
			return item1;
		return null;
	}

	public static boolean contains(int id1, int id2, Item... items) {
		boolean containsId1 = false;
		boolean containsId2 = false;
		for (Item item : items) {
			if (item.getId() == id1)
				containsId1 = true;
			else if (item.getId() == id2)
				containsId2 = true;
		}
		return containsId1 && containsId2;
	}

	public static void handleItemOnItem(final Player player, InputStream stream) {
	    int itemUsedWithId = stream.readShort();
	    int toSlot = stream.readShortLE128();
	    int hash1 = stream.readInt();
	    int hash2 = stream.readInt();
	    int interfaceId = hash1 >> 16;
	    int interfaceId2 = hash2 >> 16;
	    int comp1 = hash1 & 0xFFFF;
	    int fromSlot = stream.readShort();
	    int itemUsedId = stream.readShortLE128();
		if ((interfaceId2 == 747 || interfaceId2 == 662)
				&& interfaceId == Inventory.INVENTORY_INTERFACE) {
			if (player.getFamiliar() != null) {
				player.getFamiliar().setSpecial(true);
				if (player.getFamiliar().getSpecialAttack() == SpecialAttack.ITEM) {
					if (player.getFamiliar().hasSpecialOn())
						player.getFamiliar().submitSpecial(toSlot);
				}
			}
			return;
		}
		if (interfaceId == 192 && interfaceId2 == 679) {
		    Magic.handleMagicOnItem(player, comp1, player.getInventory().getItem(toSlot));
		    return;
		}
		if (interfaceId == Inventory.INVENTORY_INTERFACE
				&& interfaceId == interfaceId2
				&& !player.getInterfaceManager().containsInventoryInter()) {
			if (toSlot >= 28 || fromSlot >= 28)
				return;
			Item usedWith = player.getInventory().getItem(toSlot);
			Item itemUsed = player.getInventory().getItem(fromSlot);
			if (itemUsed == null || usedWith == null
					|| itemUsed.getId() != itemUsedId
					|| usedWith.getId() != itemUsedWithId)
				return;
			player.stopAll();
			if (!player.getControlerManager().canUseItemOnItem(itemUsed,
					usedWith))
				return;
			Fletch fletch = Fletching.isFletching(usedWith, itemUsed);
			if (fletch != null) {
				player.getDialogueManager().startDialogue("FletchingD", fletch);
				return;
			}
			if (itemUsed.getId() == CrystalChest.toothHalf()  && usedWith.getId() == CrystalChest.loopHalf() || itemUsed.getId() == CrystalChest.loopHalf() && usedWith.getId() == CrystalChest.toothHalf()){
			        CrystalChest.makeKey(player);
			        return;
		    }
			if (itemUsed.getId() == 11710 || usedWith.getId() == 11712 || usedWith.getId() == 11714) {
                    if (player.getInventory().containsItem(11710, 1)
                                    && player.getInventory().containsItem(11712, 1)
                                    && player.getInventory().containsItem(11714, 1)) {
                            player.getInventory().deleteItem(11710, 1);
                            player.getInventory().deleteItem(11712, 1);
                            player.getInventory().deleteItem(11714, 1);
                            player.getInventory().addItem(11690, 1);
							player.getPackets().sendGameMessage("You made a godsword blade.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11702) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11702, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11702, 1);
                            player.getInventory().addItem(11694, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make an Armadyl godsword.");
                    }
                }
                
                if (itemUsed.getId() == 19499 || usedWith.getId() == 19499) {
                    if (player.getInventory().containsItem(19499, 1)) {
                            player.getInventory().deleteItem(19499, 1);
                            player.getInventory().addItem(19281, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19500 || usedWith.getId() == 19500) {
                    if (player.getInventory().containsItem(19500, 1)) {
                            player.getInventory().deleteItem(19500, 1);
                            player.getInventory().addItem(19284, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19501 || usedWith.getId() == 19501) {
                    if (player.getInventory().containsItem(19501, 1)) {
                            player.getInventory().deleteItem(19501, 1);
                            player.getInventory().addItem(19287, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19502 || usedWith.getId() == 19502) {
                    if (player.getInventory().containsItem(19502, 1)) {
                            player.getInventory().deleteItem(19502, 1);
                            player.getInventory().addItem(19290, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19503 || usedWith.getId() == 19503) {
                    if (player.getInventory().containsItem(19503, 1)) {
                            player.getInventory().deleteItem(19503, 1);
                            player.getInventory().addItem(19293, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19504 || usedWith.getId() == 19504) {
                    if (player.getInventory().containsItem(19504, 1)) {
                            player.getInventory().deleteItem(19504, 1);
                            player.getInventory().addItem(19296, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19505 || usedWith.getId() == 19505) {
                    if (player.getInventory().containsItem(19505, 1)) {
                            player.getInventory().deleteItem(19505, 1);
                            player.getInventory().addItem(19299, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19506 || usedWith.getId() == 19506) {
                    if (player.getInventory().containsItem(19506, 1)) {
                            player.getInventory().deleteItem(19506, 1);
                            player.getInventory().addItem(19302, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 19507 || usedWith.getId() == 19507) {
                    if (player.getInventory().containsItem(19507, 1)) {
                            player.getInventory().deleteItem(19507, 1);
                            player.getInventory().addItem(19305, 1);
							player.getPackets().sendGameMessage("You fix your dragon mask.");
                    }
                }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11706) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11706, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11706, 1);
                            player.getInventory().addItem(11698, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make a Saradomin godsword.");
                    }
                 }
                if (itemUsed.getId() == 11690 || usedWith.getId() == 11708) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11708, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11708, 1);
                            player.getInventory().addItem(11700, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make a Zamorak godsword.");
                    }
                }
                if ((itemUsed.getId() == 11690 || usedWith.getId() == 11704) || itemUsed.getId() == 11704 || usedWith.getId() == 11690) {
                    if (player.getInventory().containsItem(11690, 1)
                                    && player.getInventory().containsItem(11704, 1)) {
                            player.getInventory().deleteItem(11690, 1);
                            player.getInventory().deleteItem(11704, 1);
                            player.getInventory().addItem(11696, 1);
							player.getPackets().sendGameMessage("You attach the hilt to the blade and make a Bandos godsword.");
                    }
                }                
				if (itemUsed.getId() == 21369 || usedWith.getId() == 4151) {
                    if (player.getInventory().containsItem(21369, 1)
                                    && player.getInventory().containsItem(4151, 1)) {
                            player.getInventory().deleteItem(21369, 1);
                            player.getInventory().deleteItem(4151, 1);
                            player.getInventory().addItem(21371, 1);
							player.getPackets().sendGameMessage("You attach the whip vine with the whip and make an Vine whip");
                    }
                }
			int herblore = Herblore.isHerbloreSkill(itemUsed, usedWith);
			if (herblore > -1) {
				player.getDialogueManager().startDialogue("HerbloreD",
						herblore, itemUsed, usedWith);
				return;
			}
			if (itemUsed.getId() == LeatherCrafting.NEEDLE.getId()
					|| usedWith.getId() == LeatherCrafting.NEEDLE.getId()) {
				if (LeatherCrafting.handleItemOnItem(player, itemUsed, usedWith)) {
					return;
				}
			}
			if (itemUsed.getId() == 24342 || usedWith.getId() == 24340 || usedWith.getId() == 24346 || usedWith.getId() == 24344) {
			    if (player.getInventory().containsItem(24342, 1)
			                    && player.getInventory().containsItem(24342, 1)
			                    && player.getInventory().containsItem(24340, 1)
			                    && player.getInventory().containsItem(24346, 1)
			                    && player.getInventory().containsItem(24344, 1)) {
					player.getInventory().deleteItem(24342, 1);
					player.getInventory().deleteItem(24340, 1);
					player.getInventory().deleteItem(24346, 1);
					player.getInventory().deleteItem(24344, 1);
					player.getInventory().addItem(24338, 1);
					player.getInventory().addItem(24336, 10000);
					World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
							+ Utils.formatPlayerNameForDisplay(new Item(24338).getName()) +"</col>!", false);
			    }
			}
			if ((itemUsed.getId() == 8921 || usedWith.getId() == 4166 || usedWith.getId() == 4164 || usedWith.getId() == 4551 || usedWith.getId() == 4168)
			|| (itemUsed.getId() == 4166 || usedWith.getId() == 8921 || usedWith.getId() == 4164 || usedWith.getId() == 4551 || usedWith.getId() == 4168)
			|| (itemUsed.getId() == 4164 || usedWith.getId() == 8921 || usedWith.getId() == 4166 || usedWith.getId() == 4551 || usedWith.getId() == 4168)
			|| (itemUsed.getId() == 4551 || usedWith.getId() == 8921 || usedWith.getId() == 4166 || usedWith.getId() == 4164 || usedWith.getId() == 4168)
			|| (itemUsed.getId() == 4168 || usedWith.getId() == 8921 || usedWith.getId() == 4166 || usedWith.getId() == 4164 || usedWith.getId() == 4551)) {
			    if (player.getInventory().containsItem(4166, 1)
			                    && player.getInventory().containsItem(8921, 1)
			                    && player.getInventory().containsItem(4164, 1)
			                    && player.getInventory().containsItem(4551, 1)
			                    && player.getInventory().containsItem(4168, 1)) {
					player.getInventory().deleteItem(8921, 1);
					player.getInventory().deleteItem(4166, 1);
					player.getInventory().deleteItem(4164, 1);
					player.getInventory().deleteItem(4551, 1);
					player.getInventory().deleteItem(4168, 1);
					player.getInventory().addItem(13263, 1);
					World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
							+ Utils.formatPlayerNameForDisplay(new Item(13263).getName()) +"</col>!", false);
			    }
			}
			if ((itemUsed.getId() == 13263 || usedWith.getId() == 15490 || usedWith.getId() == 15488)
			|| (itemUsed.getId() == 15490 || usedWith.getId() == 13263 || usedWith.getId() == 15488)
			|| (itemUsed.getId() == 15488 || usedWith.getId() == 15490 || usedWith.getId() == 13263)) {
			    if (player.getInventory().containsItem(13263, 1)
			                    && player.getInventory().containsItem(15490, 1)
			                    && player.getInventory().containsItem(15488, 1)) {
					player.getInventory().deleteItem(13263, 1);
					player.getInventory().deleteItem(15490, 1);
					player.getInventory().deleteItem(15488, 1);
					player.getInventory().addItem(15492, 1);
					World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
							+ Utils.formatPlayerNameForDisplay(new Item(15492).getName()) +"</col>!", false);
					if (!player.isCompletedFullSlayerHelmet())
						player.setCompletedFullSlayerHelmet();
			    }
			}			
			//{/*Black Mask*/8921, 
			///*Earmuffs*/4166, 
			///*Face Mask*/4164, 
			///*Spiny helm*/4551,
			///*Nose Peg*/4168};			
			Sets set = ArmourSets.getArmourSet(itemUsedId, itemUsedWithId);
			if (set != null) {
				ArmourSets.exchangeSets(player, set);
				return;
			}
			if (Firemaking.isFiremaking(player, itemUsed, usedWith))
				return;
			else if (contains(1755, Gem.OPAL.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.OPAL);
			else if (contains(1755, Gem.JADE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.JADE);
			else if (contains(1755, Gem.RED_TOPAZ.getUncut(), itemUsed,
					usedWith))
				GemCutting.cut(player, Gem.RED_TOPAZ);
			else if (contains(1755, Gem.SAPPHIRE.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.SAPPHIRE);
			else if (contains(1755, Gem.EMERALD.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.EMERALD);
			else if (contains(1755, Gem.RUBY.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.RUBY);
			else if (contains(1755, Gem.DIAMOND.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.DIAMOND);
			else if (contains(1755, Gem.DRAGONSTONE.getUncut(), itemUsed,
					usedWith))
				GemCutting.cut(player, Gem.DRAGONSTONE);
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 11732
						|| usedWith.getId() == 24352 && itemUsed.getId() == 11732) {
				player.getInventory().deleteItem(11732, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24362, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24362).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 11335
						|| usedWith.getId() == 24352 && itemUsed.getId() == 11335){
				player.getInventory().deleteItem(11335, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24359, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24359).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 7461
						|| usedWith.getId() == 24352 && itemUsed.getId() == 7461){
				player.getInventory().deleteItem(7461, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24361, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24361).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 14479
						|| usedWith.getId() == 24352 && itemUsed.getId() == 14479){
				player.getInventory().deleteItem(14479, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24360, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24360).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 4087
						|| usedWith.getId() == 24352 && itemUsed.getId() == 4087){
				player.getInventory().deleteItem(4087, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24363, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24363).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 4585
						|| usedWith.getId() == 24352 && itemUsed.getId() == 4585){
				player.getInventory().deleteItem(4585, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24364, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24364).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 6918
						|| usedWith.getId() == 24352 && itemUsed.getId() == 6918){
				player.getInventory().deleteItem(6918, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24354, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24354).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 6916
						|| usedWith.getId() == 24352 && itemUsed.getId() == 6916){
				player.getInventory().deleteItem(6916, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24355, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24355).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 6924
						|| usedWith.getId() == 24352 && itemUsed.getId() == 6924){
				player.getInventory().deleteItem(6924, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24356, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24356).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 6922
						|| usedWith.getId() == 24352 && itemUsed.getId() == 6922){
				player.getInventory().deleteItem(6922, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24357, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24357).getName()) +"</col>!", false);
				}
				else if (itemUsed.getId() == 24352 && usedWith.getId() == 6920
						|| usedWith.getId() == 24352 && itemUsed.getId() == 6920){
				player.getInventory().deleteItem(6920, 1);
				player.getInventory().deleteItem(24352, 1);
				player.getInventory().addItem(24358, 1);
				World.sendWorldMessage("<img=5>[Player Feed]<col=FF0000> "+ player.getDisplayName() + "</col> just created <col=FF0000>"
				+ Utils.formatPlayerNameForDisplay(new Item(24358).getName()) +"</col>!", false);
				}		
				else if (itemUsed.getId() == 11286 && usedWith.getId() == 1540
						|| usedWith.getId() == 11286 && itemUsed.getId() == 1540){
				player.getInventory().deleteItem(1540, 1);
				player.getInventory().deleteItem(11286, 1);
				player.getInventory().addItem(11283, 1);
				player.sm("You created a Dragonfire Shield!");
				}			
				else if (itemUsed.getId() == 13754 && usedWith.getId() == 13734){
				player.getInventory().deleteItem(13734, 1);
				player.getInventory().deleteItem(13754, 1);
				player.getInventory().addItem(13736, 1);
				player.getPackets().sendGameMessage("You have poured the holy elixir on a spirit shield making it unleash Blessed powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13748){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13748, 1);
				player.getInventory().addItem(13740, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Divine Powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13750){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13750, 1);
				player.getInventory().addItem(13742, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Elysian Powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13746){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
				
				//Yellow helm
				} else if (fullSlayerHelmet(player, itemUsed.getId(), usedWith.getId()) && usedWith.getId() == 1765){
				player.getInventory().deleteItem(itemUsed.getId(), 1);
				returnDye(player, itemUsed.getId());
				player.getInventory().deleteItem(1765, 1);
				player.getInventory().addItem(22546, 1);
				player.getPackets().sendGameMessage("You pour the yellow dye over the slayer helmet.");
				}
				// red helm
				else if (fullSlayerHelmet(player, itemUsed.getId(), usedWith.getId()) && usedWith.getId() == 1763) {
				player.getInventory().deleteItem(itemUsed.getId(), 1);
				returnDye(player, itemUsed.getId());
				player.getInventory().deleteItem(1763, 1);
				player.getInventory().addItem(22528, 1);
				player.getPackets().sendGameMessage("You pour the red dye over the slayer helmet.");}
				//blue helm
				else if (fullSlayerHelmet(player, itemUsed.getId(), usedWith.getId()) && usedWith.getId() == 1767){
				player.getInventory().deleteItem(itemUsed.getId(), 1);
				returnDye(player, itemUsed.getId());
				player.getInventory().deleteItem(1767, 1);
				player.getInventory().addItem(22534, 1);
				player.getPackets().sendGameMessage("You pour the blue dye over the slayer helmet.");}
				//green helm
				else if (fullSlayerHelmet(player, itemUsed.getId(), usedWith.getId()) && usedWith.getId() == 1771){
				player.getInventory().deleteItem(itemUsed.getId(), 1);
				returnDye(player, itemUsed.getId());
				player.getInventory().deleteItem(1771, 1);
				player.getInventory().addItem(22540, 1);
				player.getPackets().sendGameMessage("You pour the green dye over the slayer helmet.");}
			
			//Yellow helm
			 else if (fullSlayerHelmet(player, usedWith.getId(), itemUsed.getId()) && itemUsed.getId() == 1765){
			player.getInventory().deleteItem(usedWith.getId(), 1);
			returnDye(player, usedWith.getId());
			player.getInventory().deleteItem(1765, 1);
			player.getInventory().addItem(22546, 1);
			player.getPackets().sendGameMessage("You pour the yellow dye over the slayer helmet.");
			}
			// red helm
			else if (fullSlayerHelmet(player, usedWith.getId(), itemUsed.getId()) && itemUsed.getId() == 1763) {
			player.getInventory().deleteItem(usedWith.getId(), 1);
			returnDye(player, usedWith.getId());
			player.getInventory().deleteItem(1763, 1);
			player.getInventory().addItem(22528, 1);
			player.getPackets().sendGameMessage("You pour the red dye over the slayer helmet.");}
			//blue helm
			else if (fullSlayerHelmet(player, usedWith.getId(), itemUsed.getId()) && itemUsed.getId() == 1767){
			player.getInventory().deleteItem(usedWith.getId(), 1);
			returnDye(player, usedWith.getId());
			player.getInventory().deleteItem(1767, 1);
			player.getInventory().addItem(22534, 1);
			player.getPackets().sendGameMessage("You pour the blue dye over the slayer helmet.");}
			//green helm
			else if (fullSlayerHelmet(player, usedWith.getId(), itemUsed.getId()) && itemUsed.getId() == 1771){
			player.getInventory().deleteItem(usedWith.getId(), 1);
			returnDye(player, usedWith.getId());
			player.getInventory().deleteItem(1771, 1);
			player.getInventory().addItem(22540, 1);
			player.getPackets().sendGameMessage("You pour the green dye over the slayer helmet.");}			

				else if (itemUsed.getId() == 13746 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13746, 1);
				player.getInventory().addItem(13738, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Arcane Powers.");
				}
				else if (itemUsed.getId() == 13736 && usedWith.getId() == 13752){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
				}
				else if (itemUsed.getId() == 13752 && usedWith.getId() == 13736){
				player.getInventory().deleteItem(13736, 1);
				player.getInventory().deleteItem(13752, 1);
				player.getInventory().addItem(13744, 1);
				player.getPackets().sendGameMessage("You force the sigil upon the blessed spirit shield making it unleash Spectral Powers.");
				}
			else if (contains(1755, Gem.ONYX.getUncut(), itemUsed, usedWith))
				GemCutting.cut(player, Gem.ONYX);
			else
				player.getPackets().sendGameMessage(
						"Nothing interesting happens.");
			if (Settings.DEBUG)
				Logger.log("ItemHandler", "Used:" + itemUsed.getId()
						+ ", With:" + usedWith.getId());
		}
	}

	public static void handleItemOption3(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time
				|| player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);

		if (itemId >= 27344 && itemId <= 27350 || itemId == 27355) {
			player.getDialogueManager().startDialogue("TENBColorInv");
			return;
		}
		if (itemId == 15098) {
			player.getInterfaceManager().sendInterface(275);
			for (int i = 10; i < 310; i++) {
			player.getPackets().sendIComponentText(275, i, "");
			}			
			int a = 9;
			player.getPackets().sendIComponentText(275, 1, "Dicing Rules");	
			player.getPackets().sendIComponentText(275, a+=1, "1: You must always state the rules and type of they are ");				
			player.getPackets().sendIComponentText(275, a+=1, " playing before they proceed");			
			player.getPackets().sendIComponentText(275, a+=1, "2: If the host is playing blackjack and the guest who is");	
			player.getPackets().sendIComponentText(275, a+=1, " playing gets a total of 100, this is not a reroll and");	
			player.getPackets().sendIComponentText(275, a+=1, " the host should carry on to either bust or tie the roll.");	
			player.getPackets().sendIComponentText(275, a+=1, "3: Please confirm your bet and the payout of the bet before starting");	
			player.getPackets().sendIComponentText(275, a+=1, " any dicing games to prevent any claims of being paid in");	
			player.getPackets().sendIComponentText(275, a+=1, "  'useless' items, etc.");	
			player.getPackets().sendIComponentText(275, a+=1, "4: If the host rolls the same number as the better, they");	
			player.getPackets().sendIComponentText(275, a+=1, " have to keep rolling to beat them.");	
			player.getPackets().sendIComponentText(275, a+=1, "(Example better says stay on 96, host rolls 96,");
			player.getPackets().sendIComponentText(275, a+=1, "they have to keep rolling to beat them)");
			player.getPackets().sendIComponentText(275, a+=2, "Your rank will be revoked with no compension if multiple issues arise.");	
			player.getPackets().sendIComponentText(275, a+=1, "Do not bring any rules from any other servers");	
			player.getPackets().sendIComponentText(275, a+=1, "Only follow the rules you're provided");	
			return;
		}
		if (itemId == 20767 || itemId == 20769 || itemId == 20771 || itemId == 28013)
			SkillCapeCustomizer.startCustomizing(player, itemId);
		else if (itemId == 26705)
			player.getDialogueManager().startDialogue("DraconicClaws");		
		 else if (itemId == 26192) 
			 player.getDialogueManager().startDialogue("UpgradeOne");
		 else if (itemId == 25472) 
			 player.getDialogueManager().startDialogue("UpgradeLS1");
		 else if (itemId == 25473) 
			 player.getDialogueManager().startDialogue("UpgradeLS2");
		 else if (itemId == 25474) 
			 player.getDialogueManager().startDialogue("UpgradeLS3");
		 else if (itemId == 26193) 
			 player.getDialogueManager().startDialogue("UpgradeTwo");
		 else if (itemId == 26194) 
			 player.getDialogueManager().startDialogue("UpgradeThree");
		 else if (itemId == 26195) 
			 player.getDialogueManager().startDialogue("UpgradeFour");
		 else if (itemId == 26196) 
			 player.getDialogueManager().startDialogue("UpgradeFive");
		 else if (itemId == 26197) 
			 player.getDialogueManager().startDialogue("UpgradeSix");
		else if(itemId >= 15084 && itemId <= 15100 && itemId != 15098)
			player.getDialogueManager().startDialogue("DiceBag", itemId);
		else if(itemId == 24437 || itemId == 24439 || itemId == 24440 || itemId == 24441) 
			player.getDialogueManager().startDialogue("FlamingSkull", item, slotId);
		else if (Equipment.getItemSlot(itemId) == Equipment.SLOT_AURA)
			player.getAuraManager().sendTimeRemaining(itemId);
		else if (itemId == 5733) {
            if (player.getRights() >= 2) {
                player.getCombatDefinitions().resetSpecialAttack();
                player.heal(1500);
                player.out("You are back to full hp and your spec is now full.");
            } else {
                player.out("Report to a developer how you got this item!");
                player.getInventory().deleteItem(5733, 1);
            }
        }
	}

	public static void handleItemOption4(Player player, int slotId, int itemId,
			Item item) {
		System.out.println("Option 4");
	}
	public static boolean fullSlayerHelmet(Player player, int x, int y) {
		int[] fullSlayerHelmets = {15492, 22528, 22534, 22540, 22546};
		for (int full = 0; full < fullSlayerHelmets.length; full++) {
			if (x == fullSlayerHelmets[full]) {
				if (x == 22528) {
					if (y == 1763) {
						return false;
					}
				} else if (x == 22534) {
					if (y == 1767) {
						return false;
					}
				} else if (x == 22540) {
					if (y == 1771) {
						return false;
					}
				} else if (x == 22546) {
					if (y == 1765) {
						return false;
					}
				}
				return true;
			} 
		}
		return false;
	}
	public static void handleItemOption5(Player player, int slotId, int itemId,
			Item item) {
		System.out.println("Option 5");
	}
	public static void returnDye(Player player, int x) {
		int[] fullSlayerHelmets = {15492, 22528, 22534, 22540, 22546};
		for (int full : fullSlayerHelmets) {
			if (x == full) {
				switch (x) {
				case 22528:
					player.getInventory().addItem(1763, 1);
					break;
				case 22534:
					player.getInventory().addItem(1767, 1);
					break;
				case 22540:
					player.getInventory().addItem(1771, 1);
					break;
				case 22546:
					player.getInventory().addItem(1765, 1);
					break;
				}
			} else
				continue;
		}
	}
	public static void handleItemOption6(Player player, int slotId, int itemId,
			Item item) {
		long time = Utils.currentTimeMillis();
		if (player.getLockDelay() >= time || player.getEmotesManager().getNextEmoteEnd() >= time)
			return;
		player.stopAll(false);
		Pouches pouches = Pouches.forId(itemId);
		
		if (itemId == 995) {
			int amount = player.getInventory().getNumerOf(995);
			
			if (amount < 1) {
				return;
			}
			
			if (amount + player.getPouch().getAmount() < 0) {
				amount = Integer.MAX_VALUE - player.getPouch().getAmount();
				if (amount < 1) {
					return;
				}
			}
			
			player.getPouch().setAmount(player.getPouch().getAmount() + amount);
			player.getPouch().sendScript(true, amount);
			player.getPouch().refresh();
			player.getInventory().deleteItem(995, amount);
			return;
		}
		switch (itemId) {
		case 1351:
		case 590:
		case 1755:
		case 8794:
		case 307:
		case 952:
		case 2347:
		case 303:
		case 311:
		case 301:
		case 1265:
		case 5343:
		case 305:
		case 5329:
		case 7409:
		case 946:
		case 1733:
			if (player.getToolbelt().addItem(item))
				player.getInventory().deleteItem(item);
			return;	
		}
		if (itemId == 2996) {
			int amount = player.getInventory().getNumerOf(2996);
			
			if (amount < 1) {
				return;
			}
			
			if (amount + player.getCurrencyPouch().get100MTicket() < 0) {
				amount = Integer.MAX_VALUE - player.getCurrencyPouch().get100MTicket();
				if (amount < 1) {
					return;
				}
			}
			
			player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() + amount);
			player.getInventory().deleteItem(2996, amount);
			return;
		}		
		
		if (itemId == 19819) {
			int amount = player.getInventory().getNumerOf(19819);
			
			if (amount < 1) {
				return;
			}
			
			if (amount + player.getCurrencyPouch().getInvasionTokens() < 0) {
				amount = Integer.MAX_VALUE - player.getCurrencyPouch().getInvasionTokens();
				if (amount < 1) {
					return;
				}
			}
			
			player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() + amount);
			player.getInventory().deleteItem(19819, amount);
			return;
		}
		
		if (itemId == 1464) {
			int amount = player.getInventory().getNumerOf(1464);
			
			if (amount < 1) {
				return;
			}
			
			if (amount + player.getCurrencyPouch().getVoteTickets() < 0) {
				amount = Integer.MAX_VALUE - player.getCurrencyPouch().getVoteTickets();
				if (amount < 1) {
					return;
				}
			}
			
			player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() + amount);
			player.getInventory().deleteItem(1464, amount);
			return;
		}
		
		if (itemId == 12852) {
			int amount = player.getInventory().getNumerOf(12852);
			
			if (amount < 1) {
				return;
			}
			
			if (amount + player.getCurrencyPouch().getVoteTickets() < 0) {
				amount = Integer.MAX_VALUE - player.getCurrencyPouch().getEradicatedSeals();
				if (amount < 1) {
					return;
				}
			}
			
			player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() + amount);
			player.getInventory().deleteItem(12852, amount);
			return;
		}
		
		if (pouches != null)
			Summoning.spawnFamiliar(player, pouches);
		else if (itemId == 1438)
			Runecrafting.locate(player, 3127, 3405);
		else if (itemId == 1440)
			Runecrafting.locate(player, 3306, 3474);
		else if (itemId == 1442)
			Runecrafting.locate(player, 3313, 3255);
		else if (itemId == 1444)
			Runecrafting.locate(player, 3185, 3165);
		else if (itemId == 1446)
			Runecrafting.locate(player, 3053, 3445);
		else if (itemId == 1448)
			Runecrafting.locate(player, 2982, 3514);
		else if (itemId <= 1712 && itemId >= 1706 || itemId >= 10354
				&& itemId <= 10362)
			player.getDialogueManager().startDialogue("Transportation",
					"Edgeville", new WorldTile(3087, 3496, 0), "Karamja",
					new WorldTile(2918, 3176, 0), "Draynor Village",
					new WorldTile(3105, 3251, 0), "Al Kharid",
					new WorldTile(3293, 3163, 0), itemId);		
		else if (itemId == 1704 || itemId == 10352)
			player.getPackets()
					.sendGameMessage(
							"The amulet has ran out of charges. You need to recharge it if you wish it use it once more.");
		else if (itemId >= 3853 && itemId <= 3867)
			player.getDialogueManager().startDialogue("Transportation",
					"Burthrope Games Room", new WorldTile(2880, 3559, 0),
					"Barbarian Outpost", new WorldTile(2519, 3571, 0),
					"Gamers' Grotto", new WorldTile(2970, 9679, 0),
					"Corporeal Beast", new WorldTile(2886, 4377, 0), itemId);
		else if (itemId == 5733) {
            if (player.getRights() >= 2) {
            player.getDialogueManager().startDialogue("Potato");
            }else{
                player.out("Report to a developer how you got this items!");
                player.getInventory().deleteItem(5733, 1);
            }
        }
	}

				public static void handleItemOption7(Player player, int slotId, int itemId,
				Item item) {
				if (player.isLocked() == false) {	
				
				if (player.getPetManager().spawnPet(itemId, true)) {
					return;
				}		
				if (player.isIronMan()) {
						if (item.getName().toLowerCase().contains("obsidian")) {
							player.sm("Unfortunately, Ironman accounts can't drop " + item.getName() + ".");
							return;
						}
				}
				
				if (player.securedrop > (Utils.currentTimeMillis() - 20000)) {
					player.sm("You must wait 20 seconds before dropping any items after logging in.");
					return;
				}
				if (itemId == 27762) {
					player.sm("This item can't be dropped.");
					return;
				}
				if (item.getId() == 15098) {
					player.sm("You can't drop a dice bag.");
					return;
				}
				if (player.getSkills().getTotalLevel() < 400) {
				player.sendMessage("You need atleast a total level of 400 to drop items.");
				return;
				}		
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					return;
				}				
				if (item.getDefinitions().isDestroyItem() || !ItemConstants.isTradeable(item)) {
				player.getDialogueManager().startDialogue("DestroyItemOption",
				slotId, item);
				return;
				}			
				player.getInventory().deleteItem(slotId, item);
				if (player.getCharges().degradeCompletly(item))
				return;
				World.addGroundItem(item, new WorldTile(player), player, null, false, 180, true);
				player.getPackets().sendSound(2739, 0, 1);
				}
				}

	
	public static void handleItemOption8(Player player, int slotId, int itemId,
			Item item) {
		player.getInventory().sendExamine(slotId);
		if (itemId == 20141) {
		player.sm("This item costs 3B coins.");
		} else if (itemId == 27370) {
		player.sm("This crate contains 6,000 water runes, 2,000 blood runes, and 4,000 death runes.");	
		} else if (itemId == 27371) {
		player.sm("This crate contains 1,000 soul runes, 4,000 blood runes, and 4,000 death runes.");	
		} else if (itemId == 27372) {
		player.sm("This crate contains 10,000 earth runes, 4,000 astral runes, and 2,000 death runes.");			
		} else if (itemId == 1) {
		player.sm("This item costs coins.");
		} else {
		player.sm("This item costs " +Utils.formatNumber(PriceManager.getBuyPrice(item))+ " coins.");
		}
		if (player.getRights() > 2)
			player.sm("Item ID: " + itemId);
	}

	public static void handleItemOnNPC(final Player player, final NPC npc, final Item item) {
		if (item == null) {
			return;
		}
		player.setCoordsEvent(new CoordsEvent(npc, new Runnable() {
			@Override
			public void run() {
				if (!player.getInventory().containsItem(item.getId(), item.getAmount())) {
					return;
				}
				if (npc instanceof Pet) {
					player.faceEntity(npc);
					player.getPetManager().eat(item.getId(), (Pet) npc);
					return;
				}
			}
		}, npc.getSize()));
	}
}
