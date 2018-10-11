package com.rs.game.player.content.custom;
import com.rs.game.player.Player;
 
public enum TitleManager {
		/**
		* (id#, cost, "name of title")
	 	*/
		JUNIOR_CADET(1, 500, "Junior Cadet"), 
		SERJEANT(2, 1000, "Serjeant"),
		COMMANDER(3, 1500, "Commander"),
		WAR_CHIEF(4, 2000, "War-Chief"),
		SIR(5, 500, "Sir"),
		LORD(6, 600, "Lord"),
		DUDERINO(7, 650, "Duderino"),
		LIONHEART(8, 500, "Lionheart"),
		HELLRAISER(9, 500, "Hellraiser"),
		CRUSADER(10, 1000, "Crusader"),
		DESPERADO(11, 600, "Desperado"),
		BARON(12, 500, "Baron"),
		COUNT(13, 650, "Count"),
		OVER_LORD(14, 800, "Overlord"),
		BANDITO(15, 950, "Bandito"),
		DUKE(16, 1100, "Duke"),
		KING(17, 1250, "King"),
		BIG_CHEESE(18, 2000, "Big-Cheese"),
		BIG_WIG(19, 2000, "Bigwig"),
		WUNDERKIND(20, 500, "Wunderkind"),
		VYRELING(21, 1000, "Vyreling"),
		VYRE_GRUNT(22, 1500, "Vyre Grunt"),
		VYRE_WATCH(23, 2000, "Vyrewatch"),
		VYRELORD(24, 2500, "Vyrelord"),
		YTHAAR(25, 1500, "YtHaar"),
		EMPEROR(26, 3000, "Emperor"),
		PRINCE(27, 1000, "Prince"),
		WITCH_KING(28, 2000, "Witch King"),
		ARCHON(29, 1000, "Archon"),
		JUSTICIAR(30, 1000, "Justiciar"),
		THE_AWESOME(31, 3000, "The Awesome");
		
    private int titleId;
    private int cost;
    private String name;

    /**
     * Gets the titleId#
     */
    public int getId() {
    	return titleId;
    }
    /**
     * Gets the title's cost
     */
    public int getCost() {
    	return cost;
    }
    /**
     * Gets name of title
     */
    public String getName() {
    	return name;
    }
    
    /**
     * sets the integers, no need to change
     */
   TitleManager(int id, int cost, String name) {
	   this.titleId = id;
	   this.cost = cost;
	   this.name = name;
   }
    /**
     * Applies the title if player has enough LP
     */
    public static void applyTitle(Player player, int titleId) {
		for (TitleManager title : TitleManager.values()) {
			if (title.getId() == titleId) {
				if (checkPoints(player, title.getId())) {	
					player.getAppearence().setTitle(title.titleId);
					player.purchase(title.cost);
					player.sendMessage("Title <col=FF0000>"+title.name+"</col> bought for "+title.getCost()+" loyalty points!");
				} else {
					player.sendMessage("You do not have enough Loyalty Points! Play some more!");
				}
			return;
			}
		}
		return;
	}
    
    /**
     * Display's a menu with all the available titles
     */
    public static void printMenu(Player player) {
    	player.getInterfaceManager().sendInterface(275);
    	int i = 18;
    	player.getPackets().sendIComponentText(275, 2, "Loyalty Title Menu");
    	player.getPackets().sendIComponentText(275, 16, "To purchase a listed title, just do ::title #");
    	player.getPackets().sendIComponentText(275, 17, "Replace # with a number listed to the left of the name.");
    	player.getPackets().sendIComponentText(275, 18, "");

    	for (TitleManager title : TitleManager.values()) {
    		i++; player.getPackets().sendIComponentText(275, i, title.getId()+" - "+title.getName()+" (Cost: "+title.getCost()+" points)");
		}
		return;
	}
    /**
     * Check's if the player has enough points
     */
    public static boolean checkPoints(Player player, int titleId) {
    	for (TitleManager title : TitleManager.values()) {
			if (title.getId() == titleId)
			if (player.getLoyaltyPoints() >= title.getCost())
				return true;
		}
		return false;
	}
    /**
     * End of Class
     */
}
