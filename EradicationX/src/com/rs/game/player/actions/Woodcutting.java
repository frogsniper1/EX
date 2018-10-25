package com.rs.game.player.actions;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;

public final class Woodcutting extends Action {

    private static final int[] BIRD_NESTS = { 5070, 5071, 5072, 5073, 5074, 5075, 7413, 11966 };
    public static enum TreeDefinitions {

	NORMAL(1, 25, 1511, 20, 4, 1341, 8, 0), // TODO

	DRAMEN(36, 0, 771, 20, 4, -1, 8, 0),

	EVERGREEN(1, 25, 1511, 20, 4, 57931, 8, 0),

	DEAD(1, 25, 1511, 20, 4, 12733, 8, 0),

	OAK(15, 37.5, 1521, 30, 4, 1341, 15, 15), // TODO

	WILLOW(30, 67.5, 1519, 60, 4, 1341, 51, 15), // TODO

	MAPLE(45, 100, 1517, 83, 16, 31057, 72, 10),

	YEW(60, 175, 1515, 120, 17, 1341, 94, 10), // TODO

	IVY(68, 332.5, -1, 120, 17, 46319, 58, 10),

	MAGIC(75, 250, 1513, 20, 4, 37824, 8, 10000),

	CURSED_MAGIC(82, 250, 1513, 150, 21, 37822, 121, 10),

	FRUIT_TREES(1, 25, -1, 20, 4, 1341, 8, 0),

	MUTATED_VINE(83, 140, 21358, 83, 16, -1, 72, 0),

	CURLY_VINE(83, 140, null, 83, 16, 12279, 72, 0),

	CURLY_VINE_COLLECTABLE(83, 140, new int[] { 21350, 21350, 21350, 21350 }, 83, 16, 12283, 72, 0),

	STRAIT_VINE(83, 140, null, 83, 16, 12277, 72, 0),

	STRAIT_VINE_COLLECTABLE(83, 140, new int[] { 21349, 21349, 21349, 21349 }, 83, 16, 12283, 72, 0);

	private int level;
	private double xp;
	private int[] logsId;
	private int logBaseTime;
	private int logRandomTime;
	private int stumpId;
	private int respawnDelay;
	private int randomLifeProbability;

	private TreeDefinitions(int level, double xp, int[] logsId, int logBaseTime, int logRandomTime, int stumpId, int respawnDelay, int randomLifeProbability) {
	    this.level = level;
	    this.xp = xp;
	    this.logsId = logsId;
	    this.logBaseTime = logBaseTime;
	    this.logRandomTime = logRandomTime;
	    this.stumpId = stumpId;
	    this.respawnDelay = respawnDelay;
	    this.randomLifeProbability = randomLifeProbability;
	}

	private TreeDefinitions(int level, double xp, int logsId, int logBaseTime, int logRandomTime, int stumpId, int respawnDelay, int randomLifeProbability) {
	    this(level, xp, new int[] { logsId }, logBaseTime, logRandomTime, stumpId, respawnDelay, randomLifeProbability);
	}

	public int getLevel() {
	    return level;
	}

	public double getXp() {
	    return xp;
	}

	public int[] getLogsId() {
	    return logsId;
	}

	public int getLogBaseTime() {
	    return logBaseTime;
	}

	public int getLogRandomTime() {
	    return logRandomTime;
	}

	public int getStumpId() {
	    return stumpId;
	}

	public int getRespawnDelay() {
	    return respawnDelay;
	}

	public int getRandomLifeProbability() {
	    return randomLifeProbability;
	}
    }

	    public enum HatchetDefinitions {

	BRONZE(1351, 1, 1, 879),

	IRON(1349, 5, 2, 877),

	STEEL(1353, 5, 3, 875),

	BLACK(1361, 11, 4, 873),

	MITHRIL(1355, 21, 5, 871),

	ADAMANT(1357, 31, 7, 869),

	RUNE(1359, 41, 10, 867),

