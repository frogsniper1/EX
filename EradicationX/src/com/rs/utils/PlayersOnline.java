package com.rs.utils;

import java.sql.*;
import java.util.TimerTask;

import com.rs.cores.CoresManager;
import com.rs.game.World;

public class PlayersOnline {

	public static Connection con = null;
	public static Statement stm;
	public static int amount = 0;

	public static void createCon() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://ftp.eradicationx.com/PlayerCount", "pcount", "BO]QVGR=54w=");
			stm = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stm.executeQuery(s);
				return rs;
			} else {
				stm.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			System.out.println("MySQL Error:"+s);
			e.printStackTrace();
		}
		return null;
	}

	public static void destroyCon() {
		try {
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public static void online() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (amount != World.getPlayers().size() && (World.getPlayers().size() > 12)) {
					createCon();	
					query("DELETE FROM `online` WHERE id = 1;");
					query("INSERT INTO `online` (id, currentlyonline) VALUES('1','"+ World.getPlayers().size() +"');");
					destroyCon();
					}
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
		}, 0, 10);
	}
	
	
}