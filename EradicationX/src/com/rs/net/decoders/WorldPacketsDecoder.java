package com.rs.net.decoders;

import com.rs.Settings;
import com.rs.content.exchange.ItemOffer;
import com.rs.content.utils.IPMute;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.DamageDummy;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.creations.StealingCreation;
import com.rs.game.minigames.duel.DuelRules;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.ChatMessage;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Inventory;
import com.rs.game.player.LogicPacket;
import com.rs.game.player.Player;
import com.rs.game.player.PublicChatMessage;
import com.rs.game.player.Raffle;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.PlayerFollow;
import com.rs.game.player.content.Commands;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.custom.DoubleXpManager;
import com.rs.game.player.content.custom.PriceManager;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.io.InputStream;
import com.rs.net.Session;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.net.decoders.handlers.InventoryOptionsHandler;
import com.rs.net.decoders.handlers.NPCHandler;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.DisplayNames;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.Utils;
import com.rs.utils.huffman.Huffman;

public final class WorldPacketsDecoder extends Decoder {

	private static final byte[] PACKET_SIZES = new byte[104];

	private final static int WALKING_PACKET = 8;
	private final static int MINI_WALKING_PACKET = 58;
	private final static int AFK_PACKET = -1;
	public final static int ACTION_BUTTON1_PACKET = 14;
	public final static int ACTION_BUTTON2_PACKET = 67;
	public final static int ACTION_BUTTON3_PACKET = 5;
	public final static int ACTION_BUTTON4_PACKET = 55;
	public final static int ACTION_BUTTON5_PACKET = 68;
	public final static int ACTION_BUTTON6_PACKET = 90;
	public final static int ACTION_BUTTON7_PACKET = 6;
	public final static int ACTION_BUTTON8_PACKET = 32;
	public final static int ACTION_BUTTON9_PACKET = 27;
	public final static int WORLD_MAP_CLICK = 38;
	public final static int ACTION_BUTTON10_PACKET = 96;
	private final static int PLAYER_OPTION_5_PACKET = 77;
	public final static int RECEIVE_PACKET_COUNT_PACKET = 33;
	private final static int MAGIC_ON_ITEM_PACKET = -1;
	private final static int PLAYER_OPTION_4_PACKET = 17;
	private final static int MOVE_CAMERA_PACKET = 103;
	private final static int INTERFACE_ON_OBJECT = 37;
	private final static int CLICK_PACKET = -1;
	private final static int MOUVE_MOUSE_PACKET = -1;
	private final static int KEY_TYPED_PACKET = 1;
	private final static int CLOSE_INTERFACE_PACKET = 54;
	private final static int COMMANDS_PACKET = 60;
	private final static int ITEM_ON_ITEM_PACKET = 3;
	private final static int IN_OUT_SCREEN_PACKET = -1;
	private final static int DONE_LOADING_REGION_PACKET = 30;
	private final static int PING_PACKET = 21;
	private final static int SCREEN_PACKET = 98;
	private final static int CHAT_TYPE_PACKET = 83;
	private final static int CHAT_PACKET = 53;
	//private final static int PUBLIC_QUICK_CHAT_PACKET = 86;
	private final static int ADD_FRIEND_PACKET = 89;
	private final static int ADD_IGNORE_PACKET = 4;
	private final static int REMOVE_IGNORE_PACKET = 73;
	private final static int JOIN_FRIEND_CHAT_PACKET = 36;
	private final static int CHANGE_FRIEND_CHAT_PACKET = 22;
	private final static int KICK_FRIEND_CHAT_PACKET = 74;
	private final static int REMOVE_FRIEND_PACKET = 24;
	private final static int SEND_FRIEND_MESSAGE_PACKET = 82;
	//private final static int SEND_FRIEND_QUICK_CHAT_PACKET = 0;
	private final static int OBJECT_CLICK1_PACKET = 26;
	private final static int OBJECT_CLICK2_PACKET = 59;
	private final static int OBJECT_CLICK3_PACKET = 40;
	private final static int OBJECT_CLICK4_PACKET = 23;
	private final static int OBJECT_CLICK5_PACKET = 80;
	private final static int OBJECT_EXAMINE_PACKET = 25;
	private final static int NPC_CLICK1_PACKET = 31;
	private final static int NPC_CLICK2_PACKET = 101;
	private final static int NPC_CLICK3_PACKET = 34;
	private final static int ATTACK_NPC = 20;
	private final static int PLAYER_OPTION_1_PACKET = 42;
	private final static int PLAYER_OPTION_2_PACKET = 46;
	private final static int ITEM_TAKE_PACKET = 57;
	private final static int DIALOGUE_CONTINUE_PACKET = 72;
	private final static int ENTER_INTEGER_PACKET = 81;
	private final static int ENTER_NAME_PACKET = 29;
	private final static int ENTER_STRING_PACKET = 48;
	private final static int SWITCH_INTERFACE_ITEM_PACKET = 76;
	private final static int INTERFACE_ON_PLAYER = 50;
	private final static int INTERFACE_ON_NPC = 66;
	private final static int COLOR_ID_PACKET = 97;
	private static final int NPC_EXAMINE_PACKET = 9;
	private final static int REPORT_ABUSE_PACKET = -1;
	
	private static final int FORUM_THREAD_ID_PACKET = 18;
	private final static int PLAYER_OPTION_9_PACKET = 56;

	static {
		loadPacketSizes();
	}

	public static void loadPacketSizes() {
		PACKET_SIZES[0] = -1;
		PACKET_SIZES[1] = -2;
		PACKET_SIZES[2] = -1;
		PACKET_SIZES[3] = 16;
		PACKET_SIZES[4] = -1;
		PACKET_SIZES[5] = 8;
		PACKET_SIZES[6] = 8;
		PACKET_SIZES[7] = 3;
		PACKET_SIZES[8] = -1;
		PACKET_SIZES[9] = 3;
		PACKET_SIZES[10] = -1;
		PACKET_SIZES[11] = -1;
		PACKET_SIZES[12] = -1;
		PACKET_SIZES[13] = 7;
		PACKET_SIZES[14] = 8;
		PACKET_SIZES[15] = 6;
		PACKET_SIZES[16] = 2;
		PACKET_SIZES[17] = 3;
		PACKET_SIZES[18] = -1;
		PACKET_SIZES[19] = -2;
		PACKET_SIZES[20] = 3;
		PACKET_SIZES[21] = 0;
		PACKET_SIZES[22] = -1;
		PACKET_SIZES[23] = 9;
		PACKET_SIZES[24] = -1;
		PACKET_SIZES[25] = 9;
		PACKET_SIZES[26] = 9;
		PACKET_SIZES[27] = 8;
		PACKET_SIZES[28] = 4;
		PACKET_SIZES[29] = -1;
		PACKET_SIZES[30] = 0;
		PACKET_SIZES[31] = 3;
		PACKET_SIZES[32] = 8;
		PACKET_SIZES[33] = 4;
		PACKET_SIZES[34] = 3;
		PACKET_SIZES[35] = -1;
		PACKET_SIZES[36] = -1;
		PACKET_SIZES[37] = 17;
		PACKET_SIZES[38] = 4;
		PACKET_SIZES[39] = 4;
		PACKET_SIZES[40] = 9;
		PACKET_SIZES[41] = -1;
		PACKET_SIZES[42] = 3;
		PACKET_SIZES[43] = 7;
		PACKET_SIZES[44] = -2;
		PACKET_SIZES[45] = 7;
		PACKET_SIZES[46] = 3;
		PACKET_SIZES[47] = 4;
		PACKET_SIZES[48] = -1;
		PACKET_SIZES[49] = 3;
		PACKET_SIZES[50] = 11;
		PACKET_SIZES[51] = 3;
		PACKET_SIZES[52] = -1;
		PACKET_SIZES[53] = -1;
		PACKET_SIZES[54] = 0;
		PACKET_SIZES[55] = 8;
		PACKET_SIZES[56] = 3;
		PACKET_SIZES[57] = 7;
		PACKET_SIZES[58] = -1;
		PACKET_SIZES[59] = 9;
		PACKET_SIZES[60] = -1;
		PACKET_SIZES[61] = 7;
		PACKET_SIZES[62] = 7;
		PACKET_SIZES[63] = 12;
		PACKET_SIZES[64] = 4;
		PACKET_SIZES[65] = 3;
		PACKET_SIZES[66] = 11;
		PACKET_SIZES[67] = 8;
		PACKET_SIZES[68] = 8;
		PACKET_SIZES[69] = 15;
		PACKET_SIZES[70] = 1;
		PACKET_SIZES[71] = 2;
		PACKET_SIZES[72] = 6;
		PACKET_SIZES[73] = -1;
		PACKET_SIZES[74] = -1;
		PACKET_SIZES[75] = -2;
		PACKET_SIZES[76] = 16;
		PACKET_SIZES[77] = 3;
		PACKET_SIZES[78] = 1;
		PACKET_SIZES[79] = 3;
		PACKET_SIZES[80] = 9;
		PACKET_SIZES[81] = 4;
		PACKET_SIZES[82] = -2;
		PACKET_SIZES[83] = 1;
		PACKET_SIZES[84] = 1;
		PACKET_SIZES[85] = 3;
		PACKET_SIZES[86] = -1;
		PACKET_SIZES[87] = 4;
		PACKET_SIZES[88] = 3;
		PACKET_SIZES[89] = -1;
		PACKET_SIZES[90] = 8;
		PACKET_SIZES[91] = -2;
		PACKET_SIZES[92] = -1;
		PACKET_SIZES[93] = -1;
		PACKET_SIZES[94] = 9;
		PACKET_SIZES[95] = -2;
		PACKET_SIZES[96] = 8;
		PACKET_SIZES[97] = 2;
		PACKET_SIZES[98] = 6;
		PACKET_SIZES[99] = 2;
		PACKET_SIZES[100] = -2;
		PACKET_SIZES[101] = 3;
		PACKET_SIZES[102] = 7;
		PACKET_SIZES[103] = 4;
	}

	private Player player;
	private int chatType;
	public WorldPacketsDecoder(Session session, Player player) {
		super(session);
		this.player = player;
	}

