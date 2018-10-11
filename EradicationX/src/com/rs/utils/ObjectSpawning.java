package com.rs.utils;

import com.rs.game.World;
import com.rs.game.WorldObject;

public enum ObjectSpawning {
		/** START HOME OBJECTS */
                UNLIT_BEACON(38453, 2865, 3550, 0, 1),
                wallspear12312(849, 1181, 4299, 0, -3),
                wallspear1231(849, 1182, 4299, 0, -3),
                wallspear1232347(849, 1182, 4299, 0, -3),
                wallspear123123456(849, 1183, 4299, 0, -3),
                wallspear12313123456(849, 1188, 4300, 0, -3),
                wallspear1123123456123(849, 1191, 4300, 0, -3),
                wallspear1(849, 1171, 4300, 0, -3),
                wallspear2(849, 1172, 4300, 0, -3),
                wallspear3(849, 1173, 4300, 0, -3),
                wallspear4(849, 1174, 4300, 0, -3),
                wallspear5(849, 1183, 4300, 0, -3),
                wallspear6(849, 1184, 4300, 0, -3),
                wallspear7(849, 1185, 4300, 0, -3),
                wallspear8(849, 1186, 4300, 0, -3),
                wallspear9(849, 1187, 4300, 0, -3),
                wallspear98(849, 1189, 4300, 0, 1),
                wallspear889(849, 1189, 4300, 0, 1),
                wallspear89889(849, 1190, 4300, 0, 1),
                wallspear2323(849, 1191, 4301, 0, 1),
                tree16562(13421, 1191, 4321, 0, 0),
                tree16561(13421, 1191, 4316, 0, 0),
                tree165622(13421, 1191, 4322, 0, 0),
                tree1656(13421, 1191, 4315, 0, 0),
                tree12(13421, 1171, 4310, 0, 0),
                tree13(13421, 1172, 4310, 0, 0),
                tree14(13421, 1173, 4310, 0, 0),
                tree15(13421, 1174, 4310, 0, 0),
                tree16(13421, 1175, 4310, 0, 0),
                tree17(13421, 1176, 4310, 0, 0),
                tree18(13421, 1177, 4310, 0, 0),
                Waterfountain(24161, 1180, 4309, 0, 0),
                Waterfall2(63173, 1190, 4323, 0, 2),
                Waterfall(63173, 1190, 4311, 0, 2),
                TREEDUDE(1317, 1180, 4318, 0, 0),
                WILDY_WALL52(65087, 1181, 4309, 0, 0),
                WILDY_WALL51(65087, 1183, 4309, 0, 0),
                WILDY_WALL53(65087, 1185, 4309, 0, 0),
                WILDY_WALL54(65087, 1187, 4309, 0, 0),
                WILDY_WALL55(65087, 1188, 4309, 0, 0),
                WILDY_WALL56(65087, 1190, 4309, 0, 0),
                WILDY_WALL57(65087, 1192, 4309, 0, 0),
                WILDY_WALL58(65087, 1181, 4310, 0, 0),
                WILDY_WALL69(65087, 1183, 4310, 0, 0),
                WILDY_WALL70(65087, 1185, 4310, 0, 0),
                WILDY_WALL89(65087, 1187, 4310, 0, 0),
                WILDY_WALL27(65087, 1181, 4310, 0, 0),
                WILDY_WALL35(65087, 1190, 4310, 0, 0),
                BANK_BOOTH_1(36786, 1191, 4308, 0, 1),
                BANK_BOOTH_2(36786, 1191, 4307, 0, 1),
                BANK_BOOTH_3(36786, 1191, 4306, 0, 1),
                BANK_BOOTH_4(36786, 1191, 4305, 0, 1),
                BANK_BOOTH_5(36786, 1191, 4304, 0, 1),
                STATUE(26969, 2870, 3538, 0, 2),
                BANK_BOOTH_6(36786, 1191, 4303, 0, 1),
                BANK_BOOTH_7(36786, 1191, 4302, 0, 1),
                BANK_BOOTH_8(36786, 2870, 4301, 0, 1),
                BANK_BOOTH_9(36786, 2870, 3534, 0, 1),
                BANK_BOOTH_10(36786, 2870, 3533, 0, 1),
                
