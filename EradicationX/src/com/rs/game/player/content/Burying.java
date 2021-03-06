package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public class Burying {

	public enum Bone {
		BONES(526, 25),
		BURNT_BONES(528, 25),
		WOLF_BONES(2859, 25),
		MONKEY_BONES(3183, 25),
		BAT_BONES(530, 25),
		BIG_BONES(532, 75),
		JOGRE_BONES(3125, 75),
		ZOGRE_BONES(4812, 115),
		BABYDRAGON_BONES(534, 150),
		WYVERN_BONES(6812, 210),
		DRAGON_BONES(536, 250),
		FAYRG_BONES(4830, 300),
		RAURG_BONES(4832, 320),
		OURG_BONES(4834, 350),
		FROST_DRAGON_BONES(18830, 400),
		DAGANNOTH_BONES(6729, 500);


		private int id;
		private double experience;

		private static Map<Integer, Bone> bones = new HashMap<Integer, Bone>();

		static {
			for (Bone bone : Bone.values()) {
				bones.put(bone.getId(), bone);
			}
		}

		public static Bone forId(int id) {
			return bones.get(id);
		}

		private Bone(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}

		public static final Animation BURY_ANIMATION = new Animation(827);

		public static void bury(final Player player, int inventorySlot) {
			final Item item = player.getInventory().getItem(inventorySlot);
			if (item == null || Bone.forId(item.getId()) == null)
				return;
			if (player.getBoneDelay() > Utils.currentTimeMillis())
				return;
			final Bone bone = Bone.forId(item.getId());
			final ItemDefinitions itemDef = new ItemDefinitions(item.getId());
			player.addBoneDelay(3000);
			player.getPackets().sendSound(2738, 0, 1);
			player.setNextAnimation(new Animation(827));
			player.isBurying = true;
			player.getPackets().sendGameMessage(
					"You dig a hole in the ground...");
			WorldTasksManager.schedule(new WorldTask() {
				@Override
				public void run() {
					player.getPackets().sendGameMessage(
							"You bury the " + itemDef.getName().toLowerCase());
					if (player.getInventory().containsItem(item.getId(), 1)) {
					player.getInventory().deleteItem(item.getId(), 1);
					player.getSkills().addXp(Skills.PRAYER,
							bone.getExperience());
					} else {
						player.sm("Wait a minute - You dropped the bone! Noob.");
					}
					stop();
					player.isBurying = false;
				}

			}, 2);
		}
	}
}