	DRAGON(6739, 61, 13, 2846),

	INFERNO(13661, 61, 13, 10251);

	private int itemId, levelRequried, axeTime, emoteId;

	private HatchetDefinitions(int itemId, int levelRequried, int axeTime, int emoteId) {
	    this.itemId = itemId;
	    this.levelRequried = levelRequried;
	    this.axeTime = axeTime;
	    this.emoteId = emoteId;
	}

	public int getItemId() {
	    return itemId;
	}

	public int getLevelRequried() {
	    return levelRequried;
	}

	public int getAxeTime() {
	    return axeTime;
	}

	public int getEmoteId() {
	    return emoteId;
	}
    }
	
	private WorldObject tree;
	private TreeDefinitions definitions;
	@SuppressWarnings("unused")
	private HatchetDefinitions hatchet;

	private int emoteId;
	private boolean usingBeaver = false;
	private int axeTime;

	public Woodcutting(WorldObject tree, TreeDefinitions definitions) {
		this.tree = tree;
		this.definitions = definitions;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		player.getPackets()
				.sendGameMessage(
						usingBeaver ? "Your beaver uses its strong teeth to chop down the tree..."
								: "You swing your hatchet at the "
										+ (TreeDefinitions.IVY == definitions ? "ivy"
												: "tree") + "...", true);
		setActionDelay(player, getWoodcuttingDelay(player));
		return true;
	}

	private int getWoodcuttingDelay(Player player) {
		int summoningBonus = player.getFamiliar() != null ? (player
				.getFamiliar().getId() == 6808 || player.getFamiliar().getId() == 6807) ? 10
				: 0
				: 0;
		int wcTimer = definitions.getLogBaseTime()
				- (player.getSkills().getLevel(8) + summoningBonus)
				- Utils.getRandom(axeTime);
		if (wcTimer < 1 + definitions.getLogRandomTime())
			wcTimer = 1 + Utils.getRandom(definitions.getLogRandomTime());
		wcTimer /= player.getAuraManager().getWoodcuttingAccurayMultiplier();
		return wcTimer;
	}

	private boolean checkAll(Player player) {
		if (!hasAxe(player)) {
			player.getPackets().sendGameMessage(
					"You need a hatchet to chop down this tree.");
			return false;
		}
		if (!setAxe(player)) {
			player.getPackets().sendGameMessage(
					"You dont have the required level to use that axe.");
			return false;
		}
		if (!hasWoodcuttingLevel(player))
			return false;
		if (!player.getInventory().hasFreeSlots()) {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			return false;
		}
		return true;
	}

	private boolean hasWoodcuttingLevel(Player player) {
		if (definitions.getLevel() > player.getSkills().getLevel(8)) {
			player.getPackets().sendGameMessage(
					"You need a woodcutting level of " + definitions.getLevel()
							+ " to chop down this tree.");
			return false;
		}
		return true;
	}

