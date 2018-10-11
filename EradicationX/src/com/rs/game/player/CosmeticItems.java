package com.rs.game.player;

import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;


public enum CosmeticItems {
	PALADIN(26790, 26466, 26468, 26470, 26472, -1, -1),
	TETSU(26325, 26326, 26327, -1, -1, -1, -1),
	TOKHAAR_WARLORD(-1, 26184, 26186, 26190, 26188, -1, -1),
	
	OBSIDIAN_MELEE(26128, 26140, 26136, 26126, 26124, -1, 26134),
	OBSIDIAN_MAGE(26132, 26140, 26136, 26126, 26124, -1, 26134),
	OBSIDIAN_RANGED(26130, 26140, 26136, 26126, 26124, -1, 26134),
	
	KRILS(25374, 25376, 25378, 25382, 25380, -1, -1),
	KRILS_GODCRUSHER(25386, 25388, 25390, 25394, 25392, -1, -1),
	
	DRAGON_BONE(24359, 24360, 24363, 24362, 24361, -1, -1),
	//        helm,   chest, legs, boots, gloves, cape, shield
	CRAFTING(25185, 25186, 25187, 25188, 25189, -1, -1),	
	KALPHITE(27994, 28791, 28792, 28794, 28793, 28790, -1),	
	WARSUIT(25273, 25275, 25277, 25281, -1, -1, -1),		
	SOUS_CHEF(25180, 25181, 25182, 25183, 25184, -1, -1),
	SANTA(1050, 14600, 14603, 14605, 14602, -1, -1),	
	BOTANIST(25190, 25191, 25192, 25193, 25194, -1, -1),
	PRIMAL(16711, 17259, 20823, 16359, 16293, -1, -1),
	BLACKSMITH(25195, 25196, 25197, 25198, 25199, -1, -1),
	FISHING(24423, 24424, 24425, 24426, -1, -1, -1),
	FACTORY(22959, 22958, 22960, 22962, 22961, -1, -1),
	
	PALADIN2(-1, 26466, 26468, 26470, 26472, -1, -1),
	KRILS2(-1, 25376, 25378, 25382, 25380, -1, -1),
	KRILS_GODCRUSHER2(-1, 25388, 25390, 25394, 25392, -1, -1),
	
	DRAGON_BONE2(-1, 24360, 24363, 24362, 24361, -1, -1),
	//        helm,   chest, legs, boots, gloves, cape, shield
	CRAFTING2(-1, 25186, 25187, 25188, 25189, -1, -1),	
	KALPHITE2(-1, 28791, 28792, 28794, 28793, -1, -1),	
	WARSUIT2(-1, 25275, 25277, 25281, -1, -1, -1),		
	SOUS_CHEF2(-1, 25181, 25182, 25183, 25184, -1, -1),
	SANTA2(-1, 14600, 14603, 14605, 14602, -1, -1),	
	BOTANIST2(-1, 25191, 25192, 25193, 25194, -1, -1),
	PRIMAL2(-1, 17259, 20823, 16359, 16293, -1, -1),
	BLACKSMITH2(-1, 25196, 25197, 25198, 25199, -1, -1),
	FISHING2(-1, 24424, 24425, 24426, -1, -1, -1),
	FACTORY2(-1, 22958, 22960, 22962, 22961, -1, -1),	
	AGILE(-1, 14936, 14938, -1, -1, -1, -1),	
	WARCHIEF(15033, 15034, 15035, 15037, 15038, -1, -1),
	WARCHIEF2(-1, 15034, 15035, 15037, 15038, -1, -1),
	LORDMARSHAL(15039, 15040, 15041, 15043, 15044, -1, -1),
	LORDMARSHAL2(-1, 15040, 15041, 15043, 15044, -1, -1),
	BUILDER(-1, 10863, 10864, 10865, -1, -1, -1),
	SEXYOUTFIT(-1, 5028, 5046, -1, -1, -1, -1),
	BLUEFIRST(-1, 26102, 26101, -1, -1, -1, -1),
	REDFIRST(-1, 26104, 26103, -1, -1, -1, -1)
	;
	
