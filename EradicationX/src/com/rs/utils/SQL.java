package com.rs.utils;

import java.sql.*;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class SQL {

	public static Connection con = null;
	public static Statement stmt;
	public static boolean connectionMade;

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/highscores", "root", "lolwut");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stmt.executeQuery(s);
				return rs;
			} else {
				stmt.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			destroyConnection();
			createConnection();
			e.printStackTrace();
		}
		return null;
	}

	public static void destroyConnection() {
		try {
			stmt.close();
			con.close();
			connectionMade = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean saveHighScore(Player player) {// saves hiscores
		try {
			createConnection();// creates connection
			Skills skills = player.getSkills();// gets skills
			int[] overall = getOverall(player);// just a int
			query("DELETE FROM `skills` WHERE playerName = '"
					+ player.getUsername() + "';");// read those codes, they are
													// explained by itself
			query("DELETE FROM `skillsoverall` WHERE playerName = '"
					+ player.getUsername() + "';");
			query("INSERT INTO `skills` (`playerName`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangexp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`, `Hunterlvl`, `Hunterxp`, `Constructionlvl`, `Constructionxp`, `Summoninglvl`, `Summoningxp`, `Dungeoneeringlvl`, `Dungeoneeringxp`) VALUES ('"
					+ player.getUsername()
					+ "',"
					+ skills.getLevel(0)
					+ ","
					+ skills.getXp(0)
					+ ","
					+ skills.getLevel(1)
					+ ","
					+ skills.getXp(1)
					+ ","
					+ skills.getLevel(2)
					+ ","
					+ skills.getXp(2)
					+ ","
					+ skills.getLevel(3)
					+ ","
					+ skills.getXp(3)
					+ ","
					+ skills.getLevel(4)
					+ ","
					+ skills.getXp(4)
					+ ","
					+ skills.getLevel(5)
					+ ","
					+ skills.getXp(5)
					+ ","
					+ skills.getLevel(6)
					+ ","
					+ skills.getXp(6)
					+ ","
					+ skills.getLevel(7)
					+ ","
					+ skills.getXp(7)
					+ ","
					+ skills.getLevel(8)
					+ ","
					+ skills.getXp(8)
					+ ","
					+ skills.getLevel(9)
					+ ","
					+ skills.getXp(9)
					+ ","
					+ skills.getLevel(10)
					+ ","
					+ skills.getXp(10)
					+ ","
					+ skills.getLevel(11)
					+ ","
					+ skills.getXp(11)
					+ ","
					+ skills.getLevel(12)
					+ ","
					+ skills.getXp(12)
					+ ","
					+ skills.getLevel(13)
					+ ","
					+ skills.getXp(13)
					+ ","
					+ skills.getLevel(14)
					+ ","
					+ skills.getXp(14)
					+ ","
					+ skills.getLevel(15)
					+ ","
					+ skills.getXp(15)
					+ ","
					+ skills.getLevel(16)
					+ ","
					+ skills.getXp(16)
					+ ","
					+ skills.getLevel(17)
					+ ","
					+ skills.getXp(17)
					+ ","
					+ skills.getLevel(18)
					+ ","
					+ skills.getXp(18)
					+ ","
					+ skills.getLevel(19)
					+ ","
					+ skills.getXp(19)
					+ ","
					+ skills.getLevel(20)
					+ ","
					+ skills.getXp(20)
					+ ","
					+ skills.getLevel(21)
					+ ","
					+ skills.getXp(21)
					+ ","
					+ skills.getLevel(22)
					+ ","
					+ skills.getXp(22)
					+ ","
					+ skills.getLevel(23)
					+ ","
					+ skills.getXp(23)
					+ ","
					+ skills.getLevel(24)
					+ ","
					+ skills.getXp(24) + ");");
			query("INSERT INTO `skillsoverall` (`playerName`,`lvl`,`xp`) VALUES ('"
					+ player.getUsername()
					+ "',"
					+ overall[0]
					+ ","
					+ overall[1] + ");");
			Logger.log("Hiscores", "Hiscores saved for " + player.getUsername()
					+ ".");
			// query("INSERT INTO `skillsoverall` (`playerName`,`lvl`,`xp`) VALUES ('"+player.getDisplayName()+"',"+(skills.getLevelForXp(0)
			// + skills.getLevelForXp(1) + skills.getLevelForXp(2) +
			// skills.getLevelForXp(3) + skills.getLevelForXp(4) +
			// skills.getLevelForXp(5) + skills.getLevelForXp(6) +
			// skills.getLevelForXp(7) + skills.getLevelForXp(8) +
			// skills.getLevelForXp(9) + skills.getLevelForXp(10) +
			// skills.getLevelForXp(11) + skills.getLevelForXp(12) +
			// skills.getLevelForXp(13) + skills.getLevelForXp(14) +
			// skills.getLevelForXp(15) + skills.getLevelForXp(16) +
			// skills.getLevelForXp(17) + skills.getLevelForXp(18) +
			// skills.getLevelForXp(19) +
			// skills.getLevelForXp(20))+","+((skills.getXp(0)) +
			// (skills.getXp(1)) + (skills.getXp(2)) + (skills.getXp(3)) +
			// (skills.getXp(4)) + (skills.getXp(5)) + (skills.getXp(6)) +
			// (skills.getXp(7)) + (skills.getXp(8)) + (skills.getXp(9)) +
			// (skills.getXp(10)) + (skills.getXp(11)) + (skills.getXp(12)) +
			// (skills.getXp(13)) + (skills.getXp(14)) + (skills.getXp(15)) +
			// (skills.getXp(16)) + (skills.getXp(17)) + (skills.getXp(18)) +
			// (skills.getXp(19)) + (skills.getXp(20)))+");");
			destroyConnection();
		} catch (Exception e) {
			Logger.log("Hiscores", "Error, could not save highscores for "
					+ player.getUsername() + ".");// couldnt save the player on
													// hiscores, it tells u it
													// didnt on the run.bat
			return false;
		}
		return true;
	}

	public static int[] getOverall(Player player) {
		int totalLevel = 0;
		int totalXp = 0;
		for (int i = 0; i < 24; i++) {
			totalLevel += player.getSkills().getLevelForXp(i);
		}
		for (int i = 0; i < 24; i++) {
			totalXp += player.getSkills().getXp(i);
		}
		return new int[] { totalLevel, totalXp };
	}

}