	private boolean setAxe(Player player) {
		int level = player.getSkills().getLevel(8);
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId != -1) {
			switch (weaponId) {
			case 6739: // dragon axe
				if (level >= 61) {
					emoteId = 2846;
					axeTime = 13;
					return true;
				}
				break;
			case 1359: // rune axe
				if (level >= 41) {
					emoteId = 867;
					axeTime = 10;
					return true;
				}
				break;
			case 1357: // adam axe
				if (level >= 31) {
					emoteId = 869;
					axeTime = 7;
					return true;
				}
				break;
			case 1355: // mit axe
				if (level >= 21) {
					emoteId = 871;
					axeTime = 5;
					return true;
				}
				break;
			case 1361: // black axe
				if (level >= 11) {
					emoteId = 873;
					axeTime = 4;
					return true;
				}
				break;
			case 1353: // steel axe
				if (level >= 6) {
					emoteId = 875;
					axeTime = 3;
					return true;
				}
				break;
			case 1349: // iron axe
				emoteId = 877;
				axeTime = 2;
				return true;
			case 1351: // bronze axe
				emoteId = 879;
				axeTime = 1;
				return true;
			case 13661: // Inferno adze
				if (level >= 61) {
					emoteId = 10251;
					axeTime = 13;
					return true;
				}
				break;
			}
		}
		if (player.getInventory().containsOneItem(6739)) {
			if (level >= 61) {
				emoteId = 2846;
				axeTime = 13;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1359)) {
			if (level >= 41) {
				emoteId = 867;
				axeTime = 10;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1357)) {
			if (level >= 31) {
				emoteId = 869;
				axeTime = 7;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1355)) {
			if (level >= 21) {
				emoteId = 871;
				axeTime = 5;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1361)) {
			if (level >= 11) {
				emoteId = 873;
				axeTime = 4;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1353)) {
			if (level >= 6) {
				emoteId = 875;
				axeTime = 3;
				return true;
			}
		}
		if (player.getInventory().containsOneItem(1349)) {
			emoteId = 877;
			axeTime = 2;
			return true;
		}
		if (player.getToolbelt().contains(1351)) {
			emoteId = 879;
			axeTime = 1;
			return true;
		}
		if (player.getInventory().containsOneItem(13661)) {
			if (level >= 61) {
				emoteId = 10251;
				axeTime = 13;
				return true;
			}
		}
		return false;

	}

	private boolean hasAxe(Player player) {
		if (player.getInventory().containsOneItem(1351, 1349, 1353, 1355, 1357,
				1361, 1359, 6739, 13661))
			return true;
		if (player.getToolbelt().contains(1351))
			return true;
		int weaponId = player.getEquipment().getWeaponId();
		if (weaponId == -1)
			return false;
		switch (weaponId) {
		case 1351:// Bronze Axe
		case 1349:// Iron Axe
		case 1353:// Steel Axe
		case 1361:// Black Axe
		case 1355:// Mithril Axe
		case 1357:// Adamant Axe
		case 1359:// Rune Axe
		case 6739:// Dragon Axe
		case 13661: // Inferno adze
			return true;
		default:
			return false;
		}

	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(new Animation(usingBeaver ? 1 : emoteId));
		return checkTree(player);
	}

	private boolean usedDeplateAurora;

	@Override
	public int processWithDelay(Player player) {
		addLog(player);
		if (!usedDeplateAurora
				&& (1 + Math.random()) < player.getAuraManager()
						.getChanceNotDepleteMN_WC()) {
			usedDeplateAurora = true;
		} else if (Utils.getRandom(definitions.getRandomLifeProbability()) == 0) {
			long time = definitions.respawnDelay * 600;
			World.spawnTemporaryObject(
					new WorldObject(definitions.getStumpId(), tree.getType(),
							tree.getRotation(), tree.getX(), tree.getY(), tree
									.getPlane()), time);
			if (tree.getPlane() < 3 && definitions != TreeDefinitions.IVY) {
				WorldObject object = World.getObject(new WorldTile(
						tree.getX() - 1, tree.getY() - 1, tree.getPlane() + 1));

				if (object == null) {
					object = World.getObject(new WorldTile(tree.getX(), tree
							.getY() - 1, tree.getPlane() + 1));
					if (object == null) {
						object = World.getObject(new WorldTile(tree.getX() - 1,
								tree.getY(), tree.getPlane() + 1));
						if (object == null) {
							object = World.getObject(new WorldTile(tree.getX(),
									tree.getY(), tree.getPlane() + 1));
						}
					}
				}

				if (object != null)
					World.removeTemporaryObject(object, time, false);
			}
			player.setNextAnimation(new Animation(-1));
			return -1;
		}
		if (!player.getInventory().hasFreeSlots()) {
			player.setNextAnimation(new Animation(-1));
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			return -1;
		}
		return getWoodcuttingDelay(player);
	}

	private void addLog(Player player) {
		double xpBoost = 1.00;
		if (player.getEquipment().getChestId() == 10939)
			xpBoost += 0.050;
		if (player.getEquipment().getLegsId() == 10940)
			xpBoost += 0.050;
		if (player.getEquipment().getHatId() == 10941)
			xpBoost += 0.050;
		if (player.getEquipment().getBootsId() == 10933)
			xpBoost += 0.050;
		if (player.getEquipment().getChestId() == 10939
				&& player.getEquipment().getLegsId() == 10940
				&& player.getEquipment().getHatId() == 10941
				&& player.getEquipment().getBootsId() == 10933)
			xpBoost += 0.005;
		player.getSkills().addXp(8, definitions.getXp() * xpBoost);
		    for (int item : definitions.getLogsId())
				player.getInventory().addItemDrop(item, 1);
		if (definitions == TreeDefinitions.IVY) {
			player.getPackets().sendGameMessage(
					"You succesfully cut an ivy vine.", true);
			// todo gfx
		} else {
		String logName = ItemDefinitions.getItemDefinitions(definitions.getLogsId()[0]).getName().toLowerCase();
		player.getPackets().sendGameMessage("You get some " + logName + ".", true);
			// todo infernal adze
		}
		player.checkBot();//anti afk bot
	}

    public static void addLog(TreeDefinitions definitions, boolean usingBeaver, Player player) {
	if (!usingBeaver) {
	    double xpBoost = 1.00;
	    if (player.getEquipment().getChestId() == 10939)
		xpBoost += 0.008;
	    if (player.getEquipment().getLegsId() == 10940)
		xpBoost += 0.006;
	    if (player.getEquipment().getHatId() == 10941)
		xpBoost += 0.004;
	    if (player.getEquipment().getBootsId() == 10933)
		xpBoost += 0.002;
	    if (player.getEquipment().getChestId() == 10939 && player.getEquipment().getLegsId() == 10940 && player.getEquipment().getHatId() == 10941 && player.getEquipment().getBootsId() == 10933)
		xpBoost += 0.005;
	    player.getSkills().addXp(8, definitions.getXp() * xpBoost);
	}
	if (definitions.getLogsId() != null) {
	    if (usingBeaver) {
		if (player.getFamiliar() != null) {
		    for (int item : definitions.getLogsId())
			player.getInventory().addItemDrop(item, 1);
		}
	    } else {
		for (int item : definitions.getLogsId())
		    player.getInventory().addItemDrop(item, 1);
		if (Utils.random(50) == 0) {
		    player.getInventory().addItemDrop(BIRD_NESTS[Utils.random(BIRD_NESTS.length)], 1);
		    player.getPackets().sendGameMessage("A bird's nest falls out of the tree!");
		}
	    }
	    if (definitions == TreeDefinitions.IVY) {
		player.getPackets().sendGameMessage("You succesfully cut an ivy vine.", true);
		// todo gfx
	    } else {
		String logName = ItemDefinitions.getItemDefinitions(definitions.getLogsId()[0]).getName().toLowerCase();
		player.getPackets().sendGameMessage("You get some " + logName + ".", true);
		// todo infernal adze
	    }
	}
    }
	
	public static HatchetDefinitions getHatchet(Player player) {
	HatchetDefinitions hatchet = null;
	for (HatchetDefinitions def : HatchetDefinitions.values()) {
	    if (player.getInventory().containsItemToolBelt(def.itemId) || player.getEquipment().getWeaponId() == def.itemId) {
		hatchet = def;
		if (player.getSkills().getLevel(Skills.WOODCUTTING) < hatchet.levelRequried) {
		    hatchet = null;
		    break;
		}
	    }
	}
	return hatchet;
    }
	
	private boolean checkTree(Player player) {
		return World.getRegion(tree.getRegionId()).containsObject(tree.getId(),
				tree);
	}

	@Override
	public void stop(Player player) {
		setActionDelay(player, 3);
	}

}
