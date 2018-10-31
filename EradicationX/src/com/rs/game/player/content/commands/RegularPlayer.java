package com.rs.game.player.content.commands;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.rs.content.utils.IPMute;
import com.rs.game.WorldTile;
import com.motivoters.motivote.service.MotivoteRS;
import com.rs.Settings;
import com.rs.game.ForceTalk;
import com.rs.game.World;
import com.rs.game.item.Item;
import com.rs.game.player.CosmeticItems;
import com.rs.game.player.Equipment;
import com.rs.game.player.KillCount;
import com.rs.game.player.Player;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.TicketSystem;
import com.rs.game.player.content.VoteReward;
import com.rs.game.player.content.WorldVote;
import com.rs.game.player.content.custom.DoubleVoteManager;
import com.rs.game.player.content.custom.TriviaBot;
import com.rs.game.player.content.custom.YellHandler;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.rss.ForumThread;
import com.rs.utils.DropUtils;
import com.rs.utils.Encrypt;
import com.rs.utils.Utils;
import com.rs.utils.VotingBoard;

public class RegularPlayer {
	public static boolean hasPrivelages(Player player) {
		if (player.getRights() >= 1 || player.getRights() == 0 && player.isExtremeDonator()
				|| player.getRights() == 0 && player.isLentDonator()
				|| player.getRights() == 0 && player.isLentExtreme() || player.getRights() == 0 && player.isLentSavior()
				|| player.getRights() == 0 && player.isLentEradicator()
				|| player.getRights() == 0 && player.isEradicator() || player.getRights() == 0 && player.isDonator()
				|| player.getRights() == 0 && player.isForumMod() || player.getRights() == 0 && player.isDicer()
				|| player.getRights() == 0 && player.isOwner()) {
			return true;
		}
		return false;
	}

	public static boolean processCommand(Player player, String[] cmd, boolean console, boolean clientCommand) {
		if (clientCommand) {
		} else {
			if (player.isIronMan() || player.getRights() > 6) {
				if (cmd[0].equals("ironzone") || cmd[0].equals("iz") || cmd[0].equals("ironmanzone")
						|| cmd[0].equals("imz") || cmd[0].equals("eradzone")) {
					player.sm("Teleporting to: Ironman's Zone");
					if (!player.canSpawn()) {
						player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
						return true;
					}
					Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3182, 5713, 0));
					player.getPackets().sendGameMessage("Ironman Zone (Untradeable Eradicator Zone)");
					return true;
				}

				if (cmd[0].equals("giveup")) {
					int x = 0;
					if (cmd.length > 1)
						x = Integer.parseInt(cmd[1]);
					if (x == player.getSecurityPin())
						player.getDialogueManager().startDialogue("GiveUp");
					else
						player.sm("You must input your security pin. Try ;;giveup idhere");
					return true;
				}

			}

			switch (cmd[0]) {
			case "download":
				player.getPackets().sendOpenURL("http://eradication-reborn.com/Eradication-X-Reborn.jar");
				break;
			case "npcdrops":
				StringBuilder npcNameSB = new StringBuilder(cmd[1]);
				if (cmd.length > 1) {
					for (int i = 2; i < cmd.length; i++) {
						npcNameSB.append(" ").append(cmd[i]);
					}
				}
				DropUtils.sendNPCDrops(player, npcNameSB.toString());
				return true;

			case "itemdrops":
				StringBuilder itemName = new StringBuilder(cmd[1]);
				if (cmd.length > 1) {
					for (int i = 2; i < cmd.length; i++) {
						itemName.append(" ").append(cmd[i]);
					}
				}
				DropUtils.sendItemDrops(player, itemName.toString());
				return true;
			}
			if (cmd[0].equalsIgnoreCase("checkjoined")) {
				player.sm(player.joined.size() + "");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("togglelogin")) {
				player.setHasLoginToggled(!player.isHasLoginToggled());
				player.sm("Your login message is now " + (player.isHasLoginToggled() ? "disabled." : "enabled."));
				return true;
			}
			/*
			 * if (cmd[0].equalsIgnoreCase("ts") || cmd[0].equalsIgnoreCase("teamspeak") ||
			 * cmd[0].equalsIgnoreCase("eradts")) {
			 * player.sm("The official teamspeak server is: eradicationx.teamspeak.host");
			 * return true; }
			 */
			if (cmd[0].equalsIgnoreCase("worldmap")) {
				if (player.getInterfaceManager().containsScreenInter()
						|| player.getInterfaceManager().containsInventoryInter()) {
					player.getPackets()
							.sendGameMessage("Please finish what you're doing before opening the world map.");
					return true;
				}
				// world map open
				player.getPackets().sendWindowsPane(755, 0);
				int posHash = player.getX() << 14 | player.getY();
				player.getPackets().sendGlobalConfig(622, posHash); // map open
				// center
				// pos
				player.getPackets().sendGlobalConfig(674, posHash); // player
				// position
				return true;
			}
			if (cmd[0].equalsIgnoreCase("checkobby")) {
				player.sm("---------------------------------");
				player.sm("Melee Obsidian bonus: " + player.fullObsidianEquipped(Equipment.OBSIDIAN_WHELM));
				player.sm("Mage Obsidian bonus: " + player.fullObsidianEquipped(Equipment.OBSIDIAN_MHELM));
				player.sm("Ranged Obsidian bonus: " + player.fullObsidianEquipped(Equipment.OBSIDIAN_RHELM));
				player.sm("Elite Melee Obsidian bonus: " + player.fullEObsidianEquipped(Equipment.EOBSIDIAN_WHELM));
				player.sm("Elite Mage Obsidian bonus: " + player.fullEObsidianEquipped(Equipment.EOBSIDIAN_MHELM));
				player.sm("Elite Ranged Obsidian bonus: " + player.fullEObsidianEquipped(Equipment.EOBSIDIAN_RHELM));
				player.sm("White Melee Obsidian bonus: " + player.fullWObsidianEquipped(Equipment.WOBSIDIAN_WHELM));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("zoomout")) {
				int zoomId = Integer.valueOf(cmd[1]);

				if (zoomId < 25 || zoomId > 2500) {
					player.getPackets().sendGameMessage("You can't zoom that much.");
					return true;
				}

				player.getPackets().sendGlobalConfig(184, zoomId);
				player.getPackets().sendGameMessage("<img=14><col=FF0000>Do ;;resetzoom to return to normal.");
				return true;
			}

