package com.rs.game.player.content;


import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.minigames.FightPits;
import com.rs.game.player.Player;
import com.rs.utils.IPBanL;
import com.rs.utils.PkRank;
import com.rs.utils.Utils;


public class Rotten_Commands {


    final static int MODERN = 0, ANCIENT = 1, LUNAR = 2;


    public static void Curse(Player p) {
        if (!p.getPrayer().isAncientCurses()) {
            p.getPrayer().setPrayerBook(true);
            p.out("Curse Activated");
        } else {
            p.getPrayer().setPrayerBook(false);
            p.out("Curse Desactivated");
        }
    }


    public static void God_Mode(Player p) {
        p.setHitpoints(Short.MAX_VALUE);
        p.getEquipment().setEquipmentHpIncrease(Short.MAX_VALUE - 990);
        for (int i = 0; i < 10; i++)
            p.getCombatDefinitions().getBonuses()[i] = 5000;
        for (int i = 14; i < p.getCombatDefinitions().getBonuses().length; i++)
            p.getCombatDefinitions().getBonuses()[i] = 5000;
    }


    public static void Kill_Me(Player p) {
        p.applyHit(new Hit(p, 2000, HitLook.REGULAR_DAMAGE));
    }


    public static void Hit_test(Player p) {
        for (int i = 0; i < 5; i++)
            p.applyHit(new Hit(p, Utils.getRandom(3), HitLook.HEALED_DAMAGE));
    }


    public static void master(Player p) {
        for (int skill = 0; skill < 25; skill++)
            p.getSkills().addXp(skill, 150000000);
    }
	

    public static void Count_anim(Player p) {
        p.out(Utils.getAnimationDefinitionsSize() + " anims.");
    }


    public static void Open_Bank(Player p) {
        p.stopAll();
        p.getBankT().openBank();
    }


    public static void Kill_All(Player p) {
        for (Player loop : World.getPlayers()) {
            loop.applyHit(new Hit(loop, p.getHitpoints(),
                    HitLook.REGULAR_DAMAGE));
            loop.stopAll();
        }
    }


    public static void Reset_fight_pit(Player p) {
        FightPits.endGame();
        p.out("Fight pits restarted!");
    }


    public static void Mod_meeting(Player p) {
        for (Player staff : World.getPlayers()) {
            if (staff.getRights() != 1)
                continue;
            staff.setNextWorldTile(p);
            staff.out("You been teleported for a staff meeting by "
                    + p.getDisplayName());
        }
    }


    public static void Reload_files() {
        IPBanL.init();
        PkRank.init();
    }


    public static void Shutdown() {
        int delay = 200;
        World.safeShutdown(false, delay);
    }


    public static void Staff_meeting(Player p) {
        for (Player staff : World.getPlayers()) {
            if (staff.getRights() == 0)
                continue;
            staff.setNextWorldTile(new WorldTile(2847, 5151, 0));
            staff.getPackets().sendGameMessage(
                    "You been teleported for a staff meeting by "
                            + p.getDisplayName());
        }
    }


    public static void Restore_special(Player p) {
        p.getCombatDefinitions().resetSpecialAttack();
    }


    public static void Reset_All(Player p) {
        for (int skill = 0; skill < 25; skill++)
            p.getSkills().setXp(skill, 0);
        p.getSkills().init();
    }


    public static void Unmute_All(Player p) {
        for (Player targets : World.getPlayers()) {
            if (p == null)
                continue;
            targets.setMuted(0);
            p.out("All muted login player where unmuted!");
        }
    }


    public static void Mage_Book(Player p, int Magic) {
        if (Magic == MODERN) {
            p.getCombatDefinitions().setSpellBook(MODERN);
            p.out("Switched to Modern Magic");
        }
        if (Magic == ANCIENT) {
            p.getCombatDefinitions().setSpellBook(ANCIENT);
            p.out("Switched to Ancient Magicks");
        }
        if (Magic == LUNAR) {
            p.getCombatDefinitions().setSpellBook(LUNAR);
            p.out("Switched to Lunar Spell");
        }
    }
}