	private int helm;
	private int chest;
	private int legs;
	private int boots;
	private int gloves;
	private int cape;
	private int shield;
	
	CosmeticItems(int helm, int chest, int legs, int boots, int gloves, int cape, int shield) {
		this.helm = helm;
		this.chest = chest;
		this.legs = legs;
		this.boots = boots;
		this.gloves = gloves;
		this.cape = cape;
		this.shield = shield;
	}
	
	public int getHelmId() {
		return helm;
	}
	
	public int getChestId() {
		return chest;
	}
	
	public int getLegsId() {
		return legs;
	}
	
	public int getBootsId() {
		return boots;
	}
	
	public int getGlovesId() {
		return gloves;
	}
	
	public int getCapeId() {
		return cape;
	}
	
	public int getShieldId() {
		return shield;
	}
	
    public static void openInterface(Player player) {
        if (!player.getInterfaceManager().containsInterface(1156))
            player.getInterfaceManager().sendInterface(1156);
        
        sendString(player, new int[]{ 108, 109, 90  }, "Kril's Outfit", "Unlock: Kill Zamorak 50 times", "Apply");
        sendString(player, new int[]{ 113, 114, 206 }, "Kril's Godcrusher Outfit", "Unlock: Kill Zamorak 500 times", "Apply");
        sendString(player, new int[]{ 137, 138, 254 }, "Dragon Bone Outfit", "Unlock: Kill Queen Black Dragon 1 time", "Apply");
        sendString(player, new int[]{ 110, 111, 200 }, "Sous Chef Outfit", "Unlock: 200m Cooking Exp", "Apply");
        sendString(player, new int[]{ 116, 117, 212 }, "Botanist Outfit", "Unlock: 200m Herblore Exp", "Apply");
        sendString(player, new int[]{ 134, 135, 248 }, "Blacksmith Outfit", "Unlock: 200m Smithing exp", "Apply");
        sendString(player, new int[]{ 122, 123, 230 }, "Fishing Outfit", "Unlock: 200m Fishing Exp", "Apply");
        sendString(player, new int[]{ 128, 129, 236 }, "Factory Outfit", "Unlock: 200m Slayer Exp", "Apply");
        sendString(player, new int[]{ 125, 126, 224 }, "Paladin Outfit", "Unlock: Combat Level of 138", "Apply");
        sendString(player, new int[]{ 146, 147, 272 }, "Santa Outfit",  "Unlock: Extreme Donator Rank", "Apply");
        sendString(player, new int[]{ 143, 144, 266 }, "Primal Outfit", "Unlock: 200m Dungeoneering Exp", "Apply");
        sendString(player, new int[]{ 119, 120, 218 }, "Artisan's Outfit", "Unlock: 200m Crafting Exp", "Apply");
        sendString(player, new int[]{ 131, 132, 242 }, "Kalphite Outfit", "Unlock: 55,000 Loyalty Points", "Apply");
        sendString(player, new int[]{ 140, 141, 260 }, "Dwarven Warsuit", "Unlock: 200 Trio Boss Kills ", "Apply");
        sendString(player, new int[]{ 149, 150, 278 }, "Agile Outfit", "Unlock: 200M Agility Exp", "Apply");
        sendString(player, new int[]{ 152, 153, 284 }, "Warchief Suit", "Unlock: 75,000 Loyalty Points", "Apply");
        sendString(player, new int[]{ 167, 168, 308 }, "Lord Marshal Suit", "Unlock: 75,000 Loyalty Points", "Apply");
        sendString(player, new int[]{ 155, 157, 290 }, "Builder Outfit", "Unlock: 200M Construction XP", "Apply");
        sendString(player, new int[]{ 159, 161, 296 }, "Sexy Outfit", "Unlock: 75,000 Loyalty Points", "Apply");
        sendString(player, new int[]{ 163, 165, 302 }, "First Tower (Blue)", "Unlock: 200M Runecrafting Exp", "Apply");
        sendString(player, new int[]{ 170, 171, 314 }, "First Tower (Red)", "Unlock: 200M Runecrafting Exp", "Apply");
        sendString(player, new int[]{ 318, 319, 326 }, "---", "Unlock: -", "-");
        
        sendString(player, new int[]{ 156, 164, 160 }, null, null, null); // leave null
        
    }
    
