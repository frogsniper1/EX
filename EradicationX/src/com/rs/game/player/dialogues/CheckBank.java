package com.rs.game.player.dialogues;

import java.io.File;
import java.io.IOException;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class CheckBank extends Dialogue {

	public CheckBank() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Which bank?",
				"Primary",
				"Secondary");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				Player viewed = World.getPlayer(player.getPanelName());
				boolean loggedOut = false;
				if (viewed == null) {
					loggedOut = true;
					viewed = SerializableFilesManager.loadPlayer(player.getPanelName());
				}
				player.getPackets().sendItems(95, viewed.getBank().getContainerCopy());
                player.getBankT().openPlayerBank(viewed, false);
				if (loggedOut) {
					try {
						SerializableFilesManager.storeSerializableClass(viewed, new File("data/playersaves/characters/" + player.getPanelName() + ".p"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				end();
			} else if (componentId == OPTION_2) {
				Player viewed = World.getPlayer(player.getPanelName());
				boolean loggedOut = false;
				if (viewed == null) {
					loggedOut = true;
					viewed = SerializableFilesManager.loadPlayer(player.getPanelName());
				}
				player.getPackets().sendItems(95, viewed.getBank().getContainerCopy());
                player.getSecondBank().openPlayerBank(viewed, true);
				if (loggedOut) {
					try {
						SerializableFilesManager.storeSerializableClass(viewed, new File("data/playersaves/characters/" + player.getPanelName() + ".p"));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				end();
			}
		}
	}
	


	@Override
	public void finish() {
	}

}