			if (cmd[0].equalsIgnoreCase("zoom")) {
				int zoomId = Integer.valueOf(cmd[1]);

				if (zoomId < 25 || zoomId > 2500) {
					player.getPackets().sendGameMessage("You can't zoom that much.");
					return true;
				}

				player.getPackets().sendGlobalConfig(184, zoomId);
				player.getPackets().sendGameMessage("<img=14><col=FF0000>Do ;;resetzoom to return to normal.");
				return true;
			}

			if (cmd[0].equalsIgnoreCase("resetzoom")) {
				player.getPackets().sendGlobalConfig(184, 0);
				return true;
			}
			if (cmd[0].equals("emptyfriendslist")) {
				player.sm("All of your friends in your friends list were removed.");
				player.sm("Please relog for your list to refresh.");
				player.getFriendsIgnores().emptyFriends();
				return true;
			}
			if (cmd[0].equals("getmeout")) {
				if (player.getControlerManager().getControler() != null) {
					if (player.getControlerManager().getControler().getArguments() != null) {
						if (player.getControlerManager().getControler().getArguments()[0] == (Object) 69) {
							player.unlock();
							player.getControlerManager().forceStop();
							if (player.getNextWorldTile() == null)
								player.setNextWorldTile(Settings.RESPAWN_PLAYER_LOCATION);
							player.wenttoevent = true;
							player.setFreezeDelay(0);
						}
					}
				}

				return true;
			}
			if (cmd[0].equals("checktbox")) {
				int[] trimmedItems = { 6617, 6619, 6623, 6625, 6629, 4125, 2583, 2585, 2587, 2589, 2591, 2593, 2595,
						2597, 4129, 2599, 2601, 2603, 2605, 2607, 2609, 2611, 2613, 2623, 2625, 2627, 2629, 2617, 2619,
						2621, 19428, 19431, 19437, 19440, 19413, 19416, 19422, 19425, 2661, 2663, 2665, 2667, 2653,
						2655, 2657, 2659, 2671, 2673, 2675, 2669, 19398, 19401, 19407, 19410, 19336, 19337, 19338,
						19340, 19341, 19342, 19343, 19345, 7368, 7364, 7362, 7366, 7380, 7372, 7378, 7370, 7376, 7384,
						7382, 7374, 10368, 10370, 10372, 10374, 10376, 10378, 10380, 10382, 10384, 10386, 10388, 10390,
						19443, 19445, 19447, 19449, 19452, 19454, 19456, 19458, 19459, 19461, 19463, 19465 };
				player.getInterfaceManager().sendInterface(275);
				for (int i = 10; i < 310; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				int number = 1;
				for (int rare : trimmedItems) {
					Item item = new Item(rare);
					player.getPackets().sendIComponentText(275, (11 + number), number + ". " + item.getName());
					number++;
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX's Trimmed Box");
				player.getPackets().sendIComponentText(275, 10, " ");
				return true;
			}
			if (cmd[0].equals("checkcbox")) {
				int[] trimmedItems = { 22568, 22578, 22588, 22598, 22610, 22620, 22630, 22640, 20125, 20127, 20129,
						20131, 20133, 24329, 24330, 24331, 24332, 24333, 18705, 18706, 18707, 18708, 18709, 24560,
						24561, 24562, 24563, 24564, 24555, 24556, 24557, 24558, 24559, 19706, 19707, 19708, 25816,
						25817, 25818, 25819, 25820, 25845, 25847, 25849, 25851, 25853, 25835, 25837, 25839, 25841,
						25843, 25825, 25827, 25829, 25831, 25833 };
				player.getInterfaceManager().sendInterface(275);
				for (int i = 10; i < 310; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				int number = 1;
				for (int rare : trimmedItems) {
					Item item = new Item(rare);
					player.getPackets().sendIComponentText(275, (11 + number), number + ". " + item.getName());
					number++;
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX's Cosmetic Box");
				player.getPackets().sendIComponentText(275, 10, " ");
				return true;
			}
			if (cmd[0].equals("rares")) {
				player.sm("Here are a list of rares. These are found in the raffle.");
				player.getInterfaceManager().sendInterface(275);
				for (int i = 10; i < 310; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				int number = 1;
				for (int rare : Settings.RARES) {
					Item item = new Item(rare);
					player.getPackets().sendIComponentText(275, (11 + number), number + ". " + item.getName());
					number++;
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX's Rares");
				player.getPackets().sendIComponentText(275, 10, " ");
				return true;
			}
			if (cmd[0].equals("cosmetic")) {
				CosmeticItems.openInterface(player);
				player.setInterfaceAmount(0);
				return true;
			}
			if (cmd[0].equals("donatedamount")) {
				// NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
				player.sm("You've donated $" + player.donatedtotal + " towards the server.");
				return true;
			}
			if (cmd[0].equals("titlez")) {
				KillCount.openInterface(player);
				return true;
			}
			if (cmd[0].equals("answer")) {
				if (player.hasDisabledTrivia()) {
					player.sendMessage("Please re-enable trivia by typing ::trivia");
					return true;
				}
				String name = "";
				for (int i = 1; i < cmd.length; i++) {
					name += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				}
				TriviaBot.getInstance().verify(player, name);
				return true;
			}

			if (cmd[0].equals("trivia")) {
				player.setDisableTrivia(!player.hasDisabledTrivia());
				player.sendMessage("Trivia questions are now " + (player.hasDisabledTrivia() ? "hidden" : "visible"));
				return true;
			}

			if (cmd[0].equals("hidedice")) {
				player.setDiceAnnounce(player.isDiceAnnounce() ? false : true);
				player.getPackets().sendGameMessage(
						"You have " + (player.isDiceAnnounce() ? "locked" : "unlocked") + " your dice announcements.");
				return true;
			}
			if (cmd[0].equals("changepin")) {
				player.getTemporaryAttributtes().put("changesecuritypin", Boolean.TRUE);
				player.getPackets().sendRunScript(108, new Object[] { "Please enter your current pin number:" });
				return true;
			}
			if (cmd[0].equals("creationdate")) {
				long yourmilliseconds = player.getCreationDate();
				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
				Date resultdate = new Date(yourmilliseconds);
				player.sm("You created your account on: " + sdf.format(resultdate));
				long num = Utils.currentTimeMillis() - player.getCreationDate();
				int alldays = (int) (num / (1000 * 60 * 60 * 24));
				player.sm("That means your account is " + alldays + " days old!");
				return true;
			}
			if (cmd[0].equals("boxinggloves")) {
				long required = 15778500000L;
				long b = Utils.currentTimeMillis() - player.getCreationDate();
				if (b >= required) {
					player.sm("Both boxing gloves have been placed in your inventory.");
					player.getInventory().addItem(7671, 1);
					player.getInventory().addItem(7673, 1);
					return true;
				} else {
					int alldays = (int) (required / (1000 * 60 * 60 * 24));
					int days = (int) (b / (1000 * 60 * 60 * 24));
					int leftdays = alldays - days;
					player.sm("You must have played for " + alldays + " days!");
					player.sm("You can't use the gloves until " + leftdays + " more days.");
				}
				return true;
			}
			if (cmd[0].equals("vettitle")) {
				long required = 15778500000L;
				long b = Utils.currentTimeMillis() - player.getCreationDate();
				if (b >= required) {
					player.getAppearence().setTitle(992254);
					return true;
				} else {
					int alldays = (int) (required / (1000 * 60 * 60 * 24));
					int days = (int) (b / (1000 * 60 * 60 * 24));
					int leftdays = alldays - days;
					player.sm("You must have played for " + alldays + " days!");
					player.sm("You can't use the veteran title until " + leftdays + " more days.");
				}
				return true;
			}
			if (cmd[0].equals("mastertrivia")) {
				if (player.isTriviamaster()) {
					player.getAppearence().setTitle(992256);
					return true;
				} else {
					player.sm("You can't use the title.");
				}
				return true;
			}
			if (cmd[0].equals("ctitlecolor")) {
				if (player.getClanManager().getClan().getClanLeaderUsername().equalsIgnoreCase(player.getUsername())) {
					player.getTemporaryAttributtes().put("ctitlecolor", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the title color in HEX format (Colorpicker.com)" });
				} else {
					player.sm("You must be a clan leader to access these commands.");
				}
				return true;
			}
			if (cmd[0].equals("ctitlename")) {
				if (player.getClanManager().getClan().getClanLeaderUsername().equalsIgnoreCase(player.getUsername())) {
					player.getTemporaryAttributtes().put("ctitlename", Boolean.TRUE);
					player.getPackets().sendRunScript(109, new Object[] { "Please enter the title name you want" });
				} else {
					player.sm("You must be a clan leader to access these commands.");
				}
				return true;
			}
			if (cmd[0].equals("ctitleshade")) {
				if (player.getClanManager().getClan().getClanLeaderUsername().equalsIgnoreCase(player.getUsername())) {
					player.getTemporaryAttributtes().put("ctitleshade", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the title color in HEX format (Colorpicker.com)" });
				} else {
					player.sm("You must be a clan leader to access these commands.");
				}
				return true;
			}

			if (cmd[0].equals("ctitlenamecolor")) {
				if (player.getClanManager().getClan().getClanLeaderUsername().equalsIgnoreCase(player.getUsername())) {
					player.getTemporaryAttributtes().put("ctitlenamecolor", Boolean.TRUE);
					player.getPackets().sendRunScript(109,
							new Object[] { "Please enter the title color in HEX format (Colorpicker.com)" });
				} else {
					player.sm("You must be a clan leader to access these commands.");
				}
				return true;
			}

			if (cmd[0].equals("clantitle")) {
				if (player.getClanName() != null) {
					player.sm("You are now using your clan's title.");
					if (player.getClanManager().getClan().getClanLeaderUsername()
							.equalsIgnoreCase(player.getUsername()))
						player.sm(
								"You're the clan leader. You may edit the clan title via ;;ctitlecolor, ;;ctitlename, ;;ctitleshade, ;;ctitlenamecolor.");
					player.getAppearence().setTitle(491234);
					return true;
				} else {
					player.sm("You must be in a clan to use a clan title.");
				}
				return true;
			}
			if (cmd[0].equals("resettimer")) {
				if (player.inInstance()) {
					player.setInstanceBooth(0);
					player.setInstanceEnd(true);
					player.sm("Your instance has ended. You will now be sent to the original room.");
				} else {
					player.sm(
							"You can't use this command when you're not in an instance. You noob. What'd the command even do if you weren't in an instance?");
				}
				return true;
			}
			if (cmd[0].equals("worldvote")) {
				if (WorldVote.isActive()) {
					long num = WorldVote.getTime() - (Utils.currentTimeMillis() - (1 * 60 * 60 * 1000));
					player.sm("The World Vote reward is currently active and will end in "
							+ TimeUnit.MILLISECONDS.toMinutes(num) + " minutes.");
				} else {
					player.sm("World Vote count is currently at " + WorldVote.getVotes()
							+ ". Hourly 1.5x xp starts when it reaches 200 votes.");
				}
				return true;
			}
			if (cmd[0].equals("checkdonated")) {
				player.checkamountdonated(player, player.getUsername());
				return true;
			}
			if (cmd[0].equals("rules")) {
				player.getPackets().sendOpenURL(
						"http://eradication-reborn.com/forums/index.php?/topic/16-eradication-reborn-rules/");
				player.getInterfaceManager().sendInterface(275);
				for (int i = 0; i < 200; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				player.getPackets().sendIComponentText(275, 1, "Eradication-Reborn");
				player.getPackets().sendIComponentText(275, 10, "");
				player.getPackets().sendIComponentText(275, 11, "");
				player.getPackets().sendIComponentText(275, 12, "<col=ED0C17><u>Eradication-Reborn Rules</u>");
				player.getPackets().sendIComponentText(275, 14, "<col=ED0C17><u>1. No Scamming:");
				player.getPackets().sendIComponentText(275, 15,
						"<col=ED0C17>If found scamming, you will be confiscated of the item,");
				player.getPackets().sendIComponentText(275, 16, "<col=ED0C17>and jailed/banned for 24 hours");
				player.getPackets().sendIComponentText(275, 18, "<col=ED0C17><u>2. No Abuse of Bugs/Glitches: ");
				player.getPackets().sendIComponentText(275, 19,
						"<col=ED0C17>If a glitch is found, kindly report it on its section.");
				player.getPackets().sendIComponentText(275, 20,
						"<col=ED0C17> Otherwise, you will be banned for abusing the glitch.");
				player.getPackets().sendIComponentText(275, 22, "<col=ED0C17><u>3. No Staff Impersonation:");
				player.getPackets().sendIComponentText(275, 23,
						"<col=ED0C17>Change of Name Display to resemble a Staff Member is not tolerated.");
				player.getPackets().sendIComponentText(275, 24,
						"<col=ED0C17>You will be requested to remove the name,");
				player.getPackets().sendIComponentText(275, 25, "<col=ED0C17>otherwise you will be muted.");
				player.getPackets().sendIComponentText(275, 27, "<col=ED0C17><u>4. No Spamming or Advertising:");
				player.getPackets().sendIComponentText(275, 28,
						"<col=ED0C17>Do not spam other player's game-chat or advertise another server,");
				player.getPackets().sendIComponentText(275, 29,
						"<col=ED0C17>you will be muted for spamming, and IPbanned for Advertising.");
				player.getPackets().sendIComponentText(275, 31, "<col=ED0C17><u>5. No AFKing:");
				player.getPackets().sendIComponentText(275, 32,
						"<col=ED0C17>Anyone seen AFK skilling will result in a sendhome at first, then jail.");
				player.getPackets().sendIComponentText(275, 34, "<col=ED0C17><u>6. No Disrespecting Staff:");
				player.getPackets().sendIComponentText(275, 35,
						"<col=ED0C17>Disrespecting our dedicated staff will result in the following:");
				player.getPackets().sendIComponentText(275, 36, "<col=ED0C17>Light flame - Warn 3 times, then mute");
				player.getPackets().sendIComponentText(275, 37, "<col=ED0C17>Medium flame - Mute");
				player.getPackets().sendIComponentText(275, 38, "<col=ED0C17>Threat flame - IPban");
				player.getPackets().sendIComponentText(275, 40, "<col=ED0C17><u>7. No Multi-Logging:");
				player.getPackets().sendIComponentText(275, 41,
						"<col=ED0C17>Multilogging is not allowed, you will be asked to");
				player.getPackets().sendIComponentText(275, 42, "<col=ED0C17>log off your alternate account.");
				player.getPackets().sendIComponentText(275, 44, "<col=ED0C17><u>8. No use of Macros/Bots:");
				player.getPackets().sendIComponentText(275, 45,
						"<col=ED0C17> No Auto-clicking levels (Jail), or use of any bots");
				player.getPackets().sendIComponentText(275, 46,
						"<col=ED0C17>Auto-Typing is allowed if used every 5+ seconds");
				player.getPackets().sendIComponentText(275, 48, "<col=ED0C17><u>9. No Safespotting:");
				player.getPackets().sendIComponentText(275, 49,
						"<col=ED0C17>Safe spotting bosses (exception of Corp) is not acceptable.");
				player.getPackets().sendIComponentText(275, 50,
						"<col=ED0C17>first offense will be a warning, subsequent offenses will result in a jail.");
				player.getPackets().sendIComponentText(275, 52, "<col=ED0C17><u>10. No Real World Trading (RWT):");
				player.getPackets().sendIComponentText(275, 53,
						"<col=ED0C17>RWT is not tolerated. Instant IP ban will be applied on your account.");
				player.getPackets().sendIComponentText(275, 55,
						"<col=ED0C17><u>11. Buying/Selling Runescape Items/accounts:");
				player.getPackets().sendIComponentText(275, 56,
						"<col=ED0C17> Not tolerated. Only form of Runescape related item ");
				player.getPackets().sendIComponentText(275, 57,
						"<col=ED0C17>exchanging will be for donation purposes only.");
				player.getPackets().sendIComponentText(275, 59, "<col=ED0C17><u>12. No Flaming:");
				player.getPackets().sendIComponentText(275, 60,
						"<col=ED0C17>You will be granted two warnings, then you will be muted.");
				player.getPackets().sendIComponentText(275, 62, "<col=ED0C17><u>13. No Flame Baiting:");
				player.getPackets().sendIComponentText(275, 63,
						"<col=ED0C17>If you are caught attempting to provoke a player into flaming,");
				player.getPackets().sendIComponentText(275, 64, "<col=ED0C17>you will be punished aswell.");
				player.getPackets().sendIComponentText(275, 66, "<col=ED0C17><u>14. DDoS Threats:");
				player.getPackets().sendIComponentText(275, 67,
						"<col=ED0C17> Any form of threats to DDoS the server/players is not tolerated.");
				player.getPackets().sendIComponentText(275, 68, "<col=ED0C17> Will result in an immediate ipban.");
				player.getPackets().sendIComponentText(275, 70, "<col=ED0C17><u>15. Multi-voting:");
				player.getPackets().sendIComponentText(275, 71,
						"<col=ED0C17> Voting for over 10 tickets in a day is not tolerated. A moderator");
				player.getPackets().sendIComponentText(275, 72,
						"<col=ED0C17> has permission to revoke your tickets from your account.");
				player.getPackets().sendIComponentText(275, 74, "<col=ED0C17><u>16. Jail Evading:");
				player.getPackets().sendIComponentText(275, 75,
						"<col=ED0C17> Evading jail by logging onto an alternate account WILL RESULT");
				player.getPackets().sendIComponentText(275, 76, "<col=ED0C17>A JAIL ON THE ALTERNATE ACCOUNT!");
				player.getPackets().sendIComponentText(275, 78, "<col=ED0C17><u>18. PvP Farming:");
				player.getPackets().sendIComponentText(275, 79, "<col=ED0C17> Killing a player constantly, who isn't");
				player.getPackets().sendIComponentText(275, 80,
						"<col=ED0C17>actively fighting back, will result in a jail.");
				player.getPackets().sendIComponentText(275, 82, "<col=0C7682> If you have any questions, do ::ticket");
				player.getPackets().sendIComponentText(275, 83,
						"<col=0C7682> If you have one of these punishments and wish to ");
				player.getPackets().sendIComponentText(275, 84,
						"<col=0C7682> appeal, go on our forums and make a thread.");
				player.getPackets().sendIComponentText(275, 85, "<col=0C7682>");
				player.getPackets().sendIComponentText(275, 86,
						"<col=0C7682><img=7><img=13><img=16><img=18<img=0> EradicationX Staff <img=7><img=13><img=16><img=18<img=0>");
				player.getPackets().sendIComponentText(275, 3, "");
			}
			if (cmd[0].equals("dicerules")) {
				player.getInterfaceManager().sendInterface(275);
				for (int i = 10; i < 310; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX");
				player.getPackets().sendIComponentText(275, 10, "");
				player.getPackets().sendIComponentText(275, 11, "");
				player.getPackets().sendIComponentText(275, 12, "<col=ED0C17><u>Dicing Rules</u>");
				player.getPackets().sendIComponentText(275, 13, "<col=ED0C17>The rules are straight forward:");
				player.getPackets().sendIComponentText(275, 14, "<col=ED0C17>The only method of gambling is dicing.");
				player.getPackets().sendIComponentText(275, 15,
						"<col=ED0C17>Any other method of dicing will cause the gambler");
				player.getPackets().sendIComponentText(275, 16,
						"<col=ED0C17>to get banned. Only trust <img=11>Dice Ranks");
				player.getPackets().sendIComponentText(275, 17,
						"<col=ED0C17>If a rank doesn't pay out, report it to a staff");
				player.getPackets().sendIComponentText(275, 18,
						"<col=ED0C17>Immediately. No proof is required if said in chat.");
				return true;
			}
			if (cmd[0].equals("recov")) {
				if (player.getRecovQuestion() == null) {
					player.getPackets().sendGameMessage("Please set your recovery question first.");
					return true;
				}
				if (player.getRecovAnswer() != null && player.getRights() < 2) {
					player.getPackets().sendGameMessage("You can only set recovery answer once.");
					return true;
				}
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				player.setRecovAnswer(message);
				player.getPackets().sendGameMessage(
						"Your recovery answer has been set to - " + Utils.fixChatMessage(player.getRecovAnswer()));
				return true;
			}

			if (cmd[0].equals("routfit")) {
				player.getAppearence().resetCosmetics(true);
				return true;
			}

			if (cmd[0].equals("setrecovery")) {
				if (player.getRecovQuestion() != null && player.getRights() < 2) {
					player.getPackets().sendGameMessage("You already have a recovery question set.");
					return true;
				}
				String message = "";
				for (int i = 1; i < cmd.length; i++)
					message += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				player.setRecovQuestion(message);
				player.getPackets().sendGameMessage(
						"Your recovery question has been set to - " + Utils.fixChatMessage(player.getRecovQuestion()));
				return true;
			}

			if (cmd[0].equals("yell") || cmd[0].equals("y")) {
				if (IPMute.isMuted(player.getSession().getIP())) {
					player.getPackets().sendGameMessage("Your account has been temporarily IP Muted.");
					return true;
				}
				if (player.getMuted() > Utils.currentTimeMillis()) {
					player.getPackets().sendGameMessage("You're temporarily muted and cannot yell.");
					return true;
				}
				if (!hasPrivelages(player)) {
					player.sendMessage("Only a donator may use this feature. Donate today!");
					return true;
				}
				String message1 = "";
				for (int i = 1; i < cmd.length; i++)
					message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				YellHandler.sendYell(player, Utils.fixChatMessage(message1));
				return true;
			}

			if (cmd[0].equalsIgnoreCase("vote")) {
				if (!player.getUsername().contains("_")) {
					player.getPackets().sendOpenURL(Settings.VOTE_LINK);
					return true;
				}
				player.getDialogueManager().startDialogue(new Dialogue() {
					@Override
					public void start() {
						sendNPCDialogue(659, HAPPY,
								"Note: Players with a space in there names are advised to use an underscore instead for your vote to work properly");
						stage = 0;
					}

					@Override
					public void run(int interfaceId, int componentId) {
						switch (stage) {
						case 0:
							finish();
							player.getPackets().sendOpenURL(Settings.VOTE_LINK);
							break;

						}
					}

					@Override
					public void finish() {
						player.getInterfaceManager().closeChatBoxInterface();
					}

				});
				return true;
			}
			/*
			 * if (cmd[0].equalsIgnoreCase("redeem")) { String auth = cmd[1]; if
			 * (player.completed == false) player.completed = true; else {
			 * player.sm("Your old auth is still being processed."); return true; }
			 * player.sm("Checking auth..."); VoteReward myRunnable = new VoteReward(auth,
			 * player); Thread t = new Thread(myRunnable); t.start(); return true; }
			 */
			/*
			 * if (cmd[0].equalsIgnoreCase("redeem")) {
			 * 
			 * new Thread() { public void run() { try { if (Integer.parseInt(cmd[1]) != 1) {
			 * player.sendMessage("This id has no reward."); return; } String playerName =
			 * player.getUsername(); final String request =
			 * com.everythingrs.vote.Vote.validate(
			 * "11944m94w22wo8p9nhkbzhyqfrvlvinjowshh7622mlhh3erk9zqr08edpa4yrvgscfdzk6gvi",
			 * playerName, 1); String[][] errorMessage = { { "error_invalid",
			 * "There was an error processing your request." }, {
			 * "error_non_existent_server", "This server is not registered at EverythingRS."
			 * }, { "error_invalid_reward",
			 * "The reward you're trying to claim doesn't exist" }, {
			 * "error_non_existant_rewards",
			 * "This server does not have any rewards set up yet." }, {
			 * "error_non_existant_player", "There is not record of user " + playerName +
			 * " make sure to vote first" }, { "not_enough",
			 * "You do not have enough vote rewards to recieve this item" } }; for (String[]
			 * message : errorMessage) { if (request.equalsIgnoreCase(message[0])) {
			 * player.sendMessage(message[1]); return; } } if
			 * (request.startsWith("complete")) { WorldVote.setVotes(WorldVote.getVotes() +
			 * 1); if (!DoubleVoteManager.isFirstDayofMonth()) { player.getCurrencyPouch()
			 * .setVoteTickets(player.getCurrencyPouch().getVoteTickets() + 2);
			 * player.voteDisplayAmount++; player.sendVoteNotification(); } else {
			 * player.getCurrencyPouch()
			 * .setVoteTickets(player.getCurrencyPouch().getVoteTickets() + 4);
			 * player.voteDisplayAmount += 2; player.sendVoteNotification(); } if
			 * (WorldVote.getVotes() >= 200) { World.sendWorldMessage(
			 * "<img=5><col=ff0000>[Global Vote]: Hourly 1.5x XP is now active. This XP stacks with every other bonus you have!"
			 * , false); WorldVote.startReward(); }
			 * player.setSpellPower(player.getSpellPower() + 2);
			 * player.setVote(player.getVote() + 2); VotingBoard.checkRank(player);
			 * 
			 * } } catch (Exception e) { player.sendMessage(
			 * "Our API services are currently offline. We are working on bringing it back up"
			 * ); e.printStackTrace(); } } }.start(); return true; }
			 */
			if (cmd[0].equals("checkmbox")) {
				int[] trimmedItems = { 1037, 14595, 14603, 1050, 23679, 23680, 23681, 11700, 23683, 23684, 23685, 23686,
						23687, 23688, 23689, 11730, 23691, 23692, 23693, 23694, 23695, 23696, 23697, 23698, 23699,
						23700, 25031, 25028, 25034, 2617, 2615, 27359, 20822, 20824, 20825, 20826, 20823, 16955, 16425,
						20135, 20139, 20143, 20159, 20163, 20167, 20147, 20151, 20155, 18349, 18351, 18355, 18359,
						18357, 16669, 17239, 1053, 1055, 1057, 19281, 19284, 19287, 19290, 19293, 19296, 19299, 19302,
						19305, 11789, 24359, 24360, 24361, 24362, 24363, 24639, 24641, 24643, 24645, 24647, 24649,
						24651, 28821, 28822, 28818, 28820, 28812, 17361, 28813, 27994 };
				player.getInterfaceManager().sendInterface(275);
				for (int i = 10; i < 310; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				int number = 1;
				for (int rare : trimmedItems) {
					Item item = new Item(rare);
					player.getPackets().sendIComponentText(275, (11 + number), number + ". " + item.getName());
					number++;
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX's Mystery Box");
				player.getPackets().sendIComponentText(275, 10, " ");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("highscores")) {
				player.getPackets().sendOpenURL(Settings.HIGHSCORES_LINK);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("topic")) {
				try {
					int num = Integer.parseInt(cmd[1]);
					if (num < 1) {
						player.getPackets().sendGameMessage("Please choose a valid thread ID.");
						return true;
					}
					player.getPackets().sendOpenURL(

							"http://eradication-reborn.com/forums/index.php?app=forums&module=forums&controller=topic&id=" + num
									+ "");
					return true;

				} catch (NumberFormatException e) {
					player.getPackets().sendGameMessage(";;topic threadId");
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("news")) {
				ForumThread.openInterface(player);
				return true;
			}
			if (cmd[0].equalsIgnoreCase("update")) {
				player.getPackets().sendOpenURL("http://eradication-reborn.com/forums/index.php?/forum/8-updates/");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("discord")) {
				player.getPackets().sendOpenURL("https://discord.gg/BYdrJNV");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("priceguide") || cmd[0].equalsIgnoreCase("pc")
					|| cmd[0].equalsIgnoreCase("pricecheck") || cmd[0].equalsIgnoreCase("prices")) {
				player.getPackets().sendOpenURL(Settings.FORUMS_LINK);
				return true;
			}
			/*
			 * if (cmd[0].equalsIgnoreCase("droprates") ||
			 * cmd[0].equalsIgnoreCase("droprate") || cmd[0].equalsIgnoreCase("dr")) {
			 * player.getPackets().sendOpenURL(
			 * "http://eradicationx.com/forums/index.php?/topic/3916-boss-drop-guide-with-all-drop-rates/"
			 * ); return true; }
			 */
			if (cmd[0].equalsIgnoreCase("perks")) {
				player.getPerksManager().displayAvailablePerks();
				return true;
			}
			
			if (cmd[0].equalsIgnoreCase("donatorperks")) {
				player.getPackets()
						.sendOpenURL("http://eradication-reborn.com/forums/index.php?/topic/15-donator-rank-benefits/");
				return true;
			}
			/*
			 * if (cmd[0].equalsIgnoreCase("launcher")) { player.getPackets().sendOpenURL(
			 * "http://www.eradicationx.com/files/EradicationX-Launcher.jar");
			 * player.getPackets().
			 * sendGameMessage("Please delete the client that doesn't say 2.0."); return
			 * true; }
			 */

			if (cmd[0].equals("train2")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3116, 9849, 0));
				player.getPackets().sendGameMessage("<img=7> Training2. <img=7>.");
				return true;

			}
			if (cmd[0].equals("skilling")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2214, 3304, 0));
				return true;

			}

			if (cmd[0].equals("dkboss")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2640, 10425, 0));
				return true;

			}

			if (cmd[0].equals("trio")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Trio");
				return true;

			}
			if (cmd[0].equals("armadyl")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Armadyl");
				return true;

			}
			if (cmd[0].equals("blink")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Blink");
				return true;

			}
			if (cmd[0].equals("corp")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Corp");
				return true;

			}
			if (cmd[0].equals("eradicatorboss")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("EradicatorBoss");
				return true;

			}
			if (cmd[0].equals("fear")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Fear");
				return true;

			}
			if (cmd[0].equals("geno") || cmd[0].equals("deathlotus")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Geno");
				return true;

			}
			if (cmd[0].equals("gradum")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Gradum");
				return true;

			}
			if (cmd[0].equals("necrolord")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Necrolord");
				return true;

			}
			if (cmd[0].equals("rajj") || cmd[0].equals("seasinger")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Rajj");
				return true;

			}
			if (cmd[0].equals("saradomin")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Saradomin");
				return true;

			}
			if (cmd[0].equals("stq")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("STQ");
				return true;

			}
			if (cmd[0].equals("wildywyrm")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Wyrm");
				return true;

			}
			if (cmd[0].equals("zamorak")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Zamorak");
				return true;

			}

			if (cmd[0].equals("bandos")) {
				if (!player.canSpawn() || player.inInstance()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				player.getDialogueManager().startDialogue("Bandos");
				return true;

			}

			if (cmd[0].equals("glacors")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4184, 5733, 0));
				player.getPackets().sendGameMessage("<img=7> Glacors. <img=7>.");
				return true;

			}

			if (cmd[0].equals("copytitle")) {
				if (player.getCopyrightKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992230);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("fataltitle")) {
				if (player.getFatalKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992231);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("armadyltitle")) {
				if (player.getArmadylKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992242);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("sunfreettitle")) {
				if (player.getSunfreetKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992245);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("nextitle")) {
				if (player.getNexKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992246);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("cleartitle")) {
				player.getAppearence().setTitle(0);

			}
			if (cmd[0].equals("jadtitle")) {
				if (player.getJadKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992247);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("obsidiankingtitle")) {
				if (player.getObsidianKingKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992248);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("hmtriotitle")) {
				if (player.getHMTrioKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992251);
			}
			if (cmd[0].equals("bandostitle")) {
				if (player.getBandosKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992243);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("zamoraktitle")) {
				if (player.getZamorakKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992241);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("saradomintitle")) {
				if (player.getSaradominKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992244);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("sometitle")) {
				if (player.getSomethingKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992232);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("eradbosstitle")) {
				if (player.getEradicatorBossKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992233);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("deathlotusitle")) {
				if (player.getGenoKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992234);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("seasingertitle")) {
				if (player.getRajjKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992235);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("feartitle")) {
				if (player.getFearKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992236);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("blinktitle")) {
				if (player.getBlinkKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992237);
				player.sm("Please use the 'killcount' button in the EX tab next time.");
			}
			if (cmd[0].equals("extremebosstitle")) {
				if (player.getExtremeBossKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992238);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("regularbosstitle")) {
				if (player.getRegularBossKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992239);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("gradumtitle")) {
				if (player.getGradumKills() < 100) {
					player.getPackets().sendGameMessage("You cannot access this title.");
					return true;
				}
				player.getAppearence().setTitle(992240);
				player.sm("Please use the 'killcount' button in the EX tab next time.");

			}
			if (cmd[0].equals("empty")) {
				player.getDialogueManager().startDialogue("EmptyWarning");
				return true;
			}

			if (cmd[0].equals("ticket")) {
				if (player.getMuted() > Utils.currentTimeMillis()) {
					player.getPackets().sendGameMessage("You temporary muted. Recheck in 48 hours.");
					return true;
				}
				TicketSystem.requestTicket(player);
				return true;
			}

			if (cmd[0].equals("train")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2408, 3851, 0));
				player.getPackets().sendGameMessage("<img=7> Training. <img=7>.");
				return true;

			}

			if (cmd[0].equals("blink")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2867, 5216, 0));
				player.getPackets().sendGameMessage("<img=7> Blink <img=7>.");
				return true;

			}

			if (cmd[0].equals("fear")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3438, 3731, 0));
				player.getPackets().sendGameMessage("<img=7> dropping upgradable whip :) <img=7>.");
				return true;

			}

