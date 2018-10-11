package com.rs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.rs.game.player.Player;

public class VoteManager {

	public static Connection con = null;
	public static Statement stmt;
	public static boolean connectionMade;

	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String IP = "IP/Link Here";
			String DB = "DB Here";
			String User = "User Here";
			String Pass = "Pass Here";
			con = DriverManager.getConnection("jdbc:mysql://" + IP + "/" + DB,
					User, Pass);
			stmt = con.createStatement();
		} catch (Exception e) {
			Logger.log("VoteManager", "Connection to SQL database failed!");
			e.printStackTrace();
		}
	}

	public static boolean checkVote(Player p) {
		try {
			createConnection();
			// Statement stmt = con.createStatement();
			String playerName = p.getUsername();
			ResultSet rs = query("SELECT id,item_id, item_amount FROM `items` WHERE `username`= '"
					+ playerName + "' AND `claimed`= '0'");
			if (p.getInventory().getItems().freeSlots() > 0) {
				if (rs.next()) {
					int rowid = rs.getInt("id");
					int itemid = rs.getInt("item_id");
					int amount = rs.getInt("item_amount");
					query("UPDATE `items` SET `claimed` = 1 WHERE `username` = '"
							+ playerName + "' AND `id`='" + rowid + "'");
					p.getInventory().addItem(itemid, amount);
					p.getPackets().sendGameMessage(
							"Thank you for voting and enjoy your reward!");
				} else {
					p.getPackets().sendGameMessage("You havn't voted yet.");
				}
			} else {
				p.getPackets().sendGameMessage(
						"Please make space for your items.");
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return false;
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
			// destroyConnection();
		}
		return null;
	}

}