                ANVIL_1(24744, 1173, 4308, 0, 3),
                ANVIL_2(24744, 1172, 4308, 0, 3),
                ANVIL_3(24744, 1171, 4308, 0, 3),
                
		THIEF_STALL_1(4875, 1171, 4301, 0, 0),
		THIEF_STALL_2(4876, 1171, 4302, 0, 0),
		THIEF_STALL_3(4877, 1171, 4303, 0, 0),
		THIEF_STALL_4(4878, 1171, 4304, 0, 0),
		/** END HOME OBJECTS */
		DUNG_1(49766, 89, 721, 2, 0),
		DUNG_2(49768, 88, 721, 2, 0),
		DUNG_3(49770, 87, 721, 2, 0),
		DUNG_4(49772, 86, 721, 2, 0),
		DUNG_5(49774, 85, 721, 2, 0),
		RUNITE_ORE_1234234(14860, 1173, 4313, 0, -3),
		RUNITE_ORE_22342(14860, 1173, 4315, 0, -3),
		RUNITE_ORE_3452(14860, 1172, 4315, 0, -3),
		RUNITE_ORE_445(14860, 1171, 4313, 0, -3),
		RUNITE_ORE_505(14860, 1171, 4312, 0, -3),
		RUNITE_ORE_69(14860, 1595, 4510, 0, -3),
		MAGIC_TREE_12(1306, 1177, 4309, 0, 0),
		MAGIC_TREE_23(1306, 1176, 4309, 0, 0),
                MAGIC_TREE_24(1306, 1175, 4309, 0, 0),
                MAGIC_TREE_25(1306, 1175, 4307, 0, 0),
                MAGIC_TREE_26(1306, 1174, 4301, 0, 0),
                MAGIC_TREE_27(1306, 1174, 4302, 0, 0),
                MAGIC_TREE_28(1306, 1174, 4303, 0, 0),
		FURNACE(11010, 1176, 4301, 0, 2),
                FURNACE2(11666, 1170, 4306, 0, 0),
                OBELISK(29945, 1181, 4311, 0, 0),
		ANVIL(2783, 1603, 4512, 0, -4),
		ALTAR1(409, 1188, 4309, 0, 0),
                ALTAR(409, 1188, 4312, 0, 0),
		ZAROS_ALTAR_PC(47120, 1179, 4299, 0, 0),
		CORP(-1, 3671, 2976, 0, 0),
		RC_ALTAR_1(2478, 1179, 4333, 0, -2),
		RC_ALTAR_2(2479, 1189, 4333, 0, -2),
		RC_ALTAR_3(2480, 1186, 4333, 0, -2),
		RC_ALTAR_4(2481,1184, 4333, 0, -2),
		RC_ALTAR_5(2482, 1181, 4333, 0, -2),
		RC_ALTAR_6(2483, 1178, 4333, 0, -2),
		RC_ALTAR_7(2484, 1175, 4333, 0, -2),
		RC_ALTAR_8(2485, 1172, 4333, 0, -2),
		RC_ALTAR_9(2486, 1172, 4336, 0, -2),
		RC_ALTAR_10(2487, 1175, 4336, 0, -2),
		RC_ALTAR_11(2488, 1178, 4336, 0, -2),
		RC_ALTAR_12(2489, 1182, 4336, 0, -2),
		RC_AREA_1(36786, 2587, 3422, 0, 2),
		RC_AREA_2(36786, 2510, 3169, 0, -2),
		NOTICE_BOARD(40760, 1187, 4305, 0, 0); // TODO: Put this somewhere
		
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
