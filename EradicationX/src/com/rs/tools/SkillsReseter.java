package com.rs.tools;

import java.io.File;
import java.io.IOException;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;

public class SkillsReseter {

	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		File[] chars = new File("data/playersaves/reset").listFiles();
		for (File acc : chars) {
			try {
				Player player = (Player) SerializableFilesManager
						.loadSerializedFile(acc);
				for (int i = 0; i < 25000; i++) {
					player.getBank().removeItem(i);
				}
				for (int i = 0; i < 25000; i++) {
					player.getInventory().getItems()
							.removeAll(new Item(i, Integer.MAX_VALUE));
				}
				for (int i = 0; i < 25000; i++) {
					player.getEquipment().getItems()
							.removeAll(new Item(i, Integer.MAX_VALUE));
				}
				player.getPouch().setAmount(0);
				player.reseted = 1;
				// player.getInventory().addItem(995, 20000000);
				// player.getBank().removeItem(0);
				SerializableFilesManager.storeSerializableClass(player, acc);
			} catch (Throwable e) {
				e.printStackTrace();
				System.out.println("failed: " + acc.getName());
			}
		}
		System.out.println("Done.");
	}
}
