package com.rs.utils.spawning;

import com.rs.game.World;
import com.rs.game.WorldTile;

public enum NPCSpawning {

	SLAYER(9085, 2406, 4718, 0, false), 
	LOYALTY_SHOP(13727, 2407, 4718, 0, false),
	FATHER_AERECK(456, 1182, 4301, 0, false), 
	MANAGER(13768, 2405, 4718, 0, false), 
	GENERAL_STORE(529, 2404, 4718, 0, false), 
	AJJAT(4288, 2403, 4718, 0, false),
	HIGH_LEVEL(551, 2402, 4718, 0, false),
	TELEPORTS(230, 2865, 3537, 0, false), 
	WC_SHOP(519, 2401, 4718, 0, false), 
	HERB_SHOP(587, 2407, 4720, 0, false), 
	POTIONS(576, 2406, 4720, 0, false), 
	RANGED(550, 2405, 4720, 0, false), 
	BARROWS(3820, 2404, 4720, 0, false), 
	WEAPON_STORE(538, 2403, 4720, 0, false), 
	HORVIK_ARMOR(549, 2402, 4720, 0, false),
	HUNTER(5112, 2401, 4720, 0, false), 
	FOOD_SHOP(8864, 2407, 4715, 0, false), 
	MAGIC_SHOP(546, 2406, 4715, 0, false), 
	PURE_SHOP(457, 2405, 4715, 0, false), 
	SKILLCAPES(548, 1178, 4301, 0, false),

	FISHING_SPOT_1(327, 2604, 3419, 0, true),
	FISHING_SPOT_2(6267, 2605, 3420, 0 , true),
	FISHING_SPOT_3(312, 2605, 3421, 0, true),
	FISHING_SPOT_4(313, 2604, 3422, 0, true),
	FISHING_SPOT_5(952, 2603, 3422, 0, true),
	FISHING_SPOT_6(327, 2604, 3423, 0, true),
	FISHING_SPOT_7(6267, 2605, 3424, 0, true),
	FISHING_SPOT_8(312, 2605, 3425, 0, true),
	FISHING_SPOT_9(327, 2599, 3419, 0, true),
	FISHING_SPOT_10(6267, 2598, 3422, 0, true),
	FISHING_SPOT_11(8864, 2590, 3419, 0, true),
	POLY_DUNG_SHOP(875, 4724, 5466, 0, false),
	CORP_SHOP(13191, 2959, 4382, 2, false),
	TZHAAR_1(2625, 2617, 5132, 0, false),
	TZHAAR_2(2614, 4666, 5082, 0, false),
	
	/** DONOR ZONE NPCS **/
	DONOR_ZONE_1(6892, 1977, 5050, 0, false),
	DONOR_ZONE_2(1918, 1979, 5048, 0, false),
	DONOR_ZONE_3(14854, 1978, 5049, 0, false),
	DONOR_ZONE_4(537, 1976, 5051, 0, false),
	DONOR_ZONE_5(6970, 1976, 5051, 1, false);
	
	
	private int npcId;
	private int posX;
	private int posY;
	private int height;
	private boolean spawned;

	NPCSpawning(int id, int x, int y, int z, boolean spawned) {
		this.npcId = id;
		this.posX = x;
		this.posY = y;
		this.height = z;
		this.spawned = spawned;
	}
	
	public static void spawnNpcs() {
		for (NPCSpawning spawn : NPCSpawning.values()) {
			World.spawnNPC(spawn.npcId, new WorldTile(spawn.posX, spawn.posY, spawn.height), 0, spawn.spawned);
		}
		
	}
}
