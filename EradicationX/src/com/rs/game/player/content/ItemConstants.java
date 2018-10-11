package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.QuestManager.Quests;

public class ItemConstants {

	public static int getDegradeItemWhenWear(int id) {
		// pvp armors
		if (id == 1205 )
			return id + 2; // if you wear it it becomes corrupted LOL
		return -1;
	}

	// return amt of charges
	public static int getItemDefaultCharges(int id) {
		// pvp armors
		if (id == 13952)
			return 1500;
		if (id == 13975)
			return 3000;
		if (id == 13960)
			return 6000; // 1hour
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return 60000;
		return -1;
	}

	// return what id it degrades to, -1 for disapear which is default so we
	// dont add -1
	public static int getItemDegrade(int id) {
		if (id == 11285) // DFS
			return 11283;
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return id + 1;
		return -1;
	}

	public static int getDegradeItemWhenCombating(int id) {
		// nex armors
		if (id == 20135 || id == 20139 || id == 20143 || id == 20147
				|| id == 20151 || id == 20155 || id == 20159 || id == 20163
				|| id == 20167 || id == 20171)
			return id + 2;
		return -1;
	}

	public static boolean itemDegradesWhileHit(int id) {
		if (id == 2550)
			return true;
		return false;
	}

	public static boolean itemDegradesWhileWearing(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName()
				.toLowerCase();
		if (name.contains("c. dragon") || name.contains("corrupt dragon")
				|| name.contains("morrigan's") || name.contains("zuriel's"))
			return true;
		return false;
	}

	public static boolean itemDegradesWhileCombating(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName()
				.toLowerCase();
		// nex armors
		if (name.contains("torva") || name.contains("pernix")
				|| name.contains("virtux") || name.contains("zaryte"))
			return true;
		return false;
	}

