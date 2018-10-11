package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.Magic;

public class BossTeleports extends Dialogue {

	@Override
	public void start() {
		stage = 1;
		if (stage == 1) {
			sendOptionsDialogue("BossTeles", "<shad=00FF00>Bandos",
					"<shad=FD3EDA>Armadyl", "<shad=05F7FF>Saradomin",
					"<shad=FFCD05>Zamorak", "More Options...");
			stage = 1;
		}
	}

	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2870,
						5363, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2828,
						5302, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_3) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2923,
						5250, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2926,
						5325, 0));
				player.getControlerManager().startControler("GodWars");
				end();
			}
			if (componentId == OPTION_5) {
				stage = 2;
				sendOptionsDialogue("BossTeles",
						"<shad=00FF00>King Black Dragon",
						"<shad=FD3EDA>Tormented Demons",
						"<shad=05F7FF>Queen Black Dragon",
						"<shad=FFCD05>Corporeal Beast", "more...");
			}
		} else if (stage == 2) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3067,
						10254, 0));
				player.getPackets()
						.sendGameMessage(
								"Careful, make sure to have an anti-dragon shield, you're going to need it!");
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2562,
						5739, 0));
				end();
			}
			if (componentId == OPTION_3) {
				player.getControlerManager().startControler(
						"QueenBlackDragonControler");
				end();
			}
			if (componentId == OPTION_4) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2966,
						4383, 2));
				end();
			}
			if (componentId == OPTION_5) {
				stage = 3;
				sendOptionsDialogue("BossTeles", "<shad=00FF00>Nex",
						"<shad=FFCD05>Glacors", "Back to 1st page..");
			}
		} else if (stage == 3) {
			if (componentId == OPTION_1) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(2904,
						5203, 0));
				end();
			}
			if (componentId == OPTION_2) {
				Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(4189,
						5736, 0));
				end();
			}
			if (componentId == OPTION_3) {
				stage = 1;
				sendOptionsDialogue("BossTeles", "<shad=00FF00>Bandos",
						"<shad=FD3EDA>Armadyl", "<shad=05F7FF>Saradomin",
						"<shad=FFCD05>Zamorak", "More Options...");
			}
		}
	}

	@Override
	public void finish() {

	}

}