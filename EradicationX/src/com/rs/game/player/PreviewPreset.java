package com.rs.game.player;

public class PreviewPreset {
	
	private static BankPreset selectedPreset;
	public static final int CLOSE = 1, AURA = 30, HELMET = 31, CAPE = 32, AMULET = 33, CHEST = 34, LEGS = 35, 
							BOOTS = 36, ARROW = 37, MAINHAND = 38, SHIELD = 39, GLOVES = 40, RING = 41;
	
	
	public static void openInterface(Player player, BankPreset preset) {
		if (!preset.containsSaves()) {
			player.sm("You have no presets saved to preview.");
			return;
		}
		player.getInterfaceManager().sendInterface(3010);
		selectedPreset = preset;
		if (selectedPreset.getInventory() != null) {
			for (int i = 0; i < selectedPreset.getInventory().getSize(); i++) {
				if (getInventoryId(i) != -1) {
				if (!selectedPreset.getInventory().get(i).getDefinitions().isStackable())
					player.getPackets().sendItemOnIComponent(3010, 2 + i, selectedPreset.getInventory().get(i).getId(), 1);
				else
					player.getPackets().sendItemOnIComponent(3010, 2 + i, selectedPreset.getInventory().get(i).getId(), selectedPreset.getInventory().get(i).getAmount());
				} else 
					player.getPackets().sendItemOnIComponent(3010, 2 + i, 28029, 1);
			}
		} else
			for (int i = 2; i <= 29; i++)
				player.getPackets().sendSpriteOnIComponent(3010, i, 2526);
		if (selectedPreset.getEquipment() != null) {
			if (getEquipmentId(Equipment.SLOT_AURA) != -1)
				player.getPackets().sendItemOnIComponent(3010, AURA, getEquipmentId(Equipment.SLOT_AURA), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, AURA, 14109);
			if (getEquipmentId(Equipment.SLOT_HAT) != -1)
				player.getPackets().sendItemOnIComponent(3010, HELMET, getEquipmentId(Equipment.SLOT_HAT), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, HELMET, 156);
			if (getEquipmentId(Equipment.SLOT_CAPE) != -1)
				player.getPackets().sendItemOnIComponent(3010, CAPE, getEquipmentId(Equipment.SLOT_CAPE), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, CAPE, 157);
			if (getEquipmentId(Equipment.SLOT_AMULET) != -1)
				player.getPackets().sendItemOnIComponent(3010, AMULET, getEquipmentId(Equipment.SLOT_AMULET), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, AMULET, 158);
			if (getEquipmentId(Equipment.SLOT_ARROWS) != -1)
				player.getPackets().sendItemOnIComponent(3010, ARROW, getEquipmentId(Equipment.SLOT_ARROWS), selectedPreset.getEquipment().get(Equipment.SLOT_ARROWS).getAmount());
			else
				player.getPackets().sendSpriteOnIComponent(3010, ARROW, 166);
			if (getEquipmentId(Equipment.SLOT_WEAPON) != -1)
				player.getPackets().sendItemOnIComponent(3010, MAINHAND, getEquipmentId(Equipment.SLOT_WEAPON), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, MAINHAND, 159);
			if (getEquipmentId(Equipment.SLOT_CHEST) != -1)
				player.getPackets().sendItemOnIComponent(3010, CHEST, getEquipmentId(Equipment.SLOT_CHEST), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, CHEST, 161);
			if (getEquipmentId(Equipment.SLOT_SHIELD) != -1)
				player.getPackets().sendItemOnIComponent(3010, SHIELD, getEquipmentId(Equipment.SLOT_SHIELD), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, SHIELD, 162);
			if (getEquipmentId(Equipment.SLOT_LEGS) != -1)
				player.getPackets().sendItemOnIComponent(3010, LEGS, getEquipmentId(Equipment.SLOT_LEGS), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, LEGS, 163);
			if (getEquipmentId(Equipment.SLOT_HANDS) != -1)
				player.getPackets().sendItemOnIComponent(3010, GLOVES, getEquipmentId(Equipment.SLOT_HANDS), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, GLOVES, 164);
			if (getEquipmentId(Equipment.SLOT_FEET) != -1)
				player.getPackets().sendItemOnIComponent(3010, BOOTS, getEquipmentId(Equipment.SLOT_FEET), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, BOOTS, 165);
			if (getEquipmentId(Equipment.SLOT_RING) != -1)
				player.getPackets().sendItemOnIComponent(3010, RING, getEquipmentId(Equipment.SLOT_RING), 1);
			else
				player.getPackets().sendSpriteOnIComponent(3010, RING, 160);
		} else
			for (int i = 30; i <= 41; i++)
				player.getPackets().sendSpriteOnIComponent(3010, i, 2526);
	}
	
	private static int getEquipmentId(int slot) {
		if (selectedPreset.getEquipment().get(slot) != null)
			return selectedPreset.getEquipment().get(slot).getId();
		return -1;
	}
	
	private static int getInventoryId(int slot) {
		if (selectedPreset.getInventory().get(slot) != null)
			return selectedPreset.getInventory().get(slot).getId();
		return -1;
	}
	
}