	public static boolean canWear(Item item, Player player) {
		if(player.getRights() == 2)
			return true;
		if (player.getRights() == 7)
			return true;
		if ((item.getId() == 20769 || item.getId() == 20771)) {
		if (!player.checkCompletionistCapeRequirements()) {
				player.sm("You need to complete all achievements to wield this cape.");
				return false;
		}
		} else if (item.getId() == 6570) {
			if (!player.isCompletedFightCaves()) {
				player.getPackets()
						.sendGameMessage(
								"You need to complete fightcaves before you can equip firecape.");
				return false;
			}
		} else if (item.getId() == 29950) {
        	int exp = (int) player.getSkills().getXp(Skills.THIEVING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");
				return false;
            }
		} else if (item.getId() == 29951) {
        	int exp = (int) player.getSkills().getXp(Skills.WOODCUTTING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29952) {
        	int exp = (int) player.getSkills().getXp(Skills.SUMMONING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");         
				return false;
            }
		} else if (item.getId() == 15492 
				|| item.getId() == 22528
				|| item.getId() == 22534
				|| item.getId() == 22540
				|| item.getId() == 22546) {
			if (!player.isCompletedFullSlayerHelmet())
				player.setCompletedFullSlayerHelmet();
		} else if (item.getId() == 29953) {
        	int exp = (int) player.getSkills().getXp(Skills.STRENGTH);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29954) {
        	int exp = (int) player.getSkills().getXp(Skills.SMITHING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");   
				return false;
            }
		} else if (item.getId() == 29955) {
        	int exp = (int) player.getSkills().getXp(Skills.SLAYER);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29956) {
        	int exp = (int) player.getSkills().getXp(Skills.RUNECRAFTING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29957) {
        	int exp = (int) player.getSkills().getXp(Skills.RANGE);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29958) {
        	int exp = (int) player.getSkills().getXp(Skills.PRAYER);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29959) {
        	int exp = (int) player.getSkills().getXp(Skills.MINING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29960) {
        	int exp = (int) player.getSkills().getXp(Skills.MAGIC);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29961) {
        	int exp = (int) player.getSkills().getXp(Skills.HUNTER);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29962) {
        	int exp = (int) player.getSkills().getXp(Skills.HITPOINTS);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29963) {
        	int exp = (int) player.getSkills().getXp(Skills.HERBLORE);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape."); 
				return false;
            }
		} else if (item.getId() == 29964) {
        	int exp = (int) player.getSkills().getXp(Skills.FLETCHING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");   
				return false;
            }
		} else if (item.getId() == 29965) {
        	int exp = (int) player.getSkills().getXp(Skills.FISHING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");   
				return false;
            }
		} else if (item.getId() == 29966) {
        	int exp = (int) player.getSkills().getXp(Skills.FIREMAKING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");     
				return false;
            }
		} else if (item.getId() == 29967) {
        	int exp = (int) player.getSkills().getXp(Skills.FARMING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");    
				return false;
            }
		} else if (item.getId() == 29968) {
        	int exp = (int) player.getSkills().getXp(Skills.DEFENCE);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");   
				return false;
            }
		} else if (item.getId() == 29969) {
        	int exp = (int) player.getSkills().getXp(Skills.CRAFTING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");   
				return false;
            }
		} else if (item.getId() == 29970) {
        	int exp = (int) player.getSkills().getXp(Skills.COOKING);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29971) {
        	int exp = (int) player.getSkills().getXp(Skills.CONSTRUCTION);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29972) {
        	int exp = (int) player.getSkills().getXp(Skills.ATTACK);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }
		} else if (item.getId() == 29973) {
        	int exp = (int) player.getSkills().getXp(Skills.AGILITY);
            if (exp < 200000000) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape.");  
				return false;
            }	
		} else if (item.getId() == 27355 || item.getId() == 27344 
		|| item.getId() == 27345 || item.getId() == 27346
		|| item.getId() == 27347 || item.getId() == 27348
		|| item.getId() == 27349 || item.getId() == 27350) {
				if (!player.check10BRequirements()) {			
				player.getPackets().sendGameMessage("You do not meet the requirements for this cape. You must get 10,000,000,000 XP.");  
				return false;
            }		
		} else if (item.getId() == 6194 ) {
			if (!player.checkAmuletofCompletion()) {
				player.getPackets().sendGameMessage("You do not meet the requirements for this amulet. You must complete all the achievements in the trophy tab."); 
				return false;
			}
		} else if (item.getId() == 14642 || item.getId() == 14645
				|| item.getId() == 15433 || item.getId() == 15435
				|| item.getId() == 14641 || item.getId() == 15432
				|| item.getId() == 15434) {
			if (!player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)) {
				player.getPackets()
						.sendGameMessage(
								"You need to have completed Nomad's Requiem miniquest to use this cape.");
				return false;
			}
		}
		return true;
	}

	public static boolean isTradeable(Item item) {
		if (item.getDefinitions().getName().toLowerCase().contains("flaming skull"))
			return false;
		String name = ItemDefinitions.getItemDefinitions(item.getId()).getName().toLowerCase();
		if (name.contains("tokhaar-kal") || name.contains("mithril seed")
				|| name.contains("lucky")
				|| name.contains("flaming skull")
				|| name.contains("upgraded completionist cape")
				|| name.contains("mod")
				|| name.contains("master cape")
				|| name.contains("tzrek-jad")
				|| name.contains("zanik")
				|| name.contains("creeping hand")
				|| name.contains("abyssal whip (lime)")		
				|| name.contains("fist of guthix") || name.contains("pk token")
				|| name.contains("dice") || name.contains("fire cape"))
			return false;
		switch (item.getId()) {
		case 6570: // firecape
		case 6529: // tokkul
		case 24155:
		case 29852:
		case 29853:
		case 29854:
		case 29856:
		case 29857:
		case 29862:
		case 9736:
		case 10092:
		case 18747:
		case 15706:
		case 2468:
		case 19445:
		case 19447:
		case 2934:
		case 2942:
		case 20767:
		case 13263: // Slayer helms
		case 15492:
		case 22528:
		case 22534:
		case 22540:
		case 22546:
		case 20768:
		case 21512: //jad pet
		case 28013:
		case 6194: 
		case 27355:
		case 27344:
		case 27345:
		case 27346:
		case 27347:
		case 27348:
		case 27349:
		case 27350:		//5b comp cape
		case 14652: //creeping hand
		case 20748:
		case 20749:
		case 20752:
		case 20753:
		case 20754:
		case 20769:
		case 20770:
		case 10724: //skeleton suit
		case 10725: //skeleton suit
		case 10726: //skeleton suit
		case 10727: //skeleton suit
		case 10728: //skeleton suit
		case 7671: //boxing gloves
		case 7673: //boxing gloves
		case 29979: //wildyboss project
		case 29978: //wildyboss project
		case 9814:
		case 10662:
		case 20771:
		case 27086: // BPet start
		case 27087:
		case 27088:
		case 27089:
		case 27090:
		case 27091:
		case 27092:
		case 27093:
		case 27094:
		case 27095:
		case 27096:
		case 27110:
		case 27111:
		case 27112:
		case 27743:
		case 27747:
		case 27761:
		case 27764:
		case 27765:
		case 27097:
		case 27098:
		case 29855:
		case 27735:
		case 27736:
		case 27737:
		case 27738:
		case 27739:
		case 27740:
		case 27741:
		case 27742:
		case 29858:
		case 27762:
		case 29859:
		case 6196:
		case 29860:
		case 29861:
		case 27099:
		case 27100:
		case 27101:
		case 13328:
		case 27102:
		case 27103:
		case 27104:
		case 27105:
		case 27106:
		case 15246:
		case 27107:
		case 27108: // BPet end			
		case 29922:
		case 20772:
		case 24154:
		case 7462: // barrow gloves
		case 23659: // tookhaar-kal
			return false;
		default:
			return true;
		}
	}
}
