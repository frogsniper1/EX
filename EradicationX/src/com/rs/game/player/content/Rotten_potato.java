package com.rs.game.player.content;


import com.rs.game.player.Player;


public class Rotten_potato {


    public void Eat(Player p) {
        p.out("You can't eat the admin potato");
    }


    public void Heal(Player p) {
        p.heal(1500);
    }


    public void Cm_tool(Player p) {
        p.getDialogueManager().startDialogue("Potato");
    }


    public void Commands() {


    }
}