package com.rs.game.player.dialogues;


// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class XPBook extends Dialogue {

	public XPBook() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Select a Option", "Attack experience",
				"Strength Experience", "Defence Experience",
				"Ranged Experience", "Magic Experience");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getSkills().addXp(0, 250);
				player.sm("You have chosen attack experience.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(22340, 1);
				player.getInventory().refresh();
			} else if (componentId == OPTION_2) {
				player.getSkills().addXp(2, 250);
				player.sm("You have chosen strength experience.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(22340, 1);
				player.getInventory().refresh();
			} else if (componentId == OPTION_3) {
				player.getSkills().addXp(1, 250);
				player.sm("You have chosen defence experience.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(22340, 1);
				player.getInventory().refresh();

			} else if (componentId == OPTION_4) {
				player.getSkills().addXp(4, 250);
				player.sm("You have chosen ranged experience.");
				player.getInterfaceManager().closeChatBoxInterface();
				player.getInventory().deleteItem(22340, 1);
				player.getInventory().refresh();

			} else if (componentId == OPTION_5) {
				player.getSkills().addXp(6, 250);
				player.getInventory().deleteItem(22340, 1);
				player.getInventory().refresh();
				player.sm("You have chosen magic experience.");
				player.getInterfaceManager().closeChatBoxInterface();
			}
		}

	}

	/*private void teleportPlayer(int x, int y, int z) {
		player.setNextGraphics(new Graphics(111));
		player.setNextWorldTile(new WorldTile(x, y, z));
		player.stopAll();

	}*/

	@Override
	public void finish() {
	}

}
