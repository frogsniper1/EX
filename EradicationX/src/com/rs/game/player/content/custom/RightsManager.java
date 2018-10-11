package com.rs.game.player.content.custom;

import com.rs.game.player.Player;

public enum RightsManager {
	
	REGULAR("Player", "", "", ""),
	HEADMOD("Head Moderator","<img=16>","FF0000", ""), 
	HEADEXECUTIVE("Head Administrator","<img=21>","E30505", "570303"), 
	FORUMMOD("Forum Mod", "<img=20>","FF8930", ""),
	FORUMADMIN("Forum Admin", "<img=24>","FFFF33", "FF6600"),
	OWNER("Owner", "<img=7>", "084FA1", ""),
	DONATOR("Donator", "<img=10>", "268703", ""),
	EDONATOR("Extreme Donator", "<img=8>", "DB1A1A", ""),
	SAVIOR("Super Donator", "<img=9>", "33B8D6", ""), 	
	SUPPORTER("Supporter", "<img=13>", "FF69B4", ""),	
	HERO("Hero", "<img=22>", "FFFFFF", ""),
	DICER("Dicer", "<img=11>", "D60F80", ""),
	MODERATOR("Moderator", "<img=0>", "559568", ""),
	LDONATOR("Donator", "<img=19>", "268703", ""),
	LEDONATOR("Extreme Donator", "<img=15>", "DB1A1A", ""),
	LSDONATOR("Super Donator", "<img=12>", "33B8D6", ""),
	LERADICATOR("The Eradicator", "<img=14>", "02385E", ""),
	ERADICATOR("The Eradicator", "<img=18>", "BF1B5D", ""),
	EXECUTIVE("Administrator", "<img=17>", "078A65", "");
	
	private String title;
	private String crown;
	private String colorId;
	private String shadeId;
	
	RightsManager(String title, String crown, String Color, String Shade) {
		this.title = title;
		this.crown = crown;
		this.colorId = Color;
		this.shadeId = Shade;
	}
	
	public String getShade() {
		return shadeId;
	}
	
	public String getTitle() {
		return title;
	}
	public String getCrown() {
		return crown;
	}
	public String getColor() {
		return colorId;
	}
	
	public static String print(RightsManager r, Player player) {
		String crown = null;
		String titleInfo= null;
		String color= null;
		String shade= null;
		if (r != null) {
			crown = r.getCrown();
			titleInfo = r.getTitle();
			color = r.getColor();
			shade = r.getShade();
		}
		if (player.getYellTitle() != null) {
			titleInfo = player.getYellTitle();
		}
		if (player.getYellColor() != null) {
			color = player.getYellColor();
		}
		if (player.getYellShade() != null) {
			shade = player.getYellShade();
		}
		return "<col="+color+"><shad="+shade+">["+titleInfo+"]</col> "+crown+""+player.getDisplayName()+"<col="+color+">";
	}
	
	public static String getInfo(Player player) {
				if (player.getRights() == 7) 
					return print(OWNER, player);
				if (player.isHeadExecutive())
					return print(HEADEXECUTIVE, player);
				if (player.getRights() == 2) 
					return print(EXECUTIVE, player);
				if (player.isHeadMod()) 
					return print(HEADMOD, player);
				if (player.isForumAdmin())
					return print(FORUMADMIN, player);
				if (player.isModerator()) 
					return print(MODERATOR, player);
				if (player.isSupporter()) 
					return print(SUPPORTER, player);
				if (player.isForumMod()) 
					return print(FORUMMOD, player);
				if (player.isHero()) 
					return print(HERO, player);
				if (player.isDicer())
					return print(DICER, player);
				if (player.isEradicator()) 
					return print(ERADICATOR, player);
				if (player.isLentEradicator()) 
					return print(LERADICATOR, player);
				if (player.isSavior()) 
					return print(SAVIOR, player);
				if (player.isLentSavior()) 
					return print(LSDONATOR, player);
				if (player.isExtremeDonator()) 
					return print(EDONATOR, player);
				if (player.isLentExtreme()) 
					return print(LEDONATOR, player);
				if (player.isDonator()) 
					return print(DONATOR, player);
				if (player.isLentDonator()) 
					return print(LDONATOR, player);
				return "";
	}
}
