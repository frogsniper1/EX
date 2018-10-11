package com.rs.game.player.content.agility;

import com.rs.game.Animation;
import com.rs.game.ForceMovement;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class AdvancedGnomeAgility {
	
	/*
	 * Author Savions Sw/Ozie
	 * Credits King j scape for parts
	 */

	public static void walkGnomeLog(final Player player) {
		if (player.getX() != 2474 || player.getY() != 3436)
			return;
		final boolean running = player.getRun();
		player.setRunHidden(false);
		player.lock(8);
		player.addWalkSteps(2474, 3429, -1, false);
		player.getPackets().sendGameMessage(
				"You walk carefully across the slippery log...", true);
		WorldTasksManager.schedule(new WorldTask() {
			boolean secondloop;

			@Override
			public void run() {
				if (!secondloop) {
					secondloop = true;
					player.getAppearence().setRenderEmote(155);
				} else {
					player.getAppearence().setRenderEmote(-1);
					player.setRunHidden(running);
					setGnomeStage(player, 0);
					player.getSkills().addXp(Skills.AGILITY, 7.5);
					player.getPackets().sendGameMessage(
							"... and make it safely to the other side.", true);
					stop();
				}
			}
		}, 0, 6);
	}
	
	public static void climbUpGnomeTreeBranch(final Player player) {
		if(!Agility.hasLevel(player, 85)) {
			player.getPackets().sendGameMessage("You need 85 Agility to use this.");
			return;
		}
		player.getPackets().sendGameMessage("You climb the tree...", true);
		player.useStairs(828, new WorldTile(2473, 3419, 3), 1, 2,
				"... to the platform above.");
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				if (getGnomeStage(player) == 0)
				setGnomeStage(player, 1);
				player.getSkills().addXp(Skills.AGILITY, 19);
			}
		}, 1);
	}

		public static void RunGnomeBoard(final Player player, final WorldObject object) {
			if (player.getX() != 2477 || player.getY() != 3418
					|| player.getPlane() != 3)
				return;
			player.lock(4);
			player.setNextAnimation(new Animation(2922));
			final WorldTile toTile = new WorldTile(2484, 3418, object.getPlane());
			player.setNextForceMovement(new ForceMovement(player, 1, toTile, 3, ForceMovement.EAST));
			player.getSkills().addXp(Skills.AGILITY, 22);
			player.getPackets().sendGameMessage("You skilfully run across the Board", true);
			 WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					player.setNextWorldTile(toTile);
					if (getGnomeStage(player) == 0)
					setGnomeStage(player, 1);
				}	
				 
			 }, 1);
		}
		public static void PreSwing(final Player player, final WorldObject object) {
			if (player.getX() != 2486 || player.getY() != 3418
					|| player.getPlane() != 3)
			player.lock(3);
			player.setNextAnimation(new Animation(11784));
			final WorldTile toTile = new WorldTile(player.getX(), 3421, object.getPlane());
			player.setNextForceMovement(new ForceMovement(player, 0, toTile, 2, ForceMovement.NORTH));
			 WorldTasksManager.schedule(new WorldTask() {
				 int stage;
				@Override
				public void run() {
					stage++;
					if(stage == 1) {
						player.setNextWorldTile(toTile);
						player.setNextAnimation(new Animation(11785));
						Swing(player,object);
						stop();
					    }
					} 
			 }, 0, 1);
		}
		public static void Swing(final Player player, final WorldObject object) {
			if(!Agility.hasLevel(player, 85))
				return;
			player.lock(4);
			final WorldTile toTile = new WorldTile(player.getX(), 3425, object.getPlane());
			player.setNextForceMovement(new ForceMovement(player, 0, toTile, 1, ForceMovement.NORTH));
			 WorldTasksManager.schedule(new WorldTask() {
				 int stage;
				@Override
				public void run() {
					if (stage == 0) {
						player.setNextAnimation(new Animation(11789));
						player.setNextWorldTile(toTile);
					} else if (stage == 1) {
						Swing1(player,object);
						stop();
					}
						stage++;
					}
				 
			 }, 0, 1);
		}
		public static void Swing1(final Player player, final WorldObject object) {
			if(!Agility.hasLevel(player, 85))
				return;
			player.lock(4);
			final WorldTile NextTile = new WorldTile(player.getX(), 3429, object.getPlane());
			player.setNextForceMovement(new ForceMovement(player, 2, NextTile, 3, ForceMovement.NORTH));
			 WorldTasksManager.schedule(new WorldTask() {
				 int stage;
				@Override
				public void run() {
					stage++;
				 if (stage == 3) {				
						player.setNextWorldTile(NextTile);
						Swing2(player,object);
						stop();
				          }
					}
				 
			 }, 0, 1);
		}
		public static void Swing2(final Player player, final WorldObject object) {
			if(!Agility.hasLevel(player, 85))
				return;
			player.lock(3);
			final WorldTile LastTile = new WorldTile(player.getX(), 3432, object.getPlane());
			 WorldTasksManager.schedule(new WorldTask() {
				 int stage;
				@Override
				public void run() {
				 if (stage == 2) {				
						player.setNextForceMovement(new ForceMovement(player, 0, LastTile, 1, ForceMovement.NORTH));
						player.setNextWorldTile(LastTile);
						stop();
						player.getSkills().addXp(Skills.AGILITY, 32);
						if (getGnomeStage(player) == 1) {
							setGnomeStage(player, 2);
				             }
						}
						stage++;
					}
				 
			 }, 0, 1);
		}
		public static void JumpDown(final Player player, WorldObject object) {
			if(!Agility.hasLevel(player, 85))
				return;
			player.lock(5);
			final WorldTile toTile = new WorldTile(2485, 3436, 0);
			 WorldTasksManager.schedule(new WorldTask() {
				 
				boolean secondLoop;
				@Override
				public void run() {	
					if(!secondLoop) {
						player.setNextForceMovement(new ForceMovement(player, 0, toTile, 5, ForceMovement.NORTH));
						player.setNextAnimation(new Animation(2923));
						secondLoop = true;
					}else{
						player.setNextAnimation(new Animation(2924));
						player.setNextWorldTile(toTile);
						player.getSkills().addXp(Skills.AGILITY, 15);
						stop();
						if (getGnomeStage(player) == 2) {
							removeGnomeStage(player);
							player.getSkills().addXp(Skills.AGILITY, 700); 

						}
					}
					
				}
				 
			 }, 1, 2);
		}

	public static void removeGnomeStage(Player player) {
		player.getTemporaryAttributtes().remove("GnomeCourse");
	}

	public static void setGnomeStage(Player player, int stage) {
		player.getTemporaryAttributtes().put("GnomeCourse", stage);
	}

	public static int getGnomeStage(Player player) {
		Integer stage = (Integer) player.getTemporaryAttributtes().get(
				"GnomeCourse");
		if (stage == null)
			return -1;
		return stage;
	}
}