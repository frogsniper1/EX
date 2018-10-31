package com.rs.game.player.content.perks;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.utils.Colors;

public class PerksManager{

	/**
	 * A list of available perks.
	 */
	public boolean unliRun, sleightOfHand, masterFisherman, prayerBetrayer, avasSecret, keyExpert, dragonTrainer, gwdSpecialist, dungeon, petChanter, perslaysion,
			overclocked, elfFiend, masterChef, masterDiviner, quarryMaster, miniGamer, masterFledger, thePiromaniac,
			huntsman, portsMaster, investigator, divineDoubler, imbuedFocus, alchemicSmith, lifeSteal, regenerator,
			butcher, slayerporter, basher, unDead, reflector, petmaster, petlooter, solomon;
	/**
	 * The player instance.
	 */
	private transient Player player;

	/**
	 * The player instance saving to.
	 *
	 * @param player
	 *            The player.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Lets shorten the line here. Have to love our eyes man :(.
	 *
	 * @param line
	 *            The interID to print.
	 * @param message
	 *            The String to print.
	 */
	private void sendText(int line, String message) {
		player.getPackets().sendIComponentText(275, line, "<shad=000000>" + message);
	}

	/**
	 * Displays players activated game perks.
	 */
	public void displayAvailablePerks() {
		for (int i = 0; i < 309; i++)
			player.getPackets().sendIComponentText(275, i, "");
		sendText(1, Colors.RED + Settings.SERVER_NAME + " Game Perks");
		sendText(10, Colors.CYAN + "Game Perks can be purchased from our ::store.");
		sendText(11, Colors.RED + "Red - not active</col>  -  " + Colors.GREEN + "Green - active</col>.");
		sendText(12, "</shad>----------------------");
		sendText(13, (unliRun ? Colors.GREEN : Colors.RED) + "[1] Unlimited Run");
		sendText(14, (sleightOfHand ? Colors.GREEN : Colors.RED) + "[2] Sleight of Hand");
		sendText(15, (masterFisherman ? Colors.GREEN : Colors.RED) + "[3] Master Fisherman");
		sendText(16, (prayerBetrayer ? Colors.GREEN : Colors.RED) + "[4] Prayer Betrayer");
		sendText(17, (avasSecret ? Colors.GREEN : Colors.RED) + "[5] Ava's Effect");
		//sendText(13, (solomon ? Colors.GREEN : Colors.RED) + "[1] Solomon");
		//sendText(14, (unliRun ? Colors.GREEN : Colors.RED) + "[2] Unlimited Run");
		//sendText(15, (greenThumb ? Colors.GREEN : Colors.RED) + "[3] Green Thumb");
		//sendText(16, (birdMan ? Colors.GREEN : Colors.RED) + "[4] Bird Man");
		//sendText(17, (unbreakableForge ? Colors.GREEN : Colors.RED) + "[5] Unbreakable Forge");
		//sendText(18, (sleightOfHand ? Colors.GREEN : Colors.RED) + "[6] Sleight of Hand");
		//sendText(19, (familiarExpert ? Colors.GREEN : Colors.RED) + "[7] Familiar Expert");
		//sendText(20, (chargeBefriender ? Colors.GREEN : Colors.RED) + "[8] Charge Befriender");
		//sendText(21, (charmCollector ? Colors.GREEN : Colors.RED) + "[9] Charm Collector");
		//sendText(22, (herbivore ? Colors.GREEN : Colors.RED) + "[10] Herbivore");
		//sendText(23, (masterFisherman ? Colors.GREEN : Colors.RED) + "[11] Master Fisherman");
		/*sendText(24, (delicateCraftsman ? Colors.GREEN : Colors.RED) + "[12] Delicate Craftsman");
		sendText(25, (coinCollector ? Colors.GREEN : Colors.RED) + "[13] Coin Collector");
		sendText(26, (prayerBetrayer ? Colors.GREEN : Colors.RED) + "[14] Prayer Betrayer");
		sendText(27, (avasSecret ? Colors.GREEN : Colors.RED) + "[15 Avas Secret");
		sendText(28, (keyExpert ? Colors.GREEN : Colors.RED) + "[16] Key Expert");
		sendText(29, (dragonTrainer ? Colors.GREEN : Colors.RED) + "[17] Dragon Trainer");
		sendText(30, (gwdSpecialist ? Colors.GREEN : Colors.RED) + "[18] GWD Specialist");
		sendText(31, (dungeon ? Colors.GREEN : Colors.RED) + "[19] Dungeons Master");
		sendText(32, (petChanter ? Colors.GREEN : Colors.RED) + "[20] Pet'chanter");
		sendText(33, (perslaysion ? Colors.GREEN : Colors.RED) + "[21] Per'slay'sion");
		sendText(34, (overclocked ? Colors.GREEN : Colors.RED) + "[22] Overclocked");
		sendText(35, (elfFiend ? Colors.GREEN : Colors.RED) + "[23] Elf Fiend");
		sendText(36, (masterChef ? Colors.GREEN : Colors.RED) + "[24] Master Chefs Man");
		sendText(37, (masterDiviner ? Colors.GREEN : Colors.RED) + "[25] Master Diviner");
		sendText(38, (quarryMaster ? Colors.GREEN : Colors.RED) + "[26] Quarrymaster");
		sendText(39, (miniGamer ? Colors.GREEN : Colors.RED) + "[27] The Mini-Gamer");
		sendText(40, (masterFledger ? Colors.GREEN : Colors.RED) + "[28] Master Fledger");
		sendText(41, (thePiromaniac ? Colors.GREEN : Colors.RED) + "[29] The Piromaniac");
		sendText(42, (huntsman ? Colors.GREEN : Colors.RED) + "[30] Huntsman");
		sendText(43, (portsMaster ? Colors.GREEN : Colors.RED) + "[31] Ports Master");
		sendText(44, (investigator ? Colors.GREEN : Colors.RED) + "[32] Investigator");
		sendText(45, (divineDoubler ? Colors.GREEN : Colors.RED) + "[33] Divine Doubler");
		sendText(46, (imbuedFocus ? Colors.GREEN : Colors.RED) + "[34] Imbued Focus");
		sendText(47, (alchemicSmith ? Colors.GREEN : Colors.RED) + "[35] Alchemic Smithing");
		sendText(48, (lifeSteal ? Colors.GREEN : Colors.RED) + "[36] Life Stealer");
		sendText(49, (regenerator ? Colors.GREEN : Colors.RED) + "[37] Regen'erator");
		sendText(50, (butcher ? Colors.GREEN : Colors.RED) + "[38] The Butcher");
		sendText(51, (slayerporter ? Colors.GREEN : Colors.RED) + "[39] Slayerporter");
		sendText(52, (basher ? Colors.GREEN : Colors.RED) + "[40] Basher");
		sendText(53, (unDead ? Colors.GREEN : Colors.RED) + "[41] UnDead");
		sendText(54, (reflector ? Colors.GREEN : Colors.RED) + "[42] Reflector");
		sendText(55, (petmaster ? Colors.GREEN : Colors.RED) + "[43] PetMaster");
		sendText(56, (petlooter ? Colors.GREEN : Colors.RED) + "[44] PetLooter");*/
		//sendText(57, (solomon ? Colors.GREEN : Colors.RED) + "[45] Solomon");
		player.getInterfaceManager().sendInterface(275);
	}
	
