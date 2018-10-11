package com.rs.game.player;

import com.rs.cache.loaders.ClientScriptMap;

public class TitleHandler {

	public enum Titles {
		JUNIOR_CADET(1, "Junior Cadet ", "AA44AA", null, false),
		SERJEANT(2, "Serjeant ", "AA44AA", null, false),
		COMMANDER(3, "Commander ", "AA44AA", null, false),
		WAR_CHIEF(4, "War-chief ", "AA44AA", null, false),
		SIR(5, "Sir ", "C86400", null, false),
		LORD(6, "Lord ", "C86400", null, false),
		DUDERINO(7, "Duderino ", "C86400", null, false),
		LIONHEART(8, "Lionheart ", "C86400", null, false),
		HELLRAISER(9, "Hellraiser ", "C86400", null, false),
		CRUSADER(10, "Crusader ", "C86400", null, false),
		DESPERADO(11, "Desperado ", "C86400", null, false),
		BARON(12, "Baron ", "C86400", null, false),
		COUNT(13, "Count ", "C86400", null, false),
		OVERLORD(14, "Overlord ", "C86400", null, false),
		BANDITO(15, "Bandito ", "C86400", null, false),
		DUKE(16, "Duke ", "C86400", null, false),
		KING(17, "King ", "C86400", null, false),
		BIG_CHEESE_(18, "Big Cheese ", "C86400", null, false),
		BIGWIG_(19, "Bigwig ", "C86400", null, false),
		WUNDERKIND(20, "Wunderkind ", "C86400", null, false),
		VYRELING(21, "Vyreling ", "466AFA", null, false),
		VYRE_GRUNT(22, "Vyre Grunt ", "7D3FEC", null, false),
		VYREWATCH(23, "Vyrewatch ", "6C0B2B", null, false),
		VYRELORD(24, "Vyrelord ", "C12006", null, false),
		YT_HAAR(25, "Yt?Haar ", "C12006", null, false),
		EMPEROR(26, "Emperor ", "C86400", null, false),
		PRINCE(27, "Prince ", "C86400", null, false),
		WITCH_KING(28, "Witch king ", "C86400", null, false),
		ARCHON(29, "Archon ", "C86400", null, false),
		JUSTICIAR(30, "Justiciar ", "C86400", null, false),
		THE_AWESOME(31, "The Awesome ", "C86400", null, false),
		THE_MAGNIFICENT(32, " the magnificent", "C86400", null, true),
		THE_UNDEFEATED(33, " the undefeated", "C86400", null, true),
		THE_STRANGE(34, " the strange", "C86400", null, true),
		THE_DIVINE(35, " the divine", "C86400", null, true),
		THE_FALLEN(36, " the fallen", "C86400", null, true),
		THE_WARRIOR(37, " the warrior", "C86400", null, true),
		THE_REAL_(38, "The Real ", "C86400", null, false),
		COWARDLY_(39, "Cowardly ", "AA44AA", null, false),
		THE_REDUNDANT(40, " the Redundant", "AA44AA", null, true),
		EVERYONE_ATTACK(41, "Everyone attack ", "AA44AA", null, false),
		SMELLY(42, "Smelly ", "AA44AA", null, false),
		THE_IDIOT(43, " the Idiot", "AA44AA", null, true),
		SIR_LAME(44, "Sir Lame ", "AA44AA", null, false),
		THE_FLAMBOYANT(45, " the Flamboyant", "AA44AA", null, true),
		WEAKLING(46, "Weakling ", "AA44AA", null, false),
		WAS_PUNISHED(47, " was punished", "AA44AA", null, true),
		LOST(48, " lost", "AA44AA", null, true),
		YOU_FAIL(49, " ...you fail", "AA44AA", null, true),
		NO_MATES_(50, "No-mates ", "AA44AA", null, false),
		ATE_DIRT(51, " ate dirt", "AA44AA", null, true),
		DELUSIONAL_(52, "Delusional ", "AA44AA", null, false),
		THE_RESPAWNER(53, " the Respawner", "AA44AA", null, true),
		CUTIE_PIE_(54, "Cutie-pie ", "AA44AA", null, false),
		THE_FAIL_MAGNET(55, " the Fail Magnet", "AA44AA", null, true),
		WAS_TERMINATED(56, " was terminated", "AA44AA", null, true),
		LAZY(57, "Lazy ", "AA44AA", null, false),
		WHO(58, "? Who?", "AA44AA", null, true),
		
		// custom titles go below this
		// id, title, color, shadow, afterName
		ALPHA_DONOR(59, "Alpha Donator ", "FFD700", "AA3300", false),
		DEVELOPER(60, "Developer ", "008080", null, false);
		
		private int id;
		private String title, color, shade;
		private boolean after;
		
		Titles(int id, String title, String color, String shade, boolean after) {
			this.id = id;
			this.title = title;
			this.color = color;
			this.shade = shade;
			this.after = after;
		}
		
		public int getId() {
			return id;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getColor() {
			return color;
		}
		
		public String getShade() {
			return shade;
		}
		
		public boolean goesAfter() {
			return after;
		}
		
	}
	
	public static boolean goesAfterName(int titleId) {
		for (Titles t : Titles.values()) {
			if (t.getId() == titleId) {
				return t.goesAfter();
			}
		}
		return false;
	}
	
	public static Titles getTitle(int id) {
		for (Titles tl : Titles.values()) {
			if (tl.getId() == id) {
				return tl;
			}
		}
		return null;
	}
	
	public static String getTitleString(int titleId) {
		for (Titles t : Titles.values()) {
			if (t.getId() == titleId) {
				String color = t.getColor() == null ? "" : "<col="+t.getColor()+">";
				String shade = t.getShade() == null ? "" : "<shad="+t.getShade()+">";
				String pref1 = t.getColor() == null ? "" : "</col>";
				String pref2 = t.getShade() == null ? "" : "</shad>";
				String title = color + shade + t.getTitle() + pref1 + pref2;
				return title;
			}
		}
		return ClientScriptMap.getMap(1093).getStringValue(titleId);
	}
}