    public static void sendString(Player player, int[] cIds, String title, String desc, String button) {
        player.getPackets().sendIComponentText(1156, cIds[0], title == null ? "" : title);
        player.getPackets().sendIComponentText(1156, cIds[1], desc == null ? "" : desc);
        player.getPackets().sendIComponentText(1156, cIds[2], button == null ? "" : button);
    }
    
    public static void handleButtons(Player player, int buttonId) {
    	if (buttonId == 88) {
    		if (player.getInterfaceAmount() == 0) {
    		if (player.getZamorakKills() < 50) {
    			int amountLeft = 50 - player.getZamorakKills();
    			player.sm("You need to kill Zamorak "+amountLeft+" more times.");
    			return;
    		}
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();  
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(KRILS2);   
    		} else {
        		player.getAppearence().setOutfit(KRILS);    			
    		}
    		return;
    	} else if (player.getInterfaceAmount() == 1) {
			if (player.getCopyrightKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992230);
    		return;    		
    	}
    	}
    	
        if (buttonId == 115) {
    		if (player.getInterfaceAmount() == 0) {        	
        	if (player.getZamorakKills() < 500) {
        		int amountLeft = 500 - player.getZamorakKills();
    			player.sm("You need to kill Zamorak "+amountLeft+" more times.");
    			return;
    		}
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(KRILS_GODCRUSHER2);   
    		} else {
        		player.getAppearence().setOutfit(KRILS_GODCRUSHER);   			
    		}    		
            return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getFatalKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992231);
        		return;      			
    		}
        }
        
        if (buttonId == 139) {
    		if (player.getInterfaceAmount() == 0) {        	
        	if (!player.isKilledQueenBlackDragon()) {
        		player.sm("You have not yet killed Queen Black Dragon.");
        		return;
        	}
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(DRAGON_BONE2);   
    		} else {
        		player.getAppearence().setOutfit(DRAGON_BONE);   			
    		}     		
            return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getSomethingKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992232);
        		return;      			
    		}
        }
        
        if (buttonId == 112) {
    		if (player.getInterfaceAmount() == 0) {
        	int exp = (int) player.getSkills().getXp(Skills.COOKING);
            if (exp < 200000000) {
            	player.sm("You only have "+Utils.formatNumber(exp)+" Cooking Exp.");
            	return;
            }
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(SOUS_CHEF2);   
    		} else {
        		player.getAppearence().setOutfit(SOUS_CHEF);   			
    		}       		
            return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getEradicatorBossKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992233);
        		return;      			
    		}
        }
        
        if (buttonId == 118) {
    		if (player.getInterfaceAmount() == 0) {        	
        	int exp = (int) player.getSkills().getXp(Skills.HERBLORE);
            if (exp < 200000000) {
            	player.sm("You only have "+Utils.formatNumber(exp)+" Herblore Exp.");
            	return;
            }
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(BOTANIST2);   
    		} else {
        		player.getAppearence().setOutfit(BOTANIST);   			
    		}      		
            return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getGenoKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992234);
        		return;      			
    		}
        }
        
        if (buttonId == 136) {
    		if (player.getInterfaceAmount() == 0) {        	
        	int exp = (int) player.getSkills().getXp(Skills.SMITHING);
            if (exp < 200000000) {
            	player.sm("You only have "+Utils.formatNumber(exp)+" Smithing Exp.");
            	return;
            }
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(BLACKSMITH2);   
    		} else {
        		player.getAppearence().setOutfit(BLACKSMITH);   			
    		}     		
        	return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getRajjKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992235);
        		return;      			
    		}
        }
        
        if (buttonId == 124) {
    		if (player.getInterfaceAmount() == 0) {        	
        	int exp = (int) player.getSkills().getXp(Skills.FISHING);
            if (exp < 200000000) {
            	player.sm("You only have "+Utils.formatNumber(exp)+" Fishing Exp.");
            	return;
            }
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(FISHING2);   
    		} else {
        		player.getAppearence().setOutfit(FISHING);   			
    		}       		
        	return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getFearKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992236);
        		return;      			
    		}
        }

        if (buttonId == 130) {
    		if (player.getInterfaceAmount() == 0) {       	
        	int exp = (int) player.getSkills().getXp(Skills.SLAYER);
            if (exp < 200000000) {
            	player.sm("You only have "+Utils.formatNumber(exp)+" Slayer Exp.");
            	return;
            }
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(FACTORY2);   
    		} else {
        		player.getAppearence().setOutfit(FACTORY);   			
    		}      		
        	return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getBlinkKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992237);
        		return;      			
    		}
        }

        if (buttonId == 127) {
    		if (player.getInterfaceAmount() == 0) {    		
        	if (player.getSkills().getCombatLevelWithSummoning() < 138) {
    			player.sm("You must have a combat level of 138 for the Paladin Outfit.");
    			return;
    		}
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(PALADIN2);   
    		} else {
        		player.getAppearence().setOutfit(PALADIN);   			
    		}     		
        	return;
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getExtremeBossKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992238);
        		return;      			
    		}
        }

        if (buttonId == 145) {
    		if (player.getInterfaceAmount() == 0) {        	
        	int exp = (int) player.getSkills().getXp(Skills.DUNGEONEERING);
            if (exp < 200000000) {
            	player.sm("You only have "+Utils.formatNumber(exp)+" Dungeoneering Exp.");
            	return;
            }
    		int hoodId = player.getEquipment().getHatId();
    		int capeId = player.getEquipment().getCapeId();
    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(PRIMAL2);   
    		} else {
        		player.getAppearence().setOutfit(PRIMAL);   			
    		}    		
    		} else if (player.getInterfaceAmount() == 1) {
    			if (player.getRegularBossKills() < 100) {
    				player.getPackets().sendGameMessage("You cannot access this title.");
    				return;
    			}
    			player.getAppearence().setTitle(992239);
        		return;     			
    		}
        }

        if (buttonId == 148) {
    		if (player.getInterfaceAmount() == 0) {        	
    			if (player.isExtremeDonator() || player.isSavior() || player.isEradicator() || player.getRights() > 0) {
    	    		int hoodId = player.getEquipment().getHatId();
    	    		int capeId = player.getEquipment().getCapeId();
    	    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    	    				|| capeId == 20771 || capeId == 20767) {
    	        		player.getAppearence().setOutfit(SANTA2);   
    	    		} else {
    	        		player.getAppearence().setOutfit(SANTA);   			
    	    		}     	    		
            		return;
            	} else if (!player.isExtremeDonator() || !player.isSavior() || !player.isEradicator()) {
            		player.sm("You do not have access to this override.");
            		return;
            	}
        } else if (player.getInterfaceAmount() == 1) {        	
			if (player.getGradumKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992240);
    		return;  
        	}
        }	
        
        if (buttonId == 121) {
    		if (player.getInterfaceAmount() == 0) {        	
            	int exp = (int) player.getSkills().getXp(Skills.CRAFTING);
                if (exp < 200000000) {
                	player.sm("You only have "+Utils.formatNumber(exp)+" Crafting Exp.");
                	
            	} else {
            		int hoodId = player.getEquipment().getHatId();
            		int capeId = player.getEquipment().getCapeId();
    	    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    	    				|| capeId == 20771 || capeId == 20767) {
    	        		player.getAppearence().setOutfit(CRAFTING2);   
    	    		} else {
    	        		player.getAppearence().setOutfit(CRAFTING);   			
    	    		}               		
            	}
        } else if (player.getInterfaceAmount() == 1) {           	
			if (player.getArmadylKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
			}
			player.getAppearence().setTitle(992242);
        	}
        }
        


        if (buttonId == 133) {
      		if (player.getInterfaceAmount() == 0) {        	
            	int loyaltypoints = player.getLoyaltyPoints();
                if (loyaltypoints < 55000) {
                	player.sm("You only have "+Utils.formatNumber(loyaltypoints)+" Loyalty Points.");
                	return;           
            	} else {
            		int hoodId = player.getEquipment().getHatId();
            		int capeId = player.getEquipment().getCapeId();
    	    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    	    				|| capeId == 20771 || capeId == 20767 || capeId == 28013) {
    	        		player.getAppearence().setOutfit(KALPHITE2);   
    	    		} else {
    	        		player.getAppearence().setOutfit(KALPHITE);   			
    	    		}            		
            		return;
            	}
        } else if (player.getInterfaceAmount() == 1) {           	      	
			if (player.getZamorakKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992241);
    		return;
        }
        }
        

        if (buttonId == 142) {
      		if (player.getInterfaceAmount() == 0) {        	
            	int trio1 = player.getFatalKills();
            	int trio2 = player.getSomethingKills();
            	int trio3 = player.getCopyrightKills();
            	int hoodId = player.getEquipment().getHatId();
        		int capeId = player.getEquipment().getCapeId();
                if (trio1 < 200 || trio2 < 200 || trio3 < 200) {
                	player.sm("You only have "+Utils.formatNumber(trio1)+" Fatal Resort Kills.");
                	player.sm("You only have "+Utils.formatNumber(trio2)+" Something Kills.");
                	player.sm("You only have "+Utils.formatNumber(trio3)+" Copyright Kills.");
                	return;
                } else if (trio1 >= 200 && trio2 >= 200 && trio3 >= 200)
    	    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    				|| capeId == 20771 || capeId == 20767) {
        		player.getAppearence().setOutfit(WARSUIT2);   
    		} else {
        		player.getAppearence().setOutfit(WARSUIT);   			
    		}                 	
      		} else if (player.getInterfaceAmount() == 1) {        	
			if (player.getBandosKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992243);
    		return;
        }
        }

        if (buttonId == 151) {
        	if (player.getInterfaceAmount() == 1) {
			if (player.getSaradominKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992244);
    		return;
        	} else if (player.getInterfaceAmount() == 0) {        	
     		if (player.getInterfaceAmount() == 0) {        	
            	int exp = (int) player.getSkills().getXp(Skills.AGILITY);
                if (exp < 200000000) {
                	player.sm("You only have "+Utils.formatNumber(exp)+" Agility Exp.");
                	
            	} else {
    	        		player.getAppearence().setOutfit(AGILE);               		
            	}
    		}    
			}			
        }

        if (buttonId == 154) {
        	if (player.getInterfaceAmount() == 1) {
			if (player.getSunfreetKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992245);
    		return;
        	} else if (player.getInterfaceAmount() == 0) {        	
           	int loyaltypoints = player.getLoyaltyPoints();
                if (loyaltypoints < 75000) {
                	player.sm("You only have "+Utils.formatNumber(loyaltypoints)+" Loyalty Points.");
                	return;           
            	} else {
            		int hoodId = player.getEquipment().getHatId();
            		int capeId = player.getEquipment().getCapeId();
    	    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    	    				|| capeId == 20771 || capeId == 20767 || capeId == 28013) {
    	        		player.getAppearence().setOutfit(WARCHIEF);   
    	    		} else {
    	        		player.getAppearence().setOutfit(WARCHIEF2);   			
    	    		}            		
            		return;
            	}   
			}	
        }

        if (buttonId == 169) {
        	if (player.getInterfaceAmount() == 1) {
			if (player.getNexKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992246);
    		return;
        	} else if (player.getInterfaceAmount() == 0) {        	
           	int loyaltypoints = player.getLoyaltyPoints();
                if (loyaltypoints < 75000) {
                	player.sm("You only have "+Utils.formatNumber(loyaltypoints)+" Loyalty Points.");
                	return;           
            	} else {
            		int hoodId = player.getEquipment().getHatId();
            		int capeId = player.getEquipment().getCapeId();
    	    		if (hoodId == 20770 || hoodId == 20768 || capeId == 20769
    	    				|| capeId == 20771 || capeId == 20767 || capeId == 28013) {
    	        		player.getAppearence().setOutfit(LORDMARSHAL);   
    	    		} else {
    	        		player.getAppearence().setOutfit(LORDMARSHAL2);   			
    	    		}            		
            		return;
            	}   
			}
        }

        if (buttonId == 158) {
        	if (player.getInterfaceAmount() == 1) {
			if (player.getJadKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992247);
    		return;
			} else if (player.getInterfaceAmount() == 0) {        	
     		if (player.getInterfaceAmount() == 0) {        	
            	int exp = (int) player.getSkills().getXp(Skills.CONSTRUCTION);
                if (exp < 200000000) {
                	player.sm("You only have "+Utils.formatNumber(exp)+" Construction Exp.");
                	
            	} else {
    	        		player.getAppearence().setOutfit(BUILDER);               		
            	}
    		}    
			}
        }

        if (buttonId == 162) {
        	if (player.getInterfaceAmount() == 1) {
			if (player.getObsidianKingKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992248);
    		return;
			} else if (player.getInterfaceAmount() == 0) {        	
           	int loyaltypoints = player.getLoyaltyPoints();
                if (loyaltypoints < 75000) {
                	player.sm("You only have "+Utils.formatNumber(loyaltypoints)+" Loyalty Points.");
                	return;           
            	} else {
    	        		player.getAppearence().setOutfit(SEXYOUTFIT);            		
            		return;
            	}   
			}
        }

        if (buttonId == 166) {
        	if (player.getInterfaceAmount() == 1) {
			if (player.getWildyBossKills() < 100) {
				player.getPackets().sendGameMessage("You cannot access this title.");
				return;
			}
			player.getAppearence().setTitle(992249);
    		return;
			} else if (player.getInterfaceAmount() == 0) {        	
     		if (player.getInterfaceAmount() == 0) {        	
            	int exp = (int) player.getSkills().getXp(Skills.RUNECRAFTING);
                if (exp < 200000000) {
                	player.sm("You only have "+Utils.formatNumber(exp)+" Runecrafting Exp.");
                	
            	} else {
    	        		player.getAppearence().setOutfit(BLUEFIRST);               		
            	}
    		}    
			}
        }

        if (buttonId == 172) {
        	if (player.getInterfaceAmount() == 1) {
    			if (player.getWildyBossKills() >= 100 && player.getFatalKills() >= 100
    					&& player.getCopyrightKills() >= 100 
    					 && player.getSomethingKills() >= 100 && player.getBandosKills() >= 100
    					 && player.getSaradominKills() >= 100 && player.getArmadylKills() >= 100
    					 && player.getZamorakKills() >= 100
    					 && player.getGradumKills() >= 100
    					 && player.getFearKills() >= 100 && player.getGenoKills() >= 100
    					 && player.getRajjKills() >= 100 && player.getBlinkKills() >= 100 && player.getHairymonkeykills() >= 100
    					 && player.getEradicatorBossKills() >= 100 && player.getCorporealKills() >= 100) {
										player.getAppearence().setTitle(992253);
    				return;
    			} else {		
				player.getPackets().sendGameMessage("You cannot access this title. Note: You do not need to do donator bosses.");
        	return;
			}
 			} else if (player.getInterfaceAmount() == 0) {        	
     		if (player.getInterfaceAmount() == 0) {        	
            	int exp = (int) player.getSkills().getXp(Skills.RUNECRAFTING);
                if (exp < 200000000) {
                	player.sm("You only have "+Utils.formatNumber(exp)+" Runecrafting Exp.");
                	
            	} else {
    	        		player.getAppearence().setOutfit(REDFIRST);               		
            	}
    		}    
			}       	
        }

        if (buttonId == 320) {
        	if (player.getInterfaceAmount() == 1) {
        	player.getAppearence().setTitle(0);
        	return;
        	}
        }
    }
}