	public void buyPerk(int productId) {
		switch(productId) {
		case 1:
			//if(player.getPerksManager().bankCommand) {
			//	player.sm("You already bought this perk.");
			//	break;
			//}
			//if(!checkInv(25))
			//	break;
			//player.sm(Colors.GREEN+"You succesfully bought bank command!");
			//player.getPerksManager().displayAvailablePerks();
			//break;
		case 2:
			if(player.getPerksManager().unliRun) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().unliRun = true;
			player.sm(Colors.GREEN+"You succesfully bought Unli Run!");
			player.getPerksManager().displayAvailablePerks();
			break;
		/*case 3:
			if(player.getPerksManager().greenThumb) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().greenThumb = true;
			player.sm(Colors.GREEN+"You succesfully bought Green Thumb!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 4:
			if(player.getPerksManager().birdMan) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(1))
				break;
			player.getPerksManager().birdMan = true;
			player.sm(Colors.GREEN+"You succesfully bought Bird Man!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 5:
			if(player.getPerksManager().unbreakableForge) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(1))
				break;
			player.getPerksManager().unbreakableForge = true;
			player.sm(Colors.GREEN+"You succesfully bought Unbreakable Forge!");
			player.getPerksManager().displayAvailablePerks();
			break;*/
		case 6:
			if(player.getPerksManager().sleightOfHand) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(4))
				break;
			player.getPerksManager().sleightOfHand = true;
			player.sm(Colors.GREEN+"You succesfully bought Sleight of Hand!");
			player.getPerksManager().displayAvailablePerks();
			break;
		/*case 7:
			if(player.getPerksManager().familiarExpert) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().familiarExpert = true;
			player.sm(Colors.GREEN+"You succesfully bought Familiar Expert!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 8:
			if(player.getPerksManager().chargeBefriender) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(25))
				break;
			player.getPerksManager().chargeBefriender = true;
			player.sm(Colors.GREEN+"You succesfully bought Charge Befriender!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 9:
			if(player.getPerksManager().charmCollector) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().charmCollector = true;
			player.sm(Colors.GREEN+"You succesfully bought Charm Collector!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 10:
			if(player.getPerksManager().herbivore) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(8))
				break;
			player.getPerksManager().herbivore = true;
			player.sm(Colors.GREEN+"You succesfully bought Herbivore!");
			player.getPerksManager().displayAvailablePerks();
			break;*/
		case 11:
			if(player.getPerksManager().masterFisherman) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().masterFisherman = true;
			player.sm(Colors.GREEN+"You succesfully bought Master Fisherman!");
			player.getPerksManager().displayAvailablePerks();
			break;
		/*case 12:
			if(player.getPerksManager().delicateCraftsman) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().delicateCraftsman = true;
			player.sm(Colors.GREEN+"You succesfully bought Delicate Craftsman!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 13:
			if(player.getPerksManager().coinCollector) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().coinCollector = true;
			player.sm(Colors.GREEN+"You succesfully bought Coin Collector!");
			player.getPerksManager().displayAvailablePerks();
			break;*/
		case 14:
			if(player.getPerksManager().prayerBetrayer) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(10))
				break;
			player.getPerksManager().prayerBetrayer = true;
			player.sm(Colors.GREEN+"You succesfully bought Prayer Betrayer!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 15:
			if(player.getPerksManager().avasSecret) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().avasSecret = true;
			player.sm(Colors.GREEN+"You succesfully bought Avas Secret!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 16:
			if(player.getPerksManager().keyExpert) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(6))
				break;
			player.getPerksManager().keyExpert = true;
			player.sm(Colors.GREEN+"You succesfully bought Key Expert!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 17:
			if(player.getPerksManager().dragonTrainer) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().dragonTrainer = true;
			player.sm(Colors.GREEN+"You succesfully bought Dragon Trainer!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 18:
			if(player.getPerksManager().gwdSpecialist) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().gwdSpecialist = true;
			player.sm(Colors.GREEN+"You succesfully bought GWD Specialist!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 19:
			if(player.getPerksManager().dungeon) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(10))
				break;
			player.getPerksManager().dungeon = true;
			player.sm(Colors.GREEN+"You succesfully bought Dungeons Master!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 20:
			if(player.getPerksManager().petChanter) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().petChanter = true;
			player.sm(Colors.GREEN+"You succesfully bought Pet'chanter!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 21:
			if(player.getPerksManager().perslaysion) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(10))
				break;
			player.getPerksManager().perslaysion = true;
			player.sm(Colors.GREEN+"You succesfully bought Per'slay'sion!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 22:
			if(player.getPerksManager().overclocked) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(6))
				break;
			player.getPerksManager().overclocked = true;
			player.sm(Colors.GREEN+"You succesfully bought Overclocked!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 23:
			if(player.getPerksManager().elfFiend) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(4))
				break;
			player.getPerksManager().elfFiend = true;
			player.sm(Colors.GREEN+"You succesfully bought Elf Fiend!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 24:
			if(player.getPerksManager().masterChef) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(3))
				break;
			player.getPerksManager().masterChef = true;
			player.sm(Colors.GREEN+"You succesfully bought Master Chefs Man!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 25:
			if(player.getPerksManager().masterDiviner) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(10))
				break;
			player.getPerksManager().masterDiviner = true;
			player.sm(Colors.GREEN+"You succesfully bought Master Diviner!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 26:
			if(player.getPerksManager().quarryMaster) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().quarryMaster = true;
			player.sm(Colors.GREEN+"You succesfully bought Quarrymaster!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 27:
			if(player.getPerksManager().miniGamer) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().miniGamer = true;
			player.sm(Colors.GREEN+"You succesfully bought The Mini-Gamer!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 28:
			if(player.getPerksManager().masterFledger) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(6))
				break;
			player.getPerksManager().masterFledger = true;
			player.sm(Colors.GREEN+"You succesfully bought Master Fledger!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 29:
			if(player.getPerksManager().thePiromaniac) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().thePiromaniac = true;
			player.sm(Colors.GREEN+"You succesfully bought The Piromaniac!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 30:
			if(player.getPerksManager().huntsman) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(6))
				break;
			player.getPerksManager().huntsman = true;
			player.sm(Colors.GREEN+"You succesfully bought Huntsman!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 31:
			if(player.getPerksManager().portsMaster) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(4))
				break;
			player.getPerksManager().portsMaster = true;
			player.sm(Colors.GREEN+"You succesfully bought Ports Master!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 32:
			if(player.getPerksManager().investigator) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))
				break;
			player.getPerksManager().investigator = true;
			player.sm(Colors.GREEN+"You succesfully bought Investigator!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 33:
			if(player.getPerksManager().divineDoubler) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(10))
				break;
			player.getPerksManager().divineDoubler = true;
			player.sm(Colors.GREEN+"You succesfully bought Divine Doubler!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 34:
			if(player.getPerksManager().imbuedFocus) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(5))
				break;
			player.getPerksManager().imbuedFocus = true;
			player.sm(Colors.GREEN+"You succesfully bought Imbued Focus!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 35:
			if(player.getPerksManager().alchemicSmith) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(7))
				break;
			player.getPerksManager().alchemicSmith = true;
			player.sm(Colors.GREEN+"You succesfully bought Alchemic Smithing!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 36:
			if(player.getPerksManager().lifeSteal) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(65))
				break;
			player.getPerksManager().lifeSteal = true;
			player.sm(Colors.GREEN+"You succesfully bought Life Stealer!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 37:
			if(player.getPerksManager().regenerator) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().regenerator = true;
			player.sm(Colors.GREEN+"You succesfully bought Regenerator!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 38:
			if(player.getPerksManager().butcher) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().butcher = true;
			player.sm(Colors.GREEN+"You succesfully bought The Butcher!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 39:
			if(player.getPerksManager().slayerporter) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().slayerporter = true;
			player.sm(Colors.GREEN+"You succesfully bought Slayerporter!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 40:
			if(player.getPerksManager().basher) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().basher = true;
			player.sm(Colors.GREEN+"You succesfully bought Basher!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 41:
			if(player.getPerksManager().unDead) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().unDead = true;
			player.sm(Colors.GREEN+"You succesfully bought UnDead!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 42:
			if(player.getPerksManager().reflector) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().reflector = true;
			player.sm(Colors.GREEN+"You succesfully bought Reflector!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 43:
			if(player.getPerksManager().petmaster) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().petmaster = true;
			player.sm(Colors.GREEN+"You succesfully bought Pet Master!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 44:
			if(player.getPerksManager().petlooter) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().petlooter = true;
			player.sm(Colors.GREEN+"You succesfully bought Pet Looter!");
			player.getPerksManager().displayAvailablePerks();
			break;
		case 45:
			if(player.getPerksManager().solomon) {
				player.sm("You already bought this perk.");
				break;
			}
			if(!checkInv(15))//price ng perk dito
				break;
			player.getPerksManager().solomon = true;
			player.sm(Colors.GREEN+"You succesfully bought Solomon!");
			player.getPerksManager().displayAvailablePerks();
			break;
			default:
				player.sm(Colors.RED+"The Perk ID does not exist");
				break;
		}
	}
	
	private boolean checkInv(int amount) {
		final int tokens = 32523;
		int tokensCount = player.getInventory().getNumerOf(tokens);
		if(tokensCount >= amount) {
			delete(tokens, amount);
			return true;
		}
		player.sm(Colors.RED+"You don't have enough tokens.");
		return false;
	}
	
	private void delete(int tokens, int amount) {
		player.getInventory().deleteItem(tokens, amount);
	}
	
	
}
