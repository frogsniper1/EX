package com.rs.game.player.content;

import java.util.HashMap;
import java.util.Map;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;

public class GildedAltar {

	public enum bonestoOffer {

		NORMAL(526, 200),

		BIG(532, 300),

		OURG(4834, 500),

		DRAGON(536, 520),

		FROST_DRAGON(18830, 750),
		
		BABY_DRAGON(534, 420);

		private int id;
		private double experience;

		private static Map<Integer, bonestoOffer> bones = new HashMap<Integer, bonestoOffer>();

		static {
			for (bonestoOffer bone : bonestoOffer.values()) {
				bones.put(bone.getId(), bone);
			}
		}

		public static bonestoOffer forId(int id) {
			return bones.get(id);
		}

		private bonestoOffer(int id, double experience) {
			this.id = id;
			this.experience = experience;
		}

		public int getId() {
			return id;
		}

		public double getExperience() {
			return experience;
		}

		public static boolean stopOfferGod = false;

		public static void offerprayerGod(final Player player, Item item, WorldObject object) {
			final int itemId = item.getId();
			final bonestoOffer bone = bonestoOffer.forId(item.getId());
			WorldTasksManager.schedule(new WorldTask() {
				public void run() {
					try {
						if (!player.getInventory().containsItem(itemId, 1)) {
							stop();
							return;
						}
						player.getPackets()
								.sendGameMessage(
										"The gods are very pleased with your offering.");
						player.setNextAnimation(new Animation(3705));
						World.sendGraphics(null, new Graphics(624), new WorldTile(object.getX(), object.getY(), object.getPlane()));
						player.getInventory().deleteItem(new Item(itemId, 1));
						double xp = bone.getExperience()
								* player.getAuraManager().getPrayerMultiplier();
						player.getSkills().addXp(Skills.PRAYER, xp);
						player.getInventory().refresh();
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, 0, 3);
		}
	}
}
					
						
