package com.rs.game.player;

import java.io.Serializable;

import com.rs.game.item.Item;
import com.rs.game.item.ItemsContainer;
import com.rs.game.player.content.ItemConstants;

public class BankPreset implements Serializable {

	private static final long serialVersionUID = 5337960495718406631L;
	
	private ItemsContainer<Item> savedInventory;
	private ItemsContainer<Item> savedEquipment;
	private String presetName;
	private int quickSelect; 
	
	private transient Player player;
	
	public BankPreset(Player player) {
		presetName = "Empty preset";
		if (quickSelect != 0) {
			if (quickSelect == 1)
				player.quickselect1 = 0;
			else if (quickSelect == 2)
				player.quickselect2 = 0;
		}
		quickSelect = 0;
		this.player = player;
	}
	
	public ItemsContainer<Item> getInventory() {
		return savedInventory;
	}
	
	public ItemsContainer<Item> getEquipment() {
		return savedEquipment;
	}
	
	public boolean containsSaves() {
		if (savedEquipment == null && savedInventory == null) 
			return false;
		return true;
	}
	
	public void saveInventory() {
		savedInventory = new ItemsContainer<Item>(28, false);
		for (int i = 0; i < 28; i++) {
			if (player.getInventory().getItem(i) == null)
				continue;
			savedInventory.set(i, player.getInventory().getItems().getItemsCopy()[i]);
		}
	}
	
	public void saveEquipment() {
		savedEquipment = new ItemsContainer<Item>(15, false);
		for (int i = 0; i < 15; i++) {
			if (player.getEquipment().getItem(i) == null)
				continue;
			savedEquipment.set(i, player.getEquipment().getItems().getItemsCopy()[i]);
		}
	}
	
	public void loadPreset() {
		int itemid = 0;
		int notedid = 0;
		boolean issue = false;
		if (player.getInventory().containsItem(995, 2000000000) && player.getBank().containsItem(995)) {
			player.sm("Please resolve your issue of your coin overflow and then continue. ;;100mticket");
			return;
		}
		if (!player.getBank().hasBankSpace()) {
			player.sm("Your bank is full. You cannot load a preset until you have enough space.");
			return;
		}
		if (savedInventory != null) {
			player.getBank().depositAllInventory(true);
			for (int i = 0; i < savedInventory.getSize(); i++) {
				if (savedInventory.get(i) != null) {
					itemid = savedInventory.get(i).getId() ;
					if (player.getBank().containsItem(itemid)) {
						if (savedInventory.get(i).getDefinitions().isStackable()) {
							if (savedInventory.getAllAmount(itemid) <= player.getBank().getItem(itemid).getAmount()) {
								player.getInventory().addItem(itemid, savedInventory.getAllAmount(itemid));
								if (player.getBank().getItem(itemid).getAmount() - savedInventory.getAllAmount(itemid) == 0) 
									player.getBank().removeItem(player.getBank().getItemSlot(itemid), player.getBank().getItem(itemid).getAmount(), true, false);
								else	{
								player.getBank().removeItem(player.getBank().getItemSlot(itemid),
										savedInventory.getAllAmount(itemid), true, false);
								}
							} else { // coin loss
								player.getInventory().addItem(itemid, player.getBank().getItem(itemid).getAmount());
								player.getBank().removeItem(player.getBank().getItemSlot(itemid),
										player.getBank().getItem(itemid).getAmount(), true, false);
								player.sm("<col=E37507>Not all of " + savedInventory.get(i).getName().toLowerCase() + " were withdrawn.");
								issue = true;
							}
						} else {
							player.getBank().removeItem(player.getBank().getItemSlot(itemid), 1, true, false);
							player.getInventory().addItem(itemid, 1);
						}
					} else if (player.getBank().containsItem(new Item(itemid).getDefinitions().getCertId())) {
						if (new Item(itemid).getDefinitions().getCertId() == -1)
							continue;
						notedid = new Item(itemid).getDefinitions().getCertId();
						if (savedInventory.get(i).getDefinitions().isStackable()) {
							if (savedInventory.getAllAmount(itemid) <= player.getBank().getItem(notedid).getAmount()) {
								player.getInventory().addItem(itemid, savedInventory.getAllAmount(itemid));
								if (player.getBank().getItem(notedid).getAmount() - savedInventory.getAllAmount(itemid) == 0) 
									player.getBank().removeItem(player.getBank().getItemSlot(notedid), player.getBank().getItem(notedid).getAmount(), true, false);
								else	{
								player.getBank().removeItem(player.getBank().getItemSlot(notedid),
										savedInventory.getAllAmount(itemid), true, false);
								}
							} else { // coin loss
								player.getInventory().addItem(itemid, player.getBank().getItem(itemid).getAmount());
								player.getBank().removeItem(player.getBank().getItemSlot(itemid),
										player.getBank().getItem(itemid).getAmount(), true, false);
								player.sm("<col=E37507>Not all of " + savedInventory.get(i).getName().toLowerCase() + " were withdrawn.");
								issue = true;
							}
						} else {
							player.getBank().removeItem(player.getBank().getItemSlot(itemid), 1, true, false);
							player.getInventory().addItem(itemid, 1);
						}
					} else {
						issue = true;
						player.sm("<col=E30000>Missing " + savedInventory.get(i).getName().toLowerCase() + " from your bank.");
					}
				}
			}
		}
		if (savedEquipment != null) {
			player.getBank().depositAllEquipment(true);
			for (int i = 0; i < savedEquipment.getSize(); i++) {
				if (savedEquipment.get(i) != null) {
					itemid = savedEquipment.get(i).getId() ;
					if (!ItemConstants.canWear(new Item(itemid), player)) {
						issue = true;
						continue;
					}
					if (player.getBank().containsItem(itemid)) {
						if (savedEquipment.get(i).getDefinitions().isStackable()) {
							if (savedEquipment.getAllAmount(itemid) <= player.getBank().getItem(itemid).getAmount()) {
								player.getEquipment().getItems().set(savedEquipment.get(i).getDefinitions().getEquipSlot(), savedEquipment.get(i));
								player.getEquipment().refresh(savedEquipment.get(i).getDefinitions().getEquipSlot());
								if (player.getBank().getItem(itemid).getAmount() - savedEquipment.getAllAmount(itemid) == 0) 
									player.getBank().removeItem(player.getBank().getItemSlot(itemid),
											player.getBank().getItem(itemid).getAmount(), true, false);
								else	
								player.getBank().removeItem(player.getBank().getItemSlot(itemid),
										savedEquipment.getAllAmount(itemid), true, true);
							} else {
								player.getEquipment().getItems().set(savedEquipment.get(i).getDefinitions().getEquipSlot(), player.getBank().getItem(itemid));
								player.getEquipment().refresh(savedEquipment.get(i).getDefinitions().getEquipSlot());
								player.getBank().removeItem(player.getBank().getItemSlot(itemid),
										player.getBank().getItem(itemid).getAmount(), true, false);
								player.sm("<col=E37507>Not all of " + savedEquipment.get(i).getName().toLowerCase() + " were withdrawn.");
								issue = true;
							}
						} else {
							player.getBank().removeItem(player.getBank().getItemSlot(itemid), 1, true, false);
							player.getEquipment().getItems().set(savedEquipment.get(i).getDefinitions().getEquipSlot(), savedEquipment.get(i));
							player.getEquipment().refresh(savedEquipment.get(i).getDefinitions().getEquipSlot());
							player.getAppearence().generateAppearenceData();
						}
					} else {
						issue = true;
						player.sm("<col=E30000>Missing " + savedEquipment.get(i).getName().toLowerCase() + " from your bank.");
					}
				}
			}			
		}
		player.closeInterfaces();
		if (issue)
			player.sm("Preset loaded with some issues listed above.");
		else
			player.sm("Preset successfully loaded.");
	}
	
