package com.rs.game.player;

import java.io.Serializable;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.actions.Bonfire;
import com.rs.utils.ItemExamines;

public final class Equipment implements Serializable {

	private static final long serialVersionUID = -4147163237095647617L;

	public static final byte SLOT_HAT = 0, SLOT_CAPE = 1, SLOT_AMULET = 2,
			SLOT_WEAPON = 3, SLOT_CHEST = 4, SLOT_SHIELD = 5, SLOT_LEGS = 7,
			SLOT_HANDS = 9, SLOT_FEET = 10, SLOT_RING = 12, SLOT_ARROWS = 13,
			SLOT_AURA = 14;

	public static final int OBSIDIAN_WHELM = 26128;
	public static final int OBSIDIAN_RHELM = 26130;
	public static final int OBSIDIAN_MHELM = 26132;
	public static final int OBSIDIAN_LEGS = 26136;
	public static final int OBSIDIAN_BODY = 26140;
	public static final int OBSIDIAN_GLOVES = 26124; 
	public static final int OBSIDIAN_BOOTS = 26126;
	public static final int EOBSIDIAN_WHELM = 29854;
	public static final int EOBSIDIAN_RHELM = 29858;
	public static final int EOBSIDIAN_MHELM = 29859;
	public static final int EOBSIDIAN_BODY = 29862;
	public static final int EOBSIDIAN_SHIELD = 29853;
	public static final int EOBSIDIAN_LEGS = 29852;
	public static final int EOBSIDIAN_GLOVES = 29855;
	public static final int EOBSIDIAN_BOOTS = 29856;	
	public static final int EROYALCOURT_RAPIER = 29896;	
	public static final int EROYALCOURT_RAPIER_OH = 29895;	
	public static final int EASCENSION_CBOW_OH = 29898;	
	public static final int EASCENSION_CBOW = 29897;	
	public static final int EVIRTUS_WAND = 29899;	
	public static final int EVIRTUS_BOOK = 29900;
	public static final int WOBSIDIAN_WHELM = 27354;
	public static final int WOBSIDIAN_BODY = 27351;
	public static final int WOBSIDIAN_LEGS = 27360;
	public static final int WOBSIDIAN_GLOVES = 27353;
	public static final int WOBSIDIAN_BOOTS = 27352;
	
	private ItemsContainer<Item> items;

	private transient Player player;
	private transient int equipmentHpIncrease;

