package com.rs.utils.spawning;

import com.rs.game.World;
import com.rs.game.WorldObject;

public enum ObjectSpawning {
		/** START HOME OBJECTS */
                UNLIT_BEACON(38453, 2865, 3550, 0, 0),
		BANK_BOOTH_1(36786, 2875, 3538, 0, 0),
                BANK_BOOTH_2(36786, 2874, 3538, 0, 0),
                BANK_BOOTH_3(36786, 2873, 3538, 0, 0),
                BANK_BOOTH_4(36786, 2872, 3538, 0, 0),
                BANK_BOOTH_5(36786, 2871, 3538, 0, 0),
                STATUE(26969, 2870, 3538, 0, 2),
                BANK_BOOTH_6(36786, 2870, 3537, 0, 1),
                BANK_BOOTH_7(36786, 2870, 3536, 0, 1),
                BANK_BOOTH_8(36786, 2870, 3535, 0, 1),
                BANK_BOOTH_9(36786, 2870, 3534, 0, 1),
                BANK_BOOTH_10(36786, 2870, 3533, 0, 1),
                
                ANVIL_1(24744, 2869, 3550, 0, 3),
                ANVIL_2(24744, 2871, 3550, 0, 3),
                ANVIL_3(24744, 2873, 3550, 0, 3),
                
		THIEF_STALL_1(4875, 2862, 3546, 0, 1),
		THIEF_STALL_2(4876, 2862, 3547, 0, 1),
		THIEF_STALL_3(4877, 2862, 3548, 0, 1),
		THIEF_STALL_4(4878, 2862, 3549, 0, 1),
		/** END HOME OBJECTS */
		DUNG_1(49766, 89, 721, 2, 0),
		DUNG_2(49768, 88, 721, 2, 0),
		DUNG_3(49770, 87, 721, 2, 0),
		DUNG_4(49772, 86, 721, 2, 0),
		DUNG_5(49774, 85, 721, 2, 0),
		RUNITE_ORE_1(14860, 1595, 4505, 0, -3),
		RUNITE_ORE_2(14860, 1595, 4506, 0, -3),
		RUNITE_ORE_3(14860, 1595, 4507, 0, -3),
		RUNITE_ORE_4(14860, 1595, 4508, 0, -3),
		RUNITE_ORE_5(14860, 1595, 4509, 0, -3),
		RUNITE_ORE_6(14860, 1595, 4510, 0, -3),
		MAGIC_TREE_1(1306, 1595, 4503, 0, 0),
		MAGIC_TREE_2(1306, 1595, 4510, 0, 0),
		FURNACE(11010, 1599, 4512, 0, -4),
                FURNACE2(11666, 2875, 3547, 0, 2),
                OBELISK(29945, 2862, 3551, 0, 0),
		ANVIL(2783, 1603, 4512, 0, -4),
		ALTAR(409, 2863, 3533, 0, 0),
		ZAROS_ALTAR_PC(47120, 2651, 2653, 0, -1),
		CORP(-1, 3671, 2976, 0, 0),
		RC_ALTAR_1(2478, 2600, 3155, 0, -2),
		RC_ALTAR_2(2479, 2597, 3155, 0, -2),
		RC_ALTAR_3(2480, 2594, 3157, 0, -2),
		RC_ALTAR_4(2481,2594, 3160, 0, -2),
		RC_ALTAR_5(2482, 2594, 3163, 0, -2),
		RC_ALTAR_6(2483, 2594, 3166, 0, -2),
		RC_ALTAR_7(2484, 2603, 3157, 0, -2),
		RC_ALTAR_8(2485, 2600, 3168, 0, -2),
		RC_ALTAR_9(2486, 2603, 3166, 0, -2),
		RC_ALTAR_10(2487, 2603, 3160, 0, -2),
		RC_ALTAR_11(2488, 2597, 3168, 0, -2),
		RC_ALTAR_12(2489, 2513, 3169, 0, -2),
		RC_AREA_1(36786, 2587, 3422, 0, 2),
		RC_AREA_2(36786, 2510, 3169, 0, -2),
		NOTICE_BOARD(40760, 0, 0, 0, 0); // TODO: Put this somewhere
		
	private int id;
	private int x;
	private int y;
	private int z;
	private int face;
	
	ObjectSpawning(int id, int x, int y, int z, int face) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.face = face;
	}
	
	public static void spawnObjects() {
		for (ObjectSpawning obj : ObjectSpawning.values()) {
			World.spawnObject(new WorldObject(obj.id, 10, obj.face, obj.x, obj.y, obj.z), true);
		}
	}
}
