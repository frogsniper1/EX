package com.rs.game.npc.instances;



import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.instances.HardModeTrioInstance;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

@SuppressWarnings("serial")
public class InstanceHardModeTrio extends NPC {

	private Player player;
	public InstanceHardModeTrio(int id, WorldTile tile, Player player) {
		super(id, tile, -1, true, true);	
		setCapDamage(8500);
		setForceAgressive(false);
		this.player = player;
		setForceTargetDistance(100);
		setForceMultiArea(true);
		if (player == null)
			return;
		if (player.getControlerManager().getControler() == null)
			return;
		if (player.getControlerManager().getControler().toString().toLowerCase().contains("hardmode")) {
			HardModeTrioInstance hm = (HardModeTrioInstance) player.getControlerManager().getControler();
			hm.health = 45000;
		}
	}	
	
	@Override
	public void applyHit(Hit hit) {
		super.applyHit(hit);
		if (player == null) 
			return;
		if (player.getControlerManager().getControler() == null)
			return;
		if (player.getControlerManager().getControler().toString().toLowerCase().contains("hardmode")) {
			HardModeTrioInstance hm = (HardModeTrioInstance) player.getControlerManager().getControler();
			hm.health -= hit.getDamage();
		}
		
	}
	
	@Override
	public void setHitpoints(int hitpoints) {
		if (player == null) {
			super.setHitpoints(hitpoints);
			return;
		}
		if (player.getControlerManager().getControler() == null) {
			super.setHitpoints(hitpoints);
			return;
		}
		if (player.getControlerManager().getControler().toString().toLowerCase().contains("hardmode")) {
			return;
		}
	}

	@Override
	public void processNPC() {
		if (combat.target != null) {
		if (!(combat.target instanceof Player)) {
			this.setNextForceTalk(new ForceTalk("Don't put your familiars on me."));
			combat.target.sendDeath(this);
		}
		}
		super.processNPC();
	}
	
	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		final WorldTile tile = this;
		super.sendDeath(source);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 0) {
				} else if (loop >= defs.getDeathDelay()) {
					reset();
					if (player != null) {
						if (player.getTimer() != 0 || player.getTimer() > 0) {			
							new InstanceHardModeTrio(id, tile, player);	
						}
						} else {
							new InstanceHardModeTrio(id, tile, player);	
						}
					if (World.canMoveNPC(getPlane(), tile.getX() + 1,
							tile.getY(), 1))
						tile.moveLocation(1, 0, 0);
					else if (World.canMoveNPC(getPlane(), tile.getX() - 1,
							tile.getY(), 1))
						tile.moveLocation(-1, 0, 0);
					else if (World.canMoveNPC(getPlane(), tile.getX(),
							tile.getY() - 1, 1))
						tile.moveLocation(0, -1, 0);
					else if (World.canMoveNPC(getPlane(), tile.getX(),
							tile.getY() + 1, 1))
						tile.moveLocation(0, 1, 0);
					finish();
					stop();
				}
				loop++;
			}
		}, 0, player.getSpawnRate());		
	}



}