			if (cmd[0].equals("dung")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3458, 3731, 0));
				// Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(1825, 5163, 2));
				player.getPackets().sendGameMessage("<img=7> Dungeoneering <img=7>.");
				return true;

			}

			if (cmd[0].equals("kdr")) {
				double kill = player.getKillCount();
				double death = player.getDeathCount();
				double dr = kill / death;
				player.setNextForceTalk(new ForceTalk("<col=ff0000>I'VE KILLED " + player.getKillCount()
						+ " PLAYERS AND DIED " + player.getDeathCount() + " TIMES. KDR: " + dr));
				return true;
			}

			if (cmd[0].equalsIgnoreCase("players")) {
				player.getInterfaceManager().sendInterface(275);
				int number = 0;
				int dicer = 0;
				int staff = 0;
				for (int i = 0; i < 100; i++) {
					player.getPackets().sendIComponentText(275, i, "");
				}
				for (Player p5 : World.getPlayers()) {
					if (p5 == null)
						continue;
					if (p5.isDicer())
						dicer++;
					if (p5.checkStaff())
						staff++;
					number++;
					String titles = "";
					String ironman = "";
					titles = "[Player]: ";
					if (p5.isIronMan()) {
						ironman = "<img=23>";
						titles = "<col=6D6F78><img=23>[Ironman]: ";
					}
					if (p5.isDonator())
						titles = "[" + ironman + "<img=10><col=268703>Donator]: ";
					if (p5.isExtremeDonator())
						titles = "[" + ironman + "<img=8><col=DB1A1A>Extreme Donator</col>]: ";
					if (p5.isSavior())
						titles = "[" + ironman + "<img=9><col=33B8D6>Super Donator</col>]: ";
					if (p5.isEradicator())
						titles = "[" + ironman + "<img=18><col=02385E>Eradicator</col>]: ";
					if (p5.isDicer())
						titles = "[" + ironman + "<img=11><col=C423C4>Dicer</col>]: ";
					if (p5.isHero())
						titles = "[" + ironman + "<img=22><col=000000>Hero</col>]: ";
					if (p5.isSupporter())
						titles = "[" + ironman + "<img=13><col=00ff48>Supporter</col>]: ";
					if (p5.isForumMod())
						titles = "[" + ironman + "<img=20><col=FF8930>Forum Moderator</col>]: ";
					if (p5.getRights() == 1)
						titles = "[" + ironman + "<img=0><col=559568>Moderator</col>]: ";
					if (p5.isHeadMod())
						titles = "[" + ironman + "<img=16>Head Moderator</col>]: ";
					if (p5.isForumAdmin())
						titles = "[" + ironman + "<img=24>Forum Admin</col>]: ";
					if (p5.isExecutive() || p5.getRights() == 2)
						titles = "[" + ironman + "<img=17>Executive</col>]: ";
					if (p5.isHeadExecutive())
						titles = "[" + ironman + "<img=21>Head Executive</col>]: ";
					if (p5.getRights() == 7)
						titles = "[" + ironman + "<img=7> <col=00FFFF>Owner/Developer</col>]: ";
					player.getPackets().sendIComponentText(275, (14 + number),
							number + ". " + titles + "" + p5.getDisplayName());
				}
				player.getPackets().sendIComponentText(275, 1, "EradicationX's Players");
				player.getPackets().sendIComponentText(275, 10, " ");
				player.getPackets().sendIComponentText(275, 11, "Players Online: " + number);
				player.getPackets().sendIComponentText(275, 12, "Dicers Online: " + dicer);
				player.getPackets().sendIComponentText(275, 13, "Staff Online: " + staff);
				player.getPackets().sendGameMessage("There are currently " + World.getPlayers().size()
						+ " players playing " + Settings.SERVER_NAME + ".");
				return true;
			}

			if (cmd[0].equals("shops")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, Settings.SHOPS);
				// Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3968, 4822, 2));
				player.getPackets().sendGameMessage("<img=5> Shops. <img=5>");
				return true;
			}

			if (cmd[0].equals("donate") || cmd[0].equals("store")) {
				// if (player.setRPayPin()) {
				if (!player.getUsername().contains("_")) {
					//player.getPackets().sendOpenURL(
					//		"http://eradication-reborn.com/forums/index.php?/topic/15-donator-rank-benefits/");
					player.getPackets().sendOpenURL(Settings.STORE_LINK);
					return true;
				}
				player.getDialogueManager().startDialogue(new Dialogue() {

					@Override
					public void start() {
						sendNPCDialogue(659, HAPPY,
								"Note: Players with a space in there names are advised to use an underscore instead for your donation to work properly");
						stage = 0;
					}

					@Override
					public void run(int interfaceId, int componentId) {
						switch (stage) {
						case 0:
							finish();
							//player.getPackets().sendOpenURL(
							//		"http://eradication-reborn.com/forums/index.php?/topic/15-donator-rank-benefits/");
							player.getPackets().sendOpenURL(Settings.STORE_LINK);
							break;

						}
					}

					@Override
					public void finish() {
						player.getInterfaceManager().closeChatBoxInterface();
					}

				});
				return true;
			}

			if (cmd[0].equalsIgnoreCase("hiddenclaim")) {
				try {
					player.rspsdata(player, player.getUsername(), true);
					return true;
				} catch (

				Exception e) {
				}
				return true;
			}
			if (cmd[0].equalsIgnoreCase("claimweb") || cmd[0].equalsIgnoreCase("donated")
					|| cmd[0].equalsIgnoreCase("claimreward") || cmd[0].equalsIgnoreCase("paid")) {
				if (player.isIronMan()) {
					player.sm(
							"Ironman accounts cannot donate. If you choose to ignore this warning and donate regardless, we are not held responsible!");
					return true;
				}
				try {
					if (!player.rspsdata(player, player.getUsername(), false))
						if (!player.rspsdata(player, player.getDisplayName(), false))
							player.sm("Could not find any donations pending for you.");
					return true;
				} catch (Exception e) {
				}
				player.sm("Could not find any donations pending for you.");
				return true;
			}

			if (cmd[0].equals("lockxp")) {
				player.setXpLocked(player.isXpLocked() ? false : true);
				player.getPackets()
						.sendGameMessage("You have " + (player.isXpLocked() ? "locked" : "unlocked") + " your xp.");
				return true;
			}

			if (cmd[0].equalsIgnoreCase("home")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, Settings.PLAYER_HOME_LOCATION);
				return true;
			}

			if (cmd[0].equalsIgnoreCase("nex")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2903, 5203, 0));
				return true;
			}

			if (cmd[0].equalsIgnoreCase("cw") || cmd[0].equalsIgnoreCase("clanwars") || cmd[0].equalsIgnoreCase("pk")
					|| cmd[0].equalsIgnoreCase("ffa")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2991, 9679, 0));
				return true;
			}

			if (cmd[0].equalsIgnoreCase("chill")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4382, 5917, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("dicezone") || cmd[0].equalsIgnoreCase("dice")) {
				if (!player.canSpawn()) {
					player.getPackets().sendGameMessage("You can't teleport while you're in this area.");
					return true;
				}
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2196, 4961, 0));
				return true;
			}
			if (cmd[0].equalsIgnoreCase("100mticket") || cmd[0].equalsIgnoreCase("100m")) {
				int tickets = 0;
				int coins = 0;
				if (player.getInventory().containsItem(995, 100000000)) {
					if (player.getTrade().isTrading()) {
						player.sm("Please finish trading before converting your coins.");
						return true;
					}
					if (player.getInventory().hasFreeSlots() || player.getInventory().containsItem(2996, 1)) {
						tickets = (player.getInventory().getItems().getNumberOf(995)) / 100000000;
						coins = tickets * 100000000;
						player.getInventory().deleteItem(995, coins);
						player.getInventory().addItem(2996, tickets);
						player.sm(Utils.formatNumber(coins) + " coins have been exchanged for " + tickets
								+ " 100M Tickets.");
					} else {
						player.sm("You don't have enough inventory space for this command!");
						return true;
					}
				} else
					player.getPackets().sendGameMessage("<col=FFFF00>Tickets are 100m each.</col>");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("upgrade1") || cmd[0].equalsIgnoreCase("upgrade2")
					|| cmd[0].equalsIgnoreCase("upgrade3") || cmd[0].equalsIgnoreCase("upgrade4")
					|| cmd[0].equalsIgnoreCase("upgrade5") || cmd[0].equalsIgnoreCase("upgrade6")
					|| cmd[0].equalsIgnoreCase("upgrade7")) {
				player.sm("This command was removed. Please right click your whip to upgrade it.");
				return true;
			}
			if (cmd[0].equalsIgnoreCase("staff1") || cmd[0].equalsIgnoreCase("staff2")
					|| cmd[0].equalsIgnoreCase("staff3")) {
				player.sm("This command was removed. Please right click your staff to upgrade it.");
				return true;
			}

			if (cmd[0].equals("hideyell")) {
				player.setYellOff(!player.isYellOff());
				player.getPackets()
						.sendGameMessage("You have turned " + (player.isYellOff() ? "off" : "on") + " yell.");
				return true;
			}

			if (cmd[0].equals("changepass")) {
				String message1 = "";
				for (int i = 1; i < cmd.length; i++)
					message1 += cmd[i] + ((i == cmd.length - 1) ? "" : " ");
				if (message1.length() > 15 || message1.length() < 5) {
					player.getPackets().sendGameMessage("You cannot set your password to over 15 chars.");
					return true;
				}
				player.setPassword(Encrypt.encryptSHA1(cmd[1]));
				player.getPackets().sendGameMessage("You changed your password! Your password is " + cmd[1] + ".");
				return true;
			}

			if (cmd[0].equals("forums")) {
				player.getPackets().sendOpenURL(Settings.FORUMS_LINK);
				return true;
			}
			if (cmd[0].equals("commands")) {
				player.sm("This command was removed. Please check ex tab for commands.");
				return true;

			}
			player.sm("The command " + cmd[0] + " does not exist.");
		}
		return false;
	}
}
