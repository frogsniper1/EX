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
import com.rs.utils.Logger;

public class UnlitBeacon {

	public enum logstoOffer {
		NORMAL(1511, 50), OAK(1521, 180), WILLOW(1519, 228), MAPLE(1517, 280), YEW(
				1515, 320), MAGIC(1513, 450);

		private int id;
		private double experience;

		private static Map<Integer, logstoOffer> bones = new HashMap<Integer, logstoOffer>();

		static {
			for (logstoOffer bone : logstoOffer.values()) {
				bones.put(bone.getId(), bone);
			}
		}

		public static logstoOffer forId(int id) {
			return bones.get(id);
		}

		private logstoOffer(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}

		public static boolean stopOffer = false;

		public static void RunBeacon(final Player player, Item item) {
			final int itemId = item.getId();
			new ItemDefinitions(item.getId());
			final logstoOffer logs = logstoOffer.forId(item.getId());
			WorldTasksManager.schedule(new WorldTask() {

				public void run() {
					try {
						if (!player.getInventory().containsItem(itemId, 1)) {
							stop();
							player.unlock();
							return;
						}
						player.getPackets().sendGameMessage("You throw a log in to the Unlit Beacon.");
						player.setNextAnimation(new Animation(835));
						player.getInventory().addItem(995, 15844);
						player.lock();
						player.getInventory().deleteItem(new Item(itemId, 1));
						double xp = logs.getExperience() * player.getAuraManager().getPrayerMultiplier();
						player.getSkills().addXp(Skills.FIREMAKING, xp);
						player.getInventory().refresh();
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 0, 3);
		}
	}
}