	@Override
	public void decode(InputStream stream) {
		while (stream.getRemaining() > 0 && session.getChannel().isConnected() && !player.hasFinished()) {
			int packetId = stream.readPacket(player);
			if (packetId == 102) {
				if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
						|| player.isDead())
					return;
				long currentTime = Utils.currentTimeMillis();
				if (player.getLockDelay() > currentTime)
					return;
				int y = stream.readUnsignedShort();
				int x = stream.readUnsignedShortLE();
				final int id = stream.readUnsignedShort();
				final WorldTile tile = new WorldTile(x, y, player.getPlane());
				final int regionId = tile.getRegionId();
				if (!player.getMapRegionsIds().contains(regionId))
					return;
				final FloorItem item = World.getRegion(regionId).getGroundItem(id,
						tile, player);
				player.sm(ItemExamines.getExamine(item));
				if (item.getId() == 20141) {
				player.sm("This item costs 3B coins.");
				} else if (item.getId() == 27370) {
				player.sm("This crate contains 6,000 water runes, 2,000 blood runes, and 4,000 death runes.");	
				} else if (item.getId() == 27371) {
				player.sm("This crate contains 1,000 soul runes, 4,000 blood runes, and 4,000 death runes.");	
				} else if (item.getId() == 27372) {
				player.sm("This crate contains 10,000 earth runes, 4,000 astral runes, and 2,000 death runes.");			
				} else if (item.getId() == 1) {
				player.sm("This item costs coins.");
				} else {
				player.sm("This item costs " +Utils.formatNumber(PriceManager.getBuyPrice(item))+ " coins.");
				}
				if (player.getRights() > 2)
					player.sm("Item ID: " + item.getId());
				return;
			}
			if (packetId == 71) {
				final short itemId = (short) stream.readShort();
				final ItemOffer offer = new ItemOffer(player, itemId, player.offerType, player.getGESlot());
				offer.setPrice((int) (offer.getPrice() * 1.25));
				player.getGeOffers()[offer.getSlot()] = offer;
				offer.getOwner().getPackets().sendConfig(1109, offer.getId());
				offer.getOwner().getPackets().sendConfig(1110, 1);
				offer.getOwner().getPackets().sendConfig(1111, offer.getPrice());
				offer.getOwner().getPackets().sendConfig(1114, offer.getPrice());
				offer.getOwner().getPackets().sendConfig(1115, offer.getDefinitions().getValue() / 4);
				offer.getOwner().getPackets().sendConfig(1116, offer.getDefinitions().getValue() / 3);
				offer.getOwner().getPackets().sendIComponentText(105, 143, ItemExamines.getExamine(new Item(offer.getId())));
				break;
			}
			if (packetId >= PACKET_SIZES.length || packetId < 0) {
				if (Settings.DEBUG)
					System.out.println("PacketId " + packetId + " has fake packet id.");
				break;
			}
			int length = PACKET_SIZES[packetId];
			if (length == -1)
				length = stream.readUnsignedByte();
			else if (length == -2)
				length = stream.readUnsignedShort();
			else if (length == -3)
				length = stream.readInt();
			else if (length == -4) {
				length = stream.getRemaining();
				if (Settings.DEBUG)
					System.out.println("Invalid size for PacketId " + packetId + ". Size guessed to be " + length);
			}
			if (length > stream.getRemaining()) {
				length = stream.getRemaining();
				if (Settings.DEBUG)
					System.out.println("PacketId " + packetId
							+ " has fake size. - expected size " + length);
				// break;

			}
			/*
			 * System.out.println("PacketId " +packetId+
			 * " has . - expected size " +length);
			 */
			int startOffset = stream.getOffset();
			processPackets(packetId, stream, length);
			stream.setOffset(startOffset + length);
		}
	}

	public static void decodeLogicPacket(final Player player, LogicPacket packet) {
		int packetId = packet.getId();
		InputStream stream = new InputStream(packet.getData());
		if (packetId == WALKING_PACKET || packetId == MINI_WALKING_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() > currentTime)
				return;
			if (player.getFreezeDelay() >= currentTime) {
				player.sm(
						"A magical force prevents you from moving.");
				return;
			}
			int length = stream.getLength();
			/*if (packetId == MINI_WALKING_PACKET)
				length -= 13;*/
			int baseX = stream.readUnsignedShort128();
			boolean forceRun = stream.readUnsigned128Byte() == 1;
			int baseY = stream.readUnsignedShort128();
			int steps = (length - 5) / 2;
			if (steps > 25)
				steps = 25;
			player.stopAll();
			if(forceRun)
				player.setRun(forceRun);
			for (int step = 0; step < steps; step++)
				if (!player.addWalkSteps(baseX + stream.readUnsignedByte(),
						baseY + stream.readUnsignedByte(), 25,
						true))
					break;
		} else if (packetId == INTERFACE_ON_OBJECT) {
			boolean forceRun = stream.readByte128() == 1;
			int itemId = stream.readShortLE128();
			int y = stream.readShortLE128();
			int objectId = stream.readIntV2();
			int interfaceHash = stream.readInt();
			final int interfaceId = interfaceHash >> 16;
			int slot = stream.readShortLE();
			int x = stream.readShort128();
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() >= currentTime || player.getEmotesManager().getNextEmoteEnd() >= currentTime)
				return;
			final WorldTile tile = new WorldTile(x, y, player.getPlane());
			int regionId = tile.getRegionId();
			if (!player.getMapRegionsIds().contains(regionId))
				return;
			WorldObject mapObject = World.getRegion(regionId).getObject(objectId, tile);
			if (mapObject == null || mapObject.getId() != objectId)
				return;
			final WorldObject object = !player.isAtDynamicRegion() ? mapObject : new WorldObject(objectId, mapObject.getType(), mapObject.getRotation(), x, y, player.getPlane());
			final Item item = player.getInventory().getItem(slot);
			if (player.isDead() || Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (item == null || item.getId() != itemId)
				return;
			player.stopAll(false); // false
			if(forceRun)
				player.setRun(forceRun);
			switch (interfaceId) {
			case Inventory.INVENTORY_INTERFACE: // inventory
				ObjectHandler.handleItemOnObject(player, object, interfaceId, item);
				break;
			}
		} else if (packetId == PLAYER_OPTION_2_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerFollow(p2));
		} else if (packetId == PLAYER_OPTION_5_PACKET) {
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player other = World.getPlayers().get(playerIndex);
			if (other == null || other.isDead() || other.hasFinished()
					|| !player.getMapRegionsIds().contains(other.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			if (player.getInterfaceManager().containsScreenInter()) {
				player.getPackets()
						.sendGameMessage("The other player is busy.");
				return;
			}
			if (!other.withinDistance(player, 14)) {
				player.sm(
						"Unable to find target " + other.getDisplayName());
				return;
			}
			if (player.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
				player.getPackets()
						.sendGameMessage(
								"<col=B00000>You view "
										+ other.getDisplayName()
										+ " Stats's until 10 seconds after the end of combat.");
				return;
			}
			player.getInterfaceManager().sendInterface(1314);
			player.getPackets().sendIComponentText(1314, 91,
					"" + other.getDisplayName() + "'s stats");
			player.getPackets().sendIComponentText(1314, 90, "");
			player.getPackets().sendIComponentText(1314, 61,
					"" + other.getSkills().getLevel(0));// attack
			player.getPackets().sendIComponentText(1314, 62,
					"" + other.getSkills().getLevel(2)); // str
			player.getPackets().sendIComponentText(1314, 63,
					"" + other.getSkills().getLevel(1)); // def
			player.getPackets().sendIComponentText(1314, 65,
					"" + other.getSkills().getLevel(4)); // range
			player.getPackets().sendIComponentText(1314, 66,
					"" + other.getSkills().getLevel(5)); // prayer
			player.getPackets().sendIComponentText(1314, 64,
					"" + other.getSkills().getLevel(6)); // mage
			player.getPackets().sendIComponentText(1314, 78,
					"" + other.getSkills().getLevel(20)); // rc
			player.getPackets().sendIComponentText(1314, 81,
					"" + other.getSkills().getLevel(22)); // construction
			player.getPackets().sendIComponentText(1314, 76,
					"" + other.getSkills().getLevel(24)); // dung
			player.getPackets().sendIComponentText(1314, 82,
					"" + other.getSkills().getLevel(3)); // hitpoints
			player.getPackets().sendIComponentText(1314, 83,
					"" + other.getSkills().getLevel(16)); // agility
			player.getPackets().sendIComponentText(1314, 84,
					"" + other.getSkills().getLevel(15)); // herblore
			player.getPackets().sendIComponentText(1314, 80,
					"" + other.getSkills().getLevel(17)); // thiving
			player.getPackets().sendIComponentText(1314, 70,
					"" + other.getSkills().getLevel(12)); // crafting
			player.getPackets().sendIComponentText(1314, 85,
					"" + other.getSkills().getLevel(9)); // fletching
			player.getPackets().sendIComponentText(1314, 77,
					"" + other.getSkills().getLevel(18)); // slayer
			player.getPackets().sendIComponentText(1314, 79,
					"" + other.getSkills().getLevel(21)); // hunter
			player.getPackets().sendIComponentText(1314, 68,
					"" + other.getSkills().getLevel(14)); // mining
			player.getPackets().sendIComponentText(1314, 69,
					"" + other.getSkills().getLevel(13)); // smithing
			player.getPackets().sendIComponentText(1314, 74,
					"" + other.getSkills().getLevel(10)); // fishing
			player.getPackets().sendIComponentText(1314, 75,
					"" + other.getSkills().getLevel(7)); // cooking
			player.getPackets().sendIComponentText(1314, 73,
					"" + other.getSkills().getLevel(11)); // firemaking
			player.getPackets().sendIComponentText(1314, 71,
					"" + other.getSkills().getLevel(8)); // wc
			player.getPackets().sendIComponentText(1314, 72,
					"" + other.getSkills().getLevel(19)); // farming
			player.getPackets().sendIComponentText(1314, 67,
					"" + other.getSkills().getLevel(23)); // summining
			player.getPackets().sendIComponentText(1314, 87,
					"" + other.getMaxHitpoints()); // hitpoints
			player.getPackets().sendIComponentText(1314, 86,
					"" + other.getSkills().getCombatLevelWithSummoning()); // combatlevel
			player.getPackets().sendIComponentText(1314, 88,
					"" + other.getSkills().getTotalLevel(other)); // total level
			player.getPackets().sendIComponentText(1314, 89,
					"" + other.getSkills().getTotalXp(other)); // total level
			player.getTemporaryAttributtes().put("finding_player",
					Boolean.FALSE);
			return;
		} else if (packetId == PLAYER_OPTION_4_PACKET) {
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			if(player.isCantTrade()) {
				player.sm("You are busy.");
				return;
			}
			if (p2.getInterfaceManager().containsScreenInter() || p2.isCantTrade()) {
				player.sm("The other player is busy.");
				return;
			}
			if (!p2.withinDistance(player, 14)) {
				player.sm(
						"Unable to find target "+p2.getDisplayName());
				return;
			}

			if (p2.getTemporaryAttributtes().get("TradeTarget") == player) {
				p2.getTemporaryAttributtes().remove("TradeTarget");
				player.getTrade().openTrade(p2);
				p2.getTrade().openTrade(player);
				return;
			}
			player.getTemporaryAttributtes().put("TradeTarget", p2);
			player.sm("Sending " + p2.getDisplayName() + " a request...");
			p2.getPackets().sendTradeRequestMessage(player);
		} else if (packetId == PLAYER_OPTION_1_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis()
					|| !player.getControlerManager().canPlayerOption1(p2))
				return;
			if (player.playeroption1.equals("Duel")) {
				player.stopAll();
				if (p2.getInterfaceManager().containsScreenInter()) {
					player.getPackets().sendGameMessage("The other player is busy.");
					return;
				}
				if (p2.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
					player.getPackets()
							.sendGameMessage(
									"Other player is currently in combat.");
					return;
				}
				if (p2.getTemporaryAttributtes().get("DuelChallenged") == player) {
					player.getControlerManager().removeControlerWithoutCheck();
					p2.getControlerManager().removeControlerWithoutCheck();
					p2.getTemporaryAttributtes().remove("DuelChallenged");
					player.setLastDuelRules(new DuelRules(player, p2));
					p2.setLastDuelRules(new DuelRules(p2, player));
					player.getControlerManager().startControler("DuelArena", p2,
							p2.getTemporaryAttributtes().get("DuelFriendly"));
					p2.getControlerManager().startControler("DuelArena", player,
							p2.getTemporaryAttributtes().remove("DuelFriendly"));
					return;
				}
				player.getTemporaryAttributtes().put("DuelTarget", p2);
				player.getInterfaceManager().sendInterface(1310);
				player.getPackets().sendIComponentText(1310, 5, "You are about to challenge");
				player.getPackets().sendIComponentText(1310, 6, p2.getDisplayName());
				player.getPackets().sendIComponentText(1310, 7, "Combat Level: ");
				player.getPackets().sendIComponentText(1310, 8, "    "+p2.getSkills().getCombatLevelWithSummoning()+"");
				player.getPackets().sendIComponentText(1310, 9, "Killcount: ");
				player.getPackets().sendIComponentText(1310, 10,""+ p2.getKillCount());
				player.getPackets().sendIComponentText(1310, 15, "Duel");
				player.getPackets().sendIComponentText(1310, 50, "Cancel");
				player.getTemporaryAttributtes().put("WillDuelFriendly", true);
				player.getPackets().sendConfig(283, 67108864);
				return;
			}
			if (!player.isCanPvp())
				return;
			if (!player.getControlerManager().canAttack(p2))
				return;

			if (!player.isCanPvp() || !p2.isCanPvp()) {
				player.getPackets()
				.sendGameMessage(
						"You can only attack players in a player-vs-player area.");
				return;
			}
			if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
				if (player.getAttackedBy() != p2
						&& player.getAttackedByDelay() > Utils
						.currentTimeMillis()) {
					player.sm(
							"You are already in combat.");
					return;
				}
				if (p2.getAttackedBy() != player
						&& p2.getAttackedByDelay() > Utils.currentTimeMillis()) {
					if (p2.getAttackedBy() instanceof NPC) {
						p2.setAttackedBy(player);
					} else {
						player.sm(
								"That player is already in combat.");
						return;
					}
				}
			}
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerCombat(p2));
		} else if (packetId == ATTACK_NPC) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead()) {
				return;
			}
			if (player.getLockDelay() > Utils.currentTimeMillis()) {
				return;
			}
			int npcIndex = stream.readUnsignedShort128();
			boolean forceRun = stream.read128Byte() == 1;
			if(forceRun)
				player.setRun(forceRun);
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isDead() || npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId())
					|| !npc.getDefinitions().hasAttackOption()) {
				return;
			}
			if (!player.getControlerManager().canAttack(npc)) {
				return;
			}
			if (npc instanceof Familiar) {
				Familiar familiar = (Familiar) npc;
				if (familiar == player.getFamiliar()) {
					player.sm(
							"You can't attack your own familiar.");
					return;
				}
				if (!familiar.canAttack(player)) {
					player.sm(
							"You can't attack this npc.");
					return;
				}
			} else if (!npc.isForceMultiAttacked()) {
				if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
					if (player.getAttackedBy() != npc
							&& player.getAttackedByDelay() > Utils
							.currentTimeMillis()) {
						player.sm(
								"You are already in combat.");
						return;
					}
					if (npc.getAttackedBy() != player
							&& npc.getAttackedByDelay() > Utils
							.currentTimeMillis()) {
						player.sm(
								"This npc is already in combat.");
						return;
					}
					if (npc.equals(DamageDummy.dummy)) {
						if (!DamageDummy.dummy.getPlayer().equals(player)) {
							player.sm("That isn't your dummy. Dummy.");
						return;
						}
					}
						
				}
			}
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerCombat(npc));
		} else if (packetId == INTERFACE_ON_PLAYER) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			@SuppressWarnings("unused")
			int junk1 = stream.readUnsignedShort();
			int playerIndex = stream.readUnsignedShort();
			int interfaceHash = stream.readIntV2();
			@SuppressWarnings("unused")
			int junk2 = stream.readUnsignedShortLE128();
			@SuppressWarnings("unused")
			boolean unknown = stream.read128Byte() == 1;
			int interfaceId = interfaceHash >> 16;
			int componentId = interfaceHash - (interfaceId << 16);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (componentId == 65535)
				componentId = -1;
			if (componentId != -1
					&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId)
				return;
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			player.stopAll(false);
			switch (interfaceId) {
			case 1110:
				if (componentId == 87)
					ClansManager.invite(player, p2);
				break;
			case 662:
			case 747:
				if (player.getFamiliar() == null)
					return;
				player.resetWalkSteps();
				if ((interfaceId == 747 && componentId == 15)
						|| (interfaceId == 662 && componentId == 65)
						|| (interfaceId == 662 && componentId == 74)
						|| interfaceId == 747 && componentId == 18) {
					if ((interfaceId == 662 && componentId == 74
							|| interfaceId == 747 && componentId == 24 || interfaceId == 747
							&& componentId == 18)) {
						if (player.getFamiliar().getSpecialAttack() != SpecialAttack.ENTITY)
							return;
					}
					if (!player.isCanPvp() || !p2.isCanPvp()) {
						player.getPackets()
						.sendGameMessage(
								"You can only attack players in a player-vs-player area.");
						return;
					}
					if (!player.getFamiliar().canAttack(p2)) {
						player.getPackets()
						.sendGameMessage(
								"You can only use your familiar in a multi-zone area.");
						return;
					} else {
						player.getFamiliar().setSpecial(
								interfaceId == 662 && componentId == 74
								|| interfaceId == 747
								&& componentId == 18);
						player.getFamiliar().setTarget(p2);
					}
				}
				break;
			case 193:
				switch (componentId) {
				case 28:
				case 32:
				case 24:
				case 20:
				case 30:
				case 34:
				case 26:
				case 22:
				case 29:
				case 33:
				case 25:
				case 21:
				case 31:
				case 35:
				case 27:
				case 23:
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(p2
								.getCoordFaceX(p2.getSize()), p2
								.getCoordFaceY(p2.getSize()), p2.getPlane()));
						if (!player.getControlerManager().canAttack(p2))
							return;
						if (!player.isCanPvp() || !p2.isCanPvp()) {
							player.getPackets()
							.sendGameMessage(
									"You can only attack players in a player-vs-player area.");
							return;
						}
						if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
							if (player.getAttackedBy() != p2
									&& player.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								player.getPackets()
								.sendGameMessage(
										"That "
												+ (player
														.getAttackedBy() instanceof Player ? "player"
																: "npc")
																+ " is already in combat.");
								return;
							}
							if (p2.getAttackedBy() != player
									&& p2.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								if (p2.getAttackedBy() instanceof NPC) {
									p2.setAttackedBy(player); // changes enemy
									// to player,
									// player has
									// priority over
									// npc on single
									// areas
								} else {
									player.getPackets()
									.sendGameMessage(
											"That player is already in combat.");
									return;
								}
							}
						}
						player.getActionManager()
						.setAction(new PlayerCombat(p2));
					}
					break;
				}
			case 192:
				switch (componentId) {
				case 25: // air strike
				case 28: // water strike
				case 30: // earth strike
				case 32: // fire strike
				case 34: // air bolt
				case 39: // water bolt
				case 42: // earth bolt
				case 45: // fire bolt
				case 49: // air blast
				case 52: // water blast
				case 58: // earth blast
				case 63: // fire blast
				case 70: // air wave
				case 73: // water wave
				case 77: // earth wave
				case 80: // fire wave
				case 86: // teleblock
				case 84: // air surge
				case 87: // water surge
				case 89: // earth surge
				case 91: // fire surge
				case 99: // storm of armadyl
				case 36: // bind
				case 66: // Sara Strike
				case 67: // Guthix Claws
				case 68: // Flame of Zammy
				case 55: // snare
				case 81: // entangle
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(p2
								.getCoordFaceX(p2.getSize()), p2
								.getCoordFaceY(p2.getSize()), p2.getPlane()));
						if (!player.getControlerManager().canAttack(p2))
							return;
						if (!player.isCanPvp() || !p2.isCanPvp()) {
							player.getPackets()
							.sendGameMessage(
									"You can only attack players in a player-vs-player area.");
							return;
						}
						if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
							if (player.getAttackedBy() != p2
									&& player.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								player.getPackets()
								.sendGameMessage(
										"That "
												+ (player
														.getAttackedBy() instanceof Player ? "player"
																: "npc")
																+ " is already in combat.");
								return;
							}
							if (p2.getAttackedBy() != player
									&& p2.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								if (p2.getAttackedBy() instanceof NPC) {
									p2.setAttackedBy(player); // changes enemy
									// to player,
									// player has
									// priority over
									// npc on single
									// areas
								} else {
									player.getPackets()
									.sendGameMessage(
											"That player is already in combat.");
									return;
								}
							}
						}
						player.getActionManager()
						.setAction(new PlayerCombat(p2));
					}
					break;
				}
				break;
			}
			if (Settings.DEBUG)
				System.out.println("Spell:" + componentId);
		} else if (packetId == INTERFACE_ON_NPC) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int interfaceHash = stream.readInt();
			int npcIndex = stream.readUnsignedShortLE();
			int interfaceSlot = stream.readUnsignedShortLE128();
			@SuppressWarnings("unused")
			int junk2 =stream.readUnsignedShortLE();
			int interfaceId = interfaceHash >> 16;
			int componentId = interfaceHash - (interfaceId << 16);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (componentId == 65535)
				componentId = -1;
			if (componentId != -1
					&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId)
				return;
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isDead() || npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId()))
				return;
			player.stopAll(false);
			if (interfaceId != Inventory.INVENTORY_INTERFACE) {
				if (!npc.getDefinitions().hasAttackOption()) {
					player.sm(
							"You can't attack this npc.");
					return;
				}
			}
			switch (interfaceId) {
			case Inventory.INVENTORY_INTERFACE:
				Item item = player.getInventory().getItem(interfaceSlot);
				if (item == null || !player.getControlerManager().processItemOnNPC(npc, item))
					return;
				InventoryOptionsHandler.handleItemOnNPC(player, npc, item);
				break;
			case 662:
			case 747:
				if (player.getFamiliar() == null)
					return;
				player.resetWalkSteps();
				if ((interfaceId == 747 && componentId == 15)
						|| (interfaceId == 662 && componentId == 65)
						|| (interfaceId == 662 && componentId == 74)
						|| interfaceId == 747 && componentId == 18
						|| interfaceId == 747 && componentId == 24) {
					if ((interfaceId == 662 && componentId == 74 || interfaceId == 747
							&& componentId == 18)) {
						if (player.getFamiliar().getSpecialAttack() != SpecialAttack.ENTITY)
							return;
					}
					if(npc instanceof Familiar) {
						Familiar familiar = (Familiar) npc;
						if (familiar == player.getFamiliar()) {
							player.sm("You can't attack your own familiar.");
							return;
						}
						if (!player.getFamiliar().canAttack(familiar.getOwner())) {
							player.sm("You can only attack players in a player-vs-player area.");
							return;
						}
					}
					if (!player.getFamiliar().canAttack(npc)) {
						player.getPackets()
						.sendGameMessage(
								"You can only use your familiar in a multi-zone area.");
						return;
					} else {
						player.getFamiliar().setSpecial(
								interfaceId == 662 && componentId == 74
								|| interfaceId == 747
								&& componentId == 18);
						player.getFamiliar().setTarget(npc);
					}
				}
				break;
			case 193:
				switch (componentId) {
				case 28:
				case 32:
				case 24:
				case 20:
				case 30:
				case 34:
				case 26:
				case 22:
				case 29:
				case 33:
				case 25:
				case 21:
				case 31:
				case 35:
				case 27:
				case 23:
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(npc
								.getCoordFaceX(npc.getSize()), npc
								.getCoordFaceY(npc.getSize()), npc.getPlane()));
						if (!player.getControlerManager().canAttack(npc))
							return;
						if (npc instanceof Familiar) {
							Familiar familiar = (Familiar) npc;
							if (familiar == player.getFamiliar()) {
								player.sm(
										"You can't attack your own familiar.");
								return;
							}
							if (!familiar.canAttack(player)) {
								player.sm(
										"You can't attack this npc.");
								return;
							}
						} else if (!npc.isForceMultiAttacked()) {
							if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
								if (player.getAttackedBy() != npc
										&& player.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.sm(
											"You are already in combat.");
									return;
								}
								if (npc.getAttackedBy() != player
										&& npc.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.sm(
											"This npc is already in combat.");
									return;
								}
								if (npc.equals(DamageDummy.dummy)) {
									if (!DamageDummy.dummy.getPlayer().equals(player)) {
										player.sm("That isn't your dummy. Dummy.");
									return;
									}
								}
							}
						}
						player.getActionManager().setAction(
								new PlayerCombat(npc));
					}
					break;
				}
			case 192:
				switch (componentId) {
				case 25: // air strike
				case 28: // water strike
				case 30: // earth strike
				case 32: // fire strike
				case 34: // air bolt
				case 39: // water bolt
				case 42: // earth bolt
				case 45: // fire bolt
				case 49: // air blast
				case 52: // water blast
				case 58: // earth blast
				case 63: // fire blast
				case 70: // air wave
				case 73: // water wave
				case 77: // earth wave
				case 80: // fire wave
				case 84: // air surge
				case 87: // water surge
				case 89: // earth surge
				case 66: // Sara Strike
				case 67: // Guthix Claws
				case 68: // Flame of Zammy
				case 93:
				case 91: // fire surge
				case 99: // storm of Armadyl
				case 36: // bind
				case 55: // snare
				case 81: // entangle
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(npc
								.getCoordFaceX(npc.getSize()), npc
								.getCoordFaceY(npc.getSize()), npc.getPlane()));
						if (!player.getControlerManager().canAttack(npc))
							return;
						if (npc instanceof Familiar) {
							Familiar familiar = (Familiar) npc;
							if (familiar == player.getFamiliar()) {
								player.sm(
										"You can't attack your own familiar.");
								return;
							}
							if (!familiar.canAttack(player)) {
								player.sm(
										"You can't attack this npc.");
								return;
							}
						} else if (!npc.isForceMultiAttacked()) {
							if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
								if (player.getAttackedBy() != npc
										&& player.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.sm(
											"You are already in combat.");
									return;
								}
								if (npc.getAttackedBy() != player
										&& npc.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.sm(
											"This npc is already in combat.");
									return;
								}
								if (npc.equals(DamageDummy.dummy)) {
									if (!DamageDummy.dummy.getPlayer().equals(player)) {
										player.sm("That isn't your dummy. Dummy.");
									return;
									}
								}
							}
						}
						player.getActionManager().setAction(
								new PlayerCombat(npc));
					}
					break;
				}
				break;
			}
			if (Settings.DEBUG)
				System.out.println("Spell:" + componentId);
		} else if (packetId == NPC_CLICK1_PACKET)
			NPCHandler.handleOption1(player, stream);
		else if (packetId == NPC_CLICK2_PACKET)
			NPCHandler.handleOption2(player, stream);
		else if (packetId == NPC_CLICK3_PACKET)
			NPCHandler.handleOption3(player, stream);
		else if (packetId == OBJECT_CLICK1_PACKET)
			ObjectHandler.handleOption(player, stream, 1);
		else if (packetId == OBJECT_CLICK2_PACKET)
			ObjectHandler.handleOption(player, stream, 2);
		else if (packetId == OBJECT_CLICK3_PACKET)
			ObjectHandler.handleOption(player, stream, 3);
		else if (packetId == OBJECT_CLICK4_PACKET)
			ObjectHandler.handleOption(player, stream, 4);
		else if (packetId == OBJECT_CLICK5_PACKET)
			ObjectHandler.handleOption(player, stream, 5);
		else if (packetId == ITEM_TAKE_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() > currentTime)
				// || player.getFreezeDelay() >= currentTime)
				return;
			int y = stream.readUnsignedShort();
			int x = stream.readUnsignedShortLE();
			final int id = stream.readUnsignedShort();
			boolean forceRun = stream.read128Byte() == 1;
			final WorldTile tile = new WorldTile(x, y, player.getPlane());
			final int regionId = tile.getRegionId();
			if (!player.getMapRegionsIds().contains(regionId))
				return;
			final FloorItem item = World.getRegion(regionId).getGroundItem(id,
					tile, player);
			if (player.isIronMan()) {
				if (item.getOwner() != null && item.getOwner() != player) {
					player.sm("Ironman accounts can't pick up other people's items.");
					return;
				}
			}
			if (item == null)
				return;
			if (item.getOwner() != null)
				Logger.printDropLog(player, item.getOwner(), item.getLoser(), item);
			player.stopAll(false);
			if(forceRun)
				player.setRun(forceRun);
			player.setCoordsEvent(new CoordsEvent(tile, new Runnable() {
				@Override
				public void run() {
					final FloorItem item = World.getRegion(regionId)
							.getGroundItem(id, tile, player);
					if (item == null)
						return;
					player.setNextFaceWorldTile(tile);
					player.addWalkSteps(tile.getX(), tile.getY(), 1);
					World.removeGroundItem(player, item);
				}
			}, 1, 1));
		}
	}

	public void processPackets(final int packetId, InputStream stream,
			int length) {
		String line1 = "";
		String line2 = "";
		String line3 = "";
		String line4 = "";
		player.setPacketsDecoderPing(Utils.currentTimeMillis());
		if (packetId == PING_PACKET) {
			// kk we ping :)
		} else if (packetId == MOUVE_MOUSE_PACKET) {
			// USELESS PACKET
		} else if (packetId == KEY_TYPED_PACKET) 
			ClientPacketsDecoder.getKeyBinds(stream, player);
		 else if (packetId == RECEIVE_PACKET_COUNT_PACKET) {
			// interface packets
			stream.readInt();
		} else if (packetId == ITEM_ON_ITEM_PACKET) {
			InventoryOptionsHandler.handleItemOnItem(player, stream);
		} else if (packetId == AFK_PACKET) {
			player.getSession().getChannel().close();
		} else if (packetId == CLOSE_INTERFACE_PACKET) {
			if (player.hasStarted() && !player.hasFinished() && !player.isRunning()) { //used for old welcome screen
				player.run();
				return;
			}
			player.stopAll();
		} else if (packetId == MOVE_CAMERA_PACKET) {
			// not using it atm
			stream.readUnsignedShort();
			stream.readUnsignedShort();
		} else if (packetId == IN_OUT_SCREEN_PACKET) {
			// not using this check because not 100% efficient
			@SuppressWarnings("unused")
			boolean inScreen = stream.readByte() == 1;
		} else if (packetId == SCREEN_PACKET) {
			int displayMode = stream.readUnsignedByte();
			player.setScreenWidth(stream.readUnsignedShort());
			player.setScreenHeight(stream.readUnsignedShort());
			@SuppressWarnings("unused")
			boolean switchScreenMode = stream.readUnsignedByte() == 1;
			if (!player.hasStarted() || player.hasFinished()
					|| displayMode == player.getDisplayMode()
					|| !player.getInterfaceManager().containsInterface(742))
				return;
			player.setDisplayMode(displayMode);
			player.getInterfaceManager().removeAll();
			player.getInterfaceManager().sendInterfaces();
			player.getInterfaceManager().sendInterface(742);
		} else if (packetId == CLICK_PACKET) {
			int mouseHash = stream.readShortLE128();
			int mouseButton = mouseHash >> 15;
			int time = mouseHash - (mouseButton << 15); // time
			int positionHash = stream.readIntV1();
			int y = positionHash >> 16; // y;
			int x = positionHash - (y << 16); // x
			@SuppressWarnings("unused")
			boolean clicked;
			// mass click or stupid autoclicker, lets stop lagg
			if (time <= 1 || x < 0 || x > player.getScreenWidth() || y < 0
					|| y > player.getScreenHeight()) {
				// player.getSession().getChannel().close();
				clicked = false;
				return;
			}
			clicked = true;
		} else if (packetId == DIALOGUE_CONTINUE_PACKET) {
			int interfaceHash = stream.readInt();
			int junk = stream.readShort128();
			int interfaceId = interfaceHash >> 16;
			int buttonId = (interfaceHash & 0xFF);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
				// hack, or server error or client error
				// player.getSession().getChannel().close();
				return;
			}
			if (!player.isRunning()
					|| !player.getInterfaceManager().containsInterface(
							interfaceId))
				return;
			if(Settings.DEBUG)
				Logger.log(this, "Dialogue: "+interfaceId+", "+buttonId+", "+junk);
			int componentId = interfaceHash - (interfaceId << 16);
			player.getDialogueManager().continueDialogue(interfaceId,
					componentId);
		} else if (packetId == WORLD_MAP_CLICK) {
			int coordinateHash = stream.readInt();
			int x = coordinateHash >> 14;
			int y = coordinateHash & 0x3fff;
			int plane = coordinateHash >> 28;
            Integer hash  =  (Integer)player.getTemporaryAttributtes().get("worldHash");
			if (hash == null || coordinateHash != hash)
				player.getTemporaryAttributtes().put("worldHash", coordinateHash);
			else {
				player.getTemporaryAttributtes().remove("worldHash");
				player.getHintIconsManager().addHintIcon(x, y, plane, 20, 0, 2, -1, true);
				player.getPackets().sendConfig(1159, coordinateHash);
            }
		} else if (packetId == ACTION_BUTTON1_PACKET
				|| packetId == ACTION_BUTTON2_PACKET
				|| packetId == ACTION_BUTTON4_PACKET
				|| packetId == ACTION_BUTTON5_PACKET
				|| packetId == ACTION_BUTTON6_PACKET
				|| packetId == ACTION_BUTTON7_PACKET
				|| packetId == ACTION_BUTTON8_PACKET
				|| packetId == ACTION_BUTTON3_PACKET
				|| packetId == ACTION_BUTTON9_PACKET
				|| packetId == ACTION_BUTTON10_PACKET) {
			ButtonHandler.handleButtons(player, stream, packetId);
		} else if (packetId == ENTER_NAME_PACKET) {
			
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			if (value.equals(""))
				return;
			if (player.getInterfaceManager().containsInterface(1108)) 
				player.getFriendsIgnores().setChatPrefix(value);
			else if (player.getTemporaryAttributtes().get("yellname") == Boolean.TRUE) {
					player.getTemporaryAttributtes().put("yellname", Boolean.FALSE);
			} else if(player.getTemporaryAttributtes().get("grand_exchange_offer") == Boolean.TRUE) {
				
			} else if (player.getTemporaryAttributtes().get("titlecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX yell color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.settitlecolor(newcolor);
					player.getAppearence().setTitle(53900);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title color has been changed to <col="+player.getTitleColors()+">"+player.getTitleColors()+"</col>.");
				}
				player.getTemporaryAttributtes().put("titlecolor", Boolean.FALSE);
				
			}	else if (player.getTemporaryAttributtes().remove("setclan") != null) {
					ClansManager.createClan(player, value);
			}	else if (player.getTemporaryAttributtes().remove("joinguestclan") != null) {
					ClansManager.connectToClan(player, value, true);
			}	else if (player.getTemporaryAttributtes().remove("banclanplayer") != null) {
					ClansManager.banPlayer(player, value);
			}	else if (player.getTemporaryAttributtes().remove("unbanclanplayer") != null) {
					ClansManager.unbanPlayer(player, value);				
			} else if (player.getTemporaryAttributtes().get("shadenamecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.settitleshadecolor(newcolor);
					player.getAppearence().setTitle(53900);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your shade has been changed to <col="+player.getTitleShadeColor()+">"+player.getTitleShadeColor()+"</col>.");
				}
				player.getTemporaryAttributtes().put("shadenamecolor", Boolean.FALSE);

			} else if (player.getTemporaryAttributtes().get("ctitleshade") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.getClanManager().getClan().setTitleShade(newcolor);;
					player.getDialogueManager().startDialogue("SimpleMessage", "Your shade has been changed to <col="+player.getTitleShadeColor()+">"+player.getTitleShadeColor()+"</col>.");
					SerializableFilesManager.saveClan(player.getClanManager().getClan());
				}
				player.getTemporaryAttributtes().put("ctitleshade", Boolean.FALSE);
								
				
			} else if (player.getTemporaryAttributtes().get("titlenamecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.settitlenamecolor(newcolor);
					player.getAppearence().setTitle(53900);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title color has been changed to <col="+player.getTitleNameColor()+">"+player.getTitleNameColor()+"</col>.");
				}
				player.getTemporaryAttributtes().put("titlenamecolor", Boolean.FALSE);
				
				
			} else if (player.getTemporaryAttributtes().get("ctitlenamecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.getClanManager().getClan().setTitleNameColor(newcolor);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title color has been changed to <col="+player.getTitleNameColor()+">"+player.getTitleNameColor()+"</col>.");
					SerializableFilesManager.saveClan(player.getClanManager().getClan());
				}
				player.getTemporaryAttributtes().put("ctitlenamecolor", Boolean.FALSE);				
				
			} else if (player.getTemporaryAttributtes().get("kickinstanceplayer") == Boolean.TRUE) {		
				Player partner = World.getPlayerByDisplayName(value);
				if (partner == player) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You can't kick yourself.");
				}
				if (partner == null) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Couldn't find player.");
					return;
				}
				if (partner.getLeaderName() == player) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You have kicked " + partner.getDisplayName() + ".");
					partner.sm("You were kicked by the leader.");
					partner.setInstanceKick(true);
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("This player doesn't have an instance.");
				}
				player.getTemporaryAttributtes().put("kickinstanceplayer", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("ctitlecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX yell color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.getClanManager().getClan().setTitleColor(newcolor);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title color has been changed to <col="+player.getYellColor()+">"+player.getYellColor()+"</col>.");
					SerializableFilesManager.saveClan(player.getClanManager().getClan());
				}
				player.getTemporaryAttributtes().put("ctitlecolor", Boolean.FALSE);		
				
			} else if (player.getTemporaryAttributtes().get("yelltitlecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX yell color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.setYellColor(newcolor);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title color has been changed to <col="+player.getYellColor()+">"+player.getYellColor()+"</col>.");
				}
				player.getTemporaryAttributtes().put("yelltitlecolor", Boolean.FALSE);
			} else if (player.getTemporaryAttributtes().get("yellshadenamecolor") == Boolean.TRUE) {
				String newcolor = value;
				if(newcolor.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(newcolor) || newcolor.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested title color can only contain numeric and regular characters.");
				} else {
					player.setYellShade(newcolor);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your shade has been changed to <col="+player.getYellShade()+">"+player.getYellShade()+"</col>.");
				}
				player.getTemporaryAttributtes().put("yellshadenamecolor", Boolean.FALSE);
			} else if (player.getTemporaryAttributtes().get("yellcustomtitle") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 12) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Titles are limted to twelve characters due to spam.");
				} else if(Utils.containsInvalidCharacter(value) || 
						newvalue.toLowerCase().contains("owner") ||
						newvalue.toLowerCase().contains("developer") ||
						newvalue.toLowerCase().contains("support") ||
						newvalue.toLowerCase().contains("executive") ||
						newvalue.toLowerCase().contains("fatal") ||
						newvalue.toLowerCase().contains("something") ||
						newvalue.toLowerCase().contains("copyright") ||
						newvalue.toLowerCase().contains("fuck") ||
						newvalue.toLowerCase().contains("ass") ||
						newvalue.toLowerCase().contains("nigger") ||
						newvalue.toLowerCase().contains("bitch") ||
						newvalue.toLowerCase().contains("slut") ||
						newvalue.toLowerCase().contains("ebola") ||
						newvalue.toLowerCase().contains("cancer") ||
						newvalue.toLowerCase().contains("sex") ||
						newvalue.toLowerCase().contains("pussy") ||
						newvalue.toLowerCase().contains("dick") ||
						newvalue.toLowerCase().contains("fatal") ||
						newvalue.toLowerCase().contains("resort") ||
						newvalue.toLowerCase().contains("vagina") ||
						newvalue.toLowerCase().contains("porn") ||
						newvalue.toLowerCase().contains("server") ||
						newvalue.toLowerCase().contains("autistic") ||
						newvalue.toLowerCase().contains("spastic") ||
						newvalue.toLowerCase().contains("scape") ||
						newvalue.toLowerCase().contains(".com") ||
						newvalue.toLowerCase().contains("dragonkk") ||
						newvalue.toLowerCase().contains("owner") ||
						newvalue.toLowerCase().contains("admin") ||
						newvalue.toLowerCase().contains("coder") ||
						newvalue.toLowerCase().contains("slayer") ||
						newvalue.toLowerCase().contains("master") ||
						newvalue.toLowerCase().contains("savior") ||
						newvalue.toLowerCase().contains("hero") ||
						newvalue.toLowerCase().contains("super") ||
						newvalue.toLowerCase().contains("donator") ||
						newvalue.toLowerCase().contains("eradicator") ||
						newvalue.toLowerCase().contains("max") ||
						newvalue.toLowerCase().contains("trusted") ||
						newvalue.toLowerCase().contains("gambler") ||
						newvalue.toLowerCase().contains("cock") ||
						newvalue.toLowerCase().contains("gamebreaker") ||
						newvalue.toLowerCase().contains("game breaker") ||
						newvalue.toLowerCase().contains("staff") ||
						newvalue.toLowerCase().contains("mod")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You have entered invaild characters or words.");
					player.getAppearence().setTitle(0);
					player.getAppearence().generateAppearenceData();
				} else {
					player.setYellTitle(newvalue);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title has been changed to " + player.getYellTitle() + ".");
				}
				player.getTemporaryAttributtes().put("yellcustomtitle", Boolean.FALSE);
								
								
			} else if (player.getTemporaryAttributtes().get("ctitlename") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 12) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Titles are limted to twelve characters due to spam.");
				} else if(Utils.containsInvalidCharacter(value) || 
						newvalue.toLowerCase().contains("owner") ||
						newvalue.toLowerCase().contains("developer") ||
						newvalue.toLowerCase().contains("support") ||
						newvalue.toLowerCase().contains("executive") ||
						newvalue.toLowerCase().contains("fatal") ||
						newvalue.toLowerCase().contains("something") ||
						newvalue.toLowerCase().contains("copyright") ||
						newvalue.toLowerCase().contains("fuck") ||
						newvalue.toLowerCase().contains("ass") ||
						newvalue.toLowerCase().contains("nigger") ||
						newvalue.toLowerCase().contains("bitch") ||
						newvalue.toLowerCase().contains("slut") ||
						newvalue.toLowerCase().contains("ebola") ||
						newvalue.toLowerCase().contains("cancer") ||
						newvalue.toLowerCase().contains("sex") ||
						newvalue.toLowerCase().contains("pussy") ||
						newvalue.toLowerCase().contains("dick") ||
						newvalue.toLowerCase().contains("fatal") ||
						newvalue.toLowerCase().contains("resort") ||
						newvalue.toLowerCase().contains("vagina") ||
						newvalue.toLowerCase().contains("porn") ||
						newvalue.toLowerCase().contains("server") ||
						newvalue.toLowerCase().contains("autistic") ||
						newvalue.toLowerCase().contains("spastic") ||
						newvalue.toLowerCase().contains("scape") ||
						newvalue.toLowerCase().contains(".com") ||
						newvalue.toLowerCase().contains("dragonkk") ||
						newvalue.toLowerCase().contains("owner") ||
						newvalue.toLowerCase().contains("admin") ||
						newvalue.toLowerCase().contains("coder") ||
						newvalue.toLowerCase().contains("slayer") ||
						newvalue.toLowerCase().contains("master") ||
						newvalue.toLowerCase().contains("savior") ||
						newvalue.toLowerCase().contains("hero") ||
						newvalue.toLowerCase().contains("super") ||
						newvalue.toLowerCase().contains("donator") ||
						newvalue.toLowerCase().contains("eradicator") ||
						newvalue.toLowerCase().contains("max") ||
						newvalue.toLowerCase().contains("trusted") ||
						newvalue.toLowerCase().contains("gambler") ||
						newvalue.toLowerCase().contains("cock") ||
						newvalue.toLowerCase().contains("staff") ||
						newvalue.toLowerCase().contains("mod")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You have entered invaild characters or words.");
					player.getAppearence().setTitle(0);
					player.getAppearence().generateAppearenceData();
				} else {
					player.getClanManager().getClan().setTitle(newvalue);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title has been changed to " + player.getYellTitle() + ".");
					SerializableFilesManager.saveClan(player.getClanManager().getClan());
				}
				player.getTemporaryAttributtes().put("ctitlename", Boolean.FALSE);		
				
			} else if (player.getTemporaryAttributtes().get("instance_name_hm") == Boolean.TRUE) {		
				Player leader = World.getPlayerByDisplayName(value);
				player.setLeaderName(leader);
				if (leader == player) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You can't join your own group.");
				}
				if (leader == null) {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Couldn't find player.");
					return;
				}
				if ((leader.getControlerManager().toString() == null)) {
					player.sm("This player isn't in an instance.");
					return;
				}
				if (!leader.getControlerManager().toString().contains(player.instanceDialogue)) {
					player.setLeaderName(null);
					player.sm("That player isn't in the correct instance you're trying to join.");
					player.getInterfaceManager().closeChatBoxInterface();
					return; 
				}
				if (leader.getInstancePin() > 0 && leader.getInstancePin() != 1 && leader.getInstancePin() != 2) {
					player.setLeaderName(leader);
					player.setInstancePin(0);
					player.getTemporaryAttributtes().put("leader_pin_hm", Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Please enter the leader's pin:" });	
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (leader.getInstancePin() == 1) {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("This player doesn't allow players.");
				} else if (leader.getInstancePin() == 2) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.setLeaderName(leader);
					if (leader.joined.size() > 6) {
						player.setLeaderName(null);
						player.sm("Hard mode trio doesn't allow more than 6 players in an instance.");
						return;
					}
					player.sm("Connecting to player...");
					player.getControlerManager().startControler("SharedInstanceHardMode");
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("This player doesn't have an instance.");
				}
				player.getTemporaryAttributtes().put("instance_name_hm", Boolean.FALSE);				
				
			} else if (player.getTemporaryAttributtes().get("instance_name") == Boolean.TRUE) {		
				Player leader = World.getPlayerByDisplayName(value);
				player.setLeaderName(leader);
				if (leader == player) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("You can't join your own group.");
				}
				if (leader == null) {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Couldn't find player.");
					return;
				}
				if (!leader.getControlerManager().toString().contains(player.instanceDialogue)) {
					player.setLeaderName(null);
					player.sm("That player isn't in the correct instance you're trying to join.");
					player.getInterfaceManager().closeChatBoxInterface();
					return; 
				}
				if (leader.getInstancePin() > 0 && leader.getInstancePin() != 1 && leader.getInstancePin() != 2) {
					player.setLeaderName(leader);
					player.setInstancePin(0);
					player.getTemporaryAttributtes().put("leader_pin", Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Please enter the leader's pin:" });	
					player.getInterfaceManager().closeChatBoxInterface();
				} else if (leader.getInstancePin() == 1) {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("This player doesn't allow players.");
				} else if (leader.getInstancePin() == 2) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.setLeaderName(leader);
					player.sm("Connecting to player...");
					player.getControlerManager().startControler("SharedInstance");
				} else {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("This player doesn't have an instance.");
				}
				player.getTemporaryAttributtes().put("instance_name", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("reasonl1") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 30) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Dialogues are limted to 30 characters due to space.");
				} else {				
					line1 = newvalue;
					for (Player players: World.getPlayers()) {
						players.getPackets().sendIComponentText(3005, 1, line1);
						players.getPackets().sendIComponentText(3005, 2, line2);
						players.getPackets().sendIComponentText(3005, 3, line3);
						players.getPackets().sendIComponentText(3005, 4, line4);
						players.getInterfaceManager().sendOverlay(3005, false);
					}	
					player.getTemporaryAttributtes().put("reasonl2", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the input on line 2:" });
				}
				player.getTemporaryAttributtes().put("reasonl1", Boolean.FALSE);
			
			} else if (player.getTemporaryAttributtes().get("reasonl2") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 30) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Dialogues are limted to 30 characters due to space.");
				} else {
					line2 = newvalue;
					for (Player players: World.getPlayers()) {
						players.getPackets().sendIComponentText(3005, 1, line1);
						players.getPackets().sendIComponentText(3005, 2, line2);
						players.getPackets().sendIComponentText(3005, 3, line3);
						players.getPackets().sendIComponentText(3005, 4, line4);
						players.getInterfaceManager().sendOverlay(3005, false);
					}	
					player.getTemporaryAttributtes().put("reasonl3", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the input on line 3:" });
				}
				player.getTemporaryAttributtes().put("reasonl2", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("reasonl3") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 30) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Dialogues are limted to 30 characters due to space.");
				} else {
					line3 = newvalue;
					for (Player players: World.getPlayers()) {
						players.getPackets().sendIComponentText(3005, 1, line1);
						players.getPackets().sendIComponentText(3005, 2, line2);
						players.getPackets().sendIComponentText(3005, 3, line3);
						players.getPackets().sendIComponentText(3005, 4, line4);
						players.getInterfaceManager().sendOverlay(3005, false);
					}	
					player.getTemporaryAttributtes().put("reasonl4", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the input on line 4:" });
				}
				player.getTemporaryAttributtes().put("reasonl3", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("reasonl4") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 30) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Dialogues are limted to 30 characters due to space.");
				} else {
					line4 = newvalue;
					for (Player players: World.getPlayers()) {
						players.getPackets().sendIComponentText(3005, 1, line1);
						players.getPackets().sendIComponentText(3005, 2, line2);
						players.getPackets().sendIComponentText(3005, 3, line3);
						players.getPackets().sendIComponentText(3005, 4, line4);
						players.getInterfaceManager().sendOverlay(3005, false);						
					}	
				}
				player.getTemporaryAttributtes().put("reasonl4", Boolean.FALSE);
				
				
			} else if (player.getTemporaryAttributtes().get("customtitle") == Boolean.TRUE) {
				String newvalue = value;
				if (newvalue.length() > 12) {
					player.getDialogueManager().startDialogue("SimpleMessage", "Titles are limted to twelve characters due to spam.");
				} else if(Utils.containsInvalidCharacter(value) || 
						newvalue.contains("0") ||
						newvalue.contains("1") ||
						newvalue.contains("2") ||
						newvalue.contains("3") ||
						newvalue.contains("4") ||
						newvalue.contains("5") ||
						newvalue.contains("6") ||
						newvalue.contains("7") ||
						newvalue.contains("8") ||
						newvalue.contains("9") ||
						newvalue.contains("9") ||
						newvalue.toLowerCase().contains("owner") ||
						newvalue.toLowerCase().contains("developer") ||
						newvalue.toLowerCase().contains("support") ||
						newvalue.toLowerCase().contains("executive") ||
						newvalue.toLowerCase().contains("fatal") ||
						newvalue.toLowerCase().contains("something") ||
						newvalue.toLowerCase().contains("copyright") ||
						newvalue.toLowerCase().contains("fuck") ||
						newvalue.toLowerCase().contains("ass") ||
						newvalue.toLowerCase().contains("nigger") ||
						newvalue.toLowerCase().contains("bitch") ||
						newvalue.toLowerCase().contains("slut") ||
						newvalue.toLowerCase().contains("ebola") ||
						newvalue.toLowerCase().contains("cancer") ||
						newvalue.toLowerCase().contains("sex") ||
						newvalue.toLowerCase().contains("pussy") ||
						newvalue.toLowerCase().contains("dick") ||
						newvalue.toLowerCase().contains("fatal") ||
						newvalue.toLowerCase().contains("resort") ||
						newvalue.toLowerCase().contains("vagina") ||
						newvalue.toLowerCase().contains("porn") ||
						newvalue.toLowerCase().contains("server") ||
						newvalue.toLowerCase().contains("autistic") ||
						newvalue.toLowerCase().contains("spastic") ||
						newvalue.toLowerCase().contains("scape") ||
						newvalue.toLowerCase().contains(".com") ||
						newvalue.toLowerCase().contains("dragonkk") ||
						newvalue.toLowerCase().contains("owner") ||
						newvalue.toLowerCase().contains("admin") ||
						newvalue.toLowerCase().contains("coder") ||
						newvalue.toLowerCase().contains("slayer") ||
						newvalue.toLowerCase().contains("master") ||
						newvalue.toLowerCase().contains("savior") ||
						newvalue.toLowerCase().contains("super") ||
						newvalue.toLowerCase().contains("donator") ||
						newvalue.toLowerCase().contains("eradicator") ||
						newvalue.toLowerCase().contains("max") ||
						newvalue.toLowerCase().contains("trusted") ||
						newvalue.toLowerCase().contains("gambler") ||
						newvalue.toLowerCase().contains("cock") ||
						newvalue.toLowerCase().contains("staff") ||
						newvalue.toLowerCase().contains("mod") ||
						newvalue.contains("10")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You have entered invaild characters or word.");
					player.getAppearence().setTitle(0);
					player.getAppearence().generateAppearenceData();
				} else {
					if (newvalue.equals("empty"))
						newvalue = "";
					player.setTitle(newvalue);
					player.getAppearence().setTitle(53900);
					player.getAppearence().generateAppearenceData();
					player.getDialogueManager().startDialogue("SimpleMessage", "Your title has been changed to " + player.getTitle() + ".");
				}
				player.getTemporaryAttributtes().put("customtitle", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("view_name") == Boolean.TRUE) {
				
				player.getTemporaryAttributtes().remove("view_name");
				Player other = World.getPlayerByDisplayName(value);
				if (other == null) {
					player.sm("Couldn't find player.");
					return;
				}
				ClanWars clan = other.getCurrentFriendChat() != null ? other.getCurrentFriendChat().getClanWars() : null;
				if (clan == null) {
					player.sm("This player's clan is not in war.");
					return;
				}
				if (clan.getSecondTeam().getOwnerDisplayName() != other.getCurrentFriendChat().getOwnerDisplayName()) {
					player.getTemporaryAttributtes().put("view_prefix", 1);
				}
				player.getTemporaryAttributtes().put("view_clan", clan);
				ClanWars.enter(player);		
			} else if (player.getTemporaryAttributtes().get("setpresetname0") == Boolean.TRUE) {
				player.getBankPreset().setPresetName(value);
				player.sm("Preset 1 has been renamed to " + value + ".");
				player.sendBankPreset();
				player.getTemporaryAttributtes().put("setpresetname0", Boolean.FALSE);	
			} else if (player.getTemporaryAttributtes().get("setpresetname1") == Boolean.TRUE) {
				player.getBankPreset2().setPresetName(value);
				player.sm("Preset 2 has been renamed to " + value + ".");
				player.sendBankPreset();
				player.getTemporaryAttributtes().put("setpresetname1", Boolean.FALSE);	
			} else if (player.getTemporaryAttributtes().get("setpresetname2") == Boolean.TRUE) {
				player.getBankPreset3().setPresetName(value);
				player.sm("Preset 3 has been renamed to " + value + ".");
				player.sendBankPreset();
				player.getTemporaryAttributtes().put("setpresetname2", Boolean.FALSE);	
			} else if (player.getTemporaryAttributtes().get("setpresetname3") == Boolean.TRUE) {
				player.getBankPreset4().setPresetName(value);
				player.sm("Preset 4 has been renamed to " + value + ".");
				player.sendBankPreset();
				player.getTemporaryAttributtes().put("setpresetname3", Boolean.FALSE);	
			} else if (player.getTemporaryAttributtes().get("setdisplay") == Boolean.TRUE) {
				if(Utils.invalidAccountName(Utils.formatPlayerNameForProtocol(value))) {
					player.sm("Invalid name!");
					player.getTemporaryAttributtes().put("customTitle", Boolean.FALSE);
					return;
				}
				if(!DisplayNames.setDisplayName(player, value)) {
					player.sm("Name already in use!");
					player.getTemporaryAttributtes().put("customTitle", Boolean.FALSE);
					return;
				}
				player.sm("Changed display name!");
				player.getTemporaryAttributtes().put("customTitle", Boolean.FALSE);
			} 
		} else if (packetId == ENTER_STRING_PACKET) {
			
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			if (value.equals(""))
				return;
			
			 if (player.getTemporaryAttributtes().remove("entering_note") == Boolean.TRUE)
					player.getNotes().add(value);
			 else if (player.getTemporaryAttributtes().remove("editing_note") == Boolean.TRUE)
					player.getNotes().edit(value);
			
		} else if (packetId == ENTER_INTEGER_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			int value = stream.readInt();
			if ((player.getInterfaceManager().containsInterface(762) && player
					.getInterfaceManager().containsInterface(763))
					|| player.getInterfaceManager().containsInterface(11)) {
				if (value < 0)
					return;
				Integer bank_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("bank_item_X_Slot");
				if (bank_item_X_Slot == null)
					return;
				player.getBank().setLastX(value);
				player.getBank().refreshLastX();
				if (player.getTemporaryAttributtes().remove("bank_isWithdraw") != null)
					player.getBank().withdrawItem(bank_item_X_Slot, value);
				else
					player.getBank()
							.depositItem(
									bank_item_X_Slot,
									value,
									player.getInterfaceManager()
											.containsInterface(11) ? false
											: true);
			} else if (player.getInterfaceManager().containsInterface(206)
					&& player.getInterfaceManager().containsInterface(207)) {
				if (value < 0)
					return;
				Integer pc_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("pc_item_X_Slot");
				if (pc_item_X_Slot == null)
					return;
				if (player.getTemporaryAttributtes().remove("pc_isRemove") != null)
					player.getPriceCheckManager().removeItem(pc_item_X_Slot,
							value);
				else
					player.getPriceCheckManager()
					.addItem(pc_item_X_Slot, value);
			} else if (player.getTemporaryAttributtes().remove("eradicatedseal_remove") != null) {
				int amnt = value;

				if (amnt < 1)
					return;
				
				if (player.getCurrencyPouch().getEradicatedSeals() == 0) {
					player.sendMessage("You have no Eradicated seals in your pouch!");
					return;
				}
				
				int inInv =  player.getInventory().getItems().getNumberOf(12852);
				
				if (!player.getInventory().hasFreeSlots() && inInv == 0) {
					player.sm("Not enough space in your inventory.");
					return;
				}
				
				if (amnt + inInv < 0) {
					amnt = Integer.MAX_VALUE - inInv;
				}
				
				if (amnt > player.getCurrencyPouch().getEradicatedSeals())
					amnt = player.getCurrencyPouch().getEradicatedSeals();
				
				if (amnt < 1) {
					player.sendMessage("You don't have enough inventory space.");
					return;
				}
				
				player.getCurrencyPouch().setEradicatedSeals(player.getCurrencyPouch().getEradicatedSeals() - amnt);
				player.getCurrencyPouch().openInterface();
				
				player.getInventory().addItem(12852, amnt);			
			} else if (player.getTemporaryAttributtes().remove("votetick_remove") != null) {
				int amnt = value;

				if (amnt < 1)
					return;
				
				if (player.getCurrencyPouch().getVoteTickets() == 0) {
					player.sendMessage("You have no Vote tickets in your pouch!");
					return;
				}
				
				int inInv =  player.getInventory().getItems().getNumberOf(1464);
				
				if (!player.getInventory().hasFreeSlots() && inInv == 0) {
					player.sm("Not enough space in your inventory.");
					return;
				}
				
				if (amnt + inInv < 0) {
					amnt = Integer.MAX_VALUE - inInv;
				}
				
				if (amnt > player.getCurrencyPouch().getVoteTickets())
					amnt = player.getCurrencyPouch().getVoteTickets();
				
				if (amnt < 1) {
					player.sendMessage("You don't have enough inventory space.");
					return;
				}
				
				player.getCurrencyPouch().setVoteTickets(player.getCurrencyPouch().getVoteTickets() - amnt);
				player.getCurrencyPouch().openInterface();
				
				player.getInventory().addItem(1464, amnt);		
				
			} else if (player.getTemporaryAttributtes().remove("invasiontok_remove") != null) {
				int amnt = value;

				if (amnt < 1)
					return;
				
				if (player.getCurrencyPouch().getInvasionTokens() == 0) {
					player.sendMessage("You have no Invasion tokens in your pouch!");
					return;
				}
				
				int inInv =  player.getInventory().getItems().getNumberOf(19819);
				
				if (!player.getInventory().hasFreeSlots() && inInv == 0) {
					player.sm("Not enough space in your inventory.");
					return;
				}
				
				if (amnt + inInv < 0) {
					amnt = Integer.MAX_VALUE - inInv;
				}
				
				if (amnt > player.getCurrencyPouch().getInvasionTokens())
					amnt = player.getCurrencyPouch().getInvasionTokens();
				
				if (amnt < 1) {
					player.sendMessage("You don't have enough inventory space.");
					return;
				}
				
				player.getCurrencyPouch().setInvasionTokens(player.getCurrencyPouch().getInvasionTokens() - amnt);
				player.getCurrencyPouch().openInterface();
				
				player.getInventory().addItem(19819, amnt);		
			} else if (player.getTemporaryAttributtes().remove("hundredm_remove") != null) {
				int amnt = value;

				if (amnt < 1)
					return;
				
				if (player.getCurrencyPouch().get100MTicket() == 0) {
					player.sendMessage("You have no 100M Tickets in your pouch!");
					return;
				}
				
				int inInv =  player.getInventory().getItems().getNumberOf(2996);
				
				if (!player.getInventory().hasFreeSlots() && inInv == 0) {
					player.sm("Not enough space in your inventory.");
					return;
				}
				
				if (amnt + inInv < 0) {
					amnt = Integer.MAX_VALUE - inInv;
				}
				
				if (amnt > player.getCurrencyPouch().get100MTicket())
					amnt = player.getCurrencyPouch().get100MTicket();
				
				if (amnt < 1) {
					player.sendMessage("You don't have enough inventory space.");
					return;
				}
				
				player.getCurrencyPouch().set100MTicket(player.getCurrencyPouch().get100MTicket() - amnt);
				player.getCurrencyPouch().openInterface();
				
				player.getInventory().addItem(2996, amnt);	
			
			} else if (player.getTemporaryAttributtes().remove("money_remove") != null) {
				int amnt = value;

				if (amnt < 1)
					return;
				
				
				if (player.getPouch().getAmount() == 0) {
					player.sendMessage("You have no money in your pouch!");
					return;
				}
				
				int inInv =  player.getInventory().getItems().getNumberOf(995);
				
				if (!player.getInventory().hasFreeSlots() && inInv == 0) {
					player.sm("Not enough space in your inventory.");
					return;
				}
				
				if (amnt + inInv < 0) {
					amnt = Integer.MAX_VALUE - inInv;
				}
				
				if (amnt > player.getPouch().getAmount())
					amnt = player.getPouch().getAmount();
				
				if (amnt < 1) {
					player.sendMessage("You don't have enough inventory space for any more gold. You can do <col=FF0000>::100MTicket</col> to swap coins for a voucher.");
					return;
				}
				
				player.getPouch().setAmount(player.getPouch().getAmount() - amnt);
				player.getPouch().sendScript(false, amnt);
				player.getPouch().refresh();
				
				player.getInventory().addItem(995, amnt);	
		
			} else if (player.getTemporaryAttributtes().get("leader_pin") == Boolean.TRUE) {		
				Player leader = player.getLeaderName();
				if (value == leader.getInstancePin()) {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Pin accepted");
					player.getControlerManager().startControler("SharedInstance");
				} else {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Incorrect pin.");
					player.setTimer(0);
				}
				player.getTemporaryAttributtes().put("leader_pin", Boolean.FALSE);				
						
				
			} else if (player.getTemporaryAttributtes().get("leader_pin_hm") == Boolean.TRUE) {		
				Player leader = player.getLeaderName();
				if (value == leader.getInstancePin()) {
					player.getInterfaceManager().closeChatBoxInterface();
					if (leader.joined.size() > 6) {
						player.setLeaderName(null);
						player.sm("Hard mode trio doesn't allow more than 6 players in an instance.");
						return;
					}
					player.sm("Pin accepted");
					player.getControlerManager().startControler("SharedInstanceHardMode");
				} else {
					player.setLeaderName(null);
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Incorrect pin.");
					player.setTimer(0);
				}
				player.getTemporaryAttributtes().put("leader_pin_hm", Boolean.FALSE);							
				
			} else if (player.getTemporaryAttributtes().get("hours") == Boolean.TRUE) {		
				if (value >= 1 && value <= 24) {
					player.setLendhours(value);
					player.getTemporaryAttributtes().put("lendmoney", Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Please enter the amount of 100M Tickets you're charging." });
				} else {
					player.sm("Invalid.");
				}
				player.getTemporaryAttributtes().put("hours", Boolean.FALSE);						
	
			} else if (player.getTemporaryAttributtes().get("shopamount") == Boolean.TRUE) {		
				if (value >= 1 && value <= 1000000) {
					Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
					if (shop == null)
						player.getTemporaryAttributtes().put("shopamount", Boolean.FALSE);
				    shop.sendInfo(player, player.shopSlotId, true, value);
				} else {
					player.sm("Invalid amount entered. The maximum amount is 1 million.");
				}
				player.getTemporaryAttributtes().put("shopamount", Boolean.FALSE);							
				
			} else if (player.getTemporaryAttributtes().get("lendmoney") == Boolean.TRUE) {		
				if (value >= 1 && value <= 450) {
					if (player.getRanklend() == 3) {
						if (value / player.getLendhours() < 5) {
							player.getDialogueManager().startDialogue("SimpleMessage", "You must charge at least 500M an hour for a Super donator rank.");
						return;
						}
					}
					if (player.getRanklend() == 4) {
						if (value / player.getLendhours() < 8) {
							player.getDialogueManager().startDialogue("SimpleMessage", "You must charge at least 800M an hour for an Eradicator rank.");
						return;
						}
					}
					player.setTicketamount(value);
					player.getDialogueManager().startDialogue("LendaRankb");	
				} else {
					player.getInterfaceManager().closeChatBoxInterface();
					player.sm("Invalid amount entered.");
				}
				player.getTemporaryAttributtes().put("lendmoney", Boolean.FALSE);	
				
			} else if (player.getTemporaryAttributtes().get("securitypin") == Boolean.TRUE) {
			if (player.getPinAttempts() > 10) {
				player.sm("You've run out of pin attempts. For further help, contact a staff member.");
				return;
				}
			if (value != player.getSecurityPin()) {
				if (player.getWrong() == 5) {
					player.setWrong(0);
					player.logout(false);
				}
				player.getDialogueManager().startDialogue("SimpleMessage", "Incorrect Pin!");
				player.setPinAttempts(player.getPinAttempts() + 1);
				player.setWrong(player.getWrong() + 1);
				player.getTemporaryAttributtes().put("securitypin", Boolean.TRUE);
				player.getPackets().sendRunScript(108,
						new Object[] { "Please enter your security pin (" + player.getPinAttempts() + " attempts remaining)" });					
				player.lock(2147000000);
			} else {
				player.setHacker(1);
				player.setWrong(0);
				player.unlock();
				player.getDialogueManager().startDialogue("SimpleMessage", "Pin accepted. You may continue.");
				player.getTemporaryAttributtes().put("securitypin", Boolean.FALSE);	
			}
			
			} else if (player.getTemporaryAttributtes().get("x_amount_shop") != null) {
				Shop shop = (Shop) player.getTemporaryAttributtes().get(
						"Shop");
				Integer slotId = (Integer) player
						.getTemporaryAttributtes().remove("x_amount_shop");
				if (slotId == null)
					return;
				shop.sell(player, slotId, value, true);
				player.getTemporaryAttributtes().put("x_amount_shop", Boolean.FALSE);	
				
			} else if (player.getTemporaryAttributtes().get("changesecuritypin") == Boolean.TRUE) {
				if (value == player.getSecurityPin()) {
					player.getTemporaryAttributtes().put("createsecuritypin", Boolean.TRUE);
					player.getPackets().sendRunScript(108,
							new Object[] { "Please enter your new pin number:" });	
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "That isn't your current pin number. If you need assistance, ask an executive.");					
				}
				player.getTemporaryAttributtes().put("changesecuritypin", Boolean.FALSE);
					
			} else if (player.getTemporaryAttributtes().get("createsecuritypin") == Boolean.TRUE) {
				if (value > 999 && value < 10000) {
				player.setSecurityPin(value);
				player.getDialogueManager().startDialogue("SimpleMessage", "Pin created. Your pin is "+ player.getSecurityPin());		
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "Invalid input, please make sure your new pin is four digits.");					
				}
				player.getTemporaryAttributtes().put("createsecuritypin", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("createinstancepin") == Boolean.TRUE) {
				if (value != 0 && value < 2147483647) {
				player.setInstancePin(value);
				player.sm("Pin created. Your pin is "+ player.getInstancePin()+".");		
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "Invalid input.");					
				}
				player.getInterfaceManager().closeChatBoxInterface();
				player.getTemporaryAttributtes().put("createinstancepin", Boolean.FALSE);
				
			
			} else if (player.getTemporaryAttributtes().get("startaninstancepin") == Boolean.TRUE) {
				if (value != 0 && value < 2147483647) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.setInstancePin(value);
				player.sm("Pin created. Your pin is "+ player.getInstancePin());	
				if (player.chargeMoney(200000000)) {
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler(player.getInstanceControler());
				} else if (player.getCurrencyPouch().spend100mTicket(2)) {
					 
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler(player.getInstanceControler());
				} else {
					player.sm("Sorry, but you don't have enough money.");
				}
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "Invalid input.");					
				}
				player.getTemporaryAttributtes().put("startaninstancepin", Boolean.FALSE);			
			
			} else if (player.getTemporaryAttributtes().get("startinstancepin") == Boolean.TRUE) {
				if (value != 0 && value < 2147483647) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.setInstancePin(value);
				player.sm("Pin created. Your pin is "+ player.getInstancePin());	
				if (player.chargeMoney(300000000)) {
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler(player.getInstanceControler());
				} else if (player.getCurrencyPouch().spend100mTicket(3)) {
					player.sm("3 100M tickets were deducted from your inventory.");
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler(player.getInstanceControler());
				} else {
					player.sm("Sorry, but you don't have enough money.");
				}
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "Invalid input.");					
				}
				player.getTemporaryAttributtes().put("startinstancepin", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().get("startinstancepinhard") == Boolean.TRUE) {
				if (value != 0 && value < 2147483647) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.setInstancePin(value);
				player.sm("Pin created. Your pin is "+ player.getInstancePin());	
				Item item = player.getInventory().containsAtleastItem(new int[]{29947, 27749, 27750, 27751, 27752, 27753});
				if (item != null) {
					player.getInventory().deleteItem(item);
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getControlerManager().startControler(player.getInstanceControler());
				} else {
					player.sm("Sorry, but you don't have a Trio key.");
				}			
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "Invalid input.");					
				}
				player.getTemporaryAttributtes().put("startinstancepinhard", Boolean.FALSE);
		
			} else if (player.getTemporaryAttributtes().get("startfreeinstancepin") == Boolean.TRUE) {
				if (value != 0 && value < 2147483647) {
				player.setInstancePin(value);
				player.sm("Pin created. Your pin is "+ player.getInstancePin());	
					player.sm("Enjoy your instance, the NPCs will stop spawning in an hour.");
					player.getInterfaceManager().closeChatBoxInterface();
					player.getControlerManager().startControler(player.getInstanceControler());
				} else { 
				player.getDialogueManager().startDialogue("SimpleMessage", "Invalid input.");					
				}
				player.getTemporaryAttributtes().put("startfreeinstancepin", Boolean.FALSE);
				
			} else if (player.getTemporaryAttributtes().remove("boneamountmoney") != null) {
				int amnt = value;
				int moneymade = value * 15000000;
				int maxcashlimit = player.getInventory().getItems().getNumberOf(995);
				if (maxcashlimit + moneymade > 2147483647 || maxcashlimit + moneymade < 0) {
				player.sm("<col=CC0E0E><img=5>[Alert]: Not enough space in your inventory! You can do ::100MTicket to free up the amount of coins you have.");
				return;
				}				
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}	
				if (amnt > 143) {
					player.sm("Invalid amount! The maximum amount of bones you can turn in for money is 143!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.getInventory().addItem(995, player.getDeposittedBones() * 15000000);
						player.out(player.getDeposittedBones() +" Eradicated bones have been removed for "+player.getDeposittedBones() * 15000000+" coins.");	
						player.setDeposittedBones(0);					
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated bones have been removed for "+moneymade+" coins.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);			
						player.getInventory().addItem(995, moneymade);
						return;
					}												
			} else if (player.getTemporaryAttributtes().remove("boneamountattack") != null) {
				int amnt = value;			
				int attackxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;
				int xprates = attackxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = attackxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Attack XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.ATTACK, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Attack XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.ATTACK, attackxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountstrength") != null) {
				int amnt = value;			
				int strengthxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;				
				int xprates = strengthxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = strengthxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Strength XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.STRENGTH, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Strength XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.STRENGTH, strengthxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountdefence") != null) {
				int amnt = value;			
				int defencexpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = defencexpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = defencexpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Defence XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.DEFENCE, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Defence XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.DEFENCE, defencexpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountranged") != null) {
				int amnt = value;			
				int rangedxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = rangedxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = rangedxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Ranged XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.RANGE, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Ranged XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.RANGE, rangedxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountprayer") != null) {
				int amnt = value;			
				int prayerxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = prayerxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = prayerxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Prayer XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.PRAYER, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Prayer XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.PRAYER, prayerxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountmagic") != null) {
				int amnt = value;			
				int magicxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = magicxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = magicxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Magic XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.MAGIC, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Magic XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.MAGIC, magicxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountrunecrafting") != null) {
				int amnt = value;			
				int runecraftingxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = runecraftingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = runecraftingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Runecrafting XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.RUNECRAFTING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Runecrafting XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.RUNECRAFTING, runecraftingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountconstruction") != null) {
				int amnt = value;			
				int constructionxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = constructionxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = constructionxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Construction XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.CONSTRUCTION, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Construction XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.CONSTRUCTION, constructionxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountdungeoneering") != null) {
				int amnt = value;			
				int dungeoneeringxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = dungeoneeringxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = dungeoneeringxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Dungeoneering XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.DUNGEONEERING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Dungeoneering XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.DUNGEONEERING, dungeoneeringxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountconstitution") != null) {
				int amnt = value;			
				int constitutionxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = constitutionxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = constitutionxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Constitution XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.HITPOINTS, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Constitution XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.HITPOINTS, constitutionxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountagility") != null) {
				int amnt = value;			
				int agilityxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = agilityxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = agilityxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Agility XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.AGILITY, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Agility XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.AGILITY, agilityxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountherblore") != null) {
				int amnt = value;			
				int herblorexpgained = amnt * 400;
				int xprates = herblorexpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = herblorexpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Herblore XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.HERBLORE, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Herblore XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.HERBLORE, herblorexpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountthieving") != null) {
				int amnt = value;			
				int thievingxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = thievingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = thievingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Thieving XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.THIEVING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Thieving XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.THIEVING, thievingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountcrafting") != null) {
				int amnt = value;			
				int craftingxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = craftingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = craftingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Crafting XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.CRAFTING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Crafting XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.CRAFTING, craftingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountfletching") != null) {
				int amnt = value;			
				int fletchingxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = fletchingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = fletchingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Fletching XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.FLETCHING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Fletching XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FLETCHING, fletchingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountslayer") != null) {
				int amnt = value;			
				int slayerxpgained = amnt * 400;
				int xprates = slayerxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = slayerxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Slayer XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.SLAYER, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Slayer XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.SLAYER, slayerxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamounthunter") != null) {
				int amnt = value;			
				int hunterxpgained = amnt * 400;
				int xprates = hunterxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = hunterxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Hunter XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.HUNTER, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Hunter XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.HUNTER, hunterxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountmining") != null) {
				int amnt = value;			
				int miningxpgained = amnt * 400;
				int xprates = miningxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = miningxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Mining XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.MINING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Mining XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.MINING, miningxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountsmithing") != null) {
				int amnt = value;			
				int smithingxpgained = amnt * 400;
				int xprates = smithingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = smithingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Smithing XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.SMITHING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Smithing XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.SMITHING, smithingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountfishing") != null) {
				int amnt = value;			
				int fishingxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = fishingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = fishingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Fishing XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.FISHING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Fishing XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FISHING, fishingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountcooking") != null) {
				int amnt = value;			
				int cookingxpgained = amnt * 400;
				int xprates = cookingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = cookingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Cooking XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.COOKING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Cooking XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.COOKING, cookingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountfiremaking") != null) {
				int amnt = value;			
				int firemakingxpgained = amnt * 400;
				int xprates = firemakingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = firemakingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Firemaking XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.FIREMAKING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Firemaking XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FIREMAKING, firemakingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountwoodcutting") != null) {
				int amnt = value;			
				int woodcuttingxpgained = amnt * 400;
				int xprates = woodcuttingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = woodcuttingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Woodcutting XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.WOODCUTTING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Woodcutting XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.WOODCUTTING, woodcuttingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountfarming") != null) {
				int amnt = value;			
				int farmingxpgained = amnt * 400;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				int xprates = farmingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = farmingxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Farming XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.FARMING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Farmings XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FARMING, farmingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("boneamountsummoning") != null) {
				int amnt = value;			
				int summoningxpgained = amnt * 400;
				int xprates = summoningxpgained * 120 * 3;
				int attackxpgainedgreater = player.getDeposittedBones() * 400;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = summoningxpgained * 120 * 3 * 2;
				}
				if (player.getDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getDeposittedBones()) {
						player.out(player.getDeposittedBones() +" Eradicated Bones have been removed for "+xprates+" Summoning XP.");
						player.setDeposittedBones(0);
						player.getSkills().addXp(Skills.SUMMONING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getDeposittedBones())  {
						player.out(amnt +" Eradicated Bones have been removed for "+xprates+" Summoning XP.");
						player.setDeposittedBones(player.getDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.SUMMONING, summoningxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtmoney") != null) {
				int amnt = value;
				int moneymade = value * 5000000;
				int maxcashlimit = player.getInventory().getItems().getNumberOf(995);
				if (maxcashlimit + moneymade > 2147483647 || maxcashlimit + moneymade < 0) {
				player.sm("<col=CC0E0E><img=5>[Alert]: Not enough space in your inventory! You can do ::100MTicket to free up the amount of coins you have.");
				return;
				}				
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}	
				if (amnt > 429) {
					player.sm("Invalid amount! The maximum amount of bones you can turn in for money is 429!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.getInventory().addItem(995, player.getSuperDeposittedBones() * 5000000);
						player.out(player.getSuperDeposittedBones() +" Super bones have been removed for "+player.getSuperDeposittedBones() * 5000000+" coins.");	
						player.setSuperDeposittedBones(0);					
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super bones have been removed for "+moneymade+" coins.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);			
						player.getInventory().addItem(995, moneymade);
						return;
					}												
			} else if (player.getTemporaryAttributtes().remove("superboneamtattack") != null) {
				int amnt = value;			
				int attackxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;
				int xprates = attackxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = attackxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Attack XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.ATTACK, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Attack XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.ATTACK, attackxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtstrength") != null) {
				int amnt = value;			
				int strengthxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;				
				int xprates = strengthxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = strengthxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Strength XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.STRENGTH, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Strength XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.STRENGTH, strengthxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtdefence") != null) {
				int amnt = value;			
				int defencexpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = defencexpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = defencexpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Defence XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.DEFENCE, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Defence XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.DEFENCE, defencexpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtranged") != null) {
				int amnt = value;			
				int rangedxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = rangedxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = rangedxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Ranged XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.RANGE, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Ranged XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.RANGE, rangedxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtprayer") != null) {
				int amnt = value;			
				int prayerxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = prayerxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = prayerxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Prayer XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.PRAYER, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Prayer XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.PRAYER, prayerxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtmagic") != null) {
				int amnt = value;			
				int magicxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = magicxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = magicxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Magic XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.MAGIC, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Magic XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.MAGIC, magicxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtrunecrafting") != null) {
				int amnt = value;			
				int runecraftingxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = runecraftingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = runecraftingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Runecrafting XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.RUNECRAFTING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Runecrafting XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.RUNECRAFTING, runecraftingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtconstruction") != null) {
				int amnt = value;			
				int constructionxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = constructionxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = constructionxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Construction XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.CONSTRUCTION, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Construction XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.CONSTRUCTION, constructionxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtdungeoneering") != null) {
				int amnt = value;			
				int dungeoneeringxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = dungeoneeringxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = dungeoneeringxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Dungeoneering XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.DUNGEONEERING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Dungeoneering XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.DUNGEONEERING, dungeoneeringxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtconstitution") != null) {
				int amnt = value;			
				int constitutionxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = constitutionxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = constitutionxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Constitution XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.HITPOINTS, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Constitution XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.HITPOINTS, constitutionxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtagility") != null) {
				int amnt = value;			
				int agilityxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = agilityxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = agilityxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Agility XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.AGILITY, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Agility XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.AGILITY, agilityxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtherblore") != null) {
				int amnt = value;			
				int herblorexpgained = amnt * 133;
				int xprates = herblorexpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = herblorexpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Herblore XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.HERBLORE, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Herblore XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.HERBLORE, herblorexpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtthieving") != null) {
				int amnt = value;			
				int thievingxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = thievingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = thievingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Thieving XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.THIEVING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Thieving XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.THIEVING, thievingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtcrafting") != null) {
				int amnt = value;			
				int craftingxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = craftingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = craftingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Crafting XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.CRAFTING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Crafting XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.CRAFTING, craftingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtfletching") != null) {
				int amnt = value;			
				int fletchingxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = fletchingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = fletchingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Fletching XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.FLETCHING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Fletching XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FLETCHING, fletchingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtslayer") != null) {
				int amnt = value;			
				int slayerxpgained = amnt * 133;
				int xprates = slayerxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = slayerxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Slayer XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.SLAYER, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Slayer XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.SLAYER, slayerxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamthunter") != null) {
				int amnt = value;			
				int hunterxpgained = amnt * 133;
				int xprates = hunterxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = hunterxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Hunter XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.HUNTER, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Hunter XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.HUNTER, hunterxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtmining") != null) {
				int amnt = value;			
				int miningxpgained = amnt * 133;
				int xprates = miningxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = miningxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Mining XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.MINING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Mining XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.MINING, miningxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtsmithing") != null) {
				int amnt = value;			
				int smithingxpgained = amnt * 133;
				int xprates = smithingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = smithingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Smithing XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.SMITHING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Smithing XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.SMITHING, smithingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtfishing") != null) {
				int amnt = value;			
				int fishingxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = fishingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = fishingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Fishing XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.FISHING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Fishing XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FISHING, fishingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtcooking") != null) {
				int amnt = value;			
				int cookingxpgained = amnt * 133;
				int xprates = cookingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = cookingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Cooking XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.COOKING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Cooking XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.COOKING, cookingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtfiremaking") != null) {
				int amnt = value;			
				int firemakingxpgained = amnt * 133;
				int xprates = firemakingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = firemakingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Firemaking XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.FIREMAKING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Firemaking XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FIREMAKING, firemakingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtwoodcutting") != null) {
				int amnt = value;			
				int woodcuttingxpgained = amnt * 133;
				int xprates = woodcuttingxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = woodcuttingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Woodcutting XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.WOODCUTTING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Woodcutting XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.WOODCUTTING, woodcuttingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtfarming") != null) {
				int amnt = value;			
				int farmingxpgained = amnt * 133;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				int xprates = farmingxpgained * 120 * 3;
				if (DoubleXpManager.isWeekend()) {
					 xprates = farmingxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Farming XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.FARMING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Farmings XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.FARMING, farmingxpgained);						
					}
			} else if (player.getTemporaryAttributtes().remove("superboneamtsummoning") != null) {
				int amnt = value;			
				int summoningxpgained = amnt * 133;
				int xprates = summoningxpgained * 120 * 3;
				int attackxpgainedgreater = player.getSuperDeposittedBones() * 133;		
				if (DoubleXpManager.isWeekend()) {
					 xprates = summoningxpgained * 120 * 3 * 2;
				}
				if (player.getSuperDeposittedBones() == 0) {
					player.out("You have no bones depositted in the machine!");
					return;
				}
					if (amnt > player.getSuperDeposittedBones()) {
						player.out(player.getSuperDeposittedBones() +" Super Bones have been removed for "+xprates+" Summoning XP.");
						player.setSuperDeposittedBones(0);
						player.getSkills().addXp(Skills.SUMMONING, attackxpgainedgreater);
						return;
					}
					if (amnt <= player.getSuperDeposittedBones())  {
						player.out(amnt +" Super Bones have been removed for "+xprates+" Summoning XP.");
						player.setSuperDeposittedBones(player.getSuperDeposittedBones() - amnt);
						player.getSkills().addXp(Skills.SUMMONING, summoningxpgained);						
					}								
			} else if (player.getTemporaryAttributtes().get("bones") != null) {
			} else if (player.getInterfaceManager().containsInterface(671)
					&& player.getInterfaceManager().containsInterface(665)) {
				if (player.getFamiliar() == null
						|| player.getFamiliar().getBob() == null)
					return;
				if (value < 0)
					return;
				Integer bob_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("bob_item_X_Slot");
				if (bob_item_X_Slot == null)
					return;
				if (player.getTemporaryAttributtes().remove("bob_isRemove") != null)
					player.getFamiliar().getBob()
					.removeItem(bob_item_X_Slot, value);
				else
					player.getFamiliar().getBob()
					.addItem(bob_item_X_Slot, value);
			} else if (player.getInterfaceManager().containsInterface(335)
					&& player.getInterfaceManager().containsInterface(336)) {
				if (value <= 0)
					return;
				Integer trade_item_X_Slot = (Integer) player
						.getTemporaryAttributtes().remove("trade_item_X_Slot");
			    if (player.getTemporaryAttributtes().get("trade_moneypouch") == Boolean.TRUE) {		
					player.getTrade().addCoins(value, 995);
					player.getTemporaryAttributtes().put("trade_moneypouch", Boolean.FALSE);	
			    }
			    if (player.getTemporaryAttributtes().get("trade_100m") == Boolean.TRUE) {		
					player.getTrade().addCoins(value, 2996);
					player.getTemporaryAttributtes().put("trade_100m", Boolean.FALSE);	
			    }
			    if (player.getTemporaryAttributtes().get("trade_invasion") == Boolean.TRUE) {	
					player.getTrade().addCoins(value, 19819);
					player.getTemporaryAttributtes().put("trade_invasion", Boolean.FALSE);	
			    }
			    if (player.getTemporaryAttributtes().get("trade_vote") == Boolean.TRUE) {		
					player.getTrade().addCoins(value, 1464);
					player.getTemporaryAttributtes().put("trade_vote", Boolean.FALSE);	
			    }
			    if (player.getTemporaryAttributtes().get("trade_seals") == Boolean.TRUE) {	
					player.getTrade().addCoins(value, 12852);
					player.getTemporaryAttributtes().put("trade_seals", Boolean.FALSE);	
			    }
				if (trade_item_X_Slot == null)
					return;
				if (player.getTemporaryAttributtes().remove("trade_isRemove") != null)
					player.getTrade().removeItem(trade_item_X_Slot, value, false, null);
				else
					player.getTrade().addItem(trade_item_X_Slot, value);
			} else if (player.getTemporaryAttributtes().get("skillId") != null) {
				if (player.getEquipment().wearingArmour()) {
					player.getDialogueManager().finishDialogue();
					player.getDialogueManager().startDialogue("SimpleMessage", "You cannot do this while having armour on!");
					return;
				}
				int skillId = (Integer) player.getTemporaryAttributtes()
						.remove("skillId");
				if (skillId == Skills.HITPOINTS && value <= 9)
					value = 10;
				else if (value < 1)
					value = 1;
				else if (value > 99)
					value = 99;
				player.getSkills().set(skillId, value);
				player.getSkills().setXp(skillId, Skills.getXPForLevel(value));
				player.getAppearence().generateAppearenceData();
				player.getDialogueManager().finishDialogue();
			} else if (player.getTemporaryAttributtes().get("kilnX") != null) {
				int index = (Integer) player.getTemporaryAttributtes().get("scIndex");
				int componentId = (Integer) player.getTemporaryAttributtes().get("scComponentId");
				int itemId = (Integer) player.getTemporaryAttributtes().get("scItemId");
				player.getTemporaryAttributtes().remove("kilnX");
				if (StealingCreation.proccessKilnItems(player, componentId, index, itemId, value))
					return;
			} else if (player.getTemporaryAttributtes().remove("raffleamountentry") != null) {
				int amnt = value;		
				if (amnt > 1000) {
					player.getDialogueManager().startDialogue("SimpleMessage", "You can only enter a maximum of 1000 100M Tickets at a time.");
					return;
				}
				if (amnt >= 1) {
					if (player.getCurrencyPouch().spend100mTicket(amnt)) {
						player.getDialogueManager().startDialogue("SimpleMessage", "You have entered the raffle. " + amnt + " 100M Tickets has been removed from your inventory.");
						Raffle.addRaffle(player, amnt);
		                for (NPC n : World.getNPCs()) {
		                    if (n.getId() == 15971) {
		                        n.setNextForceTalk(new ForceTalk("The weekly prize pool is now at " + Raffle.getEntries() + " 100M Tickets!"));
		                    }
		                }
					} else {
						player.getDialogueManager().startDialogue("SimpleMessage", "You don't have " + amnt + " Tickets to enter!");
					}
					return;
				} else {
					player.getDialogueManager().startDialogue("SimpleMessage", "You can't enter zero tickets!");
					return;
				}
			}
			
		} else if (packetId == SWITCH_INTERFACE_ITEM_PACKET) {
			stream.readShortLE128();
			int fromInterfaceHash = stream.readIntV1();
			int toInterfaceHash = stream.readInt();
			int fromSlot = stream.readUnsignedShort();
			int toSlot = stream.readUnsignedShortLE128();
			stream.readUnsignedShortLE();


			int toInterfaceId = toInterfaceHash >> 16;
				int toComponentId = toInterfaceHash - (toInterfaceId << 16);
				int fromInterfaceId = fromInterfaceHash >> 16;
				int fromComponentId = fromInterfaceHash - (fromInterfaceId << 16);

				if (Utils.getInterfaceDefinitionsSize() <= fromInterfaceId
						|| Utils.getInterfaceDefinitionsSize() <= toInterfaceId)
					return;
				if (!player.getInterfaceManager()
						.containsInterface(fromInterfaceId)
						|| !player.getInterfaceManager().containsInterface(
								toInterfaceId))
					return;
				if (fromComponentId != -1
						&& Utils.getInterfaceDefinitionsComponentsSize(fromInterfaceId) <= fromComponentId)
					return;
				if (toComponentId != -1
						&& Utils.getInterfaceDefinitionsComponentsSize(toInterfaceId) <= toComponentId)
					return;
				if (fromInterfaceId == 1265 && toInterfaceId == 1266 && player.getTemporaryAttributtes().get("shop_buying") != null) {
					if ((boolean) player.getTemporaryAttributtes().get("shop_buying") == true) {
						Shop shop = (Shop) player.getTemporaryAttributtes().get("Shop");
						if (shop == null)
							return;
						shop.buy(player, fromSlot, 1);
					}
				}
				if (fromInterfaceId == Inventory.INVENTORY_INTERFACE
						&& fromComponentId == 0
						&& toInterfaceId == Inventory.INVENTORY_INTERFACE
						&& toComponentId == 0) {
					toSlot -= 28;
					if (toSlot < 0
							|| toSlot >= player.getInventory()
							.getItemsContainerSize()
							|| fromSlot >= player.getInventory()
							.getItemsContainerSize())
						return;
					player.getInventory().switchItem(fromSlot, toSlot);
				} else if (fromInterfaceId == 763 && fromComponentId == 0
						&& toInterfaceId == 763 && toComponentId == 0) {
					if (toSlot >= player.getInventory().getItemsContainerSize()
							|| fromSlot >= player.getInventory()
							.getItemsContainerSize())
						return;
					player.getInventory().switchItem(fromSlot, toSlot);
				} else if (fromInterfaceId == 762 && toInterfaceId == 762) {
					player.getBank().switchItem(fromSlot, toSlot, fromComponentId,
							toComponentId);
				}
				if (Settings.DEBUG)
					System.out.println("Switch item " + fromInterfaceId + ", "
							+ fromSlot + ", " + toSlot);
		} else if (packetId == DONE_LOADING_REGION_PACKET) {
			/*
			 * if(!player.clientHasLoadedMapRegion()) { //load objects and items
			 * here player.setClientHasLoadedMapRegion(); }
			 * //player.refreshSpawnedObjects(); //player.refreshSpawnedItems();
			 */
		} else if (packetId == WALKING_PACKET
				|| packetId == MINI_WALKING_PACKET
				|| packetId == ITEM_TAKE_PACKET
				|| packetId == PLAYER_OPTION_2_PACKET
				|| packetId == PLAYER_OPTION_5_PACKET
				|| packetId == PLAYER_OPTION_4_PACKET
				|| packetId == PLAYER_OPTION_1_PACKET || packetId == ATTACK_NPC
				|| packetId == INTERFACE_ON_PLAYER
				|| packetId == INTERFACE_ON_NPC
				|| packetId == NPC_CLICK1_PACKET
				|| packetId == NPC_CLICK2_PACKET
				|| packetId == NPC_CLICK3_PACKET
				|| packetId == OBJECT_CLICK1_PACKET
				|| packetId == SWITCH_INTERFACE_ITEM_PACKET
				|| packetId == OBJECT_CLICK2_PACKET
				|| packetId == OBJECT_CLICK3_PACKET
				|| packetId == OBJECT_CLICK4_PACKET
				|| packetId == OBJECT_CLICK5_PACKET
				|| packetId == INTERFACE_ON_OBJECT) 

			player.addLogicPacketToQueue(new LogicPacket(packetId, length,
					stream));
		
		else if (packetId == OBJECT_EXAMINE_PACKET) {
			ObjectHandler.handleOption(player, stream, -1);
		}else if (packetId == NPC_EXAMINE_PACKET) {
			NPCHandler.handleExamine(player, stream);
		} else if (packetId == JOIN_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			FriendChatsManager.joinChat(stream.readString(), player);
		} else if (packetId == KICK_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 1000); // avoids
			player.kickPlayerFromFriendsChannel(stream.readString());
		} else if (packetId == PLAYER_OPTION_9_PACKET) {
			boolean forceRun = stream.readUnsignedByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2 == player || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.isLocked())
				return;
			if (forceRun)
				player.setRun(forceRun);
			player.stopAll();
			ClansManager.viewInvite(player, p2);
		} else if (packetId == CHANGE_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted()
					|| !player.getInterfaceManager().containsInterface(1108))
				return;
			player.getFriendsIgnores().changeRank(stream.readString(),
					stream.readUnsignedByte128());
		} else if (packetId == ADD_FRIEND_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().addFriend(stream.readString());
		} else if (packetId == REMOVE_FRIEND_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().removeFriend(stream.readString());
		} else if (packetId == ADD_IGNORE_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().addIgnore(stream.readString(), stream.readUnsignedByte() == 1);
		} else if (packetId == REMOVE_IGNORE_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().removeIgnore(stream.readString());
		} else if (packetId == SEND_FRIEND_MESSAGE_PACKET) {
			if (!player.hasStarted())
				return;
			if (IPMute.isMuted(player.getSession().getIP())) {
				player.sm(
						"Your account has been permanently IP Muted.");
				return;
			}
			if (player.getMuted() > Utils.currentTimeMillis() || player.starter == 0) {
				player.sm(
						"You temporary muted. Recheck in 30 minutes.");
				return;
			}
			String username = stream.readString();
			Player p2 = World.getPlayerByDisplayName(username);
			if (p2 == null) {
				p2 = World.getPlayerPMName(username);
				if (p2 == null) {
					player.sm(username + " isn't online.");
						return;
				}
			}
			String str = Utils.fixChatMessage(Huffman.readEncryptedMessage(150, stream));
			if (str.toLowerCase().contains("catanai")) {
				for (int i = 0; i < 2; i++)
				World.sendWorldMessage("WARNING: <col=0FFFFF>"+player.getUsername()+" said catanai in private messages! Ask fatal for logs", true);
			}
			if (p2.getFriendsIgnores().getPrivateStatus() == 2)
				return;
			player.getFriendsIgnores().sendMessage(p2,	str);
			player.antiNull++;
			if (player.antiNull >= 2) {
				player.sm("Shut it! You can't talk for 15 seconds now.");
				player.setMuted(Utils.currentTimeMillis() + ((15000)));
			}
			player.pmLog(player, p2,  str);
		/*} else if (packetId == SEND_FRIEND_QUICK_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			String username = stream.readString();
			int fileId = stream.readUnsignedShort();
			byte[] data = null;
			if (length > 3 + username.length()) {
				data = new byte[length - (3 + username.length())];
				stream.readBytes(data);
			}
			data = Utils.completeQuickMessage(player, fileId, data);
			Player p2 = World.getPlayerByDisplayName(username);
			if (p2 == null || data == null || fileId > 1163)
				return;
			player.getFriendsIgnores().sendQuickChatMessage(p2,
					new QuickChatMessage(fileId, data));
		} else if (packetId == PUBLIC_QUICK_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			if (player.getLastPublicMessage() > Utils.currentTimeMillis())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 300);
			// just tells you which client script created packet
			@SuppressWarnings("unused")
			boolean secondClientScript = stream.readByte() == 1;// script 5059
			// or 5061
			int fileId = stream.readUnsignedShort();
			byte[] data = null;
			if (length > 3) {
				data = new byte[length - 3];
				stream.readBytes(data);
			}
			data = Utils.completeQuickMessage(player, fileId, data);
			if (data == null || fileId > 1163)
				return;
			if (chatType == 0)
				player.sendPublicChatMessage(new QuickChatMessage(fileId, data));
			else if (chatType == 1)
				player.sendFriendsChannelQuickMessage(new QuickChatMessage(
						fileId, data));
			else if (Settings.DEBUG)
				Logger.log(this, "Unknown chat type: " + chatType);*/
		} else if (packetId == CHAT_TYPE_PACKET) {
			chatType = stream.readUnsignedByte();
		} else if (packetId == CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			if (player.getLastPublicMessage() > Utils.currentTimeMillis())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 300);
			int colorEffect = stream.readUnsignedByte();
			int moveEffect = stream.readUnsignedByte();
			String message = Huffman.readEncryptedMessage(200, stream);
			if (message == null || message.replaceAll(" ", "").equals(""))
				return;
			if (colorEffect > 11 || moveEffect > 5 || colorEffect < 0 || moveEffect < 0) {
				player.sm("An error occured in this text. (Invalid color or animation effect)");
				return;
			}
			if (player.starter == 0)
				return;
			if (player.getRights() < 7) {
				   String[] Q = { ".com", ".net", ".c o m", ". c o m", ".co.uk", ".tk", "scape", ".c0m", ".n3t", ".webs", ".co.cc", "www.", ".org", ".us", ".weebly", ".edu", ".uk", ".fr", ".ru", ".gov", ".us.to", ".zzl.org" };
				   for (String string : Q)
				    if (message.contains(string)) {
				     player.sm("Sorry, No advertising.");
				     return;
				    }
				   }
				  //Known DKK chat Exploits - "0hdr2ufufl9ljlzlyla", "0hdr7lo", "0hdr3lo", "0hdr4lo", "0hdr2ufuflal9l2l2uol=lulglgldlzlyl(lul%lcuolyl7lsl(l%l?uolul8l9lguolzl7lglylal(l" };
				    if (message.contains("0hdr") || (message.contains("ohdr") || (message.contains("extinction") || (message.contains("e x t i n c t i o n") || (message.contains("extincti0n") || (message.contains("ext1nct1on") || message.contains("0hdr2ufuflal9l2l2uol=lulglgldlzlyl(lul%lcuolyl7lsl(l%l?uolul8l9lguolzl7lglylal(l"))))))) {
				     player.sm("Lol");
				     return;
				   }
				    if (message.length() > 71) 
				    	message = message.substring(0,70);
				    
			Player.chatLog(player, message);
			if (message.startsWith("::") || message.startsWith(";;")) {
				// if command exists and processed wont send message as public
				// message
				Commands.processCommand(player, message.replace("::", "").replace(";;", ""), false, false);
				return;
			}
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.sm(
						"You temporary muted. Recheck in a while.");
				return;
			}
			if (IPMute.isMuted(player.getSession().getIP())) {
				player.sm(
						"You're account has been permanently IP Muted.");
				return;
			}
			int effects = (colorEffect << 8) | (moveEffect & 0xff);
			if (chatType == 1)
				player.sendFriendsChannelMessage(Utils.fixChatMessage(message));
			else if (chatType == 2)
				player.sendClanChannelMessage(new ChatMessage(message));
			else if (chatType == 3)
				player.sendGuestClanChannelMessage(new ChatMessage(message));
			else
				player.sendPublicChatMessage(new PublicChatMessage(Utils.fixChatMessage(message), effects));
			player.setLastMsg(message);
			player.antiNull++;
			if (player.antiNull >= 3) {
				player.sm("Shut it! You can't talk for 15 seconds now.");
				player.setMuted(Utils.currentTimeMillis() + ((15000)));
			}
			player.setLastPublicMessageString(message);
			if (Settings.DEBUG)
				Logger.log(this, "[Chat type " + chatType+"] "+player.getDisplayName()+": "+message+"");
		} else if (packetId == COMMANDS_PACKET) {
			if (!player.isRunning())
				return;
			boolean clientCommand = stream.readUnsignedByte() == 1;
			@SuppressWarnings("unused")
			boolean unknown = stream.readUnsignedByte() == 1;
			String command = stream.readString();
			if (!Commands.processCommand(player, command, true, clientCommand) && Settings.DEBUG)
				Logger.log(this, "Command: " + command);
		} else if (packetId == COLOR_ID_PACKET) {
			if (!player.hasStarted())
				return;
			int colorId = stream.readUnsignedShort();
			if (player.getTemporaryAttributtes().get("SkillcapeCustomize") != null)
				SkillCapeCustomizer.handleSkillCapeCustomizerColor(player, colorId);
		} else if (packetId == FORUM_THREAD_ID_PACKET) {
			String threadId = stream.readString();
			if (player.getInterfaceManager().containsInterface(1100))
				ClansManager.setThreadIdInterface(player, threadId);
			else if (Settings.DEBUG) {
				player.antiNull++;
				if (player.antiNull >= 10)
					player.forceLogout();
				Logger.log(this, "Called FORUM_THREAD_ID_PACKET: " + threadId + player.antiNull);
			}
		}else if (packetId == REPORT_ABUSE_PACKET) {
			if (!player.hasStarted())
				return;
			@SuppressWarnings("unused")
			String username = stream.readString();
			@SuppressWarnings("unused")
			int type = stream.readUnsignedByte();
			@SuppressWarnings("unused")
			boolean mute = stream.readUnsignedByte() == 1;
			@SuppressWarnings("unused")
			String unknown2 = stream.readString();
		} else {
			//if (Settings.DEBUG)
			//	Logger.log(this, "Missing packet " + packetId + ", expected size: " + length + ", actual size: "+ PACKET_SIZES[packetId]);
		}
	}

}
