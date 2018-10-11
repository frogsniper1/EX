package com.rs.game.player.controlers;

import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.PlayerDesign;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;

public class StartTutorial extends Controler {

	private static final int QUEST_GUIDE_NPC = 13955;

	@Override
	public void start() {
		refreshStage();
	}

	public NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	@Override
	public void process() {
	}

	@Override
	public boolean processObjectClick1(WorldObject object) {
		int id = object.getId();
		if ((id == 47120 && getStage() == 1)
				|| (Wilderness.isDitch(id) && getStage() == 2))
			return true;
		return false;
	}

	@Override
	public boolean processObjectClick2(WorldObject object) {
		return false;
	}

	@Override
	public boolean processObjectClick3(WorldObject object) {
		return false;
	}

	public void refreshStage() {
		int stage = getStage();
		if (stage == 0) {
			NPC guide = findNPC(QUEST_GUIDE_NPC);
			if (guide != null)
				player.getHintIconsManager().addHintIcon(guide, 0, -1, false);
		} else if (stage == 1) {
			PlayerDesign.open(player);
			updateProgress();
		} else if (stage == 2) {
			player.getHintIconsManager().addHintIcon(3092, 3521, 0, 0, 0, 0,
					-1, false);
		}
		sendInterfaces();
	}

	@Override
	public void sendInterfaces() {
		int stage = getStage();
		player.getInterfaceManager().replaceRealChatBoxInterface(372);
		if (stage == 0) {
			player.getPackets().sendIComponentText(372, 0, "Hello, " + player.getDisplayName() + "!");
			player.getPackets()
					.sendIComponentText(372, 1,
							"Welcome to EradicationX!");
			player.getPackets().sendIComponentText(372, 3,
					"You should start off by making sure your client isn't on software mode.");
			player.getPackets()
					.sendIComponentText(372, 5,
							"When you're ready, go ahead and talk to the NPC!");
			player.getPackets().sendIComponentText(372, 6, "");
			player.getPackets().sendIComponentText(372, 2, "");
			player.getPackets().sendIComponentText(372, 4, "");
		} else if (stage == 1) {
			player.getPackets().sendIComponentText(372, 0, "Getting Started");
			player.getPackets().sendIComponentText(372, 1,
					"Talk to the Eradicator.");
			player.getPackets().sendIComponentText(372, 2,
					"");
			player.getPackets().sendIComponentText(372, 3, "");
			player.getPackets().sendIComponentText(372, 4, "");
			player.getPackets().sendIComponentText(372, 5, "");
			player.getPackets().sendIComponentText(372, 6, "");
		} else if (stage == 2) {
			player.getPackets().sendIComponentText(372, 0, "Getting Started");
			player.getPackets().sendIComponentText(372, 1,
					"");
			player.getPackets().sendIComponentText(372, 2,
					"");
			player.getPackets().sendIComponentText(372, 3, "");
			player.getPackets().sendIComponentText(372, 4, "");
			player.getPackets().sendIComponentText(372, 5, "");
			player.getPackets().sendIComponentText(372, 6, "");
		}
	}

	public void updateProgress() {
		setStage(getStage() + 1);
		if (getStage() == 2) {
			player.getDialogueManager().startDialogue("QuestGuide",
					QUEST_GUIDE_NPC, this);
		}
		refreshStage();
	}

	@Override
	public boolean processNPCClick1(NPC npc) {
		if (npc.getId() == QUEST_GUIDE_NPC) {
			player.getDialogueManager().startDialogue("QuestGuide",
					QUEST_GUIDE_NPC, this);
		}
		return false;
	}

	public void setStage(int stage) {
		getArguments()[0] = stage;
	}

	public int getStage() {
		if (getArguments() == null)
			setArguments(new Object[] { 0 }); // index 0 = stage
		return (Integer) getArguments()[0];
	}

	/*
	 * return remove controler
	 */
	@Override
	public boolean login() {
		start();
		return false;
	}

	/*
	 * return remove controler
	 */
	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public boolean keepCombating(Entity target) {
		return false;
	}

	@Override
	public boolean canAttack(Entity target) {
		return false;
	}

	@Override
	public boolean canHit(Entity target) {
		return false;
	}

	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		return false;
	}

	@Override
	public void forceClose() {
		player.getHintIconsManager().removeUnsavedHintIcon();
		player.getPackets().sendOpenURL("http://eradicationx.com/forums/index.php?/topic/67-eradicationx-rules/#comment-164");
		player.getMusicsManager().reset();
		player.getInventory().addItem(15246, 1);
		player.getToolbelt().addItem(new Item(1351));
		player.getToolbelt().addItem(new Item(590));
		player.getToolbelt().addItem(new Item(1755));
		player.getToolbelt().addItem(new Item(8794));
		player.getToolbelt().addItem(new Item(307));
		player.getToolbelt().addItem(new Item(952));
		player.getToolbelt().addItem(new Item(2347));
		player.getToolbelt().addItem(new Item(303));
		player.getToolbelt().addItem(new Item(311));
		player.getToolbelt().addItem(new Item(301));
		player.getToolbelt().addItem(new Item(1265));
		player.getToolbelt().addItem(new Item(5343));
		player.getToolbelt().addItem(new Item(305));
		player.getToolbelt().addItem(new Item(5329));
		player.getToolbelt().addItem(new Item(7409));
		player.getToolbelt().addItem(new Item(946));
		player.getToolbelt().addItem(new Item(1733));
		player.sm("Have fun playing EradicationX! If you ever need help, feel free to ask our helpful community! Whenever you're ready, open up your starter pack.");
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.getInterfaceManager().sendInterfaces();
				player.getInterfaceManager()
						.closeReplacedRealChatBoxInterface();
			}
		});
		removeControler();	
		player.starter = 1;
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3968, 4823, 1));
	}
}
