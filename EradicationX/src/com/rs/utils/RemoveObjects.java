package com.rs.utils;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;

public enum RemoveObjects {

    TORCH_1(2861, 3536, 0, true),
    SHOP_DOOR_1(2855, 3541, 0, true),
    SHOP_DOOR_2(2854, 3541, 0, true),
    HOME_DOOR(2877, 3542, 0, true),
    PILLAR_1(2865, 3547, 0, true),
    PILLAR_2(2873, 3547, 0, true),
    PILLAR_3(2873, 3544, 0, true),
    PILLAR_4(2873, 3540, 0, true),
    PILLAR_5(2873, 3537, 0, true),
    PILLAR_6(2865, 3537, 0, true),
    STATUE_1(2866, 3533, 0, true),
    STATUE_2(2872, 3533, 0, true),
    STATUE_3(2866, 3551, 0, true),
    STATUE_4(2873, 3551, 0, true),
    STATUE_5(2861, 3539, 0, true),
    STATUE_6(2861, 3537, 0, true),
    STATUE_7(2863, 3539, 0, true),
    WALL_1(2862, 3536, 0, true),
    WALL_2(2862, 3537, 0, true),
    WALL_3(2862, 3538, 0, true),
    WALL_4(2862, 3539, 0, true);
    
    private int coordX;
    private int coordY;
    private int plane;
    private boolean clipped;

    RemoveObjects(int x, int y, int plane, boolean clipped) {
        this.coordX = x;
        this.coordY = y;
        this.plane = plane;
        this.clipped = clipped;
    }

    public static void removeObjects() {
        for (RemoveObjects obj : RemoveObjects.values()) {
            WorldObject object = World.getObject(new WorldTile(obj.coordX, obj.coordY, obj.plane));
            if (object != null) {
                World.removeObject(object, obj.clipped);
            }
        }
    }
}