	static final int[] DISABLED_SLOTS = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 1, 1, 0 };

	public Equipment() {
		items = new ItemsContainer<Item>(15, false);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void init() {
		player.getPackets().sendItems(94, items);
		refresh(null);
	}

	public void refresh(int... slots) {
		if (slots != null) {
			player.getPackets().sendUpdateItems(94, items, slots);
			player.getCombatDefinitions().checkAttackStyle();
		}
		player.getCombatDefinitions().refreshBonuses();
		refreshConfigs(slots == null);
	}

	public void reset() {
		items.reset();
		init();
	}

	public Item getItem(int slot) {
		return items.get(slot);
	}

	public void sendExamine(int slotId) {
		Item item = items.get(slotId);
		if (item == null)
			return;
		player.getPackets().sendGameMessage(ItemExamines.getExamine(item));
	}

	public void refreshConfigs(boolean init) {
		double hpIncrease = 0;
		for (int index = 0; index < items.getSize(); index++) {
			Item item = items.get(index);
			if (item == null)
				continue;
			int id = item.getId();
			if (index == Equipment.SLOT_HAT) {
				if (id == 20135 || id == 20137 // torva
						|| id == 20147 || id == 20149 // pernix
						|| id == 20159 || id == 20161 // virtus
				)
					hpIncrease += 66;

			} else if (index == Equipment.SLOT_CHEST) {
				if (id == 20139 || id == 20141 // torva
						|| id == 20151 || id == 20153 // pernix
						|| id == 20163 || id == 20165// virtus  add the tetsu here
				)
					hpIncrease += 200;
			} else if (index == Equipment.SLOT_LEGS) {
				if (id == 20143 || id == 20145 // torva
						|| id == 20155 || id == 20157 // pernix
						|| id == 20167 || id == 20169 // virtus
				)
					hpIncrease += 134;
			}

		}
		if (player.getLastBonfire() > 0) {
			int maxhp = player.getSkills().getLevel(Skills.HITPOINTS) * 10;
			hpIncrease += (maxhp * Bonfire.getBonfireBoostMultiplier(player))
					- maxhp;
		}
		if (player.getHpBoostMultiplier() != 0) {
			int maxhp = player.getSkills().getLevel(Skills.HITPOINTS) * 10;
			hpIncrease += maxhp * player.getHpBoostMultiplier();
		}
		if (hpIncrease != equipmentHpIncrease) {
			equipmentHpIncrease = (int) hpIncrease;
			if (!init)
				player.refreshHitPoints();
		}
	}

	public static boolean hideArms(Player player, Item item) {
		item = player.getEquipment().getItems().get(SLOT_CHEST);
		switch (player.getAppearence().getTopStyle()) {
			case 452:
			case 445:
				return true;
		}
		if (item != null) 
			return item.getDefinitions().getEquipType() == 6;
		return false;
	}

	public static boolean hideHair(Item item) {
		String name = item.getName().toLowerCase();
		if (name.contains("full"))
			return true;
		return item.getDefinitions().getEquipType() == 8;
	}

	public static boolean showBear(Item item) {
		String name = item.getName().toLowerCase();
		return !hideHair(item) || name.contains("horns")
				|| name.contains("hat") || name.contains("afro")
				|| name.contains("cowl") || name.contains("tattoo")
				|| name.contains("headdress") || name.contains("hood")
				|| (name.contains("mask") && !name.contains("h'ween"))
				|| (name.contains("helm") && !name.contains("full"));
	}

	public static int getItemSlot(int itemId) {
		return ItemDefinitions.getItemDefinitions(itemId).getEquipSlot();
	}

	public static boolean isTwoHandedWeapon(Item item) {
		return item.getDefinitions().getEquipType() == 5;
	}

	public int getWeaponRenderEmote() {
		Item weapon = items.get(3);
		if (weapon == null)
			return 1426;
		if (weapon.getId() == 28004 || weapon.getId() == 26787)
			return 1713;
		if (weapon.getId() == 6195)
			return 1271;
		return weapon.getDefinitions().getRenderAnimId();
	}

	public boolean hasShield() {
		return items.get(5) != null;
	}

	public int getWeaponId() {
		Item item = items.get(SLOT_WEAPON);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getChestId() {
		Item item = items.get(SLOT_CHEST);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getHatId() {
		Item item = items.get(SLOT_HAT);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getShieldId() {
		Item item = items.get(SLOT_SHIELD);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getLegsId() {
		Item item = items.get(SLOT_LEGS);
		if (item == null)
			return -1;
		return item.getId();
	}

	public void removeAmmo(int ammoId, int ammount) {	
		if (ammount == -1) {
			if (ammoId == 25202) {
			refresh(SLOT_WEAPON);
			} else {		
			items.remove(SLOT_WEAPON, new Item(ammoId, 1));
			refresh(SLOT_WEAPON);
			}
		} else {
			items.remove(SLOT_ARROWS, new Item(ammoId, ammount));
			refresh(SLOT_ARROWS);
		}
	}

	public int getAuraId() {
		Item item = items.get(SLOT_AURA);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getCapeId() {
		Item item = items.get(SLOT_CAPE);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getRingId() {
		Item item = items.get(SLOT_RING);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getAmmoId() {
		Item item = items.get(SLOT_ARROWS);
		if (item == null)
			return -1;
		return item.getId();
	}

	public void deleteItem(int itemId, int amount) {
		Item[] itemsBefore = items.getItemsCopy();
		items.remove(new Item(itemId, amount));
		refreshItems(itemsBefore);
	}

	public void refreshItems(Item[] itemsBefore) {
		int[] changedSlots = new int[itemsBefore.length];
		int count = 0;
		for (int index = 0; index < itemsBefore.length; index++) {
			if (itemsBefore[index] != items.getItems()[index])
				changedSlots[count++] = index;
		}
		int[] finalChangedSlots = new int[count];
		System.arraycopy(changedSlots, 0, finalChangedSlots, 0, count);
		refresh(finalChangedSlots);
	}
	
	public void refresh() {
		Item[] itemsBefore = items.getItemsCopy();
		refreshItems(itemsBefore);
	}

	public int getBootsId() {
		Item item = items.get(SLOT_FEET);
		if (item == null)
			return -1;
		return item.getId();
	}

	public int getGlovesId() {
		Item item = items.get(SLOT_HANDS);
		if (item == null)
			return -1;
		return item.getId();
	}

	public ItemsContainer<Item> getItems() {
		return items;
	}

	public int getEquipmentHpIncrease() {
		return equipmentHpIncrease;
	}

	public void setEquipmentHpIncrease(int hp) {
		this.equipmentHpIncrease = hp;
	}

	public boolean wearingArmour() {
		return getItem(SLOT_HAT) != null || getItem(SLOT_CAPE) != null
				|| getItem(SLOT_AMULET) != null || getItem(SLOT_WEAPON) != null
				|| getItem(SLOT_CHEST) != null || getItem(SLOT_SHIELD) != null
				|| getItem(SLOT_LEGS) != null || getItem(SLOT_HANDS) != null
				|| getItem(SLOT_FEET) != null;
	}

	public int wearingThreeItems() {
		int amount = 0;
		if (items.get(SLOT_AMULET) != null)
			amount++;
		if (items.get(SLOT_CHEST) != null)
			amount++;
		if (items.get(SLOT_LEGS) != null)
			amount++;
		if (items.get(SLOT_FEET) != null)
			amount++;
		if (items.get(SLOT_CAPE) != null)
			amount++;
		if (items.get(SLOT_WEAPON) != null)
			amount++;
		if (items.get(SLOT_HANDS) != null)
			amount++;
		if (items.get(SLOT_HAT) != null)
			amount++;
		if (items.get(SLOT_AMULET) != null)
			amount++;
		if (items.get(SLOT_SHIELD) != null)
			amount++;
		if (items.get(SLOT_RING) != null)
			amount++;
		return amount;
	}
	
	public int getAmuletId() {
		Item item = items.get(SLOT_AMULET);
		if (item == null)
			return -1;
		return item.getId();
	}

	public boolean hasTwoHandedWeapon() {
		Item weapon = items.get(SLOT_WEAPON);
		return weapon != null && isTwoHandedWeapon(weapon);
	}
}
