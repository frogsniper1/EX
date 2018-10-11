package com.rs.content.exchange;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rs.utils.SerializableFilesManager;
/**
 * Represents the Grand Exchange of the World.
 *
 * @author Jefferson
 *
 */
public class GrandExchange {
	private static final String FILE_LOCATION = "data/offers.ser";
	/**
	 * Offers Database Location.
	 */
	public static final File DATABASE_LOCATION = new File(FILE_LOCATION + ".ge");
	/**
	 * World offers.
	 */
	private static CopyOnWriteArrayList<ItemOffer> offers;
	/**
	 * @return the offers
	 */
	public static CopyOnWriteArrayList<ItemOffer> getOffers() {
		return offers;
	}
	/**
	 * Creates a new Instance in the JVM.
	 */
	@SuppressWarnings("unchecked")
	public static void init() {
		File file = new File(FILE_LOCATION);
		if (file.exists()) {
			try {
				offers = (CopyOnWriteArrayList<ItemOffer>) SerializableFilesManager.loadSerializedFile(file);
				System.out.println("Loaded " + offers.size() + " grand exchange item offers!");
				return;
			} catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
		}
		offers = new CopyOnWriteArrayList<>();
	}
	public static void save() {
		try {
			SerializableFilesManager.storeSerializableClass(offers, new File(FILE_LOCATION));
		} catch (IOException e) {
		}
	}
}
