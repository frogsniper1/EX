package com.rs.game.player.content;


import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;


public class EradicatorBones {


		public static void offerprayerGod(final Player player) {
			WorldTasksManager.schedule(new WorldTask() {
				public void run() {
						if (!player.getInventory().containsItem(15410, 1)) {
							player.sm("You have " + player.getDeposittedBones() + " Eradicator Bones inside the machine."); 
							stop();		
						} else if (player.getInventory().containsItem(15410, 1)) {
							player.setNextGraphics(new Graphics(28));
							player.setNextAnimation(new Animation(835));
							player.getInventory().deleteItem(15410, 1);
							player.addFreezeDelay(1000, true);
							player.setDeposittedBones(player.getDeposittedBones() + 1);
						}
				}
			}, 0, 1);
		}
	}