	public int changeQuickSelect(int preset) {
		boolean presetused = false;
		boolean preset2used = false;
		BankPreset[] presets = getPresets();
		switch (preset) {
			case 1:
				if (presets[1].getQuickSelect() == 1 ||
					presets[2].getQuickSelect() == 1 ||
					presets[3].getQuickSelect() == 1)
					presetused = true;
				if (presets[1].getQuickSelect() == 2 ||
						presets[2].getQuickSelect() == 2 ||
						presets[3].getQuickSelect() == 2)
						preset2used = true;
				break;
			case 2:
				if (presets[0].getQuickSelect() == 1 ||
					presets[2].getQuickSelect() == 1 ||
					presets[3].getQuickSelect() == 1)
					presetused = true;
				if (presets[0].getQuickSelect() == 2 ||
					presets[2].getQuickSelect() == 2 ||
					presets[3].getQuickSelect() == 2)
						preset2used = true;
				break;
			case 3:
				if (presets[1].getQuickSelect() == 1 ||
					presets[0].getQuickSelect() == 1 ||
					presets[3].getQuickSelect() == 1)
					presetused = true;
				if (presets[1].getQuickSelect() == 2 ||
					presets[0].getQuickSelect() == 2 ||
					presets[3].getQuickSelect() == 2)
						preset2used = true;
				break;
			case 4:
				if (presets[1].getQuickSelect() == 1 ||
					presets[2].getQuickSelect() == 1 ||
					presets[0].getQuickSelect() == 1)
						presetused = true;
				if (presets[1].getQuickSelect() == 2 ||
					presets[2].getQuickSelect() == 2 ||
					presets[0].getQuickSelect() == 2)
						preset2used = true;
				break;
		}
		if (presetused && preset2used)
			return quickSelect = 0;
		if (presetused && quickSelect == 0)
			return quickSelect = 2;
		else if (preset2used && quickSelect == 0)
			return quickSelect = 1;
		if (quickSelect == 1) {
			if (preset2used)
				return quickSelect = 0;
			else
				return quickSelect = 2;
		}
		if (quickSelect == 2) 
			return quickSelect = 0;
		if (quickSelect == 0)
			return quickSelect = 1;
		return quickSelect;
	}
	
	public BankPreset[] getPresets() {
		BankPreset[] presets = new BankPreset[4];
		presets[0] = player.getBankPreset();
		presets[1] = player.getBankPreset2();
		presets[2] = player.getBankPreset3();
		presets[3] = player.getBankPreset4();
		return presets;
	}
	
	public int getQuickButton() {
		BankPreset[] presets = getPresets();
		if (presets[0].getQuickSelect() == 1)
			return 1;
		if (presets[1].getQuickSelect() == 1)
			return 2;
		if (presets[2].getQuickSelect() == 1)
			return 3;
		if (presets[3].getQuickSelect() == 1)
			return 4;
		return -1;
	}
	
	public int getQuickButton2() {
		BankPreset[] presets = getPresets();
		if (presets[0].getQuickSelect() == 2)
			return 1;
		if (presets[1].getQuickSelect() == 2)
			return 2;
		if (presets[2].getQuickSelect() == 2)
			return 3;
		if (presets[3].getQuickSelect() == 2)
			return 4;
		return -1;
	}	
	
	public void setQuickSelect(int quickSelect) {
		this.quickSelect = quickSelect;
	}

	public int getQuickSelect() {
		return quickSelect;
	}
	
	public void setPresetName(String presetName) {
		this.presetName = presetName;
	}
	
	public String getPresetName() {
		return presetName;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}	
	
	
}